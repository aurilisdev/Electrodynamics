package electrodynamics.api.capability.types.boolstorage;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class CapabilityBooleanStorage implements IBooleanStorage, ICapabilitySerializable<CompoundTag> {

	public final LazyOptional<IBooleanStorage> holder = LazyOptional.of(() -> this);
	
	public CapabilityBooleanStorage() {
		values = new ArrayList<>();
		values.add(false);
	}
	
	public CapabilityBooleanStorage(int size) {
		values = new ArrayList<>();
		for(int i = 0; i < size; i++) {
			values.add(false);
		}
		
	}

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
			nbt.putInt(ElectrodynamicsCapabilities.BOOLEAN_KEY, values.size());
			for(int i = 0; i < values.size(); i++) {
				nbt.putBoolean(ElectrodynamicsCapabilities.BOOLEAN_KEY + i, values.get(i));
			}
			return nbt;
		}
		return new CompoundTag();
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		if (ElectrodynamicsCapabilities.BOOLEAN_STORAGE_CAPABILITY != null) {
			int size = nbt.getInt(ElectrodynamicsCapabilities.BOOLEAN_KEY);
			values = new ArrayList<>();
			for(int i = 0; i < size; i++) {
				values.add(nbt.getBoolean(ElectrodynamicsCapabilities.BOOLEAN_KEY + i));
			}
		}
	}

	private List<Boolean> values;

	@Override
	public void setBoolean(int index, boolean bool) {
		values.set(index, bool);
	}

	@Override
	public boolean getBoolean(int index) {
		return values.get(index);
	}

}
