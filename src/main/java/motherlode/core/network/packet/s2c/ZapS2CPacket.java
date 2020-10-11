package motherlode.core.network.packet.s2c;

import motherlode.core.util.PositionUtilities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.io.IOException;

public class ZapS2CPacket implements Packet<ClientPlayPacketListener> {
    private BlockPos origin;
    private Vec3d target;

    public ZapS2CPacket(BlockPos origin, Vec3d target){
        this.origin = origin;
        this.target = target;
    }

    @Override
    public void read(PacketByteBuf buf) throws IOException {
        this.origin = buf.readBlockPos();
        this.target = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    @Override
    public void write(PacketByteBuf buf) throws IOException {
        buf.writeBlockPos(this.origin);

        buf.writeDouble(this.target.getX());
        buf.writeDouble(this.target.getY());
        buf.writeDouble(this.target.getZ());
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void apply(ClientPlayPacketListener listener) {
        Vec3d from = new Vec3d(this.origin.getX() + 0.5f, this.origin.getY() + 0.85f, this.origin.getZ() + 0.5f);
        for(float i = 0; i < 1.f; i += 0.05f){
            Vec3d lerped = PositionUtilities.fromLerpedPosition(from, this.target, i);
                    MinecraftClient.getInstance().particleManager.addParticle(
                            ParticleTypes.ENCHANTED_HIT, lerped.x, lerped.y, lerped.z,
                            0.D, 0.D, 0.D);
        }
    }
}
