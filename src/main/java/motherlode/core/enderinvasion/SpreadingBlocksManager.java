package motherlode.core.enderinvasion;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class SpreadingBlocksManager {

    public static final SpreadingBlocksManager SPREAD = new SpreadingBlocksManager();
    public static final SpreadingBlocksManager PURIFICATION = new SpreadingBlocksManager();

    private final HashMap<Identifier, SpreadRecipe> recipes;

    public SpreadingBlocksManager() {

        this.recipes = new HashMap<>();
    }

    public void addRecipe(SpreadRecipe recipe) {

        recipes.put(recipe.getIdentifier(), recipe);
    }
    public void addRecipes(SpreadingBlocksManager recipes) {

        recipes.recipes.forEach((id, recipe) -> this.addRecipe(recipe));
    }
    public SpreadRecipe getRecipe(Identifier identifier) {

        return recipes.get(identifier);
    }
    public SpreadRecipe getRecipe(Block input) {

        for(Map.Entry<Identifier, SpreadRecipe> entry: recipes.entrySet()) {

            if(entry.getValue().isBlockValid(input)) return entry.getValue();
        }
        return null;
    }
    public static void addSimpleRecipe(SpreadingBlocksManager manager, Identifier identifier, Block input, Block result) {

        addRecipe(manager, identifier, block -> block.equals(input), state -> result.getDefaultState());
    }
    public static void addRecipe(SpreadingBlocksManager manager, Identifier identifier, Predicate<Block> isValidBlock, Function<BlockState, BlockState> convert) {

        manager.addRecipe(new SpreadRecipe(identifier, isValidBlock, convert));
    }
}
