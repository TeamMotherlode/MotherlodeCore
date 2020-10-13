package motherlode.core.block.entity;

import com.google.common.collect.Lists;
import motherlode.core.Motherlode;
import motherlode.core.block.DefaultTrapBlock;
import motherlode.core.block.ZapperTrapBlock;
import motherlode.core.network.packet.s2c.ZapS2CPacket;
import motherlode.core.registry.MotherlodeBlockEntities;
import motherlode.core.registry.MotherlodeBlocks;
import motherlode.core.util.PositionUtilities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.fabricmc.loader.launch.FabricServerTweaker;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.Tickable;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.RayTraceContext;

import java.util.*;
import java.util.stream.Stream;

public class ZapperTrapBlockEntity extends BlockEntity implements Tickable {
    private static final int ZAP_DELAY_MAXIMUM = 16;
    private static final int ZAP_RADIUS_CONNECTION = 8;
    private static final int RAYCAST_GRANULARITY = 10;
    private static final int ZAP_DAMAGE = 4;

    private int zap_delay = ZAP_DELAY_MAXIMUM;

    public ZapperTrapBlockEntity() {
        super(MotherlodeBlockEntities.ZAPPER_TRAP);
    }

    public List<ZapperTrapBlockEntity> searchConnections(){
        List<ZapperTrapBlockEntity> connections = new ArrayList<>();
        this.world.blockEntities.forEach(blockEntity -> {
            if(blockEntity != this)
                if(blockEntity instanceof ZapperTrapBlockEntity){
                    ZapperTrapBlockEntity zapper = (ZapperTrapBlockEntity)blockEntity;
                    if(zapper.getPos().isWithinDistance(this.pos, ZAP_RADIUS_CONNECTION)) {
                        connections.add(zapper);
                    }
                }
        });
        return connections;
    }

    @Override
    public void tick() {
        if(this.world.isClient) return;

        BlockState thisBlockState = this.world.getBlockState(this.pos);
        if(!thisBlockState.get(DefaultTrapBlock.POWERED)) return;

        zap_delay -= (this.world.random.nextInt() % 6) + 1;
        if(zap_delay > 0) return;
        zap_delay = ZAP_DELAY_MAXIMUM;

        Direction thisDirection = thisBlockState.get(ZapperTrapBlock.FACING);
        Vec3d start = new Vec3d(this.pos.getX(), this.pos.getY(), this.pos.getZ())
                .add(ZapperTrapBlock.FACING_ROD_OFFSET[thisDirection.getId()]);

        List<ZapperTrapBlockEntity> connections;
        List<Entity> caught = new ArrayList<>();

        connections = searchConnections();
        connections.forEach(zapper -> {
            BlockState zapperBlockState = this.world.getBlockState(zapper.getPos());
            if (zapperBlockState.get(DefaultTrapBlock.POWERED)) {
                Direction direction = zapperBlockState.get(ZapperTrapBlock.FACING);
                Stream<PlayerEntity> watching = PlayerStream.watching(this.world, this.pos);

                //Offset target position by position of lightning rod (precalculated estimates)
                Vec3d target = new Vec3d(zapper.getPos().getX(), zapper.getPos().getY(), zapper.getPos().getZ())
                        .add(ZapperTrapBlock.FACING_ROD_OFFSET[direction.getId()]);

                //Damage any entity within the zap line
                //Raycasts are expensive, especially with what we're doing, so we need to limit the granularity
                double ray_radius = start.distanceTo(target) / RAYCAST_GRANULARITY;
                for(int i = 0; i < RAYCAST_GRANULARITY; i++){
                    Vec3d lerped = PositionUtilities.fromLerpedPosition(start, target, (float)i / RAYCAST_GRANULARITY);
                    Vec3d lower_bound = lerped.subtract(ray_radius, ray_radius, ray_radius);
                    Vec3d upper_bound = lerped.add(ray_radius, ray_radius, ray_radius);
                    List<Entity> inside = this.world.getEntities((Entity)null, new Box(lower_bound, upper_bound), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR);

                    inside.forEach(entity -> {
                        if(!caught.contains(entity)){
                            caught.add(entity);
                        }
                    });

                    BlockPos blockPos = new BlockPos((int)lerped.getX(), (int)lerped.getY(), (int)lerped.getZ());
                    if(lerped.x < 0) blockPos = blockPos.subtract(new Vec3i(1, 0, 0));
                    if(lerped.z < 0) blockPos = blockPos.subtract(new Vec3i(0, 0, 1));

                    if(!world.getBlockState(blockPos).isAir()){
                        if(world.getBlockState(blockPos).getBlock() != MotherlodeBlocks.TRAP_ZAPPER){
                            caught.clear();
                            return;
                        }
                    }
                }

                watching.forEach(player -> {
                    ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, new ZapS2CPacket(start, target));
                });
            }
        });

        caught.forEach(entity -> {
            if(entity instanceof LivingEntity)
                entity.damage(DamageSource.GENERIC, ZAP_DAMAGE);
        });
    }
}
