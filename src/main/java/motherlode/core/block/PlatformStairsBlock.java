package motherlode.core.block;

import com.swordglowsblue.artifice.api.builder.assets.BlockStateBuilder;
import com.swordglowsblue.artifice.api.builder.assets.ModelBuilder;
import com.swordglowsblue.artifice.api.util.Processor;
import motherlode.core.Motherlode;
import motherlode.core.registry.MotherlodeBlocks;
import net.minecraft.block.*;
import net.minecraft.block.enums.StairShape;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.Pair;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

import java.util.ArrayList;
import java.util.function.Function;

public class PlatformStairsBlock extends MotherlodeStairsBlock {

    private static final Function<String, Processor<BlockStateBuilder>> BLOCKSTATE_PROCESSOR_FUNCTION;


    public PlatformStairsBlock(BlockState baseBlockState, Settings settings) {
        super(baseBlockState, settings);
        MotherlodeBlocks.defaultLootTableList.add(this);
        MotherlodeBlocks.cutouts.add(this);
        MotherlodeBlocks.customStateList.put(this, BLOCKSTATE_PROCESSOR_FUNCTION);
        ArrayList<Function<String, Pair<String, Processor<ModelBuilder>>>> models = new ArrayList<>();
        models.add(name -> new Pair<>(name, modelBuilder -> modelBuilder
                .parent(Motherlode.id("block/templates/platform_stairs"))
                .texture("side", Motherlode.id("block/" + name.replace("_stairs", "")))
                .texture("top", Motherlode.id("block/" + name.replace("_stairs", "") + "_top"))));
        models.add(name -> new Pair<>(name + "_inner", modelBuilder -> modelBuilder
                .parent(Motherlode.id("block/templates/platform_stairs_inner"))
                .texture("side", Motherlode.id("block/" + name.replace("_stairs", "")))
                .texture("top", Motherlode.id("block/" + name.replace("_stairs", "") + "_top"))));
        models.add(name -> new Pair<>(name + "_outer", modelBuilder -> modelBuilder
                .parent(Motherlode.id("block/templates/platform_stairs_outer"))
                .texture("side", Motherlode.id("block/" + name.replace("_stairs", "")))
                .texture("top", Motherlode.id("block/" + name.replace("_stairs", "") + "_top"))));
        MotherlodeBlocks.customBlockModelList.put(this, models);
        MotherlodeBlocks.defaultItemModelList.add(this);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction dir = state.get(PlatformStairsBlock.FACING);
        int id = dir.getOpposite().getHorizontal();
        int add = getShapeIndexIndex(state);
        if (state.get(SHAPE).equals(StairShape.INNER_RIGHT) || state.get(SHAPE).equals(StairShape.OUTER_RIGHT))
            id = (id + 1) % 4;
        id = add * 4 + id;
        return STAIR_SHAPES[id];
    }

    public int getShapeIndexIndex(BlockState state) {
        int i = state.get(SHAPE).ordinal();
        if (i == 2) return 1;
        else if (i == 3 || i == 4) return 2;
        return i;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        //noinspection ConstantConditions
        ItemPlacementContext ctx2 = new ItemPlacementContext(ctx.getPlayer(), ctx.getHand(), ctx.getStack(),
                new BlockHitResult(ctx.getHitPos(), Direction.UP, ctx.getBlockPos(), ctx.hitsInsideBlock()));
        return super.getPlacementState(ctx2);
    }

    private static final VoxelShape[] STAIR_SHAPES;

    private static final VoxelShape BNW, BSW, TNW, TSW, BNE, BSE, TNE, TSE;

