package motherlode.core.redstone;

import motherlode.core.registry.MotherlodeBlockEntities;
import motherlode.core.registry.MotherlodeContainers;
import motherlode.core.registry.MotherlodeRedstoneBlocks;
import motherlode.core.registry.MotherlodeScreenHandlers;
import net.fabricmc.api.ModInitializer;

public class MotherlodeRedstoneMod implements ModInitializer {

    @Override
    public void onInitialize() {

        MotherlodeRedstoneBlocks.init();
        MotherlodeBlockEntities.init();
        MotherlodeContainers.init();
        MotherlodeScreenHandlers.init();
    }
}