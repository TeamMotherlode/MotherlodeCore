package motherlode.accessories.api;

import java.util.UUID;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import com.google.common.collect.Multimap;

@FunctionalInterface
public interface TrinketModifier {
    void putModifiers(Multimap<EntityAttribute, EntityAttributeModifier> map, float attributeEfficiency, String group, String slot, UUID uuid, ItemStack stack);
}
