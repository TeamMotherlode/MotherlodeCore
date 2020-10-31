package motherlode.biomes.world;

import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CountNoiseDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;
import motherlode.base.Motherlode;
import motherlode.biomes.MotherlodeBiomesBlocks;
import motherlode.biomes.MotherlodeModule;
import motherlode.biomes.world.feature.MarshFeature;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;

/**
 * @author Indigo Amann
 */
public class MotherlodeBiomeFeatures {

    public static final Feature<DefaultFeatureConfig> MARSH = register("marsh", new MarshFeature(DefaultFeatureConfig.CODEC));
    public static final RegistryKey<ConfiguredFeature<?, ?>> CONFIGURED_SPROUTS = register("sprouts", Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(MotherlodeBiomesBlocks.SPROUTS.getDefaultState()), SimpleBlockPlacer.INSTANCE).tries(96).build()).decorate(Decorator.COUNT_NOISE.configure(new CountNoiseDecoratorConfig(-0.8D, 5, 10))));

    @SuppressWarnings("deprecation")
    public static void register() {
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.VEGETAL_DECORATION, CONFIGURED_SPROUTS);
    }

    public static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return Registry.register(Registry.FEATURE, Motherlode.id(MotherlodeModule.MODID, name), feature);
    }

    public static <FC extends FeatureConfig, F extends Feature<FC>> RegistryKey<ConfiguredFeature<?, ?>> register(String name, ConfiguredFeature<FC, F> feature) {
        RegistryKey<ConfiguredFeature<?, ?>> key = RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN, Motherlode.id(MotherlodeModule.MODID, name));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, key.getValue(), feature);
        return key;
    }

    public static <C extends SurfaceConfig, F extends SurfaceBuilder<C>> F register(String name, F surfaceBuilder) {
        return Registry.register(Registry.SURFACE_BUILDER, Motherlode.id(MotherlodeModule.MODID, name), surfaceBuilder);
    }
}
