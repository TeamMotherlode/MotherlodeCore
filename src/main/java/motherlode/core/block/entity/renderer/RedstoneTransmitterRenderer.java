package motherlode.core.block.entity.renderer;

import motherlode.core.Motherlode;
import motherlode.core.block.entity.RedstoneTransmitterBlockEntity;
import motherlode.core.item.DefaultGemItem;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.GraphicsMode;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;

public class RedstoneTransmitterRenderer extends BlockEntityRenderer<RedstoneTransmitterBlockEntity> {
	public RedstoneTransmitterRenderer(BlockEntityRenderDispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	public void render(RedstoneTransmitterBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		MinecraftClient client = MinecraftClient.getInstance();
		if(!shouldShow(client, entity, tickDelta) || client.options.graphicsMode == GraphicsMode.FAST)
			return;
		VertexConsumer buffer;
		if (client.options.graphicsMode == GraphicsMode.FABULOUS)
			buffer = vertexConsumers.getBuffer(RenderLayer.getTranslucentMovingBlock());
		else if(client.options.graphicsMode == GraphicsMode.FANCY)
			buffer = vertexConsumers.getBuffer(RenderLayer.getTranslucent());
		else return;

		Matrix4f model = matrices.peek().getModel();
		Matrix3f normal = matrices.peek().getNormal();
		Sprite sprite = client.getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEX).apply(Motherlode.id("block/diamond_block_mini"));

		if (!entity.isEmpty()) {
			for(int i = 0; i < entity.size(); i++) {
				if(entity.getStack(i).isEmpty()) {
					continue;
				}
				Item item = entity.getStack(i).getItem();

				int color;
				if(item instanceof DefaultGemItem)
					color = ((DefaultGemItem) item).getColor();
				else if(item == Items.DIAMOND)
					color = 0x49EAD6;
				else if(item == Items.EMERALD)
					color = 0x17DA61;
				else continue;

				renderSide(model, normal, buffer, light, color, sprite, ((i%3) * 4) + 3, 13, ((i/3) * 4) + 3, Direction.UP);
				renderSide(model, normal, buffer, light, color, sprite, ((i%3) * 4) + 3, 13, ((i/3) * 4) + 3, Direction.NORTH);
				renderSide(model, normal, buffer, light, color, sprite, ((i%3) * 4) + 3, 13, ((i/3) * 4) + 3, Direction.SOUTH);
				renderSide(model, normal, buffer, light, color, sprite, ((i%3) * 4) + 3, 13, ((i/3) * 4) + 3, Direction.EAST);
				renderSide(model, normal, buffer, light, color, sprite, ((i%3) * 4) + 3, 13, ((i/3) * 4) + 3, Direction.WEST);
			}
		}
	}

