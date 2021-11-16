package electrodynamics.api.electricity.formatting;

public enum MeasurementUnit {
    MICRO("Micro", "mi", 1.0E-6D),
    MILLI("Milli", "m", 0.001D),
    KILO("Kilo", "k", 1000.0D),
    MEGA("Mega", "M", 1000000.0D);

    protected double value;

    protected String symbol;
    protected String name;

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