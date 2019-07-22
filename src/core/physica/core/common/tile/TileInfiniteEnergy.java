package physica.core.common.tile;

import net.minecraft.tileentity.TileEntity;
import physica.api.core.abstraction.AbstractionLayer;
import physica.api.core.abstraction.Face;
import physica.api.core.electricity.IElectricityProvider;
import physica.core.common.configuration.ConfigCore;
import physica.library.location.Location;
import physica.library.tile.TileBase;

public class TileInfiniteEnergy extends TileBase implements IElectricityProvider {

	public static final int VISIBLE_STORAGE = Integer.MAX_VALUE;

	@Override
	public void updateServer(int ticks)
	{
		Location loc = getLocation();
		if (ConfigCore.DISABLE_INFINITE_ENERGY_CUBE)
		{
			loc.setBlockAir(World());
			return;
		}
		for (Face dir : Face.VALID)
		{
			TileEntity tile = World().getTileEntity(loc.xCoord + dir.offsetX, loc.yCoord + dir.offsetY, loc.zCoord + dir.offsetZ);
			if (tile != null)
			{
				if (AbstractionLayer.Electricity.isElectricReceiver(tile))
				{
					if (AbstractionLayer.Electricity.canConnectElectricity(tile, dir.getOpposite()))
					{
						AbstractionLayer.Electricity.receiveElectricity(tile, dir.getOpposite(), VISIBLE_STORAGE, false);
					}
				}
			}
		}
	}

	@Override
	public boolean canConnectElectricity(Face from)
	{
		return true;
	}

	@Override
	public int extractElectricity(Face from, int maxExtract, boolean simulate)
	{
		return maxExtract;
	}

	@Override
	public int getElectricityStored(Face from)
	{
		return VISIBLE_STORAGE;
	}

	@Override
	public int getElectricCapacity(Face from)
	{
		return VISIBLE_STORAGE;
	}

}
