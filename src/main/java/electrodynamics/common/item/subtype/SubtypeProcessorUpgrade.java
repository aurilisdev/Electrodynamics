package electrodynamics.common.item.subtype;

import electrodynamics.api.subtype.Subtype;

public enum SubtypeProcessorUpgrade implements Subtype {
	basiccapacity(1, 1.5), basicspeed(1.5, 1), advancedcapacity(1, 2.25), advancedspeed(2.25, 1);

	public final double speedMultiplier;
	public final double capacityMultiplier;

	private SubtypeProcessorUpgrade(double speedMultiplier, double capacityMultiplier) {
		this.speedMultiplier = speedMultiplier;
		this.capacityMultiplier = capacityMultiplier;
	}

	@Override
	public String tag() {
		return "processorupgrade" + name();
	}

	@Override
	public String forgeTag() {
		return "processorupgrade/" + name();
	}

	@Override
	public boolean isItem() {
		return true;
	}
}
