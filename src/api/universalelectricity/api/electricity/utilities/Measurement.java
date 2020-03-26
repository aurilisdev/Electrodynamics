package universalelectricity.api.electricity.utilities;

public enum Measurement {
	MILLI("Milli", "m", 0.001D), KILO("Kilo", "k", 1E3), MEGA("Mega", "M", 1.0E6), GIGA("Giga", "G", 1.0E9);

	public String name;
	public String symbol;
	public double value;

	private Measurement(String name, String symbol, double value) {
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

	public int integerValue() {
		return (int) value;
	}

	public double process(double value) {
		return value / this.value;
	}
}
