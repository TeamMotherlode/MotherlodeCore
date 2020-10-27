package motherlode.orestoolsarmor;

import net.fabricmc.api.ModInitializer;

public class MotherlodeModule implements ModInitializer {
    public static final String MODID = "motherlode-ores-tools-armor";
    @Override
    public void onInitialize() {

        MotherlodeOresToolsArmorBlocks.init();
        MotherlodeOresToolsArmorItems.init();
        MotherlodeOresToolsArmorTags.init();
    }
}
