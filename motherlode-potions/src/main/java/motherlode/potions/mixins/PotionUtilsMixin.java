package motherlode.potions.mixins;

import motherlode.registry.MotherlodePotions;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Collection;

@Mixin(PotionUtil.class)
public class PotionUtilsMixin {

    @Redirect(method = "getColor(Lnet/minecraft/item/ItemStack;)I",at = @At(value = "INVOKE",
              target = "Lnet/minecraft/potion/PotionUtil;getColor(Ljava/util/Collection;)I"))
    private static int getColor(Collection<StatusEffectInstance> _c, ItemStack stack) {
        return MotherlodePotions.applyTint(stack) ? PotionUtil.getColor(PotionUtil.getPotionEffects(stack)): -1;
    }

}
