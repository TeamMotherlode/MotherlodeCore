package motherlode.core.particle;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import motherlode.core.util.PositionUtilities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

public class ZapParticle extends SpriteBillboardParticle {
    private static final Random RANDOM = new Random();
    private static final int STEPS = 8;
    private static final float VARIANCE = 0.25f;
    private static final float THICKNESS = 0.01f;

    private static final float[] ZAP_MIDDLE_COLOR = { 1.f, 1.f, 1.f, 1.f};
    private static final float[] ZAP_OUTSIDE_COLOR = { 0.2f, 0.5f, 1.f, 0.5f};

    private final Vec3d target;
    private final Vec3d source;
    private final Vec3d[] vertices;

    public ZapParticle(ClientWorld clientWorld, Vec3d source, Vec3d target) {
        super(clientWorld, source.x, source.y, source.z);
        this.target = target;
        this.source = source;
        this.vertices = new Vec3d[STEPS];
        this.maxAge = (RANDOM.nextInt() % 6) + 2;
        generateVertices();
    }

    public ZapParticle(ClientWorld clientWorld, Vec3d source) {
        this(clientWorld, source, source.add(
                1 - (RANDOM.nextDouble() * 2),
                1 - (RANDOM.nextDouble() * 2),
                1 - (RANDOM.nextDouble() * 2)));
    }

    private void generateVertices() {
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
        matrixStack.translate(this.x - camera.getPos().x, this.y - camera.getPos().y, this.z - camera.getPos().z);
        VertexConsumerProvider.Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEffectVertexConsumers();
        VertexConsumer linesConsumer = immediate.getBuffer(RenderLayer.getLines());

        for(int i = 1; i < STEPS; i++){
            Vec3d vertex = vertices[i-1];
            Vec3d connection = vertices[i];

            Vec3d campos = camera.getPos();
            vertex = vertex.subtract(campos);
            connection = connection.subtract(campos);

            linesConsumer.vertex(vertex.x, vertex.y, vertex.z)
                    .color(ZAP_MIDDLE_COLOR[0], ZAP_MIDDLE_COLOR[1], ZAP_MIDDLE_COLOR[2], ZAP_MIDDLE_COLOR[3]).next();
            linesConsumer.vertex(connection.x, connection.y, connection.z)
                    .color(ZAP_MIDDLE_COLOR[0], ZAP_MIDDLE_COLOR[1], ZAP_MIDDLE_COLOR[2], ZAP_MIDDLE_COLOR[3]).next();

            //Render outside lines for a clean gradient

            vertex = vertex.add(0, THICKNESS, 0);
            connection = connection.add(0, THICKNESS, 0);

            linesConsumer.vertex(vertex.x, vertex.y, vertex.z)
                    .color(ZAP_OUTSIDE_COLOR[0], ZAP_OUTSIDE_COLOR[1], ZAP_OUTSIDE_COLOR[2], ZAP_OUTSIDE_COLOR[3]).next();
            linesConsumer.vertex(connection.x, connection.y, connection.z)
                    .color(ZAP_OUTSIDE_COLOR[0], ZAP_OUTSIDE_COLOR[1], ZAP_OUTSIDE_COLOR[2], ZAP_OUTSIDE_COLOR[3]).next();

            vertex = vertex.subtract( 0, 2*THICKNESS, 0 );
            connection = connection.subtract( 0, 2*THICKNESS, 0 );

            linesConsumer.vertex(vertex.x, vertex.y, vertex.z)
                    .color(ZAP_OUTSIDE_COLOR[0], ZAP_OUTSIDE_COLOR[1], ZAP_OUTSIDE_COLOR[2], ZAP_OUTSIDE_COLOR[3]).next();
            linesConsumer.vertex(connection.x, connection.y, connection.z)
                    .color(ZAP_OUTSIDE_COLOR[0], ZAP_OUTSIDE_COLOR[1], ZAP_OUTSIDE_COLOR[2], ZAP_OUTSIDE_COLOR[3]).next();


        }
        immediate.draw();
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.CUSTOM;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<ZapParticleEffect> {
        public Factory(SpriteProvider spriteProvider) {
        }

        @Override
        public Particle createParticle(ZapParticleEffect parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            Vec3d source = new Vec3d(x, y, z);
            Vec3d target = source.add(RANDOM.nextDouble(), RANDOM.nextDouble(), RANDOM.nextDouble());
            ZapParticle zapParticle = new ZapParticle(world, source, target);
            return zapParticle;
        }
    }
}
