package electrodynamics.common.settings;

import electrodynamics.api.configuration.BooleanValue;
import electrodynamics.api.configuration.Configuration;
import electrodynamics.api.configuration.DoubleValue;

@Configuration(name = "electrodynamics_ore_config")
public class OreConfig {

	@BooleanValue(def = false)
	public static boolean DISABLE_ALL_ORES = false;

	// Stone Ores
	@BooleanValue(def = false)
	public static boolean DISABLE_STONE_ORES = false;

	@BooleanValue(def = true)
	public static boolean SPAWN_ALUMINUM_ORE = true;
	@BooleanValue(def = true)
	public static boolean SPAWN_CHROMIUM_ORE = true;
	@BooleanValue(def = true)
	public static boolean SPAWN_FLUORITE_ORE = true;
	@BooleanValue(def = true)
	public static boolean SPAWN_LEAD_ORE = true;
	@BooleanValue(def = true)
	public static boolean SPAWN_LITHIUM_ORE = true;
	@BooleanValue(def = true)
	public static boolean SPAWN_MOLYBDENUM_ORE = true;
	@BooleanValue(def = true)
	public static boolean SPAWN_MONAZITE_ORE = true;
	@BooleanValue(def = true)
	public static boolean SPAWN_NITER_ORE = true;
	@BooleanValue(def = true)
	public static boolean SPAWN_SALT_ORE = true;
	@BooleanValue(def = true)
	public static boolean SPAWN_SILVER_ORE = true;
	@BooleanValue(def = true)
	public static boolean SPAWN_SULFUR_ORE = true;
	@BooleanValue(def = true)
	public static boolean SPAWN_SYLVITE_ORE = true;
	@BooleanValue(def = true)
	public static boolean SPAWN_TIN_ORE = true;
	@BooleanValue(def = true)
	public static boolean SPAWN_TITANIUM_ORE = true;
	@BooleanValue(def = true)
	public static boolean SPAWN_THORIUM_ORE = true;
	@BooleanValue(def = true)
	public static boolean SPAWN_URANIUM_ORE = true;
	@BooleanValue(def = true)
	public static boolean SPAWN_VANADIUM_ORE = true;

	// Deepslate Ores
	@BooleanValue(def = false)
	public static boolean DISABLE_DEEPSLATE_ORES = false;

	@BooleanValue(def = true)
	public static boolean SPAWN_DEEP_ALUMINUM_ORE = true;
	@BooleanValue(def = true)
	public static boolean SPAWN_DEEP_CHROMIUM_ORE = true;
	@BooleanValue(def = true)
	public static boolean SPAWN_DEEP_FLUORITE_ORE = true;
	@BooleanValue(def = true)
	public static boolean SPAWN_DEEP_LEAD_ORE = true;
	@BooleanValue(def = true)
	public static boolean SPAWN_DEEP_LITHIUM_ORE = true;
	@BooleanValue(def = true)
	public static boolean SPAWN_DEEP_MOLYBDENUM_ORE = true;
	@BooleanValue(def = true)
	public static boolean SPAWN_DEEP_MONAZITE_ORE = true;
	@BooleanValue(def = true)
	public static boolean SPAWN_DEEP_NITER_ORE = true;
	@BooleanValue(def = true)
	public static boolean SPAWN_DEEP_SALT_ORE = true;
	@BooleanValue(def = true)
	public static boolean SPAWN_DEEP_SILVER_ORE = true;
	@BooleanValue(def = true)
	public static boolean SPAWN_DEEP_SULFUR_ORE = true;
	@BooleanValue(def = true)
	public static boolean SPAWN_DEEP_SYLVITE_ORE = true;
	@BooleanValue(def = true)
	public static boolean SPAWN_DEEP_TIN_ORE = true;
	@BooleanValue(def = true)
	public static boolean SPAWN_DEEP_TITANIUM_ORE = true;
	@BooleanValue(def = true)
	public static boolean SPAWN_DEEP_THORIUM_ORE = true;
	@BooleanValue(def = true)
	public static boolean SPAWN_DEEP_URANIUM_ORE = true;
	@BooleanValue(def = true)
	public static boolean SPAWN_DEEP_VANADIUM_ORE = true;

	// Multipliers
	@DoubleValue(def = 1)
	public static double ORE_GENERATION_MULTIPLIER = 1;

}
