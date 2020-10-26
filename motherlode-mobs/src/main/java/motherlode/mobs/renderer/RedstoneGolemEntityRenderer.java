package motherlode.mobs.renderer;

import motherlode.mobs.MotherlodeModule;
import motherlode.mobs.entity.RedstoneGolemEntity;
import motherlode.mobs.model.RedstoneGolemModel;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class RedstoneGolemEntityRenderer extends MobEntityRenderer<RedstoneGolemEntity, RedstoneGolemModel> {
    public RedstoneGolemEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new RedstoneGolemModel(), 1);
    }

    @Override
    public Identifier getTexture(RedstoneGolemEntity entity) {
        return new Identifier(MotherlodeModule.MODID,"textures/entity/redstone_golem/redstone_golem.png");
    }
}
