package motherlode.core.registry;

import dev.emi.trinkets.api.SlotGroups;
import dev.emi.trinkets.api.Slots;
import dev.emi.trinkets.api.TrinketSlots;
import net.minecraft.util.Identifier;

public class MotherlodeTrinketSlots {

    public static final String TOTEM = "totem";

    public static void init() {
        TrinketSlots.addSlot(SlotGroups.OFFHAND, Slots.RING, new Identifier("trinkets", "textures/item/empty_trinket_slot_ring.png"));
        TrinketSlots.addSlot(SlotGroups.OFFHAND, TOTEM, new Identifier("motherlode", "textures/item/empty_trinket_slot_totem.png"));
    }
}
