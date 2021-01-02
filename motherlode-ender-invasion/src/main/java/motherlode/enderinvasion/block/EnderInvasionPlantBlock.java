package motherlode.enderinvasion.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import motherlode.base.block.DefaultPlantBlock;
import motherlode.enderinvasion.MotherlodeEnderInvasionBlocks;

public class EnderInvasionPlantBlock extends DefaultPlantBlock {
    private final VoxelShape shape;

    public EnderInvasionPlantBlock(Settings settings) {
        super(settings);
        this.shape = null;
    }

    public EnderInvasionPlantBlock(Settings settings, double height) {
        super(settings);
        this.shape = Block.createCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, height, 11.0D);
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.shape != null ? this.shape : super.getOutlineShape(state, world, pos, context);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return super.canPlantOnTop(floor, world, pos) || floor.isOf(MotherlodeEnderInvasionBlocks.CORRUPTED_DIRT);
    }
}
