package motherlode.base;

import motherlode.base.api.impl.MotherlodeAssetsImpl;
import net.fabricmc.api.ClientModInitializer;
import com.swordglowsblue.artifice.api.Artifice;

public class MotherlodeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Artifice.registerAssets(Motherlode.id("resource_pack"), pack ->
            MotherlodeAssetsImpl.getAssets().forEach(pair ->
                pair.getRight().accept(pack, pair.getLeft())));
        MotherlodeAssetsImpl.removeAssetProcessorList();
    }
}
