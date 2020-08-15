package electrodynamics.common.item.subtype;

import electrodynamics.common.subtype.Subtype;

public enum SubtypeImpureDust implements Subtype {
	iron, gold, copper, tin, silver, lead, vanadium;

	@Override
	public String tag() {
		return "impuredust" + name();
	}

	@Override
	public String forgeTag() {
		return "impuredusts/" + name();
	}

	@Override
	public boolean isItem() {
		return true;
	}
}
