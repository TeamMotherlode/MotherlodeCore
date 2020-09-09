package motherlode.core.enderinvasion;

import nerdhub.cardinal.components.api.component.Component;
import net.minecraft.nbt.CompoundTag;

public interface EnderInvasionComponent extends Component {
    enum State {
        PRE_ECHERITE, ENDER_INVASION, POST_ENDER_DRAGON
    }

    State value();
    void setValue(State state);

    class Impl implements EnderInvasionComponent {

        private State value;

        public Impl(State state) {
            this.value = state;
        }
        @Override
        public State value() {
            return this.value;
        }
        @Override
        public void setValue(State state) {
            this.value = state;
        }
        @Override
        public void fromTag(CompoundTag tag) {
            this.value = State.values()[tag.getInt("value")];
        }
        @Override
        public CompoundTag toTag(CompoundTag tag) {
            tag.putInt("value", value.ordinal());
            return tag;
        }
    }
}