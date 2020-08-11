package motherlode.core;

import motherlode.core.enderinvasion.*;
import motherlode.core.registry.*;
import nerdhub.cardinal.components.api.ComponentRegistry;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.event.ChunkComponentCallback;
import nerdhub.cardinal.components.api.event.LevelComponentCallback;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class Motherlode implements ModInitializer {
    private static final String MODID = "motherlode";



    @Override
    public void onInitialize() {

        LevelComponentCallback.EVENT.register((levelProperties, components) -> components.put(ENDER_INVASION_STATE, new EnderInvasionComponentImpl(EnderInvasionState.PRE_ECHERITE)));
        ChunkComponentCallback.EVENT.register((chunk, components) -> components.put(ENDER_INVASION_CHUNK_STATE, new EnderInvasionChunkComponentImpl(EnderInvasionChunkState.PRE_ECHERITE)));
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

        MotherlodeData.register();
        MotherlodeFeatures.register();
        MotherlodeSpreadRecipes.register();
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

    public static final ComponentType<EnderInvasionComponent> ENDER_INVASION_STATE =
            ComponentRegistry.INSTANCE.registerIfAbsent(id("enderinvasion_state"), EnderInvasionComponent.class);

    public static final ComponentType<EnderInvasionChunkComponent> ENDER_INVASION_CHUNK_STATE =
            ComponentRegistry.INSTANCE.registerIfAbsent(id("enderinvasion_chunk_state"), EnderInvasionChunkComponent.class);

    public static Identifier id(String name) {
        return new Identifier(MODID, name);
    }
}
