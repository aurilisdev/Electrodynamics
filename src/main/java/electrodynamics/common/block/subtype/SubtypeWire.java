package electrodynamics.common.block.subtype;

import electrodynamics.api.ISubtype;

// Calculated using https://www.omnicalculator.com/physics/wire-resistance
// Also manipulated uniformally
public enum SubtypeWire implements ISubtype {
    tin(0.02024 * 5.0, 60, false, false, false, false), iron(0.017866 * 5.0, 100, false, false, false, false),
    copper(0.0031464 * 5.0, 360, false, false, false, false), silver(0.0029256 * 5.0, 600, false, false, false, false),
    gold(0.00449 * 5.0, 1000, false, false, false, false), superconductive(0.0, Long.MAX_VALUE, false, false, false, false),
    // split between types
    insulatedtin(SubtypeWire.tin.resistance, 60, true, false, false, false),
    insulatediron(SubtypeWire.iron.resistance, 100, true, false, false, false),
    insulatedcopper(SubtypeWire.copper.resistance, 360, true, false, false, false),
    insulatedsilver(SubtypeWire.silver.resistance, 600, true, false, false, false),
    insulatedgold(SubtypeWire.gold.resistance, 1000, true, false, false, false),
    insulatedsuperconductive(0.0, Long.MAX_VALUE, true, false, false, false),
    // split between types
    highlyinsulatedtin(SubtypeWire.tin.resistance / 3.0, 180, true, false, false, true),
    highlyinsulatediron(SubtypeWire.iron.resistance / 3.0, 300, true, false, false, true),
    highlyinsulatedcopper(SubtypeWire.copper.resistance / 3.0, 1080, true, false, false, true),
    highlyinsulatedsilver(SubtypeWire.silver.resistance / 3.0, 1800, true, false, false, true),
    highlyinsulatedgold(SubtypeWire.gold.resistance / 3.0, 3000, true, false, false, true),
    highlyinsulatedsuperconductive(0.0, Long.MAX_VALUE, true, false, false, true),
    // split between types
    ceramicinsulatedtin(SubtypeWire.tin.resistance, 60, true, false, true, false),
    ceramicinsulatediron(SubtypeWire.iron.resistance, 100, true, false, true, false),
    ceramicinsulatedcopper(SubtypeWire.copper.resistance, 360, true, false, true, false),
    ceramicinsulatedsilver(SubtypeWire.silver.resistance, 600, true, false, true, false),
    ceramicinsulatedgold(SubtypeWire.gold.resistance, 1000, true, false, true, false),
    ceramicinsulatedsuperconductive(0.0, Long.MAX_VALUE, true, false, true, false),
    // split between types
    logisticstin(SubtypeWire.tin.resistance, 60, true, true, false, false), logisticsiron(SubtypeWire.iron.resistance, 100, true, true, false, false),
    logisticscopper(SubtypeWire.copper.resistance, 360, true, true, false, false),
    logisticssilver(SubtypeWire.silver.resistance, 600, true, true, false, false),
    logisticsgold(SubtypeWire.gold.resistance, 1000, true, true, false, false),
    logisticssuperconductive(0.0, Long.MAX_VALUE, true, true, false, false);
    // split between types

    public final double resistance;
    public final long capacity;
    public final boolean insulated;
    public final boolean highlyinsulated;
    public final boolean logistical;
    public final boolean ceramic;

    SubtypeWire(double resistance, long capacity, boolean insulated, boolean logistical, boolean ceramic, boolean highlyinsulated) {
	this.resistance = resistance;
	this.capacity = capacity;
	this.insulated = insulated;
	this.logistical = logistical;
	this.ceramic = ceramic;
	this.highlyinsulated = highlyinsulated;
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
