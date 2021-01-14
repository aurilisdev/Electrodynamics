package electrodynamics.common.settings;

import electrodynamics.api.configuration.Configuration;
import electrodynamics.api.configuration.FloatValue;
import electrodynamics.api.utilities.TransferPack;

@Configuration(name = "Electrodynamics")
public class Constants {
	@FloatValue(comment = "", def = 0.95f)
	public static float TRANSFORMER_EFFICIENCY = 0.95f;
	public static final TransferPack COALGENERATOR_MAX_OUTPUT = TransferPack.ampsVoltage(34, 120);
}
