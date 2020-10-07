package motherlode.biomes.mixin;

import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {
    @Inject(method = "applyFog", at = @At("HEAD"), cancellable = true)
    private static void applyFoggyBiomeEffect(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, CallbackInfo ci) {
        /* Biome biome = camera.getFocusedEntity().world.getBiome(camera.getFocusedEntity().getBlockPos());
        if(biome instanceof AbstractFoggyBiome && camera.getFocusedEntity().world.getFluidState(camera.getBlockPos()).getFluid() == Fluids.EMPTY) {
            AbstractFoggyBiome foggyBiome = ((AbstractFoggyBiome)biome);
            RenderSystem.fogDensity(foggyBiome.getFogDensity(camera.getFocusedEntity().getPos().getX(), camera.getFocusedEntity().getPos().getZ()));
            RenderSystem.fogMode(foggyBiome.getFogMode());
            ci.cancel();
        } */ // TODO
    }
}
