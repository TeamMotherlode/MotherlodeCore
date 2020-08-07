package motherlode.core;

import motherlode.core.registry.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.io.IOException;

public class Motherlode implements ModInitializer {
    public static final String MODID = "motherlode";
    public static final boolean isClient = FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;

    @Override
    public void onInitialize() {
        MotherlodeEntities.init();
        MotherlodeBlocks.init();
        MotherlodeItems.init();
        MotherlodeBlockEntities.init();
        MotherlodeFeatures.init();
        MotherlodeStructures.init();
        MotherlodeBiomes.init();
        MotherlodeSounds.init();
        MotherlodeTags.init();
        MotherlodeScreenHandlers.init();
        MotherlodePotions.init();

        try {
            MotherlodeData.register();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MotherlodeFeatures.register();
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

    public static Identifier id(String name) {
        return new Identifier(MODID, name);
    }
}
