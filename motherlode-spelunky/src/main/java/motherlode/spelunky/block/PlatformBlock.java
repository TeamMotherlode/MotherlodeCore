package motherlode.spelunky.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public class PlatformBlock extends Block implements Waterloggable {
    public static final BooleanProperty WATERLOGGED;
    public static final VoxelShape TOP_SHAPE;
    public static final VoxelShape BOTTOM_SHAPE;
    public static final VoxelShape DUBBLE_SHAPE;
    public static final EnumProperty<SlabType> TYPE;

    public PlatformBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false).with(TYPE, SlabType.TOP));
    }

    /**
     * Based on the given variables, what should the outline be for the block?
     * The outline is the part that you can hit/collide with.
     * In this case the outline is only dependent on the state. If it is top, bottom or dubble.
     */
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        SlabType type = state.get(TYPE);
        switch (type) {
            case TOP:
                return TOP_SHAPE;
            case BOTTOM:
                return BOTTOM_SHAPE;
            default:
                return DUBBLE_SHAPE;
        }
    }

    /**
     * This function gives the resulting state when a update happens to the neighbour of the block.
     * In this method nothing really happens to the blockstate itself, but if it is waterlogged, we need to update the water
     * to make it start flowing.
     */
    @Override
    @SuppressWarnings("deprecation")
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (state.get(WATERLOGGED)) {
            world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return state;
    }

    /**
     * The state of the block when it is placed.
     * Mostly copied from {@link SlabBlock#getPlacementState(ItemPlacementContext ctx)} as the code is exactly the same.
     *
     * @param ctx the itemplacementcontext when the block is placed.
     * @return Blockstate the state to return.
     */
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        BlockState blockState = ctx.getWorld().getBlockState(blockPos);
        if (blockState.isOf(this)) {
            return blockState.with(TYPE, SlabType.DOUBLE).with(WATERLOGGED, false);
        } else {
            FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
            BlockState blockState2 = this.getDefaultState().with(TYPE, SlabType.BOTTOM).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
            Direction direction = ctx.getSide();
            return direction != Direction.DOWN && (direction == Direction.UP || ctx.getHitPos().y - (double) blockPos.getY() <= 0.5D) ? blockState2 : blockState2.with(TYPE, SlabType.TOP);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        ItemStack itemStack = context.getStack();
        SlabType slabType = state.get(TYPE);
        if (slabType != SlabType.DOUBLE && itemStack.getItem() == this.asItem()) {
            if (context.canReplaceExisting()) {
                boolean bl = context.getHitPos().y - (double) context.getBlockPos().getY() > 0.5D;
                Direction direction = context.getSide();
                if (slabType == SlabType.BOTTOM) {
                    return direction == Direction.UP || bl && direction.getAxis().isHorizontal();
                } else {
                    return direction == Direction.DOWN || !bl && direction.getAxis().isHorizontal();
                }
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED).add(TYPE);
    }

    static {
        WATERLOGGED = Properties.WATERLOGGED;

        TYPE = Properties.SLAB_TYPE;

        TOP_SHAPE = Block.createCuboidShape(0.0D, 14.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        BOTTOM_SHAPE = Block.createCuboidShape(0.0D, 6.0D, 0.0D, 16.0D, 8.0D, 16.0D);
        DUBBLE_SHAPE = VoxelShapes.union(TOP_SHAPE, BOTTOM_SHAPE);
    }
}
