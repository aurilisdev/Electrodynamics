package physica.core.common.tile.cable;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import physica.api.core.ITileBasePowered;
import physica.core.common.block.BlockEnergyCable.EnumEnergyCable;
import physica.library.tile.TileBase;

public class TileEnergyCable extends TileBase implements ITileBasePowered, IEnergyProvider {

	public static final int MAX_ENERGY_STORED_BASE = 10240;
	private int energyStored;
	private ForgeDirection lastReceive = ForgeDirection.UNKNOWN;
	private int lastVoltage;

	@Override
	public AxisAlignedBB getRenderBoundingBox()
	{
		return INFINITE_EXTENT_AABB;
	}

	@Override
	public int getEnergyStored()
	{
		return energyStored;
	}

	@Override
	public void setEnergyStored(int energy)
	{
		energyStored = Math.min(energy, getMaxEnergyStored(ForgeDirection.UNKNOWN));
	}

	@Override
	public void updateServer(int ticks)
	{
		super.updateServer(ticks);
		if (energyStored > 0)
		{
			boolean found = false;
			HashMap<IEnergyReceiver, ForgeDirection> receivers = new HashMap<>();
			for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			{
				if (dir != lastReceive)
				{
					TileEntity tile = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
					if (tile instanceof IEnergyReceiver)
					{
						IEnergyReceiver receiver = (IEnergyReceiver) tile;
						int max = receiver.getMaxEnergyStored(dir.getOpposite());
						if (receiver.canConnectEnergy(dir.getOpposite()) && (tile instanceof TileEnergyCable ? receiver.getEnergyStored(dir.getOpposite()) < max : true))
						{
							receivers.put((IEnergyReceiver) tile, dir);
						}
					}
				}
			}
			for (Entry<IEnergyReceiver, ForgeDirection> entry : receivers.entrySet())
			{
				energyStored -= entry.getKey().receiveEnergy(entry.getValue().getOpposite(), energyStored / receivers.size(), false);
				found = true;
			}

			if (!found)
			{
				TileEntity tile = worldObj.getTileEntity(xCoord + lastReceive.offsetX, yCoord + lastReceive.offsetY, zCoord + lastReceive.offsetZ);
				if (tile instanceof IEnergyReceiver)
				{
					IEnergyReceiver receiver = (IEnergyReceiver) tile;
					if (receiver.canConnectEnergy(lastReceive.getOpposite()))
					{
						energyStored -= receiver.receiveEnergy(lastReceive.getOpposite(), energyStored, false);
					}
				}
			}
			if (lastVoltage > getVoltage() && getBlockMetadata() != EnumEnergyCable.superConductor.ordinal())
			{
				for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
				{
					TileEntity tile = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
					if (tile instanceof TileEnergyCable)
					{
						TileEnergyCable cable = (TileEnergyCable) tile;
						if (cable.getVoltage() < lastVoltage)
						{
							((TileEnergyCable) tile).lastVoltage = lastVoltage;
						}
					}
				}
				worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.fire);
			}
		}

	}

	public int getVoltage()
	{
		return getBlockMetadata() == EnumEnergyCable.superConductor.ordinal() ? 0 : 120 * (getBlockMetadata() + 1);
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
	{
		lastReceive = from;
		int capacityLeft = getMaxEnergyStored(from) - getEnergyStored();
		int setCount = simulate ? getEnergyStored() : capacityLeft >= maxReceive ? getEnergyStored() + maxReceive : getMaxEnergyStored(from);

		setEnergyStored(setCount);
		if (getVoltage() > 0)
		{
			lastVoltage = maxReceive / (getMaxEnergyStored(ForgeDirection.UNKNOWN) / getVoltage());
		} else
		{
			lastVoltage = -10;
		}
		return capacityLeft >= maxReceive ? maxReceive : capacityLeft;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from)
	{
		if (hasWorldObj())
		{
			if (getBlockMetadata() == EnumEnergyCable.superConductor.ordinal())
			{
				return MAX_ENERGY_STORED_BASE * 60;
			}
			return (int) (MAX_ENERGY_STORED_BASE * Math.pow(getBlockMetadata() + 1, 2) * 3);
		} else
		{
			return Integer.MAX_VALUE; // The section above has a worldObj which is null when the tile is loaded at first.
		}
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from)
	{
		return true;
	}

	@Override
	public void writeSynchronizationPacket(List<Object> dataList, EntityPlayer player)
	{
		super.writeSynchronizationPacket(dataList, player);
		dataList.add(lastReceive.ordinal());
	}

	@Override
	public void readSynchronizationPacket(ByteBuf buf, EntityPlayer player)
	{
		super.readSynchronizationPacket(buf, player);
		lastReceive = ForgeDirection.getOrientation(buf.readInt());
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate)
	{
		int remove = Math.max(0, Math.min(maxExtract, energyStored));
		if (!simulate)
		{
			energyStored -= remove;
		}
		return remove;
	}

	@Override
	public int getEnergyUsage()
	{
		return 0;
	}

	@Override
	public int getEnergyStored(ForgeDirection from)
	{
		return ITileBasePowered.super.getEnergyStored(from);
	}
}
