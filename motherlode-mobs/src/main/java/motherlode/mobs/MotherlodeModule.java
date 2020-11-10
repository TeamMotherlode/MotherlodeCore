package motherlode.mobs;

import net.minecraft.util.Identifier;
import net.fabricmc.api.ModInitializer;
import motherlode.base.Motherlode;
import org.apache.logging.log4j.Level;

public class MotherlodeModule implements ModInitializer {
    public static final String MODID = "motherlode-mobs";

    @Override
    public void onInitialize() {
        MotherlodeMobsEntities.init();
    }

    public static void log(Level level, CharSequence message) {
        Motherlode.log(level, "Motherlode Mobs", message);
    }

    public static void log(Level level, Object message) {
        Motherlode.log(level, "Motherlode Mobs", String.valueOf(message));
    }

    public static Identifier id(String name) {
        return Motherlode.id(MODID, name);
    }
}
