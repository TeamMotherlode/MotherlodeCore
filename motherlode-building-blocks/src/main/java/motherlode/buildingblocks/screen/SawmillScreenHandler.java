package motherlode.buildingblocks.screen;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.recipe.CuttingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandlerContext;
import motherlode.buildingblocks.MotherlodeBuildingBlocks;
import motherlode.buildingblocks.MotherlodeModule;

public class SawmillScreenHandler extends CutterScreenHandler {

    public SawmillScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(syncId, playerInventory, MotherlodeModule.SAWMILL_SCREEN_HANDLER);
    }

    public SawmillScreenHandler(int syncId, ScreenHandlerContext context, PlayerInventory playerInventory) {
        super(syncId, context, playerInventory, MotherlodeModule.SAWMILL_SCREEN_HANDLER);
    }

    @Override
    public RecipeType<? extends CuttingRecipe> getRecipeType() {
        return MotherlodeModule.SAWMILLING_RECIPE_TYPE;
    }

    @Override
    public Block getBlock() {
        return MotherlodeBuildingBlocks.SAWMILL;
    }
}
