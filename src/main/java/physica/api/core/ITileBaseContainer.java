package physica.api.core;

import java.util.List;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public interface ITileBaseContainer extends ITileBase, ISidedInventory {

	int[] ACCESSIBLE_SLOTS_NONE = new int[] {};

	@Override
	default ItemStack getStackInSlot(int slot)
	{
		return getInventoryArray()[slot];
	}

	@Override
	default ItemStack decrStackSize(int slot, int amount)
	{
		if (getInventoryArray()[slot] != null)
		{
			ItemStack itemstack;
			if (getInventoryArray()[slot].stackSize <= amount)
			{
				itemstack = getInventoryArray()[slot];
				getInventoryArray()[slot] = null;
				markDirty();
				onInventoryChanged();
				return itemstack;
			} else
			{
				itemstack = getInventoryArray()[slot].splitStack(amount);
				if (getInventoryArray()[slot].stackSize == 0)
				{
					getInventoryArray()[slot] = null;
				}
				markDirty();
				onInventoryChanged();
				return itemstack;
			}
		} else
		{
			return null;
		}
	}

	@Override
	default ItemStack getStackInSlotOnClosing(int slot)
	{
		if (getInventoryArray()[slot] != null)
		{
			ItemStack itemstack = getInventoryArray()[slot];
			getInventoryArray()[slot] = null;
			onInventoryChanged();
			return itemstack;
		} else
		{
			return null;
		}
	}

	@Override
	default void setInventorySlotContents(int slot, ItemStack stack)
	{
		getInventoryArray()[slot] = stack;
		onInventoryChanged();

		if (stack != null && stack.stackSize > getInventoryStackLimit())
		{
			stack.stackSize = getInventoryStackLimit();
		}
	}

	default void onInventoryChanged()
	{
		markDirty();
	}

	@Override
	default String getInventoryName()
	{
		return "container.physica.base";
	}

	@Override
	default boolean hasCustomInventoryName()
	{
		return false;
	}

	@Override
	default void handleReadFromNBT(NBTTagCompound nbt)
	{
		ITileBase.super.handleReadFromNBT(nbt);
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		nullifyInventoryArray();
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
	default void handleWriteToNBT(NBTTagCompound nbt)
	{
		ITileBase.super.handleWriteToNBT(nbt);
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
	default void readSynchronizationPacket(ByteBuf buf, EntityPlayer player)
	{
		ITileBase.super.readSynchronizationPacket(buf, player);
		readClientGuiPacket(buf, player);
		handleReadFromNBT(ByteBufUtils.readTag(buf));
	}

	@Override
	default void writeSynchronizationPacket(List<Object> dataList, EntityPlayer player)
	{
		ITileBase.super.writeSynchronizationPacket(dataList, player);
		writeClientGuiPacket(dataList, player);
		NBTTagCompound tag = new NBTTagCompound();
		handleWriteToNBT(tag);
		dataList.add(tag);
	}

	@Override
	default int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	default boolean isUseableByPlayer(EntityPlayer player)
	{
		return This().getWorldObj().getTileEntity(This().xCoord, This().yCoord, This().zCoord) != this ? false : player.getDistanceSq(This().xCoord + 0.5D, This().yCoord + 0.5D, This().zCoord + 0.5D) <= 64.0D;
	}

	@Override
	default void openInventory()
	{
	}

	@Override
	default void closeInventory()
	{
	}

	@Override
	default boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		return false;
	}

	abstract ItemStack[] getInventoryArray();

	abstract void nullifyInventoryArray();
}
