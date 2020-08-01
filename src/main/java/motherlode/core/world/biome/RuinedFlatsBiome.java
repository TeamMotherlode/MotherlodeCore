package motherlode.core.world.biome;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.platform.GlStateManager;
import motherlode.core.Motherlode;
import motherlode.core.registry.MotherlodeBlocks;
import motherlode.core.registry.MotherlodeFeatures;
import motherlode.core.registry.MotherlodeStructures;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;

import java.awt.*;
import java.util.Random;

public class RuinedFlatsBiome extends AbstractFoggyBiome {

    private static final SimplexNoiseSampler samplerA = new SimplexNoiseSampler(new Random(8086));
    private static final SimplexNoiseSampler samplerB = new SimplexNoiseSampler(new Random(2024));
    private static final SimplexNoiseSampler samplerC = new SimplexNoiseSampler(new Random(1492));

    public RuinedFlatsBiome() {
        super(
                new Settings()
                        .configureSurfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)
                        .precipitation(Precipitation.RAIN)
                        .category(Category.NONE)
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
                        .parent(null)
        );
        this.addFeature(GenerationStep.Feature.LAKES, MotherlodeFeatures.MARSH.configure(new DefaultFeatureConfig()).createDecoratedFeature(Decorator.CHANCE_HEIGHTMAP_DOUBLE.configure(new ChanceDecoratorConfig(5))));
        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(MotherlodeBlocks.SPROUTS.getDefaultState()), SimpleBlockPlacer.field_24871).tries(96).build()).createDecoratedFeature(Decorator.NOISE_HEIGHTMAP_DOUBLE.configure(new NoiseHeightmapDecoratorConfig(-0.8D, 5, 10))));
        this.addStructureFeature(MotherlodeStructures.CAMP.configure(new StructurePoolFeatureConfig(Motherlode.id("camps/ruined/start"), 5)));
        DefaultBiomeFeatures.addLandCarvers(this);
        DefaultBiomeFeatures.addMineables(this);
        DefaultBiomeFeatures.addDefaultOres(this);
        DefaultBiomeFeatures.addDefaultDisks(this);
        DefaultBiomeFeatures.addDefaultMushrooms(this);
        DefaultBiomeFeatures.addSprings(this);
        DefaultBiomeFeatures.addFrozenTopLayer(this);
        DefaultBiomeFeatures.addForestGrass(this);
        DefaultBiomeFeatures.addMossyRocks(this);
        this.addSpawn(SpawnGroup.AMBIENT, new SpawnEntry(EntityType.BAT, 10, 8, 8));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.SPIDER, 100, 4, 4));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.ZOMBIE, 95, 4, 4));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.SKELETON, 100, 4, 4));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.CREEPER, 100, 4, 4));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.SLIME, 100, 4, 4));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.ENDERMAN, 10, 1, 4));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.WITCH, 10, 1, 1));
    }

    @Override
    public int getGrassColorAt(double x, double z) {
        int red = (int)(((samplerA.sample(x/36, z/36)+1.0)/2)*22)+70;
        int green = (int)(((samplerB.sample(x/12, z/12)+1.0)/2)*30)+130;
        int blue = (int)(((samplerC.sample(x/30, z/30)+1.0)/2)*20)+40;
        Color c = new Color(red, green, blue);
        return c.getRGB();
    }

    @Override
    public int getSkyColor() {
        return 0x8294ad;
    }


    @Override
    public float getFogDensity(double posX, double posZ) {
        return 0.024f;
    }

    @Override
    public GlStateManager.FogMode getFogMode() {
        return GlStateManager.FogMode.EXP2;
    }
}
