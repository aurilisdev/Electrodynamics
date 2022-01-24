package electrodynamics.common.item.subtype;

import electrodynamics.api.ISubtype;

public enum SubtypeCeramic implements ISubtype {
	wet,
	cooked,
	plate,
	fuse;

	@Override
	public String tag() {
		return "ceramic" + name();
	}

	@Override
	public String forgeTag() {
		return "ceramic/" + name();
	}

	@Override
	public boolean isItem() {
		return true;
	}
}
