package physica.core.common.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import physica.api.core.electricity.ElectricityHandler;
import physica.api.core.electricity.IElectricityProvider;
import physica.core.common.configuration.ConfigCore;
import physica.library.tile.TileBase;

public class TileInfiniteEnergy extends TileBase implements IElectricityProvider {

	public static final int VISIBLE_STORAGE = Integer.MAX_VALUE;

	@Override
	public void updateServer(int ticks)
	{
		if (ConfigCore.DISABLE_INFINITE_ENERGY_CUBE)
		{
			worldObj.setBlockToAir(xCoord, yCoord, zCoord);
			return;
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
						ElectricityHandler.receiveElectricity(tile, dir.getOpposite(), VISIBLE_STORAGE, false);
					}
				}
			}
		}
	}

	@Override
	public boolean canConnectElectricity(ForgeDirection from)
	{
		return true;
	}

	@Override
	public int extractElectricity(ForgeDirection from, int maxExtract, boolean simulate)
	{
		return maxExtract;
	}

	@Override
	public int getElectricityStored(ForgeDirection from)
	{
		return VISIBLE_STORAGE;
	}

	@Override
	public int getElectricCapacity(ForgeDirection from)
	{
		return VISIBLE_STORAGE;
	}

}
