package motherlode.core.mixins;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

@Mixin(PotionUtil.class)
public class PotionUtilsMixin {

    @Inject(method = "getColor(Lnet/minecraft/potion/Potion;)I", at = @At("RETURN"), cancellable = true)
    private static void getColor(Potion _potion, CallbackInfoReturnable<Integer> callback) {
        callback.setReturnValue(-1);
    }

    @Inject(method = "getColor(Ljava/util/Collection;)I", at = @At("RETURN"), cancellable = true)
    private static void getColor(Collection<StatusEffectInstance> _effects, CallbackInfoReturnable<Integer> callback) {
        callback.setReturnValue(-1);
    }
}
