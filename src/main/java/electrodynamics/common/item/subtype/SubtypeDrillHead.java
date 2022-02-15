package electrodynamics.common.item.subtype;

import electrodynamics.api.ISubtype;

public enum SubtypeDrillHead implements ISubtype {
	steel(200, false),
	stainlesssteel(400, false),
	hslasteel(600, false),
	titanium(1000, false),
	titaniumcarbide(1, true);

	public final int durability;
	public final boolean isUnbreakable;

	private SubtypeDrillHead(int durability, boolean unbreakable) {
		this.durability = durability;
		this.isUnbreakable = unbreakable;
	}

	@Override
	public String tag() {
		return "drillhead" + name();
	}

	@Override
	public String forgeTag() {
		return "drillhead/" + name();
	}

	@Override
	public boolean isItem() {
		return true;
	}

}
