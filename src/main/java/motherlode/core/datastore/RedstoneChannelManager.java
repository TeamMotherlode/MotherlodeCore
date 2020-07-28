package motherlode.core.datastore;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.PersistentState;

import java.util.HashMap;
import java.util.Map;

public class RedstoneChannelManager extends PersistentState {

	private final Map<Integer, Integer> channels = new HashMap<>();
	private final Map<Integer, Boolean> updatedChannel = new HashMap<>();

	public RedstoneChannelManager() {
		super("motherlode_wireless_channels");
	}

	@Override
	public void fromTag(CompoundTag tag) {
		ListTag channels = tag.getList("channels", 3);

		for(int i = 0; i < channels.size(); i+=2)
			this.channels.put(channels.getInt(i), channels.getInt(i+1));
	}

	@Override
	public CompoundTag toTag(CompoundTag tag) {
		ListTag listTag = new ListTag();
		channels.forEach((key, val) -> {
			listTag.add(IntTag.of(key));
			listTag.add(IntTag.of(val));
		});

		tag.put("channels", listTag);

		return tag;
	}

	public int getChannelValue(int channel) {
		return channels.getOrDefault(channel, 0);
	}

	public void setChannelValue(int channel, int value) {
		if(updatedChannel.getOrDefault(channel, false) && channels.getOrDefault(channel, -1) < value) {
			channels.put(channel, value);
		} else if(!updatedChannel.getOrDefault(channel, false)) {
			updatedChannel.put(channel, true);
			channels.put(channel, value);
		}
	}

	public void tick() {
		updatedChannel.clear();
	}
}
