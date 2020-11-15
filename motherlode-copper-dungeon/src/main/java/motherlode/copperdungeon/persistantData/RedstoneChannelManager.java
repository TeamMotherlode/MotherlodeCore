package motherlode.core.persistantData;

import motherlode.core.registry.MotherlodeBlocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.PersistentState;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RedstoneChannelManager extends PersistentState {

	private final Map<Integer, Integer> channels = new HashMap<>();
	private final Map<Integer, Boolean> updatedChannels = new HashMap<>();
	private final Map<Integer, List<BlockPos>> receivers = new HashMap<>();
	private final Map<Integer, Map<BlockPos, Integer>> transmitters = new HashMap<>();

	public RedstoneChannelManager() {
		super("motherlode_wireless_channels");
	}

	@Override
	public void fromTag(CompoundTag tag) {
		ListTag channels = tag.getList("channels", 10);

		for(Tag cTag : channels)
			this.channels.put(((CompoundTag)cTag).getInt("id"), ((CompoundTag)cTag).getInt("value"));

		ListTag receiverListTag = tag.getList("receivers", 10);
		receiverListTag.forEach((rTag) -> {
			CompoundTag receiverTag = ((CompoundTag)rTag);
			List<BlockPos> recieverList = receivers.getOrDefault(receiverTag.getInt("channel"), new ArrayList<>());
			recieverList.add(new BlockPos(receiverTag.getInt("x"), receiverTag.getInt("y"), receiverTag.getInt("z")));
			receivers.put(receiverTag.getInt("channel"), recieverList);
		});


		ListTag transmittersListTag = tag.getList("transmitters", 10);
		transmittersListTag.forEach((tTag) -> {
			CompoundTag transmitterTag = ((CompoundTag)tTag);
			Map<BlockPos, Integer> transmittersMap = transmitters.getOrDefault(transmitterTag.getInt("channel"), new HashMap<>());
			transmittersMap.put(new BlockPos(transmitterTag.getInt("x"), transmitterTag.getInt("y"), transmitterTag.getInt("z")), transmitterTag.getInt("value"));
			transmitters.put(transmitterTag.getInt("channel"), transmittersMap);
		});

	}

	@Override
	public CompoundTag toTag(CompoundTag tag) {
		ListTag listTag = new ListTag();
		channels.forEach((key, val) -> {
			CompoundTag channelTag = new CompoundTag();
			channelTag.putInt("id", key);
			channelTag.putInt("value", val);
			listTag.add(channelTag);
		});

		tag.put("channels", listTag);


		ListTag receiverList = new ListTag();
		receivers.forEach((key, channel) -> {
			for(BlockPos receiverData : channel) {
				CompoundTag receiverTag = new CompoundTag();
				receiverTag.putInt("x", receiverData.getX());
				receiverTag.putInt("y", receiverData.getY());
				receiverTag.putInt("z", receiverData.getZ());
				receiverTag.putInt("channel", key);
				receiverList.add(receiverTag);
			}
		});

		tag.put("receivers", receiverList);


		ListTag transmitterList = new ListTag();
		transmitters.forEach((key, channel) ->
				channel.forEach((pos, value) -> {
					CompoundTag transmitterTag = new CompoundTag();
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


		if(value > currChannelVal && updatedChannels.getOrDefault(channel, false)) {
			channels.put(channel, value);
			transmitters.get(channel).put(pos, value);
			updatedChannels.put(channel, true);
		} else if(!updatedChannels.getOrDefault(channel, false)) {
			transmitters.get(channel).put(pos, value);

			if(value > currChannelVal) {
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
		for(int i = 0; i < receivers.get(channel).size(); i++) {
			if(receivers.get(channel).get(i).equals(pos)) {
				receivers.get(channel).remove(i);
				return;
			}
		}
	}

	public void swapReceiver(BlockPos pos, int oldChannel, int newChannel) {
		for(int i = 0; i < receivers.get(oldChannel).size(); i++) {
			if(receivers.get(oldChannel).get(i).equals(pos)) {
				if(!receivers.containsKey(newChannel))
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
		if(transmitters.get(channel).values().size() == 0) {
			channels.remove(channel);
			updatedChannels.put(channel, true);
		}
		else updateChannelValue(channel);
	}

	public void swapTransmitter(BlockPos pos, int oldChannel, int newChannel) {
		if(!transmitters.containsKey(newChannel))
			transmitters.put(newChannel, new HashMap<>());
		transmitters.get(newChannel).put(pos, transmitters.get(oldChannel).remove(pos));
		updateChannelValue(oldChannel);
	}

	// Helper methods
	public void swapType(boolean receiver, int channel, BlockPos pos, int value) {
		markDirty();
		if(receiver) {
			removeReceiver(channel, pos);
			registerTransmitter(channel, pos, value);
		} else {
			removeTransmitter(channel, pos);
			registerReceiver(channel, pos);
		}
	}

	public void swapChannel(boolean receiver, BlockPos pos, int oldChannel, int newChannel) {
		markDirty();
		if(receiver) {
			swapReceiver(pos, oldChannel, newChannel);
		} else {
			swapTransmitter(pos, oldChannel, newChannel);
		}
	}

	public void remove(boolean receiver, int channel, BlockPos pos) {
		markDirty();
		if(receiver) {
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
			if(updatedChannels.getOrDefault(channel, false))
				for (BlockPos receiver : receivers)
					world.getBlockTickScheduler().schedule(receiver, MotherlodeBlocks.REDSTONE_TRANSMITTER, 0);
		});

		updatedChannels.clear();
	}
}