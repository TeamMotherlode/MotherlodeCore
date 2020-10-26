package motherlode.mobs.renderer;

import motherlode.mobs.entity.ArmadilloEntity;
import motherlode.mobs.MotherlodeModule;
import motherlode.mobs.model.ArmadilloModel;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class ArmadilloEntityRenderer extends MobEntityRenderer<ArmadilloEntity, ArmadilloModel> {

    public ArmadilloEntityRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new ArmadilloModel(), 1);
    }

    @Override
    public Identifier getTexture(ArmadilloEntity entity) {
        return new Identifier(MotherlodeModule.MODID,"textures/entity/armadillo/armadillo.png");
    }

    protected void setupTransforms(ArmadilloEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.setupTransforms(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
    }


}
