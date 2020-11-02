package motherlode.buildingblocks;

import net.minecraft.util.Identifier;
import motherlode.base.Motherlode;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.Level;

public class MotherlodeModule implements ModInitializer {
    public static final String MODID = "motherlode-building-blocks";

    @Override
    public void onInitialize() {
        MotherlodeBuildingBlocks.init();
    }

    public static void log(Level level, CharSequence message) {
        Motherlode.log(level, "Motherlode Building Blocks", message);
    }

    public static void log(Level level, Object message) {
        Motherlode.log(level, "Motherlode Building Blocks", String.valueOf(message));
    }

    public static Identifier id(String name) {
        return Motherlode.id(MODID, name);
    }
}
