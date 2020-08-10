package motherlode.core.potions.mixins;

import com.sun.scenario.Settings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PotionItem.class)
public class PotionItemMixin extends Item {

    public PotionItemMixin(Settings settings) {super(settings);}

    @Inject(method = "hasGlint", at = @At("RETURN"), cancellable = true)
    private void hasGlint(ItemStack stack, CallbackInfoReturnable<Boolean> callback) {
        callback.setReturnValue(super.hasGlint(stack));
    }

}
