package motherlode.copperdungeon.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import motherlode.base.Motherlode;
import motherlode.base.api.Registerable;
import motherlode.base.api.assets.AssetProcessor;
import motherlode.base.api.assets.CommonAssets;
import motherlode.base.api.assets.CommonData;
import motherlode.base.api.assets.DataProcessor;
import motherlode.base.api.varianttype.RegisterableVariantType;
import motherlode.base.block.MotherlodeStairsBlock;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;

public class DecorationBlock implements RegisterableVariantType<Block>, AssetProcessor, DataProcessor {
    private final Block base;
    private final StairsBlock stairs;
    private final SlabBlock slab;
    private final Block[] all;

    private final Item.Settings settings;

    public DecorationBlock(AbstractBlock.Settings settings, Item.Settings itemSettings) {
        this.base = new Block(settings);
        this.stairs = new MotherlodeStairsBlock(this.base.getDefaultState(), settings);
        this.slab = new SlabBlock(settings);
        this.all = new Block[] { this.base, this.stairs, this.slab };

        this.settings = itemSettings;
    }

    public Block getBaseBlock() {
        return this.base;
    }

    public StairsBlock getStairVariant() {
        return this.stairs;
    }

    public SlabBlock getSlabVariant() {
        return this.slab;
    }

    @Override
    public Block[] variants() {
        return this.all;
    }

    @Override
    public void register(Identifier id) {
        Motherlode.register(
            Registerable.block(this.base, this.settings),
            id,
            this.base
        );

        Motherlode.register(
            Registerable.block(this.stairs, this.settings),
            Motherlode.id(id, name -> name + "_stairs"),
            this.stairs
        );

        Motherlode.register(
            Registerable.block(this.slab, this.settings),
            Motherlode.id(id, name -> name + "_slab"),
            this.slab
        );
    }

    @Override
    public void accept(ArtificeResourcePack.ClientResourcePackBuilder pack, Identifier id) {
        CommonAssets.DEFAULT_BLOCK.accept(pack, id);
        CommonAssets.STAIR.andThen(CommonAssets.BLOCK_ITEM).accept(pack, Motherlode.id(id, name -> name + "_stairs"));
        CommonAssets.SLAB.andThen(CommonAssets.BLOCK_ITEM).accept(pack, Motherlode.id(id, name -> name + "_slab"));
    }

    @Override
    public void accept(ArtificeResourcePack.ServerResourcePackBuilder pack, Identifier id) {
        CommonData.DEFAULT_BLOCK_LOOT_TABLE.accept(pack, id);
        CommonData.DEFAULT_BLOCK_LOOT_TABLE.accept(pack, Motherlode.id(id, name -> name + "_stairs"));
        CommonData.DEFAULT_BLOCK_LOOT_TABLE.accept(pack, Motherlode.id(id, name -> name + "_slab"));
    }
}
