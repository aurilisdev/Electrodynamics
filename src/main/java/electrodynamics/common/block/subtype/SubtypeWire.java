package electrodynamics.common.block.subtype;

import electrodynamics.common.subtype.Subtype;

public enum SubtypeWire implements Subtype {
	tin(0.00645, 60, false), iron(0.0466, 100, false), copper(0.00806, 360, false), silver(0.00763, 600, false), gold(0.0117, 1000, false), superconductive(0.0, Long.MAX_VALUE, false), insulatedtin(0.00645, 60, true),
	insulatediron(0.0466, 100, true), insulatedcopper(0.00806, 360, true), insulatedsilver(0.00763, 600, true), insulatedgold(0.0117, 1000, true), insulatedsuperconductive(0.0, Long.MAX_VALUE, true);
	public final double resistance;
	public final long maxAmps;
	public final boolean insulated;

	private SubtypeWire(double resistance, long maxAmps, boolean insulated) {
		this.resistance = resistance;
		this.maxAmps = maxAmps;
		this.insulated = insulated;
	}

	@Override
	public String tag() {
		return "wire" + name();
	}

	@Override
	public String forgeTag() {
		return tag();
	}

	@Override
	public boolean isItem() {
		return false;
	}
}
