package electrodynamics.common.item.subtype;

import electrodynamics.common.subtype.Subtype;

public enum SubtypeOxide implements Subtype {
	vanadium, disulfur, trisulfur;

	@Override
	public String tag() {
		return "oxide" + name();
	}

	@Override
	public String forgeTag() {
		return "oxide/" + name();
	}

	@Override
	public boolean isItem() {
		return true;
	}
}
