package motherlode.base.api.worldgen;

import java.util.function.Predicate;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import motherlode.base.api.impl.FeatureTargetImpl;

/**
 * An interface used for selecting the biomes that a {@link ConfiguredFeature} generates in and which {@link GenerationStep.Feature} to use.
 * There are implementations for Minecraft's dimensions in {@link FeatureTargets}.
 */
@SuppressWarnings("deprecation")
public interface FeatureTarget {
    /**
     * Returns a {@link Predicate} to test which biomes a {@link ConfiguredFeature} can generate in.
     * The return value of this method should not change.
     *
     * @return The biome selector for this feature target
     */
    Predicate<BiomeSelectionContext> getBiomeSelector();

    /**
     * Returns the {@link GenerationStep.Feature} that determines in which generation step the {@link ConfiguredFeature} will be generated in the world.
     * The return value of this method should not change.
     *
     * @return The generation step for this feature target
     */
    GenerationStep.Feature getGenerationStepFeature();

    /**
     * Returns a {@code FeatureTarget} that will return the passed arguments in its implementation.
     *
     * @param biomeSelector  The biome selector to return by {@link #getBiomeSelector()}.
     * @param generationStep The {@link GenerationStep.Feature} to return by {@link #getGenerationStepFeature()}.
     * @return A {@code FeatureTarget} using the passed biome selector and generation step.
     */
    static FeatureTarget of(Predicate<BiomeSelectionContext> biomeSelector, GenerationStep.Feature generationStep) {
        return new FeatureTargetImpl(biomeSelector, generationStep);
    }
}
