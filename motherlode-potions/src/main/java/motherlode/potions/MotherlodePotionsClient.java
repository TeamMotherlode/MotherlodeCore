package motherlode.potions;

import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.util.Identifier;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import motherlode.base.CommonAssets;
import motherlode.base.Motherlode;
import motherlode.base.api.AssetProcessor;
import motherlode.potions.MotherlodePotions.PotionModelInfo;

public class MotherlodePotionsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FabricModelPredicateProviderRegistry.register(Items.POTION, new Identifier("potion_type"), (itemStack, _world, _entity) -> {
            PotionModelInfo potion = MotherlodePotions.potionModelInfos.get(PotionUtil.getPotion(itemStack));
            return potion == null ? 1 : potion.predicateValue;
        });
    }

    public static AssetProcessor getAssetProcessor() {
        return (pack, id) -> {
            for (PotionModelInfo info : MotherlodePotions.potionModelInfos.values()) {
                if (!info.useDefaultModel)
                    pack.addItemModel(Motherlode.id(MotherlodeModule.MODID, info.model), model -> model
                        .parent(new Identifier("item/generated"))
                        .texture("layer0", Motherlode.id(MotherlodeModule.MODID, "item/" + info.model)));
            }

            pack.addItemModel(Motherlode.id(MotherlodeModule.MODID, "default"), model -> model
                .parent(new Identifier("item/generated"))
                .texture("layer0", new Identifier("item/potion_overlay"))
                .texture("layer1", new Identifier("item/potion")));

            pack.addItemModel(new Identifier("potion"), model -> {
                model.parent(new Identifier("item/generated"));
                model.texture("layer0", new Identifier("item/potion"));
                model.texture("layer1", new Identifier("item/potion_overlay"));

                for (PotionModelInfo info : MotherlodePotions.getPotionModelInfos()) {
                    if (info.model == null || info.useDefaultModel)
                        model.override(override -> CommonAssets.floatPredicate(override, "potion_type", info.predicateValue).model(Motherlode.id(MotherlodeModule.MODID, "item/default")));
                    else
                        model.override(override -> CommonAssets.floatPredicate(override, "potion_type", info.predicateValue).model(Motherlode.id(MotherlodeModule.MODID, "item/" + info.model)));
                }
            });
        };
    }
}
