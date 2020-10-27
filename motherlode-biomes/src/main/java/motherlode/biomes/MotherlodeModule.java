package motherlode.biomes;

import motherlode.biomes.world.MotherlodeBiomeFeatures;
import motherlode.biomes.world.MotherlodeBiomes;
import motherlode.biomes.world.MotherlodeStructures;
import net.fabricmc.api.ModInitializer;

public class MotherlodeModule implements ModInitializer {
    public static final String MODID = "motherlode-biomes";

    @Override
    public void onInitialize() {
        MotherlodeBiomesBlocks.init();
        MotherlodeBiomeFeatures.register();
        MotherlodeStructures.init();
        MotherlodeBiomes.init();
    }
}
