package motherlode.redstone;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import motherlode.redstone.block.RedstoneTransmitterRenderer;

public class MotherlodeBlockEntityRenderers {
    private static <BE extends BlockEntity> void register(BlockEntityType<BE> type, BlockEntityRendererFactory<BE> factory) {
        BlockEntityRendererRegistry.INSTANCE.register(type, factory);
    }

    public static void init() {
        register(MotherlodeRedstoneBlockEntities.REDSTONE_TRANSMITTER, RedstoneTransmitterRenderer::new);
    }
}
