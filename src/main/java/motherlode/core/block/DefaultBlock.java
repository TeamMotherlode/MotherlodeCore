package motherlode.core.block;

import motherlode.core.api.ArtificeProperties;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;

public class DefaultBlock extends Block implements ArtificeProperties {
    public final boolean hasDefaultState;
    public final boolean hasDefaultModel;
    public final boolean hasDefaultItemModel;
    public final boolean hasDefaultLootTable;
    public final boolean transparent;

    public DefaultBlock(AbstractBlock.Settings settings) {
        this(true, true, true, true, settings);
    }

    public DefaultBlock(boolean hasDefaultState, boolean hasDefaultModel, boolean hasDefaultItemModel, boolean hasDefaultLootTable, AbstractBlock.Settings settings) {
        this(hasDefaultState, hasDefaultModel, hasDefaultItemModel, hasDefaultLootTable, false, settings);
    }
    public DefaultBlock(boolean hasDefaultState, boolean hasDefaultModel, boolean hasDefaultItemModel, boolean hasDefaultLootTable, boolean transparent, AbstractBlock.Settings settings) {
        super(settings);

        this.hasDefaultState = hasDefaultState;
        this.hasDefaultModel = hasDefaultModel;
        this.hasDefaultItemModel = hasDefaultItemModel;
        this.hasDefaultLootTable = hasDefaultLootTable;
        this.transparent = transparent;
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

    @Override
    @Environment(EnvType.CLIENT)
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        return stateFrom.isOf(this) ? this.transparent : super.isSideInvisible(state, stateFrom, direction);
    }
}
