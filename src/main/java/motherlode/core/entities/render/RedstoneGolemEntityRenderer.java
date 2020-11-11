package motherlode.core.entities.render;

import motherlode.core.entities.RedstoneGolemEntity;
import motherlode.core.entities.model.RedstoneGolemModel;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class RedstoneGolemEntityRenderer extends MobEntityRenderer<RedstoneGolemEntity, RedstoneGolemModel> {
    public RedstoneGolemEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new RedstoneGolemModel(), 1);
    }

    @Override
    public Identifier getTexture(RedstoneGolemEntity entity) {
        return new Identifier("motherlode","textures/entity/redstone_golem/redstone_golem.png");
    }
}
