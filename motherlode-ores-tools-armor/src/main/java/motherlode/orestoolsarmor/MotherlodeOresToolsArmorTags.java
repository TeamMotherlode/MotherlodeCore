package motherlode.orestoolsarmor;

import motherlode.base.Motherlode;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;

public class MotherlodeOresToolsArmorTags {
    public static Tag<Item> GEMS = TagRegistry.item(Motherlode.id(MotherlodeOresToolsArmorMod.MODID, "gems"));

    public static void init() {
        // Called to load the class
    }
}
