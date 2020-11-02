package motherlode.base.api.impl;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import motherlode.base.api.AssetProcessor;
import motherlode.base.api.DataProcessor;

public class MotherlodeAssetsImpl {
    private static List<Pair<Identifier, AssetProcessor>> ASSET_PROCESSORS = new ArrayList<>();

    public static void addAssets(Identifier id, AssetProcessor p) {

        ASSET_PROCESSORS.add(new Pair<>(id, p));
    }

    public static List<Pair<Identifier, AssetProcessor>> getAssets() {

        return ASSET_PROCESSORS;
    }

    public static void removeAssetProcessorList() {
        ASSET_PROCESSORS = null;
    }

    private static List<Pair<Identifier, DataProcessor>> DATA_PROCESSORS = new ArrayList<>();

    public static void addData(Identifier id, DataProcessor p) {

        DATA_PROCESSORS.add(new Pair<>(id, p));
    }

    public static List<Pair<Identifier, DataProcessor>> getData() {

        return DATA_PROCESSORS;
    }

    public static void removeDataProcessorList() {
        DATA_PROCESSORS = null;
    }
}
