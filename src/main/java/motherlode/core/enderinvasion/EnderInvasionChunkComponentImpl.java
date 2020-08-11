package motherlode.core.enderinvasion;

import motherlode.core.Motherlode;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.component.extension.CopyableComponent;
import net.minecraft.nbt.CompoundTag;

public class EnderInvasionChunkComponentImpl implements EnderInvasionChunkComponent, CopyableComponent<EnderInvasionChunkComponent> {

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
    public void fromTag(CompoundTag tag) {

        this.value = EnderInvasionChunkState.values()[tag.getInt("value")];
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

        return Motherlode.ENDER_INVASION_CHUNK_STATE;
    }
}