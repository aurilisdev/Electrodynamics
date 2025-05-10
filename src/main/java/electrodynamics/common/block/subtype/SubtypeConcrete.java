package electrodynamics.common.block.subtype;

import voltaic.api.ISubtype;

public enum SubtypeConcrete implements ISubtype {
	regular,
	tile,
	bricks;

	@Override
	public String tag() {
		return "concrete" + name();
	}

	@Override
	public String forgeTag() {
		return "concrete/" + name();
	}

	@Override
	public boolean isItem() {
		return false;
	}

}
