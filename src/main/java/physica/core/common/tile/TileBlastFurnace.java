package physica.core.common.tile;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.EnumSkyBlock;
import physica.api.core.IGuiInterface;
import physica.core.client.gui.GuiBlastFurnace;
import physica.core.common.CoreItemRegister;
import physica.core.common.inventory.ContainerBlastFurnace;
import physica.library.tile.TileBaseContainer;

public class TileBlastFurnace extends TileBaseContainer implements IGuiInterface {

	public static final int		TOTAL_BURN_TIME			= 200;

	public static final int		SLOT_INPUT				= 0;
	public static final int		SLOT_INPUTFUEL			= 1;
	public static final int		SLOT_OUTPUT				= 2;

	private static final int[]	ACCESSIBLE_SLOTS_TOP	= new int[] { SLOT_INPUT };
	private static final int[]	ACCESSIBLE_SLOTS_DOWN	= new int[] { SLOT_OUTPUT };
	private static final int[]	ACCESSIBLE_SLOTS_SIDES	= new int[] { SLOT_INPUTFUEL };

	public int					furnaceBurnTime;
	public int					currentItemBurnTime;
	public int					furnaceCookTime;

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		furnaceBurnTime = tag.getShort("BurnTime");
		furnaceCookTime = tag.getShort("CookTime");
		currentItemBurnTime = TileEntityFurnace.getItemBurnTime(getStackInSlot(SLOT_INPUTFUEL));
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		tag.setShort("BurnTime", (short) furnaceBurnTime);
		tag.setShort("CookTime", (short) furnaceCookTime);
	}

	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int scale)
	{
		return furnaceCookTime * scale / TOTAL_BURN_TIME;
	}

	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int scale)
	{
		if (currentItemBurnTime == 0)
		{
			currentItemBurnTime = TOTAL_BURN_TIME;
		}

		return furnaceBurnTime * scale / currentItemBurnTime;
	}

	public boolean isBurning()
	{
		return furnaceBurnTime > 0;
	}

	@Override
	public void updateClient(int ticks)
	{
		super.updateClient(ticks);
		if (isBurning())
		{
			if (worldObj.rand.nextFloat() < 0.25f)
			{
				worldObj.spawnParticle("smoke", xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, 0, 0.05f, 0);
			} else if (worldObj.rand.nextFloat() < 0.025f)
			{
				worldObj.spawnParticle("lava", xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, 0, 0.05f, 0);
			}
		}
	}

	@Override
	public void updateServer(int ticks)
	{
		super.updateServer(ticks);
		if (furnaceBurnTime > 0)
		{
			--furnaceBurnTime;
		}
		ItemStack stackInputFuel = getStackInSlot(SLOT_INPUTFUEL);
		if (!worldObj.isRemote)
		{
			if (furnaceBurnTime != 0 || stackInputFuel != null && getStackInSlot(SLOT_INPUT) != null)
			{
				if (furnaceBurnTime == 0 && canSmelt())
				{
					currentItemBurnTime = furnaceBurnTime = TileEntityFurnace.getItemBurnTime(stackInputFuel);

					if (furnaceBurnTime > 0)
					{
						if (stackInputFuel != null)
						{
							--stackInputFuel.stackSize;

							if (stackInputFuel.stackSize == 0)
							{
								setInventorySlotContents(SLOT_INPUTFUEL, stackInputFuel.getItem().getContainerItem(stackInputFuel));
							}
						}
					}
				}

				if (isBurning() && canSmelt())
				{
					++furnaceCookTime;

					if (furnaceCookTime == TOTAL_BURN_TIME)
					{
						furnaceCookTime = 0;
						smeltItem();
					}
				} else
				{
					furnaceCookTime = 0;
				}
			}
		}
	}

	@Override
	public void writeSynchronizationPacket(List<Object> dataList, EntityPlayer player)
	{
		super.writeSynchronizationPacket(dataList, player);
		dataList.add(furnaceBurnTime);
		dataList.add(currentItemBurnTime);
		dataList.add(furnaceCookTime);
	}

	@Override
	public void readSynchronizationPacket(ByteBuf buf, EntityPlayer player)
	{
		super.readSynchronizationPacket(buf, player);
		furnaceBurnTime = buf.readInt();
		currentItemBurnTime = buf.readInt();
		furnaceCookTime = buf.readInt();
		worldObj.updateLightByType(EnumSkyBlock.Block, xCoord, yCoord, zCoord);
	}

	@Override
	public int getSyncRate()
	{
		return 10;
	}

	private boolean canSmelt()
	{
		ItemStack input = getStackInSlot(SLOT_INPUT);
		if (input == null)
		{
			return false;
		} else
		{
			ItemStack recipe = input.getItem() == Items.iron_ingot ? new ItemStack(CoreItemRegister.itemMetaIngot, 1, 2) : null;
			if (recipe == null)
			{
				return false;
			}
			ItemStack output = getStackInSlot(SLOT_OUTPUT);
			if (output == null)
			{
				return true;
			}
			if (!output.isItemEqual(recipe))
			{
				return false;
			}
			int result = output.stackSize + recipe.stackSize;
			return result <= getInventoryStackLimit() && result <= output.getMaxStackSize();
		}
	}

	public void smeltItem()
	{
		if (canSmelt())
		{
			ItemStack input = getStackInSlot(SLOT_INPUT);
			ItemStack recipe = input.getItem() == Items.iron_ingot ? new ItemStack(CoreItemRegister.itemMetaIngot, 1, 2) : null;
			ItemStack output = getStackInSlot(SLOT_OUTPUT);
			if (output == null)
			{
				setInventorySlotContents(SLOT_OUTPUT, recipe.copy());
			} else if (output.getItem() == recipe.getItem())
			{
				output.stackSize += recipe.stackSize;
			}

			--input.stackSize;
			if (input.stackSize <= 0)
			{
				setInventorySlotContents(SLOT_INPUT, null);
			}
		}
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		return slot == 2 ? false : slot == 1 ? TileEntityFurnace.isItemFuel(stack) : stack != null && stack.getItem() == Items.iron_ingot;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side)
	{
		return side == 0 ? ACCESSIBLE_SLOTS_DOWN : side == 1 ? ACCESSIBLE_SLOTS_TOP : ACCESSIBLE_SLOTS_SIDES;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack item, int side)
	{
		return isItemValidForSlot(slot, item);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack item, int side)
	{
		return side != 0 || slot != 1 || item.getItem() == Items.bucket;
	}

	@Override
	public int getSizeInventory()
	{
		return 3;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen getClientGuiElement(int id, EntityPlayer player)
	{
		return new GuiBlastFurnace(player, this);
	}

	@Override
	public Container getServerGuiElement(int id, EntityPlayer player)
	{
		return new ContainerBlastFurnace(player, this);
	}
}
