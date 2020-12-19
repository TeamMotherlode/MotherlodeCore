package motherlode.enderinvasion.component;

import net.minecraft.nbt.CompoundTag;
import dev.onyxstudios.cca.api.v3.component.ComponentV3;

public interface EnderInvasionComponent extends ComponentV3 {
    enum State {
        PRE_ECHERITE, ENDER_INVASION, POST_ENDER_DRAGON
    }

    State value();

    void setValue(State state);

    int getInvasionEndTick();

    void setInvasionEndTick(int tick);

    class Impl implements EnderInvasionComponent {

        private State value;
        private int invasionEndTick;

        public Impl(State state) {
            this.value = state;
            this.invasionEndTick = -1;
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
        public int getInvasionEndTick() {
            return this.invasionEndTick;
        }

        @Override
        public void setInvasionEndTick(int tick) {
            this.invasionEndTick = tick;
        }

        @Override
        public void readFromNbt(CompoundTag tag) {
            this.value = State.values()[tag.getInt("value")];
            this.invasionEndTick = tag.contains("invasionEndTick") ? tag.getInt("invasionEndTick") : -1;
        }

        @Override
        public void writeToNbt(CompoundTag tag) {
            tag.putInt("value", value.ordinal());
            if (invasionEndTick != -1) tag.putInt("invasionEndTick", this.invasionEndTick);
        }
    }
}
