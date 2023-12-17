package electrodynamics.api.capability.types.itemhandler;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.logging.log4j.util.TriConsumer;

public class CapabilityItemStackHandler extends ItemStackHandler implements ICapabilitySerializable<CompoundNBT> {

	private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> this);
	private final ItemStack owner;

	private TriConsumer<ItemStack, CapabilityItemStackHandler, Integer> onChange = (stack, handler, slot) -> {
	};

	public CapabilityItemStackHandler(int size, ItemStack owner) {
		super(size);
		this.owner = owner;
	}

	public CapabilityItemStackHandler setOnChange(TriConsumer<ItemStack, CapabilityItemStackHandler, Integer> onChange) {
		this.onChange = onChange;
		return this;
	}

	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return handler.cast();
		}
		return LazyOptional.empty();
	}

	@Override
	protected void onContentsChanged(int slot) {
		onChange.accept(owner, this, slot);
	}

	public List<ItemStack> getItems() {
		return stacks;
	}

}
