package motherlode.base.api;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface ArtificeProcessor extends BiConsumer<ArtificeResourcePack.ClientResourcePackBuilder, Identifier> {

    default ArtificeResourcePack.ClientResourcePackBuilder process(ArtificeResourcePack.ClientResourcePackBuilder pack, Identifier id) {

        accept(pack, id);
        return pack;
    }
    default ArtificeProcessor after(BiConsumer<ArtificeResourcePack.ClientResourcePackBuilder, Identifier> before) {

        return (pack, id) -> {

            before.accept(pack, id);
            this.accept(pack, id);
        };
    }
}
