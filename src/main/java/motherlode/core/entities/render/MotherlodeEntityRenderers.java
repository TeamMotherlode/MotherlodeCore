package motherlode.core.entities.render;

import motherlode.core.registry.MotherlodeEntities;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.entity.EntityType;

public class MotherlodeEntityRenderers {
    public static void init() {
        EntityRendererRegistry.INSTANCE.register(MotherlodeEntities.ARMADILLO_ENTITY,
                (dispatcher, context) ->
                        new ArmadilloEntityRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(MotherlodeEntities.LIZARD_ENTITY,
                (dispatcher,context) ->
                new LizardEntityRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(MotherlodeEntities.REDSTONE_GOLEM_ENTITY,
                ((entityRenderDispatcher, context) -> new RedstoneGolemEntityRenderer(entityRenderDispatcher)));
        EntityRendererRegistry.INSTANCE.register(MotherlodeEntities.REDSTONE_MARAUDER_ENTITY,
                ((entityRenderDispatcher, context) -> new RedstoneMarauderEntityRenderer(entityRenderDispatcher)));
        EntityRendererRegistry.INSTANCE.register(MotherlodeEntities.REDSTONE_SENTRY_ENTITY,
                ((entityRenderDispatcher, context) -> new RedstoneSentryEntityRenderer(entityRenderDispatcher)));
    }
}
