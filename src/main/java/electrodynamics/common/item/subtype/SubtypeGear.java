package electrodynamics.common.item.subtype;

import electrodynamics.common.subtype.Subtype;

public enum SubtypeGear implements Subtype {
	iron, copper, tin, steel, bronze;

	@Override
	public String tag() {
		return "gear" + name();
	}

	@Override
	public String forgeTag() {
		return "gears/" + name();
	}

	@Override
	public boolean isItem() {
		return true;
	}
}
