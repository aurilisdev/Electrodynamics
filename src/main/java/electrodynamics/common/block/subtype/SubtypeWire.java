package electrodynamics.common.block.subtype;

import java.util.Objects;

import electrodynamics.api.ISubtype;

// Calculated using https://www.omnicalculator.com/physics/wire-resistance
// Area is actually 0.125 = 15625mm^2
// Length is 1 meter + 2 meters of wire - 2 * center length -> 3 - 2 * 0.125 = 2.75 meters per wire block. this is static and isnt calculated dynamically even though
// wires are rarely connected in all directions.
// Also manipulated uniformally
public enum SubtypeWire implements ISubtype {
	tin(0.020064, 60, false, false, false, false, 0), // Tin has 15% the conductivity of copper. Tin resistance = copper / 0.15
	iron(0.01709, 100, false, false, false, false, 0),
	copper(0.0030096, 360, false, false, false, false, 0), // annealed copper
	silver(0.0027984, 600, false, false, false, false, 0),
	gold(0.004294, 1000, false, false, false, false, 0),
	superconductive(0.0, Long.MAX_VALUE, false, false, false, false, 0),
	// split between types
	insulatedtin(SubtypeWire.tin.resistance, 60, true, false, false, false, 1),
	insulatediron(SubtypeWire.iron.resistance, 100, true, false, false, false, 1),
	insulatedcopper(SubtypeWire.copper.resistance, 360, true, false, false, false, 1),
	insulatedsilver(SubtypeWire.silver.resistance, 600, true, false, false, false, 1),
	insulatedgold(SubtypeWire.gold.resistance, 1000, true, false, false, false, 1),
	insulatedsuperconductive(0.0, Long.MAX_VALUE, true, false, false, false, 1),
	// split between types
	highlyinsulatedtin(SubtypeWire.tin.resistance / 4.0, 180, true, false, false, true, 4),
	highlyinsulatediron(SubtypeWire.iron.resistance / 4.0, 300, true, false, false, true, 4),
	highlyinsulatedcopper(SubtypeWire.copper.resistance / 4.0, 1080, true, false, false, true, 4),
	highlyinsulatedsilver(SubtypeWire.silver.resistance / 4.0, 1800, true, false, false, true, 4),
	highlyinsulatedgold(SubtypeWire.gold.resistance / 4.0, 3000, true, false, false, true, 4),
	highlyinsulatedsuperconductive(0.0, Long.MAX_VALUE, true, false, false, true, 4),
	// split between types
	ceramicinsulatedtin(SubtypeWire.tin.resistance, 60, true, false, true, false, 3),
	ceramicinsulatediron(SubtypeWire.iron.resistance, 100, true, false, true, false, 3),
	ceramicinsulatedcopper(SubtypeWire.copper.resistance, 360, true, false, true, false, 3),
	ceramicinsulatedsilver(SubtypeWire.silver.resistance, 600, true, false, true, false, 3),
	ceramicinsulatedgold(SubtypeWire.gold.resistance, 1000, true, false, true, false, 3),
	ceramicinsulatedsuperconductive(0.0, Long.MAX_VALUE, true, false, true, false, 3),
	// split between types
	logisticstin(SubtypeWire.tin.resistance, 60, true, true, false, false, 2),
	logisticsiron(SubtypeWire.iron.resistance, 100, true, true, false, false, 2),
	logisticscopper(SubtypeWire.copper.resistance, 360, true, true, false, false, 2),
	logisticssilver(SubtypeWire.silver.resistance, 600, true, true, false, false, 2),
	logisticsgold(SubtypeWire.gold.resistance, 1000, true, true, false, false, 2),
	logisticssuperconductive(0.0, Long.MAX_VALUE, true, true, false, false, 2);
	// split between types

	public final double resistance;
	public final long capacity;
	public final boolean insulated;
	public final boolean highlyinsulated;
	public final boolean logistical;
	public final boolean ceramic;
	//0 = uninsulated, 1 = insulated, 2 = logistical, 3 = ceramic, 4 = heavily insulated
	//useful for extracting the different subclasses of wire easily
	//we should look at using this to possibly simplify the class 
	public final int wireType;

	SubtypeWire(double resistance, long capacity, boolean insulated, boolean logistical, boolean ceramic, boolean highlyinsulated, int wireType) {
		this.resistance = resistance;
		this.capacity = capacity;
		this.insulated = insulated;
		this.logistical = logistical;
		this.ceramic = ceramic;
		this.highlyinsulated = highlyinsulated;
		this.wireType = wireType;
	}

	public static SubtypeWire getUninsulatedWire(SubtypeWire type) {
		String wanted = getWireMaterial(type);
		for (SubtypeWire wire : values()) {
			if (Objects.equals(wire.name(), wanted)) {
				return wire;
			}
		}
		return type;
	}
	
	public static SubtypeWire getInsulatedWire(SubtypeWire type) {
		String wanted = "insulated" + getWireMaterial(type);
		for (SubtypeWire wire : values()) {
			if (Objects.equals(wire.name(), wanted)) {
				return wire;
			}
		}
		return type;
	}
	
	public static SubtypeWire getHighlyInsulatedWire(SubtypeWire type) {
		String wanted = "highlyinsulated" + getWireMaterial(type);
		for (SubtypeWire wire : values()) {
			if (Objects.equals(wire.name(), wanted)) {
				return wire;
			}
		}
		return type;
	}
	
	public static SubtypeWire getLogisticsWire(SubtypeWire type) {
		String wanted = "logistics" + getWireMaterial(type);
		for (SubtypeWire wire : values()) {
			if (Objects.equals(wire.name(), wanted)) {
				return wire;
			}
		}
		return type;
	}
	
	public static SubtypeWire getCeramicWire(SubtypeWire type) {
		String wanted = "ceramicinsulated" + getWireMaterial(type);
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
	
	private static String getWireMaterial(SubtypeWire type) {
		return type.name().replace("logistics", "").replace("ceramic", "").replace("highly", "").replace("insulated", "");
	}
}
