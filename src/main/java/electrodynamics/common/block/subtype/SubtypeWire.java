package electrodynamics.common.block.subtype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nullable;

import electrodynamics.api.ISubtype;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
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

	tin(Conductor.TIN, InsulationMaterial.BARE, WireClass.BARE, WireColor.NONE, WireColor.NONE),
	iron(Conductor.IRON, InsulationMaterial.BARE, WireClass.BARE, WireColor.NONE, WireColor.NONE),
	copper(Conductor.COPPER, InsulationMaterial.BARE, WireClass.BARE, WireColor.NONE, WireColor.NONE),
	silver(Conductor.SILVER, InsulationMaterial.BARE, WireClass.BARE, WireColor.NONE, WireColor.NONE),
	gold(Conductor.GOLD, InsulationMaterial.BARE, WireClass.BARE, WireColor.NONE, WireColor.NONE),
	superconductive(Conductor.SUPERCONDUCTIVE, InsulationMaterial.BARE, WireClass.BARE, WireColor.NONE, WireColor.NONE),

	/* INSULATED */

	// black
	insulatedtinblack(Conductor.TIN, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.BLACK, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_TIN_WIRES),
	insulatedironblack(Conductor.IRON, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.BLACK, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_IRON_WIRES),
	insulatedcopperblack(Conductor.COPPER, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.BLACK, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_COPPER_WIRES),
	insulatedsilverblack(Conductor.SILVER, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.BLACK, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_SILVER_WIRES),
	insulatedgoldblack(Conductor.GOLD, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.BLACK, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_GOLD_WIRES),
	insulatedsuperconductiveblack(Conductor.SUPERCONDUCTIVE, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.BLACK, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_SUPERCONDUCTIVE_WIRES),
	// red
	insulatedtinred(Conductor.TIN, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.RED, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_TIN_WIRES),
	insulatedironred(Conductor.IRON, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.RED, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_IRON_WIRES),
	insulatedcopperred(Conductor.COPPER, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.RED, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_COPPER_WIRES),
	insulatedsilverred(Conductor.SILVER, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.RED, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_SILVER_WIRES),
	insulatedgoldred(Conductor.GOLD, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.RED, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_GOLD_WIRES),
	insulatedsuperconductivered(Conductor.SUPERCONDUCTIVE, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.RED, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_SUPERCONDUCTIVE_WIRES),
	// white
	insulatedtinwhite(Conductor.TIN, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.WHITE, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_TIN_WIRES),
	insulatedironwhite(Conductor.IRON, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.WHITE, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_IRON_WIRES),
	insulatedcopperwhite(Conductor.COPPER, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.WHITE, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_COPPER_WIRES),
	insulatedsilverwhite(Conductor.SILVER, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.WHITE, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_SILVER_WIRES),
	insulatedgoldwhite(Conductor.GOLD, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.WHITE, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_GOLD_WIRES),
	insulatedsuperconductivewhite(Conductor.SUPERCONDUCTIVE, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.WHITE, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_SUPERCONDUCTIVE_WIRES),
	// green
	insulatedtingreen(Conductor.TIN, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.GREEN, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_TIN_WIRES),
	insulatedirongreen(Conductor.IRON, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.GREEN, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_IRON_WIRES),
	insulatedcoppergreen(Conductor.COPPER, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.GREEN, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_COPPER_WIRES),
	insulatedsilvergreen(Conductor.SILVER, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.GREEN, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_SILVER_WIRES),
	insulatedgoldgreen(Conductor.GOLD, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.GREEN, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_GOLD_WIRES),
	insulatedsuperconductivegreen(Conductor.SUPERCONDUCTIVE, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.GREEN, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_SUPERCONDUCTIVE_WIRES),
	// blue
	insulatedtinblue(Conductor.TIN, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.BLUE, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_TIN_WIRES),
	insulatedironblue(Conductor.IRON, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.BLUE, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_IRON_WIRES),
	insulatedcopperblue(Conductor.COPPER, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.BLUE, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_COPPER_WIRES),
	insulatedsilverblue(Conductor.SILVER, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.BLUE, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_SILVER_WIRES),
	insulatedgoldblue(Conductor.GOLD, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.BLUE, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_GOLD_WIRES),
	insulatedsuperconductiveblue(Conductor.SUPERCONDUCTIVE, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.BLUE, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_SUPERCONDUCTIVE_WIRES),
	// yellow
	insulatedtinyellow(Conductor.TIN, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.YELLOW, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_TIN_WIRES),
	insulatedironyellow(Conductor.IRON, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.YELLOW, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_IRON_WIRES),
	insulatedcopperyellow(Conductor.COPPER, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.YELLOW, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_COPPER_WIRES),
	insulatedsilveryellow(Conductor.SILVER, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.YELLOW, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_SILVER_WIRES),
	insulatedgoldyellow(Conductor.GOLD, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.YELLOW, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_GOLD_WIRES),
	insulatedsuperconductiveyellow(Conductor.SUPERCONDUCTIVE, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.YELLOW, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_SUPERCONDUCTIVE_WIRES),
	// brown
	insulatedtinbrown(Conductor.TIN, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.BROWN, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_TIN_WIRES),
	insulatedironbrown(Conductor.IRON, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.BROWN, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_IRON_WIRES),
	insulatedcopperbrown(Conductor.COPPER, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.BROWN, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_COPPER_WIRES),
	insulatedsilverbrown(Conductor.SILVER, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.BROWN, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_SILVER_WIRES),
	insulatedgoldbrown(Conductor.GOLD, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.BROWN, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_GOLD_WIRES),
	insulatedsuperconductivebrown(Conductor.SUPERCONDUCTIVE, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.BROWN, WireColor.BLACK, ElectrodynamicsTags.Items.INSULATED_SUPERCONDUCTIVE_WIRES),

	/* HIGHLY INSULATED */

	// black
	highlyinsulatedtinblack(Conductor.TIN, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.BLACK, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_TIN_WIRES),
	highlyinsulatedironblack(Conductor.IRON, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.BLACK, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_IRON_WIRES),
	highlyinsulatedcopperblack(Conductor.COPPER, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.BLACK, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_COPPER_WIRES),
	highlyinsulatedsilverblack(Conductor.SILVER, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.BLACK, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_SILVER_WIRES),
	highlyinsulatedgoldblack(Conductor.GOLD, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.BLACK, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_GOLD_WIRES),
	highlyinsulatedsuperconductiveblack(Conductor.SUPERCONDUCTIVE, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.BLACK, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_SUPERCONDUCTIVE_WIRES),
	// red
	highlyinsulatedtinred(Conductor.TIN, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.RED, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_TIN_WIRES),
	highlyinsulatedironred(Conductor.IRON, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.RED, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_IRON_WIRES),
	highlyinsulatedcopperred(Conductor.COPPER, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.RED, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_COPPER_WIRES),
	highlyinsulatedsilverred(Conductor.SILVER, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.RED, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_SILVER_WIRES),
	highlyinsulatedgoldred(Conductor.GOLD, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.RED, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_GOLD_WIRES),
	highlyinsulatedsuperconductivered(Conductor.SUPERCONDUCTIVE, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.RED, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_SUPERCONDUCTIVE_WIRES),
	// white
	highlyinsulatedtinwhite(Conductor.TIN, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.WHITE, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_TIN_WIRES),
	highlyinsulatedironwhite(Conductor.IRON, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.WHITE, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_IRON_WIRES),
	highlyinsulatedcopperwhite(Conductor.COPPER, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.WHITE, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_COPPER_WIRES),
	highlyinsulatedsilverwhite(Conductor.SILVER, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.WHITE, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_SILVER_WIRES),
	highlyinsulatedgoldwhite(Conductor.GOLD, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.WHITE, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_GOLD_WIRES),
	highlyinsulatedsuperconductivewhite(Conductor.SUPERCONDUCTIVE, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.WHITE, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_SUPERCONDUCTIVE_WIRES),
	// green
	highlyinsulatedtingreen(Conductor.TIN, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.GREEN, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_TIN_WIRES),
	highlyinsulatedirongreen(Conductor.IRON, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.GREEN, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_IRON_WIRES),
	highlyinsulatedcoppergreen(Conductor.COPPER, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.GREEN, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_COPPER_WIRES),
	highlyinsulatedsilvergreen(Conductor.SILVER, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.GREEN, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_SILVER_WIRES),
	highlyinsulatedgoldgreen(Conductor.GOLD, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.GREEN, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_GOLD_WIRES),
	highlyinsulatedsuperconductivegreen(Conductor.SUPERCONDUCTIVE, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.GREEN, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_SUPERCONDUCTIVE_WIRES),
	// blue
	highlyinsulatedtinblue(Conductor.TIN, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.BLUE, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_TIN_WIRES),
	highlyinsulatedironblue(Conductor.IRON, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.BLUE, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_IRON_WIRES),
	highlyinsulatedcopperblue(Conductor.COPPER, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.BLUE, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_COPPER_WIRES),
	highlyinsulatedsilverblue(Conductor.SILVER, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.BLUE, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_SILVER_WIRES),
	highlyinsulatedgoldblue(Conductor.GOLD, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.BLUE, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_GOLD_WIRES),
	highlyinsulatedsuperconductiveblue(Conductor.SUPERCONDUCTIVE, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.BLUE, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_SUPERCONDUCTIVE_WIRES),
	// yellow
	highlyinsulatedtinyellow(Conductor.TIN, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.YELLOW, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_TIN_WIRES),
	highlyinsulatedironyellow(Conductor.IRON, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.YELLOW, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_IRON_WIRES),
	highlyinsulatedcopperyellow(Conductor.COPPER, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.YELLOW, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_COPPER_WIRES),
	highlyinsulatedsilveryellow(Conductor.SILVER, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.YELLOW, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_SILVER_WIRES),
	highlyinsulatedgoldyellow(Conductor.GOLD, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.YELLOW, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_GOLD_WIRES),
	highlyinsulatedsuperconductiveyellow(Conductor.SUPERCONDUCTIVE, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.YELLOW, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_SUPERCONDUCTIVE_WIRES),
	// brown
	highlyinsulatedtinbrown(Conductor.TIN, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.BROWN, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_TIN_WIRES),
	highlyinsulatedironbrown(Conductor.IRON, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.BROWN, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_IRON_WIRES),
	highlyinsulatedcopperbrown(Conductor.COPPER, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.BROWN, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_COPPER_WIRES),
	highlyinsulatedsilverbrown(Conductor.SILVER, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.BROWN, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_SILVER_WIRES),
	highlyinsulatedgoldbrown(Conductor.GOLD, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.BROWN, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_GOLD_WIRES),
	highlyinsulatedsuperconductivebrown(Conductor.SUPERCONDUCTIVE, InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.BROWN, WireColor.BLACK, 4.0, ElectrodynamicsTags.Items.THICK_SUPERCONDUCTIVE_WIRES),

	/* CERAMIC INSULATED */

	// black
	ceramicinsulatedtinblack(Conductor.TIN, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.BLACK, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_TIN_WIRES),
	ceramicinsulatedironblack(Conductor.IRON, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.BLACK, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_IRON_WIRES),
	ceramicinsulatedcopperblack(Conductor.COPPER, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.BLACK, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_COPPER_WIRES),
	ceramicinsulatedsilverblack(Conductor.SILVER, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.BLACK, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_SILVER_WIRES),
	ceramicinsulatedgoldblack(Conductor.GOLD, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.BLACK, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_GOLD_WIRES),
	ceramicinsulatedsuperconductiveblack(Conductor.SUPERCONDUCTIVE, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.BLACK, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_SUPERCONDUCTIVE_WIRES),
	// red
	ceramicinsulatedtinred(Conductor.TIN, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.RED, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_TIN_WIRES),
	ceramicinsulatedironred(Conductor.IRON, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.RED, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_IRON_WIRES),
	ceramicinsulatedcopperred(Conductor.COPPER, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.RED, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_COPPER_WIRES),
	ceramicinsulatedsilverred(Conductor.SILVER, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.RED, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_SILVER_WIRES),
	ceramicinsulatedgoldred(Conductor.GOLD, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.RED, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_GOLD_WIRES),
	ceramicinsulatedsuperconductivered(Conductor.SUPERCONDUCTIVE, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.RED, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_SUPERCONDUCTIVE_WIRES),
	// white
	ceramicinsulatedtinwhite(Conductor.TIN, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.WHITE, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_TIN_WIRES),
	ceramicinsulatedironwhite(Conductor.IRON, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.WHITE, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_IRON_WIRES),
	ceramicinsulatedcopperwhite(Conductor.COPPER, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.WHITE, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_COPPER_WIRES),
	ceramicinsulatedsilverwhite(Conductor.SILVER, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.WHITE, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_SILVER_WIRES),
	ceramicinsulatedgoldwhite(Conductor.GOLD, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.WHITE, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_GOLD_WIRES),
	ceramicinsulatedsuperconductivewhite(Conductor.SUPERCONDUCTIVE, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.WHITE, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_SUPERCONDUCTIVE_WIRES),
	// green
	ceramicinsulatedtingreen(Conductor.TIN, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.GREEN, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_TIN_WIRES),
	ceramicinsulatedirongreen(Conductor.IRON, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.GREEN, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_IRON_WIRES),
	ceramicinsulatedcoppergreen(Conductor.COPPER, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.GREEN, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_COPPER_WIRES),
	ceramicinsulatedsilvergreen(Conductor.SILVER, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.GREEN, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_SILVER_WIRES),
	ceramicinsulatedgoldgreen(Conductor.GOLD, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.GREEN, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_GOLD_WIRES),
	ceramicinsulatedsuperconductivegreen(Conductor.SUPERCONDUCTIVE, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.GREEN, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_SUPERCONDUCTIVE_WIRES),
	// blue
	ceramicinsulatedtinblue(Conductor.TIN, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.BLUE, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_TIN_WIRES),
	ceramicinsulatedironblue(Conductor.IRON, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.BLUE, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_IRON_WIRES),
	ceramicinsulatedcopperblue(Conductor.COPPER, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.BLUE, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_COPPER_WIRES),
	ceramicinsulatedsilverblue(Conductor.SILVER, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.BLUE, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_SILVER_WIRES),
	ceramicinsulatedgoldblue(Conductor.GOLD, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.BLUE, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_GOLD_WIRES),
	ceramicinsulatedsuperconductiveblue(Conductor.SUPERCONDUCTIVE, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.BLUE, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_SUPERCONDUCTIVE_WIRES),
	// yellow
	ceramicinsulatedtinyellow(Conductor.TIN, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.YELLOW, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_TIN_WIRES),
	ceramicinsulatedironyellow(Conductor.IRON, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.YELLOW, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_IRON_WIRES),
	ceramicinsulatedcopperyellow(Conductor.COPPER, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.YELLOW, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_COPPER_WIRES),
	ceramicinsulatedsilveryellow(Conductor.SILVER, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.YELLOW, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_SILVER_WIRES),
	ceramicinsulatedgoldyellow(Conductor.GOLD, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.YELLOW, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_GOLD_WIRES),
	ceramicinsulatedsuperconductiveyellow(Conductor.SUPERCONDUCTIVE, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.YELLOW, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_SUPERCONDUCTIVE_WIRES),
	// brown
	ceramicinsulatedtinbrown(Conductor.TIN, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.BROWN, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_TIN_WIRES),
	ceramicinsulatedironbrown(Conductor.IRON, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.BROWN, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_IRON_WIRES),
	ceramicinsulatedcopperbrown(Conductor.COPPER, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.BROWN, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_COPPER_WIRES),
	ceramicinsulatedsilverbrown(Conductor.SILVER, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.BROWN, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_SILVER_WIRES),
	ceramicinsulatedgoldbrown(Conductor.GOLD, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.BROWN, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_GOLD_WIRES),
	ceramicinsulatedsuperconductivebrown(Conductor.SUPERCONDUCTIVE, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.BROWN, WireColor.BROWN, ElectrodynamicsTags.Items.CERAMIC_SUPERCONDUCTIVE_WIRES),

	/* LOGISTICAL */

	// black
	logisticstinblack(Conductor.TIN, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.BLACK, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_TIN_WIRES),
	logisticsironblack(Conductor.IRON, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.BLACK, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_IRON_WIRES),
	logisticscopperblack(Conductor.COPPER, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.BLACK, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_COPPER_WIRES),
	logisticssilverblack(Conductor.SILVER, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.BLACK, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_SILVER_WIRES),
	logisticsgoldblack(Conductor.GOLD, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.BLACK, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_GOLD_WIRES),
	logisticssuperconductiveblack(Conductor.SUPERCONDUCTIVE, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.BLACK, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_SUPERCONDUCTIVE_WIRES),
	// red
	logisticstinred(Conductor.TIN, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.RED, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_TIN_WIRES),
	logisticsironred(Conductor.IRON, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.RED, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_IRON_WIRES),
	logisticscopperred(Conductor.COPPER, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.RED, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_COPPER_WIRES),
	logisticssilverred(Conductor.SILVER, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.RED, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_SILVER_WIRES),
	logisticsgoldred(Conductor.GOLD, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.RED, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_GOLD_WIRES),
	logisticssuperconductivered(Conductor.SUPERCONDUCTIVE, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.RED, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_SUPERCONDUCTIVE_WIRES),
	// white
	logisticstinwhite(Conductor.TIN, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.WHITE, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_TIN_WIRES),
	logisticsironwhite(Conductor.IRON, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.WHITE, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_IRON_WIRES),
	logisticscopperwhite(Conductor.COPPER, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.WHITE, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_COPPER_WIRES),
	logisticssilverwhite(Conductor.SILVER, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.WHITE, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_SILVER_WIRES),
	logisticsgoldwhite(Conductor.GOLD, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.WHITE, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_GOLD_WIRES),
	logisticssuperconductivewhite(Conductor.SUPERCONDUCTIVE, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.WHITE, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_SUPERCONDUCTIVE_WIRES),
	// green
	logisticstingreen(Conductor.TIN, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.GREEN, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_TIN_WIRES),
	logisticsirongreen(Conductor.IRON, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.GREEN, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_IRON_WIRES),
	logisticscoppergreen(Conductor.COPPER, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.GREEN, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_COPPER_WIRES),
	logisticssilvergreen(Conductor.SILVER, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.GREEN, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_SILVER_WIRES),
	logisticsgoldgreen(Conductor.GOLD, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.GREEN, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_GOLD_WIRES),
	logisticssuperconductivegreen(Conductor.SUPERCONDUCTIVE, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.GREEN, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_SUPERCONDUCTIVE_WIRES),
	// blue
	logisticstinblue(Conductor.TIN, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.BLUE, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_TIN_WIRES),
	logisticsironblue(Conductor.IRON, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.BLUE, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_IRON_WIRES),
	logisticscopperblue(Conductor.COPPER, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.BLUE, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_COPPER_WIRES),
	logisticssilverblue(Conductor.SILVER, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.BLUE, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_SILVER_WIRES),
	logisticsgoldblue(Conductor.GOLD, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.BLUE, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_GOLD_WIRES),
	logisticssuperconductiveblue(Conductor.SUPERCONDUCTIVE, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.BLUE, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_SUPERCONDUCTIVE_WIRES),
	// yellow
	logisticstinyellow(Conductor.TIN, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.YELLOW, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_TIN_WIRES),
	logisticsironyellow(Conductor.IRON, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.YELLOW, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_IRON_WIRES),
	logisticscopperyellow(Conductor.COPPER, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.YELLOW, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_COPPER_WIRES),
	logisticssilveryellow(Conductor.SILVER, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.YELLOW, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_SILVER_WIRES),
	logisticsgoldyellow(Conductor.GOLD, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.YELLOW, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_GOLD_WIRES),
	logisticssuperconductiveyellow(Conductor.SUPERCONDUCTIVE, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.YELLOW, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_SUPERCONDUCTIVE_WIRES),
	// brown
	logisticstinbrown(Conductor.TIN, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.BROWN, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_TIN_WIRES),
	logisticsironbrown(Conductor.IRON, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.BROWN, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_IRON_WIRES),
	logisticscopperbrown(Conductor.COPPER, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.BROWN, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_COPPER_WIRES),
	logisticssilverbrown(Conductor.SILVER, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.BROWN, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_SILVER_WIRES),
	logisticsgoldbrown(Conductor.GOLD, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.BROWN, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_GOLD_WIRES),
	logisticssuperconductivebrown(Conductor.SUPERCONDUCTIVE, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.BROWN, WireColor.BLACK, ElectrodynamicsTags.Items.LOGISTICAL_SUPERCONDUCTIVE_WIRES);

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
	public final WireColor color;
	public final WireColor defaultColor;
	@Nullable
	public TagKey<Item> itemTag = null;

	private SubtypeWire(Conductor conductor, InsulationMaterial insulation, WireClass wireClass, WireColor color, WireColor defaultColor, double dividend, TagKey<Item> tag) {
		resistance = conductor.resistance / dividend;
		this.conductor = conductor;
		this.insulation = insulation;
		this.wireClass = wireClass;
		this.color = color;
		this.defaultColor = defaultColor;
		itemTag = tag;
	}

	private SubtypeWire(Conductor conductor, InsulationMaterial insulation, WireClass wireClass, WireColor color, WireColor defaultColor, TagKey<Item> tag) {
		this(conductor, insulation, wireClass, color, defaultColor, 1, tag);
	}

	private SubtypeWire(Conductor conductor, InsulationMaterial insulation, WireClass wireClass, WireColor color, WireColor defaultColor, double dividend) {
		this(conductor, insulation, wireClass, color, defaultColor, dividend, null);
	}

	private SubtypeWire(Conductor conductor, InsulationMaterial insulation, WireClass wireClass, WireColor color, WireColor defaultColor) {
		this(conductor, insulation, wireClass, color, defaultColor, 1, null);
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

	public boolean isDefaultColor() {
		return color == defaultColor;
	}

	@Nullable
	public static SubtypeWire getWire(Conductor conductor, InsulationMaterial insulation, WireClass wireClass, WireColor color) {

		for (SubtypeWire wire : WIRES.getOrDefault(wireClass, new HashSet<>())) {
			if (wire.conductor == conductor && wire.insulation == insulation && wire.wireClass == wireClass && wire.color == color) {
				return wire;
			}
		}

		return null;
	}

	public static SubtypeWire[] getWires(Conductor[] conductors, InsulationMaterial insulation, WireClass wireClass, WireColor... colors) {

		List<SubtypeWire> list = new ArrayList<>();

		SubtypeWire wire;

		for (Conductor conductor : conductors) {
			for (WireColor color : colors) {
				wire = SubtypeWire.getWire(conductor, insulation, wireClass, color);
				if (wire != null) {
					list.add(wire);
				}
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
		CERAMIC(true, true, 480, 3, Properties.copy(Blocks.STONE), SoundType.TUFF);

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

	// based on NEC wire colors
	public static enum WireColor {

		NONE(255, 255, 255, 255, null),
		BLACK(40, 40, 40, 255, Tags.Items.DYES_BLACK),
		RED(200, 0, 0, 255, Tags.Items.DYES_RED),
		WHITE(255, 255, 255, 255, Tags.Items.DYES_WHITE),
		GREEN(24, 147, 50, 255, Tags.Items.DYES_GREEN),
		BLUE(68, 140, 203, 255, Tags.Items.DYES_BLUE),
		YELLOW(250, 240, 104, 255, Tags.Items.DYES_YELLOW),
		BROWN(102, 78, 55, 255, Tags.Items.DYES_BROWN);

		public final int color;
		public final int r;
		public final int g;
		public final int b;
		public final int a;
		@Nullable
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
			for (WireColor color : values()) {
				if (color.dyeTag != null && ForgeRegistries.ITEMS.tags().getTag(color.dyeTag).contains(item)) {
					return color;
				}
			}
			return null;
		}
	}

}
