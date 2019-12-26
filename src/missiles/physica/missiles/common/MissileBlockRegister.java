package physica.missiles.common;

import cpw.mods.fml.common.registry.GameRegistry;
import physica.api.core.load.IContent;
import physica.api.core.load.LoadPhase;
import physica.library.item.ItemBlockDescriptable;
import physica.missiles.common.block.BlockPrimedBlock;
import physica.missiles.common.items.ItemBlockPrimed;

public class MissileBlockRegister implements IContent {
	public static BlockPrimedBlock primedBlock;

	@Override
	public void register(LoadPhase phase)
	{
		if (phase == LoadPhase.RegisterObjects)
		{
			GameRegistry.registerBlock(primedBlock = new BlockPrimedBlock(), ItemBlockPrimed.class, "primedBlock");

			ItemBlockDescriptable.addDescription(primedBlock, 0, "Slightly more desructive version of TNT", "Also explodes instantaneously.");
		}
	}
}
