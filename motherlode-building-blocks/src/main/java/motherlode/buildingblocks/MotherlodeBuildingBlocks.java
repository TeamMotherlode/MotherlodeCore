package motherlode.buildingblocks;

import motherlode.base.Motherlode;
import motherlode.base.api.ArtificeProcessor;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import java.util.ArrayList;
import java.util.List;

public class MotherlodeBuildingBlocks {

    static final List<Pair<Identifier, ArtificeProcessor>> ARTIFICE_PROCESSORS = new ArrayList<>();

    public static final StoneVariantType LIMESTONE = register(StoneVariantType.newStone("limestone",false));
    public static final StoneVariantType GRAVESTONE = register(StoneVariantType.newStone("gravestone",true));
    public static final StoneVariantType JASPER =  register(StoneVariantType.newStone("jasper",false));
    public static final StoneVariantType MARBLE =  register(StoneVariantType.newStone("marble",false));
    public static final StoneVariantType SLATE =  register(StoneVariantType.newStone("slate",false));

    public static final StoneVariantType BRICK =  register(StoneVariantType.fromBlock("brick", Blocks.BRICKS));
    public static final StoneVariantType MAGMA =  register(StoneVariantType.fromBlock("magma", Blocks.MAGMA_BLOCK));
    public static final StoneVariantType OBSIDIAN =  register(StoneVariantType.fromBlock("obsidian", null));
    public static final StoneVariantType CRYING_OBSIDIAN =  register(StoneVariantType.fromBlock("crying_obsidian", null));
    public static final StoneVariantType GOLD =  register(StoneVariantType.fromBlock("gold", Blocks.GOLD_BLOCK));
    public static final StoneVariantType ICE =  register(StoneVariantType.fromBlock("ice", null));

    public static final StoneVariantType STONE =  register(StoneVariantType.fromStone("stone", Blocks.STONE_BRICKS, Blocks.SMOOTH_STONE));
    public static final StoneVariantType GRANITE =  register(StoneVariantType.fromStone("granite", null, Blocks.POLISHED_GRANITE));
    public static final StoneVariantType DIORITE =  register(StoneVariantType.fromStone("diorite", null, Blocks.POLISHED_DIORITE));
    public static final StoneVariantType ANDESITE =  register(StoneVariantType.fromStone("andesite", null, Blocks.POLISHED_ANDESITE));
    public static final StoneVariantType BLACKSTONE =  register(StoneVariantType.fromStone("blackstone", null, Blocks.POLISHED_BLACKSTONE));
    public static final StoneVariantType BASALT =  register(StoneVariantType.fromStone("basalt", null, Blocks.POLISHED_BASALT));
    public static final StoneVariantType SANDSTONE =  register(StoneVariantType.fromStone("sandstone", null, Blocks.SMOOTH_SANDSTONE));

    public static StoneVariantType register(StoneVariantType stone) {

        Identifier id = Motherlode.id(MotherlodeBuildingBlocksMod.MODID, stone.getId());
        stone.register(id);
        ARTIFICE_PROCESSORS.add(new Pair<>(id, stone));
        return stone;
    }
    public static void init() {

        // Called to load the class
    }
}
