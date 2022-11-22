package electrodynamics.registers;

import static electrodynamics.registers.UnifiedElectrodynamicsRegister.supplier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import electrodynamics.api.ISubtype;
import electrodynamics.api.References;
import electrodynamics.common.block.connect.BlockWire;
import electrodynamics.common.block.subtype.SubtypeConcrete;
import electrodynamics.common.block.subtype.SubtypeGlass;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypeOreDeepslate;
import electrodynamics.common.block.subtype.SubtypePipe;
import electrodynamics.common.block.subtype.SubtypeRawOreBlock;
import electrodynamics.common.block.subtype.SubtypeResourceBlock;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.blockitem.BlockItemDescriptable;
import electrodynamics.common.blockitem.BlockItemWire;
import electrodynamics.common.item.ItemCeramic;
import electrodynamics.common.item.ItemDescriptable;
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
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ElectrodynamicsItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, References.ID);
	public static final HashMap<ISubtype, RegistryObject<Item>> SUBTYPEITEMREGISTER_MAPPINGS = new HashMap<>();

	static {
		registerSubtypeBlockItem(SubtypeOre.values());
		registerSubtypeBlockItem(SubtypeOreDeepslate.values());
		registerSubtypeBlockItem(SubtypeRawOreBlock.values());
		registerSubtypeBlockItem(SubtypeMachine.values());
		registerSubtypeBlockItem(SubtypeGlass.values());
		registerSubtypeBlockItem(SubtypeResourceBlock.values());
		registerSubtypeBlockItem(SubtypeConcrete.values());
		for (SubtypeWire subtype : SubtypeWire.values()) {
			SUBTYPEITEMREGISTER_MAPPINGS.put(subtype, ITEMS.register(subtype.tag(), supplier(() -> new BlockItemWire((BlockWire) ElectrodynamicsBlocks.SUBTYPEBLOCKREGISTER_MAPPINGS.get(subtype).get(), new Item.Properties().tab(References.CORETAB)), subtype)));
		}
		for (SubtypePipe subtype : SubtypePipe.values()) {
			SUBTYPEITEMREGISTER_MAPPINGS.put(subtype, ITEMS.register(subtype.tag(), supplier(() -> new BlockItemDescriptable(() -> ElectrodynamicsBlocks.SUBTYPEBLOCKREGISTER_MAPPINGS.get(subtype).get(), new Item.Properties().tab(References.CORETAB)), subtype)));
		}
		registerSubtypeItem(SubtypeRawOre.values());
		registerSubtypeItem(SubtypeIngot.values());
		registerSubtypeItem(SubtypeDust.values());
		registerSubtypeItem(SubtypeImpureDust.values());
		registerSubtypeItem(SubtypeCrystal.values());
		registerSubtypeItem(SubtypeOxide.values());
		registerSubtypeItem(SubtypeGear.values());
		registerSubtypeItem(SubtypePlate.values());
		registerSubtypeItem(SubtypeCircuit.values());
		registerSubtypeItem(SubtypeRod.values());
		registerSubtypeItem(SubtypeNugget.values());
		for (SubtypeItemUpgrade subtype : SubtypeItemUpgrade.values()) {
			SUBTYPEITEMREGISTER_MAPPINGS.put(subtype, ITEMS.register(subtype.tag(), supplier(() -> new ItemUpgrade(new Item.Properties().tab(References.CORETAB), subtype), subtype)));
		}
		for (SubtypeCeramic subtype : SubtypeCeramic.values()) {
			SUBTYPEITEMREGISTER_MAPPINGS.put(subtype, ITEMS.register(subtype.tag(), supplier(() -> new ItemCeramic(subtype), subtype)));
		}
		for (SubtypeDrillHead drill : SubtypeDrillHead.values()) {
			SUBTYPEITEMREGISTER_MAPPINGS.put(drill, ITEMS.register(drill.tag(), supplier(() -> new ItemDrillHead(drill), drill)));
		}
		
	}

	
	public static final RegistryObject<Item> ITEM_COMPOSITEPLATING = ITEMS.register("compositeplating", supplier(() -> new Item(new Item.Properties().stacksTo(64).tab(References.CORETAB))));
	public static final RegistryObject<Item> ITEM_SHEETPLASTIC = ITEMS.register("sheetplastic", supplier(() -> new Item(new Item.Properties().stacksTo(64).tab(References.CORETAB))));
	public static final RegistryObject<Item> ITEM_RAWCOMPOSITEPLATING = ITEMS.register("compositeplatingraw", supplier(() -> new Item(new Item.Properties().stacksTo(64).tab(References.CORETAB))));
	public static final RegistryObject<Item> ITEM_MOLYBDENUMFERTILIZER = ITEMS.register("molybdenumfertilizer", supplier(() -> new BoneMealItem(new Item.Properties().stacksTo(64).tab(References.CORETAB))));
	public static final RegistryObject<Item> ITEM_CONCRETEMIX = ITEMS.register("concretemix", supplier(() -> new ItemDescriptable(new Item.Properties().stacksTo(64).tab(References.CORETAB), "tooltip.info.concretejoke")));
	public static final RegistryObject<Item> ITEM_FRAME = ITEMS.register("frame", supplier(() -> new BlockItemDescriptable(() -> ElectrodynamicsBlocks.blockFrame, new Item.Properties().stacksTo(64))));
	public static final RegistryObject<Item> ITEM_FRAMECORNER = ITEMS.register("framecorner", supplier(() -> new BlockItemDescriptable(() -> ElectrodynamicsBlocks.blockFrameCorner, new Item.Properties().stacksTo(64))));
	public static final RegistryObject<Item> ITEM_LOGISTICALMANAGER = ITEMS.register("logisticalmanager", supplier(() -> new BlockItemDescriptable(() -> ElectrodynamicsBlocks.blockLogisticalManager, new Item.Properties().tab(References.CORETAB))));
	
	
	public static final RegistryObject<Item> ITEM_SEISMICMARKER = ITEMS.register("seismicmarker", supplier(() -> new BlockItemDescriptable(() -> ElectrodynamicsBlocks.blockSeismicMarker, new Item.Properties().tab(References.CORETAB))));

	public static final RegistryObject<Item> ITEM_INSULATION = ITEMS.register("insulation", supplier(() -> new Item(new Item.Properties().tab(References.CORETAB))));
	public static final RegistryObject<Item> ITEM_CERAMICINSULATION = ITEMS.register("insulationceramic", supplier(() -> new Item(new Item.Properties().tab(References.CORETAB))));
	public static final RegistryObject<Item> ITEM_MOTOR = ITEMS.register("motor", supplier(() -> new Item(new Item.Properties().tab(References.CORETAB))));
	public static final RegistryObject<Item> ITEM_BATTERY = ITEMS.register("battery", supplier(() -> new ItemElectric((ElectricItemProperties) new ElectricItemProperties().capacity(1666666.66667).extract(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).receive(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).tab(References.CORETAB).stacksTo(1))));
	public static final RegistryObject<Item> ITEM_LITHIUMBATTERY = ITEMS.register("lithiumbattery", supplier(() -> new ItemElectric((ElectricItemProperties) new ElectricItemProperties().capacity(4 * 1666666.66667).extract(TransferPack.joulesVoltage(4 * 1666666.66667 / (120.0 * 20.0), 240)).receive(TransferPack.joulesVoltage(4 * 1666666.66667 / (120.0 * 20.0), 240)).tab(References.CORETAB).stacksTo(1))));
	public static final RegistryObject<Item> ITEM_CARBYNEBATTERY = ITEMS.register("carbynebattery", supplier(() -> new ItemElectric((ElectricItemProperties) new ElectricItemProperties().capacity(8 * 1666666.66667).extract(TransferPack.joulesVoltage(8 * 1666666.66667 / (120.0 * 20.0), 480)).receive(TransferPack.joulesVoltage(8 * 1666666.66667 / (120.0 * 20.0), 480)).tab(References.CORETAB).stacksTo(1))));
	public static final RegistryObject<Item> ITEM_COIL = ITEMS.register("coil", supplier(() -> new Item(new Item.Properties().tab(References.CORETAB))));
	public static final RegistryObject<Item> ITEM_MULTIMETER = ITEMS.register("multimeter", supplier(() -> new ItemMultimeter(new Item.Properties().tab(References.CORETAB).stacksTo(1))));
	public static final RegistryObject<Item> ITEM_ELECTRICDRILL = ITEMS.register("electricdrill", supplier(() -> new ItemElectricDrill((ElectricItemProperties) new ElectricItemProperties().capacity(1000000).extract(TransferPack.joulesVoltage(1000, 240)).receive(TransferPack.joulesVoltage(1000, 240)).tab(References.CORETAB).stacksTo(1))));
	public static final RegistryObject<Item> ITEM_ELECTRICCHAINSAW = ITEMS.register("electricchainsaw", supplier(() -> new ItemElectricChainsaw((ElectricItemProperties) new ElectricItemProperties().capacity(1000000).extract(TransferPack.joulesVoltage(1000, 240)).receive(TransferPack.joulesVoltage(1000, 240)).tab(References.CORETAB).stacksTo(1))));
	public static final RegistryObject<Item> ITEM_ELECTRICBATON = ITEMS.register("electricbaton", supplier(() -> new ItemElectricBaton((ElectricItemProperties) new ElectricItemProperties().capacity(1000000).extract(TransferPack.joulesVoltage(1000, 240)).receive(TransferPack.joulesVoltage(1000, 240)).tab(References.CORETAB).stacksTo(1))));
	public static final RegistryObject<Item> ITEM_KINETICRAILGUN = ITEMS.register("railgunkinetic", supplier(() -> new ItemRailgunKinetic((ElectricItemProperties) new ElectricItemProperties().capacity(ItemRailgunKinetic.JOULES_PER_SHOT * 5).extract(TransferPack.joulesVoltage(ItemRailgunKinetic.JOULES_PER_SHOT * 5, 240)).receive(TransferPack.joulesVoltage(ItemRailgunKinetic.JOULES_PER_SHOT * 5, 240)).tab(References.CORETAB).stacksTo(1))));
	public static final RegistryObject<Item> ITEM_PLASMARAILGUN = ITEMS.register("railgunplasma", supplier(() -> new ItemRailgunPlasma((ElectricItemProperties) new ElectricItemProperties().capacity(ItemRailgunPlasma.JOULES_PER_SHOT * 10).extract(TransferPack.joulesVoltage(ItemRailgunKinetic.JOULES_PER_SHOT * 10, 480)).receive(TransferPack.joulesVoltage(ItemRailgunKinetic.JOULES_PER_SHOT * 10, 480)).tab(References.CORETAB).stacksTo(1))));
	public static final RegistryObject<Item> ITEM_SEISMICSCANNER = ITEMS.register("seismicscanner", supplier(() -> new ItemSeismicScanner((ElectricItemProperties) new ElectricItemProperties().capacity(ItemSeismicScanner.JOULES_PER_SCAN * 30).extract(TransferPack.joulesVoltage(ItemSeismicScanner.JOULES_PER_SCAN, 120)).receive(TransferPack.joulesVoltage(ItemSeismicScanner.JOULES_PER_SCAN, 120)).tab(References.CORETAB).stacksTo(1))));
	public static final RegistryObject<Item> ITEM_MECHANIZEDCROSSBOW = ITEMS.register("mechanizedcrossbow", supplier(() -> new ItemMechanizedCrossbow((ElectricItemProperties) new ElectricItemProperties().capacity(ItemMechanizedCrossbow.JOULES_PER_SHOT * ItemMechanizedCrossbow.NUMBER_OF_SHOTS).extract(TransferPack.joulesVoltage(ItemMechanizedCrossbow.JOULES_PER_SHOT, 120)).receive(TransferPack.joulesVoltage(ItemMechanizedCrossbow.JOULES_PER_SHOT, 120)).tab(References.CORETAB).stacksTo(1))));
	public static final RegistryObject<Item> ITEM_WRENCH = ITEMS.register("wrench", supplier(() -> new ItemWrench(new Item.Properties().tab(References.CORETAB))));
	public static final RegistryObject<Item> ITEM_SOLARPANELPLATE = ITEMS.register("solarpanelplate", supplier(() -> new Item(new Item.Properties().tab(References.CORETAB))));
	public static final RegistryObject<Item> ITEM_CANISTERREINFORCED = ITEMS.register("canisterreinforced", supplier(() -> new ItemCanister(new Item.Properties().stacksTo(1).tab(References.CORETAB))));
	public static final RegistryObject<Item> ITEM_TITANIUM_COIL = ITEMS.register("titaniumheatcoil", supplier(() -> new Item(new Item.Properties().tab(References.CORETAB))));
	public static final RegistryObject<Item> COAL_COKE = ITEMS.register("coalcoke", supplier(() -> new Item(new Item.Properties().tab(References.CORETAB))));
	public static final RegistryObject<Item> SLAG = ITEMS.register("slag", supplier(() -> new Item(new Item.Properties().tab(References.CORETAB))));
	public static final RegistryObject<Item> GUIDEBOOK = ITEMS.register("guidebook", supplier(() -> new ItemGuidebook(new Item.Properties().tab(References.CORETAB))));

	public static final RegistryObject<Item> ITEM_COMPOSITEHELMET = ITEMS.register("compositearmorhelmet", supplier(() -> new ItemCompositeArmor(EquipmentSlot.HEAD)));
	public static final RegistryObject<Item> ITEM_COMPOSITECHESTPLATE = ITEMS.register("compositearmorchestplate", supplier(() -> new ItemCompositeArmor(EquipmentSlot.CHEST)));
	public static final RegistryObject<Item> ITEM_COMPOSITELEGGINGS = ITEMS.register("compositearmorleggings", supplier(() -> new ItemCompositeArmor(EquipmentSlot.LEGS)));
	public static final RegistryObject<Item> ITEM_COMPOSITEBOOTS = ITEMS.register("compositearmorboots", supplier(() -> new ItemCompositeArmor(EquipmentSlot.FEET)));

	public static final RegistryObject<Item> ITEM_RUBBERBOOTS = ITEMS.register("rubberboots", supplier(() -> new ItemRubberArmor(EquipmentSlot.FEET, new Item.Properties().tab(References.CORETAB).stacksTo(1).defaultDurability(100000))));

	public static final RegistryObject<Item> ITEM_NIGHTVISIONGOGGLES = ITEMS.register("nightvisiongoggles", supplier(() -> new ItemNightVisionGoggles((ElectricItemProperties) new ElectricItemProperties().capacity(ItemNightVisionGoggles.JOULES_PER_TICK * 200000).extract(TransferPack.joulesVoltage(ItemNightVisionGoggles.JOULES_PER_TICK, 120)).receive(TransferPack.joulesVoltage(ItemNightVisionGoggles.JOULES_PER_TICK, 120)).tab(References.CORETAB).stacksTo(1))));
	public static final RegistryObject<Item> ITEM_HYDRAULICBOOTS = ITEMS.register("hydraulicboots", supplier(ItemHydraulicBoots::new));
	public static final RegistryObject<Item> ITEM_JETPACK = ITEMS.register("jetpack", supplier(ItemJetpack::new));
	public static final RegistryObject<Item> ITEM_SERVOLEGGINGS = ITEMS.register("servoleggings", supplier(() -> new ItemServoLeggings((ElectricItemProperties) new ElectricItemProperties().capacity(ItemServoLeggings.JOULES_PER_TICK * 200000).extract(TransferPack.joulesVoltage(ItemServoLeggings.JOULES_PER_TICK, 120)).receive(TransferPack.joulesVoltage(ItemServoLeggings.JOULES_PER_TICK, 120)).tab(References.CORETAB).stacksTo(1))));

	public static final RegistryObject<Item> ITEM_COMBATHELMET = ITEMS.register("combatarmorhelmet", supplier(() -> new ItemCombatArmor(new ElectricItemProperties().capacity(ItemNightVisionGoggles.JOULES_PER_TICK * 200000).extract(TransferPack.joulesVoltage(ItemNightVisionGoggles.JOULES_PER_TICK, 120)).receive(TransferPack.joulesVoltage(ItemNightVisionGoggles.JOULES_PER_TICK, 120)).tab(References.CORETAB).stacksTo(1), EquipmentSlot.HEAD)));
	public static final RegistryObject<Item> ITEM_COMBATCHESTPLATE = ITEMS.register("combatarmorchestplate", supplier(() -> new ItemCombatArmor(new Item.Properties().stacksTo(1).tab(References.CORETAB), EquipmentSlot.CHEST)));
	public static final RegistryObject<Item> ITEM_COMBATLEGGINGS = ITEMS.register("combatarmorleggings", supplier(() -> new ItemCombatArmor(new ElectricItemProperties().capacity(ItemServoLeggings.JOULES_PER_TICK * 200000).extract(TransferPack.joulesVoltage(ItemServoLeggings.JOULES_PER_TICK, 120)).receive(TransferPack.joulesVoltage(ItemServoLeggings.JOULES_PER_TICK, 120)).tab(References.CORETAB).stacksTo(1), EquipmentSlot.LEGS)));
	public static final RegistryObject<Item> ITEM_COMBATBOOTS = ITEMS.register("combatarmorboots", supplier(() -> new ItemCombatArmor(new Item.Properties().stacksTo(1).tab(References.CORETAB), EquipmentSlot.FEET)));

	private static void registerSubtypeItem(ISubtype[] array) {
		for (ISubtype subtype : array) {
			SUBTYPEITEMREGISTER_MAPPINGS.put(subtype, ITEMS.register(subtype.tag(), supplier(() -> new Item(new Item.Properties().tab(References.CORETAB)), subtype)));
		}
	}

	private static void registerSubtypeBlockItem(ISubtype[] array) {
		for (ISubtype subtype : array) {
			SUBTYPEITEMREGISTER_MAPPINGS.put(subtype, ITEMS.register(subtype.tag(), supplier(() -> new BlockItemDescriptable(() -> ElectrodynamicsBlocks.SUBTYPEBLOCKREGISTER_MAPPINGS.get(subtype).get(), new Item.Properties().tab(References.CORETAB)), subtype)));
		}
	}
	
	public static Item[] getAllItemForSubtype(ISubtype[] values){
		List<Item> list = new ArrayList<>();
		for(ISubtype value : values) {
			list.add(SUBTYPEITEMREGISTER_MAPPINGS.get(value).get());
		}
		return list.toArray(new Item[] {});
	}
	
	public static Item getItem(ISubtype value) {
		return SUBTYPEITEMREGISTER_MAPPINGS.get(value).get();
	}
}
