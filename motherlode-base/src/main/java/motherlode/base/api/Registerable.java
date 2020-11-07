package motherlode.base.api;

import java.util.Objects;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@FunctionalInterface
public interface Registerable<T> {
    /**
     * Registers something.
     * @param identifier The {@link Identifier} used to register it.
     */
    void register(Identifier identifier);

    /**
     * Composes a new {@code Registerable} that will first call this {@code register} method, then the given {@code Registerable}'s {@code register} method.
     * @param after The {@code AssetProcessor} to apply after this one.
     * @return The composed {@code AssetProcessor}
     */
    default Registerable<T> andThen(Registerable<T> after) {
        Objects.requireNonNull(after);

        return id -> {
            this.register(id);
            after.register(id);
        };
    }

    /**
     * Creates a {@code Registerable} that registers the given block and a {@link BlockItem} for that block.
     * @param block The block to register.
     * @param itemSettings The {@link Item.Settings} used for the {@code BlockItem}.
     * @return A registerable that registers the given block and a {@code BlockItem} for that block.
     */
    static <T extends Block> Registerable<T> block(Block block, Item.Settings itemSettings) {
        return id -> {
            Registry.register(Registry.BLOCK, id, block);
            if (itemSettings != null) Registry.register(Registry.ITEM, id, new BlockItem(block, itemSettings));
        };
    }

    /**
     * Creates a {@code Registerable} that registers the given block and a {@link BlockItem} for that block.
     * @param block The block to register.
     * @param group The {@link ItemGroup} which the {@code BlockItem} will be added to.
     * @return A registerable that registers the given block and a {@code BlockItem} for that block.
     */
    static <T extends Block> Registerable<T> block(Block block, ItemGroup group) {
        return id -> {
            Registry.register(Registry.BLOCK, id, block);
            if (group != null)
                Registry.register(Registry.ITEM, id, new BlockItem(block, new Item.Settings().group(group)));
        };
    }

    /**
     * Creates a {@code Registerable} that registers the given item.
     * @param item The item to register.
     * @return A registerable that registers the given item.
     */
    static <T extends Item> Registerable<T> item(Item item) {
        return id -> Registry.register(Registry.ITEM, id, item);
    }
}
