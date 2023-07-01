package electrodynamics.datagen.server.recipe.types.vanilla;

import java.util.function.Consumer;

import electrodynamics.api.References;
import electrodynamics.common.block.subtype.SubtypeGlass;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.block.subtype.SubtypeFluidPipe;
import electrodynamics.common.block.subtype.SubtypeRawOreBlock;
import electrodynamics.common.block.subtype.SubtypeResourceBlock;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.block.subtype.SubtypeWire.Conductor;
import electrodynamics.common.block.subtype.SubtypeWire.InsulationMaterial;
import electrodynamics.common.block.subtype.SubtypeWire.WireClass;
import electrodynamics.common.block.subtype.SubtypeWire.WireColor;
import electrodynamics.common.item.subtype.SubtypeCeramic;
import electrodynamics.common.item.subtype.SubtypeCircuit;
import electrodynamics.common.item.subtype.SubtypeCrystal;
import electrodynamics.common.item.subtype.SubtypeDrillHead;
import electrodynamics.common.item.subtype.SubtypeDust;
import electrodynamics.common.item.subtype.SubtypeGear;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.common.item.subtype.SubtypeNugget;
import electrodynamics.common.item.subtype.SubtypeOxide;
import electrodynamics.common.item.subtype.SubtypePlate;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.datagen.utils.recipe.AbstractRecipeGenerator;
import electrodynamics.datagen.utils.recipe.ElectrodynamicsShapedCraftingRecipe;
import electrodynamics.datagen.utils.recipe.ElectrodynamicsShapelessCraftingRecipe;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.PartialNBTIngredient;

public class ElectrodynamicsCraftingTableRecipes extends AbstractRecipeGenerator {

