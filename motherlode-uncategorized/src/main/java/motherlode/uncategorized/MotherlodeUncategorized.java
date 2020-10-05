package motherlode.uncategorized;

import motherlode.base.Motherlode;
import motherlode.uncategorized.registry.MotherlodeBlocks;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class MotherlodeUncategorized implements ModInitializer {

    @Override
    public void onInitialize() {
        MotherlodeBlocks.init();
    }

    public static final ItemGroup BLOCKS = FabricItemGroupBuilder.create(Motherlode.id("blocks"))
		.icon(() -> new ItemStack(Blocks.IRON_ORE.asItem()))
		.build();

    public static final ItemGroup ITEMS = FabricItemGroupBuilder.create(Motherlode.id("items"))
            .icon(() -> new ItemStack(Items.IRON_INGOT))
            .build();

    public static final ItemGroup MUSIC = FabricItemGroupBuilder.create(Motherlode.id("music"))
            .icon(() -> new ItemStack(Items.MUSIC_DISC_CAT))
            .build();
}