	private void renderSide(Matrix4f model, Matrix3f normal, VertexConsumer buffer, int light, int color, Sprite sprite, float x, float y, float z, Direction side) {
		// These are slightly off from what one pixel (in normal res textures) actually is (1/16) to avoid z-fighting
		float halfWidth = 1f/8f;
		float onePixelSmall = 127;
		float onePixelLarge = 129;
		float min = onePixelSmall/2048f - halfWidth;
		float max = onePixelLarge/2048f + halfWidth;
		float minY = onePixelSmall/2048f - halfWidth;
		float midY = onePixelLarge/2048f + halfWidth;

		float minU = sprite.getMinU();
		float maxU = sprite.getMaxU();
		float minV = sprite.getMinV();
		float maxV = sprite.getMaxV();
		float midV = maxV;


		int r = (color & 0xFF0000) >> 16;
		int g = (color & 0xFF00) >> 8;
		int b = (color & 0xFF);
		int a = 255;

		switch(side) {
			case NORTH:
				buffer.vertex(model, min + (x/16), minY + (y/16), min + (z/16)).color(r, g, b, a).texture(minU, minV).light(light).normal(normal, 0f, 0f, -1f).next();
				buffer.vertex(model, min + (x/16), midY + (y/16), min + (z/16)).color(r, g, b, a).texture(minU, midV).light(light).normal(normal, 0f, 0f, -1f).next();
				buffer.vertex(model, max + (x/16), midY + (y/16), min + (z/16)).color(r, g, b, a).texture(maxU, midV).light(light).normal(normal, 0f, 0f, -1f).next();
				buffer.vertex(model, max + (x/16), minY + (y/16), min + (z/16)).color(r, g, b, a).texture(maxU, minV).light(light).normal(normal, 0f, 0f, -1f).next();
			break;
			case SOUTH:
				buffer.vertex(model, min + (x/16), minY + (y/16), max + (z/16)).color(r, g, b, a).texture(minU, minV).light(light).normal(normal, 0f, 0f, 1f).next();
				buffer.vertex(model, max + (x/16), minY + (y/16), max + (z/16)).color(r, g, b, a).texture(maxU, minV).light(light).normal(normal, 0f, 0f, 1f).next();
				buffer.vertex(model, max + (x/16), midY + (y/16), max + (z/16)).color(r, g, b, a).texture(maxU, midV).light(light).normal(normal, 0f, 0f, 1f).next();
				buffer.vertex(model, min + (x/16), midY + (y/16), max + (z/16)).color(r, g, b, a).texture(minU, midV).light(light).normal(normal, 0f, 0f, 1f).next();
			break;
			case EAST:
				buffer.vertex(model, max + (x/16), minY + (y/16), min + (z/16)).color(r, g, b, a).texture(minU, minV).light(light).normal(normal, 1f, 0f, 0f).next();
				buffer.vertex(model, max + (x/16), midY + (y/16), min + (z/16)).color(r, g, b, a).texture(minU, midV).light(light).normal(normal, 1f, 0f, 0f).next();
				buffer.vertex(model, max + (x/16), midY + (y/16), max + (z/16)).color(r, g, b, a).texture(maxU, midV).light(light).normal(normal, 1f, 0f, 0f).next();
				buffer.vertex(model, max + (x/16), minY + (y/16), max + (z/16)).color(r, g, b, a).texture(maxU, minV).light(light).normal(normal, 1f, 0f, 0f).next();
			break;
			case WEST:
				buffer.vertex(model, min + (x/16), minY + (y/16), min + (z/16)).color(r, g, b, a).texture(minU, minV).light(light).normal(normal, -1f, 0f, 0f).next();
				buffer.vertex(model, min + (x/16), minY + (y/16), max + (z/16)).color(r, g, b, a).texture(maxU, minV).light(light).normal(normal, -1f, 0f, 0f).next();
				buffer.vertex(model, min + (x/16), midY + (y/16), max + (z/16)).color(r, g, b, a).texture(maxU, midV).light(light).normal(normal, -1f, 0f, 0f).next();
				buffer.vertex(model, min + (x/16), midY + (y/16), min + (z/16)).color(r, g, b, a).texture(minU, midV).light(light).normal(normal, -1f, 0f, 0f).next();
			break;
			case UP:
				buffer.vertex(model, min + (x/16), midY + (y/16), min + (z/16)).color(r, g, b, a).texture(minU, minV).light(light).normal(normal, 0f, -1f, 0f).next();
				buffer.vertex(model, min + (x/16), midY + (y/16), max + (z/16)).color(r, g, b, a).texture(minU, maxV).light(light).normal(normal, 0f, -1f, 0f).next();
				buffer.vertex(model, max + (x/16), midY + (y/16), max + (z/16)).color(r, g, b, a).texture(maxU, maxV).light(light).normal(normal, 0f, -1f, 0f).next();
				buffer.vertex(model, max + (x/16), midY + (y/16), min + (z/16)).color(r, g, b, a).texture(maxU, minV).light(light).normal(normal, 0f, -1f, 0f).next();
			break;
			case DOWN:
				buffer.vertex(model, min + (x/16), minY + (y/16), min + (z/16)).color(r, g, b, a).texture(minU, minV).light(light).normal(normal, 0f, 1f, 0f).next();
				buffer.vertex(model, min + (x/16), minY + (y/16), max + (z/16)).color(r, g, b, a).texture(minU, maxV).light(light).normal(normal, 0f, 1f, 0f).next();
				buffer.vertex(model, max + (x/16), minY + (y/16), max + (z/16)).color(r, g, b, a).texture(maxU, maxV).light(light).normal(normal, 0f, 1f, 0f).next();
				buffer.vertex(model, max + (x/16), minY + (y/16), min + (z/16)).color(r, g, b, a).texture(maxU, minV).light(light).normal(normal, 0f, 1f, 0f).next();
		}
	}

	private boolean shouldShow(MinecraftClient client, BlockEntity entity, float tickDelta) {
		HitResult hitResult = client.getCameraEntity().rayTrace(5, tickDelta, false);
		if(hitResult instanceof BlockHitResult) {
			return ((BlockHitResult) hitResult).getBlockPos().equals(entity.getPos());
		} else return false;
	}

}
