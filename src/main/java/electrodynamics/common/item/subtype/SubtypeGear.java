package electrodynamics.common.item.subtype;

import electrodynamics.api.ISubtype;

public enum SubtypeGear implements ISubtype {
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
