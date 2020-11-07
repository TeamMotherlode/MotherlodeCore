package motherlode.base.api;

import net.minecraft.util.Identifier;
import motherlode.base.Motherlode;

/**
 * Assets and data can be registered using this interface.
 * An implementation of it is returned by {@link Motherlode#getAssetsManager()}.
 */
public interface AssetsManager {
    /**
     * Uses the given {@link AssetProcessor} to register assets.
     *
     * @param id     The {@link Identifier} that will be passed to the given {@code AssetProcessor}.
     * @param assets The {@code AssetProcessor} that will be called to register the assets.
     */
    void addAssets(Identifier id, AssetProcessor assets);

    /**
     * Uses the given {@link DataProcessor} to register data.
     *
     * @param id   The {@link Identifier} that will be passed to the given {@code DataProcessor}.
     * @param data The {@code DataProcessor} that will be called to register the data.
     */
    void addData(Identifier id, DataProcessor data);
}
