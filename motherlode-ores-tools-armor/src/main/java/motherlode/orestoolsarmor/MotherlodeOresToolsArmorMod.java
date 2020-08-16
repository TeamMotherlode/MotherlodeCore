package motherlode.orestoolsarmor;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import motherlode.base.api.ArtificeProcessor;
import motherlode.base.api.MotherlodeAssetsEntryPoint;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

import java.util.Map;

public class MotherlodeOresToolsArmorMod implements ModInitializer, MotherlodeAssetsEntryPoint {

    public static final String MODID = "motherlode-ores-tools-armor";

    @Override
    public void onInitialize() {

        // TODO
    }

    @Override
    public void registerAssets(ArtificeResourcePack.ClientResourcePackBuilder pack) {

        /* for(Map.Entry<Identifier, ArtificeProcessor> entry: MotherlodeOresToolsArmorItems.ARTIFICE_PROCESSORS.entrySet()) {

            entry.getValue().accept(pack, entry.getKey());
        } */
    }
}
