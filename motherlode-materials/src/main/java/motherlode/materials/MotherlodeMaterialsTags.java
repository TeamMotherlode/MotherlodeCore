package motherlode.materials;

import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.fabricmc.fabric.api.tag.TagRegistry;

public class MotherlodeMaterialsTags {
    public static Tag<Item> GEMS = TagRegistry.item(MotherlodeModule.id("gems"));

    public static void init() {
        // Called to load the class
    }
}
