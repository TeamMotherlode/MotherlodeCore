package motherlode.accessories;

import motherlode.base.Motherlode;
import dev.emi.trinkets.api.SlotGroups;
import dev.emi.trinkets.api.Slots;
import dev.emi.trinkets.api.TrinketSlots;

public class MotherlodeAccessoriesTrinketSlots {

    public static final String TOTEM = "totem";

    public static void init() {
        TrinketSlots.addSlot(SlotGroups.OFFHAND, Slots.RING, Motherlode.id("trinkets", "textures/item/empty_trinket_slot_ring.png"));
        TrinketSlots.addSlot(SlotGroups.OFFHAND, TOTEM, MotherlodeModule.id("textures/item/empty_trinket_slot_totem.png"));
    }
}
