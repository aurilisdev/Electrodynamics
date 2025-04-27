package electrodynamics.datagen.server;

import java.util.function.Consumer;

import electrodynamics.Electrodynamics;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.item.subtype.SubtypeRawOre;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards.Builder;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import voltaic.datagen.utils.server.advancement.AdvancementBuilder.AdvancementBackgrounds;
import voltaic.datagen.utils.server.advancement.BaseAdvancementProvider;

public class ElectrodynamicsAdvancementProvider extends BaseAdvancementProvider {


	public ElectrodynamicsAdvancementProvider() {
		super(Electrodynamics.ID);
	}

	@Override
	public void generate(Provider registries, Consumer<Advancement> consumer, ExistingFileHelper existingFileHelper) {

		Advancement root = advancement("root")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.downgradetransformer), ElectroTextUtils.advancement("root.title").withStyle(ChatFormatting.AQUA), ElectroTextUtils.advancement("root.desc"), AdvancementBackgrounds.STONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasCraftingTable", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
				//
				.rewards(Builder.experience(10))
				//
				.save(consumer);

		// ORES

		Advancement ores = advancement("ores")
				//
				.display(ElectrodynamicsItems.ITEMS_ORE.getValue(SubtypeOre.tin), ElectroTextUtils.advancement("ores.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("ores.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, false, false, false)
				//
				.addCriterion("HasWoodenPickaxe", InventoryChangeTrigger.TriggerInstance.hasItems(Items.WOODEN_PICKAXE))
				//
				.rewards(Builder.experience(10))
				//
				.parent(root)
				//
				.save(consumer);

		advancement("raworevanadium")
				//
				.display(ElectrodynamicsItems.ITEMS_RAWORE.getValue(SubtypeRawOre.vanadinite), ElectroTextUtils.advancement("rawvanadium.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("rawvanadium.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasRawOre", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_RAWORE.getValue(SubtypeRawOre.vanadinite)))
				//
				.rewards(Builder.experience(10))
				//
				.parent(ores)
				//
				.save(consumer);

		advancement("raworeuranium")
				//
				.display(ElectrodynamicsItems.ITEMS_RAWORE.getValue(SubtypeRawOre.uranium), ElectroTextUtils.advancement("rawuranium.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("rawuranium.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasRawOre", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_RAWORE.getValue(SubtypeRawOre.uranium)))
				//
				.rewards(Builder.experience(10))
				//
				.parent(ores)
				//
				.save(consumer);

		advancement("raworechromium")
				//
				.display(ElectrodynamicsItems.ITEMS_RAWORE.getValue(SubtypeRawOre.chromium), ElectroTextUtils.advancement("rawchromium.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("rawchromium.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasRawOre", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_RAWORE.getValue(SubtypeRawOre.chromium)))
				//
				.rewards(Builder.experience(10))
				//
				.parent(ores)
				//
				.save(consumer);

		advancement("rawfluorite")
				//
				.display(ElectrodynamicsItems.ITEMS_RAWORE.getValue(SubtypeRawOre.fluorite), ElectroTextUtils.advancement("rawfluorite.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("rawfluorite.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasRawOre", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_RAWORE.getValue(SubtypeRawOre.fluorite)))
				//
				.rewards(Builder.experience(10))
				//
				.parent(ores)
				//
				.save(consumer);

		advancement("raworelead")
				//
				.display(ElectrodynamicsItems.ITEMS_RAWORE.getValue(SubtypeRawOre.lead), ElectroTextUtils.advancement("rawlead.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("rawlead.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasRawOre", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_RAWORE.getValue(SubtypeRawOre.lead)))
				//
				.rewards(Builder.experience(10))
				//
				.parent(ores)
				//
				.save(consumer);

		advancement("raworelithium")
				//
				.display(ElectrodynamicsItems.ITEMS_RAWORE.getValue(SubtypeRawOre.lepidolite), ElectroTextUtils.advancement("rawlithium.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("rawlithium.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasRawOre", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_RAWORE.getValue(SubtypeRawOre.lepidolite)))
				//
				.rewards(Builder.experience(10))
				//
				.parent(ores)
				//
				.save(consumer);

		advancement("raworesilver")
				//
				.display(ElectrodynamicsItems.ITEMS_RAWORE.getValue(SubtypeRawOre.silver), ElectroTextUtils.advancement("rawsilver.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("rawsilver.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasRawOre", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_RAWORE.getValue(SubtypeRawOre.silver)))
				//
				.rewards(Builder.experience(10))
				//
				.parent(ores)
				//
				.save(consumer);

		advancement("raworethorium")
				//
				.display(ElectrodynamicsItems.ITEMS_RAWORE.getValue(SubtypeRawOre.thorium), ElectroTextUtils.advancement("rawthorium.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("rawthorium.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasRawOre", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_RAWORE.getValue(SubtypeRawOre.thorium)))
				//
				.rewards(Builder.experience(10))
				//
				.parent(ores)
				//
				.save(consumer);

		advancement("raworetin")
				//
				.display(ElectrodynamicsItems.ITEMS_RAWORE.getValue(SubtypeRawOre.tin), ElectroTextUtils.advancement("rawtin.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("rawtin.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasRawOre", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_RAWORE.getValue(SubtypeRawOre.tin)))
				//
				.rewards(Builder.experience(10))
				//
				.parent(ores)
				//
				.save(consumer);

		advancement("raworetitanium")
				//
				.display(ElectrodynamicsItems.ITEMS_RAWORE.getValue(SubtypeRawOre.titanium), ElectroTextUtils.advancement("rawtitanium.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("rawtitanium.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasRawOre", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_RAWORE.getValue(SubtypeRawOre.titanium)))
				//
				.rewards(Builder.experience(10))
				//
				.parent(ores)
				//
				.save(consumer);

		// WIRING

		Advancement basicWiring = advancement("basicwiring")
				//
				.display(ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.copper), ElectroTextUtils.advancement("basicwiring.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("basicwiring.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasCopperWire", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.copper)))
				//
				.rewards(Builder.experience(20))
				//
				.parent(root)
				//
				.save(consumer);

		Advancement betterWiring = advancement("betterwiring")
				//
				.display(ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.silver), ElectroTextUtils.advancement("betterwiring.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("betterwiring.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasSilverWire", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.silver)))
				//
				.rewards(Builder.experience(20))
				//
				.parent(basicWiring)
				//
				.save(consumer);

		Advancement superiorWiring = advancement("superiorwiring")
				//
				.display(ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.gold), ElectroTextUtils.advancement("superiorwiring.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("superiorwiring.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasGoldWire", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.gold)))
				//
				.rewards(Builder.experience(20))
				//
				.parent(betterWiring)
				//
				.save(consumer);

		advancement("superconductivewiring")
				//
				.display(ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.superconductive), ElectroTextUtils.advancement("superconductivewiring.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("superconductivewiring.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasSuperconductiveWire", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.superconductive)))
				//
				.rewards(Builder.experience(50))
				//
				.parent(superiorWiring)
				//
				.save(consumer);

		// INSULATION

		Advancement insulation = advancement("insulation")
				//
				.display(ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.insulatedcopperblack), ElectroTextUtils.advancement("insulatedwiring.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("insulation.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasInsulation", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEM_INSULATION.get()))
				//
				.rewards(Builder.experience(20))
				//
				.parent(basicWiring)
				//
				.save(consumer);

		Advancement insulatedWiring = advancement("insulatedwiring")
				//
				.display(ElectrodynamicsItems.ITEM_INSULATION.get(), ElectroTextUtils.advancement("insulatedwiring.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("insulatedwiring.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasInsulatedCopperWire", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.insulatedcopperblack)))
				//
				.rewards(Builder.experience(20))
				//
				.parent(insulation)
				//
				.save(consumer);

		advancement("highlyinsulatedwiring")
				//
				.display(ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.highlyinsulatedcopperblack), ElectroTextUtils.advancement("highlyinsulatedwiring.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("highlyinsulatedwiring.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasHighlyInsulatedCopperWire", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.highlyinsulatedcopperblack)))
				//
				.rewards(Builder.experience(20))
				//
				.parent(insulatedWiring)
				//
				.save(consumer);

		advancement("ceramicinsulation")
				//
				.display(ElectrodynamicsItems.ITEM_CERAMICINSULATION.get(), ElectroTextUtils.advancement("ceramicinsulation.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("ceramicinsulation.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasCeramicInsulation", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEM_CERAMICINSULATION.get()))
				//
				.rewards(Builder.experience(30))
				//
				.parent(insulation)
				//
				.save(consumer);

		advancement("ceramicinsulatedwiring")
				//
				.display(ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.ceramicinsulatedcopperbrown), ElectroTextUtils.advancement("ceramicinsulatedwiring.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("ceramicinsulatedwiring.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasCeramicInsulatedCopperWire", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.ceramicinsulatedcopperbrown)))
				//
				.rewards(Builder.experience(15))
				//
				.parent(insulatedWiring)
				//
				.save(consumer);

		// WIRING DEVICES

		advancement("downgradetransformer")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.downgradetransformer), ElectroTextUtils.advancement("downgradetransformer.title").withStyle(ChatFormatting.GOLD), ElectroTextUtils.advancement("downgradetransformer.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasTransformer", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.downgradetransformer)))
				//
				.rewards(Builder.experience(25))
				//
				.parent(basicWiring)
				//
				.save(consumer);

		advancement("upgradetransformer")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.upgradetransformer), ElectroTextUtils.advancement("upgradetransformer.title").withStyle(ChatFormatting.LIGHT_PURPLE), ElectroTextUtils.advancement("upgradetransformer.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasTransformer", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.upgradetransformer)))
				//
				.rewards(Builder.experience(25))
				//
				.parent(basicWiring)
				//
				.save(consumer);

		advancement("circuitbreaker")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.circuitbreaker), ElectroTextUtils.advancement("circuitbreaker.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("circuitbreaker.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasCircuitBreaker", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.circuitbreaker)))
				//
				.rewards(Builder.experience(30))
				//
				.parent(basicWiring)
				//
				.save(consumer);

		// GENERATORS

		Advancement coalGenerator = advancement("coalgenerator")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.coalgenerator), ElectroTextUtils.advancement("coalgenerator.title").withStyle(ChatFormatting.GOLD), ElectroTextUtils.advancement("coalgenerator.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasCoalGenerator", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.coalgenerator)))
				//
				.rewards(Builder.experience(25))
				//
				.parent(root)
				//
				.save(consumer);

		advancement("thermoelectricgenerator")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.thermoelectricgenerator), ElectroTextUtils.advancement("thermoelectricgenerator.title").withStyle(ChatFormatting.RED), ElectroTextUtils.advancement("thermoelectricgenerator.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasThermoGenerator", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.thermoelectricgenerator)))
				//
				.rewards(Builder.experience(20))
				//
				.parent(coalGenerator)
				//
				.save(consumer);

		Advancement solarPanel = advancement("solarpanel")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.solarpanel), ElectroTextUtils.advancement("solarpanel.title").withStyle(ChatFormatting.BLUE), ElectroTextUtils.advancement("solarpanel.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasPanel", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.solarpanel)))
				//
				.rewards(Builder.experience(25))
				//
				.parent(coalGenerator)
				//
				.save(consumer);

		advancement("advancedsolarpanel")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.advancedsolarpanel), ElectroTextUtils.advancement("advancedsolarpanel.title").withStyle(ChatFormatting.DARK_BLUE), ElectroTextUtils.advancement("advancedsolarpanel.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasPanel", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.advancedsolarpanel)))
				//
				.rewards(Builder.experience(75))
				//
				.parent(solarPanel)
				//
				.save(consumer);

		// BATTERY BOXES

		Advancement batteryBox = advancement("batterybox")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.batterybox), ElectroTextUtils.advancement("batterybox.title").withStyle(ChatFormatting.GOLD), ElectroTextUtils.advancement("batterybox.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasBatteryBox", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.batterybox)))
				//
				.rewards(Builder.experience(30))
				//
				.parent(coalGenerator)
				//
				.save(consumer);

		Advancement lithiumBatteryBox = advancement("lithiumbatterybox")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.lithiumbatterybox), ElectroTextUtils.advancement("lithiumbatterybox.title").withStyle(ChatFormatting.BLUE), ElectroTextUtils.advancement("lithiumbatterybox.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasBatteryBox", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.lithiumbatterybox)))
				//
				.rewards(Builder.experience(70))
				//
				.parent(batteryBox)
				//
				.save(consumer);

		advancement("carbynebatterybox")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.carbynebatterybox), ElectroTextUtils.advancement("carbynebatterybox.title").withStyle(ChatFormatting.RED), ElectroTextUtils.advancement("carbynebatterybox.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasBatteryBox", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.carbynebatterybox)))
				//
				.rewards(Builder.experience(150))
				//
				.parent(lithiumBatteryBox)
				//
				.save(consumer);

		// WIRE MILLS

		Advancement wiremill = advancement("wiremill")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.wiremill), ElectroTextUtils.advancement("wiremill.title").withStyle(ChatFormatting.GOLD), ElectroTextUtils.advancement("wiremill.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasMill", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.wiremill)))
				//
				.rewards(Builder.experience(20))
				//
				.parent(coalGenerator)
				//
				.save(consumer);

		Advancement doubleWiremill = advancement("doublewiremill")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.wiremilldouble), ElectroTextUtils.advancement("doublewiremill.title").withStyle(ChatFormatting.BLUE), ElectroTextUtils.advancement("doublewiremill.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasMill", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.wiremilldouble)))
				//
				.rewards(Builder.experience(50))
				//
				.parent(wiremill)
				//
				.save(consumer);

		advancement("triplewiremill")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.wiremilltriple), ElectroTextUtils.advancement("triplewiremill.title").withStyle(ChatFormatting.RED), ElectroTextUtils.advancement("triplewiremill.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasMill", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.wiremilltriple)))
				//
				.rewards(Builder.experience(100))
				//
				.parent(doubleWiremill)
				//
				.save(consumer);

		// ELECTRIC FURNACE

		Advancement electricFurnace = advancement("electricfurnace")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricfurnace), ElectroTextUtils.advancement("electricfurnace.title").withStyle(ChatFormatting.GOLD), ElectroTextUtils.advancement("electricfurnace.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasFurnace", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricfurnace)))
				//
				.rewards(Builder.experience(30))
				//
				.parent(coalGenerator)
				//
				.save(consumer);

		Advancement doubleElectricFurnace = advancement("doubleelectricfurnace")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricfurnacedouble), ElectroTextUtils.advancement("doubleelectricfurnace.title").withStyle(ChatFormatting.BLUE), ElectroTextUtils.advancement("doubleelectricfurnace.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasFurnace", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricfurnacedouble)))
				//
				.rewards(Builder.experience(50))
				//
				.parent(electricFurnace)
				//
				.save(consumer);

		advancement("tripleelectricfurnace")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricfurnacetriple), ElectroTextUtils.advancement("tripleelectricfurnace.title").withStyle(ChatFormatting.RED), ElectroTextUtils.advancement("tripleelectricfurnace.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasFurnace", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricfurnacetriple)))
				//
				.rewards(Builder.experience(100))
				//
				.parent(doubleElectricFurnace)
				//
				.save(consumer);

		// ELECTRIC ARC FURNACE

		Advancement electricArcFurnace = advancement("electricarcfurnace")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricarcfurnace), ElectroTextUtils.advancement("electricarcfurnace.title").withStyle(ChatFormatting.GOLD), ElectroTextUtils.advancement("electricarcfurnace.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasFurnace", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricarcfurnace)))
				//
				.rewards(Builder.experience(30))
				//
				.parent(coalGenerator)
				//
				.save(consumer);

		Advancement doubleElectricArcFurnace = advancement("doubleelectricarcfurnace")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricarcfurnacedouble), ElectroTextUtils.advancement("doubleelectricarcfurnace.title").withStyle(ChatFormatting.BLUE), ElectroTextUtils.advancement("doubleelectricarcfurnace.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasFurnace", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricarcfurnacedouble)))
				//
				.rewards(Builder.experience(50))
				//
				.parent(electricArcFurnace)
				//
				.save(consumer);

		advancement("tripleelectricarcfurnace")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricarcfurnacetriple), ElectroTextUtils.advancement("tripleelectricarcfurnace.title").withStyle(ChatFormatting.RED), ElectroTextUtils.advancement("tripleelectricarcfurnace.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasFurnace", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricarcfurnacetriple)))
				//
				.rewards(Builder.experience(150))
				//
				.parent(doubleElectricArcFurnace)
				//
				.save(consumer);

		// MINERAL GRINDER

		Advancement mineralGrinder = advancement("mineralgrinder")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.mineralgrinder), ElectroTextUtils.advancement("mineralgrinder.title").withStyle(ChatFormatting.GOLD), ElectroTextUtils.advancement("mineralgrinder.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasGrinder", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.mineralgrinder)))
				//
				.rewards(Builder.experience(20))
				//
				.parent(coalGenerator)
				//
				.save(consumer);

		Advancement doubleMineralGrinder = advancement("doublemineralgrinder")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.mineralgrinderdouble), ElectroTextUtils.advancement("doublemineralgrinder.title").withStyle(ChatFormatting.BLUE), ElectroTextUtils.advancement("doublemineralgrinder.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasGrinder", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.mineralgrinderdouble)))
				//
				.rewards(Builder.experience(50))
				//
				.parent(mineralGrinder)
				//
				.save(consumer);

		advancement("triplemineralgrinder")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.mineralgrindertriple), ElectroTextUtils.advancement("triplemineralgrinder.title").withStyle(ChatFormatting.RED), ElectroTextUtils.advancement("triplemineralgrinder.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasGrinder", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.mineralgrindertriple)))
				//
				.rewards(Builder.experience(100))
				//
				.parent(doubleMineralGrinder)
				//
				.save(consumer);

		// MINERAL CRUSHER

		Advancement mineralCrusher = advancement("mineralcrusher")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.mineralcrusher), ElectroTextUtils.advancement("mineralcrusher.title").withStyle(ChatFormatting.GOLD), ElectroTextUtils.advancement("mineralcrusher.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasCrusher", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.mineralcrusher)))
				//
				.rewards(Builder.experience(30))
				//
				.parent(coalGenerator)
				//
				.save(consumer);

		Advancement doubleMineralCrusher = advancement("doublemineralcrusher")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.mineralcrusherdouble), ElectroTextUtils.advancement("doublemineralcrusher.title").withStyle(ChatFormatting.BLUE), ElectroTextUtils.advancement("doublemineralcrusher.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasCrusher", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.mineralcrusherdouble)))
				//
				.rewards(Builder.experience(70))
				//
				.parent(mineralCrusher)
				//
				.save(consumer);

		advancement("triplemineralcrusher")
				//
				.display(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.mineralcrushertriple), ElectroTextUtils.advancement("triplemineralcrusher.title").withStyle(ChatFormatting.RED), ElectroTextUtils.advancement("triplemineralcrusher.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasCrusher", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.mineralcrushertriple)))
				//
				.rewards(Builder.experience(150))
				//
				.parent(doubleMineralCrusher)
				//
				.save(consumer);

		// MISC

		advancement("multimeter")
				//
				.display(ElectrodynamicsItems.ITEM_MULTIMETER.get(), ElectroTextUtils.advancement("multimeter.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("multimeter.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasMeter", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.ITEM_MULTIMETER.get()))
				//
				.rewards(Builder.experience(10))
				//
				.parent(basicWiring)
				//
				.save(consumer);
	}

}
