package physica.core.common.block;

import physica.core.common.tile.TileElectricFurnace;

public class BlockElectricFurnace extends BlockMachine {
	public BlockElectricFurnace() {
		super("electricFurnace", TileElectricFurnace.class);
		setHazard(true);
	}

	@Override
	public void registerRecipes()
	{
		addRecipe(this, "SSS", "SCS", "SMS", 'S', "ingotSteel", 'C', "circuitAdvanced", 'M', "motor");
	}

}
