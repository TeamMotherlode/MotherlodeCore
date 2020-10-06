package motherlode.buildingblocks.block;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import motherlode.base.CommonAssets;
import motherlode.base.Motherlode;
import motherlode.base.api.AssetProcessor;
import motherlode.buildingblocks.MotherlodeBuildingBlocksMod;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ShovelItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class ShovelableBlock extends Block implements AssetProcessor {

    public static final BooleanProperty SHOVELED = BooleanProperty.of("shoveled");
    public final boolean isRotatable;
    private final SoundEvent shovelSound;

    public ShovelableBlock(boolean rotatable, SoundEvent shovelSound, AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(SHOVELED, false));
        this.isRotatable = rotatable;
        this.shovelSound = shovelSound;
    }

    public ShovelableBlock(boolean rotatable, AbstractBlock.Settings settings) {
        this(rotatable, SoundEvents.ITEM_SHOVEL_FLATTEN, settings);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        return state.with(SHOVELED, world.getBlockState(pos).get(SHOVELED) && world.getBlockState(pos.up()).isAir());
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(player.getStackInHand(hand).getItem() instanceof ShovelItem && hit.getSide() != Direction.DOWN && !world.getBlockState(pos).get(SHOVELED) && world.getBlockState(pos.up()).isAir()) {
            world.playSound(pos.getX()+0.5, pos.getY() +0.5, pos.getZ()+0.5, shovelSound, SoundCategory.BLOCKS, 1.0f, 1.0f, true);
            world.setBlockState(pos, state.with(SHOVELED, true));
            return ActionResult.SUCCESS;
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(0, 0, 0, 16, state.get(SHOVELED) ? 15 : 16, 16);
    }

    @Override
    public boolean hasSidedTransparency(BlockState state) {
        return state.get(SHOVELED);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SHOVELED);
    }

    @Override
    public void accept(ArtificeResourcePack.ClientResourcePackBuilder pack, Identifier id) {

        pack.addBlockState(id, state -> {
            for (int i = 0; i < (this.isRotatable ? 4 : 1); i++) {
                int finalI = i;
                state.variant("shoveled=false", variant -> variant
                  .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath()))
                  .rotationY(finalI * 90)
                );
                state.variant("shoveled=true", variant -> variant
                  .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_shoveled"))
                  .rotationY(finalI * 90)
                );
            }
        });
        pack.addBlockModel(id, state -> state
          .parent(new Identifier("block/cube_all"))
          .texture("all", Motherlode.id(id.getNamespace(), "block/" + id.getPath()))
        );
        pack.addBlockModel(Motherlode.id(id.getNamespace(), id.getPath() + "_shoveled"), state -> state
          .parent(Motherlode.id(MotherlodeBuildingBlocksMod.MODID, "block/cube_lowered"))
          .texture("top", Motherlode.id(id.getNamespace(), "block/" + id.getPath()))
          .texture("side", Motherlode.id(id.getNamespace(), "block/" + id.getPath()))
          .texture("bottom", Motherlode.id(id.getNamespace(), "block/" + id.getPath()))
        );

        CommonAssets.BLOCK_ITEM.accept(pack, id);
    }
}
