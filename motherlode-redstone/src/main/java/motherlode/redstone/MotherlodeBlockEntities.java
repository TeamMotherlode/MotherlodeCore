package motherlode.redstone;

import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;
import motherlode.base.Motherlode;
import motherlode.redstone.block.RedstoneTransmitterBlockEntity;

public class MotherlodeBlockEntities {
    public static BlockEntityType<RedstoneTransmitterBlockEntity> REDSTONE_TRANSMITTER = register("redstone_transmitter", RedstoneTransmitterBlockEntity::new, MotherlodeRedstoneBlocks.REDSTONE_TRANSMITTER);

    public static void init() {
        // CALLED TO MAINTAIN REGISTRY ORDER
    }

    private static <B extends BlockEntity> BlockEntityType<B> register(String name, Supplier<B> supplier, Block... supportedBlocks) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, Motherlode.id(MotherlodeModule.MODID, name), BlockEntityType.Builder.create(supplier, supportedBlocks).build(null));
    }
}