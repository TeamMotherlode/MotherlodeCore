package motherlode.core.registry;

import motherlode.core.Motherlode;
import motherlode.core.redstone.gui.RedstoneTransmitterGuiDescription;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;

public class MotherlodeScreenHandlers {
    public static final ScreenHandlerType<RedstoneTransmitterGuiDescription> REDSTONE_TRANSMITTER_TYPE = ScreenHandlerRegistry.registerExtended(Motherlode.id("redstone_transmitter"), (syncId, inventory, buf) -> new RedstoneTransmitterGuiDescription(syncId, inventory, ScreenHandlerContext.create(inventory.player.world, buf.readBlockPos())));

	public static void init() {

    }
}