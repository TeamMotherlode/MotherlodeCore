package motherlode.base;

import java.util.List;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import motherlode.base.api.AssetProcessor;
import motherlode.base.api.impl.MotherlodeAssetsImpl;
import net.fabricmc.api.ClientModInitializer;
import com.swordglowsblue.artifice.api.Artifice;

public class MotherlodeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        List<Pair<Identifier, AssetProcessor>> assets = MotherlodeAssetsImpl.INSTANCE.getAssets();

        Artifice.registerAssetPack(Motherlode.id("resource_pack"), pack ->
            assets.forEach(pair ->
                pair.getRight().accept(pack, pair.getLeft())));
        MotherlodeAssetsImpl.INSTANCE.removeAssetProcessorList();
    }
}
