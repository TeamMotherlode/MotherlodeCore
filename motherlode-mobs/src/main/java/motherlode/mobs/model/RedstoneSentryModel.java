package motherlode.mobs.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import motherlode.mobs.entity.RedstoneSentryEntity;

public class RedstoneSentryModel extends EntityModel<RedstoneSentryEntity> {
    private final ModelPart arm_left;
    private final ModelPart head;
    private final ModelPart crystal;
    private final ModelPart leg_right;
    private final ModelPart arm_right;
    private final ModelPart leg_left;
    private final ModelPart body;

    public RedstoneSentryModel() {
        textureWidth = 128;
        textureHeight = 64;

        arm_left = new ModelPart(this);
        arm_left.setPivot(8.0F, 6.0F, 1.0F);
        arm_left.setTextureOffset(54, 0).addCuboid(0.5F, -8.0F, -3.0F, 6.0F, 22.0F, 7.0F, 0.0F, false);
        arm_left.setTextureOffset(42, 42).addCuboid(1.5F, -11.0F, -2.0F, 4.0F, 6.0F, 5.0F, 0.0F, true);

        head = new ModelPart(this);
        head.setPivot(0.0F, 3.0F, -4.0F);
        head.setTextureOffset(0, 0).addCuboid(-4.5F, -3.0F, -7.0F, 9.0F, 8.0F, 7.0F, 0.0F, false);

        crystal = new ModelPart(this);
        crystal.setPivot(0.5F, -1.8F, -4.0F);
        head.addChild(crystal);
        setRotationAngle(crystal, -0.5236F, 0.0F, 0.0F);

        leg_right = new ModelPart(this);
        leg_right.setPivot(-5.0F, 14.0F, 1.5F);
        leg_right.setTextureOffset(0, 39).addCuboid(-2.0F, 0.0F, -3.5F, 6.0F, 10.0F, 7.0F, 0.0F, false);

        arm_right = new ModelPart(this);
        arm_right.setPivot(-8.0F, 6.0F, 1.0F);
        arm_right.setTextureOffset(54, 0).addCuboid(-6.5F, -8.0F, -3.0F, 6.0F, 22.0F, 7.0F, 0.0F, true);
        arm_right.setTextureOffset(42, 42).addCuboid(-5.5F, -11.0F, -2.0F, 4.0F, 6.0F, 5.0F, 0.0F, true);

        leg_left = new ModelPart(this);
        leg_left.setPivot(4.0F, 14.0F, 1.5F);
        leg_left.setTextureOffset(0, 39).addCuboid(-3.0F, 0.0F, -3.5F, 6.0F, 10.0F, 7.0F, 0.0F, true);

        body = new ModelPart(this);
        body.setPivot(0.5F, 8.0F, 0.0F);
        body.setTextureOffset(0, 15).addCuboid(-9.0F, -8.0F, -4.0F, 17.0F, 14.0F, 10.0F, 0.0F, false);
        body.setTextureOffset(26, 45).addCuboid(-4.0F, -3.0F, 6.0F, 7.0F, 7.0F, 1.0F, 0.0F, false);
    }

    @Override
    public void setAngles(RedstoneSentryEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        arm_left.render(matrixStack, buffer, packedLight, packedOverlay);
        head.render(matrixStack, buffer, packedLight, packedOverlay);
        leg_right.render(matrixStack, buffer, packedLight, packedOverlay);
        arm_right.render(matrixStack, buffer, packedLight, packedOverlay);
        leg_left.render(matrixStack, buffer, packedLight, packedOverlay);
        body.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.pitch = x;
        modelRenderer.yaw = y;
        modelRenderer.roll = z;
    }
}
