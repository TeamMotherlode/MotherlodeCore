package motherlode.base.api;

import java.util.function.Predicate;
import net.minecraft.block.Blocks;
import net.minecraft.structure.rule.AlwaysTrueRuleTest;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;

@SuppressWarnings("deprecation")
public enum OreTargets implements OreTarget {
    OVERWORLD(
        BiomeSelectors.foundInOverworld(),
        GenerationStep.Feature.UNDERGROUND_ORES,
        OreFeatureConfig.Rules.BASE_STONE_OVERWORLD),
    NETHER(
        BiomeSelectors.foundInTheNether(),
        GenerationStep.Feature.UNDERGROUND_DECORATION,
        OreFeatureConfig.Rules.BASE_STONE_NETHER),
    THE_END(
        BiomeSelectors.foundInTheEnd(),
        GenerationStep.Feature.UNDERGROUND_ORES,
        new BlockMatchRuleTest(Blocks.END_STONE)),
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
