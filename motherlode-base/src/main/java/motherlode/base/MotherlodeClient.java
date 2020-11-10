package motherlode.base;

import java.util.List;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.fabricmc.api.ClientModInitializer;
import motherlode.base.api.AssetProcessor;
import motherlode.base.api.impl.AssetsManagerImpl;
import com.swordglowsblue.artifice.api.Artifice;

public class MotherlodeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        List<Pair<Identifier, AssetProcessor>> assets = AssetsManagerImpl.INSTANCE.getAssets();

        Artifice.registerAssetPack(Motherlode.id("resource_pack"), pack ->
            assets.forEach(pair ->
                pair.getRight().accept(pack, pair.getLeft())));
        AssetsManagerImpl.INSTANCE.removeAssetProcessorList();
    }
}
