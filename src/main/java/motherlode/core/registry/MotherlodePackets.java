package motherlode.core.registry;

import motherlode.core.packets.C2SRedstoneTransmitterSwapPacket;
import motherlode.core.packets.MotherlodeC2SPacket;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.util.Identifier;

public class MotherlodePackets {
	public static final Identifier C2S_REDSTONE_TRANSMITTER_SWAP = register(new C2SRedstoneTransmitterSwapPacket());

	public static void init() {
		// CALLED TO MAINTAIN REGISTRY ORDER
	}

	public static Identifier register(MotherlodeC2SPacket packet) {
		ServerSidePacketRegistry.INSTANCE.register(packet.getID(), packet);
		return packet.getID();
	}
}
