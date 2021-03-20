package motherlode.buildingblocks.block;

import net.minecraft.block.Block;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.util.Identifier;
import motherlode.base.Motherlode;
import motherlode.base.api.Registerable;
import motherlode.base.api.assets.AssetProcessor;
import motherlode.base.api.assets.CommonAssets;
import motherlode.base.api.assets.CommonData;
import motherlode.base.api.varianttype.MotherlodeVariantType;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;

public class StonePillarBlocks implements MotherlodeVariantType.Extension<Block, StoneType> {
    private PillarBlock pillar;
    public SlabBlock slab;

    @Override
    public void registerExtension(Identifier id, StoneType stoneType) {
        this.pillar = new PillarBlock(StoneType.BLOCK_SETTINGS);
        this.slab = new SlabBlock(StoneType.BLOCK_SETTINGS);

        Registerable.block(this.pillar, StoneType.ITEM_SETTINGS).register(Motherlode.id(id, name -> name + "_pillar"));
        Registerable.block(this.slab, StoneType.ITEM_SETTINGS).register(Motherlode.id(id, name -> name + "_pillar_slab"));
    }

    private static final AssetProcessor PILLAR = CommonAssets.PILLAR.andThen(CommonAssets.BLOCK_ITEM);

    @Override
    public void accept(ArtificeResourcePack.ClientResourcePackBuilder pack, Identifier id) {
        PILLAR.accept(pack, Motherlode.id(id, name -> name + "_pillar"));
        StoneType.SLAB_BLOCK.accept(pack, Motherlode.id(id, name -> name + "_pillar_slab"));
    }

    @Override
    public void accept(ArtificeResourcePack.ServerResourcePackBuilder pack, Identifier id) {
        CommonData.DEFAULT_BLOCK_LOOT_TABLE.accept(pack, Motherlode.id(id, name -> name + "_pillar"));
        CommonData.DEFAULT_BLOCK_LOOT_TABLE.accept(pack, Motherlode.id(id, name -> name + "_pillar_slab"));
    }

    @Override
    public void registerOnClient(Identifier id) {
    }

    @Override
    public Block[] variants() {
        return new Block[] { this.pillar, this.slab };
    }

    public PillarBlock getPillar() {
        return this.pillar;
    }

    public SlabBlock getSlab() {
        return this.slab;
    }
}
