package motherlode.base.api.assets;

import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.util.Identifier;
import motherlode.base.Motherlode;
import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.builder.assets.BlockStateBuilder;
import com.swordglowsblue.artifice.api.builder.assets.ModelBuilder;

public final class CommonAssets {
    private static final String[] facings = new String[] { "east", "north", "south", "west" };
    private static final String[] halfs = new String[] { "bottom", "top" };
    private static final String[] shapes = new String[] { "inner_left", "inner_right", "outer_left", "outer_right", "straight" };
    private static final String[] modelStrings = new String[] { "", "_inner", "_outer" };

    // models: 0 = "", 1 = "_inner", 2 = "_outer" | xs & ys: # = # * 90
    private static final int[] models = new int[] { 1, 1, 2, 2, 0, 1, 1, 2, 2, 0 };
    private static final int[] xs = new int[] { 0, 0, 0, 0, 0, 2, 2, 2, 2, 2 };
    private static final int[] ys = new int[] { 3, 0, 3, 0, 0, 0, 1, 0, 1, 0, 0, 2, 2, 3, 3, 3, 3, 3, 0, 3, 3, 0, 0, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 2 };

    public static final Function<Identifier, AssetProcessor> BLOCK_ITEM_FUNCTION = modelId -> (pack, id) ->
        pack.addItemModel(modelId, state -> state
            .parent(Motherlode.id(id, name -> "block/" + name))
        );

    public static final AssetProcessor BLOCK_ITEM = (pack, id) -> BLOCK_ITEM_FUNCTION.apply(id).accept(pack, id);

    public static final AssetProcessor FLAT_BLOCK_ITEM_MODEL = (pack, id) ->
        pack.addItemModel(id, state -> state
            .parent(new Identifier("item/generated"))
            .texture("layer0", Motherlode.id(id, name -> "block/" + name))
        );

    public static final AssetProcessor DEFAULT_ITEM_MODEL = (pack, id) ->
        pack.addItemModel(id, state -> state
            .parent(new Identifier("item/generated"))
            .texture("layer0", Motherlode.id(id, name -> "item/" + name))
        );

    public static final AssetProcessor HANDHELD_ITEM_MODEL = (pack, id) ->
        pack.addItemModel(id, state -> state
            .parent(new Identifier("item/handheld"))
            .texture("layer0", Motherlode.id(id, name -> "item/" + name))
        );

    public static final AssetProcessor DEFAULT_BLOCK_STATE = (pack, id) ->
        pack.addBlockState(id, state -> state
            .variant("", settings -> settings
                .model(Motherlode.id(id, name -> "block/" + name))
            )
        );

    public static final AssetProcessor DEFAULT_DIRECTIONAL_BLOCK_STATE = (pack, id) ->
        pack.addBlockState(id, builder -> {
            int y = 0;
            for (String facing : facings) {
                int yy = y;
                builder.variant("facing=" + facing, settings -> settings
                    .model(Motherlode.id(id, name -> "block/" + name))
                    .rotationY(ys[yy] * 90)
                );
                y++;
            }
        });

    public static final AssetProcessor DEFAULT_BLOCK_MODEL = (pack, id) ->
        pack.addBlockModel(id, state -> state
            .parent(new Identifier("block/cube_all"))
            .texture("all", Motherlode.id(id, name -> "block/" + name))
        );

    public static final AssetProcessor DEFAULT_BLOCK = DEFAULT_BLOCK_STATE.andThen(DEFAULT_BLOCK_MODEL).andThen(BLOCK_ITEM);

    public static final Function<Identifier, AssetProcessor> PLANT_FUNCTION = modelId -> (pack, id) ->
        pack.addBlockModel(modelId, state -> state
            .parent(new Identifier("block/tinted_cross"))
            .texture("cross", Motherlode.id(id, name -> "block/" + name))
        );

    public static final AssetProcessor PLANT = (pack, id) ->
        pack.addBlockModel(id, state -> state
            .parent(new Identifier("block/tinted_cross"))
            .texture("cross", Motherlode.id(id, name -> "block/" + name))
        );

