package motherlode.core.block;

import motherlode.core.block.entity.ZapperTrapBlockEntity;
import motherlode.core.registry.MotherlodeBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class ZapperTrapBlock extends DefaultTrapBlock implements BlockEntityProvider {
    public ZapperTrapBlock(Settings settings) {
        super(settings);
        MotherlodeBlocks.translucent.add(this);
    }

    public boolean onSyncedBlockEvent(BlockState state, World world, BlockPos pos, int type, int data) {
        super.onSyncedBlockEvent(state, world, pos, type, data);
        return true;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new ZapperTrapBlockEntity();
    }
}
