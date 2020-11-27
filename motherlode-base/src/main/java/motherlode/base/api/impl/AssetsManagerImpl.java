package motherlode.base.api.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import motherlode.base.api.AssetsManager;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;

public class AssetsManagerImpl implements AssetsManager {
    public static final AssetsManagerImpl INSTANCE = new AssetsManagerImpl();

    private List<Consumer<ArtificeResourcePack.ClientResourcePackBuilder>> assetProcessors = new ArrayList<>();
    private List<Consumer<ArtificeResourcePack.ServerResourcePackBuilder>> dataProcessors = new ArrayList<>();

    @Override
    public void addAssets(Consumer<ArtificeResourcePack.ClientResourcePackBuilder> p) {
        this.assetProcessors.add(p);
    }

    public List<Consumer<ArtificeResourcePack.ClientResourcePackBuilder>> getAssets() {
        return this.assetProcessors;
    }

    public void removeAssetProcessorList() {
        this.assetProcessors = null;
    }

    @Override
    public void addData(Consumer<ArtificeResourcePack.ServerResourcePackBuilder> p) {
        this.dataProcessors.add(p);
    }

    public List<Consumer<ArtificeResourcePack.ServerResourcePackBuilder>> getData() {
        return this.dataProcessors;
    }

    public void removeDataProcessorList() {
        this.dataProcessors = null;
    }
}
