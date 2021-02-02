package motherlode.redstone;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.ItemGroup;
import motherlode.base.Motherlode;
import motherlode.base.api.Registerable;
import motherlode.base.api.assets.AssetProcessor;
import motherlode.base.api.assets.CommonAssets;
import motherlode.base.api.assets.CommonData;
import motherlode.redstone.block.GrateBlock;
import motherlode.redstone.block.RedstoneTransmitterBlock;

public class MotherlodeRedstoneBlocks {
    public static final Block REDSTONE_TRANSMITTER = register("redstone_transmitter", new RedstoneTransmitterBlock(AbstractBlock.Settings.of(Material.METAL).requiresTool().strength(3.0F, 3.0F)), CommonAssets.DEFAULT_BLOCK_STATE.andThen(CommonAssets.BLOCK_ITEM));
    public static final Block STEEL_GRATE = register("steel_grate", new GrateBlock(AbstractBlock.Settings.of(Material.METAL).requiresTool().dynamicBounds().nonOpaque().strength(3.0F, 2.5F)), CommonAssets.DEFAULT_BLOCK);

    public static void init() {
    }

    private static Block register(String name, Block block, AssetProcessor p) {
        return Motherlode.register(
            Registerable.block(block, ItemGroup.REDSTONE),
            MotherlodeModule.id(name),
            block,
            p,
            CommonData.DEFAULT_BLOCK_LOOT_TABLE
        );
    }
}
