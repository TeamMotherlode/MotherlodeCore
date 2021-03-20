package motherlode.buildingblocks;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import motherlode.base.Motherlode;
import motherlode.base.api.Processor;
import motherlode.base.api.Registerable;
import motherlode.base.api.assets.AssetProcessor;
import motherlode.base.api.assets.CommonAssets;
import motherlode.base.api.assets.CommonData;
import motherlode.buildingblocks.block.MotherlodeStoneBlocks;
import motherlode.buildingblocks.block.PaintableWallBlock;
import motherlode.buildingblocks.block.SawmillBlock;
import motherlode.buildingblocks.block.StonePillarBlocks;
import motherlode.buildingblocks.block.StoneType;

@SuppressWarnings("unused")
public class MotherlodeBuildingBlocks {
    private static final Item.Settings BLOCK_ITEM_SETTINGS = new Item.Settings().group(ItemGroup.BUILDING_BLOCKS);

    public static final StoneType LIMESTONE = new StoneType(MotherlodeModule.id("limestone")).with(new MotherlodeStoneBlocks()).with(new StonePillarBlocks()).register();
    public static final StoneType GRAVESTONE = new StoneType(MotherlodeModule.id("gravestone")).with(new MotherlodeStoneBlocks()).with(new StonePillarBlocks()).register();
    public static final StoneType JASPER = new StoneType(MotherlodeModule.id("jasper")).with(new MotherlodeStoneBlocks()).with(new StonePillarBlocks()).register();
    public static final StoneType MARBLE = new StoneType(MotherlodeModule.id("marble")).with(new MotherlodeStoneBlocks()).with(new StonePillarBlocks()).register();
    public static final StoneType SLATE = new StoneType(MotherlodeModule.id("slate")).with(new MotherlodeStoneBlocks()).with(new StonePillarBlocks()).register();
    public static final StoneType TUFF = new StoneType(MotherlodeModule.id("tuff")).with(new MotherlodeStoneBlocks()).with(new StonePillarBlocks()).register();

    public static final StoneType BRICK = new StoneType(MotherlodeModule.id("brick")).with(new MotherlodeStoneBlocks()).register();
    public static final StoneType MAGMA = new StoneType(MotherlodeModule.id("magma"), false).with(new MotherlodeStoneBlocks()).register();
    public static final StoneType OBSIDIAN = new StoneType(MotherlodeModule.id("obsidian"), false).with(new MotherlodeStoneBlocks()).register();
    public static final StoneType CRYING_OBSIDIAN = new StoneType(MotherlodeModule.id("crying_obsidian"), false).with(new MotherlodeStoneBlocks()).register();
    public static final StoneType GOLD = new StoneType(MotherlodeModule.id("gold"), false).with(new MotherlodeStoneBlocks()).register();
    public static final StoneType ICE = new StoneType(MotherlodeModule.id("ice"), false).with(new MotherlodeStoneBlocks()).register();

    public static final StoneType LAPIS_LAZULI = new StoneType(MotherlodeModule.id("lapis_lazuli"), false).with(new MotherlodeStoneBlocks()).register();
    public static final StoneType DRIPSTONE = new StoneType(MotherlodeModule.id("dripstone"), false).with(new MotherlodeStoneBlocks()).register();
    public static final StoneType GLOWSTONE = new StoneType(MotherlodeModule.id("glowstone"), false).with(new MotherlodeStoneBlocks()).register();
    public static final StoneType SNOW = new StoneType(MotherlodeModule.id("snow"), false).with(new MotherlodeStoneBlocks()).register();
    public static final StoneType END_STONE = new StoneType(MotherlodeModule.id("end_stone"), false).with(new MotherlodeStoneBlocks()).register();
    public static final StoneType REDSTONE = new StoneType(MotherlodeModule.id("redstone"), false).with(new MotherlodeStoneBlocks()).register();
    public static final StoneType DIRT = new StoneType(MotherlodeModule.id("dirt"), false).with(new MotherlodeStoneBlocks()).register();
    public static final StoneType SOUL_SAND = new StoneType(MotherlodeModule.id("soul_sand"), false).with(new MotherlodeStoneBlocks()).register();
    public static final StoneType NETHER_BRICKS = new StoneType(MotherlodeModule.id("nether_bricks"), false).with(new MotherlodeStoneBlocks()).register();
    public static final StoneType WARPED = new StoneType(MotherlodeModule.id("warped"), false).with(new MotherlodeStoneBlocks()).register();
    public static final StoneType CRIMSON = new StoneType(MotherlodeModule.id("crimson"), false).with(new MotherlodeStoneBlocks()).register();

    public static final StoneType STONE = new StoneType(MotherlodeModule.id("stone")).withoutBase().with(new MotherlodeStoneBlocks()).register();
    public static final StoneType GRANITE = new StoneType(MotherlodeModule.id("granite")).withoutBase().with(new MotherlodeStoneBlocks()).register();
    public static final StoneType DIORITE = new StoneType(MotherlodeModule.id("diorite")).withoutBase().with(new MotherlodeStoneBlocks()).register();
    public static final StoneType ANDESITE = new StoneType(MotherlodeModule.id("andesite")).withoutBase().with(new MotherlodeStoneBlocks()).register();
    public static final StoneType BLACKSTONE = new StoneType(MotherlodeModule.id("blackstone")).withoutBase().with(new MotherlodeStoneBlocks()).register();
    public static final StoneType BASALT = new StoneType(MotherlodeModule.id("basalt")).withoutBase().with(new MotherlodeStoneBlocks()).register();
    public static final StoneType SANDSTONE = new StoneType(MotherlodeModule.id("sandstone")).withoutBase().with(new MotherlodeStoneBlocks()).register();
    public static final StoneType RED_SANDSTONE = new StoneType(MotherlodeModule.id("red_sandstone")).withoutBase().with(new MotherlodeStoneBlocks()).register();

    public static final Block MORTAR_BRICKS = register("mortar_bricks", new PaintableWallBlock(FabricBlockSettings.copy(Blocks.TERRACOTTA)));

    public static final Block SAWMILL = register("sawmill", new SawmillBlock(FabricBlockSettings.of(Material.WOOD).strength(3f).sounds(BlockSoundGroup.WOOD).nonOpaque()), CommonAssets.DEFAULT_DIRECTIONAL_BLOCK_STATE.andThen(CommonAssets.BLOCK_ITEM));
    public static final Block MULCH = register("mulch", new Block(FabricBlockSettings.of(Material.SOIL).breakByHand(true).strength(1.2f)), CommonAssets.DEFAULT_BLOCK);

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

    private static <T extends Block> T register(String name, T block, AssetProcessor assets) {

        return register(name, block, assets, null);
    }

    private static <T extends Block & AssetProcessor> T register(String name, T block) {

        return register(name, block, block);
    }

    private static <T extends Block> T register(String name, T block, AssetProcessor assets, Processor<Block> p) {

        return Motherlode.register(
            Registerable.block(block, BLOCK_ITEM_SETTINGS),
            MotherlodeModule.id(name),
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
