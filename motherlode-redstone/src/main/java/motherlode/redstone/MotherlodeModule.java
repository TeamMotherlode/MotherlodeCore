package motherlode.redstone;

import net.fabricmc.api.ModInitializer;

public class MotherlodeModule implements ModInitializer {
    public static final String MODID = "motherlode-redstone";

    @Override
    public void onInitialize() {
        MotherlodeRedstoneBlocks.init();
        MotherlodeBlockEntities.init();
        MotherlodeRedstoneScreenHandlers.init();
    }
}
