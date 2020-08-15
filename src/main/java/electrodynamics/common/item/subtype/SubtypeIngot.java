package electrodynamics.common.item.subtype;

import electrodynamics.common.subtype.Subtype;

public enum SubtypeIngot implements Subtype {
	copper, tin, silver, steel, lead, superconductive, bronze, vanadium;

	@Override
	public String tag() {
		return "ingot" + name();
	}

	@Override
	public String forgeTag() {
		return "ingots/" + name();
	}

	@Override
	public boolean isItem() {
		return true;
	}
}
