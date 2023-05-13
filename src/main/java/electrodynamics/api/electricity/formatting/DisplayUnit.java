package electrodynamics.api.electricity.formatting;

public enum DisplayUnit {
	AMPERE("Amp", "A"),
	AMP_HOUR("Amp Hour", "Ah"),
	VOLTAGE("Volt", "V"),
	WATT("Watt", "W"),
	WATT_HOUR("Watt Hour", "Wh"),
	RESISTANCE("Ohm", "Ω"),
	CONDUCTANCE("Siemen", "S"),
	JOULES("Joule", "J"),
	BUCKETS("Bucket", "B"),
	TEMPERATURE_KELVIN("Kelvin", "K"),
	TEMPERATURE_CELCIUS("Celcius", "C"),
	TEMPERATURE_FAHRENHEIT("Fahrenheit", "F"),
	TIME_SECONDS("Second", "s"),
	PRESSURE_ATM("Pressure", "ATM"),
	PERCENTAGE("Pecent", "%", "");

	public final String symbol;
	public final String name;
	public final String distanceFromValue;

	DisplayUnit(String name, String symbol) {
		this(name, symbol, " ");
	}
	
	DisplayUnit(String name, String symbol, String distanceFromValue) {
		this.name = name;
		this.symbol = symbol;
		this.distanceFromValue = distanceFromValue;
	}

	public String getPlural() {
		return name + "s";
	}
}
