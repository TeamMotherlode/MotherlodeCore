package motherlode.redstone;

import motherlode.base.CommonArtificeProcessors;
import motherlode.base.Motherlode;
import motherlode.base.api.ArtificeProcessor;
import motherlode.redstone.MotherlodeRedstoneMod;
import motherlode.redstone.RedstoneTransmitterBlock;
import motherlode.uncategorized.MotherlodeUncategorized;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public class MotherlodeRedstoneBlocks {

    public static final Map<Identifier, ArtificeProcessor> ARTIFICE_PROCESSORS = new HashMap<>();

    public static final Block REDSTONE_TRANSMITTER = register("redstone_transmitter", new RedstoneTransmitterBlock(true, false, true, true, AbstractBlock.Settings.of(Material.METAL).requiresTool().strength(3.0F, 3.0F)), CommonArtificeProcessors.DEFAULT_BLOCK_STATE.andThen(CommonArtificeProcessors.BLOCK_ITEM));

    public static void init() {

    }
    private static Block register(String name, Block block, ArtificeProcessor p) {

        Identifier id = Motherlode.id(MotherlodeRedstoneMod.MODID, name);
        Registry.register(Registry.BLOCK, id, block);
        Registry.register(Registry.ITEM, id, new BlockItem(block, new Item.Settings().group(MotherlodeUncategorized.BLOCKS)));
        ARTIFICE_PROCESSORS.put(id, p);
        return block;
    }
}
