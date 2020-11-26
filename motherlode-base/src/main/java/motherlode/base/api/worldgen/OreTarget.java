package motherlode.base.api.worldgen;

import java.util.function.Predicate;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.world.gen.GenerationStep;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import motherlode.base.api.impl.OreTargetImpl;

/**
 * An interface used for selecting the biomes that ores generate in, which blocks they can replace and the {@link GenerationStep.Feature} to use.
 * There are implementations for Minecraft's dimensions in {@link FeatureTargets}.
 */
@SuppressWarnings("deprecation")
public interface OreTarget extends FeatureTarget {
    /**
     * Returns a {@link Predicate} to test which biomes an ore can generate in.
     * The return value of this method should not change.
     *
     * @return The biome selector for this ore target
     */
    @Override
    Predicate<BiomeSelectionContext> getBiomeSelector();

    /**
     * Returns the {@link GenerationStep.Feature} that determines in which generation step the {@link net.minecraft.world.gen.feature.ConfiguredFeature} of the ore will be generated in the world.
     * The return value of this method should not change.
     *
     * @return The generation step for this ore target
     */
    @Override
    GenerationStep.Feature getGenerationStepFeature();

    /**
     * Returns a {@link RuleTest} that determines which blocks an ore can replace while generating.
     * The return value of this method should not change.
     *
     * @return The {@code RuleTest} for this ore target
     */
    RuleTest getRuleTest();

    /**
     * Returns a {@code FeatureTarget} that will return the passed arguments in its implementation.
     *
     * @param biomeSelector  The biome selector to return by {@link #getBiomeSelector()}.
     * @param generationStep The {@link GenerationStep.Feature} to return by {@link #getGenerationStepFeature()}.
     * @param ruleTest       A {@link RuleTest} to return by {@link #getRuleTest()}.
     * @return A {@code FeatureTarget} using the passed biome selector, generation step and rule test.
     */
    static OreTarget of(Predicate<BiomeSelectionContext> biomeSelector, GenerationStep.Feature generationStep, RuleTest ruleTest) {
        return new OreTargetImpl(biomeSelector, generationStep, ruleTest);
    }
}
