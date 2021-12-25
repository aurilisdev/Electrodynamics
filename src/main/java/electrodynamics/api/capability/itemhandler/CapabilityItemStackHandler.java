package electrodynamics.api.capability.itemhandler;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class CapabilityItemStackHandler extends ItemStackHandler implements ICapabilitySerializable<CompoundTag> {

	private final LazyOptional<IItemHandler> lazyOptional = LazyOptional.of(() -> this);
	private Class<?>[] invalidClasses;

	public CapabilityItemStackHandler(Class<?>... classes) {
		this(1);
		invalidClasses = classes;
	}

	public CapabilityItemStackHandler(int slotCount, Class<?>... classes) {
		super(slotCount);
		invalidClasses = classes;
	}

	public void invalidate() {
		lazyOptional.invalidate();
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return lazyOptional.cast();
		}
		return LazyOptional.empty();
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		super.deserializeNBT(nbt);
	}

	@Override
	public CompoundTag serializeNBT() {
		return super.serializeNBT();
	}

	@Override
	public boolean isItemValid(int slot, ItemStack stack) {
		Item item = stack.getItem();
		for (Class<?> clazz : invalidClasses) {
			if (clazz.isInstance(item)) {
				return false;
			}
		}
		return super.isItemValid(slot, stack);
	}

}
