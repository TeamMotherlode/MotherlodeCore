package motherlode.base;

import com.swordglowsblue.artifice.api.Artifice;
import motherlode.base.api.MotherlodeAssetsEntryPoint;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class MotherlodeClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        Artifice.registerAssets(Motherlode.id("resource_pack"), pack -> {

            FabricLoader.getInstance().getEntrypointContainers("motherlode:assets", MotherlodeAssetsEntryPoint.class).forEach(entrypoint ->
                    entrypoint.getEntrypoint().registerAssets(pack));
        });
    }
}
