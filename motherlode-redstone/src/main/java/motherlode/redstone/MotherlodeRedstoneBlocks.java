package motherlode.redstone;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.ItemGroup;
import motherlode.base.api.CommonAssets;
import motherlode.base.api.CommonData;
import motherlode.base.Motherlode;
import motherlode.base.api.AssetProcessor;
import motherlode.base.api.Registerable;
import motherlode.redstone.block.RedstoneTransmitterBlock;

public class MotherlodeRedstoneBlocks {
    public static final Block REDSTONE_TRANSMITTER = register("redstone_transmitter", new RedstoneTransmitterBlock(AbstractBlock.Settings.of(Material.METAL).requiresTool().strength(3.0F, 3.0F)), CommonAssets.DEFAULT_BLOCK_STATE.andThen(CommonAssets.BLOCK_ITEM));

    public static void init() {
    }

    private static Block register(String name, Block block, AssetProcessor p) {
        return Motherlode.register(
            Registerable.block(block, ItemGroup.REDSTONE),
            Motherlode.id(MotherlodeModule.MODID, name),
            block,
            p,
            CommonData.DEFAULT_BLOCK_LOOT_TABLE
        );
    }
}
