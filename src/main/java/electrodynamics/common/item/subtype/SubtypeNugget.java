package electrodynamics.common.item.subtype;

import electrodynamics.api.ISubtype;

public enum SubtypeNugget implements ISubtype {
	tin,copper,silver,superconductive,steel,stainlesssteel,hslasteel,titaniumcarbide;

	@Override
	public String tag() {
		return "nugget" + name();
	}

	@Override
	public String forgeTag() {
		return "nuggets/" + name();
	}

	@Override
	public boolean isItem() {
		return true;
	}
	

}
