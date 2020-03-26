package physica.item.subtypes;

public enum EnumPlate {
	iron("plateIron"), steel("plateSteel"), lead("plateLead");

	private String ore;

	private EnumPlate(String ore) {
		this.ore = ore;
	}

	public String getOre() {
		return ore;
	}
}
