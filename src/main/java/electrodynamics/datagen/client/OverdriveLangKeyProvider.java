package electrodynamics.datagen.client;

import electrodynamics.api.References;
import electrodynamics.common.block.subtype.SubtypeGlass;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypeOreDeepslate;
import electrodynamics.common.block.subtype.SubtypePipe;
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
import electrodynamics.registers.ElectrodynamicsBlocks;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class OverdriveLangKeyProvider extends LanguageProvider {

	private String locale;

	public OverdriveLangKeyProvider(DataGenerator gen, String locale) {
		super(gen, References.ID, locale);
		this.locale = locale;
	}

	@Override
	protected void addTranslations() {

		switch (locale) {
		case "en_us":
		default:
			
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
			addItem(ElectrodynamicsItems.GUIDEBOOK, "EEC 1st Edition");
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
			addItem(ElectrodynamicsItems.SLAG, "Metallic Slag");
			addItem(ElectrodynamicsItems.ITEM_CERAMICINSULATION, "Ceramic Insulation");
			addItem(ElectrodynamicsItems.ITEM_COIL, "Copper Coil");
			addItem(ElectrodynamicsItems.ITEM_MOLYBDENUMFERTILIZER, "Fertilizer");
			addItem(ElectrodynamicsItems.ITEM_MOTOR, "Motor");
			addItem(ElectrodynamicsItems.COAL_COKE, "Coal Coke");
			addItem(ElectrodynamicsItems.ITEM_RAWCOMPOSITEPLATING, "Raw Composite Plating");
			
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
			
			addBlock(ElectrodynamicsBlocks.FRAME, "Quarry Frame");
			addBlock(ElectrodynamicsBlocks.FRAME_CORNER, "Quarry Frame Corner");
			addBlock(ElectrodynamicsBlocks.LOGISTICAL_MANAGER, "Logistical Manager");
			addBlock(ElectrodynamicsBlocks.SEISMIC_MARKER, "Seismic Marker");
			addBlock(ElectrodynamicsBlocks.MULTI_SUBNODE, "Multiblock Subnode");
			
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
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOre.halite), "Halite Ore");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOre.lead), "Galena Ore");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOre.lepidolite), "Lepidolite Ore");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOre.molybdenum), "Molybdenite Ore");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOre.monazite), "Monazite Ore");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOre.niter), "Saltpeter Ore");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOre.rutile), "Rutile Ore");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOre.silver), "Argentite Ore");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOre.sulfur), "Sulfur Ore");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOre.sylvite), "Sylvite Ore");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOre.thorianite), "Thorianite Ore");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOre.tin), "Cassiterite Ore");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOre.uraninite), "Uraninite Ore");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOre.vanadinite), "Vanadinite Ore");
			
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOreDeepslate.aluminum), "Deep Bauxite Ore");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOreDeepslate.chromite), "Deep Chromite Ore");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOreDeepslate.fluorite), "Deep Fluorite Ore");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOreDeepslate.halite), "Deep Halite Ore");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOreDeepslate.lead), "Deep Galena Ore");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOreDeepslate.lepidolite), "Deep Lepidolite Ore");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOreDeepslate.molybdenum), "Deep Molybdenite Ore");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOreDeepslate.monazite), "Deep Monazite Ore");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOreDeepslate.niter), "Deep Saltpeter Ore");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOreDeepslate.rutile), "Deep Rutile Ore");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOreDeepslate.silver), "Deep Argentite Ore");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOreDeepslate.sulfur), "Deep Sulfur Ore");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOreDeepslate.sylvite), "Deep Sylvite Ore");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOreDeepslate.thorianite), "Deep Thorianite Ore");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOreDeepslate.tin), "Deep Cassiterite Ore");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOreDeepslate.uraninite), "Deep Uraninite Ore");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeOreDeepslate.vanadinite), "Deep Vanadinite Ore");
			
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypePipe.copper), "Copper Pipe");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypePipe.steel), "Steel Pipe");
			
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
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeWire.ceramicinsulatedtin), "Insulated Tin Wire");
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
			addContainer(SubtypeMachine.creativefluidsource, "Creative Fluid Source");
			addContainer(SubtypeMachine.creativepowersource, "Creative Power Source");
			//
			//
			//
			//
			//
			//
			addContainer(SubtypeMachine.electrolyticseparator, "Electrolytic Separator");
			addContainer(SubtypeMachine.energizedalloyer, "Energized Alloyer");
			addContainer(SubtypeMachine.fermentationplant, "Fermentation Plant");
			addContainer(SubtypeMachine.fluidvoid, "Fluid Void");
			addContainer(SubtypeMachine.hydroelectricgenerator, "Hydroelectric Generator");
			addContainer(SubtypeMachine.lathe, "Lathe");
			addContainer(SubtypeMachine.lithiumbatterybox, "Lithium Battery Box");
			//
			//
			//
			//
			//
			//
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
			//
			//
			//
			addContainer("guidebook", "Guidebook");
			

			addTooltip("energystored", "%1$s / %2$s %3$sFE");
			addTooltip("creativeenergystored", "INFINITE");
			addTooltip("matterval", "Matter: %s");
			addTooltip("potmatterval", "Potential Matter: %s");
			addTooltip("nomatter", "NONE");
			addTooltip("openmenu", "Open Menu");
			addTooltip("closemenu", "Close Menu");
			addTooltip("menuhome", "Home");
			addTooltip("menusettings", "Settings");
			addTooltip("menuupgrades", "Upgrades");
			addTooltip("matterstored", "%1$s / %2$s %3$skM");
			addTooltip("usage", "%s");
			addTooltip("usagetick", "%s/t");
			addTooltip("menuio", "I/O");
			addTooltip("ioinput", "Input");
			addTooltip("iooutput", "Output");
			addTooltip("ionone", "None");
			addTooltip("iotop", "Top");
			addTooltip("iobottom", "Bottom");
			addTooltip("ioleft", "Left");
			addTooltip("ioright", "Right");
			addTooltip("iofront", "Front");
			addTooltip("ioback", "Back");
			addTooltip("io", "%1$s (%2$s)");
			addTooltip("upgradeinfo", "Hold %s for Details");
			addTooltip("controlkey", "Ctrl");
			addTooltip("speedbonus", "Speed: %s");
			addTooltip("mattstorebonus", "Matter Storage: %s");
			addTooltip("mattusebonus", "Matter Usage: %s");
			addTooltip("failurebonus", "Failure: %s");
			addTooltip("powstorebonus", "Power Storage: %s");
			addTooltip("powusebonus", "Power Usage: %s");
			addTooltip("rangebonus", "Range: %s");
			addTooltip("mufflerupgrade", "Mutes machine sound");
			addTooltip("invaliddest", "Invalid Destination");
			addTooltip("empty", "Empty");
			addTooltip("storedpattern", "%1$s [%2$s]");
			addTooltip("has_storage_loc", "Bound: %s");
			addTooltip("no_storage_loc", "Unbound");
			addTooltip("menutasks", "Tasks");
			addTooltip("order", "Order");
			addTooltip("orderabv", "O");
			addTooltip("remainabv", "R");
			addTooltip("reporder", "%1$s: %2$s, %3$s: %4$s");
			addTooltip("noorder", "No Orders Queued");
			addTooltip("fused", "Fused");
			addTooltip("effectiveuses", "Uses: %s");
			addTooltip("hasshifttip", "Hold %s for info...");
			addTooltip("shiftkey", "Shift");
			addTooltip("fusedrive", "Fuse Drive");
			addTooltip("erasepattern", "Erase");
			addTooltip("erasedrive", "Erase Drive");
			addTooltip("fusedpattern", "Fused");
			addTooltip("fusepattern", "Fuse");
			
			addItemDescTooltip(ItemRegistry.ITEM_RAW_MATTER_DUST, "A failure, but recyclable");
			addItemDescTooltip(ItemRegistry.ITEM_MATTER_DUST, "Ready to decompose");
			addItemDescTooltip(ItemRegistry.ITEM_ION_SNIPER, "The zombie is WAY over there");
			addItemDescTooltip(ItemRegistry.ITEM_PHASER_RIFLE, "There are many zombies");
			addItemDescTooltip(ItemRegistry.ITEM_PLASMA_SHOTGUN, "There is a big zombie next to me");
			addItemDescTooltip(ItemRegistry.ITEM_PHASER, "There is one zombie plus B E A M");
			addItemDescTooltip(ItemRegistry.ITEM_OMNI_TOOL, "Like a pocket multi-tool but better");
			addItemDescTooltip(ItemRegistry.ITEM_TRANSPORTER_FLASHDRIVE, "Right-click to store a location");
			addItemDescTooltip(ItemRegistry.ITEM_PATTERN_DRIVE, "Stores up to 3 unique patterns");
			addItemDescTooltip(ItemRegistry.ITEM_MATTER_SCANNER, "A portable Matter Analyzer with perks");
			addItemDescTooltip(ItemRegistry.ITEM_ANDROID_PILL_RED, "Makes you an android");
			addItemDescTooltip(ItemRegistry.ITEM_ANDROID_PILL_BLUE, "Makes you an human for a price");
			addItemDescTooltip(ItemRegistry.ITEM_ANDROID_PILL_YELLOW, "Resets all android abilities");
			addItemDescTooltip(ItemRegistry.ITEM_COMMUNICATOR, "Link to a Transporter for on the go");
			
			for (CrateColors color : TileTritaniumCrate.CrateColors.values()) {
				addBlockItemDescTooltip(BlockRegistry.BLOCK_TRITANIUM_CRATES.get(color), "Retains items when broken");
			}
			addBlockItemDescTooltip(BlockRegistry.BLOCK_SOLAR_PANEL, "E=MC^2 if you think about it");
			addBlockItemDescTooltip(BlockRegistry.BLOCK_MATTER_DECOMPOSER, "Converts items to matter");
			addBlockItemDescTooltip(BlockRegistry.BLOCK_MATTER_RECYCLER, "Makes raw matter dust usable again");
			addBlockItemDescTooltip(BlockRegistry.BLOCK_CHARGER, "Wireless charging for androids");
			addBlockItemDescTooltip(BlockRegistry.BLOCK_MICROWAVE, "A 24th century smoker");
			addBlockItemDescTooltip(BlockRegistry.BLOCK_INSCRIBER, "Makes ciruits n' stuff");
			for(TypeMatterConduit conduit : TypeMatterConduit.values()) {
				addBlockItemDescTooltip(BlockRegistry.BLOCK_MATTER_CONDUITS.get(conduit), "XFER Limit: " + conduit.capacity + "kM");
			}
			addBlockItemDescTooltip(BlockRegistry.BLOCK_TRANSPORTER, "Say the line Kirk");
			addBlockItemDescTooltip(BlockRegistry.BLOCK_SPACETIME_ACCELERATOR, "Speeds up machines around it");
			for(TypeMatterNetworkCable cable : TypeMatterNetworkCable.values()) {
				addBlockItemDescTooltip(BlockRegistry.BLOCK_MATTER_NETWORK_CABLES.get(cable), "It's not an AE cable you guys");
			}
			addBlockItemDescTooltip(BlockRegistry.BLOCK_CHUNKLOADER, "The bane of servers");
			addBlockItemDescTooltip(BlockRegistry.BLOCK_MATTER_ANALYZER, "Scans a block's matter content");
			addBlockItemDescTooltip(BlockRegistry.BLOCK_PATTERN_STORAGE, "Holds 6 Pattern Drives");
			addBlockItemDescTooltip(BlockRegistry.BLOCK_PATTERN_MONITOR, "Queues Replication tasks");
			addBlockItemDescTooltip(BlockRegistry.BLOCK_MATTER_REPLICATOR, "Makes ordered items from matter");
			addBlockItemDescTooltip(BlockRegistry.BLOCK_DISC_MANIPULATOR, "Wipe patterns and more");
			

			addGuiLabel("redstonelow", "Low");
			addGuiLabel("redstonehigh", "High");
			addGuiLabel("redstonenone", "None");
			addGuiLabel("redstone", "Redstone");
			addGuiLabel("ioitems", "Items");
			addGuiLabel("ioenergy", "Energy");
			addGuiLabel("iomatter", "Matter");
			addGuiLabel("time", "Time: %s");
			addGuiLabel("usage", "Usage: %s");
			addGuiLabel("usagetick", "Usage: %s/t");
			addGuiLabel("failure", "Failure: %s");
			addGuiLabel("range", "Range: %s Blocks");
			addGuiLabel("storage", "Storage: %s");
			addGuiLabel("soundmuted", "Sound Muffled");
			addGuiLabel("unknown", "Unknown");
			addGuiLabel("xlabel", "X");
			addGuiLabel("ylabel", "Y");
			addGuiLabel("zlabel", "Z");
			addGuiLabel("importpos", "Import");
			addGuiLabel("resetpos", "Reset");
			addGuiLabel("dimensionname", "DIM: %s");
			addGuiLabel("multiplier", "Multiplier: %s");
			addGuiLabel("orderratio", "%1$s / %2$s");
			addGuiLabel("replicatorqueue", "Local Orders");
			addGuiLabel("systemqueue", "Global Orders");
			addGuiLabel("isreciever", "Recieve");
			addGuiLabel("notreciever", "Transmit");
			addGuiLabel("transportermode", "Function");
			

			addCommand("startmattercalc", "Starting Matter calculations...");
			addCommand("endmattercalc", "Finshed Matter calculations. Saved under \"Matter Overdrive/generated.json\"");
			addCommand("manualfailed", "unexpected error");
			addCommand("mainhandempty", "You must be holding an item");
			addCommand("assignedvalue", "Assigned %1$s kM to %2$s");
			addCommand("endmanualassign", "Saved under \"Matter Overdrive/manual.json\"");
			addCommand("startzeroscommand", "Starting Zeros Command...");
			addCommand("endzeroscommand", "Finshed writing zeros file. Saved under \"Matter Overdrive/zeros.json\"");

			addSubtitle("crate_open", "Tritanium Crate Opens");
			addSubtitle("crate_close", "Tritanium Crate Closes");
			addSubtitle("button_expand", "Button Shifts");
			addSubtitle("button_generic", "Button is pressed");
			addSubtitle("matter_decomposer", "Matter Decomposer running");
			addSubtitle("generic_machine", "Machine runs");
			addSubtitle("transporter", "Transporter Build-up");
			addSubtitle("transporter_arrive", "Transported Entity Appears");
			addSubtitle("matter_scanner_running", "Matter Scanner scans");
			addSubtitle("matter_scanner_beep", "Matter Scanner beeps");
			addSubtitle("matter_scanner_fail", "Scan fails");
			addSubtitle("matter_scanner_success", "Scan succeeds");

			addDimension("overworld", "Overworld");
			addDimension("the_nether", "Nether");
			addDimension("the_end", "End");

			addKeyCategory("main", "Matter Overdrive - Main");
			addKeyLabel("togglematterscanner", "Toggle Matter Scanner");
			
			addJei(InscriberRecipe.RECIPE_GROUP, "Inscriber");
			addJei("microwave", "Microwave");
			addJei("matter_recycler", "Matter Recycler");
			addJei("matter_decomposer", "Matter Decomposer");
			addJei("powerconsumed", "Total: %s");
			addJei("usagepertick", "%s/t");
			addJei("processtime", "Time: ");
			
			addDamageSource("android_transformation", "%s became human again");
			
			addChatMessage(ItemCommunicator.CHAT_MESSAGE, "Transporter location corrupted. Attempting Cancelation...");
			
		}
		
	}

	private void addItem(RegistryObject<Item> item, String translation) {
		add(item.get(), translation);
	}
	
	private void addItem(Item item, String translation) {
		add(item, translation);
	}

	private void addBlock(RegistryObject<Block> block, String translation) {
		add(block.get(), translation);
	}
	
	private void addBlock(Block block, String translation) {
		add(block, translation);
	}

	private void addTooltip(String key, String translation) {
		add("tooltip." + References.ID + "." + key, translation);
	}
	
	private void addItemDescTooltip(RegistryObject<Item> reg, String translation) {
		addTooltip(name(reg.get()) + ".desc", translation);
	}
	
	private void addBlockItemDescTooltip(RegistryObject<Block> reg, String translation) {
		addTooltip(name(reg.get().asItem()) + ".desc", translation);
	}

	private void addContainer(SubtypeMachine key, String translation) {
		addContainer(key.name(), translation);
	}
	
	private void addContainer(String key, String translation) {
		add("container." + key, translation);
	}

	private void addCommand(String key, String translation) {
		add("command." + References.ID + "." + key, translation);
	}

	private void addSubtitle(String key, String translation) {
		add("subtitles." + References.ID + "." + key, translation);
	}

	private void addGuiLabel(String key, String translation) {
		add("gui." + References.ID + "." + key, translation);
	}

	private void addDimension(String key, String translation) {
		add("dimension." + References.ID + "." + key, translation);
	}

	private void addKeyCategory(String key, String translation) {
		add("keycategory." + References.ID + "." + key, translation);
	}

	private void addKeyLabel(String key, String translation) {
		add("key." + References.ID + "." + key, translation);
	}
	
	private void addJei(String key, String translation) {
		add("jei." + References.ID + "." + key, translation);
	}
	
	private void addDamageSource(String key, String translation) {
		add("death.attack." + key, translation);
	}
	
	private void addChatMessage(String key, String translation) {
		add("chat." + References.ID + "." + key, translation);
	}
	
	private String name(Item item) {
		return ForgeRegistries.ITEMS.getKey(item).getPath();
	}

	private String getNameFromEnum(String baseString) {
		String name = baseString.toLowerCase();
		if (name.contains("_")) {
			String[] split = name.split("_");
			name = "";
			for (String str : split) {
				if (str.length() > 0) {
					name = name + str.substring(0, 1).toUpperCase() + str.substring(1) + " ";
				}
			}
			while (name.charAt(name.length() - 1) == ' ') {
				name = name.substring(0, name.length() - 1);
			}
		} else {
			name = name.substring(0, 1).toUpperCase() + name.substring(1);
		}
		return name;
	}

}
