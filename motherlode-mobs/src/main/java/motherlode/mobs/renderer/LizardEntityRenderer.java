package motherlode.mobs.renderer;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import motherlode.mobs.MotherlodeModule;
import motherlode.mobs.entity.LizardEntity;
import motherlode.mobs.model.LizardModel;

public class LizardEntityRenderer extends MobEntityRenderer<LizardEntity, LizardModel> {
    public LizardEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new LizardModel(), 1);
    }

    @Override
    public Identifier getTexture(LizardEntity entity) {
        return new Identifier(MotherlodeModule.MODID, "textures/entity/lizard/sand_lizard.png");
    }

    protected void scale(LizardEntity lizardEntity, MatrixStack matrixStack, float f) {
        super.scale(lizardEntity, matrixStack, f);
        //matrixStack.scale(0.8F, 0.8F, 0.8F);
        //matrixStack.translate(0.0D, 0.9D, 0.0D);
    }
}
