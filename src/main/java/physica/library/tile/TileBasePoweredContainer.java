package physica.library.tile;

import physica.api.core.tile.ITileBasePoweredContainer;

public abstract class TileBasePoweredContainer extends TileBaseContainer implements ITileBasePoweredContainer {

	private int wattTicksStored;

	@Override
	public int getElectricityStored()
	{
		return wattTicksStored;
	}

	@Override
	public void setElectricityStored(int energy)
	{
		wattTicksStored = energy;
	}
}
