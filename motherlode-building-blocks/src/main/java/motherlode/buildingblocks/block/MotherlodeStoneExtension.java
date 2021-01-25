package motherlode.buildingblocks.block;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.StringIdentifiable;
import motherlode.base.Motherlode;
import motherlode.base.api.Registerable;
import motherlode.base.api.assets.CommonAssets;
import motherlode.base.api.assets.CommonData;
import motherlode.base.api.varianttype.MotherlodeVariantType;
import motherlode.base.util.Triple;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;

public class MotherlodeStoneExtension implements MotherlodeVariantType.Extension<Block> {
    private final List<Pair<Variant, Block>> variants;
    private final List<Triple<Block, Variant, SlabBlock>> slabs;
    private final List<Triple<Block, Variant, StairsBlock>> stairs;

    public MotherlodeStoneExtension() {
        this.variants = new ArrayList<>();
        this.slabs = new ArrayList<>();
        this.stairs = new ArrayList<>();
    }

    @Override
    public Block[] variants() {
        return Stream.concat(variants.stream().map(Pair::getRight), Stream.concat(
            slabs.stream().map(Triple::getThird), stairs.stream().map(Triple::getThird)
        )).toArray(Block[]::new);
    }

    @Override
    public void registerExtension(Identifier id) {
        Variant[] variants = Variant.values();

        for (Variant variant : variants) {
            Block block = new Block(StoneVariantType.BLOCK_SETTINGS);
            this.variants.add(new Pair<>(variant, block));
            this.slabs.add(new Triple<>(block, variant, new SlabBlock(StoneVariantType.BLOCK_SETTINGS)));
            this.stairs.add(new Triple<>(block, variant, new DefaultStairsBlock(block.getDefaultState(), StoneVariantType.BLOCK_SETTINGS)));
        }

        for (int i = 0; i < variants.length; i++) {
            int ii = i;
            Registerable.block(this.variants.get(i).getRight(), StoneVariantType.ITEM_SETTINGS).register(Motherlode.id(id, name -> name + variants[ii].asString()));
            Registerable.block(this.slabs.get(i).getThird(), StoneVariantType.ITEM_SETTINGS).register(Motherlode.id(id, name -> name + variants[ii].asString() + "_slab"));
            Registerable.block(this.stairs.get(i).getThird(), StoneVariantType.ITEM_SETTINGS).register(Motherlode.id(id, name -> name + variants[ii].asString() + "_stairs"));
        }
    }

    @Override
    public void registerOnClient(Identifier id) {
    }

    @Override
    public void accept(ArtificeResourcePack.ClientResourcePackBuilder pack, Identifier id) {
        for (Pair<Variant, Block> pair : this.variants) {
            CommonAssets.DEFAULT_BLOCK.accept(pack, Motherlode.id(id, name -> name + pair.getLeft().asString()));
        }

        for (Triple<Block, Variant, SlabBlock> triple : this.slabs) {
            StoneVariantType.SLAB_BLOCK.accept(pack, Motherlode.id(id, name -> name + triple.getSecond().asString() + "_slab"));
        }

        for (Triple<Block, Variant, StairsBlock> triple : this.stairs) {
            StoneVariantType.STAIRS_BLOCK.accept(pack, Motherlode.id(id, name -> name + triple.getSecond().asString() + "_stairs"));
        }
    }

    @Override
    public void accept(ArtificeResourcePack.ServerResourcePackBuilder pack, Identifier id) {
        for (Pair<Variant, Block> pair : this.variants) {
            CommonData.DEFAULT_BLOCK_LOOT_TABLE.accept(pack, Motherlode.id(id, name -> name + pair.getLeft().asString()));
        }

        for (Triple<Block, Variant, SlabBlock> triple : this.slabs) {
            CommonData.DEFAULT_BLOCK_LOOT_TABLE.accept(pack, Motherlode.id(id, name -> name + triple.getSecond().asString() + "_slab"));
        }

        for (Triple<Block, Variant, StairsBlock> triple : this.stairs) {
            CommonData.DEFAULT_BLOCK_LOOT_TABLE.accept(pack, Motherlode.id(id, name -> name + triple.getSecond().asString() + "_stairs"));
        }
    }

    public Block getVariant(Variant variant) {
        return this.variants.stream().filter(pair -> pair.getLeft() == variant).findAny().map(Pair::getRight).orElseThrow(IllegalStateException::new);
    }

    public SlabBlock getSlab(Variant variant) {
        return this.slabs.stream().filter(triple -> triple.getSecond() == variant).findAny().map(Triple::getThird).orElseThrow(IllegalStateException::new);
    }

    public StairsBlock getStairs(Variant variant) {
        return this.stairs.stream().filter(triple -> triple.getSecond() == variant).findAny().map(Triple::getThird).orElseThrow(IllegalStateException::new);
    }

    public enum Variant implements StringIdentifiable {
        SMALL_BRICKS("_small_bricks"),
        HERRINGBONE("_herringbone"),
        TILES("_tiles"),
        SMALL_TILES("_small_tiles"),
        PILLAR("_pillar");

        private final String name;

        Variant(String name) {
            this.name = name;
        }

        @Override
        public String asString() {
            return this.name;
        }
    }
}
