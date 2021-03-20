package motherlode.buildingblocks.recipe;

import net.minecraft.recipe.RecipeType;
import motherlode.base.api.varianttype.MotherlodeVariantType;
import motherlode.base.api.woodtype.WoodType;
import motherlode.buildingblocks.MotherlodeModule;

public class SawmillingRecipeType implements RecipeType<SawmillingRecipe> {
    public static final WoodSawmillRecipesExtension OAK_RECIPES = MotherlodeVariantType.extend(WoodType.OAK, "minecraft", new WoodSawmillRecipesExtension());
    public static final WoodSawmillRecipesExtension DARK_OAK_RECIPES = MotherlodeVariantType.extend(WoodType.DARK_OAK, "minecraft", new WoodSawmillRecipesExtension());
    public static final WoodSawmillRecipesExtension BIRCH_RECIPES = MotherlodeVariantType.extend(WoodType.BIRCH, "minecraft", new WoodSawmillRecipesExtension());
    public static final WoodSawmillRecipesExtension SPRUCE_RECIPES = MotherlodeVariantType.extend(WoodType.SPRUCE, "minecraft", new WoodSawmillRecipesExtension());
    public static final WoodSawmillRecipesExtension JUNGLE_RECIPES = MotherlodeVariantType.extend(WoodType.JUNGLE, "minecraft", new WoodSawmillRecipesExtension());
    public static final WoodSawmillRecipesExtension ACACIA_RECIPES = MotherlodeVariantType.extend(WoodType.ACACIA, "minecraft", new WoodSawmillRecipesExtension());

    @Override
    public String toString() {
        return MotherlodeModule.id("sawmilling").toString();
    }

    public static void init() {
    }
}
