package motherlode.base.api.woodtype;

import java.util.function.Function;
import net.minecraft.util.Identifier;
import motherlode.base.Motherlode;
import motherlode.base.api.assets.AssetProcessor;
import motherlode.base.api.assets.CommonAssets;

public class WoodTypeAssets {
    private static final boolean[] BOOLEAN = new boolean[] { true, false };
    private static final String[] ORIENTATIONS = new String[] { "north", "east", "south", "west" };
    private static final String[] ORIENTATIONS2 = new String[] { "south", "west", "north", "east" };
    private static final String[] BUTTON_MODEL_TYPES = new String[] { "", "_pressed", "_inventory" };

    public static final AssetProcessor BUTTON_STATE = (pack, id) ->
        pack.addBlockState(Motherlode.id(id, name -> name + "_button"), state -> {
            for (boolean pressed : BOOLEAN) {
                for (int i = 0; i < ORIENTATIONS2.length; i++) {
                    int ii = i;
                    state.variant("face=ceiling,facing=" + ORIENTATIONS2[i] + ",powered=" + pressed, variant -> variant
                        .model(Motherlode.id(id, name -> "block/" + name + "_button" + (pressed ? "_pressed" : "")))
                        .rotationY(ii * 90)
                        .rotationX(180)
                    );
                }

                for (int i = 0; i < ORIENTATIONS.length; i++) {
                    int ii = i;
                    state.variant("face=floor,facing=" + ORIENTATIONS[i] + ",powered=" + pressed, variant -> variant
                        .model(Motherlode.id(id, name -> "block/" + name + "_button" + (pressed ? "_pressed" : "")))
                        .rotationY(ii * 90)
                    );
                }

                for (int i = 0; i < ORIENTATIONS.length; i++) {
                    int ii = i;
                    state.variant("face=wall,facing=" + ORIENTATIONS[i] + ",powered=" + pressed, variant -> variant
                        .model(Motherlode.id(id, name -> "block/" + name + "_button" + (pressed ? "_pressed" : "")))
                        .rotationX(90)
                        .rotationY(ii * 90)
                        .uvlock(true)
                    );
                }
            }
        });

    public static final Function<Identifier, AssetProcessor> BUTTON_MODELS = textureId -> (pack, id) -> {
        for (String modelType : BUTTON_MODEL_TYPES)
            pack.addBlockModel(Motherlode.id(id, name -> name + "_button" + modelType), model -> model
                .parent(new Identifier("minecraft", "block/button" + modelType))
                .texture("texture", textureId)
            );
    };

    public static final AssetProcessor BUTTON = (pack, id) -> BUTTON_STATE.andThen(BUTTON_MODELS.apply(Motherlode.id(id, name -> "block/" + name + "_planks")))
        .andThen((pack2, id2) -> CommonAssets.BLOCK_ITEM_FUNCTION.apply(Motherlode.id(id, name -> name + "_button"))
            .accept(pack2, Motherlode.id(id, name -> name + "_button_inventory"))).accept(pack, id);

    public static final AssetProcessor FENCE_GATE_STATE = (pack, id) ->
        pack.addBlockState(Motherlode.id(id, name -> name + "_fence_gate"), state -> {
            for (int i = 0; i < ORIENTATIONS2.length; i++) {
                int ii = i;

                for (boolean inWall : BOOLEAN)
                    for (boolean open : BOOLEAN) {
                        state.variant("facing=" + ORIENTATIONS2[i] + ",in_wall=" + inWall + ",open=" + open, variant -> variant
                            .model(Motherlode.id(id, name -> "block/" + name + "_fence_gate" + (inWall ? "_wall" : "") + (open ? "_open" : "")))
                            .rotationY(ii * 90)
                            .uvlock(true)
                        );
                    }
            }
        });

    private static final String[] FENCE_GATE_MODEL_TYPES = new String[] { "", "_wall", "_open", "_wall_open" };

    public static final Function<Identifier, AssetProcessor> FENCE_GATE_MODELS = textureId -> (pack, id) -> {
        for (String modelType : FENCE_GATE_MODEL_TYPES)
            pack.addBlockModel(Motherlode.id(id, name -> name + "_fence_gate" + modelType), model -> model
                .parent(new Identifier("minecraft", "block/template_fence_gate" + modelType))
                .texture("texture", textureId)
            );
    };

    public static final AssetProcessor FENCE_GATE = (pack, id) -> FENCE_GATE_STATE.andThen(FENCE_GATE_MODELS.apply(Motherlode.id(id, name -> "block/" + name + "_planks")))
        .andThen((pack2, id2) -> CommonAssets.BLOCK_ITEM.accept(pack2, Motherlode.id(id2, name -> name + "_fence_gate"))).accept(pack, id);

    public static final AssetProcessor PRESSURE_PLATE_STATE = (pack, id) ->
        pack.addBlockState(Motherlode.id(id, name -> name + "_pressure_plate"), state -> {
            for (boolean pressed : BOOLEAN)
                state.variant("powered=" + pressed, variant -> variant
                    .model(Motherlode.id(id, name -> "block/" + name + "_pressure_plate" + (pressed ? "_down" : "")))
                );
        });

    public static final Function<Identifier, AssetProcessor> PRESSURE_PLATE_MODELS = textureId -> (pack, id) -> {
        for (boolean pressed : BOOLEAN)
            pack.addBlockModel(Motherlode.id(id, name -> name + "_pressure_plate" + (pressed ? "_down" : "")), model -> model
                .parent(new Identifier("minecraft", "block/pressure_plate_" + (pressed ? "down" : "up")))
                .texture("texture", textureId)
            );
    };

    public static final AssetProcessor PRESSURE_PLATE = (pack, id) -> PRESSURE_PLATE_STATE.andThen(PRESSURE_PLATE_MODELS.apply(Motherlode.id(id, name -> "block/" + name + "_planks")))
        .andThen((pack2, id2) -> CommonAssets.BLOCK_ITEM.accept(pack2, Motherlode.id(id2, name -> name + "_pressure_plate"))).accept(pack, id);
}
