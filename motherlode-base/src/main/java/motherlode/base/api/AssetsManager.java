package motherlode.base.api;

import net.minecraft.util.Identifier;

public interface AssetsManager {
    void addAssets(Identifier id, AssetProcessor assets);
    void addData(Identifier id, DataProcessor data);
}
