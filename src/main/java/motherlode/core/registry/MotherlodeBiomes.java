package motherlode.core.registry;

import motherlode.core.Motherlode;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class MotherlodeBiomes {
	public static void init() {
		// CALLED TO MAINTAIN REGISTRY ORDER AND SET RIVER BIOMES
	}

	private static <T extends Biome> T register(String name, T biome) {
		return Registry.register(Registry.BIOME, Motherlode.id(name), biome);
	}
}
