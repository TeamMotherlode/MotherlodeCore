package motherlode.biomes.world;

import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilders;

public class RuinedEdgeBiome {
    public static Biome create() {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addBatsAndMonsters(builder);
        GenerationSettings.Builder builder2 = new GenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);

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
          .scale(0.01F)
          .temperature(0.8F)
          .downfall(0.4F)
          .effects(
            new BiomeEffects.Builder()
              .grassColor(0x73bd53)
              .waterColor(0x003b4d)
              .waterFogColor(0x002230)
              .fogColor(12638463)
              .skyColor(0x8294ad)
              .moodSound(BiomeMoodSound.CAVE)
              .build())
          .spawnSettings(builder.build())
          .generationSettings(builder2.build())
          .build();
    }
}
