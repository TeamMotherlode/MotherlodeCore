package motherlode.spelunky;

import motherlode.spelunky.fluid.SlimeFluid;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class MotherlodeSpelunkyFluids {


    public static final FlowableFluid STILL_SLIME = register("slime", new SlimeFluid.Still());
    public static final FlowableFluid FLOWING_SLIME = register("flowing_slime", new SlimeFluid.Flowing());

    private static <T extends Fluid> T register(String name, T value) {
        return Registry.register(Registry.FLUID, name, value);
    }

    public static void init(){

    }

}


