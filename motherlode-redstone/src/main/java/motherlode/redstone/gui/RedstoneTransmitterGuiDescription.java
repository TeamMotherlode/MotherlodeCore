package motherlode.redstone.gui;

import java.util.ArrayList;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.state.property.Properties;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import motherlode.orestoolsarmor.MotherlodeOresToolsArmorTags;
import motherlode.orestoolsarmor.item.DefaultGemItem;
import motherlode.redstone.MotherlodeModule;
import motherlode.redstone.MotherlodeRedstonePackets;
import motherlode.redstone.MotherlodeRedstoneScreenHandlers;
import motherlode.redstone.block.RedstoneTransmitterBlockEntity;
import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import io.netty.buffer.Unpooled;

public class RedstoneTransmitterGuiDescription extends SyncedGuiDescription {
    ArrayList<WSprite> gems = new ArrayList<>();
    ArrayList<WSprite> miniGemsTop = new ArrayList<>();
    ArrayList<WSprite> miniGemsBottom = new ArrayList<>();
    RedstoneTransmitterBlockEntity currEntity;

    public RedstoneTransmitterGuiDescription(int syncId, PlayerInventory inventory, ScreenHandlerContext context) {
        super(MotherlodeRedstoneScreenHandlers.REDSTONE_TRANSMITTER_TYPE, syncId, inventory, SyncedGuiDescription.getBlockInventory(context), SyncedGuiDescription.getBlockPropertyDelegate(context));

        WGridPanel root = new WGridPanel();
        setRootPanel(root);

        WPlainPanel panel = new WPlainPanel();

        WItemSlot grid = WItemSlot.of(blockInventory, 0, 2, 2).setFilter(itemStack -> (!itemStack.isEmpty() && itemStack.isIn(MotherlodeOresToolsArmorTags.GEMS)));
        panel.add(grid, 36, 17);

        WSprite transmitter = new WSprite(MotherlodeModule.id("textures/gui/container/transmitter_disconnected.png"));
        panel.add(transmitter, 109, 17, 15, 54);

        World world = getWorld(context);
        BlockPos pos = getBlockPos(context);

        boolean isOn = world.getBlockState(pos).get(Properties.POWER) > 0;

        currEntity = (RedstoneTransmitterBlockEntity) world.getBlockEntity(pos);

        WSpriteButton transmitButton = new WSpriteButton(MotherlodeModule.id(isOn ? "textures/gui/container/transmitter_signal_on.png" : "textures/gui/container/transmitter_signal_off.png"));
        transmitButton.setFocusedImage(MotherlodeModule.id(isOn ? "textures/gui/container/transmitter_signal_focused_on.png" : "textures/gui/container/transmitter_signal_focused_off.png"));
        if (currEntity.isReceiver()) {
            transmitButton.setImage(getImage(false, isOn, false));
            transmitButton.setFocusedImage(getImage(true, isOn, false));
            transmitButton.setTooltip(new LiteralText("Receiver").asOrderedText());
        } else {
            transmitButton.setImage(getImage(false, isOn, true));
            transmitButton.setFocusedImage(getImage(true, isOn, true));
            transmitButton.setTooltip(new LiteralText("Transmitter").asOrderedText());
        }

        panel.add(transmitButton, 110, 40, 13, 8);

        transmitButton.setOnClick(() -> {
            currEntity.swapTransmitter();

            if (world.isClient()) {
                PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
                buf.writeBlockPos(pos);
                ClientSidePacketRegistry.INSTANCE.sendToServer(MotherlodeRedstonePackets.REDSTONE_TRANSMITTER_SWAP_C2S, buf);
            }

            if (currEntity.isReceiver()) {
                transmitButton.setImage(getImage(false, isOn, false));
                transmitButton.setFocusedImage(getImage(true, isOn, false));
                transmitButton.setTooltip(new LiteralText("Receiver").asOrderedText());
            } else {
                transmitButton.setImage(getImage(false, isOn, true));
                transmitButton.setFocusedImage(getImage(true, isOn, true));
                transmitButton.setTooltip(new LiteralText("Transmitter").asOrderedText());
            }
        });

        for (int i = 0; i < 4; i++) {
            WSprite gem = new WSprite(MotherlodeModule.id("textures/gui/container/gem.png"));
            panel.add(gem, 38 + (i * 18 - ((i / 2) * 36)), 21 + ((i / 2) * 18), 14, 11);
            gems.add(gem);

            WSprite miniGemTop = new WSprite(MotherlodeModule.id("textures/gui/container/white.png"));
            panel.add(miniGemTop, 120 - ((i % 2) * 8), 20 + ((i / 2) * 8), 1, 1);

            WSprite miniGemBottom = new WSprite(MotherlodeModule.id("textures/gui/container/white.png"));
            panel.add(miniGemBottom, 120 - ((i % 2) * 8), 59 + ((i / 2) * 8), 1, 1);

            miniGemsTop.add(miniGemTop);
            miniGemsBottom.add(miniGemBottom);
        }

        for (int i = 0; i < blockInventory.size(); i++) {
            if (blockInventory.getStack(i).isEmpty()) {
                gems.get(i).setImage(MotherlodeModule.id("textures/gui/container/gem.png"));
                miniGemsTop.get(i).setImage(MotherlodeModule.id("textures/gui/container/empty.png"));
                miniGemsBottom.get(i).setImage(MotherlodeModule.id("textures/gui/container/empty.png"));
            } else {
                gems.get(i).setImage(MotherlodeModule.id("textures/gui/container/empty.png"));
                if (blockInventory.getStack(i).isIn(MotherlodeOresToolsArmorTags.GEMS)) {
                    miniGemsTop.get(i).setImage(MotherlodeModule.id("textures/gui/container/white.png"));
                    miniGemsBottom.get(i).setImage(MotherlodeModule.id("textures/gui/container/white.png"));

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
                    miniGemsTop.get(i).setImage(MotherlodeModule.id("textures/gui/container/empty.png"));
                    miniGemsBottom.get(i).setImage(MotherlodeModule.id("textures/gui/container/empty.png"));
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
                gems.get(i).setImage(MotherlodeModule.id("textures/gui/container/gem.png"));
                miniGemsTop.get(i).setImage(MotherlodeModule.id("textures/gui/container/empty.png"));
                miniGemsBottom.get(i).setImage(MotherlodeModule.id("textures/gui/container/empty.png"));
            } else {
                gems.get(i).setImage(MotherlodeModule.id("textures/gui/container/empty.png"));
                if (blockInventory.getStack(i).isIn(MotherlodeOresToolsArmorTags.GEMS)) {
                    miniGemsTop.get(i).setImage(MotherlodeModule.id("textures/gui/container/white.png"));
                    miniGemsBottom.get(i).setImage(MotherlodeModule.id("textures/gui/container/white.png"));

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
                    miniGemsTop.get(i).setImage(MotherlodeModule.id("textures/gui/container/empty.png"));
                    miniGemsBottom.get(i).setImage(MotherlodeModule.id("textures/gui/container/empty.png"));
                }
            }
        }
        return stack;
    }

    @Override
    public void close(PlayerEntity player) {
        if (!world.isClient())
            currEntity.update();

        super.close(player);
    }

    public BlockPos getBlockPos(ScreenHandlerContext ctx) {
        return (BlockPos) ctx.run((world, pos) -> pos).orElse(new BlockPos(0, 0, 0));
    }

    public World getWorld(ScreenHandlerContext ctx) {
        return (World) ctx.run((world, pos) -> world).orElse(null);
    }

    public Identifier getImage(boolean focused, boolean on, boolean up) {
        return MotherlodeModule.id(String.format("textures/gui/container/transmitter_signal_%s%s_%s.png", focused ? "focused_" : "", on ? "on" : "off", up ? "up" : "down"));
    }
}
