package motherlode.enderinvasion;

import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
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
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import motherlode.enderinvasion.component.EnderInvasionChunkComponent;
import motherlode.enderinvasion.component.EnderInvasionComponent;
import motherlode.enderinvasion.impl.EnderInvasionGenerator;
import motherlode.enderinvasion.recipe.BlockRecipeManager;
import motherlode.enderinvasion.util.EnderInvasionHelper;
import motherlode.materials.MotherlodeMaterialsBlocks;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;

public final class EnderInvasion {
    public static final ComponentKey<EnderInvasionComponent> STATE =
        ComponentRegistry.getOrCreate(MotherlodeModule.id("ender_invasion_state"), EnderInvasionComponent.class);

    public static final ComponentKey<EnderInvasionChunkComponent> CHUNK_STATE =
        ComponentRegistry.getOrCreate(MotherlodeModule.id("ender_invasion_chunk_state"), EnderInvasionChunkComponent.class);

    public static final LootConditionType POST_ENDER_INVASION_LOOT_CONDITION = Registry.register(Registry.LOOT_CONDITION_TYPE, MotherlodeModule.id("post_ender_invasion"), new LootConditionType(new PostEnderInvasionLootCondition.Serializer()));

    public static final Identifier PLAY_PORTAL_PARTICLE_PACKET_ID = MotherlodeModule.id("ender_invasion_portal_particle");

    public static Difficulty SPREAD_DIFFICULTY = Difficulty.HARD;
    public static int INVASION_END_TIME = 216000;
    public static double ENDERMAN_SPAWN_RATE_DAY = 0.1;
    public static double ENDERMAN_SPAWN_RATE_NIGHT = 0.25;

    private final EnderInvasionGenerator generator;

    public EnderInvasion(EnderInvasionGenerator generator) {
        this.generator = generator;
    }

