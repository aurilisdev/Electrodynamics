package electrodynamics.datagen.server.recipe.types.vanilla;

import java.util.function.Consumer;

import electrodynamics.Electrodynamics;
import electrodynamics.common.block.subtype.SubtypeFluidPipe;
import electrodynamics.common.block.subtype.SubtypeGlass;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.block.subtype.SubtypeRawOreBlock;
import electrodynamics.common.block.subtype.SubtypeResourceBlock;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.block.subtype.SubtypeWire.InsulationMaterial;
import electrodynamics.common.block.subtype.SubtypeWire.WireClass;
import electrodynamics.common.block.subtype.SubtypeWire.WireColor;
import electrodynamics.common.block.subtype.SubtypeWire.WireMaterial;
import electrodynamics.common.item.subtype.SubtypeCeramic;
import electrodynamics.common.item.subtype.SubtypeCircuit;
import electrodynamics.common.item.subtype.SubtypeCrystal;
import electrodynamics.common.item.subtype.SubtypeDrillHead;
import electrodynamics.common.item.subtype.SubtypeDust;
import electrodynamics.common.item.subtype.SubtypeGear;
import electrodynamics.common.item.subtype.SubtypeNugget;
import electrodynamics.common.item.subtype.SubtypeOxide;
import electrodynamics.common.item.subtype.SubtypePlate;
import electrodynamics.datagen.DataGenerators;
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
import voltaic.common.item.subtype.SubtypeItemUpgrade;
import voltaic.common.tags.VoltaicTags;
import voltaic.datagen.utils.server.recipe.AbstractRecipeGenerator;
import voltaic.datagen.utils.server.recipe.CustomShapedCraftingRecipe;
import voltaic.datagen.utils.server.recipe.CustomShapelessCraftingRecipe;
import voltaic.registers.VoltaicItems;

public class ElectrodynamicsCraftingTableRecipes extends AbstractRecipeGenerator {

