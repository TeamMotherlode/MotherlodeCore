package motherlode.base.api.impl.worldgen;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import motherlode.base.Motherlode;
import motherlode.base.api.worldgen.FeatureManager;
import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;

@SuppressWarnings("deprecation")
public class MotherlodeFeatureImpl implements FeatureManager {
    public static final MotherlodeFeatureImpl INSTANCE = new MotherlodeFeatureImpl();

    private List<Pair<Pair<Predicate<BiomeSelectionContext>, GenerationStep.Feature>, RegistryKey<ConfiguredFeature<?, ?>>>> FEATURES = new ArrayList<>();

    @Override
    public void addFeature(Predicate<BiomeSelectionContext> biomeSelector, GenerationStep.Feature generationStep, RegistryKey<ConfiguredFeature<?, ?>> feature) {
        FEATURES.add(new Pair<>(new Pair<>(biomeSelector, generationStep), feature));
    }

    public List<Pair<Pair<Predicate<BiomeSelectionContext>, GenerationStep.Feature>, RegistryKey<ConfiguredFeature<?, ?>>>> getFeatures() {
        return FEATURES;
    }

    public void addFeatures() {
        BiomeModification modification = BiomeModifications.create(Motherlode.id("motherlode_features"));

        for (Pair<Pair<Predicate<BiomeSelectionContext>, GenerationStep.Feature>, RegistryKey<ConfiguredFeature<?, ?>>> pair : getFeatures()) {
            modification.add(ModificationPhase.ADDITIONS, pair.getLeft().getLeft(), context ->
                context.getGenerationSettings().addFeature(pair.getLeft().getRight(), pair.getRight()));
        }
        FEATURES = null;
    }
}
