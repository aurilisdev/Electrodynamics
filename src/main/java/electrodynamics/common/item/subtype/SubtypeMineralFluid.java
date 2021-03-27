package electrodynamics.common.item.subtype;

import electrodynamics.api.ISubtype;

public enum SubtypeMineralFluid implements ISubtype {
    copper, tin, silver, lead, vanadium, iron, gold;

    @Override
    public String tag() {
	return "ingot" + name();
    }

    @Override
    public String forgeTag() {
	return "ingots/" + name();
    }

    @Override
    public boolean isItem() {
	return true;
    }
}
