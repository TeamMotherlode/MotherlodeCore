package motherlode.base.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.PillarBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Blocks.class)
public interface BlocksAccessor {

    @Invoker("createLogBlock")
    static PillarBlock callCreateLogBlock(MapColor topMapColor, MapColor sideMapColor) {
        throw new IllegalStateException();
    }

    @Invoker("createLeavesBlock")
    static LeavesBlock callCreateLeavesBlock(BlockSoundGroup soundGroup) {
        throw new IllegalStateException();
    }

    @Invoker("canSpawnOnLeaves")
    @SuppressWarnings("unused")
    static Boolean canSpawnOnLeaves(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
        throw new IllegalStateException();
    }

    @Invoker("always")
    @SuppressWarnings("unused")
    static Boolean always(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
        throw new IllegalStateException();
    }

    @Invoker("never")
    @SuppressWarnings("unused")
    static Boolean never(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
        throw new IllegalStateException();
    }
}
