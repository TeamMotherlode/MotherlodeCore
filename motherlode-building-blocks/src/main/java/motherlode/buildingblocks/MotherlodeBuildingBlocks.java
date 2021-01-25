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
import motherlode.buildingblocks.block.MotherlodeStoneExtension;
import motherlode.buildingblocks.block.PaintableWallBlock;
import motherlode.buildingblocks.block.StoneVariantType;

@SuppressWarnings("unused")
public class MotherlodeBuildingBlocks {
    private static final Item.Settings BLOCK_ITEM_SETTINGS = new Item.Settings().group(ItemGroup.BUILDING_BLOCKS);

    public static final StoneVariantType LIMESTONE = new StoneVariantType(MotherlodeModule.id("limestone")).with(new MotherlodeStoneExtension()).register();
    public static final StoneVariantType GRAVESTONE = new StoneVariantType(MotherlodeModule.id("gravestone")).with(new MotherlodeStoneExtension()).register();
    public static final StoneVariantType JASPER = new StoneVariantType(MotherlodeModule.id("jasper")).with(new MotherlodeStoneExtension()).register();
    public static final StoneVariantType MARBLE = new StoneVariantType(MotherlodeModule.id("marble")).with(new MotherlodeStoneExtension()).register();
    public static final StoneVariantType SLATE = new StoneVariantType(MotherlodeModule.id("slate")).with(new MotherlodeStoneExtension()).register();

    public static final StoneVariantType BRICK = new StoneVariantType(MotherlodeModule.id("brick")).with(new MotherlodeStoneExtension()).register();
    public static final StoneVariantType MAGMA = new StoneVariantType(MotherlodeModule.id("magma"), false).with(new MotherlodeStoneExtension()).register();
    public static final StoneVariantType OBSIDIAN = new StoneVariantType(MotherlodeModule.id("obsidian"), false).with(new MotherlodeStoneExtension()).register();
    public static final StoneVariantType CRYING_OBSIDIAN = new StoneVariantType(MotherlodeModule.id("crying_obsidian"), false).with(new MotherlodeStoneExtension()).register();
    public static final StoneVariantType GOLD = new StoneVariantType(MotherlodeModule.id("gold"), false).with(new MotherlodeStoneExtension()).register();
    public static final StoneVariantType ICE = new StoneVariantType(MotherlodeModule.id("ice"), false).with(new MotherlodeStoneExtension()).register();

    public static final StoneVariantType STONE = new StoneVariantType(MotherlodeModule.id("stone")).withoutBase().with(new MotherlodeStoneExtension()).register();
    public static final StoneVariantType GRANITE = new StoneVariantType(MotherlodeModule.id("granite")).withoutBase().with(new MotherlodeStoneExtension()).register();
    public static final StoneVariantType DIORITE = new StoneVariantType(MotherlodeModule.id("diorite")).withoutBase().with(new MotherlodeStoneExtension()).register();
    public static final StoneVariantType ANDESITE = new StoneVariantType(MotherlodeModule.id("andesite")).withoutBase().with(new MotherlodeStoneExtension()).register();
    public static final StoneVariantType BLACKSTONE = new StoneVariantType(MotherlodeModule.id("blackstone")).withoutBase().with(new MotherlodeStoneExtension()).register();
    public static final StoneVariantType BASALT = new StoneVariantType(MotherlodeModule.id("basalt")).withoutBase().with(new MotherlodeStoneExtension()).register();
    public static final StoneVariantType SANDSTONE = new StoneVariantType(MotherlodeModule.id("sandstone")).withoutBase().with(new MotherlodeStoneExtension()).register();

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
