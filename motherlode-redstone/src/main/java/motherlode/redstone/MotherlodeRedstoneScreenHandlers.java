package motherlode.redstone;

import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import motherlode.base.Motherlode;
import motherlode.redstone.gui.RedstoneTransmitterGuiDescription;

public class MotherlodeRedstoneScreenHandlers {
    public static final ScreenHandlerType<RedstoneTransmitterGuiDescription> REDSTONE_TRANSMITTER_TYPE = ScreenHandlerRegistry.registerExtended(Motherlode.id(MotherlodeModule.MODID, "redstone_transmitter"), (syncId, inventory, buf) -> new RedstoneTransmitterGuiDescription(syncId, inventory, ScreenHandlerContext.create(inventory.player.world, buf.readBlockPos())));

    public static void init() {
    }
}
