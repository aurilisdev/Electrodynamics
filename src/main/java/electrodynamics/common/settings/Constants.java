package electrodynamics.common.settings;

import electrodynamics.api.configuration.Configuration;
import electrodynamics.api.configuration.DoubleValue;
import electrodynamics.api.configuration.FloatValue;
import electrodynamics.api.configuration.IntValue;
import electrodynamics.api.configuration.StringValue;
import electrodynamics.api.utilities.TransferPack;

@Configuration(name = "Electrodynamics")
public class Constants {
	@IntValue(comment = "", def = 2)
	public static int TEST = 2;
	@FloatValue(comment = "", def = 0.95f)
	public static float TRANSFORMER_EFFICIENCY = 0.95f;
	@DoubleValue(comment = "", def = 34)
	public static double COALGENERATOR_AMPERAGE = 34;
	@DoubleValue(comment = "", def = 120)
	public static double COALGENERATOR_VOLTAGE = 120;
	@StringValue(def = "hey")
	public static String MOTD = "hey";
	public static final TransferPack COALGENERATOR_MAX_OUTPUT = TransferPack.ampsVoltage(COALGENERATOR_AMPERAGE, COALGENERATOR_VOLTAGE);
}
