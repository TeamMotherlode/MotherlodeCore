package motherlode.copperdungeon.block;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.api.server.PlayerStream;
import motherlode.base.util.PositionUtilities;
import motherlode.copperdungeon.MotherlodeCopperDungeonBlockEntities;
import motherlode.copperdungeon.MotherlodeCopperDungeonBlocks;
import motherlode.copperdungeon.networking.ZapS2CPacket;

public class ZapperTrapBlockEntity extends BlockEntity {
    private static final int ZAP_DELAY_MAXIMUM = 16;
    private static final int ZAP_RADIUS_CONNECTION = 8;
    private static final int RAYCAST_GRANULARITY = 10;
    private static final int ZAP_DAMAGE = 4;

    private int zapDelay = ZAP_DELAY_MAXIMUM;

    public ZapperTrapBlockEntity(BlockPos pos, BlockState state) {
        super(MotherlodeCopperDungeonBlockEntities.ZAPPER_TRAP, pos, state);
    }

    public List<ZapperTrapBlockEntity> searchConnections() {
        List<ZapperTrapBlockEntity> connections = new ArrayList<>();
        this.world.getWorldChunk(getPos()).getBlockEntities().forEach((pos, blockEntity) -> {
            if (blockEntity != this)
                if (blockEntity instanceof ZapperTrapBlockEntity) {
                    ZapperTrapBlockEntity zapper = (ZapperTrapBlockEntity) blockEntity;
                    if (zapper.getPos().isWithinDistance(this.pos, ZAP_RADIUS_CONNECTION)) {
                        connections.add(zapper);
                    }
                }
        });
        return connections;
    }

    public static void tick(World world, BlockPos pos, BlockState state, ZapperTrapBlockEntity blockEntity) {
        if (world.isClient) return;

        BlockState thisBlockState = world.getBlockState(pos);
        if (!thisBlockState.get(DefaultTrapBlock.POWERED)) return;

        blockEntity.zapDelay -= (world.random.nextInt() % 6) + 1;
        if (blockEntity.zapDelay > 0) return;
        blockEntity.zapDelay = ZAP_DELAY_MAXIMUM;

        Direction thisDirection = thisBlockState.get(ZapperTrapBlock.FACING);
        Vec3d start = new Vec3d(pos.getX(), pos.getY(), pos.getZ())
            .add(ZapperTrapBlock.FACING_ROD_OFFSET[thisDirection.getId()]);

        List<ZapperTrapBlockEntity> connections;
        List<Entity> caught = new ArrayList<>();

        connections = blockEntity.searchConnections();
        connections.forEach(zapper -> {
            BlockState zapperBlockState = world.getBlockState(zapper.getPos());
            if (zapperBlockState.get(DefaultTrapBlock.POWERED)) {
                Direction direction = zapperBlockState.get(ZapperTrapBlock.FACING);
                Stream<PlayerEntity> watching = PlayerStream.watching(world, pos);

                // Offset target position by position of lightning rod (precalculated estimates)
                Vec3d target = new Vec3d(zapper.getPos().getX(), zapper.getPos().getY(), zapper.getPos().getZ())
                    .add(ZapperTrapBlock.FACING_ROD_OFFSET[direction.getId()]);

                // Damage any entity within the zap line
                // Raycasts are expensive, especially with what we're doing, so we need to limit the granularity
                double ray_radius = start.distanceTo(target) / RAYCAST_GRANULARITY;
                for (int i = 0; i < RAYCAST_GRANULARITY; i++) {
                    Vec3d lerped = PositionUtilities.fromLerpedPosition(start, target, (float) i / RAYCAST_GRANULARITY);
                    Vec3d lower_bound = lerped.subtract(ray_radius, ray_radius, ray_radius);
                    Vec3d upper_bound = lerped.add(ray_radius, ray_radius, ray_radius);
                    List<Entity> inside = world.getOtherEntities(null, new Box(lower_bound, upper_bound), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR);

                    inside.forEach(entity -> {
                        if (!caught.contains(entity)) {
                            caught.add(entity);
                        }
                    });

                    BlockPos blockPos = new BlockPos((int) lerped.getX(), (int) lerped.getY(), (int) lerped.getZ());
                    if (lerped.x < 0) blockPos = blockPos.subtract(new Vec3i(1, 0, 0));
                    if (lerped.z < 0) blockPos = blockPos.subtract(new Vec3i(0, 0, 1));

                    if (!world.getBlockState(blockPos).isAir()) {
                        if (world.getBlockState(blockPos).getBlock() != MotherlodeCopperDungeonBlocks.ZAPPER_TRAP) {
                            caught.clear();
                            return;
                        }
                    }
                }

                watching.forEach(player ->
                    ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, new ZapS2CPacket(start, target)));
            }
        });

        caught.forEach(entity -> {
            if (entity instanceof LivingEntity)
                entity.damage(DamageSource.GENERIC, ZAP_DAMAGE);
        });
    }
}
