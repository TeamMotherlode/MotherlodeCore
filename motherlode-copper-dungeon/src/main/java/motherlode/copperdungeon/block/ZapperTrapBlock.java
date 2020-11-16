package motherlode.copperdungeon.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import motherlode.base.mixin.BlockWithEntityAccessor;
import motherlode.copperdungeon.MotherlodeCopperDungeonBlockEntities;
import motherlode.copperdungeon.particle.ZapParticle;

public class ZapperTrapBlock extends DefaultTrapBlock implements BlockEntityProvider {
    public static final DirectionProperty FACING = Properties.FACING;
    public static final Vec3d[] FACING_ROD_OFFSET = {
        new Vec3d(0.5f, 0.15f, 0.5f), // Down
        new Vec3d(0.5f, 0.85f, 0.5f), // Up
        new Vec3d(0.5f, 0.5f, 0.15f), // North
        new Vec3d(0.5f, 0.5f, 0.85f), // South
        new Vec3d(0.15f, 0.5f, 0.5f), // East
        new Vec3d(0.85f, 0.5f, 0.5f) // West
    };

    /* protected static final VoxelShape[] OUTLINE_SHAPES = {
        VoxelShapes.union(// Down
            createCuboidShape(0.d, 16.d, 0.d, 16.d, 10.d, 16.d),
            createCuboidShape(4.d, 9.d, 4.d, 12.d, 8.d, 12.d),
            createCuboidShape(4.5d, 7.d, 4.5d, 11.5d, 6.d, 11.5d),
            createCuboidShape(5.d, 5.d, 5.d, 11.d, 4.d, 11.d),
            createCuboidShape(6.5d, 3.d, 6.5d, 9.5d, 0.d, 9.5d)),
        VoxelShapes.union(// Up
            createCuboidShape(0.d, 0.d, 0.d, 16.d, 6.d, 16.d),
            createCuboidShape(4.d, 7.d, 4.d, 12.d, 8.d, 12.d),
            createCuboidShape(4.5d, 9.d, 4.5d, 11.5d, 10.d, 11.5d),
            createCuboidShape(5.d, 11.d, 5.d, 11.d, 12.d, 11.d),
            createCuboidShape(6.5d, 13.d, 6.5d, 9.5d, 16.d, 9.5d)),
        VoxelShapes.union(// North
            createCuboidShape(0.d, 0.d, 16.d, 16.d, 16.d, 10.d),
            createCuboidShape(4.d, 4.d, 9.d, 12.d, 12.d, 8.d),
            createCuboidShape(4.5d, 4.5d, 7.d, 11.5d, 11.5d, 6.d),
            createCuboidShape(5.d, 5.d, 5.d, 11.d, 11.d, 4.d),
            createCuboidShape(6.5d, 6.5d, 3.d, 9.5d, 9.5d, 0.d)),
        VoxelShapes.union(// South
            createCuboidShape(0.d, 0.d, 0.d, 16.d, 16.d, 6.d),
            createCuboidShape(4.d, 4.d, 7.d, 12.d, 12.d, 8.d),
            createCuboidShape(4.5d, 4.5d, 9.d, 11.5d, 11.5d, 10.d),
            createCuboidShape(5.d, 5.d, 11.d, 11.d, 11.d, 12.d),
            createCuboidShape(6.5d, 6.5d, 13.d, 9.5d, 9.5d, 16.d)),
        VoxelShapes.union(// East
            createCuboidShape(16.d, 0.d, 0.d, 10.d, 16.d, 16.d),
            createCuboidShape(9.d, 4.d, 4.d, 8.d, 12.d, 12.d),
            createCuboidShape(7.d, 4.5d, 4.5d, 6.0d, 11.5d, 11.5d),
            createCuboidShape(5.d, 5.d, 5.d, 4.d, 11.d, 11.d),
            createCuboidShape(3.d, 6.5d, 6.5d, 0d, 9.5d, 9.5d)),
        VoxelShapes.union(// West
            createCuboidShape(0.d, 0.d, 0.d, 6.d, 16.d, 16.d),
            createCuboidShape(7.d, 4.d, 4.d, 8.d, 12.d, 12.d),
            createCuboidShape(9.d, 4.5d, 4.5d, 10.d, 11.5d, 11.5d),
            createCuboidShape(11.d, 5.d, 5.d, 12.d, 11.d, 11.d),
            createCuboidShape(13.d, 6.5d, 6.5d, 16.d, 9.5d, 9.5d)),
    }; */

    public ZapperTrapBlock(Settings settings) {
        super(settings.luminance((blockState) -> blockState.get(POWERED) ? 4 : 0));
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.UP));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState()
            .with(POWERED, false)
            .with(FACING, ctx.getPlayerLookDirection().getOpposite());
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    /* @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return OUTLINE_SHAPES[state.get(FACING).getId()];
    } */

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED);
    }

    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(POWERED)) {
            double d = (double) pos.getX() + 0.5D;
            double e = pos.getY();
            double f = (double) pos.getZ() + 0.5D;

            MinecraftClient.getInstance().particleManager.addParticle(new ZapParticle(MinecraftClient.getInstance().world, new Vec3d(d, e, f)));
        }
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ZapperTrapBlockEntity(pos, state);
    }

    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? BlockWithEntityAccessor.callCheckType(type, MotherlodeCopperDungeonBlockEntities.ZAPPER_TRAP, ZapperTrapBlockEntity::tick) : null;
    }
}
