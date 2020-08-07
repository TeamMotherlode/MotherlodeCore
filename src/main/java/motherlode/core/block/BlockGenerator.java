package motherlode.core.block;

import motherlode.core.block.defaults.DefaultShapedBlock;
import motherlode.core.block.defaults.DefaultShovelableBlock;
import motherlode.core.registry.MotherlodeBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.shape.VoxelShape;

/**
 * For blocks that don't need there own class, but is repetitive enough to warrant a method to generate it
 */
public class BlockGenerator {

    private static VoxelShape platformShape = Block.createCuboidShape(0.0D, 13.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    private BlockGenerator(){}

    public static Block mineral(int miningLevel) {
        return new Block(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5.0F, 6.0F).breakByTool(FabricToolTags.PICKAXES, miningLevel));
    }

    public static Block platform() {
        return new DefaultShapedBlock(platformShape, FabricBlockSettings.copy(Blocks.OAK_PLANKS));
    }

    public static Block rockyDirt() {
        return new DefaultShovelableBlock(false, FabricBlockSettings.copy(Blocks.COARSE_DIRT).sounds(BlockSoundGroup.NYLIUM));
    }
}
