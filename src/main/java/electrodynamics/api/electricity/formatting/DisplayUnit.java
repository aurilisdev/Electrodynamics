package electrodynamics.api.electricity.formatting;

public enum DisplayUnit {
	AMPERE("Amp", "A"), AMP_HOUR("Amp Hour", "Ah"), VOLTAGE("Volt", "V"), WATT("Watt", "W"), WATT_HOUR("Watt Hour", "Wh"), RESISTANCE("Ohm", "Ω"),
	CONDUCTANCE("Siemen", "S"), JOULES("Joule", "J"), BUCKETS("Bucket", "B");

	protected String symbol;

	protected String name;

	DisplayUnit(String name, String symbol) {
		this.name = name;
		this.symbol = symbol;
	}

	public String getPlural() {
		return name + "s";
	}
}
