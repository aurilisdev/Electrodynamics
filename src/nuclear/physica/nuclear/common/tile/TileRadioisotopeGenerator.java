package physica.nuclear.common.tile;

import net.minecraftforge.common.util.ForgeDirection;
import physica.api.core.electricity.IElectricityProvider;
import physica.library.tile.TileBase;

public class TileRadioisotopeGenerator extends TileBase implements IElectricityProvider {
	@Override
	public boolean canConnectElectricity(ForgeDirection from)
	{
		return from == ForgeDirection.UP || from == ForgeDirection.DOWN;
	}

	@Override
	public int getElectricityStored(ForgeDirection from)
	{
		return 0;
	}

	@Override
	public int getElectricCapacity(ForgeDirection from)
	{
		return 0;
	}

}
