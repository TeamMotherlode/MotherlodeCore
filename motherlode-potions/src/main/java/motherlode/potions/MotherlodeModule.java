package motherlode.potions;

import net.fabricmc.api.ModInitializer;

public class MotherlodeModule implements ModInitializer {
    public static final String MODID = "motherlode-potions";

    @Override
    public void onInitialize() {
        MotherlodePotions.init();
    }
}