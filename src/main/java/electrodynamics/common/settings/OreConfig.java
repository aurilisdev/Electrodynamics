package electrodynamics.common.settings;

import electrodynamics.api.configuration.Configuration;
import electrodynamics.api.configuration.DoubleValue;
import electrodynamics.api.configuration.StringValue;

@Configuration(name = "electrodynamics_ore_config")
public class OreConfig {

	@StringValue(def = "tin:silver:lead:uraninite:thorianite:monazite:vanadinite:sulfur:niter:aluminum:chromite:rutile:halite:lepidolite:molybdenum:fluorite:sylvite")
	public static String oresToSpawn = "tin:silver:lead:uraninite:thorianite:monazite:vanadinite:sulfur:niter:aluminum:chromite:rutile:halite:lepidolite:molybdenum:fluorite:sylvite";
	@DoubleValue(def = 1)
	public static double OREGENERATIONMULTIPLIER = 1;
}
