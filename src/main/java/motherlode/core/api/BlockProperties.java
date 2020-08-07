package motherlode.core.api;

import motherlode.core.assets.BlockTint;
import motherlode.core.assets.BlockModel;
import motherlode.core.assets.ItemModel;

import net.minecraft.item.Item;

public class BlockProperties {

    public final boolean defaultState;
    public final BlockModel blockModel;
    public final boolean blockModelBoolean;
    public final ItemModel itemModel;
    public final boolean defaultLootTable;
    public final Item.Settings itemSettings;
    public final boolean cutout;
    public final BlockTint blockTint;

    public BlockProperties(boolean state, BlockModel model, ItemModel itemModel, boolean lootTable, boolean cutout, BlockTint blockTint, Item.Settings itemSettings) {
        this(state, model, false, itemModel, lootTable, cutout, blockTint, itemSettings);
    }

    public BlockProperties(boolean state, BlockModel model, boolean blockModelBoolean, ItemModel itemModel, boolean lootTable, boolean cutout, BlockTint blockTint, Item.Settings itemSettings) {
        this.defaultState = state;
        this.blockModel = model;
        this.blockModelBoolean = blockModelBoolean;
        this.itemModel = itemModel;
        this.defaultLootTable = lootTable;
        this.itemSettings = itemSettings;
        this.cutout = cutout;
        this.blockTint = blockTint;
    }

}
