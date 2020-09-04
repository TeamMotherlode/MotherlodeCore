package motherlode.core.enderinvasion;

import nerdhub.cardinal.components.api.component.Component;

public interface EnderInvasionComponent extends Component {

    EnderInvasionState value();
    void setValue(EnderInvasionState state);
}