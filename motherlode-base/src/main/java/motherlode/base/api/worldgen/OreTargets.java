package motherlode.base.api.worldgen;

import java.util.function.Predicate;
import net.minecraft.block.Blocks;
import net.minecraft.structure.rule.AlwaysTrueRuleTest;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;

/**
 * Provides implementations of {@link OreTarget} for Minecraft's dimensions.
 */
@SuppressWarnings("deprecation")
public enum OreTargets implements OreTarget {
    /**
     * Ores using this can generate in all overworld biomes and can replace all blocks that are in the {@code minecraft:base_stone_overworld} block tag.
     */
    OVERWORLD(
        BiomeSelectors.foundInOverworld(),
        GenerationStep.Feature.UNDERGROUND_ORES,
        OreFeatureConfig.Rules.BASE_STONE_OVERWORLD),
    /**
     * Ores using this can generate in all Nether biomes and can replace all blocks that are in the {@code minecraft:base_stone_nether} block tag.
     */
    NETHER(
        BiomeSelectors.foundInTheNether(),
        GenerationStep.Feature.UNDERGROUND_DECORATION,
        OreFeatureConfig.Rules.BASE_STONE_NETHER),
    /**
     * Ores using this can generate in all biomes found in The End and can replace End Stone.
     */
    THE_END(
        BiomeSelectors.foundInTheEnd(),
        GenerationStep.Feature.UNDERGROUND_ORES,
        new BlockMatchRuleTest(Blocks.END_STONE)),
    /**
     * Ores using this can generate in all biomes and can replace all blocks.
     * This is useful for testing that an ore can generate at all, but should not be used in production.
     */
    DEBUG(
        BiomeSelectors.all(),
        GenerationStep.Feature.UNDERGROUND_ORES,
        AlwaysTrueRuleTest.INSTANCE
    );

    private final Predicate<BiomeSelectionContext> biomeSelector;
    private final GenerationStep.Feature feature;
    private final RuleTest ruleTest;

    OreTargets(Predicate<BiomeSelectionContext> biomeSelector, GenerationStep.Feature feature, RuleTest ruleTest) {
        this.biomeSelector = biomeSelector;
        this.feature = feature;
        this.ruleTest = ruleTest;
    }

    @Override
    public Predicate<BiomeSelectionContext> getBiomeSelector() {
        return this.biomeSelector;
    }

    @Override
    public GenerationStep.Feature getGenerationStepFeature() {
        return this.feature;
    }

    @Override
    public RuleTest getRuleTest() {
        return this.ruleTest;
    }
}
