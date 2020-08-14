package motherlode.core.potions;

import com.swordglowsblue.artifice.api.Artifice;
import motherlode.base.Motherlode;
import motherlode.uncategorized.registry.MotherlodeAssets;
import motherlode.core.registry.MotherlodePotions;
import net.minecraft.util.Identifier;

import static motherlode.core.registry.MotherlodePotions.PotionModelInfo;

public class MotherlodePotionsAssets {

    public static void register() {

        Artifice.registerAssets(Motherlode.id("potions_pack"), pack -> {

            for (PotionModelInfo info : MotherlodePotions.potionModelInfos.values()) {
                if (!info.useDefaultModel)
                    pack.addItemModel(Motherlode.id("potions/" + info.model), (model) -> model
                            .parent(new Identifier("item/generated"))
                            .texture("layer0", Motherlode.id("item/potions/" + info.model)));
            }

            pack.addItemModel(Motherlode.id("potions/default"), (model) -> model
                    .parent(new Identifier("item/generated"))
                    .texture("layer0", new Identifier("item/potion_overlay"))
                    .texture("layer1", new Identifier("item/potion")));

            pack.addItemModel(new Identifier("potion"), (model) -> {
                model.parent(new Identifier("item/generated"));
                model.texture("layer0", new Identifier("item/potion"));
                model.texture("layer1", new Identifier("item/potion_overlay"));

                for (PotionModelInfo info : MotherlodePotions.getPotionModelInfos()) {
                    if (info.model == null || info.useDefaultModel)
                        model.override( override -> MotherlodeAssets.floatPredicate(override, "potion_type", info.predicateValue).model(Motherlode.id("item/potions/default")) );
                    else
                        model.override( override -> MotherlodeAssets.floatPredicate(override, "potion_type", info.predicateValue).model(Motherlode.id("item/potions/" + info.model)) );

                }
            });
        });
    }
}
