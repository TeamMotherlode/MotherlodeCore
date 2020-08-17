package motherlode.potions;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import motherlode.base.Motherlode;
import motherlode.base.api.MotherlodeAssetsEntryPoint;
import motherlode.registry.MotherlodePotions;
import motherlode.uncategorized.registry.MotherlodeAssets;
import net.minecraft.util.Identifier;

import static motherlode.registry.MotherlodePotions.PotionModelInfo;

public class MotherlodePotionsAssets implements MotherlodeAssetsEntryPoint {

    public void registerAssets(ArtificeResourcePack.ClientResourcePackBuilder pack) {

        for (PotionModelInfo info : MotherlodePotions.potionModelInfos.values()) {
            if (!info.useDefaultModel)
                pack.addItemModel(Motherlode.id(MotherlodePotionsMod.MODID, "potions/" + info.model), (model) -> model
                        .parent(new Identifier("item/generated"))
                        .texture("layer0", Motherlode.id(MotherlodePotionsMod.MODID, "item/" + info.model)));
        }

        pack.addItemModel(Motherlode.id(MotherlodePotionsMod.MODID, "default"), (model) -> model
                .parent(new Identifier("item/generated"))
                .texture("layer0", new Identifier("item/potion_overlay"))
                .texture("layer1", new Identifier("item/potion")));

        pack.addItemModel(new Identifier("potion"), (model) -> {
            model.parent(new Identifier("item/generated"));
            model.texture("layer0", new Identifier("item/potion"));
            model.texture("layer1", new Identifier("item/potion_overlay"));

            for (PotionModelInfo info : MotherlodePotions.getPotionModelInfos()) {
                if (info.model == null || info.useDefaultModel)
                    model.override(override -> MotherlodeAssets.floatPredicate(override, "potion_type", info.predicateValue).model(Motherlode.id(MotherlodePotionsMod.MODID, "item/default")));
                else
                    model.override(override -> MotherlodeAssets.floatPredicate(override, "potion_type", info.predicateValue).model(Motherlode.id(MotherlodePotionsMod.MODID, "item/" + info.model)));

            }
        });
    }
}
