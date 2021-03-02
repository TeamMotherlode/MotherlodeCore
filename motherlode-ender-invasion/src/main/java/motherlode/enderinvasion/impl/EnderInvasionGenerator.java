package motherlode.enderinvasion.impl;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.WorldChunk;

public interface EnderInvasionGenerator {
    boolean isInEnderInvasion(ServerWorld world, BlockPos pos, double purificationProgress);

    boolean isChunkUnaffected(ServerWorld world, WorldChunk chunk);

    boolean convertChunk(ServerWorld world, WorldChunk chunk);
    boolean purifyChunk(ServerWorld world, WorldChunk chunk, double purificationProgress);

    void generateDecoration(ServerWorld world, WorldChunk chunk);
}
