package motherlode.base.api;

import net.minecraft.block.Block;

@Deprecated
public interface ArtificeProperties {
    boolean hasDefaultState();

    boolean hasDefaultModel();

    boolean hasDefaultItemModel();

    boolean hasDefaultLootTable();

    Block getBlockInstance();
}
