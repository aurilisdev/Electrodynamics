package physica.core.common.item.subtypes;

public enum EnumImpureBlend {
	iron("dustIron"), gold("dustGold"), copper("dustCopper"), tin("dustTin"), silver("dustSilver"), lead("dustLead");

	private String ore;

	private EnumImpureBlend(String ore) {
		this.ore = ore;
	}

	public String getOre() {
		return ore;
	}
}
