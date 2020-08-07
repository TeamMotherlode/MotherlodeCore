package motherlode.core.block.defaults;

import motherlode.core.registry.MotherlodeBlocks;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

import java.util.function.Function;

public class DefaultPlantBlock extends PlantBlock {
    private final int height;

    public DefaultPlantBlock(int height, Function<Block, String> textureName, Settings settings) {
        super(settings);
        this.height = height;
    }

    public DefaultPlantBlock(int height, Settings settings) {
        this(height, block -> block.getTranslationKey().replace("block.motherlode.",""), settings);
    }

    public DefaultPlantBlock(int height, String textureName, Settings settings) {
        this(height, block -> textureName, settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return isHoldingShovelOrSword(context) ? VoxelShapes.empty() : Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, height, 14.0D);
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.XYZ;
    }

    public static boolean isHoldingShovelOrSword(ShapeContext ctx) {
        boolean b = false;
        for(Item e : Registry.ITEM) {
            if(e instanceof ShovelItem || e instanceof SwordItem) {
                if(ctx.isHolding(e)) b = true;
            }
        }
        return b;
    }
}
