package motherlode.core.mixins;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import motherlode.core.world.biome.AbstractFoggyBiome;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {
    @Inject(method = "applyFog", at = @At("HEAD"), cancellable = true)
    private static void applyFoggyBiomeEffect(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, CallbackInfo ci) {
        Biome biome = camera.getFocusedEntity().world.getBiome(camera.getFocusedEntity().getBlockPos());
        if(biome instanceof AbstractFoggyBiome && camera.getFocusedEntity().world.getFluidState(camera.getBlockPos()).getFluid() == Fluids.EMPTY) {
            AbstractFoggyBiome foggyBiome = ((AbstractFoggyBiome)biome);
            RenderSystem.fogDensity(foggyBiome.getFogDensity(camera.getFocusedEntity().getPos().getX(), camera.getFocusedEntity().getPos().getZ()));
            RenderSystem.fogMode(foggyBiome.getFogMode());
            ci.cancel();
        }
    }
}
