package electrodynamics.common.block.subtype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nullable;

import electrodynamics.api.ISubtype;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;

// Calculated using https://www.omnicalculator.com/physics/wire-resistance
// Area is actually 0.125 = 15625mm^2
// Length is 1 meter + 2 meters of wire - 2 * center length -> 3 - 2 * 0.125 = 2.75 meters per wire block. this is static and isnt calculated dynamically even though
// wires are rarely connected in all directions.
// Also manipulated uniformally
public enum SubtypeWire implements ISubtype {

	/* UNINSULATED */
	tin(Conductor.TIN, InsulationMaterial.BARE, WireClass.BARE),
	iron(Conductor.IRON, InsulationMaterial.BARE, WireClass.BARE),
	copper(Conductor.COPPER, InsulationMaterial.BARE, WireClass.BARE),
	silver(Conductor.SILVER, InsulationMaterial.BARE, WireClass.BARE),
	gold(Conductor.GOLD, InsulationMaterial.BARE, WireClass.BARE),
	superconductive(Conductor.SUPERCONDUCTIVE, InsulationMaterial.BARE, WireClass.BARE),
	/* INSULATED */
	insulatedtin(Conductor.TIN, InsulationMaterial.WOOL, WireClass.INSULATED),
	insulatediron(Conductor.IRON, InsulationMaterial.WOOL, WireClass.INSULATED),
	insulatedcopper(Conductor.COPPER, InsulationMaterial.WOOL, WireClass.INSULATED),
	insulatedsilver(Conductor.SILVER, InsulationMaterial.WOOL, WireClass.INSULATED),
	insulatedgold(Conductor.GOLD, InsulationMaterial.WOOL, WireClass.INSULATED),
	insulatedsuperconductive(Conductor.SUPERCONDUCTIVE, InsulationMaterial.WOOL, WireClass.INSULATED),
	/* HIGHLY INSULATED */
	highlyinsulatedtin(Conductor.TIN, InsulationMaterial.THICK_WOOL, WireClass.THICK, 4.0),
	highlyinsulatediron(Conductor.IRON, InsulationMaterial.THICK_WOOL, WireClass.THICK, 4.0),
	highlyinsulatedcopper(Conductor.COPPER, InsulationMaterial.THICK_WOOL, WireClass.THICK, 4.0),
	highlyinsulatedsilver(Conductor.SILVER, InsulationMaterial.THICK_WOOL, WireClass.THICK, 4.0),
	highlyinsulatedgold(Conductor.GOLD, InsulationMaterial.THICK_WOOL, WireClass.THICK, 4.0),
	highlyinsulatedsuperconductive(Conductor.SUPERCONDUCTIVE, InsulationMaterial.THICK_WOOL, WireClass.THICK, 4.0),
	/* CERAMIC INSULATED */
	ceramicinsulatedtin(Conductor.TIN, InsulationMaterial.CERAMIC, WireClass.CERAMIC),
	ceramicinsulatediron(Conductor.IRON, InsulationMaterial.CERAMIC, WireClass.CERAMIC),
	ceramicinsulatedcopper(Conductor.COPPER, InsulationMaterial.CERAMIC, WireClass.CERAMIC),
	ceramicinsulatedsilver(Conductor.SILVER, InsulationMaterial.CERAMIC, WireClass.CERAMIC),
	ceramicinsulatedgold(Conductor.GOLD, InsulationMaterial.CERAMIC, WireClass.CERAMIC),
	ceramicinsulatedsuperconductive(Conductor.SUPERCONDUCTIVE, InsulationMaterial.CERAMIC, WireClass.CERAMIC),
	/* LOGISTICAL */
	logisticstin(Conductor.TIN, InsulationMaterial.WOOL, WireClass.LOGISTICAL),
	logisticsiron(Conductor.IRON, InsulationMaterial.WOOL, WireClass.LOGISTICAL),
	logisticscopper(Conductor.COPPER, InsulationMaterial.WOOL, WireClass.LOGISTICAL),
	logisticssilver(Conductor.SILVER, InsulationMaterial.WOOL, WireClass.LOGISTICAL),
	logisticsgold(Conductor.GOLD, InsulationMaterial.WOOL, WireClass.LOGISTICAL),
	logisticssuperconductive(Conductor.SUPERCONDUCTIVE, InsulationMaterial.WOOL, WireClass.LOGISTICAL);
	// split between types

