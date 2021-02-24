package motherlode.materials.world;

import java.util.function.Predicate;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import motherlode.base.api.worldgen.OreTarget;

@SuppressWarnings("deprecation")
public enum GemTargets implements OreTarget {
    /**
     * Generates in deserts.
     */
    RUBY(
        BiomeSelectors.categories(Biome.Category.DESERT),
        GenerationStep.Feature.UNDERGROUND_ORES,
        OreFeatureConfig.Rules.BASE_STONE_OVERWORLD),

    /**
     * Generates in oceans.
     */
    SAPPHIRE(
        BiomeSelectors.categories(Biome.Category.OCEAN),
        GenerationStep.Feature.UNDERGROUND_ORES,
        OreFeatureConfig.Rules.BASE_STONE_OVERWORLD),

    /**
     * Generates in forests.
     */
    TOPAZ(
        BiomeSelectors.categories(Biome.Category.FOREST),
        GenerationStep.Feature.UNDERGROUND_ORES,
        OreFeatureConfig.Rules.BASE_STONE_OVERWORLD);

    private final Predicate<BiomeSelectionContext> biomeSelector;
    private final GenerationStep.Feature feature;
    private final RuleTest ruleTest;

    GemTargets(Predicate<BiomeSelectionContext> biomeSelector, GenerationStep.Feature feature, RuleTest ruleTest) {
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
