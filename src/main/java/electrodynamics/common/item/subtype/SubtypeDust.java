package electrodynamics.common.item.subtype;

import electrodynamics.api.ISubtype;

public enum SubtypeDust implements ISubtype {
	iron, gold, copper, tin, silver, steel, lead, bronze, superconductive, endereye, vanadium, sulfur, niter, obsidian, lithium, salt, silica,
	molybdenum;

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
