package electrodynamics.datagen.server;

import static electrodynamics.datagen.utils.AdvancementBuilder.create;

import java.util.function.Consumer;

import electrodynamics.api.References;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.condition.ConfigCondition;
import electrodynamics.common.item.subtype.SubtypeRawOre;
import electrodynamics.datagen.utils.AdvancementBuilder;
import electrodynamics.datagen.utils.AdvancementBuilder.AdvancementBackgrounds;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards.Builder;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider.AdvancementGenerator;

public class ElectrodynamicsAdvancementProvider implements AdvancementGenerator {

	public final String modID;

	public ElectrodynamicsAdvancementProvider(String modID) {
		this.modID = modID;
	}

	public ElectrodynamicsAdvancementProvider() {
		this(References.ID);
	}

	public AdvancementBuilder advancement(String name) {
		return create(new ResourceLocation(modID, name));
	}

	@Override
	public void generate(Provider registries, Consumer<Advancement> consumer, ExistingFileHelper existingFileHelper) {
		advancement("dispenseguidebook")
				//
				.addCriterion("SpawnIn", new PlayerTrigger.TriggerInstance(CriteriaTriggers.TICK.getId(), ContextAwarePredicate.ANY))
				//
				.rewards(Builder.loot(new ResourceLocation("advancement_reward/electroguidebook")))
				//
				.condition(new ConfigCondition())
				//
				.save(consumer);

		Advancement root = advancement("root")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.downgradetransformer), ElectroTextUtils.advancement("root.title").withStyle(ChatFormatting.AQUA), ElectroTextUtils.advancement("root.desc"), AdvancementBackgrounds.STONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasCraftingTable", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
				//
				.rewards(Builder.experience(10))
				//
				.save(consumer);

		// ORES

