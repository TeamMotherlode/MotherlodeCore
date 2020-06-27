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
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.SlotActionType;

import java.util.ArrayList;

public class RedstoneTransmitterGuiDescription extends SyncedGuiDescription {
    ArrayList<WSprite> gems = new ArrayList<>();
    ArrayList<WSprite> miniGemsTop = new ArrayList<>();
    ArrayList<WSprite> miniGemsBottom = new ArrayList<>();

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

            WSprite miniGemTop = new WSprite(Motherlode.id("textures/gui/container/white.png"));
            panel.add(miniGemTop, 112 + (i * 4 - ((i / 3) * 12)), 25 + ((i / 3) * 4), 1, 1);

            WSprite miniGemBottom = new WSprite(Motherlode.id("textures/gui/container/white.png"));
            panel.add(miniGemBottom, 112 + (i * 4 - ((i / 3) * 12)), 64 + ((i / 3) * 4), 1, 1);

            miniGemsTop.add(miniGemTop);
            miniGemsBottom.add(miniGemBottom);
        }

        for (int i = 0; i < blockInventory.size(); i++) {
            if (blockInventory.getStack(i).isEmpty()) {
                gems.get(i).setImage(Motherlode.id("textures/gui/container/gem.png"));
                miniGemsTop.get(i).setImage(Motherlode.id("textures/gui/container/empty.png"));
                miniGemsBottom.get(i).setImage(Motherlode.id("textures/gui/container/empty.png"));
            } else {
                gems.get(i).setImage(Motherlode.id("textures/gui/container/empty.png"));
                miniGemsTop.get(i).setImage(Motherlode.id("textures/gui/container/white.png"));
                miniGemsBottom.get(i).setImage(Motherlode.id("textures/gui/container/white.png"));
                miniGemsTop.get(i).setOpaqueTint(0x49EAD6);
                miniGemsBottom.get(i).setOpaqueTint(0x49EAD6);
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
                miniGemsTop.get(i).setImage(Motherlode.id("textures/gui/container/empty.png"));
                miniGemsBottom.get(i).setImage(Motherlode.id("textures/gui/container/empty.png"));
            } else {
                gems.get(i).setImage(Motherlode.id("textures/gui/container/empty.png"));
                // TODO Check simply if it's a gem
                if (blockInventory.getStack(i).getItem() == Items.DIAMOND) {
                    miniGemsTop.get(i).setImage(Motherlode.id("textures/gui/container/white.png"));
                    miniGemsBottom.get(i).setImage(Motherlode.id("textures/gui/container/white.png"));

                    // TODO Tint based on gem color
                    miniGemsTop.get(i).setOpaqueTint(0x49EAD6);
                    miniGemsBottom.get(i).setOpaqueTint(0x49EAD6);
                } else {
                    miniGemsTop.get(i).setImage(Motherlode.id("textures/gui/container/empty.png"));
                    miniGemsBottom.get(i).setImage(Motherlode.id("textures/gui/container/empty.png"));
                }
            }
        }
        return stack;
    }
}
