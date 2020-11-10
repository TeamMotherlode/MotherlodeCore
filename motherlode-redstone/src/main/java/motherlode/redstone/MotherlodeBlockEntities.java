package motherlode.redstone;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import motherlode.base.Motherlode;
import motherlode.redstone.block.RedstoneTransmitterBlockEntity;

public class MotherlodeBlockEntities {
    public static BlockEntityType<RedstoneTransmitterBlockEntity> REDSTONE_TRANSMITTER = register("redstone_transmitter", RedstoneTransmitterBlockEntity::new, MotherlodeRedstoneBlocks.REDSTONE_TRANSMITTER);

    public static void init() {
        // CALLED TO MAINTAIN REGISTRY ORDER
    }

    private static <B extends BlockEntity> BlockEntityType<B> register(String name, FabricBlockEntityTypeBuilder.Factory<B> factory, Block... supportedBlocks) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, Motherlode.id(MotherlodeModule.MODID, name), FabricBlockEntityTypeBuilder.create(factory, supportedBlocks).build(null));
    }
}
