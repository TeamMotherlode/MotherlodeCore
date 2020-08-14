package motherlode.uncategorized.api;

import net.minecraft.block.Block;

public interface ArtificeProperties {
    boolean hasDefaultState();

    boolean hasDefaultModel();

    boolean hasDefaultItemModel();

    boolean hasDefaultLootTable();

    Block getBlockInstance();
}
