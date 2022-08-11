package physica.library.energy;

import physica.api.core.utilities.IBaseUtilities;
import physica.library.energy.base.Measurement;
import physica.library.energy.base.Unit;

public class ElectricityDisplay implements IBaseUtilities {

	public static String getDisplay(double value, Unit unit, int decimalPlaces, boolean isShort) {
		String unitName = unit.name;
		double testValue = value;
		if (testValue < 10) {
			testValue = value / 1000.0;
		}

		if (isShort) {
			unitName = unit.symbol;
		} else if (testValue > 1.0D) {
			unitName = unit.getPlural();
		}
		if (value == 0.0D) {
			return value + " " + unitName;
		}
		if (testValue < 1.0D) {
			return IBaseUtilities.roundPreciseStatic(Measurement.MILLI.process(value), decimalPlaces) + " " + Measurement.MILLI.getName(isShort) + unitName;
		} else if (testValue > Measurement.GIGA.value) {
			return IBaseUtilities.roundPreciseStatic(Measurement.GIGA.process(value), decimalPlaces) + " " + Measurement.GIGA.getName(isShort) + unitName;
		} else if (testValue > Measurement.MEGA.value) {
			return IBaseUtilities.roundPreciseStatic(Measurement.MEGA.process(value), decimalPlaces) + " " + Measurement.MEGA.getName(isShort) + unitName;
		} else if (testValue > Measurement.KILO.value) {
			return IBaseUtilities.roundPreciseStatic(Measurement.KILO.process(value), decimalPlaces) + " " + Measurement.KILO.getName(isShort) + unitName;
		}
		return IBaseUtilities.roundPreciseStatic(value, decimalPlaces) + " " + unitName;
	}

	public static String getDisplay(double value, Unit unit) {
		return getDisplay(value, unit, 2, false);
	}

	public static String getDisplayShort(double value, Unit unit) {
		return getDisplay(value, unit, 2, true);
	}

	public static String getDisplayShort(double value, Unit unit, int decimalPlaces) {
		return getDisplay(value, unit, decimalPlaces, true);
	}

	public static String getDisplaySimple(double value, Unit unit, int decimalPlaces) {
		if (value > 1.0D) {
			if (decimalPlaces < 1) {
				return (int) value + " " + unit.getPlural();
			}
			return IBaseUtilities.roundPreciseStatic(value, decimalPlaces) + " " + unit.getPlural();
		}
		if (decimalPlaces < 1) {
			return (int) value + " " + unit.name;
		}
		return IBaseUtilities.roundPreciseStatic(value, decimalPlaces) + " " + unit.name;
	}

	public static String getDisplayShortTicked(double value, Unit unit) {
		return getDisplayShort(value, unit) + "/t";
	}

}
