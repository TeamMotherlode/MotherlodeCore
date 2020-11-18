package motherlode.base.mixin;

import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.PillarBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Blocks.class)
public interface BlocksAccessor {

    @Invoker("createLogBlock")
    static PillarBlock callCreateLogBlock(MapColor topMapColor, MapColor sideMapColor) {
        throw new IllegalStateException();
    }

    @Invoker("createLeavesBlock")
    static LeavesBlock callCreateLeavesBlock() {
        throw new IllegalStateException();
    }
}
