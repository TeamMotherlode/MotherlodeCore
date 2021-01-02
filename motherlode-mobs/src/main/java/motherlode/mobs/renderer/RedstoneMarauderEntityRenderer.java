package motherlode.mobs.renderer;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import motherlode.mobs.MotherlodeModule;
import motherlode.mobs.entity.RedstoneMarauderEntity;
import motherlode.mobs.model.RedstoneMarauderModel;

public class RedstoneMarauderEntityRenderer extends MobEntityRenderer<RedstoneMarauderEntity, RedstoneMarauderModel> {
    public RedstoneMarauderEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new RedstoneMarauderModel(), 1);
    }

    @Override
    public Identifier getTexture(RedstoneMarauderEntity entity) {
        return new Identifier(MotherlodeModule.MODID, "textures/entity/redstone_marauder/redstone_marauder.png");
    }

    protected void scale(RedstoneMarauderEntity redstoneMarauderEntity, MatrixStack matrixStack, float f) {
        super.scale(redstoneMarauderEntity, matrixStack, f);
        matrixStack.translate(0.0D, -0.7D, 0.0D);
    }
}
