package motherlode.core.enderinvasion;

import net.minecraft.nbt.CompoundTag;

public class EnderInvasionChunkComponentImpl implements EnderInvasionChunkComponent {

    private EnderInvasionChunkState value;

    public EnderInvasionChunkComponentImpl(EnderInvasionChunkState state) {

        this.value = state;
    }
    @Override
    public EnderInvasionChunkState value() {

        return this.value;
    }
    @Override
    public void setValue(EnderInvasionChunkState state) {

        this.value = state;
    }
    @Override
    public void readFromNbt(CompoundTag tag) {

        this.value = EnderInvasionChunkState.values()[tag.getInt("value")];
    }
    @Override
    public void writeToNbt(CompoundTag tag) {

        tag.putInt("value", value.ordinal());
    }
}