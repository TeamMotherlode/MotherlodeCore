package motherlode.core.enderinvasion;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.*;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.dimension.DimensionType;
import java.util.Objects;
import java.util.Random;

public class EnderInvasionHelper {

    private static SimplexNoiseSampler NOISE_GENERATOR;
    private static long NOISE_GENERATOR_SEED;

    public static void convertChunk(ServerWorld world, WorldChunk chunk) {

        if(!shouldConvertChunk(world, chunk)) return;

        int maxY = chunk.getHighestNonEmptySectionYOffset() * 16;

        for (int x = 0; x < 16; x++) {

            for (int y = 0; y < maxY; y++) {

                for (int z = 0; z < 16; z++) {

                    BlockPos pos = new BlockPos(chunk.getPos().x * 16 + x, y, chunk.getPos().z * 16 + z);
                    double noise = getNoise(world, pos, EnderInvasion.NOISE_SCALE);

                    if (noise < EnderInvasion.NOISE_THRESHOLD)
                        continue;

                    EnderInvasionEvents.CONVERT.invoker().convert(world, chunk, pos, noise, NOISE_GENERATOR);
                }
            }
        }
        EnderInvasionEvents.AFTER_CONVERSION.invoker().afterConversion(world, chunk, NOISE_GENERATOR);

        EnderInvasion.CHUNK_STATE.get(chunk).setValue(EnderInvasionChunkState.GENERATION_DONE);
    }

    public static boolean shouldConvertChunk(ServerWorld world, WorldChunk chunk) {

        if (world.getDimension() != DimensionType.getOverworldDimensionType())
            return false;
        if (EnderInvasion.STATE.get(world.getLevelProperties()).value() != EnderInvasionState.ENDER_INVASION)
            return false;
        return EnderInvasion.CHUNK_STATE.get(chunk).value() == EnderInvasionChunkState.PRE_ECHERITE;
    }

    public static SimplexNoiseSampler getNoiseSampler(ServerWorld world) {

        if (NOISE_GENERATOR == null || NOISE_GENERATOR_SEED != world.getSeed()) {

            NOISE_GENERATOR = new SimplexNoiseSampler(new Random(world.getSeed()));
            NOISE_GENERATOR_SEED = world.getSeed();
        }
        return NOISE_GENERATOR;
    }

    public static double getNoise(ServerWorld world, Vec3i pos, double scale) {

        return getNoiseSampler(world).method_22416(pos.getX() * scale, pos.getY() * scale, pos.getZ() * scale);
    }

    public static void spawnMobGroup(ServerWorld world, Chunk chunk, EntityType<? extends MobEntity> entityType, BlockPos pos) {

        int i = pos.getY();

        BlockState blockState = chunk.getBlockState(pos);
        if (!blockState.isSolidBlock(chunk, pos)) {
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            int j = 0;

            int l = pos.getX();
            int m = pos.getZ();
            int o = MathHelper.ceil(world.random.nextFloat() * 4.0F);
            int p = 0;

            for (int q = 0; q < o; ++q) {
                l += world.random.nextInt(6) - world.random.nextInt(6);
                m += world.random.nextInt(6) - world.random.nextInt(6);
                mutable.set(l, i, m);
                double d = (double) l + 0.5D;
                double e = (double) m + 0.5D;
                PlayerEntity playerEntity = world.getClosestPlayer(d, i, e, -1.0D, false);
                if (playerEntity != null) {
                    double f = playerEntity.squaredDistanceTo(d, i, e);
                    if (isAcceptableSpawnPosition(world, chunk, mutable, f)) {

                        o = 1 + world.random.nextInt(4);

                        if (canSpawn(world, entityType, mutable, f)) {
                            MobEntity mobEntity = createMob(world, entityType);
                            if (mobEntity == null) {
                                return;
                            }

                            mobEntity.refreshPositionAndAngles(d, i, e, world.random.nextFloat() * 360.0F, 0.0F);
                            if (isValidSpawn(world, mobEntity, f)) {
                                mobEntity.initialize(world, world.getLocalDifficulty(mobEntity.getBlockPos()), SpawnReason.NATURAL, null, null);
                                ++j;
                                ++p;
                                world.spawnEntity(mobEntity);
                                if (j >= mobEntity.getLimitPerChunk()) {
                                    return;
                                }

                                if (mobEntity.spawnsTooManyForEachTry(p)) {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Methods needed to spawn the mob group that are private in SpawnHelper
    private static boolean isAcceptableSpawnPosition(ServerWorld world, Chunk chunk, BlockPos.Mutable pos, double squaredDistance) {
        if (squaredDistance <= 576.0D) {
            return false;
        } else if (world.getSpawnPos().isWithinDistance(new Vec3d(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D), 24.0D)) {
            return false;
        } else {
            ChunkPos chunkPos = new ChunkPos(pos);
            return Objects.equals(chunkPos, chunk.getPos()) || world.getChunkManager().shouldTickChunk(chunkPos);
        }
    }
    private static boolean canSpawn(ServerWorld world, EntityType<? extends MobEntity> entityType, BlockPos.Mutable pos, double squaredDistance) {
        if (!entityType.isSpawnableFarFromPlayer() && squaredDistance > (double)(entityType.getSpawnGroup().getImmediateDespawnRange() * entityType.getSpawnGroup().getImmediateDespawnRange())) {
            return false;
        } else if (entityType.isSummonable()) {
            SpawnRestriction.Location location = SpawnRestriction.getLocation(entityType);
            if (!SpawnHelper.canSpawn(location, world, pos, entityType)) {
                return false;
            } else if (!MobEntity.canMobSpawn(entityType, world, SpawnReason.NATURAL, pos, world.random)) {
                return false;
            } else {
                return world.doesNotCollide(entityType.createSimpleBoundingBox((double)pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D));
            }
        } else {
            return false;
        }
    }
    private static MobEntity createMob(ServerWorld world, EntityType<?> type) {
        try {
            Entity entity = type.create(world);
            if (!(entity instanceof MobEntity)) {
                throw new IllegalStateException("Trying to spawn a non-mob: " + Registry.ENTITY_TYPE.getId(type));
            } else {
                return (MobEntity)entity;
            }
        } catch (Exception var4) {
            System.err.println("Failed to create mob");
            var4.printStackTrace(System.err);
            return null;
        }
    }

    private static boolean isValidSpawn(ServerWorld world, MobEntity entity, double squaredDistance) {
        if (squaredDistance > (double)(entity.getType().getSpawnGroup().getImmediateDespawnRange() * entity.getType().getSpawnGroup().getImmediateDespawnRange()) && entity.canImmediatelyDespawn(squaredDistance)) {
            return false;
        } else {
            return entity.canSpawn(world, SpawnReason.NATURAL) && entity.canSpawn(world);
        }
    }


}