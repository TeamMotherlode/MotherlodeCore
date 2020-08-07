package motherlode.core.assets;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import motherlode.core.Motherlode;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public enum ItemModel {

    DEFAULT("item/generated", "item/"),
    BLOCK(null, null),
    FLAT_BLOCK("item/generated", "block/"),
    HANDHELD("item/handheld", "item/"),
    MANUAL(null, null);

    String parent, texturePrefix;

    ItemModel(String parent, String texturePrefix) {
        this.parent = parent;
        this.texturePrefix = texturePrefix;
    }

    public void register(ArtificeResourcePack.ClientResourcePackBuilder pack, String id) {
        if (this == BLOCK)
            pack.addItemModel(Motherlode.id(id), model -> model
                    .parent(Motherlode.id("block/"+id))
            );
        else if (this != MANUAL)
            pack.addItemModel(Motherlode.id(id), model -> model
                .parent(new Identifier(parent))
                .texture("layer0", Motherlode.id(texturePrefix +id))
            );
    }

}
