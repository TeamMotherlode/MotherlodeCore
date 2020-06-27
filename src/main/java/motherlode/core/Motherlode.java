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

        MotherlodeData.register();
    }

    public static final ItemGroup MAIN_GROUP = FabricItemGroupBuilder
            .create(
		new Identifier(MODID, "general"))
		.icon(() -> new ItemStack(MotherlodeItems.ITEM_GROUP))
		.build();

    public static Identifier id(String name) {
        return new Identifier(MODID, name);
    }
}