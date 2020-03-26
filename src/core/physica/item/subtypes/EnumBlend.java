package physica.item.subtypes;

public enum EnumBlend {
	iron("dustIron"), gold("dustGold"), copper("dustCopper"), tin("dustTin"), silver("dustSilver"), steel("dustSteel"), lead("dustLead"), bronze("dustBronze"), superconductive("dustSuperConductive");

	private String ore;

	private EnumBlend(String ore) {
		this.ore = ore;
	}

	public String getOre()
	{
		return ore;
	}
}
