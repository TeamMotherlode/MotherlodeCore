package motherlode.uncategorized.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class PotBlock extends DefaultShapedBlock {
    private static final Random rand = new Random();

    public static final Integer maxPattern = 18;
    public static final VoxelShape POT_SHAPE =  VoxelShapes.union(
            VoxelShapes.cuboid(3/16D, 12/16D, 3/16D, 13/16D, 14/16D, 13/16D),
            VoxelShapes.cuboid(4/16D, 10/16D, 4/16D, 12/16D, 12/16D, 12/16D),
            VoxelShapes.cuboid(3/16D, 0 /16D, 3/16D, 13/16D, 10/16D, 13/16D)
    );

    public static final IntProperty PATTERN = IntProperty.of("pattern", 0, maxPattern);
    public static final EnumProperty<PotColor> COLOR = EnumProperty.of("color", PotColor.class);


    public PotBlock(Settings settings) {
        super(POT_SHAPE, false, false, false, false, settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(PATTERN, 0).with(COLOR, PotColor.WHITE));
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        int randomPattern = rand.nextInt(maxPattern + 1);
        PotColor randomColor = PotColor.getRandom(rand);

        return super.getPlacementState(ctx).with(PATTERN, randomPattern).with(COLOR, randomColor);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PATTERN).add(COLOR);
    }

}