    static {
        BNW = Block.createCuboidShape(0.0D, 6.0D, 0.0D, 8.0D, 8.0D, 8.0D);
        BSW = Block.createCuboidShape(0.0D, 6.0D, 8.0D, 8.0D, 8.0D, 16.0D);
        TNW = Block.createCuboidShape(0.0D, 14.0D, 0.0D, 8.0D, 16.0D, 8.0D);
        TSW = Block.createCuboidShape(0.0D, 14.0D, 8.0D, 8.0D, 16.0D, 16.0D);
        BNE = Block.createCuboidShape(8.0D, 6.0D, 0.0D, 16.0D, 8.0D, 8.0D);
        BSE = Block.createCuboidShape(8.0D, 6.0D, 8.0D, 16.0D, 8.0D, 16.0D);
        TNE = Block.createCuboidShape(8.0D, 14.0D, 0.0D, 16.0D, 16.0D, 8.0D);
        TSE = Block.createCuboidShape(8.0D, 14.0D, 8.0D, 16.0D, 16.0D, 16.0D);
        STAIR_SHAPES = new VoxelShape[]{
                //Normal Straight S W N E
                VoxelShapes.union(TNW, TNE, BSW, BSE), VoxelShapes.union(BNW, TNE, BSW, TSE), VoxelShapes.union(BNW, BNE, TSW, TSE), VoxelShapes.union(TNW, BNE, TSW, BSE),
                //Corners Inner
                VoxelShapes.union(TNW, TNE, TSW, BSE), VoxelShapes.union(TNW, TNE, BSW, TSE), VoxelShapes.union(BNW, TNE, TSW, TSE), VoxelShapes.union(TNW, BNE, TSW, TSE),
                //Corners Outer
                VoxelShapes.union(TNW, BNE, BSW, BSE), VoxelShapes.union(BNW, TNE, BSW, BSE), VoxelShapes.union(BNW, BNE, BSW, TSE), VoxelShapes.union(BNW, BNE, TSW, BSE),
        };
    }

    static {
        BLOCKSTATE_PROCESSOR_FUNCTION = name -> blockStateBuilder -> blockStateBuilder
                .variant("facing=east,shape=inner_left", variant -> variant
                        .model(Motherlode.id("block/" + name + "_inner"))
                        .uvlock(true)
                        .rotationY(270))
                .variant("facing=east,shape=inner_right", variant -> variant
                        .model(Motherlode.id("block/" + name + "_inner")))
                .variant("facing=east,shape=outer_left", variant -> variant
                        .model(Motherlode.id("block/" + name + "_outer"))
                        .uvlock(true)
                        .rotationY(270))
                .variant("facing=east,shape=outer_right", variant -> variant
                        .model(Motherlode.id("block/" + name + "_outer")))
                .variant("facing=east,shape=straight", variant -> variant
                        .model(Motherlode.id("block/" + name)))
                .variant("facing=north,shape=inner_left", variant -> variant
                        .model(Motherlode.id("block/" + name + "_inner"))
                        .uvlock(true)
                        .rotationY(180))
                .variant("facing=north,shape=inner_right", variant -> variant
                        .model(Motherlode.id("block/" + name + "_inner"))
                        .rotationY(270)
                        .uvlock(true))
                .variant("facing=north,shape=outer_left", variant -> variant
                        .model(Motherlode.id("block/" + name + "_outer"))
                        .uvlock(true)
                        .rotationY(180))
                .variant("facing=north,shape=outer_right", variant -> variant
                        .model(Motherlode.id("block/" + name + "_outer"))
                        .uvlock(true)
                        .rotationY(270))
                .variant("facing=north,shape=straight", variant -> variant
                        .model(Motherlode.id("block/" + name))
                        .rotationY(270)
                        .uvlock(true))

                .variant("facing=south,shape=inner_left", variant -> variant
                        .model(Motherlode.id("block/" + name + "_inner")))
                .variant("facing=south,shape=inner_right", variant -> variant
                        .model(Motherlode.id("block/" + name + "_inner"))
                        .rotationY(90)
                        .uvlock(true))
                .variant("facing=south,shape=outer_left", variant -> variant
                        .model(Motherlode.id("block/" + name + "_outer")))
                .variant("facing=south,shape=outer_right", variant -> variant
                        .model(Motherlode.id("block/" + name + "_outer"))
                        .uvlock(true)
                        .rotationY(90))
                .variant("facing=south,shape=straight", variant -> variant
                        .model(Motherlode.id("block/" + name))
                        .rotationY(90)
                        .uvlock(true))
                .variant("facing=west,shape=inner_left", variant -> variant
                        .model(Motherlode.id("block/" + name + "_inner"))
                        .rotationY(90)
                        .uvlock(true))
                .variant("facing=west,shape=inner_right", variant -> variant
                        .model(Motherlode.id("block/" + name + "_inner"))
                        .rotationY(180)
                        .uvlock(true))
                .variant("facing=west,shape=outer_left", variant -> variant
                        .model(Motherlode.id("block/" + name + "_outer"))
                        .rotationY(90)
                        .uvlock(true))
                .variant("facing=west,shape=outer_right", variant -> variant
                        .model(Motherlode.id("block/" + name + "_outer"))
                        .uvlock(true)
                        .rotationY(180))
                .variant("facing=west,shape=straight", variant -> variant
                        .model(Motherlode.id("block/" + name))
                        .rotationY(180)
                        .uvlock(true));
    }

}
