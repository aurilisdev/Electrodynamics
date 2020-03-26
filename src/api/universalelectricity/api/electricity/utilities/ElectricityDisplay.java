package universalelectricity.api.electricity.utilities;

public class ElectricityDisplay {
	public static double roundPreciseStatic(double value, int precision) {
		int scale = (int) Math.pow(10, precision);
		return (double) Math.round(value * scale) / scale;
	}

	public static String getDisplay(double value, Unit unit, int decimalPlaces, boolean isShort) {
		String unitName = unit.name;
		double checkValue = value;
		if (checkValue < 10) {
			checkValue = value / 1000.0;
		}
		if (isShort) {
			unitName = unit.symbol;
		} else if (checkValue > 1.0D) {
			unitName = unit.getPlural();
		}
		if (value == 0.0D) {
			return value + " " + unitName;
		}
		if (checkValue < 1.0D) {
			return roundPreciseStatic(Measurement.MILLI.process(value), decimalPlaces) + " "
					+ Measurement.MILLI.getName(isShort) + unitName;
		} else if (checkValue > Measurement.GIGA.value) {
			return roundPreciseStatic(Measurement.GIGA.process(value), decimalPlaces) + " "
					+ Measurement.GIGA.getName(isShort) + unitName;
		} else if (checkValue > Measurement.MEGA.value) {
			return roundPreciseStatic(Measurement.MEGA.process(value), decimalPlaces) + " "
					+ Measurement.MEGA.getName(isShort) + unitName;
		} else if (checkValue > Measurement.KILO.value) {
			return roundPreciseStatic(Measurement.KILO.process(value), decimalPlaces) + " "
					+ Measurement.KILO.getName(isShort) + unitName;
		}
		return roundPreciseStatic(value, decimalPlaces) + " " + unitName;
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
			return roundPreciseStatic(value, decimalPlaces) + " " + unit.getPlural();
		}
		if (decimalPlaces < 1) {
			return (int) value + " " + unit.name;
		}
		return roundPreciseStatic(value, decimalPlaces) + " " + unit.name;
	}

}
