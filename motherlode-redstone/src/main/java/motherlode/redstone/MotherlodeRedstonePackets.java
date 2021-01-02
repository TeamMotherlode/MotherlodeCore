package motherlode.redstone;

import net.minecraft.util.Identifier;
import net.fabricmc.fabric.api.network.PacketConsumer;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import motherlode.redstone.networking.RedstoneTransmitterSwapC2SPacket;

public class MotherlodeRedstonePackets {
    public static final Identifier REDSTONE_TRANSMITTER_SWAP_C2S = register("redstone_transmitter_swap", new RedstoneTransmitterSwapC2SPacket());

    public static void init() {
    }

    public static Identifier register(String name, PacketConsumer packet) {
        ServerSidePacketRegistry.INSTANCE.register(MotherlodeModule.id(name), packet);
        return MotherlodeModule.id(name);
    }
}
