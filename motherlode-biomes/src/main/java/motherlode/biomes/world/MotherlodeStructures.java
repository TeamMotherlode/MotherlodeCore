package motherlode.biomes.world;

import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;
import motherlode.base.Motherlode;
import motherlode.biomes.MotherlodeModule;
import motherlode.biomes.world.feature.structure.CampGenerator;
import motherlode.biomes.world.feature.structure.CampStructureFeature;
import motherlode.biomes.world.feature.structure.RuinedCampsData;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;

public class MotherlodeStructures {

    public static final StructureFeature<StructurePoolFeatureConfig> CAMP = new CampStructureFeature(StructurePoolFeatureConfig.CODEC);
    public static final ConfiguredStructureFeature<StructurePoolFeatureConfig, ? extends StructureFeature<StructurePoolFeatureConfig>> CONFIGURED_CAMP = register("camp", CAMP.configure(new StructurePoolFeatureConfig(() -> RuinedCampsData.POOL, 5)));
    public static final StructurePieceType CAMP_PIECE = register("camp_piece", CampGenerator.CampPiece::new);

    public static void init() {

        FabricStructureBuilder.create(Motherlode.id(MotherlodeModule.MODID, "camp"), CAMP)
            .step(GenerationStep.Feature.SURFACE_STRUCTURES)
            .defaultConfig(8, 4, 12345)
            .superflatFeature(CONFIGURED_CAMP)
            .adjustsSurface()
            .register();
    }

    private static StructurePieceType register(String name, StructurePieceType entry) {
        return Registry.register(Registry.STRUCTURE_PIECE, Motherlode.id(MotherlodeModule.MODID, name), entry);
    }

    private static <FC extends FeatureConfig, F extends StructureFeature<FC>> ConfiguredStructureFeature<FC, F> register(String name, ConfiguredStructureFeature<FC, F> entry) {
        return Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, Motherlode.id(MotherlodeModule.MODID, name), entry);
    }
}
