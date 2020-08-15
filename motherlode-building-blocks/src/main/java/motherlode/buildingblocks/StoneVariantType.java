package motherlode.buildingblocks;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import motherlode.base.Motherlode;
import motherlode.base.api.ArtificeProcessor;
import motherlode.base.api.RegisterableVariantType;
import motherlode.uncategorized.MotherlodeUncategorized;
import motherlode.uncategorized.block.DefaultStairsBlock;
import motherlode.uncategorized.registry.MotherlodeBlocks;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoneVariantType implements RegisterableVariantType<Block>, ArtificeProcessor {

    private static final List<String> IGNORE = new ArrayList<>();
    private static final AbstractBlock.Settings BLOCK_SETTINGS = AbstractBlock.Settings.of(Material.STONE).requiresTool().strength(3.0F, 3.0F);

    private final String ID;
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
    public final Map<Block, Block> STAIRS = new HashMap<>();
    public final Map<Block, Block> SLABS = new HashMap<>();
    public final Map<Identifier, Block> CARVED;

    public final List<Block> ALL = new ArrayList<>();

    public static StoneVariantType newStone(String baseID, boolean rubble) {
        return new StoneVariantType(baseID, true, true, true, rubble, null, null, null);
    }

    public static StoneVariantType fromStone(String baseID, Block bricks, Block polished) {
        return new StoneVariantType(baseID, false, polished != null, false, false, null, polished, bricks);
    }

    public static StoneVariantType fromBlock(String baseID, Block base) {
        return new StoneVariantType(baseID, false, false, false, false, base, null, null);
    }

    private StoneVariantType(String id, boolean newStoneType, boolean polished, boolean pillar, boolean rubble, Block baseBlock, Block polishedBlock, Block bricksBlock) {

        ID = id;
        BASE = baseBlock != null ? baseBlock : newStoneType ? get() : Registry.BLOCK.get(new Identifier(id));
        COBBLE = newStoneType ? get() : null;
        RUBBLE = rubble ? get() : null;
        POLISHED = polished ? polishedBlock != null ? polishedBlock : get() : null;
        BRICKS = bricksBlock == null ? get() : bricksBlock;
        BRICKS_SMALL = get();
        HERRINGBONE = get();
        TILES = get();
        TILES_SMALL = get();

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
            String stairsId = Registry.BLOCK.getId(block).getPath() + "_stairs";
            if (!IGNORE.contains(stairsId)) {
                StairsBlock stairBlock = new DefaultStairsBlock(BASE.getDefaultState(), BLOCK_SETTINGS);
                STAIRS.put(block, stairBlock);
                MotherlodeBlocks.usesStairModel.put(stairBlock, !Registry.BLOCK.getId(block).getNamespace().equals("minecraft"));
                MotherlodeBlocks.defaultItemModelList.add(stairBlock);
            }
        }

        PILLAR = pillar ? new PillarBlock(BLOCK_SETTINGS) : null;
        if (pillar) {
            MotherlodeBlocks.usesPillarModel.add(PILLAR);
            MotherlodeBlocks.defaultItemModelList.add(PILLAR);
            ALL.add(PILLAR);
        }

        for (Block block : ALL) {
            String slabId = Registry.BLOCK.getId(block).getPath() + "_slab";
            if (!IGNORE.contains(slabId)) {
                SlabBlock slabBlock = new SlabBlock(BLOCK_SETTINGS);
                SLABS.put(block, slabBlock);
                MotherlodeBlocks.usesSlabModel.put(slabBlock, !Registry.BLOCK.getId(block).getNamespace().equals("minecraft"));
                MotherlodeBlocks.defaultItemModelList.add(slabBlock);
            }
        }

        Map<Identifier, Block> carved = new HashMap<>();
        for (char variant = 'a'; variant < 'z'; variant++) {
            String carvedId = id + "_carved_" + variant;
            if (getClass().getResourceAsStream("assets/" + MotherlodeBuildingBlocksMod.MODID + "/textures/block/" + carvedId + ".png") == null)
                break;
            carved.put(Motherlode.id(MotherlodeBuildingBlocksMod.MODID, carvedId), get());
        }
        CARVED = carved;
        ALL.addAll(carved.values());
        ALL.addAll(STAIRS.values());
        ALL.addAll(SLABS.values());
    }

    @Override
    public Block[] variants() {
        return ALL.toArray(new Block[ALL.size()]);
    }

    @Override
    public void register(Identifier id) {

        if (BASE != null) register(id, BASE);
        if (COBBLE != null) register(Motherlode.id(id.getNamespace(), id.getPath() + "_cobble"), COBBLE);
        if (RUBBLE != null) register(Motherlode.id(id.getNamespace(), id.getPath() + "_rubble"), RUBBLE);
        if (POLISHED != null) register(Motherlode.id(id.getNamespace(), "polished" + id.getPath()), POLISHED);
        if (BRICKS != null) register(Motherlode.id(id.getNamespace(), id.getPath() + "_bricks"), BRICKS);
        if (BRICKS_SMALL != null)
            register(Motherlode.id(id.getNamespace(), id.getPath() + "_bricks_small"), BRICKS_SMALL);
        if (HERRINGBONE != null) register(Motherlode.id(id.getNamespace(), id.getPath() + "_herringbone"), HERRINGBONE);
        if (TILES != null) register(Motherlode.id(id.getNamespace(), id.getPath() + "_tiles"), TILES);
        if (TILES_SMALL != null) register(Motherlode.id(id.getNamespace(), id.getPath() + "_tiles_small"), TILES_SMALL);
        if (PILLAR != null) register(Motherlode.id(id.getNamespace(), id.getPath() + "_pillar"), PILLAR);

        for(Map.Entry<Block, Block> entry: STAIRS.entrySet()) {

            register(Motherlode.id(id.getNamespace(), Registry.BLOCK.getId(entry.getKey()).getPath() + "_stairs"), entry.getValue());
        }
        for(Map.Entry<Block, Block> entry: SLABS.entrySet()) {

            register(Motherlode.id(id.getNamespace(), Registry.BLOCK.getId(entry.getKey()).getPath() + "_slab"), entry.getValue());
        }
        for(Map.Entry<Identifier, Block> entry: CARVED.entrySet()) {

            register(entry.getKey(), entry.getValue());
        }
    }

    private static void register(Identifier id, Block block) {

        Registry.register(Registry.BLOCK, id, block);
        Registry.register(Registry.ITEM, id, new BlockItem(block, new Item.Settings().group(MotherlodeUncategorized.BLOCKS)));
    }

    @Override
    public void accept(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder, Identifier identifier) {

        // TODO
    }

    public String getId() {

        return ID;
    }

    public Block getStairs(Block block) {
        return STAIRS.get(block);
    }

    public Block getSlab(Block block) {
        return SLABS.get(block);
    }

    public Block getCarved(char variant) {
        return variant < 'a' || variant > 'a' + CARVED.size() ? null : CARVED.get(Motherlode.id(MotherlodeBuildingBlocksMod.MODID, ID + "_carved_" + variant));
    }

    private static Block get() {

        return new Block(BLOCK_SETTINGS);
    }

    static {
        IGNORE.add("stone_bricks_stairs");
    }
}