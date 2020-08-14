package motherlode.core.registry;

import motherlode.uncategorized.Motherlode;
import motherlode.core.potions.ThornsEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

;

public class MotherlodePotions {

    public static Map<Potion,PotionModelInfo> potionModelInfos = new HashMap<>();

    public static final StatusEffect THORNS_EFFECT = register("thorns", new ThornsEffect(9848355));

    public static final Potion THORNS = register("thorns", new Potion(new StatusEffectInstance(THORNS_EFFECT, 3600)));
    public static final Potion LONG_THORNS = register("long_thorns", new Potion(new StatusEffectInstance(THORNS_EFFECT, 9600)));
    public static final Potion STRONG_THORNS = register("strong_thorns", new Potion(new StatusEffectInstance(THORNS_EFFECT, 1800, 1)));
    public static final Potion HEALING_III = register("healing_3", healing(2));
    public static final Potion HEALING_IV = register("healing_4", healing(3));

    private static Potion healing(int amplifier) {
        return new Potion("healing", new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1, amplifier));
    }

    private static StatusEffect register(String id, StatusEffect entry) {
        return Registry.register(Registry.STATUS_EFFECT, Motherlode.id(id), entry);
    }

    private static Potion register(String name, Potion potion) {
        return Registry.register(Registry.POTION, name, potion);
    }

    public static void init() {}

    static {
        addPotion(Potions.EMPTY, null, 0.000F);
        addPotion(Potions.WATER, null, 0.010F);
        addPotion(Potions.MUNDANE, null, 0.020F);
        addPotion(Potions.THICK, null, 0.030F);
        addPotion(Potions.AWKWARD, null, 0.040F);

        addPotion(Potions.NIGHT_VISION, "night_vision", 0.050F);
        addPotion(Potions.LONG_NIGHT_VISION, "night_vision", 0.051F);

        addPotion(Potions.INVISIBILITY, "invisibility", 0.060F);
        addPotion(Potions.LONG_INVISIBILITY, "invisibility", 0.061F);

        addPotion(Potions.LEAPING, "leaping", 0.070F);
        addPotion(Potions.LONG_LEAPING, "leaping", 0.071F);
        addPotion(Potions.STRONG_LEAPING, "leaping", 0.072F);

        addPotion(Potions.FIRE_RESISTANCE, "fire_resistance", 0.080F);
        addPotion(Potions.LONG_FIRE_RESISTANCE, "fire_resistance", 0.081F);

        addPotion(Potions.SWIFTNESS, "swiftness", 0.090F);
        addPotion(Potions.LONG_SWIFTNESS, "swiftness", 0.091F);
        addPotion(Potions.STRONG_SWIFTNESS, "swiftness", 0.092F);

        addPotion(Potions.SLOWNESS, "slowness", 0.100F);
        addPotion(Potions.LONG_SLOWNESS, "slowness", 0.101F);
        addPotion(Potions.STRONG_SLOWNESS, "slowness", 0.102F);

        addPotion(Potions.WATER_BREATHING, "water_breathing", 0.110F);
        addPotion(Potions.LONG_WATER_BREATHING, "water_breathing", 0.111F);

        addPotion(Potions.POISON, "poison", 0.120F);
        addPotion(Potions.LONG_POISON, "poison", 0.121F);
        addPotion(Potions.STRONG_POISON, "poison", 0.122F);

        addPotion(Potions.REGENERATION, "regeneration", 0.130F);
        addPotion(Potions.LONG_REGENERATION, "regeneration", 0.131F);
        addPotion(Potions.STRONG_REGENERATION, "regeneration", 0.132F);

        addPotion(Potions.STRENGTH, "strength", 0.140F);
        addPotion(Potions.LONG_STRENGTH, "strength", 0.141F);
        addPotion(Potions.STRONG_STRENGTH, "strength", 0.142F);

        addPotion(Potions.WEAKNESS, "weakness", 0.150F);
        addPotion(Potions.LONG_WEAKNESS, "weakness", 0.151F);

        addPotion(Potions.LUCK, "luck", 0.160F);

        addPotion(Potions.TURTLE_MASTER, "turtle_master", 0.170F);
        addPotion(Potions.LONG_TURTLE_MASTER, "turtle_master", 0.171F);
        addPotion(Potions.STRONG_TURTLE_MASTER, "turtle_master", 0.172F);

        addPotion(Potions.SLOW_FALLING, "slow_falling", 0.180F);
        addPotion(Potions.LONG_SLOW_FALLING, "slow_falling", 0.181F);

        addPotion(Potions.HARMING, "harming", 0.190F);
        addPotion(Potions.STRONG_HARMING, "harming", 0.191F);

        addPotion(Potions.HEALING, "healing_1", 0.200F);
        addPotion(Potions.STRONG_HEALING, "healing_2", 0.201F);
        addPotion(HEALING_III, "healing_3", 0.202F);
        addPotion(HEALING_IV, "healing_4", 0.203F);

        addPotion(THORNS, "thorns", 0.210F);
        addPotion(LONG_THORNS, "thorns", 0.211F);
        addPotion(STRONG_THORNS, "thorns", 0.212F);

    }

    public static void addPotion(Potion potion, String model, float predicateValue) {
        potionModelInfos.put(potion, new PotionModelInfo(model, predicateValue));
    }

    public static List<PotionModelInfo> getPotionModelInfos() {
        return potionModelInfos.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    public static boolean applyTint(ItemStack stack) {
        if (stack.getItem() != Items.POTION)
            return true;

        PotionModelInfo info = potionModelInfos.get(PotionUtil.getPotion(stack));
        return info == null || info.useDefaultModel;
    }
    
    public static class PotionModelInfo implements Comparable<PotionModelInfo>{
        public final String model;
        public final float predicateValue;
        public final boolean useDefaultModel;
        
        public PotionModelInfo(String model, float predicateValue) {
            this.model = model;
            this.predicateValue = predicateValue;
            this.useDefaultModel = getClass().getResourceAsStream("/assets/motherlode/textures/item/potions/" + model + ".png") == null;
        }

        @Override
        public int compareTo(PotionModelInfo o) {
            return Float.compare(this.predicateValue, o.predicateValue);
        }
    }


}
