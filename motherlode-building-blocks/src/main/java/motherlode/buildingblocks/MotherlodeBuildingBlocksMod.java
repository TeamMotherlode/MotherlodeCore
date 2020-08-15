package motherlode.buildingblocks;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import motherlode.base.api.ArtificeProcessor;
import motherlode.base.api.MotherlodeAssetsEntryPoint;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

import java.util.Map;

public class MotherlodeBuildingBlocksMod implements ModInitializer, MotherlodeAssetsEntryPoint {

    public static final String MODID = "motherlode-building-blocks";

    @Override
    public void onInitialize() {

        MotherlodeBuildingBlocks.init();
    }

    @Override
    public void registerAssets(ArtificeResourcePack.ClientResourcePackBuilder pack) {

        for(Map.Entry<Identifier, ArtificeProcessor> entry: MotherlodeBuildingBlocks.ARTIFICE_PROCESSORS.entrySet()) {

            entry.getValue().accept(pack, entry.getKey());
        }
    }
}
