package motherlode.orestoolsarmor.item;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import motherlode.base.CommonData;
import motherlode.base.Motherlode;
import motherlode.base.api.DataProcessor;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;

public class DefaultGemItem extends Item implements DataProcessor {
    private final int color;

    public DefaultGemItem(int color, Item.Settings settings) {
        super(settings);

        this.color = color;
    }

    public int getColor() {
        return color;
    }

    @Override
    public void accept(ArtificeResourcePack.ServerResourcePackBuilder pack, Identifier id) {
        CommonData.ITEM_TAG.apply(Motherlode.id(CommonData.COMMON_NAMESPACE, id.getPath()))
            .accept(pack, id);
    }
}