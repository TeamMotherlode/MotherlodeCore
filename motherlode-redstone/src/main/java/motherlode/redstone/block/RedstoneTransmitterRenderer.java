package motherlode.redstone.block;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GraphicsMode;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.math.Vector4f;
import motherlode.materials.MotherlodeMaterialsItems;
import motherlode.redstone.MotherlodeModule;

public class RedstoneTransmitterRenderer implements BlockEntityRenderer<RedstoneTransmitterBlockEntity> {
    public RedstoneTransmitterRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public void render(RedstoneTransmitterBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        MinecraftClient client = MinecraftClient.getInstance();
        VertexConsumer buffer;
        if (client.options.graphicsMode == GraphicsMode.FABULOUS)
            buffer = vertexConsumers.getBuffer(RenderLayer.getTranslucentMovingBlock());
        else
            buffer = vertexConsumers.getBuffer(RenderLayer.getTranslucent());

        Matrix4f model = matrices.peek().getModel();
        Matrix3f normal = matrices.peek().getNormal();

        if (!entity.isEmpty()) {
            for (int i = 0; i < entity.size(); i++) {
                if (entity.getStack(i).isEmpty())
                    continue;

                Item item = entity.getStack(i).getItem();

                renderRect(model, normal, buffer, light + 2, 0xFFFFFF, getGemUV(item), new Vec3f((i % 2) * 14, 14, (i / 2) * 14), new Vec3f(2f, 2f, 2f));
            }
        }
    }

    private Vector4f[] getGemUV(Item gemItem) {
        Sprite gemSprite = MinecraftClient.getInstance().getSpriteAtlas(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).apply(MotherlodeModule.id("block/transmitter_gem"));
        if (gemItem.equals(Items.EMERALD)) return getCubeUV(gemSprite, 4);
        if (gemItem.equals(MotherlodeMaterialsItems.SAPPHIRE)) return getCubeUV(gemSprite, 8);
        if (gemItem.equals(MotherlodeMaterialsItems.RUBY)) return getCubeUV(gemSprite, 12);
        if (gemItem.equals(MotherlodeMaterialsItems.AMETHYST)) return getCubeUV(gemSprite, 16);
        if (gemItem.equals(MotherlodeMaterialsItems.TOPAZ)) return getCubeUV(gemSprite, 20);
        if (gemItem.equals(MotherlodeMaterialsItems.HOWLITE)) return getCubeUV(gemSprite, 24);
        if (gemItem.equals(MotherlodeMaterialsItems.ONYX)) return getCubeUV(gemSprite, 28);
        if (gemItem.equals(Items.DIAMOND)) return getCubeUV(gemSprite, 32);
        return getCubeUV(gemSprite, 2);
    }

    private Vector4f[] getCubeUV(Sprite sprite, float y) {
        Vector4f[] cubeUV = new Vector4f[6];
        float vPixel = (sprite.getMaxV() - sprite.getMinV()) / 32;
        float uPixel = (sprite.getMaxU() - sprite.getMinU()) / 8;
        cubeUV[0] = new Vector4f(sprite.getMinV() + (vPixel * (y - 2)), sprite.getMaxV() - (vPixel * (32 - y)), sprite.getMinU(), sprite.getMaxU() - uPixel * 6);
        cubeUV[1] = new Vector4f(sprite.getMinV() + (vPixel * (y - 2)), sprite.getMaxV() - (vPixel * (32 - y)), sprite.getMinU() + uPixel * 2, sprite.getMaxU() - uPixel * 4);
        cubeUV[2] = new Vector4f(sprite.getMinV() + (vPixel * (y - 2)), sprite.getMaxV() - (vPixel * (32 - y)), sprite.getMinU() + uPixel * 4, sprite.getMaxU() - uPixel * 2);
        cubeUV[3] = new Vector4f(sprite.getMinV() + (vPixel * (y - 2)), sprite.getMaxV() - (vPixel * (32 - y)), sprite.getMinU() + uPixel * 6, sprite.getMaxU());
        cubeUV[4] = new Vector4f(sprite.getMinV() + (vPixel * (y - 2)), sprite.getMaxV() - (vPixel * (32 - y + 4)), sprite.getMinU() + uPixel * 2, sprite.getMaxU() - uPixel * 4);
        cubeUV[5] = new Vector4f(sprite.getMinV() + (vPixel * (y - 2)), sprite.getMaxV() - (vPixel * (32 - y + 4)), sprite.getMinU() + uPixel * 4, sprite.getMaxU() - uPixel * 2);

        return cubeUV;
    }

