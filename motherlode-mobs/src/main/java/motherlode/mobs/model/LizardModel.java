package motherlode.mobs.model;

import motherlode.mobs.entity.LizardEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class LizardModel extends EntityModel<LizardEntity> {
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart leg_front_left;
    private final ModelPart head;
    private final ModelPart leg_front_right;
    private final ModelPart leg_back_left;
    private final ModelPart leg_back_right;

    public LizardModel() {
        textureWidth = 64;
        textureHeight = 32;

        body = new ModelPart(this);
        body.setPivot(0.0F, 23.5F, -1.0F);
        body.setTextureOffset(0, 0).addCuboid(-2.0F, -2.7F, -2.0F, 4.0F, 2.0F, 6.0F, 0.0F, false);

        tail = new ModelPart(this);
        tail.setPivot(0.0F, -2.0F, 6.0F);
        body.addChild(tail);
        setRotationAngle(tail, -0.2618F, 0.0F, 0.0F);
        tail.setTextureOffset(14, 0).addCuboid(-1.0F, 0.3176F, -1.9319F, 2.0F, 1.0F, 4.0F, 0.0F, false);

        leg_front_left = new ModelPart(this);
        leg_front_left.setPivot(1.4F, 23.0F, 0.2F);
        setRotationAngle(leg_front_left, 0.0F, 0.3491F, 0.6109F);
        leg_front_left.setTextureOffset(0, 8).addCuboid(0.5029F, -1.1761F, -2.3794F, 3.0F, 1.0F, 1.0F, 0.0F, false);

        head = new ModelPart(this);
        head.setPivot(0.0F, 21.0F, -1.1F);
        head.setTextureOffset(4, 8).addCuboid(-1.5F, -1.0F, -5.3F, 3.0F, 2.0F, 4.0F, 0.0F, false);
        head.setTextureOffset(0, 0).addCuboid(1.0F, -0.5F, -4.3F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        head.setTextureOffset(0, 0).addCuboid(-2.0F, -0.5F, -4.3F, 1.0F, 1.0F, 1.0F, 0.0F, true);

        leg_front_right = new ModelPart(this);
        leg_front_right.setPivot(-1.4F, 23.0F, 0.2F);
        setRotationAngle(leg_front_right, 0.0F, -0.3491F, -0.6109F);
        leg_front_right.setTextureOffset(0, 8).addCuboid(-3.5029F, -1.1761F, -2.3794F, 3.0F, 1.0F, 1.0F, 0.0F, true);

        leg_back_left = new ModelPart(this);
        leg_back_left.setPivot(1.4F, 23.0F, 3.8F);
        setRotationAngle(leg_back_left, 0.0F, -0.2618F, 0.6109F);
        leg_back_left.setTextureOffset(0, 8).addCuboid(-0.6988F, -1.1761F, -2.4319F, 3.0F, 1.0F, 1.0F, 0.0F, false);

        leg_back_right = new ModelPart(this);
        leg_back_right.setPivot(-1.4F, 23.0F, 3.8F);
        setRotationAngle(leg_back_right, 0.0F, 0.2618F, -0.6109F);
        leg_back_right.setTextureOffset(0, 8).addCuboid(-2.3012F, -1.1761F, -2.4319F, 3.0F, 1.0F, 1.0F, 0.0F, true);
    }

    @Override
    public void setAngles(LizardEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.head.pitch = headPitch * 0.017453292F;
        this.head.yaw = headYaw * 0.017453292F;
        this.body.pitch = 1.5707964F;
        this.leg_back_right.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.leg_back_left.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.leg_front_right.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.leg_front_left.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
//        //body.render(matrixStack, buffer, packedLight, packedOverlay);
//        leg_front_left.render(matrixStack, buffer, packedLight, packedOverlay);
//        //head.render(matrixStack, buffer, packedLight, packedOverlay);
//        leg_front_right.render(matrixStack, buffer, packedLight, packedOverlay);
//        leg_back_left.render(matrixStack, buffer, packedLight, packedOverlay);
//        leg_back_right.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.pitch = x;
        modelRenderer.yaw = y;
        modelRenderer.roll = z;
    }

}
