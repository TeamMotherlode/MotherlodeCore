package motherlode.uncategorized.item;

import motherlode.uncategorized.api.ArtificeProperties;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class DefaultItem extends Item implements ArtificeProperties {
    private boolean hasDefaultItemModel;

    public DefaultItem(Settings settings) {
        this(true, settings);
    }

    public DefaultItem(boolean hasDefaultItemModel, Settings settings) {
        super(settings);
        this.hasDefaultItemModel = hasDefaultItemModel;
    }

    @Override
    public boolean hasDefaultItemModel() {
        return hasDefaultItemModel;
    }

    @Override
    public Block getBlockInstance() {
        return null;
    }

    @Override
    public boolean hasDefaultState() {
        return false;
    }

    @Override
    public boolean hasDefaultModel() {
        return false;
    }

    @Override
    public boolean hasDefaultLootTable() {
        return false;
    }
}