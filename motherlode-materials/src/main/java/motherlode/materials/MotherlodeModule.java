package motherlode.materials;

import net.minecraft.util.Identifier;
import net.fabricmc.api.ModInitializer;
import motherlode.base.Motherlode;
import org.apache.logging.log4j.Level;

public class MotherlodeModule implements ModInitializer {
    public static final String MODID = "motherlode-materials";

    @Override
    public void onInitialize() {
        MotherlodeMaterialsBlocks.init();
        MotherlodeMaterialsItems.init();
        MotherlodeMaterialsTags.init();
    }

    public static void log(Level level, CharSequence message) {
        Motherlode.log(level, "Motherlode Materials", message);
    }

    public static void log(Level level, Object message) {
        Motherlode.log(level, "Motherlode Materials", String.valueOf(message));
    }

    public static Identifier id(String name) {
        return new Identifier(MODID, name);
    }
}
