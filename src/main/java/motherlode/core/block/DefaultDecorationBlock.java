package motherlode.core.block;

import motherlode.core.Motherlode;
import motherlode.core.registry.MotherlodeBlocks;
import motherlode.core.registry.MotherlodeItems;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class DefaultDecorationBlock extends DefaultBlock {

    private StairsBlock STAIR_VARIANT;
    private SlabBlock SLAB_VARIANT;
    private final String id;

    public StairsBlock getSTAIR_VARIANT() {
        return STAIR_VARIANT;
    }

    public SlabBlock getSLAB_VARIANT() {
        return SLAB_VARIANT;
    }

    public DefaultDecorationBlock(String id, Settings settings) {
        super(settings);
        this.id = id;
    }

    public DefaultDecorationBlock registerAll(){
        STAIR_VARIANT = Registry.register(Registry.BLOCK, Motherlode.id(id+"_stairs"), new DefaultStairsBlock(this.getDefaultState(), settings));
        MotherlodeItems.register(id+"_stairs", new BlockItem(STAIR_VARIANT, new Item.Settings().group(Motherlode.BLOCKS)));
        MotherlodeBlocks.usesStairModel.put(STAIR_VARIANT, !Registry.BLOCK.getId(this).getNamespace().equals("minecraft"));
        MotherlodeBlocks.defaultItemModelList.add(STAIR_VARIANT);

        SLAB_VARIANT = Registry.register(Registry.BLOCK, Motherlode.id(id+"_slab"), new SlabBlock(settings));
        MotherlodeItems.register(id+"_slab", new BlockItem(SLAB_VARIANT, new Item.Settings().group(Motherlode.BLOCKS)));
        MotherlodeBlocks.usesSlabModel.put(SLAB_VARIANT, !Registry.BLOCK.getId(this).getNamespace().equals("minecraft"));
        MotherlodeBlocks.defaultItemModelList.add(SLAB_VARIANT);
        return this;
    }

}
