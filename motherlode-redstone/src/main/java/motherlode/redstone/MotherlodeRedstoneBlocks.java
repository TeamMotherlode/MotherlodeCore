package motherlode.redstone;

import motherlode.base.CommonAssets;
import motherlode.base.CommonData;
import motherlode.base.Motherlode;
import motherlode.base.api.AssetGenerator;
import motherlode.base.api.MotherlodeAssets;
import motherlode.base.api.MotherlodeData;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MotherlodeRedstoneBlocks {

    public static final Block REDSTONE_TRANSMITTER = register("redstone_transmitter", new RedstoneTransmitterBlock(true, false, true, true, AbstractBlock.Settings.of(Material.METAL).requiresTool().strength(3.0F, 3.0F)), CommonAssets.DEFAULT_BLOCK_STATE.andThen(CommonAssets.BLOCK_ITEM));

    public static void init() {

    }
    private static Block register(String name, Block block, AssetGenerator p) {

        Identifier id = Motherlode.id(MotherlodeRedstoneMod.MODID, name);
        Registry.register(Registry.BLOCK, id, block);
        Registry.register(Registry.ITEM, id, new BlockItem(block, new Item.Settings().group(ItemGroup.REDSTONE)));
        MotherlodeAssets.addGenerator(id, p);
        MotherlodeData.addGenerator(id, CommonData.DEFAULT_BLOCK_LOOT_TABLE);
        return block;
    }
}
