package motherlode.copperdungeon.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DefaultTrapBlock extends Block {
    public static final BooleanProperty POWERED = Properties.POWERED;

    public DefaultTrapBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(POWERED, false));
    }

    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        boolean gettingPower = world.isReceivingRedstonePower(pos);
        boolean alreadyPowered = state.get(POWERED);
        if (gettingPower && !alreadyPowered) {
            if (canDeploy(state, world, pos)) {
                world.setBlockState(pos, state.with(POWERED, true), 4);
                world.addSyncedBlockEvent(pos, this, 0, 1);
            }
        } else if (!gettingPower && alreadyPowered) {
            world.setBlockState(pos, state.with(POWERED, false), 4);
            world.addSyncedBlockEvent(pos, this, 0, 0);
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        boolean gettingPower = world.isReceivingRedstonePower(pos);
        if (gettingPower) {
            if (canDeploy(state, world, pos)) {
                world.setBlockState(pos, state.with(POWERED, true), 4);
                world.addSyncedBlockEvent(pos, this, 0, 1);
            }
        }
    }

    public boolean onSyncedBlockEvent(BlockState state, World world, BlockPos pos, int type, int data) {
        world.setBlockState(pos, state.with(POWERED, data == 1));
        return true;
    }

    public boolean canDeploy(BlockState state, World world, BlockPos pos) {
        return true; // Default behavior to allow deployment for all traps
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState()
            .with(POWERED, false);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }
}
