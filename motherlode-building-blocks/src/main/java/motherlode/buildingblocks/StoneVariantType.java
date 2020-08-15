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
    private final boolean NEW_STONE_TYPE;
    private final boolean NEW_BRICKS;
    private final boolean NEW_POLISHED;
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

    public final Map<Identifier, Block> ALL = new HashMap<>();

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
        NEW_STONE_TYPE = newStoneType;
        BASE = baseBlock != null ? baseBlock : newStoneType ? get() : Registry.BLOCK.get(new Identifier(id));
        COBBLE = newStoneType ? get() : null;
        RUBBLE = rubble ? get() : null;
        POLISHED = polished ? polishedBlock != null ? polishedBlock : get() : null;
        NEW_POLISHED = polished? polishedBlock != null : false;
        BRICKS = bricksBlock == null ? get() : bricksBlock;
        NEW_BRICKS = bricksBlock != null;
        BRICKS_SMALL = get();
        HERRINGBONE = get();
        TILES = get();
        TILES_SMALL = get();

        ALL.put(Motherlode.id(MotherlodeBuildingBlocksMod.MODID, id), BASE);
        if (newStoneType) ALL.put(Motherlode.id(MotherlodeBuildingBlocksMod.MODID, id + "_cobble"), COBBLE);
        if (rubble) ALL.put(Motherlode.id(MotherlodeBuildingBlocksMod.MODID, id + "_rubble"), RUBBLE);
        if (polished) ALL.put(Motherlode.id(MotherlodeBuildingBlocksMod.MODID,  "polished_" + id), POLISHED);
        ALL.put(Motherlode.id(MotherlodeBuildingBlocksMod.MODID, id + "_bricks"), BRICKS);
        ALL.put(Motherlode.id(MotherlodeBuildingBlocksMod.MODID, id + "_bricks_small"), BRICKS_SMALL);
        ALL.put(Motherlode.id(MotherlodeBuildingBlocksMod.MODID, id + "_herringbone"), HERRINGBONE);
        ALL.put(Motherlode.id(MotherlodeBuildingBlocksMod.MODID, id + "_tiles"), TILES);
        ALL.put(Motherlode.id(MotherlodeBuildingBlocksMod.MODID, id + "_tiles_small"), TILES_SMALL);

        Map<Identifier, Block> temp = new HashMap<>();

        for (Map.Entry<Identifier, Block> entry : ALL.entrySet()) {
            String stairsId = entry.getKey().getPath() + "_stairs";
            if (!IGNORE.contains(stairsId)) {
                StairsBlock stairBlock = new DefaultStairsBlock(BASE.getDefaultState(), BLOCK_SETTINGS);
                STAIRS.put(entry.getValue(), stairBlock);
                temp.put(Motherlode.id(entry.getKey().getNamespace(), stairsId), stairBlock);
                MotherlodeBlocks.usesStairModel.put(stairBlock, !entry.getKey().getNamespace().equals("minecraft"));
                MotherlodeBlocks.defaultItemModelList.add(stairBlock);
            }
        }

        PILLAR = pillar ? new PillarBlock(BLOCK_SETTINGS) : null;
        if (pillar) {
            MotherlodeBlocks.usesPillarModel.add(PILLAR);
            MotherlodeBlocks.defaultItemModelList.add(PILLAR);
            ALL.put(Motherlode.id(MotherlodeBuildingBlocksMod.MODID, id + "_pillar"), PILLAR);
        }

        for (Map.Entry<Identifier, Block> entry : ALL.entrySet()) {
            String slabId = entry.getKey().getPath() + "_slab";
            if (!IGNORE.contains(slabId)) {
                SlabBlock slabBlock = new SlabBlock(BLOCK_SETTINGS);
                SLABS.put(entry.getValue(), slabBlock);
                temp.put(Motherlode.id(entry.getKey().getNamespace(), slabId), slabBlock);
                MotherlodeBlocks.usesSlabModel.put(slabBlock, !entry.getKey().getNamespace().equals("minecraft"));
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
        ALL.putAll(temp);
        temp.clear();
        ALL.putAll(carved);
    }

    @Override
    public Block[] variants() {
        return ALL.values().toArray(new Block[ALL.size()]);
    }

    @Override
    public void register(Identifier id) {

        for(Map.Entry<Identifier, Block> entry: ALL.entrySet()) {

            if(entry.getValue() == BASE && !NEW_STONE_TYPE) continue;
            if(entry.getValue() == BRICKS && !NEW_BRICKS) continue;
            if(entry.getValue() == POLISHED && !NEW_POLISHED) continue;

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