    private void renderRect(Matrix4f model, Matrix3f normal, VertexConsumer buffer, int light, int color, Vector4f[] uv, Vec3f pos, Vec3f size) {
        renderSide(model, normal, buffer, light, color, uv[0], pos, size.getX(), size.getY(), Direction.NORTH);
        renderSide(model, normal, buffer, light, color, uv[1], pos, size.getX(), size.getY(), Direction.SOUTH);
        renderSide(model, normal, buffer, light, color, uv[2], pos, size.getZ(), size.getY(), Direction.EAST);
        renderSide(model, normal, buffer, light, color, uv[3], pos, size.getZ(), size.getY(), Direction.WEST);
        renderSide(model, normal, buffer, light, color, uv[4], pos, size.getX(), size.getZ(), Direction.UP);
        renderSide(model, normal, buffer, light, color, uv[5], pos, size.getX(), size.getZ(), Direction.DOWN);
    }

    private void renderSide(Matrix4f model, Matrix3f normal, VertexConsumer buffer, int light, int color, Vector4f uv, Vec3f pos, float width, float height, Direction side) {
        // These are slightly off from what one pixel (in normal res textures) actually is (1/16) to avoid z-fighting
        float onePixelSmall = 127;
        float onePixelLarge = 129;
        float min = onePixelSmall / 2048f - width / 32;
        float max = onePixelLarge / 2048f + width / 32;
        float minY = onePixelSmall / 2048f - height / 32;
        float midY = onePixelLarge / 2048f + height / 32;

        float minV = uv.getY();
        float maxV = uv.getX();
        float minU = uv.getW();
        float maxU = uv.getZ();

        int r = (color & 0xFF0000) >> 16;
        int g = (color & 0xFF00) >> 8;
        int b = (color & 0xFF);
        int a = 255;

        switch (side) {
            case NORTH:
                buffer.vertex(model, min + (pos.getX() / 16), minY + (pos.getY() / 16), min + (pos.getZ() / 16)).color(r, g, b, a).texture(minU, minV).light(light).normal(normal, 0f, 0f, -1f).next();
                buffer.vertex(model, min + (pos.getX() / 16), midY + (pos.getY() / 16), min + (pos.getZ() / 16)).color(r, g, b, a).texture(minU, maxV).light(light).normal(normal, 0f, 0f, -1f).next();
                buffer.vertex(model, max + (pos.getX() / 16), midY + (pos.getY() / 16), min + (pos.getZ() / 16)).color(r, g, b, a).texture(maxU, maxV).light(light).normal(normal, 0f, 0f, -1f).next();
                buffer.vertex(model, max + (pos.getX() / 16), minY + (pos.getY() / 16), min + (pos.getZ() / 16)).color(r, g, b, a).texture(maxU, minV).light(light).normal(normal, 0f, 0f, -1f).next();
                break;
            case SOUTH:
                buffer.vertex(model, min + (pos.getX() / 16), minY + (pos.getY() / 16), max + (pos.getZ() / 16)).color(r, g, b, a).texture(minU, minV).light(light).normal(normal, 0f, 0f, 1f).next();
                buffer.vertex(model, max + (pos.getX() / 16), minY + (pos.getY() / 16), max + (pos.getZ() / 16)).color(r, g, b, a).texture(maxU, minV).light(light).normal(normal, 0f, 0f, 1f).next();
                buffer.vertex(model, max + (pos.getX() / 16), midY + (pos.getY() / 16), max + (pos.getZ() / 16)).color(r, g, b, a).texture(maxU, maxV).light(light).normal(normal, 0f, 0f, 1f).next();
                buffer.vertex(model, min + (pos.getX() / 16), midY + (pos.getY() / 16), max + (pos.getZ() / 16)).color(r, g, b, a).texture(minU, maxV).light(light).normal(normal, 0f, 0f, 1f).next();
                break;
            case EAST:
                buffer.vertex(model, max + (pos.getX() / 16), minY + (pos.getY() / 16), min + (pos.getZ() / 16)).color(r, g, b, a).texture(minU, minV).light(light).normal(normal, 1f, 0f, 0f).next();
                buffer.vertex(model, max + (pos.getX() / 16), midY + (pos.getY() / 16), min + (pos.getZ() / 16)).color(r, g, b, a).texture(minU, maxV).light(light).normal(normal, 1f, 0f, 0f).next();
                buffer.vertex(model, max + (pos.getX() / 16), midY + (pos.getY() / 16), max + (pos.getZ() / 16)).color(r, g, b, a).texture(maxU, maxV).light(light).normal(normal, 1f, 0f, 0f).next();
                buffer.vertex(model, max + (pos.getX() / 16), minY + (pos.getY() / 16), max + (pos.getZ() / 16)).color(r, g, b, a).texture(maxU, minV).light(light).normal(normal, 1f, 0f, 0f).next();
                break;
            case WEST:
                buffer.vertex(model, min + (pos.getX() / 16), minY + (pos.getY() / 16), min + (pos.getZ() / 16)).color(r, g, b, a).texture(minU, minV).light(light).normal(normal, -1f, 0f, 0f).next();
                buffer.vertex(model, min + (pos.getX() / 16), minY + (pos.getY() / 16), max + (pos.getZ() / 16)).color(r, g, b, a).texture(maxU, minV).light(light).normal(normal, -1f, 0f, 0f).next();
                buffer.vertex(model, min + (pos.getX() / 16), midY + (pos.getY() / 16), max + (pos.getZ() / 16)).color(r, g, b, a).texture(maxU, maxV).light(light).normal(normal, -1f, 0f, 0f).next();
                buffer.vertex(model, min + (pos.getX() / 16), midY + (pos.getY() / 16), min + (pos.getZ() / 16)).color(r, g, b, a).texture(minU, maxV).light(light).normal(normal, -1f, 0f, 0f).next();
                break;
            case UP:
                buffer.vertex(model, min + (pos.getX() / 16), midY + (pos.getY() / 16), min + (pos.getZ() / 16)).color(r, g, b, a).texture(minU, minV).light(light).normal(normal, 0f, -1f, 0f).next();
                buffer.vertex(model, min + (pos.getX() / 16), midY + (pos.getY() / 16), max + (pos.getZ() / 16)).color(r, g, b, a).texture(minU, maxV).light(light).normal(normal, 0f, -1f, 0f).next();
                buffer.vertex(model, max + (pos.getX() / 16), midY + (pos.getY() / 16), max + (pos.getZ() / 16)).color(r, g, b, a).texture(maxU, maxV).light(light).normal(normal, 0f, -1f, 0f).next();
                buffer.vertex(model, max + (pos.getX() / 16), midY + (pos.getY() / 16), min + (pos.getZ() / 16)).color(r, g, b, a).texture(maxU, minV).light(light).normal(normal, 0f, -1f, 0f).next();
                break;
            case DOWN:
                buffer.vertex(model, min + (pos.getX() / 16), minY + (pos.getY() / 16), min + (pos.getZ() / 16)).color(r, g, b, a).texture(minU, minV).light(light).normal(normal, 0f, 1f, 0f).next();
                buffer.vertex(model, min + (pos.getX() / 16), minY + (pos.getY() / 16), max + (pos.getZ() / 16)).color(r, g, b, a).texture(minU, maxV).light(light).normal(normal, 0f, 1f, 0f).next();
                buffer.vertex(model, max + (pos.getX() / 16), minY + (pos.getY() / 16), max + (pos.getZ() / 16)).color(r, g, b, a).texture(maxU, maxV).light(light).normal(normal, 0f, 1f, 0f).next();
                buffer.vertex(model, max + (pos.getX() / 16), minY + (pos.getY() / 16), min + (pos.getZ() / 16)).color(r, g, b, a).texture(maxU, minV).light(light).normal(normal, 0f, 1f, 0f).next();
        }
    }
}
