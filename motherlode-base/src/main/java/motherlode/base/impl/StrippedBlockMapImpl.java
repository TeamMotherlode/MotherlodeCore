package motherlode.base.impl;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.block.Block;
import motherlode.base.api.woodtype.StrippedBlockMap;

public class StrippedBlockMapImpl implements StrippedBlockMap {
    public static final StrippedBlockMapImpl INSTANCE = new StrippedBlockMapImpl();

    private final Map<Block, Block> strippedBlocks;

    private StrippedBlockMapImpl() {
        this.strippedBlocks = new HashMap<>();
    }

    public void addStrippedBlock(Block block, Block strippedBlock) {
        this.strippedBlocks.put(block, strippedBlock);
    }

    public Block getStrippedBlock(Object block) {
        return this.strippedBlocks.get(block);
    }
}
