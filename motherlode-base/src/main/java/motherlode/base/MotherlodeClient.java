package motherlode.base;

import net.fabricmc.api.ClientModInitializer;

public class MotherlodeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MotherlodeInitEvents.CLIENT.invoker().initialize();
    }
}
