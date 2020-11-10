package motherlode.core.block;

import motherlode.core.registry.MotherlodeBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.lwjgl.system.CallbackI;

import java.util.Arrays;
import java.util.stream.Collectors;

public class PlatformBlock extends Block implements Waterloggable {
    public static final BooleanProperty WATERLOGGED;
    public static final DirectionProperty FACING;
    public static final VoxelShape NORMAL_SHAPE;
    public static final VoxelShape[] STAIR_SHAPES = new VoxelShape[4];




    public PlatformBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(WATERLOGGED, false));
        MotherlodeBlocks.defaultLootTableList.add(this);
        MotherlodeBlocks.cutouts.add(this);
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
        Direction facing = ctx.getPlayerFacing();
        BlockPos placePos = ctx.getBlockPos();
        FluidState fluidState = ctx.getWorld().getFluidState(placePos);
        World world = ctx.getWorld();
        PlayerEntity playerEntity = ctx.getPlayer();
        BlockState result = null;
        BlockState back = world.getBlockState(placePos.offset(facing.getOpposite()));
        BlockState front = world.getBlockState(placePos.offset(facing));
        if (playerEntity != null && (playerEntity.getBlockPos().getX() != placePos.getX() || playerEntity.getBlockPos().getZ() != placePos.getZ())) {
            if (playerEntity.getY() > placePos.getY() && world.getBlockState(placePos.offset(facing)).getMaterial().isReplaceable()) {
                // Stair Downwards
                result = this.getDefaultState().with(FACING, facing).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
                if (back.isOf(this) && !back.get(FACING).equals(Direction.DOWN)){
                    world.setBlockState(placePos.offset(facing.getOpposite()), back.with(FACING, Direction.DOWN));
                }
            } else if (world.getBlockState(placePos.offset(facing.getOpposite())).getMaterial().isReplaceable()){
                // Stair Upwards
                result = this.getDefaultState().with(FACING, facing.getOpposite()).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
                if (front.isOf(this) && !front.get(FACING).equals(Direction.DOWN)){
                    world.setBlockState(placePos.offset(facing), front.with(FACING, Direction.DOWN));
                }
            }
        }
        if (result == null) result = this.getDefaultState().with(PlatformBlock.FACING, Direction.DOWN).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
        return result;
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

    static{
        WATERLOGGED = Properties.WATERLOGGED;
        FACING = DirectionProperty.of("facing", (facing) -> facing != Direction.UP);
        NORMAL_SHAPE = Block.createCuboidShape(0.0D, 14.0D, 0.0D, 16.0D, 16.0D, 16.0D);

        STAIR_SHAPES[0] = VoxelShapes.union(Block.createCuboidShape(0.0D, 6.0D, 0.0D, 16.0D, 8.0D, 8.0D),
                Block.createCuboidShape(0.0D, 14.0D, 8.0D, 16.0D, 16.0D, 16.0D),
                Block.createCuboidShape(0.0D, 6.0D, 8.0D, 16.0D, 16.0D, 9.0D));

        STAIR_SHAPES[3] = VoxelShapes.union(Block.createCuboidShape(0.0D, 6.0D, 0.0D, 8.0D, 8.0D, 16.0D),
                Block.createCuboidShape(8.0D, 14.0D, 0.0D, 16.0D, 16.0D, 16.0D),
                Block.createCuboidShape(8.0D, 6.0D, 0.0D, 9.0D, 16.0D, 16.0D));

        STAIR_SHAPES[2] = VoxelShapes.union(Block.createCuboidShape(0.0D, 6.0D, 8.0D, 16.0D, 8.0D, 16.0D),
                Block.createCuboidShape(0.0D, 14.0D, 0.0D, 16.0D, 16.0D, 8.0D),
                Block.createCuboidShape(0.0D, 6.0D, 7.0D, 16.0D, 16.0D, 8.0D));

        STAIR_SHAPES[1] = VoxelShapes.union(Block.createCuboidShape(8.0D, 6.0D, 0.0D, 16.0D, 8.0D, 16.0D),
                Block.createCuboidShape(0.0D, 14.0D, 0.0D, 8.0D, 16.0D, 16.0D),
                Block.createCuboidShape(7.0D, 6.0D, 0.0D, 8.0D, 16.0D, 16.0D));
    }
}
