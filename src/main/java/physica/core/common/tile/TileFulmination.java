package physica.core.common.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import physica.api.core.electricity.ElectricityHandler;
import physica.api.core.electricity.IElectricityHandler;
import physica.api.core.tile.ITileBasePowered;
import physica.core.common.event.FulminationEventHandler;
import physica.library.tile.TileBase;

public class TileFulmination extends TileBase implements ITileBasePowered, IElectricityHandler {

	public static int	MAX_ENERGY_STORED	= 500000;
	private int			energyStored;

	@Override
	public int getElectricityStored()
	{
		return energyStored;
	}

	@Override
	public void setElectricityStored(int energy)
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
				if (ElectricityHandler.isElectricReceiver(tile))
				{
					if (ElectricityHandler.canConnectElectricity(tile, dir.getOpposite()))
					{
						ElectricityHandler.receiveElectricity(tile, dir.getOpposite(), Math.min(5000, energyStored), false);
					}
				}
			}
		}
	}

	@Override
	public int getElectricCapacity(ForgeDirection from)
	{
		return MAX_ENERGY_STORED;
	}

	@Override
	public boolean canConnectElectricity(ForgeDirection from)
	{
		return true;
	}

	@Override
	public int extractElectricity(ForgeDirection from, int maxExtract, boolean simulate)
	{
		if (!simulate)
		{
			energyStored -= Math.min(5000, energyStored);
		}
		return Math.min(5000, energyStored);
	}

	@Override
	public int getElectricityUsage()
	{
		return 0;
	}

	@Override
	public int receiveElectricity(ForgeDirection from, int maxReceive, boolean simulate)
	{
		return 0;
	}

	@Override
	public int getElectricityStored(ForgeDirection from)
	{
		return ITileBasePowered.super.getElectricityStored(from);
	}
}
