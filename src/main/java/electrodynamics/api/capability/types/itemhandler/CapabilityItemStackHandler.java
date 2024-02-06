package electrodynamics.api.capability.types.itemhandler;

import java.util.List;

import org.apache.logging.log4j.util.TriConsumer;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.items.ItemStackHandler;

public class CapabilityItemStackHandler extends ItemStackHandler implements INBTSerializable<CompoundTag> {

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
	protected void onContentsChanged(int slot) {
		onChange.accept(owner, this, slot);
	}

	public List<ItemStack> getItems() {
		return stacks;
	}

}
