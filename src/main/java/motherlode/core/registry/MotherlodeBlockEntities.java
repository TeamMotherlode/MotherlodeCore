package motherlode.core.registry;

import motherlode.core.Motherlode;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public class MotherlodeBlockEntities {
	public static void init() {
		// CALLED TO MAINTAIN REGISTRY ORDER
	}

	private static <B extends BlockEntity> BlockEntityType<B> register(String name, Supplier<B> supplier, Block... supportedBlocks) {
		return Registry.register(Registry.BLOCK_ENTITY_TYPE, Motherlode.id(name), BlockEntityType.Builder.create(supplier, supportedBlocks).build(null));
	}
}
