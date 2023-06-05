package electrodynamics.common.block.subtype;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nullable;

import electrodynamics.api.ISubtype;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

// Calculated using https://www.omnicalculator.com/physics/wire-resistance
// Area is actually 0.125 = 15625mm^2
// Length is 1 meter + 2 meters of wire - 2 * center length -> 3 - 2 * 0.125 = 2.75 meters per wire block. this is static and isnt calculated dynamically even though
// wires are rarely connected in all directions.
// Also manipulated uniformally

//Storing the color on the enum was the cleanest solution...
public enum SubtypeWire implements ISubtype {

	/* UNINSULATED */
	
	tin(Conductor.TIN, InsulationMaterial.BARE, WireClass.STANDARD, WireColor.BLACK),
	iron(Conductor.IRON, InsulationMaterial.BARE, WireClass.STANDARD, WireColor.BLACK),
	copper(Conductor.COPPER, InsulationMaterial.BARE, WireClass.STANDARD, WireColor.BLACK),
	silver(Conductor.SILVER, InsulationMaterial.BARE, WireClass.STANDARD, WireColor.BLACK),
	gold(Conductor.GOLD, InsulationMaterial.BARE, WireClass.STANDARD, WireColor.BLACK),
	superconductive(Conductor.SUPERCONDUCTIVE, InsulationMaterial.BARE, WireClass.STANDARD, WireColor.BLACK),
	
	/* INSULATED */
	
