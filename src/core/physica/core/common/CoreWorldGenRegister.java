package physica.core.common;

import cpw.mods.fml.common.registry.GameRegistry;
import physica.api.core.load.IContent;
import physica.api.core.load.LoadPhase;
import physica.core.common.configuration.ConfigCore;
import physica.library.worldgen.OreGenReplace;
import physica.library.worldgen.OreGeneratorSettings;

public class CoreWorldGenRegister implements IContent {

	@Override
	public void register(LoadPhase phase)
	{
		if (phase == LoadPhase.WorldRegister)
		{
			GameRegistry.registerWorldGenerator(new OreGenReplace(CoreBlockRegister.blockTinOre, 0, new OreGeneratorSettings(ConfigCore.TIN_ORE_MIN_Y, ConfigCore.TIN_ORE_MAX_Y, ConfigCore.TIN_ORE_BRANCH_SIZE, ConfigCore.TIN_ORE_COUNT),
					"pickaxe", ConfigCore.TIN_ORE_HARVEST_LEVEL), 1);
			GameRegistry.registerWorldGenerator(new OreGenReplace(CoreBlockRegister.blockCopperOre, 0,
					new OreGeneratorSettings(ConfigCore.COPPER_ORE_MIN_Y, ConfigCore.COPPER_ORE_MAX_Y, ConfigCore.COPPER_ORE_BRANCH_SIZE, ConfigCore.COPPER_ORE_COUNT), "pickaxe", ConfigCore.COPPER_ORE_HARVEST_LEVEL), 1);
			GameRegistry.registerWorldGenerator(new OreGenReplace(CoreBlockRegister.blockLeadOre, 0, new OreGeneratorSettings(ConfigCore.LEAD_ORE_MIN_Y, ConfigCore.LEAD_ORE_MAX_Y, ConfigCore.LEAD_ORE_BRANCH_SIZE, ConfigCore.LEAD_ORE_COUNT),
					"pickaxe", ConfigCore.LEAD_ORE_HARVEST_LEVEL), 1);
			GameRegistry.registerWorldGenerator(new OreGenReplace(CoreBlockRegister.blockCopperOre, 0,
					new OreGeneratorSettings(ConfigCore.SILVER_ORE_MIN_Y, ConfigCore.SILVER_ORE_MAX_Y, ConfigCore.SILVER_ORE_BRANCH_SIZE, ConfigCore.SILVER_ORE_COUNT), "pickaxe", ConfigCore.SILVER_ORE_HARVEST_LEVEL), 1);
		}
	}

}
