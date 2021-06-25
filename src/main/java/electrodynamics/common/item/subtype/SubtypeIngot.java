package electrodynamics.common.item.subtype;

import electrodynamics.api.ISubtype;

public enum SubtypeIngot implements ISubtype {
    copper, tin, silver, steel, lead, superconductive, bronze, vanadium, lithium;

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
