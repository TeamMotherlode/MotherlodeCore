package motherlode.base.api;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface ArtificeProcessor<T> extends BiConsumer<ArtificeResourcePack.ClientResourcePackBuilder, T> {

    default ArtificeResourcePack.ClientResourcePackBuilder process(ArtificeResourcePack.ClientResourcePackBuilder pack, T t) {

        accept(pack, t);
        return pack;
    }
    default ArtificeProcessor<T> after(BiConsumer<ArtificeResourcePack.ClientResourcePackBuilder, T> before) {

        return (pack, t) -> {

            before.accept(pack, t);
            this.accept(pack, t);
        };
    }
}
