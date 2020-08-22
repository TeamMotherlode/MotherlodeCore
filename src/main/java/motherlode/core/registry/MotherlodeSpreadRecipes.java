package motherlode.core.registry;

import motherlode.core.enderinvasion.SpreadingBlocksManager;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.PillarBlock;
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
        addSimpleRecipe(SPREAD, id("ferns_to_corrupted_grass"), Blocks.FERN, MotherlodeBlocks.CORRUPTED_GRASS);
        addSimpleRecipe(SPREAD, id("sprouts_to_corrupted_grass"), MotherlodeBlocks.SPROUTS, MotherlodeBlocks.CORRUPTED_GRASS);
        addSimpleRecipe(SPREAD, id("brown_mushroom_to_end_cap"), Blocks.BROWN_MUSHROOM, MotherlodeBlocks.END_CAP);
        addSimpleRecipe(SPREAD, id("red_mushroom_to_end_cap"), Blocks.RED_MUSHROOM, MotherlodeBlocks.END_CAP);
        addRecipe(SPREAD, id("logs_to_withered_log"), BlockTags.LOGS::contains, state -> MotherlodeBlocks.WITHERED_LOG.getDefaultState()
                .with(PillarBlock.AXIS, state.get(PillarBlock.AXIS)));

        addLeavesRecipe(SPREAD, "oak_leaves_to_withered_oak_leaves", Blocks.OAK_LEAVES, MotherlodeBlocks.WITHERED_OAK_LEAVES);
        addLeavesRecipe(SPREAD, "dark_oak_leaves_to_withered_dark_oak_leaves", Blocks.DARK_OAK_LEAVES, MotherlodeBlocks.WITHERED_DARK_OAK_LEAVES);
        addLeavesRecipe(SPREAD, "spruce_leaves_to_withered_spruce_leaves", Blocks.SPRUCE_LEAVES, MotherlodeBlocks.WITHERED_SPRUCE_LEAVES);
        addLeavesRecipe(SPREAD, "birch_leaves_to_withered_birch_leaves", Blocks.BIRCH_LEAVES, MotherlodeBlocks.WITHERED_BIRCH_LEAVES);
        addLeavesRecipe(SPREAD, "jungle_leaves_to_withered_jungle_leaves", Blocks.JUNGLE_LEAVES, MotherlodeBlocks.WITHERED_JUNGLE_LEAVES);
        addLeavesRecipe(SPREAD, "acacia_leaves_to_withered_acacia_leaves", Blocks.ACACIA_LEAVES, MotherlodeBlocks.WITHERED_ACACIA_LEAVES);

        addSimpleRecipe(PURIFICATION, id("corrupted_dirt_purification"), MotherlodeBlocks.CORRUPTED_DIRT, Blocks.DIRT);
        addSimpleRecipe(PURIFICATION, id("corrupted_grass_purification"), MotherlodeBlocks.CORRUPTED_GRASS, Blocks.GRASS);
        addSimpleRecipe(PURIFICATION, id("end_stone_purification"), Blocks.END_STONE, Blocks.STONE);
    }
    private static void addLeavesRecipe(SpreadingBlocksManager manager, String name, Block from, Block to) {

        addRecipe(manager, id(name), from::equals, state -> to.getDefaultState()
                .with(LeavesBlock.DISTANCE, state.get(LeavesBlock.DISTANCE))
                .with(LeavesBlock.PERSISTENT, state.get(LeavesBlock.PERSISTENT)));
    }
}
