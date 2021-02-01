package motherlode.buildingblocks.block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.StringIdentifiable;
import motherlode.base.Motherlode;
import motherlode.base.api.Registerable;
import motherlode.base.api.assets.AssetProcessor;
import motherlode.base.api.assets.CommonAssets;
import motherlode.base.api.assets.CommonData;
import motherlode.base.api.varianttype.MotherlodeVariantType;
import motherlode.base.util.Triple;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;

public class StoneType extends MotherlodeVariantType<Block, StoneType> {
    public static final AbstractBlock.Settings BLOCK_SETTINGS = AbstractBlock.Settings.of(Material.STONE).requiresTool().strength(3.0F, 3.0F);
    public static final Item.Settings ITEM_SETTINGS = new Item.Settings().group(ItemGroup.BUILDING_BLOCKS);

    public static final AssetProcessor STAIRS_BLOCK = CommonAssets.STAIR.andThen(CommonAssets.BLOCK_ITEM);
    public static final AssetProcessor SLAB_BLOCK = CommonAssets.SLAB.andThen(CommonAssets.BLOCK_ITEM);

    private final List<Pair<Variant, Block>> variants;
    private final List<Triple<Block, Variant, SlabBlock>> slabs;
    private final List<Triple<Block, Variant, StairsBlock>> stairs;
    private final boolean baseBlock;

    public StoneType(Identifier id) {
        this(id, true);
    }

    public StoneType(Identifier id, boolean baseBlock) {
        super(id);

        this.variants = new ArrayList<>();
        this.slabs = new ArrayList<>();
        this.stairs = new ArrayList<>();
        this.baseBlock = baseBlock;
    }

    @Override
    protected StoneType getThis() {
        return this;
    }

    @Override
    public Block[] baseVariants() {
        return Stream.concat(variants.stream().skip(this.baseBlock ? 0 : 1).map(Pair::getRight), Stream.concat(
            slabs.stream().map(Triple::getThird), stairs.stream().map(Triple::getThird)
        )).toArray(Block[]::new);
    }

    @Override
    public void registerBase(Identifier id) {
        Variant[] variants = this.baseBlock ? Variant.values() : Arrays.copyOfRange(Variant.values(), 1, Variant.values().length);

        for (Variant variant : variants) {
            Block block = get();
            this.variants.add(new Pair<>(variant, block));
            this.slabs.add(new Triple<>(block, variant, new SlabBlock(BLOCK_SETTINGS)));
            this.stairs.add(new Triple<>(block, variant, new DefaultStairsBlock(block.getDefaultState(), BLOCK_SETTINGS)));
        }

        for (int i = this.baseBlock ? 0 : 1; i < variants.length; i++) {
            int ii = i;
            Registerable.block(this.variants.get(i).getRight(), ITEM_SETTINGS).register(Motherlode.id(id, name -> name + variants[ii].asString()));
            Registerable.block(this.slabs.get(i).getThird(), ITEM_SETTINGS).register(Motherlode.id(id, name -> name + variants[ii].asString() + "_slab"));
            Registerable.block(this.stairs.get(i).getThird(), ITEM_SETTINGS).register(Motherlode.id(id, name -> name + variants[ii].asString() + "_stairs"));
        }
    }

    @Override
    protected void registerOnClient(Identifier id) {
    }

    @Override
    public void accept(ArtificeResourcePack.ClientResourcePackBuilder pack, Identifier id) {
        for (Pair<Variant, Block> pair : this.variants) {
            CommonAssets.DEFAULT_BLOCK.accept(pack, Motherlode.id(id, name -> name + pair.getLeft().asString()));
        }

        for (Triple<Block, Variant, SlabBlock> triple : this.slabs) {
            SLAB_BLOCK.accept(pack, Motherlode.id(id, name -> name + triple.getSecond().asString() + "_slab"));
        }

        for (Triple<Block, Variant, StairsBlock> triple : this.stairs) {
            STAIRS_BLOCK.accept(pack, Motherlode.id(id, name -> name + triple.getSecond().asString() + "_stairs"));
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

    private static Block get() {
        return new Block(BLOCK_SETTINGS);
    }

    public enum Variant implements StringIdentifiable {
        BASE(""),
        COBBLE("_cobble"),
        POLISHED("_polished"),
        BRICKS("_bricks");

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
