package motherlode.core.enderinvasion;

import motherlode.core.Motherlode;
import motherlode.core.registry.MotherlodeBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.dimension.DimensionType;

import java.util.Random;

public class EnderInvasionUtil {

    private static final double NOISE_THRESHOLD = 0.75;
    private static final double NOISE_SCALE = 0.002;
    private static final double END_CAP_NOISE_THRESHOLD = 0.75;
    private static final double END_FOAM_NOISE_THRESHOLD = 0.9;
    private static final double DECORATION_NOISE_SCALE = 0.05;

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

                    if (SimplexNoise.noise(pos.getX() * NOISE_SCALE, pos.getY() * NOISE_SCALE, pos.getZ() * NOISE_SCALE) < NOISE_THRESHOLD)
                        continue;

                    BlockState state = chunk.getBlockState(pos);
                    SpreadRecipe recipe = SpreadingBlocksManager.SPREAD.getRecipe(state.getBlock());
                    if (recipe != null) {

                        chunk.setBlockState(pos, recipe.convert(state), false);
                    }
                    generateDecoration(world, chunk, pos);
                }
            }
        }

        Motherlode.ENDER_INVASION_CHUNK_STATE.get(chunk).setValue(EnderInvasionChunkState.GENERATION_DONE);
    }

    public static void generateDecoration(ServerWorld world, WorldChunk chunk, BlockPos pos) {

        if (!chunk.getBlockState(pos).isAir() && !chunk.getBlockState(pos).isOf(MotherlodeBlocks.CORRUPTED_GRASS)) return;
        double noise = SimplexNoise.noise(pos.getX() * DECORATION_NOISE_SCALE, pos.getY() * DECORATION_NOISE_SCALE, pos.getZ() * DECORATION_NOISE_SCALE);

        if (noise >= END_FOAM_NOISE_THRESHOLD && checkEndFoam(chunk, pos))
            chunk.setBlockState(pos, MotherlodeBlocks.END_FOAM.getDefaultState(), false);

        if (world.getLightLevel(pos) < 15 && noise >= END_CAP_NOISE_THRESHOLD && chunk.getBlockState(pos.down()).isOf(MotherlodeBlocks.CORRUPTED_DIRT)) {
            chunk.setBlockState(pos, MotherlodeBlocks.END_CAP.getDefaultState(), false);
        }
    }

    public static boolean checkEndFoam(WorldChunk chunk, BlockPos pos) {

        boolean ground = false;
        for (int i = 3; i > 0; i--) {

            BlockPos blockPos = pos.down(i);
            if (chunk.getBlockState(blockPos).isOf(MotherlodeBlocks.CORRUPTED_DIRT)) {
                ground = true;
                continue;
            }
            if (chunk.getBlockState(blockPos).isOf(MotherlodeBlocks.END_FOAM)) continue;

            if (ground) {
                if (chunk.getBlockState(blockPos).isAir() || chunk.getBlockState(pos.down(i)).isOf(MotherlodeBlocks.CORRUPTED_GRASS))
                    chunk.setBlockState(blockPos, MotherlodeBlocks.END_FOAM.getDefaultState(), false);
                else return false;
            }
        }
        return ground;
    }
}