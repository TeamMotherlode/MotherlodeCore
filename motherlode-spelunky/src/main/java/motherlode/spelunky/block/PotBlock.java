package motherlode.spelunky.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import java.util.Optional;
import java.util.Random;

public class PotBlock extends Block {

    public final VoxelShape shape;

    private static final Random rand = new Random();

    public static final Integer maxPattern = 18;
    public static final VoxelShape POT_SHAPE =  VoxelShapes.union(
      VoxelShapes.cuboid(3/16D, 12/16D, 3/16D, 13/16D, 14/16D, 13/16D),
      VoxelShapes.cuboid(4/16D, 10/16D, 4/16D, 12/16D, 12/16D, 12/16D),
      VoxelShapes.cuboid(3/16D, 0 /16D, 3/16D, 13/16D, 10/16D, 13/16D)
    );

    public static final IntProperty PATTERN = IntProperty.of("pattern", 0, maxPattern);
    public static final EnumProperty<PotColor> COLOR = EnumProperty.of("color", PotColor.class);

    public PotBlock(Block.Settings settings) {
        super(settings);

        this.shape = POT_SHAPE;
        this.setDefaultState(this.stateManager.getDefaultState().with(PATTERN, 0).with(COLOR, PotColor.WHITE));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return shape;
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        int randomPattern = rand.nextInt(maxPattern + 1);
        PotColor randomColor = PotColor.getRandom(rand);

        return Optional.ofNullable(super.getPlacementState(ctx)).map(state -> state.with(PATTERN, randomPattern).with(COLOR, randomColor)).orElse(null);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PATTERN).add(COLOR);
    }
}