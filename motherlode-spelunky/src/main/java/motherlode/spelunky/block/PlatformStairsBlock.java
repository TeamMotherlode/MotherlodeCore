package motherlode.spelunky.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.enums.StairShape;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import motherlode.base.block.MotherlodeStairsBlock;

public class PlatformStairsBlock extends MotherlodeStairsBlock {

    public PlatformStairsBlock(BlockState baseBlockState, Settings settings) {
        super(baseBlockState, settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction dir = state.get(PlatformStairsBlock.FACING);
        int id = dir.getOpposite().getHorizontal();
        int add = getShapeIndexIndex(state);
        if (state.get(SHAPE).equals(StairShape.INNER_RIGHT) || state.get(SHAPE).equals(StairShape.OUTER_RIGHT))
            id = (id + 1) % 4;
        id = add * 4 + id;
        return STAIR_SHAPES[id];
    }

    public int getShapeIndexIndex(BlockState state) {
        int i = state.get(SHAPE).ordinal();
        if (i == 2) return 1;
        else if (i == 3 || i == 4) return 2;
        return i;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        //noinspection ConstantConditions
        ItemPlacementContext ctx2 = new ItemPlacementContext(ctx.getPlayer(), ctx.getHand(), ctx.getStack(),
            new BlockHitResult(ctx.getHitPos(), Direction.UP, ctx.getBlockPos(), ctx.hitsInsideBlock()));
        return super.getPlacementState(ctx2);
    }

    private static final VoxelShape[] STAIR_SHAPES;

    private static final VoxelShape BNW, BSW, TNW, TSW, BNE, BSE, TNE, TSE;

    static {
        BNW = Block.createCuboidShape(0.0D, 6.0D, 0.0D, 8.0D, 8.0D, 8.0D);
        BSW = Block.createCuboidShape(0.0D, 6.0D, 8.0D, 8.0D, 8.0D, 16.0D);
        TNW = Block.createCuboidShape(0.0D, 14.0D, 0.0D, 8.0D, 16.0D, 8.0D);
        TSW = Block.createCuboidShape(0.0D, 14.0D, 8.0D, 8.0D, 16.0D, 16.0D);
        BNE = Block.createCuboidShape(8.0D, 6.0D, 0.0D, 16.0D, 8.0D, 8.0D);
        BSE = Block.createCuboidShape(8.0D, 6.0D, 8.0D, 16.0D, 8.0D, 16.0D);
        TNE = Block.createCuboidShape(8.0D, 14.0D, 0.0D, 16.0D, 16.0D, 8.0D);
        TSE = Block.createCuboidShape(8.0D, 14.0D, 8.0D, 16.0D, 16.0D, 16.0D);
        STAIR_SHAPES = new VoxelShape[] {
            //Normal Straight S W N E
            VoxelShapes.union(TNW, TNE, BSW, BSE), VoxelShapes.union(BNW, TNE, BSW, TSE), VoxelShapes.union(BNW, BNE, TSW, TSE), VoxelShapes.union(TNW, BNE, TSW, BSE),
            //Corners Inner
            VoxelShapes.union(TNW, TNE, TSW, BSE), VoxelShapes.union(TNW, TNE, BSW, TSE), VoxelShapes.union(BNW, TNE, TSW, TSE), VoxelShapes.union(TNW, BNE, TSW, TSE),
            //Corners Outer
            VoxelShapes.union(TNW, BNE, BSW, BSE), VoxelShapes.union(BNW, TNE, BSW, BSE), VoxelShapes.union(BNW, BNE, BSW, TSE), VoxelShapes.union(BNW, BNE, TSW, BSE),
        };
    }
}
