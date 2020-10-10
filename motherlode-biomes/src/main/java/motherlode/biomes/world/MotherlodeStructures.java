package motherlode.biomes.world;

import motherlode.base.Motherlode;
import motherlode.biomes.MotherlodeBiomesMod;
import motherlode.biomes.world.feature.structure.CampGenerator;
import motherlode.biomes.world.feature.structure.CampStructureFeature;
import motherlode.biomes.world.feature.structure.RuinedCampsData;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class MotherlodeStructures {

    public static final StructureFeature<StructurePoolFeatureConfig> CAMP = new CampStructureFeature(StructurePoolFeatureConfig.CODEC);
    public static final ConfiguredStructureFeature<StructurePoolFeatureConfig, ? extends StructureFeature<StructurePoolFeatureConfig>> CONFIGURED_CAMP = CAMP.configure(new StructurePoolFeatureConfig(() -> RuinedCampsData.POOL, 5));
    public static final StructurePieceType CAMP_PIECE = register("camp_piece", CampGenerator.CampPiece::new);

    public static void init() {

        FabricStructureBuilder.create(Motherlode.id(MotherlodeBiomesMod.MODID, "camp"), CAMP)
          .step(GenerationStep.Feature.SURFACE_STRUCTURES)
          .defaultConfig(32, 8, 12345)
          .superflatFeature(CONFIGURED_CAMP)
          .adjustsSurface()
          .register();
    }

    static StructurePieceType register(String name, StructurePieceType entry) {
        return Registry.register(Registry.STRUCTURE_PIECE, Motherlode.id(MotherlodeBiomesMod.MODID, name), entry);
    }
}
