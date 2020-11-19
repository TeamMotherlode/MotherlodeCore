package motherlode.orestoolsarmor;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import motherlode.base.api.CommonAssets;
import motherlode.base.Motherlode;
import motherlode.base.api.AssetProcessor;
import motherlode.base.api.DataProcessor;
import motherlode.base.api.Registerable;
import motherlode.orestoolsarmor.item.DefaultGemItem;
import motherlode.orestoolsarmor.item.IngotItem;
import motherlode.orestoolsarmor.item.MotherlodeMaterials;
import motherlode.orestoolsarmor.item.ToolArmorVariantType;

@SuppressWarnings("unused")
public class MotherlodeOresToolsArmorItems {
    private static final Item.Settings SETTINGS = new Item.Settings().group(ItemGroup.MATERIALS);

    // public static final Item COPPER_INGOT = register("copper_ingot", new IngotItem(SETTINGS));
    // public static final Item COPPER_NUGGET = register("copper_nugget");
    public static final Item SILVER_INGOT = register("silver_ingot", new IngotItem(SETTINGS));
    public static final Item SILVER_NUGGET = register("silver_nugget");
    public static final Item CHARITE_INGOT = register("charite_ingot", new IngotItem(SETTINGS, "crystal", false));
    public static final Item CHARITE_CRYSTAL = register("charite_crystal");
    public static final Item CHARITE_POWDER = register("charite_powder");
    public static final Item ECHERITE_INGOT = register("echerite_ingot", new IngotItem(SETTINGS));
    public static final Item ECHERITE_NUGGET = register("echerite_nugget");
    public static final Item TITANIUM_INGOT = register("titanium_ingot", new IngotItem(SETTINGS));
    public static final Item TITANIUM_NUGGET = register("titanium_nugget");
    public static final Item ADAMANTITE_INGOT = register("adamantite_ingot", new IngotItem(SETTINGS));
    public static final Item ADAMANTITE_NUGGET = register("adamantite_nugget");
    public static final Item AMETHYST = register("amethyst", new DefaultGemItem(0xF989FF, SETTINGS));
    public static final Item HOWLITE = register("howlite", new DefaultGemItem(0xFFFFFF, SETTINGS));
    public static final Item RUBY = register("ruby", new DefaultGemItem(0xEA3E44, SETTINGS));
    public static final Item SAPPHIRE = register("sapphire", new DefaultGemItem(0x34A6DA, SETTINGS));
    public static final Item TOPAZ = register("topaz", new DefaultGemItem(0xFFC304, SETTINGS));
    public static final Item ONYX = register("onyx", new DefaultGemItem(0x302A3B, SETTINGS));

    public static final ToolArmorVariantType COPPER = register("copper", new ToolArmorVariantType(MotherlodeModule.id("copper"), "copper_ingot", MotherlodeMaterials.COPPER_TOOLS, MotherlodeMaterials.COPPER_ARMOR));
    public static final ToolArmorVariantType SILVER = register("silver", new ToolArmorVariantType(MotherlodeModule.id("silver"), "silver_ingot", MotherlodeMaterials.SILVER_TOOLS, MotherlodeMaterials.SILVER_ARMOR));
    public static final ToolArmorVariantType CHARITE = register("charite", new ToolArmorVariantType(MotherlodeModule.id("charite"), "charite_ingot", MotherlodeMaterials.CHARITE_TOOLS, MotherlodeMaterials.CHARITE_ARMOR));
    public static final ToolArmorVariantType ECHERITE = register("echerite", new ToolArmorVariantType(MotherlodeModule.id("echerite"), "echerite_ingot", MotherlodeMaterials.ECHERITE_TOOLS, MotherlodeMaterials.ECHERITE_ARMOR));
    public static final ToolArmorVariantType TITANIUM = register("titanium", new ToolArmorVariantType(MotherlodeModule.id("titanium"), "titanium_ingot", MotherlodeMaterials.TITANIUM_TOOLS, MotherlodeMaterials.TITANIUM_ARMOR));
    public static final ToolArmorVariantType ADAMANTITE = register("adamantite", new ToolArmorVariantType(MotherlodeModule.id("adamantite"), "adamantite_ingot", MotherlodeMaterials.ADAMANTITE_TOOLS, MotherlodeMaterials.ADAMANTITE_ARMOR));

    public static Item register(String name) {
        Item item = new Item(SETTINGS);
        return Motherlode.register(
            Registerable.item(item),
            MotherlodeModule.id(name),
            item,
            null,
            CommonAssets.DEFAULT_ITEM_MODEL,
            null
        );
    }

    public static Item register(String name, Item item) {
        return Motherlode.register(
            Registerable.item(item),
            MotherlodeModule.id(name),
            item,
            null,
            CommonAssets.DEFAULT_ITEM_MODEL,
            item instanceof DataProcessor ? (DataProcessor) item : null
        );
    }

    public static <T extends Registerable<? super Item> & AssetProcessor & DataProcessor> T register(String name, T item) {
        return Motherlode.register(
            item,
            MotherlodeModule.id(name),
            item,
            null,
            item,
            item
        );
    }

    public static void init() {
        // Called to load the class
    }
}
