package motherlode.core.registry;

import motherlode.core.Motherlode;
import motherlode.core.MotherlodeMaterials;
import motherlode.core.assets.ItemModel;
import motherlode.core.item.DefaultGemItem;
import motherlode.core.item.DefaultMusicDiscItem;
import motherlode.core.item.MaterialToolsAndArmor;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.registry.Registry;

public class MotherlodeItems {

    private static final Item.Settings ITEM_GROUP = new Item.Settings().group(Motherlode.ITEMS);

    public static final Item COPPER_INGOT = register("copper_ingot", new Item(ITEM_GROUP));
    public static final Item COPPER_NUGGET = register("copper_nugget", new Item(ITEM_GROUP));
    public static final Item SILVER_INGOT = register("silver_ingot", new Item(ITEM_GROUP));
    public static final Item SILVER_NUGGET = register("silver_nugget", new Item(ITEM_GROUP));
    public static final Item CHARITE_CRYSTAL = register("charite_crystal", new Item(ITEM_GROUP));
    public static final Item CHARITE_POWDER = register("charite_powder", new Item(ITEM_GROUP));
    public static final Item ECHERITE_INGOT = register("echerite_ingot", new Item(ITEM_GROUP));
    public static final Item ECHERITE_NUGGET = register("echerite_nugget", new Item(ITEM_GROUP));
    public static final Item TITANIUM_INGOT = register("titanium_ingot", new Item(ITEM_GROUP));
    public static final Item TITANIUM_NUGGET = register("titanium_nugget", new Item(ITEM_GROUP));
    public static final Item ADAMANTITE_INGOT = register("adamantite_ingot", new Item(ITEM_GROUP));
    public static final Item ADAMANTITE_NUGGET = register("adamantite_nugget", new Item(ITEM_GROUP));
    public static final Item AMETHYST = register("amethyst", new DefaultGemItem(0xF989FF, ITEM_GROUP));
    public static final Item HOWLITE = register("howlite", new DefaultGemItem(0xFFFFFF, ITEM_GROUP));
    public static final Item RUBY = register("ruby", new DefaultGemItem(0xEA3E44, ITEM_GROUP));
    public static final Item SAPPHIRE = register("sapphire", new DefaultGemItem(0x34A6DA, ITEM_GROUP));
    public static final Item TOPAZ = register("topaz", new DefaultGemItem(0xFFC304, ITEM_GROUP));
    public static final Item ONYX = register("onyx", new DefaultGemItem(0x302A3B, ITEM_GROUP));

    public static final MaterialToolsAndArmor COPPER = new MaterialToolsAndArmor(MotherlodeMaterials.COPPER_TOOLS, MotherlodeMaterials.COPPER_ARMOR);
    public static final MaterialToolsAndArmor SILVER = new MaterialToolsAndArmor(MotherlodeMaterials.SILVER_TOOLS, MotherlodeMaterials.SILVER_ARMOR);
    public static final MaterialToolsAndArmor CHARITE = new MaterialToolsAndArmor(MotherlodeMaterials.CHARITE_TOOLS, MotherlodeMaterials.CHARITE_ARMOR);
    public static final MaterialToolsAndArmor ECHERITE = new MaterialToolsAndArmor(MotherlodeMaterials.ECHERITE_TOOLS, MotherlodeMaterials.ECHERITE_ARMOR);
    public static final MaterialToolsAndArmor TITANIUM = new MaterialToolsAndArmor(MotherlodeMaterials.TITANIUM_TOOLS, MotherlodeMaterials.TITANIUM_ARMOR);
    public static final MaterialToolsAndArmor ADAMANTITE = new MaterialToolsAndArmor(MotherlodeMaterials.ADAMANTITE_TOOLS, MotherlodeMaterials.ADAMANTITE_ARMOR);

    public static void init() {}

    public static <T extends Item> T register(String name, T item) {
        return register(name, ItemModel.DEFAULT, item);
    }

    public static <T extends Item> T register(String name, ItemModel model, T item) {
        if (Motherlode.isClient)
            MotherlodeAssets.itemModels.put(name, model);
        return Registry.register(Registry.ITEM, Motherlode.id(name), item);
    }
}