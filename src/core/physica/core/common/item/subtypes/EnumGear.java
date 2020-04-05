package physica.core.common.item.subtypes;

public enum EnumGear {
	iron("gearIron"), copper("gearCopper"), tin("gearTin"), steel("gearSteel"), bronze("gearBronze");

	private String ore;

	private EnumGear(String ore) {
		this.ore = ore;
	}

	public String getOre() {
		return ore;
	}
}
