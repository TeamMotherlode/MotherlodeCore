package motherlode.orestoolsarmor;

import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import motherlode.base.CommonAssets;
import motherlode.base.CommonData;
import motherlode.base.Motherlode;
import motherlode.base.api.DataProcessor;
import motherlode.base.api.Registerable;
import motherlode.orestoolsarmor.MotherlodeOreBlock.Dimension;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;

@SuppressWarnings("unused")
public class MotherlodeOresToolsArmorBlocks {
    private static final Item.Settings BLOCK_ITEM_SETTINGS = new Item.Settings().group(ItemGroup.MATERIALS);

    public static final MotherlodeOreBlock COPPER_ORE = register("copper_ore", new MotherlodeOreBlock(3, 7, 12, 3, 11, 64, Dimension.OVERWORLD, 1, "copper_ingot"));
    public static final MotherlodeOreBlock SILVER_ORE = register("silver_ore", new MotherlodeOreBlock(2, "silver_ingot"));
    public static final MotherlodeOreBlock CHARITE_ORE = register("charite_ore", new MotherlodeOreBlock(3, Dimension.NETHER, "charite_crystal"));
    public static final MotherlodeOreBlock ECHERITE_ORE = register("echerite_ore", new MotherlodeOreBlock(4, Dimension.NETHER, "echerite_ingot"));
    public static final MotherlodeOreBlock TITANIUM_ORE = register("titanium_ore", new MotherlodeOreBlock(5, "titanium_ingot"));
    public static final MotherlodeOreBlock ADAMANTITE_ORE = register("adamantite_ore", new MotherlodeOreBlock(6, "adamantite_ingot"));
    public static final MotherlodeOreBlock AMETHYST_ORE = register("amethyst_ore", new MotherlodeOreBlock(2, "amethyst"));
    public static final MotherlodeOreBlock HOWLITE_ORE = register("howlite_ore", new MotherlodeOreBlock(2, "howlite"));
    public static final MotherlodeOreBlock RUBY_ORE = register("ruby_ore", new MotherlodeOreBlock(2, "ruby"));
    public static final MotherlodeOreBlock SAPPHIRE_ORE = register("sapphire_ore", new MotherlodeOreBlock(2, "sapphire"));
    public static final MotherlodeOreBlock TOPAZ_ORE = register("topaz_ore", new MotherlodeOreBlock(2, "topaz"));
    public static final MotherlodeOreBlock ONYX_ORE = register("onyx_ore", new MotherlodeOreBlock(2, "onyx"));

    public static final Block COPPER_BLOCK = register("copper_block", mineralBlock(1, "copper_ingot"));
    public static final Block SILVER_BLOCK = register("silver_block", mineralBlock(2, "silver_ingot"));
    public static final Block CHARITE_BLOCK = register("charite_block", mineralBlock(3, "charite_ingot"));
    public static final Block ECHERITE_BLOCK = register("echerite_block", mineralBlock(4, "echerite_ingot"));
    public static final Block TITANIUM_BLOCK = register("titanium_block", mineralBlock(5, "titanium_ingot"));
    public static final Block ADAMANTITE_BLOCK = register("adamantite_block", mineralBlock(6, "adamantite_ingot"));
    public static final Block AMETHYST_BLOCK = register("amethyst_block", mineralBlock(2, "amethyst"));
    public static final Block HOWLITE_BLOCK = register("howlite_block", mineralBlock(2, "howlite"));
    public static final Block RUBY_BLOCK = register("ruby_block", mineralBlock(2, "ruby"));
    public static final Block SAPPHIRE_BLOCK = register("sapphire_block", mineralBlock(2, "sapphire"));
    public static final Block TOPAZ_BLOCK = register("topaz_block", mineralBlock(2, "topaz"));
    public static final Block ONYX_BLOCK = register("onyx_block", mineralBlock(2, "onyx"));

    private static Block mineralBlock(int miningLevel, String mineral) {
        return new MineralBlock(FabricBlockSettings.of(Material.STONE, MaterialColor.IRON).requiresTool().strength(5.0F, 6.0F).breakByTool(FabricToolTags.PICKAXES, miningLevel), mineral);
    }

    private static <T extends Block> T register(String name, T block) {

        return Motherlode.register(
            Registerable.block(block, BLOCK_ITEM_SETTINGS),
            Motherlode.id(MotherlodeModule.MODID, name),
            block,
            null,
            CommonAssets.DEFAULT_BLOCK,
            block instanceof DataProcessor ? ((DataProcessor) block).andThen(CommonData.DEFAULT_BLOCK_LOOT_TABLE)
                : CommonData.DEFAULT_BLOCK_LOOT_TABLE
        );
    }

    public static void init() {

        // Called to load the class
    }
}
