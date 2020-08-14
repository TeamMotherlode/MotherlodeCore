package motherlode.uncategorized.registry;

import motherlode.uncategorized.Motherlode;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.registry.Registry;

public class MotherlodeFluids {
	public static void init() {
		// CALLED TO MAINTAIN REGISTRY ORDER
	}

	static <T extends Fluid> T register(String name, T fluid) {
		T b = Registry.register(Registry.FLUID, Motherlode.id(name), fluid);
		return b;
	}
}
