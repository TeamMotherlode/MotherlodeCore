package motherlode.enderinvasion.util;

import java.util.Optional;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.GameRules;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.WorldChunk;
import net.fabricmc.fabric.api.server.PlayerStream;
import motherlode.enderinvasion.mixin.SpawnHelperAccessor;
import motherlode.enderinvasion.recipe.BlockRecipeManager;
import io.netty.buffer.Unpooled;

public class EnderInvasionHelper {
    public static <T extends Chunk> void forEachBlock(T chunk, BiConsumer<T, BlockPos> consumer) {
        int maxY = chunk.getHighestNonEmptySectionYOffset() * 16;

        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < maxY; y++) {
                for (int z = 0; z < 16; z++) {
                    consumer.accept(chunk, new BlockPos(chunk.getPos().x * 16 + x, y, chunk.getPos().z * 16 + z));
                }
            }
        }
    }

    // Spawns a group of mobs
    public static void spawnMobGroup(ServerWorld world, Chunk chunk, EntityType<? extends MobEntity> entityType, BlockPos pos, SpawnHelper.Info info) {
        if (((SpawnHelperAccessor.Info) info).callIsBelowCap(entityType.getSpawnGroup())) {
            spawn(world, chunk, entityType, pos, ((SpawnHelperAccessor.Info) info)::callTest, ((SpawnHelperAccessor.Info) info)::callRun);
        }
    }

    private static void spawn(ServerWorld world, Chunk chunk, EntityType<? extends MobEntity> entityType, BlockPos pos, SpawnHelper.Checker checker, SpawnHelper.Runner runner) {
        if (!world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING)) return;

        int i = pos.getY();

        BlockState blockState = chunk.getBlockState(pos);
        if (!blockState.isSolidBlock(chunk, pos)) {
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            int j = 0;

            for (int k = 0; k < 3; k++) {
                int l = pos.getX();
                int m = pos.getZ();
                EntityData entityData = null;
                int o = MathHelper.ceil(world.random.nextFloat() * 4.0F);
                int p = 0;

                for (int q = 0; q < o; q++) {
                    l += world.random.nextInt(6) - world.random.nextInt(6);
                    m += world.random.nextInt(6) - world.random.nextInt(6);
                    mutable.set(l, i, m);
                    double d = (double) l + 0.5D;
                    double e = (double) m + 0.5D;
                    PlayerEntity playerEntity = world.getClosestPlayer(d, i, e, -1.0D, false);
                    if (playerEntity != null) {
                        double f = playerEntity.squaredDistanceTo(d, i, e);
                        if (SpawnHelperAccessor.isAcceptableSpawnPosition(world, chunk, mutable, f)) {
                            o = 1 + world.random.nextInt(4);

                            if (canSpawn(world, entityType, mutable, f) && checker.test(entityType, mutable, chunk)) {
                                MobEntity mobEntity = SpawnHelperAccessor.createMob(world, entityType);
                                if (mobEntity == null) {
                                    return;
                                }

                                mobEntity.refreshPositionAndAngles(d, i, e, world.random.nextFloat() * 360.0F, 0.0F);
                                if (SpawnHelperAccessor.isValidSpawn(world, mobEntity, f)) {
                                    entityData = mobEntity.initialize(world, world.getLocalDifficulty(mobEntity.getBlockPos()), SpawnReason.NATURAL, entityData, null);
                                    ++j;
                                    ++p;
                                    world.spawnEntity(mobEntity);
                                    runner.run(mobEntity, chunk);
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
    }

    private static boolean canSpawn(ServerWorld world, EntityType<? extends MobEntity> entityType, BlockPos.Mutable pos, double squaredDistance) {
        if (!entityType.isSpawnableFarFromPlayer() && squaredDistance > (double) (entityType.getSpawnGroup().getImmediateDespawnRange() * entityType.getSpawnGroup().getImmediateDespawnRange())) {
            return false;
        } else if (entityType.isSummonable()) {
            SpawnRestriction.Location location = SpawnRestriction.getLocation(entityType);
            if (!SpawnHelper.canSpawn(location, world, pos, entityType)) {
                return false;
            } else if (!MobEntity.canMobSpawn(entityType, world, SpawnReason.NATURAL, pos, world.random)) {
                return false;
            } else {
                return world.isSpaceEmpty(entityType.createSimpleBoundingBox((double) pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D));
            }
        } else {
            return false;
        }
    }

    // Checks if a corrupted block can survive here
    public static boolean canSurvive(WorldView world, BlockPos pos) {
        BlockPos posUp = pos.up();
        BlockState stateUp = world.getBlockState(posUp);
        if (!world.getFluidState(posUp).isIn(FluidTags.WATER) && !stateUp.isSolidBlock(world, posUp)) return true;

        for (Direction direction : Direction.values()) {
            BlockPos pos2 = pos.add(direction.getVector());
            if (world.getFluidState(pos2).isIn(FluidTags.WATER)) return false;
        }
        return true;
    }

    public static void convert(WorldChunk chunk, BlockRecipeManager manager, BlockPos pos) {
        BlockState state = chunk.getBlockState(pos);
        Optional.of(state.getBlock())
            .map(manager::getRecipe)
            .map(recipe -> recipe.convert(state))
            .ifPresent(convertedState -> chunk.setBlockState(pos, convertedState, false));
    }

    // Returns a Vec3i where x and z can be from -1 to 1, and y can be from -2 to 2
    public static Vec3i randomNearbyBlockPos(Random random) {
        return new Vec3i(random.nextInt(3) - 1, random.nextInt(5) - 2, random.nextInt(3) - 1);
    }

    public static void spawnParticles(ServerWorld world, BlockPos pos, Random random, int tries, Predicate<BlockPos> inEnderInvasion) {
        for (int i = 0; i < tries; i++) {
            BlockPos blockPos = pos.add(random.nextInt(5) - 2, random.nextInt(5) - 2, random.nextInt(5) - 2);
            BlockState blockState = world.getBlockState(blockPos);

            if (!blockState.isAir() && !world.getBlockState(blockPos.up()).getMaterial().isSolid() && inEnderInvasion.test(blockPos)) {
                Stream<PlayerEntity> watchingPlayers = PlayerStream.watching(world, blockPos);
                PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
                passedData.writeBlockPos(blockPos);
                passedData.writeDouble(random.nextDouble());

                passedData.writeDouble(random.nextDouble() * 2 - 1);
                passedData.writeDouble((random.nextDouble() + 0.3) * 4);
                passedData.writeDouble(random.nextDouble() * 2 - 1);

                /* watchingPlayers.forEach(player ->
                    ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, EnderInvasion.PLAY_PORTAL_PARTICLE_PACKET_ID, passedData)); */ // TODO
            }
        }
    }

    public static class WrappedBoolean {
        private boolean value;

        public WrappedBoolean(boolean value) {
            this.value = value;
        }

        public boolean get() {
            return this.value;
        }

        public void set(boolean value) {
            this.value = value;
        }
    }
}
