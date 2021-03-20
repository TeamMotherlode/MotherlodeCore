package motherlode.biomes.world.feature.structure;

import java.util.List;
import java.util.Random;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.SimpleStructurePiece;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import motherlode.biomes.MotherlodeModule;
import motherlode.biomes.world.MotherlodeStructures;

public class CampGenerator {
    private static final Identifier CAMP_PIECE = MotherlodeModule.id("camp_piece");

    public static void addPieces(ServerWorld world, BlockPos pos, BlockRotation rotation, List<StructurePiece> pieces) {
        pieces.add(new CampPiece(world, pos, CAMP_PIECE, rotation));
    }

    public static class CampPiece extends SimpleStructurePiece {
        private final BlockRotation rotation;
        private final Identifier template;

        public CampPiece(ServerWorld world, NbtCompound compoundTag) {
            super(MotherlodeStructures.CAMP_PIECE, compoundTag);
            this.template = new Identifier(compoundTag.getString("Template"));
            this.rotation = BlockRotation.valueOf(compoundTag.getString("Rot"));
            this.initializeStructureData(world);
        }

        public CampPiece(ServerWorld world, BlockPos pos, Identifier template, BlockRotation rotation) {
            super(MotherlodeStructures.CAMP_PIECE, 0);
            this.pos = pos;
            this.rotation = rotation;
            this.template = template;

            this.initializeStructureData(world);
        }

        private void initializeStructureData(ServerWorld world) {
            Structure structure = world.getStructureManager().getStructureOrBlank(this.template);
            StructurePlacementData placementData = (new StructurePlacementData())
                .setRotation(this.rotation)
                .setMirror(BlockMirror.NONE)
                .addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
            this.setStructureData(structure, this.pos, placementData);
        }

        protected void writeNbt(ServerWorld world, NbtCompound tag) {
            super.writeNbt(world, tag);
            tag.putString("Template", this.template.toString());
            tag.putString("Rot", this.rotation.name());
        }

        @Override
        protected void handleMetadata(String metadata, BlockPos pos, ServerWorldAccess serverWorldAccess, Random random,
                                      BlockBox boundingBox) {
        }
    }
}
