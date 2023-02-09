package electrodynamics.common.block.subtype;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.ISubtype;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

// Calculated using https://www.omnicalculator.com/physics/wire-resistance
// Area is actually 0.125 = 15625mm^2
// Length is 1 meter + 2 meters of wire - 2 * center length -> 3 - 2 * 0.125 = 2.75 meters per wire block. this is static and isnt calculated dynamically even though
// wires are rarely connected in all directions.
// Also manipulated uniformally
public enum SubtypeWire implements ISubtype {

	/* UNINSULATED */
	tin(WireMaterial.TIN, 60, WireClass.BARE, WireType.UNINSULATED),
	iron(WireMaterial.IRON, 100, WireClass.BARE, WireType.UNINSULATED),
	copper(WireMaterial.COPPER, 360, WireClass.BARE, WireType.UNINSULATED),
	silver(WireMaterial.SILVER, 600, WireClass.BARE, WireType.UNINSULATED),
	gold(WireMaterial.GOLD, 1000, WireClass.BARE, WireType.UNINSULATED),
	superconductive(WireMaterial.SUPERCONDUCTIVE, Long.MAX_VALUE, WireClass.BARE, WireType.UNINSULATED),
	/* INSULATED */
	insulatedtin(WireMaterial.TIN, 60, WireClass.INSULATED, WireType.INSULATED),
	insulatediron(WireMaterial.IRON, 100, WireClass.INSULATED, WireType.INSULATED),
	insulatedcopper(WireMaterial.COPPER, 360, WireClass.INSULATED, WireType.INSULATED),
	insulatedsilver(WireMaterial.SILVER, 600, WireClass.INSULATED, WireType.INSULATED),
	insulatedgold(WireMaterial.GOLD, 1000, WireClass.INSULATED, WireType.INSULATED),
	insulatedsuperconductive(WireMaterial.SUPERCONDUCTIVE, Long.MAX_VALUE, WireClass.INSULATED, WireType.INSULATED),
	/* HIGHLY INSULATED */
	highlyinsulatedtin(WireMaterial.TIN, 4.0, 180, WireClass.HIGHLY_INSULATED, WireType.HIGHLY_INSULATED),
	highlyinsulatediron(WireMaterial.IRON, 4.0, 300, WireClass.HIGHLY_INSULATED, WireType.HIGHLY_INSULATED),
	highlyinsulatedcopper(WireMaterial.COPPER, 4.0, 1080, WireClass.HIGHLY_INSULATED, WireType.HIGHLY_INSULATED),
	highlyinsulatedsilver(WireMaterial.SILVER, 4.0, 1800, WireClass.HIGHLY_INSULATED, WireType.HIGHLY_INSULATED),
	highlyinsulatedgold(WireMaterial.GOLD, 4, 3000, WireClass.HIGHLY_INSULATED, WireType.HIGHLY_INSULATED),
	highlyinsulatedsuperconductive(WireMaterial.SUPERCONDUCTIVE, Long.MAX_VALUE, WireClass.HIGHLY_INSULATED, WireType.HIGHLY_INSULATED),
	/* CERAMIC INSULATED */
	ceramicinsulatedtin(WireMaterial.TIN, 60, WireClass.WELL_INSULATED, WireType.CERAMIC),
	ceramicinsulatediron(WireMaterial.IRON, 100, WireClass.WELL_INSULATED, WireType.CERAMIC),
	ceramicinsulatedcopper(WireMaterial.COPPER, 360, WireClass.WELL_INSULATED, WireType.CERAMIC),
	ceramicinsulatedsilver(WireMaterial.SILVER, 600, WireClass.WELL_INSULATED, WireType.CERAMIC),
	ceramicinsulatedgold(WireMaterial.GOLD, 1000, WireClass.WELL_INSULATED, WireType.CERAMIC),
	ceramicinsulatedsuperconductive(WireMaterial.SUPERCONDUCTIVE, Long.MAX_VALUE, WireClass.WELL_INSULATED, WireType.CERAMIC),
	/* LOGISTICAL */
	logisticstin(WireMaterial.TIN, 60, WireClass.INSULATED, WireType.LOGISTICAL),
	logisticsiron(WireMaterial.IRON, 100, WireClass.INSULATED, WireType.LOGISTICAL),
	logisticscopper(WireMaterial.COPPER, 360, WireClass.INSULATED, WireType.LOGISTICAL),
	logisticssilver(WireMaterial.SILVER, 600, WireClass.INSULATED, WireType.LOGISTICAL),
	logisticsgold(WireMaterial.GOLD, 1000, WireClass.INSULATED, WireType.LOGISTICAL),
	logisticssuperconductive(WireMaterial.SUPERCONDUCTIVE, Long.MAX_VALUE, WireClass.INSULATED, WireType.LOGISTICAL);
	// split between types

	public final double resistance;
	public final long capacity;
	public final WireClass wireClass;
	public final WireType wireType;
	public final WireMaterial material;

	SubtypeWire(WireMaterial material, double dividend, long capacity, WireClass wireClass, WireType wireType) {
		resistance = material.resistance / dividend;
		this.capacity = capacity;
		this.wireClass = wireClass;
		this.wireType = wireType;
		this.material = material;
	}

	SubtypeWire(WireMaterial material, long capacity, WireClass wireClass, WireType wireType) {
		this(material, 1, capacity, wireClass, wireType);
	}

	public static SubtypeWire getWireForType(WireType wireType, WireMaterial material) {

		for (SubtypeWire wire : values()) {
			if (wire.wireType == wireType && wire.material == material) {
				return wire;
			}
		}

		return SubtypeWire.copper;
	}

	public static SubtypeWire[] getWiresForType(WireType type) {
		List<SubtypeWire> wires = new ArrayList<>();

		for (SubtypeWire wire : values()) {
			if (wire.wireType == type) {
				wires.add(wire);
			}
		}

		return wires.toArray(new SubtypeWire[wires.size()]);
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

	public static enum WireClass {
		BARE(false, true, 0),
		INSULATED(true, false, 240),
		HIGHLY_INSULATED(true, false, 960),
		WELL_INSULATED(true, true, 480);

		public final boolean insulated;
		public final boolean fireProof;
		public final int shockVoltage;

		WireClass(boolean insulated, boolean fireProof, int shockVoltage) {
			this.insulated = insulated;
			this.fireProof = fireProof;
			this.shockVoltage = shockVoltage;
		}
	}

	public static enum WireType {
		UNINSULATED(false, 1, Material.METAL, SoundType.METAL),
		INSULATED(false, 2, Material.WOOL, SoundType.WOOL),
		LOGISTICAL(true, 2, Material.WOOL, SoundType.WOOL),
		CERAMIC(false, 2, Material.STONE, SoundType.STONE),
		HIGHLY_INSULATED(false, 3, Material.WOOL, SoundType.WOOL);

		public final boolean conductsRedstone;
		public final int radius;
		public final Material material;
		public final SoundType soundType;

		WireType(boolean conductsRedstone, int radius, Material material, SoundType soundType) {
			this.conductsRedstone = conductsRedstone;
			this.radius = radius;
			this.material = material;
			this.soundType = soundType;
		}
	}

	public static enum WireMaterial {

		COPPER(0.0030096), // annealed copper
		GOLD(0.004294),
		IRON(0.01709),
		SILVER(0.0027984),
		SUPERCONDUCTIVE(0),
		TIN(0.020064); // Tin has 15% the conductivity of copper. Tin resistance = copper / 0.15

		public final double resistance;

		WireMaterial(double resistance) {
			this.resistance = resistance;
		}

	}

}
