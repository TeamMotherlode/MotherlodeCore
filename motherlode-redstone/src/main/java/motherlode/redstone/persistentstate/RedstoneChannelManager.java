package motherlode.redstone.persistentstate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.PersistentState;
import net.minecraft.world.World;
import motherlode.redstone.MotherlodeRedstoneBlocks;

public class RedstoneChannelManager extends PersistentState {
    private final Map<Integer, Integer> channels;
    private final Map<Integer, Boolean> updatedChannels;
    private final Map<Integer, List<BlockPos>> receivers;
    private final Map<Integer, Map<BlockPos, Integer>> transmitters;

    public RedstoneChannelManager() {
        this(new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>());
    }

    public RedstoneChannelManager(Map<Integer, Integer> channels, Map<Integer, Boolean> updatedChannels, Map<Integer, List<BlockPos>> receivers, Map<Integer, Map<BlockPos, Integer>> transmitters) {
        this.channels = channels;
        this.updatedChannels = updatedChannels;
        this.receivers = receivers;
        this.transmitters = transmitters;
    }

    public static RedstoneChannelManager fromTag(NbtCompound tag) {
        NbtList channelsTag = tag.getList("channels", 10);

        if (channelsTag == null) return null;

        final Map<Integer, Integer> channels = new HashMap<>();
        final Map<Integer, Boolean> updatedChannels = new HashMap<>();
        final Map<Integer, List<BlockPos>> receivers = new HashMap<>();
        final Map<Integer, Map<BlockPos, Integer>> transmitters = new HashMap<>();

        for (NbtElement cTag : channelsTag)
            channels.put(((NbtCompound) cTag).getInt("id"), ((NbtCompound) cTag).getInt("value"));

        NbtList receiverListTag = tag.getList("receivers", 10);
        receiverListTag.forEach((rTag) -> {
            NbtCompound receiverTag = ((NbtCompound) rTag);
            List<BlockPos> recieverList = receivers.getOrDefault(receiverTag.getInt("channel"), new ArrayList<>());
            recieverList.add(new BlockPos(receiverTag.getInt("x"), receiverTag.getInt("y"), receiverTag.getInt("z")));
            receivers.put(receiverTag.getInt("channel"), recieverList);
        });

        NbtList transmittersListTag = tag.getList("transmitters", 10);
        transmittersListTag.forEach((tTag) -> {
            NbtCompound transmitterTag = ((NbtCompound) tTag);
            Map<BlockPos, Integer> transmittersMap = transmitters.getOrDefault(transmitterTag.getInt("channel"), new HashMap<>());
            transmittersMap.put(new BlockPos(transmitterTag.getInt("x"), transmitterTag.getInt("y"), transmitterTag.getInt("z")), transmitterTag.getInt("value"));
            transmitters.put(transmitterTag.getInt("channel"), transmittersMap);
        });

        return new RedstoneChannelManager(channels, updatedChannels, receivers, transmitters);
    }

    @Override
    public NbtCompound writeNbt(NbtCompound tag) {
        NbtList listTag = new NbtList();
        channels.forEach((key, val) -> {
            NbtCompound channelTag = new NbtCompound();
            channelTag.putInt("id", key);
            channelTag.putInt("value", val);
            listTag.add(channelTag);
        });

        tag.put("channels", listTag);

        NbtList receiverList = new NbtList();
        receivers.forEach((key, channel) -> {
            for (BlockPos receiverData : channel) {
                NbtCompound receiverTag = new NbtCompound();
                receiverTag.putInt("x", receiverData.getX());
                receiverTag.putInt("y", receiverData.getY());
                receiverTag.putInt("z", receiverData.getZ());
                receiverTag.putInt("channel", key);
                receiverList.add(receiverTag);
            }
        });

        tag.put("receivers", receiverList);

        NbtList transmitterList = new NbtList();
        transmitters.forEach((key, channel) ->
            channel.forEach((pos, value) -> {
                NbtCompound transmitterTag = new NbtCompound();
                transmitterTag.putInt("x", pos.getX());
                transmitterTag.putInt("y", pos.getY());
                transmitterTag.putInt("z", pos.getZ());
                transmitterTag.putInt("channel", key);
                transmitterTag.putInt("value", value);
                transmitterList.add(transmitterTag);
            })
        );

        tag.put("transmitters", transmitterList);

        return tag;
    }

