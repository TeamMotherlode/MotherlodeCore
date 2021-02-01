package motherlode.buildingblocks.recipe;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CuttingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import motherlode.buildingblocks.MotherlodeModule;
import motherlode.buildingblocks.MotherlodeBuildingBlocks;

public class SawmillingRecipe extends CuttingRecipe {

    public SawmillingRecipe(Identifier id, String group, Ingredient input, ItemStack output) {
        super(MotherlodeModule.SAWMILLING_RECIPE_TYPE, RecipeSerializer.STONECUTTING, id, group, input, output);
    }

    public boolean matches(Inventory inv, World world) {
        return this.input.test(inv.getStack(0));
    }

    @Environment(EnvType.CLIENT)
    public ItemStack getRecipeKindIcon() {
        return new ItemStack(MotherlodeBuildingBlocks.SAWMILL);
    }
}
