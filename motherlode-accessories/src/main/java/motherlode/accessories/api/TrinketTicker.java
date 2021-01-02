package motherlode.accessories.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

@FunctionalInterface
public interface TrinketTicker {
    void tick(float attributeEfficiency, PlayerEntity player, ItemStack stack);
}
