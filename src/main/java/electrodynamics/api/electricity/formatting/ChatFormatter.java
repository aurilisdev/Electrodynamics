package electrodynamics.api.electricity.formatting;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;

public class ChatFormatter {

	public static IFormattableTextComponent getChatDisplay(double value, IDisplayUnit unit, int decimalPlaces, boolean isShort) {
		if (value < Long.MIN_VALUE + 10000) {
			return new StringTextComponent("-").append(ElectroTextUtils.gui("displayunit.infinity.name")).append(" ").append((isShort ? unit.getSymbol() : unit.getNamePlural()));
		}
		if (value > Long.MAX_VALUE - 10000) {
			return ElectroTextUtils.gui("displayunit.infinity.name").append(" ").append((isShort ? unit.getSymbol() : unit.getNamePlural()));
		}
		IFormattableTextComponent unitName;
		if (isShort) {
			unitName = unit.getSymbol();
		} else if (value > 1.0D) {
			unitName = unit.getNamePlural();
		} else {
			unitName = unit.getName();
		}

		if (value == 0.0D) {
			return new StringTextComponent(value + "").append(unit.getDistanceFromValue()).append(unitName);
		}

		for (MeasurementUnit measurement : MeasurementUnit.values()) {

			if (value < measurement.getValue()) {

				if (measurement.ordinal() == 0) {
					return formatDecimals(measurement.process(value), decimalPlaces).append(unit.getDistanceFromValue()).append(measurement.getName(isShort)).append(unitName);
				}
				measurement = MeasurementUnit.values()[measurement.ordinal() - 1];
				return formatDecimals(measurement.process(value), decimalPlaces).append(unit.getDistanceFromValue()).append(measurement.getName(isShort)).append(unitName);
			}
		}

		MeasurementUnit measurement = MeasurementUnit.values()[MeasurementUnit.values().length - 1];
		return formatDecimals(measurement.process(value), decimalPlaces).append(unit.getDistanceFromValue()).append(measurement.getName(isShort)).append(unitName);
	}

	public static IFormattableTextComponent getChatDisplay(double value, IDisplayUnit unit) {
		return getChatDisplay(value, unit, 3, false);
	}

	public static IFormattableTextComponent getChatDisplayShort(double value, IDisplayUnit unit) {
		return getChatDisplay(value, unit, 3, true);
	}

	public static IFormattableTextComponent getDisplayShort(double value, IDisplayUnit unit, int decimalPlaces) {
		return getChatDisplay(value, unit, decimalPlaces, true);
	}

	public static IFormattableTextComponent getChatDisplaySimple(double value, IDisplayUnit unit, int decimalPlaces) {
		if (value > 1.0D) {

			if (decimalPlaces < 1) {
				return new StringTextComponent((int) value + "").append(unit.getDistanceFromValue()).append(unit.getNamePlural());
			}

			return formatDecimals(value, decimalPlaces).append(unit.getDistanceFromValue()).append(unit.getNamePlural());
		}

		if (decimalPlaces < 1) {
			return new StringTextComponent((int) value + "").append(unit.getDistanceFromValue()).append(unit.getName());
		}

		return formatDecimals(value, decimalPlaces).append(unit.getDistanceFromValue()).append(unit.getName());
	}

	public static double roundDecimals(double d, int decimalPlaces) {
		int j = (int) (d * Math.pow(10.0D, decimalPlaces));
		return j / Math.pow(10.0D, decimalPlaces);
	}

	public static IFormattableTextComponent formatDecimals(double d, int decimalPlaces) {
		DecimalFormat format = new DecimalFormat("0" + getDecimals(decimalPlaces));
		format.setRoundingMode(RoundingMode.HALF_EVEN);
		return new StringTextComponent(format.format(roundDecimals(d, decimalPlaces)));
	}

	public static IFormattableTextComponent formatFluidMilibuckets(double amount) {

		return getChatDisplayShort(amount / 1000.0, DisplayUnit.BUCKETS);

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