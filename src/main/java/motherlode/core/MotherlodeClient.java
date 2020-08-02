package motherlode.core;

import motherlode.core.block.PotBlock;
import motherlode.core.block.PotColor;
import motherlode.core.gui.RedstoneTransmitterGuiDescription;
import motherlode.core.gui.RedstoneTransmitterScreen;
import motherlode.core.registry.MotherlodeAssets;
import motherlode.core.registry.MotherlodeBlocks;
import motherlode.core.registry.MotherlodeScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.resource.GrassColormapResourceSupplier;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class MotherlodeClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MotherlodeAssets.register();
    	BlockRenderLayerMap.INSTANCE.putBlock(MotherlodeBlocks.ROPE_BLOCK, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(MotherlodeBlocks.POT, RenderLayer.getTranslucent());
		ColorProviderRegistry.BLOCK.register((state, _world, _pos, _tintIndex) -> state.get(PotBlock.COLOR).getColor(), MotherlodeBlocks.POT);
		ScreenRegistry.register(MotherlodeScreenHandlers.REDSTONE_TRANSMITTER_TYPE, (ScreenRegistry.Factory<RedstoneTransmitterGuiDescription, RedstoneTransmitterScreen>) RedstoneTransmitterScreen::new);
		
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
		ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) ->
			BiomeColors.getGrassColor(world, pos), MotherlodeBlocks.WATERPLANT);
	}
}