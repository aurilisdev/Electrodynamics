package electrodynamics.api.capability.multicapability;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.capability.types.intstorage.CapabilityIntStorage;
import electrodynamics.api.capability.types.itemhandler.CapabilityItemStackHandler;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

public class SeismicScannerCapability implements ICapabilitySerializable<CompoundTag> {

	private CapabilityIntStorage number;
	private CapabilityItemStackHandler handler;

	public SeismicScannerCapability(CapabilityIntStorage number, CapabilityItemStackHandler handler) {
		this.number = number;
		this.handler = handler;
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap == ElectrodynamicsCapabilities.INTEGER_STORAGE_CAPABILITY) {
			return number.holder.cast();
		} else if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return handler.holder.cast();
		}
		return LazyOptional.empty();
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag nbt = new CompoundTag();
		nbt.put("int", number.serializeNBT());
		nbt.put("item", handler.serializeNBT());
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		number.deserializeNBT(nbt.getCompound("int"));
		handler.deserializeNBT(nbt.getCompound("item"));
	}
	
	public static CompoundTag saveToClientNBT(ItemStack stack) {
		CompoundTag tag = new CompoundTag();
		tag.put("int", CapabilityIntStorage.saveToClientNBT(stack));
		return tag;
	}
	
	public static void readFromClientNBT(CompoundTag tag, ItemStack stack) {
		CapabilityIntStorage.readFromClientNBT(tag.getCompound("int"), stack);
	}

}
