package motherlode.enderinvasion.impl;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.gen.SimpleRandom;
import motherlode.enderinvasion.EnderInvasionEvents;
import motherlode.enderinvasion.MotherlodeEnderInvasionBlocks;
import motherlode.enderinvasion.MotherlodeEnderInvasionTags;
import motherlode.enderinvasion.util.EnderInvasionHelper;

public class OldEnderInvasionImpl implements EnderInvasionGenerator {
    public static final double NOISE_THRESHOLD = 0.75;
    public static final double NOISE_SCALE = 0.002;
    public static final double END_CAP_NOISE_THRESHOLD = 0.75;
    public static final double END_FOAM_NOISE_THRESHOLD = 0.9;
    public static final double DECORATION_NOISE_SCALE = 0.05;

    private static SimplexNoiseSampler NOISE_GENERATOR;
    private static long NOISE_GENERATOR_SEED;

    @Override
    public boolean isInEnderInvasion(ServerWorld world, BlockPos pos, double purificationProgress) {
        return getNoise(world.getSeed(), pos, NOISE_SCALE) >= getPostEnderDragonNoiseThreshold(NOISE_THRESHOLD, purificationProgress);
    }

    @Override
    public boolean isChunkUnaffected(ServerWorld world, WorldChunk chunk) {
        EnderInvasionHelper.WrappedBoolean unaffected = new EnderInvasionHelper.WrappedBoolean(true);

        EnderInvasionHelper.forEachBlock(chunk, (c, pos) -> {
            if (isInEnderInvasion(world, pos, 0))
                unaffected.set(false);
        });

        return unaffected.get();
    }

    @Override
    public boolean convertChunk(ServerWorld world, WorldChunk chunk) {
        EnderInvasionHelper.WrappedBoolean unaffected = new EnderInvasionHelper.WrappedBoolean(true);

        EnderInvasionHelper.forEachBlock(chunk, (c, pos) -> {
            if (isInEnderInvasion(world, pos, 0)) {
                unaffected.set(false);

                EnderInvasionEvents.CONVERT_BLOCK.invoker().convertBlock(world, c, pos);

                this.generateDecoration(world, chunk, pos, getNoise(world.getSeed(), pos, DECORATION_NOISE_SCALE));
            }
        });

        EnderInvasionEvents.AFTER_CHUNK_CONVERSION.invoker().afterConversion(world, chunk);

        return unaffected.get();
    }

    @Override
    public boolean purifyChunk(ServerWorld world, WorldChunk chunk, double purificationProgress) {
        EnderInvasionHelper.WrappedBoolean unaffected = new EnderInvasionHelper.WrappedBoolean(true);

        EnderInvasionHelper.forEachBlock(chunk, (c, pos) -> {
            if (!isInEnderInvasion(world, pos, purificationProgress)) {
                unaffected.set(false);

                EnderInvasionEvents.PURIFY_BLOCK.invoker().convertBlock(world, c, pos);
            }
        });

        return unaffected.get();
    }

    @Override
    public void generateDecoration(ServerWorld world, WorldChunk chunk) {
        // Empty
    }

    public void generateDecoration(ServerWorld world, WorldChunk chunk, BlockPos pos, double noise) {
        if (!chunk.getBlockState(pos).isAir() && !chunk.getBlockState(pos).isOf(MotherlodeEnderInvasionBlocks.CORRUPTED_GRASS))
            return;

        if (noise >= END_FOAM_NOISE_THRESHOLD && checkEndFoam(chunk, pos))
            chunk.setBlockState(pos, MotherlodeEnderInvasionBlocks.END_FOAM.getDefaultState(), false);

        if (world.getLightLevel(pos) < 15 && noise >= END_CAP_NOISE_THRESHOLD && chunk.getBlockState(pos.down()).isOf(MotherlodeEnderInvasionBlocks.CORRUPTED_DIRT)) {
            chunk.setBlockState(pos, MotherlodeEnderInvasionBlocks.END_CAP.getDefaultState(), false);
        }
    }

    public boolean checkEndFoam(WorldChunk chunk, BlockPos pos) {
        boolean ground = false;
        for (int i = 3; i > 0; i--) {

            BlockPos blockPos = pos.down(i);
            if (chunk.getBlockState(blockPos).isOf(MotherlodeEnderInvasionBlocks.CORRUPTED_DIRT)) {
                ground = true;
                continue;
            }
            if (chunk.getBlockState(blockPos).isOf(MotherlodeEnderInvasionBlocks.END_FOAM)) continue;

            if (ground) {
                if (chunk.getBlockState(blockPos).isAir() || MotherlodeEnderInvasionTags.END_FOAM_REPLACEABLE.contains(MotherlodeEnderInvasionBlocks.END_FOAM))
                    chunk.setBlockState(blockPos, MotherlodeEnderInvasionBlocks.END_FOAM.getDefaultState(), false);
                else return false;
            }
        }
        return ground;
    }

    private static SimplexNoiseSampler getNoiseSampler(long seed) {
        if (NOISE_GENERATOR == null || NOISE_GENERATOR_SEED != seed) {

            NOISE_GENERATOR = new SimplexNoiseSampler(new SimpleRandom(seed));
            NOISE_GENERATOR_SEED = seed;
        }
        return NOISE_GENERATOR;
    }

    public static double getNoise(long seed, Vec3i pos, double scale) {
        return getNoiseSampler(seed).sample(pos.getX() * scale, pos.getY() * scale, pos.getZ() * scale);
    }

    public static double getPostEnderDragonNoiseThreshold(double normalThreshold, double progress) {
        return (progress - progress * normalThreshold) + normalThreshold;
    }
}
