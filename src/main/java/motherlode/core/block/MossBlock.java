package motherlode.core.block;

import com.google.common.collect.Maps;
import motherlode.core.registry.MotherlodeBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.Map;

public class MossBlock extends Block {
    public static final DirectionProperty SIDE;

    private static final Map<Direction, VoxelShape> SHAPES = Maps.newHashMap();

    public MossBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(SIDE, Direction.DOWN));
        MotherlodeBlocks.cutouts.add(this);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES.get(state.get(SIDE));
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.offset(state.get(SIDE))).isSideSolidFullSquare(world, pos, state.get(SIDE).getOpposite()) || world.getBlockState(pos.offset(state.get(SIDE))).getBlock().isIn(BlockTags.LEAVES);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState().with(SIDE, ctx.getSide().getOpposite());
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        super.neighborUpdate(state, world, pos, block, fromPos, notify);
        if(!canPlaceAt(state, world, pos)) {
            world.breakBlock(pos, true);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SIDE);
    }

    @Override
    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.empty();
    }

    static {
        SIDE = DirectionProperty.of("side", Direction.values());
        SHAPES.put(Direction.DOWN, createCuboidShape(0, 0, 0, 16, 1.5, 16));
        SHAPES.put(Direction.UP, createCuboidShape(0, 14, 0, 16, 16, 16));
        SHAPES.put(Direction.NORTH, createCuboidShape(0, 0, 0, 16, 16, 1));
        SHAPES.put(Direction.SOUTH, createCuboidShape(0, 0, 15, 16, 16, 16));
        SHAPES.put(Direction.EAST, createCuboidShape(15, 0, 0, 16, 16, 16));
        SHAPES.put(Direction.WEST, createCuboidShape(0, 0, 0, 1, 16, 16));
    }
}
