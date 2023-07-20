package electrodynamics.api.capability.types.itemhandler;

import java.util.List;

import org.apache.logging.log4j.util.TriConsumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class CapabilityItemStackHandler extends ItemStackHandler implements ICapabilitySerializable<CompoundTag> {

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
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		if (cap == ForgeCapabilities.ITEM_HANDLER) {
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
