package motherlode.core.potions.mixins;

import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PotionEntity.class)
public class PotionEntityMixin {

    @Redirect(method = "onCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/potion/PotionUtil;getColor(Lnet/minecraft/item/ItemStack;)I"))
    private int getColor(ItemStack stack) {
        int color = PotionUtil.getColor(stack);
        return color != -1 ? color : PotionUtil.getColor(PotionUtil.getPotionEffects(stack));
    }

}
