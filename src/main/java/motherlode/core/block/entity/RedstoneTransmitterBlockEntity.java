package motherlode.core.block.entity;

import motherlode.core.gui.RedstoneTransmitterGuiDescription;
import motherlode.core.inventory.DefaultInventory;
import motherlode.core.item.DefaultGemItem;
import motherlode.core.persistantData.RedstoneChannelManager;
import motherlode.core.registry.MotherlodeBlockEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;

import java.util.Arrays;

public class RedstoneTransmitterBlockEntity extends BlockEntity implements DefaultInventory, BlockEntityClientSerializable, ExtendedScreenHandlerFactory, Tickable {
    private final DefaultedList<ItemStack> stacks = DefaultedList.ofSize(9, ItemStack.EMPTY);
    private boolean receiver = false;
    private boolean updated = false;
    private int channelIDCache = getChannelID();

    public RedstoneTransmitterBlockEntity() {
        super(MotherlodeBlockEntities.REDSTONE_TRANSMITTER);
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }

    @Override
    @Environment(EnvType.CLIENT)
    public ScreenHandler createMenu(int syncId, PlayerInventory inventory, PlayerEntity player) {
        return new RedstoneTransmitterGuiDescription(syncId, inventory, ScreenHandlerContext.create(world, pos));
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return stacks;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        Inventories.fromTag(tag, stacks);
        receiver = tag.getBoolean("transmitter");
    }

    @Override
    public void onClose(PlayerEntity player) {
        updated = true;
    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        fromTag(getCachedState(), tag);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        Inventories.toTag(tag, stacks);
        tag.putBoolean("transmitter", receiver);
        return super.toTag(tag);
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        return toTag(tag);
    }

    @Override
    public void tick() {
        if(world.isClient())
            return;

        RedstoneChannelManager rcm = ((ServerWorld)world).getPersistentStateManager().getOrCreate(RedstoneChannelManager::new, "motherlode_wireless_channels");

        if(!updated)
            return;

        if(channelIDCache != getChannelID()) {
            rcm.swapChannel(receiver, pos, channelIDCache, getChannelID());
            channelIDCache = getChannelID();
        }

        if(!receiver)
            rcm.setChannelValue(getChannelID(), pos, world.getReceivedRedstonePower(pos));
        else
            world.setBlockState(pos, world.getBlockState(pos).with(Properties.POWER, rcm.getChannelValue(getChannelID())));

        updated = false;
    }

    public void update() {
        updated = true;
    }

    public int getChannelID() {
        Integer[] idArr = new Integer[stacks.size()];
        for(int i = 0; i < stacks.size(); i++)
            idArr[i] = DefaultGemItem.GemType.getType(stacks.get(i).getItem()).ordinal() + 1;
        return Arrays.deepHashCode(idArr);
    }

    public void swapTransmitter() {
        receiver = !receiver;
        if(world.isClient())
            return;

        ((ServerWorld)world).getPersistentStateManager().getOrCreate(RedstoneChannelManager::new, "motherlode_wireless_channels")
                .swapType(!receiver, channelIDCache, pos, 0);

        updated = true;
        if(!receiver)
            world.setBlockState(pos, world.getBlockState(pos).with(Properties.POWER, 0));
    }

    public void remove() {
        if(world.isClient())
            return;

        ServerWorld sWorld = (ServerWorld)world;
        RedstoneChannelManager rcm = sWorld.getPersistentStateManager().getOrCreate(RedstoneChannelManager::new, "motherlode_wireless_channels");

        rcm.remove(receiver, channelIDCache, pos);
    }

    public void register() {
        ((ServerWorld)world).getPersistentStateManager().getOrCreate(RedstoneChannelManager::new, "motherlode_wireless_channels").registerTransmitter(getChannelID(), pos, 0);
    }

    public boolean getReceiver() {
        return receiver;
    }
}
