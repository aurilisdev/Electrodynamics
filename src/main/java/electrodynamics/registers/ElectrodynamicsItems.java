package electrodynamics.registers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import electrodynamics.api.ISubtype;
import electrodynamics.api.References;
import electrodynamics.common.block.connect.BlockGasPipe;
import electrodynamics.common.block.connect.BlockWire;
import electrodynamics.common.block.subtype.SubtypeGlass;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypeOreDeepslate;
import electrodynamics.common.block.subtype.SubtypeFluidPipe;
import electrodynamics.common.block.subtype.SubtypeGasPipe;
import electrodynamics.common.block.subtype.SubtypeRawOreBlock;
import electrodynamics.common.block.subtype.SubtypeResourceBlock;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.blockitem.BlockItemDescriptable;
import electrodynamics.common.blockitem.BlockItemWire;
import electrodynamics.common.blockitem.BlockItemGasPipe;
import electrodynamics.common.item.ItemCeramic;
import electrodynamics.common.item.ItemDrillHead;
import electrodynamics.common.item.ItemUpgrade;
import electrodynamics.common.item.gear.armor.types.ItemCombatArmor;
import electrodynamics.common.item.gear.armor.types.ItemCompositeArmor;
import electrodynamics.common.item.gear.armor.types.ItemHydraulicBoots;
import electrodynamics.common.item.gear.armor.types.ItemJetpack;
import electrodynamics.common.item.gear.armor.types.ItemNightVisionGoggles;
import electrodynamics.common.item.gear.armor.types.ItemRubberArmor;
import electrodynamics.common.item.gear.armor.types.ItemServoLeggings;
import electrodynamics.common.item.gear.tools.ItemCanister;
import electrodynamics.common.item.gear.tools.ItemGuidebook;
import electrodynamics.common.item.gear.tools.ItemPortableCylinder;
import electrodynamics.common.item.gear.tools.ItemMultimeter;
import electrodynamics.common.item.gear.tools.ItemWrench;
import electrodynamics.common.item.gear.tools.electric.ItemElectricBaton;
import electrodynamics.common.item.gear.tools.electric.ItemElectricChainsaw;
import electrodynamics.common.item.gear.tools.electric.ItemElectricDrill;
import electrodynamics.common.item.gear.tools.electric.ItemMechanizedCrossbow;
import electrodynamics.common.item.gear.tools.electric.ItemRailgunKinetic;
import electrodynamics.common.item.gear.tools.electric.ItemRailgunPlasma;
import electrodynamics.common.item.gear.tools.electric.ItemSeismicScanner;
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
import electrodynamics.prefab.item.ElectricItemProperties;
import electrodynamics.prefab.item.ItemElectric;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ElectrodynamicsItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, References.ID);
	public static final HashMap<ISubtype, RegistryObject<Item>> SUBTYPEITEMREGISTER_MAPPINGS = new HashMap<>();

	//yes this is dirty but it works
	public static RegistryObject<Item> ITEM_COMPRESSOR;
	public static RegistryObject<Item> ITEM_DECOMPRESSOR;
	public static RegistryObject<Item> ITEM_THERMOELECTRIC_MANIPULATOR;
	
	public static RegistryObject<Item> ITEM_COMPRESSOR_ADDONTANK;
	
	public static RegistryObject<Item> ITEM_FRAME;
	public static RegistryObject<Item> ITEM_FRAMECORNER;
	public static RegistryObject<Item> ITEM_LOGISTICALMANAGER;
	public static RegistryObject<Item> ITEM_SEISMICMARKER;
	
	public static RegistryObject<Item> ITEM_FLUIDVALVE;
	public static RegistryObject<Item> ITEM_FLUIDPIPEPUMP;
	public static RegistryObject<Item> ITEM_FLUIDPIPEFILTER;
	
	public static RegistryObject<Item> ITEM_GASVALVE;
	public static RegistryObject<Item> ITEM_GASPIPEPUMP;
	public static RegistryObject<Item> ITEM_GASPIPEFILTER;
	
	public static RegistryObject<Item> ITEM_STEELSCAFFOLD;
	
	static {
		//Blocks
		registerSubtypeBlockItem(SubtypeOre.values());
		registerSubtypeBlockItem(SubtypeOreDeepslate.values());
		registerSubtypeBlockItem(SubtypeRawOreBlock.values());
		registerSubtypeBlockItem(SubtypeResourceBlock.values());
		registerSubtypeBlockItem(SubtypeGlass.values());
		for(SubtypeMachine machine : SubtypeMachine.values()) {
			if(machine == SubtypeMachine.downgradetransformer || machine == SubtypeMachine.upgradetransformer || machine == SubtypeMachine.multimeterblock || machine == SubtypeMachine.circuitbreaker || machine == SubtypeMachine.relay || machine == SubtypeMachine.potentiometer) {
				SUBTYPEITEMREGISTER_MAPPINGS.put(machine, ITEMS.register(machine.tag(), () -> new BlockItemDescriptable(() -> ElectrodynamicsBlocks.SUBTYPEBLOCKREGISTER_MAPPINGS.get(machine).get(), new Item.Properties().tab(References.GRIDTAB))));
			} else {
				SUBTYPEITEMREGISTER_MAPPINGS.put(machine, ITEMS.register(machine.tag(), () -> new BlockItemDescriptable(() -> ElectrodynamicsBlocks.SUBTYPEBLOCKREGISTER_MAPPINGS.get(machine).get(), new Item.Properties().tab(References.CORETAB))));
			}
			
		}
		ITEM_STEELSCAFFOLD = ITEMS.register("steelscaffold", () -> new BlockItemDescriptable(() -> ElectrodynamicsBlocks.blockSteelScaffold, new Item.Properties().stacksTo(64).tab(References.CORETAB)));
		ITEM_FRAME = ITEMS.register("frame", () -> new BlockItemDescriptable(() -> ElectrodynamicsBlocks.blockFrame, new Item.Properties().stacksTo(64)));
		ITEM_FRAMECORNER = ITEMS.register("framecorner", () -> new BlockItemDescriptable(() -> ElectrodynamicsBlocks.blockFrameCorner, new Item.Properties().stacksTo(64)));
		ITEM_LOGISTICALMANAGER = ITEMS.register("logisticalmanager", () -> new BlockItemDescriptable(() -> ElectrodynamicsBlocks.blockLogisticalManager, new Item.Properties().tab(References.CORETAB)));
		ITEM_SEISMICMARKER = ITEMS.register("seismicmarker", () -> new BlockItemDescriptable(() -> ElectrodynamicsBlocks.blockSeismicMarker, new Item.Properties().tab(References.CORETAB)));
		
		ITEM_COMPRESSOR = ITEMS.register("compressor", () -> new BlockItemDescriptable(() -> ElectrodynamicsBlocks.blockCompressor, new Item.Properties().tab(References.CORETAB)));
		ITEM_DECOMPRESSOR = ITEMS.register("decompressor", () -> new BlockItemDescriptable(() -> ElectrodynamicsBlocks.blockDecompressor, new Item.Properties().tab(References.CORETAB)));
		ITEM_THERMOELECTRIC_MANIPULATOR = ITEMS.register("thermoelectricmanipulator", () -> new BlockItemDescriptable(() -> ElectrodynamicsBlocks.blockThermoelectricManipulator, new Item.Properties().tab(References.CORETAB)));
		ITEM_COMPRESSOR_ADDONTANK = ITEMS.register("compressoraddontank", () -> new BlockItemDescriptable(() -> ElectrodynamicsBlocks.blockGasTransformerAddonTank, new Item.Properties().tab(References.CORETAB)));
		
		for (SubtypeWire subtype : SubtypeWire.values()) {
			SUBTYPEITEMREGISTER_MAPPINGS.put(subtype, ITEMS.register(subtype.tag(), () -> new BlockItemWire((BlockWire) ElectrodynamicsBlocks.SUBTYPEBLOCKREGISTER_MAPPINGS.get(subtype).get(), new Item.Properties().tab(References.GRIDTAB))));
		}
		
		for (SubtypeFluidPipe subtype : SubtypeFluidPipe.values()) {
			SUBTYPEITEMREGISTER_MAPPINGS.put(subtype, ITEMS.register(subtype.tag(), () -> new BlockItemDescriptable(() -> ElectrodynamicsBlocks.SUBTYPEBLOCKREGISTER_MAPPINGS.get(subtype).get(), new Item.Properties().tab(References.CORETAB))));
		}
		ITEM_FLUIDVALVE = ITEMS.register("fluidvalve", () -> new BlockItemDescriptable(() -> ElectrodynamicsBlocks.blockFluidValve, new Item.Properties().tab(References.CORETAB)));
		ITEM_FLUIDPIPEPUMP = ITEMS.register("fluidpipepump", () -> new BlockItemDescriptable(() -> ElectrodynamicsBlocks.blockFluidPipePump, new Item.Properties().tab(References.CORETAB)));
		ITEM_FLUIDPIPEFILTER = ITEMS.register("fluidpipefilter", () -> new BlockItemDescriptable(() -> ElectrodynamicsBlocks.blockFluidPipeFilter, new Item.Properties().tab(References.CORETAB)));
		
		for (SubtypeGasPipe subtype : SubtypeGasPipe.values()) {
			SUBTYPEITEMREGISTER_MAPPINGS.put(subtype, ITEMS.register(subtype.tag(), () -> new BlockItemGasPipe((BlockGasPipe) ElectrodynamicsBlocks.SUBTYPEBLOCKREGISTER_MAPPINGS.get(subtype).get(), new Item.Properties().tab(References.CORETAB))));
		}
		ITEM_GASVALVE = ITEMS.register("gasvalve", () -> new BlockItemDescriptable(() -> ElectrodynamicsBlocks.blockGasValve, new Item.Properties().tab(References.CORETAB)));
		ITEM_GASPIPEPUMP = ITEMS.register("gaspipepump", () -> new BlockItemDescriptable(() -> ElectrodynamicsBlocks.blockGasPipePump, new Item.Properties().tab(References.CORETAB)));
		ITEM_GASPIPEFILTER = ITEMS.register("gaspipefilter", () -> new BlockItemDescriptable(() -> ElectrodynamicsBlocks.blockGasPipeFilter, new Item.Properties().tab(References.CORETAB)));
		
		//Items
		registerSubtypeItem(SubtypeRawOre.values());
		registerSubtypeItem(SubtypeIngot.values());
		registerSubtypeItem(SubtypeNugget.values());
		registerSubtypeItem(SubtypeDust.values());
		registerSubtypeItem(SubtypeImpureDust.values());
		registerSubtypeItem(SubtypeCrystal.values());
		registerSubtypeItem(SubtypeOxide.values());
		registerSubtypeItem(SubtypeGear.values());
		registerSubtypeItem(SubtypePlate.values());
		registerSubtypeItem(SubtypeCircuit.values());
		registerSubtypeItem(SubtypeRod.values());
		for (SubtypeItemUpgrade subtype : SubtypeItemUpgrade.values()) {
			SUBTYPEITEMREGISTER_MAPPINGS.put(subtype, ITEMS.register(subtype.tag(), () -> new ItemUpgrade(new Item.Properties().tab(References.CORETAB), subtype)));
		}
		for (SubtypeCeramic subtype : SubtypeCeramic.values()) {
			SUBTYPEITEMREGISTER_MAPPINGS.put(subtype, ITEMS.register(subtype.tag(), () -> new ItemCeramic(subtype)));
		}
		for (SubtypeDrillHead drill : SubtypeDrillHead.values()) {
			SUBTYPEITEMREGISTER_MAPPINGS.put(drill, ITEMS.register(drill.tag(), () -> new ItemDrillHead(drill)));
		}
	}

	public static final RegistryObject<Item> ITEM_COAL_COKE = ITEMS.register("coalcoke", () -> new Item(new Item.Properties().tab(References.CORETAB)));
	public static final RegistryObject<Item> ITEM_SLAG = ITEMS.register("slag", () -> new Item(new Item.Properties().tab(References.CORETAB)));
	public static final RegistryObject<Item> ITEM_SHEETPLASTIC = ITEMS.register("sheetplastic", () -> new Item(new Item.Properties().stacksTo(64).tab(References.CORETAB)));
	public static final RegistryObject<Item> ITEM_RAWCOMPOSITEPLATING = ITEMS.register("compositeplatingraw", () -> new Item(new Item.Properties().stacksTo(64).tab(References.CORETAB)));
	public static final RegistryObject<Item> ITEM_COMPOSITEPLATING = ITEMS.register("compositeplating", () -> new Item(new Item.Properties().stacksTo(64).tab(References.CORETAB)));
	public static final RegistryObject<Item> ITEM_MOLYBDENUMFERTILIZER = ITEMS.register("molybdenumfertilizer", () -> new BoneMealItem(new Item.Properties().stacksTo(64).tab(References.CORETAB)));
	public static final RegistryObject<Item> ITEM_INSULATION = ITEMS.register("insulation", () -> new Item(new Item.Properties().tab(References.CORETAB)));
	public static final RegistryObject<Item> ITEM_CERAMICINSULATION = ITEMS.register("insulationceramic", () -> new Item(new Item.Properties().tab(References.CORETAB)));
	public static final RegistryObject<Item> ITEM_COIL = ITEMS.register("coil", () -> new Item(new Item.Properties().tab(References.CORETAB)));
	public static final RegistryObject<Item> ITEM_TITANIUM_COIL = ITEMS.register("titaniumheatcoil", () -> new Item(new Item.Properties().tab(References.CORETAB)));
	public static final RegistryObject<Item> ITEM_MOTOR = ITEMS.register("motor", () -> new Item(new Item.Properties().tab(References.CORETAB)));
	public static final RegistryObject<Item> ITEM_SOLARPANELPLATE = ITEMS.register("solarpanelplate", () -> new Item(new Item.Properties().tab(References.CORETAB)));
	public static final RegistryObject<Item> ITEM_PLASTIC_FIBERS = ITEMS.register("plasticfibers", () -> new Item(new Item.Properties().stacksTo(64).tab(References.CORETAB)));
	public static final RegistryObject<Item> ITEM_MECHANICALVALVE = ITEMS.register("mechanicalvalve", () -> new Item(new Item.Properties().stacksTo(64).tab(References.CORETAB)));
	public static final RegistryObject<Item> ITEM_PRESSUREGAGE = ITEMS.register("pressuregauge", () -> new Item(new Item.Properties().stacksTo(64).tab(References.CORETAB)));
	public static final RegistryObject<Item> ITEM_FIBERGLASSSHEET = ITEMS.register("fiberglasssheet", () -> new Item(new Item.Properties().stacksTo(64).tab(References.CORETAB)));
	

	public static final RegistryObject<Item> ITEM_BATTERY = ITEMS.register("battery", () -> new ItemElectric((ElectricItemProperties) new ElectricItemProperties().capacity(1666666.66667).extract(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).receive(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).setIsEnergyStorageOnly().tab(References.CORETAB).stacksTo(1), item -> Items.AIR));
	public static final RegistryObject<Item> ITEM_LITHIUMBATTERY = ITEMS.register("lithiumbattery", () -> new ItemElectric((ElectricItemProperties) new ElectricItemProperties().capacity(4 * 1666666.66667).extract(TransferPack.joulesVoltage(4 * 1666666.66667 / (120.0 * 20.0), 240)).receive(TransferPack.joulesVoltage(4 * 1666666.66667 / (120.0 * 20.0), 240)).setIsEnergyStorageOnly().tab(References.CORETAB).stacksTo(1), item -> Items.AIR));
	public static final RegistryObject<Item> ITEM_CARBYNEBATTERY = ITEMS.register("carbynebattery", () -> new ItemElectric((ElectricItemProperties) new ElectricItemProperties().capacity(8 * 1666666.66667).extract(TransferPack.joulesVoltage(8 * 1666666.66667 / (120.0 * 20.0), 480)).receive(TransferPack.joulesVoltage(8 * 1666666.66667 / (120.0 * 20.0), 480)).setIsEnergyStorageOnly().tab(References.CORETAB).stacksTo(1), item -> Items.AIR));
	
	public static final RegistryObject<Item> ITEM_WRENCH = ITEMS.register("wrench", () -> new ItemWrench(new Item.Properties().tab(References.CORETAB)));
	public static final RegistryObject<Item> ITEM_MULTIMETER = ITEMS.register("multimeter", () -> new ItemMultimeter(new Item.Properties().tab(References.CORETAB).stacksTo(1)));
	public static final RegistryObject<Item> ITEM_CANISTERREINFORCED = ITEMS.register("canisterreinforced", () -> new ItemCanister(new Item.Properties().stacksTo(1).tab(References.CORETAB)));
	public static final RegistryObject<Item> ITEM_PORTABLECYLINDER = ITEMS.register("portablecylinder", () -> new ItemPortableCylinder(new Item.Properties().tab(References.CORETAB).stacksTo(1)));

	public static final RegistryObject<Item> GUIDEBOOK = ITEMS.register("guidebook", () -> new ItemGuidebook(new Item.Properties().tab(References.CORETAB)));
	
	public static final RegistryObject<Item> ITEM_ELECTRICDRILL = ITEMS.register("electricdrill", () -> new ItemElectricDrill((ElectricItemProperties) new ElectricItemProperties().capacity(1666666.66667).extract(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).receive(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).tab(References.CORETAB).stacksTo(1)));
	public static final RegistryObject<Item> ITEM_ELECTRICCHAINSAW = ITEMS.register("electricchainsaw", () -> new ItemElectricChainsaw((ElectricItemProperties) new ElectricItemProperties().capacity(1666666.66667).extract(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).receive(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).tab(References.CORETAB).stacksTo(1)));
	public static final RegistryObject<Item> ITEM_ELECTRICBATON = ITEMS.register("electricbaton", () -> new ItemElectricBaton((ElectricItemProperties) new ElectricItemProperties().capacity(1666666.66667 * 4).extract(TransferPack.joulesVoltage(4 * 1666666.66667 / (120.0 * 20.0), 240)).receive(TransferPack.joulesVoltage(4 * 1666666.66667 / (120.0 * 20.0), 240)).tab(References.CORETAB).stacksTo(1)));
	public static final RegistryObject<Item> ITEM_KINETICRAILGUN = ITEMS.register("railgunkinetic", () -> new ItemRailgunKinetic((ElectricItemProperties) new ElectricItemProperties().capacity(1666666.66667 * 4).extract(TransferPack.joulesVoltage(4 * 1666666.66667, 240)).receive(TransferPack.joulesVoltage(4 * 1666666.66667 / (120.0 * 20.0), 240)).tab(References.CORETAB).stacksTo(1)));
	public static final RegistryObject<Item> ITEM_PLASMARAILGUN = ITEMS.register("railgunplasma", () -> new ItemRailgunPlasma((ElectricItemProperties) new ElectricItemProperties().capacity(1666666.66667 * 8).extract(TransferPack.joulesVoltage(8 * 1666666.66667, 480)).receive(TransferPack.joulesVoltage(8 * 1666666.66667 / (120.0 * 20.0), 480)).tab(References.CORETAB).stacksTo(1)));
	public static final RegistryObject<Item> ITEM_SEISMICSCANNER = ITEMS.register("seismicscanner", () -> new ItemSeismicScanner((ElectricItemProperties) new ElectricItemProperties().capacity(1666666.66667).extract(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).receive(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).tab(References.CORETAB).stacksTo(1)));
	public static final RegistryObject<Item> ITEM_MECHANIZEDCROSSBOW = ITEMS.register("mechanizedcrossbow", () -> new ItemMechanizedCrossbow((ElectricItemProperties) new ElectricItemProperties().capacity(1666666.66667).extract(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).receive(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).tab(References.CORETAB).stacksTo(1)));

	public static final RegistryObject<Item> ITEM_COMPOSITEHELMET = ITEMS.register("compositearmorhelmet", () -> new ItemCompositeArmor(EquipmentSlot.HEAD));
	public static final RegistryObject<Item> ITEM_COMPOSITECHESTPLATE = ITEMS.register("compositearmorchestplate", () -> new ItemCompositeArmor(EquipmentSlot.CHEST));
	public static final RegistryObject<Item> ITEM_COMPOSITELEGGINGS = ITEMS.register("compositearmorleggings", () -> new ItemCompositeArmor(EquipmentSlot.LEGS));
	public static final RegistryObject<Item> ITEM_COMPOSITEBOOTS = ITEMS.register("compositearmorboots", () -> new ItemCompositeArmor(EquipmentSlot.FEET));

	public static final RegistryObject<Item> ITEM_RUBBERBOOTS = ITEMS.register("rubberboots", () -> new ItemRubberArmor(EquipmentSlot.FEET, new Item.Properties().tab(References.CORETAB).stacksTo(1).defaultDurability(100000)));

	public static final RegistryObject<Item> ITEM_NIGHTVISIONGOGGLES = ITEMS.register("nightvisiongoggles", () -> new ItemNightVisionGoggles((ElectricItemProperties) new ElectricItemProperties().capacity(1666666.66667).extract(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).receive(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).tab(References.CORETAB).stacksTo(1)));
	public static final RegistryObject<Item> ITEM_HYDRAULICBOOTS = ITEMS.register("hydraulicboots", ItemHydraulicBoots::new);
	public static final RegistryObject<Item> ITEM_JETPACK = ITEMS.register("jetpack", ItemJetpack::new);
	public static final RegistryObject<Item> ITEM_SERVOLEGGINGS = ITEMS.register("servoleggings", () -> new ItemServoLeggings((ElectricItemProperties) new ElectricItemProperties().capacity(1666666.66667).extract(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).receive(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).tab(References.CORETAB).stacksTo(1).fireResistant()));

	public static final RegistryObject<Item> ITEM_COMBATHELMET = ITEMS.register("combatarmorhelmet", () -> new ItemCombatArmor(new ElectricItemProperties().capacity(1666666.66667).extract(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).receive(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).tab(References.CORETAB).stacksTo(1).fireResistant(), EquipmentSlot.HEAD));
	public static final RegistryObject<Item> ITEM_COMBATCHESTPLATE = ITEMS.register("combatarmorchestplate", () -> new ItemCombatArmor(new Item.Properties().stacksTo(1).tab(References.CORETAB).fireResistant(), EquipmentSlot.CHEST));
	public static final RegistryObject<Item> ITEM_COMBATLEGGINGS = ITEMS.register("combatarmorleggings", () -> new ItemCombatArmor(new ElectricItemProperties().capacity(1666666.66667).extract(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).receive(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).tab(References.CORETAB).stacksTo(1).fireResistant(), EquipmentSlot.LEGS));
	public static final RegistryObject<Item> ITEM_COMBATBOOTS = ITEMS.register("combatarmorboots", () -> new ItemCombatArmor(new Item.Properties().stacksTo(1).tab(References.CORETAB), EquipmentSlot.FEET));

	private static void registerSubtypeItem(ISubtype[] array) {
		for (ISubtype subtype : array) {
			SUBTYPEITEMREGISTER_MAPPINGS.put(subtype, ITEMS.register(subtype.tag(), () -> new Item(new Item.Properties().tab(References.CORETAB))));
		}
	}

	private static void registerSubtypeBlockItem(ISubtype[] array) {
		for (ISubtype subtype : array) {
			SUBTYPEITEMREGISTER_MAPPINGS.put(subtype, ITEMS.register(subtype.tag(), () -> new BlockItemDescriptable(() -> ElectrodynamicsBlocks.SUBTYPEBLOCKREGISTER_MAPPINGS.get(subtype).get(), new Item.Properties().tab(References.CORETAB))));
		}
	}

	public static Item[] getAllItemForSubtype(ISubtype[] values) {
		List<Item> list = new ArrayList<>();
		for (ISubtype value : values) {
			list.add(SUBTYPEITEMREGISTER_MAPPINGS.get(value).get());
		}
		return list.toArray(new Item[] {});
	}

	public static Item getItem(ISubtype value) {
		return SUBTYPEITEMREGISTER_MAPPINGS.get(value).get();
	}
}
