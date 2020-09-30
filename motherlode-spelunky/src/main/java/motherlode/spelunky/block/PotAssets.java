package motherlode.spelunky.block;

import motherlode.base.CommonAssets;
import motherlode.base.Motherlode;
import motherlode.base.api.AssetProcessor;
import motherlode.spelunky.MotherlodeSpelunkyMod;

public class PotAssets {

    public static final AssetProcessor POT_BLOCK_STATE = (pack, id) ->
      pack.addBlockState(Motherlode.id(MotherlodeSpelunkyMod.MODID, "pot"), state -> {
        for (int i = 0; i <= PotBlock.maxPattern; i++) {
            int ii = i;
            pack.addBlockModel(Motherlode.id(MotherlodeSpelunkyMod.MODID, "pot_with_overlay_" + i), model -> model
              .parent(Motherlode.id("block/pot"))
              .texture("overlay", Motherlode.id("block/pots/pot_overlay_" + ii))
            );
            state.variant("pattern=" + i, settings -> settings.model(Motherlode.id("block/pot_with_overlay_" + ii)));
        }
    });

    public static final AssetProcessor POT_ITEM_MODEL = (pack, id) ->
        pack.addItemModel(Motherlode.id(MotherlodeSpelunkyMod.MODID, "pot"), model -> {
            for (int i = 0; i <= PotBlock.maxPattern; i++) {
                float pattern = i / 100F;
                int ii = i;
                model.override(override -> CommonAssets.floatPredicate(override, "pot_pattern", pattern).model(Motherlode.id(MotherlodeSpelunkyMod.MODID, "item/pot_" + ii)));

                pack.addItemModel(Motherlode.id(MotherlodeSpelunkyMod.MODID, "pot_" + ii), model2 -> model2
                  .parent(Motherlode.id(MotherlodeSpelunkyMod.MODID, "item/pot_template"))
                  .texture("overlay", Motherlode.id("block/pots/pot_overlay_" + ii))
                );
            }
        });
}
