package motherlode.enderinvasion.recipe;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;

public class BlockRecipeManager {
    public static final BlockRecipeManager SPREAD = new BlockRecipeManager();
    public static final BlockRecipeManager PURIFICATION = new BlockRecipeManager();

    private final HashMap<Identifier, BlockRecipe> recipes;

    public BlockRecipeManager() {
        this.recipes = new HashMap<>();
    }

    public void addRecipe(BlockRecipe recipe) {
        recipes.put(recipe.getIdentifier(), recipe);
    }

    public void addRecipes(BlockRecipeManager recipes) {
        recipes.recipes.forEach((id, recipe) -> this.addRecipe(recipe));
    }

    public BlockRecipe getRecipe(Identifier identifier) {
        return recipes.get(identifier);
    }

    public BlockRecipe getRecipe(Block input) {
        for (Map.Entry<Identifier, BlockRecipe> entry : recipes.entrySet()) {
            if (entry.getValue().isBlockValid(input)) return entry.getValue();
        }
        return null;
    }

    public static void addSimpleRecipe(BlockRecipeManager manager, Identifier identifier, Block input, Block result) {
        addRecipe(manager, identifier, block -> block.equals(input), state -> result.getDefaultState());
    }

    public static void addRecipe(BlockRecipeManager manager, Identifier identifier, Predicate<Block> isValidBlock, Function<BlockState, BlockState> convert) {
        manager.addRecipe(new BlockRecipe(identifier, isValidBlock, convert));
    }
}
