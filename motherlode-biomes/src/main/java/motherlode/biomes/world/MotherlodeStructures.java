package motherlode.biomes.world;

import motherlode.base.Motherlode;
import motherlode.biomes.MotherlodeBiomesMod;
import motherlode.biomes.mixin.StructureFeatureAccessor;
import motherlode.biomes.world.feature.structure.CampGenerator;
import motherlode.biomes.world.feature.structure.CampStructureFeature;
import motherlode.biomes.world.feature.structure.RuinedCampsData;
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

    static StructureFeature<StructurePoolFeatureConfig> register(String name, GenerationStep.Feature genStep, StructureFeature<StructurePoolFeatureConfig> entry) {
        StructureFeature.STRUCTURES.put(name, entry);
        StructureFeatureAccessor.getStructureGenStepMap().put(entry, genStep);
        return Registry.register(Registry.STRUCTURE_FEATURE, Motherlode.id(MotherlodeBiomesMod.MODID, name), entry);
    }
    static StructurePieceType register(String name, StructurePieceType entry) {
        return Registry.register(Registry.STRUCTURE_PIECE, Motherlode.id(MotherlodeBiomesMod.MODID, name), entry);
    }
}
