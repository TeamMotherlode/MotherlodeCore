package motherlode.core.block;

import motherlode.core.Motherlode;
import motherlode.core.registry.MotherlodeBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ShovelItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class DefaultShovelableBlock extends DefaultBlock {

    public static final BooleanProperty SHOVELED;
    public final boolean isRotatable;
    private final SoundEvent shovelSound;

    public DefaultShovelableBlock(boolean rotatable, SoundEvent shovelSound, Settings settings) {
        super(false, false, true, true, settings);
        this.setDefaultState(getDefaultState().with(SHOVELED, false));
        MotherlodeBlocks.shovelableBlocks.add(this);
        this.isRotatable = rotatable;
        this.shovelSound = shovelSound;
    }

    public DefaultShovelableBlock(boolean rotatable, Settings settings) {
        this(rotatable, SoundEvents.ITEM_SHOVEL_FLATTEN, settings);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        return state.with(SHOVELED, world.getBlockState(pos).get(SHOVELED) && world.getBlockState(pos.up()).isAir());
    }

    @Override
    public Block getBlockInstance() {
        return this;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(player.getStackInHand(hand).getItem() instanceof ShovelItem && hit.getSide() != Direction.DOWN && !world.getBlockState(pos).get(SHOVELED) && world.getBlockState(pos.up()).isAir()) {
            world.playSound(pos.getX()+0.5, pos.getY() +0.5, pos.getZ()+0.5, shovelSound, SoundCategory.BLOCKS, 1.0f, 1.0f, true);
            world.setBlockState(pos, state.with(SHOVELED, true));
            return ActionResult.SUCCESS;
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return createCuboidShape(0, 0, 0, 16, state.get(SHOVELED) ? 15 : 16, 16);
    }

    @Override
    public boolean hasSidedTransparency(BlockState state) {
        return state.get(SHOVELED);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SHOVELED);
    }

    static {
        SHOVELED = BooleanProperty.of("shoveled");
    }
}
