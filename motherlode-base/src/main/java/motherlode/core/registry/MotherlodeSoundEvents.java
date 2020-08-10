package motherlode.core.registry;

import motherlode.core.Motherlode;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MotherlodeSoundEvents {

    private static SoundEvent register(String name) {
        Identifier id = Motherlode.id(name);
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
    }

}
