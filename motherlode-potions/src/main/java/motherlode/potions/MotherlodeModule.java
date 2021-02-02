package motherlode.potions;

import net.minecraft.util.Identifier;
import net.fabricmc.api.ModInitializer;
import motherlode.base.Motherlode;
import org.apache.logging.log4j.Level;

public class MotherlodeModule implements ModInitializer {
    public static final String MODID = "motherlode-potions";

    @Override
    public void onInitialize() {
        MotherlodePotions.init();
        Motherlode.getAssetsManager().addAssets(MotherlodePotionsClient::generateAssets);
    }

    public static void log(Level level, CharSequence message) {
        Motherlode.log(level, "Motherlode Potions", message);
    }

    public static void log(Level level, Object message) {
        Motherlode.log(level, "Motherlode Potions", String.valueOf(message));
    }

    public static Identifier id(String name) {
        return new Identifier(MODID, name);
    }
}
