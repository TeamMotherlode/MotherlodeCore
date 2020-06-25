package motherlode.core.registry;

import motherlode.core.Motherlode;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;

/**
 * @author Indigo Amann
 */
public class MotherlodeFeatures {
	public static void init() {
		// CALLED TO MAINTAIN REGISTRY ORDER
	}

	public static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
		return Registry.register(Registry.FEATURE, Motherlode.id(name), feature);
	}

	public static <C extends SurfaceConfig, F extends SurfaceBuilder<C>> F register(String name, F surfaceBuilder) {
		return Registry.register(Registry.SURFACE_BUILDER, Motherlode.id(name), surfaceBuilder);
	}
}
