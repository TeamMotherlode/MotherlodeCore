package motherlode.core;

import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentInitializer;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.level.LevelComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.level.LevelComponentInitializer;
import motherlode.core.enderinvasion.*;
import motherlode.core.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class Motherlode implements ModInitializer, LevelComponentInitializer, ChunkComponentInitializer {
    private static final String MODID = "motherlode";



    @Override
    public void onInitialize() {

        initializeEnderInvasion();
        MotherlodeEntities.init();
        MotherlodeBlocks.init();
        MotherlodeItems.init();
        MotherlodeBlockEntities.init();
        MotherlodeEnchantments.init();
        MotherlodeFeatures.init();
        MotherlodeStructures.init();
        MotherlodeBiomes.init();
        MotherlodeSounds.init();
        MotherlodeFluids.init();
        MotherlodeTags.init();
        MotherlodeScreenHandlers.init();
        MotherlodePotions.init();

        EnderInvasionUtil.changeEnderPearlTrade();

        MotherlodeData.register();
        MotherlodeFeatures.register();
        MotherlodeSpreadRecipes.register();
    }

    private static final Identifier PIGLIN_BARTERING_LOOT_TABLE_ID = new Identifier("minecraft", "gameplay/piglin_bartering");

    private void initializeEnderInvasion() {

        ServerChunkEvents.CHUNK_LOAD.register(EnderInvasionUtil::convertChunk);
    }

    public static final ItemGroup BLOCKS = FabricItemGroupBuilder.create( id("blocks"))
		.icon(() -> new ItemStack(MotherlodeBlocks.COPPER_ORE))
		.build();

    public static final ItemGroup ITEMS = FabricItemGroupBuilder.create( id("items"))
            .icon(() -> new ItemStack(MotherlodeItems.COPPER_INGOT))
            .build();

    public static final ItemGroup ARMOUR_AND_TOOLS = FabricItemGroupBuilder.create( id("armor_and_tools"))
            .icon(() -> new ItemStack(MotherlodeItems.COPPER.HELMET))
            .build();

    public static final ItemGroup MUSIC = FabricItemGroupBuilder.create( id("music"))
            .icon(() -> new ItemStack(Items.MUSIC_DISC_CAT))
            .build();

    public static final ComponentKey<EnderInvasionComponent> ENDER_INVASION_STATE =
            ComponentRegistryV3.INSTANCE.getOrCreate(id("enderinvasion_state"), EnderInvasionComponent.class);

    public static final ComponentKey<EnderInvasionChunkComponent> ENDER_INVASION_CHUNK_STATE =
            ComponentRegistryV3.INSTANCE.getOrCreate(id("enderinvasion_chunk_state"), EnderInvasionChunkComponent.class);

    @Override
    public void registerLevelComponentFactories(LevelComponentFactoryRegistry registry) {

        registry.register(ENDER_INVASION_STATE, levelProperties -> new EnderInvasionComponentImpl(EnderInvasionState.PRE_ECHERITE));
    }

    @Override
    public void registerChunkComponentFactories(ChunkComponentFactoryRegistry registry) {

        registry.register(ENDER_INVASION_CHUNK_STATE, chunk -> new EnderInvasionChunkComponentImpl(EnderInvasionChunkState.PRE_ECHERITE));
    }

    public static Identifier id(String name) {
        return new Identifier(MODID, name);
    }
}
