package motherlode.mobs.model;

// Made with Blockbench 3.5.4
// Exported for Minecraft version 1.15

import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import motherlode.mobs.MotherlodeModule;
import motherlode.mobs.entity.ArmadilloEntity;
import software.bernie.geckolib.animation.model.AnimatedEntityModel;
import software.bernie.geckolib.animation.render.AnimatedModelRenderer;
import software.bernie.geckolib.forgetofabric.ResourceLocation;

public class ArmadilloModel extends AnimatedEntityModel<ArmadilloEntity> {
    private final AnimatedModelRenderer Tail;
    private final AnimatedModelRenderer LegBackLeft;
    private final AnimatedModelRenderer BodyBack;
    private final AnimatedModelRenderer Head;
    private final AnimatedModelRenderer EarLeft;
    private final AnimatedModelRenderer EarRight;
    private final AnimatedModelRenderer MainBody;
    private final AnimatedModelRenderer LegFrontLeft;
    private final AnimatedModelRenderer LegBackLeft_1;
    private final AnimatedModelRenderer LegFrontLeft_1;

    public ArmadilloModel() {
        // Doesn't get remapped without this cast
        ((Model) this).textureWidth = 64;
        ((Model) this).textureHeight = 32;

        Tail = new AnimatedModelRenderer(this);
        Tail.setRotationPoint(0.0F, 19.5F, 9.0F);
        Tail.setTextureOffset(1, 25).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);
        Tail.setModelRendererName("Tail");
        this.registerModelRenderer(Tail);

        LegBackLeft = new AnimatedModelRenderer(this);
        LegBackLeft.setRotationPoint(2.7F, 19.5F, 4.5F);
        LegBackLeft.setTextureOffset(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, true);
        LegBackLeft.setModelRendererName("LegBackLeft");
        this.registerModelRenderer(LegBackLeft);

        BodyBack = new AnimatedModelRenderer(this);
        BodyBack.setRotationPoint(0.0F, 17.5F, 3.5F);
        BodyBack.setTextureOffset(11, 18).addBox(-3.5F, -3.5F, -1.5F, 7.0F, 7.0F, 7.0F, 0.0F, false);
        BodyBack.setModelRendererName("BodyBack");
        this.registerModelRenderer(BodyBack);

        Head = new AnimatedModelRenderer(this);
        Head.setRotationPoint(0.0F, 17.1F, -6.0F);
        Head.setTextureOffset(0, 16).addBox(-2.0F, -1.0F, -5.0F, 4.0F, 4.0F, 5.0F, 0.0F, false);
        Head.setModelRendererName("Head");
        this.registerModelRenderer(Head);

        EarLeft = new AnimatedModelRenderer(this);
        EarLeft.setRotationPoint(-1.0F, -1.0F, -1.0F);
        ((ModelPart) Head).addChild(EarLeft);
        setRotationAngle(EarLeft, 0.0F, 0.0F, -0.1707F);
        EarLeft.setTextureOffset(0, 16).addBox(-1.9241F, -1.6737F, 0.0F, 2.0F, 2.0F, 0.0F, 0.0F, false);
        EarLeft.setModelRendererName("EarLeft");
        this.registerModelRenderer(EarLeft);

        EarRight = new AnimatedModelRenderer(this);
        EarRight.setRotationPoint(1.0F, -1.0F, -1.0F);
        ((ModelPart) Head).addChild(EarRight);
        setRotationAngle(EarRight, 0.0F, 0.0F, 0.1707F);
        EarRight.setTextureOffset(0, 16).addBox(-0.0759F, -1.6737F, 0.0F, 2.0F, 2.0F, 0.0F, 0.0F, true);
        EarRight.setModelRendererName("EarRight");
        this.registerModelRenderer(EarRight);

        MainBody = new AnimatedModelRenderer(this);
        MainBody.setRotationPoint(0.0F, 17.0F, -1.0F);
        MainBody.setTextureOffset(0, 0).addBox(-4.0F, -4.0F, -5.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
        MainBody.setModelRendererName("MainBody");
        this.registerModelRenderer(MainBody);

        LegFrontLeft = new AnimatedModelRenderer(this);
        LegFrontLeft.setRotationPoint(2.8F, 19.5F, -4.0F);
        LegFrontLeft.setTextureOffset(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, true);
        LegFrontLeft.setModelRendererName("LegFrontLeft");
        this.registerModelRenderer(LegFrontLeft);

        LegBackLeft_1 = new AnimatedModelRenderer(this);
        LegBackLeft_1.setRotationPoint(-2.7F, 19.5F, 4.5F);
        LegBackLeft_1.setTextureOffset(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);
        LegBackLeft_1.setModelRendererName("LegBackLeft_1");
        this.registerModelRenderer(LegBackLeft_1);

        LegFrontLeft_1 = new AnimatedModelRenderer(this);
        LegFrontLeft_1.setRotationPoint(-2.8F, 19.5F, -4.0F);
        LegFrontLeft_1.setTextureOffset(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);
        LegFrontLeft_1.setModelRendererName("LegFrontLeft_1");
        this.registerModelRenderer(LegFrontLeft_1);

        this.rootBones.add(Tail);
        this.rootBones.add(LegBackLeft);
        this.rootBones.add(BodyBack);
        this.rootBones.add(Head);
        this.rootBones.add(MainBody);
        this.rootBones.add(LegFrontLeft);
        this.rootBones.add(LegBackLeft_1);
        this.rootBones.add(LegFrontLeft_1);
    }

    @Override
    public ResourceLocation getAnimationFileLocation() {
        return new ResourceLocation(MotherlodeModule.MODID, "animations/armadillo.animations.json");
    }
}
