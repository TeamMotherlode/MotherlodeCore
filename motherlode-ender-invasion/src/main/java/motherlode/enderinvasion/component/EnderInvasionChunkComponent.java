package motherlode.enderinvasion.component;

import net.minecraft.nbt.CompoundTag;
import motherlode.enderinvasion.EnderInvasion;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.CopyableComponent;

public interface EnderInvasionChunkComponent extends ComponentV3, CopyableComponent<EnderInvasionChunkComponent> {
    enum State {
        UNAFFECTED, PRE_ECHERITE, ENDER_INVASION
    }

    State value();

    void setValue(State state);

    class Impl implements EnderInvasionChunkComponent {

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
        public void readFromNbt(CompoundTag tag) {
            this.value = State.values()[tag.getInt("value")];
        }

        @Override
        public void writeToNbt(CompoundTag tag) {
            tag.putInt("value", value.ordinal());
        }

        public ComponentKey<?> getComponentKey() {
            return EnderInvasion.CHUNK_STATE;
        }

        @Override
        public void copyFrom(EnderInvasionChunkComponent other) {
            this.setValue(other.value());
        }
    }
}