	public static final HashMap<WireClass, HashSet<SubtypeWire>> WIRES = new HashMap<>();

	static {
		for (SubtypeWire wire : SubtypeWire.values()) {
			HashSet<SubtypeWire> wireSet = WIRES.getOrDefault(wire.wireClass, new HashSet<>());
			wireSet.add(wire);
			WIRES.put(wire.wireClass, wireSet);
		}
	}

	public final double resistance;
	public final WireClass wireClass;
	public final InsulationMaterial insulation;
	public final Conductor conductor;

	private SubtypeWire(Conductor conductor, InsulationMaterial insulation, WireClass wireClass, double dividend) {
		resistance = conductor.resistance / dividend;
		this.conductor = conductor;
		this.insulation = insulation;
		this.wireClass = wireClass;
	}

	private SubtypeWire(Conductor conductor, InsulationMaterial insulation, WireClass wireClass) {
		this(conductor, insulation, wireClass, 1);
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

	@Nullable
	public static SubtypeWire getWire(Conductor conductor, InsulationMaterial insulation, WireClass wireClass) {

		for (SubtypeWire wire : WIRES.getOrDefault(wireClass, new HashSet<>())) {
			if (wire.conductor == conductor && wire.insulation == insulation && wire.wireClass == wireClass) {
				return wire;
			}
		}

		return null;
	}

	public static SubtypeWire[] getWires(Conductor[] conductors, InsulationMaterial insulation, WireClass wireClass) {

		List<SubtypeWire> list = new ArrayList<>();

		SubtypeWire wire;

		for (Conductor conductor : conductors) {
			wire = SubtypeWire.getWire(conductor, insulation, wireClass);
			if (wire != null) {
				list.add(wire);
			}
		}

		return list.toArray(new SubtypeWire[] {});
	}

	/**
	 * A distinction is made between this and WireClass, as there can be multiple different wires with the same insulation but different properties like the Logistical Wire. It shares the same insulation value as the standard Insulated wire
	 * 
	 * @author skip999
	 *
	 */
	public static enum InsulationMaterial {

		BARE(false, true, 0, 1, Properties.copy(Blocks.IRON_BLOCK), SoundType.METAL),
		WOOL(true, false, 240, 2, Properties.copy(Blocks.WHITE_WOOL), SoundType.WOOL),
		THICK_WOOL(true, false, 960, 3, Properties.copy(Blocks.WHITE_WOOL), SoundType.WOOL),
		CERAMIC(true, true, 480, 3, Properties.copy(Blocks.STONE), SoundType.BASALT);

		public final boolean insulated;
		public final boolean fireProof;
		public final int shockVoltage;
		public final double radius;
		public final Properties material;
		public final SoundType soundType;

		InsulationMaterial(boolean insulated, boolean fireProof, int shockVoltage, double radius, Properties material, SoundType sounndType) {
			this.insulated = insulated;
			this.fireProof = fireProof;
			this.shockVoltage = shockVoltage;
			this.radius = radius;
			this.material = material;
			this.soundType = sounndType;
		}
	}

	/**
	 * This is a category enum to make distinctions between the different types of wires
	 * 
	 * @author skip999
	 *
	 */
	public static enum WireClass {

		BARE(false),
		INSULATED(false),
		THICK(false),
		CERAMIC(false),
		LOGISTICAL(true);

		public final boolean conductsRedstone;

		WireClass(boolean conductsRedstone) {
			this.conductsRedstone = conductsRedstone;
		}
	}

	public static enum Conductor {

		COPPER(0.0030096, 360), // annealed copper
		GOLD(0.004294, 1000),
		IRON(0.01709, 100),
		SILVER(0.0027984, 600),
		SUPERCONDUCTIVE(0, Long.MAX_VALUE),
		TIN(0.020064, 60); // Tin has 15% the conductivity of copper. Tin resistance = copper / 0.15

		public final double resistance;
		public final long ampacity;

		Conductor(double resistance, long ampacity) {
			this.resistance = resistance;
			this.ampacity = ampacity;
		}

		@Override
		public String toString() {
			return super.toString().toLowerCase(Locale.ROOT);
		}

	}

}