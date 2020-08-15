package motherlode.redstone.registry;

import motherlode.base.Motherlode;
import motherlode.redstone.MotherlodeRedstoneMod;
import motherlode.redstone.RedstoneTransmitterBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public class MotherlodeBlockEntities {
	public static BlockEntityType<RedstoneTransmitterBlockEntity> REDSTONE_TRANSMITTER = register("redstone_transmitter", RedstoneTransmitterBlockEntity::new, MotherlodeRedstoneBlocks.REDSTONE_TRANSMITTER);

	public static void init() {
		// CALLED TO MAINTAIN REGISTRY ORDER
	}

	private static <B extends BlockEntity> BlockEntityType<B> register(String name, Supplier<B> supplier, Block... supportedBlocks) {
		return Registry.register(Registry.BLOCK_ENTITY_TYPE, Motherlode.id(MotherlodeRedstoneMod.MODID, name), BlockEntityType.Builder.create(supplier, supportedBlocks).build(null));
	}
}
