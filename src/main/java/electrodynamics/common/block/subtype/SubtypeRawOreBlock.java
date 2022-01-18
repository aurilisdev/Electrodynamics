package electrodynamics.common.block.subtype;

import electrodynamics.api.ISubtype;

public enum SubtypeRawOreBlock implements ISubtype {
	tin, lead, silver;

	@Override
	public String tag() {
		return "raworeblock" + name();
	}

	@Override
	public String forgeTag() {
		return "raworeblocks/" + name();
	}

	@Override
	public boolean isItem() {
		return false;
	}

}
