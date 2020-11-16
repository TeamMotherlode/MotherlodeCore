package motherlode.copperdungeon.networking;

import java.io.IOException;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.util.math.Vec3d;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import motherlode.copperdungeon.particle.ZapParticle;

public class ZapS2CPacket implements Packet<ClientPlayPacketListener> {
    private Vec3d origin;
    private Vec3d target;

    public ZapS2CPacket(Vec3d origin, Vec3d target) {
        this.origin = origin;
        this.target = target;
    }

    @Override
    public void read(PacketByteBuf buf) throws IOException {
        this.origin = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
        this.target = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    @Override
    public void write(PacketByteBuf buf) throws IOException {
        buf.writeDouble(this.origin.getX());
        buf.writeDouble(this.origin.getY());
        buf.writeDouble(this.origin.getZ());

        buf.writeDouble(this.target.getX());
        buf.writeDouble(this.target.getY());
        buf.writeDouble(this.target.getZ());
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void apply(ClientPlayPacketListener listener) {
        MinecraftClient.getInstance().particleManager.addParticle(new ZapParticle(MinecraftClient.getInstance().world, this.origin, this.target));
    }
}
