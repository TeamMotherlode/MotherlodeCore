package motherlode.core.enderinvasion;

import motherlode.core.Motherlode;
import motherlode.core.registry.MotherlodeBlocks;
import motherlode.core.registry.MotherlodeTags;
import nerdhub.cardinal.components.api.ComponentRegistry;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.event.ChunkComponentCallback;
import nerdhub.cardinal.components.api.event.LevelComponentCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.chunk.WorldChunk;
import java.util.Random;

public class EnderInvasion {

    public static final ComponentType<EnderInvasionComponent> STATE =
        ComponentRegistry.INSTANCE.registerIfAbsent(Motherlode.id("enderinvasion_state"), EnderInvasionComponent.class);

    public static final ComponentType<EnderInvasionChunkComponent> CHUNK_STATE =
            ComponentRegistry.INSTANCE.registerIfAbsent(Motherlode.id("enderinvasion_chunk_state"), EnderInvasionChunkComponent.class);

    public static final LootConditionType POST_ENDER_INVASION_LOOT_CONDITION = Registry.register(Registry.LOOT_CONDITION_TYPE, Motherlode.id("post_ender_invasion"), new LootConditionType(new PostEnderInvasionLootCondition.Serializer()));

    public static final double NOISE_THRESHOLD = 0.75;
    public static final double NOISE_SCALE = 0.002;
    public static final double END_CAP_NOISE_THRESHOLD = 0.75;
    public static final double END_FOAM_NOISE_THRESHOLD = 0.9;
    public static final double DECORATION_NOISE_SCALE = 0.05;

    private static final double ENDERMAN_SPAWN_RATE_DAY = 0.075;
    private static final double ENDERMAN_SPAWN_RATE_NIGHT = 0.1;

    public static void initializeEnderInvasion() {

        // Register the components
        LevelComponentCallback.EVENT.register((levelProperties, components) -> components.put(STATE, new EnderInvasionComponentImpl(EnderInvasionState.PRE_ECHERITE)));
        ChunkComponentCallback.EVENT.register((chunk, components) -> components.put(CHUNK_STATE, new EnderInvasionChunkComponentImpl(EnderInvasionChunkState.PRE_ECHERITE)));

        // Call EnderInvasionHelper.convertChunk when a chunk gets loaded
        ServerChunkEvents.CHUNK_LOAD.register(EnderInvasionHelper::convertChunk);

        // Prevent clerics from selling ender pearls before the ender invasion has started
        TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(VillagerProfession.CLERIC).get(4)[2] =
                new PostEnderInvasionSellItemFactory(Items.ENDER_PEARL, 5, 1, 15);

        // Convert the chunk
        EnderInvasionEvents.CONVERT.register((world, chunk, pos, noise, sampler) -> {

            BlockState state = chunk.getBlockState(pos);
            SpreadRecipe recipe = SpreadingBlocksManager.SPREAD.getRecipe(state.getBlock());
            if (recipe != null) {

                chunk.setBlockState(pos, recipe.convert(state), false);
            }
            generateDecoration(world, chunk, pos, sampler.method_22416(pos.getX() * DECORATION_NOISE_SCALE, pos.getY() * DECORATION_NOISE_SCALE, pos.getZ() * DECORATION_NOISE_SCALE));
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
        // TODO block spread

        if(STATE.get(world.getLevelProperties()).value() == EnderInvasionState.ENDER_INVASION &&
                world.getBlockState(pos.down()).isIn(MotherlodeTags.Blocks.SPREADABLE) &&
                world.random.nextDouble() < (world.isNight()? ENDERMAN_SPAWN_RATE_NIGHT : ENDERMAN_SPAWN_RATE_DAY)) {

            EnderInvasionHelper.spawnMobGroup(world, world.getChunk(pos), EntityType.ENDERMAN, pos);
        }
    }
}
