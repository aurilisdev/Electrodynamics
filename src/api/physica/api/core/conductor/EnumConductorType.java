package physica.api.core.conductor;

public enum EnumConductorType {
	copper(120, 15360), silver(240, 61440), gold(360, 138240), superconductor(-1, 307200);

	public String getName()
	{
		return name();
	}

	private int	voltage;
	private int	transferRate;

	private EnumConductorType(int voltage, int transferRate) {
		this.voltage = voltage;
		this.transferRate = transferRate;
	}

	public int getVoltage()
	{
		return voltage;
	}

	public int getTransferRate()
	{
		return transferRate;
	}

	public String asset()
	{
		return this == copper ? "wirecopper" : this == silver ? "wiresilver" : this == gold ? "wiregold" : this == superconductor ? "wiresuperconductor" : "wirecopper";
	}
}