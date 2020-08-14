package motherlode.base;

import motherlode.base.api.ArtificeProcessor;
import net.minecraft.util.Identifier;

public class CommonArtificeProcessors {

    public static final ArtificeProcessor<Identifier> BLOCK_ITEM = (pack, id) -> {

        pack.addItemModel(id, state -> state
                .parent(Motherlode.id(id.getNamespace(), "block/" + id.getPath()))
        );
    };
    public static final ArtificeProcessor<Identifier> FULL_BLOCK = BLOCK_ITEM.after((pack, id) -> {

        pack.addBlockState(id, state -> state
                .variant("", settings -> settings
                        .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath()))
                )
        );
        pack.addBlockModel(id, state -> state
                .parent(new Identifier("block/cube_all"))
                .texture("all", Motherlode.id(id.getNamespace(), "block/" + id.getPath()))
        );
    });
    public static final ArtificeProcessor<Identifier> PLANT = (pack, id) -> {

       pack.addBlockModel(id, state -> state
                .parent(new Identifier("block/tinted_cross"))
                .texture("cross", Motherlode.id(id.getNamespace(), "block/" + id.getPath()))
        );
    };
    public static final ArtificeProcessor<Identifier> THICK_CROSS = (pack, id) -> {

        pack.addBlockModel(id, state -> state
                .parent(Motherlode.id("block/thick_cross"))
                .texture("cross", Motherlode.id(id.getNamespace(), "block/" + id.getPath()))
        );
    };
    public static final ArtificeProcessor<Identifier> FLAT_ITEM_MODEL = (pack, id) -> {

        pack.addItemModel(id, state -> state
                .parent(new Identifier("item/generated"))
                .texture("layer0", Motherlode.id(id.getNamespace(), "block/" + id.getPath()))
        );
    };
    public static final ArtificeProcessor<Identifier> DEFAULT_ITEM_MODEL = (pack, id) -> {

        pack.addItemModel(id, state -> state
                .parent(new Identifier("item/generated"))
                .texture("layer0", Motherlode.id(id.getNamespace(), "item/" + id.getPath()))
        );
    };
    public static final ArtificeProcessor<Identifier> HANDHELD_ITEM_MODEL = (pack, id) -> {

        pack.addItemModel(id, state -> state
                .parent(new Identifier("item/handheld"))
                .texture("layer0", Motherlode.id(id.getNamespace(), "item/" + id.getPath()))
        );
    };
}
