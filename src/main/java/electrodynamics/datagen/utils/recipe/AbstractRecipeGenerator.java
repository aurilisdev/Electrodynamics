package electrodynamics.datagen.utils.recipe;

import java.util.function.Consumer;

import electrodynamics.common.block.subtype.SubtypeFluidPipe;
import electrodynamics.common.block.subtype.SubtypeGlass;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypeOreDeepslate;
import electrodynamics.common.block.subtype.SubtypeRawOreBlock;
import electrodynamics.common.block.subtype.SubtypeResourceBlock;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.item.subtype.SubtypeCeramic;
import electrodynamics.common.item.subtype.SubtypeCircuit;
import electrodynamics.common.item.subtype.SubtypeCrystal;
import electrodynamics.common.item.subtype.SubtypeDrillHead;
import electrodynamics.common.item.subtype.SubtypeDust;
import electrodynamics.common.item.subtype.SubtypeGear;
import electrodynamics.common.item.subtype.SubtypeImpureDust;
import electrodynamics.common.item.subtype.SubtypeIngot;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.common.item.subtype.SubtypeNugget;
import electrodynamics.common.item.subtype.SubtypeOxide;
import electrodynamics.common.item.subtype.SubtypePlate;
import electrodynamics.common.item.subtype.SubtypeRawOre;
import electrodynamics.common.item.subtype.SubtypeRod;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Item;

public abstract class AbstractRecipeGenerator {

	public static Item[] CUSTOM_GLASS;
	public static Item[] MACHINES;
	public static Item[] ORES;
	public static Item[] DEEPSLATE_ORES;
	public static Item[] PIPES;
	public static Item[] RAW_ORE_BLOCKS;
	public static Item[] STORAGE_BLOCKS;
	public static Item[] WIRES;

	public static Item[] CERAMICS;
	public static Item[] CIRCUITS;
	public static Item[] CRYSTALS;
	public static Item[] DRILL_HEADS;
	public static Item[] DUSTS;
	public static Item[] GEARS;
	public static Item[] IMPURE_DUSTS;
	public static Item[] INGOTS;
	public static Item[] UPGRADES;
	public static Item[] NUGGETS;
	public static Item[] OXIDES;
	public static Item[] PLATES;
	public static Item[] RAW_ORES;
	public static Item[] RODS;

	public AbstractRecipeGenerator() {

		CUSTOM_GLASS = ElectrodynamicsItems.getAllItemForSubtype(SubtypeGlass.values());
		MACHINES = ElectrodynamicsItems.getAllItemForSubtype(SubtypeMachine.values());
		ORES = ElectrodynamicsItems.getAllItemForSubtype(SubtypeOre.values());
		DEEPSLATE_ORES = ElectrodynamicsItems.getAllItemForSubtype(SubtypeOreDeepslate.values());
		PIPES = ElectrodynamicsItems.getAllItemForSubtype(SubtypeFluidPipe.values());
		RAW_ORE_BLOCKS = ElectrodynamicsItems.getAllItemForSubtype(SubtypeRawOreBlock.values());
		STORAGE_BLOCKS = ElectrodynamicsItems.getAllItemForSubtype(SubtypeResourceBlock.values());
		WIRES = ElectrodynamicsItems.getAllItemForSubtype(SubtypeWire.values());

		CERAMICS = ElectrodynamicsItems.getAllItemForSubtype(SubtypeCeramic.values());
		CIRCUITS = ElectrodynamicsItems.getAllItemForSubtype(SubtypeCircuit.values());
		CRYSTALS = ElectrodynamicsItems.getAllItemForSubtype(SubtypeCrystal.values());
		DRILL_HEADS = ElectrodynamicsItems.getAllItemForSubtype(SubtypeDrillHead.values());
		DUSTS = ElectrodynamicsItems.getAllItemForSubtype(SubtypeDust.values());
		GEARS = ElectrodynamicsItems.getAllItemForSubtype(SubtypeGear.values());
		IMPURE_DUSTS = ElectrodynamicsItems.getAllItemForSubtype(SubtypeImpureDust.values());
		INGOTS = ElectrodynamicsItems.getAllItemForSubtype(SubtypeIngot.values());
		UPGRADES = ElectrodynamicsItems.getAllItemForSubtype(SubtypeItemUpgrade.values());
		NUGGETS = ElectrodynamicsItems.getAllItemForSubtype(SubtypeNugget.values());
		OXIDES = ElectrodynamicsItems.getAllItemForSubtype(SubtypeOxide.values());
		PLATES = ElectrodynamicsItems.getAllItemForSubtype(SubtypePlate.values());
		RAW_ORES = ElectrodynamicsItems.getAllItemForSubtype(SubtypeRawOre.values());
		RODS = ElectrodynamicsItems.getAllItemForSubtype(SubtypeRod.values());

	}

	public abstract void addRecipes(Consumer<FinishedRecipe> consumer);

}
