package motherlode.core.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class DefaultShapedBlock extends DefaultBlock {
    public final VoxelShape shape;

    public DefaultShapedBlock(VoxelShape shape, Settings settings) {
        this(shape, true, true, true, true, settings);
    }

    public DefaultShapedBlock(VoxelShape shape, boolean hasDefaultState, boolean hasDefaultModel, boolean hasDefaultItemModel, boolean hasDefaultLootTable, Settings settings) {
        super(hasDefaultState, hasDefaultModel, hasDefaultItemModel, hasDefaultLootTable, settings);

        this.shape = shape;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return shape;
    }

    @Override
    public Block getBlockInstance() {
        return this;
    }
}
