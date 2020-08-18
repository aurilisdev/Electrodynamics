package electrodynamics.common.inventory.container;

import electrodynamics.common.inventory.container.slot.GenericSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class GenericContainerInventory extends Container {
	protected final IInventory inventory;
	protected final IIntArray inventorydata;
	protected final World world;
	protected final int slotCount;
	private int nextIndex = 0;

	public int nextIndex() {
		return nextIndex++;
	}

	protected GenericContainerInventory(ContainerType<?> type, int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
		super(type, id);
		assertInventorySize(inventory, inventory.getSizeInventory());
		assertIntArraySize(inventorydata, inventorydata.size());
		this.inventory = inventory;
		this.inventorydata = inventorydata;
		world = playerinv.player.world;
		addInventorySlots(inventory, playerinv);
		slotCount = inventorySlots.size();
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlot(new GenericSlot(playerinv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k) {
			addSlot(new GenericSlot(playerinv, k, 8 + k * 18, 142));
		}

		trackIntArray(inventorydata);
	}

	public abstract void addInventorySlots(IInventory inv, PlayerInventory playerinv);

	public void clear() {
		inventory.clear();
	}

	@OnlyIn(Dist.CLIENT)
	public int getSize() {
		return inventory.getSizeInventory();
	}

	public IInventory getIInventory() {
		return inventory;
	}

	@Override
	public boolean canInteractWith(PlayerEntity player) {
		return inventory.isUsableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity player, int index) {
		Slot slot = inventorySlots.get(index);

		if (slot != null && slot.getStack() != null && !slot.getStack().isEmpty()) {
			ItemStack itemStack = slot.getStack();
			ItemStack originalStack = itemStack.copy();

			if (index < slotCount) {
				if (!mergeItemStack(itemStack, slotCount, inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!mergeItemStack(itemStack, 0, slotCount, false)) {
				return ItemStack.EMPTY;
			}

			if (itemStack.getCount() == 0) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}

			return originalStack;
		}
		return ItemStack.EMPTY;
	}

	@Override
	protected boolean mergeItemStack(ItemStack stack, int min, int max, boolean negative) {
		boolean flag1 = false;
		int k = negative ? max - 1 : min;
		Slot slot;
		ItemStack itemstack1;
		if (stack.isStackable()) {
			while (stack.getCount() > 0 && (!negative && k < max || negative && k >= min)) {
				slot = inventorySlots.get(k);
				itemstack1 = slot.getStack();

				if (itemstack1 != null && !itemstack1.isEmpty() && itemstack1.getItem() == stack.getItem() && stack.getDamage() == itemstack1.getDamage() && ItemStack.areItemStackTagsEqual(stack, itemstack1)) {

					int l = itemstack1.getCount() + stack.getCount();

					if (l <= stack.getMaxStackSize()) {
						stack.setCount(0);
						itemstack1.setCount(l);
						slot.onSlotChanged();
						flag1 = true;
					} else if (itemstack1.getCount() < stack.getMaxStackSize()) {
						stack.setCount(stack.getCount() - (stack.getMaxStackSize() - itemstack1.getCount()));
						itemstack1.setCount(stack.getMaxStackSize());
						slot.onSlotChanged();
						flag1 = true;
					}
				}

				if (negative) {
					--k;
				} else {
					++k;
				}
			}
		}

		if (stack.getCount() > 0) {
			k = negative ? max - 1 : min;
			while (!negative && k < max || negative && k >= min) {
				slot = inventorySlots.get(k);
				itemstack1 = slot.getStack();

				if (itemstack1 == null || itemstack1.isEmpty()) {
					if (slot.isItemValid(stack)) {
						slot.putStack(stack.copy());
						slot.onSlotChanged();
						stack.setCount(0);
						flag1 = true;
						break;
					}
				}

				if (negative) {
					--k;
				} else {
					++k;
				}
			}
		}
		return flag1;
	}

	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
		detectAndSendChanges();
		return super.slotClick(slotId, dragType, clickTypeIn, player);
	}

	@Override
	public void onContainerClosed(PlayerEntity player) {
		super.onContainerClosed(player);
		inventory.closeInventory(player);
	}
}