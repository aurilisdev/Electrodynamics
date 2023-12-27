package electrodynamics.datagen.server;

import static electrodynamics.datagen.utils.AdvancementBuilder.create;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import electrodynamics.Electrodynamics;
import electrodynamics.api.References;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.condition.ConfigCondition;
import electrodynamics.datagen.utils.AdvancementBuilder;
import electrodynamics.datagen.utils.AdvancementBuilder.AdvancementBackgrounds;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.AdvancementRewards.Builder;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.TickTrigger;
import net.minecraft.command.FunctionObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class ElectrodynamicsAdvancementProvider implements IDataProvider {

	private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
	
	public final String modID;
	public final DataGenerator generator;

	public ElectrodynamicsAdvancementProvider(DataGenerator generatorIn, String modID) {
		generator = generatorIn;
		this.modID = modID;
	}

	public ElectrodynamicsAdvancementProvider(DataGenerator generatorIn) {
		this(generatorIn, References.ID);
	}

	@Override
	public void run(DirectoryCache cache) throws IOException {
		Set<ResourceLocation> registeredAdvancements = Sets.newHashSet();
		Path path = generator.getOutputFolder();
		Consumer<AdvancementBuilder> consumer = advancementBuilder -> {
			if (!registeredAdvancements.add(advancementBuilder.id)) {
				throw new IllegalStateException("Duplicate advancement " + advancementBuilder.id);
			}
			Path filePath = path.resolve("data/" + advancementBuilder.id.getNamespace() + "/advancements/" + advancementBuilder.id.getPath() + ".json");

			try {
				IDataProvider.save(GSON, cache, advancementBuilder.serializeToJson(), filePath);
			} catch (IOException ioexception) {
				Electrodynamics.LOGGER.error("Couldn't save advancement {}", filePath, ioexception);
			}
		};

		registerAdvancements(consumer);

	}

	@Override
	public String getName() {
		return "Electrodynamics Advancement Provider";
	}

	/**
	 * Override this to do mod-specific advancements
	 * 
	 * @param consumer
	 */
	public void registerAdvancements(Consumer<AdvancementBuilder> consumer) {

		advancement("dispenseguidebook")
				//
				.addCriterion("SpawnIn", new TickTrigger.Instance(EntityPredicate.AndPredicate.ANY))
				//
				.rewards(new AdvancementRewards(0, new ResourceLocation[] { new ResourceLocation("advancement_reward/electroguidebook") }, new ResourceLocation[0], FunctionObject.CacheableFunction.NONE))
				//
				.condition(new ConfigCondition())
				//
				.save(consumer);

		Advancement root = advancement("root")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.downgradetransformer), ElectroTextUtils.advancement("root.title").withStyle(TextFormatting.AQUA), ElectroTextUtils.advancement("root.desc"), AdvancementBackgrounds.STONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasCraftingTable", InventoryChangeTrigger.Instance.hasItems(Items.CRAFTING_TABLE))
				//
				.rewards(Builder.experience(10))
				//
				.save(consumer);

		// ORES

		Advancement ores = advancement("ores")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeOre.tin), ElectroTextUtils.advancement("ores.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("ores.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, false, false, false)
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
				.display(ElectrodynamicsItems.getItem(SubtypeOre.vanadinite), ElectroTextUtils.advancement("vanadiumore.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("vanadiumore.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasRawOre", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.getItem(SubtypeOre.vanadinite)))
				//
				.rewards(Builder.experience(10))
				//
				.parent(ores)
				//
				.save(consumer);

		advancement("raworeuranium")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeOre.uraninite), ElectroTextUtils.advancement("uraniumore.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("uraniumore.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasRawOre", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.getItem(SubtypeOre.uraninite)))
				//
				.rewards(Builder.experience(10))
				//
				.parent(ores)
				//
				.save(consumer);

		advancement("raworechromium")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeOre.chromite), ElectroTextUtils.advancement("chromiumore.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("chromiumore.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasRawOre", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.getItem(SubtypeOre.chromite)))
				//
				.rewards(Builder.experience(10))
				//
				.parent(ores)
				//
				.save(consumer);

		advancement("rawfluorite")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeOre.fluorite), ElectroTextUtils.advancement("fluoriteore.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("fluoriteore.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasRawOre", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.getItem(SubtypeOre.fluorite)))
				//
				.rewards(Builder.experience(10))
				//
				.parent(ores)
				//
				.save(consumer);

		advancement("raworelead")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeOre.lead), ElectroTextUtils.advancement("leadore.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("leadore.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasRawOre", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.getItem(SubtypeOre.lead)))
				//
				.rewards(Builder.experience(10))
				//
				.parent(ores)
				//
				.save(consumer);

		advancement("raworelithium")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeOre.lepidolite), ElectroTextUtils.advancement("lithiumore.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("lithiumore.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasRawOre", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.getItem(SubtypeOre.lepidolite)))
				//
				.rewards(Builder.experience(10))
				//
				.parent(ores)
				//
				.save(consumer);

		advancement("raworesilver")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeOre.silver), ElectroTextUtils.advancement("silverore.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("silverore.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasRawOre", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.getItem(SubtypeOre.silver)))
				//
				.rewards(Builder.experience(10))
				//
				.parent(ores)
				//
				.save(consumer);

		advancement("raworethorium")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeOre.thorianite), ElectroTextUtils.advancement("thoriumore.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("thoriumore.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasRawOre", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.getItem(SubtypeOre.thorianite)))
				//
				.rewards(Builder.experience(10))
				//
				.parent(ores)
				//
				.save(consumer);

		advancement("raworetin")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeOre.tin), ElectroTextUtils.advancement("tinore.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("tinore.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasRawOre", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.getItem(SubtypeOre.tin)))
				//
				.rewards(Builder.experience(10))
				//
				.parent(ores)
				//
				.save(consumer);

		advancement("raworetitanium")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeOre.rutile), ElectroTextUtils.advancement("titaniumore.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("titaniumore.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasRawOre", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.getItem(SubtypeOre.rutile)))
				//
				.rewards(Builder.experience(10))
				//
				.parent(ores)
				//
				.save(consumer);

		// WIRING

		Advancement basicWiring = advancement("basicwiring")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeWire.copper), ElectroTextUtils.advancement("basicwiring.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("basicwiring.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasCopperWire", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.getItem(SubtypeWire.copper)))
				//
				.rewards(Builder.experience(20))
				//
				.parent(root)
				//
				.save(consumer);

		Advancement betterWiring = advancement("betterwiring")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeWire.silver), ElectroTextUtils.advancement("betterwiring.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("betterwiring.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasSilverWire", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.getItem(SubtypeWire.silver)))
				//
				.rewards(Builder.experience(20))
				//
				.parent(basicWiring)
				//
				.save(consumer);

		Advancement superiorWiring = advancement("superiorwiring")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeWire.gold), ElectroTextUtils.advancement("superiorwiring.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("superiorwiring.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasGoldWire", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.getItem(SubtypeWire.gold)))
				//
				.rewards(Builder.experience(20))
				//
				.parent(betterWiring)
				//
				.save(consumer);

		advancement("superconductivewiring")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeWire.superconductive), ElectroTextUtils.advancement("superconductivewiring.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("superconductivewiring.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasSuperconductiveWire", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.getItem(SubtypeWire.superconductive)))
				//
				.rewards(Builder.experience(50))
				//
				.parent(superiorWiring)
				//
				.save(consumer);

		// INSULATION

		Advancement insulation = advancement("insulation")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeWire.insulatedcopper), ElectroTextUtils.advancement("insulatedwiring.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("insulation.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
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
				.addCriterion("HasInsulatedCopperWire", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.getItem(SubtypeWire.insulatedcopper)))
				//
				.rewards(Builder.experience(20))
				//
				.parent(insulation)
				//
				.save(consumer);

		advancement("highlyinsulatedwiring")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeWire.highlyinsulatedcopper), ElectroTextUtils.advancement("highlyinsulatedwiring.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("highlyinsulatedwiring.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasHighlyInsulatedCopperWire", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.getItem(SubtypeWire.highlyinsulatedcopper)))
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
				.display(ElectrodynamicsItems.getItem(SubtypeWire.ceramicinsulatedcopper), ElectroTextUtils.advancement("ceramicinsulatedwiring.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("ceramicinsulatedwiring.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasCeramicInsulatedCopperWire", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.getItem(SubtypeWire.ceramicinsulatedcopper)))
				//
				.rewards(Builder.experience(15))
				//
				.parent(insulatedWiring)
				//
				.save(consumer);

		// WIRING DEVICES

		advancement("downgradetransformer")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.downgradetransformer), ElectroTextUtils.advancement("downgradetransformer.title").withStyle(TextFormatting.GOLD), ElectroTextUtils.advancement("downgradetransformer.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasTransformer", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.downgradetransformer)))
				//
				.rewards(Builder.experience(25))
				//
				.parent(basicWiring)
				//
				.save(consumer);

		advancement("upgradetransformer")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.upgradetransformer), ElectroTextUtils.advancement("upgradetransformer.title").withStyle(TextFormatting.LIGHT_PURPLE), ElectroTextUtils.advancement("upgradetransformer.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasTransformer", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.upgradetransformer)))
				//
				.rewards(Builder.experience(25))
				//
				.parent(basicWiring)
				//
				.save(consumer);

		advancement("circuitbreaker")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.circuitbreaker), ElectroTextUtils.advancement("circuitbreaker.title").withStyle(TextFormatting.GRAY), ElectroTextUtils.advancement("circuitbreaker.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasCircuitBreaker", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.circuitbreaker)))
				//
				.rewards(Builder.experience(30))
				//
				.parent(basicWiring)
				//
				.save(consumer);

		// GENERATORS

		Advancement coalGenerator = advancement("coalgenerator")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.coalgenerator), ElectroTextUtils.advancement("coalgenerator.title").withStyle(TextFormatting.GOLD), ElectroTextUtils.advancement("coalgenerator.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasCoalGenerator", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.coalgenerator)))
				//
				.rewards(Builder.experience(25))
				//
				.parent(root)
				//
				.save(consumer);

		advancement("thermoelectricgenerator")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.thermoelectricgenerator), ElectroTextUtils.advancement("thermoelectricgenerator.title").withStyle(TextFormatting.RED), ElectroTextUtils.advancement("thermoelectricgenerator.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasThermoGenerator", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.thermoelectricgenerator)))
				//
				.rewards(Builder.experience(20))
				//
				.parent(coalGenerator)
				//
				.save(consumer);

		Advancement solarPanel = advancement("solarpanel")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.solarpanel), ElectroTextUtils.advancement("solarpanel.title").withStyle(TextFormatting.BLUE), ElectroTextUtils.advancement("solarpanel.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasPanel", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.solarpanel)))
				//
				.rewards(Builder.experience(25))
				//
				.parent(coalGenerator)
				//
				.save(consumer);

		advancement("advancedsolarpanel")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.advancedsolarpanel), ElectroTextUtils.advancement("advancedsolarpanel.title").withStyle(TextFormatting.DARK_BLUE), ElectroTextUtils.advancement("advancedsolarpanel.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasPanel", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.advancedsolarpanel)))
				//
				.rewards(Builder.experience(75))
				//
				.parent(solarPanel)
				//
				.save(consumer);

		// BATTERY BOXES

		Advancement batteryBox = advancement("batterybox")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.batterybox), ElectroTextUtils.advancement("batterybox.title").withStyle(TextFormatting.GOLD), ElectroTextUtils.advancement("batterybox.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasBatteryBox", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.batterybox)))
				//
				.rewards(Builder.experience(30))
				//
				.parent(coalGenerator)
				//
				.save(consumer);

		advancement("lithiumbatterybox")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.lithiumbatterybox), ElectroTextUtils.advancement("lithiumbatterybox.title").withStyle(TextFormatting.BLUE), ElectroTextUtils.advancement("lithiumbatterybox.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasBatteryBox", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.lithiumbatterybox)))
				//
				.rewards(Builder.experience(70))
				//
				.parent(batteryBox)
				//
				.save(consumer);

		// WIRE MILLS

		Advancement wiremill = advancement("wiremill")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.wiremill), ElectroTextUtils.advancement("wiremill.title").withStyle(TextFormatting.GOLD), ElectroTextUtils.advancement("wiremill.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasMill", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.wiremill)))
				//
				.rewards(Builder.experience(20))
				//
				.parent(coalGenerator)
				//
				.save(consumer);

		Advancement doubleWiremill = advancement("doublewiremill")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.wiremilldouble), ElectroTextUtils.advancement("doublewiremill.title").withStyle(TextFormatting.BLUE), ElectroTextUtils.advancement("doublewiremill.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasMill", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.wiremilldouble)))
				//
				.rewards(Builder.experience(50))
				//
				.parent(wiremill)
				//
				.save(consumer);

		advancement("triplewiremill")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.wiremilltriple), ElectroTextUtils.advancement("triplewiremill.title").withStyle(TextFormatting.RED), ElectroTextUtils.advancement("triplewiremill.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasMill", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.wiremilltriple)))
				//
				.rewards(Builder.experience(100))
				//
				.parent(doubleWiremill)
				//
				.save(consumer);

		// ELECTRIC FURNACE

		Advancement electricFurnace = advancement("electricfurnace")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.electricfurnace), ElectroTextUtils.advancement("electricfurnace.title").withStyle(TextFormatting.GOLD), ElectroTextUtils.advancement("electricfurnace.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasFurnace", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.electricfurnace)))
				//
				.rewards(Builder.experience(30))
				//
				.parent(coalGenerator)
				//
				.save(consumer);

		Advancement doubleElectricFurnace = advancement("doubleelectricfurnace")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.electricfurnacedouble), ElectroTextUtils.advancement("doubleelectricfurnace.title").withStyle(TextFormatting.BLUE), ElectroTextUtils.advancement("doubleelectricfurnace.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasFurnace", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.electricfurnacedouble)))
				//
				.rewards(Builder.experience(50))
				//
				.parent(electricFurnace)
				//
				.save(consumer);

		advancement("tripleelectricfurnace")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.electricfurnacetriple), ElectroTextUtils.advancement("tripleelectricfurnace.title").withStyle(TextFormatting.RED), ElectroTextUtils.advancement("tripleelectricfurnace.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasFurnace", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.electricfurnacetriple)))
				//
				.rewards(Builder.experience(100))
				//
				.parent(doubleElectricFurnace)
				//
				.save(consumer);

		// MINERAL GRINDER

		Advancement mineralGrinder = advancement("mineralgrinder")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.mineralgrinder), ElectroTextUtils.advancement("mineralgrinder.title").withStyle(TextFormatting.GOLD), ElectroTextUtils.advancement("mineralgrinder.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasGrinder", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.mineralgrinder)))
				//
				.rewards(Builder.experience(20))
				//
				.parent(coalGenerator)
				//
				.save(consumer);

		Advancement doubleMineralGrinder = advancement("doublemineralgrinder")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.mineralgrinderdouble), ElectroTextUtils.advancement("doublemineralgrinder.title").withStyle(TextFormatting.BLUE), ElectroTextUtils.advancement("doublemineralgrinder.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasGrinder", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.mineralgrinderdouble)))
				//
				.rewards(Builder.experience(50))
				//
				.parent(mineralGrinder)
				//
				.save(consumer);

		advancement("triplemineralgrinder")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.mineralgrindertriple), ElectroTextUtils.advancement("triplemineralgrinder.title").withStyle(TextFormatting.RED), ElectroTextUtils.advancement("triplemineralgrinder.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasGrinder", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.mineralgrindertriple)))
				//
				.rewards(Builder.experience(100))
				//
				.parent(doubleMineralGrinder)
				//
				.save(consumer);

		// MINERAL CRUSHER

		Advancement mineralCrusher = advancement("mineralcrusher")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.mineralcrusher), ElectroTextUtils.advancement("mineralcrusher.title").withStyle(TextFormatting.GOLD), ElectroTextUtils.advancement("mineralcrusher.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasCrusher", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.mineralcrusher)))
				//
				.rewards(Builder.experience(30))
				//
				.parent(coalGenerator)
				//
				.save(consumer);

		Advancement doubleMineralCrusher = advancement("doublemineralcrusher")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.mineralcrusherdouble), ElectroTextUtils.advancement("doublemineralcrusher.title").withStyle(TextFormatting.BLUE), ElectroTextUtils.advancement("doublemineralcrusher.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasCrusher", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.mineralcrusherdouble)))
				//
				.rewards(Builder.experience(70))
				//
				.parent(mineralCrusher)
				//
				.save(consumer);

		advancement("triplemineralcrusher")
				//
				.display(ElectrodynamicsItems.getItem(SubtypeMachine.mineralcrushertriple), ElectroTextUtils.advancement("triplemineralcrusher.title").withStyle(TextFormatting.RED), ElectroTextUtils.advancement("triplemineralcrusher.desc"), AdvancementBackgrounds.NONE, FrameType.TASK, true, true, false)
				//
				.addCriterion("HasCrusher", InventoryChangeTrigger.Instance.hasItems(ElectrodynamicsItems.getItem(SubtypeMachine.mineralcrushertriple)))
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

	public AdvancementBuilder advancement(String name) {
		return create(new ResourceLocation(modID, name));
	}

}
