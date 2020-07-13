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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoneBlocks {

    private static final List<String> IGNORE = new ArrayList<>();
    private static final AbstractBlock.Settings BLOCK_SETTINGS = AbstractBlock.Settings.of(Material.STONE).requiresTool().strength(3.0F, 3.0F);
    static {
        IGNORE.add("stone_bricks_stairs");
    }

    public final Block BASE;
    public final Block COBBLE;
    public final Block RUBBLE;
    public final Block POLISHED;
    public final Block BRICKS;
    public final Block BRICKS_SMALL;
    public final Block HERRINGBONE;
    public final Block TILES;
    public final Block TILES_SMALL;
    public final Block PILLAR;
    public final Map<Block,Block> STAIRS = new HashMap<>();
    public final Map<Block,Block> SLABS = new HashMap<>();
    public final Block[] CARVED;

    public final List<Block> ALL = new ArrayList<>();

    public static StoneBlocks newStone(String baseID, boolean rubble) {
        return new StoneBlocks(baseID, true, true, true, rubble, null, null, null);
    }

    public static StoneBlocks fromStone(String baseID, Block bricks, Block polished) {
        return new StoneBlocks(baseID, false, polished != null, false, false, null, polished, bricks);
    }

    public static StoneBlocks fromBlock(String baseID, Block base) {
        return new StoneBlocks(baseID, false, false, false, false, base, null, null);
    }

    private StoneBlocks(String baseID, boolean newStoneType, boolean polished, boolean pillar, boolean rubble, Block baseBlock, Block polishedBlock, Block bricksBlock) {

        BASE = baseBlock != null ? baseBlock : newStoneType? register(baseID) : Registry.BLOCK.get(new Identifier(baseID));
        COBBLE = newStoneType? register(baseID + "_cobble") : null;
        RUBBLE = rubble? register(baseID + "_rubble") : null;
        POLISHED = polished ? polishedBlock != null ? polishedBlock : register("polished_" + baseID) : null;
        BRICKS = bricksBlock == null ? register(baseID + "_bricks") : bricksBlock;
        BRICKS_SMALL = register(baseID + "_bricks_small");
        HERRINGBONE = register(baseID + "_herringbone");
        TILES = register(baseID + "_tiles");
        TILES_SMALL = register(baseID + "_tiles_small");

        ALL.add(BASE);
        if (newStoneType) ALL.add(COBBLE);
        if (rubble) ALL.add(RUBBLE);
        if (polished) ALL.add(POLISHED);
        ALL.add(BRICKS);
        ALL.add(BRICKS_SMALL);
        ALL.add(HERRINGBONE);
        ALL.add(TILES);
        ALL.add(TILES_SMALL);

        for (Block block : ALL) {
            String id = Registry.BLOCK.getId(block).getPath() + "_stairs";
            if (!IGNORE.contains(id)) {
                StairsBlock stairBlock = Registry.register(Registry.BLOCK,Motherlode.id(id), new DefaultStairsBlock(BASE.getDefaultState(), BLOCK_SETTINGS));
                STAIRS.put(block, stairBlock);
                MotherlodeItems.register(id, new BlockItem(stairBlock, new Item.Settings().group(Motherlode.BLOCKS)));
                MotherlodeBlocks.usesStairModel.put(stairBlock, !Registry.BLOCK.getId(block).getNamespace().equals("minecraft"));
                MotherlodeBlocks.defaultItemModelList.add(stairBlock);
            }
        }

        PILLAR = pillar ? Registry.register(Registry.BLOCK,Motherlode.id(baseID + "_pillar"), new PillarBlock(BLOCK_SETTINGS)) : null;
        if (pillar) {
            MotherlodeItems.register(baseID + "_pillar", new BlockItem(PILLAR, new Item.Settings().group(Motherlode.BLOCKS)));
            MotherlodeBlocks.usesPillarModel.add(PILLAR);
            MotherlodeBlocks.defaultItemModelList.add(PILLAR);
            ALL.add(PILLAR);
        }

        for (Block block : ALL) {
            String id = Registry.BLOCK.getId(block).getPath() + "_slab";
            if (!IGNORE.contains(id)) {
                SlabBlock slabBlock = Registry.register(Registry.BLOCK,Motherlode.id(id), new SlabBlock(BLOCK_SETTINGS));
                SLABS.put(block, slabBlock);
                MotherlodeItems.register(id, new BlockItem(slabBlock, new Item.Settings().group(Motherlode.BLOCKS)));
                MotherlodeBlocks.usesSlabModel.put(slabBlock, !Registry.BLOCK.getId(block).getNamespace().equals("minecraft"));
                MotherlodeBlocks.defaultItemModelList.add(slabBlock);
            }
        }

        List<Block> carved = new ArrayList<>();
        for (char variant = 'a'; variant < 'z'; variant++) {
            String id = baseID + "_carved_" + variant;
            if (getClass().getResourceAsStream("assets/motherlode/textures/block/" + id + ".png") == null)
                break;
            carved.add(register(id));
        }
        CARVED = new Block[carved.size()];
        carved.toArray(CARVED);

        ALL.addAll(carved);
        ALL.addAll(STAIRS.values());
        ALL.addAll(SLABS.values());

    }

    public Block getStairs(Block block) {
        return STAIRS.get(block);
    }
    public Block getSlab(Block block) {
        return SLABS.get(block);
    }
    public Block getCarved(char variant) {
        return variant < 'a' || variant > 'a' + CARVED.length ? null : CARVED[variant - 'a'];
    }

    static DefaultBlock register(String id) {
        return MotherlodeBlocks.register(id, new DefaultBlock(AbstractBlock.Settings.of(Material.STONE).requiresTool().strength(3.0F, 3.0F)));
    }

}