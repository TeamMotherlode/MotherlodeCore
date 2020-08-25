package motherlode.redstone;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import motherlode.base.api.ArtificeProcessor;
import motherlode.base.api.MotherlodeAssetsEntryPoint;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

public class MotherlodeRedstoneMod implements ModInitializer, MotherlodeAssetsEntryPoint {

    public static final String MODID = "motherlode-redstone";

    @Override
    public void onInitialize() {

        MotherlodeRedstoneBlocks.init();
        MotherlodeBlockEntities.init();
        MotherlodeScreenHandlers.init();
    }
    @Override
    public void registerAssets(ArtificeResourcePack.ClientResourcePackBuilder pack) {

        for(Pair<Identifier, ArtificeProcessor> entry: MotherlodeRedstoneBlocks.ARTIFICE_PROCESSORS) {

            entry.getRight().accept(pack, entry.getLeft());
        }
    }
}