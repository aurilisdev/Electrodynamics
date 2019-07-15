package physica.nuclear.common;

import cpw.mods.fml.common.registry.GameRegistry;
import physica.api.core.load.IContent;
import physica.api.core.load.LoadPhase;
import physica.library.worldgen.OreGenReplace;
import physica.library.worldgen.OreGeneratorSettings;
import physica.nuclear.common.configuration.ConfigNuclearPhysics;

public class NuclearWorldGenRegister implements IContent {

	@Override
	public void register(LoadPhase phase)
	{
		if (phase == LoadPhase.WorldRegister)
		{
			GameRegistry.registerWorldGenerator(new OreGenReplace(NuclearBlockRegister.blockUraniumOre, 0,
					new OreGeneratorSettings(ConfigNuclearPhysics.URANIUM_ORE_MIN_Y, ConfigNuclearPhysics.URANIUM_ORE_MAX_Y, ConfigNuclearPhysics.URANIUM_ORE_BRANCH_SIZE, ConfigNuclearPhysics.URANIUM_ORE_COUNT), "pickaxe",
					ConfigNuclearPhysics.URANIUM_ORE_HARVEST_LEVEL), 1);
		}
	}
}
