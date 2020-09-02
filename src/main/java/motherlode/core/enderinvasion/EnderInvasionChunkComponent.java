package motherlode.core.enderinvasion;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;

public interface EnderInvasionChunkComponent extends ComponentV3 {

    EnderInvasionChunkState value();
    void setValue(EnderInvasionChunkState state);
}