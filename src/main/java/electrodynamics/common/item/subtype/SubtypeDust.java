package electrodynamics.common.item.subtype;

import electrodynamics.api.subtype.Subtype;

public enum SubtypeDust implements Subtype {
	iron, gold, copper, tin, silver, steel, lead, bronze, superconductive, endereye, vanadium, sulfur, obsidian;

	@Override
	public String tag() {
		return "dust" + name();
	}

	@Override
	public String forgeTag() {
		return "dusts/" + name();
	}

	@Override
	public boolean isItem() {
		return true;
	}
}
