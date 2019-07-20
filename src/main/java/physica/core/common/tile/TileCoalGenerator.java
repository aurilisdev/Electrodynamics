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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.common.util.ForgeDirection;
import physica.api.core.abstraction.AbstractionLayer;
import physica.api.core.electricity.IElectricityProvider;
import physica.api.core.inventory.IGuiInterface;
import physica.core.client.gui.GuiCoalGenerator;
import physica.core.common.inventory.ContainerCoalGenerator;
import physica.library.tile.TileBaseContainer;

public class TileCoalGenerator extends TileBaseContainer implements IGuiInterface, IElectricityProvider {

	public static final int		SLOT_INPUT			= 0;
	private static final int[]	ACCESSIBLE_SLOTS	= new int[] { SLOT_INPUT };
	public static final int		MAX_GENERATE		= 10000;
	public static final int		MIN_GENERATE		= 100;
	private static final float	BASE_ACCELERATION	= 0.3F;
	public double				prevGenerateWatts;
	public double				generate;
	public int					itemCookTime;
	private TileEntity			cachedOutput;

	@Override
	public boolean canConnectElectricity(ForgeDirection from)
	{
		return from == getFacing().getOpposite();
	}

	@Override
	public void updateServer(int ticks)
	{
		super.updateServer(ticks);
		prevGenerateWatts = generate;

		if (itemCookTime > 0)
		{
			itemCookTime -= 1;
			generate = Math.min(generate + Math.min(generate * 0.005D + BASE_ACCELERATION, 5.0D), 10000.0D);
		}
		if (getStackInSlot(SLOT_INPUT) != null)
		{
			if (getStackInSlot(SLOT_INPUT).getItem() == Items.coal)
			{
				if (itemCookTime <= 0)
				{
					itemCookTime = 320;
					decrStackSize(0, 1);
				}
			}
			if (generate > MIN_GENERATE)
			{
				ForgeDirection out = getFacing().getOpposite();
				if (cachedOutput == null || cachedOutput.isInvalid())
				{
					cachedOutput = null;
					TileEntity outputTile = worldObj.getTileEntity(xCoord + out.offsetX, yCoord + out.offsetY, zCoord + out.offsetZ);
					if (AbstractionLayer.Electricity.isElectricReceiver(outputTile))
					{
						cachedOutput = outputTile;
					}
				}
				if (cachedOutput != null)
				{
					if (AbstractionLayer.Electricity.canConnectElectricity(cachedOutput, out.getOpposite()))
					{
						AbstractionLayer.Electricity.receiveElectricity(cachedOutput, out.getOpposite(), (int) generate, false);
					}
				}
			}
		}
		if (itemCookTime <= 0)
		{
			generate = Math.max(generate - 8.0D, 0.0D);
		}
	}

	@Override
	public int getSyncRate()
	{
		return 40;
	}

	@Override
	public void readSynchronizationPacket(ByteBuf buf, EntityPlayer player)
	{
		super.readSynchronizationPacket(buf, player);
		worldObj.updateLightByType(EnumSkyBlock.Block, xCoord, yCoord, zCoord);
		worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		tag.setInteger("itemCookTime", itemCookTime);
		tag.setDouble("generateRate", generate);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		itemCookTime = tag.getInteger("itemCookTime");
		generate = tag.getDouble("generateRate");
	}

	@Override
	public void writeClientGuiPacket(List<Object> dataList, EntityPlayer player)
	{
		super.writeClientGuiPacket(dataList, player);
		dataList.add(generate);
		dataList.add(itemCookTime);
	}

	@Override
	public void readClientGuiPacket(ByteBuf buf, EntityPlayer player)
	{
		super.readClientGuiPacket(buf, player);
		generate = buf.readDouble();
		itemCookTime = buf.readInt();
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		return stack != null && stack.getItem() == Items.coal;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side)
	{
		return ACCESSIBLE_SLOTS;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack item, int side)
	{
		return isItemValidForSlot(slot, item);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack item, int side)
	{
		return false;
	}

	@Override
	public int getSizeInventory()
	{
		return 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen getClientGuiElement(int id, EntityPlayer player)
	{
		return new GuiCoalGenerator(player, this);
	}

	@Override
	public Container getServerGuiElement(int id, EntityPlayer player)
	{
		return new ContainerCoalGenerator(player, this);
	}

	@Override
	public int getElectricityStored(ForgeDirection from)
	{
		return generate > MIN_GENERATE ? (int) generate : 0;
	}

	@Override
	public int extractElectricity(ForgeDirection from, int maxExtract, boolean simulate)
	{
		return from == getFacing().getOpposite() ? getElectricityStored(from) : 0;
	}

	@Override
	public int getElectricCapacity(ForgeDirection from)
	{
		return MAX_GENERATE;
	}
}
