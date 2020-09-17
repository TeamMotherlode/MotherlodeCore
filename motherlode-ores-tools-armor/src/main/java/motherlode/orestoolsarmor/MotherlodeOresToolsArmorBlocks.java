package motherlode.orestoolsarmor;

import motherlode.base.CommonAssets;
import motherlode.base.CommonData;
import motherlode.base.Motherlode;
import motherlode.base.api.MotherlodeAssets;
import motherlode.base.api.MotherlodeData;
import motherlode.orestoolsarmor.MotherlodeOreBlock.Dimension;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MotherlodeOresToolsArmorBlocks {

    private static final Item.Settings BLOCK_ITEM_SETTINGS = new Item.Settings().group(ItemGroup.MATERIALS);

    public static final MotherlodeOreBlock COPPER_ORE = register("copper_ore", new MotherlodeOreBlock(3, 7, 12, 3, 11, 64, Dimension.OVERWORLD, 1));
    public static final MotherlodeOreBlock SILVER_ORE = register("silver_ore", new MotherlodeOreBlock(2));
    public static final MotherlodeOreBlock CHARITE_ORE = register("charite_ore", new MotherlodeOreBlock(3, Dimension.NETHER));
    public static final MotherlodeOreBlock ECHERITE_ORE = register("echerite_ore", new MotherlodeOreBlock( 4, Dimension.NETHER));
    public static final MotherlodeOreBlock TITANIUM_ORE = register("titanium_ore", new MotherlodeOreBlock(5));
    public static final MotherlodeOreBlock ADAMANTITE_ORE = register("adamantite_ore", new MotherlodeOreBlock(6));
    public static final MotherlodeOreBlock AMETHYST_ORE = register("amethyst_ore", new MotherlodeOreBlock(2));
    public static final MotherlodeOreBlock HOWLITE_ORE = register("howlite_ore", new MotherlodeOreBlock(2));
    public static final MotherlodeOreBlock RUBY_ORE = register("ruby_ore", new MotherlodeOreBlock(2));
    public static final MotherlodeOreBlock SAPPHIRE_ORE = register("sapphire_ore", new MotherlodeOreBlock(2));
    public static final MotherlodeOreBlock TOPAZ_ORE = register("topaz_ore", new MotherlodeOreBlock(2));
    public static final MotherlodeOreBlock ONYX_ORE = register("onyx_ore", new MotherlodeOreBlock(2));

    public static final Block COPPER_BLOCK = register("copper_block", mineralBlock(1));
    public static final Block SILVER_BLOCK = register("silver_block", mineralBlock(2));
    public static final Block CHARITE_BLOCK = register("charite_block", mineralBlock(3));
    public static final Block ECHERITE_BLOCK = register("echerite_block", mineralBlock(4));
    public static final Block TITANIUM_BLOCK = register("titanium_block", mineralBlock(5));
    public static final Block ADAMANTITE_BLOCK = register("adamantite_block", mineralBlock(6));
    public static final Block AMETHYST_BLOCK = register("amethyst_block", mineralBlock(2));
    public static final Block HOWLITE_BLOCK = register("howlite_block", mineralBlock(2));
    public static final Block RUBY_BLOCK = register("ruby_block", mineralBlock(2));
    public static final Block SAPPHIRE_BLOCK = register("sapphire_block", mineralBlock(2));
    public static final Block TOPAZ_BLOCK = register("topaz_block", mineralBlock(2));
    public static final Block ONYX_BLOCK = register("onyx_block", mineralBlock(2));

    private static Block mineralBlock(int miningLevel) {
        return new Block(FabricBlockSettings.of(Material.STONE, MaterialColor.IRON).requiresTool().strength(5.0F, 6.0F).breakByTool(FabricToolTags.PICKAXES, miningLevel));
    }

    private static<T extends Block> T register(String name, T block) {

        Identifier id = Motherlode.id(MotherlodeOresToolsArmorMod.MODID, name);
        Registry.register(Registry.BLOCK, id, block);
        Registry.register(Registry.ITEM, id, new BlockItem(block, BLOCK_ITEM_SETTINGS));
        MotherlodeAssets.addGenerator(id, CommonAssets.DEFAULT_BLOCK);
        MotherlodeData.addGenerator(id, CommonData.DEFAULT_BLOCK_LOOT_TABLE);
        return block;
    }

    public static void init() {

     // Called to load the class
    }
}
