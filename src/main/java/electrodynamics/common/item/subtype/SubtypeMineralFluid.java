package electrodynamics.common.item.subtype;

import electrodynamics.api.ISubtype;

public enum SubtypeMineralFluid implements ISubtype {
    copper, tin, silver, lead, vanadium, iron, gold, lithium;

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
