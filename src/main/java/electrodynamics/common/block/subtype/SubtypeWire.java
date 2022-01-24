package electrodynamics.common.block.subtype;

import java.util.Objects;

import electrodynamics.api.ISubtype;

// Calculated using https://www.omnicalculator.com/physics/wire-resistance
// Area is actually 0.125 = 15625mm^2
// Length is 1 meter + 2 meters of wire - 2 * center length -> 3 - 2 * 0.125 = 2.75 meters per wire block. this is static and isnt calculated dynamically even though
// wires are rarely connected in all directions.
// Also manipulated uniformally
public enum SubtypeWire implements ISubtype {
	tin(0.020064, 60, false, false, false, false), // Tin has 15% the conductivity of copper. Tin resistance = copper / 0.15
	iron(0.01709, 100, false, false, false, false),
	copper(0.0030096, 360, false, false, false, false), // annealed copper
	silver(0.0027984, 600, false, false, false, false),
	gold(0.004294, 1000, false, false, false, false),
	superconductive(0.0, Long.MAX_VALUE, false, false, false, false),
	// split between types
	insulatedtin(SubtypeWire.tin.resistance, 60, true, false, false, false),
	insulatediron(SubtypeWire.iron.resistance, 100, true, false, false, false),
	insulatedcopper(SubtypeWire.copper.resistance, 360, true, false, false, false),
	insulatedsilver(SubtypeWire.silver.resistance, 600, true, false, false, false),
	insulatedgold(SubtypeWire.gold.resistance, 1000, true, false, false, false),
	insulatedsuperconductive(0.0, Long.MAX_VALUE, true, false, false, false),
	// split between types
	highlyinsulatedtin(SubtypeWire.tin.resistance / 4.0, 180, true, false, false, true),
	highlyinsulatediron(SubtypeWire.iron.resistance / 4.0, 300, true, false, false, true),
	highlyinsulatedcopper(SubtypeWire.copper.resistance / 4.0, 1080, true, false, false, true),
	highlyinsulatedsilver(SubtypeWire.silver.resistance / 4.0, 1800, true, false, false, true),
	highlyinsulatedgold(SubtypeWire.gold.resistance / 4.0, 3000, true, false, false, true),
	highlyinsulatedsuperconductive(0.0, Long.MAX_VALUE, true, false, false, true),
	// split between types
	ceramicinsulatedtin(SubtypeWire.tin.resistance, 60, true, false, true, false),
	ceramicinsulatediron(SubtypeWire.iron.resistance, 100, true, false, true, false),
	ceramicinsulatedcopper(SubtypeWire.copper.resistance, 360, true, false, true, false),
	ceramicinsulatedsilver(SubtypeWire.silver.resistance, 600, true, false, true, false),
	ceramicinsulatedgold(SubtypeWire.gold.resistance, 1000, true, false, true, false),
	ceramicinsulatedsuperconductive(0.0, Long.MAX_VALUE, true, false, true, false),
	// split between types
	logisticstin(SubtypeWire.tin.resistance, 60, true, true, false, false),
	logisticsiron(SubtypeWire.iron.resistance, 100, true, true, false, false),
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

	public static SubtypeWire getUninsulatedWire(SubtypeWire type) {
		String wanted = type.name().replace("logistics", "").replace("ceramic", "").replace("highly", "").replace("insulated", "");
		for (SubtypeWire wire : values()) {
			if (Objects.equals(wire.name(), wanted)) {
				return wire;
			}
		}
		return type;
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
