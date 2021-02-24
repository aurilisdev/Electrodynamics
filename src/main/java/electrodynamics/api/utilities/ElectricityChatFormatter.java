package electrodynamics.api.utilities;

import electrodynamics.api.formatting.ElectricUnit;
import electrodynamics.api.formatting.MeasurementUnit;

public class ElectricityChatFormatter {
    public static String getDisplay(double value, ElectricUnit unit, int decimalPlaces, boolean isShort) {
	if (value < Long.MIN_VALUE + 10000) {
	    return "-Infinite" + (isShort ? unit.symbol : unit.getPlural());
	} else if (value > Long.MAX_VALUE - 10000) {
	    return "Infinite " + (isShort ? unit.symbol : unit.getPlural());
	}
	String unitName = unit.name;
	if (isShort) {
	    unitName = unit.symbol;
	} else if (value > 1.0D) {
	    unitName = unit.getPlural();
	}

	if (value == 0.0D) {
	    return value + " " + unitName;
	}

	if (value <= MeasurementUnit.MILLI.value) {
	    return roundDecimals(MeasurementUnit.MICRO.process(value), decimalPlaces) + " "
		    + MeasurementUnit.MICRO.getName(isShort) + unitName;
	}

	if (value < 1.0D) {
	    return roundDecimals(MeasurementUnit.MILLI.process(value), decimalPlaces) + " "
		    + MeasurementUnit.MILLI.getName(isShort) + unitName;
	}

	if (value > MeasurementUnit.MEGA.value) {
	    return roundDecimals(MeasurementUnit.MEGA.process(value), decimalPlaces) + " "
		    + MeasurementUnit.MEGA.getName(isShort) + unitName;
	}

	if (value > MeasurementUnit.KILO.value) {
	    return roundDecimals(MeasurementUnit.KILO.process(value), decimalPlaces) + " "
		    + MeasurementUnit.KILO.getName(isShort) + unitName;
	}

	return roundDecimals(value, decimalPlaces) + " " + unitName;
    }

    public static String getDisplay(double value, ElectricUnit unit) {
	return getDisplay(value, unit, 2, false);
    }

    public static String getDisplayShort(double value, ElectricUnit unit) {
	return getDisplay(value, unit, 2, true);
    }

    public static String getDisplayShort(double value, ElectricUnit unit, int decimalPlaces) {
	return getDisplay(value, unit, decimalPlaces, true);
    }

    public static String getDisplaySimple(double value, ElectricUnit unit, int decimalPlaces) {
	if (value > 1.0D) {

	    if (decimalPlaces < 1) {
		return (int) value + " " + unit.getPlural();
	    }

	    return roundDecimals(value, decimalPlaces) + " " + unit.getPlural();
	}

	if (decimalPlaces < 1) {
	    return (int) value + " " + unit.name;
	}

	return roundDecimals(value, decimalPlaces) + " " + unit.name;
    }

    public static double roundDecimals(double d, int decimalPlaces) {
	int j = (int) (d * Math.pow(10.0D, decimalPlaces));
	return j / Math.pow(10.0D, decimalPlaces);
    }

    public static double roundDecimals(double d) {
	return roundDecimals(d, 2);
    }
}
