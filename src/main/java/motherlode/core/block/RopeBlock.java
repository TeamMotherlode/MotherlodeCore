package motherlode.core.block;

import motherlode.core.registry.MotherlodeBlocks;
import net.minecraft.block.*;
import net.minecraft.block.enums.WireConnection;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class RopeBlock extends Block {
    public static final EnumProperty<WireConnection> CONNECTED = EnumProperty.of("connected", WireConnection.class);
    public static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty BOTTOM = BooleanProperty.of("bottom");

    private static final VoxelShape ROPE_SHAPE = Block.createCuboidShape(6.5, 0.0, 6.5, 9.5, 16.0, 9.5);
    private static final VoxelShape SHORT_ROPE_SHAPE = Block.createCuboidShape(6.5, 0.0, 6.5, 9.5, 12.0, 9.5);
    private static final VoxelShape HOOK_SHORT_SHAPE = Block.createCuboidShape(7.0, 9.0, 7.0, 9.0, 13.0, 9.0);
    private static final VoxelShape HOOK_LONG_TOP_SHAPE = Block.createCuboidShape(7.0, 9.0, 7.0, 9.0, 16.0, 9.0);
    private static final VoxelShape HOOK_LONG_SIDE_SHAPE_X = Block.createCuboidShape(2.5, 9.0, 7.0, 13.5, 11.0, 9.0);
    private static final VoxelShape HOOK_LONG_SIDE_SHAPE_Z = Block.createCuboidShape(7.0, 9.0, 2.5, 9.0, 11.0, 13.5);

    public RopeBlock() {
        super(AbstractBlock.Settings.of(Material.PLANT));
        this.setDefaultState(this.stateManager.getDefaultState().with(CONNECTED, WireConnection.NONE).with(FACING, Direction.NORTH).with(BOTTOM, true));
    }

    public static boolean canPlaceAt(WorldView worldView, BlockPos pos, Direction direction, WireConnection connection) {
        if (isSupportedByRope(worldView, pos))
            return true;

        if (connection == WireConnection.UP)
            direction = Direction.UP;

        final BlockPos blockPos = pos.offset(direction);
        return worldView.getBlockState(blockPos).isSideSolidFullSquare(worldView, blockPos, direction.getOpposite());
    }

    private static boolean isSupportedByRope(WorldView worldView, BlockPos blockpos) {
        BlockState state = worldView.getBlockState(blockpos);
        if (state.isOf(MotherlodeBlocks.ROPE))
            return worldView.getBlockState(blockpos).get(CONNECTED) == WireConnection.NONE && worldView.getBlockState(blockpos.offset(Direction.UP)).isOf(MotherlodeBlocks.ROPE);

        return worldView.getBlockState(blockpos.offset(Direction.UP)).isOf(MotherlodeBlocks.ROPE);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        final ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.getItem() != MotherlodeBlocks.ROPE.asItem())
            return ActionResult.PASS;

        while (world.getBlockState(pos).isOf(MotherlodeBlocks.ROPE))
            pos = pos.offset(Direction.DOWN);

        if (world.getBlockState(pos).isOf(Blocks.AIR)) {
            world.setBlockState(pos, MotherlodeBlocks.ROPE.getDefaultState());
            if (!player.abilities.creativeMode)
                itemStack.decrement(1);
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAIL;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return canPlaceAt(world, pos, state.get(FACING), state.get(CONNECTED));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction side = ctx.getSide().getOpposite();
        boolean isBelowRope = isSupportedByRope(ctx.getWorld(), ctx.getBlockPos());
        if (side == Direction.UP || side == Direction.DOWN) {
            BlockState state2 = this.getDefaultState().with(FACING, ctx.getPlayerFacing()).with(CONNECTED, isBelowRope ? WireConnection.NONE : WireConnection.UP);
            if (state2.canPlaceAt(ctx.getWorld(), ctx.getBlockPos()))
                return state2;

            for (Direction direction : ctx.getPlacementDirections()) {
                if (direction.getAxis() != Direction.Axis.Y) {
                    state2 = this.getDefaultState().with(FACING, direction).with(CONNECTED, isBelowRope ? WireConnection.NONE : WireConnection.SIDE);
                    if (state2.canPlaceAt(ctx.getWorld(), ctx.getBlockPos()))
                        return state2;
                }
            }
        } else {
            BlockState state2 = this.getDefaultState().with(FACING, side).with(CONNECTED, isBelowRope ? WireConnection.NONE : WireConnection.SIDE);
            if (state2.canPlaceAt(ctx.getWorld(), ctx.getBlockPos()))
                return state2;

            state2 = this.getDefaultState().with(FACING, side).with(CONNECTED, isBelowRope ? WireConnection.NONE : WireConnection.UP);
            if (state2.canPlaceAt(ctx.getWorld(), ctx.getBlockPos()))
                return state2;
        }
        return null;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        WireConnection connected = state.get(CONNECTED);
        if (connected == WireConnection.UP && world.getBlockState(pos.offset(Direction.UP)).isOf(Blocks.AIR))
            return Blocks.AIR.getDefaultState();

        if (connected == WireConnection.SIDE && world.getBlockState(pos.offset(state.get(FACING))).isOf(Blocks.AIR))
            return Blocks.AIR.getDefaultState();

        if (connected == WireConnection.NONE && !world.getBlockState(pos.offset(Direction.UP)).isOf(MotherlodeBlocks.ROPE))
            return Blocks.AIR.getDefaultState();

        if (connected != WireConnection.NONE && world.getBlockState(pos.offset(Direction.UP)).isOf(MotherlodeBlocks.ROPE))
            state = state.with(CONNECTED, WireConnection.NONE);

        return state.with(BOTTOM, !world.getBlockState(pos.offset(Direction.DOWN)).isOf(MotherlodeBlocks.ROPE));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape base;
        if (state.get(CONNECTED) == WireConnection.NONE)
            base = ROPE_SHAPE;
        else {
            Direction facing = state.get(FACING);
            double x_offset = (facing.getAxis() == Direction.Axis.Z) ? 0.0 : ((facing == Direction.EAST) ? -0.125 : 0.125);
            double z_offset = (facing.getAxis() == Direction.Axis.X) ? 0.0 : ((facing == Direction.SOUTH) ? -0.125 : 0.125);
            if (state.get(CONNECTED) == WireConnection.UP)
                base = VoxelShapes.union(SHORT_ROPE_SHAPE, HOOK_SHORT_SHAPE.offset(x_offset, 0.0, z_offset), HOOK_LONG_TOP_SHAPE.offset(-x_offset, 0.0, -z_offset));
            else
                base = VoxelShapes.union(SHORT_ROPE_SHAPE, HOOK_SHORT_SHAPE.offset(x_offset, 0.0, z_offset),
                        facing.getAxis() == Direction.Axis.X ?
                                HOOK_LONG_SIDE_SHAPE_X.offset(-x_offset * 1.25, 0.0, 0.0) :
                                HOOK_LONG_SIDE_SHAPE_Z.offset(0.0, 0.0, -z_offset * 1.25));
        }

        if (state.get(BOTTOM))
            return VoxelShapes.union(base);
        return base;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CONNECTED, FACING, BOTTOM);
    }
}
