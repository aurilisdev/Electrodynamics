package physica.core.common.utility;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;

public class InventoryUtility {
	public static void incrStackSize(IItemHandlerModifiable itemHandler, int slot) {
		ItemStack itemStack = itemHandler.getStackInSlot(slot);
		itemHandler.insertItem(slot, ItemHandlerHelper.copyStackWithSize(itemStack, itemStack.getCount() + 1), false);
	}

	public static void decrStackSize(IItemHandlerModifiable itemHandler, int slot) {
		ItemStack itemStack = itemHandler.getStackInSlot(slot);
		if (!itemStack.isEmpty()) {
			itemHandler.extractItem(slot, 1, false);
		}
	}

	public static NBTTagCompound getNBTTagCompound(ItemStack itemStack) {
		if (!itemStack.isEmpty()) {
			if (itemStack.getTagCompound() == null) {
				itemStack.setTagCompound(new NBTTagCompound());
			}

			return itemStack.getTagCompound();
		}
		return null;
	}

	public static ItemStack getItemStackWithNBT(IBlockState state, World world, BlockPos pos) {
		if (state != null) {
			Block block = state.getBlock();
			ItemStack dropStack = new ItemStack(block, block.quantityDropped(state, 0, world.rand),
					block.damageDropped(state));
			TileEntity tile = world.getTileEntity(pos);

			if (tile != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tile.writeToNBT(tag);
				dropStack.setTagCompound(tag);
			}

			return dropStack;
		}
		return null;
	}

	public static void dropBlockWithNBT(IBlockState state, World world, BlockPos pos) {
		if (!world.isRemote && world.getGameRules().getBoolean("doTileDrops")) {
			ItemStack itemStack = getItemStackWithNBT(state, world, pos);
			if (!itemStack.isEmpty()) {
				InventoryUtility.dropItemStack(world, pos, itemStack, 10);
			}
		}
	}

	public static void readFromNBT(NBTTagCompound tag, IItemHandlerModifiable inventory) {
		if (tag.getTagId("Inventory") == Constants.NBT.TAG_LIST) {
			NBTTagList tagList = tag.getTagList("Inventory", Constants.NBT.TAG_COMPOUND);
			for (int i = 0; i < tagList.tagCount(); i++) {
				NBTTagCompound slotTag = (NBTTagCompound) tagList.get(i);
				byte slot = slotTag.getByte("Slot");
				if (slot < inventory.getSlots()) {
					inventory.setStackInSlot(slot, new ItemStack(slotTag));
				}
			}
		}
	}

	public static void dropItemStack(World world, BlockPos pos, ItemStack itemStack, int delay) {
		dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), itemStack, delay);
	}

	public static void dropItemStack(World world, double x, double y, double z, ItemStack itemStack, int delay) {
		if (!world.isRemote && !itemStack.isEmpty()) {
			float motion = 0.7F;
			double motionX = world.rand.nextFloat() * motion + (1.0F - motion) * 0.5D;
			double motionY = world.rand.nextFloat() * motion + (1.0F - motion) * 0.5D;
			double motionZ = world.rand.nextFloat() * motion + (1.0F - motion) * 0.5D;
			EntityItem entityItem = new EntityItem(world, x + motionX, y + motionY, z + motionZ, itemStack);
			if (itemStack.hasTagCompound()) {
				entityItem.getItem().setTagCompound(itemStack.getTagCompound().copy());
			}
			entityItem.setPickupDelay(delay);
			world.spawnEntity(entityItem);
		}
	}
}