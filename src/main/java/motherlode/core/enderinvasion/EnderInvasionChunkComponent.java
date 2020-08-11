package motherlode.core.enderinvasion;

import nerdhub.cardinal.components.api.component.Component;

public interface EnderInvasionChunkComponent extends Component {

    EnderInvasionChunkState value();
    void setValue(EnderInvasionChunkState state);
}