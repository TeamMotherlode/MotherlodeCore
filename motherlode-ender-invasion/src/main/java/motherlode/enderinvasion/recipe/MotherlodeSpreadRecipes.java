package motherlode.enderinvasion.recipe;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.PillarBlock;
import net.minecraft.tag.BlockTags;
import motherlode.enderinvasion.MotherlodeEnderInvasionBlocks;
import static motherlode.enderinvasion.MotherlodeModule.id;
import static motherlode.enderinvasion.recipe.BlockRecipeManager.PURIFICATION;
import static motherlode.enderinvasion.recipe.BlockRecipeManager.SPREAD;
import static motherlode.enderinvasion.recipe.BlockRecipeManager.addRecipe;
import static motherlode.enderinvasion.recipe.BlockRecipeManager.addSimpleRecipe;

public class MotherlodeSpreadRecipes {
    public static void register() {
        addSimpleRecipe(SPREAD, id("grass_block_to_corrupted_dirt"), Blocks.GRASS_BLOCK, MotherlodeEnderInvasionBlocks.CORRUPTED_DIRT);
        addSimpleRecipe(SPREAD, id("dirt_to_corrupted_dirt"), Blocks.DIRT, MotherlodeEnderInvasionBlocks.CORRUPTED_DIRT);
        addSimpleRecipe(SPREAD, id("stone_to_end_stone"), Blocks.STONE, Blocks.END_STONE);
        addSimpleRecipe(SPREAD, id("grass_to_corrupted_grass"), Blocks.GRASS, MotherlodeEnderInvasionBlocks.CORRUPTED_GRASS);
        addSimpleRecipe(SPREAD, id("ferns_to_corrupted_grass"), Blocks.FERN, MotherlodeEnderInvasionBlocks.CORRUPTED_GRASS);
        // addSimpleRecipe(SPREAD, id("sprouts_to_corrupted_grass"), MotherlodeEnderInvasionBlocks.SPROUTS, MotherlodeEnderInvasionBlocks.CORRUPTED_GRASS); // TODO
        addSimpleRecipe(SPREAD, id("brown_mushroom_to_end_cap"), Blocks.BROWN_MUSHROOM, MotherlodeEnderInvasionBlocks.END_CAP);
        addSimpleRecipe(SPREAD, id("red_mushroom_to_end_cap"), Blocks.RED_MUSHROOM, MotherlodeEnderInvasionBlocks.END_CAP);
        addRecipe(SPREAD, id("logs_to_withered_log"), BlockTags.LOGS::contains, state -> MotherlodeEnderInvasionBlocks.WITHERED_LOG.getDefaultState()
            .with(PillarBlock.AXIS, state.get(PillarBlock.AXIS)));

        addLeavesRecipe(SPREAD, "oak_leaves_to_withered_oak_leaves", Blocks.OAK_LEAVES, MotherlodeEnderInvasionBlocks.WITHERED_OAK_LEAVES);
        addLeavesRecipe(SPREAD, "dark_oak_leaves_to_withered_dark_oak_leaves", Blocks.DARK_OAK_LEAVES, MotherlodeEnderInvasionBlocks.WITHERED_DARK_OAK_LEAVES);
        addLeavesRecipe(SPREAD, "spruce_leaves_to_withered_spruce_leaves", Blocks.SPRUCE_LEAVES, MotherlodeEnderInvasionBlocks.WITHERED_SPRUCE_LEAVES);
        addLeavesRecipe(SPREAD, "birch_leaves_to_withered_birch_leaves", Blocks.BIRCH_LEAVES, MotherlodeEnderInvasionBlocks.WITHERED_BIRCH_LEAVES);
        addLeavesRecipe(SPREAD, "jungle_leaves_to_withered_jungle_leaves", Blocks.JUNGLE_LEAVES, MotherlodeEnderInvasionBlocks.WITHERED_JUNGLE_LEAVES);
        addLeavesRecipe(SPREAD, "acacia_leaves_to_withered_acacia_leaves", Blocks.ACACIA_LEAVES, MotherlodeEnderInvasionBlocks.WITHERED_ACACIA_LEAVES);

        addSimpleRecipe(PURIFICATION, id("corrupted_dirt_purification"), MotherlodeEnderInvasionBlocks.CORRUPTED_DIRT, Blocks.DIRT);
        addSimpleRecipe(PURIFICATION, id("corrupted_grass_purification"), MotherlodeEnderInvasionBlocks.CORRUPTED_GRASS, Blocks.GRASS);
        addSimpleRecipe(PURIFICATION, id("end_stone_purification"), Blocks.END_STONE, Blocks.STONE);

        addLeavesRecipe(PURIFICATION, "withered_oak_leaves_purification", MotherlodeEnderInvasionBlocks.WITHERED_OAK_LEAVES, Blocks.OAK_LEAVES);
        addLeavesRecipe(PURIFICATION, "withered_dark_oak_leaves_purification", MotherlodeEnderInvasionBlocks.WITHERED_DARK_OAK_LEAVES, Blocks.DARK_OAK_LEAVES);
        addLeavesRecipe(PURIFICATION, "withered_spruce_leaves_purification", MotherlodeEnderInvasionBlocks.WITHERED_SPRUCE_LEAVES, Blocks.SPRUCE_LEAVES);
        addLeavesRecipe(PURIFICATION, "withered_birch_leaves_purification", MotherlodeEnderInvasionBlocks.WITHERED_BIRCH_LEAVES, Blocks.BIRCH_LEAVES);
        addLeavesRecipe(PURIFICATION, "withered_jungle_leaves_purification", MotherlodeEnderInvasionBlocks.WITHERED_JUNGLE_LEAVES, Blocks.JUNGLE_LEAVES);
        addLeavesRecipe(PURIFICATION, "withered_acacia_leaves_purification", MotherlodeEnderInvasionBlocks.WITHERED_ACACIA_LEAVES, Blocks.ACACIA_LEAVES);
    }

    private static void addLeavesRecipe(BlockRecipeManager manager, String name, Block from, Block to) {
        addRecipe(manager, id(name), from::equals, state -> to.getDefaultState()
            .with(LeavesBlock.DISTANCE, state.get(LeavesBlock.DISTANCE))
            .with(LeavesBlock.PERSISTENT, state.get(LeavesBlock.PERSISTENT)));
    }
}
