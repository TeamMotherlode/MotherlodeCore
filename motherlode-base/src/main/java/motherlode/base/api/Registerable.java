package motherlode.base.api;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@FunctionalInterface
public interface Registerable<T> {
    void register(Identifier identifier);

    static <T extends Block> Registerable<T> block(Block block, Item.Settings itemSettings) {
        return id -> {
            Registry.register(Registry.BLOCK, id, block);
            if (itemSettings != null) Registry.register(Registry.ITEM, id, new BlockItem(block, itemSettings));
        };
    }

    static <T extends Block> Registerable<T> block(Block block, ItemGroup group) {
        return id -> {
            Registry.register(Registry.BLOCK, id, block);
            if (group != null)
                Registry.register(Registry.ITEM, id, new BlockItem(block, new Item.Settings().group(group)));
        };
    }

    static <T extends Item> Registerable<T> item(Item item) {
        return id -> Registry.register(Registry.ITEM, id, item);
    }
}
