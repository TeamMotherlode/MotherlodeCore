package motherlode.biomes.world;

import motherlode.base.Motherlode;
import motherlode.biomes.MotherlodeBiomesMod;
import net.fabricmc.fabric.api.biome.v1.OverworldBiomes;
import net.fabricmc.fabric.api.biome.v1.OverworldClimate;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

public class MotherlodeBiomes {

    public static final RegistryKey<Biome> RUINED_FLATS = register("ruined_flats", RuinedFlatsBiome.create());
    public static final RegistryKey<Biome> RUINED_EDGE = register("ruined_edge", RuinedEdgeBiome.create());

    @SuppressWarnings("deprecation")
    public static void init() {
        OverworldBiomes.addContinentalBiome(RUINED_FLATS, OverworldClimate.TEMPERATE, 2);
        OverworldBiomes.setRiverBiome(RUINED_FLATS, RUINED_EDGE);
        OverworldBiomes.addEdgeBiome(RUINED_FLATS, RUINED_EDGE, 500);
    }

    private static RegistryKey<Biome> register(String name, Biome biome) {
        RegistryKey<Biome> key = RegistryKey.of(Registry.BIOME_KEY, Motherlode.id(MotherlodeBiomesMod.MODID, name));
        Registry.register(BuiltinRegistries.BIOME, key.getValue(), biome);
        return key;
    }
}
