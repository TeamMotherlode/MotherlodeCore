package motherlode.core.block;

import motherlode.core.block.entity.ZapperTrapBlockEntity;
import motherlode.core.particle.ZapParticle;
import motherlode.core.registry.MotherlodeBlocks;
import motherlode.core.registry.MotherlodeParticles;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Random;

public class ZapperTrapBlock extends DefaultTrapBlock implements BlockEntityProvider {
    public static final DirectionProperty FACING = Properties.FACING;
    public static final Vec3d[] FACING_ROD_OFFSET = {
            new Vec3d(0.5f, 0.15f, 0.5f),   //DOWN
            new Vec3d(0.5f, 0.85f, 0.5f),   //UP
            new Vec3d(0.5f, 0.5f, 0.15f),   //NORTH
            new Vec3d(0.5f, 0.5f, 0.85f),    //SOUTH
            new Vec3d(0.15f, 0.5f, 0.5f),  //EAST
            new Vec3d(0.85f, 0.5f, 0.5f)   //WEST
    };

    protected static final VoxelShape[] OUTLINE_SHAPES = {
            VoxelShapes.union(  //DOWN
                    createCuboidShape(0.d, 16.d, 0.d, 16.d, 10.d, 16.d),
                    createCuboidShape(4.d, 9.d, 4.d, 12.d, 8.d, 12.d),
                    createCuboidShape(4.5d, 7.d, 4.5d, 11.5d, 6.d, 11.5d),
                    createCuboidShape(5.d, 5.d, 5.d, 11.d, 4.d, 11.d),
                    createCuboidShape(6.5d, 3.d, 6.5d, 9.5d, 0.d, 9.5d)),
            VoxelShapes.union(  //UP
                    createCuboidShape(0.d, 0.d, 0.d, 16.d, 6.d, 16.d),
                    createCuboidShape(4.d, 7.d, 4.d, 12.d, 8.d, 12.d),
                    createCuboidShape(4.5d, 9.d, 4.5d, 11.5d, 10.d, 11.5d),
                    createCuboidShape(5.d, 11.d, 5.d, 11.d, 12.d, 11.d),
                    createCuboidShape(6.5d, 13.d, 6.5d, 9.5d, 16.d, 9.5d)),
            VoxelShapes.union(  //NORTH
                    createCuboidShape(0.d, 0.d, 16.d, 16.d, 16.d, 10.d),
                    createCuboidShape(4.d, 4.d, 9.d, 12.d, 12.d, 8.d),
                    createCuboidShape(4.5d, 4.5d, 7.d, 11.5d, 11.5d, 6.d),
                    createCuboidShape(5.d, 5.d, 5.d, 11.d, 11.d, 4.d),
                    createCuboidShape(6.5d, 6.5d, 3.d, 9.5d, 9.5d, 0.d)),
            VoxelShapes.union(  //SOUTH
                    createCuboidShape(0.d, 0.d, 0.d, 16.d, 16.d, 6.d),
                    createCuboidShape(4.d, 4.d, 7.d, 12.d, 12.d, 8.d),
                    createCuboidShape(4.5d, 4.5d, 9.d, 11.5d, 11.5d, 10.d),
                    createCuboidShape(5.d, 5.d, 11.d, 11.d, 11.d, 12.d),
                    createCuboidShape(6.5d, 6.5d, 13.d, 9.5d, 9.5d, 16.d)),
            VoxelShapes.union(  //EAST
                    createCuboidShape(16.d, 0.d, 0.d, 10.d, 16.d, 16.d),
                    createCuboidShape(9.d, 4.d, 4.d, 8.d, 12.d, 12.d),
                    createCuboidShape(7.d, 4.5d, 4.5d, 6.0d, 11.5d, 11.5d),
                    createCuboidShape(5.d, 5.d, 5.d, 4.d, 11.d, 11.d),
                    createCuboidShape(3.d, 6.5d, 6.5d, 0d, 9.5d, 9.5d)),
            VoxelShapes.union(  //WEST
                    createCuboidShape(0.d, 0.d, 0.d, 6.d, 16.d, 16.d),
                    createCuboidShape(7.d, 4.d, 4.d, 8.d, 12.d, 12.d),
                    createCuboidShape(9.d, 4.5d, 4.5d, 10.d, 11.5d, 11.5d),
                    createCuboidShape(11.d, 5.d, 5.d, 12.d, 11.d, 11.d),
                    createCuboidShape(13.d, 6.5d, 6.5d, 16.d, 9.5d, 9.5d)),
    };

    public ZapperTrapBlock(Settings settings) {
        super(settings.lightLevel((blockState) -> blockState.get(POWERED) ? 4 : 0));
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.UP));
        MotherlodeBlocks.translucent.add(this);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
       return this.getDefaultState()
                .with(POWERED, false)
                .with(FACING, ctx.getPlayerLookDirection().getOpposite());
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return OUTLINE_SHAPES[state.get(FACING).getId()];
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED);
    }

    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(POWERED)) {
            double d = (double)pos.getX() + 0.5D;
            double e = pos.getY();
            double f = (double)pos.getZ() + 0.5D;

            MinecraftClient.getInstance().particleManager.addParticle(new ZapParticle(MinecraftClient.getInstance().world, new Vec3d(d, e, f)));
        }
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new ZapperTrapBlockEntity();
    }
}
