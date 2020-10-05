package motherlode.potions;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import motherlode.base.Motherlode;
import motherlode.base.api.AssetProcessor;
import motherlode.base.api.MotherlodeAssets;
import motherlode.potions.MotherlodePotions.PotionModelInfo;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.util.Identifier;

public class MotherlodePotionsClient implements ClientModInitializer, AssetProcessor {

    @Override
    public void onInitializeClient() {

        MotherlodeAssets.addAssets(null, this);

        FabricModelPredicateProviderRegistry.register(Items.POTION, new Identifier("potion_type"), (itemStack, _world, _entity) -> {
            PotionModelInfo potion = MotherlodePotions.potionModelInfos.get( PotionUtil.getPotion(itemStack) );
            return potion == null ? 1 : potion.predicateValue;
        });
    }
    @Override
    public void accept(ArtificeResourcePack.ClientResourcePackBuilder pack, Identifier id) {

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
                    model.override(override -> motherlode.uncategorized.registry.MotherlodeAssets.floatPredicate(override, "potion_type", info.predicateValue).model(Motherlode.id(MotherlodePotionsMod.MODID, "item/default")));
                else
                    model.override(override -> motherlode.uncategorized.registry.MotherlodeAssets.floatPredicate(override, "potion_type", info.predicateValue).model(Motherlode.id(MotherlodePotionsMod.MODID, "item/" + info.model)));

            }
        });
    }
}