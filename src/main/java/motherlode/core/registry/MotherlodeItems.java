package motherlode.core.registry;

import java.util.ArrayList;

import motherlode.core.Motherlode;
import motherlode.core.MotherlodeMaterials;
import motherlode.core.item.*;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class MotherlodeItems {

    public static final ArrayList<Item> defaultItemModelList = new ArrayList<>();

    public static final Item COPPER_INGOT = register("copper_ingot", new DefaultItem(newSettings()));
    public static final Item SILVER_INGOT = register("silver_ingot", new DefaultItem(newSettings()));
    public static final Item CHARITE_CRYSTAL = register("charite_crystal", new DefaultItem(newSettings()));
    public static final Item ECHERITE_INGOT = register("echerite_ingot", new DefaultItem(newSettings()));
    public static final Item TITANIUM_INGOT = register("titanium_ingot", new DefaultItem(newSettings()));
    public static final Item ADAMANTITE_INGOT = register("adamantite_ingot", new DefaultItem(newSettings()));
    public static final Item AMETHYST = register("amethyst", new DefaultGemItem(0xF989FF, newSettings()));
    public static final Item HOWLITE = register("howlite", new DefaultGemItem(0xFFFFFF, newSettings()));
    public static final Item RUBY = register("ruby", new DefaultGemItem(0xEA3E44, newSettings()));
    public static final Item SAPPHIRE = register("sapphire", new DefaultGemItem(0x34A6DA, newSettings()));
    public static final Item TOPAZ = register("topaz", new DefaultGemItem(0xFFC304, newSettings()));
    public static final Item ONYX = register("onyx", new DefaultGemItem(0x675885, newSettings()));

    public static final MaterialToolsAndArmor COPPER = new MaterialToolsAndArmor(MotherlodeMaterials.COPPER_TOOLS, MotherlodeMaterials.COPPER_ARMOR);
    public static final MaterialToolsAndArmor SILVER = new MaterialToolsAndArmor(MotherlodeMaterials.SILVER_TOOLS, MotherlodeMaterials.SILVER_ARMOR);
    public static final MaterialToolsAndArmor CHARITE = new MaterialToolsAndArmor(MotherlodeMaterials.CHARITE_TOOLS, MotherlodeMaterials.CHARITE_ARMOR);
    public static final MaterialToolsAndArmor ECHERITE = new MaterialToolsAndArmor(MotherlodeMaterials.ECHERITE_TOOLS, MotherlodeMaterials.ECHERITE_ARMOR);
    public static final MaterialToolsAndArmor TITANIUM = new MaterialToolsAndArmor(MotherlodeMaterials.TITANIUM_TOOLS, MotherlodeMaterials.TITANIUM_ARMOR);
    public static final MaterialToolsAndArmor ADAMANTITE = new MaterialToolsAndArmor(MotherlodeMaterials.ADAMANTITE_TOOLS, MotherlodeMaterials.ADAMANTITE_ARMOR);

    public static void init() {
        // CALLED TO MAINTAIN REGISTRY ORDER
    }

    static Item.Settings newSettings() {
        return new Item.Settings().group(Motherlode.ITEMS);
    }

    public static <T extends Item> T register(String name, T item) {
        if (item instanceof DefaultItem){
            if (((DefaultItem) item).hasDefaultItemModel()){
                defaultItemModelList.add(item);
            }
        }
        return Registry.register(Registry.ITEM, Motherlode.id(name), item);
    }
}