package motherlode.biomes;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.world.BiomeColors;

public class MotherlodeBiomesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) ->
                BiomeColors.getGrassColor(world, pos), MotherlodeBiomesBlocks.WATERPLANT);
    }
}
