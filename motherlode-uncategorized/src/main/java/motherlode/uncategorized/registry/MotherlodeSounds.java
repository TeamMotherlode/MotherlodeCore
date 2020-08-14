package motherlode.uncategorized.registry;

import motherlode.uncategorized.Motherlode;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.registry.Registry;

public class MotherlodeSounds {
	public static void init() {
		// CALLED TO MAINTAIN REGISTRY ORDER
	}

	private static SoundEvent register(String name) {
		return Registry.register(Registry.SOUND_EVENT, Motherlode.id(name), new SoundEvent(Motherlode.id(name)));
	}
}
