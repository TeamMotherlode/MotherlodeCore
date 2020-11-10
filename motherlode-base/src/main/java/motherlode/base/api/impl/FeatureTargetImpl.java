package motherlode.base.api.impl;

import java.util.function.Predicate;
import net.minecraft.world.gen.GenerationStep;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import motherlode.base.api.worldgen.FeatureTarget;

@SuppressWarnings("deprecation")
public class FeatureTargetImpl implements FeatureTarget {
    private Predicate<BiomeSelectionContext> biomeSelector;
    private GenerationStep.Feature generationStep;

    public FeatureTargetImpl(Predicate<BiomeSelectionContext> biomeSelector, GenerationStep.Feature generationStep) {
        this.biomeSelector = biomeSelector;
        this.generationStep = generationStep;
    }

    @Override
    public Predicate<BiomeSelectionContext> getBiomeSelector() {
        return this.biomeSelector;
    }

    @Override
    public GenerationStep.Feature getGenerationStepFeature() {
        return this.generationStep;
    }
}
