package electrodynamics.common.item.subtype;

import electrodynamics.api.ISubtype;

public enum SubtypeLeadCanister implements ISubtype{
	empty;

	@Override
	public String tag() {
		return "leadcanister" + name();
	}

	@Override
	public String forgeTag() {
		return "buckets/" + name();
	}

	@Override
	public boolean isItem() {
		return true;
	}

}
