package electrodynamics.common.item.subtype;

import electrodynamics.api.ISubtype;

public enum SubtypeImpureDust implements ISubtype {
    iron, gold, copper, tin, silver, lead, vanadium, lithium, molybdenum;

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
