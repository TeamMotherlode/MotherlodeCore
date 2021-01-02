package motherlode.base.impl.assets;

import java.util.ArrayList;
import java.util.List;
import motherlode.base.api.assets.AssetsGenerator;
import motherlode.base.api.assets.AssetsManager;
import motherlode.base.api.assets.DataGenerator;

public class AssetsManagerImpl implements AssetsManager {
    public static final AssetsManagerImpl INSTANCE = new AssetsManagerImpl();

    private List<AssetsGenerator> assetGenerators = new ArrayList<>();
    private List<DataGenerator> dataGenerators = new ArrayList<>();

    @Override
    public void addAssets(AssetsGenerator p) {
        this.assetGenerators.add(p);
    }

    public List<AssetsGenerator> getAssets() {
        return this.assetGenerators;
    }

    public void removeAssetProcessorList() {
        this.assetGenerators = null;
    }

    @Override
    public void addData(DataGenerator p) {
        this.dataGenerators.add(p);
    }

    public List<DataGenerator> getData() {
        return this.dataGenerators;
    }

    public void removeDataProcessorList() {
        this.dataGenerators = null;
    }
}
