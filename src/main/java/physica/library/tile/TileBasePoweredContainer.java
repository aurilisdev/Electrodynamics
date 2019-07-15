package physica.library.tile;

import physica.api.core.tile.ITileBasePoweredContainer;

public abstract class TileBasePoweredContainer extends TileBaseContainer implements ITileBasePoweredContainer {

	private int energyStored;

	@Override
	public int getElectricityStored()
	{
		return energyStored;
	}

	@Override
	public void setElectricityStored(int energy)
	{
		energyStored = energy;
	}
}
