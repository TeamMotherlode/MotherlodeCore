package motherlode.core.entities.render;

import motherlode.core.registry.MotherlodeEntities;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

public class MotherlodeEntityRenderers {
    public static void init() {
        EntityRendererRegistry.INSTANCE.register(MotherlodeEntities.ARMADILLO_ENTITY,
                (dispatcher, context) ->
                        new ArmadilloEntityRenderer(dispatcher));
    }
}
