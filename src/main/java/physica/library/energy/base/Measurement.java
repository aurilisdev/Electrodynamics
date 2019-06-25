package physica.library.energy.base;

public enum Measurement {
	MICRO("Micro", "µ", 1.0E-6D), MILLI("Milli", "m", 0.001D), KILO("Kilo", "k", 1000.0D), MEGA("Mega", "M", 1000000.0D), GIGA("Giga", "G", 1000000000.0D);

	public String name;
	public String symbol;
	public double value;

	private Measurement(String name, String symbol, double value) {
		this.name = name;
		this.symbol = symbol;
		this.value = value;
	}

	public String getName(boolean isSymbol)
	{
		if (isSymbol)
		{
			return symbol;
		}
		return name;
	}

	public double process(double value)
	{
		return value / this.value;
	}
}
