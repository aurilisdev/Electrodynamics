package electrodynamics.common.item.subtype;

import electrodynamics.api.ISubtype;

public enum SubtypePlate implements ISubtype {
    iron, steel, lead, bronze, lithium, stainlesssteel, vanadiumsteel, titanium, aluminum, hslasteel,titaniumcarbide;

    @Override
    public String tag() {
	return "plate" + name();
    }

    @Override
    public String forgeTag() {
	return "plates/" + name();
    }

    @Override
    public boolean isItem() {
	return true;
    }
}
