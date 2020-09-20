package motherlode.biomes.world;

import motherlode.base.Motherlode;
import motherlode.biomes.MotherlodeBiomesMod;
import motherlode.biomes.world.feature.structure.CampGenerator;
import motherlode.biomes.world.feature.structure.CampStructureFeature;
import motherlode.biomes.world.feature.structure.RuinedCampsData;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class MotherlodeStructures {

    public static final StructureFeature<DefaultFeatureConfig> CAMP = register("camp", new CampStructureFeature(DefaultFeatureConfig.CODEC));
    public static final ConfiguredStructureFeature<?, ?> CAMP_CONFIGURED = CAMP.configure(DefaultFeatureConfig.DEFAULT);
    public static final StructurePieceType CAMP_PIECE = register("camp_piece", CampGenerator.CampPiece::new);

    public static void init() {
        RuinedCampsData.init();
    }

    static StructureFeature<DefaultFeatureConfig> register(String name, StructureFeature<DefaultFeatureConfig> entry) {
        return Registry.register(Registry.STRUCTURE_FEATURE, Motherlode.id(MotherlodeBiomesMod.MODID, name), entry);
    }
    static StructurePieceType register(String name, StructurePieceType entry) {
        return Registry.register(Registry.STRUCTURE_PIECE, Motherlode.id(MotherlodeBiomesMod.MODID, name), entry);
    }
}
