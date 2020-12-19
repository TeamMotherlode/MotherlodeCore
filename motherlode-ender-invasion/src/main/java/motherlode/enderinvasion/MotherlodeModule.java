package motherlode.enderinvasion;

import net.minecraft.util.Identifier;
import net.fabricmc.api.ModInitializer;
import motherlode.base.Motherlode;
import motherlode.enderinvasion.recipe.MotherlodeSpreadRecipes;
import org.apache.logging.log4j.Level;

public class MotherlodeModule implements ModInitializer {
    public static final String MODID = "motherlode-ender-invasion";

    @Override
    public void onInitialize() {
        EnderInvasion.initializeEnderInvasion();
        MotherlodeEnderInvasionBlocks.init();
        MotherlodeEnderInvasionTags.init();
        MotherlodeSpreadRecipes.register();
    }

    public static void log(Level level, CharSequence message) {
        Motherlode.log(level, "Motherlode Ender Invasion", message);
    }

    public static void log(Level level, Object message) {
        Motherlode.log(level, "Motherlode Ender Invasion", String.valueOf(message));
    }

    public static Identifier id(String name) {
        return Motherlode.id(MODID, name);
    }
}
