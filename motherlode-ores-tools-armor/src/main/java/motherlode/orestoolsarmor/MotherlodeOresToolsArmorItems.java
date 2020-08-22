package motherlode.orestoolsarmor;

import motherlode.base.CommonArtificeProcessors;
import motherlode.base.Motherlode;
import motherlode.base.api.ArtificeProcessor;
import motherlode.base.api.Registerable;
import motherlode.orestoolsarmor.item.MotherlodeMaterials;
import motherlode.orestoolsarmor.item.DefaultGemItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import java.util.HashMap;
import java.util.Map;

public class MotherlodeOresToolsArmorItems {
    
    public static final Map<Identifier, ArtificeProcessor> ARTIFICE_PROCESSORS = new HashMap<>();
    private static Item.Settings SETTINGS = new Item.Settings().group(ItemGroup.MATERIALS);

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
    public static final Item AMETHYST = register("amethyst", new DefaultGemItem(0xF989FF, SETTINGS));
    public static final Item HOWLITE = register("howlite", new DefaultGemItem(0xFFFFFF, SETTINGS));
    public static final Item RUBY = register("ruby", new DefaultGemItem(0xEA3E44, SETTINGS));
    public static final Item SAPPHIRE = register("sapphire", new DefaultGemItem(0x34A6DA, SETTINGS));
    public static final Item TOPAZ = register("topaz", new DefaultGemItem(0xFFC304, SETTINGS));
    public static final Item ONYX = register("onyx", new DefaultGemItem(0x302A3B, SETTINGS));

    public static final ToolArmorVariantType COPPER = register("copper", new ToolArmorVariantType(Motherlode.id(MotherlodeOresToolsArmorMod.MODID, "copper"), MotherlodeMaterials.COPPER_TOOLS, MotherlodeMaterials.COPPER_ARMOR));
    public static final ToolArmorVariantType SILVER = register("silver", new ToolArmorVariantType(Motherlode.id(MotherlodeOresToolsArmorMod.MODID, "silver"), MotherlodeMaterials.SILVER_TOOLS, MotherlodeMaterials.SILVER_ARMOR));
    public static final ToolArmorVariantType CHARITE = register("charite", new ToolArmorVariantType(Motherlode.id(MotherlodeOresToolsArmorMod.MODID, "charite"), MotherlodeMaterials.CHARITE_TOOLS, MotherlodeMaterials.CHARITE_ARMOR));
    public static final ToolArmorVariantType ECHERITE = register("echerite", new ToolArmorVariantType(Motherlode.id(MotherlodeOresToolsArmorMod.MODID, "echerite"), MotherlodeMaterials.ECHERITE_TOOLS, MotherlodeMaterials.ECHERITE_ARMOR));
    public static final ToolArmorVariantType TITANIUM = register("titanium", new ToolArmorVariantType(Motherlode.id(MotherlodeOresToolsArmorMod.MODID, "titanium"), MotherlodeMaterials.TITANIUM_TOOLS, MotherlodeMaterials.TITANIUM_ARMOR));
    public static final ToolArmorVariantType ADAMANTITE = register("adamantite", new ToolArmorVariantType(Motherlode.id(MotherlodeOresToolsArmorMod.MODID, "adamantite"), MotherlodeMaterials.ADAMANTITE_TOOLS, MotherlodeMaterials.ADAMANTITE_ARMOR));

    public static Item register(String name) {

        return register(name, new Item(SETTINGS));
    }
    public static<T extends Registerable<Item>> T register(String name, T t) {

        register(name,  t, t instanceof ArtificeProcessor? (ArtificeProcessor) t : CommonArtificeProcessors.DEFAULT_ITEM_MODEL);
        return t;
    }
    public static Item register(String name, Item item) {

        register(name, item instanceof Registerable? (Registerable<Item>) item : registerItem(item), item instanceof ArtificeProcessor? (ArtificeProcessor) item : CommonArtificeProcessors.DEFAULT_ITEM_MODEL);
        return item;
    }
    public static Item register(String name, Item item, ArtificeProcessor p) {
        
        register(name, registerItem(item), p);
        return item;
    }
    public static void register(String name, Registerable<Item> register, ArtificeProcessor p) {

        Identifier id = Motherlode.id(MotherlodeOresToolsArmorMod.MODID, name);
        ARTIFICE_PROCESSORS.put(id, p);
        register.register(id);
    }
    private static Registerable<Item> registerItem(Item item) {

        return id -> Registry.register(Registry.ITEM, id, item);
    }
    public static void init() {

        // Called to load the class
    }
}
