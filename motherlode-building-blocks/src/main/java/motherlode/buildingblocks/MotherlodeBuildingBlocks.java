package motherlode.buildingblocks;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import motherlode.base.Motherlode;
import motherlode.base.api.Processor;
import motherlode.base.api.Registerable;
import motherlode.base.api.assets.AssetProcessor;
import motherlode.base.api.assets.CommonData;
import motherlode.buildingblocks.block.PaintableWallBlock;
import motherlode.buildingblocks.block.StoneVariantType;

@SuppressWarnings("unused")
public class MotherlodeBuildingBlocks {
    private static final Item.Settings BLOCK_ITEM_SETTINGS = new Item.Settings().group(ItemGroup.BUILDING_BLOCKS);

    public static final StoneVariantType LIMESTONE = register(StoneVariantType.newStone("limestone", false));
    public static final StoneVariantType GRAVESTONE = register(StoneVariantType.newStone("gravestone", true));
    public static final StoneVariantType JASPER = register(StoneVariantType.newStone("jasper", false));
    public static final StoneVariantType MARBLE = register(StoneVariantType.newStone("marble", false));
    public static final StoneVariantType SLATE = register(StoneVariantType.newStone("slate", false));

    public static final StoneVariantType BRICK = register(StoneVariantType.fromBlock("brick", Blocks.BRICKS, false, true));
    public static final StoneVariantType MAGMA = register(StoneVariantType.fromBlock("magma", Blocks.MAGMA_BLOCK, false,  true));
    public static final StoneVariantType OBSIDIAN = register(StoneVariantType.fromBlock("obsidian", null, false, true));
    public static final StoneVariantType CRYING_OBSIDIAN = register(StoneVariantType.fromBlock("crying_obsidian", null, false, true));
    public static final StoneVariantType GOLD = register(StoneVariantType.fromBlock("gold", Blocks.GOLD_BLOCK, false, true));
    public static final StoneVariantType ICE = register(StoneVariantType.fromBlock("ice", null, false, true));
    public static final StoneVariantType TUFF = register(StoneVariantType.fromBlock("tuff", null, true, true));
    public static final StoneVariantType LAPIS_LAZULI = register(StoneVariantType.fromBlock("lapis_lazuli", null, false, false));
    public static final StoneVariantType DRIPSTONE = register(StoneVariantType.fromBlock("dripstone", null, false, true));
    public static final StoneVariantType GLOWSTONE = register(StoneVariantType.fromBlock("glowstone", null, false, false));
    public static final StoneVariantType SNOW = register(StoneVariantType.fromBlock("snow", null, false, true));
    public static final StoneVariantType END_STONE = register(StoneVariantType.fromBlock("end_stone", null, false, true));
    public static final StoneVariantType REDSTONE = register(StoneVariantType.fromBlock("redstone", null, false, false));
    public static final StoneVariantType DIRT = register(StoneVariantType.fromBlock("dirt", null, false, true));
    public static final StoneVariantType SOUL_SAND = register(StoneVariantType.fromBlock("soul_sand", null, false, true));
    public static final StoneVariantType NETHER_BRICKS = register(StoneVariantType.fromBlock("nether_bricks", null, false, true));
    public static final StoneVariantType WARPED = register(StoneVariantType.fromBlock("warped", null, false, true));
    public static final StoneVariantType CRIMSON = register(StoneVariantType.fromBlock("crimson", null, false, true));

    public static final StoneVariantType STONE = register(StoneVariantType.fromStone("stone", Blocks.STONE_BRICKS, Blocks.SMOOTH_STONE, true));
    public static final StoneVariantType GRANITE = register(StoneVariantType.fromStone("granite", null, Blocks.POLISHED_GRANITE, true));
    public static final StoneVariantType DIORITE = register(StoneVariantType.fromStone("diorite", null, Blocks.POLISHED_DIORITE, true));
    public static final StoneVariantType ANDESITE = register(StoneVariantType.fromStone("andesite", null, Blocks.POLISHED_ANDESITE, true));
    public static final StoneVariantType BLACKSTONE = register(StoneVariantType.fromStone("blackstone", null, Blocks.POLISHED_BLACKSTONE, true));
    public static final StoneVariantType BASALT = register(StoneVariantType.fromStone("basalt", null, Blocks.POLISHED_BASALT, true));
    public static final StoneVariantType SANDSTONE = register(StoneVariantType.fromStone("sandstone", null, Blocks.SMOOTH_SANDSTONE, true));
    public static final StoneVariantType RED_SANDSTONE = register(StoneVariantType.fromStone("red_sandstone", null, Blocks.SMOOTH_RED_SANDSTONE, true));



    public static final Block MORTAR_BRICKS = register("mortar_bricks", new PaintableWallBlock(FabricBlockSettings.copy(Blocks.TERRACOTTA)));

    /* public static final Block DIRT_PATH = register("dirt_path", new DefaultPathBlock(FabricBlockSettings.copy(Blocks.DIRT_PATH)),
        CommonAssets.DEFAULT_BLOCK_STATE.andThen(CommonAssets.BLOCK_ITEM), block ->
            UseBlockCallback.EVENT.register((player, world, hand, hit) -> {
                BlockPos pos = hit.getBlockPos();
                if (player.getStackInHand(hand).getItem().isIn(FabricToolTags.SHOVELS) && world.getBlockState(pos).getBlock() == Blocks.DIRT && world.getBlockState(pos.up()).isAir() && hit.getSide() != Direction.DOWN) {
                    world.setBlockState(pos, block.getDefaultState());
                    world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0f, 1.0f, true);
                    return ActionResult.SUCCESS;
                }
                return ActionResult.PASS;
            })); */

    private static StoneVariantType register(StoneVariantType stone) {

        return Motherlode.register(
            stone,
            Motherlode.id(MotherlodeModule.MODID, stone.getId()),
            stone,
            stone,
            stone
        );
    }

    private static <T extends Block> T register(String name, T block, AssetProcessor assets) {

        return register(name, block, assets, null);
    }

    private static <T extends Block & AssetProcessor> T register(String name, T block) {

        return register(name, block, block);
    }

    private static <T extends Block> T register(String name, T block, AssetProcessor assets, Processor<Block> p) {

        return Motherlode.register(
            Registerable.block(block, BLOCK_ITEM_SETTINGS),
            Motherlode.id(MotherlodeModule.MODID, name),
            block,
            p,
            assets,
            CommonData.DEFAULT_BLOCK_LOOT_TABLE
        );
    }

    public static void init() {

        // Called to load the class
    }
}
