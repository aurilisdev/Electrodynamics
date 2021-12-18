package electrodynamics.common.settings;

import electrodynamics.api.configuration.Configuration;
import electrodynamics.api.configuration.DoubleValue;
import electrodynamics.api.configuration.FloatValue;
import electrodynamics.api.configuration.IntValue;
import electrodynamics.prefab.utilities.object.TransferPack;

@Configuration(name = "Electrodynamics")
public class Constants {
	
	@FloatValue(def = 0.9925f)
	public static float TRANSFORMER_EFFICIENCY = 0.9925f;
	@FloatValue(def = 0.995f)
	public static float CIRCUITBREAKER_EFFICIENCY = 0.995f;
	@DoubleValue(def = 34)
	public static double COALGENERATOR_AMPERAGE = 34.0;
	@DoubleValue(def = 4.5)
	public static double THERMOELECTRICGENERATOR_AMPERAGE = 4.5;
	@DoubleValue(def = 6)
	public static double HYDROELECTRICGENERATOR_AMPERAGE = 6;
	@DoubleValue(def = 10)
	public static double WINDMILL_MAX_AMPERAGE = 10;
	@DoubleValue(def = 7)
	public static double SOLARPANEL_AMPERAGE = 7;
	@DoubleValue(def = 50.0)
	public static double ELECTRICPUMP_USAGE_PER_TICK = 50.0;
	@DoubleValue(def = 20.0)
	public static double ADVANCEDSOLARPANEL_AMPERAGE = 20.0;
	@DoubleValue(def = 175)
	public static double ELECTRICFURNACE_USAGE_PER_TICK = 175.0;
	@IntValue(def = 100)
	public static int ELECTRICFURNACE_REQUIRED_TICKS = 100;
	@DoubleValue(def = 125.0)
	public static double WIREMILL_USAGE_PER_TICK = 125.0;
	@IntValue(def = 200)
	public static int WIREMILL_REQUIRED_TICKS = 200;
	@DoubleValue(def = 350.0)
	public static double MINERALGRINDER_USAGE_PER_TICK = 350.0;
	@IntValue(def = 200)
	public static int MINERALGRINDER_REQUIRED_TICKS = 200;
	@DoubleValue(def = 450.0)
	public static double MINERALCRUSHER_USAGE_PER_TICK = 450.0;
	@IntValue(def = 200)
	public static int MINERALCRUSHER_REQUIRED_TICKS = 200;
	@DoubleValue(def = 400.0)
	public static double MINERALWASHER_USAGE_PER_TICK = 400.0;
	@IntValue(def = 200)
	public static int MINERALWASHER_REQUIRED_TICKS = 200;
	@DoubleValue(def = 400.0)
	public static double CHEMICALMIXER_USAGE_PER_TICK = 400.0;
	@IntValue(def = 200)
	public static int CHEMICALMIXER_REQUIRED_TICKS = 200;
	@DoubleValue(def = 350.0)
	public static double OXIDATIONFURNACE_USAGE_PER_TICK = 350.0;
	@IntValue(def = 200)
	public static int OXIDATIONFURNACE_REQUIRED_TICKS = 200;
	@DoubleValue(def = 10.0)
	public static double FERMENTATIONPLANT_USAGE_PER_TICK = 20.0;
	@IntValue(def = 2000)
	public static int FERMENTATIONPLANT_REQUIRED_TICKS = 2000;
	@DoubleValue(def = 350.0)
	public static double COMBUSTIONCHAMBER_JOULES_PER_TICK = 350.0;
	@DoubleValue(def = 800.0)
	public static double CHEMICALCRYSTALLIZER_USAGE_PER_TICK = 800.0;
	@IntValue(def = 200)
	public static int CHEMICALCRYSTALLIZER_REQUIRED_TICKS = 200;
	@IntValue(def = 50)
	public static int ENERGIZEDALLOYER_REQUIRED_TICKS = 50;
	@DoubleValue(def = 2000.0)
	public static double ENERGIZEDALLOYER_USAGE_PER_TICK = 2000.0;
	@IntValue(def = 200)
	public static int LATHE_REQUIRED_TICKS = 200;
	@DoubleValue(def = 350.0)
	public static double LATHE_USAGE_PER_TICK = 350.0;
	@IntValue(def = 50)
	public static int REINFORCEDALLOYER_REQUIRED_TICKS = 50;
	@DoubleValue(def = 2000.0)
	public static double REINFORCEDALLOYER_USAGE_PER_TICK = 2000.0;
	@IntValue(def = 10)
	public static int CHARGER_REQUIRED_TICKS = 100;
	@DoubleValue(def = 1000.0)
	public static double CHARGER_USAGER_PER_TICK = 1000.0;
	@IntValue(def = 100)
    public static int COBBLE_GEN_REQUIRED_TICKS = 100;
    @DoubleValue(def = 100)
    public static double COBBLE_GEN_USAGE_PER_TICK = 100;

	public static TransferPack COALGENERATOR_MAX_OUTPUT = TransferPack.ampsVoltage(COALGENERATOR_AMPERAGE, 120);
}
