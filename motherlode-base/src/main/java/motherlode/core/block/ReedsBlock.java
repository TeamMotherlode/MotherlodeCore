package motherlode.core.block;

import motherlode.core.registry.MotherlodeBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.Random;

public class ReedsBlock extends Block implements Waterloggable {

    public static final EnumProperty<Type> TYPE;

    public ReedsBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(TYPE, Type.SINGLE));
        MotherlodeBlocks.cutouts.add(this);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        if(ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER) {
            return getDefaultState().with(TYPE, Type.WATERLOGGED);
        }
        return super.getPlacementState(ctx);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TYPE);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Block b = world.getBlockState(pos.down()).getBlock();
        return (b == Blocks.GRASS_BLOCK || b == Blocks.DIRT || b == Blocks.COARSE_DIRT || b == Blocks.SAND || b == Blocks.CLAY) &&
                ((b instanceof Waterloggable && world.getBlockState(pos.down()).get(Properties.WATERLOGGED)) ||
                        world.getFluidState(pos).getFluid() == Fluids.WATER ||
                            isWaterNeighboring(world, pos.down()));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(player.getStackInHand(hand).getItem() == Items.BONE_MEAL) {
            if(!world.isClient && new Random().nextInt(3) == 1) {
                if (state.get(TYPE) == Type.WATERLOGGED && world.getBlockState(pos.up()).isAir()) {
                    world.setBlockState(pos, getDefaultState().with(TYPE, Type.BOTTOM));
                    world.setBlockState(pos.up(), getDefaultState().with(TYPE, Type.TOP));
                } else if (state.get(TYPE) == Type.BOTTOM && world.getBlockState(pos.up(2)).isAir()) {
                    world.setBlockState(pos.up(), getDefaultState().with(TYPE, Type.MIDDLE));
                    world.setBlockState(pos.up(2), getDefaultState().with(TYPE, Type.TOP));
                } else if (state.get(TYPE) == Type.TOP && world.getBlockState(pos.up()).isAir() && world.getBlockState(pos.down()) != getDefaultState().with(TYPE, Type.MIDDLE)) {
                    world.setBlockState(pos, getDefaultState().with(TYPE, Type.MIDDLE));
                    world.setBlockState(pos.up(), getDefaultState().with(TYPE, Type.TOP));
                }
            }
            if(!player.isCreative()) player.getStackInHand(hand).decrement(1);
            world.syncWorldEvent(2005, pos, 0);
            return ActionResult.SUCCESS;
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(TYPE) == Type.WATERLOGGED || state.get(TYPE) == Type.BOTTOM ? Fluids.WATER.getStill(false) : Fluids.EMPTY.getDefaultState();
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        world.getBlockTickScheduler().schedule(pos, this, 1);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.scheduledTick(state, world, pos, random);
        boolean brk = false;
        if(state.get(TYPE) == Type.BOTTOM || state.get(TYPE) == Type.SINGLE || state.get(TYPE) == Type.WATERLOGGED) {
            if(!canPlaceAt(state, world, pos)) brk = true;
        }
        if(state.get(TYPE) == Type.BOTTOM && (world.getBlockState(pos.up()) != getDefaultState().with(TYPE, Type.TOP) && world.getBlockState(pos.up()) != getDefaultState().with(TYPE, Type.MIDDLE))){
            brk = true;
        } else if(state.get(TYPE) == Type.MIDDLE && (world.getBlockState(pos.up()) != getDefaultState().with(TYPE, Type.TOP) || world.getBlockState(pos.down()) != getDefaultState().with(TYPE, Type.BOTTOM))) {
            brk = true;
        } else if(state.get(TYPE) == Type.TOP && (world.getBlockState(pos.down()) != getDefaultState().with(TYPE, Type.BOTTOM) && world.getBlockState(pos.down()) != getDefaultState().with(TYPE, Type.MIDDLE))) brk = true;
        if(brk) world.breakBlock(pos, true);
    }

    private static boolean isWaterNeighboring(WorldView world, BlockPos pos) {
        boolean b = false;
        if(world.getFluidState(pos.north()).getFluid() == Fluids.WATER) b = true;
        else if(world.getFluidState(pos.south()).getFluid() == Fluids.WATER) b = true;
        else if(world.getFluidState(pos.east()).getFluid() == Fluids.WATER) b = true;
        else if(world.getFluidState(pos.west()).getFluid() == Fluids.WATER) b = true;
        return b;
    }

    static {
        TYPE = EnumProperty.of("type", Type.class);
    }

    public enum Type implements StringIdentifiable {
        SINGLE("single"),
        WATERLOGGED("waterlogged"),
        BOTTOM("bottom"),
        MIDDLE("middle"),
        TOP("top");

        String id;
        Type(String id) { this.id = id; }

        @Override
        public String asString() {
            return id;
        }
    }
}
