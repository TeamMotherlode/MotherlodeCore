package motherlode.uncategorized;

import motherlode.uncategorized.registry.*;
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
        MotherlodeEntities.init();
        MotherlodeBlocks.init();
        MotherlodeItems.init();
        MotherlodeEnchantments.init();
        MotherlodeFeatures.init();
        MotherlodeStructures.init();
        MotherlodeBiomes.init();
        MotherlodeSounds.init();
        MotherlodeFluids.init();
        MotherlodeTags.init();

        MotherlodeData.register();
        MotherlodeFeatures.register();
    }

    public static final ItemGroup BLOCKS = FabricItemGroupBuilder.create( id("blocks"))
		.icon(() -> new ItemStack(MotherlodeBlocks.COPPER_ORE.asItem()))
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

    public static Identifier id(String namespace, String name) { return new Identifier(namespace, name); }
    public static Identifier id(String name) {
        return id(MODID, name);
    }
}
