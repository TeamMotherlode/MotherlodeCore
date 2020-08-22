package motherlode.orestoolsarmor;

import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class MotherlodeOresToolsArmorFeatures {

    public static void register() {
        Registry.BIOME.forEach(MotherlodeOresToolsArmorFeatures::addToBiome);
        RegistryEntryAddedCallback.event(Registry.BIOME).register((i, identifier, biome) -> addToBiome(biome));
    }

    public static void addToBiome(Biome biome) {

        MotherlodeOresToolsArmorBlocks.CHARITE_ORE.accept(biome);
        MotherlodeOresToolsArmorBlocks.ECHERITE_ORE.accept(biome);
        MotherlodeOresToolsArmorBlocks.ADAMANTITE_ORE.accept(biome);
        MotherlodeOresToolsArmorBlocks.COPPER_ORE.accept(biome);
        MotherlodeOresToolsArmorBlocks.SILVER_ORE.accept(biome);
        MotherlodeOresToolsArmorBlocks.TITANIUM_ORE.accept(biome);
        MotherlodeOresToolsArmorBlocks.AMETHYST_ORE.accept(biome);
        MotherlodeOresToolsArmorBlocks.RUBY_ORE.accept(biome);
        MotherlodeOresToolsArmorBlocks.SAPPHIRE_ORE.accept(biome);
        MotherlodeOresToolsArmorBlocks.TOPAZ_ORE.accept(biome);
    }

}
