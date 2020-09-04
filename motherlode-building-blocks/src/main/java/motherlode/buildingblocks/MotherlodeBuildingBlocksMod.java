package motherlode.buildingblocks;

import net.fabricmc.api.ModInitializer;

public class MotherlodeBuildingBlocksMod implements ModInitializer {

    public static final String MODID = "motherlode-building-blocks";

    @Override
    public void onInitialize() {

        MotherlodeBuildingBlocks.init();
    }
}
