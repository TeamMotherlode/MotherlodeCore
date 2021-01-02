package motherlode.base.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import motherlode.base.api.fluid.FluidFlowable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FlowableFluid.class)
public abstract class FlowableFluidMixin {
    @Redirect(method = "onScheduledTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"))
    private boolean allowFlow(World world, BlockPos pos, BlockState state, int flags) {
        if (world.getBlockState(pos).getBlock() instanceof FluidFlowable) {
            ((FluidFlowable) world.getBlockState(pos).getBlock()).tryDrainFluid(world, pos, world.getBlockState(pos));
            if (!state.isAir()) {
                ((FluidFlowable) world.getBlockState(pos).getBlock()).tryFillWithFluid(world, pos, world.getBlockState(pos), state.getFluidState());
            }
            return true;
        }
        return world.setBlockState(pos, state, flags);
    }

    @Inject(method = "receivesFlow", at = @At("RETURN"), cancellable = true)
    private void allowFlow(Direction face, BlockView world, BlockPos pos, BlockState state, BlockPos fromPos, BlockState fromState, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue() && (state.getBlock() instanceof FluidFlowable || fromState.getBlock() instanceof FluidFlowable)) cir.setReturnValue(true);
    }
}
