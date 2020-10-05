package motherlode.uncategorized;

import motherlode.uncategorized.registry.MotherlodeAssets;
import motherlode.uncategorized.registry.MotherlodeBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class MotherlodeUncategorizedClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {

		motherlode.base.api.MotherlodeAssets.addAssets(null, new MotherlodeAssets());
		
    		for(Block block : MotherlodeBlocks.cutouts) {
			BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
		}
		for(Block block : MotherlodeBlocks.grassColored) {
			ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) ->
				BiomeColors.getGrassColor(world, pos), block);
			ColorProviderRegistry.ITEM.register((stack, tintIndex) -> FoliageColors.getSpruceColor(), block);
		}
		for(Block block : MotherlodeBlocks.foliageColored) {
			ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) ->
					BiomeColors.getFoliageColor(world, pos), block);
			ColorProviderRegistry.ITEM.register((stack, tintIndex) -> FoliageColors.getDefaultColor(), block);
		}
	}
}
