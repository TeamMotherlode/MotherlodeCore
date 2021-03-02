package motherlode.enderinvasion;

import net.minecraft.client.render.RenderLayer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;

public class MotherlodeEnderInvasionClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(MotherlodeEnderInvasionBlocks.END_FOAM, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(MotherlodeEnderInvasionBlocks.CORRUPTED_GRASS, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(MotherlodeEnderInvasionBlocks.WITHERED_OAK_LEAVES, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(MotherlodeEnderInvasionBlocks.WITHERED_DARK_OAK_LEAVES, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(MotherlodeEnderInvasionBlocks.WITHERED_BIRCH_LEAVES, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(MotherlodeEnderInvasionBlocks.WITHERED_SPRUCE_LEAVES, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(MotherlodeEnderInvasionBlocks.WITHERED_JUNGLE_LEAVES, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(MotherlodeEnderInvasionBlocks.WITHERED_ACACIA_LEAVES, RenderLayer.getCutoutMipped());
    }
}
