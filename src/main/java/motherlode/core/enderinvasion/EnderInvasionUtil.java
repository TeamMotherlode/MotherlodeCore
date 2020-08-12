package motherlode.core.enderinvasion;

import motherlode.core.Motherlode;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.dimension.DimensionType;

import java.util.Random;

public class EnderInvasionUtil {

    public static void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {

        // TODO add functionality (block spread)
    }

    public static void convertChunk(ServerWorld world, WorldChunk chunk) {

        if (!(world.getDimension() == DimensionType.getOverworldDimensionType())) return;
        if (!(Motherlode.ENDER_INVASION_STATE.get(world.getLevelProperties()).value() == EnderInvasionState.ENDER_INVASION))
            return;
        if (!(Motherlode.ENDER_INVASION_CHUNK_STATE.get(chunk).value() == EnderInvasionChunkState.PRE_ECHERITE)) return;

        BlockPos blockPos;
        BlockState state;
        SpreadRecipe recipe;

        int maxY = chunk.getHighestNonEmptySectionYOffset() * 16;

        for (int x = 0; x < 16; x++) {

            for (int y = 0; y < maxY; y++) {

                for (int z = 0; z < 16; z++) {

                    blockPos = new BlockPos(chunk.getPos().x * 16 + x, y, chunk.getPos().z * 16 + z);
                    state = chunk.getBlockState(blockPos);
                    recipe = SpreadingBlocksManager.SPREAD.getRecipe(state.getBlock());
                    if (recipe == null) continue;
                    chunk.setBlockState(blockPos, recipe.convert(state), false);
                }
            }
        }
        Motherlode.ENDER_INVASION_CHUNK_STATE.get(chunk).setValue(EnderInvasionChunkState.GENERATION_DONE);
    }
}