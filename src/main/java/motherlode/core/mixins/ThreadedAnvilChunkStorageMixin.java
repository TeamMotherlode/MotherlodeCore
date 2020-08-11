package motherlode.core.mixins;

import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Either;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ChunkHolder;
import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.storage.VersionedChunkStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;
import java.util.concurrent.CompletableFuture;

@Mixin(ThreadedAnvilChunkStorage.class)
public abstract class ThreadedAnvilChunkStorageMixin extends VersionedChunkStorage implements ChunkHolder.PlayersWatchingChunkProvider {

    public ThreadedAnvilChunkStorageMixin(File file, DataFixer dataFixer, boolean bl) {
        super(file, dataFixer, bl);
    }

    @Inject(method = "createChunkFuture", at = @At(value = "RETURN"))
    private void changeLoadedChunk(ChunkHolder chunkHolder, ChunkStatus chunkStatus, CallbackInfoReturnable<CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>>> cir) {

        cir.getReturnValue().whenComplete((either, throwable) -> {

            if(throwable == null) {

                either.mapLeft(chunk -> {

                    chunk.setBlockState(new BlockPos(chunkHolder.getPos().x * 16, 80, chunkHolder.getPos().z * 16), Blocks.DIAMOND_BLOCK.getDefaultState(), false);
                    return chunk;
                });
            }
        });
    }
}
