package electrodynamics.api.formatting;

public enum ElectricUnit {
	AMPERE("Amp", "A"), AMP_HOUR("Amp Hour", "Ah"), VOLTAGE("Volt", "V"), WATT("Watt", "W"), WATT_HOUR("Watt Hour", "Wh"), RESISTANCE("Ohm", "Ω"), CONDUCTANCE("Siemen", "S"), JOULES("Joule", "J");

	public String symbol;

	public String name;

	ElectricUnit(String name, String symbol) {
		this.name = name;
		this.symbol = symbol;
	}

	public String getPlural() {
		return name + "s";
	}
}
