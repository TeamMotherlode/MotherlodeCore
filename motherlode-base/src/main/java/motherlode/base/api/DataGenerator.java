package motherlode.base.api;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.util.Identifier;
import java.util.Objects;
import java.util.function.BiConsumer;

public interface DataGenerator extends BiConsumer<ArtificeResourcePack.ServerResourcePackBuilder, Identifier> {

    default ArtificeResourcePack.ServerResourcePackBuilder process(ArtificeResourcePack.ServerResourcePackBuilder pack, Identifier id) {

        accept(pack, id);
        return pack;
    }
    default DataGenerator after(BiConsumer<? super ArtificeResourcePack.ServerResourcePackBuilder, ? super Identifier> before) {
        Objects.requireNonNull(before);

        return (pack, id) -> {

            before.accept(pack, id);
            this.accept(pack, id);
        };
    }
    @Override
    default DataGenerator andThen(BiConsumer<? super ArtificeResourcePack.ServerResourcePackBuilder, ? super Identifier> after) {
        Objects.requireNonNull(after);

        return (pack, id) -> {

            this.accept(pack, id);
            after.accept(pack, id);
        };
    }
}
