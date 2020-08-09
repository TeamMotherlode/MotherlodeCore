package motherlode.core;

import motherlode.core.block.entity.renderer.RedstoneTransmitterRenderer;
import motherlode.core.gui.RedstoneTransmitterGuiDescription;
import motherlode.core.gui.RedstoneTransmitterScreen;
import motherlode.core.registry.MotherlodeAssets;
import motherlode.core.registry.MotherlodeBlockEntities;
import motherlode.core.registry.MotherlodeScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.texture.SpriteAtlasTexture;

@Environment(EnvType.CLIENT)
public class MotherlodeClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MotherlodeAssets.register();

		// GUIs
		ScreenRegistry.register(MotherlodeScreenHandlers.REDSTONE_TRANSMITTER_TYPE, (ScreenRegistry.Factory<RedstoneTransmitterGuiDescription, RedstoneTransmitterScreen>) RedstoneTransmitterScreen::new);

		// Block Entity Renderers
		 BlockEntityRendererRegistry.INSTANCE.register(MotherlodeBlockEntities.REDSTONE_TRANSMITTER, RedstoneTransmitterRenderer::new);

		// Sprite Registry
		ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEX).register(((spriteAtlasTexture, registry) -> {
			registry.register(Motherlode.id("block/transmitter_gem"));
		}));
	}
}