	@Override
	public void addRecipes(Consumer<FinishedRecipe> consumer) {

		addMachine(consumer);
		addGear(consumer);
		addWires(consumer);

		ElectrodynamicsShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_BATTERY.get(), 1)
				//
				.addPattern(" C ")
				//
				.addPattern("TRT")
				//
				.addPattern("TWT")
				//
				.addKey('C', ItemTags.COALS)
				//
				.addKey('T', ElectrodynamicsTags.Items.INGOT_TIN)
				//
				.addKey('R', Tags.Items.DUSTS_REDSTONE)
				//
				.addKey('W', WIRES[SubtypeWire.copper.ordinal()])
				//
				.complete(References.ID, "battery_basic", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_LITHIUMBATTERY.get(), 1)
				//
				.addPattern(" L ")
				//
				.addPattern("SCS")
				//
				.addPattern("SWS")
				//
				.addKey('L', ElectrodynamicsTags.Items.PLATE_LITHIUM)
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('C', OXIDES[SubtypeOxide.thionylchloride.ordinal()])
				//
				.addKey('W', WIRES[SubtypeWire.gold.ordinal()])
				//
				.complete(References.ID, "battery_lithium", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_CARBYNEBATTERY.get(), 1)
				//
				.addPattern(" S ")
				//
				.addPattern("TNT")
				//
				.addPattern("TNT")
				//
				.addKey('S', WIRES[SubtypeWire.superconductive.ordinal()])
				//
				.addKey('T', ElectrodynamicsTags.Items.PLATE_TITANIUMCARBIDE)
				//
				.addKey('N', ElectrodynamicsTags.Items.DUST_NETHERITE)
				//
				.complete(References.ID, "battery_carbyne", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(CERAMICS[SubtypeCeramic.fuse.ordinal()], 1)
				//
				.addPattern("#P#")
				//
				.addPattern(" W ")
				//
				.addPattern("#P#")
				//
				.addKey('#', CERAMICS[SubtypeCeramic.cooked.ordinal()])
				//
				.addKey('P', CERAMICS[SubtypeCeramic.plate.ordinal()])
				//
				.addKey('W', WIRES[SubtypeWire.copper.ordinal()])
				//
				.complete(References.ID, "ceramic_fuse", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(CERAMICS[SubtypeCeramic.plate.ordinal()], 1)
				//
				.addPattern("###")
				//
				.addKey('#', CERAMICS[SubtypeCeramic.cooked.ordinal()])
				//
				.complete(References.ID, "ceramic_plate", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(CERAMICS[SubtypeCeramic.wet.ordinal()], 4)
				//
				.addPattern("SCS")
				//
				.addPattern("CWC")
				//
				.addPattern("SCS")
				//
				.addKey('S', Tags.Items.SAND)
				//
				.addKey('C', Items.CLAY_BALL)
				//
				.addKey('W', Items.WATER_BUCKET)
				//
				.complete(References.ID, "wet_ceramic", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(CIRCUITS[SubtypeCircuit.basic.ordinal()], 1)
				//
				.addPattern("WRW")
				//
				.addPattern("RPR")
				//
				.addPattern("WRW")
				//
				.addKey('W', ElectrodynamicsTags.Items.INSULATED_COPPER_WIRES)
				//
				.addKey('R', Tags.Items.DUSTS_REDSTONE)
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.complete(References.ID, "circuit_basic", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(CIRCUITS[SubtypeCircuit.advanced.ordinal()], 1)
				//
				.addPattern("QQQ")
				//
				.addPattern("CDC")
				//
				.addPattern("QQQ")
				//
				.addKey('Q', Tags.Items.GEMS_QUARTZ)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_BASIC)
				//
				.addKey('D', Tags.Items.GEMS_DIAMOND)
				//
				.complete(References.ID, "circuit_advanced", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(CIRCUITS[SubtypeCircuit.elite.ordinal()], 1)
				//
				.addPattern("GGG")
				//
				.addPattern("CBC")
				//
				.addPattern("GGG")
				//
				.addKey('G', Tags.Items.INGOTS_GOLD)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ADVANCED)
				//
				.addKey('B', Tags.Items.STORAGE_BLOCKS_LAPIS)
				//
				.complete(References.ID, "circuit_elite", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(CIRCUITS[SubtypeCircuit.ultimate.ordinal()], 1)
				//
				.addPattern("OPO")
				//
				.addPattern("COC")
				//
				.addPattern("OPO")
				//
				.addKey('O', ElectrodynamicsTags.Items.DUST_OBSIDIAN)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ELITE)
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.complete(References.ID, "circuit_ultimate", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_COIL.get(), 1)
				//
				.addPattern(" W ")
				//
				.addPattern("WIW")
				//
				.addPattern(" W ")
				//
				.addKey('W', WIRES[SubtypeWire.copper.ordinal()])
				//
				.addKey('I', Tags.Items.INGOTS_IRON)
				//
				.complete(References.ID, "copper_coil", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_RAWCOMPOSITEPLATING.get(), 6)
				//
				.addPattern("TTT")
				//
				.addPattern("CCC")
				//
				.addPattern("PPP")
				//
				.addKey('T', ElectrodynamicsTags.Items.PLATE_TITANIUM)
				//
				.addKey('C', CERAMICS[SubtypeCeramic.plate.ordinal()])
				//
				.addKey('P', ElectrodynamicsTags.Items.PLASTIC)
				//
				.complete(References.ID, "raw_composite_plating", consumer);

		for (SubtypeNugget nugget : SubtypeNugget.values()) {
			ElectrodynamicsShapedCraftingRecipe.start(nugget.productIngot.get(), 1)
					//
					.addPattern("NNN")
					//
					.addPattern("NNN")
					//
					.addPattern("NNN")
					//
					.addKey('N', nugget.tag)
					//
					.complete(References.ID, nugget.name() + "_nuggets_to_" + nugget.name() + "_ingot", consumer);
		}

		ElectrodynamicsShapedCraftingRecipe.start(DRILL_HEADS[SubtypeDrillHead.hslasteel.ordinal()], 1)
				//
				.addPattern(" I ")
				//
				.addPattern("IPI")
				//
				.addKey('I', ElectrodynamicsTags.Items.INGOT_HSLASTEEL)
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_HSLASTEEL)
				//
				.complete(References.ID, "drill_head_hslasteel", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(DRILL_HEADS[SubtypeDrillHead.stainlesssteel.ordinal()], 1)
				//
				.addPattern(" I ")
				//
				.addPattern("IPI")
				//
				.addKey('I', ElectrodynamicsTags.Items.INGOT_STAINLESSSTEEL)
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STAINLESSSTEEL)
				//
				.complete(References.ID, "drill_head_stainlesssteel", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(DRILL_HEADS[SubtypeDrillHead.steel.ordinal()], 1)
				//
				.addPattern(" I ")
				//
				.addPattern("IPI")
				//
				.addKey('I', ElectrodynamicsTags.Items.INGOT_STEEL)
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.complete(References.ID, "drill_head_steel", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(DRILL_HEADS[SubtypeDrillHead.titanium.ordinal()], 1)
				//
				.addPattern(" I ")
				//
				.addPattern("IPI")
				//
				.addKey('I', ElectrodynamicsTags.Items.INGOT_TITANIUM)
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_TITANIUM)
				//
				.complete(References.ID, "drill_head_titanium", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(DRILL_HEADS[SubtypeDrillHead.titaniumcarbide.ordinal()], 1)
				//
				.addPattern(" I ")
				//
				.addPattern("IPI")
				//
				.addKey('I', ElectrodynamicsTags.Items.INGOT_TITANIUMCARBIDE)
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_TITANIUMCARBIDE)
				//
				.complete(References.ID, "drill_head_titaniumcarbide", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(DUSTS[SubtypeDust.superconductive.ordinal()], 9)
				//
				.addPattern("#S#")
				//
				.addPattern("G#G")
				//
				.addPattern("#S#")
				//
				.addKey('#', ElectrodynamicsTags.Items.DUST_ENDEREYE)
				//
				.addKey('S', ElectrodynamicsTags.Items.DUST_SILVER)
				//
				.addKey('G', ElectrodynamicsTags.Items.DUST_GOLD)
				//
				.complete(References.ID, "dust_superconductive", consumer);

		for (SubtypeResourceBlock block : SubtypeResourceBlock.values()) {
			ElectrodynamicsShapedCraftingRecipe.start(STORAGE_BLOCKS[block.ordinal()], 1)
					//
					.addPattern("III")
					//
					.addPattern("III")
					//
					.addPattern("III")
					//
					.addKey('I', block.sourceIngot)
					//
					.complete(References.ID, "resource_block_" + block.name(), consumer);
		}

		for (SubtypeRawOreBlock block : SubtypeRawOreBlock.values()) {
			ElectrodynamicsShapedCraftingRecipe.start(RAW_ORE_BLOCKS[block.ordinal()], 1)
					//
					.addPattern("RRR")
					//
					.addPattern("RRR")
					//
					.addPattern("RRR")
					//
					.addKey('R', block.sourceRawOre)
					//
					.complete(References.ID, "raw_ore_block_" + block.name(), consumer);
		}

		ElectrodynamicsShapedCraftingRecipe.start(WIRES[SubtypeWire.copper.ordinal()], 1)
				//
				.addPattern("C")
				//
				.addPattern("C")
				//
				.addKey('C', Tags.Items.INGOTS_COPPER)
				//
				.complete(References.ID, "wire_copper", consumer);

		for (SubtypeGear gear : SubtypeGear.values()) {
			ElectrodynamicsShapedCraftingRecipe.start(GEARS[gear.ordinal()], 1)
					//
					.addPattern(" I ")
					//
					.addPattern("I I")
					//
					.addPattern(" I ")
					//
					.addKey('I', gear.sourceIngot)
					//
					.complete(References.ID, "gear_" + gear.name(), consumer);
		}

		ElectrodynamicsShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_MOTOR.get(), 1)
				//
				.addPattern(" S ")
				//
				.addPattern("WCW")
				//
				.addPattern(" S ")
				//
				.addKey('S', ElectrodynamicsTags.Items.INGOT_STEEL)
				//
				.addKey('W', ElectrodynamicsTags.Items.INSULATED_COPPER_WIRES)
				//
				.addKey('C', ElectrodynamicsItems.ITEM_COIL.get())
				//
				.complete(References.ID, "motor_steel", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_MOTOR.get(), 4)
				//
				.addPattern(" S ")
				//
				.addPattern("WCW")
				//
				.addPattern(" S ")
				//
				.addKey('S', ElectrodynamicsTags.Items.INGOT_STAINLESSSTEEL)
				//
				.addKey('W', ElectrodynamicsTags.Items.INSULATED_COPPER_WIRES)
				//
				.addKey('C', ElectrodynamicsItems.ITEM_COIL.get())
				//
				.complete(References.ID, "motor_stainlesssteel", consumer);

		for (SubtypeFluidPipe pipe : SubtypeFluidPipe.values()) {
			ElectrodynamicsShapedCraftingRecipe.start(PIPES[pipe.ordinal()], 6)
					//
					.addPattern("III")
					//
					.addPattern("   ")
					//
					.addPattern("III")
					//
					.addKey('I', pipe.sourceIngot)
					//
					.complete(References.ID, "pipe_" + pipe.name() + "_horizontal", consumer);

			ElectrodynamicsShapedCraftingRecipe.start(PIPES[pipe.ordinal()], 6)
					//
					.addPattern("I I")
					//
					.addPattern("I I")
					//
					.addPattern("I I")
					//
					.addKey('I', pipe.sourceIngot)
					//
					.complete(References.ID, "pipe_" + pipe.name() + "_vertical", consumer);
		}

		ElectrodynamicsShapedCraftingRecipe.start(PLATES[SubtypePlate.bronze.ordinal()], 1)
				//
				.addPattern("II")
				//
				.addPattern("II")
				//
				.addKey('I', ElectrodynamicsTags.Items.INGOT_BRONZE)
				//
				.complete(References.ID, "plate_bronze", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(PLATES[SubtypePlate.iron.ordinal()], 1)
				//
				.addPattern("II")
				//
				.addPattern("II")
				//
				.addKey('I', Tags.Items.INGOTS_IRON)
				//
				.complete(References.ID, "plate_iron", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(PLATES[SubtypePlate.lead.ordinal()], 1)
				//
				.addPattern("II")
				//
				.addPattern("II")
				//
				.addKey('I', ElectrodynamicsTags.Items.INGOT_LEAD)
				//
				.complete(References.ID, "plate_lead", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(PLATES[SubtypePlate.steel.ordinal()], 1)
				//
				.addPattern("II")
				//
				.addPattern("II")
				//
				.addKey('I', ElectrodynamicsTags.Items.INGOT_STEEL)
				//
				.complete(References.ID, "plate_steel", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_SEISMICMARKER.get(), 1)
				//
				.addPattern(" P ")
				//
				.addPattern("PAP")
				//
				.addPattern(" P ")
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('A', Tags.Items.GEMS_AMETHYST)
				//
				.complete(References.ID, "seismic_marker", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_SOLARPANELPLATE.get(), 1)
				//
				.addPattern("BGB")
				//
				.addPattern("RWR")
				//
				.addPattern("ICI")
				//
				.addKey('B', Items.BLUE_STAINED_GLASS_PANE)
				//
				.addKey('G', Items.GRAY_STAINED_GLASS_PANE)
				//
				.addKey('R', Tags.Items.DUSTS_REDSTONE)
				//
				.addKey('W', WIRES[SubtypeWire.copper.ordinal()])
				//
				.addKey('I', Tags.Items.INGOTS_IRON)
				//
				.addKey('C', Tags.Items.INGOTS_COPPER)
				//
				.complete(References.ID, "solar_panel_plate", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(UPGRADES[SubtypeItemUpgrade.advancedcapacity.ordinal()], 1)
				//
				.addPattern("PBP")
				//
				.addPattern("BWB")
				//
				.addPattern("CBC")
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('B', UPGRADES[SubtypeItemUpgrade.basiccapacity.ordinal()])
				//
				.addKey('W', ElectrodynamicsTags.Items.INSULATED_COPPER_WIRES)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ADVANCED)
				//
				.complete(References.ID, "upgrade_advanced_capacity", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(UPGRADES[SubtypeItemUpgrade.advancedspeed.ordinal()], 1)
				//
				.addPattern("PGP")
				//
				.addPattern("BWB")
				//
				.addPattern("CGC")
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('G', ElectrodynamicsTags.Items.GEAR_BRONZE)
				//
				.addKey('B', UPGRADES[SubtypeItemUpgrade.basicspeed.ordinal()])
				//
				.addKey('W', ElectrodynamicsTags.Items.INSULATED_COPPER_WIRES)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ADVANCED)
				//
				.complete(References.ID, "upgrade_advanced_speed", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(UPGRADES[SubtypeItemUpgrade.basiccapacity.ordinal()], 1)
				//
				.addPattern("PBP")
				//
				.addPattern("BWB")
				//
				.addPattern("CBC")
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('B', ElectrodynamicsItems.ITEM_BATTERY.get())
				//
				.addKey('W', ElectrodynamicsTags.Items.INSULATED_COPPER_WIRES)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_BASIC)
				//
				.complete(References.ID, "upgrade_basic_capacity", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(UPGRADES[SubtypeItemUpgrade.basicspeed.ordinal()], 1)
				//
				.addPattern("PGP")
				//
				.addPattern("WWW")
				//
				.addPattern("CGC")
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('G', ElectrodynamicsTags.Items.GEAR_BRONZE)
				//
				.addKey('W', ElectrodynamicsTags.Items.INSULATED_GOLD_WIRES)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_BASIC)
				//
				.complete(References.ID, "upgrade_basic_speed", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(UPGRADES[SubtypeItemUpgrade.experience.ordinal()], 1)
				//
				.addPattern("PBP")
				//
				.addPattern("BWB")
				//
				.addPattern("PBP")
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_BRONZE)
				//
				.addKey('B', Items.GLASS_BOTTLE)
				//
				.addKey('W', ElectrodynamicsTags.Items.INSULATED_COPPER_WIRES)
				//
				.complete(References.ID, "upgrade_experience", consumer);

		ItemStack fortuneBook = new ItemStack(Items.ENCHANTED_BOOK);
		EnchantedBookItem.addEnchantment(fortuneBook, new EnchantmentInstance(Enchantments.BLOCK_FORTUNE, 1));
		ElectrodynamicsShapedCraftingRecipe.start(UPGRADES[SubtypeItemUpgrade.fortune.ordinal()], 1)
				//
				.addPattern("PCP")
				//
				.addPattern("CBC")
				//
				.addPattern("PCP")
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ELITE)
				//
				.addKey('B', PartialNBTIngredient.of(fortuneBook.getItem(), fortuneBook.getTag()))
				//
				.complete(References.ID, "upgrade_fortune", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(UPGRADES[SubtypeItemUpgrade.improvedsolarcell.ordinal()], 1)
				//
				.addPattern("PPP")
				//
				.addPattern("BCB")
				//
				.addPattern("BSB")
				//
				.addKey('P', ElectrodynamicsItems.ITEM_SOLARPANELPLATE.get())
				//
				.addKey('B', ElectrodynamicsTags.Items.PLATE_BRONZE)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_BASIC)
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.complete(References.ID, "upgrade_improved_solar_cell", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(UPGRADES[SubtypeItemUpgrade.iteminput.ordinal()], 4)
				//
				.addPattern("ACA")
				//
				.addPattern("CPC")
				//
				.addPattern("ACA")
				//
				.addKey('A', ElectrodynamicsTags.Items.PLATE_ALUMINUM)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_BASIC)
				//
				.addKey('P', Items.STICKY_PISTON)
				//
				.complete(References.ID, "upgrade_item_input", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(UPGRADES[SubtypeItemUpgrade.itemoutput.ordinal()], 4)
				//
				.addPattern("ACA")
				//
				.addPattern("CPC")
				//
				.addPattern("ACA")
				//
				.addKey('A', ElectrodynamicsTags.Items.PLATE_ALUMINUM)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_BASIC)
				//
				.addKey('P', Items.PISTON)
				//
				.complete(References.ID, "upgrade_item_output", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(UPGRADES[SubtypeItemUpgrade.itemvoid.ordinal()], 4)
				//
				.addPattern("PCP")
				//
				.addPattern("CBC")
				//
				.addPattern("PCP")
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('C', Items.CACTUS)
				//
				.addKey('B', ElectrodynamicsTags.Items.CIRCUITS_BASIC)
				//
				.complete(References.ID, "upgrade_item_void", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(UPGRADES[SubtypeItemUpgrade.range.ordinal()], 1)
				//
				.addPattern("PWP")
				//
				.addPattern("WBW")
				//
				.addPattern("PWP")
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('W', ElectrodynamicsTags.Items.INSULATED_COPPER_WIRES)
				//
				.addKey('B', ElectrodynamicsTags.Items.CIRCUITS_BASIC)
				//
				.complete(References.ID, "upgrade_range", consumer);

		ItemStack silkTouchBook = new ItemStack(Items.ENCHANTED_BOOK);
		EnchantedBookItem.addEnchantment(silkTouchBook, new EnchantmentInstance(Enchantments.SILK_TOUCH, 1));
		ElectrodynamicsShapedCraftingRecipe.start(UPGRADES[SubtypeItemUpgrade.silktouch.ordinal()], 1)
				//
				.addPattern("PCP")
				//
				.addPattern("CBC")
				//
				.addPattern("PCP")
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ULTIMATE)
				//
				.addKey('B', PartialNBTIngredient.of(silkTouchBook.getItem(), silkTouchBook.getTag()))
				//
				.complete(References.ID, "upgrade_silk_touch", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(UPGRADES[SubtypeItemUpgrade.stator.ordinal()], 1)
				//
				.addPattern("PCP")
				//
				.addPattern("CRC")
				//
				.addPattern("PCP")
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('C', ElectrodynamicsItems.ITEM_COIL.get())
				//
				.addKey('R', Tags.Items.DUSTS_REDSTONE)
				//
				.complete(References.ID, "upgrade_stator", consumer);

		ItemStack unbreakingBook = new ItemStack(Items.ENCHANTED_BOOK);
		EnchantedBookItem.addEnchantment(unbreakingBook, new EnchantmentInstance(Enchantments.UNBREAKING, 1));
		ElectrodynamicsShapedCraftingRecipe.start(UPGRADES[SubtypeItemUpgrade.unbreaking.ordinal()], 1)
				//
				.addPattern("PCP")
				//
				.addPattern("CBC")
				//
				.addPattern("PCP")
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ADVANCED)
				//
				.addKey('B', PartialNBTIngredient.of(unbreakingBook.getItem(), unbreakingBook.getTag()))
				//
				.complete(References.ID, "upgrade_unbreaking", consumer);

		ElectrodynamicsShapelessCraftingRecipe.start(UPGRADES[SubtypeItemUpgrade.itemoutput.ordinal()], 1)
				//
				.addIngredient(UPGRADES[SubtypeItemUpgrade.itemoutput.ordinal()])
				//
				.complete(References.ID, "upgrade_item_output_reset", consumer);

		ElectrodynamicsShapelessCraftingRecipe.start(UPGRADES[SubtypeItemUpgrade.iteminput.ordinal()], 1)
				//
				.addIngredient(UPGRADES[SubtypeItemUpgrade.iteminput.ordinal()])
				//
				.complete(References.ID, "upgrade_item_input_reset", consumer);

		for (SubtypeNugget nugget : SubtypeNugget.values()) {
			ElectrodynamicsShapelessCraftingRecipe.start(NUGGETS[nugget.ordinal()], 9)
					//
					.addIngredient(nugget.sourceIngot)
					//
					.complete(References.ID, nugget.name() + "_ingot_to_" + nugget.name() + "_nuggets", consumer);

		}

		ElectrodynamicsShapelessCraftingRecipe.start(DUSTS[SubtypeDust.bronze.ordinal()], 3)
				//
				.addIngredient(ElectrodynamicsTags.Items.DUST_COPPER)
				//
				.addIngredient(ElectrodynamicsTags.Items.DUST_COPPER)
				//
				.addIngredient(ElectrodynamicsTags.Items.DUST_TIN)
				//
				.complete(References.ID, "dust_bronze", consumer);

		for (SubtypeResourceBlock block : SubtypeResourceBlock.values()) {
			ElectrodynamicsShapelessCraftingRecipe.start(block.productIngot.get(), 9)
					//
					.addIngredient(STORAGE_BLOCKS[block.ordinal()])
					//
					.complete(References.ID, block.name() + "_ingot_from_storage_block", consumer);
		}

		for (SubtypeRawOreBlock block : SubtypeRawOreBlock.values()) {
			ElectrodynamicsShapelessCraftingRecipe.start(block.productRawOre.get(), 9)
					//
					.addIngredient(RAW_ORE_BLOCKS[block.ordinal()])
					//
					.complete(References.ID, "raw_ore_" + block.name() + "_from_storage_block", consumer);
		}

		ElectrodynamicsShapelessCraftingRecipe.start(Items.GUNPOWDER, 6)
				//
				.addIngredient(ElectrodynamicsTags.Items.DUST_SULFUR)
				//
				.addIngredient(ElectrodynamicsTags.Items.DUST_SALTPETER)
				//
				.addIngredient(ItemTags.COALS)
				//
				.complete(References.ID, "gunpowder", consumer);

		ElectrodynamicsShapelessCraftingRecipe.start(ElectrodynamicsItems.ITEM_INSULATION.get(), 20)
				//
				.addIngredient(ElectrodynamicsTags.Items.PLASTIC)
				//
				.complete(References.ID, "insulation_from_plastic", consumer);

		ElectrodynamicsShapelessCraftingRecipe.start(ElectrodynamicsItems.ITEM_INSULATION.get(), 1)
				//
				.addIngredient(ItemTags.WOOL)
				//
				.complete(References.ID, "insulation_from_wool", consumer);

		ElectrodynamicsShapelessCraftingRecipe.start(ElectrodynamicsItems.ITEM_INSULATION.get(), 6)
				//
				.addIngredient(Tags.Items.LEATHER)
				//
				.complete(References.ID, "insulation_from_leather", consumer);

		ElectrodynamicsShapelessCraftingRecipe.start(ElectrodynamicsItems.ITEM_INSULATION.get(), 3)
				//
				.addIngredient(Items.RABBIT_HIDE)
				//
				.complete(References.ID, "insulation_from_rabbit_hide", consumer);

		ElectrodynamicsShapelessCraftingRecipe.start(ElectrodynamicsItems.ITEM_MOLYBDENUMFERTILIZER.get(), 8)
				//
				.addIngredient(Items.BONE_MEAL)
				//
				.addIngredient(ElectrodynamicsTags.Items.DUST_MOLYBDENUM)
				//
				.addIngredient(ElectrodynamicsTags.Items.DUST_MOLYBDENUM)
				//
				.complete(References.ID, "molybdenum_fertilizer", consumer);

		ElectrodynamicsShapelessCraftingRecipe.start(Items.OBSIDIAN, 2)
				//
				.addIngredient(Items.WATER_BUCKET)
				//
				.addIngredient(Items.LAVA_BUCKET)
				//
				.complete(References.ID, "obsidian", consumer);

	}

	private void addMachine(Consumer<FinishedRecipe> consumer) {

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.advancedsolarpanel.ordinal()], 1)
				//
				.addPattern(" S ")
				//
				.addPattern("SPS")
				//
				.addPattern("III")
				//
				.addKey('S', MACHINES[SubtypeMachine.solarpanel.ordinal()])
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('I', Tags.Items.INGOTS_IRON)
				//
				.complete(References.ID, "machine_advanced_solar_panel", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.batterybox.ordinal()], 1)
				//
				.addPattern("BBB")
				//
				.addPattern("SWS")
				//
				.addPattern("BBB")
				//
				.addKey('B', ElectrodynamicsItems.ITEM_BATTERY.get())
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('W', ElectrodynamicsTags.Items.INSULATED_COPPER_WIRES)
				//
				.complete(References.ID, "machine_battery_box_basic", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.carbynebatterybox.ordinal()], 1)
				//
				.addPattern("BBB")
				//
				.addPattern("PWP")
				//
				.addPattern("BBB")
				//
				.addKey('B', ElectrodynamicsItems.ITEM_CARBYNEBATTERY.get())
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_HSLASTEEL)
				//
				.addKey('W', ElectrodynamicsTags.Items.THICK_SUPERCONDUCTIVE_WIRES)
				//
				.complete(References.ID, "machine_battery_box_carbyne", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.chargerhv.ordinal()], 1)
				//
				.addPattern("W W")
				//
				.addPattern("NMN")
				//
				.addPattern("PCP")
				//
				.addKey('W', ElectrodynamicsTags.Items.THICK_SUPERCONDUCTIVE_WIRES)
				//
				.addKey('N', Items.NETHERITE_SCRAP)
				//
				.addKey('M', MACHINES[SubtypeMachine.chargermv.ordinal()])
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_HSLASTEEL)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ULTIMATE)
				//
				.complete(References.ID, "machine_charger_hv", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.chargerlv.ordinal()], 1)
				//
				.addPattern("W W")
				//
				.addPattern("PBP")
				//
				.addPattern("PCP")
				//
				.addKey('W', ElectrodynamicsTags.Items.INSULATED_COPPER_WIRES)
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('B', MACHINES[SubtypeMachine.batterybox.ordinal()])
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ADVANCED)
				//
				.complete(References.ID, "machine_charger_lv", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.chargermv.ordinal()], 1)
				//
				.addPattern("W W")
				//
				.addPattern("PLP")
				//
				.addPattern("PCP")
				//
				.addKey('W', ElectrodynamicsTags.Items.THICK_GOLD_WIRES)
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STAINLESSSTEEL)
				//
				.addKey('L', MACHINES[SubtypeMachine.chargerlv.ordinal()])
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ELITE)
				//
				.complete(References.ID, "machine_charger_mv", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.chemicalcrystallizer.ordinal()], 1)
				//
				.addPattern("SCS")
				//
				.addPattern("GMG")
				//
				.addPattern("SCS")
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('G', ElectrodynamicsTags.Items.GEAR_BRONZE)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ELITE)
				//
				.complete(References.ID, "machine_chemical_crystallizer", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.chemicalmixer.ordinal()], 1)
				//
				.addPattern("SCS")
				//
				.addPattern("MGM")
				//
				.addPattern("SCS")
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ADVANCED)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.addKey('G', ElectrodynamicsTags.Items.GEAR_BRONZE)
				//
				.complete(References.ID, "machine_chemical_mixer", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.circuitbreaker.ordinal()], 1)
				//
				.addPattern("WCW")
				//
				.addPattern("PFP")
				//
				.addPattern("WCW")
				//
				.addKey('W', WIRES[SubtypeWire.copper.ordinal()])
				//
				.addKey('C', ElectrodynamicsItems.ITEM_COIL.get())
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('F', CERAMICS[SubtypeCeramic.fuse.ordinal()])
				//
				.complete(References.ID, "machine_circuit_breaker", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.coalgenerator.ordinal()], 1)
				//
				.addPattern("SSS")
				//
				.addPattern("SFS")
				//
				.addPattern("SMS")
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('F', Items.FURNACE)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.complete(References.ID, "machine_coal_generator_steel", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.coalgenerator.ordinal()], 1)
				//
				.addPattern("BBB")
				//
				.addPattern("BFB")
				//
				.addPattern("BMB")
				//
				.addKey('B', ElectrodynamicsTags.Items.PLATE_BRONZE)
				//
				.addKey('F', Items.FURNACE)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.complete(References.ID, "machine_coal_generator_bronze", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.combustionchamber.ordinal()], 1)
				//
				.addPattern("PMP")
				//
				.addPattern("GWG")
				//
				.addPattern("PCP")
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.addKey('G', ElectrodynamicsTags.Items.GEAR_IRON)
				//
				.addKey('W', ElectrodynamicsItems.ITEM_COIL.get())
				//
				.addKey('C', Items.CAULDRON)
				//
				.complete(References.ID, "machine_combustion_chamber", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.coolantresavoir.ordinal()], 1)
				//
				.addPattern("SSS")
				//
				.addPattern("STS")
				//
				.addPattern("SCS")
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('T', MACHINES[SubtypeMachine.tanksteel.ordinal()])
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_BASIC)
				//
				.complete(References.ID, "machine_coolant_resavoir", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.creativefluidsource.ordinal()], 1)
				//
				.addPattern("CCC")
				//
				.addPattern("CBC")
				//
				.addPattern("CCC")
				//
				.addKey('C', MACHINES[SubtypeMachine.creativepowersource.ordinal()])
				//
				.addKey('B', Items.BEDROCK)
				//
				.complete(References.ID, "machine_creative_fluid_source", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.creativepowersource.ordinal()], 1)
				//
				.addPattern("BBB")
				//
				.addPattern("BNB")
				//
				.addPattern("BBB")
				//
				.addKey('B', Items.BEDROCK)
				//
				.addKey('N', Items.NETHER_STAR)
				//
				.complete(References.ID, "machine_creative_power_source", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.downgradetransformer.ordinal()], 1)
				//
				.addPattern("ISI")
				//
				.addPattern("W C")
				//
				.addPattern("SIS")
				//
				.addKey('I', Tags.Items.INGOTS_IRON)
				//
				.addKey('S', ElectrodynamicsTags.Items.INGOT_STEEL)
				//
				.addKey('W', WIRES[SubtypeWire.copper.ordinal()])
				//
				.addKey('C', ElectrodynamicsItems.ITEM_COIL.get())
				//
				.complete(References.ID, "machine_downgrade_transformer", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.electricfurnace.ordinal()], 1)
				//
				.addPattern("SSS")
				//
				.addPattern("SCS")
				//
				.addPattern("SMS")
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_BASIC)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.complete(References.ID, "machine_electric_furnace", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.electricfurnacedouble.ordinal()], 1)
				//
				.addPattern("CSC")
				//
				.addPattern("GFG")
				//
				.addPattern("CSC")
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ADVANCED)
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('G', Tags.Items.INGOTS_GOLD)
				//
				.addKey('F', MACHINES[SubtypeMachine.electricfurnace.ordinal()])
				//
				.complete(References.ID, "machine_electric_furnace_double", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.electricfurnacetriple.ordinal()], 1)
				//
				.addPattern("COC")
				//
				.addPattern("DFD")
				//
				.addPattern("COC")
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ELITE)
				//
				.addKey('O', ElectrodynamicsTags.Items.DUST_OBSIDIAN)
				//
				.addKey('D', Tags.Items.GEMS_DIAMOND)
				//
				.addKey('F', MACHINES[SubtypeMachine.electricfurnacedouble.ordinal()])
				//
				.complete(References.ID, "machine_electric_furnace_triple", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.electricpump.ordinal()], 1)
				//
				.addPattern("SSS")
				//
				.addPattern("SMS")
				//
				.addPattern("SPS")
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.addKey('P', Items.PISTON)
				//
				.complete(References.ID, "machine_electric_pump", consumer);

		ElectrodynamicsShapelessCraftingRecipe.start(MACHINES[SubtypeMachine.electricarcfurnace.ordinal()], 1)
				//
				.addIngredient(MACHINES[SubtypeMachine.electricfurnace.ordinal()])
				//
				.addIngredient(Items.BLAST_FURNACE)
				//
				.complete(References.ID, "machine_electric_arc_furnace", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.electricarcfurnacedouble.ordinal()], 1)
				//
				.addPattern("CSC")
				//
				.addPattern("GEG")
				//
				.addPattern("CSC")
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ADVANCED)
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('G', Tags.Items.INGOTS_GOLD)
				//
				.addKey('E', MACHINES[SubtypeMachine.electricarcfurnace.ordinal()])
				//
				.complete(References.ID, "machine_electric_arc_furnace_double", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.electricarcfurnacetriple.ordinal()], 1)
				//
				.addPattern("COC")
				//
				.addPattern("DED")
				//
				.addPattern("COC")
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ELITE)
				//
				.addKey('O', ElectrodynamicsTags.Items.DUST_OBSIDIAN)
				//
				.addKey('D', Tags.Items.GEMS_DIAMOND)
				//
				.addKey('E', MACHINES[SubtypeMachine.electricarcfurnacedouble.ordinal()])
				//
				.complete(References.ID, "machine_electric_arc_furnace_triple", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.electrolyticseparator.ordinal()], 1)
				//
				.addPattern("PTP")
				//
				.addPattern("TXT")
				//
				.addPattern("PCP")
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('T', MACHINES[SubtypeMachine.tanksteel.ordinal()])
				//
				.addKey('X', MACHINES[SubtypeMachine.upgradetransformer.ordinal()])
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ADVANCED)
				//
				.complete(References.ID, "machine_electrolytic_separator", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.energizedalloyer.ordinal()], 1)
				//
				.addPattern("ACA")
				//
				.addPattern("CTC")
				//
				.addPattern("AEA")
				//
				.addKey('A', ElectrodynamicsTags.Items.PLATE_ALUMINUM)
				//
				.addKey('C', CERAMICS[SubtypeCeramic.plate.ordinal()])
				//
				.addKey('T', ElectrodynamicsItems.ITEM_TITANIUM_COIL.get())
				//
				.addKey('E', ElectrodynamicsTags.Items.CIRCUITS_ELITE)
				//
				.complete(References.ID, "machine_energized_alloyer", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.fermentationplant.ordinal()], 1)
				//
				.addPattern("IMI")
				//
				.addPattern("BCB")
				//
				.addPattern("IFI")
				//
				.addKey('I', ElectrodynamicsTags.Items.PLATE_IRON)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.addKey('B', ElectrodynamicsTags.Items.CIRCUITS_BASIC)
				//
				.addKey('C', Items.CAULDRON)
				//
				.addKey('F', Items.FURNACE)
				//
				.complete(References.ID, "machine_fermentation_plant", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.fluidvoid.ordinal()], 1)
				//
				.addPattern("SBS")
				//
				.addPattern("BCB")
				//
				.addPattern("SBS")
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('B', Items.BUCKET)
				//
				.addKey('C', Items.CACTUS)
				//
				.complete(References.ID, "machine_fluid_void", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.lithiumbatterybox.ordinal()], 1)
				//
				.addPattern("BBB")
				//
				.addPattern("SWS")
				//
				.addPattern("BBB")
				//
				.addKey('B', ElectrodynamicsItems.ITEM_LITHIUMBATTERY.get())
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STAINLESSSTEEL)
				//
				.addKey('W', ElectrodynamicsTags.Items.INSULATED_GOLD_WIRES)
				//
				.complete(References.ID, "battery_box_lithium", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.lathe.ordinal()], 1)
				//
				.addPattern("AMA")
				//
				.addPattern("MCM")
				//
				.addPattern("AWA")
				//
				.addKey('A', ElectrodynamicsTags.Items.PLATE_ALUMINUM)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ELITE)
				//
				.addKey('W', MACHINES[SubtypeMachine.wiremill.ordinal()])
				//
				.complete(References.ID, "lathe", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_LOGISTICALMANAGER.get(), 1)
				//
				.addPattern("SCS")
				//
				.addPattern("CPC")
				//
				.addPattern("SCS")
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_BASIC)
				//
				.addKey('P', Items.PISTON)
				//
				.complete(References.ID, "machine_logistical_manager", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.hydroelectricgenerator.ordinal()], 1)
				//
				.addPattern(" M ")
				//
				.addPattern("WSW")
				//
				.addPattern("PSP")
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.addKey('P', Items.PISTON)
				//
				.addKey('W', ItemTags.PLANKS)
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.complete(References.ID, "machine_hydroelectric_generator", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.mineralcrusher.ordinal()], 1)
				//
				.addPattern("SCS")
				//
				.addPattern("GPG")
				//
				.addPattern("SWS")
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ADVANCED)
				//
				.addKey('G', ElectrodynamicsTags.Items.GEAR_BRONZE)
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_BRONZE)
				//
				.addKey('W', WIRES[SubtypeWire.copper.ordinal()])
				//
				.complete(References.ID, "machine_mineral_crusher", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.mineralcrusherdouble.ordinal()], 1)
				//
				.addPattern("EDE")
				//
				.addPattern("MCM")
				//
				.addPattern("EDE")
				//
				.addKey('E', ElectrodynamicsTags.Items.CIRCUITS_ELITE)
				//
				.addKey('D', Tags.Items.GEMS_DIAMOND)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.addKey('C', MACHINES[SubtypeMachine.mineralcrusher.ordinal()])
				//
				.complete(References.ID, "machine_mineral_crusher_double", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.mineralcrushertriple.ordinal()], 1)
				//
				.addPattern("UVU")
				//
				.addPattern("MCM")
				//
				.addPattern("UVU")
				//
				.addKey('U', ElectrodynamicsTags.Items.CIRCUITS_ULTIMATE)
				//
				.addKey('V', CRYSTALS[SubtypeCrystal.vanadium.ordinal()])
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.addKey('C', MACHINES[SubtypeMachine.mineralcrusherdouble.ordinal()])
				//
				.complete(References.ID, "machine_mineral_crusher_triple", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.mineralgrinder.ordinal()], 1)
				//
				.addPattern("PCP")
				//
				.addPattern("GMG")
				//
				.addPattern("PGP")
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_BASIC)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.addKey('G', ElectrodynamicsTags.Items.GEAR_COPPER)
				//
				.complete(References.ID, "machine_mineral_grinder", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.mineralgrinderdouble.ordinal()], 1)
				//
				.addPattern("CGC")
				//
				.addPattern("BMB")
				//
				.addPattern("CGC")
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ADVANCED)
				//
				.addKey('B', ElectrodynamicsTags.Items.GEAR_BRONZE)
				//
				.addKey('G', Tags.Items.INGOTS_GOLD)
				//
				.addKey('M', MACHINES[SubtypeMachine.mineralgrinder.ordinal()])
				//
				.complete(References.ID, "machine_mineral_grinder_double", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.mineralgrindertriple.ordinal()], 1)
				//
				.addPattern("CDC")
				//
				.addPattern("GMG")
				//
				.addPattern("CDC")
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ELITE)
				//
				.addKey('G', ElectrodynamicsTags.Items.GEAR_STEEL)
				//
				.addKey('D', ElectrodynamicsTags.Items.DUST_SUPERCONDUCTIVE)
				//
				.addKey('M', MACHINES[SubtypeMachine.mineralgrinderdouble.ordinal()])
				//
				.complete(References.ID, "machine_mineral_grinder_triple", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.mineralwasher.ordinal()], 1)
				//
				.addPattern("SGS")
				//
				.addPattern("CEC")
				//
				.addPattern("PEP")
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('G', ElectrodynamicsTags.Items.GEAR_STEEL)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ULTIMATE)
				//
				.addKey('P', PIPES[SubtypeFluidPipe.steel.ordinal()])
				//
				.addKey('E', MACHINES[SubtypeMachine.electricpump.ordinal()])
				//
				.complete(References.ID, "machine_mineral_washer", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.motorcomplex.ordinal()], 1)
				//
				.addPattern("PGP")
				//
				.addPattern("GCM")
				//
				.addPattern("PGP")
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('G', ElectrodynamicsTags.Items.GEAR_STEEL)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ELITE)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.complete(References.ID, "machine_motor_complex", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.multimeterblock.ordinal()], 1)
				//
				.addPattern(" S ")
				//
				.addPattern("SMS")
				//
				.addPattern(" S ")
				//
				.addKey('S', ElectrodynamicsTags.Items.INGOT_STEEL)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MULTIMETER.get())
				//
				.complete(References.ID, "machine_multimeter_block", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.oxidationfurnace.ordinal()], 1)
				//
				.addPattern("PGP")
				//
				.addPattern("CFC")
				//
				.addPattern("PMP")
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('G', ElectrodynamicsTags.Items.GEAR_STEEL)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ADVANCED)
				//
				.addKey('F', MACHINES[SubtypeMachine.electricfurnace.ordinal()])
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.complete(References.ID, "machine_oxidation_furnace", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.quarry.ordinal()], 1)
				//
				.addPattern("PCP")
				//
				.addPattern("CDC")
				//
				.addPattern("PCP")
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ELITE)
				//
				.addKey('D', Items.DIAMOND_PICKAXE)
				//
				.complete(References.ID, "machine_quarry", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.reinforcedalloyer.ordinal()], 1)
				//
				.addPattern("CSC")
				//
				.addPattern("SAS")
				//
				.addPattern("CTC")
				//
				.addKey('C', ElectrodynamicsItems.ITEM_COMPOSITEPLATING.get())
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STAINLESSSTEEL)
				//
				.addKey('A', MACHINES[SubtypeMachine.energizedalloyer.ordinal()])
				//
				.addKey('T', ElectrodynamicsItems.ITEM_TITANIUM_COIL.get())
				//
				.complete(References.ID, "machine_reinforced_alloyer", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.seismicrelay.ordinal()], 1)
				//
				.addPattern("PAP")
				//
				.addPattern("ACA")
				//
				.addPattern("MAM")
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('A', Tags.Items.GEMS_AMETHYST)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ULTIMATE)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_SEISMICMARKER.get())
				//
				.complete(References.ID, "machine_seismic_relay", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.solarpanel.ordinal()], 1)
				//
				.addPattern("SPS")
				//
				.addPattern("CIC")
				//
				.addPattern("IWI")
				//
				.addKey('S', ElectrodynamicsItems.ITEM_SOLARPANELPLATE.get())
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_BASIC)
				//
				.addKey('I', Tags.Items.INGOTS_IRON)
				//
				.addKey('W', WIRES[SubtypeWire.copper.ordinal()])
				//
				.complete(References.ID, "machine_solar_panel", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.tanksteel.ordinal()], 1)
				//
				.addPattern("SGS")
				//
				.addPattern("GCG")
				//
				.addPattern("SGS")
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('G', Tags.Items.GLASS)
				//
				.addKey('C', Items.CAULDRON)
				//
				.complete(References.ID, "machine_tank_steel", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.tankreinforced.ordinal()], 1)
				//
				.addPattern("SGS")
				//
				.addPattern("GTG")
				//
				.addPattern("SGS")
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STAINLESSSTEEL)
				//
				.addKey('G', CUSTOM_GLASS[SubtypeGlass.clear.ordinal()])
				//
				.addKey('T', MACHINES[SubtypeMachine.tanksteel.ordinal()])
				//
				.complete(References.ID, "machine_tank_reinforced", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.tankhsla.ordinal()], 1)
				//
				.addPattern("SGS")
				//
				.addPattern("GTG")
				//
				.addPattern("SGS")
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_HSLASTEEL)
				//
				.addKey('G', CUSTOM_GLASS[SubtypeGlass.aluminum.ordinal()])
				//
				.addKey('T', MACHINES[SubtypeMachine.tankreinforced.ordinal()])
				//
				.complete(References.ID, "machine_tank_hsla", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.thermoelectricgenerator.ordinal()], 1)
				//
				.addPattern("ISI")
				//
				.addPattern("SPS")
				//
				.addPattern("CFC")
				//
				.addKey('I', Tags.Items.INGOTS_IRON)
				//
				.addKey('S', ElectrodynamicsTags.Items.INGOT_STEEL)
				//
				.addKey('C', Tags.Items.INGOTS_COPPER)
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('F', Items.FURNACE)
				//
				.complete(References.ID, "machine_thermoelectric_generator", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.upgradetransformer.ordinal()], 1)
				//
				.addPattern("ISI")
				//
				.addPattern("C W")
				//
				.addPattern("SSS")
				//
				.addKey('I', Tags.Items.INGOTS_IRON)
				//
				.addKey('S', ElectrodynamicsTags.Items.INGOT_STEEL)
				//
				.addKey('W', WIRES[SubtypeWire.copper.ordinal()])
				//
				.addKey('C', ElectrodynamicsItems.ITEM_COIL.get())
				//
				.complete(References.ID, "machine_upgrade_transformer", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.windmill.ordinal()], 1)
				//
				.addPattern(" GM")
				//
				.addPattern(" S ")
				//
				.addPattern("ISI")
				//
				.addKey('G', ElectrodynamicsTags.Items.GEAR_IRON)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('I', Tags.Items.INGOTS_IRON)
				//
				.complete(References.ID, "machine_windmill", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.wiremill.ordinal()], 1)
				//
				.addPattern("PBP")
				//
				.addPattern("MGM")
				//
				.addPattern("PGP")
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('B', ElectrodynamicsTags.Items.CIRCUITS_BASIC)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.addKey('G', ElectrodynamicsTags.Items.GEAR_BRONZE)
				//
				.complete(References.ID, "machine_wiremill", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.wiremilldouble.ordinal()], 1)
				//
				.addPattern("CGC")
				//
				.addPattern("WMW")
				//
				.addPattern("CGC")
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_BASIC)
				//
				.addKey('G', ElectrodynamicsTags.Items.GEAR_BRONZE)
				//
				.addKey('W', ElectrodynamicsTags.Items.INSULATED_SILVER_WIRES)
				//
				.addKey('M', MACHINES[SubtypeMachine.wiremill.ordinal()])
				//
				.complete(References.ID, "machine_wiremill_double", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(MACHINES[SubtypeMachine.wiremilltriple.ordinal()], 1)
				//
				.addPattern("CGC")
				//
				.addPattern("WMW")
				//
				.addPattern("CGC")
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ADVANCED)
				//
				.addKey('G', ElectrodynamicsTags.Items.GEAR_STEEL)
				//
				.addKey('W', ElectrodynamicsTags.Items.CERAMIC_SILVER_WIRES)
				//
				.addKey('M', MACHINES[SubtypeMachine.wiremilldouble.ordinal()])
				//
				.complete(References.ID, "machine_wiremill_triple", consumer);

	}

	private void addGear(Consumer<FinishedRecipe> consumer) {

		ElectrodynamicsShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_CANISTERREINFORCED.get(), 4)
				//
				.addPattern("STS")
				//
				.addPattern("A A")
				//
				.addPattern("STS")
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STAINLESSSTEEL)
				//
				.addKey('T', ElectrodynamicsTags.Items.PLATE_TITANIUM)
				//
				.addKey('A', CUSTOM_GLASS[SubtypeGlass.aluminum.ordinal()])
				//
				.complete(References.ID, "reinforced_canister", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_COMBATBOOTS.get(), 1)
				//
				.addPattern("THT")
				//
				.addPattern("TST")
				//
				.addPattern("TBT")
				//
				.addKey('T', ElectrodynamicsTags.Items.PLATE_TITANIUMCARBIDE)
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_HSLASTEEL)
				//
				.addKey('H', ElectrodynamicsItems.ITEM_HYDRAULICBOOTS.get())
				//
				.addKey('B', ElectrodynamicsItems.ITEM_COMPOSITEBOOTS.get())
				//
				.complete(References.ID, "combat_boots", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_COMBATCHESTPLATE.get(), 1)
				//
				.addPattern("TJT")
				//
				.addPattern("TST")
				//
				.addPattern("TCT")
				//
				.addKey('T', ElectrodynamicsTags.Items.PLATE_TITANIUMCARBIDE)
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_HSLASTEEL)
				//
				.addKey('J', ElectrodynamicsItems.ITEM_JETPACK.get())
				//
				.addKey('C', ElectrodynamicsItems.ITEM_COMPOSITECHESTPLATE.get())
				//
				.complete(References.ID, "combat_chestplate", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_COMBATHELMET.get(), 1)
				//
				.addPattern("TNT")
				//
				.addPattern("TST")
				//
				.addPattern("THT")
				//
				.addKey('T', ElectrodynamicsTags.Items.PLATE_TITANIUMCARBIDE)
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_HSLASTEEL)
				//
				.addKey('N', ElectrodynamicsItems.ITEM_NIGHTVISIONGOGGLES.get())
				//
				.addKey('H', ElectrodynamicsItems.ITEM_COMPOSITEHELMET.get())
				//
				.complete(References.ID, "combat_helmet", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_COMBATLEGGINGS.get(), 1)
				//
				.addPattern("TMT")
				//
				.addPattern("TST")
				//
				.addPattern("TLT")
				//
				.addKey('T', ElectrodynamicsTags.Items.PLATE_TITANIUMCARBIDE)
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_HSLASTEEL)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_SERVOLEGGINGS.get())
				//
				.addKey('L', ElectrodynamicsItems.ITEM_COMPOSITELEGGINGS.get())
				//
				.complete(References.ID, "combat_leggings", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_COMPOSITEBOOTS.get(), 1)
				//
				.addPattern("P P")
				//
				.addPattern("PSP")
				//
				.addKey('P', ElectrodynamicsItems.ITEM_COMPOSITEPLATING.get())
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.complete(References.ID, "composite_boots", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_COMPOSITECHESTPLATE.get(), 1)
				//
				.addPattern("PSP")
				//
				.addPattern("PPP")
				//
				.addPattern("PPP")
				//
				.addKey('P', ElectrodynamicsItems.ITEM_COMPOSITEPLATING.get())
				//
				.addKey('S', ElectrodynamicsTags.Items.PLASTIC)
				//
				.complete(References.ID, "composite_chestplate", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_COMPOSITEHELMET.get(), 1)
				//
				.addPattern("PPP")
				//
				.addPattern("PGP")
				//
				.addKey('P', ElectrodynamicsItems.ITEM_COMPOSITEPLATING.get())
				//
				.addKey('G', CUSTOM_GLASS[SubtypeGlass.aluminum.ordinal()])
				//
				.complete(References.ID, "composite_helmet", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_COMPOSITELEGGINGS.get(), 1)
				//
				.addPattern("PPP")
				//
				.addPattern("P P")
				//
				.addPattern("P P")
				//
				.addKey('P', ElectrodynamicsItems.ITEM_COMPOSITEPLATING.get())
				//
				.complete(References.ID, "composite_leggings", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_ELECTRICBATON.get(), 1)
				//
				.addPattern("  T")
				//
				.addPattern("PT ")
				//
				.addPattern("BC ")
				//
				.addKey('T', ElectrodynamicsTags.Items.ROD_STEEL)
				//
				.addKey('P', ElectrodynamicsTags.Items.PLASTIC)
				//
				.addKey('B', ElectrodynamicsItems.ITEM_LITHIUMBATTERY.get())
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ADVANCED)
				//
				.complete(References.ID, "electric_baton", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_ELECTRICCHAINSAW.get(), 1)
				//
				.addPattern("SP ")
				//
				.addPattern("IMB")
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('P', ElectrodynamicsTags.Items.PLASTIC)
				//
				.addKey('I', WIRES[SubtypeWire.iron.ordinal()])
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.addKey('B', ElectrodynamicsItems.ITEM_BATTERY.get())
				//
				.complete(References.ID, "electric_chainsaw", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_ELECTRICDRILL.get(), 1)
				//
				.addPattern("DMP")
				//
				.addPattern(" RB")
				//
				.addKey('D', DRILL_HEADS[SubtypeDrillHead.steel.ordinal()])
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('R', ElectrodynamicsTags.Items.ROD_STEEL)
				//
				.addKey('B', ElectrodynamicsItems.ITEM_BATTERY.get())
				//
				.complete(References.ID, "electric_drill", consumer);

		ElectrodynamicsShapelessCraftingRecipe.start(ElectrodynamicsItems.GUIDEBOOK.get(), 1)
				//
				.addIngredient(Items.BOOK)
				//
				.addIngredient(WIRES[SubtypeWire.copper.ordinal()])
				//
				.complete(References.ID, "guidebook", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_HYDRAULICBOOTS.get(), 1)
				//
				.addPattern("C C")
				//
				.addPattern("P P")
				//
				.addPattern("WSW")
				//
				.addKey('C', ElectrodynamicsItems.ITEM_CANISTERREINFORCED.get())
				//
				.addKey('P', Items.PISTON)
				//
				.addKey('W', ItemTags.WOOL)
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.complete(References.ID, "hydraulic_boots", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_CERAMICINSULATION.get(), 6)
				//
				.addPattern("P P")
				//
				.addPattern("PBP")
				//
				.addPattern("P P")
				//
				.addKey('P', CERAMICS[SubtypeCeramic.plate.ordinal()])
				//
				.addKey('B', Items.IRON_BARS)
				//
				.complete(References.ID, "ceramic_insulation", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_JETPACK.get(), 1)
				//
				.addPattern("CTC")
				//
				.addPattern("SPS")
				//
				.addPattern("P P")
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ADVANCED)
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('T', MACHINES[SubtypeMachine.tanksteel.ordinal()])
				//
				.addKey('P', PIPES[SubtypeFluidPipe.steel.ordinal()])
				//
				.complete(References.ID, "jetpack", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_MECHANIZEDCROSSBOW.get(), 1)
				//
				.addPattern(" S ")
				//
				.addPattern("SbC")
				//
				.addPattern(" MB")
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_BASIC)
				//
				.addKey('b', Items.CROSSBOW)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.addKey('B', ElectrodynamicsItems.ITEM_BATTERY.get())
				//
				.complete(References.ID, "mechanized_crossbow", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_MULTIMETER.get(), 1)
				//
				.addPattern("SWS")
				//
				.addPattern("SCS")
				//
				.addPattern("SWS")
				//
				.addKey('S', ElectrodynamicsTags.Items.INGOT_STEEL)
				//
				.addKey('W', ElectrodynamicsTags.Items.INSULATED_COPPER_WIRES)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_BASIC)
				//
				.complete(References.ID, "multimeter", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_NIGHTVISIONGOGGLES.get(), 1)
				//
				.addPattern("LLL")
				//
				.addPattern("PCP")
				//
				.addPattern("GBG")
				//
				.addKey('L', Tags.Items.LEATHER)
				//
				.addKey('P', Items.LIME_STAINED_GLASS_PANE)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ADVANCED)
				//
				.addKey('G', Items.GLOW_INK_SAC)
				//
				.addKey('B', ElectrodynamicsItems.ITEM_BATTERY.get())
				//
				.complete(References.ID, "night_vision_goggles", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_RUBBERBOOTS.get(), 1)
				//
				.addPattern("I I")
				//
				.addPattern("I I")
				//
				.addKey('I', ElectrodynamicsItems.ITEM_INSULATION.get())
				//
				.complete(References.ID, "rubber_boots", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_KINETICRAILGUN.get(), 1)
				//
				.addPattern("CXS")
				//
				.addPattern("LPR")
				//
				.addPattern("BCS")
				//
				.addKey('L', ElectrodynamicsItems.ITEM_LITHIUMBATTERY.get())
				//
				.addKey('X', MACHINES[SubtypeMachine.upgradetransformer.ordinal()])
				//
				.addKey('S', WIRES[SubtypeWire.superconductive.ordinal()])
				//
				.addKey('B', ElectrodynamicsTags.Items.STORAGE_BLOCK_STAINLESSSTEEL)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ELITE)
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STAINLESSSTEEL)
				//
				.addKey('R', ElectrodynamicsTags.Items.ROD_STAINLESSSTEEL)
				//
				.complete(References.ID, "rail_gun_kinetic", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_PLASMARAILGUN.get(), 1)
				//
				.addPattern("CXS")
				//
				.addPattern("LPR")
				//
				.addPattern("BCS")
				//
				.addKey('L', ElectrodynamicsItems.ITEM_CARBYNEBATTERY.get())
				//
				.addKey('X', MACHINES[SubtypeMachine.upgradetransformer.ordinal()])
				//
				.addKey('S', WIRES[SubtypeWire.superconductive.ordinal()])
				//
				.addKey('B', ElectrodynamicsTags.Items.STORAGE_BLOCK_HSLASTEEL)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ULTIMATE)
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_HSLASTEEL)
				//
				.addKey('R', ElectrodynamicsTags.Items.ROD_TITANIUMCARBIDE)
				//
				.complete(References.ID, "rail_gun_plasma", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_SEISMICSCANNER.get(), 1)
				//
				.addPattern("PQP")
				//
				.addPattern("ABA")
				//
				.addPattern("PCP")
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('Q', Tags.Items.GEMS_QUARTZ)
				//
				.addKey('A', Tags.Items.GEMS_AMETHYST)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_BASIC)
				//
				.addKey('B', ElectrodynamicsItems.ITEM_BATTERY.get())
				//
				.complete(References.ID, "seismic_scanner", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_SERVOLEGGINGS.get(), 1)
				//
				.addPattern("PCP")
				//
				.addPattern("MWM")
				//
				.addPattern("B B")
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_BASIC)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.addKey('W', ElectrodynamicsTags.Items.INSULATED_COPPER_WIRES)
				//
				.addKey('B', ElectrodynamicsItems.ITEM_BATTERY.get())
				//
				.complete(References.ID, "servo_leggings", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_WRENCH.get(), 1)
				//
				.addPattern(" S ")
				//
				.addPattern(" SS")
				//
				.addPattern("S  ")
				//
				.addKey('S', ElectrodynamicsTags.Items.INGOT_STEEL)
				//
				.complete(References.ID, "wrench", consumer);

	}

	private void addWires(Consumer<FinishedRecipe> consumer) {

		// Insulated Wires
		for (SubtypeWire wire : SubtypeWire.getWires(Conductor.values(), InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.BLACK)) {

			SubtypeWire uninsulated = SubtypeWire.getWire(wire.conductor, InsulationMaterial.BARE, WireClass.BARE, WireColor.NONE);

			ElectrodynamicsShapelessCraftingRecipe.start(WIRES[wire.ordinal()], 1)
					//
					.addIngredient(WIRES[uninsulated.ordinal()])
					//
					.addIngredient(ElectrodynamicsItems.ITEM_INSULATION.get())
					//
					.complete(References.ID, "wire_" + wire.name(), consumer);

		}

		// Logistics Wires
		for (SubtypeWire wire : SubtypeWire.getWires(Conductor.values(), InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.BLACK)) {

			SubtypeWire insulated = SubtypeWire.getWire(wire.conductor, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.BLACK);

			ElectrodynamicsShapelessCraftingRecipe.start(WIRES[wire.ordinal()], 1)
					//
					.addIngredient(insulated.itemTag)
					//
					.addIngredient(Tags.Items.DUSTS_REDSTONE)
					//
					.complete(References.ID, "wire_" + wire.name(), consumer);

		}

		// Ceramic Insulated
		for (SubtypeWire wire : SubtypeWire.getWires(Conductor.values(), InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.BROWN)) {

			SubtypeWire insulated = SubtypeWire.getWire(wire.conductor, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.BLACK);

			ElectrodynamicsShapelessCraftingRecipe.start(WIRES[wire.ordinal()], 1)
					//
					.addIngredient(insulated.itemTag)
					//
					.addIngredient(ElectrodynamicsItems.ITEM_CERAMICINSULATION.get())
					//
					.complete(References.ID, "wire_" + wire.name(), consumer);

		}

		// Highly Insulated
		for (SubtypeWire wire : SubtypeWire.getWires(Conductor.values(), InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.BLACK)) {

			SubtypeWire insulated = SubtypeWire.getWire(wire.conductor, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.BLACK);

			ElectrodynamicsShapelessCraftingRecipe.start(WIRES[wire.ordinal()], 2)
					//
					.addIngredient(insulated.itemTag)
					//
					.addIngredient(insulated.itemTag)
					//
					.addIngredient(insulated.itemTag)
					//
					.addIngredient(ElectrodynamicsItems.ITEM_INSULATION.get())
					//
					.addIngredient(ElectrodynamicsItems.ITEM_INSULATION.get())
					//
					.addIngredient(ElectrodynamicsItems.ITEM_INSULATION.get())
					//
					.complete(References.ID, "wire_" + wire.name(), consumer);

		}

		// Insulated Wires
		for (SubtypeWire wire : SubtypeWire.getWires(Conductor.values(), InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.values())) {
			if (wire.color.dyeTag == null) {
				continue;
			}
			ElectrodynamicsShapedCraftingRecipe.start(WIRES[wire.ordinal()], 8)
					//
					.addPattern("WWW")
					//
					.addPattern("WDW")
					//
					.addPattern("WWW")
					//
					.addKey('W', wire.itemTag)
					//
					.addKey('D', wire.color.dyeTag)
					//
					.complete(References.ID, "wire_" + wire.name() + "_multi", consumer);

			ElectrodynamicsShapelessCraftingRecipe.start(WIRES[wire.ordinal()], 1)
					//
					.addIngredient(wire.itemTag)
					//
					.addIngredient(wire.color.dyeTag)
					//
					.complete(References.ID, "wire_" + wire.name() + "_single", consumer);
		}

		// Highly Insulated Wires
		for (SubtypeWire wire : SubtypeWire.getWires(Conductor.values(), InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.values())) {
			if (wire.color.dyeTag == null) {
				continue;
			}
			ElectrodynamicsShapedCraftingRecipe.start(WIRES[wire.ordinal()], 8)
					//
					.addPattern("WWW")
					//
					.addPattern("WDW")
					//
					.addPattern("WWW")
					//
					.addKey('W', wire.itemTag)
					//
					.addKey('D', wire.color.dyeTag)
					//
					.complete(References.ID, "wire_" + wire.name() + "_multi", consumer);

			ElectrodynamicsShapelessCraftingRecipe.start(WIRES[wire.ordinal()], 1)
					//
					.addIngredient(wire.itemTag)
					//
					.addIngredient(wire.color.dyeTag)
					//
					.complete(References.ID, "wire_" + wire.name() + "_single", consumer);
		}

		// Ceramic Insulated Wires
		for (SubtypeWire wire : SubtypeWire.getWires(Conductor.values(), InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.values())) {
			if (wire.color.dyeTag == null) {
				continue;
			}
			ElectrodynamicsShapedCraftingRecipe.start(WIRES[wire.ordinal()], 8)
					//
					.addPattern("WWW")
					//
					.addPattern("WDW")
					//
					.addPattern("WWW")
					//
					.addKey('W', wire.itemTag)
					//
					.addKey('D', wire.color.dyeTag)
					//
					.complete(References.ID, "wire_" + wire.name() + "_multi", consumer);

			ElectrodynamicsShapelessCraftingRecipe.start(WIRES[wire.ordinal()], 1)
					//
					.addIngredient(wire.itemTag)
					//
					.addIngredient(wire.color.dyeTag)
					//
					.complete(References.ID, "wire_" + wire.name() + "_single", consumer);
		}

		// Ceramic Insulated Wires
		for (SubtypeWire wire : SubtypeWire.getWires(Conductor.values(), InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.values())) {
			if (wire.color.dyeTag == null) {
				continue;
			}
			ElectrodynamicsShapedCraftingRecipe.start(WIRES[wire.ordinal()], 8)
					//
					.addPattern("WWW")
					//
					.addPattern("WDW")
					//
					.addPattern("WWW")
					//
					.addKey('W', wire.itemTag)
					//
					.addKey('D', wire.color.dyeTag)
					//
					.complete(References.ID, "wire_" + wire.name() + "_multi", consumer);

			ElectrodynamicsShapelessCraftingRecipe.start(WIRES[wire.ordinal()], 1)
					//
					.addIngredient(wire.itemTag)
					//
					.addIngredient(wire.color.dyeTag)
					//
					.complete(References.ID, "wire_" + wire.name() + "_single", consumer);
		}

	}

}
