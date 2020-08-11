package motherlode.core.block;

import motherlode.core.Motherlode;
import motherlode.core.enderinvasion.EnderInvasionComponent;
import motherlode.core.enderinvasion.EnderInvasionState;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EcheriteOreBlock extends DefaultOreBlock {

    public EcheriteOreBlock(boolean hasDefaultLootTable, int miningLevel) {
        super(hasDefaultLootTable, miningLevel);
    }

    public EcheriteOreBlock(boolean hasDefaultLootTable, int minExperience, int maxExperience, int veinSize, int veinsPerChunk, int minY, int maxY, int miningLevel) {
        super(hasDefaultLootTable, minExperience, maxExperience, veinSize, veinsPerChunk, minY, maxY, miningLevel);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack stack) {

        super.afterBreak(world, player, pos, state, blockEntity, stack);

        EnderInvasionComponent component = Motherlode.ENDER_INVASION_STATE.get(world.getLevelProperties());

        if(component.value() == EnderInvasionState.PRE_ECHERITE) {

            component.setValue(EnderInvasionState.ENDER_INVASION);

            // Send chat message
            world.getPlayers().forEach(p -> player.sendMessage(new TranslatableText("enderinvasion.motherlode.start").formatted(Formatting.DARK_GREEN), false));
        }
    }
}
