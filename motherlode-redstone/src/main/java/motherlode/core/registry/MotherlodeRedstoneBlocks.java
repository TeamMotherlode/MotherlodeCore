package motherlode.core.registry;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import motherlode.core.redstone.RedstoneTransmitterBlock;

public class MotherlodeRedstoneBlocks {

    public static final Block REDSTONE_TRANSMITTER = MotherlodeBlocks.register("redstone_transmitter", new RedstoneTransmitterBlock(true, false, true, true, AbstractBlock.Settings.of(Material.METAL).requiresTool().strength(3.0F, 3.0F)));

    public static void init() {

    }
}