    public void initializeEnderInvasion() {
        // Prevents clerics from selling ender pearls before the ender invasion has started
        TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(VillagerProfession.CLERIC).get(4)[2] =
            new PostEnderInvasionSellItemFactory(Items.ENDER_PEARL, 5, 1, 15);

        // Call convertChunk when a chunk gets loaded
        ServerChunkEvents.CHUNK_LOAD.register(this::convertChunk);

        // Start the ender invasion when Echerite Ore is broken
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            if (state.isOf(MotherlodeMaterialsBlocks.ECHERITE_ORE)) {
                EnderInvasionComponent component = EnderInvasion.STATE.get(world.getLevelProperties());

                if (component.value() == EnderInvasionComponent.State.PRE_ECHERITE) {
                    component.setValue(EnderInvasionComponent.State.ENDER_INVASION);

                    // Send chat message
                    world.getPlayers().forEach(p -> player.sendMessage(new TranslatableText("message.motherlode-ender-invasion.start").formatted(Formatting.DARK_GREEN), false));
                }
            }
        });

        // Convert blocks using BlockRecipeManager.SPREAD
        EnderInvasionEvents.CONVERT_BLOCK.register((world, chunk, pos) ->
            EnderInvasionHelper.convert(chunk, BlockRecipeManager.SPREAD, pos));

        // Purify blocks using BlockRecipeManager.PURIFICATION
        EnderInvasionEvents.PURIFY_BLOCK.register((world, chunk, pos) ->
            EnderInvasionHelper.convert(chunk, BlockRecipeManager.PURIFICATION, pos));

        Identifier piglinBarteringLootTable = new Identifier("minecraft", "gameplay/piglin_bartering");

        // Override piglin bartering loot table to prevent ender pearls before the ender invasion
        // TODO Try to just modify the ender pearl entry instead of overriding the whole thing
        LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, supplier, setter) -> {
            if (piglinBarteringLootTable.equals(id)) {
                setter.set(lootManager.getTable(MotherlodeModule.id("gameplay/piglin_bartering")));
            }
        });
    }

    public void convertChunk(ServerWorld world, WorldChunk chunk) {
        final Profiler profiler = world.getProfiler();
        profiler.push("enderinvasion");

        if (!world.equals(world.getServer().getWorld(World.OVERWORLD))) return;

        switch (EnderInvasion.STATE.get(world.getLevelProperties()).value()) {
            case PRE_ECHERITE:
                checkUnaffected(world, chunk);
                break;
            case ENDER_INVASION:
                corruptChunk(world, chunk);
                this.generator.generateDecoration(world, chunk);
                break;

            case POST_ENDER_DRAGON:
                if (EnderInvasion.CHUNK_STATE.get(chunk).value() == EnderInvasionChunkComponent.State.PRE_ECHERITE)
                    corruptChunk(world, chunk);
                purifyChunk(world, chunk);
                break;
        }
        profiler.pop();
    }

    public void checkUnaffected(ServerWorld world, WorldChunk chunk) {
        EnderInvasionChunkComponent chunkState = EnderInvasion.CHUNK_STATE.get(chunk);

        if (chunkState.value() == EnderInvasionChunkComponent.State.UNAFFECTED) return;

        if (this.generator.isChunkUnaffected(world, chunk)) chunkState.setValue(
            EnderInvasionChunkComponent.State.UNAFFECTED);
    }

    public void corruptChunk(ServerWorld world, WorldChunk chunk) {
        final Profiler profiler = world.getProfiler();
        profiler.push("corruptChunk");

        EnderInvasionChunkComponent chunkState = EnderInvasion.CHUNK_STATE.get(chunk);

        if (chunkState.value() == EnderInvasionChunkComponent.State.ENDER_INVASION
            || chunkState.value() == EnderInvasionChunkComponent.State.UNAFFECTED) return;

        boolean unaffected = this.generator.convertChunk(world, chunk);

        chunkState.setValue(unaffected ? EnderInvasionChunkComponent.State.UNAFFECTED :
            EnderInvasionChunkComponent.State.ENDER_INVASION);

        profiler.pop();
    }

    public void purifyChunk(ServerWorld world, WorldChunk chunk) {
        final Profiler profiler = world.getProfiler();
        profiler.push("purifyChunk");

        EnderInvasionChunkComponent chunkState = EnderInvasion.CHUNK_STATE.get(chunk);
        if (chunkState.value() == EnderInvasionChunkComponent.State.UNAFFECTED
            || chunkState.value() == EnderInvasionChunkComponent.State.PURIFIED) return;

        boolean unaffected = this.generator.purifyChunk(world, chunk,
            STATE.get(world.getLevelProperties()).getInvasionEndTick() / (double) INVASION_END_TIME);

        chunkState.setValue(unaffected ? EnderInvasionChunkComponent.State.UNAFFECTED :
            EnderInvasionChunkComponent.State.PURIFIED);

        profiler.pop();
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!world.equals(world.getServer().getWorld(World.OVERWORLD))) return;

        final Profiler profiler = world.getProfiler();
        profiler.push("enderInvasionTick");

        if (!EnderInvasionHelper.canSurvive(world, pos)) {
            EnderInvasionHelper.convert(world.getWorldChunk(pos), BlockRecipeManager.PURIFICATION, pos);

            profiler.pop();
            return;
        }

        EnderInvasionComponent component = EnderInvasion.STATE.get(world.getLevelProperties());

        switch (component.value()) {
            case ENDER_INVASION:

                EnderInvasionHelper.spawnParticles(world, pos, random, 4, blockPos -> this.generator.isInEnderInvasion(world, pos, 0));

                if (generator.isInEnderInvasion(world, pos, 0)
                    && world.random.nextDouble() < (world.isNight() ? ENDERMAN_SPAWN_RATE_NIGHT : ENDERMAN_SPAWN_RATE_DAY)) {

                    EnderInvasionHelper.spawnMobGroup(world, world.getChunk(pos), EntityType.ENDERMAN, pos, world.getChunkManager().getSpawnInfo());
                    break;
                }
                spread(state, world, pos, random);
                break;

            case POST_ENDER_DRAGON:

                if (CHUNK_STATE.get(world.getChunk(pos)).value() == EnderInvasionChunkComponent.State.UNAFFECTED) break;

                if (!this.generator.isInEnderInvasion(world, pos, component.getInvasionEndTick() / (double) INVASION_END_TIME))
                    EnderInvasionEvents.PURIFY_BLOCK.invoker().convertBlock(world, world.getWorldChunk(pos), pos);
                break;
        }

        profiler.pop();
    }

    public void spread(BlockState state, ServerWorld world, BlockPos pos, Random random) {

        if (!state.isIn(MotherlodeEnderInvasionTags.SPREADABLE)) return;
        if (world.getDifficulty().getId() < SPREAD_DIFFICULTY.getId()) return;

        for (int i = 0; i < 3; i++) {

            BlockPos blockPos = pos.add(EnderInvasionHelper.randomNearbyBlockPos(random));
            EnderInvasionHelper.convert(world.getWorldChunk(blockPos), BlockRecipeManager.SPREAD, blockPos);
        }
    }
}
