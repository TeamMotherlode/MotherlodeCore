package motherlode.core.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class PotBlock extends DefaultShapedBlock {
    public static Integer maxPattern = 1;
    public static Integer minPattern = 0;
    private static IntProperty PATTERN;
    private Random rand = new Random();
    public final static VoxelShape POT_SHAPE =  VoxelShapes.cuboid(3/16D, 0D, 3/16D, 13/16D, 14/16D, 13/16D);

    public PotBlock(Settings settings) {
        super(POT_SHAPE, false, false, true, false, settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(PATTERN, minPattern));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PATTERN);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx).with(PATTERN, rand.nextInt(maxPattern + 1));
    }

    static {
        PATTERN = IntProperty.of("pattern", minPattern, maxPattern);
    }
}