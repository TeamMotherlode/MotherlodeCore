package motherlode.base;

import com.swordglowsblue.artifice.api.builder.assets.BlockStateBuilder;
import motherlode.base.api.ArtificeProcessor;
import net.minecraft.util.Identifier;

public class CommonArtificeProcessors {

    private static final String[] facings = new String[]{"east", "north", "south", "west"};
    private static final String[] halfs = new String[]{"bottom", "top"};
    private static final String[] shapes = new String[]{"inner_left", "inner_right", "outer_left", "outer_right", "straight"};
    private static final String[] modelStrings = new String[]{"", "_inner", "_outer"};

    // models: 0 = "", 1 = "_inner", 2 = "_outer" | xs & ys: # = # * 90
    private static final int[] models = new int[]{1, 1, 2, 2, 0, 1, 1, 2, 2, 0};
    private static final int[] xs = new int[]{0, 0, 0, 0, 0, 2, 2, 2, 2, 2};
    private static final int[] ys = new int[]{3, 0, 3, 0, 0, 0, 1, 0, 1, 0, 0, 2, 2, 3, 3, 3, 3, 3, 0, 3, 3, 0, 0, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 2};

    public static final ArtificeProcessor BLOCK_ITEM = (pack, id) -> {

        pack.addItemModel(id, state -> state
                .parent(Motherlode.id(id.getNamespace(), "block/" + id.getPath()))
        );
    };

    public static final ArtificeProcessor DEFAULT_BLOCK_STATE = (pack, id) -> {

        pack.addBlockState(id, state -> state
                .variant("", settings -> settings
                        .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath()))
                )
        );
    };

    public static final ArtificeProcessor DEFAULT_BLOCK_MODEL = (pack, id) -> {

        pack.addBlockModel(id, state -> state
                .parent(new Identifier("block/cube_all"))
                .texture("all", Motherlode.id(id.getNamespace(), "block/" + id.getPath()))
        );
    };

    public static final ArtificeProcessor DEFAULT_BLOCK = DEFAULT_BLOCK_STATE.andThen(DEFAULT_BLOCK_MODEL).andThen(BLOCK_ITEM);

    public static final ArtificeProcessor PLANT = (pack, id) -> {

       pack.addBlockModel(id, state -> state
                .parent(new Identifier("block/tinted_cross"))
                .texture("cross", Motherlode.id(id.getNamespace(), "block/" + id.getPath()))
        );
    };

    public static final ArtificeProcessor THICK_CROSS = (pack, id) -> {

        pack.addBlockModel(id, state -> state
                .parent(Motherlode.id("block/thick_cross"))
                .texture("cross", Motherlode.id(id.getNamespace(), "block/" + id.getPath()))
        );
    };

    public static final ArtificeProcessor FLAT_ITEM_MODEL = (pack, id) -> {

        pack.addItemModel(id, state -> state
                .parent(new Identifier("item/generated"))
                .texture("layer0", Motherlode.id(id.getNamespace(), "block/" + id.getPath()))
        );
    };

    public static final ArtificeProcessor DEFAULT_ITEM_MODEL = (pack, id) -> {

        pack.addItemModel(id, state -> state
                .parent(new Identifier("item/generated"))
                .texture("layer0", Motherlode.id(id.getNamespace(), "item/" + id.getPath()))
        );
    };

    public static final ArtificeProcessor HANDHELD_ITEM_MODEL = (pack, id) -> {

        pack.addItemModel(id, state -> state
                .parent(new Identifier("item/handheld"))
                .texture("layer0", Motherlode.id(id.getNamespace(), "item/" + id.getPath()))
        );
    };

    public static final ArtificeProcessor PILLAR = (pack, id) -> {

        for (String variant : new String[]{"", "_horizontal"}) {
            pack.addBlockModel(Motherlode.id(id.getNamespace(), id.getPath() + variant), model -> model
                    .parent(new Identifier("block/cube_column" + variant))
                    .texture("end", Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_top"))
                    .texture("side", Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_side"))
            );
        }
        pack.addBlockState(Motherlode.id(id.getNamespace(), id.getPath()), builder -> builder
                .variant("axis=x", settings -> settings.model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_horizontal")).rotationX(90).rotationY(90))
                .variant("axis=y", settings -> settings.model(Motherlode.id(id.getNamespace(), "block/" + id.getPath())))
                .variant("axis=z", settings -> settings.model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_horizontal")).rotationX(90))
        );
    };

    public static final ArtificeProcessor STAIR = (pack, id) -> {

        String texId = id.getPath().replace("_stairs", "");
        for (int i = 0; i < 3; i++) {
            int ii = i;
            pack.addBlockModel(Motherlode.id(id.getNamespace(), id.getPath() + modelStrings[i]), model -> model
                    .parent(new Identifier("block/" + (ii == 0 ? "" : ii == 1 ? "inner_" : "outer_") + "stairs"))
                    .texture("top", new Identifier(id.getNamespace(), "block/" + texId))
                    .texture("bottom", new Identifier(id.getNamespace(), "block/" + texId))
                    .texture("side", new Identifier(id.getNamespace(), "block/" + texId))
            );
        }
        pack.addBlockState(id, builder -> stairBlockState(builder, id));
    };

    private static void stairBlockState(BlockStateBuilder builder, Identifier id) {
        int i = 0;
        for (String facing : facings) {
            int j = 0;
            for (String half : halfs) {
                for (String shape : shapes) {
                    int jj = j;
                    int ii = i;
                    builder.variant("facing=" + facing + ",half=" + half + ",shape=" + shape, settings ->
                            settings.model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + modelStrings[models[jj]]))
                                    .rotationX(xs[jj] * 90)
                                    .rotationY(ys[ii] * 90)
                                    .uvlock(xs[jj] != 0 || ys[ii] != 0));
                    i++;
                    j++;
                }
            }
        }
    }

    public static final ArtificeProcessor SLAB = (pack, id) -> {

        String texId = id.getPath().replace("_slab", "").replace("_pillar", "_pillar_side");
        for (String variant : new String[]{"_top", ""}) {
            pack.addBlockModel(Motherlode.id(id.getNamespace(), id.getPath() + variant), model -> model
                    .parent(new Identifier("block/slab" + variant))
                    .texture("top", new Identifier(id.getNamespace(), "block/" + texId))
                    .texture("bottom", new Identifier(id.getNamespace(), "block/" + texId))
                    .texture("side", new Identifier(id.getNamespace(), "block/" + texId))
            );
        }
        pack.addBlockModel(Motherlode.id(id.getNamespace(), id.getPath() + "_double"), model -> model
                .parent(new Identifier("block/cube_column"))
                .texture("end", new Identifier(id.getNamespace(), "block/" + texId))
                .texture("side", new Identifier(id.getNamespace(), "block/" + texId))
        );
        pack.addBlockState(id, builder -> builder
                .variant("type=top", settings -> settings.model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_top")))
                .variant("type=bottom", settings -> settings.model(Motherlode.id(id.getNamespace(), "block/" + id.getPath())))
                .variant("type=double", settings -> settings.model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_double")))
        );
    };
}
