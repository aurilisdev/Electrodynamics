package electrodynamics.api.capability.types.boolstorage;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class CapabilityBooleanStorage implements IBooleanStorage, ICapabilitySerializable<CompoundTag> {

	public final LazyOptional<IBooleanStorage> holder = LazyOptional.of(() -> this);

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap == ElectrodynamicsCapabilities.BOOLEAN_STORAGE_CAPABILITY) {
			return holder.cast();
		}
		return LazyOptional.empty();
	}

	@Override
	public CompoundTag serializeNBT() {
		if (ElectrodynamicsCapabilities.BOOLEAN_STORAGE_CAPABILITY != null) {
			CompoundTag nbt = new CompoundTag();
			nbt.putBoolean(ElectrodynamicsCapabilities.BOOLEAN_KEY, bool);
			return nbt;
		}
		return new CompoundTag();
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		if (ElectrodynamicsCapabilities.BOOLEAN_STORAGE_CAPABILITY != null) {
			bool = nbt.getBoolean(ElectrodynamicsCapabilities.BOOLEAN_KEY);
		}
	}

	private boolean bool = false;

	@Override
	public void setBoolean(boolean bool) {
		this.bool = bool;
	}

	@Override
	public boolean getBoolean() {
		return bool;
	}

}
