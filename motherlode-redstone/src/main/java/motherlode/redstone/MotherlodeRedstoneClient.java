package motherlode.redstone;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import motherlode.redstone.gui.RedstoneTransmitterGuiDescription;
import motherlode.redstone.gui.RedstoneTransmitterScreen;

public class MotherlodeRedstoneClient implements ClientModInitializer {
    @Override
    @SuppressWarnings("RedundantCast")
    public void onInitializeClient() {
        ScreenRegistry.register(MotherlodeRedstoneScreenHandlers.REDSTONE_TRANSMITTER_TYPE, (ScreenRegistry.Factory<RedstoneTransmitterGuiDescription, RedstoneTransmitterScreen>) RedstoneTransmitterScreen::new);
    }
}
