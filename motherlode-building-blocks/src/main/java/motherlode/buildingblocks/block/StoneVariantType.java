package motherlode.buildingblocks.block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;
import motherlode.base.CommonAssets;
import motherlode.base.CommonData;
import motherlode.base.Motherlode;
import motherlode.base.api.AssetProcessor;
import motherlode.base.api.DataProcessor;
import motherlode.base.api.RegisterableVariantType;
import motherlode.buildingblocks.MotherlodeModule;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;

public class StoneVariantType implements RegisterableVariantType<Block>, AssetProcessor, DataProcessor {
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
    public final List<Pair<Identifier, Block>> ALL = new ArrayList<>();

    private final boolean newBase;
    private final boolean newBricks;
    private final boolean newPolished;

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
        this.newBase = newStoneType && baseBlock == null;
        COBBLE = newStoneType ? get() : null;
        RUBBLE = rubble ? get() : null;
        POLISHED = polished ? polishedBlock != null ? polishedBlock : get() : null;
        this.newPolished = polished && polishedBlock == null;
        BRICKS = bricksBlock == null ? get() : bricksBlock;
        this.newBricks = bricksBlock == null;
        BRICKS_SMALL = get();
        HERRINGBONE = get();
        TILES = get();
        TILES_SMALL = get();

        ALL.add(new Pair<>(Motherlode.id(MotherlodeModule.MODID, id), BASE));
        ALL.add(new Pair<>(Motherlode.id(MotherlodeModule.MODID, "polished_" + id), POLISHED));
        ALL.add(new Pair<>(Motherlode.id(MotherlodeModule.MODID, id + "_bricks"), BRICKS));
        if (newStoneType) ALL.add(new Pair<>(Motherlode.id(MotherlodeModule.MODID, id + "_cobble"), COBBLE));
        if (rubble) ALL.add(new Pair<>(Motherlode.id(MotherlodeModule.MODID, id + "_rubble"), RUBBLE));
        ALL.add(new Pair<>(Motherlode.id(MotherlodeModule.MODID, id + "_bricks_small"), BRICKS_SMALL));
        ALL.add(new Pair<>(Motherlode.id(MotherlodeModule.MODID, id + "_herringbone"), HERRINGBONE));
        ALL.add(new Pair<>(Motherlode.id(MotherlodeModule.MODID, id + "_tiles"), TILES));
        ALL.add(new Pair<>(Motherlode.id(MotherlodeModule.MODID, id + "_tiles_small"), TILES_SMALL));

        List<Pair<Identifier, Block>> temp = new ArrayList<>();

        for (Pair<Identifier, Block> entry : ALL) {
            String stairsId = entry.getLeft().getPath() + "_stairs";
            if (!IGNORE.contains(stairsId)) {
                StairsBlock stairBlock = new DefaultStairsBlock(BASE.getDefaultState(), BLOCK_SETTINGS);
                STAIRS.put(entry.getRight(), stairBlock);
                temp.add(new Pair<>(Motherlode.id(entry.getLeft().getNamespace(), stairsId), stairBlock));
            }
        }

        PILLAR = pillar ? new PillarBlock(BLOCK_SETTINGS) : null;
        if (pillar) {
            ALL.add(new Pair<>(Motherlode.id(MotherlodeModule.MODID, id + "_pillar"), PILLAR));
        }

        for (Pair<Identifier, Block> entry : ALL) {
            String slabId = entry.getLeft().getPath() + "_slab";
            if (!IGNORE.contains(slabId)) {
                SlabBlock slabBlock = new SlabBlock(BLOCK_SETTINGS);
                SLABS.put(entry.getRight(), slabBlock);
                temp.add(new Pair<>(Motherlode.id(entry.getLeft().getNamespace(), slabId), slabBlock));
            }
        }

        Map<Identifier, Block> carved = new HashMap<>();
        for (char variant = 'a'; variant < 'z'; variant++) {
            String carvedId = id + "_carved_" + variant;
            if (getClass().getResourceAsStream("assets/" + MotherlodeModule.MODID + "/textures/block/" + carvedId + ".png") == null)
                break;
            Pair<Identifier, Block> pair = new Pair<>(Motherlode.id(MotherlodeModule.MODID, carvedId), get());
            carved.put(pair.getLeft(), pair.getRight());
            ALL.add(pair);
        }
        CARVED = carved;
        ALL.addAll(temp);
        temp.clear();
    }

    @Override
    public Block[] variants() {
        return ALL.stream().map(Pair::getRight).collect(Collectors.toList()).toArray(new Block[ALL.size()]);
    }

    @Override
    public void register(Identifier id) {
        for (Pair<Identifier, Block> entry : ALL) {
            if (entry.getRight() == null) continue;
            if (entry.getRight() == BASE && !newBase) continue;
            if (entry.getRight() == BRICKS && !newBricks) continue;
            if (entry.getRight() == POLISHED && !newPolished) continue;

            register(entry.getLeft(), entry.getRight());
        }
    }

    private static void register(Identifier id, Block block) {
        Registry.register(Registry.BLOCK, id, block);
        Registry.register(Registry.ITEM, id, new BlockItem(block, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
    }

    private static final AssetProcessor block = CommonAssets.DEFAULT_BLOCK_STATE.andThen(CommonAssets.DEFAULT_BLOCK_MODEL);
    private static final AssetProcessor stair = CommonAssets.STAIR.andThen(CommonAssets.BLOCK_ITEM);
    private static final AssetProcessor slab = CommonAssets.SLAB.andThen(CommonAssets.BLOCK_ITEM);

    @Override
    public void accept(ArtificeResourcePack.ClientResourcePackBuilder pack, Identifier identifier) {
        for (Pair<Identifier, Block> entry : ALL) {
            if (STAIRS.containsValue(entry.getRight()) || SLABS.containsValue(entry.getRight())) continue;

            CommonAssets.BLOCK_ITEM.accept(pack, entry.getLeft());

            if (PILLAR != entry.getRight()) {

                block.accept(pack, entry.getLeft());
            } else {
                CommonAssets.PILLAR.accept(pack, entry.getLeft());
            }
        }

        for (Map.Entry<Block, Block> entry : STAIRS.entrySet()) {
            stair.accept(pack, Registry.BLOCK.getId(entry.getValue()));
        }
        for (Map.Entry<Block, Block> entry : SLABS.entrySet()) {
            slab.accept(pack, Registry.BLOCK.getId(entry.getValue()));
        }
    }

    @Override
    public void accept(ArtificeResourcePack.ServerResourcePackBuilder pack, Identifier identifier) {
        for (Pair<Identifier, Block> entry : ALL) {
            if (entry.getRight() == null) continue;
            if (entry.getRight() == BASE && !newBase) continue;
            if (entry.getRight() == BRICKS && !newBricks) continue;
            if (entry.getRight() == POLISHED && !newPolished) continue;

            CommonData.DEFAULT_BLOCK_LOOT_TABLE.accept(pack, entry.getLeft());
        }
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
        return variant < 'a' || variant > 'a' + CARVED.size() ? null : CARVED.get(Motherlode.id(MotherlodeModule.MODID, ID + "_carved_" + variant));
    }

    private static Block get() {
        return new Block(BLOCK_SETTINGS);
    }

    static {
        IGNORE.add("stone_bricks_stairs");
    }
}
