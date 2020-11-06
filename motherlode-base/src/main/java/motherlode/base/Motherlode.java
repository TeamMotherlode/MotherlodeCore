package motherlode.base;

import java.util.List;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import motherlode.base.api.AssetProcessor;
import motherlode.base.api.AssetsManager;
import motherlode.base.api.DataManager;
import motherlode.base.api.DataProcessor;
import motherlode.base.api.Processor;
import motherlode.base.api.Registerable;
import motherlode.base.api.impl.MotherlodeAssetsImpl;
import motherlode.base.api.impl.MotherlodeDataImpl;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import com.swordglowsblue.artifice.api.Artifice;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Motherlode implements ModInitializer {
    public static final String MODID = "motherlode";
    private static final Logger LOGGER = LogManager.getLogger("Motherlode");
    private static boolean moduleInitDone;

    @Override
    public void onInitialize() {
        FabricLoader.getInstance().getEntrypointContainers("motherlode:init", ModInitializer.class)
            .forEach(container -> container.getEntrypoint().onInitialize());

        moduleInitDone = true;

        List<Pair<Identifier, DataProcessor>> data = MotherlodeAssetsImpl.INSTANCE.getData();
        Artifice.registerDataPack(Motherlode.id("data_pack"), pack ->
            data.forEach(pair ->
                pair.getRight().accept(pack, pair.getLeft())));
        MotherlodeAssetsImpl.INSTANCE.removeDataProcessorList();

        MotherlodeDataImpl.INSTANCE.addOreFeatures();

        log(Level.INFO, "[Motherlode] Initialized.");
    }

    public static void log(Level level, CharSequence message) {
        LOGGER.log(level, message);
    }

    public static void log(Level level, Object message) {
        LOGGER.log(level, String.valueOf(message));
    }

    public static void log(Level level, String module, CharSequence message) {
        LOGGER.log(level, "[" + module + "] " + message);
    }

    public static void log(Level level, String module, Object message) {
        LOGGER.log(level, "[" + module + "] " + message);
    }

    public static <T> T register(Registerable<?> registerable, Identifier id, T t, Processor<? super T> p, AssetProcessor assets, DataProcessor data) {
        registerable.register(id);

        if (p != null) p.accept(t);
        if (assets != null) getAssetsManager().addAssets(id, assets);
        if (data != null) getAssetsManager().addData(id, data);

        return t;
    }

    public static AssetsManager getAssetsManager() {
        if (moduleInitDone)
            throw new IllegalStateException("Trying to add assets outside motherlode:init entry point.");
        return MotherlodeAssetsImpl.INSTANCE;
    }

    public static DataManager getDataManager() {
        if (moduleInitDone) throw new IllegalStateException("Trying to add data outside motherlode:init entry point.");
        return MotherlodeDataImpl.INSTANCE;
    }

    public static Identifier id(String namespace, String name) {
        return new Identifier(namespace, name);
    }

    public static Identifier id(String name) {
        return id(MODID, name);
    }
}
