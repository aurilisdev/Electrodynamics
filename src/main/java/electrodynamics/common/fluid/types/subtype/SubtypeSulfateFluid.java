package electrodynamics.common.fluid.types.subtype;

import electrodynamics.api.ISubtype;

public enum SubtypeSulfateFluid implements ISubtype {
	copper, tin, silver, lead, vanadium, iron, gold, lithium, molybdenum;

	@Override
	public String tag() {
		return "fluid" + name();
	}

	@Override
	public String forgeTag() {
		return "fluid/" + name();
	}

	@Override
	public boolean isItem() {
		return false;
	}
}
