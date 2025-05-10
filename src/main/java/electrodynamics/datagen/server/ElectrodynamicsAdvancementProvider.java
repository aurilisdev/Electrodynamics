package electrodynamics.datagen.server;

import java.util.function.Consumer;

import electrodynamics.Electrodynamics;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards.Builder;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Items;
import net.minecraft.util.text.TextFormatting;
import voltaic.datagen.utils.server.advancement.AdvancementBuilder;
import voltaic.datagen.utils.server.advancement.AdvancementBuilder.AdvancementBackgrounds;
import voltaic.datagen.utils.server.advancement.BaseAdvancementProvider;

public class ElectrodynamicsAdvancementProvider extends BaseAdvancementProvider {

	public ElectrodynamicsAdvancementProvider(DataGenerator generatorIn) {
		super(generatorIn, Electrodynamics.ID);
	}

	/**
	 * Override this to do mod-specific advancements
	 * 
	 * @param consumer
	 */
	public void registerAdvancements(Consumer<AdvancementBuilder> consumer) {

		Advancement root = advancement("root")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.downgradetransformer), ElectroTextUtils.advancement("root.title").withStyle(TextFormatting.AQUA), ElectroTextUtils.advancement("root.desc"), AdvancementBackgrounds.STONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasCraftingTable", InventoryChangeTrigger.Instance.hasItems(Items.CRAFTING_TABLE))
				//
				.rewards(Builder.experience(10))
				//
				.save(consumer);

		// ORES

		Advancement ores = advancement("ores")
				//
				.display(ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.tin), ElectroTextUtils.advancement("ores.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("ores.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, false, false, false)
				//
				.addCriterion("HasWoodenPickaxe", InventoryChangeTrigger.Instance.hasItems(Items.WOODEN_PICKAXE))
				//
				.rewards(Builder.experience(10))
				//
				.parent(root)
				//
				.save(consumer);

