package motherlode.base.api;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import java.util.ArrayList;
import java.util.List;

public class MotherlodeData {

    /**
     * Called when Motherlode's assets have to be registered with Artifice.
     */
    public static final Event<DataLoad> EVENT = EventFactory.createArrayBacked(DataLoad.class, callbacks -> pack -> {
        for (DataLoad callback : callbacks) {
            callback.registerData(pack);
        }
    });

    private static final List<Pair<Identifier, DataGenerator>> DATA_GENERATORS = new ArrayList<>();

    public static void addGenerator(Identifier id, DataGenerator p) {

        DATA_GENERATORS.add(new Pair<>(id, p));
    }

    public static List<Pair<Identifier, DataGenerator>> getGenerators() {

        return DATA_GENERATORS;
    }

    @FunctionalInterface
    public interface DataLoad {
        void registerData(ArtificeResourcePack.ServerResourcePackBuilder pack);
    }
}
