package motherlode.base;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.Level;

public class MotherlodeBase implements ModInitializer, DedicatedServerModInitializer {
    public static final String MODID = "motherlode-base";

    private static boolean moduleInitDone;

    @Override
    public void onInitialize() {
        FabricLoader.getInstance().getEntrypointContainers("motherlode:init", ModInitializer.class)
            .forEach(container -> container.getEntrypoint().onInitialize());
        moduleInitDone = true;

        MotherlodeInitEvents.MAIN.invoker().initialize();

        log(Level.INFO, "[Motherlode] Initialized.");
    }

    @Override
    public void onInitializeServer() {
        MotherlodeInitEvents.DEDICATED_SERVER.invoker().initialize();
    }

    public static boolean isModuleInitializationDone() {
        return moduleInitDone;
    }

    private static void log(Level level, CharSequence message) {
        Motherlode.getLogger().log(level, message);
    }

    private static void log(Level level, Object message) {
        Motherlode.getLogger().log(level, String.valueOf(message));
    }
}