		Advancement ores = advancement("ores")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeOre.tin), ElectroTextUtils.advancement("ores.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("ores.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, false, false, false)
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
				.display(ElectrodynamicsItems.getItem(SubtypeRawOre.vanadinite), ElectroTextUtils.advancement("rawvanadium.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("rawvanadium.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasRawOre", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeRawOre.vanadinite)))
				//
				.rewards(Builder.experience(10))
				//
				.parent(ores)
				//
				.save(consumer);

		advancement("raworeuranium")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeRawOre.uranium), ElectroTextUtils.advancement("rawuranium.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("rawuranium.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasRawOre", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeRawOre.uranium)))
				//
				.rewards(Builder.experience(10))
				//
				.parent(ores)
				//
				.save(consumer);

		advancement("raworechromium")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeRawOre.chromium), ElectroTextUtils.advancement("rawchromium.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("rawchromium.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasRawOre", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeRawOre.chromium)))
				//
				.rewards(Builder.experience(10))
				//
				.parent(ores)
				//
				.save(consumer);

		advancement("rawfluorite")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeRawOre.fluorite), ElectroTextUtils.advancement("rawfluorite.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("rawfluorite.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasRawOre", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeRawOre.fluorite)))
				//
				.rewards(Builder.experience(10))
				//
				.parent(ores)
				//
				.save(consumer);

		advancement("raworelead")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeRawOre.lead), ElectroTextUtils.advancement("rawlead.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("rawlead.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasRawOre", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeRawOre.lead)))
				//
				.rewards(Builder.experience(10))
				//
				.parent(ores)
				//
				.save(consumer);

		advancement("raworelithium")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeRawOre.lepidolite), ElectroTextUtils.advancement("rawlithium.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("rawlithium.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasRawOre", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeRawOre.lepidolite)))
				//
				.rewards(Builder.experience(10))
				//
				.parent(ores)
				//
				.save(consumer);

		advancement("raworesilver")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeRawOre.silver), ElectroTextUtils.advancement("rawsilver.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("rawsilver.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasRawOre", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeRawOre.silver)))
				//
				.rewards(Builder.experience(10))
				//
				.parent(ores)
				//
				.save(consumer);

		advancement("raworethorium")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeRawOre.thorium), ElectroTextUtils.advancement("rawthorium.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("rawthorium.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasRawOre", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeRawOre.thorium)))
				//
				.rewards(Builder.experience(10))
				//
				.parent(ores)
				//
				.save(consumer);

		advancement("raworetin")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeRawOre.tin), ElectroTextUtils.advancement("rawtin.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("rawtin.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasRawOre", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeRawOre.tin)))
				//
				.rewards(Builder.experience(10))
				//
				.parent(ores)
				//
				.save(consumer);

		advancement("raworetitanium")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeRawOre.titanium), ElectroTextUtils.advancement("rawtitanium.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("rawtitanium.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasRawOre", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeRawOre.titanium)))
				//
				.rewards(Builder.experience(10))
				//
				.parent(ores)
				//
				.save(consumer);

		// WIRING

		Advancement basicWiring = advancement("basicwiring")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeWire.copper), ElectroTextUtils.advancement("basicwiring.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("basicwiring.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasCopperWire", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeWire.copper)))
				//
				.rewards(Builder.experience(20))
				//
				.parent(root)
				//
				.save(consumer);

		Advancement betterWiring = advancement("betterwiring")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeWire.silver), ElectroTextUtils.advancement("betterwiring.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("betterwiring.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasSilverWire", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeWire.silver)))
				//
				.rewards(Builder.experience(20))
				//
				.parent(basicWiring)
				//
				.save(consumer);

		Advancement superiorWiring = advancement("superiorwiring")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeWire.gold), ElectroTextUtils.advancement("superiorwiring.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("superiorwiring.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasGoldWire", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeWire.gold)))
				//
				.rewards(Builder.experience(20))
				//
				.parent(betterWiring)
				//
				.save(consumer);

		advancement("superconductivewiring")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeWire.superconductive), ElectroTextUtils.advancement("superconductivewiring.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("superconductivewiring.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasSuperconductiveWire", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeWire.superconductive)))
				//
				.rewards(Builder.experience(50))
				//
				.parent(superiorWiring)
				//
				.save(consumer);

		// INSULATION

		Advancement insulation = advancement("insulation")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeWire.insulatedcopperblack), ElectroTextUtils.advancement("insulatedwiring.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("insulation.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
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
				.addCriterion("HasInsulatedCopperWire", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeWire.insulatedcopperblack)))
				//
				.rewards(Builder.experience(20))
				//
				.parent(insulation)
				//
				.save(consumer);

		advancement("highlyinsulatedwiring")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeWire.highlyinsulatedcopperblack), ElectroTextUtils.advancement("highlyinsulatedwiring.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("highlyinsulatedwiring.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasHighlyInsulatedCopperWire", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeWire.highlyinsulatedcopperblack)))
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
				.display(ElectrodynamicsItems.getItem(SubtypeWire.ceramicinsulatedcopperbrown), ElectroTextUtils.advancement("ceramicinsulatedwiring.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("ceramicinsulatedwiring.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasCeramicInsulatedCopperWire", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeWire.ceramicinsulatedcopperbrown)))
				//
				.rewards(Builder.experience(15))
				//
				.parent(insulatedWiring)
				//
				.save(consumer);

		// WIRING DEVICES

		advancement("downgradetransformer")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.downgradetransformer), ElectroTextUtils.advancement("downgradetransformer.title").withStyle(ChatFormatting.GOLD), ElectroTextUtils.advancement("downgradetransformer.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasTransformer", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.downgradetransformer)))
				//
				.rewards(Builder.experience(25))
				//
				.parent(basicWiring)
				//
				.save(consumer);

		advancement("upgradetransformer")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.upgradetransformer), ElectroTextUtils.advancement("upgradetransformer.title").withStyle(ChatFormatting.LIGHT_PURPLE), ElectroTextUtils.advancement("upgradetransformer.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasTransformer", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.upgradetransformer)))
				//
				.rewards(Builder.experience(25))
				//
				.parent(basicWiring)
				//
				.save(consumer);

		advancement("circuitbreaker")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.circuitbreaker), ElectroTextUtils.advancement("circuitbreaker.title").withStyle(ChatFormatting.GRAY), ElectroTextUtils.advancement("circuitbreaker.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasCircuitBreaker", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.circuitbreaker)))
				//
				.rewards(Builder.experience(30))
				//
				.parent(basicWiring)
				//
				.save(consumer);

		// GENERATORS

		Advancement coalGenerator = advancement("coalgenerator")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.coalgenerator), ElectroTextUtils.advancement("coalgenerator.title").withStyle(ChatFormatting.GOLD), ElectroTextUtils.advancement("coalgenerator.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasCoalGenerator", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.coalgenerator)))
				//
				.rewards(Builder.experience(25))
				//
				.parent(root)
				//
				.save(consumer);

		advancement("thermoelectricgenerator")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.thermoelectricgenerator), ElectroTextUtils.advancement("thermoelectricgenerator.title").withStyle(ChatFormatting.RED), ElectroTextUtils.advancement("thermoelectricgenerator.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasThermoGenerator", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.thermoelectricgenerator)))
				//
				.rewards(Builder.experience(20))
				//
				.parent(coalGenerator)
				//
				.save(consumer);

		Advancement solarPanel = advancement("solarpanel")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.solarpanel), ElectroTextUtils.advancement("solarpanel.title").withStyle(ChatFormatting.BLUE), ElectroTextUtils.advancement("solarpanel.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasPanel", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.solarpanel)))
				//
				.rewards(Builder.experience(25))
				//
				.parent(coalGenerator)
				//
				.save(consumer);

		advancement("advancedsolarpanel")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.advancedsolarpanel), ElectroTextUtils.advancement("advancedsolarpanel.title").withStyle(ChatFormatting.DARK_BLUE), ElectroTextUtils.advancement("advancedsolarpanel.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasPanel", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.advancedsolarpanel)))
				//
				.rewards(Builder.experience(75))
				//
				.parent(solarPanel)
				//
				.save(consumer);

		// BATTERY BOXES

		Advancement batteryBox = advancement("batterybox")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.batterybox), ElectroTextUtils.advancement("batterybox.title").withStyle(ChatFormatting.GOLD), ElectroTextUtils.advancement("batterybox.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasBatteryBox", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.batterybox)))
				//
				.rewards(Builder.experience(30))
				//
				.parent(coalGenerator)
				//
				.save(consumer);

		Advancement lithiumBatteryBox = advancement("lithiumbatterybox")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.lithiumbatterybox), ElectroTextUtils.advancement("lithiumbatterybox.title").withStyle(ChatFormatting.BLUE), ElectroTextUtils.advancement("lithiumbatterybox.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasBatteryBox", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.lithiumbatterybox)))
				//
				.rewards(Builder.experience(70))
				//
				.parent(batteryBox)
				//
				.save(consumer);

		advancement("carbynebatterybox")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.carbynebatterybox), ElectroTextUtils.advancement("carbynebatterybox.title").withStyle(ChatFormatting.RED), ElectroTextUtils.advancement("carbynebatterybox.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasBatteryBox", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.carbynebatterybox)))
				//
				.rewards(Builder.experience(150))
				//
				.parent(lithiumBatteryBox)
				//
				.save(consumer);

		// WIRE MILLS

		Advancement wiremill = advancement("wiremill")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.wiremill), ElectroTextUtils.advancement("wiremill.title").withStyle(ChatFormatting.GOLD), ElectroTextUtils.advancement("wiremill.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasMill", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.wiremill)))
				//
				.rewards(Builder.experience(20))
				//
				.parent(coalGenerator)
				//
				.save(consumer);

		Advancement doubleWiremill = advancement("doublewiremill")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.wiremilldouble), ElectroTextUtils.advancement("doublewiremill.title").withStyle(ChatFormatting.BLUE), ElectroTextUtils.advancement("doublewiremill.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasMill", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.wiremilldouble)))
				//
				.rewards(Builder.experience(50))
				//
				.parent(wiremill)
				//
				.save(consumer);

		advancement("triplewiremill")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.wiremilltriple), ElectroTextUtils.advancement("triplewiremill.title").withStyle(ChatFormatting.RED), ElectroTextUtils.advancement("triplewiremill.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasMill", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.wiremilltriple)))
				//
				.rewards(Builder.experience(100))
				//
				.parent(doubleWiremill)
				//
				.save(consumer);

		// ELECTRIC FURNACE

		Advancement electricFurnace = advancement("electricfurnace")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.electricfurnace), ElectroTextUtils.advancement("electricfurnace.title").withStyle(ChatFormatting.GOLD), ElectroTextUtils.advancement("electricfurnace.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasFurnace", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.electricfurnace)))
				//
				.rewards(Builder.experience(30))
				//
				.parent(coalGenerator)
				//
				.save(consumer);

		Advancement doubleElectricFurnace = advancement("doubleelectricfurnace")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.electricfurnacedouble), ElectroTextUtils.advancement("doubleelectricfurnace.title").withStyle(ChatFormatting.BLUE), ElectroTextUtils.advancement("doubleelectricfurnace.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasFurnace", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.electricfurnacedouble)))
				//
				.rewards(Builder.experience(50))
				//
				.parent(electricFurnace)
				//
				.save(consumer);

		advancement("tripleelectricfurnace")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.electricfurnacetriple), ElectroTextUtils.advancement("tripleelectricfurnace.title").withStyle(ChatFormatting.RED), ElectroTextUtils.advancement("tripleelectricfurnace.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasFurnace", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.electricfurnacetriple)))
				//
				.rewards(Builder.experience(100))
				//
				.parent(doubleElectricFurnace)
				//
				.save(consumer);

		// ELECTRIC ARC FURNACE

		Advancement electricArcFurnace = advancement("electricarcfurnace")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.electricarcfurnace), ElectroTextUtils.advancement("electricarcfurnace.title").withStyle(ChatFormatting.GOLD), ElectroTextUtils.advancement("electricarcfurnace.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasFurnace", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.electricarcfurnace)))
				//
				.rewards(Builder.experience(30))
				//
				.parent(coalGenerator)
				//
				.save(consumer);

		Advancement doubleElectricArcFurnace = advancement("doubleelectricarcfurnace")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.electricarcfurnacedouble), ElectroTextUtils.advancement("doubleelectricarcfurnace.title").withStyle(ChatFormatting.BLUE), ElectroTextUtils.advancement("doubleelectricarcfurnace.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasFurnace", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.electricarcfurnacedouble)))
				//
				.rewards(Builder.experience(50))
				//
				.parent(electricArcFurnace)
				//
				.save(consumer);

		advancement("tripleelectricarcfurnace")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.electricarcfurnacetriple), ElectroTextUtils.advancement("tripleelectricarcfurnace.title").withStyle(ChatFormatting.RED), ElectroTextUtils.advancement("tripleelectricarcfurnace.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasFurnace", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.electricarcfurnacetriple)))
				//
				.rewards(Builder.experience(150))
				//
				.parent(doubleElectricArcFurnace)
				//
				.save(consumer);

		// MINERAL GRINDER

		Advancement mineralGrinder = advancement("mineralgrinder")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.mineralgrinder), ElectroTextUtils.advancement("mineralgrinder.title").withStyle(ChatFormatting.GOLD), ElectroTextUtils.advancement("mineralgrinder.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasGrinder", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.mineralgrinder)))
				//
				.rewards(Builder.experience(20))
				//
				.parent(coalGenerator)
				//
				.save(consumer);

		Advancement doubleMineralGrinder = advancement("doublemineralgrinder")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.mineralgrinderdouble), ElectroTextUtils.advancement("doublemineralgrinder.title").withStyle(ChatFormatting.BLUE), ElectroTextUtils.advancement("doublemineralgrinder.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasGrinder", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.mineralgrinderdouble)))
				//
				.rewards(Builder.experience(50))
				//
				.parent(mineralGrinder)
				//
				.save(consumer);

		advancement("triplemineralgrinder")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.mineralgrindertriple), ElectroTextUtils.advancement("triplemineralgrinder.title").withStyle(ChatFormatting.RED), ElectroTextUtils.advancement("triplemineralgrinder.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasGrinder", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.mineralgrindertriple)))
				//
				.rewards(Builder.experience(100))
				//
				.parent(doubleMineralGrinder)
				//
				.save(consumer);

		// MINERAL CRUSHER

		Advancement mineralCrusher = advancement("mineralcrusher")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.mineralcrusher), ElectroTextUtils.advancement("mineralcrusher.title").withStyle(ChatFormatting.GOLD), ElectroTextUtils.advancement("mineralcrusher.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasCrusher", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.mineralcrusher)))
				//
				.rewards(Builder.experience(30))
				//
				.parent(coalGenerator)
				//
				.save(consumer);

		Advancement doubleMineralCrusher = advancement("doublemineralcrusher")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.mineralcrusherdouble), ElectroTextUtils.advancement("doublemineralcrusher.title").withStyle(ChatFormatting.BLUE), ElectroTextUtils.advancement("doublemineralcrusher.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasCrusher", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.mineralcrusherdouble)))
				//
				.rewards(Builder.experience(70))
				//
				.parent(mineralCrusher)
				//
				.save(consumer);

		advancement("triplemineralcrusher")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.mineralcrushertriple), ElectroTextUtils.advancement("triplemineralcrusher.title").withStyle(ChatFormatting.RED), ElectroTextUtils.advancement("triplemineralcrusher.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasCrusher", InventoryChangeTrigger.TriggerInstance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.mineralcrushertriple)))
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
