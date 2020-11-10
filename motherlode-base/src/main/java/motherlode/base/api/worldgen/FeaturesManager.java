package motherlode.base.api.worldgen;

import java.util.function.Function;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import motherlode.base.Motherlode;

/**
 * Using this interface, one can more easily create and register {@link ConfiguredFeature}s, and add them to world generation.
 * An implementation of it can be got by calling {@link Motherlode#getFeaturesManager()}.
 */
public interface FeaturesManager {
    /**
     * Adds a {@link ConfiguredFeature} to all biomes selected by the biome selector.
     *
     * @param target            Determines which in biomes and in which {@code GenerationStep} to generate the feature.
     * @param configuredFeature The {@link RegistryKey} of the {@code ConfiguredFeature} to add.
     */
    void addFeature(FeatureTarget target, RegistryKey<ConfiguredFeature<?, ?>> configuredFeature);

    /**
     * Registers a {@link ConfiguredFeature}.
     *
     * @param id                The {@link Identifier} to register the {@code ConfiguredFeature} under.
     * @param configuredFeature The {@code ConfiguredFeature} to register.
     * @return The {@link RegistryKey} of the registered {@code ConfiguredFeature}.
     */
    RegistryKey<ConfiguredFeature<?, ?>> registerConfiguredFeature(Identifier id, ConfiguredFeature<?, ?> configuredFeature);

    /**
     * Registers a {@link ConfiguredStructureFeature}.
     *
     * @param id                         The {@link Identifier} to register the {@code ConfiguredStructureFeature} under.
     * @param configuredStructureFeature The {@code ConfiguredStructureFeature} to register.
     * @return The {@link RegistryKey} of the registered {@code ConfiguredStructureFeature}.
     */
    RegistryKey<ConfiguredStructureFeature<?, ?>> registerConfiguredStructureFeature(Identifier id, ConfiguredStructureFeature<?, ?> configuredStructureFeature);

    /**
     * Registers the given {@link ConfiguredFeature} and adds it to the world using {@link #addFeature(FeatureTarget, RegistryKey)}.
     *
     * @param id                The {@link Identifier} to register the {@code ConfiguredFeature} under.
     * @param target            Determines which in biomes and in which {@code GenerationStep} to generate the feature.
     * @param configuredFeature The {@code ConfiguredFeature} to add.
     * @return The {@link RegistryKey} for the {@code ConfiguredFeature} created and registered by this method.
     */
    default RegistryKey<ConfiguredFeature<?, ?>> addFeature(Identifier id, FeatureTarget target, ConfiguredFeature<?, ?> configuredFeature) {
        RegistryKey<ConfiguredFeature<?, ?>> key = this.registerConfiguredFeature(id, configuredFeature);

        this.addFeature(target, key);
        return key;
    }

    /**
     * Creates a {@link ConfiguredFeature}, registers it and adds it to the world.
     *
     * @param id         The {@link Identifier} to register the {@code ConfiguredFeature} under.
     * @param target     Determines which in biomes and in which {@code GenerationStep} to generate the feature.
     * @param feature    The {@link Feature} to generate.
     * @param config     The {@code FeatureConfig} to configure the {@code Feature} with.
     * @param decorators This can be used to {@link net.minecraft.world.gen.decorator.Decoratable decorate} the created {@code ConfiguredFeature}.
     * @param <FC>       The type of the {@code FeatureConfig} for the given {@code Feature}.
     * @param <F>        The type of the given {@code Feature}.
     * @return The {@link RegistryKey} for the {@code ConfiguredFeature} created and registered by this method.
     */
    default <FC extends FeatureConfig, F extends Feature<FC>> RegistryKey<ConfiguredFeature<?, ?>> addFeature(Identifier id, FeatureTarget target, F feature, FC config, Function<ConfiguredFeature<FC, ?>, ConfiguredFeature<?, ?>> decorators) {
        return this.addFeature(id, target, decorators.apply(feature.configure(config)));
    }

    /**
     * Creates and registers a {@link ConfiguredFeature} to generate an ore using the specified options.
     *
     * @param id         The {@link Identifier} to register the {@code ConfiguredFeature} under.
     * @param target     The {@link OreTarget} to specify the biomes that the ore will generate in, the blocks that the ore can replace and the {@link net.minecraft.world.gen.GenerationStep.Feature} used.
     * @param state      The ore will generate with this {@link BlockState}.
     * @param veinSize   Each ore vein will have about this amount of ores.
     * @param decorators This can be used to {@link net.minecraft.world.gen.decorator.Decoratable decorate} the created {@code ConfiguredFeature}.
     * @return The {@link RegistryKey} for the {@code ConfiguredFeature} created and registered by this method.
     */
    default RegistryKey<ConfiguredFeature<?, ?>> addOre(Identifier id, OreTarget target, BlockState state, int veinSize, Function<ConfiguredFeature<OreFeatureConfig, ?>, ConfiguredFeature<?, ?>> decorators) {
        return this.addFeature(id, target, Feature.ORE, new OreFeatureConfig(target.getRuleTest(), state, veinSize), decorators);
    }

    /**
     * Creates and registers a {@link ConfiguredFeature} to generate an ore using the specified options.
     *
     * @param id            The {@link Identifier} to register the {@code ConfiguredFeature} under.
     * @param target        The {@link OreTarget} to specify the biomes that the ore will generate in, the blocks that the ore can replace and the {@link GenerationStep.Feature} used.
     * @param state         The ore will generate with this {@link BlockState}.
     * @param veinSize      Each ore vein will have about this amount of ores.
     * @param veinsPerChunk The average amount of ore veins per chunk.
     * @param minY          The minimum Y level that that this ore will generate in.
     * @param maxY          The maximum Y level that that this ore will generate in.
     * @return The {@link RegistryKey} for the {@code ConfiguredFeature} created and registered by this method.
     */
    default RegistryKey<ConfiguredFeature<?, ?>> addOre(Identifier id, OreTarget target, BlockState state, int veinSize, int veinsPerChunk, int minY, int maxY) {
        return this.addOre(id, target, state, veinSize, f -> f.decorate(Decorator.RANGE.configure(
            new RangeDecoratorConfig(minY, 0, maxY - minY))).repeat(veinsPerChunk).spreadHorizontally());
    }

    /**
     * Adds the given {@link MotherlodeOreBlock} to the world.
     *
     * @param id    The {@link Identifier} used for registering the {@link ConfiguredFeature} created for this.
     * @param block The {@code MotherlodeOreBlock} which will be added to world generation.
     * @return The {@link RegistryKey} for the {@code ConfiguredFeature} created and registered by this method.
     */
    default RegistryKey<ConfiguredFeature<?, ?>> addOre(Identifier id, MotherlodeOreBlock block) {
        return this.addOre(id, block.getTarget(), block.getDefaultState(), block.getVeinSize(), block.getDecoratorsFunction());
    }
}
