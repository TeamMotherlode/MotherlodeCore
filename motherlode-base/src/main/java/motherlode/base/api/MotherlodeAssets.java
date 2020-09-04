package motherlode.base.api;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import java.util.ArrayList;
import java.util.List;

public class MotherlodeAssets {

    /**
     * Called when Motherlode's assets have to be registered with Artifice.
     */
    public static final Event<AssetsLoad> EVENT = EventFactory.createArrayBacked(AssetsLoad.class, callbacks -> pack -> {
        for (AssetsLoad callback : callbacks) {
            callback.registerAssets(pack);
        }
    });

    private static final List<Pair<Identifier, ArtificeProcessor>> ARTIFICE_PROCESSORS = new ArrayList<>();

    public static void addProcessor(Identifier id, ArtificeProcessor p) {

        ARTIFICE_PROCESSORS.add(new Pair<>(id, p));
    }

    public static List<Pair<Identifier, ArtificeProcessor>> getProcessors() {

        return ARTIFICE_PROCESSORS;
    }

    @FunctionalInterface
    public interface AssetsLoad {
        void registerAssets(ArtificeResourcePack.ClientResourcePackBuilder pack);
    }
}
