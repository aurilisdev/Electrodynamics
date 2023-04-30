package electrodynamics.api.electricity.formatting;

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

		if (value <= MeasurementUnit.MILLI.value) {
			return roundDecimals(MeasurementUnit.MICRO.process(value), decimalPlaces) + " " + MeasurementUnit.MICRO.getName(isShort) + unitName;
		}

		if (value < 1.0D) {
			return roundDecimals(MeasurementUnit.MILLI.process(value), decimalPlaces) + " " + MeasurementUnit.MILLI.getName(isShort) + unitName;
		}

		if (value > MeasurementUnit.MEGA.value) {
			return roundDecimals(MeasurementUnit.MEGA.process(value), decimalPlaces) + " " + MeasurementUnit.MEGA.getName(isShort) + unitName;
		}

		if (value > MeasurementUnit.KILO.value) {
			return roundDecimals(MeasurementUnit.KILO.process(value), decimalPlaces) + " " + MeasurementUnit.KILO.getName(isShort) + unitName;
		}

		return roundDecimals(value, decimalPlaces) + " " + unitName;
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

	public static String formatFluidMilibuckets(double amount) {

		if (amount > 1000) {

			return getChatDisplayShort(amount / 1000.0, DisplayUnit.BUCKETS);

		} else {
			return amount + " mB";

		}

	}
}
