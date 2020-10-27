package motherlode.orestoolsarmor;

import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import motherlode.base.Motherlode;
import net.fabricmc.fabric.api.tag.TagRegistry;

public class MotherlodeOresToolsArmorTags {
    public static Tag<Item> GEMS = TagRegistry.item(Motherlode.id(MotherlodeModule.MODID, "gems"));

    public static void init() {
        // Called to load the class
    }
}
