package motherlode.copperdungeon;

import net.minecraft.client.render.RenderLayer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;

public class MotherlodeCopperDungeonClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(MotherlodeCopperDungeonBlocks.CUTTER_TRAP, RenderLayer.getTranslucent());

        MotherlodeCopperDungeonParticles.init();
    }
}
