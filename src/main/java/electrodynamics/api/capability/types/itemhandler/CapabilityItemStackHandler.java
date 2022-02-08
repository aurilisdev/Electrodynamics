package electrodynamics.api.capability.types.itemhandler;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class CapabilityItemStackHandler implements ICapabilitySerializable<CompoundTag> {

	private ItemStackHandler handler;
	public final LazyOptional<IItemHandler> holder = LazyOptional.of(this::getHandler);

	public CapabilityItemStackHandler(int slotCount) {
		handler = new ItemStackHandler(slotCount);
	}

	public ItemStackHandler getHandler() {
		return handler;
	}

	public void invalidate() {
		holder.invalidate();
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return holder.cast();
		}
		return LazyOptional.empty();
	}

	@Override
	public CompoundTag serializeNBT() {
		return handler.serializeNBT();
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		handler.deserializeNBT(nbt);
	}

}
