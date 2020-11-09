package motherlode.core.block;

import motherlode.core.registry.MotherlodeBlocks;
import net.minecraft.block.AbstractBlock;

public class MotherlodeLadderBlock extends net.minecraft.block.LadderBlock {
    // Ladder block has protected Access access.
    public MotherlodeLadderBlock(AbstractBlock.Settings settings) {
        super(settings);
        MotherlodeBlocks.defaultLootTableList.add(this);
        MotherlodeBlocks.cutouts.add(this);
    }
}
