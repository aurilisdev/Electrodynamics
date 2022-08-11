package physica.nuclear.common.configuration;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;

import net.minecraftforge.common.config.Configuration;
import physica.CoreReferences;
import physica.api.core.load.IContent;
import physica.api.core.load.LoadPhase;
import physica.nuclear.PhysicaNuclearPhysics;
import physica.nuclear.common.items.update.ItemUpdateAntimatter;

public class ConfigNuclearPhysics implements IContent {

	public static String			CATEGORY							= "NUCLEAR_PHYSICS";

	public static float				ANTIMATTER_CREATION_SPEED			= 1f;
	public static boolean			ENABLE_PARTICLE_COLLISION			= true;
	public static boolean			ENABLE_PARTICLE_CHUNKLOADING		= true;
	public static float				TURBINE_STEAM_TO_RF_RATIO			= 2f;

	public static HashSet<String>	PROTECTED_WORLDS					= new HashSet<>(Arrays.asList("spawn", "creative"));
	public static HashSet<String>	QUANTUM_ASSEMBLER_BLACKLIST			= new HashSet<>();
	public static int				QUANTUM_ASSEMBLER_TICKS_REQUIRED	= 120;
	public static boolean			FLIP_BLACKLIST_TO_WHITELIST			= false;

	public static int				URANIUM_ORE_MIN_Y					= 10;
	public static int				URANIUM_ORE_MAX_Y					= 40;
	public static int				URANIUM_ORE_COUNT					= 20;
	public static int				URANIUM_ORE_BRANCH_SIZE				= 3;
	public static int				URANIUM_ORE_HARVEST_LEVEL			= 3;

	public static int				PLASMA_STRENGTH						= 8;
	public static int				DARK_MATTER_USES					= 8;

	@Override
	public void register(LoadPhase phase)
	{
		if (phase == LoadPhase.ConfigRegister)
		{
			Configuration configuration = new Configuration(new File(PhysicaNuclearPhysics.configFolder, "NuclearPhysics.cfg"), CoreReferences.VERSION);
			configuration.load();

			ANTIMATTER_CREATION_SPEED = configuration.getFloat("antimatterCreationSpeed", CATEGORY, ANTIMATTER_CREATION_SPEED, 0.5f, 1.5f, "Speed at in blocks per tick when the particle accelerator particle explodes");

			ENABLE_PARTICLE_COLLISION = configuration.getBoolean("particle_collision_enable", CATEGORY, ENABLE_PARTICLE_COLLISION,
					"Should particle accelerator particles collide with entities and eachother? True to allow collision; False to disable");
			ENABLE_PARTICLE_CHUNKLOADING = configuration.getBoolean("particle_chunkloading_enable", CATEGORY, ENABLE_PARTICLE_CHUNKLOADING,
					"Should particle accelerator particles load the particle chunk if it is unloaded? True to allow enable; False to disable");
			ItemUpdateAntimatter.FULMINATION_ANTIMATTER_ENERGY_SCALE = configuration.getInt("fulmination_antimatter_energy_scale", CATEGORY, ItemUpdateAntimatter.FULMINATION_ANTIMATTER_ENERGY_SCALE, 1, 3000,
					"Multiplier for an antimatter explosion's energy generation in a fulmination generator.");

			String[] protectedWorlds = configuration.getStringList("protected_worlds", CATEGORY, PROTECTED_WORLDS.toArray(new String[0]), "Worlds that are protected from typical explosions and such");
			for (String world : protectedWorlds)
			{
				PROTECTED_WORLDS.add(world.toLowerCase());
			}

			QUANTUM_ASSEMBLER_BLACKLIST = new HashSet<>(
					Arrays.asList(configuration.getStringList("quantum_assembler_blacklist", CATEGORY, QUANTUM_ASSEMBLER_BLACKLIST.toArray(new String[0]), "Items which are blacklisted from use in the quantum assembler")));
			QUANTUM_ASSEMBLER_TICKS_REQUIRED = configuration.getInt("quantum_assembler_ticks_required", CATEGORY, QUANTUM_ASSEMBLER_TICKS_REQUIRED, 0, 100000, "How meny ticks that are required to dupe one item in the assembler");
			FLIP_BLACKLIST_TO_WHITELIST = configuration.getBoolean("flip_blacklist_to_whitelist", CATEGORY, FLIP_BLACKLIST_TO_WHITELIST, "True to turn the blacklist into a whitelist; False to ignore");

			TURBINE_STEAM_TO_RF_RATIO = configuration.getFloat("turbineSteamToRfRatio", CATEGORY, TURBINE_STEAM_TO_RF_RATIO, 0.01f, 100f, "Ratio for turbines to convert one ml of steam into rf.");

			URANIUM_ORE_MIN_Y = configuration.getInt("uranium_min_y", CATEGORY, URANIUM_ORE_MIN_Y, 0, 255, "Lowest y level/height that ore can spawn");
			URANIUM_ORE_MAX_Y = configuration.getInt("uranium_max_y", CATEGORY, URANIUM_ORE_MAX_Y, 0, 255, "Highest y level/height that ore can spawn");
			URANIUM_ORE_COUNT = configuration.getInt("uranium_chunk_count", CATEGORY, URANIUM_ORE_COUNT, 1, 100,
					"Max amount of ore to spawn in each chunk. " + "Actual count per chunk is a mix of randomization and conditions of the chunk itself.");
			URANIUM_ORE_BRANCH_SIZE = configuration.getInt("uranium_branch_size", CATEGORY, URANIUM_ORE_BRANCH_SIZE, 0, 100, "Amount of ore to generate per branch");
			URANIUM_ORE_HARVEST_LEVEL = configuration.getInt("uranium_harvest_level", CATEGORY, URANIUM_ORE_HARVEST_LEVEL, 0, 255,
					"Tool level needed to mine the ore \n" + "*     Wood:    0\n" + "*     Stone:   1\n" + "*     Iron:    2\n" + "*     Diamond: 3\n" + "*     Gold:    0");

			PLASMA_STRENGTH = configuration.getInt("plasma_strength", CATEGORY, PLASMA_STRENGTH, 0, 50, "Amount of destruction caused by plasma");

			DARK_MATTER_USES = configuration.getInt("dark_matter_uses", CATEGORY, DARK_MATTER_USES, 1, 1000, "The number of items each dark matter cell can be used in a quantum assembler");

			configuration.save();
		}
	}
}
