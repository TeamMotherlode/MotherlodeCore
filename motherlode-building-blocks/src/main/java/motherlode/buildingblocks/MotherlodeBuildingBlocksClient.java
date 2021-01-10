package motherlode.buildingblocks;

import motherlode.buildingblocks.screen.SawmillScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

public class MotherlodeBuildingBlocksClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(MotherlodeModule.SAWMILL_SCREEN_HANDLER, SawmillScreen::new);
    }
}
