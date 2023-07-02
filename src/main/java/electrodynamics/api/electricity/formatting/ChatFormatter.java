package electrodynamics.api.electricity.formatting;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class ChatFormatter {

	public static MutableComponent getChatDisplay(double value, DisplayUnit unit, int decimalPlaces, boolean isShort) {
		if (value < Long.MIN_VALUE + 10000) {
			return Component.literal("-").append(ElectroTextUtils.gui("displayunit.inifinit.name")).append((isShort ? unit.symbol : unit.namePlural));
		} else if (value > Long.MAX_VALUE - 10000) {
			return ElectroTextUtils.gui("displayunit.inifinit.name").append((isShort ? unit.symbol : unit.namePlural));
		}
		Component unitName;
		if (isShort) {
			unitName = unit.symbol;
		} else if (value > 1.0D) {
			unitName = unit.namePlural;
		} else {
			unitName = unit.name;
		}

		if (value == 0.0D) {
			return Component.literal(value + "").append(unit.distanceFromValue).append(unitName);
		}

		for (MeasurementUnit measurement : MeasurementUnit.values()) {

			if (value < measurement.value) {

				if (measurement.ordinal() == 0) {
					return formatDecimals(measurement.process(value), decimalPlaces).append(unit.distanceFromValue).append(measurement.getName(isShort)).append(unitName);
				} else {
					measurement = MeasurementUnit.values()[measurement.ordinal() - 1];
					return formatDecimals(measurement.process(value), decimalPlaces).append(unit.distanceFromValue).append(measurement.getName(isShort)).append(unitName);
				}
			}
		}

		MeasurementUnit measurement = MeasurementUnit.values()[MeasurementUnit.values().length - 1];
		return formatDecimals(measurement.process(value), decimalPlaces).append(unit.distanceFromValue).append(measurement.getName(isShort)).append(unitName);
	}

	public static MutableComponent getChatDisplay(double value, DisplayUnit unit) {
		return getChatDisplay(value, unit, 2, false);
	}

	public static MutableComponent getChatDisplayShort(double value, DisplayUnit unit) {
		return getChatDisplay(value, unit, 2, true);
	}

	public static MutableComponent getDisplayShort(double value, DisplayUnit unit, int decimalPlaces) {
		return getChatDisplay(value, unit, decimalPlaces, true);
	}

	public static MutableComponent getChatDisplaySimple(double value, DisplayUnit unit, int decimalPlaces) {
		if (value > 1.0D) {

			if (decimalPlaces < 1) {
				return Component.literal((int) value + "").append(unit.distanceFromValue).append(unit.namePlural);
			}

			return formatDecimals(value, decimalPlaces).append(unit.distanceFromValue).append(unit.namePlural);
		}

		if (decimalPlaces < 1) {
			return Component.literal((int) value + "").append(unit.distanceFromValue).append(unit.name);
		}

		return formatDecimals(value, decimalPlaces).append(unit.distanceFromValue).append(unit.name);
	}

	public static double roundDecimals(double d, int decimalPlaces) {
		int j = (int) (d * Math.pow(10.0D, decimalPlaces));
		return j / Math.pow(10.0D, decimalPlaces);
	}

	public static MutableComponent formatDecimals(double d, int decimalPlaces) {
		DecimalFormat format = new DecimalFormat("0" + getDecimals(decimalPlaces));
		format.setRoundingMode(RoundingMode.HALF_EVEN);
		return Component.literal(format.format(roundDecimals(d, decimalPlaces)));
	}

	public static MutableComponent formatFluidMilibuckets(double amount) {

		if (amount > 1000) {

			return getChatDisplayShort(amount / 1000.0, DisplayUnit.BUCKETS);

		} else {
			return Component.literal(formatDecimals(amount, 2) + " mB");

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
