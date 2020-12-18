package motherlode.core.mixins;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SpawnHelper.class)
public interface SpawnHelperAccessor {

    @Invoker("isAcceptableSpawnPosition")
    static boolean isAcceptableSpawnPosition(ServerWorld world, Chunk chunk, BlockPos.Mutable pos, double squaredDistance) {
        throw new IllegalStateException();
    }

    @Invoker("createMob")
    static MobEntity createMob(ServerWorld world, EntityType<?> type) {
        throw new IllegalStateException();
    }

    @Invoker("isValidSpawn")
    static boolean isValidSpawn(ServerWorld world, MobEntity entity, double squaredDistance) {
        throw new IllegalStateException();
    }

    @Mixin(SpawnHelper.Info.class)
    interface Info {

        @Invoker("test")
        boolean callTest(EntityType<?> type, BlockPos pos, Chunk chunk);

        @Invoker("run")
        void callRun(MobEntity entity, Chunk chunk);

        @Invoker("isBelowCap")
        boolean callIsBelowCap(SpawnGroup group);
    }

}
