package motherlode.core.item;

import net.minecraft.item.Item;

public class DefaultGemItem extends DefaultItem {
    private final int color;

    public DefaultGemItem(int color, Item.Settings settings) {
        super(settings);
        this.color = color;
    }

    public int getColor() {
        return color;
    }
}