	//black
	insulatedtinblack(Conductor.TIN, InsulationMaterial.WOOL, WireClass.STANDARD, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_TIN_WIRES),
	insulatedironblack(Conductor.IRON, InsulationMaterial.WOOL, WireClass.STANDARD, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_IRON_WIRES),
	insulatedcopperblack(Conductor.COPPER, InsulationMaterial.WOOL, WireClass.STANDARD, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_COPPER_WIRES),
	insulatedsilverblack(Conductor.SILVER, InsulationMaterial.WOOL, WireClass.STANDARD, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_SILVER_WIRES),
	insulatedgoldblack(Conductor.GOLD, InsulationMaterial.WOOL, WireClass.STANDARD, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_GOLD_WIRES),
	insulatedsuperconductiveblack(Conductor.SUPERCONDUCTIVE, InsulationMaterial.WOOL, WireClass.STANDARD, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_SUPERCONDUCTIVE_WIRES),
	//red
	insulatedtinred(Conductor.TIN, InsulationMaterial.WOOL, WireClass.STANDARD, WireColor.RED, ElectrodynamicsTags.Items.INSULATED_TIN_WIRES),
	insulatedironred(Conductor.IRON, InsulationMaterial.WOOL, WireClass.STANDARD, WireColor.RED, ElectrodynamicsTags.Items.INSULATED_IRON_WIRES),
	insulatedcopperred(Conductor.COPPER, InsulationMaterial.WOOL, WireClass.STANDARD, WireColor.RED, ElectrodynamicsTags.Items.INSULATED_COPPER_WIRES),
	insulatedsilverred(Conductor.SILVER, InsulationMaterial.WOOL, WireClass.STANDARD, WireColor.RED, ElectrodynamicsTags.Items.INSULATED_SILVER_WIRES),
	insulatedgoldred(Conductor.GOLD, InsulationMaterial.WOOL, WireClass.STANDARD, WireColor.RED, ElectrodynamicsTags.Items.INSULATED_GOLD_WIRES),
	insulatedsuperconductivered(Conductor.SUPERCONDUCTIVE, InsulationMaterial.WOOL, WireClass.STANDARD, WireColor.RED, ElectrodynamicsTags.Items.INSULATED_SUPERCONDUCTIVE_WIRES),
	//white
	insulatedtinwhite(Conductor.TIN, InsulationMaterial.WOOL, WireClass.STANDARD, WireColor.WHITE, ElectrodynamicsTags.Items.INSULATED_TIN_WIRES),
	insulatedironwhite(Conductor.IRON, InsulationMaterial.WOOL, WireClass.STANDARD, WireColor.WHITE, ElectrodynamicsTags.Items.INSULATED_IRON_WIRES),
	insulatedcopperwhite(Conductor.COPPER, InsulationMaterial.WOOL, WireClass.STANDARD, WireColor.WHITE, ElectrodynamicsTags.Items.INSULATED_COPPER_WIRES),
	insulatedsilverwhite(Conductor.SILVER, InsulationMaterial.WOOL, WireClass.STANDARD, WireColor.WHITE, ElectrodynamicsTags.Items.INSULATED_SILVER_WIRES),
	insulatedgoldwhite(Conductor.GOLD, InsulationMaterial.WOOL, WireClass.STANDARD, WireColor.WHITE, ElectrodynamicsTags.Items.INSULATED_GOLD_WIRES),
	insulatedsuperconductivewhite(Conductor.SUPERCONDUCTIVE, InsulationMaterial.WOOL, WireClass.STANDARD, WireColor.WHITE, ElectrodynamicsTags.Items.INSULATED_SUPERCONDUCTIVE_WIRES),
	//green
	insulatedtingreen(Conductor.TIN, InsulationMaterial.WOOL, WireClass.STANDARD, WireColor.GREEN, ElectrodynamicsTags.Items.INSULATED_TIN_WIRES),
	insulatedirongreen(Conductor.IRON, InsulationMaterial.WOOL, WireClass.STANDARD, WireColor.GREEN, ElectrodynamicsTags.Items.INSULATED_IRON_WIRES),
	insulatedcoppergreen(Conductor.COPPER, InsulationMaterial.WOOL, WireClass.STANDARD, WireColor.GREEN, ElectrodynamicsTags.Items.INSULATED_COPPER_WIRES),
	insulatedsilvergreen(Conductor.SILVER, InsulationMaterial.WOOL, WireClass.STANDARD, WireColor.GREEN, ElectrodynamicsTags.Items.INSULATED_SILVER_WIRES),
	insulatedgoldgreen(Conductor.GOLD, InsulationMaterial.WOOL, WireClass.STANDARD, WireColor.GREEN, ElectrodynamicsTags.Items.INSULATED_GOLD_WIRES),
	insulatedsuperconductivegreen(Conductor.SUPERCONDUCTIVE, InsulationMaterial.WOOL, WireClass.STANDARD, WireColor.GREEN, ElectrodynamicsTags.Items.INSULATED_SUPERCONDUCTIVE_WIRES),
	//blue
	insulatedtinblue(Conductor.TIN, InsulationMaterial.WOOL, WireClass.STANDARD, WireColor.BLUE, ElectrodynamicsTags.Items.INSULATED_TIN_WIRES),
	insulatedironblue(Conductor.IRON, InsulationMaterial.WOOL, WireClass.STANDARD, WireColor.BLUE, ElectrodynamicsTags.Items.INSULATED_IRON_WIRES),
	insulatedcopperblue(Conductor.COPPER, InsulationMaterial.WOOL, WireClass.STANDARD, WireColor.BLUE, ElectrodynamicsTags.Items.INSULATED_COPPER_WIRES),
	insulatedsilverblue(Conductor.SILVER, InsulationMaterial.WOOL, WireClass.STANDARD, WireColor.BLUE, ElectrodynamicsTags.Items.INSULATED_SILVER_WIRES),
	insulatedgoldblue(Conductor.GOLD, InsulationMaterial.WOOL, WireClass.STANDARD, WireColor.BLUE, ElectrodynamicsTags.Items.INSULATED_GOLD_WIRES),
	insulatedsuperconductiveblue(Conductor.SUPERCONDUCTIVE, InsulationMaterial.WOOL, WireClass.STANDARD, WireColor.BLUE, ElectrodynamicsTags.Items.INSULATED_SUPERCONDUCTIVE_WIRES),
	//yellow
	insulatedtinyellow(Conductor.TIN, InsulationMaterial.WOOL, WireClass.STANDARD, WireColor.YELLOW, ElectrodynamicsTags.Items.INSULATED_TIN_WIRES),
	insulatedironyellow(Conductor.IRON, InsulationMaterial.WOOL, WireClass.STANDARD, WireColor.YELLOW, ElectrodynamicsTags.Items.INSULATED_IRON_WIRES),
	insulatedcopperyellow(Conductor.COPPER, InsulationMaterial.WOOL, WireClass.STANDARD, WireColor.YELLOW, ElectrodynamicsTags.Items.INSULATED_COPPER_WIRES),
	insulatedsilveryellow(Conductor.SILVER, InsulationMaterial.WOOL, WireClass.STANDARD, WireColor.YELLOW, ElectrodynamicsTags.Items.INSULATED_SILVER_WIRES),
	insulatedgoldyellow(Conductor.GOLD, InsulationMaterial.WOOL, WireClass.STANDARD, WireColor.YELLOW, ElectrodynamicsTags.Items.INSULATED_GOLD_WIRES),
	insulatedsuperconductiveyellow(Conductor.SUPERCONDUCTIVE, InsulationMaterial.WOOL, WireClass.STANDARD, WireColor.YELLOW, ElectrodynamicsTags.Items.INSULATED_SUPERCONDUCTIVE_WIRES),
	
