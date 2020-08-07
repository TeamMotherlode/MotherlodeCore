package motherlode.core.registry;

import motherlode.core.Motherlode;
import motherlode.core.api.BlockProperties;
import motherlode.core.assets.BlockTint;
import motherlode.core.assets.BlockModel;
import motherlode.core.assets.ItemModel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class MotherlodeBlockProperties {

    private static final Item.Settings GROUP = new Item.Settings().group(Motherlode.BLOCKS);

    public static final BlockProperties DEFAULT = of(true, BlockModel.DEFAULT);
    public static final BlockProperties PAINTABLE_WALL = of(false, BlockModel.PAINTABLE);
    public static final BlockProperties MANUAL_MODEL = of(true, BlockModel.MANUAL);
    public static final BlockProperties PLATFORM = of(true, BlockModel.PLATFORM, ItemModel.BLOCK, true, true);
    public static final BlockProperties SHOVELABLE = of(false, BlockModel.SHOVELABLE);
    public static final BlockProperties MANUAL_CUTOUT_LOOT_TABLE = of(false, BlockModel.MANUAL, ItemModel.MANUAL, false, true);
    public static final BlockProperties REED = of(false, BlockModel.MANUAL, ItemModel.FLAT_BLOCK, false, true);
    public static final BlockProperties PILLAR = of(false, BlockModel.PILLAR, ItemModel.BLOCK, true, false);

    public static BlockProperties MANUAL_CUTOUT(ItemGroup group) {
        return new BlockProperties(false,BlockModel.MANUAL, ItemModel.MANUAL, false, true, null, new Item.Settings().group(group));
    }

    public static BlockProperties STAIRS(boolean newStone) {
        return new BlockProperties(false, BlockModel.STAIR, newStone, ItemModel.BLOCK, true, false, null, GROUP);
    }

    public static BlockProperties SLAB(boolean newStone) {
        return new BlockProperties(false, BlockModel.SLAB, newStone, ItemModel.BLOCK, true, false, null, GROUP);
    }

    public static BlockProperties ORE(boolean lootTable) {
        return of(true, BlockModel.DEFAULT, ItemModel.BLOCK, lootTable, false);
    }

    public static BlockProperties PLANT(boolean defaultStateAndModel) {
        return new BlockProperties(defaultStateAndModel, defaultStateAndModel ? BlockModel.PLANT : BlockModel.MANUAL, ItemModel.FLAT_BLOCK, false, true, BlockTint.GRASS, GROUP);
    }



    private static BlockProperties of(boolean state, BlockModel blockModel) {
        return new BlockProperties(state, blockModel, ItemModel.BLOCK, true, false, null, GROUP);
    }

    private static BlockProperties of(boolean state, BlockModel model, ItemModel itemModel, boolean lootTable, boolean cutout) {
        return new BlockProperties(state, model, itemModel, lootTable, cutout, null, GROUP);
    }
}
