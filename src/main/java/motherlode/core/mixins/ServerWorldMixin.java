package motherlode.core.mixins;

import motherlode.core.datastore.RedstoneChannelManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {


    @Shadow
    public abstract MinecraftServer getServer();

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerTickScheduler;tick()V"), method = "tick")
    private void tick(CallbackInfo cbi) {
        getServer().getOverworld().getPersistentStateManager().getOrCreate(RedstoneChannelManager::new, "motherlode_wireless_channels").tick();
    }
}