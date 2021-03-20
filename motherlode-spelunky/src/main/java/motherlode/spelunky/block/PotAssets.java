package motherlode.spelunky.block;

import motherlode.base.api.assets.AssetProcessor;
import motherlode.base.api.assets.CommonAssets;
import motherlode.spelunky.MotherlodeModule;

public class PotAssets {
    public static final AssetProcessor POT_BLOCK_STATE = (pack, id) ->
        pack.addBlockState(MotherlodeModule.id("pot"), state -> {
            for (int i = 0; i <= PotBlock.maxPattern; i++) {
                int ii = i;
                pack.addBlockModel(MotherlodeModule.id("pot_with_overlay_" + i), model -> model
                    .parent(MotherlodeModule.id("block/pot"))
                    .texture("overlay", MotherlodeModule.id("block/pots/pot_overlay_" + ii))
                );
                state.variant("pattern=" + i, settings -> settings.model(MotherlodeModule.id("block/pot_with_overlay_" + ii)));
            }
        });

    public static final AssetProcessor POT_TEMPLATE = (pack, id) ->
        pack.addItemModel(MotherlodeModule.id("pot_template"), model -> model
            .parent(MotherlodeModule.id("block/pot"))
            .texture("overlay", MotherlodeModule.id("block/pots/pot_overlay_1"))

            .display("thirdperson_righthand", settings -> settings.scale(0.625F, 0.625F, 0.625F).rotation(66F, 135F, 0F).translation(0, 4, 4))
            .display("thirdperson_lefthand", settings -> settings.scale(0.625F, 0.625F, 0.625F).rotation(66F, 135F, 0F).translation(0, 4, 4))
            .display("firstperson_righthand", settings -> settings.scale(0.625F, 0.625F, 0.625F).rotation(0F, 135F, 0F).translation(2, 2, -2))
            .display("firstperson_lefthand", settings -> settings.scale(0.625F, 0.625F, 0.625F).rotation(0F, 135F, 0F).translation(0, 5, 10))
        );

    public static final AssetProcessor POT_ITEM_MODEL = (pack, id) ->
        pack.addItemModel(MotherlodeModule.id("pot"), model -> {
            for (int i = 0; i <= PotBlock.maxPattern; i++) {
                float pattern = i / 100F;
                int ii = i;
                model.override(override -> CommonAssets.floatPredicate(override, "pot_pattern", pattern).model(MotherlodeModule.id("item/pot_" + ii)));

                pack.addItemModel(MotherlodeModule.id("pot_" + ii), model2 -> model2
                    .parent(MotherlodeModule.id("item/pot_template"))
                    .texture("overlay", MotherlodeModule.id("block/pots/pot_overlay_" + ii))
                );
            }
        });
}
