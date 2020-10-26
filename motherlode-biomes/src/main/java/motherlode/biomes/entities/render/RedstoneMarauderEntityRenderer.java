package motherlode.core.entities.render;

import motherlode.core.entities.RedstoneMarauderEntity;
import motherlode.core.entities.model.RedstoneMarauderModel;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class RedstoneMarauderEntityRenderer extends MobEntityRenderer<RedstoneMarauderEntity, RedstoneMarauderModel> {
    public RedstoneMarauderEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new RedstoneMarauderModel(), 1);
    }

    @Override
    public Identifier getTexture(RedstoneMarauderEntity entity) {
        return new Identifier("motherlode","textures/entity/redstone_marauder/redstone_marauder.png");
    }

    protected void scale(RedstoneMarauderEntity redstoneMarauderEntity, MatrixStack matrixStack, float f) {
        super.scale(redstoneMarauderEntity, matrixStack, f);
        matrixStack.translate(0.0D, -0.7D, 0.0D);
    }
}