	@Override
	public void addRecipes(Consumer<FinishedRecipe> consumer) {

		addMachine(consumer);
		addGear(consumer);
		addWires(consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_BATTERY.get(), 1)
				//
				.addPattern(" C ")
				//
				.addPattern("TRT")
				//
				.addPattern("TWT")
				//
				.addKey('C', ItemTags.COALS)
				//
				.addKey('T', VoltaicTags.Items.INGOT_TIN)
				//
				.addKey('R', Tags.Items.DUSTS_REDSTONE)
				//
				.addKey('W', ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.copper))
				//
				.complete(Electrodynamics.ID, "battery_basic", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_LITHIUMBATTERY.get(), 1)
				//
				.addPattern(" L ")
				//
				.addPattern("SCS")
				//
				.addPattern("SWS")
				//
				.addKey('L', VoltaicTags.Items.PLATE_LITHIUM)
				//
				.addKey('S', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('C', ElectrodynamicsItems.ITEMS_OXIDE.getValue(SubtypeOxide.thionylchloride))
				//
				.addKey('W', ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.gold))
				//
				.complete(Electrodynamics.ID, "battery_lithium", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_CARBYNEBATTERY.get(), 1)
				//
				.addPattern(" S ")
				//
				.addPattern("TNT")
				//
				.addPattern("TNT")
				//
				.addKey('S', ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.superconductive))
				//
				.addKey('T', VoltaicTags.Items.PLATE_TITANIUMCARBIDE)
				//
				.addKey('N', VoltaicTags.Items.DUST_NETHERITE)
				//
				.complete(Electrodynamics.ID, "battery_carbyne", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_CERAMIC.getValue(SubtypeCeramic.fuse), 1)
				//
				.addPattern("#P#")
				//
				.addPattern(" W ")
				//
				.addPattern("#P#")
				//
				.addKey('#', ElectrodynamicsItems.ITEMS_CERAMIC.getValue(SubtypeCeramic.cooked))
				//
				.addKey('P', ElectrodynamicsItems.ITEMS_CERAMIC.getValue(SubtypeCeramic.plate))
				//
				.addKey('W', ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.copper))
				//
				.complete(Electrodynamics.ID, "ceramic_fuse", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_CERAMIC.getValue(SubtypeCeramic.plate), 1)
				//
				.addPattern("###")
				//
				.addKey('#', ElectrodynamicsItems.ITEMS_CERAMIC.getValue(SubtypeCeramic.cooked))
				//
				.complete(Electrodynamics.ID, "ceramic_plate", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_CERAMIC.getValue(SubtypeCeramic.wet), 4)
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
				.complete(Electrodynamics.ID, "wet_ceramic", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_CIRCUIT.getValue(SubtypeCircuit.basic), 1)
				//
				.addPattern("WRW")
				//
				.addPattern("RPR")
				//
				.addPattern("WRW")
				//
				.addKey('W', VoltaicTags.Items.INSULATED_COPPER_WIRES)
				//
				.addKey('R', Tags.Items.DUSTS_REDSTONE)
				//
				.addKey('P', VoltaicTags.Items.PLATE_STEEL)
				//
				.complete(Electrodynamics.ID, "circuit_basic", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_CIRCUIT.getValue(SubtypeCircuit.advanced), 1)
				//
				.addPattern("QQQ")
				//
				.addPattern("CDC")
				//
				.addPattern("QQQ")
				//
				.addKey('Q', Tags.Items.GEMS_QUARTZ)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_BASIC)
				//
				.addKey('D', Tags.Items.GEMS_DIAMOND)
				//
				.complete(Electrodynamics.ID, "circuit_advanced", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_CIRCUIT.getValue(SubtypeCircuit.elite), 1)
				//
				.addPattern("GGG")
				//
				.addPattern("CBC")
				//
				.addPattern("GGG")
				//
				.addKey('G', Tags.Items.INGOTS_GOLD)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ADVANCED)
				//
				.addKey('B', Tags.Items.STORAGE_BLOCKS_LAPIS)
				//
				.complete(Electrodynamics.ID, "circuit_elite", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_CIRCUIT.getValue(SubtypeCircuit.ultimate), 1)
				//
				.addPattern("OPO")
				//
				.addPattern("COC")
				//
				.addPattern("OPO")
				//
				.addKey('O', VoltaicTags.Items.DUST_OBSIDIAN)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ELITE)
				//
				.addKey('P', VoltaicTags.Items.PLATE_STEEL)
				//
				.complete(Electrodynamics.ID, "circuit_ultimate", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_COIL.get(), 1)
				//
				.addPattern(" W ")
				//
				.addPattern("WIW")
				//
				.addPattern(" W ")
				//
				.addKey('W', ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.copper))
				//
				.addKey('I', Tags.Items.INGOTS_IRON)
				//
				.complete(Electrodynamics.ID, "copper_coil", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_LAMINATEDCOIL.get(), 1)
				//
				.addPattern(" P ")
				//
				.addPattern("PCP")
				//
				.addPattern(" P ")
				//
				.addKey('P', VoltaicTags.Items.PLASTIC)
				//
				.addKey('C', ElectrodynamicsItems.ITEM_COIL.get())
				//
				.complete(Electrodynamics.ID, "copper_coil_laminated", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_RAWCOMPOSITEPLATING.get(), 6)
				//
				.addPattern("TTT")
				//
				.addPattern("CCC")
				//
				.addPattern("PPP")
				//
				.addKey('T', VoltaicTags.Items.PLATE_TITANIUM)
				//
				.addKey('C', ElectrodynamicsItems.ITEMS_CERAMIC.getValue(SubtypeCeramic.plate))
				//
				.addKey('P', VoltaicTags.Items.PLASTIC)
				//
				.complete(Electrodynamics.ID, "raw_composite_plating", consumer);

		for (SubtypeNugget nugget : SubtypeNugget.values()) {
			CustomShapedCraftingRecipe.start(nugget.productIngot.get(), 1)
					//
					.addPattern("NNN")
					//
					.addPattern("NNN")
					//
					.addPattern("NNN")
					//
					.addKey('N', nugget.tag)
					//
					.complete(Electrodynamics.ID, nugget.name() + "_nuggets_to_" + nugget.name() + "_ingot", consumer);
		}

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_DRILLHEAD.getValue(SubtypeDrillHead.hslasteel), 1)
				//
				.addPattern(" I ")
				//
				.addPattern("IPI")
				//
				.addKey('I', VoltaicTags.Items.INGOT_HSLASTEEL)
				//
				.addKey('P', VoltaicTags.Items.PLATE_HSLASTEEL)
				//
				.complete(Electrodynamics.ID, "drill_head_hslasteel", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_DRILLHEAD.getValue(SubtypeDrillHead.stainlesssteel), 1)
				//
				.addPattern(" I ")
				//
				.addPattern("IPI")
				//
				.addKey('I', VoltaicTags.Items.INGOT_STAINLESSSTEEL)
				//
				.addKey('P', VoltaicTags.Items.PLATE_STAINLESSSTEEL)
				//
				.complete(Electrodynamics.ID, "drill_head_stainlesssteel", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_DRILLHEAD.getValue(SubtypeDrillHead.steel), 1)
				//
				.addPattern(" I ")
				//
				.addPattern("IPI")
				//
				.addKey('I', VoltaicTags.Items.INGOT_STEEL)
				//
				.addKey('P', VoltaicTags.Items.PLATE_STEEL)
				//
				.complete(Electrodynamics.ID, "drill_head_steel", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_DRILLHEAD.getValue(SubtypeDrillHead.titanium), 1)
				//
				.addPattern(" I ")
				//
				.addPattern("IPI")
				//
				.addKey('I', VoltaicTags.Items.INGOT_TITANIUM)
				//
				.addKey('P', VoltaicTags.Items.PLATE_TITANIUM)
				//
				.complete(Electrodynamics.ID, "drill_head_titanium", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_DRILLHEAD.getValue(SubtypeDrillHead.titaniumcarbide), 1)
				//
				.addPattern(" I ")
				//
				.addPattern("IPI")
				//
				.addKey('I', VoltaicTags.Items.INGOT_TITANIUMCARBIDE)
				//
				.addKey('P', VoltaicTags.Items.PLATE_TITANIUMCARBIDE)
				//
				.complete(Electrodynamics.ID, "drill_head_titaniumcarbide", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_DUST.getValue(SubtypeDust.superconductive), 9)
				//
				.addPattern("#S#")
				//
				.addPattern("G#G")
				//
				.addPattern("#S#")
				//
				.addKey('#', VoltaicTags.Items.DUST_ENDEREYE)
				//
				.addKey('S', VoltaicTags.Items.DUST_SILVER)
				//
				.addKey('G', VoltaicTags.Items.DUST_GOLD)
				//
				.complete(Electrodynamics.ID, "dust_superconductive", consumer);

		for (SubtypeResourceBlock block : SubtypeResourceBlock.values()) {
			CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_RESOURCEBLOCK.getValue(block), 1)
					//
					.addPattern("III")
					//
					.addPattern("III")
					//
					.addPattern("III")
					//
					.addKey('I', block.sourceIngot)
					//
					.complete(Electrodynamics.ID, "resource_block_" + block.name(), consumer);
		}

		for (SubtypeRawOreBlock block : SubtypeRawOreBlock.values()) {
			CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_RAWOREBLOCK.getValue(block), 1)
					//
					.addPattern("RRR")
					//
					.addPattern("RRR")
					//
					.addPattern("RRR")
					//
					.addKey('R', block.sourceRawOre)
					//
					.complete(Electrodynamics.ID, "raw_ore_block_" + block.name(), consumer);
		}

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_STEELSCAFFOLD.get(), 32)
				//
				.addPattern("SSS")
				//
				.addPattern("S S")
				//
				.addPattern("SSS")
				//
				.addKey('S', VoltaicTags.Items.ROD_STEEL)
				//
				.complete(Electrodynamics.ID, "steel_scaffold", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.copper), 1)
				//
				.addPattern("C")
				//
				.addPattern("C")
				//
				.addKey('C', Tags.Items.INGOTS_COPPER)
				//
				.complete(Electrodynamics.ID, "wire_copper", consumer);

		for (SubtypeGear gear : SubtypeGear.values()) {
			CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_GEAR.getValue(gear), 1)
					//
					.addPattern(" I ")
					//
					.addPattern("I I")
					//
					.addPattern(" I ")
					//
					.addKey('I', gear.sourceIngot)
					//
					.complete(Electrodynamics.ID, "gear_" + gear.name(), consumer);
		}

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_MOTOR.get(), 1)
				//
				.addPattern(" S ")
				//
				.addPattern("WCW")
				//
				.addPattern(" S ")
				//
				.addKey('S', VoltaicTags.Items.INGOT_STEEL)
				//
				.addKey('W', VoltaicTags.Items.INSULATED_COPPER_WIRES)
				//
				.addKey('C', ElectrodynamicsItems.ITEM_COIL.get())
				//
				.complete(Electrodynamics.ID, "motor_steel", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_MOTOR.get(), 4)
				//
				.addPattern(" S ")
				//
				.addPattern("WCW")
				//
				.addPattern(" S ")
				//
				.addKey('S', VoltaicTags.Items.INGOT_STAINLESSSTEEL)
				//
				.addKey('W', VoltaicTags.Items.INSULATED_COPPER_WIRES)
				//
				.addKey('C', ElectrodynamicsItems.ITEM_COIL.get())
				//
				.complete(Electrodynamics.ID, "motor_stainlesssteel", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_PIPE.getValue(SubtypeFluidPipe.copper), 10)
				//
				.addPattern("III")
				//
				.addPattern("   ")
				//
				.addPattern("III")
				//
				.addKey('I', Tags.Items.INGOTS_COPPER)
				//
				.complete(Electrodynamics.ID, "fluidpipe_copper_horizontal", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_PIPE.getValue(SubtypeFluidPipe.copper), 10)
				//
				.addPattern("I I")
				//
				.addPattern("I I")
				//
				.addPattern("I I")
				//
				.addKey('I', Tags.Items.INGOTS_COPPER)
				//
				.complete(Electrodynamics.ID, "fluidpipe_copper_vertical", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_PIPE.getValue(SubtypeFluidPipe.steel), 4)
				//
				.addPattern("III")
				//
				.addPattern("   ")
				//
				.addPattern("III")
				//
				.addKey('I', VoltaicTags.Items.INGOT_STEEL)
				//
				.complete(Electrodynamics.ID, "fluidpipe_steel_horizontal", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_PIPE.getValue(SubtypeFluidPipe.steel), 4)
				//
				.addPattern("I I")
				//
				.addPattern("I I")
				//
				.addPattern("I I")
				//
				.addKey('I', VoltaicTags.Items.INGOT_STEEL)
				//
				.complete(Electrodynamics.ID, "fluidpipe_steel_vertical", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_PLATE.getValue(SubtypePlate.bronze), 1)
				//
				.addPattern("II")
				//
				.addPattern("II")
				//
				.addKey('I', VoltaicTags.Items.INGOT_BRONZE)
				//
				.complete(Electrodynamics.ID, "plate_bronze", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_PLATE.getValue(SubtypePlate.copper), 1)
				//
				.addPattern("II")
				//
				.addPattern("II")
				//
				.addKey('I', Tags.Items.INGOTS_COPPER)
				//
				.complete(Electrodynamics.ID, "plate_copper", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_PLATE.getValue(SubtypePlate.iron), 1)
				//
				.addPattern("II")
				//
				.addPattern("II")
				//
				.addKey('I', Tags.Items.INGOTS_IRON)
				//
				.complete(Electrodynamics.ID, "plate_iron", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_PLATE.getValue(SubtypePlate.lead), 1)
				//
				.addPattern("II")
				//
				.addPattern("II")
				//
				.addKey('I', VoltaicTags.Items.INGOT_LEAD)
				//
				.complete(Electrodynamics.ID, "plate_lead", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_PLATE.getValue(SubtypePlate.steel), 1)
				//
				.addPattern("II")
				//
				.addPattern("II")
				//
				.addKey('I', VoltaicTags.Items.INGOT_STEEL)
				//
				.complete(Electrodynamics.ID, "plate_steel", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_SEISMICMARKER.get(), 1)
				//
				.addPattern(" P ")
				//
				.addPattern("PAP")
				//
				.addPattern(" P ")
				//
				.addKey('P', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('A', Tags.Items.GEMS_AMETHYST)
				//
				.complete(Electrodynamics.ID, "seismic_marker", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_SOLARPANELPLATE.get(), 1)
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
				.addKey('W', ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.copper))
				//
				.addKey('I', Tags.Items.INGOTS_IRON)
				//
				.addKey('C', Tags.Items.INGOTS_COPPER)
				//
				.complete(Electrodynamics.ID, "solar_panel_plate", consumer);

		CustomShapedCraftingRecipe.start(VoltaicItems.ITEMS_UPGRADE.getValue(SubtypeItemUpgrade.advancedcapacity), 1)
				//
				.addPattern("PBP")
				//
				.addPattern("BWB")
				//
				.addPattern("CBC")
				//
				.addKey('P', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('B', VoltaicItems.ITEMS_UPGRADE.getValue(SubtypeItemUpgrade.basiccapacity))
				//
				.addKey('W', VoltaicTags.Items.INSULATED_COPPER_WIRES)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ADVANCED)
				//
				.complete(Electrodynamics.ID, "upgrade_advanced_capacity_electro", consumer);

		CustomShapedCraftingRecipe.start(VoltaicItems.ITEMS_UPGRADE.getValue(SubtypeItemUpgrade.advancedspeed), 1)
				//
				.addPattern("PGP")
				//
				.addPattern("BWB")
				//
				.addPattern("CGC")
				//
				.addKey('P', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('G', VoltaicTags.Items.GEAR_BRONZE)
				//
				.addKey('B', VoltaicItems.ITEMS_UPGRADE.getValue(SubtypeItemUpgrade.basicspeed))
				//
				.addKey('W', VoltaicTags.Items.INSULATED_COPPER_WIRES)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ADVANCED)
				//
				.complete(Electrodynamics.ID, "upgrade_advanced_speed_electro", consumer);

		CustomShapedCraftingRecipe.start(VoltaicItems.ITEMS_UPGRADE.getValue(SubtypeItemUpgrade.basiccapacity), 1)
				//
				.addPattern("PBP")
				//
				.addPattern("BWB")
				//
				.addPattern("CBC")
				//
				.addKey('P', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('B', ElectrodynamicsItems.ITEM_BATTERY.get())
				//
				.addKey('W', VoltaicTags.Items.INSULATED_COPPER_WIRES)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_BASIC)
				//
				.complete(Electrodynamics.ID, "upgrade_basic_capacity_electro", consumer);

		CustomShapedCraftingRecipe.start(VoltaicItems.ITEMS_UPGRADE.getValue(SubtypeItemUpgrade.basicspeed), 1)
				//
				.addPattern("PGP")
				//
				.addPattern("WWW")
				//
				.addPattern("CGC")
				//
				.addKey('P', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('G', VoltaicTags.Items.GEAR_BRONZE)
				//
				.addKey('W', VoltaicTags.Items.INSULATED_GOLD_WIRES)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_BASIC)
				//
				.complete(Electrodynamics.ID, "upgrade_basic_speed_electro", consumer);

		CustomShapedCraftingRecipe.start(VoltaicItems.ITEMS_UPGRADE.getValue(SubtypeItemUpgrade.experience), 1)
				//
				.addPattern("PBP")
				//
				.addPattern("BWB")
				//
				.addPattern("PBP")
				//
				.addKey('P', VoltaicTags.Items.PLATE_BRONZE)
				//
				.addKey('B', Items.GLASS_BOTTLE)
				//
				.addKey('W', VoltaicTags.Items.INSULATED_COPPER_WIRES)
				//
				.complete(Electrodynamics.ID, "upgrade_experience_electro", consumer);

		ItemStack fortuneBook = new ItemStack(Items.ENCHANTED_BOOK);
		EnchantedBookItem.addEnchantment(fortuneBook, new EnchantmentInstance(Enchantments.BLOCK_FORTUNE, 1));
		CustomShapedCraftingRecipe.start(VoltaicItems.ITEMS_UPGRADE.getValue(SubtypeItemUpgrade.fortune), 1)
				//
				.addPattern("PCP")
				//
				.addPattern("CBC")
				//
				.addPattern("PCP")
				//
				.addKey('P', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ELITE)
				//
				.addKey('B', PartialNBTIngredient.of(fortuneBook.getItem(), fortuneBook.getTag()))
				//
				.complete(Electrodynamics.ID, "upgrade_fortune_electro", consumer);

		CustomShapedCraftingRecipe.start(VoltaicItems.ITEMS_UPGRADE.getValue(SubtypeItemUpgrade.improvedsolarcell), 1)
				//
				.addPattern("PPP")
				//
				.addPattern("BCB")
				//
				.addPattern("BSB")
				//
				.addKey('P', ElectrodynamicsItems.ITEM_SOLARPANELPLATE.get())
				//
				.addKey('B', VoltaicTags.Items.PLATE_BRONZE)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_BASIC)
				//
				.addKey('S', VoltaicTags.Items.PLATE_STEEL)
				//
				.complete(Electrodynamics.ID, "upgrade_improved_solar_cell_electro", consumer);

		CustomShapedCraftingRecipe.start(VoltaicItems.ITEMS_UPGRADE.getValue(SubtypeItemUpgrade.iteminput), 1)
				//
				.addPattern("C")
				//
				.addPattern("P")
				//
				.addPattern("A")
				//
				.addKey('A', VoltaicTags.Items.PLATE_ALUMINUM)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_BASIC)
				//
				.addKey('P', Items.STICKY_PISTON)
				//
				.complete(Electrodynamics.ID, "upgrade_item_input_electro", consumer);

		CustomShapedCraftingRecipe.start(VoltaicItems.ITEMS_UPGRADE.getValue(SubtypeItemUpgrade.itemoutput), 1)
				//
				.addPattern("C")
				//
				.addPattern("P")
				//
				.addPattern("A")
				//
				.addKey('A', VoltaicTags.Items.PLATE_ALUMINUM)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_BASIC)
				//
				.addKey('P', Items.PISTON)
				//
				.complete(Electrodynamics.ID, "upgrade_item_output_electro", consumer);

		CustomShapedCraftingRecipe.start(VoltaicItems.ITEMS_UPGRADE.getValue(SubtypeItemUpgrade.itemvoid), 1)
				//
				.addPattern("C")
				//
				.addPattern("B")
				//
				.addPattern("P")
				//
				.addKey('P', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('C', Items.CACTUS)
				//
				.addKey('B', VoltaicTags.Items.CIRCUITS_BASIC)
				//
				.complete(Electrodynamics.ID, "upgrade_item_void_electro", consumer);

		CustomShapedCraftingRecipe.start(VoltaicItems.ITEMS_UPGRADE.getValue(SubtypeItemUpgrade.range), 1)
				//
				.addPattern("PWP")
				//
				.addPattern("WBW")
				//
				.addPattern("PWP")
				//
				.addKey('P', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('W', VoltaicTags.Items.INSULATED_COPPER_WIRES)
				//
				.addKey('B', VoltaicTags.Items.CIRCUITS_BASIC)
				//
				.complete(Electrodynamics.ID, "upgrade_range_electro", consumer);

		ItemStack silkTouchBook = new ItemStack(Items.ENCHANTED_BOOK);
		EnchantedBookItem.addEnchantment(silkTouchBook, new EnchantmentInstance(Enchantments.SILK_TOUCH, 1));
		CustomShapedCraftingRecipe.start(VoltaicItems.ITEMS_UPGRADE.getValue(SubtypeItemUpgrade.silktouch), 1)
				//
				.addPattern("PCP")
				//
				.addPattern("CBC")
				//
				.addPattern("PCP")
				//
				.addKey('P', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ULTIMATE)
				//
				.addKey('B', PartialNBTIngredient.of(silkTouchBook.getItem(), silkTouchBook.getTag()))
				//
				.complete(Electrodynamics.ID, "upgrade_silk_touch_electro", consumer);

		CustomShapedCraftingRecipe.start(VoltaicItems.ITEMS_UPGRADE.getValue(SubtypeItemUpgrade.stator), 1)
				//
				.addPattern("PCP")
				//
				.addPattern("CRC")
				//
				.addPattern("PCP")
				//
				.addKey('P', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('C', ElectrodynamicsItems.ITEM_COIL.get())
				//
				.addKey('R', Tags.Items.DUSTS_REDSTONE)
				//
				.complete(Electrodynamics.ID, "upgrade_stator_electro", consumer);

		ItemStack unbreakingBook = new ItemStack(Items.ENCHANTED_BOOK);
		EnchantedBookItem.addEnchantment(unbreakingBook, new EnchantmentInstance(Enchantments.UNBREAKING, 1));
		CustomShapedCraftingRecipe.start(VoltaicItems.ITEMS_UPGRADE.getValue(SubtypeItemUpgrade.unbreaking), 1)
				//
				.addPattern("PCP")
				//
				.addPattern("CBC")
				//
				.addPattern("PCP")
				//
				.addKey('P', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ADVANCED)
				//
				.addKey('B', PartialNBTIngredient.of(unbreakingBook.getItem(), unbreakingBook.getTag()))
				//
				.complete(Electrodynamics.ID, "upgrade_unbreaking_electro", consumer);

		for (SubtypeNugget nugget : SubtypeNugget.values()) {
			CustomShapelessCraftingRecipe.start(ElectrodynamicsItems.ITEMS_NUGGET.getValue(nugget), 9)
					//
					.addIngredient(nugget.sourceIngot)
					//
					.complete(Electrodynamics.ID, nugget.name() + "_ingot_to_" + nugget.name() + "_nuggets", consumer);

		}

		CustomShapelessCraftingRecipe.start(ElectrodynamicsItems.ITEMS_DUST.getValue(SubtypeDust.bronze), 3)
				//
				.addIngredient(VoltaicTags.Items.DUST_COPPER)
				//
				.addIngredient(VoltaicTags.Items.DUST_COPPER)
				//
				.addIngredient(VoltaicTags.Items.DUST_TIN)
				//
				.complete(Electrodynamics.ID, "dust_bronze", consumer);

		for (SubtypeResourceBlock block : SubtypeResourceBlock.values()) {
			CustomShapelessCraftingRecipe.start(block.productIngot.get(), 9)
					//
					.addIngredient(ElectrodynamicsItems.ITEMS_RESOURCEBLOCK.getValue(block))
					//
					.complete(Electrodynamics.ID, block.name() + "_ingot_from_storage_block", consumer);
		}

		for (SubtypeRawOreBlock block : SubtypeRawOreBlock.values()) {
			CustomShapelessCraftingRecipe.start(block.productRawOre.get(), 9)
					//
					.addIngredient(ElectrodynamicsItems.ITEMS_RAWOREBLOCK.getValue(block))
					//
					.complete(Electrodynamics.ID, "raw_ore_" + block.name() + "_from_storage_block", consumer);
		}

		CustomShapelessCraftingRecipe.start(Items.GUNPOWDER, 6)
				//
				.addIngredient(VoltaicTags.Items.DUST_SULFUR)
				//
				.addIngredient(VoltaicTags.Items.DUST_SALTPETER)
				//
				.addIngredient(ItemTags.COALS)
				//
				.complete(Electrodynamics.ID, "gunpowder", consumer);

		CustomShapelessCraftingRecipe.start(ElectrodynamicsItems.ITEM_INSULATION.get(), 20)
				//
				.addIngredient(VoltaicTags.Items.PLASTIC)
				//
				.complete(Electrodynamics.ID, "insulation_from_plastic", consumer);

		CustomShapelessCraftingRecipe.start(ElectrodynamicsItems.ITEM_INSULATION.get(), 1)
				//
				.addIngredient(ItemTags.WOOL)
				//
				.complete(Electrodynamics.ID, "insulation_from_wool", consumer);

		CustomShapelessCraftingRecipe.start(ElectrodynamicsItems.ITEM_INSULATION.get(), 6)
				//
				.addIngredient(Tags.Items.LEATHER)
				//
				.complete(Electrodynamics.ID, "insulation_from_leather", consumer);

		CustomShapelessCraftingRecipe.start(ElectrodynamicsItems.ITEM_INSULATION.get(), 3)
				//
				.addIngredient(Items.RABBIT_HIDE)
				//
				.complete(Electrodynamics.ID, "insulation_from_rabbit_hide", consumer);

		CustomShapelessCraftingRecipe.start(ElectrodynamicsItems.ITEM_MOLYBDENUMFERTILIZER.get(), 8)
				//
				.addIngredient(Items.BONE_MEAL)
				//
				.addIngredient(VoltaicTags.Items.DUST_MOLYBDENUM)
				//
				.addIngredient(VoltaicTags.Items.DUST_MOLYBDENUM)
				//
				.complete(Electrodynamics.ID, "molybdenum_fertilizer", consumer);

		CustomShapelessCraftingRecipe.start(Items.OBSIDIAN, 2)
				//
				.addIngredient(Items.WATER_BUCKET)
				//
				.addIngredient(Items.LAVA_BUCKET)
				//
				.complete(Electrodynamics.ID, "obsidian", consumer);
	}

	private void addMachine(Consumer<FinishedRecipe> consumer) {

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.advanceddowngradetransformer), 1)
				//
				.addPattern("PPP")
				//
				.addPattern("OTO")
				//
				.addPattern("PCP")
				//
				.addKey('P', VoltaicTags.Items.PLATE_STAINLESSSTEEL)
				//
				.addKey('O', ElectrodynamicsItems.ITEM_LAMINATEDCOIL.get())
				//
				.addKey('T', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.downgradetransformer))
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_BASIC)
				//
				.complete(Electrodynamics.ID, "machine_advanced_downgrade_transformer", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.advancedupgradetransformer), 1)
				//
				.addPattern("PPP")
				//
				.addPattern("OTO")
				//
				.addPattern("CPW")
				//
				.addKey('P', VoltaicTags.Items.PLATE_STAINLESSSTEEL)
				//
				.addKey('O', ElectrodynamicsItems.ITEM_LAMINATEDCOIL.get())
				//
				.addKey('T', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.upgradetransformer))
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_BASIC)
				//
				.addKey('W', ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.copper))
				//
				.complete(Electrodynamics.ID, "machine_advanced_upgrade_transformer", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.advancedsolarpanel), 1)
				//
				.addPattern(" S ")
				//
				.addPattern("SPS")
				//
				.addPattern("III")
				//
				.addKey('S', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.solarpanel))
				//
				.addKey('P', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('I', Tags.Items.INGOTS_IRON)
				//
				.complete(Electrodynamics.ID, "machine_advanced_solar_panel", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.batterybox), 1)
				//
				.addPattern("BBB")
				//
				.addPattern("SWS")
				//
				.addPattern("BBB")
				//
				.addKey('B', ElectrodynamicsItems.ITEM_BATTERY.get())
				//
				.addKey('S', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('W', VoltaicTags.Items.INSULATED_COPPER_WIRES)
				//
				.complete(Electrodynamics.ID, "machine_battery_box_basic", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.carbynebatterybox), 1)
				//
				.addPattern("BBB")
				//
				.addPattern("PWP")
				//
				.addPattern("BBB")
				//
				.addKey('B', ElectrodynamicsItems.ITEM_CARBYNEBATTERY.get())
				//
				.addKey('P', VoltaicTags.Items.PLATE_HSLASTEEL)
				//
				.addKey('W', VoltaicTags.Items.THICK_SUPERCONDUCTIVE_WIRES)
				//
				.complete(Electrodynamics.ID, "machine_battery_box_carbyne", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.chargerhv), 1)
				//
				.addPattern("W W")
				//
				.addPattern("NMN")
				//
				.addPattern("PCP")
				//
				.addKey('W', VoltaicTags.Items.THICK_SUPERCONDUCTIVE_WIRES)
				//
				.addKey('N', VoltaicTags.Items.DUST_NETHERITE_SCRAP)
				//
				.addKey('M', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.chargermv))
				//
				.addKey('P', VoltaicTags.Items.PLATE_HSLASTEEL)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ULTIMATE)
				//
				.complete(Electrodynamics.ID, "machine_charger_hv", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.chargerlv), 1)
				//
				.addPattern("W W")
				//
				.addPattern("PBP")
				//
				.addPattern("PCP")
				//
				.addKey('W', VoltaicTags.Items.INSULATED_COPPER_WIRES)
				//
				.addKey('P', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('B', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.batterybox))
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ADVANCED)
				//
				.complete(Electrodynamics.ID, "machine_charger_lv", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.chargermv), 1)
				//
				.addPattern("W W")
				//
				.addPattern("PLP")
				//
				.addPattern("PCP")
				//
				.addKey('W', VoltaicTags.Items.THICK_GOLD_WIRES)
				//
				.addKey('P', VoltaicTags.Items.PLATE_STAINLESSSTEEL)
				//
				.addKey('L', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.chargerlv))
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ELITE)
				//
				.complete(Electrodynamics.ID, "machine_charger_mv", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.chemicalcrystallizer), 1)
				//
				.addPattern("SCS")
				//
				.addPattern("GMG")
				//
				.addPattern("SCS")
				//
				.addKey('S', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('G', VoltaicTags.Items.GEAR_BRONZE)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ELITE)
				//
				.complete(Electrodynamics.ID, "machine_chemical_crystallizer", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.chemicalmixer), 1)
				//
				.addPattern("SCS")
				//
				.addPattern("MGM")
				//
				.addPattern("SCS")
				//
				.addKey('S', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ADVANCED)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.addKey('G', VoltaicTags.Items.GEAR_BRONZE)
				//
				.complete(Electrodynamics.ID, "machine_chemical_mixer", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.circuitbreaker), 1)
				//
				.addPattern("SCS")
				//
				.addPattern("FBF")
				//
				.addPattern("SCS")
				//
				.addKey('S', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_BASIC)
				//
				.addKey('B', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.relay))
				//
				.addKey('F', ElectrodynamicsItems.ITEMS_CERAMIC.getValue(SubtypeCeramic.fuse))
				//
				.complete(Electrodynamics.ID, "machine_circuit_breaker", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.circuitmonitor), 1)
				//
				.addPattern("DRD")
				//
				.addPattern("PMP")
				//
				.addPattern("DCD")
				//
				.addKey('D', Tags.Items.DUSTS_REDSTONE)
				//
				.addKey('R', Items.COMPARATOR)
				//
				.addKey('P', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('M', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.multimeterblock))
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_BASIC)
				//
				.complete(Electrodynamics.ID, "machine_circuit_monitor", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.relay), 1)
				//
				.addPattern("SLS")
				//
				.addPattern("WIW")
				//
				.addPattern("SCS")
				//
				.addKey('S', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('W', ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.copper))
				//
				.addKey('I', Tags.Items.INGOTS_COPPER)
				//
				.addKey('L', Items.LEVER)
				//
				.addKey('C', ElectrodynamicsItems.ITEMS_CERAMIC.getValue(SubtypeCeramic.plate))
				//
				.complete(Electrodynamics.ID, "machine_relay", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.potentiometer), 1)
				//
				.addPattern("ACA")
				//
				.addPattern("CGC")
				//
				.addPattern("ACA")
				//
				.addKey('A', VoltaicTags.Items.PLATE_ALUMINUM)
				//
				.addKey('C', ElectrodynamicsItems.ITEMS_CERAMIC.getValue(SubtypeCeramic.plate))
				//
				.addKey('G', ItemTags.COALS)
				//
				.complete(Electrodynamics.ID, "machine_potentiometer", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.coalgenerator), 1)
				//
				.addPattern("SSS")
				//
				.addPattern("SFS")
				//
				.addPattern("SMS")
				//
				.addKey('S', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('F', Items.FURNACE)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.complete(Electrodynamics.ID, "machine_coal_generator_steel", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.coalgenerator), 1)
				//
				.addPattern("BBB")
				//
				.addPattern("BFB")
				//
				.addPattern("BMB")
				//
				.addKey('B', VoltaicTags.Items.PLATE_BRONZE)
				//
				.addKey('F', Items.FURNACE)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.complete(Electrodynamics.ID, "machine_coal_generator_bronze", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.combustionchamber), 1)
				//
				.addPattern("PMP")
				//
				.addPattern("GWG")
				//
				.addPattern("PCP")
				//
				.addKey('P', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.addKey('G', VoltaicTags.Items.GEAR_IRON)
				//
				.addKey('W', ElectrodynamicsItems.ITEM_COIL.get())
				//
				.addKey('C', Items.CAULDRON)
				//
				.complete(Electrodynamics.ID, "machine_combustion_chamber", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.coolantresavoir), 1)
				//
				.addPattern("SSS")
				//
				.addPattern("STS")
				//
				.addPattern("SCS")
				//
				.addKey('S', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('T', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.tanksteel))
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_BASIC)
				//
				.complete(Electrodynamics.ID, "machine_coolant_resavoir", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.creativefluidsource), 1)
				//
				.addPattern("CCC")
				//
				.addPattern("CBC")
				//
				.addPattern("CCC")
				//
				.addKey('C', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.creativepowersource))
				//
				.addKey('B', Items.BEDROCK)
				//
				.complete(Electrodynamics.ID, "machine_creative_fluid_source", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.creativepowersource), 1)
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
				.complete(Electrodynamics.ID, "machine_creative_power_source", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.currentregulator), 1)
				//
				.addPattern("SAS")
				//
				.addPattern("DCU")
				//
				.addPattern("SAS")
				//
				.addKey('S', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('A', VoltaicTags.Items.PLATE_ALUMINUM)
				//
				.addKey('D', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.downgradetransformer))
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_BASIC)
				//
				.addKey('U', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.upgradetransformer))
				//
				.complete(Electrodynamics.ID, "machine_current_regulator", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.downgradetransformer), 1)
				//
				.addPattern("ISI")
				//
				.addPattern("W C")
				//
				.addPattern("SIS")
				//
				.addKey('I', Tags.Items.INGOTS_IRON)
				//
				.addKey('S', VoltaicTags.Items.INGOT_STEEL)
				//
				.addKey('W', ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.copper))
				//
				.addKey('C', ElectrodynamicsItems.ITEM_COIL.get())
				//
				.complete(Electrodynamics.ID, "machine_downgrade_transformer", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricfurnace), 1)
				//
				.addPattern("SSS")
				//
				.addPattern("SCS")
				//
				.addPattern("SMS")
				//
				.addKey('S', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_BASIC)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.complete(Electrodynamics.ID, "machine_electric_furnace", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricfurnacedouble), 1)
				//
				.addPattern("CSC")
				//
				.addPattern("GFG")
				//
				.addPattern("CSC")
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ADVANCED)
				//
				.addKey('S', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('G', Tags.Items.INGOTS_GOLD)
				//
				.addKey('F', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricfurnace))
				//
				.complete(Electrodynamics.ID, "machine_electric_furnace_double", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricfurnacetriple), 1)
				//
				.addPattern("COC")
				//
				.addPattern("DFD")
				//
				.addPattern("COC")
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ELITE)
				//
				.addKey('O', VoltaicTags.Items.DUST_OBSIDIAN)
				//
				.addKey('D', Tags.Items.GEMS_DIAMOND)
				//
				.addKey('F', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricfurnacedouble))
				//
				.complete(Electrodynamics.ID, "machine_electric_furnace_triple", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricpump), 1)
				//
				.addPattern("SSS")
				//
				.addPattern("SMS")
				//
				.addPattern("SPS")
				//
				.addKey('S', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.addKey('P', Items.PISTON)
				//
				.complete(Electrodynamics.ID, "machine_electric_pump", consumer);

		CustomShapelessCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricarcfurnace), 1)
				//
				.addIngredient(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricfurnace))
				//
				.addIngredient(Items.BLAST_FURNACE)
				//
				.complete(Electrodynamics.ID, "machine_electric_arc_furnace", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricarcfurnacedouble), 1)
				//
				.addPattern("CSC")
				//
				.addPattern("GEG")
				//
				.addPattern("CSC")
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ADVANCED)
				//
				.addKey('S', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('G', Tags.Items.INGOTS_GOLD)
				//
				.addKey('E', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricarcfurnace))
				//
				.complete(Electrodynamics.ID, "machine_electric_arc_furnace_double", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricarcfurnacetriple), 1)
				//
				.addPattern("COC")
				//
				.addPattern("DED")
				//
				.addPattern("COC")
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ELITE)
				//
				.addKey('O', VoltaicTags.Items.DUST_OBSIDIAN)
				//
				.addKey('D', Tags.Items.GEMS_DIAMOND)
				//
				.addKey('E', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricarcfurnacedouble))
				//
				.complete(Electrodynamics.ID, "machine_electric_arc_furnace_triple", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electrolyticseparator), 1)
				//
				.addPattern("PTP")
				//
				.addPattern("TXT")
				//
				.addPattern("PCP")
				//
				.addKey('P', VoltaicTags.Items.PLATE_STAINLESSSTEEL)
				//
				.addKey('T', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.tanksteel))
				//
				.addKey('X', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.upgradetransformer))
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ADVANCED)
				//
				.complete(Electrodynamics.ID, "machine_electrolytic_separator", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.energizedalloyer), 1)
				//
				.addPattern("ACA")
				//
				.addPattern("CTC")
				//
				.addPattern("AEA")
				//
				.addKey('A', VoltaicTags.Items.PLATE_ALUMINUM)
				//
				.addKey('C', ElectrodynamicsItems.ITEMS_CERAMIC.getValue(SubtypeCeramic.plate))
				//
				.addKey('T', ElectrodynamicsItems.ITEM_TITANIUM_COIL.get())
				//
				.addKey('E', VoltaicTags.Items.CIRCUITS_ELITE)
				//
				.complete(Electrodynamics.ID, "machine_energized_alloyer", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.fermentationplant), 1)
				//
				.addPattern("IMI")
				//
				.addPattern("BCB")
				//
				.addPattern("IFI")
				//
				.addKey('I', VoltaicTags.Items.PLATE_IRON)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.addKey('B', VoltaicTags.Items.CIRCUITS_BASIC)
				//
				.addKey('C', Items.CAULDRON)
				//
				.addKey('F', Items.FURNACE)
				//
				.complete(Electrodynamics.ID, "machine_fermentation_plant", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.fluidvoid), 1)
				//
				.addPattern("SBS")
				//
				.addPattern("BCB")
				//
				.addPattern("SBS")
				//
				.addKey('S', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('B', Items.BUCKET)
				//
				.addKey('C', Items.CACTUS)
				//
				.complete(Electrodynamics.ID, "machine_fluid_void", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.lithiumbatterybox), 1)
				//
				.addPattern("BBB")
				//
				.addPattern("SWS")
				//
				.addPattern("BBB")
				//
				.addKey('B', ElectrodynamicsItems.ITEM_LITHIUMBATTERY.get())
				//
				.addKey('S', VoltaicTags.Items.PLATE_STAINLESSSTEEL)
				//
				.addKey('W', VoltaicTags.Items.INSULATED_GOLD_WIRES)
				//
				.complete(Electrodynamics.ID, "battery_box_lithium", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.lathe), 1)
				//
				.addPattern("AMA")
				//
				.addPattern("MCM")
				//
				.addPattern("AWA")
				//
				.addKey('A', VoltaicTags.Items.PLATE_ALUMINUM)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ELITE)
				//
				.addKey('W', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.wiremill))
				//
				.complete(Electrodynamics.ID, "lathe", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_LOGISTICALMANAGER.get(), 1)
				//
				.addPattern("SCS")
				//
				.addPattern("CPC")
				//
				.addPattern("SCS")
				//
				.addKey('S', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_BASIC)
				//
				.addKey('P', Items.PISTON)
				//
				.complete(Electrodynamics.ID, "machine_logistical_manager", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.hydroelectricgenerator), 1)
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
				.addKey('S', VoltaicTags.Items.PLATE_STEEL)
				//
				.complete(Electrodynamics.ID, "machine_hydroelectric_generator", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.mineralcrusher), 1)
				//
				.addPattern("SCS")
				//
				.addPattern("GPG")
				//
				.addPattern("SWS")
				//
				.addKey('S', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ADVANCED)
				//
				.addKey('G', VoltaicTags.Items.GEAR_BRONZE)
				//
				.addKey('P', VoltaicTags.Items.PLATE_BRONZE)
				//
				.addKey('W', ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.copper))
				//
				.complete(Electrodynamics.ID, "machine_mineral_crusher", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.mineralcrusherdouble), 1)
				//
				.addPattern("EDE")
				//
				.addPattern("MCM")
				//
				.addPattern("EDE")
				//
				.addKey('E', VoltaicTags.Items.CIRCUITS_ELITE)
				//
				.addKey('D', Tags.Items.GEMS_DIAMOND)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.addKey('C', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.mineralcrusher))
				//
				.complete(Electrodynamics.ID, "machine_mineral_crusher_double", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.mineralcrushertriple), 1)
				//
				.addPattern("UVU")
				//
				.addPattern("MCM")
				//
				.addPattern("UVU")
				//
				.addKey('U', VoltaicTags.Items.CIRCUITS_ULTIMATE)
				//
				.addKey('V', ElectrodynamicsItems.ITEMS_CRYSTAL.getValue(SubtypeCrystal.vanadium))
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.addKey('C', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.mineralcrusherdouble))
				//
				.complete(Electrodynamics.ID, "machine_mineral_crusher_triple", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.mineralgrinder), 1)
				//
				.addPattern("PCP")
				//
				.addPattern("GMG")
				//
				.addPattern("PGP")
				//
				.addKey('P', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_BASIC)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.addKey('G', VoltaicTags.Items.GEAR_COPPER)
				//
				.complete(Electrodynamics.ID, "machine_mineral_grinder", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.mineralgrinderdouble), 1)
				//
				.addPattern("CGC")
				//
				.addPattern("BMB")
				//
				.addPattern("CGC")
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ADVANCED)
				//
				.addKey('B', VoltaicTags.Items.GEAR_BRONZE)
				//
				.addKey('G', Tags.Items.INGOTS_GOLD)
				//
				.addKey('M', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.mineralgrinder))
				//
				.complete(Electrodynamics.ID, "machine_mineral_grinder_double", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.mineralgrindertriple), 1)
				//
				.addPattern("CDC")
				//
				.addPattern("GMG")
				//
				.addPattern("CDC")
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ELITE)
				//
				.addKey('G', VoltaicTags.Items.GEAR_STEEL)
				//
				.addKey('D', VoltaicTags.Items.DUST_SUPERCONDUCTIVE)
				//
				.addKey('M', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.mineralgrinderdouble))
				//
				.complete(Electrodynamics.ID, "machine_mineral_grinder_triple", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.mineralwasher), 1)
				//
				.addPattern("SGS")
				//
				.addPattern("CEC")
				//
				.addPattern("PEP")
				//
				.addKey('S', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('G', VoltaicTags.Items.GEAR_STEEL)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ULTIMATE)
				//
				.addKey('P', ElectrodynamicsItems.ITEMS_PIPE.getValue(SubtypeFluidPipe.steel))
				//
				.addKey('E', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricpump))
				//
				.complete(Electrodynamics.ID, "machine_mineral_washer", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.motorcomplex), 1)
				//
				.addPattern("PGP")
				//
				.addPattern("GCM")
				//
				.addPattern("PGP")
				//
				.addKey('P', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('G', VoltaicTags.Items.GEAR_STEEL)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ELITE)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.complete(Electrodynamics.ID, "machine_motor_complex", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.multimeterblock), 1)
				//
				.addPattern(" S ")
				//
				.addPattern("SMS")
				//
				.addPattern(" S ")
				//
				.addKey('S', VoltaicTags.Items.INGOT_STEEL)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MULTIMETER.get())
				//
				.complete(Electrodynamics.ID, "machine_multimeter_block", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.oxidationfurnace), 1)
				//
				.addPattern("PGP")
				//
				.addPattern("CFC")
				//
				.addPattern("PMP")
				//
				.addKey('P', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('G', VoltaicTags.Items.GEAR_STEEL)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ADVANCED)
				//
				.addKey('F', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricfurnace))
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.complete(Electrodynamics.ID, "machine_oxidation_furnace", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.quarry), 1)
				//
				.addPattern("PCP")
				//
				.addPattern("CDC")
				//
				.addPattern("PCP")
				//
				.addKey('P', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ELITE)
				//
				.addKey('D', Items.DIAMOND_PICKAXE)
				//
				.complete(Electrodynamics.ID, "machine_quarry", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.reinforcedalloyer), 1)
				//
				.addPattern("CSC")
				//
				.addPattern("SAS")
				//
				.addPattern("CTC")
				//
				.addKey('C', ElectrodynamicsItems.ITEM_COMPOSITEPLATING.get())
				//
				.addKey('S', VoltaicTags.Items.PLATE_STAINLESSSTEEL)
				//
				.addKey('A', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.energizedalloyer))
				//
				.addKey('T', ElectrodynamicsItems.ITEM_TITANIUM_COIL.get())
				//
				.complete(Electrodynamics.ID, "machine_reinforced_alloyer", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.seismicrelay), 1)
				//
				.addPattern("PAP")
				//
				.addPattern("ACA")
				//
				.addPattern("MAM")
				//
				.addKey('P', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('A', Tags.Items.GEMS_AMETHYST)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ULTIMATE)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_SEISMICMARKER.get())
				//
				.complete(Electrodynamics.ID, "machine_seismic_relay", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.solarpanel), 1)
				//
				.addPattern("SPS")
				//
				.addPattern("CIC")
				//
				.addPattern("IWI")
				//
				.addKey('S', ElectrodynamicsItems.ITEM_SOLARPANELPLATE.get())
				//
				.addKey('P', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_BASIC)
				//
				.addKey('I', Tags.Items.INGOTS_IRON)
				//
				.addKey('W', ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.copper))
				//
				.complete(Electrodynamics.ID, "machine_solar_panel", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.tanksteel), 1)
				//
				.addPattern("SGS")
				//
				.addPattern("GCG")
				//
				.addPattern("SGS")
				//
				.addKey('S', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('G', Tags.Items.GLASS)
				//
				.addKey('C', Items.CAULDRON)
				//
				.complete(Electrodynamics.ID, "machine_tank_steel", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.tankreinforced), 1)
				//
				.addPattern("SGS")
				//
				.addPattern("GTG")
				//
				.addPattern("SGS")
				//
				.addKey('S', VoltaicTags.Items.PLATE_STAINLESSSTEEL)
				//
				.addKey('G', ElectrodynamicsItems.ITEMS_CUSTOMGLASS.getValue(SubtypeGlass.clear))
				//
				.addKey('T', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.tanksteel))
				//
				.complete(Electrodynamics.ID, "machine_tank_reinforced", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.tankhsla), 1)
				//
				.addPattern("SGS")
				//
				.addPattern("GTG")
				//
				.addPattern("SGS")
				//
				.addKey('S', VoltaicTags.Items.PLATE_HSLASTEEL)
				//
				.addKey('G', ElectrodynamicsItems.ITEMS_CUSTOMGLASS.getValue(SubtypeGlass.aluminum))
				//
				.addKey('T', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.tankreinforced))
				//
				.complete(Electrodynamics.ID, "machine_tank_hsla", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.thermoelectricgenerator), 1)
				//
				.addPattern("ISI")
				//
				.addPattern("SPS")
				//
				.addPattern("CFC")
				//
				.addKey('I', Tags.Items.INGOTS_IRON)
				//
				.addKey('S', VoltaicTags.Items.INGOT_STEEL)
				//
				.addKey('C', Tags.Items.INGOTS_COPPER)
				//
				.addKey('P', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('F', Items.FURNACE)
				//
				.complete(Electrodynamics.ID, "machine_thermoelectric_generator", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.upgradetransformer), 1)
				//
				.addPattern("ISI")
				//
				.addPattern("C W")
				//
				.addPattern("SSS")
				//
				.addKey('I', Tags.Items.INGOTS_IRON)
				//
				.addKey('S', VoltaicTags.Items.INGOT_STEEL)
				//
				.addKey('W', ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.copper))
				//
				.addKey('C', ElectrodynamicsItems.ITEM_COIL.get())
				//
				.complete(Electrodynamics.ID, "machine_upgrade_transformer", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.windmill), 1)
				//
				.addPattern(" GM")
				//
				.addPattern(" S ")
				//
				.addPattern("ISI")
				//
				.addKey('G', VoltaicTags.Items.GEAR_IRON)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.addKey('S', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('I', Tags.Items.INGOTS_IRON)
				//
				.complete(Electrodynamics.ID, "machine_windmill", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.wiremill), 1)
				//
				.addPattern("PBP")
				//
				.addPattern("MGM")
				//
				.addPattern("PGP")
				//
				.addKey('P', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('B', VoltaicTags.Items.CIRCUITS_BASIC)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.addKey('G', VoltaicTags.Items.GEAR_BRONZE)
				//
				.complete(Electrodynamics.ID, "machine_wiremill", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.wiremilldouble), 1)
				//
				.addPattern("CGC")
				//
				.addPattern("WMW")
				//
				.addPattern("CGC")
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_BASIC)
				//
				.addKey('G', VoltaicTags.Items.GEAR_BRONZE)
				//
				.addKey('W', VoltaicTags.Items.INSULATED_SILVER_WIRES)
				//
				.addKey('M', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.wiremill))
				//
				.complete(Electrodynamics.ID, "machine_wiremill_double", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.wiremilltriple), 1)
				//
				.addPattern("CGC")
				//
				.addPattern("WMW")
				//
				.addPattern("CGC")
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ADVANCED)
				//
				.addKey('G', VoltaicTags.Items.GEAR_STEEL)
				//
				.addKey('W', VoltaicTags.Items.CERAMIC_SILVER_WIRES)
				//
				.addKey('M', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.wiremilldouble))
				//
				.complete(Electrodynamics.ID, "machine_wiremill_triple", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.fluidvalve), 1)
				//
				.addPattern("VPR")
				//
				.addKey('V', VoltaicTags.Items.GEAR_BRONZE)
				//
				.addKey('P', ElectrodynamicsItems.ITEMS_PIPE.getValue(SubtypeFluidPipe.steel))
				//
				.addKey('R', Tags.Items.DUSTS_REDSTONE)
				//
				.complete(Electrodynamics.ID, "pipe_fluidvalve", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.fluidpipepump), 1)
				//
				.addPattern(" C ")
				//
				.addPattern("VPV")
				//
				.addPattern(" M ")
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_BASIC)
				//
				.addKey('V', VoltaicTags.Items.GEAR_BRONZE)
				//
				.addKey('P', ElectrodynamicsItems.ITEMS_PIPE.getValue(SubtypeFluidPipe.steel))
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.complete(Electrodynamics.ID, "pipe_fluidpump", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.fluidpipefilter), 1)
				//
				.addPattern(" C ")
				//
				.addPattern("VPV")
				//
				.addPattern(" # ")
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_BASIC)
				//
				.addKey('V', VoltaicTags.Items.GEAR_BRONZE)
				//
				.addKey('P', ElectrodynamicsItems.ITEMS_PIPE.getValue(SubtypeFluidPipe.steel))
				//
				.addKey('#', Items.PAPER)
				//
				.complete(Electrodynamics.ID, "pipe_fluidfilter", consumer);

	}

	private void addGear(Consumer<FinishedRecipe> consumer) {

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_CANISTERREINFORCED.get(), 1)
				//
				.addPattern("S")
				//
				.addPattern("A")
				//
				.addPattern("S")
				//
				.addKey('S', VoltaicTags.Items.PLATE_STAINLESSSTEEL)
				//
				.addKey('A', ElectrodynamicsItems.ITEMS_CUSTOMGLASS.getValue(SubtypeGlass.aluminum))
				//
				.complete(Electrodynamics.ID, "reinforced_canister", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_COMBATBOOTS.get(), 1)
				//
				.addPattern("THT")
				//
				.addPattern("TST")
				//
				.addPattern("TBT")
				//
				.addKey('T', VoltaicTags.Items.PLATE_TITANIUMCARBIDE)
				//
				.addKey('S', VoltaicTags.Items.PLATE_HSLASTEEL)
				//
				.addKey('H', ElectrodynamicsItems.ITEM_HYDRAULICBOOTS.get())
				//
				.addKey('B', ElectrodynamicsItems.ITEM_COMPOSITEBOOTS.get())
				//
				.complete(Electrodynamics.ID, "combat_boots", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_COMBATCHESTPLATE.get(), 1)
				//
				.addPattern("TJT")
				//
				.addPattern("TST")
				//
				.addPattern("TCT")
				//
				.addKey('T', VoltaicTags.Items.PLATE_TITANIUMCARBIDE)
				//
				.addKey('S', VoltaicTags.Items.PLATE_HSLASTEEL)
				//
				.addKey('J', ElectrodynamicsItems.ITEM_JETPACK.get())
				//
				.addKey('C', ElectrodynamicsItems.ITEM_COMPOSITECHESTPLATE.get())
				//
				.complete(Electrodynamics.ID, "combat_chestplate", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_COMBATHELMET.get(), 1)
				//
				.addPattern("TNT")
				//
				.addPattern("TST")
				//
				.addPattern("THT")
				//
				.addKey('T', VoltaicTags.Items.PLATE_TITANIUMCARBIDE)
				//
				.addKey('S', VoltaicTags.Items.PLATE_HSLASTEEL)
				//
				.addKey('N', ElectrodynamicsItems.ITEM_NIGHTVISIONGOGGLES.get())
				//
				.addKey('H', ElectrodynamicsItems.ITEM_COMPOSITEHELMET.get())
				//
				.complete(Electrodynamics.ID, "combat_helmet", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_COMBATLEGGINGS.get(), 1)
				//
				.addPattern("TMT")
				//
				.addPattern("TST")
				//
				.addPattern("TLT")
				//
				.addKey('T', VoltaicTags.Items.PLATE_TITANIUMCARBIDE)
				//
				.addKey('S', VoltaicTags.Items.PLATE_HSLASTEEL)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_SERVOLEGGINGS.get())
				//
				.addKey('L', ElectrodynamicsItems.ITEM_COMPOSITELEGGINGS.get())
				//
				.complete(Electrodynamics.ID, "combat_leggings", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_COMPOSITEBOOTS.get(), 1)
				//
				.addPattern("P P")
				//
				.addPattern("PSP")
				//
				.addKey('P', ElectrodynamicsItems.ITEM_COMPOSITEPLATING.get())
				//
				.addKey('S', VoltaicTags.Items.PLATE_STEEL)
				//
				.complete(Electrodynamics.ID, "composite_boots", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_COMPOSITECHESTPLATE.get(), 1)
				//
				.addPattern("PSP")
				//
				.addPattern("PPP")
				//
				.addPattern("PPP")
				//
				.addKey('P', ElectrodynamicsItems.ITEM_COMPOSITEPLATING.get())
				//
				.addKey('S', VoltaicTags.Items.PLASTIC)
				//
				.complete(Electrodynamics.ID, "composite_chestplate", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_COMPOSITEHELMET.get(), 1)
				//
				.addPattern("PPP")
				//
				.addPattern("PGP")
				//
				.addKey('P', ElectrodynamicsItems.ITEM_COMPOSITEPLATING.get())
				//
				.addKey('G', ElectrodynamicsItems.ITEMS_CUSTOMGLASS.getValue(SubtypeGlass.aluminum))
				//
				.complete(Electrodynamics.ID, "composite_helmet", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_COMPOSITELEGGINGS.get(), 1)
				//
				.addPattern("PPP")
				//
				.addPattern("P P")
				//
				.addPattern("P P")
				//
				.addKey('P', ElectrodynamicsItems.ITEM_COMPOSITEPLATING.get())
				//
				.complete(Electrodynamics.ID, "composite_leggings", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_ELECTRICBATON.get(), 1)
				//
				.addPattern("  T")
				//
				.addPattern("PT ")
				//
				.addPattern("BC ")
				//
				.addKey('T', VoltaicTags.Items.ROD_STEEL)
				//
				.addKey('P', VoltaicTags.Items.PLASTIC)
				//
				.addKey('B', ElectrodynamicsItems.ITEM_LITHIUMBATTERY.get())
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ADVANCED)
				//
				.complete(Electrodynamics.ID, "electric_baton", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_ELECTRICCHAINSAW.get(), 1)
				//
				.addPattern("SP ")
				//
				.addPattern("IMB")
				//
				.addKey('S', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('P', VoltaicTags.Items.PLASTIC)
				//
				.addKey('I', ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.iron))
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.addKey('B', ElectrodynamicsItems.ITEM_BATTERY.get())
				//
				.complete(Electrodynamics.ID, "electric_chainsaw", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_ELECTRICDRILL.get(), 1)
				//
				.addPattern("DMP")
				//
				.addPattern(" RB")
				//
				.addKey('D', ElectrodynamicsItems.ITEMS_DRILLHEAD.getValue(SubtypeDrillHead.steel))
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.addKey('P', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('R', VoltaicTags.Items.ROD_STEEL)
				//
				.addKey('B', ElectrodynamicsItems.ITEM_BATTERY.get())
				//
				.complete(Electrodynamics.ID, "electric_drill", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_HYDRAULICBOOTS.get(), 1)
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
				.addKey('S', VoltaicTags.Items.PLATE_STEEL)
				//
				.complete(Electrodynamics.ID, "hydraulic_boots", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_CERAMICINSULATION.get(), 6)
				//
				.addPattern("P P")
				//
				.addPattern("PBP")
				//
				.addPattern("P P")
				//
				.addKey('P', ElectrodynamicsItems.ITEMS_CERAMIC.getValue(SubtypeCeramic.plate))
				//
				.addKey('B', Items.IRON_BARS)
				//
				.complete(Electrodynamics.ID, "ceramic_insulation", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_JETPACK.get(), 1)
				//
				.addPattern("CTC")
				//
				.addPattern("SPS")
				//
				.addPattern("P P")
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ADVANCED)
				//
				.addKey('S', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('T', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.tanksteel))
				//
				.addKey('P', ElectrodynamicsItems.ITEMS_PIPE.getValue(SubtypeFluidPipe.steel))
				//
				.complete(Electrodynamics.ID, "jetpack", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_MECHANIZEDCROSSBOW.get(), 1)
				//
				.addPattern(" S ")
				//
				.addPattern("SbC")
				//
				.addPattern(" MB")
				//
				.addKey('S', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_BASIC)
				//
				.addKey('b', Items.CROSSBOW)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.addKey('B', ElectrodynamicsItems.ITEM_BATTERY.get())
				//
				.complete(Electrodynamics.ID, "mechanized_crossbow", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_MULTIMETER.get(), 1)
				//
				.addPattern("SWS")
				//
				.addPattern("SCS")
				//
				.addPattern("SWS")
				//
				.addKey('S', VoltaicTags.Items.INGOT_STEEL)
				//
				.addKey('W', VoltaicTags.Items.INSULATED_COPPER_WIRES)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_BASIC)
				//
				.complete(Electrodynamics.ID, "multimeter", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_NIGHTVISIONGOGGLES.get(), 1)
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
				.addKey('C', VoltaicTags.Items.CIRCUITS_ADVANCED)
				//
				.addKey('G', Items.GLOW_INK_SAC)
				//
				.addKey('B', ElectrodynamicsItems.ITEM_BATTERY.get())
				//
				.complete(Electrodynamics.ID, "night_vision_goggles", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_RUBBERBOOTS.get(), 1)
				//
				.addPattern("I I")
				//
				.addPattern("I I")
				//
				.addKey('I', ElectrodynamicsItems.ITEM_INSULATION.get())
				//
				.complete(Electrodynamics.ID, "rubber_boots", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_KINETICRAILGUN.get(), 1)
				//
				.addPattern("CXS")
				//
				.addPattern("LPR")
				//
				.addPattern("BCS")
				//
				.addKey('L', ElectrodynamicsItems.ITEM_LITHIUMBATTERY.get())
				//
				.addKey('X', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.upgradetransformer))
				//
				.addKey('S', ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.superconductive))
				//
				.addKey('B', VoltaicTags.Items.STORAGE_BLOCK_STAINLESSSTEEL)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ELITE)
				//
				.addKey('P', VoltaicTags.Items.PLATE_STAINLESSSTEEL)
				//
				.addKey('R', VoltaicTags.Items.ROD_STAINLESSSTEEL)
				//
				.complete(Electrodynamics.ID, "rail_gun_kinetic", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_PLASMARAILGUN.get(), 1)
				//
				.addPattern("CXS")
				//
				.addPattern("LPR")
				//
				.addPattern("BCS")
				//
				.addKey('L', ElectrodynamicsItems.ITEM_CARBYNEBATTERY.get())
				//
				.addKey('X', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.upgradetransformer))
				//
				.addKey('S', ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.superconductive))
				//
				.addKey('B', VoltaicTags.Items.STORAGE_BLOCK_HSLASTEEL)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ULTIMATE)
				//
				.addKey('P', VoltaicTags.Items.PLATE_HSLASTEEL)
				//
				.addKey('R', VoltaicTags.Items.ROD_TITANIUMCARBIDE)
				//
				.complete(Electrodynamics.ID, "rail_gun_plasma", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_SEISMICSCANNER.get(), 1)
				//
				.addPattern("PQP")
				//
				.addPattern("ABA")
				//
				.addPattern("PCP")
				//
				.addKey('P', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('Q', Tags.Items.GEMS_QUARTZ)
				//
				.addKey('A', Tags.Items.GEMS_AMETHYST)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_BASIC)
				//
				.addKey('B', ElectrodynamicsItems.ITEM_BATTERY.get())
				//
				.complete(Electrodynamics.ID, "seismic_scanner", consumer);

		CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEM_SERVOLEGGINGS.get(), 1)
				//
				.addPattern("PCP")
				//
				.addPattern("MWM")
				//
				.addPattern("B B")
				//
				.addKey('P', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_BASIC)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.addKey('W', VoltaicTags.Items.INSULATED_COPPER_WIRES)
				//
				.addKey('B', ElectrodynamicsItems.ITEM_BATTERY.get())
				//
				.complete(Electrodynamics.ID, "servo_leggings", consumer);

		CustomShapedCraftingRecipe.start(VoltaicItems.ITEM_WRENCH.get(), 1)
				//
				.addPattern(" S ")
				//
				.addPattern(" SS")
				//
				.addPattern("S  ")
				//
				.addKey('S', VoltaicTags.Items.INGOT_STEEL)
				//
				.complete(Electrodynamics.ID, "wrench_electro", consumer);

	}

	private void addWires(Consumer<FinishedRecipe> consumer) {

// Insulated Wires
		for (SubtypeWire wire : DataGenerators.getWires(WireMaterial.values(), InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.BLACK)) {

			SubtypeWire uninsulated = DataGenerators.getWire(wire.getWireMaterial(), InsulationMaterial.BARE, WireClass.BARE, WireColor.NONE);

			CustomShapelessCraftingRecipe.start(ElectrodynamicsItems.ITEMS_WIRE.getValue(wire), 1)
					//
					.addIngredient(ElectrodynamicsItems.ITEMS_WIRE.getValue(uninsulated))
					//
					.addIngredient(ElectrodynamicsItems.ITEM_INSULATION.get())
					//
					.complete(Electrodynamics.ID, "wire_" + wire.name(), consumer);

		}

// Logistics Wires
		for (SubtypeWire wire : DataGenerators.getWires(WireMaterial.values(), InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.BLACK)) {

			SubtypeWire insulated = DataGenerators.getWire(wire.getWireMaterial(), InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.BLACK);

			CustomShapelessCraftingRecipe.start(ElectrodynamicsItems.ITEMS_WIRE.getValue(wire), 1)
					//
					.addIngredient(insulated.getItemTag())
					//
					.addIngredient(Tags.Items.DUSTS_REDSTONE)
					//
					.complete(Electrodynamics.ID, "wire_" + wire.name(), consumer);

		}

// Ceramic Insulated
		for (SubtypeWire wire : DataGenerators.getWires(WireMaterial.values(), InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.BROWN)) {

			SubtypeWire insulated = DataGenerators.getWire(wire.getWireMaterial(), InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.BLACK);

			CustomShapelessCraftingRecipe.start(ElectrodynamicsItems.ITEMS_WIRE.getValue(wire), 1)
					//
					.addIngredient(insulated.getItemTag())
					//
					.addIngredient(ElectrodynamicsItems.ITEM_CERAMICINSULATION.get())
					//
					.complete(Electrodynamics.ID, "wire_" + wire.name(), consumer);

		}

// Highly Insulated
		for (SubtypeWire wire : DataGenerators.getWires(WireMaterial.values(), InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.BLACK)) {

			SubtypeWire insulated = DataGenerators.getWire(wire.getWireMaterial(), InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.BLACK);

			CustomShapelessCraftingRecipe.start(ElectrodynamicsItems.ITEMS_WIRE.getValue(wire), 2)
					//
					.addIngredient(insulated.getItemTag())
					//
					.addIngredient(insulated.getItemTag())
					//
					.addIngredient(insulated.getItemTag())
					//
					.addIngredient(ElectrodynamicsItems.ITEM_INSULATION.get())
					//
					.addIngredient(ElectrodynamicsItems.ITEM_INSULATION.get())
					//
					.addIngredient(ElectrodynamicsItems.ITEM_INSULATION.get())
					//
					.complete(Electrodynamics.ID, "wire_" + wire.name(), consumer);

		}

// Insulated Wires
		for (SubtypeWire wire : DataGenerators.getWires(WireMaterial.values(), InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.values())) {
			if (wire.getWireColor().getDyeTag() == null) {
				continue;
			}
			CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_WIRE.getValue(wire), 8)
					//
					.addPattern("WWW")
					//
					.addPattern("WDW")
					//
					.addPattern("WWW")
					//
					.addKey('W', wire.getItemTag())
					//
					.addKey('D', wire.getWireColor().getDyeTag())
					//
					.complete(Electrodynamics.ID, "wire_" + wire.name() + "_multi", consumer);

			CustomShapelessCraftingRecipe.start(ElectrodynamicsItems.ITEMS_WIRE.getValue(wire), 1)
					//
					.addIngredient(wire.getItemTag())
					//
					.addIngredient(wire.getWireColor().getDyeTag())
					//
					.complete(Electrodynamics.ID, "wire_" + wire.name() + "_single", consumer);
		}

