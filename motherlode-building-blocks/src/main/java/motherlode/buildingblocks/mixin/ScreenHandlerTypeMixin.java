package motherlode.buildingblocks.mixin;

import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ScreenHandlerType.class)
public abstract class ScreenHandlerTypeMixin {

    @Shadow
    private static <T extends ScreenHandler> ScreenHandlerType<T> register(String id, ScreenHandlerType.Factory<T> factory) {
        throw new IllegalStateException();
    }

    @Redirect(
        method = "<clinit>",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/screen/ScreenHandlerType;register(Ljava/lang/String;Lnet/minecraft/screen/ScreenHandlerType$Factory;)Lnet/minecraft/screen/ScreenHandlerType;"
        )
    )
    private static <T extends ScreenHandler> ScreenHandlerType<T> replaceStonecutterScreenHandler(String id, ScreenHandlerType.Factory<T> factory) {
        if (id.equals("stonecutter"))
            return null;
        return register(id, factory);
    }
}
