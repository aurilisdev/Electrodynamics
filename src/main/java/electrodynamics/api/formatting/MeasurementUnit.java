package electrodynamics.api.formatting;

public enum MeasurementUnit {
    MICRO("Micro", "mi", 1.0E-6D), MILLI("Milli", "m", 0.001D), KILO("Kilo", "k", 1000.0D),
    MEGA("Mega", "M", 1000000.0D);

    public double value;

    public String symbol;
    public String name;

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