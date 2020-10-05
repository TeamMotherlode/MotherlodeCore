package motherlode.redstone;

import motherlode.base.Motherlode;
import motherlode.redstone.MotherlodeRedstoneMod;
import motherlode.redstone.gui.RedstoneTransmitterGuiDescription;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;

public class MotherlodeRedstoneScreenHandlers {
    public static final ScreenHandlerType<RedstoneTransmitterGuiDescription> REDSTONE_TRANSMITTER_TYPE = ScreenHandlerRegistry.registerExtended(Motherlode.id(MotherlodeRedstoneMod.MODID, "redstone_transmitter"), (syncId, inventory, buf) -> new RedstoneTransmitterGuiDescription(syncId, inventory, ScreenHandlerContext.create(inventory.player.world, buf.readBlockPos())));

	public static void init() {

    }
}