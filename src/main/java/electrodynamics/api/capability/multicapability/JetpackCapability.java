package electrodynamics.api.capability.multicapability;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.capability.types.intstorage.CapabilityIntStorage;
import electrodynamics.api.fluid.RestrictedFluidHandlerItemStack;
import electrodynamics.prefab.utilities.CapabilityUtils;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class JetpackCapability implements ICapabilitySerializable<CompoundTag> {

	private RestrictedFluidHandlerItemStack handler;
	private CapabilityIntStorage number;

	public JetpackCapability(RestrictedFluidHandlerItemStack handler, CapabilityIntStorage number) {
		this.handler = handler;
		this.number = number;
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap == CapabilityUtils.getFluidItemCap()) {
			return handler.getCapability(cap, side);
		} else if (cap == ElectrodynamicsCapabilities.INTEGER_STORAGE_CAPABILITY) {
			return number.holder.cast();
		}
		return LazyOptional.empty();
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag nbt = new CompoundTag();
		nbt.put("number", number.serializeNBT());
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		number.deserializeNBT(nbt.getCompound("number"));
	}

}
