package motherlode.redstone;

import motherlode.redstone.gui.RedstoneTransmitterGuiDescription;
import motherlode.redstone.gui.RedstoneTransmitterScreen;
import motherlode.redstone.registry.MotherlodeScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

public class MotherlodeRedstoneClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        ScreenRegistry.register(MotherlodeScreenHandlers.REDSTONE_TRANSMITTER_TYPE, (ScreenRegistry.Factory<RedstoneTransmitterGuiDescription, RedstoneTransmitterScreen>) RedstoneTransmitterScreen::new);
    }
}