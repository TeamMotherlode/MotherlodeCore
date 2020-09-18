package motherlode.base.api;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.util.Identifier;
import java.util.Objects;

public interface DataProcessor {

    void accept(ArtificeResourcePack.ServerResourcePackBuilder pack, Identifier id);

    default ArtificeResourcePack.ServerResourcePackBuilder process(ArtificeResourcePack.ServerResourcePackBuilder pack, Identifier id) {

        accept(pack, id);
        return pack;
    }
    default DataProcessor after(DataProcessor before) {
        Objects.requireNonNull(before);

        return (pack, id) -> {

            before.accept(pack, id);
            this.accept(pack, id);
        };
    }
    default DataProcessor andThen(DataProcessor after) {
        Objects.requireNonNull(after);

        return (pack, id) -> {

            this.accept(pack, id);
            after.accept(pack, id);
        };
    }
}
