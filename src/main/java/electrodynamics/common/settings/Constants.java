package electrodynamics.common.settings;

import electrodynamics.api.configuration.Configuration;
import electrodynamics.api.configuration.DoubleValue;
import electrodynamics.api.configuration.FloatValue;
import electrodynamics.api.configuration.IntValue;
import electrodynamics.api.utilities.TransferPack;

@Configuration(name = "Electrodynamics")
public class Constants {
    @FloatValue(def = 0.98f)
    public static float TRANSFORMER_EFFICIENCY = 0.98f;
    @DoubleValue(def = 34)
    public static double COALGENERATOR_AMPERAGE = 34.0;
    @DoubleValue(def = 4.166667)
    public static double THERMOELECTRICGENERATOR_AMPERAGE = 4.166667;
    @DoubleValue(def = 10)
    public static double SOLARPANEL_AMPERAGE = 10;
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
    @DoubleValue(def = 175.0)
    public static double MINERALGRINDER_USAGE_PER_TICK = 175.0;
    @IntValue(def = 200)
    public static int MINERALGRINDER_REQUIRED_TICKS = 200;
    @DoubleValue(def = 175.0)
    public static double MINERALCRUSHER_USAGE_PER_TICK = 110.0;
    @IntValue(def = 200)
    public static int MINERALCRUSHER_REQUIRED_TICKS = 200;
    @DoubleValue(def = 90.0)
    public static double OXIDATIONFURNACE_USAGE_PER_TICK = 90.0;
    @IntValue(def = 200)
    public static int OXIDATIONFURNACE_REQUIRED_TICKS = 250;

    public static TransferPack COALGENERATOR_MAX_OUTPUT = TransferPack.ampsVoltage(COALGENERATOR_AMPERAGE, 120);
}
