package motherlode.mobs.model;

import motherlode.mobs.entity.RedstoneMarauderEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class RedstoneMarauderModel extends EntityModel<RedstoneMarauderEntity> {
    private final ModelPart arm_left;
    private final ModelPart lower;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart whirlwind;
    private final ModelPart rock1;
    private final ModelPart rock2;
    private final ModelPart rock3;
    private final ModelPart rock4;
    private final ModelPart arm_right;
    private final ModelPart lower2;

    public RedstoneMarauderModel() {
        textureWidth = 128;
        textureHeight = 128;

        arm_left = new ModelPart(this);
        arm_left.setPivot(18.0F, -4.0F, 0.0F);
        arm_left.setTextureOffset(78, 0).addCuboid(-6.0F, -6.0F, -5.0F, 11.0F, 13.0F, 14.0F, 0.0F, false);

        lower = new ModelPart(this);
        lower.setPivot(1.5F, 17.0F, 0.0F);
        arm_left.addChild(lower);
        lower.setTextureOffset(84, 27).addCuboid(-4.5F, -10.0F, -4.0F, 9.0F, 16.0F, 12.0F, 0.0F, false);
        lower.setTextureOffset(66, 3).addCuboid(-3.5F, 6.0F, -3.0F, 3.0F, 4.0F, 5.0F, 0.0F, false);
        lower.setTextureOffset(40, 3).addCuboid(1.5F, 6.0F, -3.0F, 2.0F, 5.0F, 4.0F, 0.0F, false);
        lower.setTextureOffset(52, 2).addCuboid(1.5F, 6.0F, 2.0F, 2.0F, 5.0F, 5.0F, 0.0F, false);

        head = new ModelPart(this);
        head.setPivot(0.0F, -10.0F, 1.0F);
        setRotationAngle(head, 0.0873F, 0.0F, 0.0F);
        head.setTextureOffset(0, 0).addCuboid(-7.0F, -8.0F, -5.0F, 14.0F, 8.0F, 12.0F, 0.0F, false);

        body = new ModelPart(this);
        body.setPivot(0.0F, 0.0F, 0.0F);
        setRotationAngle(body, 0.1745F, 0.0F, 0.0F);
        body.setTextureOffset(0, 20).addCuboid(-12.0F, -8.0F, -7.0F, 24.0F, 14.0F, 18.0F, 0.0F, false);
        body.setTextureOffset(0, 53).addCuboid(-10.0F, 6.0F, -5.0F, 20.0F, 6.0F, 14.0F, 0.0F, false);

        whirlwind = new ModelPart(this);
        whirlwind.setPivot(0.0F, 17.0F, 1.0F);


        rock1 = new ModelPart(this);
        rock1.setPivot(6.0F, 0.0F, 0.0F);
        whirlwind.addChild(rock1);
        rock1.setTextureOffset(22, 42).addCuboid(-2.0F, -2.0F, -3.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);

        rock2 = new ModelPart(this);
        rock2.setPivot(-6.0F, 2.0F, 0.0F);
        whirlwind.addChild(rock2);
        rock2.setTextureOffset(93, 43).addCuboid(-2.0F, -3.0F, -3.0F, 5.0F, 5.0F, 5.0F, 0.0F, false);

        rock3 = new ModelPart(this);
        rock3.setPivot(0.0F, 2.0F, -5.0F);
        whirlwind.addChild(rock3);
        rock3.setTextureOffset(13, 40).addCuboid(-2.0F, -2.0F, -3.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);

        rock4 = new ModelPart(this);
        rock4.setPivot(0.0F, 0.0F, 0.0F);
        whirlwind.addChild(rock4);
        rock4.setTextureOffset(13, 40).addCuboid(0.0F, 1.0F, 5.0F, 3.0F, 3.0F, 3.0F, 0.0F, false);

        arm_right = new ModelPart(this);
        arm_right.setPivot(-18.0F, -4.0F, 0.0F);
        arm_right.setTextureOffset(78, 0).addCuboid(-5.0F, -6.0F, -5.0F, 11.0F, 13.0F, 14.0F, 0.0F, true);

        lower2 = new ModelPart(this);
        lower2.setPivot(-1.5F, 17.0F, 0.0F);
        arm_right.addChild(lower2);
        lower2.setTextureOffset(84, 27).addCuboid(-4.5F, -10.0F, -4.0F, 9.0F, 16.0F, 12.0F, 0.0F, true);
        lower2.setTextureOffset(66, 3).addCuboid(0.5F, 6.0F, -3.0F, 3.0F, 4.0F, 5.0F, 0.0F, true);
        lower2.setTextureOffset(40, 3).addCuboid(-3.5F, 6.0F, -3.0F, 2.0F, 5.0F, 4.0F, 0.0F, true);
        lower2.setTextureOffset(52, 2).addCuboid(-3.5F, 6.0F, 2.0F, 2.0F, 5.0F, 5.0F, 0.0F, true);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        arm_left.render(matrixStack, buffer, packedLight, packedOverlay);
        head.render(matrixStack, buffer, packedLight, packedOverlay);
        body.render(matrixStack, buffer, packedLight, packedOverlay);
        whirlwind.render(matrixStack, buffer, packedLight, packedOverlay);
        arm_right.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.pitch = x;
        modelRenderer.yaw = y;
        modelRenderer.roll = z;
    }

    @Override
    public void setAngles(RedstoneMarauderEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}
