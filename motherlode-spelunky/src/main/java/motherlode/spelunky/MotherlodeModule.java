package motherlode.spelunky;

import net.fabricmc.api.ModInitializer;

public class MotherlodeModule implements ModInitializer {
    public static final String MODID = "motherlode-spelunky";

    @Override
    public void onInitialize() {
        MotherlodeSpelunkyBlocks.init();
    }
}
