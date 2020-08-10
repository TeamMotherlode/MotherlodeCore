package motherlode.core.world.feature;

import motherlode.core.block.ReedsBlock;
import motherlode.core.registry.MotherlodeBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class MarshFeature extends Feature<DefaultFeatureConfig> {
    public MarshFeature() {
        super(DefaultFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(ServerWorldAccess serverWorldAccess, StructureAccessor accessor, ChunkGenerator generator, Random random, BlockPos bpos, DefaultFeatureConfig config) {
        boolean b = false;
        BlockPos pos = serverWorldAccess.getTopPosition(Heightmap.Type.WORLD_SURFACE, bpos).down();
        boolean[][] replace = new boolean[24][24];
        if(serverWorldAccess.getBlockState(pos.up()).isAir()) {
            for (int a = 0; a < 24; a++) {
                for (int c = 0; c < 24; c++) {
                    int x = a - 12;
                    int z = c - 12;
                    BlockPos mpos = pos.add(x, 0, z);
                    if (shouldReplace(serverWorldAccess, mpos) && Math.sqrt(Math.pow(x,2) + Math.pow(z,2)) <= 12) {
                        b = true;
                        replace[a][c] = true;
                    }
                }
            }
            for (int a = 0; a < 24; a++) {
                for (int c = 0; c < 24; c++) {
                    int x = a - 12;
                    int z = c - 12;
                    if (replace[a][c]) {
                        BlockPos mpos = pos.add(x, 0, z);
                        int r = random.nextInt(9);
                        if (r == 1) {
                            serverWorldAccess.setBlockState(mpos, MotherlodeBlocks.WATERPLANT.getDefaultState(), 2);
                        } else if (r == 2) {
                            serverWorldAccess.setBlockState(mpos, Blocks.SEAGRASS.getDefaultState(), 2);
                        } else if (r == 3) {
                            int s = random.nextInt(6);
                            int t = random.nextInt(6);
                            Block k;
                            if(s == 1 || s == 2) k = MotherlodeBlocks.REEDS;
                            else if(s == 3 || s == 4 || s == 5) k = MotherlodeBlocks.CATTAIL_REEDS;
                            else k = MotherlodeBlocks.DRY_REEDS;
                            if(t == 1 || t == 2) {
                                serverWorldAccess.setBlockState(mpos, k.getDefaultState().with(ReedsBlock.TYPE, ReedsBlock.Type.WATERLOGGED), 2);
                            } else if(t == 3 || t == 4 || t == 5) {
                                serverWorldAccess.setBlockState(mpos, k.getDefaultState().with(ReedsBlock.TYPE, ReedsBlock.Type.BOTTOM), 2);
                                serverWorldAccess.setBlockState(mpos.up(), k.getDefaultState().with(ReedsBlock.TYPE, ReedsBlock.Type.TOP), 2);
                            } else {
                                serverWorldAccess.setBlockState(mpos, k.getDefaultState().with(ReedsBlock.TYPE, ReedsBlock.Type.BOTTOM), 2);
                                serverWorldAccess.setBlockState(mpos.up(), k.getDefaultState().with(ReedsBlock.TYPE, ReedsBlock.Type.MIDDLE), 2);
                                serverWorldAccess.setBlockState(mpos.up(2), k.getDefaultState().with(ReedsBlock.TYPE, ReedsBlock.Type.TOP), 2);
                            }
                        } else serverWorldAccess.setBlockState(mpos, Blocks.WATER.getDefaultState().with(Properties.LEVEL_15, 0), 2);
                        if(!serverWorldAccess.getBlockState(mpos.up()).isAir() && r != 3) {
                            serverWorldAccess.setBlockState(mpos.up(), Blocks.AIR.getDefaultState(), 2);
                        }
                    }
                }
            }
        }
        return b;
    }

    private static boolean shouldReplace(BlockView world, BlockPos pos) {
        boolean b = true;
        if(world.getBlockState(pos).isAir()) b = false;
        if(!world.getBlockState(pos.north()).isSolidBlock(world, pos) && world.getFluidState(pos.north()).getFluid() != Fluids.WATER) b = false;
        if(!world.getBlockState(pos.south()).isSolidBlock(world, pos) && world.getFluidState(pos.north()).getFluid() != Fluids.WATER) b = false;
        if(!world.getBlockState(pos.east()).isSolidBlock(world, pos) && world.getFluidState(pos.north()).getFluid() != Fluids.WATER) b = false;
        if(!world.getBlockState(pos.west()).isSolidBlock(world, pos) && world.getFluidState(pos.north()).getFluid() != Fluids.WATER) b = false;
        if(world.getBlockState(pos.up()).isSolidBlock(world, pos)) b = false;
        if(!world.getBlockState(pos.down()).isSolidBlock(world, pos)) b = false;
        return b;
    }
}
