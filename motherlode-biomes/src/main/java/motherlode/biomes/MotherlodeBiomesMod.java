package motherlode.biomes;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import motherlode.base.api.MotherlodeAssetsEntryPoint;
import motherlode.biomes.world.MotherlodeBiomeFeatures;
import motherlode.biomes.world.MotherlodeBiomes;
import motherlode.biomes.world.MotherlodeStructures;
import net.fabricmc.api.ModInitializer;

public class MotherlodeBiomesMod implements ModInitializer, MotherlodeAssetsEntryPoint {

    public static final String MODID = "motherlode-biomes";

    @Override
    public void onInitialize() {
        MotherlodeBiomes.init();
        MotherlodeBiomeFeatures.register();
        MotherlodeStructures.init();
    }

    @Override
    public void registerAssets(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder) {
        // TODO
    }
}
