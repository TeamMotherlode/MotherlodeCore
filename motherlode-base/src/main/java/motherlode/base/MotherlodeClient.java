package motherlode.base;

import com.swordglowsblue.artifice.api.Artifice;
import motherlode.base.api.MotherlodeAssets;
import net.fabricmc.api.ClientModInitializer;

public class MotherlodeClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        Artifice.registerAssets(Motherlode.id("resource_pack"), pack -> {
            MotherlodeAssets.getAssets().forEach(pair ->
                    pair.getRight().accept(pack, pair.getLeft()));
        });
    }
}
