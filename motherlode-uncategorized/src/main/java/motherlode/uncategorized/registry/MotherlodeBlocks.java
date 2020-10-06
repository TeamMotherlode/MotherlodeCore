package motherlode.uncategorized.registry;

import com.swordglowsblue.artifice.api.util.Processor;
import motherlode.base.CommonData;
import motherlode.base.Motherlode;
import motherlode.base.api.AssetProcessor;
import motherlode.base.api.MotherlodeAssets;
import motherlode.uncategorized.MotherlodeUncategorized;
import motherlode.uncategorized.block.DefaultBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;
import java.util.function.Function;

public class MotherlodeBlocks {
    
    public static void init() {
        // CALLED TO MAINTAIN REGISTRY ORDER
    }

    static <T extends Block> T register(String name, T block, Item.Settings settings) {
        return register(name, block, new BlockItem(block, settings));
    }

    public static <T extends Block> T register(String name, T block) {
        return register(name, block, new Item.Settings().group(MotherlodeUncategorized.BLOCKS));
    }

    public static <T extends Block> T register(String name, T block, AssetProcessor p) {
        return register(name, block, new Item.Settings().group(MotherlodeUncategorized.BLOCKS));
    }

    public static <T extends Block> T register(String name, T block, Processor<T> p) {
        p.accept(block);
        return register(name, block, new Item.Settings().group(MotherlodeUncategorized.BLOCKS));
    }

    static <T extends Block> T register(String name, T block, Function<T, BlockItem> itemFactory) {
        return register(name, block, itemFactory.apply(block));
    }

    static <T extends Block, J extends DefaultBlock> T register(String name, T block, BlockItem item) {
        T b = Registry.register(Registry.BLOCK, Motherlode.id(name), block);
        if (item != null) {
            Registry.register(Registry.ITEM, Motherlode.id(name), new BlockItem(block, new Item.Settings().group(MotherlodeUncategorized.BLOCKS)));
        }
        if (block instanceof DefaultBlock) {
            DefaultBlock defaultBlock = (DefaultBlock)block;
            if (defaultBlock.hasDefaultLootTable()) {
                MotherlodeAssets.addData(Motherlode.id(name), CommonData.DEFAULT_BLOCK_LOOT_TABLE);
            }
        }
        return b;
    }
}