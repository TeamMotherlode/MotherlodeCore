package motherlode.base.api.fluid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidDrainable;
import net.minecraft.block.FluidFillable;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public interface FluidFlowable extends FluidDrainable, FluidFillable {
    Identifier EMPTY = new Identifier("empty");

    String DOT_REPLACEMENT = "__dot__";
    String DASH_REPLACEMENT = "__dash__"; // it works
    String COLON_REPLACEMENT = "__colon__";

    Property<Identifier> FLUID = new Property<Identifier>("fluid", Identifier.class) {
        private final List<Identifier> VALUES = new ArrayList<>();

        @Override
        public Collection<Identifier> getValues() {
            if (VALUES.isEmpty()) {
                for (Fluid f : Registry.FLUID) {
                    VALUES.add(Registry.FLUID.getId(f));
                }
                VALUES.add(EMPTY);
            }
            return VALUES;
        }

        @Override
        public Optional<Identifier> parse(String name) {
            return Optional.of(new Identifier(name.replace(DOT_REPLACEMENT, ".")
                .replace(DASH_REPLACEMENT, "-").replace(COLON_REPLACEMENT, ":")));
        }

        @Override
        public String name(Identifier value) {
            return value.toString().replace(".", DOT_REPLACEMENT)
                .replace("-", DASH_REPLACEMENT).replace(":", COLON_REPLACEMENT);
        }
    };

    @Override
    default boolean canFillWithFluid(BlockView view, BlockPos pos, BlockState state, Fluid fluid) {
        return state.get(FLUID).equals(EMPTY);
    }

    @Override
    default boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        if (state.get(FLUID).equals(EMPTY)) {
            if (!world.isClient()) {
                world.setBlockState(pos, state.with(FLUID, Registry.FLUID.getId(fluidState.getFluid()))
                    .with(FlowableFluid.LEVEL, Math.max(fluidState.getLevel(), 1)), 3);
                world.getFluidTickScheduler().schedule(pos, fluidState.getFluid(), fluidState.getFluid().getTickRate(world));
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    default ItemStack tryDrainFluid(WorldAccess world, BlockPos pos, BlockState state) {
        if (!state.get(FLUID).equals(EMPTY)) {
            world.setBlockState(pos, state.with(FLUID, EMPTY), 3);
            if (Registry.FLUID.get(state.get(FLUID)).getDefaultState().isStill()) {
                return new ItemStack(Registry.FLUID.get(state.get(FLUID)).getBucketItem());
            }
        }
        return ItemStack.EMPTY;
    }
}
