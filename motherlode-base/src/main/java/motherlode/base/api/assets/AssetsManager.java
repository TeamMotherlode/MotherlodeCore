package motherlode.base.api.assets;

import net.minecraft.util.Identifier;
import motherlode.base.Motherlode;

/**
 * Assets and data can be registered using this interface.
 * An implementation of it can be got by calling {@link Motherlode#getAssetsManager()}.
 */
public interface AssetsManager {
    /**
     * Uses the given {@link AssetsGenerator} to register assets.
     *
     * @param assetsGenerator The {@code AssetsGenerator} that will be called to register the assets.
     */
    void addAssets(AssetsGenerator assetsGenerator);

    /**
     * Uses the given {@link DataGenerator} to register data.
     *
     * @param data The {@code DataGenerator} that will be called to register the data.
     */
    void addData(DataGenerator data);

    /**
     * Uses the given {@link AssetProcessor} to register assets.
     *
     * @param id     The {@link Identifier} that will be passed to the given {@code AssetProcessor}.
     * @param assets The {@code AssetProcessor} that will be called to register the assets.
     */
    default void addAssets(Identifier id, AssetProcessor assets) {
        this.addAssets(pack -> assets.accept(pack, id));
    }

    /**
     * Uses the given {@link DataProcessor} to register data.
     *
     * @param id   The {@link Identifier} that will be passed to the given {@code DataProcessor}.
     * @param data The {@code DataProcessor} that will be called to register the data.
     */
    default void addData(Identifier id, DataProcessor data) {
        this.addData(pack -> data.accept(pack, id));
    }
}
