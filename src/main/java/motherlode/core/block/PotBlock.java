package motherlode.core.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;

public class PotBlock extends DefaultBlock {
    public static Integer maxSize = 4;
    public static Integer minSize = 0;
    public static Integer maxPattern = 4;
    public static Integer minPattern = 0;
    private static IntProperty SIZE;
    private static IntProperty PATTERN;
    private Random rand = new Random();

    public PotBlock(Settings settings) {
        super(false, false, true, false, settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(SIZE, minSize).with(PATTERN, minPattern));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SIZE, PATTERN);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx).with(SIZE, rand.nextInt(maxSize + 1)).with(PATTERN, rand.nextInt(maxPattern + 1));
    }

    static {
        SIZE = IntProperty.of("size", minSize, maxSize);
        PATTERN = IntProperty.of("pattern", minPattern, maxPattern);
    }
}