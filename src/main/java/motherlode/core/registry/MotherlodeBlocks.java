package motherlode.core.registry;

import motherlode.core.Motherlode;
import motherlode.core.block.DefaultBlock;
import motherlode.core.block.DefaultOreBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class MotherlodeBlocks {
    public static final Block COPPER_ORE = register("copper_ore", new DefaultOreBlock(AbstractBlock.Settings.of(Material.STONE).requiresTool().strength(3.0F, 3.0F)));
    public static final Block COPPER_BLOCK = register("copper_block", new DefaultBlock(AbstractBlock.Settings.of(Material.METAL).requiresTool().strength(3.0F, 3.0F)));
    public static final List<DefaultBlock> defaultStateList = new ArrayList<DefaultBlock>();;
    public static final List<DefaultBlock> defaultModelList = new ArrayList<DefaultBlock>();;
    public static final List<DefaultBlock> defaultItemModelList = new ArrayList<DefaultBlock>();;

    public static void init() {
        // CALLED TO MAINTAIN REGISTRY ORDER
    }

    static <T extends Block> T register(String name, T block, Item.Settings settings) {
        return register(name, block, new BlockItem(block, settings));
    }

    static <T extends Block> T register(String name, T block) {
        return register(name, block, new Item.Settings().group(Motherlode.MAIN_GROUP));
    }

    static <T extends Block> T register(String name, T block, Function<T, BlockItem> itemFactory) {
        return register(name, block, itemFactory.apply(block));
    }

    static <T extends Block, J extends DefaultBlock> T register(String name, T block, BlockItem item) {
        T b = Registry.register(Registry.BLOCK, Motherlode.id(name), block);
        if (item != null) {
            MotherlodeItems.register(name, item);
        }
        if (block instanceof DefaultBlock){
            if (((DefaultBlock) block).hasDefaultState()){
                defaultStateList.add((DefaultBlock) block);
            }
            if (((DefaultBlock) block).hasDefaultModel()){
                defaultModelList.add((DefaultBlock) block);
            }
            if (((DefaultBlock) block).hasDefaultItemModel()){
                defaultItemModelList.add((DefaultBlock) block);
            }
        }
        return b;
    }
}