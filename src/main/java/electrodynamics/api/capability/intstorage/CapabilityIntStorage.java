package electrodynamics.api.capability.intstorage;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class CapabilityIntStorage implements IIntStorage, ICapabilitySerializable<CompoundTag> {

	public final LazyOptional<IIntStorage> holder = LazyOptional.of(() -> this);
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if(cap == ElectrodynamicsCapabilities.INTEGER_STORAGE_CAPABILITY) {
			return holder.cast();
		}
		return LazyOptional.empty();
	}

	@Override
	public CompoundTag serializeNBT() {
		if(ElectrodynamicsCapabilities.INTEGER_STORAGE_CAPABILITY != null) {
			CompoundTag nbt = new CompoundTag();
			nbt.putInt(ElectrodynamicsCapabilities.INT_KEY, number);
			return nbt;
		}
		return new CompoundTag();
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		if(ElectrodynamicsCapabilities.BOOLEAN_STORAGE_CAPABILITY != null) {
			number = nbt.getInt(ElectrodynamicsCapabilities.INT_KEY);
		}
	}

	private int number = 0;
	
	@Override
	public void setInt(int number) {
		this.number = number;
	}

	@Override
	public int getInt() {
		return number;
	}

}