	/* HIGHLY INSULATED */
	
	//black
	highlyinsulatedtinblack(Conductor.TIN, InsulationMaterial.THICK_WOOL, WireClass.STANDARD, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_TIN_WIRES),
	highlyinsulatedironblack(Conductor.IRON, InsulationMaterial.THICK_WOOL, WireClass.STANDARD, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_IRON_WIRES),
	highlyinsulatedcopperblack(Conductor.COPPER, InsulationMaterial.THICK_WOOL, WireClass.STANDARD, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_COPPER_WIRES),
	highlyinsulatedsilverblack(Conductor.SILVER, InsulationMaterial.THICK_WOOL, WireClass.STANDARD, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_SILVER_WIRES),
	highlyinsulatedgoldblack(Conductor.GOLD, InsulationMaterial.THICK_WOOL, WireClass.STANDARD, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_GOLD_WIRES),
	highlyinsulatedsuperconductiveblack(Conductor.SUPERCONDUCTIVE, InsulationMaterial.THICK_WOOL, WireClass.STANDARD, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_SUPERCONDUCTIVE_WIRES),
	//red
	highlyinsulatedtinred(Conductor.TIN, InsulationMaterial.THICK_WOOL, WireClass.STANDARD, WireColor.RED, 4.0, ElectrodynamicsTags.Items.THICK_TIN_WIRES),
	highlyinsulatedironred(Conductor.IRON, InsulationMaterial.THICK_WOOL, WireClass.STANDARD, WireColor.RED, 4.0, ElectrodynamicsTags.Items.THICK_IRON_WIRES),
	highlyinsulatedcopperred(Conductor.COPPER, InsulationMaterial.THICK_WOOL, WireClass.STANDARD, WireColor.RED, 4.0, ElectrodynamicsTags.Items.THICK_COPPER_WIRES),
	highlyinsulatedsilverred(Conductor.SILVER, InsulationMaterial.THICK_WOOL, WireClass.STANDARD, WireColor.RED, 4.0, ElectrodynamicsTags.Items.THICK_SILVER_WIRES),
	highlyinsulatedgoldred(Conductor.GOLD, InsulationMaterial.THICK_WOOL, WireClass.STANDARD, WireColor.RED, 4.0, ElectrodynamicsTags.Items.THICK_GOLD_WIRES),
	highlyinsulatedsuperconductivered(Conductor.SUPERCONDUCTIVE, InsulationMaterial.THICK_WOOL, WireClass.STANDARD, WireColor.RED, 4.0, ElectrodynamicsTags.Items.THICK_SUPERCONDUCTIVE_WIRES),
	//white
	highlyinsulatedtinwhite(Conductor.TIN, InsulationMaterial.THICK_WOOL, WireClass.STANDARD, WireColor.WHITE, 4.0, ElectrodynamicsTags.Items.THICK_TIN_WIRES),
	highlyinsulatedironwhite(Conductor.IRON, InsulationMaterial.THICK_WOOL, WireClass.STANDARD, WireColor.WHITE, 4.0, ElectrodynamicsTags.Items.THICK_IRON_WIRES),
	highlyinsulatedcopperwhite(Conductor.COPPER, InsulationMaterial.THICK_WOOL, WireClass.STANDARD, WireColor.WHITE, 4.0, ElectrodynamicsTags.Items.THICK_COPPER_WIRES),
	highlyinsulatedsilverwhite(Conductor.SILVER, InsulationMaterial.THICK_WOOL, WireClass.STANDARD, WireColor.WHITE, 4.0, ElectrodynamicsTags.Items.THICK_SILVER_WIRES),
	highlyinsulatedgoldwhite(Conductor.GOLD, InsulationMaterial.THICK_WOOL, WireClass.STANDARD, WireColor.WHITE, 4.0, ElectrodynamicsTags.Items.THICK_GOLD_WIRES),
	highlyinsulatedsuperconductivewhite(Conductor.SUPERCONDUCTIVE, InsulationMaterial.THICK_WOOL, WireClass.STANDARD, WireColor.WHITE, 4.0, ElectrodynamicsTags.Items.THICK_SUPERCONDUCTIVE_WIRES),
	//green
	highlyinsulatedtingreen(Conductor.TIN, InsulationMaterial.THICK_WOOL, WireClass.STANDARD, WireColor.GREEN, 4.0, ElectrodynamicsTags.Items.THICK_TIN_WIRES),
	highlyinsulatedirongreen(Conductor.IRON, InsulationMaterial.THICK_WOOL, WireClass.STANDARD, WireColor.GREEN, 4.0, ElectrodynamicsTags.Items.THICK_IRON_WIRES),
	highlyinsulatedcoppergreen(Conductor.COPPER, InsulationMaterial.THICK_WOOL, WireClass.STANDARD, WireColor.GREEN, 4.0, ElectrodynamicsTags.Items.THICK_COPPER_WIRES),
	highlyinsulatedsilvergreen(Conductor.SILVER, InsulationMaterial.THICK_WOOL, WireClass.STANDARD, WireColor.GREEN, 4.0, ElectrodynamicsTags.Items.THICK_SILVER_WIRES),
	highlyinsulatedgoldgreen(Conductor.GOLD, InsulationMaterial.THICK_WOOL, WireClass.STANDARD, WireColor.GREEN, 4.0, ElectrodynamicsTags.Items.THICK_GOLD_WIRES),
	highlyinsulatedsuperconductivegreen(Conductor.SUPERCONDUCTIVE, InsulationMaterial.THICK_WOOL, WireClass.STANDARD, WireColor.GREEN, 4.0, ElectrodynamicsTags.Items.THICK_SUPERCONDUCTIVE_WIRES),
	//blue
	highlyinsulatedtinblue(Conductor.TIN, InsulationMaterial.THICK_WOOL, WireClass.STANDARD, WireColor.BLUE, 4.0, ElectrodynamicsTags.Items.THICK_TIN_WIRES),
	highlyinsulatedironblue(Conductor.IRON, InsulationMaterial.THICK_WOOL, WireClass.STANDARD, WireColor.BLUE, 4.0, ElectrodynamicsTags.Items.THICK_IRON_WIRES),
	highlyinsulatedcopperblue(Conductor.COPPER, InsulationMaterial.THICK_WOOL, WireClass.STANDARD, WireColor.BLUE, 4.0, ElectrodynamicsTags.Items.THICK_COPPER_WIRES),
	highlyinsulatedsilverblue(Conductor.SILVER, InsulationMaterial.THICK_WOOL, WireClass.STANDARD, WireColor.BLUE, 4.0, ElectrodynamicsTags.Items.THICK_SILVER_WIRES),
	highlyinsulatedgoldblue(Conductor.GOLD, InsulationMaterial.THICK_WOOL, WireClass.STANDARD, WireColor.BLUE, 4.0, ElectrodynamicsTags.Items.THICK_GOLD_WIRES),
	highlyinsulatedsuperconductiveblue(Conductor.SUPERCONDUCTIVE, InsulationMaterial.THICK_WOOL, WireClass.STANDARD, WireColor.BLUE, 4.0, ElectrodynamicsTags.Items.THICK_SUPERCONDUCTIVE_WIRES),
	//yellow
	highlyinsulatedtinyellow(Conductor.TIN, InsulationMaterial.THICK_WOOL, WireClass.STANDARD, WireColor.YELLOW, 4.0, ElectrodynamicsTags.Items.THICK_TIN_WIRES),
	highlyinsulatedironyellow(Conductor.IRON, InsulationMaterial.THICK_WOOL, WireClass.STANDARD, WireColor.YELLOW, 4.0, ElectrodynamicsTags.Items.THICK_IRON_WIRES),
	highlyinsulatedcopperyellow(Conductor.COPPER, InsulationMaterial.THICK_WOOL, WireClass.STANDARD, WireColor.YELLOW, 4.0, ElectrodynamicsTags.Items.THICK_COPPER_WIRES),
	highlyinsulatedsilveryellow(Conductor.SILVER, InsulationMaterial.THICK_WOOL, WireClass.STANDARD, WireColor.YELLOW, 4.0, ElectrodynamicsTags.Items.THICK_SILVER_WIRES),
	highlyinsulatedgoldyellow(Conductor.GOLD, InsulationMaterial.THICK_WOOL, WireClass.STANDARD, WireColor.YELLOW, 4.0, ElectrodynamicsTags.Items.THICK_GOLD_WIRES),
	highlyinsulatedsuperconductiveyellow(Conductor.SUPERCONDUCTIVE, InsulationMaterial.THICK_WOOL, WireClass.STANDARD, WireColor.YELLOW, 4.0, ElectrodynamicsTags.Items.THICK_SUPERCONDUCTIVE_WIRES),
	
