package motherlode.biomes.world;

import motherlode.base.Motherlode;
import motherlode.biomes.MotherlodeBiomesMod;
import motherlode.biomes.world.feature.MarshFeature;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;

/**
 * @author Indigo Amann
 */
public class MotherlodeBiomeFeatures {

    public static final Feature<DefaultFeatureConfig> MARSH = register("marsh", new MarshFeature(DefaultFeatureConfig.CODEC));

    public static void register() {
        BuiltinRegistries.BIOME.forEach(MotherlodeBiomeFeatures::addToBiome);
        RegistryEntryAddedCallback.event(BuiltinRegistries.BIOME).register((i, identifier, biome) -> addToBiome(biome));
    }

    public static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return Registry.register(Registry.FEATURE, Motherlode.id(MotherlodeBiomesMod.MODID, name), feature);
    }

    public static <C extends SurfaceConfig, F extends SurfaceBuilder<C>> F register(String name, F surfaceBuilder) {
        return Registry.register(Registry.SURFACE_BUILDER, Motherlode.id(MotherlodeBiomesMod.MODID, name), surfaceBuilder);
    }
    public static void addToBiome(Biome biome) {
        /* Category category = biome.getCategory();
        if (category == Category.PLAINS || category == Category.FOREST || category == Category.SAVANNA || category == Category.SWAMP || category == Category.TAIGA || category == Category.EXTREME_HILLS || category == Category.JUNGLE) {
            biome.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(MotherlodeBiomesBlocks.SPROUTS.getDefaultState()), SimpleBlockPlacer.INSTANCE).tries(96).build()).decorate(Decorator.COUNT_NOISE.configure(new CountNoiseDecoratorConfig(-0.8D, 5, 10))));
        } */ // TODO
    }
}
