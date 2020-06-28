package motherlode.core.potions;

import motherlode.core.registry.MotherlodePotions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

public class ThornsEffect extends StatusEffect {
    public ThornsEffect(int color) {
        super(StatusEffectType.BENEFICIAL, color);
    }

    public static void apply(LivingEntity attacker, Entity target) {
        int level = attacker.getStatusEffect(MotherlodePotions.THORNS_EFFECT).getAmplifier() + 1;
        int rand = (1 + attacker.getRandom().nextInt(3));
        attacker.damage(DamageSource.thorns(target), rand * level);
    }
}
