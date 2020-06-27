package motherlode.core.registry;

import java.util.ArrayList;

import motherlode.core.Motherlode;
import motherlode.core.item.DefaultItem;
import motherlode.core.item.DefaultGemItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class MotherlodeItems {
    public static final ArrayList<DefaultItem> defaultItemModelList = new ArrayList<DefaultItem>();;
  
    public static final Item ITEM_GROUP = register("item_group", new DefaultItem(new Item.Settings()));
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

    public static void init() {
        // CALLED TO MAINTAIN REGISTRY ORDER
    }

    static Item.Settings newSettings() {
        return new Item.Settings().group(Motherlode.MAIN_GROUP);
    }

    protected static <T extends Item> T register(String name, T item) {
        if (item instanceof DefaultItem){
            if (((DefaultItem) item).hasDefaultItemModel()){
                defaultItemModelList.add((DefaultItem) item);
            }
        }
        return Registry.register(Registry.ITEM, Motherlode.id(name), item);
    }
}