    public int getChannelValue(int channel) {
        return channels.getOrDefault(channel, 0);
    }

    public void setChannelValue(int channel, BlockPos pos, int value) {
        markDirty();
        int currChannelVal = channels.getOrDefault(channel, -1);

        if (value > currChannelVal && updatedChannels.getOrDefault(channel, false)) {
            channels.put(channel, value);
            transmitters.get(channel).put(pos, value);
            updatedChannels.put(channel, true);
        } else if (!updatedChannels.getOrDefault(channel, false)) {
            transmitters.get(channel).put(pos, value);

            if (value > currChannelVal) {
                channels.put(channel, value);
                updatedChannels.put(channel, true);
            } else {
                updateChannelValue(channel);
            }
        } else {
            transmitters.get(channel).put(pos, value);
        }
    }

    // Receiver manipulation
    public void registerReceiver(int channel, BlockPos pos) {
        List<BlockPos> receiverData = receivers.getOrDefault(channel, new ArrayList<>());
        receiverData.add(pos);
        receivers.put(channel, receiverData);
    }

    public void removeReceiver(int channel, BlockPos pos) {
        for (int i = 0; i < receivers.get(channel).size(); i++) {
            if (receivers.get(channel).get(i).equals(pos)) {
                receivers.get(channel).remove(i);
                return;
            }
        }
    }

    public void swapReceiver(BlockPos pos, int oldChannel, int newChannel) {
        for (int i = 0; i < receivers.get(oldChannel).size(); i++) {
            if (receivers.get(oldChannel).get(i).equals(pos)) {
                if (!receivers.containsKey(newChannel))
                    receivers.put(newChannel, new ArrayList<>());
                receivers.get(newChannel).add(receivers.get(oldChannel).remove(i));
                return;
            }
        }
    }

    // Transmitter manipulation
    public void registerTransmitter(int channel, BlockPos pos, int value) {
        Map<BlockPos, Integer> transmitterData = transmitters.getOrDefault(channel, new HashMap<>());
        transmitterData.put(pos, value);
        transmitters.put(channel, transmitterData);
    }

    public void removeTransmitter(int channel, BlockPos pos) {
        transmitters.get(channel).remove(pos);
        if (transmitters.get(channel).values().size() == 0) {
            channels.remove(channel);
            updatedChannels.put(channel, true);
        } else updateChannelValue(channel);
    }

    public void swapTransmitter(BlockPos pos, int oldChannel, int newChannel) {
        if (!transmitters.containsKey(newChannel))
            transmitters.put(newChannel, new HashMap<>());
        transmitters.get(newChannel).put(pos, transmitters.get(oldChannel).remove(pos));
        updateChannelValue(oldChannel);
    }

    // Helper methods
    public void swapType(boolean receiver, int channel, BlockPos pos, int value) {
        markDirty();
        if (receiver) {
            removeReceiver(channel, pos);
            registerTransmitter(channel, pos, value);
        } else {
            removeTransmitter(channel, pos);
            registerReceiver(channel, pos);
        }
    }

    public void swapChannel(boolean receiver, BlockPos pos, int oldChannel, int newChannel) {
        markDirty();
        if (receiver) {
            swapReceiver(pos, oldChannel, newChannel);
        } else {
            swapTransmitter(pos, oldChannel, newChannel);
        }
    }

    public void remove(boolean receiver, int channel, BlockPos pos) {
        markDirty();
        if (receiver) {
            removeReceiver(channel, pos);
        } else {
            removeTransmitter(channel, pos);
        }
    }

    private void updateChannelValue(int channel) {
        /*
		 This first tries to get the largest value
		 If that fails (only one transmitter or no transmitters) we just try to get the first value
		 If that fails (no transmitters) we just set the value to zero
		 */
        channels.put(channel, transmitters.get(channel).values().stream().reduce((
            (integer, integer2) -> integer > integer2 ? integer : integer2))
            .orElseGet(
                () -> transmitters.get(channel).values().stream().findFirst()
                    .orElse(0)));
        updatedChannels.put(channel, true);
    }

    public void tick(World world) {
        receivers.forEach((channel, receivers) -> {
            if (updatedChannels.getOrDefault(channel, false))
                for (BlockPos receiver : receivers)
                    world.getBlockTickScheduler().schedule(receiver, MotherlodeRedstoneBlocks.REDSTONE_TRANSMITTER, 0);
        });

        updatedChannels.clear();
    }
}
