package motherlode.base.impl;

import java.util.function.Predicate;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.world.gen.GenerationStep;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import motherlode.base.api.worldgen.OreTarget;

@SuppressWarnings("deprecation")
public class OreTargetImpl implements OreTarget {
    private final Predicate<BiomeSelectionContext> biomeSelector;
    private final GenerationStep.Feature generationStep;
    private final RuleTest ruleTest;

    public OreTargetImpl(Predicate<BiomeSelectionContext> biomeSelector, GenerationStep.Feature generationStep, RuleTest ruleTest) {
        this.biomeSelector = biomeSelector;
        this.generationStep = generationStep;
        this.ruleTest = ruleTest;
    }

    @Override
    public Predicate<BiomeSelectionContext> getBiomeSelector() {
        return this.biomeSelector;
    }

    @Override
    public GenerationStep.Feature getGenerationStepFeature() {
        return this.generationStep;
    }

    @Override
    public RuleTest getRuleTest() {
        return this.ruleTest;
    }
}