	/* CERAMIC INSULATED */
	
	ceramicinsulatedtin(Conductor.TIN, InsulationMaterial.CERAMIC, WireClass.STANDARD, WireColor.BLACK),
	ceramicinsulatediron(Conductor.IRON, InsulationMaterial.CERAMIC, WireClass.STANDARD, WireColor.BLACK),
	ceramicinsulatedcopper(Conductor.COPPER, InsulationMaterial.CERAMIC, WireClass.STANDARD, WireColor.BLACK),
	ceramicinsulatedsilver(Conductor.SILVER, InsulationMaterial.CERAMIC, WireClass.STANDARD, WireColor.BLACK),
	ceramicinsulatedgold(Conductor.GOLD, InsulationMaterial.CERAMIC, WireClass.STANDARD, WireColor.BLACK),
	ceramicinsulatedsuperconductive(Conductor.SUPERCONDUCTIVE, InsulationMaterial.CERAMIC, WireClass.STANDARD, WireColor.BLACK),
	
	/* LOGISTICAL */
	
	logisticstin(Conductor.TIN, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.BLACK),
	logisticsiron(Conductor.IRON, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.BLACK),
	logisticscopper(Conductor.COPPER, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.BLACK),
	logisticssilver(Conductor.SILVER, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.BLACK),
	logisticsgold(Conductor.GOLD, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.BLACK),
	logisticssuperconductive(Conductor.SUPERCONDUCTIVE, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.BLACK);

