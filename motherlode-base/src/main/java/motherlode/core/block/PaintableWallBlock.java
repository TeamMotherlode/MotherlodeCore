package motherlode.core.block;

import com.google.common.collect.Maps;
import motherlode.core.block.stateproperty.BlockDyeColor;
import motherlode.core.registry.MotherlodeBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Pair;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.Map;

public class PaintableWallBlock extends DefaultBlock {

    public static final EnumProperty<BlockDyeColor> SIDE_A_COLOR;
    public static final EnumProperty<BlockDyeColor> SIDE_B_COLOR;
    public static final IntProperty VARIANT;
    private static final Map<Integer, Pair<Direction, Direction>> VARIANT_TO_DIRECTIONS = Maps.newHashMap();

    public PaintableWallBlock(Settings settings) {
        super(false, false, false, true, settings);
        this.setDefaultState(getDefaultState().with(SIDE_A_COLOR, BlockDyeColor.UNCOLORED).with(SIDE_B_COLOR, BlockDyeColor.UNCOLORED).with(VARIANT, 0));
        MotherlodeBlocks.usesPaintableModel.add(this);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(player.getStackInHand(hand).getItem() instanceof DyeItem) {
            BlockDyeColor color = BlockDyeColor.fromDyeColor(((DyeItem)player.getStackInHand(hand).getItem()).getColor());
            Pair<Direction, Direction> sides = VARIANT_TO_DIRECTIONS.get(world.getBlockState(pos).get(VARIANT));
            Direction dir = hit.getSide();
            if(sides.getLeft() == dir) {
                world.setBlockState(pos, world.getBlockState(pos).with(SIDE_A_COLOR, color));
                if(!player.isCreative()) player.getStackInHand(hand).decrement(1);
                return ActionResult.SUCCESS;
            } else if(sides.getRight() == dir) {
                world.setBlockState(pos, world.getBlockState(pos).with(SIDE_B_COLOR, color));
                if(!player.isCreative()) player.getStackInHand(hand).decrement(1);
                return ActionResult.SUCCESS;
            }
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SIDE_A_COLOR, SIDE_B_COLOR, VARIANT);
    }

    static {
        SIDE_A_COLOR = EnumProperty.of("side_a", BlockDyeColor.class);
        SIDE_B_COLOR = EnumProperty.of("side_b", BlockDyeColor.class);
        VARIANT = IntProperty.of("variant", 0, 5);

        //                                          SIDE A           SIDE B
        VARIANT_TO_DIRECTIONS.put(0, new Pair<>(Direction.NORTH, Direction.SOUTH));
        VARIANT_TO_DIRECTIONS.put(1, new Pair<>(Direction.EAST, Direction.WEST));
        VARIANT_TO_DIRECTIONS.put(2, new Pair<>(Direction.NORTH, Direction.WEST));
        VARIANT_TO_DIRECTIONS.put(3, new Pair<>(Direction.NORTH, Direction.EAST));
        VARIANT_TO_DIRECTIONS.put(4, new Pair<>(Direction.SOUTH, Direction.EAST));
        VARIANT_TO_DIRECTIONS.put(5, new Pair<>(Direction.SOUTH, Direction.WEST));
    }
}
