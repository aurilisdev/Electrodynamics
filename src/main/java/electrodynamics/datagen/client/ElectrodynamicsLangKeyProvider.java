package electrodynamics.datagen.client;

import electrodynamics.api.References;
import electrodynamics.api.gas.Gas;
import electrodynamics.common.block.subtype.SubtypeGlass;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypeOreDeepslate;
import electrodynamics.common.block.subtype.SubtypeFluidPipe;
import electrodynamics.common.block.subtype.SubtypeRawOreBlock;
import electrodynamics.common.block.subtype.SubtypeResourceBlock;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.fluid.types.liquid.subtype.SubtypeSulfateFluid;
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
import electrodynamics.common.recipe.categories.fluid2fluid.specificmachines.ElectrolyticSeparatorRecipe;
import electrodynamics.common.recipe.categories.fluid2item.specificmachines.ChemicalCrystalizerRecipe;
import electrodynamics.common.recipe.categories.fluiditem2fluid.specificmachines.ChemicalMixerRecipe;
import electrodynamics.common.recipe.categories.fluiditem2fluid.specificmachines.FermentationPlantRecipe;
import electrodynamics.common.recipe.categories.fluiditem2fluid.specificmachines.MineralWasherRecipe;
import electrodynamics.common.recipe.categories.item2item.specificmachines.EnergizedAlloyerRecipe;
import electrodynamics.common.recipe.categories.item2item.specificmachines.LatheRecipe;
import electrodynamics.common.recipe.categories.item2item.specificmachines.MineralCrusherRecipe;
import electrodynamics.common.recipe.categories.item2item.specificmachines.MineralGrinderRecipe;
import electrodynamics.common.recipe.categories.item2item.specificmachines.OxidationFurnaceRecipe;
import electrodynamics.common.recipe.categories.item2item.specificmachines.ReinforcedAlloyerRecipe;
import electrodynamics.common.recipe.categories.item2item.specificmachines.WireMillRecipe;
import electrodynamics.registers.ElectrodynamicsBlocks;
import electrodynamics.registers.ElectrodynamicsFluids;
import electrodynamics.registers.ElectrodynamicsGases;
import electrodynamics.registers.ElectrodynamicsItems;
import electrodynamics.registers.ElectrodynamicsRegistries;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ElectrodynamicsLangKeyProvider extends LanguageProvider {

	public final Locale locale;
	public final String modID;

	public ElectrodynamicsLangKeyProvider(DataGenerator gen, Locale locale, String modID) {
		super(gen, modID, locale.toString());
		this.locale = locale;
		this.modID = modID;
	}

	public ElectrodynamicsLangKeyProvider(DataGenerator gen, Locale local) {
		this(gen, local, References.ID);
	}

	@Override
	protected void addTranslations() {

		add("itemGroup.itemgroup" + References.ID, "Electrodynamics");

		addItem(ElectrodynamicsItems.ITEM_BATTERY, "Battery");
		addItem(ElectrodynamicsItems.ITEM_LITHIUMBATTERY, "Lithium Battery");
		addItem(ElectrodynamicsItems.ITEM_CARBYNEBATTERY, "Carbyne Battery");

		addItem(ElectrodynamicsItems.ITEM_COMBATHELMET, "Combat Helmet");
		addItem(ElectrodynamicsItems.ITEM_COMBATCHESTPLATE, "Combat Chestplate");
		addItem(ElectrodynamicsItems.ITEM_COMBATLEGGINGS, "Combat Leggings");
		addItem(ElectrodynamicsItems.ITEM_COMBATBOOTS, "Combat Boots");

		addItem(ElectrodynamicsItems.ITEM_COMPOSITEHELMET, "Composite Helmet");
		addItem(ElectrodynamicsItems.ITEM_COMPOSITECHESTPLATE, "Composite Chestplate");
		addItem(ElectrodynamicsItems.ITEM_COMPOSITELEGGINGS, "Composite Leggings");
		addItem(ElectrodynamicsItems.ITEM_COMPOSITEBOOTS, "Composite Boots");

		addItem(ElectrodynamicsItems.ITEM_ELECTRICBATON, "Electric Baton");
		addItem(ElectrodynamicsItems.ITEM_ELECTRICCHAINSAW, "Electric Chainsaw");
		addItem(ElectrodynamicsItems.ITEM_ELECTRICDRILL, "Electric Drill");
		addItem(ElectrodynamicsItems.ITEM_HYDRAULICBOOTS, "Hydraulic Boots");
		addItem(ElectrodynamicsItems.ITEM_INSULATION, "Insulation");
		addItem(ElectrodynamicsItems.ITEM_JETPACK, "Jetpack");
		addItem(ElectrodynamicsItems.ITEM_KINETICRAILGUN, "Kinetic Rail Gun");
		addItem(ElectrodynamicsItems.ITEM_GUIDEBOOK, "EEC 1st Edition");
		addItem(ElectrodynamicsItems.ITEM_CANISTERREINFORCED, "Reinforced Canister");
		addItem(ElectrodynamicsItems.ITEM_WRENCH, "Wrench");
		addItem(ElectrodynamicsItems.ITEM_MECHANIZEDCROSSBOW, "Mechanized Crossbow");
		addItem(ElectrodynamicsItems.ITEM_MULTIMETER, "Handheld Multimeter");
		addItem(ElectrodynamicsItems.ITEM_NIGHTVISIONGOGGLES, "Night Vision Goggles");
		addItem(ElectrodynamicsItems.ITEM_PLASMARAILGUN, "Plasma Rail Gun");
		addItem(ElectrodynamicsItems.ITEM_RUBBERBOOTS, "Rubber Boots");
		addItem(ElectrodynamicsItems.ITEM_SEISMICSCANNER, "Seismic Scanner");
		addItem(ElectrodynamicsItems.ITEM_SERVOLEGGINGS, "Servo Leggings");

		addItem(ElectrodynamicsItems.ITEM_SHEETPLASTIC, "Polyethylene Sheet");
		addItem(ElectrodynamicsItems.ITEM_SOLARPANELPLATE, "Solar Panel Plate");
		addItem(ElectrodynamicsItems.ITEM_TITANIUM_COIL, "Titanium Coil");
		addItem(ElectrodynamicsItems.ITEM_SLAG, "Metallic Slag");
		addItem(ElectrodynamicsItems.ITEM_CERAMICINSULATION, "Ceramic Insulation");
		addItem(ElectrodynamicsItems.ITEM_COIL, "Copper Coil");
		addItem(ElectrodynamicsItems.ITEM_MOLYBDENUMFERTILIZER, "Fertilizer");
		addItem(ElectrodynamicsItems.ITEM_MOTOR, "Motor");
		addItem(ElectrodynamicsItems.ITEM_COAL_COKE, "Coal Coke");
		addItem(ElectrodynamicsItems.ITEM_RAWCOMPOSITEPLATING, "Raw Composite Plating");
		addItem(ElectrodynamicsItems.ITEM_COMPOSITEPLATING, "Composite Plating");

		addItem(ElectrodynamicsItems.getItem(SubtypeCeramic.cooked), "Ceramic");
		addItem(ElectrodynamicsItems.getItem(SubtypeCeramic.fuse), "Ceramic Fuse");
		addItem(ElectrodynamicsItems.getItem(SubtypeCeramic.plate), "Ceramic Plate");
		addItem(ElectrodynamicsItems.getItem(SubtypeCeramic.wet), "Wet Ceramic");

		addItem(ElectrodynamicsItems.getItem(SubtypeCircuit.basic), "Basic Circuit");
		addItem(ElectrodynamicsItems.getItem(SubtypeCircuit.advanced), "Advanced Circuit");
		addItem(ElectrodynamicsItems.getItem(SubtypeCircuit.elite), "Elite Circuit");
		addItem(ElectrodynamicsItems.getItem(SubtypeCircuit.ultimate), "Ultimate Circuit");

		addItem(ElectrodynamicsItems.getItem(SubtypeCrystal.copper), "Copper Sulfate Crystal");
		addItem(ElectrodynamicsItems.getItem(SubtypeCrystal.gold), "Gold Sulfate Crystal");
		addItem(ElectrodynamicsItems.getItem(SubtypeCrystal.halite), "Halite Crystal");
		addItem(ElectrodynamicsItems.getItem(SubtypeCrystal.iron), "Iron Sulfate Crystal");
		addItem(ElectrodynamicsItems.getItem(SubtypeCrystal.lead), "Lead Sulfate Crystal");
		addItem(ElectrodynamicsItems.getItem(SubtypeCrystal.lithium), "Lithium Sulfate Crystal");
		addItem(ElectrodynamicsItems.getItem(SubtypeCrystal.molybdenum), "Molybdenum Sulfate Crystal");
		addItem(ElectrodynamicsItems.getItem(SubtypeCrystal.netherite), "Carbyne Sulfate Crystal");
		addItem(ElectrodynamicsItems.getItem(SubtypeCrystal.potassiumchloride), "Sylvite Crystal");
		addItem(ElectrodynamicsItems.getItem(SubtypeCrystal.silver), "Silver Sulfate Crystal");
		addItem(ElectrodynamicsItems.getItem(SubtypeCrystal.tin), "Tin Sulfate Crystal");
		addItem(ElectrodynamicsItems.getItem(SubtypeCrystal.vanadium), "Vanadium Sulfate Crystal");

		addItem(ElectrodynamicsItems.getItem(SubtypeDrillHead.hslasteel), "HSLA Drill Head");
		addItem(ElectrodynamicsItems.getItem(SubtypeDrillHead.stainlesssteel), "Stainless Drill Head");
		addItem(ElectrodynamicsItems.getItem(SubtypeDrillHead.steel), "Steel Drill Head");
		addItem(ElectrodynamicsItems.getItem(SubtypeDrillHead.titanium), "Titanium Drill Head");
		addItem(ElectrodynamicsItems.getItem(SubtypeDrillHead.titaniumcarbide), "Carbide Drill Head");

		addItem(ElectrodynamicsItems.getItem(SubtypeDust.bronze), "Bronze Blend");
		addItem(ElectrodynamicsItems.getItem(SubtypeDust.copper), "Copper Dust");
		addItem(ElectrodynamicsItems.getItem(SubtypeDust.endereye), "Ender Eye Dust");
		addItem(ElectrodynamicsItems.getItem(SubtypeDust.gold), "Gold Dust");
		addItem(ElectrodynamicsItems.getItem(SubtypeDust.iron), "Iron Dust");
		addItem(ElectrodynamicsItems.getItem(SubtypeDust.lead), "Lead Dust");
		addItem(ElectrodynamicsItems.getItem(SubtypeDust.lithium), "Lithium Dust");
		addItem(ElectrodynamicsItems.getItem(SubtypeDust.molybdenum), "Molybdenum Dust");
		addItem(ElectrodynamicsItems.getItem(SubtypeDust.netherite), "Carbyne Dust");
		addItem(ElectrodynamicsItems.getItem(SubtypeDust.niter), "Niter");
		addItem(ElectrodynamicsItems.getItem(SubtypeDust.obsidian), "Obsidian Dust");
		addItem(ElectrodynamicsItems.getItem(SubtypeDust.salt), "Salt");
		addItem(ElectrodynamicsItems.getItem(SubtypeDust.silica), "Silica Dust");
		addItem(ElectrodynamicsItems.getItem(SubtypeDust.steel), "Steel Dust");
		addItem(ElectrodynamicsItems.getItem(SubtypeDust.silver), "Silver Dust");
		addItem(ElectrodynamicsItems.getItem(SubtypeDust.sulfur), "Sulfur");
		addItem(ElectrodynamicsItems.getItem(SubtypeDust.superconductive), "Superconductive Blend");
		addItem(ElectrodynamicsItems.getItem(SubtypeDust.tin), "Tin Dust");
		addItem(ElectrodynamicsItems.getItem(SubtypeDust.vanadium), "Vanadium Dust");

		addItem(ElectrodynamicsItems.getItem(SubtypeGear.bronze), "Bronze Gear");
		addItem(ElectrodynamicsItems.getItem(SubtypeGear.copper), "Copper Gear");
		addItem(ElectrodynamicsItems.getItem(SubtypeGear.iron), "Iron Gear");
		addItem(ElectrodynamicsItems.getItem(SubtypeGear.steel), "Steel Gear");
		addItem(ElectrodynamicsItems.getItem(SubtypeGear.tin), "Tin Gear");

		addItem(ElectrodynamicsItems.getItem(SubtypeImpureDust.copper), "Impure Copper Dust");
		addItem(ElectrodynamicsItems.getItem(SubtypeImpureDust.gold), "Impure Gold Dust");
		addItem(ElectrodynamicsItems.getItem(SubtypeImpureDust.iron), "Impure Iron Dust");
		addItem(ElectrodynamicsItems.getItem(SubtypeImpureDust.lead), "Impure Lead Dust");
		addItem(ElectrodynamicsItems.getItem(SubtypeImpureDust.lithium), "Impure Lithium Dust");
		addItem(ElectrodynamicsItems.getItem(SubtypeImpureDust.molybdenum), "Impure Molybdenum Dust");
		addItem(ElectrodynamicsItems.getItem(SubtypeImpureDust.netherite), "Impure Carbyne Dust");
		addItem(ElectrodynamicsItems.getItem(SubtypeImpureDust.silver), "Impure Silver Dust");
		addItem(ElectrodynamicsItems.getItem(SubtypeImpureDust.tin), "Impure Tin Dust");
		addItem(ElectrodynamicsItems.getItem(SubtypeImpureDust.vanadium), "Impure Vanadium Dust");

		addItem(ElectrodynamicsItems.getItem(SubtypeIngot.aluminum), "Aluminum Ingot");
		addItem(ElectrodynamicsItems.getItem(SubtypeIngot.bronze), "Bronze Ingot");
		addItem(ElectrodynamicsItems.getItem(SubtypeIngot.chromium), "Chromium Ingot");
		addItem(ElectrodynamicsItems.getItem(SubtypeIngot.hslasteel), "HSLA Steel Ingot");
		addItem(ElectrodynamicsItems.getItem(SubtypeIngot.lead), "Lead Ingot");
		addItem(ElectrodynamicsItems.getItem(SubtypeIngot.lithium), "Lithium Ingot");
		addItem(ElectrodynamicsItems.getItem(SubtypeIngot.molybdenum), "Molybdenum Ingot");
		addItem(ElectrodynamicsItems.getItem(SubtypeIngot.silver), "Silver Ingot");
		addItem(ElectrodynamicsItems.getItem(SubtypeIngot.stainlesssteel), "Stainless Steel Ingot");
		addItem(ElectrodynamicsItems.getItem(SubtypeIngot.steel), "Steel Ingot");
		addItem(ElectrodynamicsItems.getItem(SubtypeIngot.superconductive), "Superconductive Ingot");
		addItem(ElectrodynamicsItems.getItem(SubtypeIngot.tin), "Tin Ingot");
		addItem(ElectrodynamicsItems.getItem(SubtypeIngot.titanium), "Titanium Ingot");
		addItem(ElectrodynamicsItems.getItem(SubtypeIngot.titaniumcarbide), "Titanium Carbide Ingot");
		addItem(ElectrodynamicsItems.getItem(SubtypeIngot.vanadium), "Vanadium Ingot");
		addItem(ElectrodynamicsItems.getItem(SubtypeIngot.vanadiumsteel), "Vanadium Steel Ingot");

		addItem(ElectrodynamicsItems.getItem(SubtypeItemUpgrade.advancedcapacity), "Advanced Capacity Upgrade");
		addItem(ElectrodynamicsItems.getItem(SubtypeItemUpgrade.advancedspeed), "Advanced Speed Upgrade");
		addItem(ElectrodynamicsItems.getItem(SubtypeItemUpgrade.basiccapacity), "Basic Capacity Upgrade");
		addItem(ElectrodynamicsItems.getItem(SubtypeItemUpgrade.basicspeed), "Basic Speed Upgrade");
		addItem(ElectrodynamicsItems.getItem(SubtypeItemUpgrade.experience), "Experience Upgrade");
		addItem(ElectrodynamicsItems.getItem(SubtypeItemUpgrade.fortune), "Fortune Upgrade");
		addItem(ElectrodynamicsItems.getItem(SubtypeItemUpgrade.improvedsolarcell), "Solar Cell Upgrade");
		addItem(ElectrodynamicsItems.getItem(SubtypeItemUpgrade.iteminput), "Auto-Injector Upgrade");
		addItem(ElectrodynamicsItems.getItem(SubtypeItemUpgrade.itemoutput), "Auto-Ejector Upgrade");
		addItem(ElectrodynamicsItems.getItem(SubtypeItemUpgrade.itemvoid), "Void Upgrade");
		addItem(ElectrodynamicsItems.getItem(SubtypeItemUpgrade.range), "Range Upgrade");
		addItem(ElectrodynamicsItems.getItem(SubtypeItemUpgrade.silktouch), "Silk Touch Upgrade");
		addItem(ElectrodynamicsItems.getItem(SubtypeItemUpgrade.stator), "Stator Upgrade");
		addItem(ElectrodynamicsItems.getItem(SubtypeItemUpgrade.unbreaking), "Unbreaking Upgrade");

		addItem(ElectrodynamicsItems.getItem(SubtypeNugget.copper), "Copper Nugget");
		addItem(ElectrodynamicsItems.getItem(SubtypeNugget.hslasteel), "HSLA Steel Nugget");
		addItem(ElectrodynamicsItems.getItem(SubtypeNugget.silver), "Silver Nugget");
		addItem(ElectrodynamicsItems.getItem(SubtypeNugget.steel), "Steel Nugget");
		addItem(ElectrodynamicsItems.getItem(SubtypeNugget.stainlesssteel), "Stainless Steel Nugget");
		addItem(ElectrodynamicsItems.getItem(SubtypeNugget.superconductive), "Superconductive Nugget");
		addItem(ElectrodynamicsItems.getItem(SubtypeNugget.tin), "Tin Nugget");
		addItem(ElectrodynamicsItems.getItem(SubtypeNugget.titaniumcarbide), "Titanium Carbide Nugget");

		addItem(ElectrodynamicsItems.getItem(SubtypeOxide.calciumcarbonate), "Calcium Carbonate");
		addItem(ElectrodynamicsItems.getItem(SubtypeOxide.chromite), "Chromium Oxide");
		addItem(ElectrodynamicsItems.getItem(SubtypeOxide.chromiumdisilicide), "Chromium Disilicide");
		addItem(ElectrodynamicsItems.getItem(SubtypeOxide.disulfur), "Sulfur Dioxide");
		addItem(ElectrodynamicsItems.getItem(SubtypeOxide.dititanium), "Titanium Dioxide");
		addItem(ElectrodynamicsItems.getItem(SubtypeOxide.sodiumcarbonate), "Sodium Carbonate");
		addItem(ElectrodynamicsItems.getItem(SubtypeOxide.sulfurdichloride), "Sulfur Dichloride");
		addItem(ElectrodynamicsItems.getItem(SubtypeOxide.thionylchloride), "Thionyl Chloride");
		addItem(ElectrodynamicsItems.getItem(SubtypeOxide.trisulfur), "Sulfur Trioxide");
		addItem(ElectrodynamicsItems.getItem(SubtypeOxide.vanadium), "Vanadium Oxide");

		addItem(ElectrodynamicsItems.getItem(SubtypePlate.aluminum), "Aluminum Plate");
		addItem(ElectrodynamicsItems.getItem(SubtypePlate.bronze), "Bronze Plate");
		addItem(ElectrodynamicsItems.getItem(SubtypePlate.hslasteel), "HSLA Steel Plate");
		addItem(ElectrodynamicsItems.getItem(SubtypePlate.iron), "Iron Plate");
		addItem(ElectrodynamicsItems.getItem(SubtypePlate.lead), "Lead Plate");
		addItem(ElectrodynamicsItems.getItem(SubtypePlate.lithium), "Lithium Plate");
		addItem(ElectrodynamicsItems.getItem(SubtypePlate.stainlesssteel), "Stainless Steel Plate");
		addItem(ElectrodynamicsItems.getItem(SubtypePlate.steel), "Steel Plate");
		addItem(ElectrodynamicsItems.getItem(SubtypePlate.titanium), "Titanium Plate");
		addItem(ElectrodynamicsItems.getItem(SubtypePlate.titaniumcarbide), "Titanium Carbide Plate");
		addItem(ElectrodynamicsItems.getItem(SubtypePlate.vanadiumsteel), "Vanadium Steel Plate");

		addItem(ElectrodynamicsItems.getItem(SubtypeRawOre.chromium), "Raw Chromite");
		addItem(ElectrodynamicsItems.getItem(SubtypeRawOre.fluorite), "Raw Fluorite Crystal");
		addItem(ElectrodynamicsItems.getItem(SubtypeRawOre.lead), "Raw Galena");
		addItem(ElectrodynamicsItems.getItem(SubtypeRawOre.lepidolite), "Raw Lepidolite");
		addItem(ElectrodynamicsItems.getItem(SubtypeRawOre.silver), "Raw Argentite");
		addItem(ElectrodynamicsItems.getItem(SubtypeRawOre.thorium), "Raw Thorianite");
		addItem(ElectrodynamicsItems.getItem(SubtypeRawOre.tin), "Raw Cassiterite");
		addItem(ElectrodynamicsItems.getItem(SubtypeRawOre.titanium), "Raw Rutile");
		addItem(ElectrodynamicsItems.getItem(SubtypeRawOre.uranium), "Raw Uraninite");
		addItem(ElectrodynamicsItems.getItem(SubtypeRawOre.vanadinite), "Raw Vanadinite");

		addItem(ElectrodynamicsItems.getItem(SubtypeRod.hslasteel), "HSLA Steel Rod");
		addItem(ElectrodynamicsItems.getItem(SubtypeRod.stainlesssteel), "Stainless Steel Rod");
		addItem(ElectrodynamicsItems.getItem(SubtypeRod.steel), "Steel Rod");
		addItem(ElectrodynamicsItems.getItem(SubtypeRod.titaniumcarbide), "Titanium Carbide Rod");

		addBlock(ElectrodynamicsBlocks.blockFrame, "Quarry Frame");
		addBlock(ElectrodynamicsBlocks.blockFrameCorner, "Quarry Frame Corner");
		addBlock(ElectrodynamicsBlocks.blockLogisticalManager, "Logistical Manager");
		addBlock(ElectrodynamicsBlocks.blockSeismicMarker, "Seismic Marker");
		addBlock(ElectrodynamicsBlocks.multi, "Multiblock Subnode");

		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeGlass.aluminum), "ALON");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeGlass.clear), "Clear Glass");

		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.advancedsolarpanel), "Advanced Solar Panel");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.batterybox), "Battery Box");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.carbynebatterybox), "Carbyne Battery Box");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.chargerlv), "120V Charger");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.chargermv), "240V Charger");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.chargerhv), "480V Charger");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.chemicalcrystallizer), "Chemical Crystallizer");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.chemicalmixer), "Chemical Mixer");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.circuitbreaker), "Circuit Breaker");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.coalgenerator), "Coal Generator");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.combustionchamber), "Combustion Chamber");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.coolantresavoir), "Coolant Resavoir");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.creativefluidsource), "Creative Fluid Source");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.creativepowersource), "Creative Power Source");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.downgradetransformer), "Downgrade Transformer");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricarcfurnace), "Electric Arc Furnace");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricarcfurnacedouble), "Double Electric Arc Furnace");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricarcfurnacetriple), "Triple Electric Arc Furnace");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricfurnace), "Electric Furnace");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricfurnacedouble), "Double Electric Furnace");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricfurnacetriple), "Triple Electric Furnace");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricpump), "Electric Pump");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electrolyticseparator), "Electrolyic Separator");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.energizedalloyer), "Energized Alloyer");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.fermentationplant), "Fermentation Plant");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.fluidvoid), "Fluid Void");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.hydroelectricgenerator), "Hydroelectric Generator");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.lathe), "Lathe");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.lithiumbatterybox), "Lithium Battery Box");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.mineralcrusher), "Mineral Crusher");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.mineralcrusherdouble), "Double Mineral Crusher");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.mineralcrushertriple), "Triple Mineral Crusher");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.mineralgrinder), "Mineral Grinder");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.mineralgrinderdouble), "Double Mineral Grinder");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.mineralgrindertriple), "Triple Mineral Grinder");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.mineralwasher), "Mineral Washer");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.motorcomplex), "Motor Complex");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.multimeterblock), "Multimeter Block");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.oxidationfurnace), "Chemical Furnace");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.quarry), "Quarry");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.reinforcedalloyer), "Reinforced Alloyer");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.seismicrelay), "Seismic Relay");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.solarpanel), "Solar Panel");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.tanksteel), "Steel Tank");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.tankreinforced), "Reinforced Tank");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.tankhsla), "HSLA Tank");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.thermoelectricgenerator), "Thermoelectric Generator");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.upgradetransformer), "Upgrade Transformer");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.windmill), "Windmill");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.wiremill), "Wire Mill");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.wiremilldouble), "Double Wire Mill");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.wiremilltriple), "Triple Wire Mill");

		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOre.aluminum), "Bauxite Ore");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOre.chromite), "Chromite Ore");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOre.fluorite), "Fluorite Ore");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOre.lead), "Galena Ore");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOre.lepidolite), "Lepidolite Ore");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOre.molybdenum), "Molybdenite Ore");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOre.monazite), "Monazite Ore");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOre.niter), "Saltpeter Ore");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOre.halite), "Halite Ore");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOre.silver), "Argentite Ore");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOre.sulfur), "Sulfur Ore");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOre.sylvite), "Sylvite Ore");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOre.rutile), "Rutile Ore");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOre.tin), "Cassiterite Ore");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOre.thorianite), "Thorianite Ore");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOre.uraninite), "Uraninite Ore");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOre.vanadinite), "Vanadinite Ore");

		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOreDeepslate.aluminum), "Deep Bauxite Ore");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOreDeepslate.chromite), "Deep Chromite Ore");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOreDeepslate.fluorite), "Deep Fluorite Ore");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOreDeepslate.lead), "Deep Galena Ore");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOreDeepslate.lepidolite), "Deep Lepidolite Ore");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOreDeepslate.molybdenum), "Deep Molybdenite Ore");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOreDeepslate.monazite), "Deep Monazite Ore");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOreDeepslate.niter), "Deep Saltpeter Ore");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOreDeepslate.halite), "Deep Halite Ore");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOreDeepslate.silver), "Deep Argentite Ore");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOreDeepslate.sulfur), "Deep Sulfur Ore");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOreDeepslate.sylvite), "Deep Sylvite Ore");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOreDeepslate.tin), "Deep Cassiterite Ore");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOreDeepslate.rutile), "Deep Rutile Ore");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOreDeepslate.thorianite), "Deep Thorianite Ore");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOreDeepslate.uraninite), "Deep Uraninite Ore");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOreDeepslate.vanadinite), "Deep Vanadinite Ore");

		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeFluidPipe.copper), "Copper Fluid Pipe");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeFluidPipe.steel), "Steel Fluid Pipe");

		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeRawOreBlock.chromium), "Block of Raw Chromite");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeRawOreBlock.lead), "Block of Raw Galena");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeRawOreBlock.silver), "Block of Raw Argentite");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeRawOreBlock.thorium), "Block of Raw Thorianite");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeRawOreBlock.tin), "Block of Raw Cassiterite");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeRawOreBlock.titanium), "Block of Raw Rutile");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeRawOreBlock.uranium), "Block of Raw Uraninite");

		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeResourceBlock.aluminum), "Block of Aluminum");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeResourceBlock.bronze), "Block of Bronze");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeResourceBlock.chromium), "Block of Chromium");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeResourceBlock.hslasteel), "Block of HSLA Steel");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeResourceBlock.lead), "Block of Lead");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeResourceBlock.silver), "Block of Silver");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeResourceBlock.stainlesssteel), "Block of Stainless Steel");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeResourceBlock.steel), "Block of Steel");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeResourceBlock.tin), "Block of Tin");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeResourceBlock.titanium), "Block of Titanium");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeResourceBlock.titaniumcarbide), "Block of Titanium Carbide");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeResourceBlock.vanadiumsteel), "Block of Vanadium Steel");

		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeWire.copper), "Copper Wire");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeWire.gold), "Gold Wire");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeWire.iron), "Iron Wire");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeWire.silver), "Silver Wire");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeWire.superconductive), "Superconductive Wire");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeWire.tin), "Tin Wire");

		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeWire.insulatedcopper), "Insulated Copper Wire");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeWire.insulatedgold), "Insulated Gold Wire");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeWire.insulatediron), "Insulated Iron Wire");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeWire.insulatedsilver), "Insulated Silver Wire");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeWire.insulatedsuperconductive), "Insulated Superconductive Wire");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeWire.insulatedtin), "Insulated Tin Wire");

		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeWire.ceramicinsulatedcopper), "Ceramic Copper Wire");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeWire.ceramicinsulatedgold), "Ceramic Gold Wire");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeWire.ceramicinsulatediron), "Ceramic Iron Wire");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeWire.ceramicinsulatedsilver), "Ceramic Silver Wire");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeWire.ceramicinsulatedsuperconductive), "Ceramic Superconductive Wire");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeWire.ceramicinsulatedtin), "Ceramic Tin Wire");

		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeWire.logisticscopper), "Logistical Copper Wire");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeWire.logisticsgold), "Logistical Gold Wire");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeWire.logisticsiron), "Logistical Iron Wire");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeWire.logisticssilver), "Logistical Silver Wire");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeWire.logisticssuperconductive), "Logistical Superconductive Wire");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeWire.logisticstin), "Logistical Tin Wire");

		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeWire.highlyinsulatedcopper), "Thick Copper Wire");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeWire.highlyinsulatedgold), "Thick Gold Wire");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeWire.highlyinsulatediron), "Thick Iron Wire");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeWire.highlyinsulatedsilver), "Thick Silver Wire");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeWire.highlyinsulatedsuperconductive), "Thick Superconductive Wire");
		addBlock(ElectrodynamicsBlocks.getBlock(SubtypeWire.highlyinsulatedtin), "Thick Tin Wire");

		addFluid(ElectrodynamicsFluids.fluidClay, "Clay Slurry");
		addFluid(ElectrodynamicsFluids.fluidEthanol, "Ethanol");
		addFluid(ElectrodynamicsFluids.fluidHydraulic, "Hydraulic Fluid");
		addFluid(ElectrodynamicsFluids.fluidHydrogen, "Liquid Hydrogen");
		addFluid(ElectrodynamicsFluids.fluidHydrogenFluoride, "Hydrofluoric Acid");
		addFluid(ElectrodynamicsFluids.fluidOxygen, "Liquid Oxygen");
		addFluid(ElectrodynamicsFluids.fluidPolyethylene, "Molten Polyethylene");
		addFluid(ElectrodynamicsFluids.fluidSulfuricAcid, "Sulfuric Acid");
		addFluid(ElectrodynamicsFluids.getFluid(SubtypeSulfateFluid.copper), "Copper Sulfate");
		addFluid(ElectrodynamicsFluids.getFluid(SubtypeSulfateFluid.gold), "Gold Sulfate");
		addFluid(ElectrodynamicsFluids.getFluid(SubtypeSulfateFluid.iron), "Iron Sulfate");
		addFluid(ElectrodynamicsFluids.getFluid(SubtypeSulfateFluid.lead), "Lead Sulfate");
		addFluid(ElectrodynamicsFluids.getFluid(SubtypeSulfateFluid.lithium), "Lithium Sulfate");
		addFluid(ElectrodynamicsFluids.getFluid(SubtypeSulfateFluid.molybdenum), "Molybdenum Sulfate");
		addFluid(ElectrodynamicsFluids.getFluid(SubtypeSulfateFluid.netherite), "Carbyne Sulfate");
		addFluid(ElectrodynamicsFluids.getFluid(SubtypeSulfateFluid.silver), "Silver Sulfate");
		addFluid(ElectrodynamicsFluids.getFluid(SubtypeSulfateFluid.tin), "Tin Sulfate");
		addFluid(ElectrodynamicsFluids.getFluid(SubtypeSulfateFluid.vanadium), "Vanadium Sulfate");

		addGas(ElectrodynamicsGases.EMPTY, "Empty");

		addContainer(SubtypeMachine.advancedsolarpanel, "Advanced Solar Panel");
		addContainer(SubtypeMachine.batterybox, "Battery Box");
		addContainer(SubtypeMachine.carbynebatterybox, "Carbyne Battery Box");
		addContainer(SubtypeMachine.chargerlv, "120V Charger");
		addContainer(SubtypeMachine.chargermv, "240V Charger");
		addContainer(SubtypeMachine.chargerhv, "480V Charger");
		addContainer(SubtypeMachine.chemicalcrystallizer, "Chemical Crystallizer");
		addContainer(SubtypeMachine.chemicalmixer, "Chemical Mixer");
		addContainer(SubtypeMachine.coalgenerator, "Coal Generator");
		addContainer(SubtypeMachine.coolantresavoir, "Coolant Resavoir");
		addContainer(SubtypeMachine.combustionchamber, "Combustion Chamber");
		addContainer(SubtypeMachine.creativefluidsource, "Creative Fluid Source");
		addContainer(SubtypeMachine.creativepowersource, "Creative Power Source");
		addContainer(SubtypeMachine.electricarcfurnace, "Arc Furnace");
		addContainer(SubtypeMachine.electricarcfurnacedouble, "Double Arc Furnace");
		addContainer(SubtypeMachine.electricarcfurnacetriple, "Triple Arc Furnace");
		addContainer(SubtypeMachine.electricfurnace, "Electric Furnace");
		addContainer(SubtypeMachine.electricfurnacedouble, "Double Electric Furnace");
		addContainer(SubtypeMachine.electricfurnacetriple, "Triple Electric Furnace");
		addContainer(SubtypeMachine.electrolyticseparator, "Electrolytic Separator");
		addContainer(SubtypeMachine.energizedalloyer, "Energized Alloyer");
		addContainer(SubtypeMachine.fermentationplant, "Fermentation Plant");
		addContainer(SubtypeMachine.fluidvoid, "Fluid Void");
		addContainer(SubtypeMachine.hydroelectricgenerator, "Hydroelectric Generator");
		addContainer(SubtypeMachine.lathe, "Lathe");
		addContainer(SubtypeMachine.lithiumbatterybox, "Lithium Battery Box");
		addContainer(SubtypeMachine.mineralcrusher, "Mineral Crusher");
		addContainer(SubtypeMachine.mineralcrusherdouble, "Double Mineral Crusher");
		addContainer(SubtypeMachine.mineralcrushertriple, "Triple Mineral Crusher");
		addContainer(SubtypeMachine.mineralgrinder, "Mineral Grinder");
		addContainer(SubtypeMachine.mineralgrinderdouble, "Double Mineral Grinder");
		addContainer(SubtypeMachine.mineralgrindertriple, "Triple Mineral Grinder");
		addContainer(SubtypeMachine.mineralwasher, "Mineral Washer");
		addContainer(SubtypeMachine.motorcomplex, "Motor Complex");
		addContainer(SubtypeMachine.oxidationfurnace, "Chemical Furnace");
		addContainer(SubtypeMachine.quarry, "Quarry");
		addContainer(SubtypeMachine.reinforcedalloyer, "Reinforced Alloyer");
		addContainer(SubtypeMachine.seismicrelay, "Seismic Relay");
		addContainer(SubtypeMachine.solarpanel, "Solar Panel");
		addContainer(SubtypeMachine.tanksteel, "Steel Tank");
		addContainer(SubtypeMachine.tankreinforced, "Reinforced Tank");
		addContainer(SubtypeMachine.tankhsla, "HSLA Tank");
		addContainer(SubtypeMachine.windmill, "Windmill");
		addContainer(SubtypeMachine.wiremill, "Wire Mill");
		addContainer(SubtypeMachine.wiremilldouble, "Double Wire Mill");
		addContainer(SubtypeMachine.wiremilltriple, "Triple Wire Mill");

		addContainer("guidebook", "Guidebook");
		addContainer("seismicscanner", "Seismic Scanner");

		addTooltip("itemwire.resistance", "Resistance: %s");
		addTooltip("itemwire.maxamps", "Ampacity: %s");
		addTooltip("itemwire.info.insulationrating", "Insulation Rating: %s");
		addTooltip("itemwire.info.uninsulated", "Uninsulated!");
		addTooltip("itemwire.info.fireproof", "Fire-proof");
		addTooltip("itemwire.info.redstone", "Conducts Redstone");
		addTooltip("info.capacityupgrade", "Multiplies Energy Capacity and Transfer by %sx");
		addTooltip("info.capacityupgradevoltage", "Will upgrade machine voltage up to %s");
		addTooltip("info.speedupgrade", "Multiplies Speed by %sx");
		addTooltip("info.itemoutputupgrade", "Ejects items into adjacent inventories");
		addTooltip("info.iteminputupgrade", "Injects items from adjacent inventories");
		addTooltip("info.dirlist", "Current Directions:");
		addTooltip("info.nodirs", "Shift+Right-Click to add a direction");
		addTooltip("info.cleardirs", "Shift+Left-Click to clear directions");
		addTooltip("info.insmartmode", "Smart Mode");
		addTooltip("info.togglesmart", "Right-Click to toggle smart mode.");
		addTooltip("info.guidebookuse", "Shift+Right-Click to open wiki");
		addTooltip("info.guidebooktemp", "Book Coming Soon");
		addTooltip("info.xpstored", "Stored XP: %s");
		addTooltip("info.xpusage", "Shift+Right-Click to dispense");
		addTooltip("info.range", "Increases raduis by 1");
		addTooltip("info.broken", "NOT WORKING");
		addTooltip("transformer.energyloss", "Efficiency: 99.25%");
		addTooltip("machine.voltage", "Voltage: %s");
		addTooltip("item.electric.info", "Energy Stored: %s");
		addTooltip("item.electric.voltage", "I/O Voltage: %s");
		addTooltip("itemcanister", "1000 mB");
		addTooltip("railguntemp", "Temperature %s");
		addTooltip("railgunmaxtemp", "Max Temp: %s");
		addTooltip("railgunoverheat", "WARNING : OVERHEATING");
		addTooltip("ceramicplatecount", "Plates: %s");
		addTooltip("fluidtank.capacity", "Capacity: %s");
		addTooltip("creativepowersource.joke", "\"Unlimited Power\" - Buck Rogers from Star Trek");
		addTooltip("creativefluidsource.joke", "\"More\" - Crylo Ren");
		addTooltip("fluidvoid", "Voids fluids");
		addTooltip("seismicscanner.use", "Scans for ores in a 16 block radius");
		addTooltip("seismicscanner.opengui", "Right-Click to view status");
		addTooltip("seismicscanner.oncooldown", "COOLDOWN");
		addTooltip("seismicscanner.showuse", "Shift + Right-Click to scan");
		addTooltip("seismicscanner.currentore", "Current Ore: %s");
		addTooltip("seismicscanner.empty", "[None]");
		addTooltip("nightvisiongoggles.status", "Status: ");
		addTooltip("nightvisiongoggles.on", "ON");
		addTooltip("nightvisiongoggles.off", "OFF");
		addTooltip("jetpack.mode", "Mode: ");
		addTooltip("jetpack.moderegular", "Regular");
		addTooltip("jetpack.modehover", "Hover");
		addTooltip("jetpack.modeelytra", "Elytra");
		addTooltip("jetpack.modeoff", "Off");
		addTooltip("countdown.tickstillcheck", "Ticks left: %s");
		addTooltip("seismicmarker.redstone", "Apply redstone for guide lines");
		addTooltip("seismicrelay.use", "Detects and stores Marker rings");
		addTooltip("coolantresavoir.place", "Place above Quarry");
		addTooltip("motorcomplex.use", "Controls Speed of Quarry");
		addTooltip("blockframe.joke", "How did you get this!?!");
		addTooltip("quarry.power", "Upgrades require power to work");
		addTooltip("logisticalmanager.use", "Optional: manages quarry inventory");
		addTooltip("servolegs.step", "STEP");
		addTooltip("servolegs.both", "BOTH");
		addTooltip("servolegs.speed", "SPEED");
		addTooltip("servolegs.none", "NONE");
		addTooltip("currbattery", "Battery: %s");
		addTooltip("gasvent", "Voids gases");
		addTooltip("validupgrades", "Valid Upgrades:");

		addTooltip("guidebookjeiuse", "Press 'U' for uses");
		addTooltip("guidebookjeirecipe", "Press 'R' for recipes");

		addTooltip("guidebookname", "AKA Guidebook");

		addTooltip("inventoryio", "Inventory I/O");
		addTooltip("inventoryio.presstoshow", "press to show");
		addTooltip("inventoryio.presstohide", "press to hide");
		addTooltip("inventoryio.top", "Top");
		addTooltip("inventoryio.bottom", "Bottom");
		addTooltip("inventoryio.left", "Left");
		addTooltip("inventoryio.right", "Right");
		addTooltip("inventoryio.front", "Front");
		addTooltip("inventoryio.back", "Back");
		addTooltip("inventoryio.slotmap", "Slot Map");

		addGuiLabel("creativepowersource.voltage", "Voltage: ");
		addGuiLabel("creativepowersource.power", "Power: ");
		addGuiLabel("creativefluidsource.setfluid", "Set Fluid");
		addGuiLabel("machine.usage", "Usage: %s");
		addGuiLabel("machine.voltage", "Voltage: %s");
		addGuiLabel("machine.current", "Current: %s");
		addGuiLabel("machine.output", "Output: %s");
		addGuiLabel("machine.transfer", "Transfer: %s");
		addGuiLabel("machine.stored", "Stored: %s");
		addGuiLabel("machine.temperature", "Temperature: %s");
		addGuiLabel("machine.heat", "Heat: %s");
		addGuiLabel("coalgenerator.timeleft", "Time Left: %s");
		addGuiLabel("seismicscanner.dataheader", "Scan Results:");
		addGuiLabel("seismicscanner.notfound", "Block not found!");
		addGuiLabel("seismicscanner.xcoord", "X , %s");
		addGuiLabel("seismicscanner.ycoord", "Y , %s");
		addGuiLabel("seismicscanner.zcoord", "Z , %s");
		addGuiLabel("seismicscanner.xcoordna", "X , Not Found");
		addGuiLabel("seismicscanner.ycoordna", "Y , Not Found");
		addGuiLabel("seismicscanner.zcoordna", "Z , Not Found");
		addGuiLabel("seismicscanner.material", "Material:");
		addGuiLabel("genericcharger.chargeperc", "Charge: %s");
		addGuiLabel("genericcharger.chargecapable", "Possible: %s");
		addGuiLabel("seismicrelay.dataheader", "Markers");
		addGuiLabel("seismicrelay.posnotfound", "%s , Not Found");
		addGuiLabel("seismicrelay.posfound", "%1$s , %2$s");
		addGuiLabel("seismicrelay.posoptional", "%s , Optional");
		addGuiLabel("motorcomplex.speed", "Ticks per Block: %s");

		addGuiLabel("quarry.ringusage", "Ring Usage: %s");
		addGuiLabel("quarry.miningusage", "Drill Usage: %s");

		addGuiLabel("quarry.fortune", "Fortune: %s");
		addGuiLabel("quarry.silktouch", "Silk Touch: %s");
		addGuiLabel("quarry.unbreaking", "Unbreaking: %s");

		addGuiLabel("quarry.wateruse", "Coolant/Block: %s");

		addGuiLabel("quarry.motorcomplex", "Motor Complex");
		addGuiLabel("quarry.seismicrelay", "Seismic Relay");
		addGuiLabel("quarry.coolantresavoir", "Coolant Resavoir");

		addGuiLabel("quarry.voiditems", "Void List");
		addGuiLabel("quarry.needvoidcard", "No Card");

		addGuiLabel("quarry.status", "Status:");
		addGuiLabel("quarry.clearingarea", "Clearing...");
		addGuiLabel("quarry.setup", "Setting Up...");
		addGuiLabel("quarry.mining", "Mining...");
		addGuiLabel("quarry.notmining", "Error!");
		addGuiLabel("quarry.finished", "Finished!");

		addGuiLabel("quarry.miningposition", "Mining At: %s");
		addGuiLabel("quarry.notavailable", "N/A");

		addGuiLabel("quarry.drillhead", "Drill Head: %s");
		addGuiLabel("quarry.hashead", "Present");
		addGuiLabel("quarry.nohead", "Missing");

		addGuiLabel("quarry.errors", "Errors:");
		addGuiLabel("quarry.norelay", "No Relay");
		addGuiLabel("quarry.nomotorcomplex", "No Motor");
		addGuiLabel("quarry.nocoolantresavoir", "No Resavoir");
		addGuiLabel("quarry.nocorners", "Invalid Corners");
		addGuiLabel("quarry.motorcomplexnotpowered", "Motor Power");
		addGuiLabel("quarry.nopower", "Quarry Power");
		addGuiLabel("quarry.areanotclear", "Area Blocked");
		addGuiLabel("quarry.noring", "No Ring");
		addGuiLabel("quarry.missinghead", "No Drill Head");
		addGuiLabel("quarry.noerrors", "None");
		addGuiLabel("quarry.miningframe", "Active Frame");
		addGuiLabel("quarry.nocoolant", "Coolant Levels");
		addGuiLabel("quarry.inventoryroom", "Inventory Full");

		addGuiLabel("multimeterblock.transfer", "Transfer: %s");
		addGuiLabel("multimeterblock.voltage", "Voltage: %s");
		addGuiLabel("multimeterblock.resistance", "Resistance: %s");
		addGuiLabel("multimeterblock.loss", "Loss: %s");
		addGuiLabel("multimeterblock.minvoltage", "Min Voltage: %s");

		addGuiLabel("networkwattage", "Wattage");
		addGuiLabel("networkvoltage", "Voltage");
		addGuiLabel("networkampacity", "Ampacity");
		addGuiLabel("networkminimumvoltage", "Min. Voltage");
		addGuiLabel("networkresistance", "Resistance");
		addGuiLabel("networkload", "Load");

		addGuiLabel("displayunit.infinity.name", "Infinite");

		addGuiLabel("displayunit.ampere.name", "Ampere");
		addGuiLabel("displayunit.ampere.nameplural", "Amperes");
		addGuiLabel("displayunit.ampere.symbol", "A");
		addGuiLabel("displayunit.amphour.name", "Amp Hour");
		addGuiLabel("displayunit.amphour.nameplural", "Amp Hours");
		addGuiLabel("displayunit.amphour.symbol", "Ah");
		addGuiLabel("displayunit.voltage.name", "Volt");
		addGuiLabel("displayunit.voltage.nameplural", "Volts");
		addGuiLabel("displayunit.voltage.symbol", "V");
		addGuiLabel("displayunit.watt.name", "Watt");
		addGuiLabel("displayunit.watt.nameplural", "Watts");
		addGuiLabel("displayunit.watt.symbol", "W");
		addGuiLabel("displayunit.watthour.name", "Watt Hour");
		addGuiLabel("displayunit.watthour.nameplural", "Watt Hours");
		addGuiLabel("displayunit.watthour.symbol", "Wh");
		addGuiLabel("displayunit.resistance.name", "Ohm");
		addGuiLabel("displayunit.resistance.nameplural", "Ohms");
		addGuiLabel("displayunit.resistance.symbol", "" + '\u03A9');
		addGuiLabel("displayunit.conductance.name", "Siemen");
		addGuiLabel("displayunit.conductance.nameplural", "Siemens");
		addGuiLabel("displayunit.conductance.symbol", "S");
		addGuiLabel("displayunit.joules.name", "Joule");
		addGuiLabel("displayunit.joules.nameplural", "Joules");
		addGuiLabel("displayunit.joules.symbol", "J");
		addGuiLabel("displayunit.buckets.name", "Bucket");
		addGuiLabel("displayunit.buckets.nameplural", "Buckets");
		addGuiLabel("displayunit.buckets.symbol", "B");
		addGuiLabel("displayunit.tempkelvin.name", "Kelvin");
		addGuiLabel("displayunit.tempkelvin.nameplural", "Kelvin");
		addGuiLabel("displayunit.tempkelvin.symbol", "K");
		addGuiLabel("displayunit.tempcelcius.name", "Celcius");
		addGuiLabel("displayunit.tempcelcius.nameplural", "Celcius");
		addGuiLabel("displayunit.tempcelcius.symbol", "C");
		addGuiLabel("displayunit.tempfahrenheit.name", "Fahrenheit");
		addGuiLabel("displayunit.tempfahrenheit.nameplural", "Fahrenheit");
		addGuiLabel("displayunit.tempfahrenheit.symbol", "F");
		addGuiLabel("displayunit.timeseconds.name", "Second");
		addGuiLabel("displayunit.timeseconds.nameplural", "Seconds");
		addGuiLabel("displayunit.timeseconds.symbol", "s");
		addGuiLabel("displayunit.pressureatm.name", "Atmosphere");
		addGuiLabel("displayunit.pressureatm.nameplural", "Atmospheres");
		addGuiLabel("displayunit.pressureatm.symbol", "ATM");
		addGuiLabel("displayunit.percentage.name", "Percent");
		addGuiLabel("displayunit.percentage.nameplural", "Percent");
		addGuiLabel("displayunit.percentage.symbol", "%");

		addGuiLabel("displayunit.timeticks.name", "Tick");
		addGuiLabel("displayunit.timeticks.nameplural", "Ticks");
		addGuiLabel("displayunit.timeticks.symbol", "t");

		addGuiLabel("displayunit.forgeenergyunit.name", "Forge Energy Unit");
		addGuiLabel("displayunit.forgeenergyunit.nameplural", "Forge Energy Units");
		addGuiLabel("displayunit.forgeenergyunit.symbol", "FE");

		addGuiLabel("measurementunit.pico.name", "Pico");
		addGuiLabel("measurementunit.pico.symbol", "p");
		addGuiLabel("measurementunit.nano.name", "Nano");
		addGuiLabel("measurementunit.nano.symbol", "n");
		addGuiLabel("measurementunit.micro.name", "Micro");
		addGuiLabel("measurementunit.micro.symbol", "" + '\u00B5');
		addGuiLabel("measurementunit.milli.name", "Milli");
		addGuiLabel("measurementunit.milli.symbol", "m");
		addGuiLabel("measurementunit.none.name", "");
		addGuiLabel("measurementunit.none.symbol", "");
		addGuiLabel("measurementunit.kilo.name", "Kilo");
		addGuiLabel("measurementunit.kilo.symbol", "k");
		addGuiLabel("measurementunit.mega.name", "Mega");
		addGuiLabel("measurementunit.mega.symbol", "M");
		addGuiLabel("measurementunit.giga.name", "Giga");
		addGuiLabel("measurementunit.giga.symbol", "G");

		add("keycategory.electrodynamics", "Electrodynamics");
		addKeyLabel("jetpackascend", "Ascend with Jetpack");
		addKeyLabel("togglenvgs", "Toggle Night Vision Goggles");
		addKeyLabel("jetpackmode", "Switch Jetpack Mode");
		addKeyLabel("servoleggingsmode", "Switch Servo Leggings Mode");
		addKeyLabel("toggleservoleggings", "Toggle Servo Leggings");
		addKeyLabel("swapbattery", "Swap Battery");

		addJei("guilabel.power", "%1$sV %2$skW");
		addJei("info.item.coalgeneratorfuelsource", "Coal Generator Fuel:\n    Burn Time: %s");
		addJei("info.fluid.combustionchamberfuel", "Combustion Chamber Fuel:\n    Produces: %1$s \n    Cost: %2$s.");

		addJei(ElectrolyticSeparatorRecipe.RECIPE_GROUP, "Electrolytic Separator");
		addJei(ChemicalCrystalizerRecipe.RECIPE_GROUP, "Chemical Crystalizer");
		addJei(MineralWasherRecipe.RECIPE_GROUP, "Mineral Washer");
		addJei(FermentationPlantRecipe.RECIPE_GROUP, "Fermentation Plant");
		addJei(ChemicalMixerRecipe.RECIPE_GROUP, "Chemical Mixer");
		addJei(WireMillRecipe.RECIPE_GROUP, "Wire Mill");
		addJei(ReinforcedAlloyerRecipe.RECIPE_GROUP, "Reinforced Alloyer");
		addJei(OxidationFurnaceRecipe.RECIPE_GROUP, "Chemical Furnace");
		addJei(MineralGrinderRecipe.RECIPE_GROUP, "Mineral Grinder");
		addJei(MineralCrusherRecipe.RECIPE_GROUP, "Mineral Crusher");
		addJei(LatheRecipe.RECIPE_GROUP, "Lathe");
		addJei(EnergizedAlloyerRecipe.RECIPE_GROUP, "Energized Alloyer");
		addJei("blasting", "Electric Arc Furnace");
		addJei("smelting", "Electric Furnace");
		addJei("gas_condensing", "Gas Condensation");
		addJei("gas_evaporating", "Fluid Evaporation");

		addDamageSource("electricity", "%s was electrocuted");
		addDamageSource("acceleratedbolt", "%1$s was perforated by %2$s");
		addDamageSource("plasmabolt", "%1$s was vaporized by %2$s");

		addChatMessage("guidebookclick", "Click Here");

		addSubtitle(ElectrodynamicsSounds.SOUND_HUM, "Machine Hums");
		addSubtitle(ElectrodynamicsSounds.SOUND_MINERALCRUSHER, "Hammer crushes");
		addSubtitle(ElectrodynamicsSounds.SOUND_MINERALGRINDER, "Grinding Wheel grinds");
		addSubtitle(ElectrodynamicsSounds.SOUND_ELECTRICPUMP, "Electric Pump pumps");
		addSubtitle(ElectrodynamicsSounds.SOUND_WINDMILL, "Air spins Wind Mill blades");
		addSubtitle(ElectrodynamicsSounds.SOUND_COMBUSTIONCHAMBER, "Combustion Chamber pistons fire");
		addSubtitle(ElectrodynamicsSounds.SOUND_CERAMICPLATEBREAKING, "Ceramic Plate shatters");
		addSubtitle(ElectrodynamicsSounds.SOUND_CERAMICPLATEADDED, "Ceramic Plate added");
		addSubtitle(ElectrodynamicsSounds.SOUND_RAILGUNKINETIC, "Kinetic Railgun fires");
		addSubtitle(ElectrodynamicsSounds.SOUND_RAILGUNKINETIC_NOAMMO, "Out of rods");
		addSubtitle(ElectrodynamicsSounds.SOUND_RAILGUNPLASMA, "Plasma Railgun fires");
		addSubtitle(ElectrodynamicsSounds.SOUND_RAILGUNPLASMA_NOPOWER, "Plasma Railgun has no power");
		addSubtitle(ElectrodynamicsSounds.SOUND_RODIMPACTINGGROUND, "Rod impacts ground");
		addSubtitle(ElectrodynamicsSounds.SOUND_LATHEPLAYING, "Lathe spins");
		addSubtitle(ElectrodynamicsSounds.SOUND_EQUIPHEAVYARMOR, "Equipped heavy armor");
		addSubtitle(ElectrodynamicsSounds.SOUND_HYDROELECTRICGENERATOR, "Hydroelectric generator spins");
		addSubtitle(ElectrodynamicsSounds.SOUND_SEISMICSCANNER, "Seismic Ping");
		addSubtitle(ElectrodynamicsSounds.SOUND_ELECTROLYTICSEPARATOR, "Electrical Sparks");
		addSubtitle(ElectrodynamicsSounds.SOUND_NIGHTVISIONGOGGLESON, "Night Vision Goggles activated");
		addSubtitle(ElectrodynamicsSounds.SOUND_NIGHTVISIONGOGGLESOFF, "Night Vision Goggles de-activated");
		addSubtitle(ElectrodynamicsSounds.SOUND_HYDRAULICBOOTS, "Hydraulic piston fires");
		addSubtitle(ElectrodynamicsSounds.SOUND_JETPACKSWITCHMODE, "Equipment cycles mode");
		addSubtitle(ElectrodynamicsSounds.SOUND_JETPACK, "Jetpack engine runs");
		addSubtitle(ElectrodynamicsSounds.SOUND_BATTERY_SWAP, "Battery is swapped");
		addSubtitle(ElectrodynamicsSounds.SOUND_TRANSFORMERHUM, "Transformer hums");

		addDimension(Level.OVERWORLD, "The Overworld");
		addDimension(Level.NETHER, "The Nether");
		addDimension(Level.END, "The End");

		addAdvancement("root.title", "Electrodynamics");
		addAdvancement("root.desc", "You have entered a world of electricity.");

		addAdvancement("ores.title", "Mining");
		addAdvancement("ores.desc", "Make a Wooden Pickaxe!");
		addAdvancement("rawvanadium.title", "Raw Vanadinite Crystals");
		addAdvancement("rawvanadium.desc", "Find some Raw Vanadinite Crystals!");
		addAdvancement("rawuranium.title", "Raw Uraninite Ore");
		addAdvancement("rawuranium.desc", "Find some Raw Uraninite Ore!");
		addAdvancement("rawtitanium.title", "Raw Rutile Ore");
		addAdvancement("rawtitanium.desc", "Find some Raw Rutile Ore!");
		addAdvancement("rawtin.title", "Raw Cassiterite Ore");
		addAdvancement("rawtin.desc", "Find some Raw Cassiterite Ore!");
		addAdvancement("rawthorium.title", "Raw Thorianite Ore");
		addAdvancement("rawthorium.desc", "Find some Raw Thorianite Ore!");
		addAdvancement("rawsilver.title", "Raw Argentite Ore");
		addAdvancement("rawsilver.desc", "Find some Raw Argentite Ore!");
		addAdvancement("rawlithium.title", "Raw Lepidolite Ore");
		addAdvancement("rawlithium.desc", "Find some Raw Lepidolite Ore!");
		addAdvancement("rawvanadinite.title", "Raw Vanadinite Crystals");
		addAdvancement("rawvanadinite.desc", "Find some Raw Vanadinite Crystals!");
		addAdvancement("rawlead.title", "Raw Galena Ore");
		addAdvancement("rawlead.desc", "Find some Raw Galena Ore!");
		addAdvancement("rawfluorite.title", "Raw Fluorite Crystals");
		addAdvancement("rawfluorite.desc", "Find some Raw Fluorite Crystals!");
		addAdvancement("rawchromium.title", "Raw Chromite Ore");
		addAdvancement("rawchromium.desc", "Find some Raw Chromite Ore!");

		addAdvancement("basicwiring.title", "Wiring");
		addAdvancement("basicwiring.desc", "Make your first wires!");
		addAdvancement("betterwiring.title", "Better Wiring");
		addAdvancement("betterwiring.desc", "Make some Silver wires!");
		addAdvancement("superiorwiring.title", "Superior Wiring");
		addAdvancement("superiorwiring.desc", "Make some Gold wires!");
		addAdvancement("superconductivewiring.title", "Superconductive Wiring");
		addAdvancement("superconductivewiring.desc", "Make some Superconductive wires!");

		addAdvancement("insulation.title", "Insulation");
		addAdvancement("insulation.desc", "Make some insulation!");
		addAdvancement("insulatedwiring.title", "Insulated Wiring");
		addAdvancement("insulatedwiring.desc", "Make some insulated Copper wire!");
		addAdvancement("highlyinsulatedwiring.title", "Thick Wiring");
		addAdvancement("highlyinsulatedwiring.desc", "Make some thick Copper wire!");
		addAdvancement("ceramicinsulation.title", "Ceramic Insulation");
		addAdvancement("ceramicinsulation.desc", "Make some ceramic insulation!");
		addAdvancement("ceramicinsulatedwiring.title", "Ceramic Wiring");
		addAdvancement("ceramicinsulatedwiring.desc", "Make some ceramic Copper wire!");

		addAdvancement("downgradetransformer.title", "Downgrade Transformer");
		addAdvancement("downgradetransformer.desc", "Make a Downgrade Transformer!");
		addAdvancement("upgradetransformer.title", "Upgrade Transformer");
		addAdvancement("upgradetransformer.desc", "Make an Upgrade Transformer!");
		addAdvancement("circuitbreaker.title", "Circuit Breaker");
		addAdvancement("circuitbreaker.desc", "Make a Circuit Breaker!");

		addAdvancement("coalgenerator.title", "Coal Generator");
		addAdvancement("coalgenerator.desc", "Make a Coal Generator!");
		addAdvancement("thermoelectricgenerator.title", "Thermoelectric Generator");
		addAdvancement("thermoelectricgenerator.desc", "Make a Thermoelectric Generator!");
		addAdvancement("solarpanel.title", "Solar Panel");
		addAdvancement("solarpanel.desc", "Make a Solar Panel!");
		addAdvancement("advancedsolarpanel.title", "Advanced Solar Panel");
		addAdvancement("advancedsolarpanel.desc", "Make an Advanced Solar Panel!");

		addAdvancement("batterybox.title", "Battery Box");
		addAdvancement("batterybox.desc", "Make a Battery Box!");
		addAdvancement("lithiumbatterybox.title", "Lithium Battery Box");
		addAdvancement("lithiumbatterybox.desc", "Make a Lithium Battery Box!");
		addAdvancement("carbynebatterybox.title", "Carbyne Battery Box");
		addAdvancement("carbynebatterybox.desc", "Make a Carbyne Battery Box!");

		addAdvancement("wiremill.title", "Wire Milling");
		addAdvancement("wiremill.desc", "Make a Wire Mill!");
		addAdvancement("doublewiremill.title", "Double Milling");
		addAdvancement("doublewiremill.desc", "Make a Double Wire Mill!");
		addAdvancement("triplewiremill.title", "Triple Milling");
		addAdvancement("triplewiremill.desc", "Make a Triple Wire Mill!");

		addAdvancement("electricfurnace.title", "Electric Smelting");
		addAdvancement("electricfurnace.desc", "Make an Electric Furnace!");
		addAdvancement("doubleelectricfurnace.title", "Double Smelting");
		addAdvancement("doubleelectricfurnace.desc", "Make a Double Electric Furnace!");
		addAdvancement("tripleelectricfurnace.title", "Triple Smelting");
		addAdvancement("tripleelectricfurnace.desc", "Make a Triple Electric Furnace!");

		addAdvancement("electricarcfurnace.title", "Electric Blasting");
		addAdvancement("electricarcfurnace.desc", "Make an Electric Arc Furnace!");
		addAdvancement("doubleelectricarcfurnace.title", "Double Blasting");
		addAdvancement("doubleelectricarcfurnace.desc", "Make a Double Electric Arc Furnace!");
		addAdvancement("tripleelectricarcfurnace.title", "Triple Blasting");
		addAdvancement("tripleelectricarcfurnace.desc", "Make a Triple Electric Arc Furnace!");

		addAdvancement("mineralgrinder.title", "Mineral Grinding");
		addAdvancement("mineralgrinder.desc", "Make a Mineral Grinder!");
		addAdvancement("doublemineralgrinder.title", "Double Grinding");
		addAdvancement("doublemineralgrinder.desc", "Make a Double Mineral Grinder!");
		addAdvancement("triplemineralgrinder.title", "Triple Grinding");
		addAdvancement("triplemineralgrinder.desc", "Make a Triple Mineral Grinder!");

		addAdvancement("mineralcrusher.title", "Mineral Crushing");
		addAdvancement("mineralcrusher.desc", "Make a Mineral Crusher!");
		addAdvancement("doublemineralcrusher.title", "Double Crushing");
		addAdvancement("doublemineralcrusher.desc", "Make a Double Mineral Crushing!");
		addAdvancement("triplemineralcrusher.title", "Triple Crushing");
		addAdvancement("triplemineralcrusher.desc", "Make a Triple Mineral Crusher!");

		addAdvancement("multimeter.title", "Multimetering");
		addAdvancement("multimeter.desc", "Make a Multimeter!");

		addGuidebook("title", "Electrodynamics Electric Code 1st Edition");
		addGuidebook("titlequote", "\"There is nothing more permanent than a temporary solution.\"");

		addGuidebook("availablemodules", "Available Modules");
		addGuidebook("chapters", "Chapters");

		addGuidebook(References.ID, "Electrodynamics");

		addGuidebook("chapter.gettingstarted", "Getting Started");

		addGuidebook("chapter.gettingstarted.l1",
				"Electrodynamics is a mod based around realistic electricity and more realistic concepts in general. As a result, you will find it plays very differently than other tech mods you're used to. The main difference will be that Electro "
						+ "is much more involved, as that is the price that comes with realism. Keep this in mind as you progress in the mod! If you are brand new to this mod, I highly recommend you read the section on Electricity, as it will help you "
						+ "immensely. Another important concept to note is that Electrodynamics is not designed to stand on its own. It is based on the concept of the old Universal Electricity mod, in which you have a mod with basic concepts and technology, " + "and then have several addon mods that hook into those concepts.");
		addGuidebook("chapter.gettingstarted.l2", "In terms of actually getting started with Electrodynamics, you will need Steel and a source of power. Steel is created by smelting Iron Ingots in a Blast Furnace. The first power source you will use is the Thermoelectric Generator. I highly encourage installing a mod like JEI, " + "as it will make finding recipes much easier!");

		addGuidebook("chapter.ores", "Ores");
		addGuidebook("chapter.ores.spawnrange", "Y = %1$s to Y = %2$s");
		addGuidebook("chapter.ores.material", "Material: %s");
		addGuidebook("chapter.ores.veinsperchunk", "Veins per Chunk: %s");
		addGuidebook("chapter.ores.veinsize", "Vein Size: %s");
		addGuidebook("chapter.ores.miningteir", "Mining Teir: %s");
		addGuidebook("chapter.ores.material_aluminum", "Aluminum");
		addGuidebook("chapter.ores.material_chromium", "Chromium");
		addGuidebook("chapter.ores.material_fluorite", "Fluorite");
		addGuidebook("chapter.ores.material_salt", "NaCl");
		addGuidebook("chapter.ores.material_lead", "Lead");
		addGuidebook("chapter.ores.material_lithium", "Lithium");
		addGuidebook("chapter.ores.material_molybdenum", "Molybdenum");
		addGuidebook("chapter.ores.material_monazite", "Monazite");
		addGuidebook("chapter.ores.material_niter", "Niter");
		addGuidebook("chapter.ores.material_titanium", "Titanium");
		addGuidebook("chapter.ores.material_silver", "Silver");
		addGuidebook("chapter.ores.material_sulfur", "Sulfur");
		addGuidebook("chapter.ores.material_sylvite", "KCl");
		addGuidebook("chapter.ores.material_thorium", "Thorium");
		addGuidebook("chapter.ores.material_tin", "Tin");
		addGuidebook("chapter.ores.material_uranium", "Uranium");
		addGuidebook("chapter.ores.material_vanadium", "Vanadium");

		addGuidebook("chapter.metricprefixes", "Metric Prefixes");

		addGuidebook("chapter.metricprefixes.l1", "Electrodynamics makes use of several Metric System unit prefixes when displaying units. Prefixes are used to help condense values into a more concise and readable format. While you are possibly familiar with some or all of them, below is a list of prefixes and their numerical " + "meanings:");

		addGuidebook("chapter.metricprefixes.pico", "Pico (p) : 1 / 1000000000000");
		addGuidebook("chapter.metricprefixes.nano", "Nano (n) : 1 / 1000000000");
		addGuidebook("chapter.metricprefixes.micro", "Micro (" + '\u00B5' + ") : 1 / 1000000");
		addGuidebook("chapter.metricprefixes.mili", "Mili (m) : 1 / 1000");

		addGuidebook("chapter.metricprefixes.kilo", "Kilo (k) : 1000");
		addGuidebook("chapter.metricprefixes.mega", "Mega (M) : 1000000");
		addGuidebook("chapter.metricprefixes.giga", "Giga (G) : 1000000000");
		addGuidebook("chapter.metricprefixes.tera", "Tera (T) : 1000000000000");

		addGuidebook("chapter.metricprefixes.l2", "This prefixes will be used extensively throughout the mod, so it is important to at least familiarize yourself with the existance of this list in case you need to reference it again.");

		addGuidebook("chapter.electricity", "Electricity");

		addGuidebook("chapter.electricity.l1", "Understanding how energy and electricity work is key if you want to do well in Electrodynamics. This chapter will cover the following topics:");

		addGuidebook("chapter.electricity.topic.header", "%1$s. %2$s");
		addGuidebook("chapter.electricity.topic.electricbasics", "Electricity Basics");
		addGuidebook("chapter.electricity.topic.machinefundamentals", "Machine Fundamentals");
		addGuidebook("chapter.electricity.topic.wirebasics", "Wire Basics");
		addGuidebook("chapter.electricity.topic.wireinsulation", "Wire Insulation");
		addGuidebook("chapter.electricity.topic.wiremodification", "Wire Modification");
		addGuidebook("chapter.electricity.topic.transformers", "Transformers");
		addGuidebook("chapter.electricity.topic.gridconcepts", "Grid Concepts");
		addGuidebook("chapter.electricity.topic.gridtools", "Grid Tools");
		addGuidebook("chapter.electricity.topic.onfe", "On Forge Energy Units (FE)");
		addGuidebook("chapter.electricity.topic.summary", "Summary");

		addGuidebook("chapter.electricity.l2.1", "Machines require energy to do work, and this energy is measured in units of Joules, denoted with a capital J. However, the energy needs to be \"flowing\" in order for it to actually do work. The speed of the flow is measured in units of Volts, which are denoted with a capital V. The volume of "
				+ "the flow is measured in units of Amperes (Amps for short), and is denoted with a capital I or A. The amount of energy transfered in one second is known as the Power and is measured in units of Watts, denoted with a capital W or P. Power can easily be found through following the formula:");
		addGuidebook("chapter.electricity.powerformula", "P = I * V");
		addGuidebook("chapter.electricity.l2.2", "where P is the Power, I is the current, and V is the voltage.");

		addGuidebook("chapter.electricity.l3.1", "Now that we understand the basics of electricity, it is time to cover how electricity will actually be interacted with in the mod. As covered previously, machines require flowing energy to do work. However, not all machines are the same, and different machines will require "
				+ "different voltages for the type of work they perform. This voltage will usually be displayed in a tooltip when you hover over the machine in your inventory:");

		addGuidebook("chapter.electricity.l3.2", "However, if the tooltip does not provide this information, or if you do not have a spare machine to mouse over, there are a couple of methods of checking.");

		addGuidebook("chapter.electricity.l4", "The first method is to look at the machine in question when placed in the world. Most machines have a set of colored diagonal lines, with the color being directly related to the voltage. The following voltages are represented by the following colors:");

		addGuidebook("chapter.electricity.voltageval", "%1$s V : %2$s");
		addGuidebook("chapter.electricity.yellow", "Yellow");
		addGuidebook("chapter.electricity.blue", "Blue");
		addGuidebook("chapter.electricity.red", "Red");
		addGuidebook("chapter.electricity.purple", "Purple");

		addGuidebook("chapter.electricity.l5", "The following pages contain examples of machines with these markings:");

		addGuidebook("chapter.electricity.voltageexample", "Examples of %sV machines:");
		addGuidebook("chapter.electricity.left", "Left: %s");
		addGuidebook("chapter.electricity.middle", "Middle: %s");
		addGuidebook("chapter.electricity.right", "Right: %s");
		addGuidebook("chapter.electricity.voltageexamplenote", "Note the markings are not always at the base.");

		addGuidebook("chapter.electricity.l6", "The second method for checking a machine's voltage is to look at the voltage displayed by the energy tooltip in it's GUI. While the previous methods do not always provide the needed information, the tooltip in the GUI will always provide the correct voltage for a machine. This "
				+ "tooltip will also tell you useful information such as the wattage (power use) of the machine. The next page contains examples of this tooltip:");

		addGuidebook("chapter.electricity.guivoltagenote", "Note the other data points the tooltip can provide.");

		addGuidebook("chapter.electricity.l7", "However, not every machine has markings such as the Quarry or has a GUI like the Pump, so it's important to keep track of the voltages you are working with before engergizing a machine. Too low of a voltage, and a machine will consume energy but won't run. Too high of a " + "voltage, and the machine will explode!");

		addGuidebook("chapter.electricity.l8", "Now that we have a basic understanding of how voltage will be interacted with, it's time to understand how to get energy into the machine. All machines that use energy will have an I/O port to interface with for energy transfer. However, unlike the voltage indicators discussed "
				+ "previously which have the potential to vary, these ports are universal to all machines that use energy, and will look the same on every single machine. There are two ports for energy:");

		addGuidebook("chapter.electricity.energyinput", "Red : Input");
		addGuidebook("chapter.electricity.energyoutput", "Grey : Output");

		addGuidebook("chapter.electricity.l9", "Here is an example of each:");

		addGuidebook("chapter.electricity.l10", "However, what is connected to these ports? Now it's time to discuss how electricity is transfered to and from machines: %s. Wires in this mod function a little differently than what you're used to. For starters, wires do not store energy. Furthermore, if you hover over a wire "
				+ "in your inventory, you will notice it has three fields: Resistance, Ampacity, and Insulation Rating.");

		addGuidebook("chapter.electricity.wires", "Wires");

		addGuidebook("chapter.electricity.l11.1",
				"%1$s is a measure of how opposed a material is to the flow of electricity, and is measured in units of Ohms, denoted by a capital Omega (" + '\u03A9' + "). The Resistance of a wire will determine how much energy is lost when a current is developed over it. You can use the " + "formula %2$s to to calculate the exact amount of power lost over a wire. "
						+ "However, the resistance listed for a wire is only for a single wire, and machines tend to be more than one block away from each other. Thus a crucial concept is introduced, in that the longer wires are, the higher their overall resistance. In simpler terms, longer wires have a greater power loss. However, if you analyze "
						+ "the equation, you will observe the resistance is only a linear factor where as the current is squared. Thus power loss over a wire is affected far more by the current flowing over it than the resistance of the wire itself. It is crucial to keep this in mind when designing complex wire layouts, as you might "
						+ "lose a great deal of usable energy to the wires before it even reaches the machine.");

		addGuidebook("chapter.electricity.resistance", "Resistance");

		addGuidebook("chapter.electricity.l11.2", "The %s of a wire describes how high a current the wire can experience before it fails. If the current in a wire exceeds its ampacity, the wire runs the risk of permanent damage and/or destruction!");
		addGuidebook("chapter.electricity.ampacity", "Ampacity");
		addGuidebook("chapter.electricity.l11.3", "However, not all hope is completely lost for the wire if you introduce a higher current than it is rated for. Wires can withstand a current higher than their rated ampacity for %s ticks. While not a very long period of time, it gives you the chance to save the wire!");

		addGuidebook("chapter.electricity.l11.4", "The %s of a wire determines how high a voltage the insulation of a conductor can handle before breakdown occurs, and the energy is able to short through the insulation. Uninsulated wires can shock you amongst other items that will be covered here shortly.");
		addGuidebook("chapter.electricity.insulation", "Insulation Rating");

		addGuidebook("chapter.electricity.l12.1", "The topic of insulation is deceptively simple and is worth an in-depth look at this point. Insulation has more factors to consider than just a voltage rating when selecting an insulation type to use. These factors will now be discussed at length.");
		addGuidebook("chapter.electricity.l12.2", "As discussed, an under or uninsulated wire can shock you. However, this is not the be-all end-all factor for a wire's insulation. You can mitigate the danger of being shocked by wearing %s. You will still take some damage if you are shocked while wearing them, however you will "
				+ "stand a far higher chance of survival. It should be noted this survival chance comes at the price of boot durability, so the best practice is still to not touch an exposed wire in the first place.");

		addGuidebook("chapter.electricity.l13.1", "Different insulation types also come with pros and cons. Wool-based insulation is cheap, easy to come by, and highly effective:");
		addGuidebook("chapter.electricity.l13.2", "A specialzed variant of a wool-insulated wire is the Logistical Wire:");
		addGuidebook("chapter.electricity.l13.3", "It will emit a redstone signal when power is flowing through it.");
		addGuidebook("chapter.electricity.l13.4", "Ceramic insulation on the other hand is more expensive than wool, but gains the advantage of being fire-proof:");
		addGuidebook("chapter.electricity.l13.5", "This makes ceramicly insulated wires especially useful when working around fluids like lava. It should be noted though that ceramic insulation is not as effective as " + "woolen insulation, meaning that if you want to insulate high voltages, you will need to take the risk of fire!");

		addGuidebook("chapter.electricity.l16", "Wires have the ability to be field-modified once placed in the world. If you right-click any insulated wire with Shears (excluding thick wires), it will remove the insulation from the wire. You can also apply woolen and ceramic insulation to an existing wire "
				+ "by right-clicking the insulation on the wire. A woolen wire can be converted to a logistical wire by right-clicking a piece of redstone on it. Y");

		addGuidebook("chapter.electricity.l17.1", "Now that we know how to get energy to a machine and understand it must be at a specific voltage, you're probably wondering how that voltage is achieved. Most power sources in Electrodynamics are either 120 V or 240 V. This works well for many of the basic machines, but simply won't "
				+ "cut it for more advanced machines that require higher voltages. This is where transformers come in. Transformers function by exchanging voltage for current. This exchange rate can be calculated using what is known as the Turns Ratio, which is given by the formula:");

		addGuidebook("chapter.electricity.turnsratioformula", "N = Np / Ns");

		addGuidebook("chapter.electricity.l17.2", "Where N is the Turns Ratio, Np is the number of primary or input turns, and Ns is the number of secondary or output turns. The output voltage can be calculated by dividing the input voltage by the Turns Ratio. The output current can be calculated by multiplying the input " + "current by the Turns Ratio.");
		addGuidebook("chapter.electricity.l17.3",
				"Electrodynamics offers two transformer types: Upgrade and Downgrade. The %1$s Transformer steps up voltage, and the %2$s Transformer steps down voltage. The base upgrade variant has a fixed Turns Ratio of 0.5, and the base downgrade variant has a fixed Turns Ratio "
						+ "of 2. While cheap and effective, these base units will shock you when touched if they are live.");

		addGuidebook("chapter.electricity.upgrade", "Upgrade");
		addGuidebook("chapter.electricity.downgrade", "Downgrade");

		addGuidebook("chapter.electricity.l18.1",
				"Now that we understand the basics of electricity, how it will be interacted with, and how it can be manipulated, we can start to piece the different aspects together. This combination of aspects will be referred to as a %1$s moving forward. Operating machines in a grid introduces " + "more complex scenarios that analyzing individual components in a vacuum may overlook.");

		addGuidebook("chapter.electricity.grid", "Grid");

		addGuidebook("chapter.electricity.l18.2", "To construct a grid, we must first understand how Electrodynamics' electrical model functions. Electrodynamics assumes that all power sources are agregated into one large power source. It then assumes all loads are in series. This can be modeled with the following circuit diagram:");

		addGuidebook("chapter.electricity.l18.3", "This model is not perfect, however it gets the job done, and is about as acurate as you can get without a complex system model using software such as SPICE. Knowing how wire networks are modeled will prove valuable moving forward.");

		addGuidebook("chapter.electricity.l18.4", "The first of the more complex concepts that will be introduced is known as Return Current or %1$s. In short, the neutral current means you will lose twice the amount of energy to wire resistance you will think you will lose. While this may sound strange, a detailed examination of how current works "
				+ "will reveal a logical answer. An electrical current requires a return path to its source in order to obey the laws of entropy, that is to say the net electrical charge is zero. In real life, this is accomplished by having a Primary conductor and a Neutral conductor, as can be seen from this image here:");

		addGuidebook("chapter.electricity.neutralcurrent", "Neutral Current");

		addGuidebook("chapter.electricity.neutralwirenote", "A Single Phase Power Line. Primary Conductor (top), Neutral Conductor (middle), Communication Cable (bottom)");

		addGuidebook("chapter.electricity.l18.5", "The Primary Conductor provides the path for the current to the load, and the Neutral Conductor provides the return path for the current to its source. Electrodynamics does not render a Neutral Conductor, however it assumes that Neutral Conductor is the same length as the "
				+ "Primary Conductor, that is to say the wire you place. Thus the total power loss due to resistance can be calculated as %s. As an aisde, in real life there are techniques that can be used to reduce neutral current, however these techniques are beyond the scope of what Electodynamics is capable of.");

		addGuidebook("chapter.electricity.neutralloss", "P = 2 * R * I * I");

		addGuidebook("chapter.electricity.l18.6",
				"Before we proceed to the next complex topic, I would like to address an elephant in the room. You may be wondering: \"How is it possible that the energy returning to the power source is a net of zero if energy is lost on the way to resistance and also used by a power source.\" The first "
						+ "Law of Thermodynamics states that energy can neither be created nor destroyed, only transformed. This applies to electrons as well. If you recall to the first section of this chapter, Voltage was described as the speed of the electricity. The property that will be affected by resistance and load is actually "
						+ "the Voltage. Resistance and load can be thought of as friction slowing the electricity down, the same way a car will eventually come to a stop if you stop accelerating it. This leads to a phenomenon in the real world known as \"Voltage Drop\". Voltage Drop can be a singificant challenge in power delivery, as "
						+ "the load you are serving might have too low of a voltage to run. Fortunately however, Electrodynamics does not simultate Voltage Drop for simplicity's sake.");

		addGuidebook("chapter.electricity.l18.7", "The next complex topic that will be covered is known as %1$s. All Electrodynamics machines that use energy have a small internal storage buffer. As they do not have an energy input cap, the empty buffer is seen in its entirety when connected to a live wire. As a result, the " + "current for the device will look akin to the following:");

		addGuidebook("chapter.electricity.inrushcurrent", "Inrush Current");

		addGuidebook("chapter.electricity.l18.8",
				"In short, the current will briefly spike and quickly approach its steady state level as the buffer is charged. Steady State current is the current you would expect the machine to draw. This buffer is "
						+ "provided to help insulate machines against lag, allowing them to keep running if a tick is skipped by the server. The buffer also coincidentally models a real-world phenomenon, in that all electrical devices store energy. You may find this an odd statement. How can energy be stored without a battery? The "
						+ "answer is that energy is stored in Electrical and Magnetic fields. While the amount is insignificant to the amount of energy that can stored by a battery, it still must be accounted for.");

		addGuidebook("chapter.electricity.l18.9", "Inrush Current will mostly not affect you fortunately. As discussed previously, while wires have a maximum ampacity that cannot be exceeded, they can handle a brief overload for %s ticks before actually failing. 9 times out of 10 this will not be an issue, however it can become a problem if "
				+ "you have a prolonged inrush that exceeds this time limit. This inrush may also falsely trigger protective devices to trip!");

		addGuidebook("chapter.electricity.l19.1",
				"By now, you are feeling overwhelmed most likely. How are you supposed to keep track of what voltage your grid is operating at? How are you supposed to keep track of the overall resistance? Fear not, for Electrodynamics offers several tools and blocks to assist with this, " + "along with other tools you may find useful. These will be covered on the following pages.");

		addGuidebook("chapter.electricity.l19.handheldmultimeter.1", "The Handheld Multimeter allows you to view multiple data points for a wire grid. Right-clicking any wire conneted to a grid will display several imporant data points about that grid:");
		addGuidebook("chapter.electricity.l19.handheldmultimeter.2", "The first data point is the current power being transmitted in Amps in proportion to the Ampacity of the wire network. The second data point shows the current operating voltage of the wire network. The third data point shows the current power transfer in watts. "
				+ "The fourth data point shows the total resistance of the network, with the fifth data point showing the power loss due to resistance. The final data point shows the lowest voltage machine connected to the network. Note this chat message will disappear after some time!");

		addGuidebook("chapter.electricity.l19.multimeterblock.1", "The Multimeter Block offers the same functionality as the hand-held variant, but will display the relevant data constantly.");

		addGuidebook("chapter.electricity.l19.circuitbreaker.1", "The Circuit Breaker is a multipurpose block. It is able to act as a manual switch, opening when receiving a redstone and interrupting load. It will also open automatically if it senses that the transmitting voltage will damage a machine or if the transmitting current will damage " + "a wire or machine:");
		addGuidebook("chapter.electricity.l19.circuitbreaker.2", "However, this protective nature comes at a price, as the Circuit Breaker has a small power loss. This means you will need to be somewhat thoughtful with your use of them!");

		addGuidebook("chapter.electricity.l20",
				"Electrodynamics represents its energy in real-world quantities (Joules) as discussed in section 1 of this chapter. As a result, Electrodynamics machines cannot directly use Forge Energy Units (FE). However, this does not mean Electrodynamics cannot interact with FE. Wires are able to "
						+ "power FE machines with Joules, and the conversion rate is one to one. However Electrodynamics assumes all FE machines are 120 V rated. This means if you power a Pulverizer from Thermal Expansion with a 240 V power source, it will explode! Battery Boxes are also able to accept FE as well as emit it. However, keep in mind if the output voltage is not 120 V, "
						+ "then the FE device will explode!");

		addGuidebook("chapter.electricity.l21", "In summary, machines need energy at a specific voltage to work. There are multiple methods of finding this voltage. Machines have specific colored ports for energy input and output. Energy is transfered into machines using wires, with the type of wire "
				+ "used determining how the cable network performs. Voltages can be stepped up and stepped down using transformers. There are multiple methods for monitoring and controling a wire network. The next page contains a list of symbols and formulas for you to reference..");

		addGuidebook("chapter.electricity.symbols", "Symbols:");
		addGuidebook("chapter.electricity.symbvoltage", "Voltage : V");
		addGuidebook("chapter.electricity.symbcurrent", "Current : A or I");
		addGuidebook("chapter.electricity.symbresistance", "Resistance : " + '\u03A9');
		addGuidebook("chapter.electricity.symbpower", "Power : W or P");
		addGuidebook("chapter.electricity.symbenergy", "Energy : J or E");
		addGuidebook("chapter.electricity.symbturnsratio", "Turns Ratio : N");

		addGuidebook("chapter.electricity.equations", "Equations:");
		addGuidebook("chapter.electricity.powerfromenergy", "P = E / time");
		addGuidebook("chapter.electricity.powerfromvoltage", "P = V * I");
		addGuidebook("chapter.electricity.energytickstoseconds", "E/s = (E/tick) * 20");
		addGuidebook("chapter.electricity.ohmslaw", "V = I * R");
		addGuidebook("chapter.electricity.powerfromcurrent", "P = I * I * R");

		addGuidebook("chapter.fluids", "Fluids");

		addGuidebook("chapter.fluids.l1", "Fluids play an important role just like electricity in Electrodynamics. They're used for crafting various materials and for cooling machinery. Fortunately, if you have been able to grasp Electricity, then fluid mechanics should be a breeze, as they are very similar to how other mods work. This chapter will cover the following topics:");

		addGuidebook("chapter.fluids.topic.header", "%1$s. %2$s");
		addGuidebook("chapter.fluids.topic.fluidlist", "List of Fluids");
		addGuidebook("chapter.fluids.topic.fluidio", "Fluid IO");
		addGuidebook("chapter.fluids.topic.fluidpipes", "Fluid Pipes");
		addGuidebook("chapter.fluids.topic.fluidtools", "Fluid Tools");
		addGuidebook("chapter.fluids.topic.fluidgui", "Fluid GUIs");

		addGuidebook("chapter.fluids.l2.1", "Electrodynamics adds the following fluids:");
		addGuidebook("chapter.fluids.l2.2", "These fluids are used for various purposes throught the mod.");

		addGuidebook("chapter.fluids.l3.1", "As with energy, fluids have their own I/O ports. These ports are universal to any machine that uses or produces fluid. They are:");
		addGuidebook("chapter.fluids.fluidinput", "Input: Blue");
		addGuidebook("chapter.fluids.fluidoutput", "Output: Yellow");
		addGuidebook("chapter.fluids.l3.2", "Here are some examples:");

		addGuidebook("chapter.fluids.l4", "However, what do we hook up to these ports? The answer is simple: %s! Unlike other mods, pipes in Electrodynamics have no internal storage buffer, which means they will not transfer a fluid unless it has somewhere to go. This means you don't have to worry about a machine outputting "
				+ "a fluid if you hook up a pipe accidentally. Also, Electrodynamics machines will only accept fluids they can process with! Pipes have a limited transfer rate similar to the ampacity of wires. However, unlike wires, they will not explode if this limit is reached. The downside though is that your choice of pipe " + "is limited to the following throughput capacities:");

		addGuidebook("chapter.fluids.pipes", "Pipes");

		addGuidebook("chapter.fluids.pipecapacity", "%1$s : %2$s mB ");
		addGuidebook("chapter.fluids.pipecopper", "Copper");
		addGuidebook("chapter.fluids.pipesteel", "Steel");

		addGuidebook("chapter.fluids.l5.1", "Electrodynamics offers several fluid tools and a few specialized fluid pipes that can be used to enhance fluid control. These will be covered on the next few pages.");

		addGuidebook("chapter.fluids.l5.reinforcedcanister.1", "Most Electrodynamics fluids cannot be carried by a Vanilla bucket. For example, it simply doesn't make sense to transfer Sulfuric Acid in a bucket made out of Iron. If you need to manually move fluids such as Sulfuric Acid, you will need to use a Reinforced " + "Canister.");

		addGuidebook("chapter.fluids.l5.fluidvoid.1", "The Fluid Void deletes all fluids that are piped into it. It is also able to manually accept fluids from buckets via its GUI. For reference, the Fluid Void is able to accept up to 128 B at a time.");

		addGuidebook("chapter.fluids.l5.fluidtank.1", "Fluid Tanks are Electrodynamics' solution to bulk fluid storage. They accept fluid from the top and output fluid through the bottom. Furthermore, stack two tanks on top of eachother, and the top one will automatically output into the bottom one.");

		addGuidebook("chapter.fluids.fluidtanks", "Fluid Tanks");

		addGuidebook("chapter.fluids.l6", "Should you ever find yourself having input fluid into a machine that you didn't mean to, all hope is not lost. You can click on the input fluid gauge(s) with a bucket or reinforced canister, and the fluid will be extracted from the tank and into the fluid container. Note this does not work "
				+ "for output fluid gauges, as there is already a bucket slot for fluids to be drained into automatically.");

		addGuidebook("chapter.gases", "Gases");

		addGuidebook("chapter.gases.l1", "While fluids in Electrodynamics may be similar to what you have seen before in other mods, gases are very much more complex and in depth. However, if you pay attention to a few key items, you will find that working with gases can be fairly painless and easy to do. This chapter will cover the following topics:");

		addGuidebook("chapter.generators", "Generators");
		addGuidebook("chapter.generators.l1", "Electrodynamics offers several power sources for you to consider. Each has its pros and cons, so pick and choose the ones that suit you. It should be notes that all fuel-burning generators can be turned off with a redstone signal.");
		addGuidebook("chapter.generators.generationbase", "Default: %1$s at %2$sV");
		addGuidebook("chapter.generators.generationupgrade", "Upgraded: %1$s at %2$sV");
		addGuidebook("chapter.generators.tips", "Tips:");
		addGuidebook("chapter.generators.tipsolarweather", "*Rain reduces production");
		addGuidebook("chapter.generators.tipsolartemperature", "*Hotter biomes produce more power");
		addGuidebook("chapter.generators.tipminy", "Min Y: %s");
		addGuidebook("chapter.generators.tipidealy", "Ideal Y: %s");
		addGuidebook("chapter.generators.tiptemprange", "Temp: %1$s C < T < %2$s C");
		addGuidebook("chapter.generators.tipidealtemp", "Ideal Temp: %s C");
		addGuidebook("chapter.generators.fuels", "Fuels:");
		addGuidebook("chapter.generators.burntime", "Burn Time: %s");
		addGuidebook("chapter.generators.use", "Use:");
		addGuidebook("chapter.generators.heatsource", "Heat Sources:");
		addGuidebook("chapter.generators.multiplier", "Multiplier: x%s");
		addGuidebook("chapter.generators.upgrades", "Upgrades:");

		addGuidebook("chapter.generators.thermoelectricgeneratoruse", "The heat source is placed at the back of the machine");
		addGuidebook("chapter.generators.combustionchamberburn", "Usage/burn: %s mB");

		addGuidebook("chapter.machines.basespecs", "Base: %1$s kW at %2$s V");
		addGuidebook("chapter.machines.maxspecs", "Max: %1$s kW at %2$s V");
		addGuidebook("chapter.machines.wind", "Y=319: 1.16 kW, 120 V");
		addGuidebook("chapter.machines.windu", "Y=319, U: 2.61 kW, 120 V");
		addGuidebook("chapter.machines.coalgentemp", "Temp: 2500 C");
		addGuidebook("chapter.machines.thermoheatsource", "Heat Source: Lava");
		addGuidebook("chapter.machines.combfuel", "Fuel: Ethanol, Hydrogen");
		addGuidebook("chapter.machines.thermoname", "Thermoelectric Gen.");
		addGuidebook("chapter.machines.hydroname", "Hydroelectric Gen.");

		addGuidebook("chapter.machines", "Machines");

		addGuidebook("chapter.machines.inventoryio.header", "Inventory IO");

		addGuidebook("chapter.machines.inventoryio.1", "Machines in Electrodynamics that process with items have dedicated sides of the machine that will either accept or output said items. Machines that fall into this category will have the following data tab:");
		addGuidebook("chapter.machines.inventoryio.2", "Hovering over the tab will display the following prompt suggesting that you click the tab:");
		addGuidebook("chapter.machines.inventoryio.3", "Clicking the tab will cause the machine's GUI to display the slots that are mapped to the different sides of said machine. You can hover over the individual sides as well to see which side is represented:");
		addGuidebook("chapter.machines.inventoryio.4", "If you are finished, you can either press the data tab again to toggle the display, or exit out of the GUI and re-open it:");
		addGuidebook("chapter.machines.inventoryio.5", "It should be noted that you are as of now unable to modify which slots are mapped to which sides of the machine.");

		addGuidebook("chapter.machines.charger.header", "Understanding the Charger:");
		addGuidebook("chapter.machines.charger.1", "The Charger does what the name suggests and charges items. When charging an item, it's crucial to select a Charger with the correct voltage. If the charger's voltage is less than the item's, the item will only be charged to a percentage of it's full charge. This can be " + "calculated by the formula:");
		addGuidebook("chapter.machines.chargeformula", "V(charger) / V(item)");

		addGuidebook("chapter.machines.charger.2", "If the charger's voltage is greater than the item's, it will explode!");
		addGuidebook("chapter.machines.charger.3", "It is possible to operate a charger with battery power using the 3 battery slots:");

		addGuidebook("chapter.machines.charger.4", "The charge of the item placed in one of these slots will be transfered to the item in the charging slot. Along with actual batteries, any item with a charge can be used! However, be mindful of the item's voltage. If it is less than the voltage of the charger, it "
				+ "will be reduced to a pile of Slag! If the item's voltage is greater than the charger's, the charger will explode!");

		addGuidebook("chapter.quarry", "The Quarry");

		addGuidebook("chapter.quarry.l1", "The Quarry is Electrodynamics' way of providing auto-mining. It takes inspiration from Buildcraft's Quarry and expands upon what it can do. This short walkthrough will provide you the steps on how to set up and operate one yourself.");

		addGuidebook("chapter.quarry.step", "Step %s:");

		addGuidebook("chapter.quarry.step1l1", "Set up a ring of 4 Seismic Markers with one Marker at each corner. The ring must be a minimum of 3x3 and has a default maximum size of 66x66. Placing a redstone signal next to a marker will display guide lines.");

		addGuidebook("chapter.quarry.step2l1", "Place a Seismic Relay down at the outside of one of the corners next to a Seismic Marker. If done correctly, you will hear the sound of an Anvil. The Seismic Markers will also then be collected by the Relay and placed in its inventory.");

		addGuidebook("chapter.quarry.step3l1", "Place a Quarry on either the left or the right side of the Relay. The Quarry must be facing the same direction as the Relay.");

		addGuidebook("chapter.quarry.step4l1", "Place a Coolant Resavoir on top of the Quarry. The Quarry needs a constant supply of Water to remain cool. The faster it runs, the more water it will use!");

		addGuidebook("chapter.quarry.step5l1", "Place a Motor Complex on the side of the Quarry opposite the Seismic Relay. The green port of the Relay must be facing the green port of the Quarry. The Motor Complex controls the speed of the Quarry. It accepts either Basic or Advanced Speed Upgrades, "
				+ "and has a maximum speed of 1 Block/tick. This can be achieved with 6 Advanced upgrades, but comes with a heafty power consumtion!");

		addGuidebook("chapter.quarry.step6l1", "Power the Quarry and the Motor Complex; both use 240V. The Quarry itself uses power to perform tasks such as clearing amd maintaining the mining ring. The upgrades placed in the Quarry also have a direct impact on its power usage. Note that if blocks are in the way when "
				+ "the Quarry starts clearing the ring, they will be removed without drops! When the ring is finished, the mining arm will deploy.");

		addGuidebook("chapter.quarry.step7l1", "Open the Quarry GUI. In order to begin mining blocks, the Quarry will need a Drill Head. There are multiple types with varying durabilities:");

		addGuidebook("chapter.quarry.drillhead", "%1$s : %2$s");
		addGuidebook("chapter.quarry.steelhead", "Steel");
		addGuidebook("chapter.quarry.stainlesshead", "Stainless");
		addGuidebook("chapter.quarry.hslahead", "HSLA Steel");
		addGuidebook("chapter.quarry.titaniumhead", "Titanium");
		addGuidebook("chapter.quarry.carbidehead", "Carbide");
		addGuidebook("chapter.quarry.infinitedurability", "Infinite");

		addGuidebook("chapter.quarry.step7l2",
				"Take the Drill Head of your choice and place it in the bottom left slot of the Quarry GUI. Now direct your attention to the 3 upgrade slots in the GUI. The Quarry itself will not passively use power after setup. However, if you add upgrades, it will begin to, and depending on the " + "upgrade, this can be a substantial amount! Your 4 upgrade options are:");

		addGuidebook("chapter.quarry.step7unbreaking", "The %s reduces the durability used from the Drill Head when a block is mined. A maximum of 3 can be used for an effect of Unbreaking III, and it will cause the Quarry to draw a large amount of power when installed.");
		addGuidebook("chapter.quarry.step7fortune", "The %s applies the Fortune enchantment to the blocks mined by the Quarry. A maximum of 3 can be used for an effect of Fortune III and it cannot be used in tandem with the Silk Touch Upgrade. The upgrade draws a large amount of power.");
		addGuidebook("chapter.quarry.step7silktouch", "The %s will apply the Silk Touch enchantment to the blocks mined by the Quarry. A maximum of 1 can be used and it cannot be used in tandem with the Fortune Upgrade. The upgrade will cause the Quarry to draw a huge amount of power.");
		addGuidebook("chapter.quarry.step7void", "The %s will activate 6 otherwise hidden slots in the GUI. Any items placed in these slots will be voided by the Quarry when mined. The upgrade will cause the Quarry to start drawing its base usage.");

		addGuidebook("chapter.quarry.step8l1", "This step is optional, but highly recommended. Place a Logistical Manager in front of the Quarry, then place a chest on any remaining side of the Manager. The Logistical Manager will automatically transfer items from the Quarry's inventory into an attached chest. "
				+ "Furthermore, if you place a Drill Head in one of the attached chests, it will automatically be transfered to the Quarry when the existing one breaks.");

		addGuidebook("chapter.upgrades", "Upgrades");

		addGuidebook("chapter.upgrades.injectorheader", "Using the Injector Upgrade:");

		addGuidebook("chapter.upgrades.l1", "The Auto-Injector Upgrade gives machines the ability to intake items from surrounding inventories. The upgrade has two modes: Default and Smart. ");
		addGuidebook("chapter.upgrades.default", "Default");
		addGuidebook("chapter.upgrades.l2", " mode will attempt to fill every input slot in order of the directions on the card. ");
		addGuidebook("chapter.upgrades.smart", "Smart");
		addGuidebook("chapter.upgrades.l3", " mode will map each input slot to each direction on the card. If there are more slots than directions, the remaining slots will be mapped to the final direction. To add a direction, Shift + Right-Click the card while facing the desired direction. To clear all " + "directions, Shift + Left-Click. To toggle Smart mode, Right-Click the card.");

		addGuidebook("chapter.upgrades.ejectorheader", "Using the Ejector Upgrade:");

		addGuidebook("chapter.upgrades.l4", "The Auto-Ejector Upgrade gives machines the ability to output items to surrounding inventories. The upgrade has two modes: Default and Smart. ");
		addGuidebook("chapter.upgrades.l5", " mode will attempt to output every slot in order of the directions on the card. ");
		addGuidebook("chapter.upgrades.l6", " mode will map each output slot to each direction on the card. If there are more slots than directions, the remaining slots will be mapped to the final direction. To add a direction, Shift + Right-Click the card while facing the desired direction. To clear all " + "directions, Shift + Left-Click. To toggle Smart mode, Right-Click the card.");
		addGuidebook("chapter.upgrades.l7", "It is important to note that indexes for Electrodynamics output slots come before biproduct slots in the inventory. For example, if there are 3 output slots and 2 biproduct slots, the card would empty all 3 output slots before it empties the 2 biproduct slots.");

		addGuidebook("chapter.tools", "Tools");

		addGuidebook("chapter.tools.ammo", "Ammo:");
		addGuidebook("chapter.tools.damage", "Damage:");

		addGuidebook("chapter.tools.roddamage", "%1$s: %2$s");
		addGuidebook("chapter.tools.steel", "Steel");
		addGuidebook("chapter.tools.stainless", "Stainless Steel");
		addGuidebook("chapter.tools.hsla", "HSLA Steel");
		addGuidebook("chapter.tools.ap", " AP*");
		addGuidebook("chapter.tools.apnote", "*ignores armor");

		addGuidebook("chapter.tools.kineticl1", "To use the Kinetic Railgun, hold the gun in one hand and the ammo type of your choice in the other.");

		addGuidebook("chapter.tools.energy", "Energy");
		addGuidebook("chapter.tools.initial", "Initial");
		addGuidebook("chapter.tools.after", "After %s");

		addGuidebook("chapter.tools.seismicl1", "The Seismic Scanner is a very useful item. It is able to scan for a selected block within a 16 block radius from the player. To use the Scanner, Right-Click to access it's GUI and insert the desired block into the scanning slot. Next, Shift + Right-Click to start a "
				+ "scan. If the Scanner is able to find the block, it will list the coordinates in its GUI and temporarily highlight the block in the world. Note, you will not see the highlight if you can't see the block itself. The Scanner has a 10s cooldown between scans.");

		addGuidebook("chapter.armor", "Armor");

		addGuidebook("chapter.armor.jetpack", "The Jetpack uses Liquid Hydrogen as fuel. It can be filled in either a Fluid Tank or directly in the Electrolyzer. The Jetpack has 4 modes: Regular, Hover, Elytra, and Off. Elytra mode allows you to glide as if you had an Elytra on, and gives you the ability to slowly "
				+ "ascend.");
		addGuidebook("chapter.armor.hydraulicboots", "Hydraulic Boots use Hydraulic Fluid as their \"fuel\". They can be filled either in a tank or directly in a Chemical Mixer. Hydraulic Boots will heavily reduce fall damage, but not completely eliminate it.");
		addGuidebook("chapter.armor.combatchestplate", "The Jetpack on the Combat Chestplate uses Liquid Hydrogen as fuel. It can be filled in either a Fluid Tank or directly in the Electrolyzer. The Jetpack has 4 modes: Regular, Hover, Elytra, and Off. Elytra mode allows you to glide as if you had an Elytra on, and gives you the ability to "
				+ "slowly ascend.");
		addGuidebook("chapter.armor.combatboots", "Combat Boots use Hydraulic Fluid as their \"fuel\". They can be filled either in a tank or directly in a Chemical Mixer. Combat Boots will heavily reduce fall damage, but not completely eliminate it.");

		addGuidebook("chapter.armor.ceramicheader", "Ceramic Plate Protection:");
		addGuidebook("chapter.armor.ceramicl1",
				"Composite and Combat armor have a unique ability: Ceramic Protection. To use this ability, you must first add Ceramic Plates to the chestplate. This is accomplished by Right-Clicking a Ceramic Plate while wearing one of the two chestplates. If sucessfull, you will hear " + "the sound of a velcro strap. A maximum of two plates can be be held by both chestplates.");

		addGuidebook("chapter.armor.ceramicl2", "For the ability to trigger, you must be wearing a full set of the armor type, and it must be a complete set. You must then take more than 16 damage. If these conditions are met, you will hear the plate break and a Ceramic Plate will be removed " + "from the chestplate. The damage dealt will then be reduced to its square root.");

		addGuidebook("chapter.misc", "Misc");

		addGuidebook("chapter.tips", "Tips");
		addGuidebook("chapter.tips.tip", "Tip %s:");
		addGuidebook("chapter.tips.tip1", "Use Energy Storage frequently. Electrodynamics has several tiers of energy storage. You should invest in using them in all stages of progression. Note, the Capacity Upgrade not only increases the capacity of a Battery Box, but also the output voltage!");

		addGuidebook("chapter.tips.tip2", "Transmit at High Voltage. This is mentioned in the Electricity chapter, but you should transmit large amounts of power at high voltage and then step it down for distribution. This will help reduce the need for heavy-duty cables as well as the " + "amount of power lost due to the cable's own resistance!");

		addGuidebook("chapter.tips.tip3",
				"If you are using an electric tool from Electrodynamics or one of its surrounding mods, there is a good chance that the tool can have its battery replaced. This can be especially useful when you need to recharge a tool and don't have ready access to a charger. A battery can be replaced in one of two ways. "
						+ "The first way is to hold the tool in question in your main hand and press the \"R\" key. A battery with a matching voltage will then be selected from your inventory and placed inside of the tool. The current battery inside the tool will be then placed in your inventory. Note, this method will use the first battery it finds in your "
						+ "inventory, which might mean the battery you want may not be the one that gets used. The second and more precise way to replace a tool's battery is to hover over the tool in question with the battery in your inventory. Then, Right-Click the tool with the battery. If the voltage of the tool matches the voltage of the battery, the "
						+ "new battery will replace the old battery, and you will be left holding the old battery.");

		addGuidebook("chapter.tips.tip4", "Holding the Control key while hoving over an upgrade slot in a GUI will display what upgrades are valid for said slot.");

		addGuidebook("searchparameters", "Parameters");
		addGuidebook("selectall", "All");
		addGuidebook("selectnone", "None");
		addGuidebook("casesensitive", "Case-Sensitive");

	}

	public void addItem(RegistryObject<Item> item, String translation) {
		add(item.get(), translation);
	}

	public void addItem(Item item, String translation) {
		add(item, translation);
	}

	public void addBlock(RegistryObject<Block> block, String translation) {
		add(block.get(), translation);
	}

	public void addBlock(Block block, String translation) {
		add(block, translation);
	}

	public void addTooltip(String key, String translation) {
		add("tooltip." + modID + "." + key, translation);
	}

	public void addFluid(Fluid fluid, String translation) {
		add("fluid." + modID + "." + ForgeRegistries.FLUIDS.getKey(fluid).getPath(), translation);
	}

	public void addGas(RegistryObject<Gas> gas, String translation) {
		addGas(gas.get(), translation);
	}

	public void addGas(Gas gas, String translation) {
		add("gas." + modID + "." + ElectrodynamicsRegistries.gasRegistry().getKey(gas).getPath(), translation);
	}

	public void addContainer(SubtypeMachine key, String translation) {
		addContainer(key.name(), translation);
	}

	public void addContainer(String key, String translation) {
		add("container." + key, translation);
	}

	public void addCommand(String key, String translation) {
		add("command." + modID + "." + key, translation);
	}

	public void addSubtitle(String key, String translation) {
		add("subtitles." + modID + "." + key, translation);
	}

	public void addSubtitle(RegistryObject<SoundEvent> sound, String translation) {
		addSubtitle(sound.getId().getPath(), translation);
	}

	public void addGuiLabel(String key, String translation) {
		add("gui." + modID + "." + key, translation);
	}

	public void addDimension(ResourceKey<Level> dim, String translation) {
		addDimension(dim.location().getPath(), translation);
	}

	public void addDimension(String key, String translation) {
		add("dimension." + modID + "." + key, translation);
	}

	public void addKeyLabel(String key, String translation) {
		add("key." + modID + "." + key, translation);
	}

	public void addJei(String key, String translation) {
		add("jei." + key, translation);
	}

	public void addDamageSource(String key, String translation) {
		add("death.attack." + key, translation);
	}

	public void addChatMessage(String key, String translation) {
		add("chat." + modID + "." + key, translation);
	}

	public void addGuidebook(String key, String translation) {
		add("guidebook." + modID + "." + key, translation);
	}

	public void addAdvancement(String key, String translation) {
		add("advancement." + modID + "." + key, translation);
	}

	public void addCreativeTab(String key, String translation) {
		add("creativetab." + modID + "." + key, translation);
	}

	public static enum Locale {
		EN_US;

		@Override
		public String toString() {
			return super.toString().toLowerCase(java.util.Locale.ROOT);
		}
	}

}
