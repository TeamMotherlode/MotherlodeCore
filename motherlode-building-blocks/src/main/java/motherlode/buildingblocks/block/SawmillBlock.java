package motherlode.buildingblocks.block;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import motherlode.base.api.assets.AssetProcessor;
import motherlode.base.api.assets.CommonAssets;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import motherlode.buildingblocks.MotherlodeBuildingBlocks;
import motherlode.buildingblocks.screen.SawmillScreenHandler;
import org.jetbrains.annotations.Nullable;

public class SawmillBlock extends Block implements AssetProcessor {
    public SawmillBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        } else {
            player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
            player.incrementStat(MotherlodeBuildingBlocks.INTERACT_WITH_SAWMILL_STAT);
            return ActionResult.CONSUME;
        }
    }

    @Nullable
    @Override
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return new SimpleNamedScreenHandlerFactory((i, playerInventory, playerEntity) -> new SawmillScreenHandler(i, ScreenHandlerContext.create(world, pos), playerInventory), new TranslatableText("container.sawmill"));
    }

    @Override
    public void accept(ArtificeResourcePack.ClientResourcePackBuilder pack, Identifier id) {
        CommonAssets.BLOCK_ITEM.accept(pack, id);
    }
}