	public final double resistance;
	public final WireClass wireClass;
	public final InsulationMaterial insulation;
	public final Conductor conductor;
	public final WireColor color;
	@Nullable
	public TagKey<Item> itemTag = null;

	SubtypeWire(Conductor conductor, InsulationMaterial insulation, WireClass wireClass, WireColor color, double dividend, TagKey<Item> tag) {
		resistance = conductor.resistance / dividend;
		this.conductor = conductor;
		this.insulation = insulation;
		this.wireClass = wireClass;
		this.color = color;
		itemTag = tag;
	}

	SubtypeWire(Conductor conductor, InsulationMaterial insulation, WireClass wireClass, WireColor color, TagKey<Item> tag) {
		this(conductor, insulation, wireClass, color, 1, tag);
	}
	
	SubtypeWire(Conductor conductor, InsulationMaterial insulation, WireClass wireClass, WireColor color, double dividend) {
		this(conductor, insulation, wireClass, color, dividend, null);
	}

	SubtypeWire(Conductor conductor, InsulationMaterial insulation, WireClass wireClass, WireColor color) {
		this(conductor, insulation, wireClass, color, 1, null);
	}

	public static SubtypeWire getWire(Conductor conductor, InsulationMaterial insulation, WireClass wireClass, WireColor color) {

		for (SubtypeWire wire : values()) {
			if (wire.conductor == conductor && wire.insulation == insulation && wire.wireClass == wireClass && wire.color == color) {
				return wire;
			}
		}

		return SubtypeWire.copper;
	}
	
