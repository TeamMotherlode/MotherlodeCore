package motherlode.core.enderinvasion;

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
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.dimension.DimensionType;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import motherlode.core.Motherlode;
import motherlode.core.registry.MotherlodeBlocks;
import motherlode.core.registry.MotherlodeTags;
import nerdhub.cardinal.components.api.ComponentRegistry;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.event.ChunkComponentCallback;
import nerdhub.cardinal.components.api.event.LevelComponentCallback;

public class EnderInvasion {

    public static final ComponentType<EnderInvasionComponent> STATE =
            ComponentRegistry.INSTANCE.registerIfAbsent(Motherlode.id("enderinvasion_state"), EnderInvasionComponent.class);

    public static final ComponentType<EnderInvasionChunkComponent> CHUNK_STATE =
            ComponentRegistry.INSTANCE.registerIfAbsent(Motherlode.id("enderinvasion_chunk_state"), EnderInvasionChunkComponent.class);

    public static final LootConditionType POST_ENDER_INVASION_LOOT_CONDITION = Registry.register(Registry.LOOT_CONDITION_TYPE, Motherlode.id("post_ender_invasion"), new LootConditionType(new PostEnderInvasionLootCondition.Serializer()));

    public static final Identifier PLAY_PORTAL_PARTICLE_PACKET_ID = Motherlode.id("ender_invasion_portal_particle");

    public static final double NOISE_THRESHOLD = 0.75;
    public static final double NOISE_SCALE = 0.002;
    public static final double END_CAP_NOISE_THRESHOLD = 0.75;
    public static final double END_FOAM_NOISE_THRESHOLD = 0.9;
    public static final double DECORATION_NOISE_SCALE = 0.05;

    public static Difficulty SPREAD_DIFFICULTY = Difficulty.HARD;
    public static int INVASION_END_TIME = 216000;
    public static double ENDERMAN_SPAWN_RATE_DAY = 0.1;
    public static double ENDERMAN_SPAWN_RATE_NIGHT = 0.25;

    public static void initializeEnderInvasion() {

        // Register the components
        LevelComponentCallback.EVENT.register((levelProperties, components) -> components.put(STATE, new EnderInvasionComponent.Impl(EnderInvasionComponent.State.PRE_ECHERITE)));
        ChunkComponentCallback.EVENT.register((chunk, components) -> components.put(CHUNK_STATE, new EnderInvasionChunkComponent.Impl(EnderInvasionChunkComponent.State.PRE_ECHERITE)));

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
                setter.set(lootManager.getTable(Motherlode.id("gameplay/piglin_bartering")));
            }
        });
    }

    public static void generateDecoration(ServerWorld world, WorldChunk chunk, BlockPos pos, double noise) {
        if (!chunk.getBlockState(pos).isAir() && !chunk.getBlockState(pos).isOf(MotherlodeBlocks.CORRUPTED_GRASS))
            return;

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

    public static void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getDimension() != DimensionType.getOverworldDimensionType()) return;

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

                if (EnderInvasionHelper.getNoise(world, pos, NOISE_SCALE) >= NOISE_THRESHOLD &&
                        world.random.nextDouble() < (world.isNight() ? ENDERMAN_SPAWN_RATE_NIGHT : ENDERMAN_SPAWN_RATE_DAY)) {

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

        if (!state.isIn(MotherlodeTags.Blocks.SPREADABLE)) return;
        if (world.getDifficulty().getId() < SPREAD_DIFFICULTY.getId()) return;

        for (int i = 0; i < 3; i++) {

            BlockPos blockPos = pos.add(EnderInvasionHelper.randomNearbyBlockPos(random));
            EnderInvasionHelper.convert(world.getWorldChunk(blockPos), BlockRecipeManager.SPREAD, blockPos);
        }
    }
}
