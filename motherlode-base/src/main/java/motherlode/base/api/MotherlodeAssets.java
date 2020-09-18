package motherlode.base.api;

import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import java.util.ArrayList;
import java.util.List;

public class MotherlodeAssets {

    private static final List<Pair<Identifier, AssetProcessor>> ASSET_PROCESSORS = new ArrayList<>();

    public static void addAssets(Identifier id, AssetProcessor p) {

        ASSET_PROCESSORS.add(new Pair<>(id, p));
    }

    public static List<Pair<Identifier, AssetProcessor>> getAssets() {

        return ASSET_PROCESSORS;
    }

    private static final List<Pair<Identifier, DataProcessor>> DATA_PROCESSORS = new ArrayList<>();

    public static void addData(Identifier id, DataProcessor p) {

        DATA_PROCESSORS.add(new Pair<>(id, p));
    }

    public static List<Pair<Identifier, DataProcessor>> getData() {

        return DATA_PROCESSORS;
    }
}
