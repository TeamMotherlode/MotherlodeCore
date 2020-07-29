package motherlode.core.mixins;

import motherlode.core.registry.MotherlodePotions;
import motherlode.core.registry.MotherlodePotions.PotionModelInfo;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.ModelPredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;


@Environment(EnvType.CLIENT)
@Mixin(ModelPredicateProviderRegistry.class)
public abstract class ModelPredicateProviderRegistryMixin {

    @Shadow
    private static void register(Item item, Identifier id, ModelPredicateProvider provider) {}

    @Shadow
    private static ModelPredicateProvider register(Identifier id, ModelPredicateProvider provider) {return null;}

    static {
        register(Items.POTION, new Identifier("potion_type"), (itemStack, _world, _entity) -> {
                PotionModelInfo potion = MotherlodePotions.potionModelInfos.get( PotionUtil.getPotion(itemStack) );
                return potion == null ? 1 : potion.predicateValue;
        });

        register(new Identifier("stack_count"), ( itemStack,  _world,  _entity) -> itemStack.getCount() / 100F);
    }

}
