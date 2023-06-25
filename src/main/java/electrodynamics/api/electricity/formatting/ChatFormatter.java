package electrodynamics.api.electricity.formatting;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class ChatFormatter {

	public static String getChatDisplay(double value, DisplayUnit unit, int decimalPlaces, boolean isShort) {
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

		for (MeasurementUnit measurement : MeasurementUnit.values()) {

			if (value < measurement.value) {

				if (measurement.ordinal() == 0) {
					return formatDecimals(measurement.process(value), decimalPlaces) + unit.distanceFromValue + measurement.getName(isShort) + unitName;
				} else {
					measurement = MeasurementUnit.values()[measurement.ordinal() - 1];
					return formatDecimals(measurement.process(value), decimalPlaces) + unit.distanceFromValue + measurement.getName(isShort) + unitName;
				}
			}
		}

		MeasurementUnit measurement = MeasurementUnit.values()[MeasurementUnit.values().length - 1];
		return formatDecimals(measurement.process(value), decimalPlaces) + unit.distanceFromValue + measurement.getName(isShort) + unitName;
	}

	public static String getChatDisplay(double value, DisplayUnit unit) {
		return getChatDisplay(value, unit, 2, false);
	}

	public static String getChatDisplayShort(double value, DisplayUnit unit) {
		return getChatDisplay(value, unit, 2, true);
	}

	public static String getDisplayShort(double value, DisplayUnit unit, int decimalPlaces) {
		return getChatDisplay(value, unit, decimalPlaces, true);
	}

	public static String getChatDisplaySimple(double value, DisplayUnit unit, int decimalPlaces) {
		if (value > 1.0D) {

			if (decimalPlaces < 1) {
				return (int) value + unit.distanceFromValue + unit.getPlural();
			}

			return formatDecimals(value, decimalPlaces) + unit.distanceFromValue + unit.getPlural();
		}

		if (decimalPlaces < 1) {
			return (int) value + unit.distanceFromValue + unit.name;
		}

		return formatDecimals(value, decimalPlaces) + unit.distanceFromValue + unit.name;
	}

	public static double roundDecimals(double d, int decimalPlaces) {
		int j = (int) (d * Math.pow(10.0D, decimalPlaces));
		return j / Math.pow(10.0D, decimalPlaces);
	}

	public static String formatDecimals(double d, int decimalPlaces) {
		DecimalFormat format = new DecimalFormat("0" + getDecimals(decimalPlaces));
		format.setRoundingMode(RoundingMode.HALF_EVEN);
		return format.format(roundDecimals(d, decimalPlaces));
	}

	public static String formatFluidMilibuckets(double amount) {

		if (amount > 1000) {

			return getChatDisplayShort(amount / 1000.0, DisplayUnit.BUCKETS);

		} else {
			return formatDecimals(amount, 2) + " mB";

		}

	}

	private static String getDecimals(int num) {
		if (num <= 0) {
			return ".";
		}
		num--;
		String key = ".0";
		for (int i = 0; i < num; i++) {
			key += "#";
		}
		return key;
	}
}
