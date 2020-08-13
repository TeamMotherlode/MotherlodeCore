package motherlode.core.enderinvasion;

import motherlode.core.Motherlode;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.dimension.DimensionType;

import java.util.Random;

public class EnderInvasionUtil {

    private static final double NOISE_THRESHOLD = 0.75;
    private static final double NOISE_SCALE = 0.002;

   public static void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {

        // TODO add functionality (block spread)
    }

    public static void convertChunk(ServerWorld world, WorldChunk chunk) {

        if (!(world.getDimension() == DimensionType.getOverworldDimensionType())) return;
        if (!(Motherlode.ENDER_INVASION_STATE.get(world.getLevelProperties()).value() == EnderInvasionState.ENDER_INVASION))
            return;
        if (!(Motherlode.ENDER_INVASION_CHUNK_STATE.get(chunk).value() == EnderInvasionChunkState.PRE_ECHERITE)) return;

        int maxY = chunk.getHighestNonEmptySectionYOffset() * 16;

        for (int x = 0; x < 16; x++) {

            for (int y = 0; y < maxY; y++) {

                for (int z = 0; z < 16; z++) {

                    BlockPos pos = new BlockPos(chunk.getPos().x * 16 + x, y, chunk.getPos().z * 16 + z);

                    if(SimplexNoise.noise(pos.getX() * NOISE_SCALE, pos.getY() * NOISE_SCALE, pos.getZ() * NOISE_SCALE) < NOISE_THRESHOLD) continue;

                    BlockState state = chunk.getBlockState(pos);
                    SpreadRecipe recipe = SpreadingBlocksManager.SPREAD.getRecipe(state.getBlock());
                    if (recipe == null) continue;
                    chunk.setBlockState(pos, recipe.convert(state), false);
                }
            }
        }
        Motherlode.ENDER_INVASION_CHUNK_STATE.get(chunk).setValue(EnderInvasionChunkState.GENERATION_DONE);
    }
}