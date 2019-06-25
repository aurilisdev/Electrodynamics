package physica.core.common.tile;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import physica.api.core.ITileBasePowered;
import physica.core.common.event.FulminationEventHandler;
import physica.library.tile.TileBase;

public class TileFulmination extends TileBase implements ITileBasePowered, IEnergyProvider {

	public static int MAX_ENERGY_STORED = 500000;
	private int energyStored;

	@Override
	public int getEnergyStored()
	{
		return energyStored;
	}

	@Override
	public void setEnergyStored(int energy)
	{
		energyStored = Math.min(energy, MAX_ENERGY_STORED);
	}

	@Override
	public void updateServer(int ticks)
	{
		super.updateServer(ticks);
		if (!FulminationEventHandler.INSTANCE.isRegistered(this))
		{
			FulminationEventHandler.INSTANCE.register(this);
		}
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
		{
			TileEntity tile = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
			if (tile != null)
			{
				if (tile instanceof IEnergyReceiver)
				{
					IEnergyReceiver reciever = (IEnergyReceiver) tile;
					if (reciever.canConnectEnergy(dir.getOpposite()))
					{
						energyStored -= reciever.receiveEnergy(dir.getOpposite(), Math.min(5000, energyStored), false);
					}
				}
			}
		}
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from)
	{
		return MAX_ENERGY_STORED;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from)
	{
		return true;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate)
	{
		if (!simulate)
		{
			energyStored -= Math.min(5000, energyStored);
		}
		return Math.min(5000, energyStored);
	}

	@Override
	public int getEnergyUsage()
	{
		return 0;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
	{
		return 0;
	}

	@Override
	public int getEnergyStored(ForgeDirection from)
	{
		return ITileBasePowered.super.getEnergyStored(from);
	}

}
