package electrodynamics.api.capability.types.intstorage;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class CapabilityIntStorage implements IIntStorage, ICapabilitySerializable<CompoundTag> {

	public final LazyOptional<IIntStorage> holder = LazyOptional.of(() -> this);
	
	public CapabilityIntStorage(int size) {
		numbers = new ArrayList<>();
		for(int i = 0; i < size; i++) {
			numbers.add(0);
		}
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap == ElectrodynamicsCapabilities.INTEGER_STORAGE_CAPABILITY) {
			return holder.cast();
		}
		return LazyOptional.empty();
	}

	@Override
	public CompoundTag serializeNBT() {
		if (ElectrodynamicsCapabilities.INTEGER_STORAGE_CAPABILITY != null) {
			CompoundTag nbt = new CompoundTag();
			nbt.putInt(ElectrodynamicsCapabilities.INT_KEY, numbers.size());
			for(int i = 0; i < numbers.size(); i++) {
				nbt.putInt(ElectrodynamicsCapabilities.INT_KEY + i, numbers.get(i));
			}
			return nbt;
		}
		return new CompoundTag();
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		if (ElectrodynamicsCapabilities.BOOLEAN_STORAGE_CAPABILITY != null) {
			int size = nbt.getInt(ElectrodynamicsCapabilities.INT_KEY);
			numbers = new ArrayList<>();
			for(int i = 0; i < size; i++) {
				numbers.add(nbt.getInt(ElectrodynamicsCapabilities.INT_KEY + i));
			}
		}
	}

	private List<Integer> numbers;

	@Override
	public void setInt(int index, int number) {
		numbers.set(index, number);
	}

	@Override
	public int getInt(int index) {
		return numbers.get(index);
	}

}
