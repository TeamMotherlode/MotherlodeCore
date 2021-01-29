package motherlode.buildingblocks.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.StonecutterBlock;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import motherlode.buildingblocks.screen.StonecutterScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StonecutterBlock.class)
public class StonecutterBlockMixin {

    @Inject(
        method = "createScreenHandlerFactory",
        at = @At(
            value = "HEAD"
        ),
        cancellable = true
    )
    private void replaceScreenHandler(BlockState state, World world, BlockPos pos, CallbackInfoReturnable<NamedScreenHandlerFactory> cir) {
        cir.setReturnValue(new SimpleNamedScreenHandlerFactory((i, playerInventory, playerEntity) -> new StonecutterScreenHandler(i, ScreenHandlerContext.create(world, pos), playerInventory), new TranslatableText("container.stonecutter")));
    }
}
