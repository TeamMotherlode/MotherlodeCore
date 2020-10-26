package motherlode.core.entities.render;

import motherlode.core.entities.RedstoneSentryEntity;
import motherlode.core.entities.model.RedstoneSentryModel;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class RedstoneSentryEntityRenderer extends MobEntityRenderer<RedstoneSentryEntity, RedstoneSentryModel> {

    public RedstoneSentryEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new RedstoneSentryModel(), 1);
    }

    @Override
    public Identifier getTexture(RedstoneSentryEntity entity) {
        return new Identifier("motherlode","textures/entity/redstone_sentry/redstone_sentry.png");
    }
}
