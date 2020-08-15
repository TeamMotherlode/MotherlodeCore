package motherlode.core.potions;

import motherlode.core.registry.MotherlodePotions;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.util.Identifier;

public class MotherlodePotionsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        FabricModelPredicateProviderRegistry.register(Items.POTION, new Identifier("potion_type"), (itemStack, _world, _entity) -> {
            MotherlodePotions.PotionModelInfo potion = MotherlodePotions.potionModelInfos.get( PotionUtil.getPotion(itemStack) );
            return potion == null ? 1 : potion.predicateValue;
        });
    }
}
