package electrodynamics.common.item.subtype;

import electrodynamics.common.subtype.Subtype;

public enum SubtypePlate implements Subtype {
	iron, steel, lead;

	@Override
	public String tag() {
		return "plate" + name();
	}

	@Override
	public String forgeTag() {
		return "plates/" + name();
	}

	@Override
	public boolean isItem() {
		return true;
	}
}
