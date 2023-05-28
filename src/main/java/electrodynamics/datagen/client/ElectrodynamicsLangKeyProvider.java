package electrodynamics.datagen.client;

import electrodynamics.api.References;
import electrodynamics.api.gas.Gas;
import electrodynamics.common.block.subtype.SubtypeGlass;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypeOreDeepslate;
import electrodynamics.common.block.subtype.SubtypeFluidPipe;
import electrodynamics.common.block.subtype.SubtypeGasPipe;
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
import electrodynamics.registers.ElectrodynamicsGases;
import electrodynamics.registers.ElectrodynamicsItems;
import electrodynamics.registers.ElectrodynamicsRegistries;
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

			addBlock(ElectrodynamicsBlocks.BLOCK_FRAME, "Quarry Frame");
			addBlock(ElectrodynamicsBlocks.BLOCK_FRAME_CORNER, "Quarry Frame Corner");
			addBlock(ElectrodynamicsBlocks.BLOCK_LOGISTICALMANAGER, "Logistical Manager");
			addBlock(ElectrodynamicsBlocks.BLOCK_SEISMICMARKER, "Seismic Marker");
			addBlock(ElectrodynamicsBlocks.BLOCK_MULTISUBNODE, "Multiblock Subnode");

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
			
			addBlock(ElectrodynamicsBlocks.blockCompressor, "Compressor");
			addBlock(ElectrodynamicsBlocks.blockDecompressor, "Decompressor");
			addBlock(ElectrodynamicsBlocks.blockGasTransformerAddonTank, "Pressurized Tank");
			addBlock(ElectrodynamicsBlocks.blockGasTransformerSide, "Compressor Side");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.gasvent), "Gas Vent");
			addBlock(ElectrodynamicsBlocks.blockThermoelectricManipulator, "Thermoelectric Manipulator");
			
			addBlock(ElectrodynamicsBlocks.blockGasValve, "Gas Valve");
			addBlock(ElectrodynamicsBlocks.blockFluidValve, "Fluid Valve");
			addBlock(ElectrodynamicsBlocks.blockGasPipePump, "Gas Pipe Pump");
			addBlock(ElectrodynamicsBlocks.blockFluidPipePump, "Fluid Pipe Pump");
			addBlock(ElectrodynamicsBlocks.blockGasPipeFilter, "Gas Pipe Filter");
			addBlock(ElectrodynamicsBlocks.blockFluidPipeFilter, "Fluid Pipe Filter");
			
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.relay), "Relay");

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
			
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeGasPipe.UNINSULATEDCOPPER), "Copper Gas Pipe");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeGasPipe.UNINSULATEDSTEEL), "Steel Gas Pipe");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeGasPipe.UNINSULATEDPLASTIC), "Plastic Gas Pipe");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeGasPipe.WOOLINSULATEDCOPPER), "Woolen Copper Gas Pipe");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeGasPipe.WOOLINSULATEDSTEEL), "Woolen Steel Gas Pipe");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeGasPipe.WOOLINSULATEDPLASTIC), "Woolen Plastic Gas Pipe");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeGasPipe.CERAMICINSULATEDCOPPER), "Ceramic Copper Gas Pipe");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeGasPipe.CERAMICINSULATEDSTEEL), "Ceramic Steel Gas Pipe");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeGasPipe.CERAMICINSULATEDPLASTIC), "Ceramic Plastic Gas Pipe");
			
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.gastanksteel), "Steel Gas Cylinder");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.gastankreinforced), "Reinforced Gas Cylinder");
			addBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.gastankhsla), "HSLA Gas Cylinder");

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
			addGas(ElectrodynamicsGases.HYDROGEN, "Hydrogen");
			addGas(ElectrodynamicsGases.OXYGEN, "Oxygen");

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
			
			addContainer(SubtypeMachine.gastanksteel, "Steel Gas Cylinder");
			addContainer(SubtypeMachine.gastankreinforced, "Reinforced Gas Cylinder");
			addContainer(SubtypeMachine.gastankhsla, "HSLA Gas Cylinder");
			addContainer("compressor", "Compressor");
			addContainer("decompressor", "Decompressor");
			addContainer(SubtypeMachine.gasvent, "Gas Vent");
			addContainer("thermoelectricmanipulator", "Thermoelectric Manipulator");
			
			addContainer("gaspipepump", "Gas Pipe Pump");
			addContainer("fluidpipepump" ,"Fluid Pipe Pump");
			addContainer("gaspipefilter", "Gas Pipe Filter");
			addContainer("fluidpipefilter", "Fluid Pipe Filter");

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
			addTooltip("ceramicplatecount", "Plates Remaining: %s");
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
			
			addTooltip("pipematerial", "Material: %s");
			addTooltip("pipeinsulationmaterial", "Insulation: %s");
			addTooltip("pipemaximumpressure", "Max Pressure: %s");
			addTooltip("pipeheatloss", "Heat Loss: -%s / block");
			
			addTooltip("pipematerialcopper", "Copper");
			addTooltip("pipematerialsteel", "Steel");
			addTooltip("pipematerialplastic", "Plastic");
			
			addTooltip("pipeinsulationnone", "None");
			addTooltip("pipeinsulationwool", "Wool");
			addTooltip("pipeinsulationceramic", "Ceramic");
			
			addTooltip("currentgas", "Gas: %s");
			addTooltip("gasamount", "Amount: %1$s / %2$s");
			addTooltip("gastemperature", "Temp: %s");
			addTooltip("gaspressure", "Pressure: %s");
			
			addTooltip("tankmaxin", "In %1$s : %2$s");
			addTooltip("tankmaxout", "Out %1$s : %2$s");
			
			addTooltip("addontankcap", "Increases capacity by %s");
			addTooltip("gastank.capacity", "Capacity : %s");
			
			addTooltip("gasvalve", "Blocks gases when powered with redstone");
			addTooltip("fluidvalve", "Blocks fluids when powered with redstone");

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
			
			addGuiLabel("thermoelectricmanipulator.temp", "Temperature:");
			
			addGuiLabel("prioritypump.priority", "Priority");

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
			addSubtitle(ElectrodynamicsSounds.SOUND_BATTERY_SWAP, "Battery is swapped");
			addSubtitle(ElectrodynamicsSounds.SOUND_PRESSURERELEASE, "Gas hisses");
			addSubtitle(ElectrodynamicsSounds.SOUND_COMPRESSORRUNNING, "Compressor pressurizes gas");
			addSubtitle(ElectrodynamicsSounds.SOUND_DECOMPRESSORRUNNING, "Decompressor depressurizes gas");

			addGuidebook("title", "Electrodynamics Electric Code 1st Edition");
			addGuidebook("titlequote", "\"There is nothing more permanent than a temporary solution.\"");

			addGuidebook("availablemodules", "Available Modules");
			addGuidebook("chapters", "Chapters");

			addGuidebook(References.ID, "Electrodynamics");

			addGuidebook("chapter.gettingstarted", "Getting Started");

			addGuidebook("chapter.gettingstarted.l1",
					"Electrodynamics is a mod based around realistic electricity and more realistic concepts in general. As a result, you will find it plays very differently than other tech mods you're used to. The main difference will be that Electro "
							+ "is much more involved, as that is the price that comes with realism. Keep this in mind as you progress in the mod! If you are brand new to this mod, I highly recommend you read the section on Electricity, as it will help you "
							+ "immensely. Another important concept to note is that Electrodynamics is not designed to stand on its own. It is based on the concept of the old Universal Electricity mod, in which you have a mod with basic concepts and technology, " 
							+ "and then have several addon mods that hook into those concepts.");
			addGuidebook("chapter.gettingstarted.l2", 
					"In terms of actually getting started with Electrodynamics, you will need Steel and a source of power. Steel is created by smelting Iron Ingots in a Blast Furnace. The first power source you will use is the Thermoelectric Generator. I highly encourage installing a mod like JEI, "
					+ "as it will make finding recipes much easier!");

			addGuidebook("chapter.ores", "Ores");
			addGuidebook("chapter.ores.spawnrange", "Y = %1$s to Y = %2$s");
			addGuidebook("chapter.ores.material", "Material: %s");
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

			addGuidebook("chapter.electricity", "Electricity");

			addGuidebook("chapter.electricity.l1", "Understanding how energy and electricity work is key if you want to do well in Electrodynamics. Energy is what machines use to do work, and it is measured in units of Joules (J). However, the energy needs to be \"flowing\" in order to do work. The rate "
					+ "of flow is measured in units of Volts (V). The pressure or force of the flow in measured in units of Amperes or Amps for short (A or I). The amount of energy transfered in one second is known as the Power and is measured in units of " + "Watts (W). Power can easily be found through the formula P = IV, where I is the current and V is the voltage.");

			addGuidebook("chapter.electricity.l2", "Now, how does this impact you and what do you need to pay attention to? All machines require energy at a specific voltage to function. The voltage will be displayed in the tooltip usually. However, " + "if you do not have access to this, there are a couple of methods of checking.");

			addGuidebook("chapter.electricity.l3", "The first method is to look at the base of the machine in question. Most machines have a set of colored diagonal lines, with the color being directly related to the voltage. The " + "following voltages are represented by the following colors:");

			addGuidebook("chapter.electricity.voltageval", "%1$s V : %2$s");
			addGuidebook("chapter.electricity.yellow", "Yellow");
			addGuidebook("chapter.electricity.blue", "Blue");
			addGuidebook("chapter.electricity.red", "Red");
			addGuidebook("chapter.electricity.purple", "Purple");

			addGuidebook("chapter.electricity.l4", "The following pages contain examples of machines with these markings:");

			addGuidebook("chapter.electricity.voltageexample", "Examples of %sV machines:");
			addGuidebook("chapter.electricity.voltageexamplenote", "Note the markings do not have to be at the base.");

			addGuidebook("chapter.electricity.l5", "The second method for checking a machine's voltage is to look at the voltage displayed by the energy tooltip in it's GUI. This tooltip will also tell you useful information such as the " + "wattage of the machine. The next page contains examples of this tooltip:");

			addGuidebook("chapter.electricity.guivoltagenote", "Note, the tooltip can also contain other useful info.");

			addGuidebook("chapter.electricity.l6", "However, not every machine has markings such as the Quarry or has a GUI like the Pump, so it's important to pay attention when placing cables and machines. Too low of a " + "voltage, and a machine won't run. Too high of a voltage, and the machine will explode!");

			addGuidebook("chapter.electricity.l7",
					"Now that we have a basic understanding of voltage and how it works, it's time to understand how to get energy into the machine. All machines that use energy will have an I/O port to connect to. However, unlike the voltage indicators discussed previously, these " + "ports are universal to all machines that use energy. There are two ports for energy:");

			addGuidebook("chapter.electricity.energyinput", "Red : Input");
			addGuidebook("chapter.electricity.energyoutput", "Grey : Output");

			addGuidebook("chapter.electricity.l8", "Here is an example of each:");

			addGuidebook("chapter.electricity.l9",
					"However, what is connected to these ports? Now it's time to discuss how electricity is transfered: Wires. Wires in this mod function a little differently than what you're used to. If you hover over a "
							+ "wire in your inventory, you will notice it has a resistance and a maximum rated current. The resistance determines how much energy is lost over the wire. You can use P = I*I*R to to calculate the exact amount "
							+ "lost. The longer a wire, the greater the energy loss. In order to minimize power loss, ideal cable networks will have short segments with high curent and long segments with low current. This design is "
							+ "highlighted by the maximum current rating of a wire, known as its Ampacity. If this limit is reached, the cable will catch on fire and be destroyed! You will also notice that there are insulated and non-insulated wires. "
							+ "Non-insulated wires will shock the player on contact, while the insulated variant will protect the player up to a rated voltage (also displayed in the wire's tooltip). It is important to note that wires do " + "not store energy like Univeral Cables or Flux Ducts!");

			addGuidebook("chapter.electricity.l10",
					"Now that we know how to get energy to a machine and understand it must be at a specific voltage, you're probably wondering how that voltage is achieved. Most power sources in Electrodynamics are 120V or 240V, which works well for some basic machines, " + "but that simply won't cut it for higher voltage machines. This is where the Upgrade and Downgrade Transformers come "
							+ "in. The Upgrade Transformer will take any input voltage at any current and output double voltage at half the current. The Downgrade Transformer takes any input voltage at any current and outputs half the voltage at "
							+ "double the current. Note, Transformers are not 100% efficient, so be wise with your use of them. Also, Transformers will instantly kill you if you walk over them while energized!");

			addGuidebook("chapter.electricity.l11", "In summary, machines need energy at a specific voltage to work. There are multiple methods of finding this voltage. Machines have specific colored ports for energy input and output. Energy is transfered into machines using wires, with the type of wire "
					+ "used determining how the cable network performs. Voltages can be stepped up and stepped down using transformers. The following are a set of equations that can be useful when working with Joules:");

			addGuidebook("chapter.electricity.powerfromenergy", "P = E / time");
			addGuidebook("chapter.electricity.powerfromvoltage", "P = V * I");
			addGuidebook("chapter.electricity.energytickstoseconds", "E/s = (E/tick) * 20");
			addGuidebook("chapter.electricity.voltagefromresistance", "V = I * R");
			addGuidebook("chapter.electricity.powerfromcurrent", "P = I*I*R");

			addGuidebook("chapter.fluids", "Fluids");

			addGuidebook("chapter.fluids.l1", "Fluids play an important role just like energy in Electrodynamics. They're used for crafting various materials and for cooling machinery. Fortunately, if you have been able to grasp energy, then fluid mechanics should be a breeze, as they are very similar " + "to how other mods work.");

			addGuidebook("chapter.fluids.l2", "As with energy, fluids have their own I/O ports. These ports are universal to any machine that uses or produces fluid. They are:");
			addGuidebook("chapter.fluids.fluidinput", "Input: Blue");
			addGuidebook("chapter.fluids.fluidoutput", "Output: Yellow");
			addGuidebook("chapter.fluids.l3", "Here are some examples:");

			addGuidebook("chapter.fluids.l4",
					"However, what do we hook up to these ports? The answer is simple: Pipes! Unlike other mods, pipes in Electrodynamics have no internal storage buffer, which means they will not transfer a fluid unless it has somewhere to go. This means you don't have to worry about a machine "
							+ "outputting a fluid if you hook up a pipe accidentally. Also, Electrodynamics machines will only accept fluids they can process with! Pipes have a limited transfer rate similar to the Ampacity of wires. However, unlike wires, they "
							+ "will not explode if this limit is reached. The downside though is that your choice of pipe is limited to the following throughput capacities:");

			addGuidebook("chapter.fluids.pipecapacity", "%1$s : %2$s mB ");
			addGuidebook("chapter.fluids.pipecopper", "Copper");
			addGuidebook("chapter.fluids.pipesteel", "Steel");

			addGuidebook("chapter.fluids.l5", "But what if I need to store a large amount of fluid? Fear not, as Electrodynamics has you covered there. Electrodynamics tanks accept fluid from the top and output fluid through the bottom. Furthermore, stack two tanks on top of eachother, " + "and the top one will automatically output into the bottom one.");

			addGuidebook("chapter.fluids.l6", "One final thing to note is that Electrodynamics fluids cannot be carried by a bucket. For example, it simply doesn't make sense to transfer Sulfuric Acid in a bucket made out of Iron. If you need to manually move fluids such as Sulfuric Acid, you will need to "
					+ "use a Reinforced Canister! All fluid machines have input and output bucket slots that can be used for manually filling and draining the machine. If you need to manually empty an input tank, click a Reinforced Canister on it and the fluid will drain into the canister. To void any fluid, " + "either pipe or manually transfer it into ");

			addGuidebook("chapter.generators", "Generators");
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

			addGuidebook("chapter.machines.chargerheader", "Understanding the Charger:");
			addGuidebook("chapter.machines.l1", "The Charger does what the name suggests and charges items. When charging an item, it's crucial to select a Charger with the correct voltage. If the charger's voltage is less than the item's, the item will only be charged to a percentage of it's full charge. This can be " + "calculated by the formula:");
			addGuidebook("chapter.machines.chargeformula", "Vcharger / Vitem");

			addGuidebook("chapter.machines.l2", "If the charger's voltage is greater than the item's, it will explode!");
			addGuidebook("chapter.machines.l3", "It is possible to operate a charger with battery power using the 3 battery slots:");

			addGuidebook("chapter.machines.l4", "The charge of the item placed in one of these slots will be transfered to the item in the charging slot. Along with actual batteries, any item with a charge can be used! However, be mindful of the item's voltage. If it is less than the voltage of the charger, it "
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

			addGuidebook("chapter.quarry.step7the", "The ");

			addGuidebook("chapter.quarry.step7unbreaking", "Unbreaking");
			addGuidebook("chapter.quarry.step7unbreakingdesc", " Upgrade reduces the durability used from the Drill Head when a block is mined. A maximum of 3 can be used for an effect of Unbreaking III, and it will cause the Quarry to draw a large amount of power when installed.");

			addGuidebook("chapter.quarry.step7fortune", "Fortune");
			addGuidebook("chapter.quarry.step7fortunedesc", " Upgrade applies the Fortune enchantment to the blocks mined by the Quarry. A maximum of 3 can be used for an effect of Fortune III and it cannot be used in tandem with the Silk Touch Upgrade. The upgrade draws a large amount of power.");

			addGuidebook("chapter.quarry.step7silktouch", "Silk Touch");
			addGuidebook("chapter.quarry.step7silktouchdesc", " Upgrade will apply the Silk Touch enchantment to the blocks mined by the Quarry. A maximum of 1 can be used and it cannot be used in tandem with the Fortune Upgrade. The upgrade will cause the Quarry to draw a huge amount of power.");

			addGuidebook("chapter.quarry.step7void", "Void");
			addGuidebook("chapter.quarry.step7voiddesc", " Upgrade will activate 6 otherwise hidden slots in the GUI. Any items placed in these slots will be voided by the Quarry when mined. The upgrade will cause the Quarry to start drawing its base usage.");

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

			addGuidebook("chapter.armor.jetpack", "The Jetpack uses Condensed Hydrogen as fuel. It can be filled in either a tank or directly in the Electrolyzer. The Jetpack has 3 modes: Regular, Hover, and Off.");
			addGuidebook("chapter.armor.hydraulicboots", "Hydraulic Boots use Hydraulic Fluid as their \"fuel\". They can be filled either in a tank or directly in a Chemical Mixer. Hydraulic Boots will heavily reduce fall damage, but not completely eliminate it.");
			addGuidebook("chapter.armor.combatchestplate", "The Combat Chestplate uses Condensed Hydrogen as fuel. It can be filled in either a tank or directly in the Electrolyzer. The Combat Chestplate has 3 modes: Regular, Hover, and Off.");
			addGuidebook("chapter.armor.combatboots", "Combat Boots use Hydraulic Fluid as their \"fuel\". They can be filled either in a tank or directly in a Chemical Mixer. Combat Boots will heavily reduce fall damage, but not completely eliminate it.");

			addGuidebook("chapter.armor.ceramicheader", "Ceramic Plate Protection:");
			addGuidebook("chapter.armor.ceramicl1",
					"Composite and Combat armor have a unique ability: Ceramic Protection. To use this ability, you must first add Ceramic Plates to the chestplate. This is accomplished by Right-Clicking a Ceramic Plate while wearing one of the two chestplates. If sucessfull, you will hear " + "the sound of a velcro strap. A maximum of two plates can be be held by both chestplates.");

			addGuidebook("chapter.armor.ceramicl2", "For the ability to trigger, you must be wearing a full set of the armor type, and it must be a complete set. You must then take more than 16 damage. If these conditions are met, you will hear the plate break and a Ceramic Plate will be removed " + "from the chestplate. The damage dealt will then be reduced to its square root.");

			addGuidebook("chapter.misc", "Misc");
			addGuidebook("chapter.misc.l1", "Futurum Usui.");

			addGuidebook("chapter.tips", "Tips");
			addGuidebook("chapter.tips.tip", "Tip %s:");
			addGuidebook("chapter.tips.tip1", "Use Energy Storage frequently. Electrodynamics has several tiers of energy storage. You should invest in using them in all stages of progression. Note, the Capacity Upgrade not only increases the capacity of a Battery Box, but also the output voltage!");

			addGuidebook("chapter.tips.tip2", "Transmit at High Voltage. This is mentioned in the Electricity chapter, but you should transmit large amounts of power at high voltage and then step it down for distribution. This will help reduce the need for heavy-duty cables as well as the " + "amount of power lost due to the cable's own resistance!");

			addGuidebook("chapter.tips.tip3",
					"If you are using an electric tool from Electrodynamics or one of its surrounding mods, there is a good chance that the tool can have its battery replaced. This can be especially useful when you need to recharge a tool and don't have ready access to a charger. A battery can be replaced in one of two ways. "
							+ "The first way is to hold the tool in question in your main hand and press the \"R\" key. A battery with a matching voltage will then be selected from your inventory and placed inside of the tool. The current battery inside the tool will be then placed in your inventory. Note, this method will use the first battery it finds in your "
							+ "inventory, which might mean the battery you want may not be the one that gets used. The second and more precise way to replace a tool's battery is to hover over the tool in question with the battery in your inventory. Then, Right-Click the tool with the battery. If the voltage of the tool matches the voltage of the battery, the "
							+ "new battery will replace the old battery, and you will be left holding the old battery.");

			addGuidebook("searchparameters", "Parameters");
			addGuidebook("selectall", "All");
			addGuidebook("selectnone", "None");
			addGuidebook("casesensitive", "Case-Sensitive");

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
			return super.toString().toLowerCase(java.util.Locale.ROOT);
		}
	}

}
