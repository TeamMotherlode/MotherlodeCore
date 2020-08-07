package motherlode.core.assets;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;

import java.util.function.Consumer;

public enum BlockTint {
    GRASS(block -> {
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) ->
                BiomeColors.getGrassColor(world, pos), block);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> FoliageColors.getSpruceColor(), block);
    }),
    FOLIAGE(block -> {
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) ->
                BiomeColors.getFoliageColor(world, pos), block);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> FoliageColors.getDefaultColor(), block);
    });

    Consumer<Block> blockTintRegister;

    BlockTint(Consumer<Block> blockTintRegister) {
        this.blockTintRegister = blockTintRegister;
    }

    public void register(Block block) {
        blockTintRegister.accept(block);
    }
}
