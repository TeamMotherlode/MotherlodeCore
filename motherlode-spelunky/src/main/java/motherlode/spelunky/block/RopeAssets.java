package motherlode.spelunky.block;

import net.minecraft.util.Identifier;
import motherlode.base.api.assets.AssetProcessor;
import motherlode.base.api.assets.CommonAssets;
import motherlode.spelunky.MotherlodeModule;

public class RopeAssets {
    private static final int[] stackCounts = new int[] { 0, 8, 16, 24, 32, 40, 48, 56, 64 };

    public static final AssetProcessor ITEM_MODELS = (pack, id) -> {
        for (int stackCount : stackCounts) {
            pack.addItemModel(MotherlodeModule.id("rope" + stackCount), model -> model
                .parent(new Identifier("item/generated"))
                .texture("layer0", MotherlodeModule.id("item/rope/rope" + stackCount))
            );
        }
    };

    public static final AssetProcessor ITEM_MODEL = (pack, id) ->
        pack.addItemModel(MotherlodeModule.id("rope"), model -> {
            for (int stackCount : stackCounts)
                model.override(override -> CommonAssets.floatPredicate(override, "stack_count", stackCount / 100f)
                    .model(MotherlodeModule.id("item/rope" + stackCount)));
        });

    public static final AssetProcessor BLOCK_STATE = (pack, id) ->
        pack.addBlockState(MotherlodeModule.id("rope"), state -> {
            String[] directions = new String[] { "south", "west", "north", "east" };
            for (int i = 0; i < directions.length; i++) {
                int ii = i;
                state.variant("bottom=false,connected=up,facing=" + directions[i], variant -> variant.model(MotherlodeModule.id("block/rope_top")).rotationY(ii * 90));
                state.variant("bottom=true,connected=up,facing=" + directions[i], variant -> variant.model(MotherlodeModule.id("block/rope_top_bottom")).rotationY(ii * 90));
                state.variant("bottom=false,connected=side,facing=" + directions[i], variant -> variant.model(MotherlodeModule.id("block/rope_side")).rotationY(ii * 90));
                state.variant("bottom=true,connected=side,facing=" + directions[i], variant -> variant.model(MotherlodeModule.id("block/rope_side_bottom")).rotationY(ii * 90));
            }
            state.variant("bottom=false,connected=none", settings -> settings.model(MotherlodeModule.id("block/rope")));
            state.variant("bottom=true,connected=none", settings -> settings.model(MotherlodeModule.id("block/rope_bottom")));
        });
}
