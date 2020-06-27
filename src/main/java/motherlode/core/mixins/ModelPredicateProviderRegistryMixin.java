package motherlode.core.mixins;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.ModelPredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Environment(EnvType.CLIENT)
@Mixin(ModelPredicateProviderRegistry.class)
public abstract class ModelPredicateProviderRegistryMixin {

    @Shadow
    private static void register(Item item, Identifier id, ModelPredicateProvider provider) {}

    static {
        register(Items.POTION, new Identifier("potion_type"), (itemStack, clientWorld, livingEntity) -> {
            Potion type = PotionUtil.getPotion(itemStack);

            if (type == Potions.EMPTY) return (float)0.00;
            if (type == Potions.WATER) return (float)0.01;
            if (type == Potions.AWKWARD) return (float)0.02;
            if (type == Potions.THICK) return (float)0.03;
            if (type == Potions.MUNDANE) return (float)0.04;
            if (type == Potions.REGENERATION || type == Potions.LONG_REGENERATION || type == Potions.STRONG_REGENERATION) return (float)0.05;
            if (type == Potions.SWIFTNESS || type == Potions.LONG_SWIFTNESS || type == Potions.STRONG_SWIFTNESS) return (float)0.06;
            if (type == Potions.FIRE_RESISTANCE || type == Potions.LONG_FIRE_RESISTANCE) return (float)0.07;
            if (type == Potions.POISON || type == Potions.LONG_POISON || type == Potions.STRONG_POISON) return (float)0.08;
            if (type == Potions.HEALING || type == Potions.STRONG_HEALING) return (float)0.09;
            if (type == Potions.NIGHT_VISION || type == Potions.LONG_NIGHT_VISION) return (float)0.10;
            if (type == Potions.WEAKNESS || type == Potions.LONG_WEAKNESS) return (float)0.11;
            if (type == Potions.STRENGTH || type == Potions.LONG_STRENGTH || type == Potions.STRONG_STRENGTH) return (float)0.12;
            if (type == Potions.SLOWNESS || type == Potions.LONG_SLOWNESS || type == Potions.STRONG_SLOWNESS) return (float)0.13;
            if (type == Potions.LEAPING || type == Potions.LONG_LEAPING || type == Potions.STRONG_LEAPING) return (float)0.14;
            if (type == Potions.HARMING || type == Potions.STRONG_HARMING) return (float)0.15;
            if (type == Potions.WATER_BREATHING || type == Potions.LONG_WATER_BREATHING) return (float)0.16;
            if (type == Potions.INVISIBILITY || type == Potions.LONG_INVISIBILITY) return (float)0.17;
            if (type == Potions.LUCK) return (float)0.18;
            if (type == Potions.TURTLE_MASTER || type == Potions.LONG_TURTLE_MASTER || type == Potions.STRONG_TURTLE_MASTER) return (float)0.19;
            if (type == Potions.SLOW_FALLING || type == Potions.LONG_SLOW_FALLING) return (float)0.20;

            return 1;
        });
    }

}
