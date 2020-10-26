package motherlode.redstone.gui;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import motherlode.base.Motherlode;
import motherlode.orestoolsarmor.MotherlodeOresToolsArmorTags;
import motherlode.orestoolsarmor.item.DefaultGemItem;
import motherlode.redstone.MotherlodeModule;
import motherlode.redstone.MotherlodeRedstoneScreenHandlers;
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
        super(MotherlodeRedstoneScreenHandlers.REDSTONE_TRANSMITTER_TYPE, syncId, inventory, SyncedGuiDescription.getBlockInventory(context), SyncedGuiDescription.getBlockPropertyDelegate(context));

        WGridPanel root = new WGridPanel();
        setRootPanel(root);

        WPlainPanel panel = new WPlainPanel();

        WItemSlot grid = WItemSlot.of(blockInventory, 0, 3, 3).setFilter(itemStack -> (!itemStack.isEmpty() && itemStack.getItem().isIn(MotherlodeOresToolsArmorTags.GEMS)));
        panel.add(grid, 36, 22);

        WSprite transmitter = new WSprite(Motherlode.id(MotherlodeModule.MODID, "gui/container/transmitter_disconnected.png"));
        panel.add(transmitter, 109, 22, 15, 54);

        for (int i = 0; i < 9; i++) {
            WSprite gem = new WSprite(Motherlode.id(MotherlodeModule.MODID, "gui/container/gem.png"));
            panel.add(gem, 38 + (i * 18 - ((i / 3) * 54)), 26 + ((i / 3) * 18), 14, 11);
            gems.add(gem);

            WSprite miniGemTop = new WSprite(Motherlode.id(MotherlodeModule.MODID, "gui/container/white.png"));
            panel.add(miniGemTop, 112 + (i * 4 - ((i / 3) * 12)), 25 + ((i / 3) * 4), 1, 1);

            WSprite miniGemBottom = new WSprite(Motherlode.id(MotherlodeModule.MODID, "gui/container/white.png"));
            panel.add(miniGemBottom, 112 + (i * 4 - ((i / 3) * 12)), 64 + ((i / 3) * 4), 1, 1);

            miniGemsTop.add(miniGemTop);
            miniGemsBottom.add(miniGemBottom);
        }

        for (int i = 0; i < blockInventory.size(); i++) {
            if (blockInventory.getStack(i).isEmpty()) {
                gems.get(i).setImage(Motherlode.id(MotherlodeModule.MODID, "gui/container/gem.png"));
                miniGemsTop.get(i).setImage(Motherlode.id(MotherlodeModule.MODID, "gui/container/empty.png"));
                miniGemsBottom.get(i).setImage(Motherlode.id(MotherlodeModule.MODID, "gui/container/empty.png"));
            } else {
                gems.get(i).setImage(Motherlode.id(MotherlodeModule.MODID, "gui/container/empty.png"));
                if (blockInventory.getStack(i).getItem().isIn(MotherlodeOresToolsArmorTags.GEMS)) {
                    miniGemsTop.get(i).setImage(Motherlode.id(MotherlodeModule.MODID, "gui/container/white.png"));
                    miniGemsBottom.get(i).setImage(Motherlode.id(MotherlodeModule.MODID, "gui/container/white.png"));

                    if (blockInventory.getStack(i).getItem() == Items.DIAMOND) {
                        miniGemsTop.get(i).setOpaqueTint(0x49EAD6);
                        miniGemsBottom.get(i).setOpaqueTint(0x49EAD6);
                    }

                    if (blockInventory.getStack(i).getItem() == Items.EMERALD) {
                        miniGemsTop.get(i).setOpaqueTint(0x17DA61);
                        miniGemsBottom.get(i).setOpaqueTint(0x17DA61);
                    }

                    if (blockInventory.getStack(i).getItem() instanceof DefaultGemItem) {
                        DefaultGemItem gemItem = (DefaultGemItem) blockInventory.getStack(i).getItem();

                        miniGemsTop.get(i).setOpaqueTint(gemItem.getColor());
                        miniGemsBottom.get(i).setOpaqueTint(gemItem.getColor());
                    }
                } else {
                    miniGemsTop.get(i).setImage(Motherlode.id(MotherlodeModule.MODID, "gui/container/empty.png"));
                    miniGemsBottom.get(i).setImage(Motherlode.id(MotherlodeModule.MODID, "gui/container/empty.png"));
                }
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
                gems.get(i).setImage(Motherlode.id(MotherlodeModule.MODID, "gui/container/gem.png"));
                miniGemsTop.get(i).setImage(Motherlode.id(MotherlodeModule.MODID, "gui/container/empty.png"));
                miniGemsBottom.get(i).setImage(Motherlode.id(MotherlodeModule.MODID, "gui/container/empty.png"));
            } else {
                gems.get(i).setImage(Motherlode.id(MotherlodeModule.MODID, "gui/container/empty.png"));
                if (blockInventory.getStack(i).getItem().isIn(MotherlodeOresToolsArmorTags.GEMS)) {
                    miniGemsTop.get(i).setImage(Motherlode.id(MotherlodeModule.MODID, "gui/container/white.png"));
                    miniGemsBottom.get(i).setImage(Motherlode.id(MotherlodeModule.MODID, "gui/container/white.png"));

                    if (blockInventory.getStack(i).getItem() == Items.DIAMOND) {
                        miniGemsTop.get(i).setOpaqueTint(0x49EAD6);
                        miniGemsBottom.get(i).setOpaqueTint(0x49EAD6);
                    }

                    if (blockInventory.getStack(i).getItem() == Items.EMERALD) {
                        miniGemsTop.get(i).setOpaqueTint(0x17DA61);
                        miniGemsBottom.get(i).setOpaqueTint(0x17DA61);
                    }

                    if (blockInventory.getStack(i).getItem() instanceof DefaultGemItem) {
                        DefaultGemItem gemItem = (DefaultGemItem) blockInventory.getStack(i).getItem();

                        miniGemsTop.get(i).setOpaqueTint(gemItem.getColor());
                        miniGemsBottom.get(i).setOpaqueTint(gemItem.getColor());
                    }
                } else {
                    miniGemsTop.get(i).setImage(Motherlode.id(MotherlodeModule.MODID, "gui/container/empty.png"));
                    miniGemsBottom.get(i).setImage(Motherlode.id(MotherlodeModule.MODID, "gui/container/empty.png"));
                }
            }
        }
        return stack;
    }
}
