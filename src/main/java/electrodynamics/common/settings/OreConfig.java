package electrodynamics.common.settings;

import electrodynamics.api.configuration.Configuration;
import electrodynamics.api.configuration.DoubleValue;
import electrodynamics.api.configuration.StringValue;

@Configuration(name = "electrodynamics_ore_config")
public class OreConfig {

    @StringValue(def = "copper:tin:silver:lead:uraninite:thorianite:monazite:vanadinite:sulfur:niter:aluminum:chromite:rutile:halite:lepidolite:molybdenum:fluorite:sylvite")
    public static String oresToSpawn = "copper:tin:silver:lead:uraninite:thorianite:monazite:vanadinite:sulfur:niter:aluminum:chromite:rutile:halite:lepidolite:molybdenum:fluorite:sylvite";
    @DoubleValue(def = 0.75)
    public static double OREGENERATIONMULTIPLIER = 0.75;
}
