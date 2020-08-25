package motherlode.buildingblocks;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import motherlode.base.CommonArtificeProcessors;
import motherlode.base.Motherlode;
import motherlode.base.api.ArtificeProcessor;
import motherlode.base.api.RegisterableVariantType;
import motherlode.uncategorized.block.DefaultStairsBlock;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

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

    private final List<Pair<Identifier, Block>> TO_REGISTER = new ArrayList<>();
    public final List<Pair<Identifier, Block>> ALL = new ArrayList<>();

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

        ALL.add(new Pair<>(Motherlode.id(MotherlodeBuildingBlocksMod.MODID, id), BASE));
        if (polished) ALL.add(new Pair<>(Motherlode.id(MotherlodeBuildingBlocksMod.MODID,  "polished_" + id), POLISHED));
        ALL.add(new Pair<>(Motherlode.id(MotherlodeBuildingBlocksMod.MODID, id + "_bricks"), BRICKS));
        if (newStoneType) TO_REGISTER.add(new Pair<>(Motherlode.id(MotherlodeBuildingBlocksMod.MODID, id + "_cobble"), COBBLE));
        if (rubble) TO_REGISTER.add(new Pair<>(Motherlode.id(MotherlodeBuildingBlocksMod.MODID, id + "_rubble"), RUBBLE));
        TO_REGISTER.add(new Pair<>(Motherlode.id(MotherlodeBuildingBlocksMod.MODID, id + "_bricks_small"), BRICKS_SMALL));
        TO_REGISTER.add(new Pair<>(Motherlode.id(MotherlodeBuildingBlocksMod.MODID, id + "_herringbone"), HERRINGBONE));
        TO_REGISTER.add(new Pair<>(Motherlode.id(MotherlodeBuildingBlocksMod.MODID, id + "_tiles"), TILES));
        TO_REGISTER.add(new Pair<>(Motherlode.id(MotherlodeBuildingBlocksMod.MODID, id + "_tiles_small"), TILES_SMALL));

        ALL.addAll(TO_REGISTER);

        if(baseBlock == null && newStoneType) TO_REGISTER.add(new Pair<>(Motherlode.id(MotherlodeBuildingBlocksMod.MODID, id), BASE));
        if(polishedBlock == null && polished) TO_REGISTER.add(new Pair<>(Motherlode.id(MotherlodeBuildingBlocksMod.MODID,  "polished_" + id), POLISHED));
        if(bricksBlock == null) TO_REGISTER.add(new Pair<>(Motherlode.id(MotherlodeBuildingBlocksMod.MODID,  id + "_bricks"), BRICKS));

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
            ALL.add(new Pair<>(Motherlode.id(MotherlodeBuildingBlocksMod.MODID, id + "_pillar"), PILLAR));
            TO_REGISTER.add(new Pair<>(Motherlode.id(MotherlodeBuildingBlocksMod.MODID, id + "_pillar"), PILLAR));
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
            if (getClass().getResourceAsStream("assets/" + MotherlodeBuildingBlocksMod.MODID + "/textures/block/" + carvedId + ".png") == null)
                break;
            Pair<Identifier, Block> pair = new Pair<>(Motherlode.id(MotherlodeBuildingBlocksMod.MODID, carvedId), get());
            carved.put(pair.getLeft(), pair.getRight());
            ALL.add(pair);
            TO_REGISTER.add(pair);
        }
        CARVED = carved;
        ALL.addAll(temp);
        TO_REGISTER.addAll(temp);
        temp.clear();
    }

    @Override
    public Block[] variants() {
        return ALL.stream().map(Pair::getRight).collect(Collectors.toList()).toArray(new Block[ALL.size()]);
    }

    @Override
    public void register(Identifier id) {

        for(Pair<Identifier, Block> entry: TO_REGISTER) {

            if(entry.getRight() == null) continue;

            register(entry.getLeft(), entry.getRight());
        }
    }

    private static void register(Identifier id, Block block) {

        Registry.register(Registry.BLOCK, id, block);
        Registry.register(Registry.ITEM, id, new BlockItem(block, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
    }

    @Override
    public void accept(ArtificeResourcePack.ClientResourcePackBuilder pack, Identifier identifier) {

        for(Pair<Identifier, Block> entry: ALL) {

            if(STAIRS.containsValue(entry.getRight()) || SLABS.containsValue(entry.getRight())) continue;

            CommonArtificeProcessors.BLOCK_ITEM.accept(pack, entry.getLeft());

            if(PILLAR != entry.getRight()) {

                CommonArtificeProcessors.FULL_BLOCK.accept(pack, entry.getLeft());
            }
            else {

                CommonArtificeProcessors.PILLAR.accept(pack, entry.getLeft());
            }
        }
        BiConsumer<ArtificeResourcePack.ClientResourcePackBuilder, Identifier> stair = CommonArtificeProcessors.STAIR.andThen(CommonArtificeProcessors.BLOCK_ITEM);
        BiConsumer<ArtificeResourcePack.ClientResourcePackBuilder, Identifier> slab = CommonArtificeProcessors.SLAB.andThen(CommonArtificeProcessors.BLOCK_ITEM);

        for(Map.Entry<Block, Block> entry: STAIRS.entrySet()) {

            stair.accept(pack, Registry.BLOCK.getId(entry.getValue()));
        }
        for(Map.Entry<Block, Block> entry: SLABS.entrySet()) {

            slab.accept(pack, Registry.BLOCK.getId(entry.getValue()));
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
        return variant < 'a' || variant > 'a' + CARVED.size() ? null : CARVED.get(Motherlode.id(MotherlodeBuildingBlocksMod.MODID, ID + "_carved_" + variant));
    }

    private static Block get() {

        return new Block(BLOCK_SETTINGS);
    }

    static {
        IGNORE.add("stone_bricks_stairs");
    }
}