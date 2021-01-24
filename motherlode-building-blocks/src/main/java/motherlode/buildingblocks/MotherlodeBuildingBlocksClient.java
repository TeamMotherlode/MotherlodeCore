package motherlode.buildingblocks;

import net.minecraft.client.render.RenderLayer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import motherlode.buildingblocks.screen.SawmillScreen;

public class MotherlodeBuildingBlocksClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(MotherlodeModule.SAWMILL_SCREEN_HANDLER, SawmillScreen::new);
        BlockRenderLayerMap.INSTANCE.putBlock(MotherlodeBuildingBlocks.SAWMILL, RenderLayer.getCutout());
    }
}
