package motherlode.spelunky;

import net.minecraft.util.Identifier;
import motherlode.base.Motherlode;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.Level;

public class MotherlodeModule implements ModInitializer {
    public static final String MODID = "motherlode-spelunky";

    @Override
    public void onInitialize() {
        MotherlodeSpelunkyBlocks.init();
    }

    public static void log(Level level, CharSequence message) {
        Motherlode.log(level, "Motherlode Spelunky", message);
    }

    public static void log(Level level, Object message) {
        Motherlode.log(level, "Motherlode Spelunky", String.valueOf(message));
    }

    public static Identifier id(String name) {
        return Motherlode.id(MODID, name);
    }
}
