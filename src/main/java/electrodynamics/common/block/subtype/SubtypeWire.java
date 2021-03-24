package electrodynamics.common.block.subtype;

import electrodynamics.api.ISubtype;

// Calculated using https://www.omnicalculator.com/physics/wire-resistance
// Length defined as if all sides are connected. This number was found to be 2.875 m
// Cross-area is defined as 100 mm^2 to be more realistic wires
// To make resistance relevant we make it reduce power amount by 1000 times it normal values
public enum SubtypeWire implements ISubtype {
    tin(3.036, 60, false, false), iron(2.6799, 100, false, false), copper(0.47196, 360, false, false), silver(0.43884, 600, false, false),
    gold(0.6735, 1000, false, false), superconductive(0.0, Long.MAX_VALUE, false, false), insulatedtin(SubtypeWire.tin.resistance, 60, true, false),
    insulatediron(SubtypeWire.iron.resistance, 100, true, false), insulatedcopper(SubtypeWire.copper.resistance, 360, true, false),
    insulatedsilver(SubtypeWire.silver.resistance, 600, true, false), insulatedgold(SubtypeWire.gold.resistance, 1000, true, false),
    insulatedsuperconductive(0.0, Long.MAX_VALUE, true, false), logisticstin(SubtypeWire.tin.resistance, 60, true, true),
    logisticsiron(SubtypeWire.iron.resistance, 100, true, true), logisticscopper(SubtypeWire.copper.resistance, 360, true, true),
    logisticssilver(SubtypeWire.silver.resistance, 600, true, true), logisticsgold(SubtypeWire.gold.resistance, 1000, true, true),
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
