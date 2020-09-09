package motherlode.core.enderinvasion;

import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.component.Component;
import nerdhub.cardinal.components.api.component.extension.CopyableComponent;
import net.minecraft.nbt.CompoundTag;

public interface EnderInvasionChunkComponent extends Component {
    enum State {
        PRE_ECHERITE, GENERATION_DONE
    }

    State value();
    void setValue(State state);

    class Impl implements EnderInvasionChunkComponent, CopyableComponent<EnderInvasionChunkComponent> {

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
        @Override
        public void copyFrom(EnderInvasionChunkComponent other) {
            this.value = other.value();
        }
        @Override
        public ComponentType<?> getComponentType() {
            return EnderInvasion.CHUNK_STATE;
        }
    }
}