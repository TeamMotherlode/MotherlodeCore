package motherlode.core.enderinvasion;

import net.minecraft.nbt.CompoundTag;

public class EnderInvasionComponentImpl implements EnderInvasionComponent {

    private EnderInvasionState value;

    public EnderInvasionComponentImpl(EnderInvasionState state) {

        this.value = state;
    }
    @Override
    public EnderInvasionState value() {

        return this.value;
    }
    @Override
    public void setValue(EnderInvasionState state) {

        this.value = state;
    }
    @Override
    public void fromTag(CompoundTag tag) {

        this.value = EnderInvasionState.values()[tag.getInt("value")];
    }
    @Override
    public CompoundTag toTag(CompoundTag tag) {

        tag.putInt("value", value.ordinal());
        return tag;
    }
}