package motherlode.enderinvasion;

import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import motherlode.enderinvasion.component.EnderInvasionChunkComponent;
import motherlode.enderinvasion.component.EnderInvasionComponent;
import motherlode.enderinvasion.recipe.BlockRecipeManager;
import motherlode.enderinvasion.util.EnderInvasionHelper;
import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentInitializer;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.level.LevelComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.level.LevelComponentInitializer;

public final class EnderInvasion implements LevelComponentInitializer, ChunkComponentInitializer {
    public static final ComponentKey<EnderInvasionComponent> STATE =
        ComponentRegistry.getOrCreate(MotherlodeModule.id("ender_invasion_state"), EnderInvasionComponent.class);

    public static final ComponentKey<EnderInvasionChunkComponent> CHUNK_STATE =
        ComponentRegistry.getOrCreate(MotherlodeModule.id("ender_invasion_chunk_state"), EnderInvasionChunkComponent.class);

    public static final LootConditionType POST_ENDER_INVASION_LOOT_CONDITION = Registry.register(Registry.LOOT_CONDITION_TYPE, MotherlodeModule.id("post_ender_invasion"), new LootConditionType(new PostEnderInvasionLootCondition.Serializer()));

    public static final Identifier PLAY_PORTAL_PARTICLE_PACKET_ID = MotherlodeModule.id("ender_invasion_portal_particle");

    public static final double NOISE_THRESHOLD = 0.75;
    public static final double NOISE_SCALE = 0.002;
    public static final double END_CAP_NOISE_THRESHOLD = 0.75;
    public static final double END_FOAM_NOISE_THRESHOLD = 0.9;
    public static final double DECORATION_NOISE_SCALE = 0.05;

    public static Difficulty SPREAD_DIFFICULTY = Difficulty.HARD;
    public static int INVASION_END_TIME = 216000;
    public static double ENDERMAN_SPAWN_RATE_DAY = 0.1;
    public static double ENDERMAN_SPAWN_RATE_NIGHT = 0.25;

    @Override
    public void registerLevelComponentFactories(LevelComponentFactoryRegistry registry) {
        registry.register(STATE, worldProperties -> new EnderInvasionComponent.Impl(EnderInvasionComponent.State.PRE_ECHERITE));
    }

    @Override
    public void registerChunkComponentFactories(ChunkComponentFactoryRegistry registry) {
        registry.register(CHUNK_STATE, chunk -> new EnderInvasionChunkComponent.Impl(EnderInvasionChunkComponent.State.PRE_ECHERITE));
    }

    public static void initializeEnderInvasion() {
        // Prevents clerics from selling ender pearls before the ender invasion has started
        TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(VillagerProfession.CLERIC).get(4)[2] =
            new PostEnderInvasionSellItemFactory(Items.ENDER_PEARL, 5, 1, 15);

        // Call EnderInvasionHelper.convertChunk when a chunk gets loaded
        ServerChunkEvents.CHUNK_LOAD.register(EnderInvasionHelper::convertChunk);

        // Convert blocks using BlockRecipeManager.SPREAD and generate decoration
        EnderInvasionEvents.CONVERT_BLOCK.register((world, chunk, pos, noise) -> {
            EnderInvasionHelper.convert(chunk, BlockRecipeManager.SPREAD, pos);
            generateDecoration(world, chunk, pos, EnderInvasionHelper.getNoise(world, pos, DECORATION_NOISE_SCALE));
        });

        // Purify blocks using BlockRecipeManager.PURIFICATION
        EnderInvasionEvents.PURIFY_BLOCK.register((world, chunk, pos, noise) ->
            EnderInvasionHelper.convert(chunk, BlockRecipeManager.PURIFICATION, pos));

        Identifier piglinBarteringLootTable = new Identifier("minecraft", "gameplay/piglin_bartering");

        // Prevents piglins from trading ender pearls before the ender invasion has started
        LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, supplier, setter) -> {
            if (piglinBarteringLootTable.equals(id)) {
                setter.set(lootManager.getTable(MotherlodeModule.id("gameplay/piglin_bartering")));
            }
        });
    }

    public static void generateDecoration(ServerWorld world, WorldChunk chunk, BlockPos pos, double noise) {
        if (!chunk.getBlockState(pos).isAir() && !chunk.getBlockState(pos).isOf(MotherlodeEnderInvasionBlocks.CORRUPTED_GRASS))
            return;

        if (noise >= END_FOAM_NOISE_THRESHOLD && checkEndFoam(chunk, pos))
            chunk.setBlockState(pos, MotherlodeEnderInvasionBlocks.END_FOAM.getDefaultState(), false);

        if (world.getLightLevel(pos) < 15 && noise >= END_CAP_NOISE_THRESHOLD && chunk.getBlockState(pos.down()).isOf(MotherlodeEnderInvasionBlocks.CORRUPTED_DIRT)) {
            chunk.setBlockState(pos, MotherlodeEnderInvasionBlocks.END_CAP.getDefaultState(), false);
        }
    }

    public static boolean checkEndFoam(WorldChunk chunk, BlockPos pos) {
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

    public static void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!world.equals(world.getServer().getWorld(World.OVERWORLD))) return;

        final Profiler profiler = world.getProfiler();
        profiler.push("enderInvasionRandomTick");

        if (!EnderInvasionHelper.canSurvive(world, pos)) {
            EnderInvasionHelper.convert(world.getWorldChunk(pos), BlockRecipeManager.PURIFICATION, pos);

            profiler.pop();
            return;
        }

        switch (EnderInvasion.STATE.get(world.getLevelProperties()).value()) {
            case ENDER_INVASION:

                EnderInvasionHelper.spawnParticles(world, pos, random, 4);

                if (EnderInvasionHelper.getNoise(world, pos, NOISE_SCALE) >= NOISE_THRESHOLD
                    && world.random.nextDouble() < (world.isNight() ? ENDERMAN_SPAWN_RATE_NIGHT : ENDERMAN_SPAWN_RATE_DAY)) {

                    EnderInvasionHelper.spawnMobGroup(world, world.getChunk(pos), EntityType.ENDERMAN, pos, world.getChunkManager().getSpawnInfo());
                    break;
                }
                spread(state, world, pos, random);
                break;

            case POST_ENDER_DRAGON:

                if (CHUNK_STATE.get(world.getChunk(pos)).value() == EnderInvasionChunkComponent.State.UNAFFECTED) break;

                double noise = EnderInvasionHelper.getNoise(world, pos, NOISE_SCALE);
                if (noise < EnderInvasionHelper.getPostEnderDragonNoiseThreshold(world, INVASION_END_TIME, NOISE_THRESHOLD))
                    EnderInvasionEvents.PURIFY_BLOCK.invoker().convertBlock(world, world.getWorldChunk(pos), pos, noise);
                break;
        }

        profiler.pop();
    }

    public static void spread(BlockState state, ServerWorld world, BlockPos pos, Random random) {

        if (!state.isIn(MotherlodeEnderInvasionTags.SPREADABLE)) return;
        if (world.getDifficulty().getId() < SPREAD_DIFFICULTY.getId()) return;

        for (int i = 0; i < 3; i++) {

            BlockPos blockPos = pos.add(EnderInvasionHelper.randomNearbyBlockPos(random));
            EnderInvasionHelper.convert(world.getWorldChunk(blockPos), BlockRecipeManager.SPREAD, blockPos);
        }
    }
}
