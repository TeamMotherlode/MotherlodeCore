package motherlode.buildingblocks;

import net.fabricmc.api.ModInitializer;

public class MotherlodeModule implements ModInitializer {
    public static final String MODID = "motherlode-building-blocks";

    @Override
    public void onInitialize() {
        MotherlodeBuildingBlocks.init();
    }
}
