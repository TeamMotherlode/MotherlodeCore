package motherlode.core.packets;

import net.fabricmc.fabric.api.network.PacketConsumer;
import net.minecraft.util.Identifier;

public abstract class MotherlodeC2SPacket implements PacketConsumer {
	public abstract Identifier getID();
}
