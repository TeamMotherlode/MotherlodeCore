package motherlode.base.api;

import java.util.function.Predicate;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.world.gen.GenerationStep;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;

@SuppressWarnings("deprecation")
public interface OreTarget {
    Predicate<BiomeSelectionContext> getBiomeSelector();
    GenerationStep.Feature getGenerationStepFeature();
    RuleTest getRuleTest();
}
