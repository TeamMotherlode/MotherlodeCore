package motherlode.core.gui;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import motherlode.core.Motherlode;
import motherlode.core.registry.MotherlodeScreenHandlers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.SlotActionType;

import java.util.ArrayList;

public class RedstoneTransmitterGuiDescription extends SyncedGuiDescription {
    ArrayList<WSprite> gems = new ArrayList<>();

    public RedstoneTransmitterGuiDescription(int syncId, PlayerInventory inventory, ScreenHandlerContext context) {
        super(MotherlodeScreenHandlers.REDSTONE_TRANSMITTER_TYPE, syncId, inventory, getBlockInventory(context), getBlockPropertyDelegate(context));

        WGridPanel root = new WGridPanel();
        setRootPanel(root);

        WPlainPanel panel = new WPlainPanel();

        WItemSlot grid = WItemSlot.of(blockInventory, 0, 3, 3);
        panel.add(grid, 36, 22);

        WSprite transmitter = new WSprite(Motherlode.id("textures/gui/container/transmitter_disconnected.png"));
        panel.add(transmitter, 109, 22, 15, 54);

        for (int i = 0; i < 9; i++) {
            WSprite gem = new WSprite(Motherlode.id("textures/gui/container/gem.png"));
            panel.add(gem, 38 + (i * 18 - ((i / 3) * 54)), 26 + ((i / 3) * 18), 14, 11);
            gems.add(gem);
        }

        for (int i = 0; i < blockInventory.size(); i++) {
            if (blockInventory.getStack(i).isEmpty()) {
                gems.get(i).setImage(Motherlode.id("textures/gui/container/gem.png"));
            } else {
                gems.get(i).setImage(Motherlode.id("textures/gui/container/empty.png"));
            }
        }

        root.add(panel, 0, 0);
        root.add(this.createPlayerInventoryPanel(), 0, 5);
        root.validate(this);
    }

    @Override
    public ItemStack onSlotClick(int slotNumber, int button, SlotActionType action, PlayerEntity player) {
        ItemStack stack = super.onSlotClick(slotNumber, button, action, player);
        for (int i = 0; i < blockInventory.size(); i++) {
            if (blockInventory.getStack(i).isEmpty()) {
                gems.get(i).setImage(Motherlode.id("textures/gui/container/gem.png"));
            } else {
                gems.get(i).setImage(Motherlode.id("textures/gui/container/empty.png"));
            }
        }
        return stack;
    }
}
