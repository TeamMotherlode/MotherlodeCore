package motherlode.core.potions;

import motherlode.core.registry.MotherlodePotions;
import net.fabricmc.api.ModInitializer;

public class MotherlodePotionsMod implements ModInitializer {

    @Override
    public void onInitialize() {

        MotherlodePotions.init();
    }
}