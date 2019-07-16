package physica.library.energy.base;

public enum Unit {
	RF(1.0, "Redstone Flux", "RF", ""), JOULES(2.5, "Joule", "J"), WATT(1, "Watt", "W"), WATTTICK(1, "WattTick", "Wt"), WATTSECOND(1.0 / 20.0, "WattSecond", "Ws"), WATTHOUR(1.0 / (20.0 * 60.0 * 60.0), "WattHour", "Wh");

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

	public String getPlural()
	{
		return name + plural;
	}
}
