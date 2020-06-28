package motherlode.core.mixins.potions;

import motherlode.core.registry.MotherlodePotions;
import motherlode.core.registry.MotherlodePotions.PotionModelInfo;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.ModelPredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;


@Environment(EnvType.CLIENT)
@Mixin(ModelPredicateProviderRegistry.class)
public class ModelPredicateProviderRegistryMixin {

    @Shadow
    private static void register(Item item, Identifier id, ModelPredicateProvider provider) {}

    static {
        register(Items.POTION, new Identifier("potion_type"), (itemStack, _world, _entity) -> {
                PotionModelInfo potion = MotherlodePotions.potionPredicateValues.get( PotionUtil.getPotion(itemStack) );
                return potion == null ? 1 : potion.predicateValue;
        });
    }

}
