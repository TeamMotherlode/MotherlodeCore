package motherlode.core.enderinvasion;

import motherlode.core.Motherlode;
import motherlode.core.registry.MotherlodeBlocks;
import motherlode.core.registry.MotherlodeTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.dimension.DimensionType;
import java.util.Objects;
import java.util.Random;

public class EnderInvasionUtil {

    private static final double NOISE_THRESHOLD = 0.75;
    private static final double NOISE_SCALE = 0.002;
    private static final double END_CAP_NOISE_THRESHOLD = 0.75;
    private static final double END_FOAM_NOISE_THRESHOLD = 0.9;
    private static final double DECORATION_NOISE_SCALE = 0.05;

    private static final double ENDERMAN_SPAWN_RATE_DAY = 0.075;
    private static final double ENDERMAN_SPAWN_RATE_NIGHT = 0.1;

    private static SimplexNoiseSampler NOISE_GENERATOR;
    private static long NOISE_GENERATOR_SEED;

    public static void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {

        // TODO block spread

        if(Motherlode.ENDER_INVASION_STATE.get(world.getLevelProperties()).value() == EnderInvasionState.ENDER_INVASION &&
           world.getBlockState(pos.down()).isIn(MotherlodeTags.Blocks.SPREADABLE) &&
           world.random.nextDouble() < (world.isNight()? ENDERMAN_SPAWN_RATE_NIGHT : ENDERMAN_SPAWN_RATE_DAY)) {

            spawnMobGroup(world, world.getChunk(pos), EntityType.ENDERMAN, pos);
        }
    }

    private static void spawnMobGroup(ServerWorld world, Chunk chunk, EntityType<? extends MobEntity> entityType, BlockPos pos) {

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
                PlayerEntity playerEntity = world.getClosestPlayer(d, (double) i, e, -1.0D, false);
                if (playerEntity != null) {
                    double f = playerEntity.squaredDistanceTo(d, (double) i, e);
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
                return world.doesNotCollide(entityType.createSimpleBoundingBox((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D));
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

    public static void convertChunk(ServerWorld world, WorldChunk chunk) {

        if (!(world.getDimension() == DimensionType.getOverworldDimensionType())) return;
        if (!(Motherlode.ENDER_INVASION_STATE.get(world.getLevelProperties()).value() == EnderInvasionState.ENDER_INVASION))
            return;
        if (!(Motherlode.ENDER_INVASION_CHUNK_STATE.get(chunk).value() == EnderInvasionChunkState.PRE_ECHERITE))
            return;

        int maxY = chunk.getHighestNonEmptySectionYOffset() * 16;

        if (NOISE_GENERATOR == null || NOISE_GENERATOR_SEED != world.getSeed()) {

            NOISE_GENERATOR = new SimplexNoiseSampler(new Random(world.getSeed()));
            NOISE_GENERATOR_SEED = world.getSeed();
        }

        for (int x = 0; x < 16; x++) {

            for (int y = 0; y < maxY; y++) {

                for (int z = 0; z < 16; z++) {

                    BlockPos pos = new BlockPos(chunk.getPos().x * 16 + x, y, chunk.getPos().z * 16 + z);

                    if (NOISE_GENERATOR.method_22416(pos.getX() * NOISE_SCALE, pos.getY() * NOISE_SCALE, pos.getZ() * NOISE_SCALE) < NOISE_THRESHOLD)
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

        if (!chunk.getBlockState(pos).isAir() && !chunk.getBlockState(pos).isOf(MotherlodeBlocks.CORRUPTED_GRASS))
            return;
        double noise = NOISE_GENERATOR.method_22416(pos.getX() * DECORATION_NOISE_SCALE, pos.getY() * DECORATION_NOISE_SCALE, pos.getZ() * DECORATION_NOISE_SCALE);

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
                if (chunk.getBlockState(blockPos).isAir() || MotherlodeTags.Blocks.END_FOAM_REPLACEABLE.contains(MotherlodeBlocks.END_FOAM))
                    chunk.setBlockState(blockPos, MotherlodeBlocks.END_FOAM.getDefaultState(), false);
                else return false;
            }
        }
        return ground;
    }

    public static void changeEnderPearlTrade() {

        TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(VillagerProfession.CLERIC).get(4)[2] =
                new PostEnderInvasionSellItemFactory(Items.ENDER_PEARL, 5, 1, 15);
    }

    public static class PostEnderInvasionSellItemFactory implements TradeOffers.Factory {

        private final ItemStack sell;
        private final int price;
        private final int count;
        private final int maxUses;
        private final int experience;
        private final float multiplier;

        public PostEnderInvasionSellItemFactory(Block block, int price, int count, int maxUses, int experience) {
            this(new ItemStack(block), price, count, maxUses, experience);
        }

        public PostEnderInvasionSellItemFactory(Item item, int price, int count, int experience) {
            this((new ItemStack(item)), price, count, 12, experience);
        }

        public PostEnderInvasionSellItemFactory(Item item, int price, int count, int maxUses, int experience) {
            this(new ItemStack(item), price, count, maxUses, experience);
        }

        public PostEnderInvasionSellItemFactory(ItemStack stack, int price, int count, int maxUses, int experience) {
            this(stack, price, count, maxUses, experience, 0.05F);
        }

        public PostEnderInvasionSellItemFactory(ItemStack stack, int price, int count, int maxUses, int experience, float multiplier) {
            this.sell = stack;
            this.price = price;
            this.count = count;
            this.maxUses = maxUses;
            this.experience = experience;
            this.multiplier = multiplier;
        }

        public TradeOffer create(Entity entity, Random random) {

            if (Motherlode.ENDER_INVASION_STATE.get(entity.getEntityWorld().getLevelProperties()).value()
                    .ordinal() >= EnderInvasionState.ENDER_INVASION.ordinal())

                return new TradeOffer(new ItemStack(Items.EMERALD, this.price), new ItemStack(this.sell.getItem(), this.count), this.maxUses, this.experience, this.multiplier);

            else return null;
        }
    }

    public static final LootConditionType POST_ENDER_INVASION_LOOT_CONDITION = Registry.register(Registry.LOOT_CONDITION_TYPE, Motherlode.id("post_ender_invasion"), new LootConditionType(new PostEnderInvasionLootCondition.Serializer()));
}