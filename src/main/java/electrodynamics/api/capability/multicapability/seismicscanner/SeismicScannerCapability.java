package electrodynamics.api.capability.multicapability.seismicscanner;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.capability.intstorage.CapabilityIntStorage;
import electrodynamics.api.capability.itemhandler.CapabilityItemStackHandler;
import electrodynamics.api.capability.locationstorage.CapabilityLocationStorage;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

public class SeismicScannerCapability implements ICapabilitySerializable<CompoundTag> {

	private CapabilityIntStorage number;
	private CapabilityItemStackHandler handler;
	private CapabilityLocationStorage location;

	public SeismicScannerCapability(CapabilityIntStorage number, CapabilityItemStackHandler handler, CapabilityLocationStorage location) {
		this.number = number;
		this.handler = handler;
		this.location = location;
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap == ElectrodynamicsCapabilities.INTEGER_STORAGE_CAPABILITY) {
			return number.holder.cast();
		} else if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return handler.holder.cast();
		} else if (cap == ElectrodynamicsCapabilities.LOCATION_STORAGE_CAPABILITY) {
			return location.holder.cast();
		}
		return LazyOptional.empty();
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag nbt = new CompoundTag();
		nbt.put("int", number.serializeNBT());
		nbt.put("item", handler.serializeNBT());
		nbt.put("loc", location.serializeNBT());
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		number.deserializeNBT(nbt.getCompound("int"));
		handler.deserializeNBT(nbt.getCompound("item"));
		location.deserializeNBT(nbt.getCompound("loc"));
	}

}
