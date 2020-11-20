package motherlode.base;

import java.util.List;
import java.util.function.Consumer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.fabricmc.api.ModInitializer;
import motherlode.base.api.AssetProcessor;
import motherlode.base.api.AssetsManager;
import motherlode.base.api.DataProcessor;
import motherlode.base.api.Processor;
import motherlode.base.api.Registerable;
import motherlode.base.api.impl.AssetsManagerImpl;
import motherlode.base.api.impl.ClientRegisterImpl;
import motherlode.base.api.impl.FeaturesManagerImpl;
import motherlode.base.api.worldgen.FeaturesManager;
import com.swordglowsblue.artifice.api.Artifice;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Motherlode implements ModInitializer {
    public static final String MODID = "motherlode";
    private static final Logger LOGGER = LogManager.getLogger("Motherlode");

    @Override
    public void onInitialize() {
        // DEBUG
        /*

        getLogger().log(Level.WARN, "[Motherlode] Some debug tests are enabled. If you see this message and this is not in a development environment, please report this to the Motherlode team.");

        WoodType testWoodType = new WoodType(id(MotherlodeBase.MODID, "test"), MapColor.WOOD, MapColor.SPRUCE, (log, leaves) -> new DefaultSaplingGenerator(id(MotherlodeBase.MODID, "test_tree"),
            Feature.TREE.configure(new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(log), new SimpleBlockStateProvider(leaves), new BlobFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build())),
            new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)).register();

        */
        // DEBUG end

        MotherlodeInitEvents.MAIN.register(() -> {
            List<Pair<Identifier, DataProcessor>> data = AssetsManagerImpl.INSTANCE.getData();
            Artifice.registerDataPack(Motherlode.id("data_pack"), pack ->
                data.forEach(pair ->
                    pair.getRight().accept(pack, pair.getLeft())));
            AssetsManagerImpl.INSTANCE.removeDataProcessorList();
        });

        MotherlodeInitEvents.MAIN.register(FeaturesManagerImpl.INSTANCE::addFeatures);

        MotherlodeInitEvents.CLIENT.register(() -> ClientRegisterImpl.INSTANCE.getClientConsumers()
            .forEach(pair -> pair.getRight().accept(pair.getLeft())));

        MotherlodeInitEvents.CLIENT.register(() -> {
            List<Pair<Identifier, AssetProcessor>> assets = AssetsManagerImpl.INSTANCE.getAssets();

            Artifice.registerAssetPack(Motherlode.id("resource_pack"), pack ->
                assets.forEach(pair ->
                    pair.getRight().accept(pack, pair.getLeft())));
            AssetsManagerImpl.INSTANCE.removeAssetProcessorList();
        });
    }

    /**
     * Logs the given message and module in the format {@code [Module] Message}.
     *
     * @param level   The severity level of the log message.
     * @param module  The (human-readable) name of the module that logs this message.
     * @param message The message to log.
     */
    public static void log(Level level, String module, CharSequence message) {
        getLogger().log(level, "[" + module + "] " + message);
    }

    /**
     * Logs the given message and module in the format {@code [Module] Message}.
     *
     * @param level   The severity level of the log message.
     * @param module  The (human-readable) name of the module that logs this message.
     * @param message The object to log. The {@code toString} method of the object will be used to get the actual message (or {@code null} if the object is null).
     */
    public static void log(Level level, String module, Object message) {
        getLogger().log(level, "[" + module + "] " + message);
    }

    /**
     * Returns a {@link Logger} instance for Motherlode.
     *
     * @return A logger.
     */
    public static Logger getLogger() {
        return LOGGER;
    }

    /**
     * Registers something.
     *
     * @param registerable The {@link Registerable} used to register the thing.
     * @param id           The {@link Identifier} that will be passed to the {@code Registerable}, {@code AssetProcessor} and {@code DataProcessor}.
     * @param t            The thing to register.
     * @param <T>          The type of the thing.
     * @return The thing that was registered
     */
    public static <T> T register(Registerable<?> registerable, Identifier id, T t) {
        return register(registerable, id, t, null, null, null, null);
    }

    /**
     * Registers something.
     *
     * @param registerable The {@link Registerable} used to register the thing.
     * @param id           The {@link Identifier} that will be passed to the {@code Registerable}, {@code AssetProcessor} and {@code DataProcessor}.
     * @param t            The thing to register.
     * @param data         A {@link DataProcessor} that can be used to register data for the thing using Artifice. May be {@code null}.
     * @param <T>          The type of the thing.
     * @return The thing that was registered
     */
    public static <T> T register(Registerable<?> registerable, Identifier id, T t, DataProcessor data) {
        return register(registerable, id, t, null, null, null, data);
    }

    /**
     * Registers something.
     *
     * @param registerable The {@link Registerable} used to register the thing.
     * @param id           The {@link Identifier} that will be passed to the {@code Registerable}, {@code AssetProcessor} and {@code DataProcessor}.
     * @param t            The thing to register.
     * @param assets       An {@link AssetProcessor} that can be used to register assets for the thing using Artifice. May be {@code null}.
     * @param <T>          The type of the thing.
     * @return The thing that was registered
     */
    public static <T> T register(Registerable<?> registerable, Identifier id, T t, AssetProcessor assets) {
        return register(registerable, id, t, null, null, assets, null);
    }

    /**
     * Registers something.
     *
     * @param registerable The {@link Registerable} used to register the thing.
     * @param id           The {@link Identifier} that will be passed to the {@code Registerable}, {@code AssetProcessor} and {@code DataProcessor}.
     * @param t            The thing to register.
     * @param assets       An {@link AssetProcessor} that can be used to register assets for the thing using Artifice. May be {@code null}.
     * @param data         A {@link DataProcessor} that can be used to register data for the thing using Artifice. May be {@code null}.
     * @param <T>          The type of the thing.
     * @return The thing that was registered
     */
    public static <T> T register(Registerable<?> registerable, Identifier id, T t, AssetProcessor assets, DataProcessor data) {
        return register(registerable, id, t, null, null, assets, data);
    }

    /**
     * Registers something.
     *
     * @param registerable   The {@link Registerable} used to register the thing.
     * @param id             The {@link Identifier} that will be passed to the {@code Registerable}, {@code AssetProcessor} and {@code DataProcessor}.
     * @param t              The thing to register.
     * @param clientConsumer A {@link Consumer} that will be only be run on the client. The {@code Identifier id} will be passed to this.
     * @param <T>            The type of the thing.
     * @return The thing that was registered
     */
    public static <T> T register(Registerable<?> registerable, Identifier id, T t, Consumer<Identifier> clientConsumer) {
        return register(registerable, id, t, null, clientConsumer, null, null);
    }

    /**
     * Registers something.
     *
     * @param registerable   The {@link Registerable} used to register the thing.
     * @param id             The {@link Identifier} that will be passed to the {@code Registerable}, {@code AssetProcessor} and {@code DataProcessor}.
     * @param t              The thing to register.
     * @param clientConsumer A {@link Consumer} that will be only be run on the client. The {@code Identifier id} will be passed to this.
     * @param data           A {@link DataProcessor} that can be used to register data for the thing using Artifice. May be {@code null}.
     * @param <T>            The type of the thing.
     * @return The thing that was registered
     */
    public static <T> T register(Registerable<?> registerable, Identifier id, T t, Consumer<Identifier> clientConsumer, DataProcessor data) {
        return register(registerable, id, t, null, clientConsumer, null, data);
    }

    /**
     * Registers something.
     *
     * @param registerable   The {@link Registerable} used to register the thing.
     * @param id             The {@link Identifier} that will be passed to the {@code Registerable}, {@code AssetProcessor} and {@code DataProcessor}.
     * @param t              The thing to register.
     * @param clientConsumer A {@link Consumer} that will be only be run on the client. The {@code Identifier id} will be passed to this.
     * @param assets         An {@link AssetProcessor} that can be used to register assets for the thing using Artifice. May be {@code null}.
     * @param <T>            The type of the thing.
     * @return The thing that was registered
     */
    public static <T> T register(Registerable<?> registerable, Identifier id, T t, Consumer<Identifier> clientConsumer, AssetProcessor assets) {
        return register(registerable, id, t, null, clientConsumer, assets, null);
    }

    /**
     * Registers something.
     *
     * @param registerable   The {@link Registerable} used to register the thing.
     * @param id             The {@link Identifier} that will be passed to the {@code Registerable}, {@code AssetProcessor} and {@code DataProcessor}.
     * @param t              The thing to register.
     * @param clientConsumer A {@link Consumer} that will be only be run on the client. The {@code Identifier id} will be passed to this.
     * @param assets         An {@link AssetProcessor} that can be used to register assets for the thing using Artifice. May be {@code null}.
     * @param data           A {@link DataProcessor} that can be used to register data for the thing using Artifice. May be {@code null}.
     * @param <T>            The type of the thing.
     * @return The thing that was registered
     */
    public static <T> T register(Registerable<?> registerable, Identifier id, T t, Consumer<Identifier> clientConsumer, AssetProcessor assets, DataProcessor data) {
        return register(registerable, id, t, null, clientConsumer, assets, data);
    }

    /**
     * Registers something.
     *
     * @param registerable The {@link Registerable} used to register the thing.
     * @param id           The {@link Identifier} that will be passed to the {@code Registerable}, {@code AssetProcessor} and {@code DataProcessor}.
     * @param t            The thing to register.
     * @param p            A {@link Processor} that can be used to do something with the thing after it is registered. May be {@code null}.
     * @param <T>          The type of the thing.
     * @return The thing that was registered
     */
    public static <T> T register(Registerable<?> registerable, Identifier id, T t, Processor<? super T> p) {
        return register(registerable, id, t, p, null, null, null);
    }

    /**
     * Registers something.
     *
     * @param registerable The {@link Registerable} used to register the thing.
     * @param id           The {@link Identifier} that will be passed to the {@code Registerable}, {@code AssetProcessor} and {@code DataProcessor}.
     * @param t            The thing to register.
     * @param p            A {@link Processor} that can be used to do something with the thing after it is registered. May be {@code null}.
     * @param data         A {@link DataProcessor} that can be used to register data for the thing using Artifice. May be {@code null}.
     * @param <T>          The type of the thing.
     * @return The thing that was registered
     */
    public static <T> T register(Registerable<?> registerable, Identifier id, T t, Processor<? super T> p, DataProcessor data) {
        return register(registerable, id, t, p, null, null, data);
    }

    /**
     * Registers something.
     *
     * @param registerable The {@link Registerable} used to register the thing.
     * @param id           The {@link Identifier} that will be passed to the {@code Registerable}, {@code AssetProcessor} and {@code DataProcessor}.
     * @param t            The thing to register.
     * @param p            A {@link Processor} that can be used to do something with the thing after it is registered. May be {@code null}.
     * @param assets       An {@link AssetProcessor} that can be used to register assets for the thing using Artifice. May be {@code null}.
     * @param <T>          The type of the thing.
     * @return The thing that was registered
     */
    public static <T> T register(Registerable<?> registerable, Identifier id, T t, Processor<? super T> p, AssetProcessor assets) {
        return register(registerable, id, t, p, null, assets, null);
    }

    /**
     * Registers something.
     *
     * @param registerable The {@link Registerable} used to register the thing.
     * @param id           The {@link Identifier} that will be passed to the {@code Registerable}, {@code AssetProcessor} and {@code DataProcessor}.
     * @param t            The thing to register.
     * @param p            A {@link Processor} that can be used to do something with the thing after it is registered. May be {@code null}.
     * @param assets       An {@link AssetProcessor} that can be used to register assets for the thing using Artifice. May be {@code null}.
     * @param data         A {@link DataProcessor} that can be used to register data for the thing using Artifice. May be {@code null}.
     * @param <T>          The type of the thing.
     * @return The thing that was registered
     */
    public static <T> T register(Registerable<?> registerable, Identifier id, T t, Processor<? super T> p, AssetProcessor assets, DataProcessor data) {
        return register(registerable, id, t, p, null, assets, data);
    }

    /**
     * Registers something.
     *
     * @param registerable   The {@link Registerable} used to register the thing.
     * @param id             The {@link Identifier} that will be passed to the {@code Registerable}, {@code AssetProcessor} and {@code DataProcessor}.
     * @param t              The thing to register.
     * @param p              A {@link Processor} that can be used to do something with the thing after it is registered. May be {@code null}.
     * @param clientConsumer A {@link Consumer} that will be only be run on the client. The {@code Identifier id} will be passed to this.
     * @param <T>            The type of the thing.
     * @return The thing that was registered
     */
    public static <T> T register(Registerable<?> registerable, Identifier id, T t, Processor<? super T> p, Consumer<Identifier> clientConsumer) {
        return register(registerable, id, t, p, clientConsumer, null, null);
    }

    /**
     * Registers something.
     *
     * @param registerable   The {@link Registerable} used to register the thing.
     * @param id             The {@link Identifier} that will be passed to the {@code Registerable}, {@code AssetProcessor} and {@code DataProcessor}.
     * @param t              The thing to register.
     * @param p              A {@link Processor} that can be used to do something with the thing after it is registered. May be {@code null}.
     * @param clientConsumer A {@link Consumer} that will be only be run on the client. The {@code Identifier id} will be passed to this.
     * @param data           A {@link DataProcessor} that can be used to register data for the thing using Artifice. May be {@code null}.
     * @param <T>            The type of the thing.
     * @return The thing that was registered
     */
    public static <T> T register(Registerable<?> registerable, Identifier id, T t, Processor<? super T> p, Consumer<Identifier> clientConsumer, DataProcessor data) {
        return register(registerable, id, t, p, clientConsumer, null, data);
    }

    /**
     * Registers something.
     *
     * @param registerable   The {@link Registerable} used to register the thing.
     * @param id             The {@link Identifier} that will be passed to the {@code Registerable}, {@code AssetProcessor} and {@code DataProcessor}.
     * @param t              The thing to register.
     * @param p              A {@link Processor} that can be used to do something with the thing after it is registered. May be {@code null}.
     * @param clientConsumer A {@link Consumer} that will be only be run on the client. The {@code Identifier id} will be passed to this.
     * @param assets         An {@link AssetProcessor} that can be used to register assets for the thing using Artifice. May be {@code null}.
     * @param <T>            The type of the thing.
     * @return The thing that was registered
     */
    public static <T> T register(Registerable<?> registerable, Identifier id, T t, Processor<? super T> p, Consumer<Identifier> clientConsumer, AssetProcessor assets) {
        return register(registerable, id, t, p, clientConsumer, assets, null);
    }

    /**
     * Registers something.
     *
     * @param registerable   The {@link Registerable} used to register the thing.
     * @param id             The {@link Identifier} that will be passed to the {@code Registerable}, {@code AssetProcessor} and {@code DataProcessor}.
     * @param t              The thing to register.
     * @param p              A {@link Processor} that can be used to do something with the thing after it is registered. May be {@code null}.
     * @param clientConsumer A {@link Consumer} that will be only be run on the client. The {@code Identifier id} will be passed to this.
     * @param assets         An {@link AssetProcessor} that can be used to register assets for the thing using Artifice. May be {@code null}.
     * @param data           A {@link DataProcessor} that can be used to register data for the thing using Artifice. May be {@code null}.
     * @param <T>            The type of the thing.
     * @return The thing that was registered
     */
    public static <T> T register(Registerable<?> registerable, Identifier id, T t, Processor<? super T> p, Consumer<Identifier> clientConsumer, AssetProcessor assets, DataProcessor data) {
        registerable.register(id);

        if (p != null) p.accept(t);
        if (clientConsumer != null) registerOnClient(id, clientConsumer);
        if (assets != null) getAssetsManager().addAssets(id, assets);
        if (data != null) getAssetsManager().addData(id, data);

        return t;
    }

    /**
     * Adds a task that will only be run on the client.
     *
     * @param id             The {@link Identifier} that will be passed to the {@code Consumer} when it runs.
     * @param clientConsumer The {@link Consumer} that will be run on the client.
     */
    public static void registerOnClient(Identifier id, Consumer<Identifier> clientConsumer) {
        if (MotherlodeBase.isModuleInitializationDone())
            throw new IllegalStateException("Trying to register on client outside motherlode:init entry point.");

        ClientRegisterImpl.INSTANCE.addClientConsumer(id, clientConsumer);
    }

    /**
     * Returns an implementation of {@link AssetsManager}. Should only be called from a {@code motherlode:init} entry point.
     *
     * @return An implementation of {@code AssetsManager}
     * @throws IllegalStateException if not called from a {@code motherlode:init} entry point.
     */
    public static AssetsManager getAssetsManager() {
        if (MotherlodeBase.isModuleInitializationDone())
            throw new IllegalStateException("Trying to add assets outside motherlode:init entry point.");
        return AssetsManagerImpl.INSTANCE;
    }

    /**
     * Returns an implementation of {@link FeaturesManager}. Should only be called from a {@code motherlode:init} entry point.
     *
     * @return An implementation of {@code DataManager}
     * @throws IllegalStateException if not called from a {@code motherlode:init} entry point.
     */
    public static FeaturesManager getFeaturesManager() {
        if (MotherlodeBase.isModuleInitializationDone())
            throw new IllegalStateException("Trying to add data outside motherlode:init entry point.");
        return FeaturesManagerImpl.INSTANCE;
    }

    /**
     * Creates an {@link Identifier} from the given namespace and name.
     *
     * @param namespace The namespace to use for the {@code Identifier}.
     * @param name      The path to use for the {@code Identifier}.
     * @return The created {@code Identifier}
     */
    public static Identifier id(String namespace, String name) {
        return new Identifier(namespace, name);
    }

    /**
     * Creates an {@link Identifier} using {@code motherlode} as the namespace.
     *
     * @param name The path to use for the {@code Identifier}.
     * @return The created {@code Identifier}
     */
    private static Identifier id(String name) {
        return id(MODID, name);
    }
}
