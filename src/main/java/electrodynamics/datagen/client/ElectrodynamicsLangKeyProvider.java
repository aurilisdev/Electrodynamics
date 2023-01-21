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
import electrodynamics.registers.ElectrodynamicsBlocks;
import electrodynamics.registers.ElectrodynamicsFluids;
import electrodynamics.registers.ElectrodynamicsItems;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.data.DataGenerator;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ElectrodynamicsLangKeyProvider extends LanguageProvider {

	private Locale locale;
	private String modID;

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

		switch (locale) {
		case EN_US:
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
			addFluid(ElectrodynamicsFluids.fluidHydrogen, "Condensed Hydrogen");
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

			addTooltip("itemwire.resistance", "Resistance: %s");
			addTooltip("itemwire.maxamps", "Ampacity: %s");
			addTooltip("itemwire.info.insulationrating", "Insulation Rating: %sV");
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
			addTooltip("machine.voltage.120", "Voltage: 120 Volts");
			addTooltip("machine.voltage.240", "Voltage: 240 Volts");
			addTooltip("machine.voltage.480", "Voltage: 480 Volts");
			addTooltip("machine.voltage.960", "Voltage: 960 Volts");
			addTooltip("item.electric.info", "Energy Stored: %s");
			addTooltip("item.electric.voltage", "I/O Voltage: %s");
			addTooltip("itemcanister", "1000 mB");
			addTooltip("railguntemp", "Temperature %s");
			addTooltip("railgunmaxtemp", "Max Temp: %s");
			addTooltip("railgunoverheat", "WARNING , OVERHEATING");
			addTooltip("ceramicplatecount", "Plates Remaining: %s");
			addTooltip("tanksteel.capacity", "Capacity , 8000 mB");
			addTooltip("tankreinforced.capacity", "Capacity , 32000 mB");
			addTooltip("tankhsla.capacity", "Capacity , 120000 mB");
			addTooltip("creativepowersource.joke", "\"Unlimited Power\" - Buck Rogers from Star Trek");
			addTooltip("creativefluidsource.joke", "\"More\" - Crylo Ren");
			addTooltip("fluidvoid", "Voids fluids and gasses");
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

			addGuiLabel("creativepowersource.voltage", "Voltage: ");
			addGuiLabel("creativepowersource.power", "Power: ");
			addGuiLabel("creativepowersource.voltunit", "V");
			addGuiLabel("creativepowersource.powerunit", "MW");
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

			add("keycategory.electrodynamics", "Electrodynamics");
			addKeyLabel("jetpackascend", "Ascend with Jetpack");
			addKeyLabel("togglenvgs", "Toggle Night Vision Goggles");
			addKeyLabel("jetpackmode", "Switch Jetpack Mode");
			addKeyLabel("servoleggingsmode", "Switch Servo Leggings Mode");
			addKeyLabel("toggleservoleggings", "Toggle Servo Leggings");

			addJei("guilabel.power", "%1$sV %2$skW");
			addJei("info.item.coalgeneratorfuelsource", "Coal Generator Fuel:\n    Burn Time: %ss");
			addJei("info.fluid.combustionchamberfuel", "Combustion Chamber Fuel:\n    Produces: %2$s kW\n    Cost: %1$s mb.");

			addDamageSource("electricity", "%s was electrocuted");
			addDamageSource("accelerated_bolt", "%1$s was perforated by %2$s");
			addDamageSource("accelerated_bolt_ia", "%1$s was perforated by %2$s");
			addDamageSource("plasma_bolt", "%1$s was vaporized by %2$s");

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

			addGuidebook("availablemodules", "Available Modules");
			addGuidebook("chapters", "Chapters");

			addGuidebook(References.ID, "Electrodynamics");

			addGuidebook("chapter.ores", "Ores");
			addGuidebook("chapter.ores.spawnrange", "Y = %1$s to Y = %2$s");
			addGuidebook("chapter.ores.aluminumname", "Name: Bauxite");
			addGuidebook("chapter.ores.aluminummaterial", "Material: Aluminum");
			addGuidebook("chapter.ores.chromitename", "Name: Chromite");
			addGuidebook("chapter.ores.chromitematerial", "Material: Chromium");
			addGuidebook("chapter.ores.fluoritename", "Name: Fluorite");
			addGuidebook("chapter.ores.fluoritematerial", "Material: Fluorite");
			addGuidebook("chapter.ores.halitename", "Name: Halite");
			addGuidebook("chapter.ores.halitematerial", "Material: NaCl");
			addGuidebook("chapter.ores.leadname", "Name: Galena");
			addGuidebook("chapter.ores.leadmaterial", "Material: Lead");
			addGuidebook("chapter.ores.lepidolitename", "Name: Lepidolite");
			addGuidebook("chapter.ores.lepidolitematerial", "Material: Lithium");
			addGuidebook("chapter.ores.molybdenumname", "Name: Molybdenite");
			addGuidebook("chapter.ores.molybdenummaterial", "Material: Molybdenum");
			addGuidebook("chapter.ores.monazitename", "Name: Monazite");
			addGuidebook("chapter.ores.monazitematerial", "Material: Monazite");
			addGuidebook("chapter.ores.nitername", "Name: Niter");
			addGuidebook("chapter.ores.nitermaterial", "Material: Niter");
			addGuidebook("chapter.ores.rutilename", "Name: Rutile");
			addGuidebook("chapter.ores.rutilematerial", "Material: Titanium");
			addGuidebook("chapter.ores.silvername", "Name: Argentite");
			addGuidebook("chapter.ores.silvermaterial", "Material: Silver");
			addGuidebook("chapter.ores.sulfurname", "Name: Sulfide");
			addGuidebook("chapter.ores.sulfurmaterial", "Material: Sulfur");
			addGuidebook("chapter.ores.sylvitename", "Name: Sylvite");
			addGuidebook("chapter.ores.sylvitematerial", "Material: KCl");
			addGuidebook("chapter.ores.thorianitename", "Name: Thorianite");
			addGuidebook("chapter.ores.thorianitematerial", "Material: Thorium");
			addGuidebook("chapter.ores.tinname", "Name: Cassiterite");
			addGuidebook("chapter.ores.tinmaterial", "Material: Tin");
			addGuidebook("chapter.ores.uraninitename", "Name: Uraninite");
			addGuidebook("chapter.ores.uraninitematerial", "Material: Uranium");
			addGuidebook("chapter.ores.vanadinitename", "Name: Vanadinite");
			addGuidebook("chapter.ores.vanadinitematerial", "Material: Vanadium");

			addGuidebook("chapter.electricity", "Electricity");
			// spaces are weird, so leave a little space on the end
			// "of flow is measured in units "
			addGuidebook("chapter.electricity.p1l1", "    Understanding how energy   ");
			addGuidebook("chapter.electricity.p1l2", "and electricity work is key.   ");
			addGuidebook("chapter.electricity.p1l3", "Energy is what machines use to ");
			addGuidebook("chapter.electricity.p1l4", "do work, and it is measured in ");
			addGuidebook("chapter.electricity.p1l5", "units of Joules. However, the  ");
			addGuidebook("chapter.electricity.p1l6", "energy needs to be \"flowing\" ");
			addGuidebook("chapter.electricity.p1l7", "in order to do work. The rate  ");
			addGuidebook("chapter.electricity.p1l8", "of flow is measured in units of");
			addGuidebook("chapter.electricity.p1l9", "Volts. The pressure or force   ");
			addGuidebook("chapter.electricity.p1l10", "of the flow in measured in    ");
			addGuidebook("chapter.electricity.p1l11", "units of Amps. The amount of  ");
			addGuidebook("chapter.electricity.p1l12", "energy transfered in one      ");
			addGuidebook("chapter.electricity.p1l13", "second is known as the Power");
			addGuidebook("chapter.electricity.p1l14", "and is measured in units of    ");
			addGuidebook("chapter.electricity.p1l15", "Watts. Power can easily be     ");

			addGuidebook("chapter.electricity.p2l1", "found through the formula      ");
			addGuidebook("chapter.electricity.p2l2", "P = IV, where I is the current ");
			addGuidebook("chapter.electricity.p2l3", "and V is the voltage.    ");
			addGuidebook("chapter.electricity.p2l4", "    Now, how does this impact  ");
			addGuidebook("chapter.electricity.p2l5", "you and what do you need to    ");
			addGuidebook("chapter.electricity.p2l6", "pay attention to? All machines ");
			addGuidebook("chapter.electricity.p2l7", "require energy at a specific   ");
			addGuidebook("chapter.electricity.p2l8", "voltage to function. The       ");
			addGuidebook("chapter.electricity.p2l9", "voltage will be displayed in   ");
			addGuidebook("chapter.electricity.p2l10", "the tooltip usually. However,  ");
			addGuidebook("chapter.electricity.p2l11", "if you do not have access to   ");
			addGuidebook("chapter.electricity.p2l12", "this, there are a couple of    ");
			addGuidebook("chapter.electricity.p2l13", "methods of checking.           ");
			addGuidebook("chapter.electricity.p2l14", "    The first method is to look");
			addGuidebook("chapter.electricity.p2l15", "at the base of the machine in  ");

			addGuidebook("chapter.electricity.p3l1", "question. Most machines have a ");
			addGuidebook("chapter.electricity.p3l2", "set of colored diagonal lines, ");
			addGuidebook("chapter.electricity.p3l3", "with the color being directly  ");
			addGuidebook("chapter.electricity.p3l4", "related to the voltage. The    ");
			addGuidebook("chapter.electricity.p3l5", "following colors represent the ");
			addGuidebook("chapter.electricity.p3l6", "following voltages:            ");
			addGuidebook("chapter.electricity.p3l7", "    Yellow                     ");
			addGuidebook("chapter.electricity.p3l8", "    Blue                       ");
			addGuidebook("chapter.electricity.p3l9", "    Red                        ");
			addGuidebook("chapter.electricity.p3l10", "    Purple                     ");
			addGuidebook("chapter.electricity.p3l11", "The following pages contain    ");
			addGuidebook("chapter.electricity.p3l12", "examples of machines with      ");
			addGuidebook("chapter.electricity.p3l13", "these markings:                ");
			addGuidebook("chapter.electricity.voltageval", ": %sV");

			addGuidebook("chapter.electricity.p4l1", "Examples of 120V machines.     ");
			addGuidebook("chapter.electricity.p4l2", "Note the markings do not have  ");
			addGuidebook("chapter.electricity.p4l3", "to be at the base.");

			addGuidebook("chapter.electricity.p5l1", "Examples of 240V machines.     ");

			addGuidebook("chapter.electricity.p6l1", "Examples of 480V machines.     ");

			addGuidebook("chapter.electricity.p7l1", "Examples of 960V machines.     ");

			addGuidebook("chapter.electricity.p8l1", "The second method for          ");
			addGuidebook("chapter.electricity.p8l2", "checking a machine's voltage   ");
			addGuidebook("chapter.electricity.p8l3", "is to look at the voltage      ");
			addGuidebook("chapter.electricity.p8l4", "displayed by the energy        ");
			addGuidebook("chapter.electricity.p8l5", "tooltip in it's GUI. This tooltip");
			addGuidebook("chapter.electricity.p8l6", "will also tell you useful      ");
			addGuidebook("chapter.electricity.p8l7", "information such as the        ");
			addGuidebook("chapter.electricity.p8l8", "wattage of the machine. The    ");
			addGuidebook("chapter.electricity.p8l9", "next page contains examples    ");
			addGuidebook("chapter.electricity.p8l10", "of this tooltip:               ");

			addGuidebook("chapter.electricity.p9l1", "Note, the tooltip can also     ");
			addGuidebook("chapter.electricity.p9l2", "contain other useful info.     ");

			addGuidebook("chapter.electricity.p10l1", "    However, not every machine ");
			addGuidebook("chapter.electricity.p10l2", "has markings such as the       ");
			addGuidebook("chapter.electricity.p10l3", "Quarry or has a GUI like the   ");
			addGuidebook("chapter.electricity.p10l4", "Pump, so it's important to pay ");
			addGuidebook("chapter.electricity.p10l5", "attention when placing cables  ");
			addGuidebook("chapter.electricity.p10l6", "and machines. Too low of a     ");
			addGuidebook("chapter.electricity.p10l7", "voltage, and a machine won't   ");
			addGuidebook("chapter.electricity.p10l8", "run. Too high of a voltage, and");
			addGuidebook("chapter.electricity.p10l9", "the machine will explode!      ");
			addGuidebook("chapter.electricity.p10l10", "    Now that we have a basic   ");
			addGuidebook("chapter.electricity.p10l11", "understanding of voltage and   ");
			addGuidebook("chapter.electricity.p10l12", "how it works, it's time to     ");
			addGuidebook("chapter.electricity.p10l13", "understand the energy I/O      ");
			addGuidebook("chapter.electricity.p10l14", "ports that machines will have. ");
			addGuidebook("chapter.electricity.p10l15", "Unlike the voltage indicators  ");

			addGuidebook("chapter.electricity.p11l1", "discussed previously, these    ");
			addGuidebook("chapter.electricity.p11l2", "ports are universal to all     ");
			addGuidebook("chapter.electricity.p11l3", "machines that use energy.      ");
			addGuidebook("chapter.electricity.p11l4", "There are two ports:");
			addGuidebook("chapter.electricity.p11l5-1", "Red");
			addGuidebook("chapter.electricity.p11l5-2", ": Input");
			addGuidebook("chapter.electricity.p11l6-1", "Grey");
			addGuidebook("chapter.electricity.p11l6-2", ": Output");
			addGuidebook("chapter.electricity.p11l7", "Here is an example of each:    ");

			addGuidebook("chapter.electricity.p12l1", "    However, what is connected ");
			addGuidebook("chapter.electricity.p12l2", "to these ports? Now it's time  ");
			addGuidebook("chapter.electricity.p12l3", "to discuss how electricity is  ");
			addGuidebook("chapter.electricity.p12l4", "transfered: Wires. Wires in    ");
			addGuidebook("chapter.electricity.p12l5", "this mod function a little     ");
			addGuidebook("chapter.electricity.p12l6", "differently than what you're   ");
			addGuidebook("chapter.electricity.p12l7", "used to. If you hover over a   ");
			addGuidebook("chapter.electricity.p12l8", "wire in your inventory, you    ");
			addGuidebook("chapter.electricity.p12l9", "will notice it has a resistance");
			addGuidebook("chapter.electricity.p12l10", "and a maximum rated current.   ");
			addGuidebook("chapter.electricity.p12l11", "The resistance determines how  ");
			addGuidebook("chapter.electricity.p12l12", "much energy is lost over the   ");
			addGuidebook("chapter.electricity.p12l13", "wire. You can use P = I*I*R to ");
			addGuidebook("chapter.electricity.p12l14", "to calculate the exact amount  ");
			addGuidebook("chapter.electricity.p12l15", "lost. The longer a wire, the   ");

			addGuidebook("chapter.electricity.p13l1", "greater the energy loss. In    ");
			addGuidebook("chapter.electricity.p13l2", "order to minimize power loss,  ");
			addGuidebook("chapter.electricity.p13l3", "ideal cable networks will have ");
			addGuidebook("chapter.electricity.p13l4", "short segments with high       ");
			addGuidebook("chapter.electricity.p13l5", "curent and long segments with  ");
			addGuidebook("chapter.electricity.p13l6", "low current. This design is    ");
			addGuidebook("chapter.electricity.p13l7", "highlighted by the maximum     ");
			addGuidebook("chapter.electricity.p13l8", "current rating of a wire. If   ");
			addGuidebook("chapter.electricity.p13l9", "this limit is reached, the     ");
			addGuidebook("chapter.electricity.p13l10", "cable will catch on fire and   ");
			addGuidebook("chapter.electricity.p13l11", "be destroyed! You will also    ");
			addGuidebook("chapter.electricity.p13l12", "notice that there are insulated");
			addGuidebook("chapter.electricity.p13l13", "and non-insulated wires.       ");
			addGuidebook("chapter.electricity.p13l14", "Non-insulated wires will shock ");
			addGuidebook("chapter.electricity.p13l15", "the player on contact, while   ");

			addGuidebook("chapter.electricity.p14l1", "the insulated variant will     ");
			addGuidebook("chapter.electricity.p14l2", "protect the player up to a     ");
			addGuidebook("chapter.electricity.p14l3", "rated voltage. (also displayed ");
			addGuidebook("chapter.electricity.p14l4", "in the wire's tooltip!) It is  ");
			addGuidebook("chapter.electricity.p14l5", "important to note that wires do");
			addGuidebook("chapter.electricity.p14l6", "not store energy!              ");
			addGuidebook("chapter.electricity.p14l7", "    Now that we know how to    ");
			addGuidebook("chapter.electricity.p14l8", "get energy to a machine and    ");
			addGuidebook("chapter.electricity.p14l9", "understand it must be at a     ");
			addGuidebook("chapter.electricity.p14l10", "specific voltage, you're      ");
			addGuidebook("chapter.electricity.p14l11", "probably wondering how that    ");
			addGuidebook("chapter.electricity.p14l12", "voltage is achieved. This is   ");
			addGuidebook("chapter.electricity.p14l13", "where the Upgrade and          ");
			addGuidebook("chapter.electricity.p14l14", "Downgrade Transformers come    ");
			addGuidebook("chapter.electricity.p14l15", "in. The Upgrade Transformer    ");

			addGuidebook("chapter.electricity.p15l1", "will take any input voltage    ");
			addGuidebook("chapter.electricity.p15l2", "at any current and output      ");
			addGuidebook("chapter.electricity.p15l3", "double voltage at half the     ");
			addGuidebook("chapter.electricity.p15l4", "current. The Downgrade         ");
			addGuidebook("chapter.electricity.p15l5", "Transformer takes any input    ");
			addGuidebook("chapter.electricity.p15l6", "voltage at any current and     ");
			addGuidebook("chapter.electricity.p15l7", "outputs half the voltage at    ");
			addGuidebook("chapter.electricity.p15l8", "double the current. Note,      ");
			addGuidebook("chapter.electricity.p15l9", "Transformers are not 100%      ");
			addGuidebook("chapter.electricity.p15l10", "efficient, so be wise with    ");
			addGuidebook("chapter.electricity.p15l11", "your use of them. Also,        ");
			addGuidebook("chapter.electricity.p15l12", "Transformers will instantly    ");
			addGuidebook("chapter.electricity.p15l13", "kill you if you walk over them!");
			addGuidebook("chapter.electricity.p15l14", "Then next page contains a set  ");
			addGuidebook("chapter.electricity.p15l15", "of useful equations:           ");

			addGuidebook("chapter.electricity.p16l1", "Useful Equations:              ");
			addGuidebook("chapter.electricity.p16l2", "P = E / time                   ");
			addGuidebook("chapter.electricity.p16l3", "P = V * I                      ");
			addGuidebook("chapter.electricity.p16l4", "E/s = (E/tick) * 20            ");
			addGuidebook("chapter.electricity.p16l5", "V = I * R                      ");
			addGuidebook("chapter.electricity.p16l6", "P = I*I*R                      ");

			addGuidebook("chapter.fluids", "Fluids");
			addGuidebook("chapter.fluids.p1l1", "    Fluids play an important   ");
			addGuidebook("chapter.fluids.p1l2", "role just like energy. They're ");
			addGuidebook("chapter.fluids.p1l3", "used for crafting materials    ");
			addGuidebook("chapter.fluids.p1l4", "and for cooling machinery.     ");
			addGuidebook("chapter.fluids.p1l5", "Fortunately, if you have been  ");
			addGuidebook("chapter.fluids.p1l6", "able to grasp energy, then     ");
			addGuidebook("chapter.fluids.p1l7", "fluid mechanics should be a    ");
			addGuidebook("chapter.fluids.p1l8", "breeze.                        ");
			addGuidebook("chapter.fluids.p1l9", "    As with energy, fluids have");
			addGuidebook("chapter.fluids.p1l10", "their own I/O ports. These     ");
			addGuidebook("chapter.fluids.p1l11", "ports are universal to any     ");
			addGuidebook("chapter.fluids.p1l12", "machine that uses or produces  ");
			addGuidebook("chapter.fluids.p1l13", "fluid. They are:               ");
			addGuidebook("chapter.fluids.p1l14-1", "Blue");
			addGuidebook("chapter.fluids.p1l14-2", ": Input");
			addGuidebook("chapter.fluids.p1l15-1", "Yellow");
			addGuidebook("chapter.fluids.p1l15-2", ": Output");

			addGuidebook("chapter.fluids.p2l1", "Here are some examples:        ");
			addGuidebook("chapter.fluids.p2l2", "    However, what do we hook   ");
			addGuidebook("chapter.fluids.p2l3", "up to these ports? The answer  ");
			addGuidebook("chapter.fluids.p2l4", "is simple: Pipes! Pipes have no");
			addGuidebook("chapter.fluids.p2l5", "internal storage buffer, which ");
			addGuidebook("chapter.fluids.p2l6", "means they will not transfer a ");

			addGuidebook("chapter.fluids.p3l1", "fluid unless it has somewhere  ");
			addGuidebook("chapter.fluids.p3l2", "to go. This means you don't    ");
			addGuidebook("chapter.fluids.p3l3", "have to worry about a machine  ");
			addGuidebook("chapter.fluids.p3l4", "outputting a fluid if you hook ");
			addGuidebook("chapter.fluids.p3l5", "up a pipe accidentally. Also,  ");
			addGuidebook("chapter.fluids.p3l6", "Electrodynamics machines will  ");
			addGuidebook("chapter.fluids.p3l7", "only accept fluids they can    ");
			addGuidebook("chapter.fluids.p3l8", "process with! Pipes have a     ");
			addGuidebook("chapter.fluids.p3l9", "limited transfer rate similar   ");
			addGuidebook("chapter.fluids.p3l10", "to the current cap on wires.   ");
			addGuidebook("chapter.fluids.p3l11", "However, unlike wires, they    ");
			addGuidebook("chapter.fluids.p3l12", "will not explode if this limit ");
			addGuidebook("chapter.fluids.p3l13", "is reached! The downside is    ");
			addGuidebook("chapter.fluids.p3l14", "your choice of pipe is limited ");
			addGuidebook("chapter.fluids.p3l15", "to the following:              ");

			addGuidebook("chapter.fluids.p4l1-1", "Copper");
			addGuidebook("chapter.fluids.p4l1-2", ": 5000mB Cap");
			addGuidebook("chapter.fluids.p4l2-1", "Steel");
			addGuidebook("chapter.fluids.p4l2-2", ": 10000mB Cap");
			addGuidebook("chapter.fluids.p4l3", "    One final thing to note is ");
			addGuidebook("chapter.fluids.p4l4", "that Electrodynamics fluids    ");
			addGuidebook("chapter.fluids.p4l5", "cannot be carried by a bucket. ");
			addGuidebook("chapter.fluids.p4l6", "If you need to manually move   ");
			addGuidebook("chapter.fluids.p4l7", "fluids such as Sulfuric Acid,  ");
			addGuidebook("chapter.fluids.p4l8", "you will need to use a         ");
			addGuidebook("chapter.fluids.p4l9", "Reinforced Canister!           ");

			addGuidebook("chapter.machines", "Machines");
			addGuidebook("chapter.machines.p0l1", "Generator Specs:");
			addGuidebook("chapter.machines.basespecs", "Base: %1$s kW at %2$s V");
			addGuidebook("chapter.machines.maxspecs", "Max: %1$s kW at %2$s V");
			addGuidebook("chapter.machines.wind", "Y=319: 1.16 kW, 120 V");
			addGuidebook("chapter.machines.windu", "Y=319, U: 2.61 kW, 120 V");
			addGuidebook("chapter.machines.coalgentemp", "Temp: 2500 C");
			addGuidebook("chapter.machines.thermoheatsource", "Heat Source: Lava");
			addGuidebook("chapter.machines.combfuel", "Fuel: Ethanol, Hydrogen");
			addGuidebook("chapter.machines.thermoname", "Thermoelectric Gen.");
			addGuidebook("chapter.machines.hydroname", "Hydroelectric Gen.");

			addGuidebook("chapter.machines.p1l1", "How to use the Quarry?");
			addGuidebook("chapter.machines.p1l2-1", "Step 1:");
			addGuidebook("chapter.machines.p1l2-2", "Set up a ring of 4");
			addGuidebook("chapter.machines.p1l3", "Seismic Markers. The ring must ");
			addGuidebook("chapter.machines.p1l4", "be a minimum of 3x3 and has a  ");
			addGuidebook("chapter.machines.p1l5", "max size of 66x66. Placing a   ");
			addGuidebook("chapter.machines.p1l6", "redstone signal next to a      ");
			addGuidebook("chapter.machines.p1l7", "marker will display guide lines.");

			addGuidebook("chapter.machines.p2l1-1", "Step 2:");
			addGuidebook("chapter.machines.p2l1-2", "Place a Seismic Relay");
			addGuidebook("chapter.machines.p2l2", "down at one of the corners.    ");
			addGuidebook("chapter.machines.p2l3", "If done correctly, you will    ");
			addGuidebook("chapter.machines.p2l4", "hear the sound of an anvil,    ");
			addGuidebook("chapter.machines.p2l5", "and the Seismic Markers will   ");
			addGuidebook("chapter.machines.p2l6", "be placed in the Relay's       ");
			addGuidebook("chapter.machines.p2l7", "inventory.                     ");

			addGuidebook("chapter.machines.p4l1-1", "Step 3:");
			addGuidebook("chapter.machines.p4l1-2", "Place a Quarry on");
			addGuidebook("chapter.machines.p4l2", "either the left or the right   ");
			addGuidebook("chapter.machines.p4l3", "side of the Relay. The Quarry  ");
			addGuidebook("chapter.machines.p4l4", "must be facing the same        ");
			addGuidebook("chapter.machines.p4l5", "direction as the Relay.        ");

			addGuidebook("chapter.machines.p5l1-1", "Step 4:");
			addGuidebook("chapter.machines.p5l1-2", "Place a Coolant");
			addGuidebook("chapter.machines.p5l2", "Resavoir on top of the Quarry. ");
			addGuidebook("chapter.machines.p5l3", "The Quarry needs to be         ");
			addGuidebook("chapter.machines.p5l4", "cooled with water to run. The  ");
			addGuidebook("chapter.machines.p5l5", "faster it runs, the more water ");
			addGuidebook("chapter.machines.p5l6", "it will use.                   ");

			addGuidebook("chapter.machines.p6l1-1", "Step 5:");
			addGuidebook("chapter.machines.p6l1-2", "Place a Motor");
			addGuidebook("chapter.machines.p6l2", "Complex on the side opposite   ");
			addGuidebook("chapter.machines.p6l3", "the Seismic Relay. The green   ");
			addGuidebook("chapter.machines.p6l4", "port of the Relay must be      ");
			addGuidebook("chapter.machines.p6l5", "facing the green port of the   ");
			addGuidebook("chapter.machines.p6l6", "Quarry. The Motor Complex      ");
			addGuidebook("chapter.machines.p6l7", "controls the speed of the      ");
			addGuidebook("chapter.machines.p6l8", "Quarry. It accepts either Basic");
			addGuidebook("chapter.machines.p6l9", "or Advanced Speed Upgrades,    ");
			addGuidebook("chapter.machines.p6l10", "and has a maximum speed of     ");
			addGuidebook("chapter.machines.p6l11", "1 Block/tick. This can be      ");
			addGuidebook("chapter.machines.p6l12", "achieved with 6 Advanced       ");
			addGuidebook("chapter.machines.p6l13", "upgrades, but comes with a     ");
			addGuidebook("chapter.machines.p6l14", "heafty power consumtion!       ");

			addGuidebook("chapter.machines.p8l1-1", "Step 6:");
			addGuidebook("chapter.machines.p8l1-2", "Power the Quarry and");
			addGuidebook("chapter.machines.p8l2", "the Motor Complex. Both need   ");
			addGuidebook("chapter.machines.p8l3", "240V. The Quarry will use a    ");
			addGuidebook("chapter.machines.p8l4", "small amount of power to set   ");
			addGuidebook("chapter.machines.p8l5", "up the mining ring. If blocks  ");
			addGuidebook("chapter.machines.p8l6", "are in the way, they will be   ");
			addGuidebook("chapter.machines.p8l7", "removed without drops! When    ");
			addGuidebook("chapter.machines.p8l8", "the ring is finished, the mining");
			addGuidebook("chapter.machines.p8l9", "arm will deploy.               ");

			addGuidebook("chapter.machines.p11l1-1", "Step 7:");
			addGuidebook("chapter.machines.p11l1-2", "Open the Quarry GUI.");
			addGuidebook("chapter.machines.p11l2", "In order to begin mining, the  ");
			addGuidebook("chapter.machines.p11l3", "Quarry will need a Drill Head. ");
			addGuidebook("chapter.machines.p11l4", "There are multiple types with  ");
			addGuidebook("chapter.machines.p11l5", "varying durabilities:          ");
			addGuidebook("chapter.machines.durability", ": %s");
			addGuidebook("chapter.machines.p11l6", "Steel");
			addGuidebook("chapter.machines.p11l7", "S. Steel");
			addGuidebook("chapter.machines.p11l8", "HSLA Steel");
			addGuidebook("chapter.machines.p11l9", "Titanium");
			addGuidebook("chapter.machines.p11l10", "T. Carbide");
			addGuidebook("chapter.machines.p11l11", "Take the Drill Head of your    ");
			addGuidebook("chapter.machines.p11l12", "choice and place it in the top ");
			addGuidebook("chapter.machines.p11l13", "right slot of the Quarry GUI.  ");
			addGuidebook("chapter.machines.p11l14", "Now direct your attention to   ");
			addGuidebook("chapter.machines.p11l15", "3 upgrade slots in the GUI. The");

			addGuidebook("chapter.machines.p12l1", "Quarry itself will use no power");
			addGuidebook("chapter.machines.p12l2", "after setup. However, if you   ");
			addGuidebook("chapter.machines.p12l3", "add upgrades, it will begin to.");
			addGuidebook("chapter.machines.p12l4", "Your upgrade options are:      ");
			addGuidebook("chapter.machines.p12l5", "     Void                      ");
			addGuidebook("chapter.machines.p12l6", "     Fortune                   ");
			addGuidebook("chapter.machines.p12l7", "     Silk Touch                ");
			addGuidebook("chapter.machines.p12l8", "     Unbreaking                ");
			addGuidebook("chapter.machines.p12l9-1", "The");
			addGuidebook("chapter.machines.p12l9-2", "Void");
			addGuidebook("chapter.machines.p12l9-3", "Upgrade will activate");
			addGuidebook("chapter.machines.p12l10", "the top 6 slots in the GUI.     ");
			addGuidebook("chapter.machines.p12l11", "Any items placed in these       ");
			addGuidebook("chapter.machines.p12l12", "slots will be voided by the     ");
			addGuidebook("chapter.machines.p12l13", "Quarry. The upgrade will        ");
			addGuidebook("chapter.machines.p12l14", "cause the Quarry to start       ");
			addGuidebook("chapter.machines.p12l15", "drawing its base usage. The     ");

			addGuidebook("chapter.machines.p13l1-1", "Fortune");
			addGuidebook("chapter.machines.p13l1-2", "Upgrade applies the ");
			addGuidebook("chapter.machines.p13l2", "Fortune enchantment to the     ");
			addGuidebook("chapter.machines.p13l3", "blocks mined by the Quarry.    ");
			addGuidebook("chapter.machines.p13l4", "A maximum of 3 can be used     ");
			addGuidebook("chapter.machines.p13l5", "and it cannot be used in       ");
			addGuidebook("chapter.machines.p13l6", "tandem with the Silk Touch     ");
			addGuidebook("chapter.machines.p13l7-1", "Upgrade. The");
			addGuidebook("chapter.machines.p13l7-2", "Silk Touch");
			addGuidebook("chapter.machines.p13l8", "Upgrade will apply the Silk     ");
			addGuidebook("chapter.machines.p13l9", "Touch enchantment to the        ");
			addGuidebook("chapter.machines.p13l10", "blocks mined by the Quarry. A   ");
			addGuidebook("chapter.machines.p13l11", "maximum of 1 can be used and    ");
			addGuidebook("chapter.machines.p13l12", "it cannot be used in tandem     ");
			addGuidebook("chapter.machines.p13l13", "with the Fortune Upgrade. Both  ");
			addGuidebook("chapter.machines.p13l14", "will cause the Quarry to draw   ");
			addGuidebook("chapter.machines.p13l15", "large amounts of power. The     ");

			addGuidebook("chapter.machines.p14l1-1", "Unbreaking");
			addGuidebook("chapter.machines.p14l1-2", "Upgrade reduces");
			addGuidebook("chapter.machines.p14l2", "the durability used from the   ");
			addGuidebook("chapter.machines.p14l3", "Drill Head. A maximum of 3 can ");
			addGuidebook("chapter.machines.p14l4", "be used, and it will cause the ");
			addGuidebook("chapter.machines.p14l5", "Quarry to draw a large amount  ");
			addGuidebook("chapter.machines.p14l6", "of power.                      ");

			addGuidebook("chapter.machines.p16l1-1", "Step 8:");
			addGuidebook("chapter.machines.p16l1-2", "This step is optional,");
			addGuidebook("chapter.machines.p16l2", "but highly recommended. Place  ");
			addGuidebook("chapter.machines.p16l3", "a Logistical Manager in front  ");
			addGuidebook("chapter.machines.p16l4", "of the Quarry, with the green  ");
			addGuidebook("chapter.machines.p16l5", "square of the T facing the     ");
			addGuidebook("chapter.machines.p16l6", "front of the Quarry. Place a   ");
			addGuidebook("chapter.machines.p16l7", "chest to the left and right    ");
			addGuidebook("chapter.machines.p16l8", "of the Manager. The chest on   ");
			addGuidebook("chapter.machines.p16l9", "the left can be filled with    ");
			addGuidebook("chapter.machines.p16l10", "Drill Heads to resupply the    ");
			addGuidebook("chapter.machines.p16l11", "Quarry. The Manager will fill  ");
			addGuidebook("chapter.machines.p16l12", "the chest on the right with all");
			addGuidebook("chapter.machines.p16l13", "the blocks the Quarry mines.   ");
			addGuidebook("chapter.machines.p16l14", "Now direct your attention to   ");
			addGuidebook("chapter.machines.p16l15", "3 upgrade slots in the GUI. The");

			addGuidebook("chapter.machines.p19l1", "Using the Injector Upgrade:");
			addGuidebook("chapter.machines.p19l2", "    The Auto-Injector Upgrade  ");
			addGuidebook("chapter.machines.p19l3", "gives machines the ability to  ");
			addGuidebook("chapter.machines.p19l4", "intake items from surrounding  ");
			addGuidebook("chapter.machines.p19l5", "inventories. The upgrade has   ");
			addGuidebook("chapter.machines.p19l6", "two modes: Default and Smart.  ");
			addGuidebook("chapter.machines.p19l7-1", "Default");
			addGuidebook("chapter.machines.p19l7-2", "mode will attempt to");
			addGuidebook("chapter.machines.p19l8", "fill every input slot in order ");
			addGuidebook("chapter.machines.p19l9", "of the directions on the card. ");
			addGuidebook("chapter.machines.p19l10-1", "Smart");
			addGuidebook("chapter.machines.p19l10-2", "mode will map each input");
			addGuidebook("chapter.machines.p19l11", "slot to each direction on the  ");
			addGuidebook("chapter.machines.p19l12", "card. If there are more slots  ");
			addGuidebook("chapter.machines.p19l13", "than directions, the remaining ");
			addGuidebook("chapter.machines.p19l14", "slots will be mapped to the    ");
			addGuidebook("chapter.machines.p19l15", "final direction. To add a      ");

			addGuidebook("chapter.machines.p20l1", "direction, Shift + Right-Click ");
			addGuidebook("chapter.machines.p20l2", "the card while facing the      ");
			addGuidebook("chapter.machines.p20l3", "desired direction. To clear all");
			addGuidebook("chapter.machines.p20l4", "directions, Shift + Left-Click.");
			addGuidebook("chapter.machines.p20l5", "To toggle Smart mode, Right-   ");
			addGuidebook("chapter.machines.p20l6", "Click the card.                ");

			addGuidebook("chapter.machines.p21l1", "Using the Ejector Upgrade:");
			addGuidebook("chapter.machines.p21l2", "    The Auto-Ejector Upgrade   ");
			addGuidebook("chapter.machines.p21l3", "gives machines the ability to  ");
			addGuidebook("chapter.machines.p21l4", "output items to surrounding    ");
			addGuidebook("chapter.machines.p21l5", "inventories. The upgrade has   ");
			addGuidebook("chapter.machines.p21l6", "two modes: Default and Smart.  ");
			addGuidebook("chapter.machines.p21l7-1", "Default");
			addGuidebook("chapter.machines.p21l7-2", "mode will attempt to");
			addGuidebook("chapter.machines.p21l8", "output every slot in order of  ");
			addGuidebook("chapter.machines.p21l9", "the directions on the card.    ");
			addGuidebook("chapter.machines.p21l10-1", "Smart");
			addGuidebook("chapter.machines.p21l10-2", "mode will map each");
			addGuidebook("chapter.machines.p21l11", "output slot to each direction  ");
			addGuidebook("chapter.machines.p21l12", "on the card. If there are more ");
			addGuidebook("chapter.machines.p21l13", "slots than directions, the     ");
			addGuidebook("chapter.machines.p21l14", "remaining slots will be mapped ");
			addGuidebook("chapter.machines.p21l15", "to the final direction. To add ");

			addGuidebook("chapter.machines.p22l1", "a direction, Shift + Right-Click");
			addGuidebook("chapter.machines.p22l2", "the card while facing the      ");
			addGuidebook("chapter.machines.p22l3", "desired direction. To clear all");
			addGuidebook("chapter.machines.p22l4", "directions, Shift + Left-Click.");
			addGuidebook("chapter.machines.p22l5", "To toggle Smart mode, Right-   ");
			addGuidebook("chapter.machines.p22l6", "Click the card. It is important");
			addGuidebook("chapter.machines.p22l7", "to note that output slots come ");
			addGuidebook("chapter.machines.p22l8", "before biproduct slots. For    ");
			addGuidebook("chapter.machines.p22l9", "example, if there are 3 output ");
			addGuidebook("chapter.machines.p22l10", "slots and 2 biproduct slots,  ");
			addGuidebook("chapter.machines.p22l11", "the card would empty all 3     ");
			addGuidebook("chapter.machines.p22l12", "output slots before it empties ");
			addGuidebook("chapter.machines.p22l13", "the 2 biproduct slots.         ");
			addGuidebook("chapter.machines.p22l14", "slots will be mapped to the    ");
			addGuidebook("chapter.machines.p22l15", "final direction. To add a      ");

			addGuidebook("chapter.machines.p23l1", "Understanding the Charger:");
			addGuidebook("chapter.machines.p23l2", "    The Charger does what the  ");
			addGuidebook("chapter.machines.p23l3", "name suggests and charges      ");
			addGuidebook("chapter.machines.p23l4", "items. When charging an item,  ");
			addGuidebook("chapter.machines.p23l5", "it's crucial to select a charger");
			addGuidebook("chapter.machines.p23l6", "with the correct voltage. If   ");
			addGuidebook("chapter.machines.p23l7", "the charger's voltage is less  ");
			addGuidebook("chapter.machines.p23l8", "than the item's, the item will ");
			addGuidebook("chapter.machines.p23l9", "only be charged a percentage   ");
			addGuidebook("chapter.machines.p23l10", "of it's full charge. This can be");
			addGuidebook("chapter.machines.p23l11", "calculated by the formula:     ");
			addGuidebook("chapter.machines.p23l12", "     Vcharger / Vitem          ");
			addGuidebook("chapter.machines.p23l13", "If the charger's voltage is    ");
			addGuidebook("chapter.machines.p23l14", "greater, it will explode!      ");
			addGuidebook("chapter.machines.p23l15", "    It is possible to operate  ");

			addGuidebook("chapter.machines.p24l1", "a charger with battery power   ");
			addGuidebook("chapter.machines.p24l2", "using the 3 battery slots:     ");
			addGuidebook("chapter.machines.p24l3", "The charge of the item placed  ");
			addGuidebook("chapter.machines.p24l4", "in one of these slots will be  ");
			addGuidebook("chapter.machines.p24l5", "transfered to the item in the  ");
			addGuidebook("chapter.machines.p24l6", "charging slot. Along with actual");
			addGuidebook("chapter.machines.p24l7", "batteries, any item with a     ");
			addGuidebook("chapter.machines.p24l8", "charge can be used! However,   ");

			addGuidebook("chapter.machines.p25l1", "be mindful of the item's       ");
			addGuidebook("chapter.machines.p25l2", "voltage. If it is less than    ");
			addGuidebook("chapter.machines.p25l3", "the voltage of the charger, it ");
			addGuidebook("chapter.machines.p25l4", "will be reduced to a pile of   ");
			addGuidebook("chapter.machines.p25l5", "Slag! If the item's voltage is ");
			addGuidebook("chapter.machines.p25l6", "greater than the charger's,    ");
			addGuidebook("chapter.machines.p25l7", "the charger will explode!      ");

			addGuidebook("chapter.misc", "Misc");
			addGuidebook("chapter.misc.p1l1", "The Kinetic Railgun:");
			addGuidebook("chapter.misc.p1l2", "Ammo:");
			addGuidebook("chapter.misc.p1l3", "     Steel Rod");
			addGuidebook("chapter.misc.p1l4", "     S. Steel Rod");
			addGuidebook("chapter.misc.p1l5", "     HSLA Steel Rod");
			addGuidebook("chapter.misc.p1l6", "Damage:");
			addGuidebook("chapter.misc.p1l7-1", "Steel");
			addGuidebook("chapter.misc.p1l7-2", ": 16");
			addGuidebook("chapter.misc.p1l8-1", "S. Steel");
			addGuidebook("chapter.misc.p1l8-2", ": 18");
			addGuidebook("chapter.misc.p1l9-1", "HSLA Steel");
			addGuidebook("chapter.misc.p1l9-2", ": 4 AP*");
			addGuidebook("chapter.misc.p1l10", "*ignores armor");
			addGuidebook("chapter.misc.p1l11", "To use the Kinetic Railgun,    ");
			addGuidebook("chapter.misc.p1l12", "hold the gun in one hand and   ");
			addGuidebook("chapter.misc.p1l13", "the ammo type of your choice   ");
			addGuidebook("chapter.misc.p1l14", "in the other.                  ");

			addGuidebook("chapter.misc.p2l1", "The Plasma Railgun:");
			addGuidebook("chapter.misc.p2l2", "Ammo:");
			addGuidebook("chapter.misc.p2l3", "     Energy");
			addGuidebook("chapter.misc.p2l4", "Damage");
			addGuidebook("chapter.misc.p2l5-1", "Initial");
			addGuidebook("chapter.misc.p2l5-2", ": 40 AP*");
			addGuidebook("chapter.misc.p2l6-1", "After 1s");
			addGuidebook("chapter.misc.p2l6-2", ": 20 AP*");
			addGuidebook("chapter.misc.p2l7", "*ignores armor");

			addGuidebook("chapter.misc.p3l1", "Ceramic Plate Protection:");
			addGuidebook("chapter.misc.p3l2", "Composite and Combat armor     ");
			addGuidebook("chapter.misc.p3l3", "have a unique ability: Ceramic ");
			addGuidebook("chapter.misc.p3l4", "Protection. To use this ability,");
			addGuidebook("chapter.misc.p3l5", "you must first add Ceramic     ");
			addGuidebook("chapter.misc.p3l6", "Plates to the chestplate. This ");
			addGuidebook("chapter.misc.p3l7", "is accomplished by wearing     ");
			addGuidebook("chapter.misc.p3l8", "either a Composite or Combat   ");
			addGuidebook("chapter.misc.p3l9", "chestplate. Holding a Ceramic  ");
			addGuidebook("chapter.misc.p3l10", "plate in your hand, Right+Click.");
			addGuidebook("chapter.misc.p3l11", "If sucessfull, you will hear   ");
			addGuidebook("chapter.misc.p3l12", "the sound of a velcro strap.   ");
			addGuidebook("chapter.misc.p3l13", "A maximum of two plates can be ");
			addGuidebook("chapter.misc.p3l14", "be held by both chestplates.   ");
			addGuidebook("chapter.misc.p3l15", "For the ability to trigger, you");

			addGuidebook("chapter.misc.p4l1", "must be wearing a full set of  ");
			addGuidebook("chapter.misc.p4l2", "the armor type, and it must be ");
			addGuidebook("chapter.misc.p4l3", "a complete set. You must then  ");
			addGuidebook("chapter.misc.p4l4", "take more than 16 damage. If   ");
			addGuidebook("chapter.misc.p4l5", "these conditions are met, you  ");
			addGuidebook("chapter.misc.p4l6", "will hear the plate break, a   ");
			addGuidebook("chapter.misc.p4l7", "Ceramic Plate will be removed  ");
			addGuidebook("chapter.misc.p4l8", "from the chestplate, and the   ");
			addGuidebook("chapter.misc.p4l9", "damage dealt will be reduced   ");
			addGuidebook("chapter.misc.p4l10", "to its square root.            ");

			addGuidebook("chapter.misc.p5l1", "The Seismic Scanner:");
			addGuidebook("chapter.misc.p5l2", "The Seismic Scanner is a very  ");
			addGuidebook("chapter.misc.p5l3", "useful item. It is able to scan");
			addGuidebook("chapter.misc.p5l4", "for a selected block within a  ");
			addGuidebook("chapter.misc.p5l5", "16 block radius. To use the    ");
			addGuidebook("chapter.misc.p5l6", "scanner, Right-Click the       ");
			addGuidebook("chapter.misc.p5l7", "Scanner to access it's GUI and ");
			addGuidebook("chapter.misc.p5l8", "insert the desired block. Next,");
			addGuidebook("chapter.misc.p5l9", "Shift + Right-Click to start a ");
			addGuidebook("chapter.misc.p5l10", "scan. If the Scanner is able   ");
			addGuidebook("chapter.misc.p5l11", "to find the block, it will list");
			addGuidebook("chapter.misc.p5l12", "the coordinates in its GUI and ");
			addGuidebook("chapter.misc.p5l13", "highlight the block in the     ");
			addGuidebook("chapter.misc.p5l14", "world. Note, you will not see  ");
			addGuidebook("chapter.misc.p5l15", "the highlight if you can't see ");

			addGuidebook("chapter.misc.p6l1", "the block itself. The Scanner  ");
			addGuidebook("chapter.misc.p6l2", "has a 10s cooldown between     ");
			addGuidebook("chapter.misc.p6l3", "scans.");

			addGuidebook("chapter.gettingstarted", "Getting Started");
			addGuidebook("chapter.gettingstarted.p1l1", "    Electrodynamics is a       ");
			addGuidebook("chapter.gettingstarted.p1l2", "mod based around realistic     ");
			addGuidebook("chapter.gettingstarted.p1l3", "electricity and more realistic ");
			addGuidebook("chapter.gettingstarted.p1l4", "concepts in general. As a      ");
			addGuidebook("chapter.gettingstarted.p1l5", "result, you will find it plays ");
			addGuidebook("chapter.gettingstarted.p1l6", "very differently than other    ");
			addGuidebook("chapter.gettingstarted.p1l7", "tech mods you're used to. The  ");
			addGuidebook("chapter.gettingstarted.p1l8", "main difference will be that   ");
			addGuidebook("chapter.gettingstarted.p1l9", "Electro is much more involved, ");
			addGuidebook("chapter.gettingstarted.p1l10", "as that is the price that comes");
			addGuidebook("chapter.gettingstarted.p1l11", "with realism. Keep this in mind");
			addGuidebook("chapter.gettingstarted.p1l12", "as you progress in the mod!    ");
			addGuidebook("chapter.gettingstarted.p1l13", "    Starting out in Electro,   ");
			addGuidebook("chapter.gettingstarted.p1l14", "you will notice that getting   ");
			addGuidebook("chapter.gettingstarted.p1l15", "into technology is much more   ");

			addGuidebook("chapter.gettingstarted.p2l1", "challenging that typical. Not  ");
			addGuidebook("chapter.gettingstarted.p2l2", "only will you find you need    ");
			addGuidebook("chapter.gettingstarted.p2l3", "minerals, you will also need   ");
			addGuidebook("chapter.gettingstarted.p2l4", "materials such as wool. Also   ");
			addGuidebook("chapter.gettingstarted.p2l5", "it will be very hard to power  ");
			addGuidebook("chapter.gettingstarted.p2l6", "your first machines, as the    ");
			addGuidebook("chapter.gettingstarted.p2l7", "power generation options       ");
			addGuidebook("chapter.gettingstarted.p2l8", "you have avialable are weak.   ");
			addGuidebook("chapter.gettingstarted.p2l9", "This is done by design, as     ");
			addGuidebook("chapter.gettingstarted.p2l10", "early game Electro is focused  ");
			addGuidebook("chapter.gettingstarted.p2l11", "around rationing power and     ");
			addGuidebook("chapter.gettingstarted.p2l12", "materials. You will need to    ");
			addGuidebook("chapter.gettingstarted.p2l13", "be smart with the choices you  ");
			addGuidebook("chapter.gettingstarted.p2l14", "make in this stage.            ");
			addGuidebook("chapter.gettingstarted.p2l15", "    While weak, you still need ");

			addGuidebook("chapter.gettingstarted.p3l1", "a source of power. You have    ");
			addGuidebook("chapter.gettingstarted.p3l2", "5 options:                     ");
			addGuidebook("chapter.gettingstarted.p3l3", "     Thermoelectric Generator  ");
			addGuidebook("chapter.gettingstarted.p3l4", "     Hydroelectric Generator   ");
			addGuidebook("chapter.gettingstarted.p3l5", "     Windmill                  ");
			addGuidebook("chapter.gettingstarted.p3l6", "     Solar Panel               ");
			addGuidebook("chapter.gettingstarted.p3l7", "     Coal Generator            ");
			addGuidebook("chapter.gettingstarted.p3l8", "Of the 5, the Coal Generator   ");
			addGuidebook("chapter.gettingstarted.p3l9", "produces the most, but uses    ");
			addGuidebook("chapter.gettingstarted.p3l10", "fuel. See the Machines chapter ");
			addGuidebook("chapter.gettingstarted.p3l11", "for more detailed specs.       ");
			addGuidebook("chapter.gettingstarted.p3l12", "    Now that you have a power  ");
			addGuidebook("chapter.gettingstarted.p3l13", "source, I highly recommend     ");
			addGuidebook("chapter.gettingstarted.p3l14", "reading the Electricity chapter,");
			addGuidebook("chapter.gettingstarted.p3l15", "and I will assume you've read   ");

			addGuidebook("chapter.gettingstarted.p4l1", "it after this point. Although  ");
			addGuidebook("chapter.gettingstarted.p4l2", "weak, your power source is     ");
			addGuidebook("chapter.gettingstarted.p4l3", "still able to power machines   ");
			addGuidebook("chapter.gettingstarted.p4l4", "if you invest in a Battery Box.");
			addGuidebook("chapter.gettingstarted.p4l5", "The Battery Box will allow you ");
			addGuidebook("chapter.gettingstarted.p4l6", "you build up chrage to run     ");
			addGuidebook("chapter.gettingstarted.p4l7", "machines for a short period    ");
			addGuidebook("chapter.gettingstarted.p4l8", "of time. Knowing that your     ");
			addGuidebook("chapter.gettingstarted.p4l9", "power is limited, do not invest");
			addGuidebook("chapter.gettingstarted.p4l10", "in early game ore doubling.    ");
			addGuidebook("chapter.gettingstarted.p4l11", "You do not have the power to   ");
			addGuidebook("chapter.gettingstarted.p4l12", "run it.                        ");
			addGuidebook("chapter.gettingstarted.p4l13", "    Next, you will need to     ");
			addGuidebook("chapter.gettingstarted.p4l14", "make some machines to use the  ");
			addGuidebook("chapter.gettingstarted.p4l15", "power you are now storing.     ");

			addGuidebook("chapter.gettingstarted.p5l1", "Your first machines should be  ");
			addGuidebook("chapter.gettingstarted.p5l2", "the following (in order):      ");
			addGuidebook("chapter.gettingstarted.p5l3", "     Wire Mill                 ");
			addGuidebook("chapter.gettingstarted.p5l4", "     Mineral Crusher           ");
			addGuidebook("chapter.gettingstarted.p5l5", "     Electric Arc Furnace      ");
			addGuidebook("chapter.gettingstarted.p5l6", "     Electric Furnace          ");
			addGuidebook("chapter.gettingstarted.p5l7", "These machines will help you   ");
			addGuidebook("chapter.gettingstarted.p5l8", "maximize your limited materials");
			addGuidebook("chapter.gettingstarted.p5l9", "since you cannot double ores.   ");
			addGuidebook("chapter.gettingstarted.p5l10", "Note how the Mineral Crusher   ");
			addGuidebook("chapter.gettingstarted.p5l11", "also requires a higher voltage ");
			addGuidebook("chapter.gettingstarted.p5l12", "than your Battery Box's. This  ");
			addGuidebook("chapter.gettingstarted.p5l13", "too is by design, so you learn ");
			addGuidebook("chapter.gettingstarted.p5l14", "early on how to use the        ");
			addGuidebook("chapter.gettingstarted.p5l15", "Upgrade and Downgrade          ");

			addGuidebook("chapter.gettingstarted.p6l1", "Transformers. With your basic  ");
			addGuidebook("chapter.gettingstarted.p6l2", "infrastructure, you can now    ");
			addGuidebook("chapter.gettingstarted.p6l3", "invest in a few tools to help  ");
			addGuidebook("chapter.gettingstarted.p6l4", "you moving forward:            ");
			addGuidebook("chapter.gettingstarted.p6l5", "     Electric Drill            ");
			addGuidebook("chapter.gettingstarted.p6l6", "     Night Vision Goggles      ");
			addGuidebook("chapter.gettingstarted.p6l7", "     Seismic Scanner           ");
			addGuidebook("chapter.gettingstarted.p6l8", "These will make mining easier, ");
			addGuidebook("chapter.gettingstarted.p6l9", "however you do not need them   ");
			addGuidebook("chapter.gettingstarted.p6l10", "to progress. If you make them  ");
			addGuidebook("chapter.gettingstarted.p6l11", "however, you will need to make ");
			addGuidebook("chapter.gettingstarted.p6l12", "a Charger. The only one you    ");
			addGuidebook("chapter.gettingstarted.p6l13", "will be able to make is the    ");
			addGuidebook("chapter.gettingstarted.p6l14", "120V Charger, but it will get  ");
			addGuidebook("chapter.gettingstarted.p6l15", "the job done for now. See the  ");

			addGuidebook("chapter.gettingstarted.p7l1", "Machines chapter for a more    ");
			addGuidebook("chapter.gettingstarted.p7l2", "in-depth guide on the Charger. ");
			addGuidebook("chapter.gettingstarted.p7l3", "    To progress further in the ");
			addGuidebook("chapter.gettingstarted.p7l4", "mod, you will need to increase ");
			addGuidebook("chapter.gettingstarted.p7l5", "your power generation. You     ");
			addGuidebook("chapter.gettingstarted.p7l6", "have two main options:         ");
			addGuidebook("chapter.gettingstarted.p7l7", "     Advanced Solar Panel      ");
			addGuidebook("chapter.gettingstarted.p7l8", "     Combustion Chamber        ");
			addGuidebook("chapter.gettingstarted.p7l9", "The Advanced Solar Panel is    ");
			addGuidebook("chapter.gettingstarted.p7l10", "more expensive up-front, as    ");
			addGuidebook("chapter.gettingstarted.p7l11", "it costs more and will require ");
			addGuidebook("chapter.gettingstarted.p7l12", "large-scale energy storage.    ");
			addGuidebook("chapter.gettingstarted.p7l13", "However, with the Improved     ");
			addGuidebook("chapter.gettingstarted.p7l14", "Solar Cell Upgrade, it will    ");
			addGuidebook("chapter.gettingstarted.p7l15", "produce the most power. The    ");

			addGuidebook("chapter.gettingstarted.p8l1", "Combustion Chamber itself is   ");
			addGuidebook("chapter.gettingstarted.p8l2", "cheaper, but requires a fuel   ");
			addGuidebook("chapter.gettingstarted.p8l3", "source. You will need to also  ");
			addGuidebook("chapter.gettingstarted.p8l4", "invest in fuel production if   ");
			addGuidebook("chapter.gettingstarted.p8l5", "you want to go this route. On  ");
			addGuidebook("chapter.gettingstarted.p8l6", "top of this, the Chamber has   ");
			addGuidebook("chapter.gettingstarted.p8l7", "no available upgrades, meaning ");
			addGuidebook("chapter.gettingstarted.p8l8", "you will have to keep making   ");
			addGuidebook("chapter.gettingstarted.p8l9", "more.                          ");
			addGuidebook("chapter.gettingstarted.p8l10", "    Now that you have a better ");
			addGuidebook("chapter.gettingstarted.p8l11", "power source, you have made    ");
			addGuidebook("chapter.gettingstarted.p8l12", "it out of the early game.      ");
			addGuidebook("chapter.gettingstarted.p8l13", "Mid-Game Electro is all about  ");
			addGuidebook("chapter.gettingstarted.p8l14", "expansion. You can now run     ");
			addGuidebook("chapter.gettingstarted.p8l15", "machines more frequently,      ");

			addGuidebook("chapter.gettingstarted.p9l1", "which means you can start ore  ");
			addGuidebook("chapter.gettingstarted.p9l2", "doubling now. However, why go  ");
			addGuidebook("chapter.gettingstarted.p9l3", "with 2x when you can jump      ");
			addGuidebook("chapter.gettingstarted.p9l4", "straight to 3x! You can also   ");
			addGuidebook("chapter.gettingstarted.p9l5", "begin to invest in upgrades,   ");
			addGuidebook("chapter.gettingstarted.p9l6", "improving the efficiency of    ");
			addGuidebook("chapter.gettingstarted.p9l7", "your machines. In particular,  ");
			addGuidebook("chapter.gettingstarted.p9l8", "I would recommend the Speed    ");
			addGuidebook("chapter.gettingstarted.p9l9", "Upgrades, and the Injector     ");
			addGuidebook("chapter.gettingstarted.p9l10", "and Ejector Upgrades. You will ");
			addGuidebook("chapter.gettingstarted.p9l11", "need an Oxidation Furnace for  ");
			addGuidebook("chapter.gettingstarted.p9l12", "the latter two however. See    ");
			addGuidebook("chapter.gettingstarted.p9l13", "the Misc chapter for details   ");
			addGuidebook("chapter.gettingstarted.p9l14", "on using them.                 ");
			addGuidebook("chapter.gettingstarted.p9l15", "    You are probably tired of  ");

			addGuidebook("chapter.gettingstarted.p10l1", "manually mining by now, so why ");
			addGuidebook("chapter.gettingstarted.p10l2", "not invest in a Quarry! The    ");
			addGuidebook("chapter.gettingstarted.p10l3", "Quarry is one of your main     ");
			addGuidebook("chapter.gettingstarted.p10l4", "goals in the mid-game, and will");
			addGuidebook("chapter.gettingstarted.p10l5", "help with resource gathering   ");
			addGuidebook("chapter.gettingstarted.p10l6", "a lot. See the Machines chapter");
			addGuidebook("chapter.gettingstarted.p10l7", "for a detailed guide on how to ");
			addGuidebook("chapter.gettingstarted.p10l8", "set up and use the Quarry.     ");
			addGuidebook("chapter.gettingstarted.p10l9", "    Your final goal of the mid-");
			addGuidebook("chapter.gettingstarted.p10l10", "game is to make an Energized   ");
			addGuidebook("chapter.gettingstarted.p10l11", "Alloyer. The Alloyer will      ");
			addGuidebook("chapter.gettingstarted.p10l12", "unlock Stainless Steel for     ");
			addGuidebook("chapter.gettingstarted.p10l13", "you, enabling you to craft     ");
			addGuidebook("chapter.gettingstarted.p10l14", "items such as the Jetpack and  ");
			addGuidebook("chapter.gettingstarted.p10l15", "the Kinetic Railgun. At this   ");

			addGuidebook("chapter.gettingstarted.p11l1", "point, I will leave the choices");
			addGuidebook("chapter.gettingstarted.p11l2", "up to you. You have made it    ");
			addGuidebook("chapter.gettingstarted.p11l3", "to the mid-game and have a     ");
			addGuidebook("chapter.gettingstarted.p11l4", "Quarry up and running. Time to ");
			addGuidebook("chapter.gettingstarted.p11l5", "explore the rest of the mod    ");
			addGuidebook("chapter.gettingstarted.p11l6", "on your own!                   ");

			addGuidebook("chapter.tips", "Tips");
			addGuidebook("chapter.tips.p1l1", "1. Use Energy Storage:");
			addGuidebook("chapter.tips.p1l2", "Electrodynamics has several    ");
			addGuidebook("chapter.tips.p1l3", "tiers of energy storage. You   ");
			addGuidebook("chapter.tips.p1l4", "should invest in using them in ");
			addGuidebook("chapter.tips.p1l5", "all stages of progression.     ");
			addGuidebook("chapter.tips.p1l6", "Note, the Capacity Upgrade not ");
			addGuidebook("chapter.tips.p1l7", "only increases the capacity of ");
			addGuidebook("chapter.tips.p1l8", "a Battery Box, but also the    ");
			addGuidebook("chapter.tips.p1l9", "output voltage!                ");

			addGuidebook("chapter.tips.p2l1", "2. No 2x ore early-game:");
			addGuidebook("chapter.tips.p2l2", "This is touched on in the      ");
			addGuidebook("chapter.tips.p2l3", "Getting Started chapter, but   ");
			addGuidebook("chapter.tips.p2l4", "do not invest in 2x ore        ");
			addGuidebook("chapter.tips.p2l5", "processing in the early game.  ");
			addGuidebook("chapter.tips.p2l6", "You do not have the power to   ");
			addGuidebook("chapter.tips.p2l7", "run it, and you will have a bad");
			addGuidebook("chapter.tips.p2l8", "time if you attempt it. Instead,");
			addGuidebook("chapter.tips.p2l9", "focus on getting better power  ");
			addGuidebook("chapter.tips.p2l10", "generation so you can jump to  ");
			addGuidebook("chapter.tips.p2l11", "3x ore processing instead!     ");

			addGuidebook("chapter.tips.p3l1", "3. Transmit at High Voltage:");
			addGuidebook("chapter.tips.p3l2", "This is mentioned in the       ");
			addGuidebook("chapter.tips.p3l3", "Electricity chapter, but you   ");
			addGuidebook("chapter.tips.p3l4", "should transmit large amounts  ");
			addGuidebook("chapter.tips.p3l5", "of power at high voltage and   ");
			addGuidebook("chapter.tips.p3l6", "then step it down for          ");
			addGuidebook("chapter.tips.p3l7", "distribution. This will help   ");
			addGuidebook("chapter.tips.p3l8", "reduce the need for heavy-     ");
			addGuidebook("chapter.tips.p3l9", "duty cables as well as the     ");
			addGuidebook("chapter.tips.p3l10", "amount of power lost due to    ");
			addGuidebook("chapter.tips.p3l11", "the cable's own resistance!    ");

		}

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
	
	public static enum Locale {
		EN_US;
		
		@Override
		public String toString() {
			return super.toString().toLowerCase();
		}
	}

}
