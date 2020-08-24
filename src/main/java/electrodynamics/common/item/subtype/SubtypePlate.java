package electrodynamics.common.item.subtype;

import electrodynamics.api.subtype.Subtype;

public enum SubtypePlate implements Subtype {
	iron, steel, lead, bronze;

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
