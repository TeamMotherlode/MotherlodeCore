package motherlode.core.block;

import motherlode.core.Motherlode;
import motherlode.core.registry.MotherlodeBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class PlatformBlock extends Block implements Waterloggable {
    public static final BooleanProperty WATERLOGGED;
    public static final DirectionProperty FACING;
    public static final VoxelShape NORMAL_SHAPE;
    public static final VoxelShape[] STAIR_SHAPES = new VoxelShape[4];


    static{
        WATERLOGGED = Properties.WATERLOGGED;
        FACING = DirectionProperty.of("facing", (facing) -> facing != Direction.UP);
        NORMAL_SHAPE = Block.createCuboidShape(0.0D, 14.0D, 0.0D, 16.0D, 16.0D, 16.0D);

        STAIR_SHAPES[0] = VoxelShapes.union(Block.createCuboidShape(0.0D, 6.0D, 0.0D, 16.0D, 8.0D, 8.0D)
                , Block.createCuboidShape(0.0D, 14.0D, 8.0D, 16.0D, 16.0D, 16.0D)
                , Block.createCuboidShape(0.0D, 6.0D, 8.0D, 16.0D, 16.0D, 9.0D));

        STAIR_SHAPES[3] = VoxelShapes.union(Block.createCuboidShape(0.0D, 6.0D, 0.0D, 8.0D, 8.0D, 16.0D)
                , Block.createCuboidShape(8.0D, 14.0D, 0.0D, 16.0D, 16.0D, 16.0D)
                , Block.createCuboidShape(8.0D, 6.0D, 0.0D, 9.0D, 16.0D, 16.0D));

        STAIR_SHAPES[2] = VoxelShapes.union(Block.createCuboidShape(0.0D, 6.0D, 8.0D, 16.0D, 8.0D, 16.0D)
                , Block.createCuboidShape(0.0D, 14.0D, 0.0D, 16.0D, 16.0D, 8.0D)
                , Block.createCuboidShape(0.0D, 6.0D, 7.0D, 16.0D, 16.0D, 8.0D));

        STAIR_SHAPES[1] = VoxelShapes.union(Block.createCuboidShape(8.0D, 6.0D, 0.0D, 16.0D, 8.0D, 16.0D)
                , Block.createCuboidShape(0.0D, 14.0D, 0.0D, 8.0D, 16.0D, 16.0D)
                , Block.createCuboidShape(7.0D, 6.0D, 0.0D, 8.0D, 16.0D, 16.0D));
    }

    public PlatformBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(WATERLOGGED, false));
        MotherlodeBlocks.defaultLootTableList.add(this);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction dir = state.get(FACING);
        switch(dir){
            case NORTH:
                return STAIR_SHAPES[0];
            case EAST:
                return STAIR_SHAPES[1];
            case SOUTH:
                return STAIR_SHAPES[2];
            case WEST:
                return STAIR_SHAPES[3];
            default:
                return NORMAL_SHAPE;
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (state.get(WATERLOGGED)) {
            world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, newState, world,pos, posFrom);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getPlayerLookDirection().getOpposite();
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        FluidState fluidState = ctx.getWorld().getFluidState(pos);
        BlockState stateFront = world.getBlockState(pos.offset(direction.getOpposite()));
        BlockState stateBack = world.getBlockState(pos.offset(direction));
        if (stateFront.getBlock() instanceof PlatformBlock){
            world.setBlockState(pos.offset(direction.getOpposite()), stateFront.with(PlatformBlock.FACING, Direction.DOWN));
        }
        if (!(stateBack.getBlock() instanceof AirBlock)) {
            return this.getDefaultState().with(FACING, Direction.DOWN).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
        } else {
            return this.getDefaultState().with(FACING, direction.getAxis() == Direction.Axis.Y ? Direction.DOWN : direction).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
        }
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED).add(FACING);
    }
}
