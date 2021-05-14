package electrodynamics.common.block.subtype;

import electrodynamics.api.ISubtype;

// Calculated using https://www.omnicalculator.com/physics/wire-resistance
// Also manipulated uniformally
public enum SubtypeWire implements ISubtype {
    tin(0.02024 * 5.0, 60, false, false, false), iron(0.017866 * 5.0, 100, false, false, false), copper(0.0031464 * 5.0, 360, false, false, false),
    silver(0.0029256 * 5.0, 600, false, false, false), gold(0.00449 * 5.0, 1000, false, false, false),
    superconductive(0.0, Long.MAX_VALUE, false, false, false),
    // split between types
    insulatedtin(SubtypeWire.tin.resistance, 60, true, false, false), insulatediron(SubtypeWire.iron.resistance, 100, true, false, false),
    insulatedcopper(SubtypeWire.copper.resistance, 360, true, false, false), insulatedsilver(SubtypeWire.silver.resistance, 600, true, false, false),
    insulatedgold(SubtypeWire.gold.resistance, 1000, true, false, false), insulatedsuperconductive(0.0, Long.MAX_VALUE, true, false, false),
    // split between types
    ceramicinsulatedtin(SubtypeWire.tin.resistance, 60, true, false, true), ceramicinsulatediron(SubtypeWire.iron.resistance, 100, true, false, true),
    ceramicinsulatedcopper(SubtypeWire.copper.resistance, 360, true, false, true),
    ceramicinsulatedsilver(SubtypeWire.silver.resistance, 600, true, false, true),
    ceramicinsulatedgold(SubtypeWire.gold.resistance, 1000, true, false, true),
    ceramicinsulatedsuperconductive(0.0, Long.MAX_VALUE, true, false, true),
    // split between types
    logisticstin(SubtypeWire.tin.resistance, 60, true, true, false), logisticsiron(SubtypeWire.iron.resistance, 100, true, true, false),
    logisticscopper(SubtypeWire.copper.resistance, 360, true, true, false), logisticssilver(SubtypeWire.silver.resistance, 600, true, true, false),
    logisticsgold(SubtypeWire.gold.resistance, 1000, true, true, false), logisticssuperconductive(0.0, Long.MAX_VALUE, true, true, false);
    // split between types

    public final double resistance;
    public final long capacity;
    public final boolean insulated;
    public final boolean logistical;
    public final boolean ceramic;

    private SubtypeWire(double resistance, long capacity, boolean insulated, boolean logistical, boolean ceramic) {
	this.resistance = resistance;
	this.capacity = capacity;
	this.insulated = insulated;
	this.logistical = logistical;
	this.ceramic = ceramic;
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
