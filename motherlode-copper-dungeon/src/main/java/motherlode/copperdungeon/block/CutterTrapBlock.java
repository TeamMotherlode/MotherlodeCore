package motherlode.copperdungeon.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CutterTrapBlock extends DefaultTrapBlock {
    private static final int CUTTER_DAMAGE = 4;

    public CutterTrapBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, Entity entity) {
        super.onSteppedOn(world, pos, entity);

        BlockState state = world.getBlockState(pos);
        if(state.get(POWERED)) {
            entity.damage(DamageSource.CACTUS, CUTTER_DAMAGE);
        }
    }

    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        super.neighborUpdate(state, world, pos, block, fromPos, notify);
        boolean alreadyPowered = state.get(POWERED);
        if(alreadyPowered && !world.getBlockState(pos.up()).isAir()){
            world.setBlockState(pos, state.with(POWERED, false), 4);
            world.addSyncedBlockEvent(pos, block, 0, 0);
        }
    }

    public boolean onSyncedBlockEvent(BlockState state, World world, BlockPos pos, int type, int data) {
        super.onSyncedBlockEvent(state, world, pos, type, data);
        if(data == 1){
            world.playSound(null, pos, SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.BLOCKS, 0.15F, world.random.nextFloat() * 0.15F + 0.6F);
        }else{
            world.playSound(null, pos, SoundEvents.BLOCK_PISTON_CONTRACT, SoundCategory.BLOCKS, 0.15F, world.random.nextFloat() * 0.15F + 0.6F);
        }
        return true;
    }

    @Override
    public boolean canDeploy(BlockState state, World world, BlockPos pos) {
        return world.getBlockState(pos.up()).isAir();
    }
}
