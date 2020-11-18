package motherlode.base.api.worldgen;

import java.util.Objects;
import java.util.Random;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import motherlode.base.Motherlode;
import org.jetbrains.annotations.Nullable;

public class DefaultSaplingGenerator extends SaplingGenerator {
    private final ConfiguredFeature<TreeFeatureConfig, ?> configuredFeature;

    public DefaultSaplingGenerator(Identifier id, ConfiguredFeature<TreeFeatureConfig, ?> configuredFeature) {
        Objects.requireNonNull(configuredFeature);

        this.configuredFeature = configuredFeature;
        Motherlode.getFeaturesManager().registerConfiguredFeature(id, configuredFeature);
    }

    @Override
    protected @Nullable ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random random, boolean bl) {
        return this.configuredFeature;
    }
}
