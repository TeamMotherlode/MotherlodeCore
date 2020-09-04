package motherlode.biomes.block;

import motherlode.uncategorized.registry.MotherlodeBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.Random;

public class WaterplantBlock extends Block {

    public static final BooleanProperty SOLID;
    private static final VoxelShape SHAPE;

    public WaterplantBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(SOLID, true));
        MotherlodeBlocks.defaultLootTableList.add(this);
        MotherlodeBlocks.cutouts.add(this);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getFluidState(pos).getFluid() == Fluids.WATER && world.getFluidState(pos.up()).getFluid() == Fluids.EMPTY && !world.getBlockState(pos.up()).isSideSolidFullSquare(world, pos.up(), Direction.DOWN) && world.getBlockState(pos.down()).isSideSolidFullSquare(world, pos.down(), Direction.UP);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        super.neighborUpdate(state, world, pos, block, fromPos, notify);
        if(world.getFluidState(pos.up()).getFluid() != Fluids.EMPTY || world.getBlockState(pos.up()).isSideSolidFullSquare(world, pos, Direction.DOWN)) {
            world.breakBlock(pos, true);
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if(state.get(SOLID)) {
            if(context.isAbove(SHAPE, pos, true)) {
                return SHAPE;
            } else return VoxelShapes.empty();
        } else return VoxelShapes.empty();
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, Entity entity) {
        if(world.getBlockState(pos).get(SOLID)) {
            world.getBlockTickScheduler().schedule(pos, this, 10);
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.scheduledTick(state, world, pos, random);
        if(world.getBlockState(pos).get(SOLID)) {
            world.getBlockTickScheduler().schedule(pos, this, 100);
        }
        world.setBlockState(pos, world.getBlockState(pos).with(SOLID, !world.getBlockState(pos).get(SOLID)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SOLID);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return Fluids.WATER.getStill(false);
    }

    static {
        SOLID = BooleanProperty.of("solid");
        SHAPE = createCuboidShape(3, 0, 3, 13, 16, 13);
    }
}
