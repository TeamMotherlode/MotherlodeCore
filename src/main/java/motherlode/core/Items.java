package motherlode.core;

import net.minecraft.item.Item;

public class Items {
    public static final Item ITEM_GROUP;
    public static final Item COPPER_INGOT;
    
    static{
        ITEM_GROUP = new Item(new Item.Settings().group(Motherlode.MAIN_GROUP));
        COPPER_INGOT = new Item(new Item.Settings().group(Motherlode.MAIN_GROUP));
    }
}