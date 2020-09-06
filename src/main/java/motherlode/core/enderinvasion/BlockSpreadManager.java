package motherlode.core.enderinvasion;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class BlockSpreadManager {

    public static final BlockSpreadManager SPREAD = new BlockSpreadManager();
    public static final BlockSpreadManager PURIFICATION = new BlockSpreadManager();

    private final HashMap<Identifier, SpreadRecipe> recipes;

    public BlockSpreadManager() {

        this.recipes = new HashMap<>();
    }

    public void addRecipe(SpreadRecipe recipe) {

        recipes.put(recipe.getIdentifier(), recipe);
    }
    public void addRecipes(BlockSpreadManager recipes) {

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
    public static void addSimpleRecipe(BlockSpreadManager manager, Identifier identifier, Block input, Block result) {

        addRecipe(manager, identifier, block -> block.equals(input), state -> result.getDefaultState());
    }
    public static void addRecipe(BlockSpreadManager manager, Identifier identifier, Predicate<Block> isValidBlock, Function<BlockState, BlockState> convert) {

        manager.addRecipe(new SpreadRecipe(identifier, isValidBlock, convert));
    }
}
