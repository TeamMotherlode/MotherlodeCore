package motherlode.core.item;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class DefaultGemItem extends DefaultItem {
    private final int color;
    private final GemType gemType;

    public DefaultGemItem(int color, GemType gemType, Item.Settings settings) {
        super(settings);
        this.color = color;
        this.gemType = gemType;
    }

    public int getColor() {
        return color;
    }

    public GemType getType() {
        return gemType;
    }

    public enum GemType {
        Empty,
        Amethyst,
        Howlite,
        Ruby,
        Sapphire,
        Topaz,
        Onyx,
        Diamond,
        Emerald;

        public static GemType getType(Item item) {
            if(item instanceof DefaultGemItem)
                return ((DefaultGemItem)item).getType();
            else if(item == Items.DIAMOND)
                return Diamond;
            else if(item == Items.EMERALD)
                return Emerald;
            else
                return Empty;
        }
    }
}