	public static SubtypeWire[] getWires(Conductor[] conductors, InsulationMaterial insulation, WireClass wireClass, WireColor... colors) {

		List<SubtypeWire> list = new ArrayList<>();

		for (Conductor conductor : conductors) {
			for (WireColor color : colors) {
				list.add(SubtypeWire.getWire(conductor, insulation, wireClass, color));
			}
		}

		return list.toArray(new SubtypeWire[] {});
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

	public static enum InsulationMaterial {
		
		BARE(false, true, 0, 1, Material.METAL, SoundType.METAL),
		WOOL(true, false, 240, 2, Material.WOOL, SoundType.WOOL),
		THICK_WOOL(true, false, 960, 3, Material.WOOL, SoundType.WOOL),
		CERAMIC(true, true, 480, 2, Material.STONE, SoundType.TUFF);

		public final boolean insulated;
		public final boolean fireProof;
		public final int shockVoltage;
		public final double radius;
		public final Material material;
		public final SoundType soundType;

		InsulationMaterial(boolean insulated, boolean fireProof, int shockVoltage, double radius, Material material, SoundType sounndType) {
			this.insulated = insulated;
			this.fireProof = fireProof;
			this.shockVoltage = shockVoltage;
			this.radius = radius;
			this.material = material;
			this.soundType = sounndType;
		}
	}

	public static enum WireClass {
		
		STANDARD(false),
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
	
	//based on NEC wire colors
	public static enum WireColor {
		
		BLACK(40, 40, 40, 255, Tags.Items.DYES_BLACK), //Black is defined as the default color
		RED(200, 0, 0, 255, Tags.Items.DYES_RED), 
		WHITE(255, 255, 255, 255, Tags.Items.DYES_WHITE), 
		GREEN(24, 147, 50, 255, Tags.Items.DYES_GREEN), 
		BLUE(68, 140, 203, 255, Tags.Items.DYES_BLUE), 
		YELLOW(250, 240, 104, 255, Tags.Items.DYES_YELLOW);

		public final int color;
		public final int r;
		public final int g;
		public final int b;
		public final int a;
		public final TagKey<Item> dyeTag;
		
		private WireColor(int r, int g, int b, int a, TagKey<Item> dyeTag) {
			color = RenderingUtils.getRGBA(a, r, g, b);
			this.r = r;
			this.g = g;
			this.b = b;
			this.a = a;
			this.dyeTag = dyeTag;
		}
		
		@Override
		public String toString() {
			return super.toString().toLowerCase(Locale.ROOT);
		}
		
		@Nullable
		public static WireColor getColorFromDye(Item item) {
			for(WireColor color : values()) {
				if(ForgeRegistries.ITEMS.tags().getTag(color.dyeTag).contains(item)) {
					return color;
				}
			}
			return null;
		}
	}

}
