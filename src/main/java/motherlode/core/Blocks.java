package motherlode.core;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;

public class Blocks {
    public static final Block COPPER_ORE;
    public static final Block COPPER_BLOCK;
    
    static{
        COPPER_ORE = new OreBlock(AbstractBlock.Settings.of(Material.STONE).requiresTool().strength(3.0F, 3.0F));
        COPPER_BLOCK = new Block(AbstractBlock.Settings.of(Material.METAL).requiresTool().strength(3.0F, 3.0F));
    }
}