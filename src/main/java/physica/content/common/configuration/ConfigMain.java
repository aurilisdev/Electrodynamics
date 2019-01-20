package physica.content.common.configuration;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import physica.Physica;
import physica.References;
import physica.api.PhysicaAPI;
import physica.api.base.IProxyContent;

public class ConfigMain implements IProxyContent {
	public static boolean	isDebugMode					= false;

	public static float		antimatterCreationSpeed		= 1f;

	public static boolean	ENABLE_URANIUM_ORE			= true;
	public static int		URANIUM_ORE_MIN_Y			= 10;
	public static int		URANIUM_ORE_MAX_Y			= 40;
	public static int		URANIUM_ORE_COUNT			= 20;
	public static int		URANIUM_ORE_BRANCH_SIZE		= 3;
	public static int		URANIUM_ORE_HARVEST_LEVEL	= 1;

	@Override
	public void preInit() {
		Configuration configuration = new Configuration(new File(Physica.configFolder, "Main.cfg"), References.VERSION);
		configuration.load();
		isDebugMode = configuration.getBoolean("isDebugMode", "Debugging", isDebugMode, "Enable/disable debug mode");

		antimatterCreationSpeed = configuration.getFloat("antimatterCreationSpeed", "ParticleAccelerator", antimatterCreationSpeed, 0.5f, 1.5f,
				"Speed at in blocks per tick when the particle explodes");

		ENABLE_URANIUM_ORE = configuration.getBoolean("enable", "WorldGen", ENABLE_URANIUM_ORE, "Should world generation be enabled? True to allow ore to spawn; False to disable");
		URANIUM_ORE_MIN_Y = configuration.getInt("min_y", "WorldGen", URANIUM_ORE_MIN_Y, 0, 255, "Lowest y level/height that ore can spawn");
		URANIUM_ORE_MAX_Y = configuration.getInt("max_y", "WorldGen", URANIUM_ORE_MAX_Y, 0, 255, "Highest y level/height that ore can spawn");
		URANIUM_ORE_COUNT = configuration.getInt("chunk_count", "WorldGen", URANIUM_ORE_MAX_Y, 1, 100,
				"Max amount of ore to spawn in each chunk. " + "Actual count per chunk is a mix of randomization and conditions of the chunk itself.");
		URANIUM_ORE_BRANCH_SIZE = configuration.getInt("branch_size", "WorldGen", URANIUM_ORE_BRANCH_SIZE, 0, 100, "Amount of ore to generate per branch");
		URANIUM_ORE_HARVEST_LEVEL = configuration.getInt("harvest_level", "WorldGen", URANIUM_ORE_HARVEST_LEVEL, 0, 255,
				"Tool level needed to mine the ore \n" + "*     Wood:    0\n" + "*     Stone:   1\n" + "*     Iron:    2\n" + "*     Diamond: 3\n" + "*     Gold:    0");

		configuration.save();
		if (isDebugMode)
		{
			PhysicaAPI.isDebugMode = true;
		}
	}
}
