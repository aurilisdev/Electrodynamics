package electrodynamics.api.capability.types.doublestorage;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class CapabilityDoubleStorage implements IDoubleStorage, ICapabilitySerializable<CompoundTag> {

	public final LazyOptional<IDoubleStorage> holder = LazyOptional.of(() -> this);

	public CapabilityDoubleStorage(int size) {
		values = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			values.add(0.0);
		}
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap == ElectrodynamicsCapabilities.DIR_STORAGE_CAPABILITY) {
			return holder.cast();
		}
		return LazyOptional.empty();
	}

	@Override
	public CompoundTag serializeNBT() {
		if (ElectrodynamicsCapabilities.DOUBLE_STORAGE_CAPABILITY != null) {
			CompoundTag nbt = new CompoundTag();
			nbt.putInt(ElectrodynamicsCapabilities.DOUBLE_KEY, values.size());
			for (int i = 0; i < values.size(); i++) {
				nbt.putDouble(ElectrodynamicsCapabilities.DOUBLE_KEY + i, values.get(i));
			}
			return nbt;
		}
		return new CompoundTag();
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		if (ElectrodynamicsCapabilities.DOUBLE_STORAGE_CAPABILITY != null) {
			int size = nbt.getInt(ElectrodynamicsCapabilities.DOUBLE_KEY);
			values = new ArrayList<>();
			for (int i = 0; i < size; i++) {
				values.add(nbt.getDouble(ElectrodynamicsCapabilities.DOUBLE_KEY + i));
			}
		}
	}

	private List<Double> values;

	@Override
	public void setDouble(int index, double value) {
		values.set(index, value);
	}

	@Override
	public double getDouble(int index) {
		return values.get(index);
	}

}
