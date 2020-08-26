package motherlode.biomes.world;

import motherlode.base.Motherlode;
import motherlode.biomes.MotherlodeBiomesMod;
import net.fabricmc.fabric.api.biomes.v1.OverworldBiomes;
import net.fabricmc.fabric.api.biomes.v1.OverworldClimate;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class MotherlodeBiomes {

	public static final Biome RUINED_EDGE = register("ruined_edge", new RuinedEdgeBiome());
	public static final Biome RUINED_FLATS = register("ruined_flats", new RuinedFlatsBiome());

	public static void init() {
		OverworldBiomes.setRiverBiome(RUINED_EDGE, RUINED_FLATS);
		OverworldBiomes.addContinentalBiome(RUINED_FLATS, OverworldClimate.TEMPERATE, 2);
		OverworldBiomes.addEdgeBiome(RUINED_EDGE, RUINED_FLATS, 500);
	}

	private static <T extends Biome> T register(String name, T biome) {
		return Registry.register(Registry.BIOME, Motherlode.id(MotherlodeBiomesMod.MODID, name), biome);
	}
}
