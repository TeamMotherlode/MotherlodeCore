package motherlode.copperdungeon.particle;

import java.util.Locale;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import motherlode.copperdungeon.MotherlodeCopperDungeonParticles;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class ZapParticleEffect implements ParticleEffect {
    public static final Codec<ZapParticleEffect> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
        Codec.DOUBLE.fieldOf("sx").forGetter(zapParticleEffect -> zapParticleEffect.source.x),
        Codec.DOUBLE.fieldOf("sy").forGetter(zapParticleEffect -> zapParticleEffect.source.y),
        Codec.DOUBLE.fieldOf("sz").forGetter(zapParticleEffect -> zapParticleEffect.source.z),
        Codec.DOUBLE.fieldOf("tx").forGetter(zapParticleEffect -> zapParticleEffect.target.x),
        Codec.DOUBLE.fieldOf("ty").forGetter(zapParticleEffect -> zapParticleEffect.target.y),
        Codec.DOUBLE.fieldOf("tz").forGetter(zapParticleEffect -> zapParticleEffect.target.z))
        .apply(instance, ZapParticleEffect::new));

    public static final Factory<ZapParticleEffect> PARAMETERS_FACTORY = new Factory<ZapParticleEffect>() {

        @Override
        public ZapParticleEffect read(ParticleType<ZapParticleEffect> type, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            float sx = (float) reader.readDouble();
            reader.expect(' ');
            float sy = (float) reader.readDouble();
            reader.expect(' ');
            float sz = (float) reader.readDouble();
            reader.expect(' ');
            float tx = (float) reader.readDouble();
            reader.expect(' ');
            float ty = (float) reader.readDouble();
            reader.expect(' ');
            float tz = (float) reader.readDouble();
            return new ZapParticleEffect(sx, sy, sz, tx, ty, tz);
        }

        @Override
        public ZapParticleEffect read(ParticleType<ZapParticleEffect> type, PacketByteBuf buf) {
            return new ZapParticleEffect(
                buf.readDouble(), buf.readDouble(), buf.readDouble(),
                buf.readDouble(), buf.readDouble(), buf.readDouble());
        }
    };

    private final Vec3d source;
    private final Vec3d target;

    public ZapParticleEffect(double sx, double sy, double sz, double tx, double ty, double tz) {
        this.source = new Vec3d(sx, sy, sz);
        this.target = new Vec3d(tx, ty, tz);
    }

    @Override
    public ParticleType<?> getType() {
        return MotherlodeCopperDungeonParticles.ZAP_ARC;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeDouble(this.source.x);
        buf.writeDouble(this.source.y);
        buf.writeDouble(this.source.z);
        buf.writeDouble(this.target.x);
        buf.writeDouble(this.target.y);
        buf.writeDouble(this.target.z);
    }

    @Override
    public String asString() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f %.2f %.2f", Registry.PARTICLE_TYPE.getId(this.getType()),
            this.source.x, this.source.y, this.source.z,
            this.target.x, this.target.y, this.target.z);
    }

    @Environment(EnvType.CLIENT)
    public Vec3d getSource() {
        return source;
    }

    @Environment(EnvType.CLIENT)
    public Vec3d getTarget() {
        return target;
    }
}
