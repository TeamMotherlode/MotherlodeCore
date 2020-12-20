package motherlode.potions;

import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.util.Identifier;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import motherlode.base.api.assets.CommonAssets;
import motherlode.potions.MotherlodePotions.PotionModelInfo;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;

public class MotherlodePotionsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FabricModelPredicateProviderRegistry.register(Items.POTION, new Identifier("potion_type"), (itemStack, world, entity, i) -> {
            PotionModelInfo potion = MotherlodePotions.potionModelInfos.get(PotionUtil.getPotion(itemStack));
            return potion == null ? 1 : potion.predicateValue;
        });
    }

    public static void generateAssets(ArtificeResourcePack.ClientResourcePackBuilder pack) {
        for (PotionModelInfo info : MotherlodePotions.potionModelInfos.values()) {
            if (!info.useDefaultModel)
                pack.addItemModel(MotherlodeModule.id(info.model), model -> model
                    .parent(new Identifier("item/generated"))
                    .texture("layer0", MotherlodeModule.id("item/" + info.model)));
        }

        pack.addItemModel(MotherlodeModule.id("default"), model -> model
            .parent(new Identifier("item/generated"))
            .texture("layer0", new Identifier("item/potion_overlay"))
            .texture("layer1", new Identifier("item/potion")));

        pack.addItemModel(new Identifier("potion"), model -> {
            model.parent(new Identifier("item/generated"));
            model.texture("layer0", new Identifier("item/potion"));
            model.texture("layer1", new Identifier("item/potion_overlay"));

            for (PotionModelInfo info : MotherlodePotions.getPotionModelInfos()) {
                if (info.model == null || info.useDefaultModel)
                    model.override(override -> CommonAssets.floatPredicate(override, "potion_type", info.predicateValue).model(MotherlodeModule.id("item/default")));
                else
                    model.override(override -> CommonAssets.floatPredicate(override, "potion_type", info.predicateValue).model(MotherlodeModule.id("item/" + info.model)));
            }
        });
    }
}
