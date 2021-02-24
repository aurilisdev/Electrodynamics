package electrodynamics.common.block.subtype;

import electrodynamics.api.subtype.Subtype;

public enum SubtypeWire implements Subtype {
    tin(0.00645, 60, false, false), iron(0.0466, 100, false, false), copper(0.00806, 360, false, false),
    silver(0.00763, 600, false, false), gold(0.0117, 1000, false, false),
    superconductive(0.0, Long.MAX_VALUE, false, false), insulatedtin(0.00645, 60, true, false),
    insulatediron(0.0466, 100, true, false), insulatedcopper(0.00806, 360, true, false),
    insulatedsilver(0.00763, 600, true, false), insulatedgold(0.0117, 1000, true, false),
    insulatedsuperconductive(0.0, Long.MAX_VALUE, true, false), logisticstin(0.00645, 60, true, true),
    logisticsiron(0.0466, 100, true, true), logisticscopper(0.00806, 360, true, true),
    logisticssilver(0.00763, 600, true, true), logisticsgold(0.0117, 1000, true, true),
    logisticssuperconductive(0.0, Long.MAX_VALUE, true, true);

    public final double resistance;
    public final long maxAmps;
    public final boolean insulated;
    public final boolean logistical;

    private SubtypeWire(double resistance, long maxAmps, boolean insulated, boolean emitsredstone) {
	this.resistance = resistance;
	this.maxAmps = maxAmps;
	this.insulated = insulated;
	logistical = emitsredstone;
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
