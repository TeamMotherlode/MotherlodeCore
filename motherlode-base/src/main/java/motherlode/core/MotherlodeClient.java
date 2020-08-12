package motherlode.core;

import motherlode.core.block.PotBlock;
import motherlode.core.registry.MotherlodeAssets;
import motherlode.core.registry.MotherlodeBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class MotherlodeClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MotherlodeAssets.register();
    	BlockRenderLayerMap.INSTANCE.putBlock(MotherlodeBlocks.ROPE.get(), RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(MotherlodeBlocks.POT.get(), RenderLayer.getTranslucent());
		ColorProviderRegistry.BLOCK.register((state, _world, _pos, _tintIndex) -> state.get(PotBlock.COLOR).getColor(), MotherlodeBlocks.POT.get());
		
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
			BiomeColors.getGrassColor(world, pos), MotherlodeBlocks.WATERPLANT.get());

		FabricModelPredicateProviderRegistry.register(new Identifier("stack_count"), ( itemStack,  _world,  _entity) -> itemStack.getCount() / 100F);

		FabricModelPredicateProviderRegistry.register(MotherlodeBlocks.POT.getItem(), new Identifier("pot_pattern"), (itemStack, _world, _entity) -> {
			CompoundTag tag = itemStack.getTag();
			if (tag == null || !tag.contains("BlockStateTag"))
				return 0;
			tag = tag.getCompound("BlockStateTag");
			if (tag == null || !tag.contains("pattern"))
				return 0;

			return Integer.parseInt(tag.getString("pattern")) / 100F;
		});


	}
}
