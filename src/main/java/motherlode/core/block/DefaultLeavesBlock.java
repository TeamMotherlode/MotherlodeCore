package motherlode.core.block;

import motherlode.core.api.ArtificeProperties;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.Direction;

public class DefaultLeavesBlock extends LeavesBlock implements ArtificeProperties {
    public final boolean hasDefaultState;
    public final boolean hasDefaultModel;
    public final boolean hasDefaultItemModel;
    public final boolean hasDefaultLootTable;

    public DefaultLeavesBlock(Settings settings) {
        this(true, true, true, true, settings);
    }

    public DefaultLeavesBlock(boolean hasDefaultState, boolean hasDefaultModel, boolean hasDefaultItemModel, boolean hasDefaultLootTable, Settings settings) {
        super(settings);

        this.hasDefaultState = hasDefaultState;
        this.hasDefaultModel = hasDefaultModel;
        this.hasDefaultItemModel = hasDefaultItemModel;
        this.hasDefaultLootTable = hasDefaultLootTable;
    }

    @Override
    public boolean hasDefaultState() {
        return hasDefaultState;
    }

    @Override
    public boolean hasDefaultModel() {
        return hasDefaultModel;
    }

    @Override
    public boolean hasDefaultItemModel() {
        return hasDefaultItemModel;
    }

    @Override
    public boolean hasDefaultLootTable() {
        return hasDefaultLootTable;
    }

    @Override
    public Block getBlockInstance() {
        return this;
    }
}
