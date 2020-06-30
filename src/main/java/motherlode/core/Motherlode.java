package motherlode.core;

import motherlode.core.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class Motherlode implements ModInitializer {
    private static final String MODID = "motherlode";

    @Override
    public void onInitialize() {
        MotherlodeEntities.init();
        MotherlodeBlocks.init();
        MotherlodeItems.init();
        MotherlodeBlockEntities.init();
        MotherlodeEnchantments.init();
        MotherlodeFeatures.init();
        MotherlodeBiomes.init();
        MotherlodeSounds.init();
        MotherlodeFluids.init();
        MotherlodeTags.init();
        MotherlodeScreenHandlers.init();
        MotherlodePotions.init();

        MotherlodeData.register();
    }

    public static final ItemGroup BLOCKS = FabricItemGroupBuilder.create( id("blocks"))
		.icon(() -> new ItemStack(MotherlodeBlocks.COPPER_ORE))
		.build();

    public static final ItemGroup ITEMS = FabricItemGroupBuilder.create( id("items"))
            .icon(() -> new ItemStack(MotherlodeItems.COPPER_INGOT))
            .build();

    public static final ItemGroup ARMOUR_AND_TOOLS = FabricItemGroupBuilder.create( id("armor_and_tools"))
            .icon(() -> new ItemStack(MotherlodeItems.COPPER.helmet))
            .build();

    public static Identifier id(String name) {
        return new Identifier(MODID, name);
    }
}