package motherlode.core.block;

import motherlode.core.api.FluidFlowable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class GrateBlock extends DefaultBlock implements FluidFlowable {
    protected static final BooleanProperty OPEN = BooleanProperty.of("open");

    public GrateBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(FLUID, EMPTY)
                .with(FlowableFluid.LEVEL, 8)
                .with(OPEN, false));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        FluidState fluidState = context.getWorld().getFluidState(context.getBlockPos());
        return this.getDefaultState()
                .with(FLUID, Registry.FLUID.getId(fluidState.getFluid()))
                .with(FlowableFluid.LEVEL, Math.max(fluidState.getLevel(), 1))
                .with(OPEN, context.getWorld().isReceivingRedstonePower(context.getBlockPos()));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(OPEN) ? VoxelShapes.empty() : VoxelShapes.fullCube();
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState blockState, Direction direction, BlockState neighborBlockState, WorldAccess world, BlockPos blockPos, BlockPos neighborBlockPos) {
        if (!blockState.get(FLUID).equals(EMPTY)) {
            world.getFluidTickScheduler().schedule(blockPos, Registry.FLUID.get(blockState.get(FLUID)), Registry.FLUID.get(blockState.get(FLUID)).getTickRate(world));
        }

        return super.getStateForNeighborUpdate(blockState, direction, neighborBlockState, world, blockPos, neighborBlockPos);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FLUID).add(FlowableFluid.LEVEL).add(OPEN);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        FluidState state1 = Registry.FLUID.get(state.get(FLUID)).getDefaultState();
        if (state1.getEntries().containsKey(FlowableFluid.LEVEL)) {
            state1 = state1.with(FlowableFluid.LEVEL, state.get(FlowableFluid.LEVEL));
        }
        return state1;
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        super.neighborUpdate(state, world, pos, block, fromPos, notify);
        world.setBlockState(pos, state.with(OPEN, world.isReceivingRedstonePower(pos)));
    }
}