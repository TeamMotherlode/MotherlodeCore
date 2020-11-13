package motherlode.core.registry;

import motherlode.core.Motherlode;
import motherlode.core.block.entity.RedstoneTransmitterBlockEntity;
import motherlode.core.block.entity.ZapperTrapBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public class MotherlodeBlockEntities {
	public static BlockEntityType<RedstoneTransmitterBlockEntity> REDSTONE_TRANSMITTER = register("redstone_transmitter", RedstoneTransmitterBlockEntity::new, MotherlodeBlocks.REDSTONE_TRANSMITTER);
	public static BlockEntityType<ZapperTrapBlockEntity> ZAPPER_TRAP = register("trap_zapper", ZapperTrapBlockEntity::new, MotherlodeBlocks.TRAP_ZAPPER);

	public static void init() {
		// CALLED TO MAINTAIN REGISTRY ORDER
	}

	private static <B extends BlockEntity> BlockEntityType<B> register(String name, Supplier<B> supplier, Block... supportedBlocks) {
		return Registry.register(Registry.BLOCK_ENTITY_TYPE, Motherlode.id(name), BlockEntityType.Builder.create(supplier, supportedBlocks).build(null));
	}
}
