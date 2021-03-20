package motherlode.redstone;

import net.minecraft.util.Identifier;
import net.fabricmc.api.ModInitializer;
import motherlode.base.Motherlode;
import org.apache.logging.log4j.Level;

public class MotherlodeModule implements ModInitializer {
    public static final String MODID = "motherlode-redstone";

    @Override
    public void onInitialize() {
        MotherlodeRedstoneBlocks.init();
        MotherlodeRedstoneBlockEntities.init();
        MotherlodeRedstoneScreenHandlers.init();
    }

    public static void log(Level level, CharSequence message) {
        Motherlode.log(level, "Motherlode Redstone", message);
    }

    public static void log(Level level, Object message) {
        Motherlode.log(level, "Motherlode Redstone", String.valueOf(message));
    }

    public static Identifier id(String name) {
        return new Identifier(MODID, name);
    }
}
