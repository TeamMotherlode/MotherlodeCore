package motherlode.mobs;

import net.fabricmc.api.ClientModInitializer;

public class MotherlodeMobsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MotherlodeMobsEntityRenderers.init();
    }
}
