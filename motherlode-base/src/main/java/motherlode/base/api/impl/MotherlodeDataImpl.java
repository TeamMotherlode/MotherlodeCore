package motherlode.base.api.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import motherlode.base.Motherlode;
import motherlode.base.api.OreTarget;
import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;

public class MotherlodeDataImpl {
    private static List<Pair<OreTarget, RegistryKey<ConfiguredFeature<?, ?>>>> ORES = new ArrayList<>();

    public static void addOre(Identifier id, OreTarget target, BlockState state, int veinSize, int veinsPerChunk, int minY, int maxY) {
        ConfiguredFeature<?, ?> configuredFeature = Feature.ORE.configure(
            new OreFeatureConfig(target.getRuleTest(), state, veinSize)).decorate(Decorator.RANGE.configure(
            new RangeDecoratorConfig(minY, 0, maxY - minY))).repeat(veinsPerChunk).spreadHorizontally();

        RegistryKey<ConfiguredFeature<?, ?>> key = RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN, id);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, id, configuredFeature);

        addOre(target, key);
    }

    public static void addOre(OreTarget target, RegistryKey<ConfiguredFeature<?, ?>> feature) {
        ORES.add(new Pair<>(target, feature));
    }

    public static List<Pair<OreTarget, RegistryKey<ConfiguredFeature<?, ?>>>> getOres() {
        return ORES;
    }

    @SuppressWarnings("deprecation")
    public static void addOreFeatures() {
        List<Pair<OreTarget, List<RegistryKey<ConfiguredFeature<?, ?>>>>> ores = MotherlodeDataImpl.getOres().stream().collect(Collectors.groupingBy(Pair::getLeft)).entrySet().stream()
            .map(entry -> new Pair<>(entry.getKey(), entry.getValue().stream()
                .map(Pair::getRight).collect(Collectors.toList())))
            .collect(Collectors.toList());
        BiomeModification modification = BiomeModifications.create(Motherlode.id("motherlode_ores"));

        for (Pair<OreTarget, List<RegistryKey<ConfiguredFeature<?, ?>>>> pair : ores) {
            modification.add(ModificationPhase.ADDITIONS, pair.getLeft().getBiomeSelector(), context -> {
                for (RegistryKey<ConfiguredFeature<?, ?>> key : pair.getRight()) {
                    context.getGenerationSettings().addFeature(pair.getLeft().getGenerationStepFeature(), key);
                }
            });
        }
        ORES = null;
    }
}
