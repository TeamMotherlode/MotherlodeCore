package motherlode.core;

import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.potion.PotionUtil;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import motherlode.core.block.PotBlock;
import motherlode.core.enderinvasion.EnderInvasion;
import motherlode.core.entities.render.MotherlodeEntityRenderers;
import motherlode.core.gui.RedstoneTransmitterGuiDescription;
import motherlode.core.gui.RedstoneTransmitterScreen;
import motherlode.core.registry.MotherlodeAssets;
import motherlode.core.registry.MotherlodeBlocks;
import motherlode.core.registry.MotherlodeParticles;
import motherlode.core.registry.MotherlodePotions;
import motherlode.core.registry.MotherlodeScreenHandlers;
@Environment(EnvType.CLIENT)
public class MotherlodeClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MotherlodeAssets.register();
		MotherlodeEntityRenderers.init();
        BlockRenderLayerMap.INSTANCE.putBlock(MotherlodeBlocks.ROPE_BLOCK, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(MotherlodeBlocks.POT, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(MotherlodeBlocks.END_FOAM, RenderLayer.getTranslucent());
		ColorProviderRegistry.BLOCK.register((state, _world, _pos, _tintIndex) -> state.get(PotBlock.COLOR).getColor(), MotherlodeBlocks.POT);
		ScreenRegistry.register(MotherlodeScreenHandlers.REDSTONE_TRANSMITTER_TYPE, (ScreenRegistry.Factory<RedstoneTransmitterGuiDescription, RedstoneTransmitterScreen>) RedstoneTransmitterScreen::new);

		MotherlodeParticles.init();

		for(Block block : MotherlodeBlocks.cutouts) {
			BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
		}
		for(Block block : MotherlodeBlocks.translucent) {
			BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getTranslucent());
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

        FabricModelPredicateProviderRegistry.register(Items.POTION, new Identifier("potion_type"), (itemStack, _world, _entity) -> {
            MotherlodePotions.PotionModelInfo potion = MotherlodePotions.potionModelInfos.get(PotionUtil.getPotion(itemStack));
            return potion == null ? 1 : potion.predicateValue;
        });

        FabricModelPredicateProviderRegistry.register(new Identifier("stack_count"), (itemStack, _world, _entity) -> itemStack.getCount() / 100F);

        FabricModelPredicateProviderRegistry.register(MotherlodeBlocks.POT_ITEM, new Identifier("pot_pattern"), (itemStack, _world, _entity) -> {
            CompoundTag tag = itemStack.getTag();
            if (tag == null || !tag.contains("BlockStateTag"))
                return 0;
            tag = tag.getCompound("BlockStateTag");
            if (tag == null || !tag.contains("pattern"))
                return 0;

            return Integer.parseInt(tag.getString("pattern")) / 100F;
        });

        ClientSidePacketRegistry.INSTANCE.register(EnderInvasion.PLAY_PORTAL_PARTICLE_PACKET_ID, (packetContext, attachedData) -> {

            BlockPos pos = attachedData.readBlockPos();
            double offsetY = attachedData.readDouble();

            double velocityX = attachedData.readDouble();
            double velocityY = attachedData.readDouble();
            double velocityZ = attachedData.readDouble();

            packetContext.getTaskQueue().execute(() ->
                    MinecraftClient.getInstance().particleManager.addParticle(
                            ParticleTypes.PORTAL, pos.getX(), pos.getY() + offsetY, pos.getZ(), velocityX, velocityY, velocityZ));
        });
    }
}
