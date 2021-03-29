package electrodynamics.common.item.subtype;

import electrodynamics.api.ISubtype;

public enum SubtypeCrystal implements ISubtype {
    iron, gold, copper, tin, silver, lead, vanadium;

    @Override
    public String tag() {
	return "crystal" + name();
    }

    @Override
    public String forgeTag() {
	return "crystal/" + name();
    }

    @Override
    public boolean isItem() {
	return true;
    }
}
