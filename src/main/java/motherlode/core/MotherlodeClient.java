package motherlode.core;

import motherlode.core.gui.RedstoneTransmitterGuiDescription;
import motherlode.core.gui.RedstoneTransmitterScreen;
import motherlode.core.registry.MotherlodeAssets;
import motherlode.core.registry.MotherlodeBlocks;
import motherlode.core.registry.MotherlodeScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

@Environment(EnvType.CLIENT)
public class MotherlodeClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MotherlodeAssets.register(MotherlodeBlocks.defaultStateList, MotherlodeBlocks.defaultModelList, MotherlodeBlocks.defaultItemModelList);

		ScreenRegistry.<RedstoneTransmitterGuiDescription, RedstoneTransmitterScreen>register(MotherlodeScreenHandlers.REDSTONE_TRANSMITTER_TYPE, (gui, inventory, title) -> new RedstoneTransmitterScreen(gui, inventory.player, title));
	}
}