package motherlode.enderinvasion.impl;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import net.minecraft.world.biome.source.SeedMixer;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.gen.SimpleRandom;
import motherlode.enderinvasion.EnderInvasionEvents;
import motherlode.enderinvasion.util.EnderInvasionHelper;

public class EnderInvasionImpl implements EnderInvasionGenerator {
    private static final double PLACEMENT_NOISE_SCALE = 0.0001;
    private static final double PLACEMENT_NOISE_THRESHOLD = 0.5;

    private static final double HEIGHT_OFFSET_NOISE_SCALE = 0.0015;
    private static final double HEIGHT_OFFSET_NOISE_FACTOR = 32;

    private static final double SPREAD_NOISE_SCALE = 0.00075;
    private static final double SPREAD_NOISE_FACTOR = 48;

    private SimplexNoiseSampler placementNoise;
    private SimplexNoiseSampler heightOffsetNoise;
    private SimplexNoiseSampler spreadNoise;

    private long lastSeed;

    public EnderInvasionImpl() {
        this.lastSeed = -1;
        this.updateNoiseSamplers(0); // lastSeed = 0
    }

    @Override
    public boolean isInEnderInvasion(ServerWorld world, BlockPos pos, double purificationProgress) {
        updateNoiseSamplers(world.getSeed());

        if (!this.isInEnderInvasion(pos.getX(), pos.getZ(), 1 - purificationProgress)) return false;

        int baseline = this.getBaseline(world.getSeaLevel(), pos.getX(), pos.getZ());
        int spread = this.getSpread(pos.getX(), pos.getZ(), 1 - purificationProgress);

        return (pos.getY() > baseline - spread) && (pos.getY() < baseline + spread);
    }

    @Override
    public boolean isChunkUnaffected(ServerWorld world, WorldChunk chunk) {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (this.isInEnderInvasion(chunk.getPos().getStartX() + i, chunk.getPos().getStartZ() + j, 1))
                    return false;
            }
        }

        return true;
    }

    @Override
    public boolean convertChunk(ServerWorld world, WorldChunk chunk) {
        EnderInvasionHelper.WrappedBoolean unaffected = new EnderInvasionHelper.WrappedBoolean(true);

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                int x = chunk.getPos().getStartX() + i;
                int z = chunk.getPos().getStartZ() + j;

                if (!this.isInEnderInvasion(x, z, 1)) continue;
                unaffected.set(false);

                int baseline = this.getBaseline(world.getSeaLevel(), x, z);
                int spread = this.getSpread(x, z, 1);

                for (int y = baseline - spread + 1; y < baseline + spread; y++) {
                    if (y < world.getBottomY() || y > world.getBottomY() + world.getLogicalHeight()) continue;

                    EnderInvasionEvents.CONVERT_BLOCK.invoker().convertBlock(world, chunk, new BlockPos(x, y, z));
                }
            }
        }

        EnderInvasionEvents.AFTER_CHUNK_CONVERSION.invoker().afterConversion(world, chunk);

        return unaffected.get();
    }

    @Override
    public boolean purifyChunk(ServerWorld world, WorldChunk chunk, double purificationProgress) {
        EnderInvasionHelper.WrappedBoolean unaffected = new EnderInvasionHelper.WrappedBoolean(true);

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                int x = chunk.getPos().getStartX() + i;
                int z = chunk.getPos().getStartZ() + j;

                if (!this.isInEnderInvasion(x, z, 1)) continue;
                unaffected.set(false);

                int baseline = this.getBaseline(world.getSeaLevel(), x, z);
                int spread = this.getSpread(x, z, 1);
                int purifiedSpread = this.getSpread(x, z, 1 - purificationProgress);

                for (int y = baseline - spread + 1; y < baseline - purifiedSpread; y++) {
                    if (y < world.getBottomY() || y > world.getBottomY() + world.getLogicalHeight()) continue;

                    EnderInvasionEvents.PURIFY_BLOCK.invoker().convertBlock(world, chunk, new BlockPos(x, y, z));
                }

                for (int y = baseline + purifiedSpread + 1; y < baseline + spread; y++) {
                    if (y < world.getBottomY() || y > world.getBottomY() + world.getLogicalHeight()) continue;

                    EnderInvasionEvents.PURIFY_BLOCK.invoker().convertBlock(world, chunk, new BlockPos(x, y, z));
                }
            }
        }

        return unaffected.get();
    }

    @Override
    public void generateDecoration(ServerWorld world, WorldChunk chunk) {
        // TODO
    }

    private int getSpread(int x, int z, double purificationProgress) {
        return Math.abs((int) (this.spreadNoise.sample(x * SPREAD_NOISE_SCALE, z * SPREAD_NOISE_SCALE) * purificationProgress * SPREAD_NOISE_FACTOR));
    }

    private int getBaseline(int seaLevel, int x, int z) {
        double noise = this.heightOffsetNoise.sample(x * HEIGHT_OFFSET_NOISE_SCALE, z * HEIGHT_OFFSET_NOISE_SCALE);
        int offset = (int) (noise * HEIGHT_OFFSET_NOISE_FACTOR);

        return seaLevel + offset;
    }

    private boolean isInEnderInvasion(double x, double y, double purificationProgress) {
        return getPlacementNoise(x, y, purificationProgress) >= PLACEMENT_NOISE_THRESHOLD;
    }

    private double getPlacementNoise(double x, double y, double purificationProgress) {
        return this.placementNoise.sample(x * PLACEMENT_NOISE_SCALE, y * PLACEMENT_NOISE_SCALE) * purificationProgress;
    }

    private void updateNoiseSamplers(long seed) {
        if (this.lastSeed == seed) return;
        this.lastSeed = seed;

        this.placementNoise = new SimplexNoiseSampler(new SimpleRandom(SeedMixer.mixSeed(seed, 10L)));
        this.heightOffsetNoise = new SimplexNoiseSampler(new SimpleRandom(SeedMixer.mixSeed(seed, 20L)));
        this.spreadNoise = new SimplexNoiseSampler(new SimpleRandom(SeedMixer.mixSeed(seed, 30L)));
    }
}
