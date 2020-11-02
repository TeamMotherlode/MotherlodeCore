package motherlode.base;

import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import motherlode.base.api.AssetProcessor;
import motherlode.base.api.DataProcessor;
import motherlode.base.api.OreTarget;
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

    @Override
    public void onInitialize() {
        FabricLoader.getInstance().getEntrypointContainers("motherlode:init", ModInitializer.class)
            .forEach(container -> container.getEntrypoint().onInitialize());

        Artifice.registerData(Motherlode.id("data_pack"), pack ->
            MotherlodeAssetsImpl.getData().forEach(pair ->
                pair.getRight().accept(pack, pair.getLeft()))
        );
        MotherlodeAssetsImpl.removeDataProcessorList();

        MotherlodeDataImpl.addOreFeatures();

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
        if (assets != null) addAssets(id, assets);
        if (data != null) addData(id, data);

        return t;
    }

    public static void addAssets(Identifier id, AssetProcessor p) {
        MotherlodeAssetsImpl.addAssets(id, p);
    }

    public static void addData(Identifier id, DataProcessor p) {
        MotherlodeAssetsImpl.addData(id, p);
    }

    public static void addOre(Identifier id, OreTarget target, BlockState state, int veinSize, int veinsPerChunk, int minY, int maxY) {
        MotherlodeDataImpl.addOre(id, target, state, veinSize, veinsPerChunk, minY, maxY);
    }

    public static Identifier id(String namespace, String name) {
        return new Identifier(namespace, name);
    }

    public static Identifier id(String name) {
        return id(MODID, name);
    }
}
