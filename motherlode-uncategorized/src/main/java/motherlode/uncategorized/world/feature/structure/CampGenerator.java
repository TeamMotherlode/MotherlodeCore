package motherlode.uncategorized.world.feature.structure;

import motherlode.uncategorized.registry.MotherlodeStructures;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import java.util.List;

public class CampGenerator {
    public static void addPieces(ChunkGenerator chunkGenerator, StructureManager structureManager, Identifier startPool, BlockPos pos, int size, ChunkRandom random, List<? super PoolStructurePiece> children) {
        StructurePoolBasedGenerator.addPieces(startPool, size, Piece::new, chunkGenerator, structureManager, pos, children, random, false, false);
    }

    public static class Piece extends PoolStructurePiece {
        public Piece(StructureManager structureManager, StructurePoolElement structurePoolElement, BlockPos blockPos, int i, BlockRotation blockRotation, BlockBox blockBox) {
            super(MotherlodeStructures.CAMP_PIECE, structureManager, structurePoolElement, blockPos, i, blockRotation, blockBox);
        }

        public Piece(StructureManager structureManager, CompoundTag compoundTag) {
            super(structureManager, compoundTag, MotherlodeStructures.CAMP_PIECE);
        }
    }
}
