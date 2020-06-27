package motherlode.core.registry;

import motherlode.core.Motherlode;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class MotherlodeItems {
    public static final Item ITEM_GROUP = register("item_group", new Item(new Item.Settings()));
    public static final Item COPPER_INGOT = register("copper_ingot", new Item(newSettings()));
    public static final Item SILVER_INGOT = register("silver_ingot", new Item(newSettings()));
    public static final Item CHARITE_CRYSTAL = register("charite_crystal", new Item(newSettings()));
    public static final Item ECHERITE_INGOT = register("echerite_ingot", new Item(newSettings()));
    public static final Item TITANIUM_INGOT = register("titanium_ingot", new Item(newSettings()));
    public static final Item ADAMANTITE_INGOT = register("adamantite_ingot", new Item(newSettings()));
    public static final Item AMETHYST = register("amethyst", new Item(newSettings()));
    public static final Item HOWLITE = register("howlite", new Item(newSettings()));
    public static final Item RUBY = register("ruby", new Item(newSettings()));
    public static final Item SAPPHIRE = register("sapphire", new Item(newSettings()));
    public static final Item TOPAZ = register("topaz", new Item(newSettings()));

    public static void init() {
        // CALLED TO MAINTAIN REGISTRY ORDER
    }

    static Item.Settings newSettings() {
        return new Item.Settings().group(Motherlode.MAIN_GROUP);
    }

    protected static <T extends Item> T register(String name, T item) {
        return Registry.register(Registry.ITEM, Motherlode.id(name), item);
    }
}