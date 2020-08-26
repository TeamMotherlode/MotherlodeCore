package motherlode.biomes.world.feature.structure;

import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class CampStructureFeature extends StructureFeature<StructurePoolFeatureConfig> {

    public CampStructureFeature() {
        super(StructurePoolFeatureConfig.CODEC);
    }

    @Override
    public StructureStartFactory<StructurePoolFeatureConfig> getStructureStartFactory() {
        return Start::new;
    }

    @Override
    protected boolean shouldStartAt(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long l, ChunkRandom chunkRandom, int i, int j, Biome biome, ChunkPos chunkPos, StructurePoolFeatureConfig featureConfig) {
        return chunkRandom.nextInt(230) == 22;
    }

    @Override
    public String getName() {
        return "camp";
    }

    public static class Start extends StructureStart<StructurePoolFeatureConfig> {
        public Start(StructureFeature<StructurePoolFeatureConfig> feature, int chunkX, int chunkZ, BlockBox box, int references, long seed) {
            super(feature, chunkX, chunkZ, box, references, seed);
        }
        @Override
        public void init(ChunkGenerator chunkGenerator, StructureManager structureManager, int x, int z, Biome biome, StructurePoolFeatureConfig featureConfig) {
            CampGenerator.addPieces(chunkGenerator, structureManager, featureConfig.getStartPool(), new BlockPos(x*16, chunkGenerator.getHeight(x*16, z*16, Heightmap.Type.WORLD_SURFACE), z*16), featureConfig.size, random, children);
            setBoundingBoxFromChildren();
        }
    }
}
