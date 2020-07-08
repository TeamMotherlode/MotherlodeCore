package motherlode.core.block;

import motherlode.core.Motherlode;
import motherlode.core.registry.MotherlodeBlocks;
import motherlode.core.registry.MotherlodeItems;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class StoneBlocks {

    public final Block BASE;
    public final Block COBBLE;
    public final Block RUBBLE;
    public final Block BRICKS;
    public final Block BRICKS_SMALL;
    public final Block BRICKS_TINY;
    public final Block HERRINGBONE;
    public final Block TILES;
    public final Block TILES_SMALL;
    public final Block PILLAR;
    public final Block SLAB;
    public final Block STAIRS;
    public final Block[] CARVED;

    public final List<Block> ALL;

    public static StoneBlocks newStone(String baseID, boolean rubble) {
        return new StoneBlocks(baseID, true, true, true, rubble);
    }

    public static StoneBlocks fromStone(String baseID, boolean moreDetailed) {
        return new StoneBlocks(baseID, false, moreDetailed, moreDetailed, false);
    }

    private StoneBlocks(String baseID, boolean newStoneType, boolean tinyBricks, boolean pillar, boolean rubble) {

        BASE = newStoneType? register(baseID) : Registry.BLOCK.get(new Identifier(baseID));
        COBBLE = newStoneType? register(baseID + "_cobble") : null;
        RUBBLE = rubble? register(baseID + "_rubble") : null;
        BRICKS = register(baseID + "_bricks");
        BRICKS_SMALL = register(baseID + "_bricks_small");
        BRICKS_TINY = tinyBricks ? register(baseID + "_bricks_tiny") : null;
        HERRINGBONE = register(baseID + "_herringbone");
        TILES = register(baseID + "_tiles");
        TILES_SMALL = register(baseID + "_tiles_small");

        PILLAR = pillar ? Registry.register(Registry.BLOCK,Motherlode.id(baseID + "_pillar"),
                new PillarBlock(AbstractBlock.Settings.of(Material.STONE).requiresTool().strength(3.0F, 3.0F))) : null;
        if (pillar)
            MotherlodeItems.register(baseID + "_pillar", new BlockItem(PILLAR, new Item.Settings().group(Motherlode.BLOCKS)));

        SLAB = Registry.register(Registry.BLOCK, Motherlode.id(baseID) + "_slab",
                new SlabBlock(AbstractBlock.Settings.of(Material.STONE).requiresTool().strength(3.0F, 3.0F)));
        MotherlodeItems.register(baseID + "_slab", new BlockItem(SLAB, new Item.Settings().group(Motherlode.BLOCKS)));

        STAIRS = Registry.register(Registry.BLOCK,Motherlode.id(baseID) + "_stairs",
                new DefaultStairsBlock(BASE.getDefaultState(), AbstractBlock.Settings.of(Material.STONE).requiresTool().strength(3.0F, 3.0F)));
        MotherlodeItems.register(baseID + "_stairs", new BlockItem(STAIRS, new Item.Settings().group(Motherlode.BLOCKS)));

        List<Block> carved = new ArrayList<>();

        for (char variant = 'a'; variant < 'z'; variant++) {
            String id = baseID + "_carved_" + variant;
            if (getClass().getResourceAsStream("assets/motherlode/textures/block/" + id + ".png") == null)
                break;
            carved.add(register(id));
        }
        CARVED = new Block[carved.size()];
        carved.toArray(CARVED);

        MotherlodeBlocks.usesSlabModel.add((SlabBlock)SLAB);
        MotherlodeBlocks.usesStairModel.add((StairsBlock) STAIRS);
        MotherlodeBlocks.usesPillarModel.add(PILLAR);
        MotherlodeBlocks.defaultItemModelList.add(SLAB);
        MotherlodeBlocks.defaultItemModelList.add(STAIRS);
        MotherlodeBlocks.defaultItemModelList.add(PILLAR);

        ALL = new ArrayList<>();
        ALL.add(BASE);
        ALL.add(BRICKS);
        ALL.add(BRICKS_SMALL);
        ALL.add(HERRINGBONE);
        ALL.add(TILES);
        ALL.add(TILES_SMALL);
        ALL.add(PILLAR);
        ALL.add(SLAB);
        ALL.add(STAIRS);
        ALL.addAll(carved);
        if (COBBLE != null) ALL.add(COBBLE);
        if (RUBBLE != null) ALL.add(RUBBLE);
        if (BRICKS_TINY != null) ALL.add(BRICKS_TINY);

    }

    static DefaultBlock register(String id) {
        return MotherlodeBlocks.register(id, new DefaultBlock(AbstractBlock.Settings.of(Material.STONE).requiresTool().strength(3.0F, 3.0F)));
    }

}