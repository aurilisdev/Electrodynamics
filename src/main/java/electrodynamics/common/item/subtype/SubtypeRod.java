package electrodynamics.common.item.subtype;

import electrodynamics.api.ISubtype;

public enum SubtypeRod implements ISubtype{
	steel, stainlesssteel,hslasteel,titaniumcarbide;

	@Override
	public String tag() {
		return "rod" + name();
	}

	@Override
	public String forgeTag() {
		return "rods/" + name();
	}

	@Override
	public boolean isItem() {
		return true;
	}

}
