package electrodynamics.api.capability.types.itemhandler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import electrodynamics.prefab.tile.components.type.ComponentInventory;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public class IndexedSidedInvWrapper extends SidedInvWrapper {

	// public methods Forge, that's all I ask for...

	public ComponentInventory component;

	public IndexedSidedInvWrapper(ComponentInventory inv, @Nullable Direction side) {
		super(inv, side);
		component = inv;
	}

	public static LazyOptional<IItemHandlerModifiable>[] create(ComponentInventory inv, Direction... sides) {
		LazyOptional<IItemHandlerModifiable>[] ret = new LazyOptional[sides.length];
		for (int x = 0; x < sides.length; x++) {
			final Direction side = sides[x];
			ret[x] = LazyOptional.of(() -> new IndexedSidedInvWrapper(inv, side));
		}
		return ret;
	}

	@Override
	@NotNull
	public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
		if (stack.isEmpty()) {
			return ItemStack.EMPTY;
		}

		int slot1 = getSlot(component, slot, side);

		if (slot1 == -1) {
			return stack;
		}

		ItemStack stackInSlot = inv.getItem(slot1);

		int m;
		if (!stackInSlot.isEmpty()) {
			if ((stackInSlot.getCount() >= Math.min(stackInSlot.getMaxStackSize(), getSlotLimit(slot))) || !ItemHandlerHelper.canItemStacksStack(stack, stackInSlot) || !component.canPlaceItemThroughFace(slot1, stack, side) || !component.canPlaceItem(slot1, stack)) {
				return stack;
			}

			m = Math.min(stack.getMaxStackSize(), getSlotLimit(slot)) - stackInSlot.getCount();

			if (stack.getCount() <= m) {
				if (!simulate) {
					ItemStack copy = stack.copy();
					copy.grow(stackInSlot.getCount());
					setInventorySlotContents(slot1, copy);
				}

				return ItemStack.EMPTY;
			}
			// copy the stack to not modify the original one
			stack = stack.copy();
			if (!simulate) {
				ItemStack copy = stack.split(m);
				copy.grow(stackInSlot.getCount());
				setInventorySlotContents(slot1, copy);
			} else {
				stack.shrink(m);
			}
			return stack;
		}
		if (!component.canPlaceItemThroughFace(slot1, stack, side) || !component.canPlaceItem(slot1, stack)) {
			return stack;
		}

		m = Math.min(stack.getMaxStackSize(), getSlotLimit(slot));
		if (m < stack.getCount()) {
			// copy the stack to not modify the original one
			stack = stack.copy();
			if (!simulate) {
				setInventorySlotContents(slot1, stack.split(m));
			} else {
				stack.shrink(m);
			}
			return stack;
		}
		if (!simulate) {
			setInventorySlotContents(slot1, stack);
		}
		return ItemStack.EMPTY;

	}

	@Override
	public void setStackInSlot(int slot, @NotNull ItemStack stack) {
		int slot1 = getSlot(component, slot, side);

		if (slot1 != -1) {
			setInventorySlotContents(slot1, stack);
		}
	}

	private void setInventorySlotContents(int slot, ItemStack stack) {
		component.setItem(slot, stack);
	}

	@Override
	@NotNull
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		if (amount == 0) {
			return ItemStack.EMPTY;
		}

		int slot1 = getSlot(component, slot, side);

		if (slot1 == -1) {
			return ItemStack.EMPTY;
		}

		ItemStack stackInSlot = component.getItem(slot1);

		if (stackInSlot.isEmpty() || !inv.canTakeItemThroughFace(slot1, stackInSlot, side)) {
			return ItemStack.EMPTY;
		}

		if (simulate) {
			if (stackInSlot.getCount() < amount) {
				return stackInSlot.copy();
			}
			ItemStack copy = stackInSlot.copy();
			copy.setCount(amount);
			return copy;
		}
		return component.removeItem(slot1, Math.min(stackInSlot.getCount(), amount));
	}

}
