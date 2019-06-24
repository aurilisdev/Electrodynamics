package physica.library.tile;

import physica.api.core.ITileBasePoweredContainer;

public abstract class TileBasePoweredContainer extends TileBaseContainer implements ITileBasePoweredContainer {

	private int energyStored;

	@Override
	public int getEnergyStored()
	{
		return energyStored;
	}

	@Override
	public void setEnergyStored(int energy)
	{
		energyStored = energy;
	}
}
