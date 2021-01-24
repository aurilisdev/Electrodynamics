package electrodynamics.common.settings;

import electrodynamics.api.configuration.Configuration;
import electrodynamics.api.configuration.DoubleValue;
import electrodynamics.api.configuration.FloatValue;
import electrodynamics.api.utilities.TransferPack;

@Configuration(name = "Electrodynamics")
public class Constants {
	@FloatValue(def = 0.95f)
	public static float TRANSFORMER_EFFICIENCY = 0.95f;
	@DoubleValue(def = 34)
	public static double COALGENERATOR_AMPERAGE = 34.0;
	@DoubleValue(def = 120.0)
	public static double COALGENERATOR_VOLTAGE = 120.0;
	public static TransferPack COALGENERATOR_MAX_OUTPUT = TransferPack.ampsVoltage(COALGENERATOR_AMPERAGE, COALGENERATOR_VOLTAGE);
}
