package motherlode.biomes.world;

import com.mojang.blaze3d.platform.GlStateManager;
import motherlode.base.Motherlode;
import motherlode.biomes.MotherlodeBiomesBlocks;
import motherlode.biomes.MotherlodeBiomesMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.CountNoiseDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilders;
import java.awt.*;
import java.util.Random;

public class RuinedFlatsBiome {

    private static final SimplexNoiseSampler samplerA = new SimplexNoiseSampler(new Random(8086));
    private static final SimplexNoiseSampler samplerB = new SimplexNoiseSampler(new Random(2024));
    private static final SimplexNoiseSampler samplerC = new SimplexNoiseSampler(new Random(1492));

    public static Biome create() {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addBatsAndMonsters(builder);
        GenerationSettings.Builder builder2 = new GenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);

        builder2.feature(GenerationStep.Feature.LAKES, MotherlodeBiomeFeatures.MARSH.configure(new DefaultFeatureConfig()).decorate(Decorator.CHANCE.configure(new ChanceDecoratorConfig(5))));
        builder2.feature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(MotherlodeBiomesBlocks.SPROUTS.getDefaultState()), SimpleBlockPlacer.INSTANCE).tries(96).build()).decorate(Decorator.NOISE_HEIGHTMAP_DOUBLE.configure(new CountNoiseDecoratorConfig(-0.8D, 5, 10))));
        builder2.structureFeature(MotherlodeStructures.CAMP.configure(new StructurePoolFeatureConfig(Motherlode.id(MotherlodeBiomesMod.MODID, "camps/ruined/start"), 5)));

        DefaultBiomeFeatures.addLandCarvers(builder2);
        DefaultBiomeFeatures.addMineables(builder2);
        DefaultBiomeFeatures.addDefaultOres(builder2);
        DefaultBiomeFeatures.addDefaultDisks(builder2);
        DefaultBiomeFeatures.addDefaultMushrooms(builder2);
        DefaultBiomeFeatures.addSprings(builder2);
        DefaultBiomeFeatures.addFrozenTopLayer(builder2);
        DefaultBiomeFeatures.addForestGrass(builder2);
        DefaultBiomeFeatures.addMossyRocks(builder2);

        return new Biome.Builder()
            .precipitation(Biome.Precipitation.RAIN)
            .category(Biome.Category.NONE)
            .depth(0.125F)
            .scale(0.02F)
            .temperature(0.8F)
            .downfall(0.4F)
            .effects(
                new BiomeEffects.Builder()
                    .waterColor(0x003b4d)
                    .waterFogColor(0x002230)
                    .fogColor(0x8294ad)
                    .moodSound(BiomeMoodSound.CAVE)
                    .build()
            )
            .spawnSettings(builder.build()).generationSettings(builder2.build()).build();
    }

    public int getGrassColorAt(double x, double z) {
        int red = (int)(((samplerA.sample(x/36, z/36)+1.0)/2)*22)+70;
        int green = (int)(((samplerB.sample(x/12, z/12)+1.0)/2)*30)+130;
        int blue = (int)(((samplerC.sample(x/30, z/30)+1.0)/2)*20)+40;
        Color c = new Color(red, green, blue);
        return c.getRGB();
    }

    public int getSkyColor() {
        return 0x8294ad;
    }
    public float getFogDensity(double posX, double posZ) {
        return 0.024f;
    }
    public GlStateManager.FogMode getFogMode() {
        return GlStateManager.FogMode.EXP2;
    }
}
