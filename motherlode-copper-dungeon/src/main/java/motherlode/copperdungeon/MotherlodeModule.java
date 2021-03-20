package motherlode.copperdungeon;

import net.minecraft.util.Identifier;
import net.fabricmc.api.ModInitializer;
import motherlode.base.Motherlode;
import org.apache.logging.log4j.Level;

public class MotherlodeModule implements ModInitializer {
    public static final String MODID = "motherlode-copper-dungeon";

    @Override
    public void onInitialize() {
        MotherlodeCopperDungeonBlocks.init();
        MotherlodeCopperDungeonBlockEntities.init();
    }

    public static void log(Level level, CharSequence message) {
        Motherlode.log(level, "Motherlode Copper Dungeon", message);
    }

    public static void log(Level level, Object message) {
        Motherlode.log(level, "Motherlode Copper Dungeon", String.valueOf(message));
    }

    public static Identifier id(String name) {
        return new Identifier(MODID, name);
    }
}
