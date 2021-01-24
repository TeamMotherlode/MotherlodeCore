package motherlode.buildingblocks.mixin;

import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.client.recipebook.RecipeBookGroup;
import net.minecraft.recipe.Recipe;
import motherlode.buildingblocks.MotherlodeModule;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientRecipeBook.class)
public class ClientRecipeBookMixin {

    // This removes the "Unknown recipe category" warning
    @Inject(method = "getGroupForRecipe", at = @At(value = "HEAD"), cancellable = true)
    private static void addRecipeGroups(Recipe<?> recipe, CallbackInfoReturnable<RecipeBookGroup> cir) {
        if (recipe.getType() == MotherlodeModule.SAWMILLING_RECIPE_TYPE) {
            cir.setReturnValue(RecipeBookGroup.UNKNOWN);
        }
    }
}
