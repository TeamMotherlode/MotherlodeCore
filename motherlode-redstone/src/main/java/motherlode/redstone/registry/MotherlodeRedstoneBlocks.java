package motherlode.redstone.registry;

import motherlode.base.Motherlode;
import motherlode.redstone.MotherlodeRedstoneMod;
import motherlode.redstone.RedstoneTransmitterBlock;
import motherlode.uncategorized.MotherlodeUncategorized;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class MotherlodeRedstoneBlocks {

    public static final Block REDSTONE_TRANSMITTER = register("redstone_transmitter", new RedstoneTransmitterBlock(true, false, true, true, AbstractBlock.Settings.of(Material.METAL).requiresTool().strength(3.0F, 3.0F)));

    public static void init() {

    }
    private static Block register(String name, Block block) {

        Registry.register(Registry.BLOCK, Motherlode.id(MotherlodeRedstoneMod.MODID, name), block);
        Registry.register(Registry.ITEM, Motherlode.id(MotherlodeRedstoneMod.MODID, name), new BlockItem(block, new Item.Settings().group(MotherlodeUncategorized.BLOCKS)));
        return block;
    }
}
