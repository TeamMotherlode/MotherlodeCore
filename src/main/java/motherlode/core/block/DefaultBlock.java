package motherlode.core.block;

import motherlode.core.api.ArtificeProperties;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;

public class DefaultBlock extends Block implements ArtificeProperties {
    public final boolean hasDefaultState;
    public final boolean hasDefaultModel;
    public final boolean hasDefaultItemModel;

    public DefaultBlock(AbstractBlock.Settings settings) {
        this(true, true, true, settings);
    }

    public DefaultBlock(boolean hasDefaultState, boolean hasDefaultModel, boolean hasDefaultItemModel, AbstractBlock.Settings settings) {
        super(settings);

        this.hasDefaultState = hasDefaultState;
        this.hasDefaultModel = hasDefaultModel;
        this.hasDefaultItemModel = hasDefaultItemModel;
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
    public Block getBlockInstance() {
        return this;
    }
}
