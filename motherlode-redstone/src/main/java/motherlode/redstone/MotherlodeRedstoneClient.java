package motherlode.redstone;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.screen.PlayerScreenHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import motherlode.redstone.gui.RedstoneTransmitterGuiDescription;
import motherlode.redstone.gui.RedstoneTransmitterScreen;

public class MotherlodeRedstoneClient implements ClientModInitializer {
    @Override
    @SuppressWarnings("RedundantCast")
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(MotherlodeRedstoneBlocks.REDSTONE_TRANSMITTER, RenderLayer.getCutout());

        ScreenRegistry.register(MotherlodeRedstoneScreenHandlers.REDSTONE_TRANSMITTER_TYPE, (ScreenRegistry.Factory<RedstoneTransmitterGuiDescription, RedstoneTransmitterScreen>) RedstoneTransmitterScreen::new);

        MotherlodeBlockEntityRenderers.init();

        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register((spriteAtlasTexture, registry) ->
            registry.register(MotherlodeModule.id("block/transmitter_gem")));
    }
}
