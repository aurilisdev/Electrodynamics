package physica.core.common.block;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import physica.core.common.tile.TileCircuitPress;

public class BlockCircuitPress extends BlockMachine {
	public BlockCircuitPress() {
		super("circuitPress", TileCircuitPress.class);
		setHazard(true);
	}

	@Override
	public void registerRecipes()
	{
		addRecipe(this, "ILI", "BFB", "WRW", 'F', Blocks.furnace, 'I', Items.iron_ingot, 'L', Blocks.lever, 'B', Blocks.stone_button, 'R', Blocks.redstone_torch);
	}
}
