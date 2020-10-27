package motherlode.mobs;

import net.fabricmc.api.ModInitializer;

public class MotherlodeModule implements ModInitializer {
    public static final String MODID = "motherlode-mobs";

    @Override
    public void onInitialize() {
        MotherlodeMobsEntities.init();
    }
}
