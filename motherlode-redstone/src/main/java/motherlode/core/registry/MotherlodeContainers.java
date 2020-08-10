package motherlode.core.registry;

import motherlode.core.Motherlode;
import motherlode.core.redstone.gui.RedstoneTransmitterGuiDescription;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;

@Environment(EnvType.CLIENT)
public class MotherlodeContainers {
    public static final ScreenHandlerType<RedstoneTransmitterGuiDescription> REDSTONE_TRANSMITTER_TYPE = ScreenHandlerRegistry.registerSimple(Motherlode.id("redstone_transmitter"), (syncId, inventory) -> new RedstoneTransmitterGuiDescription(syncId, inventory, ScreenHandlerContext.EMPTY));

	public static void init() {

    }
}