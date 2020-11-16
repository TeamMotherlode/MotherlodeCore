package motherlode.redstone.block;

import java.util.Arrays;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import motherlode.orestoolsarmor.MotherlodeOresToolsArmorItems;
import motherlode.redstone.DefaultInventory;
import motherlode.redstone.MotherlodeModule;
import motherlode.redstone.MotherlodeRedstoneBlockEntities;
import motherlode.redstone.MotherlodeRedstoneBlocks;
import motherlode.redstone.gui.RedstoneTransmitterGuiDescription;
import motherlode.redstone.persistentstate.RedstoneChannelManager;
import org.apache.logging.log4j.Level;

public class RedstoneTransmitterBlockEntity extends BlockEntity implements DefaultInventory, BlockEntityClientSerializable, ExtendedScreenHandlerFactory {
    private final DefaultedList<ItemStack> stacks = DefaultedList.ofSize(4, ItemStack.EMPTY);
    private boolean receiver = false;
    private boolean updated = false;
    private int channelIDCache = getChannelID();

    public RedstoneTransmitterBlockEntity(BlockPos pos, BlockState state) {
        super(MotherlodeRedstoneBlockEntities.REDSTONE_TRANSMITTER, pos, state);
    }

    public <T extends BlockEntity> RedstoneTransmitterBlockEntity(BlockEntityType<T> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
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
    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);
        Inventories.fromTag(tag, stacks);
        receiver = tag.getBoolean("transmitter");
        channelIDCache = tag.getInt("channelCache");
    }

    @Override
    public void onClose(PlayerEntity player) {
        updated = true;
    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        fromTag(tag);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        Inventories.toTag(tag, stacks);
        tag.putBoolean("transmitter", receiver);
        tag.putInt("channelCache", channelIDCache);
        return super.toTag(tag);
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        return toTag(tag);
    }

    public static void tick(World world, BlockPos pos, BlockState state, RedstoneTransmitterBlockEntity blockEntity) {
        if (world.isClient() || !blockEntity.updated)
            return;

        RedstoneChannelManager rcm = ((ServerWorld) world).getPersistentStateManager().getOrCreate(RedstoneChannelManager::fromTag, RedstoneChannelManager::new, "motherlode_wireless_channels");

        if (blockEntity.channelIDCache != blockEntity.getChannelID()) {
            rcm.swapChannel(blockEntity.receiver, pos, blockEntity.channelIDCache, blockEntity.getChannelID());
            blockEntity.channelIDCache = blockEntity.getChannelID();
        }

        if (!blockEntity.receiver && world.isReceivingRedstonePower(pos)) {
            int maxVal = -1;
            for (Direction d : Direction.values()) {
                MotherlodeModule.log(Level.INFO, maxVal + "  " + d);
                if (!world.getBlockState(pos.offset(d)).isOf(MotherlodeRedstoneBlocks.REDSTONE_TRANSMITTER)) {
                    if (world.getEmittedRedstonePower(pos.offset(d), d) > maxVal) {
                        maxVal = world.getEmittedRedstonePower(pos.offset(d), d);
                    }
                }
            }
            rcm.setChannelValue(blockEntity.getChannelID(), pos, maxVal);
        } else if (!blockEntity.receiver)
            rcm.setChannelValue(blockEntity.getChannelID(), pos, 0);

        world.setBlockState(pos, world.getBlockState(pos).with(Properties.POWER, rcm.getChannelValue(blockEntity.getChannelID())));
        blockEntity.updated = false;
    }

    public void update() {
        updated = true;
    }

    public int getChannelID() {
        Integer[] idArr = new Integer[stacks.size()];
        for (int i = 0; i < stacks.size(); i++)
            idArr[i] = getGemValue(stacks.get(i).getItem());
        return Arrays.deepHashCode(idArr);
    }

    private int getGemValue(Item item) {
        if (item.equals(MotherlodeOresToolsArmorItems.AMETHYST)) return 1;
        if (item.equals(MotherlodeOresToolsArmorItems.HOWLITE)) return 2;
        if (item.equals(MotherlodeOresToolsArmorItems.RUBY)) return 3;
        if (item.equals(MotherlodeOresToolsArmorItems.SAPPHIRE)) return 4;
        if (item.equals(MotherlodeOresToolsArmorItems.TOPAZ)) return 5;
        if (item.equals(MotherlodeOresToolsArmorItems.ONYX)) return 6;
        if (item.equals(Items.DIAMOND)) return 7;
        if (item.equals(Items.EMERALD)) return 8;
        return 0;
    }

    public void swapTransmitter() {
        receiver = !receiver;
        if (world.isClient())
            return;

        ((ServerWorld) world).getPersistentStateManager().getOrCreate(RedstoneChannelManager::fromTag, RedstoneChannelManager::new, "motherlode_wireless_channels")
            .swapType(!receiver, channelIDCache, pos, 0);

        updated = true;
        world.setBlockState(pos, world.getBlockState(pos).with(RedstoneTransmitterBlock.RECEIVER, receiver));
    }

    public void remove() {
        if (world.isClient())
            return;

        ServerWorld sWorld = (ServerWorld) world;
        RedstoneChannelManager rcm = sWorld.getPersistentStateManager().getOrCreate(RedstoneChannelManager::fromTag, RedstoneChannelManager::new, "motherlode_wireless_channels");

        rcm.remove(receiver, channelIDCache, pos);
    }

    public void register() {
        ((ServerWorld) world).getPersistentStateManager().getOrCreate(RedstoneChannelManager::fromTag, RedstoneChannelManager::new, "motherlode_wireless_channels").registerTransmitter(getChannelID(), pos, 0);
    }

    public boolean isReceiver() {
        return receiver;
    }
}
