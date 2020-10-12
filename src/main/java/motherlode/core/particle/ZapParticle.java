package motherlode.core.particle;

import com.mojang.blaze3d.systems.RenderSystem;
import motherlode.core.util.PositionUtilities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.Model;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.client.render.entity.ElderGuardianEntityRenderer;
import net.minecraft.client.render.entity.model.GuardianEntityModel;
import net.minecraft.client.render.model.SpriteAtlasManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import sun.security.provider.certpath.Vertex;

import java.util.Random;

public class ZapParticle extends SpriteBillboardParticle {
    private static final Random RANDOM = new Random();
    private static final int STEPS = 8;
    private static final float VARIANCE = 0.25f;
    private static final int LIGHT = 13 << 4;
    private static final float THICKNESS = 0.01f;

    private final Vec3d target;
    private final Vec3d source;
    private final Vec3d[] vertices;
    private final Model model;
    private final RenderLayer LAYER;

    public ZapParticle(ClientWorld clientWorld, Vec3d source, Vec3d target) {
        super(clientWorld, source.x, source.y, source.z);
        this.target = target;
        this.source = source;
        this.vertices = new Vec3d[STEPS];
        this.model = new GuardianEntityModel();
        this.LAYER = RenderLayer.getEntityTranslucent(ElderGuardianEntityRenderer.TEXTURE);
        generateVertices();
    }

    private void generateVertices(){
        //Generate line from source towards target
        for(int i = 0; i < STEPS; i++){
            Vec3d lerped = PositionUtilities.fromLerpedPosition(this.source, this.target, (float)i / (STEPS - 1));

            if(i != 0 && i != STEPS-1)
                lerped = lerped.add(
                        (1 - RANDOM.nextDouble() * 2) * VARIANCE,
                        (1 - RANDOM.nextDouble() * 2) * VARIANCE,
                        (1 - RANDOM.nextDouble() * 2) * VARIANCE);

            vertices[i] = lerped;
        }
    }

    @Override
    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {

        MatrixStack matrixStack = new MatrixStack();
        //matrixStack.multiply(camera.getRotation());
        matrixStack.translate(this.x - camera.getPos().x, this.y - camera.getPos().y, this.z - camera.getPos().z);
        VertexConsumerProvider.Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
        VertexConsumer linesConsumer = immediate.getBuffer(RenderLayer.getLines());

        for(int i = 1; i < STEPS; i++){
            Vec3d vertex = vertices[i-1];
            Vec3d connection = vertices[i];

            vertex = vertex.subtract(camera.getPos());
            connection = connection.subtract(camera.getPos());

            linesConsumer.vertex(vertex.x, vertex.y, vertex.z).color(1.f, 1.f, 1.f, 1.f).next();
            linesConsumer.vertex(connection.x, connection.y, connection.z).color(1.f, 1.f, 1.f, 1.f).next();

            vertex = vertex.add(0, THICKNESS, 0);
            connection = connection.add(0, THICKNESS, 0);

            linesConsumer.vertex(vertex.x, vertex.y, vertex.z).color(0.2f, 0.5f, 1.f, 0.5f).next();
            linesConsumer.vertex(connection.x, connection.y, connection.z).color(0.2f, 0.5f, 1.f, 0.5f).next();

            vertex = vertex.subtract(0, 2*THICKNESS, 0);
            connection = connection.subtract(0, 2*THICKNESS, 0);

            linesConsumer.vertex(vertex.x, vertex.y, vertex.z).color(0.2f, 0.5f, 1.f, 0.5f).next();
            linesConsumer.vertex(connection.x, connection.y, connection.z).color(0.2f, 0.5f, 1.f, 0.5f).next();


        }

        //VertexConsumer vertexConsumer2 = immediate.getBuffer(this.LAYER);
        //this.model.render(matrixStack, vertexConsumer2, 15728880, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1);
        immediate.draw();
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.CUSTOM;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<ZapParticleEffect> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(ZapParticleEffect parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            Vec3d source = new Vec3d(x, y, z);
            Vec3d target = source.add(RANDOM.nextDouble(), RANDOM.nextDouble(), RANDOM.nextDouble());
            ZapParticle zapParticle = new ZapParticle(world, source, target);
            zapParticle.setSprite(MinecraftClient.getInstance().getItemRenderer().getModels().getSprite(new ItemStack(Items.ITEM_FRAME, 1)));
            return zapParticle;
        }
    }
}
