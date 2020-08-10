package motherlode.core.redstone;

import motherlode.core.redstone.gui.RedstoneTransmitterGuiDescription;
import motherlode.core.redstone.gui.RedstoneTransmitterScreen;
import motherlode.core.registry.MotherlodeScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

public class MotherlodeRedstoneClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        ScreenRegistry.register(MotherlodeScreenHandlers.REDSTONE_TRANSMITTER_TYPE, (ScreenRegistry.Factory<RedstoneTransmitterGuiDescription, RedstoneTransmitterScreen>) RedstoneTransmitterScreen::new);
    }
}
