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

public class MotherlodeStoneBlocks implements MotherlodeVariantType.Extension<Block, StoneType> {
    private final List<Pair<Variant, Block>> variants;
    private final List<Triple<Block, Variant, SlabBlock>> slabs;
    private final List<Triple<Block, Variant, StairsBlock>> stairs;

    public MotherlodeStoneBlocks() {
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
    public void registerExtension(Identifier id, StoneType stoneType) {
        Variant[] variants = Variant.values();

        for (Variant variant : variants) {
            Block block = new Block(StoneType.BLOCK_SETTINGS);
            this.variants.add(new Pair<>(variant, block));
            this.slabs.add(new Triple<>(block, variant, new SlabBlock(StoneType.BLOCK_SETTINGS)));
            this.stairs.add(new Triple<>(block, variant, new DefaultStairsBlock(block.getDefaultState(), StoneType.BLOCK_SETTINGS)));
        }

        for (int i = 0; i < variants.length; i++) {
            int ii = i;
            Registerable.block(this.variants.get(i).getRight(), StoneType.ITEM_SETTINGS).register(Motherlode.id(id, name -> name + variants[ii].asString()));
            Registerable.block(this.slabs.get(i).getThird(), StoneType.ITEM_SETTINGS).register(Motherlode.id(id, name -> name + variants[ii].asString() + "_slab"));
            Registerable.block(this.stairs.get(i).getThird(), StoneType.ITEM_SETTINGS).register(Motherlode.id(id, name -> name + variants[ii].asString() + "_stairs"));
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
            StoneType.SLAB_BLOCK.accept(pack, Motherlode.id(id, name -> name + triple.getSecond().asString() + "_slab"));
        }

        for (Triple<Block, Variant, StairsBlock> triple : this.stairs) {
            StoneType.STAIRS_BLOCK.accept(pack, Motherlode.id(id, name -> name + triple.getSecond().asString() + "_stairs"));
        }
    }

    @Override
    public void accept(ArtificeResourcePack.ServerResourcePackBuilder pack, Identifier id) {
        for (Pair<Variant, Block> pair : this.variants) {
            CommonData.DEFAULT_BLOCK_LOOT_TABLE.accept(pack, Motherlode.id(id, name -> name + pair.getLeft().asString()));
        }

        Identifier smallBricks = Motherlode.id(id, name -> name + Variant.SMALL_BRICKS.asString());
        Identifier herringbone = Motherlode.id(id, name -> name + Variant.HERRINGBONE.asString());
        Identifier tiles = Motherlode.id(id, name -> name + Variant.TILES.asString());
        Identifier smallTiles = Motherlode.id(id, name -> name + Variant.SMALL_TILES.asString());

        StoneType.addStonecuttingRecipe(pack, id, smallBricks, 1);
        StoneType.addStonecuttingRecipe(pack, id, herringbone, 1);
        StoneType.addStonecuttingRecipe(pack, id, tiles, 1);
        StoneType.addStonecuttingRecipe(pack, id, smallTiles, 1);

        pack.addShapedRecipe(smallBricks, recipe -> recipe
            .pattern("**", "**")
            .ingredientItem('*', id)
            .result(smallBricks, 4)
        );

        pack.addShapedRecipe(smallTiles, recipe -> recipe
            .pattern("**", "**")
            .ingredientItem('*', tiles)
            .result(smallTiles, 4)
        );

        for (Triple<Block, Variant, SlabBlock> triple : this.slabs) {
            Identifier normal = Motherlode.id(id, name -> name + triple.getSecond().asString());
            Identifier slab = Motherlode.id(normal, name -> name + "_slab");

            CommonData.DEFAULT_BLOCK_LOOT_TABLE.accept(pack, slab);

            pack.addShapedRecipe(slab, recipe -> recipe
                .pattern("***")
                .ingredientItem('*', normal)
                .result(slab, 6)
            );

            StoneType.addStonecuttingRecipe(pack, normal, slab, 2);
        }

        for (Triple<Block, Variant, StairsBlock> triple : this.stairs) {
            Identifier normal = Motherlode.id(id, name -> name + triple.getSecond().asString());
            Identifier stairs = Motherlode.id(normal, name -> name + "_stairs");

            CommonData.DEFAULT_BLOCK_LOOT_TABLE.accept(pack, stairs);

            pack.addShapedRecipe(stairs, recipe -> recipe
                .pattern("*  ", "** ", "***")
                .ingredientItem('*', normal)
                .result(stairs, 4)
            );

            StoneType.addStonecuttingRecipe(pack, normal, stairs, 1);
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
        SMALL_TILES("_small_tiles");

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
