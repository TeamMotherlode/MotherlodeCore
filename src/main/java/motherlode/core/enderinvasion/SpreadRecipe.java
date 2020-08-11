package motherlode.core.enderinvasion;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;

import java.util.function.Function;
import java.util.function.Predicate;

public class SpreadRecipe {

    private final Identifier identifier;
    private final Predicate<Block> isValidBlock;
    private final Function<BlockState, BlockState> convert;

    public SpreadRecipe(Identifier identifier, Predicate<Block> isValidBlock, Function<BlockState, BlockState> convert) {

        this.identifier = identifier;
        this.isValidBlock = isValidBlock;
        this.convert = convert;
    }

    public Identifier getIdentifier() {

        return this.identifier;
    }

    public boolean isBlockValid(Block block) {

        return isValidBlock.test(block);
    }

    public BlockState convert(BlockState state) {

        return convert.apply(state);
    }
    @Override
    public boolean equals(Object object) {

        if(this == object) return true;
        if(!(object instanceof SpreadRecipe)) return false;

        SpreadRecipe recipe = (SpreadRecipe) object;

        return identifier.equals(recipe.identifier);
    }
    @Override
    public int hashCode() {

        return identifier.hashCode();
    }
}
