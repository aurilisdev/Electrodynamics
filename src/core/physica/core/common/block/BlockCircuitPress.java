package physica.core.common.block;

import physica.core.common.tile.TileCircuitPress;

public class BlockCircuitPress extends BlockMachine {
	public BlockCircuitPress() {
		super("circuitPress", TileCircuitPress.class);
		setHazard(true);
	}

	@Override
	public void registerRecipes()
	{
		// TODO: Recipe
	}
}
