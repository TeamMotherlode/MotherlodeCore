package motherlode.base.api.worldgen;

import java.util.function.Predicate;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.world.gen.GenerationStep;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;

/**
 * Interface used for selecting the biomes that ores generate in, which blocks they can replace and the {@link GenerationStep.Feature} to use.
 * There are implementations for Minecraft's dimensions in {@link OreTargets};
 */
@SuppressWarnings("deprecation")
public interface OreTarget {
    /**
     * Returns a {@link Predicate} to test which biomes an ore can generate in.
     * The return value of this method should not change.
     *
     * @return The biome selector for this ore target
     */
    Predicate<BiomeSelectionContext> getBiomeSelector();

    /**
     * Returns the {@link GenerationStep.Feature} that determines in which generation step the {@link net.minecraft.world.gen.feature.ConfiguredFeature} of the ore will be generated in the world.
     * The return value of this method should not change.
     *
     * @return The generation step for this ore target
     */
    GenerationStep.Feature getGenerationStepFeature();

    /**
     * Returns a {@link RuleTest} that determines which blocks an ore can replace while generating.
     * The return value of this method should not change.
     *
     * @return The {@code RuleTest} for this ore target
     */
    RuleTest getRuleTest();
}