		advancement("raworevanadium")
				//
				.display(ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.vanadium), ElectroTextUtils.advancement("rawvanadium.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("rawvanadium.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasRawOre", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.vanadium)))
				//
				.rewards(Builder.experience(10))
				//
				.parent(ores)
				//
				.save(consumer);

		advancement("raworeuranium")
				//
				.display(ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.uranium), ElectroTextUtils.advancement("rawuranium.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("rawuranium.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasRawOre", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.uranium)))
				//
				.rewards(Builder.experience(10))
				//
				.parent(ores)
				//
				.save(consumer);

		advancement("raworechromium")
				//
				.display(ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.chromium), ElectroTextUtils.advancement("rawchromium.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("rawchromium.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasRawOre", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.chromium)))
				//
				.rewards(Builder.experience(10))
				//
				.parent(ores)
				//
				.save(consumer);

		advancement("rawfluorite")
				//
				.display(ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.fluorite), ElectroTextUtils.advancement("rawfluorite.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("rawfluorite.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasRawOre", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.fluorite)))
				//
				.rewards(Builder.experience(10))
				//
				.parent(ores)
				//
				.save(consumer);

		advancement("raworelead")
				//
				.display(ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.lead), ElectroTextUtils.advancement("rawlead.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("rawlead.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasRawOre", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.lead)))
				//
				.rewards(Builder.experience(10))
				//
				.parent(ores)
				//
				.save(consumer);

		advancement("raworelithium")
				//
				.display(ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.lithium), ElectroTextUtils.advancement("rawlithium.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("rawlithium.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasRawOre", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.lithium)))
				//
				.rewards(Builder.experience(10))
				//
				.parent(ores)
				//
				.save(consumer);

		advancement("raworesilver")
				//
				.display(ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.silver), ElectroTextUtils.advancement("rawsilver.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("rawsilver.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasRawOre", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.silver)))
				//
				.rewards(Builder.experience(10))
				//
				.parent(ores)
				//
				.save(consumer);

		advancement("raworethorium")
				//
				.display(ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.thorium), ElectroTextUtils.advancement("rawthorium.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("rawthorium.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasRawOre", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.thorium)))
				//
				.rewards(Builder.experience(10))
				//
				.parent(ores)
				//
				.save(consumer);

		advancement("raworetin")
				//
				.display(ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.tin), ElectroTextUtils.advancement("rawtin.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("rawtin.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasRawOre", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.tin)))
				//
				.rewards(Builder.experience(10))
				//
				.parent(ores)
				//
				.save(consumer);

		advancement("raworetitanium")
				//
				.display(ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.titanium), ElectroTextUtils.advancement("rawtitanium.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("rawtitanium.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasRawOre", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.titanium)))
				//
				.rewards(Builder.experience(10))
				//
				.parent(ores)
				//
				.save(consumer);

		// WIRING

		Advancement basicWiring = advancement("basicwiring")
				//
				.display(ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.copper), ElectroTextUtils.advancement("basicwiring.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("basicwiring.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasCopperWire", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.copper)))
				//
				.rewards(Builder.experience(20))
				//
				.parent(root)
				//
				.save(consumer);

		Advancement betterWiring = advancement("betterwiring")
				//
				.display(ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.silver), ElectroTextUtils.advancement("betterwiring.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("betterwiring.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasSilverWire", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.silver)))
				//
				.rewards(Builder.experience(20))
				//
				.parent(basicWiring)
				//
				.save(consumer);

		Advancement superiorWiring = advancement("superiorwiring")
				//
				.display(ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.gold), ElectroTextUtils.advancement("superiorwiring.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("superiorwiring.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasGoldWire", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.gold)))
				//
				.rewards(Builder.experience(20))
				//
				.parent(betterWiring)
				//
				.save(consumer);

		advancement("superconductivewiring")
				//
				.display(ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.superconductive), ElectroTextUtils.advancement("superconductivewiring.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("superconductivewiring.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasSuperconductiveWire", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.superconductive)))
				//
				.rewards(Builder.experience(50))
				//
				.parent(superiorWiring)
				//
				.save(consumer);

		// INSULATION

		Advancement insulation = advancement("insulation")
				//
				.display(ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.insulatedcopper), ElectroTextUtils.advancement("insulatedwiring.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("insulation.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasInsulation", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEM_INSULATION.get()))
				//
				.rewards(Builder.experience(20))
				//
				.parent(basicWiring)
				//
				.save(consumer);

		Advancement insulatedWiring = advancement("insulatedwiring")
				//
				.display(ElectrodynamicsItems.ITEM_INSULATION.get(), ElectroTextUtils.advancement("insulatedwiring.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("insulatedwiring.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasInsulatedCopperWire", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.insulatedcopper)))
				//
				.rewards(Builder.experience(20))
				//
				.parent(insulation)
				//
				.save(consumer);

		advancement("highlyinsulatedwiring")
				//
				.display(ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.highlyinsulatedcopper), ElectroTextUtils.advancement("highlyinsulatedwiring.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("highlyinsulatedwiring.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasHighlyInsulatedCopperWire", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.highlyinsulatedcopper)))
				//
				.rewards(Builder.experience(20))
				//
				.parent(insulatedWiring)
				//
				.save(consumer);

		advancement("ceramicinsulation")
				//
				.display(ElectrodynamicsItems.ITEM_CERAMICINSULATION.get(), ElectroTextUtils.advancement("ceramicinsulation.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("ceramicinsulation.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasCeramicInsulation", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEM_CERAMICINSULATION.get()))
				//
				.rewards(Builder.experience(30))
				//
				.parent(insulation)
				//
				.save(consumer);

		advancement("ceramicinsulatedwiring")
				//
				.display(ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.ceramicinsulatedcopperbrown), ElectroTextUtils.advancement("ceramicinsulatedwiring.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("ceramicinsulatedwiring.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasCeramicInsulatedCopperWire", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.ceramicinsulatedcopperbrown)))
				//
				.rewards(Builder.experience(15))
				//
				.parent(insulatedWiring)
				//
				.save(consumer);

		// WIRING DEVICES

		advancement("downgradetransformer")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.downgradetransformer), ElectroTextUtils.advancement("downgradetransformer.title").withStyle(TextFormatting.GOLD), ElectroTextUtils.advancement("downgradetransformer.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasTransformer", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.downgradetransformer)))
				//
				.rewards(Builder.experience(25))
				//
				.parent(basicWiring)
				//
				.save(consumer);

		advancement("upgradetransformer")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.upgradetransformer), ElectroTextUtils.advancement("upgradetransformer.title").withStyle(TextFormatting.LIGHT_PURPLE), ElectroTextUtils.advancement("upgradetransformer.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasTransformer", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.upgradetransformer)))
				//
				.rewards(Builder.experience(25))
				//
				.parent(basicWiring)
				//
				.save(consumer);

		advancement("circuitbreaker")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.circuitbreaker), ElectroTextUtils.advancement("circuitbreaker.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("circuitbreaker.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasCircuitBreaker", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.circuitbreaker)))
				//
				.rewards(Builder.experience(30))
				//
				.parent(basicWiring)
				//
				.save(consumer);

		// GENERATORS

		Advancement coalGenerator = advancement("coalgenerator")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.coalgenerator), ElectroTextUtils.advancement("coalgenerator.title").withStyle(TextFormatting.GOLD), ElectroTextUtils.advancement("coalgenerator.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasCoalGenerator", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.coalgenerator)))
				//
				.rewards(Builder.experience(25))
				//
				.parent(root)
				//
				.save(consumer);

		advancement("thermoelectricgenerator")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.thermoelectricgenerator), ElectroTextUtils.advancement("thermoelectricgenerator.title").withStyle(TextFormatting.RED), ElectroTextUtils.advancement("thermoelectricgenerator.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasThermoGenerator", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.thermoelectricgenerator)))
				//
				.rewards(Builder.experience(20))
				//
				.parent(coalGenerator)
				//
				.save(consumer);

		Advancement solarPanel = advancement("solarpanel")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.solarpanel), ElectroTextUtils.advancement("solarpanel.title").withStyle(TextFormatting.BLUE), ElectroTextUtils.advancement("solarpanel.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasPanel", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.solarpanel)))
				//
				.rewards(Builder.experience(25))
				//
				.parent(coalGenerator)
				//
				.save(consumer);

		advancement("advancedsolarpanel")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.advancedsolarpanel), ElectroTextUtils.advancement("advancedsolarpanel.title").withStyle(TextFormatting.DARK_BLUE), ElectroTextUtils.advancement("advancedsolarpanel.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasPanel", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.advancedsolarpanel)))
				//
				.rewards(Builder.experience(75))
				//
				.parent(solarPanel)
				//
				.save(consumer);

		// BATTERY BOXES

		Advancement batteryBox = advancement("batterybox")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.batterybox), ElectroTextUtils.advancement("batterybox.title").withStyle(TextFormatting.GOLD), ElectroTextUtils.advancement("batterybox.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasBatteryBox", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.batterybox)))
				//
				.rewards(Builder.experience(30))
				//
				.parent(coalGenerator)
				//
				.save(consumer);

