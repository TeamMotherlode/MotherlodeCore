package motherlode.core.registry;

import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;

import static motherlode.core.Motherlode.id;
import static motherlode.core.enderinvasion.SpreadingBlocksManager.SPREAD;
import static motherlode.core.enderinvasion.SpreadingBlocksManager.PURIFICATION;
import static motherlode.core.enderinvasion.SpreadingBlocksManager.addRecipe;
import static motherlode.core.enderinvasion.SpreadingBlocksManager.addSimpleRecipe;

public class MotherlodeSpreadRecipes {

    public static void register() {

        addSimpleRecipe(SPREAD, id("grass_block_to_corrupted_dirt"), Blocks.GRASS_BLOCK, MotherlodeBlocks.CORRUPTED_DIRT);
        addSimpleRecipe(SPREAD, id("dirt_to_corrupted_dirt"), Blocks.DIRT, MotherlodeBlocks.CORRUPTED_DIRT);
        addSimpleRecipe(SPREAD, id("stone_to_end_stone"), Blocks.STONE, Blocks.END_STONE);
        addSimpleRecipe(SPREAD, id("grass_to_corrupted_grass"), Blocks.GRASS, MotherlodeBlocks.CORRUPTED_GRASS);
        addSimpleRecipe(SPREAD, id("sprouts_to_corrupted_grass"), MotherlodeBlocks.SPROUTS, MotherlodeBlocks.CORRUPTED_GRASS);
        addSimpleRecipe(SPREAD, id("brown_mushroom_to_end_cap"), Blocks.BROWN_MUSHROOM, MotherlodeBlocks.END_CAP);
        addSimpleRecipe(SPREAD, id("red_mushroom_to_end_cap"), Blocks.RED_MUSHROOM, MotherlodeBlocks.END_CAP);
        addRecipe(SPREAD, id("logs_to_withered_log"), BlockTags.LOGS::contains, state -> MotherlodeBlocks.WITHERED_LOG.getDefaultState());

        addSimpleRecipe(PURIFICATION, id("corrupted_dirt_purification"), MotherlodeBlocks.CORRUPTED_DIRT, Blocks.DIRT);
        addSimpleRecipe(PURIFICATION, id("corrupted_grass_purification"), MotherlodeBlocks.CORRUPTED_GRASS, Blocks.GRASS);
        addSimpleRecipe(PURIFICATION, id("end_stone_purification"), Blocks.END_STONE, Blocks.STONE);
    }
}
