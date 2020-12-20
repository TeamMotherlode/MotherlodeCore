package motherlode.base.impl;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import motherlode.base.Motherlode;
import motherlode.base.MotherlodeBase;
import motherlode.base.api.worldgen.FeatureTarget;
import motherlode.base.api.worldgen.FeaturesManager;

@SuppressWarnings("deprecation")
public class FeaturesManagerImpl implements FeaturesManager {
    public static final FeaturesManagerImpl INSTANCE = new FeaturesManagerImpl();

    private List<Pair<FeatureTarget, RegistryKey<ConfiguredFeature<?, ?>>>> FEATURES = new ArrayList<>();

    @Override
    public void addFeature(FeatureTarget target, RegistryKey<ConfiguredFeature<?, ?>> configuredFeature) {
        FEATURES.add(new Pair<>(target, configuredFeature));
    }

    @Override
    public RegistryKey<ConfiguredFeature<?, ?>> registerConfiguredFeatureKey(Identifier id, ConfiguredFeature<?, ?> configuredFeature) {
        RegistryKey<ConfiguredFeature<?, ?>> key = RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN, id);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, key.getValue(), configuredFeature);
        return key;
    }

    @Override
    public RegistryKey<ConfiguredStructureFeature<?, ?>> registerConfiguredStructureFeatureKey(Identifier id, ConfiguredStructureFeature<?, ?> configuredStructureFeature) {
        RegistryKey<ConfiguredStructureFeature<?, ?>> key = RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_WORLDGEN, id);
        Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, key.getValue(), configuredStructureFeature);
        return key;
    }

    public List<Pair<FeatureTarget, RegistryKey<ConfiguredFeature<?, ?>>>> getFeatures() {
        return FEATURES;
    }

    public void addFeatures() {
        BiomeModification modification = BiomeModifications.create(Motherlode.id(MotherlodeBase.MODID, "motherlode_features"));

        for (Pair<FeatureTarget, RegistryKey<ConfiguredFeature<?, ?>>> pair : getFeatures()) {
            modification.add(ModificationPhase.ADDITIONS, pair.getLeft().getBiomeSelector(), context ->
                context.getGenerationSettings().addFeature(pair.getLeft().getGenerationStepFeature(), pair.getRight()));
        }
        FEATURES = null;
    }
}
