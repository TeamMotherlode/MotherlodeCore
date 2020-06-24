package motherlode.core;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Motherlode implements ModInitializer {
    private static final String MODID = "motherlode";

    @Override
    public void onInitialize() {
        blockRegistry();
        itemRegistry();
    }

    public static final ItemGroup MAIN_GROUP = FabricItemGroupBuilder
            .create(
		new Identifier(MODID, "general"))
		.icon(() -> new ItemStack(Items.ITEM_GROUP))
		.appendItems(stacks ->
		{
            stacks.add(new ItemStack(Blocks.COPPER_ORE));
            stacks.add(new ItemStack(Blocks.COPPER_BLOCK));
            stacks.add(new ItemStack(Items.COPPER_INGOT));
        }).build();
    
    private void blockRegistry() {
        registerBlock(new Identifier(MODID, "copper_ore"), Blocks.COPPER_ORE);
        registerBlock(new Identifier(MODID, "copper_block"), Blocks.COPPER_BLOCK);
    }

    private void registerBlock(Identifier id, Block block) {
        Registry.register(Registry.BLOCK, id, block);
        Registry.register(Registry.ITEM, id, new BlockItem(block, new Item.Settings().group(ItemGroup.MISC)));
    }
    
    private void itemRegistry() {
        registerItem(new Identifier(MODID, "item_group"), Items.ITEM_GROUP);
        registerItem(new Identifier(MODID, "copper_ingot"), Items.COPPER_INGOT);
    }

    private void registerItem(Identifier id, Item item) {
        Registry.register(Registry.ITEM, id, item);
    }
}