// Highly Insulated Wires
		for (SubtypeWire wire : DataGenerators.getWires(WireMaterial.values(), InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.values())) {
			if (wire.getWireColor().getDyeTag() == null) {
				continue;
			}
			CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_WIRE.getValue(wire), 8)
					//
					.addPattern("WWW")
					//
					.addPattern("WDW")
					//
					.addPattern("WWW")
					//
					.addKey('W', wire.getItemTag())
					//
					.addKey('D', wire.getWireColor().getDyeTag())
					//
					.complete(Electrodynamics.ID, "wire_" + wire.name() + "_multi", consumer);

			CustomShapelessCraftingRecipe.start(ElectrodynamicsItems.ITEMS_WIRE.getValue(wire), 1)
					//
					.addIngredient(wire.getItemTag())
					//
					.addIngredient(wire.getWireColor().getDyeTag())
					//
					.complete(Electrodynamics.ID, "wire_" + wire.name() + "_single", consumer);
		}

// Ceramic Insulated Wires
		for (SubtypeWire wire : DataGenerators.getWires(WireMaterial.values(), InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.values())) {
			if (wire.getWireColor().getDyeTag() == null) {
				continue;
			}
			CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_WIRE.getValue(wire), 8)
					//
					.addPattern("WWW")
					//
					.addPattern("WDW")
					//
					.addPattern("WWW")
					//
					.addKey('W', wire.getItemTag())
					//
					.addKey('D', wire.getWireColor().getDyeTag())
					//
					.complete(Electrodynamics.ID, "wire_" + wire.name() + "_multi", consumer);

			CustomShapelessCraftingRecipe.start(ElectrodynamicsItems.ITEMS_WIRE.getValue(wire), 1)
					//
					.addIngredient(wire.getItemTag())
					//
					.addIngredient(wire.getWireColor().getDyeTag())
					//
					.complete(Electrodynamics.ID, "wire_" + wire.name() + "_single", consumer);
		}

// Ceramic Insulated Wires
		for (SubtypeWire wire : DataGenerators.getWires(WireMaterial.values(), InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.values())) {
			if (wire.getWireColor().getDyeTag() == null) {
				continue;
			}
			CustomShapedCraftingRecipe.start(ElectrodynamicsItems.ITEMS_WIRE.getValue(wire), 8)
					//
					.addPattern("WWW")
					//
					.addPattern("WDW")
					//
					.addPattern("WWW")
					//
					.addKey('W', wire.getItemTag())
					//
					.addKey('D', wire.getWireColor().getDyeTag())
					//
					.complete(Electrodynamics.ID, "wire_" + wire.name() + "_multi", consumer);

			CustomShapelessCraftingRecipe.start(ElectrodynamicsItems.ITEMS_WIRE.getValue(wire), 1)
					//
					.addIngredient(wire.getItemTag())
					//
					.addIngredient(wire.getWireColor().getDyeTag())
					//
					.complete(Electrodynamics.ID, "wire_" + wire.name() + "_single", consumer);
		}

	}

}