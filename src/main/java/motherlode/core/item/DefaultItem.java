package motherlode.core.item;

import motherlode.core.api.ArtificeProperties;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class DefaultItem extends Item implements ArtificeProperties {
    public DefaultItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasDefaultItemModel() {
        return true;
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
}