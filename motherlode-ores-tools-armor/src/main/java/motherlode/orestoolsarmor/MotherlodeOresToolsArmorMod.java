package motherlode.orestoolsarmor;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import motherlode.base.api.ArtificeProcessor;
import motherlode.base.api.MotherlodeAssetsEntryPoint;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

public class MotherlodeOresToolsArmorMod implements ModInitializer, MotherlodeAssetsEntryPoint {

    public static final String MODID = "motherlode-ores-tools-armor";

    @Override
    public void onInitialize() {

        MotherlodeOresToolsArmorBlocks.init();
        MotherlodeOresToolsArmorItems.init();
        MotherlodeOresToolsArmorFeatures.register();
    }

    @Override
    public void registerAssets(ArtificeResourcePack.ClientResourcePackBuilder pack) {

        for(Pair<Identifier, ArtificeProcessor> entry: MotherlodeOresToolsArmorItems.ARTIFICE_PROCESSORS) {

            entry.getRight().accept(pack, entry.getLeft());
        }
        for(Pair<Identifier, ArtificeProcessor> entry: MotherlodeOresToolsArmorBlocks.ARTIFICE_PROCESSORS) {

            entry.getRight().accept(pack, entry.getLeft());
        }
    }
}