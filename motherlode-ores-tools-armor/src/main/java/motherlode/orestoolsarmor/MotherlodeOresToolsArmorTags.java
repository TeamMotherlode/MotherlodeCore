package motherlode.orestoolsarmor;

import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.fabricmc.fabric.api.tag.TagRegistry;

public class MotherlodeOresToolsArmorTags {
    public static Tag<Item> GEMS = TagRegistry.item(MotherlodeModule.id("gems"));

    public static void init() {
        // Called to load the class
    }
}
