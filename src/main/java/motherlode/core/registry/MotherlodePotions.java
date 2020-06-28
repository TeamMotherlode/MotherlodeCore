package motherlode.core.registry;

import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MotherlodePotions {

    public static Map<Potion,PotionModelInfo> potionPredicateValues = new HashMap<>();

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

        addPotion(Potions.HEALING, "healing", 0.200F);
        addPotion(Potions.STRONG_HEALING, "healing", 0.201F);

    }

    public static void addPotion(Potion potion, String model, float predicateValue) {
        potionPredicateValues.put(potion, new PotionModelInfo(model, predicateValue));
    }

    public static List<PotionModelInfo> getPotionModelInfos() {
        return potionPredicateValues.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
    
    public static class PotionModelInfo implements Comparable<PotionModelInfo>{
        public final String model;
        public final float predicateValue;
        
        public PotionModelInfo(String model, float predicateValue) {
            this.model = model;
            this.predicateValue = predicateValue;
        }

        @Override
        public int compareTo(PotionModelInfo o) {
            return Float.compare(this.predicateValue, o.predicateValue);
        }
    }


}
