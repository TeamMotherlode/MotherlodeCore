package motherlode.uncategorized.registry;

import motherlode.base.Motherlode;
import motherlode.uncategorized.world.feature.MarshFeature;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.NoiseHeightmapDecoratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;

/**
 * @author Indigo Amann
 */
public class MotherlodeFeatures {

    public static final Feature<DefaultFeatureConfig> MARSH = register("marsh", new MarshFeature());

    public static void register() {
        Registry.BIOME.forEach(MotherlodeFeatures::addToBiome);
        RegistryEntryAddedCallback.event(Registry.BIOME).register((i, identifier, biome) -> addToBiome(biome));
    }

    public static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return Registry.register(Registry.FEATURE, Motherlode.id(name), feature);
    }

    public static <C extends SurfaceConfig, F extends SurfaceBuilder<C>> F register(String name, F surfaceBuilder) {
        return Registry.register(Registry.SURFACE_BUILDER, Motherlode.id(name), surfaceBuilder);
    }
    public static void addToBiome(Biome biome) {
        Category category = biome.getCategory();
        if (category == Category.PLAINS || category == Category.FOREST || category == Category.SAVANNA || category == Category.SWAMP || category == Category.TAIGA || category == Category.EXTREME_HILLS || category == Category.JUNGLE) {
            biome.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(MotherlodeBlocks.SPROUTS.getDefaultState()), SimpleBlockPlacer.field_24871).tries(96).build()).createDecoratedFeature(Decorator.NOISE_HEIGHTMAP_DOUBLE.configure(new NoiseHeightmapDecoratorConfig(-0.8D, 5, 10))));
        }
    }
}
