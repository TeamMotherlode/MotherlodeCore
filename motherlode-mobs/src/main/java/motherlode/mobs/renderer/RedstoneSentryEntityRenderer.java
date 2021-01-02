package motherlode.mobs.renderer;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import motherlode.mobs.MotherlodeModule;
import motherlode.mobs.entity.RedstoneSentryEntity;
import motherlode.mobs.model.RedstoneSentryModel;

public class RedstoneSentryEntityRenderer extends MobEntityRenderer<RedstoneSentryEntity, RedstoneSentryModel> {
    public RedstoneSentryEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new RedstoneSentryModel(), 1);
    }

    @Override
    public Identifier getTexture(RedstoneSentryEntity entity) {
        return new Identifier(MotherlodeModule.MODID, "textures/entity/redstone_sentry/redstone_sentry.png");
    }
}
