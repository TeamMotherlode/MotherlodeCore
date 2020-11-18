package motherlode.base.mixin;

import java.util.Map;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import motherlode.base.api.impl.StrippedBlockMapImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AxeItem.class)
public class AxeItemMixin extends MiningToolItem {
    protected AxeItemMixin(float attackDamage, float attackSpeed, ToolMaterial material, Set<Block> effectiveBlocks, Settings settings) {
        super(attackDamage, attackSpeed, material, effectiveBlocks, settings);
    }

    @Redirect(method = "useOnBlock", at = @At(value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"))
    private Object redirectStrippedBlock(Map<Block, Block> strippedBlocks, Object block) {
        Block strippedBlock = strippedBlocks.get(block);

        if (strippedBlock != null) return strippedBlock;
        else return StrippedBlockMapImpl.INSTANCE.getStrippedBlock(block);
    }
}
