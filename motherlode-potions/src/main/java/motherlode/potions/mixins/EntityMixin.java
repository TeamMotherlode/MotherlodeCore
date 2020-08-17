package motherlode.potions.mixins;

import motherlode.potions.ThornsEffect;
import motherlode.registry.MotherlodePotions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {

    @Inject(method = "dealDamage", at = @At("HEAD"))
    private void dealDamage(LivingEntity attacker, Entity target, CallbackInfo _info) {
        if (attacker != null && target instanceof LivingEntity && ((LivingEntity)target).hasStatusEffect(MotherlodePotions.THORNS_EFFECT))
                ThornsEffect.apply(attacker,target);
    }

}