    public static final AssetProcessor THICK_CROSS = (pack, id) ->
        pack.addBlockModel(id, state -> state
            .parent(new Identifier(Motherlode.MODID, "block/thick_cross"))
            .texture("cross", Motherlode.id(id, name -> "block/" + name))
        );

    public static final AssetProcessor PILLAR = (pack, id) -> {
        for (String variant : new String[] { "", "_horizontal" }) {
            pack.addBlockModel(Motherlode.id(id, name -> name + variant), model -> model
                .parent(new Identifier("block/cube_column" + variant))
                .texture("end", Motherlode.id(id, name -> "block/" + name + "_top"))
                .texture("side", Motherlode.id(id, name -> "block/" + name + "_side"))
            );
        }
        pack.addBlockState(Motherlode.id(id, name -> name), builder -> builder
            .variant("axis=x", settings -> settings.model(Motherlode.id(id, name -> "block/" + name + "_horizontal")).rotationX(90).rotationY(90))
            .variant("axis=y", settings -> settings.model(Motherlode.id(id, name -> "block/" + name)))
            .variant("axis=z", settings -> settings.model(Motherlode.id(id, name -> "block/" + name + "_horizontal")).rotationX(90))
        );
    };
    public static final AssetProcessor STAIR = (pack, id) -> {
        String texId = id.getPath().replace("_stairs", "");
        for (int i = 0; i < 3; i++) {
            int ii = i;
            pack.addBlockModel(Motherlode.id(id, name -> name + modelStrings[ii]), model -> model
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
                        settings.model(Motherlode.id(id, name -> "block/" + name + modelStrings[models[jj]]))
                            .rotationX(xs[jj] * 90)
                            .rotationY(ys[ii] * 90)
                            .uvlock(xs[jj] != 0 || ys[ii] != 0));
                    i++;
                    j++;
                }
            }
        }
    }

    public static final AssetProcessor SLAB = (pack, id) -> {
        String texId = id.getPath().replace("_slab", "").replace("_pillar", "_pillar_side");
        for (String variant : new String[] { "_top", "" }) {
            pack.addBlockModel(Motherlode.id(id, name -> name + variant), model -> model
                .parent(new Identifier("block/slab" + variant))
                .texture("top", new Identifier(id.getNamespace(), "block/" + texId))
                .texture("bottom", new Identifier(id.getNamespace(), "block/" + texId))
                .texture("side", new Identifier(id.getNamespace(), "block/" + texId))
            );
        }
        pack.addBlockModel(Motherlode.id(id, name -> name + "_double"), model -> model
            .parent(new Identifier("block/cube_column"))
            .texture("end", new Identifier(id.getNamespace(), "block/" + texId))
            .texture("side", new Identifier(id.getNamespace(), "block/" + texId))
        );
        pack.addBlockState(id, builder -> builder
            .variant("type=top", settings -> settings.model(Motherlode.id(id, name -> "block/" + name + "_top")))
            .variant("type=bottom", settings -> settings.model(Motherlode.id(id, name -> "block/" + name)))
            .variant("type=double", settings -> settings.model(Motherlode.id(id, name -> "block/" + name + "_double")))
        );
    };

    public static BiFunction<Identifier, Identifier, AssetProcessor> CUBE_COLUMN = (end, side) -> (pack, id) ->
        pack.addBlockModel(id, model -> model
            .parent(new Identifier("minecraft", "block/cube_column"))
            .texture("end", end)
            .texture("side", side)
        );

    public static BiFunction<Identifier, Identifier, AssetProcessor> HORIZONTAL_CUBE_COLUMN = (end, side) -> (pack, id) ->
        pack.addBlockModel(id, model -> model
            .parent(new Identifier("minecraft", "block/cube_column_horizontal"))
            .texture("end", end)
            .texture("side", side)
        );

    public static ModelBuilder.Override floatPredicate(ModelBuilder.Override override, String name, Number value) {
        override.with("predicate", JsonObject::new, predicate -> predicate.addProperty(name, value));
        return override;
    }
}
