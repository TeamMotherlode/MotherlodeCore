package motherlode.redstone;

import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import motherlode.redstone.gui.RedstoneTransmitterGuiDescription;

public class MotherlodeRedstoneScreenHandlers {
    public static final ScreenHandlerType<RedstoneTransmitterGuiDescription> REDSTONE_TRANSMITTER_TYPE = ScreenHandlerRegistry.registerExtended(MotherlodeModule.id("redstone_transmitter"), (syncId, inventory, buf) -> new RedstoneTransmitterGuiDescription(syncId, inventory, ScreenHandlerContext.create(inventory.player.world, buf.readBlockPos())));

    public static void init() {
    }
}
