package motherlode.buildingblocks.screen;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.recipe.CuttingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandlerContext;
import motherlode.buildingblocks.MotherlodeModule;

public class StonecutterScreenHandler extends CutterScreenHandler {

    public StonecutterScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(syncId, playerInventory, MotherlodeModule.STONECUTTER_SCREEN_HANDLER);
    }

    public StonecutterScreenHandler(int syncId, ScreenHandlerContext context, PlayerInventory playerInventory) {
        super(syncId, context, playerInventory, MotherlodeModule.STONECUTTER_SCREEN_HANDLER);
    }

    @Override
    public RecipeType<? extends CuttingRecipe> getRecipeType() {
        return RecipeType.STONECUTTING;
    }

    @Override
    public Block getBlock() {
        return Blocks.STONECUTTER;
    }
}
