package motherlode.core.block;

import motherlode.core.registry.MotherlodeBlocks;
import net.minecraft.block.AbstractBlock;

public class LadderBlock extends net.minecraft.block.LadderBlock {
    // Ladder block has protected Access access.
    public LadderBlock(AbstractBlock.Settings settings) {
        super(settings);
        MotherlodeBlocks.defaultLootTableList.add(this);
    }
}
