package motherlode.core.registry;

import motherlode.core.Motherlode;
import motherlode.core.particle.ZapParticle;
import motherlode.core.particle.ZapParticleEffect;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.registry.Registry;

public class MotherlodeParticles {
    public static final ParticleType<ZapParticleEffect> ZAP_ARC =
            FabricParticleTypes.complex(ZapParticleEffect.PARAMETERS_FACTORY);

    public static void init() {
        // CALLED TO MAINTAIN REGISTRY ORDER
        registerComplex("zap_arc", ZAP_ARC, ZapParticle.Factory::new);
    }

    private static <T extends ParticleEffect> void registerComplex(String name, ParticleType<T> type,
                                                                   ParticleFactoryRegistry.PendingParticleFactory<T> factory){
        Registry.register(Registry.PARTICLE_TYPE, Motherlode.id(name), type);
        ParticleFactoryRegistry particleFactoryRegistry = ParticleFactoryRegistry.getInstance();
        ParticleFactoryRegistry.getInstance().register(type, factory);
    }
}
