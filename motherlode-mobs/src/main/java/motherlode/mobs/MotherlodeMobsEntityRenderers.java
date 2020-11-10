package motherlode.mobs;

import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import motherlode.mobs.renderer.ArmadilloEntityRenderer;
import motherlode.mobs.renderer.LizardEntityRenderer;
import motherlode.mobs.renderer.RedstoneGolemEntityRenderer;
import motherlode.mobs.renderer.RedstoneMarauderEntityRenderer;
import motherlode.mobs.renderer.RedstoneSentryEntityRenderer;

public class MotherlodeMobsEntityRenderers {
    public static void init() {
        EntityRendererRegistry.INSTANCE.register(MotherlodeMobsEntities.ARMADILLO_ENTITY,
            (dispatcher, context) ->
                new ArmadilloEntityRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(MotherlodeMobsEntities.LIZARD_ENTITY,
            (dispatcher, context) ->
                new LizardEntityRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(MotherlodeMobsEntities.REDSTONE_GOLEM_ENTITY,
            ((entityRenderDispatcher, context) -> new RedstoneGolemEntityRenderer(entityRenderDispatcher)));
        EntityRendererRegistry.INSTANCE.register(MotherlodeMobsEntities.REDSTONE_MARAUDER_ENTITY,
            ((entityRenderDispatcher, context) -> new RedstoneMarauderEntityRenderer(entityRenderDispatcher)));
        EntityRendererRegistry.INSTANCE.register(MotherlodeMobsEntities.REDSTONE_SENTRY_ENTITY,
            ((entityRenderDispatcher, context) -> new RedstoneSentryEntityRenderer(entityRenderDispatcher)));
    }
}
