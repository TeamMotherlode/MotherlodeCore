package motherlode.orestoolsarmor;

import motherlode.base.Motherlode;
import motherlode.base.api.*;
import motherlode.orestoolsarmor.item.DefaultGemItem;
import motherlode.orestoolsarmor.item.MotherlodeMaterials;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;

public class MotherlodeOresToolsArmorItems {

    private static final Item.Settings SETTINGS = new Item.Settings().group(ItemGroup.MATERIALS);

    public static final Item COPPER_INGOT = register("copper_ingot");
    public static final Item COPPER_NUGGET = register("copper_nugget");
    public static final Item SILVER_INGOT = register("silver_ingot");
    public static final Item SILVER_NUGGET = register("silver_nugget");
    public static final Item CHARITE_CRYSTAL = register("charite_crystal");
    public static final Item CHARITE_POWDER = register("charite_powder");
    public static final Item ECHERITE_INGOT = register("echerite_ingot");
    public static final Item ECHERITE_NUGGET = register("echerite_nugget");
    public static final Item TITANIUM_INGOT = register("titanium_ingot");
    public static final Item TITANIUM_NUGGET = register("titanium_nugget");
    public static final Item ADAMANTITE_INGOT = register("adamantite_ingot");
    public static final Item ADAMANTITE_NUGGET = register("adamantite_nugget");
    public static final Item AMETHYST = register(new RegisterItem().name("amethyst").item(new DefaultGemItem(0xF989FF, SETTINGS)));
    public static final Item HOWLITE = register(new RegisterItem().name("howlite").item(new DefaultGemItem(0xFFFFFF, SETTINGS)));
    public static final Item RUBY = register(new RegisterItem().name("ruby").item(new DefaultGemItem(0xEA3E44, SETTINGS)));
    public static final Item SAPPHIRE = register(new RegisterItem().name("sapphire").item(new DefaultGemItem(0x34A6DA, SETTINGS)));
    public static final Item TOPAZ = register(new RegisterItem().name("topaz").item(new DefaultGemItem(0xFFC304, SETTINGS)));
    public static final Item ONYX = register(new RegisterItem().name("onyx").item(new DefaultGemItem(0x302A3B, SETTINGS)));

    public static final ToolArmorVariantType COPPER = registerVariantType("copper", new ToolArmorVariantType(Motherlode.id(MotherlodeOresToolsArmorMod.MODID, "copper"), MotherlodeMaterials.COPPER_TOOLS, MotherlodeMaterials.COPPER_ARMOR));
    public static final ToolArmorVariantType SILVER = registerVariantType("silver", new ToolArmorVariantType(Motherlode.id(MotherlodeOresToolsArmorMod.MODID, "silver"), MotherlodeMaterials.SILVER_TOOLS, MotherlodeMaterials.SILVER_ARMOR));
    public static final ToolArmorVariantType CHARITE = registerVariantType("charite", new ToolArmorVariantType(Motherlode.id(MotherlodeOresToolsArmorMod.MODID, "charite"), MotherlodeMaterials.CHARITE_TOOLS, MotherlodeMaterials.CHARITE_ARMOR));
    public static final ToolArmorVariantType ECHERITE = registerVariantType("echerite", new ToolArmorVariantType(Motherlode.id(MotherlodeOresToolsArmorMod.MODID, "echerite"), MotherlodeMaterials.ECHERITE_TOOLS, MotherlodeMaterials.ECHERITE_ARMOR));
    public static final ToolArmorVariantType TITANIUM = registerVariantType("titanium", new ToolArmorVariantType(Motherlode.id(MotherlodeOresToolsArmorMod.MODID, "titanium"), MotherlodeMaterials.TITANIUM_TOOLS, MotherlodeMaterials.TITANIUM_ARMOR));
    public static final ToolArmorVariantType ADAMANTITE = registerVariantType("adamantite", new ToolArmorVariantType(Motherlode.id(MotherlodeOresToolsArmorMod.MODID, "adamantite"), MotherlodeMaterials.ADAMANTITE_TOOLS, MotherlodeMaterials.ADAMANTITE_ARMOR));

    public static Item register(String name) {

        return register(new RegisterItem().name(name).item(new Item(SETTINGS)));
    }

    public static<T extends RegisterableVariantType<? extends Item>> T registerVariantType(String name, T type) {

        Identifier id = Motherlode.id(MotherlodeOresToolsArmorMod.MODID, name);
        type.register(id);

        if(type instanceof AssetProcessor) MotherlodeAssets.addAssets(id, (AssetProcessor) type);
        if(type instanceof DataProcessor) MotherlodeAssets.addData(id, (DataProcessor) type);

        return type;
    }

    public static Item register(RegisterItem item) {

        return item.register(MotherlodeOresToolsArmorMod.MODID);
    }

    public static void init() {

        // Called to load the class
    }
}
