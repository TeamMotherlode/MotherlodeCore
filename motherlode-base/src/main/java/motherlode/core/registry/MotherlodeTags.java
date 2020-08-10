package motherlode.core.registry;

import motherlode.core.Motherlode;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;

public class MotherlodeTags {
	public static class Fluids {
		public static Tag<Fluid> register(String name) {
			return TagRegistry.fluid(Motherlode.id(name));
		}

		protected static void init() {
			// CALLED TO MAINTAIN REGISTRY ORDER
		}
	}

	public static class Items {
		public static final Tag<Item> GEMS = register("gems");

		public static Tag<Item> register(String name) {
			return TagRegistry.item(Motherlode.id(name));
		}

		protected static void init() {
			// CALLED TO MAINTAIN REGISTRY ORDER
		}
	}

	public static class Blocks {
		public static Tag<Block> register(String name) {
			return TagRegistry.block(Motherlode.id(name));
		}

		protected static void init() {
			// CALLED TO MAINTAIN REGISTRY ORDER
		}
	}

	public static void init() {
		Fluids.init();
		Items.init();
		Blocks.init();
	}
}
