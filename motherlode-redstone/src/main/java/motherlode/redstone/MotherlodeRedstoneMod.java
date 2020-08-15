package motherlode.redstone;

import motherlode.redstone.registry.MotherlodeBlockEntities;
import motherlode.redstone.registry.MotherlodeContainers;
import motherlode.redstone.registry.MotherlodeRedstoneBlocks;
import motherlode.redstone.registry.MotherlodeScreenHandlers;
import net.fabricmc.api.ModInitializer;

public class MotherlodeRedstoneMod implements ModInitializer {

    public static final String MODID = "motherlode-redstone";

    @Override
    public void onInitialize() {

        MotherlodeRedstoneBlocks.init();
        MotherlodeBlockEntities.init();
        MotherlodeContainers.init();
        MotherlodeScreenHandlers.init();
    }
}