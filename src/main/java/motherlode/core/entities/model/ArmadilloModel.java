package motherlode.core.entities.model;

// Made with Blockbench 3.5.4
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import motherlode.core.entities.ArmadilloEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class ArmadilloModel extends EntityModel<ArmadilloEntity> {
    private final ModelPart Tail;
    private final ModelPart LegBackLeft;
    private final ModelPart EarRight;
    private final ModelPart BodyBack;
    private final ModelPart Head;
    private final ModelPart EarLeft;
    private final ModelPart MainBody;
    private final ModelPart LegFrontLeft;
    //back right leg
    private final ModelPart LegBackLeft_1;
    //front right leg
    private final ModelPart LegFrontLeft_1;

    public ArmadilloModel() {
        textureWidth = 64;
        textureHeight = 32;

        Tail = new ModelPart(this);
        Tail.setPivot(0.0F, 2.5F, 10.5F);
        Tail.setTextureOffset(1, 25).addCuboid(-0.5F, -0.5F, 0.3F, 1.0F, 1.0F, 4.0F, 0.0F, false);

        LegBackLeft = new ModelPart(this);
        LegBackLeft.setPivot(2.3F, 2.5F, 6.5F);
        LegBackLeft.setTextureOffset(0, 0).addCuboid(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, true);

        EarRight = new ModelPart(this);
        EarRight.setPivot(0.0F, 2.1F, -4.0F);
        setRotationAngle(EarRight, 0.0F, 0.0F, 0.1707F);
        EarRight.setTextureOffset(0, 16).addCuboid(0.4F, -4.8F, -1.0F, 2.0F, 2.0F, 0.0F, 0.0F, true);

        BodyBack = new ModelPart(this);
        BodyBack.setPivot(0.0F, 0.5F, 3.5F);
        BodyBack.setTextureOffset(11, 18).addCuboid(-3.5F, -3.5F, 0.3F, 7.0F, 7.0F, 7.0F, 0.0F, false);

        Head = new ModelPart(this);
        Head.setPivot(0.0F, 2.1F, -4.0F);
        Head.setTextureOffset(0, 16).addCuboid(-2.0F, -3.0F, -5.0F, 4.0F, 4.0F, 5.0F, 0.0F, false);

        EarLeft = new ModelPart(this);
        EarLeft.setPivot(0.0F, 2.1F, -4.0F);
        setRotationAngle(EarLeft, 0.0F, 0.0F, -0.1707F);
        EarLeft.setTextureOffset(0, 16).addCuboid(-2.4F, -4.8F, -1.0F, 2.0F, 2.0F, 0.0F, 0.0F, false);

        MainBody = new ModelPart(this);
        MainBody.setPivot(0.0F, 0.0F, 0.0F);
        MainBody.setTextureOffset(0, 0).addCuboid(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

        LegFrontLeft = new ModelPart(this);
        LegFrontLeft.setPivot(2.8F, 2.5F, -2.0F);
        LegFrontLeft.setTextureOffset(0, 0).addCuboid(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, true);

        LegBackLeft_1 = new ModelPart(this);
        LegBackLeft_1.setPivot(-2.7F, 2.5F, 6.5F);
        LegBackLeft_1.setTextureOffset(0, 0).addCuboid(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);

        LegFrontLeft_1 = new ModelPart(this);
        LegFrontLeft_1.setPivot(-2.8F, 2.5F, -2.0F);
        LegFrontLeft_1.setTextureOffset(0, 0).addCuboid(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);
    }

    @Override
    public void setAngles(ArmadilloEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.Head.pitch = headPitch * 0.017453292F;
        this.Head.yaw = headYaw * 0.017453292F;
        this.MainBody.pitch = 1.5707964F;
        this.LegBackLeft_1.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.LegBackLeft.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.LegFrontLeft_1.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.LegFrontLeft.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
    }


    @Override
    public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        Tail.render(matrixStack, buffer, packedLight, packedOverlay);
        LegBackLeft.render(matrixStack, buffer, packedLight, packedOverlay);
        EarRight.render(matrixStack, buffer, packedLight, packedOverlay);
        BodyBack.render(matrixStack, buffer, packedLight, packedOverlay);
        Head.render(matrixStack, buffer, packedLight, packedOverlay);
        EarLeft.render(matrixStack, buffer, packedLight, packedOverlay);
        MainBody.render(matrixStack, buffer, packedLight, packedOverlay);
        LegFrontLeft.render(matrixStack, buffer, packedLight, packedOverlay);
        LegBackLeft_1.render(matrixStack, buffer, packedLight, packedOverlay);
        LegFrontLeft_1.render(matrixStack, buffer, packedLight, packedOverlay);
    }


    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.pitch = x;
        modelRenderer.yaw = y;
        modelRenderer.roll = z;
    }
}