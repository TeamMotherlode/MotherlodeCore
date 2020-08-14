package motherlode.uncategorized.registry;

import motherlode.uncategorized.Motherlode;
import motherlode.uncategorized.mixins.StructureFeatureAccessor;
import motherlode.uncategorized.world.feature.structure.CampGenerator;
import motherlode.uncategorized.world.feature.structure.CampStructureFeature;
import motherlode.uncategorized.world.feature.structure.RuinedCampsData;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class MotherlodeStructures {

    public static final StructureFeature<StructurePoolFeatureConfig> CAMP = register("camp", GenerationStep.Feature.SURFACE_STRUCTURES, new CampStructureFeature());
    public static final StructurePieceType CAMP_PIECE = register("camp_piece", CampGenerator.Piece::new);

    public static void init() {
        RuinedCampsData.init();
    }

    static StructureFeature register(String name, GenerationStep.Feature genStep, StructureFeature entry) {
        StructureFeature.STRUCTURES.put(name, entry);
        StructureFeatureAccessor.getStructureGenStepMap().put(entry, genStep);
        return Registry.register(Registry.STRUCTURE_FEATURE, Motherlode.id(name), entry);
    }
    static StructurePieceType register(String name, StructurePieceType entry) {
        return Registry.register(Registry.STRUCTURE_PIECE, Motherlode.id(name), entry);
    }
}
