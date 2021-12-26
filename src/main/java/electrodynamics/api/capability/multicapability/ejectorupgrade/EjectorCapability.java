package electrodynamics.api.capability.multicapability.ejectorupgrade;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.capability.boolstorage.CapabilityBooleanStorage;
import electrodynamics.api.capability.dirstorage.CapabilityDirectionalStorage;
import electrodynamics.api.capability.intstorage.CapabilityIntStorage;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class EjectorCapability implements ICapabilitySerializable<CompoundTag> {

	private CapabilityBooleanStorage bool;
	private CapabilityIntStorage number;
	private CapabilityDirectionalStorage direction;
	
	public EjectorCapability(CapabilityBooleanStorage bool, CapabilityIntStorage number, CapabilityDirectionalStorage direciton) {
		this.bool = bool;
		this.number = number;
		this.direction = direciton;
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap == ElectrodynamicsCapabilities.BOOLEAN_STORAGE_CAPABILITY) {
			return bool.holder.cast();
		} else if (cap == ElectrodynamicsCapabilities.INTEGER_STORAGE_CAPABILITY) {
			return number.holder.cast();
		} else if (cap == ElectrodynamicsCapabilities.DIR_STORAGE_CAPABILITY) {
			return direction.holder.cast();
		} 
		return LazyOptional.empty();
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag nbt = new CompoundTag();
		nbt.put("bool", bool.serializeNBT());
		nbt.put("int", number.serializeNBT());
		nbt.put("dir", direction.serializeNBT());
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		bool.deserializeNBT((CompoundTag) nbt.get("bool"));
		number.deserializeNBT((CompoundTag) nbt.get("int"));
		direction.deserializeNBT((CompoundTag) nbt.get("dir"));
	}

}
