package electrodynamics.common.item.subtype;

import electrodynamics.api.ISubtype;

public enum SubtypeCircuit implements ISubtype {
	basic, advanced, elite, ultimate;

	@Override
	public String tag() {
		return "circuit" + name();
	}

	@Override
	public String forgeTag() {
		return "circuits/" + name();
	}

	@Override
	public boolean isItem() {
		return true;
	}
}
