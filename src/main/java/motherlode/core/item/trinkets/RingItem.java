package motherlode.core.item.trinkets;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotGroups;
import dev.emi.trinkets.api.Slots;
import dev.emi.trinkets.api.TrinketItem;
import motherlode.core.Motherlode;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class RingItem extends TrinketItem {

    public final Qualities[] qualities;
    public float attributeEfficiency;

    public RingItem(Rarity rarity, Settings settings, Qualities... qualities) {
        super(settings);
        this.qualities = qualities;
    }

    @Override
    public boolean canWearInSlot(String group, String slot) {
        return group.equals(SlotGroups.OFFHAND) && slot.equals(Slots.RING);
    }

    @Override
    public void onEquip(PlayerEntity player, ItemStack stack) {
        // Convert qualities to list for checking.
        final List<Qualities> qualityList = Arrays.asList(qualities);
        // Check for certain qualities and set relevant attributes.
        if (this.getRarity(stack) == Rarity.COMMON) {
            attributeEfficiency = 1.0f;
        }
        if (this.getRarity(stack) == Rarity.UNCOMMON) {
            attributeEfficiency = 2.0f;
        }
        if (this.getRarity(stack) == Rarity.RARE) {
            attributeEfficiency = 3.0f;
        }

    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getTrinketModifiers(String group, String slot, UUID uuid, ItemStack stack) {
        Multimap<EntityAttribute, EntityAttributeModifier> map = HashMultimap.create();
        // Convert qualities to list for checking.
        final List<Qualities> qualityList = Arrays.asList(qualities);
        // Check for certain qualities and set relevant attributes.
        if (qualityList.contains(Qualities.DEXTEROUS)) {
            map.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(uuid, "Movement Speed", attributeEfficiency / 40, EntityAttributeModifier.Operation.ADDITION));
        }
        if (qualityList.contains(Qualities.STURDY)) {
            map.put(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, new EntityAttributeModifier(uuid, "Knockback Resistance", attributeEfficiency, EntityAttributeModifier.Operation.ADDITION));
            map.put(EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier(uuid, "Max Health", attributeEfficiency * 2, EntityAttributeModifier.Operation.ADDITION));
        }
        return map;
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        for (Qualities quality : this.qualities) {
            tooltip.add(quality.getText());
        }

    }

    @Override
    public void tick(PlayerEntity player, ItemStack stack) {

        final List<Qualities> qualityList = Arrays.asList(qualities);

        if (qualityList.contains(Qualities.PROTECTOR) && player.getHealth() <= 6) {
            player.applyStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, (int) (attributeEfficiency * 2), (int) attributeEfficiency));
        }

        if (qualityList.contains(Qualities.AQUATIC) && player.isTouchingWaterOrRain() && !qualityList.contains(Qualities.FAULTY)) {
            player.applyStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 1, (int) attributeEfficiency));
        }
    }

}
