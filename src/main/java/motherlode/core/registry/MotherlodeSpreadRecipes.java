package motherlode.core.registry;

import net.minecraft.block.Blocks;
import static motherlode.core.Motherlode.id;
import static motherlode.core.enderinvasion.SpreadingBlocksManager.SPREAD;
import static motherlode.core.enderinvasion.SpreadingBlocksManager.PURIFICATION;
import static motherlode.core.enderinvasion.SpreadingBlocksManager.addSimpleRecipe;

public class MotherlodeSpreadRecipes {

    public static void register() {

        addSimpleRecipe(SPREAD, id("grass_block_to_end_grass_block"), Blocks.GRASS_BLOCK, MotherlodeBlocks.END_GRASS_BLOCK);
        addSimpleRecipe(SPREAD, id("stone_to_end_stone"), Blocks.STONE, Blocks.END_STONE);

        addSimpleRecipe(PURIFICATION, id("end_grass_block_purification"), MotherlodeBlocks.END_GRASS_BLOCK, Blocks.GRASS_BLOCK);
        addSimpleRecipe(PURIFICATION, id("end_stone_purification"), Blocks.END_STONE, Blocks.STONE);
    }
}
