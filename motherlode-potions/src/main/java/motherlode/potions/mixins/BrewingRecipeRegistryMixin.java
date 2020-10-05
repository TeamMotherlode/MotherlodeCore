package motherlode.potions.mixins;

import motherlode.potions.MotherlodePotions;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BrewingRecipeRegistry.class)
public abstract class BrewingRecipeRegistryMixin {

    @Shadow
    private static void registerPotionRecipe(Potion input, Item item, Potion output) {}

    @Inject(method = "registerDefaults", at = @At("TAIL"))
    private static void addRecipes(CallbackInfo info) {
        registerPotionRecipe(Potions.AWKWARD, Items.WITHER_ROSE, MotherlodePotions.THORNS);
        registerPotionRecipe(MotherlodePotions.THORNS, Items.REDSTONE, MotherlodePotions.LONG_THORNS);
        registerPotionRecipe(MotherlodePotions.THORNS, Items.GLOWSTONE_DUST, MotherlodePotions.STRONG_THORNS);

        registerPotionRecipe(Potions.STRONG_HEALING, Items.GLOWSTONE_DUST, MotherlodePotions.HEALING_III);
        registerPotionRecipe(MotherlodePotions.HEALING_III, Items.GLOWSTONE_DUST, MotherlodePotions.HEALING_IV);
    }

}
