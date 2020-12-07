package motherlode.core.block;

import motherlode.core.api.ArtificeProperties;
import motherlode.core.registry.MotherlodeBlocks;
import net.minecraft.block.Block;

public class MotherlodePaneBlock extends net.minecraft.block.PaneBlock {
    public MotherlodePaneBlock(Settings settings) {
        super(settings);
        MotherlodeBlocks.cutouts.add(this);
        MotherlodeBlocks.defaultLootTableList.add(this);
    }
}
