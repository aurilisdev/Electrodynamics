package electrodynamics.api.electricity.formatting;

public enum MeasurementUnit {
	PICO("Pico", "p", 1.0E-12D),
	NANO("Nano", "n", 1.0E-9D),
	MICRO("Micro", '\u00B5' + "", 1.0E-6D), //unicode for micro
	MILLI("Milli", "m", 1.0E-3D),
	NONE("", "", 1.0),
	KILO("Kilo", "k", 1.0E3D),
	MEGA("Mega", "M", 1.0E6D),
	GIGA("Giga", "G", 1.0E9D);

	final double value;

	final String symbol;
	final String name;

	MeasurementUnit(String name, String symbol, double value) {
		this.name = name;
		this.symbol = symbol;
		this.value = value;
	}

	public String getName(boolean isSymbol) {
		if (isSymbol) {
			return symbol;
		}

		return name;
	}

	public double process(double value) {
		return value / this.value;
	}
}