package motherlode.redstone;

import motherlode.base.CommonArtificeProcessors;
import motherlode.base.Motherlode;
import motherlode.base.api.ArtificeProcessor;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class MotherlodeRedstoneBlocks {

    public static final List<Pair<Identifier, ArtificeProcessor>> ARTIFICE_PROCESSORS = new ArrayList<>();

    public static final Block REDSTONE_TRANSMITTER = register("redstone_transmitter", new RedstoneTransmitterBlock(true, false, true, true, AbstractBlock.Settings.of(Material.METAL).requiresTool().strength(3.0F, 3.0F)), CommonArtificeProcessors.DEFAULT_BLOCK_STATE.andThen(CommonArtificeProcessors.BLOCK_ITEM));

    public static void init() {

    }
    private static Block register(String name, Block block, ArtificeProcessor p) {

        Identifier id = Motherlode.id(MotherlodeRedstoneMod.MODID, name);
        Registry.register(Registry.BLOCK, id, block);
        Registry.register(Registry.ITEM, id, new BlockItem(block, new Item.Settings().group(ItemGroup.REDSTONE)));
        ARTIFICE_PROCESSORS.add(new Pair<>(id, p));
        return block;
    }
}
