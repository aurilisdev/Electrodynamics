package physica.core.common.configuration;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import physica.CoreReferences;
import physica.Physica;
import physica.api.core.IContent;
import physica.api.core.PhysicaAPI;

public class ConfigCore implements IContent {

	public static final String CATEGORY = "Core";

	public static boolean IS_DEBUG_MODE = false;
	public static boolean DISABLE_INFINITE_ENERGY_CUBE = false;

	public static int TIN_ORE_MIN_Y = 10;
	public static int TIN_ORE_MAX_Y = 55;
	public static int TIN_ORE_COUNT = 40;
	public static int TIN_ORE_BRANCH_SIZE = 5;
	public static int TIN_ORE_HARVEST_LEVEL = 1;

	public static int COPPER_ORE_MIN_Y = 10;
	public static int COPPER_ORE_MAX_Y = 55;
	public static int COPPER_ORE_COUNT = 40;
	public static int COPPER_ORE_BRANCH_SIZE = 5;
	public static int COPPER_ORE_HARVEST_LEVEL = 1;

	public static int LEAD_ORE_MIN_Y = 10;
	public static int LEAD_ORE_MAX_Y = 55;
	public static int LEAD_ORE_COUNT = 25;
	public static int LEAD_ORE_BRANCH_SIZE = 5;
	public static int LEAD_ORE_HARVEST_LEVEL = 2;

	public static int SILVER_ORE_MIN_Y = 10;
	public static int SILVER_ORE_MAX_Y = 55;
	public static int SILVER_ORE_COUNT = 10;
	public static int SILVER_ORE_BRANCH_SIZE = 5;
	public static int SILVER_ORE_HARVEST_LEVEL = 2;

	@Override
	public void preInit()
	{
		Configuration configuration = new Configuration(new File(Physica.configFolder, "Core.cfg"), CoreReferences.VERSION);
		configuration.load();
		IS_DEBUG_MODE = configuration.getBoolean("isDebugMode", "Debugging", IS_DEBUG_MODE, "Enable/disable debug mode");
		DISABLE_INFINITE_ENERGY_CUBE = configuration.getBoolean("disable_infinite_energy_cube", CATEGORY, DISABLE_INFINITE_ENERGY_CUBE, "True to disable infinite energy cubes. False to enable");

		if (IS_DEBUG_MODE)
		{
			PhysicaAPI.isDebugMode = true;
		}

		TIN_ORE_MIN_Y = configuration.getInt("tin_min_y", CATEGORY, TIN_ORE_MIN_Y, 0, 255, "Lowest y level/height that ore can spawn");
		TIN_ORE_MAX_Y = configuration.getInt("tin_max_y", CATEGORY, TIN_ORE_MAX_Y, 0, 255, "Highest y level/height that ore can spawn");
		TIN_ORE_COUNT = configuration.getInt("tin_chunk_count", CATEGORY, TIN_ORE_COUNT, 0, 100,
				"Max amount of ore to spawn in each chunk. " + "Actual count per chunk is a mix of randomization and conditions of the chunk itself.");
		TIN_ORE_BRANCH_SIZE = configuration.getInt("tin_branch_size", CATEGORY, TIN_ORE_BRANCH_SIZE, 0, 100, "Amount of ore to generate per branch");
		TIN_ORE_HARVEST_LEVEL = configuration.getInt("tin_harvest_level", CATEGORY, TIN_ORE_HARVEST_LEVEL, 0, 255,
				"Tool level needed to mine the ore \n" + "*     Wood:    0\n" + "*     Stone:   1\n" + "*     Iron:    2\n" + "*     Diamond: 3\n" + "*     Gold:    0");

		COPPER_ORE_MIN_Y = configuration.getInt("copper_min_y", CATEGORY, COPPER_ORE_MIN_Y, 0, 255, "Lowest y level/height that ore can spawn");
		COPPER_ORE_MAX_Y = configuration.getInt("copper_max_y", CATEGORY, COPPER_ORE_MAX_Y, 0, 255, "Highest y level/height that ore can spawn");
		COPPER_ORE_COUNT = configuration.getInt("copper_chunk_count", CATEGORY, COPPER_ORE_COUNT, 0, 100,
				"Max amount of ore to spawn in each chunk. " + "Actual count per chunk is a mix of randomization and conditions of the chunk itself.");
		COPPER_ORE_BRANCH_SIZE = configuration.getInt("copper_branch_size", CATEGORY, COPPER_ORE_BRANCH_SIZE, 0, 100, "Amount of ore to generate per branch");
		COPPER_ORE_HARVEST_LEVEL = configuration.getInt("copper_harvest_level", CATEGORY, COPPER_ORE_HARVEST_LEVEL, 0, 255,
				"Tool level needed to mine the ore \n" + "*     Wood:    0\n" + "*     Stone:   1\n" + "*     Iron:    2\n" + "*     Diamond: 3\n" + "*     Gold:    0");

		LEAD_ORE_MIN_Y = configuration.getInt("lead_min_y", CATEGORY, LEAD_ORE_MIN_Y, 0, 255, "Lowest y level/height that ore can spawn");
		LEAD_ORE_MAX_Y = configuration.getInt("lead_max_y", CATEGORY, LEAD_ORE_MAX_Y, 0, 255, "Highest y level/height that ore can spawn");
		LEAD_ORE_COUNT = configuration.getInt("lead_chunk_count", CATEGORY, LEAD_ORE_COUNT, 0, 100,
				"Max amount of ore to spawn in each chunk. " + "Actual count per chunk is a mix of randomization and conditions of the chunk itself.");
		LEAD_ORE_BRANCH_SIZE = configuration.getInt("lead_branch_size", CATEGORY, LEAD_ORE_BRANCH_SIZE, 0, 100, "Amount of ore to generate per branch");
		LEAD_ORE_HARVEST_LEVEL = configuration.getInt("lead_harvest_level", CATEGORY, LEAD_ORE_HARVEST_LEVEL, 0, 255,
				"Tool level needed to mine the ore \n" + "*     Wood:    0\n" + "*     Stone:   1\n" + "*     Iron:    2\n" + "*     Diamond: 3\n" + "*     Gold:    0");

		SILVER_ORE_MIN_Y = configuration.getInt("silver_min_y", CATEGORY, SILVER_ORE_MIN_Y, 0, 255, "Lowest y level/height that ore can spawn");
		SILVER_ORE_MAX_Y = configuration.getInt("silver_max_y", CATEGORY, SILVER_ORE_MAX_Y, 0, 255, "Highest y level/height that ore can spawn");
		SILVER_ORE_COUNT = configuration.getInt("silver_chunk_count", CATEGORY, SILVER_ORE_COUNT, 0, 100,
				"Max amount of ore to spawn in each chunk. " + "Actual count per chunk is a mix of randomization and conditions of the chunk itself.");
		SILVER_ORE_BRANCH_SIZE = configuration.getInt("silver_branch_size", CATEGORY, SILVER_ORE_BRANCH_SIZE, 0, 100, "Amount of ore to generate per branch");
		SILVER_ORE_HARVEST_LEVEL = configuration.getInt("silver_harvest_level", CATEGORY, SILVER_ORE_HARVEST_LEVEL, 0, 255,
				"Tool level needed to mine the ore \n" + "*     Wood:    0\n" + "*     Stone:   1\n" + "*     Iron:    2\n" + "*     Diamond: 3\n" + "*     Gold:    0");

		configuration.save();
	}
}
