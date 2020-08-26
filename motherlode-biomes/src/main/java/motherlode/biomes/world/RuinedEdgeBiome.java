package motherlode.biomes.world;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;

public class RuinedEdgeBiome extends Biome {
    public RuinedEdgeBiome() {
        super(
                new Settings()
                        .configureSurfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)
                        .precipitation(Precipitation.RAIN)
                        .category(Category.NONE)
                        .depth(0.125F)
                        .scale(0.01F)
                        .temperature(0.8F)
                        .downfall(0.4F)
                        .effects(
                                new BiomeEffects.Builder()
                                        .waterColor(0x003b4d)
                                        .waterFogColor(0x002230)
                                        .fogColor(12638463)
                                        .moodSound(BiomeMoodSound.CAVE)
                                        .build()
                        )
                        .parent(null)
        );
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
        return 0x73bd53;
    }
}
