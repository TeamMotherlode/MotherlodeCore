package motherlode.buildingblocks.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import motherlode.buildingblocks.MotherlodeModule;
import motherlode.buildingblocks.screen.StonecutterScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(HandledScreens.class)
public abstract class HandledScreensMixin {

    @Shadow
    private static <M extends ScreenHandler, U extends Screen & ScreenHandlerProvider<M>> void register(ScreenHandlerType<? extends M> type, HandledScreens.Provider<M, U> provider) {
        throw new IllegalStateException();
    }

    @Redirect(
        method = "<clinit>",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/screen/ingame/HandledScreens;register(Lnet/minecraft/screen/ScreenHandlerType;Lnet/minecraft/client/gui/screen/ingame/HandledScreens$Provider;)V"
        )
    )
    private static <M extends ScreenHandler, U extends Screen & ScreenHandlerProvider<M>> void replaceStonecutterScreen(ScreenHandlerType<? extends M> type, HandledScreens.Provider<M, U> provider) {
        if (type == ScreenHandlerType.STONECUTTER)
            ScreenRegistry.register(MotherlodeModule.STONECUTTER_SCREEN_HANDLER, StonecutterScreen::new);
        else
            register(type, provider);
    }
}
