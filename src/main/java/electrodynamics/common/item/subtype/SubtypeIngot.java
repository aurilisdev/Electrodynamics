package electrodynamics.common.item.subtype;

import electrodynamics.api.ISubtype;

public enum SubtypeIngot implements ISubtype {
	tin,
	silver,
	steel,
	lead,
	superconductive,
	bronze,
	vanadium,
	lithium,
	aluminum,
	chromium,
	stainlesssteel,
	vanadiumsteel,
	hslasteel,
	titanium,
	molybdenum,
	titaniumcarbide;

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
