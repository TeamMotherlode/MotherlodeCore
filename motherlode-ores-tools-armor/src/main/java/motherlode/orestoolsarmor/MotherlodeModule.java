package motherlode.orestoolsarmor;

import net.minecraft.util.Identifier;
import net.fabricmc.api.ModInitializer;
import motherlode.base.Motherlode;
import org.apache.logging.log4j.Level;

public class MotherlodeModule implements ModInitializer {
    public static final String MODID = "motherlode-ores-tools-armor";

    @Override
    public void onInitialize() {
        MotherlodeOresToolsArmorBlocks.init();
        MotherlodeOresToolsArmorItems.init();
        MotherlodeOresToolsArmorTags.init();
    }

    public static void log(Level level, CharSequence message) {
        Motherlode.log(level, "Motherlode Ores, Tools, & Armor", message);
    }

    public static void log(Level level, Object message) {
        Motherlode.log(level, "Motherlode Ores, Tools, & Armor", String.valueOf(message));
    }

    public static Identifier id(String name) {
        return Motherlode.id(MODID, name);
    }
}
