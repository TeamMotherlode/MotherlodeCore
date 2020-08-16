package motherlode.redstone;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import motherlode.base.api.ArtificeProcessor;
import motherlode.redstone.MotherlodeBlockEntities;
import motherlode.redstone.MotherlodeRedstoneBlocks;
import motherlode.redstone.MotherlodeScreenHandlers;
import motherlode.base.api.MotherlodeAssetsEntryPoint;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

import java.util.Map;

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

        for(Map.Entry<Identifier, ArtificeProcessor> entry: MotherlodeRedstoneBlocks.ARTIFICE_PROCESSORS.entrySet()) {

            entry.getValue().accept(pack, entry.getKey());
        }
    }
}