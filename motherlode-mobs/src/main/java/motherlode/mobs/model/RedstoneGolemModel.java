package motherlode.mobs.model;

import motherlode.mobs.entity.RedstoneGolemEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class RedstoneGolemModel extends EntityModel<RedstoneGolemEntity> {
    private final ModelPart arm_left;
    private final ModelPart leg_left;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart arm2;
    private final ModelPart arm_right;
    private final ModelPart leg_right;

    public RedstoneGolemModel() {
        textureWidth = 256;
        textureHeight = 128;

        arm_left = new ModelPart(this);
        arm_left.setPivot(45.0F, -21.0F, -1.0F);
        arm_left.setTextureOffset(0, 52).addCuboid(-25.0F, -13.0F, -2.0F, 14.0F, 24.0F, 12.0F, 0.0F, true);
        arm_left.setTextureOffset(52, 52).addCuboid(-20.0F, 11.0F, -2.0F, 11.0F, 22.0F, 12.0F, 0.0F, true);
        arm_left.setTextureOffset(100, 0).addCuboid(-13.0F, 33.0F, -1.0F, 3.0F, 7.0F, 5.0F, 0.0F, false);
        arm_left.setTextureOffset(116, 0).addCuboid(-18.0F, 33.0F, -1.0F, 3.0F, 6.0F, 5.0F, 0.0F, false);
        arm_left.setTextureOffset(100, 0).addCuboid(-13.0F, 33.0F, 5.0F, 3.0F, 7.0F, 5.0F, 0.0F, false);

        leg_left = new ModelPart(this);
        leg_left.setPivot(33.0F, 8.0F, -3.0F);
        leg_left.setTextureOffset(98, 58).addCuboid(-25.0F, -4.0F, 0.0F, 12.0F, 20.0F, 12.0F, 0.0F, true);

        head = new ModelPart(this);
        head.setPivot(3.0F, -32.0F, -13.0F);
        head.setTextureOffset(120, 8).addCuboid(-11.0F, -4.0F, -6.0F, 16.0F, 16.0F, 12.0F, 0.0F, false);

        body = new ModelPart(this);
        body.setPivot(3.0F, -32.0F, -13.0F);
        body.setTextureOffset(0, 0).addCuboid(-23.0F, -4.0F, 6.0F, 40.0F, 32.0F, 20.0F, 0.0F, false);
        body.setTextureOffset(120, 36).addCuboid(-14.0F, 28.0F, 9.0F, 22.0F, 8.0F, 14.0F, 0.0F, false);

        arm2 = new ModelPart(this);
        arm2.setPivot(-45.0F, -21.0F, -1.0F);
        arm2.setTextureOffset(164, 0).addCuboid(10.0F, 33.0F, -1.0F, 3.0F, 7.0F, 5.0F, 0.0F, true);
        arm2.setTextureOffset(190, 11).addCuboid(15.0F, 33.0F, -1.0F, 3.0F, 6.0F, 5.0F, 0.0F, true);
        arm2.setTextureOffset(164, 0).addCuboid(10.0F, 33.0F, 5.0F, 3.0F, 7.0F, 5.0F, 0.0F, true);

        arm_right = new ModelPart(this);
        arm_right.setPivot(-45.0F, -21.0F, -1.0F);
        arm_right.setTextureOffset(0, 52).addCuboid(11.0F, -13.0F, -2.0F, 14.0F, 24.0F, 12.0F, 0.0F, false);
        arm_right.setTextureOffset(52, 52).addCuboid(9.0F, 11.0F, -2.0F, 11.0F, 22.0F, 12.0F, 0.0F, false);
        arm_right.setTextureOffset(100, 0).addCuboid(10.0F, 33.0F, -1.0F, 3.0F, 7.0F, 5.0F, 0.0F, true);
        arm_right.setTextureOffset(116, 0).addCuboid(15.0F, 33.0F, -1.0F, 3.0F, 6.0F, 5.0F, 0.0F, true);
        arm_right.setTextureOffset(100, 0).addCuboid(10.0F, 33.0F, 5.0F, 3.0F, 7.0F, 5.0F, 0.0F, true);

        leg_right = new ModelPart(this);
        leg_right.setPivot(-33.0F, 8.0F, -3.0F);
        leg_right.setTextureOffset(98, 58).addCuboid(13.0F, -4.0F, 0.0F, 12.0F, 20.0F, 12.0F, 0.0F, false);
    }

    @Override
    public void setAngles(RedstoneGolemEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        arm_left.render(matrixStack, buffer, packedLight, packedOverlay);
        leg_left.render(matrixStack, buffer, packedLight, packedOverlay);
        head.render(matrixStack, buffer, packedLight, packedOverlay);
        body.render(matrixStack, buffer, packedLight, packedOverlay);
        arm2.render(matrixStack, buffer, packedLight, packedOverlay);
        arm_right.render(matrixStack, buffer, packedLight, packedOverlay);
        leg_right.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.pitch = x;
        modelRenderer.yaw = y;
        modelRenderer.roll = z;
    }
}
