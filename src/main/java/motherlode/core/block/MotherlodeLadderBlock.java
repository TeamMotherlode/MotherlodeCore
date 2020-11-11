package motherlode.core.block;

import motherlode.core.registry.MotherlodeBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.LadderBlock;

public class MotherlodeLadderBlock extends LadderBlock {
    // Ladder block has protected Access access.
    public MotherlodeLadderBlock(AbstractBlock.Settings settings) {
        super(settings);
        MotherlodeBlocks.defaultLootTableList.add(this);
        MotherlodeBlocks.cutouts.add(this);
    }
}
