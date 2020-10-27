package motherlode.base.api;

import java.util.Objects;
import net.minecraft.util.Identifier;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;

public interface AssetProcessor {
    void accept(ArtificeResourcePack.ClientResourcePackBuilder pack, Identifier id);

    default ArtificeResourcePack.ClientResourcePackBuilder process(ArtificeResourcePack.ClientResourcePackBuilder pack, Identifier id) {

        accept(pack, id);
        return pack;
    }

    default AssetProcessor after(AssetProcessor before) {
        Objects.requireNonNull(before);

        return (pack, id) -> {

            before.accept(pack, id);
            this.accept(pack, id);
        };
    }

    default AssetProcessor andThen(AssetProcessor after) {
        Objects.requireNonNull(after);

        return (pack, id) -> {

            this.accept(pack, id);
            after.accept(pack, id);
        };
    }
}
