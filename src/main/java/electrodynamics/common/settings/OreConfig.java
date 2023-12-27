package electrodynamics.common.settings;

import electrodynamics.api.configuration.BooleanValue;
import electrodynamics.api.configuration.Configuration;
import electrodynamics.api.configuration.DoubleValue;

@Configuration(name = "electrodynamics_ore_config")
public class OreConfig {

	@BooleanValue(def = false)
	public static boolean DISABLE_ALL_ORES = false;

	@BooleanValue(def = true)
	public static boolean SPAWN_ALUMINUM_ORE = true;
	@BooleanValue(def = true)
	public static boolean SPAWN_CHROMIUM_ORE = true;
	@BooleanValue(def = true)
	public static boolean SPAWN_COPPER_ORE = true;
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

	@DoubleValue(def = 0.75)
	public static double ORE_GENERATION_MULTIPLIER = 1;
}
