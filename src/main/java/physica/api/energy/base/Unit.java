package physica.api.energy.base;

public enum Unit
{
	RF(1.0, "Redstone Flux", "RF", ""), JOULES(0.4, "Joule", "J"), WATT(0.4, "Watt", "W");

	public String	name;
	public String	symbol;
	public String	plural;
	public double	ratio;

	private Unit(double ratio, String name, String symbol) {
		this(ratio, name, symbol, "s");
	}

	private Unit(double ratio, String name, String symbol, String plural) {
		this.name = name;
		this.symbol = symbol;
		this.plural = plural;
		this.ratio = ratio;
	}

	public String getPlural() {
		return name + plural;
	}
}
