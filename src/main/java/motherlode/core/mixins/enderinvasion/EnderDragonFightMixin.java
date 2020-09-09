package motherlode.core.mixins.enderinvasion;

import motherlode.core.enderinvasion.EnderInvasion;
import motherlode.core.enderinvasion.EnderInvasionComponent;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonFight;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderDragonFight.class)
public class EnderDragonFightMixin {

    @Inject(method = "dragonKilled", at = @At("RETURN"))
    public void onDragonKilled(EnderDragonEntity dragon, CallbackInfo ci) {

        EnderInvasionComponent state = EnderInvasion.STATE.get(dragon.getEntityWorld().getLevelProperties());

        if(state.value().ordinal() <= EnderInvasionComponent.State.ENDER_INVASION.ordinal()) {

            state.setValue(EnderInvasionComponent.State.POST_ENDER_DRAGON);
        }
    }
}
