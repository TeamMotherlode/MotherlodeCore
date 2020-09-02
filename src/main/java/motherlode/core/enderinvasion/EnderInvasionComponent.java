package motherlode.core.enderinvasion;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;

public interface EnderInvasionComponent extends ComponentV3 {

    EnderInvasionState value();
    void setValue(EnderInvasionState state);
}