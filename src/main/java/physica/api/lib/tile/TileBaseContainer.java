package physica.api.lib.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public abstract class TileBaseContainer extends TileBaseRotateable implements ISidedInventory {
	protected static final int[]	ACCESSIBLE_SLOTS_NONE	= new int[] {};
	private ItemStack[]				_inventoryArray;

	public ItemStack[] getInventoryArray() {
		if (_inventoryArray == null)
		{
			_inventoryArray = new ItemStack[getSizeInventory()];
		}
		return _inventoryArray;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return getInventoryArray()[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if (getInventoryArray()[slot] != null)
		{
			ItemStack itemstack;

			if (getInventoryArray()[slot].stackSize <= amount)
			{
				itemstack = getInventoryArray()[slot];
				getInventoryArray()[slot] = null;
				markDirty();
				return itemstack;
			} else
			{
				itemstack = getInventoryArray()[slot].splitStack(amount);

				if (getInventoryArray()[slot].stackSize == 0)
				{
					getInventoryArray()[slot] = null;
				}

				markDirty();
				return itemstack;
			}
		} else return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (getInventoryArray()[slot] != null)
		{
			ItemStack itemstack = getInventoryArray()[slot];
			getInventoryArray()[slot] = null;
			return itemstack;
		} else return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		ItemStack prev = getStackInSlot(slot);

		getInventoryArray()[slot] = stack;

		if (stack != null && stack.stackSize > getInventoryStackLimit())
		{
			stack.stackSize = getInventoryStackLimit();
		}

		if (!ItemStack.areItemStacksEqual(prev, stack) || !ItemStack.areItemStackTagsEqual(prev, stack))
		{
			onSlotStackChanged(prev, stack, slot);
		}
	}

	protected void onSlotStackChanged(ItemStack prev, ItemStack stack, int slot) {
		markDirty();
	}

	@Override
	public String getInventoryName() {
		return "container.unified.base";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		_inventoryArray = null;

		for (int index = 0; index < nbttaglist.tagCount(); ++index)
		{
			NBTTagCompound save = nbttaglist.getCompoundTagAt(index);
			int slotIndex = save.getByte("Slot") & 255;

			if (slotIndex >= 0 && slotIndex < getInventoryArray().length)
			{
				getInventoryArray()[slotIndex] = ItemStack.loadItemStackFromNBT(save);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		NBTTagList nbttaglist = new NBTTagList();

		for (int slotIndex = 0; slotIndex < getInventoryArray().length; ++slotIndex)
		{
			if (getInventoryArray()[slotIndex] != null)
			{
				NBTTagCompound save = new NBTTagCompound();
				save.setByte("Slot", (byte) slotIndex);
				getInventoryArray()[slotIndex].writeToNBT(save);
				nbttaglist.appendTag(save);
			}
		}

		nbt.setTag("Items", nbttaglist);
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) != this ? false : player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return false;
	}

}
