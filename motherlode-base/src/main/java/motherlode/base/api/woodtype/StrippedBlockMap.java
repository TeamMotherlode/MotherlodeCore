package motherlode.base.api.woodtype;

import net.minecraft.block.Block;
import motherlode.base.impl.StrippedBlockMapImpl;

public interface StrippedBlockMap {
    StrippedBlockMap INSTANCE = StrippedBlockMapImpl.INSTANCE;

    void addStrippedBlock(Block block, Block strippedBlock);
}
