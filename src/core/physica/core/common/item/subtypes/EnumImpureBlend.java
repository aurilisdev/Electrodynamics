package physica.core.common.item.subtypes;

public enum EnumImpureBlend {
	iron("impureDustIron"), gold("impureDustGold"), copper("impureDustCopper"), tin("impureDustTin"),
	silver("impureDustSilver"), lead("impureDustLead"), vanadium("impureDustVanadium");

	private String ore;

	private EnumImpureBlend(String ore) {
		this.ore = ore;
	}

	public String getOre() {
		return ore;
	}
}
