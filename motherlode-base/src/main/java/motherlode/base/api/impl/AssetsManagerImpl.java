package motherlode.base.api.impl;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import motherlode.base.api.AssetProcessor;
import motherlode.base.api.AssetsManager;
import motherlode.base.api.DataProcessor;

public class AssetsManagerImpl implements AssetsManager {
    public static final AssetsManagerImpl INSTANCE = new AssetsManagerImpl();

    private List<Pair<Identifier, AssetProcessor>> assetProcessors = new ArrayList<>();
    private List<Pair<Identifier, DataProcessor>> dataProcessors = new ArrayList<>();

    @Override
    public void addAssets(Identifier id, AssetProcessor p) {
        this.assetProcessors.add(new Pair<>(id, p));
    }

    public List<Pair<Identifier, AssetProcessor>> getAssets() {
        return this.assetProcessors;
    }

    public void removeAssetProcessorList() {
        this.assetProcessors = null;
    }

    @Override
    public void addData(Identifier id, DataProcessor p) {
        this.dataProcessors.add(new Pair<>(id, p));
    }

    public List<Pair<Identifier, DataProcessor>> getData() {
        return this.dataProcessors;
    }

    public void removeDataProcessorList() {
        this.dataProcessors = null;
    }
}
