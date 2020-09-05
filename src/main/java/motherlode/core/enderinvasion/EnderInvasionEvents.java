package motherlode.core.enderinvasion;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import net.minecraft.world.chunk.WorldChunk;

public class EnderInvasionEvents {
    /**
     * Called when a block can be converted.
     */
    public static final Event<Convert> CONVERT = EventFactory.createArrayBacked(Convert.class, callbacks -> (world, chunk, pos, noise, sampler) -> {
        for (Convert callback : callbacks) {
            callback.convert(world, chunk, pos, noise, sampler);
        }
    });
    /**
     * Called after a chunk has been converted.
     */
    public static final Event<AfterConversion> AFTER_CONVERSION = EventFactory.createArrayBacked(AfterConversion.class, callbacks -> (world, chunk, sampler) -> {
        for (AfterConversion callback : callbacks) {
            callback.afterConversion(world, chunk, sampler);
        }
    });

    @FunctionalInterface
    public interface Convert {

        void convert(ServerWorld world, WorldChunk chunk, BlockPos pos, double noise, SimplexNoiseSampler sampler);
    }
    @FunctionalInterface
    public interface AfterConversion {

        void afterConversion(ServerWorld world, WorldChunk chunk, SimplexNoiseSampler sampler);
    }
}
