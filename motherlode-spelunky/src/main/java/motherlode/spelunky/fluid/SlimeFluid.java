package motherlode.spelunky.fluid;

import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import motherlode.spelunky.MotherlodeSpelunkyBlocks;
import motherlode.spelunky.MotherlodeSpelunkyFluids;
import motherlode.spelunky.MotherlodeSpelunkyItems;

public abstract class SlimeFluid extends FlowableFluid {
    @Override
    public Fluid getFlowing() {
        return MotherlodeSpelunkyFluids.FLOWING_SLIME;
    }

    @Override
    public Fluid getStill() {
        return MotherlodeSpelunkyFluids.STILL_SLIME;
    }

    @Override
    protected boolean isInfinite() {
        return false;
    }

    @Override
    protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {
        world.breakBlock(pos, true);
        world.playSound(null, pos, SoundEvents.BLOCK_SLIME_BLOCK_BREAK, SoundCategory.BLOCKS, 1f, 0.5f);
    }

    @Override
    protected int getFlowSpeed(WorldView world) {
        return 30;
    }

    @Override
    protected int getLevelDecreasePerBlock(WorldView world) {
        return 3;
    }

    @Override
    public Item getBucketItem() {
        return MotherlodeSpelunkyItems.SLIME_BUCKET;
    }

    @Override
    protected boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
        return false;
    }

    @Override
    public int getTickRate(WorldView world) {
        return 4;
    }

    @Override
    protected float getBlastResistance() {
        return 0;
    }

    @Override
    protected BlockState toBlockState(FluidState state) {
        return MotherlodeSpelunkyBlocks.SLIME.getDefaultState().with(FluidBlock.LEVEL, method_15741(state));
    }

    public static class Still extends SlimeFluid {
        @Override
        public boolean isStill(FluidState state) {
            return true;
        }

        @Override
        public int getLevel(FluidState state) {
            return 8;
        }
    }

    public static class Flowing extends SlimeFluid {
        @Override
        public boolean isStill(FluidState state) {
            return false;
        }

        @Override
        public int getLevel(FluidState state) {
            return state.get(LEVEL);
        }

        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }
    }
}
