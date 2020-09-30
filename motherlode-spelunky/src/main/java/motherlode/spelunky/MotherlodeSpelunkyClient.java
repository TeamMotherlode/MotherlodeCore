package motherlode.spelunky;

import motherlode.spelunky.block.PotBlock;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;

public class MotherlodeSpelunkyClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        BlockRenderLayerMap.INSTANCE.putBlock(MotherlodeSpelunkyBlocks.POT, RenderLayer.getTranslucent());
        ColorProviderRegistry.BLOCK.register((state, _world, _pos, _tintIndex) -> state.get(PotBlock.COLOR).getColor(), MotherlodeSpelunkyBlocks.POT);

        FabricModelPredicateProviderRegistry.register(MotherlodeSpelunkyBlocks.POT.asItem(), new Identifier("pot_pattern"), (itemStack, _world, _entity) -> {
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
