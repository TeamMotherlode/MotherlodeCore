package motherlode.orestoolsarmor;

import net.fabricmc.api.ModInitializer;

public class MotherlodeOresToolsArmorMod implements ModInitializer {

    public static final String MODID = "motherlode-ores-tools-armor";

    @Override
    public void onInitialize() {

        MotherlodeOresToolsArmorBlocks.init();
        MotherlodeOresToolsArmorItems.init();
        MotherlodeOresToolsArmorFeatures.register();
    }
}
