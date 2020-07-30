package motherlode.core.gui;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.netty.buffer.Unpooled;
import motherlode.core.Motherlode;
import motherlode.core.block.entity.RedstoneTransmitterBlockEntity;
import motherlode.core.item.DefaultGemItem;
import motherlode.core.registry.MotherlodePackets;
import motherlode.core.registry.MotherlodeScreenHandlers;
import motherlode.core.registry.MotherlodeTags;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class RedstoneTransmitterGuiDescription extends SyncedGuiDescription {
    ArrayList<WSprite> gems = new ArrayList<>();
    ArrayList<WSprite> miniGemsTop = new ArrayList<>();
    ArrayList<WSprite> miniGemsBottom = new ArrayList<>();
    BlockPos currPos;

    public RedstoneTransmitterGuiDescription(int syncId, PlayerInventory inventory, ScreenHandlerContext context) {
        super(MotherlodeScreenHandlers.REDSTONE_TRANSMITTER_TYPE, syncId, inventory, getBlockInventory(context), getBlockPropertyDelegate(context));

        WGridPanel root = new WGridPanel();
        setRootPanel(root);

        WPlainPanel panel = new WPlainPanel();

        WItemSlot grid = WItemSlot.of(blockInventory, 0, 3, 3).setFilter(itemStack -> (!itemStack.isEmpty() && itemStack.getItem().isIn(MotherlodeTags.Items.GEMS)));
        panel.add(grid, 36, 17);

        WSprite transmitter = new WSprite(Motherlode.id("textures/gui/container/transmitter_disconnected.png"));
        panel.add(transmitter, 109, 17, 15, 54);

        context.run((world, pos) -> {
            BlockEntity entity = world.getBlockEntity(pos);
            currPos = pos;
            if(entity instanceof RedstoneTransmitterBlockEntity) {
                RedstoneTransmitterBlockEntity blockEntity = (RedstoneTransmitterBlockEntity)entity;

                WButton button = new WButton(new LiteralText(blockEntity.getReceiver() ? "Receiver" : "Transmitter"));
                panel.add(button, 100, 75, 63, 30);
                button.setOnClick(() -> {
                    if(world.isClient()) {
                        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
                        buf.writeBlockPos(pos);
                        ClientSidePacketRegistry.INSTANCE.sendToServer(MotherlodePackets.C2S_REDSTONE_TRANSMITTER_SWAP, buf);
                    }
                    blockEntity.swapTransmitter();
                    button.setLabel(new LiteralText(blockEntity.getReceiver() ? "Receiver" : "Transmitter"));
                });
            }
        });

        for (int i = 0; i < 9; i++) {
            WSprite gem = new WSprite(Motherlode.id("textures/gui/container/gem.png"));
            panel.add(gem, 38 + (i * 18 - ((i / 3) * 54)), 21 + ((i / 3) * 18), 14, 11);
            gems.add(gem);

            WSprite miniGemTop = new WSprite(Motherlode.id("textures/gui/container/white.png"));
            panel.add(miniGemTop, 112 + (i * 4 - ((i / 3) * 12)), 20 + ((i / 3) * 4), 1, 1);

            WSprite miniGemBottom = new WSprite(Motherlode.id("textures/gui/container/white.png"));
            panel.add(miniGemBottom, 112 + (i * 4 - ((i / 3) * 12)), 59 + ((i / 3) * 4), 1, 1);

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
                if (blockInventory.getStack(i).getItem().isIn(MotherlodeTags.Items.GEMS)) {
                    miniGemsTop.get(i).setImage(Motherlode.id("textures/gui/container/white.png"));
                    miniGemsBottom.get(i).setImage(Motherlode.id("textures/gui/container/white.png"));

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
                    miniGemsTop.get(i).setImage(Motherlode.id("textures/gui/container/empty.png"));
                    miniGemsBottom.get(i).setImage(Motherlode.id("textures/gui/container/empty.png"));
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
                gems.get(i).setImage(Motherlode.id("textures/gui/container/gem.png"));
                miniGemsTop.get(i).setImage(Motherlode.id("textures/gui/container/empty.png"));
                miniGemsBottom.get(i).setImage(Motherlode.id("textures/gui/container/empty.png"));
            } else {
                gems.get(i).setImage(Motherlode.id("textures/gui/container/empty.png"));
                if (blockInventory.getStack(i).getItem().isIn(MotherlodeTags.Items.GEMS)) {
                    miniGemsTop.get(i).setImage(Motherlode.id("textures/gui/container/white.png"));
                    miniGemsBottom.get(i).setImage(Motherlode.id("textures/gui/container/white.png"));

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
                    miniGemsTop.get(i).setImage(Motherlode.id("textures/gui/container/empty.png"));
                    miniGemsBottom.get(i).setImage(Motherlode.id("textures/gui/container/empty.png"));
                }
            }
        }
        return stack;
    }

    @Override
    public void close(PlayerEntity player) {
        if(!world.isClient())
            ((RedstoneTransmitterBlockEntity)world.getBlockEntity(currPos)).update();

        super.close(player);
    }
}
