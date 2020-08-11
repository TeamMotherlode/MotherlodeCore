package motherlode.core.mixins;

import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Either;
import motherlode.core.Motherlode;
import motherlode.core.enderinvasion.EnderInvasionChunkState;
import motherlode.core.enderinvasion.EnderInvasionState;
import motherlode.core.enderinvasion.SpreadRecipe;
import motherlode.core.enderinvasion.SpreadingBlocksManager;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ChunkHolder;
import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.storage.VersionedChunkStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;
import java.util.concurrent.CompletableFuture;

@Mixin(ThreadedAnvilChunkStorage.class)
public abstract class ThreadedAnvilChunkStorageMixin extends VersionedChunkStorage implements ChunkHolder.PlayersWatchingChunkProvider {

    private static final Logger LOGGER = LogManager.getLogger();

    public ThreadedAnvilChunkStorageMixin(File file, DataFixer dataFixer, boolean bl) {
        super(file, dataFixer, bl);
    }

    @Inject(method = "createChunkFuture", at = @At(value = "RETURN"))
    private void changeLoadedChunk(ChunkHolder chunkHolder, ChunkStatus chunkStatus, CallbackInfoReturnable<CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>>> cir) {

        cir.getReturnValue().whenComplete((either, throwable) -> {

            if (throwable == null) {

                either.mapLeft(chunk -> convertChunk(chunk, chunkHolder));
            }
        });
    }
    private static Chunk convertChunk(Chunk chunk, ChunkHolder holder) {

        LOGGER.info("[Motherlode Ender Invasion] " + (holder.getWorldChunk().getWorld().getDimension() == DimensionType.getOverworldDimensionType()));
        LOGGER.info("[Motherlode Ender Invasion] " + (Motherlode.ENDER_INVASION_STATE.get(holder.getWorldChunk().getWorld().getLevelProperties()).value() == EnderInvasionState.ENDER_INVASION));
        LOGGER.info("[Motherlode Ender Invasion] " + (Motherlode.ENDER_INVASION_CHUNK_STATE.get(chunk).value() == EnderInvasionChunkState.PRE_ECHERITE));

        if (holder.getWorldChunk().getWorld().getDimension() == DimensionType.getOverworldDimensionType())

        if (Motherlode.ENDER_INVASION_STATE.get(holder.getWorldChunk().getWorld().getLevelProperties()).value() == EnderInvasionState.ENDER_INVASION) {

                if (Motherlode.ENDER_INVASION_CHUNK_STATE.get(chunk).value() == EnderInvasionChunkState.PRE_ECHERITE) {

                    BlockPos blockPos;
                    BlockState state;
                    SpreadRecipe recipe;

                    int maxY = chunk.getHighestNonEmptySectionYOffset() * 16;

                    for (int x = 0; x < 16; x++) {

                        for (int y = 0; y < maxY; y++) {

                            for (int z = 0; z < 16; z++) {

                                blockPos = new BlockPos(holder.getPos().x * 16 + x, y, holder.getPos().z * 16 + z);
                                state = chunk.getBlockState(blockPos);
                                LOGGER.info("[Motherlode Ender Invasion] " + state);
                                recipe = SpreadingBlocksManager.SPREAD.getRecipe(state.getBlock());
                                if (recipe == null) continue;
                                chunk.setBlockState(blockPos, recipe.convert(state), false);
                            }
                        }
                    }
                    Motherlode.ENDER_INVASION_CHUNK_STATE.get(chunk).setValue(EnderInvasionChunkState.GENERATION_DONE);
                }
            }
        return chunk;
    }
}
