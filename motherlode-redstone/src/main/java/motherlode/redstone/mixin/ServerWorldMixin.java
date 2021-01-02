package motherlode.redstone.mixin;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import motherlode.redstone.persistentstate.RedstoneChannelManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World {

    protected ServerWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, DimensionType dimensionType, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long seed) {
        super(properties, registryRef, dimensionType, profiler, isClient, debugWorld, seed);
    }

    @Shadow
    public abstract PersistentStateManager getPersistentStateManager();

    @Inject(method = "tick", at = @At(value = "HEAD"))
    private void tick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        getPersistentStateManager().getOrCreate(RedstoneChannelManager::fromTag, RedstoneChannelManager::new, "motherlode_wireless_channels").tick(this);
    }
}
