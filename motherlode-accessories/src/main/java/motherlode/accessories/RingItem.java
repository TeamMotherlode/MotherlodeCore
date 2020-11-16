package motherlode.accessories;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import motherlode.accessories.api.Quality;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotGroups;
import dev.emi.trinkets.api.Slots;
import dev.emi.trinkets.api.TrinketItem;

public class RingItem extends TrinketItem {
    public final Quality[] qualities;
    public float attributeEfficiency;

    public RingItem(Rarity rarity, Settings settings, Quality... qualities) {
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
        final List<Quality> qualityList = Arrays.asList(qualities);
        // Check for certain qualities and set relevant attributes.
        if (qualityList.contains(Qualities.FAULTY)) {
            attributeEfficiency = 0f;
        }
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

        for (Quality quality : qualities)
            quality.putModifiers(map, attributeEfficiency, group, slot, uuid, stack);
        return map;
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        for (Quality quality : this.qualities)
            tooltip.add(quality.getText());
    }

    @Override
    public void tick(PlayerEntity player, ItemStack stack) {
        for (Quality quality : qualities)
            quality.tick(attributeEfficiency, player, stack);
    }
}
