package motherlode.copperdungeon;

import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.registry.Registry;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import motherlode.copperdungeon.particle.ZapParticle;
import motherlode.copperdungeon.particle.ZapParticleEffect;

public class MotherlodeCopperDungeonParticles {
    public static final ParticleType<ZapParticleEffect> ZAP_ARC = registerComplex("zap_arc", FabricParticleTypes.complex(ZapParticleEffect.PARAMETERS_FACTORY), ZapParticle.Factory::new);

    public static void init() {
    }

    private static <T extends ParticleEffect> ParticleType<T> registerComplex(String name, ParticleType<T> type, ParticleFactoryRegistry.PendingParticleFactory<T> factory) {
        Registry.register(Registry.PARTICLE_TYPE, MotherlodeModule.id(name), type);
        ParticleFactoryRegistry.getInstance().register(type, factory);
        return type;
    }
}
