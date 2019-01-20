package physica.content.common;


import cpw.mods.fml.common.registry.GameRegistry;
import physica.api.base.IProxyContent;
import physica.api.lib.worldgen.OreGenReplace;
import physica.api.lib.worldgen.OreGeneratorSettings;
import physica.content.common.configuration.ConfigMain;

public class WorldGenRegister implements IProxyContent {
	@Override
	public void init() {
		GameRegistry.registerWorldGenerator(new OreGenReplace(BlockRegister.blockUraniumOre, 0,
				new OreGeneratorSettings(ConfigMain.URANIUM_ORE_MIN_Y, ConfigMain.URANIUM_ORE_MAX_Y, ConfigMain.URANIUM_ORE_BRANCH_SIZE, ConfigMain.URANIUM_ORE_COUNT), "pickaxe",
				ConfigMain.URANIUM_ORE_HARVEST_LEVEL), 1);

	}
}