		Advancement lithiumBatteryBox = advancement("lithiumbatterybox")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.lithiumbatterybox), ElectroTextUtils.advancement("lithiumbatterybox.title").withStyle(TextFormatting.BLUE), ElectroTextUtils.advancement("lithiumbatterybox.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasBatteryBox", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.lithiumbatterybox)))
				//
				.rewards(Builder.experience(70))
				//
				.parent(batteryBox)
				//
				.save(consumer);

		advancement("carbynebatterybox")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.carbynebatterybox), ElectroTextUtils.advancement("carbynebatterybox.title").withStyle(TextFormatting.RED), ElectroTextUtils.advancement("carbynebatterybox.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasBatteryBox", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.carbynebatterybox)))
				//
				.rewards(Builder.experience(150))
				//
				.parent(lithiumBatteryBox)
				//
				.save(consumer);

		// WIRE MILLS

		Advancement wiremill = advancement("wiremill")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.wiremill), ElectroTextUtils.advancement("wiremill.title").withStyle(TextFormatting.GOLD), ElectroTextUtils.advancement("wiremill.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasMill", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.wiremill)))
				//
				.rewards(Builder.experience(20))
				//
				.parent(coalGenerator)
				//
				.save(consumer);

		Advancement doubleWiremill = advancement("doublewiremill")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.wiremilldouble), ElectroTextUtils.advancement("doublewiremill.title").withStyle(TextFormatting.BLUE), ElectroTextUtils.advancement("doublewiremill.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasMill", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.wiremilldouble)))
				//
				.rewards(Builder.experience(50))
				//
				.parent(wiremill)
				//
				.save(consumer);

		advancement("triplewiremill")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.wiremilltriple), ElectroTextUtils.advancement("triplewiremill.title").withStyle(TextFormatting.RED), ElectroTextUtils.advancement("triplewiremill.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasMill", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.wiremilltriple)))
				//
				.rewards(Builder.experience(100))
				//
				.parent(doubleWiremill)
				//
				.save(consumer);

		// ELECTRIC FURNACE

		Advancement electricFurnace = advancement("electricfurnace")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricfurnace), ElectroTextUtils.advancement("electricfurnace.title").withStyle(TextFormatting.GOLD), ElectroTextUtils.advancement("electricfurnace.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasFurnace", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricfurnace)))
				//
				.rewards(Builder.experience(30))
				//
				.parent(coalGenerator)
				//
				.save(consumer);

		Advancement doubleElectricFurnace = advancement("doubleelectricfurnace")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricfurnacedouble), ElectroTextUtils.advancement("doubleelectricfurnace.title").withStyle(TextFormatting.BLUE), ElectroTextUtils.advancement("doubleelectricfurnace.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasFurnace", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricfurnacedouble)))
				//
				.rewards(Builder.experience(50))
				//
				.parent(electricFurnace)
				//
				.save(consumer);

		advancement("tripleelectricfurnace")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricfurnacetriple), ElectroTextUtils.advancement("tripleelectricfurnace.title").withStyle(TextFormatting.RED), ElectroTextUtils.advancement("tripleelectricfurnace.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasFurnace", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricfurnacetriple)))
				//
				.rewards(Builder.experience(100))
				//
				.parent(doubleElectricFurnace)
				//
				.save(consumer);

		// ELECTRIC ARC FURNACE

		Advancement electricArcFurnace = advancement("electricarcfurnace")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricarcfurnace), ElectroTextUtils.advancement("electricarcfurnace.title").withStyle(TextFormatting.GOLD), ElectroTextUtils.advancement("electricarcfurnace.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasFurnace", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricarcfurnace)))
				//
				.rewards(Builder.experience(30))
				//
				.parent(coalGenerator)
				//
				.save(consumer);

		Advancement doubleElectricArcFurnace = advancement("doubleelectricarcfurnace")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricarcfurnacedouble), ElectroTextUtils.advancement("doubleelectricarcfurnace.title").withStyle(TextFormatting.BLUE), ElectroTextUtils.advancement("doubleelectricarcfurnace.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasFurnace", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricarcfurnacedouble)))
				//
				.rewards(Builder.experience(50))
				//
				.parent(electricArcFurnace)
				//
				.save(consumer);

		advancement("tripleelectricarcfurnace")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricarcfurnacetriple), ElectroTextUtils.advancement("tripleelectricarcfurnace.title").withStyle(TextFormatting.RED), ElectroTextUtils.advancement("tripleelectricarcfurnace.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasFurnace", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricarcfurnacetriple)))
				//
				.rewards(Builder.experience(150))
				//
				.parent(doubleElectricArcFurnace)
				//
				.save(consumer);

		// MINERAL GRINDER

		Advancement mineralGrinder = advancement("mineralgrinder")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.mineralgrinder), ElectroTextUtils.advancement("mineralgrinder.title").withStyle(TextFormatting.GOLD), ElectroTextUtils.advancement("mineralgrinder.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasGrinder", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.mineralgrinder)))
				//
				.rewards(Builder.experience(20))
				//
				.parent(coalGenerator)
				//
				.save(consumer);

		Advancement doubleMineralGrinder = advancement("doublemineralgrinder")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.mineralgrinderdouble), ElectroTextUtils.advancement("doublemineralgrinder.title").withStyle(TextFormatting.BLUE), ElectroTextUtils.advancement("doublemineralgrinder.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasGrinder", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.mineralgrinderdouble)))
				//
				.rewards(Builder.experience(50))
				//
				.parent(mineralGrinder)
				//
				.save(consumer);

		advancement("triplemineralgrinder")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.mineralgrindertriple), ElectroTextUtils.advancement("triplemineralgrinder.title").withStyle(TextFormatting.RED), ElectroTextUtils.advancement("triplemineralgrinder.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasGrinder", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.mineralgrindertriple)))
				//
				.rewards(Builder.experience(100))
				//
				.parent(doubleMineralGrinder)
				//
				.save(consumer);

		// MINERAL CRUSHER

		Advancement mineralCrusher = advancement("mineralcrusher")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.mineralcrusher), ElectroTextUtils.advancement("mineralcrusher.title").withStyle(TextFormatting.GOLD), ElectroTextUtils.advancement("mineralcrusher.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasCrusher", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.mineralcrusher)))
				//
				.rewards(Builder.experience(30))
				//
				.parent(coalGenerator)
				//
				.save(consumer);

		Advancement doubleMineralCrusher = advancement("doublemineralcrusher")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.mineralcrusherdouble), ElectroTextUtils.advancement("doublemineralcrusher.title").withStyle(TextFormatting.BLUE), ElectroTextUtils.advancement("doublemineralcrusher.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasCrusher", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.mineralcrusherdouble)))
				//
				.rewards(Builder.experience(70))
				//
				.parent(mineralCrusher)
				//
				.save(consumer);

		advancement("triplemineralcrusher")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.mineralcrushertriple), ElectroTextUtils.advancement("triplemineralcrusher.title").withStyle(TextFormatting.RED), ElectroTextUtils.advancement("triplemineralcrusher.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasCrusher", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.mineralcrushertriple)))
				//
				.rewards(Builder.experience(150))
				//
				.parent(doubleMineralCrusher)
				//
				.save(consumer);

		// MISC

		advancement("multimeter")
				//
				.display(ElectrodynamicsItems.ITEM_MULTIMETER.get(), ElectroTextUtils.advancement("multimeter.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("multimeter.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasMeter", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.ITEM_MULTIMETER.get()))
				//
				.rewards(Builder.experience(10))
				//
				.parent(basicWiring)
				//
				.save(consumer);
	}

}
