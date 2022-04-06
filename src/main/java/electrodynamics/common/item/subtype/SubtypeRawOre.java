package electrodynamics.common.item.subtype;

import electrodynamics.api.ISubtype;

public enum SubtypeRawOre implements ISubtype {
	silver,
	lead,
	tin,
	chromium,
	titanium,
	vanadinite,
	lepidolite,
	fluorite,
	uranium;

	@Override
	public String tag() {
		return "rawore" + name();
	}

	@Override
	public String forgeTag() {
		return "rawore/" + name();
	}

	@Override
	public boolean isItem() {
		return true;
	}
}
