package motherlode.enderinvasion;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.WorldChunk;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public class EnderInvasionEvents {
    /**
     * Called when a block can be converted.
     */
    public static final Event<ConvertBlock> CONVERT_BLOCK = EventFactory.createArrayBacked(ConvertBlock.class, callbacks -> (world, chunk, pos) -> {
        for (ConvertBlock callback : callbacks) {
            callback.convertBlock(world, chunk, pos);
        }
    });

    /**
     * Called when a block can be converted back to its normal form.
     */
    public static final Event<ConvertBlock> PURIFY_BLOCK = EventFactory.createArrayBacked(ConvertBlock.class, callbacks -> (world, chunk, pos) -> {
        for (ConvertBlock callback : callbacks) {
            callback.convertBlock(world, chunk, pos);
        }
    });

    /**
     * Called after a chunk has been converted.
     */
    public static final Event<AfterConversion> AFTER_CHUNK_CONVERSION = EventFactory.createArrayBacked(AfterConversion.class, callbacks -> (world, chunk) -> {
        for (AfterConversion callback : callbacks) {
            callback.afterConversion(world, chunk);
        }
    });

    @FunctionalInterface
    public interface ConvertBlock {

        void convertBlock(ServerWorld world, WorldChunk chunk, BlockPos pos);
    }

    @FunctionalInterface
    public interface AfterConversion {

        void afterConversion(ServerWorld world, WorldChunk chunk);
    }
}
