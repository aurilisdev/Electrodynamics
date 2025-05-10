package electrodynamics.registers;

import electrodynamics.Electrodynamics;
import electrodynamics.common.block.subtype.SubtypeGlass;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypeConcrete;
import electrodynamics.common.block.subtype.SubtypeFluidPipe;
import electrodynamics.common.block.subtype.SubtypeResourceBlock;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.blockitem.BlockItemFluidPipe;
import electrodynamics.common.blockitem.BlockItemWire;
import electrodynamics.common.item.ItemCeramic;
import electrodynamics.common.item.ItemDrillHead;
import electrodynamics.common.item.gear.armor.types.ItemCompositeArmor;
import electrodynamics.common.item.gear.armor.types.ItemRubberArmor;
import electrodynamics.common.item.gear.tools.ItemCanister;
import electrodynamics.common.item.gear.tools.ItemMultimeter;
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
import electrodynamics.common.item.subtype.SubtypeNugget;
import electrodynamics.common.item.subtype.SubtypeOxide;
import electrodynamics.common.item.subtype.SubtypePlate;
import electrodynamics.common.item.subtype.SubtypeRod;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import voltaic.api.registration.BulkRegistryObject;
import voltaic.common.blockitem.BlockItemDescriptable;
import voltaic.common.item.ItemBoneMeal;
import voltaic.common.item.ItemDescriptable;
import voltaic.common.item.ItemUpgrade;
import voltaic.common.item.ItemVoltaic;
import voltaic.common.item.subtype.SubtypeItemUpgrade;
import voltaic.prefab.item.ElectricItemProperties;
import voltaic.prefab.item.ItemElectric;
import voltaic.prefab.utilities.object.TransferPack;

public class ElectrodynamicsItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Electrodynamics.ID);

	/* BLOCKS */
	public static final BulkRegistryObject<BlockItemDescriptable, SubtypeOre> ITEMS_ORE = new BulkRegistryObject<>(SubtypeOre.values(), subtype -> ITEMS.register(subtype.tag(), () -> new BlockItemDescriptable(ElectrodynamicsBlocks.BLOCKS_ORE.getValue(subtype), new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN)));
	public static final BulkRegistryObject<BlockItemDescriptable, SubtypeResourceBlock> ITEMS_RESOURCEBLOCK = new BulkRegistryObject<>(SubtypeResourceBlock.values(), subtype -> ITEMS.register(subtype.tag(), () -> new BlockItemDescriptable(ElectrodynamicsBlocks.BLOCKS_RESOURCE.getValue(subtype), new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN)));
	public static final BulkRegistryObject<BlockItemDescriptable, SubtypeGlass> ITEMS_CUSTOMGLASS = new BulkRegistryObject<>(SubtypeGlass.values(), subtype -> ITEMS.register(subtype.tag(), () -> new BlockItemDescriptable(ElectrodynamicsBlocks.BLOCKS_CUSTOMGLASS.getValue(subtype), new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN)));
	public static final BulkRegistryObject<BlockItemDescriptable, SubtypeConcrete> ITEMS_CONCRETE = new BulkRegistryObject<>(SubtypeConcrete.values(), subtype -> ITEMS.register(subtype.tag(), () -> new BlockItemDescriptable(ElectrodynamicsBlocks.BLOCKS_CONCRETE.getValue(subtype), new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN)));
	public static final BulkRegistryObject<BlockItemDescriptable, SubtypeMachine> ITEMS_MACHINE = new BulkRegistryObject<>(SubtypeMachine.values(), subtype -> {
		if(subtype == SubtypeMachine.downgradetransformer || subtype == SubtypeMachine.upgradetransformer || subtype == SubtypeMachine.multimeterblock || subtype == SubtypeMachine.circuitbreaker || subtype == SubtypeMachine.relay || subtype == SubtypeMachine.potentiometer || subtype == SubtypeMachine.advanceddowngradetransformer || subtype == SubtypeMachine.advancedupgradetransformer || subtype == SubtypeMachine.circuitmonitor || subtype == SubtypeMachine.currentregulator) {
			return ITEMS.register(subtype.tag(), () -> new BlockItemDescriptable(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(subtype), new Item.Properties(), subtype.showInItemGroup() ? () -> ElectrodynamicsCreativeTabs.GRID : null));
		} else {
			return ITEMS.register(subtype.tag(), () -> new BlockItemDescriptable(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(subtype), new Item.Properties(), subtype.showInItemGroup() ? () -> ElectrodynamicsCreativeTabs.MAIN : null));
		}

	});

	public static final RegistryObject<BlockItemDescriptable> ITEM_STEELSCAFFOLD = ITEMS.register("steelscaffold", () -> new BlockItemDescriptable(ElectrodynamicsBlocks.BLOCK_STEELSCAFFOLDING.get(), new Item.Properties().stacksTo(64), () -> ElectrodynamicsCreativeTabs.MAIN));
	public static final RegistryObject<BlockItemDescriptable> ITEM_FRAME = ITEMS.register("frame", () -> new BlockItemDescriptable(ElectrodynamicsBlocks.BLOCK_FRAME.get(), new Item.Properties().stacksTo(64), null));
	public static final RegistryObject<BlockItemDescriptable> ITEM_FRAMECORNER = ITEMS.register("framecorner", () -> new BlockItemDescriptable(ElectrodynamicsBlocks.BLOCK_FRAME_CORNER.get(), new Item.Properties().stacksTo(64), null));
	public static final RegistryObject<BlockItemDescriptable> ITEM_LOGISTICALMANAGER = ITEMS.register("logisticalmanager", () -> new BlockItemDescriptable(ElectrodynamicsBlocks.BLOCK_LOGISTICALMANAGER.get(), new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN));;
	public static final RegistryObject<BlockItemDescriptable> ITEM_SEISMICMARKER = ITEMS.register("seismicmarker", () -> new BlockItemDescriptable(ElectrodynamicsBlocks.BLOCK_SEISMICMARKER.get(), new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN));
	public static final BulkRegistryObject<BlockItemWire, SubtypeWire> ITEMS_WIRE = new BulkRegistryObject<>(SubtypeWire.values(), subtype -> ITEMS.register(subtype.tag(), () -> new BlockItemWire(ElectrodynamicsBlocks.BLOCKS_WIRE.getValue(subtype), new Item.Properties(), () -> ElectrodynamicsCreativeTabs.GRID)));
	public static final BulkRegistryObject<BlockItemFluidPipe, SubtypeFluidPipe> ITEMS_PIPE = new BulkRegistryObject<>(SubtypeFluidPipe.values(), subtype -> ITEMS.register(subtype.tag(), () -> new BlockItemFluidPipe(ElectrodynamicsBlocks.BLOCKS_FLUIDPIPE.getValue(subtype), new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN)));


	/* ITEMS */

	public static final BulkRegistryObject<ItemVoltaic, SubtypeIngot> ITEMS_INGOT = new BulkRegistryObject<>(SubtypeIngot.values(), subtype -> ITEMS.register(subtype.tag(), () -> new ItemVoltaic(new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN)));
	public static final BulkRegistryObject<ItemVoltaic, SubtypeNugget> ITEMS_NUGGET = new BulkRegistryObject<>(SubtypeNugget.values(), subtype -> ITEMS.register(subtype.tag(), () -> new ItemVoltaic(new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN)));
	public static final BulkRegistryObject<ItemVoltaic, SubtypeDust> ITEMS_DUST = new BulkRegistryObject<>(SubtypeDust.values(), subtype -> ITEMS.register(subtype.tag(), () -> new ItemVoltaic(new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN)));
	public static final BulkRegistryObject<ItemVoltaic, SubtypeImpureDust> ITEMS_IMPUREDUST = new BulkRegistryObject<>(SubtypeImpureDust.values(), subtype -> ITEMS.register(subtype.tag(), () -> new ItemVoltaic(new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN)));
	public static final BulkRegistryObject<ItemVoltaic, SubtypeCrystal> ITEMS_CRYSTAL = new BulkRegistryObject<>(SubtypeCrystal.values(), subtype -> ITEMS.register(subtype.tag(), () -> new ItemVoltaic(new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN)));
	public static final BulkRegistryObject<ItemVoltaic, SubtypeOxide> ITEMS_OXIDE = new BulkRegistryObject<>(SubtypeOxide.values(), subtype -> ITEMS.register(subtype.tag(), () -> new ItemVoltaic(new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN)));
	public static final BulkRegistryObject<ItemVoltaic, SubtypeGear> ITEMS_GEAR = new BulkRegistryObject<>(SubtypeGear.values(), subtype -> ITEMS.register(subtype.tag(), () -> new ItemVoltaic(new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN)));
	public static final BulkRegistryObject<ItemVoltaic, SubtypePlate> ITEMS_PLATE = new BulkRegistryObject<>(SubtypePlate.values(), subtype -> ITEMS.register(subtype.tag(), () -> new ItemVoltaic(new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN)));
	public static final BulkRegistryObject<ItemVoltaic, SubtypeCircuit> ITEMS_CIRCUIT = new BulkRegistryObject<>(SubtypeCircuit.values(), subtype -> ITEMS.register(subtype.tag(), () -> new ItemVoltaic(new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN)));
	public static final BulkRegistryObject<ItemVoltaic, SubtypeRod> ITEMS_ROD = new BulkRegistryObject<>(SubtypeRod.values(), subtype -> ITEMS.register(subtype.tag(), () -> new ItemVoltaic(new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN)));
	public static final BulkRegistryObject<ItemUpgrade, SubtypeItemUpgrade> ITEMS_UPGRADE = new BulkRegistryObject<>(SubtypeItemUpgrade.values(), subtype -> ITEMS.register(subtype.tag(), () -> new ItemUpgrade(new Item.Properties(), subtype, () -> ElectrodynamicsCreativeTabs.MAIN)));
	public static final BulkRegistryObject<ItemCeramic, SubtypeCeramic> ITEMS_CERAMIC = new BulkRegistryObject<>(SubtypeCeramic.values(), subtype -> ITEMS.register(subtype.tag(), () -> new ItemCeramic(subtype)));
	public static final BulkRegistryObject<ItemDrillHead, SubtypeDrillHead> ITEMS_DRILLHEAD = new BulkRegistryObject<>(SubtypeDrillHead.values(), subtype -> ITEMS.register(subtype.tag(), () -> new ItemDrillHead(subtype)));

	public static final RegistryObject<ItemVoltaic> ITEM_COAL_COKE = ITEMS.register("coalcoke", () -> new ItemVoltaic(new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN));
	public static final RegistryObject<ItemVoltaic> ITEM_SLAG = ITEMS.register("slag", () -> new ItemVoltaic(new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN));
	public static final RegistryObject<ItemVoltaic> ITEM_SHEETPLASTIC = ITEMS.register("sheetplastic", () -> new ItemVoltaic(new Item.Properties().stacksTo(64), () -> ElectrodynamicsCreativeTabs.MAIN));
	public static final RegistryObject<ItemVoltaic> ITEM_RAWCOMPOSITEPLATING = ITEMS.register("compositeplatingraw", () -> new ItemVoltaic(new Item.Properties().stacksTo(64), () -> ElectrodynamicsCreativeTabs.MAIN));
	public static final RegistryObject<ItemVoltaic> ITEM_COMPOSITEPLATING = ITEMS.register("compositeplating", () -> new ItemVoltaic(new Item.Properties().stacksTo(64), () -> ElectrodynamicsCreativeTabs.MAIN));
	public static final RegistryObject<ItemBoneMeal> ITEM_MOLYBDENUMFERTILIZER = ITEMS.register("molybdenumfertilizer", () -> new ItemBoneMeal(new Item.Properties().stacksTo(64), () -> ElectrodynamicsCreativeTabs.MAIN));
	public static final RegistryObject<ItemVoltaic> ITEM_INSULATION = ITEMS.register("insulation", () -> new ItemVoltaic(new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN));
	public static final RegistryObject<ItemVoltaic> ITEM_CERAMICINSULATION = ITEMS.register("insulationceramic", () -> new ItemVoltaic(new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN));
	public static final RegistryObject<ItemVoltaic> ITEM_COIL = ITEMS.register("coil", () -> new ItemVoltaic(new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN));
	public static final RegistryObject<ItemVoltaic> ITEM_LAMINATEDCOIL = ITEMS.register("laminatedcoil", () -> new ItemVoltaic(new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN));
	public static final RegistryObject<ItemVoltaic> ITEM_TITANIUM_COIL = ITEMS.register("titaniumheatcoil", () -> new ItemVoltaic(new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN));
	public static final RegistryObject<ItemVoltaic> ITEM_MOTOR = ITEMS.register("motor", () -> new ItemVoltaic(new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN));
	public static final RegistryObject<ItemVoltaic> ITEM_SOLARPANELPLATE = ITEMS.register("solarpanelplate", () -> new ItemVoltaic(new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN));
	public static final RegistryObject<ItemVoltaic> ITEM_PLASTIC_FIBERS = ITEMS.register("plasticfibers", () -> new ItemVoltaic(new Item.Properties().stacksTo(64), () -> ElectrodynamicsCreativeTabs.MAIN));
	public static final RegistryObject<ItemDescriptable> ITEM_CONCRETEMIX = ITEMS.register("concretemix", () -> new ItemDescriptable(new Item.Properties().stacksTo(64), () -> ElectrodynamicsCreativeTabs.MAIN, ElectroTextUtils.tooltip("concretemixjoke"))); //TODO add translation

	public static final RegistryObject<ItemVoltaic> ITEM_BATTERY = ITEMS.register("battery", () -> new ItemElectric((ElectricItemProperties) new ElectricItemProperties().capacity(1666666.66667).extract(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).receive(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).setIsEnergyStorageOnly().stacksTo(1), () -> ElectrodynamicsCreativeTabs.MAIN));
	public static final RegistryObject<ItemElectric> ITEM_LITHIUMBATTERY = ITEMS.register("lithiumbattery", () -> new ItemElectric((ElectricItemProperties) new ElectricItemProperties().capacity(4 * 1666666.66667).extract(TransferPack.joulesVoltage(4 * 1666666.66667 / (120.0 * 20.0), 240)).receive(TransferPack.joulesVoltage(4 * 1666666.66667 / (120.0 * 20.0), 240)).setIsEnergyStorageOnly().stacksTo(1), () -> ElectrodynamicsCreativeTabs.MAIN));
	public static final RegistryObject<ItemElectric> ITEM_CARBYNEBATTERY = ITEMS.register("carbynebattery", () -> new ItemElectric((ElectricItemProperties) new ElectricItemProperties().capacity(8 * 1666666.66667).extract(TransferPack.joulesVoltage(8 * 1666666.66667 / (120.0 * 20.0), 480)).receive(TransferPack.joulesVoltage(8 * 1666666.66667 / (120.0 * 20.0), 480)).setIsEnergyStorageOnly().stacksTo(1), () -> ElectrodynamicsCreativeTabs.MAIN));

	public static final RegistryObject<ItemMultimeter> ITEM_MULTIMETER = ITEMS.register("multimeter", () -> new ItemMultimeter(new Item.Properties().stacksTo(1), () -> ElectrodynamicsCreativeTabs.MAIN));

	public static final RegistryObject<ItemElectricDrill> ITEM_ELECTRICDRILL = ITEMS.register("electricdrill", () -> new ItemElectricDrill((ElectricItemProperties) new ElectricItemProperties().capacity(1666666.66667).extract(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).receive(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).stacksTo(1), () -> ElectrodynamicsCreativeTabs.MAIN));
	public static final RegistryObject<ItemElectricChainsaw> ITEM_ELECTRICCHAINSAW = ITEMS.register("electricchainsaw", () -> new ItemElectricChainsaw((ElectricItemProperties) new ElectricItemProperties().capacity(1666666.66667).extract(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).receive(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).stacksTo(1), () -> ElectrodynamicsCreativeTabs.MAIN));
	public static final RegistryObject<ItemElectricBaton> ITEM_ELECTRICBATON = ITEMS.register("electricbaton", () -> new ItemElectricBaton((ElectricItemProperties) new ElectricItemProperties().capacity(1666666.66667 * 4).extract(TransferPack.joulesVoltage(4 * 1666666.66667 / (120.0 * 20.0), 240)).receive(TransferPack.joulesVoltage(4 * 1666666.66667 / (120.0 * 20.0), 240)).stacksTo(1), () -> ElectrodynamicsCreativeTabs.MAIN));
	public static final RegistryObject<ItemRailgunKinetic> ITEM_KINETICRAILGUN = ITEMS.register("railgunkinetic", () -> new ItemRailgunKinetic((ElectricItemProperties) new ElectricItemProperties().capacity(1666666.66667 * 4).extract(TransferPack.joulesVoltage(4 * 1666666.66667, 240)).receive(TransferPack.joulesVoltage(4 * 1666666.66667 / (120.0 * 20.0), 240)).stacksTo(1), () -> ElectrodynamicsCreativeTabs.MAIN));
	public static final RegistryObject<ItemRailgunPlasma> ITEM_PLASMARAILGUN = ITEMS.register("railgunplasma", () -> new ItemRailgunPlasma((ElectricItemProperties) new ElectricItemProperties().capacity(1666666.66667 * 8).extract(TransferPack.joulesVoltage(8 * 1666666.66667, 480)).receive(TransferPack.joulesVoltage(8 * 1666666.66667 / (120.0 * 20.0), 480)).stacksTo(1), () -> ElectrodynamicsCreativeTabs.MAIN));
	public static final RegistryObject<ItemSeismicScanner> ITEM_SEISMICSCANNER = ITEMS.register("seismicscanner", () -> new ItemSeismicScanner((ElectricItemProperties) new ElectricItemProperties().capacity(1666666.66667).extract(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).receive(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).stacksTo(1), () -> ElectrodynamicsCreativeTabs.MAIN));
	public static final RegistryObject<ItemMechanizedCrossbow> ITEM_MECHANIZEDCROSSBOW = ITEMS.register("mechanizedcrossbow", () -> new ItemMechanizedCrossbow((ElectricItemProperties) new ElectricItemProperties().capacity(1666666.66667).extract(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).receive(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).stacksTo(1), () -> ElectrodynamicsCreativeTabs.MAIN));

	public static final RegistryObject<ItemCompositeArmor> ITEM_COMPOSITEHELMET = ITEMS.register("compositearmorhelmet", () -> new ItemCompositeArmor(EquipmentSlotType.HEAD));
	public static final RegistryObject<ItemCompositeArmor> ITEM_COMPOSITECHESTPLATE = ITEMS.register("compositearmorchestplate", () -> new ItemCompositeArmor(EquipmentSlotType.CHEST));
	public static final RegistryObject<ItemCompositeArmor> ITEM_COMPOSITELEGGINGS = ITEMS.register("compositearmorleggings", () -> new ItemCompositeArmor(EquipmentSlotType.LEGS));
	public static final RegistryObject<ItemCompositeArmor> ITEM_COMPOSITEBOOTS = ITEMS.register("compositearmorboots", () -> new ItemCompositeArmor(EquipmentSlotType.FEET));

	public static final RegistryObject<ItemRubberArmor> ITEM_RUBBERBOOTS = ITEMS.register("rubberboots", () -> new ItemRubberArmor(EquipmentSlotType.FEET, new Item.Properties().stacksTo(1).durability(100000), () -> ElectrodynamicsCreativeTabs.MAIN));

	public static final RegistryObject<ItemCanister> ITEM_CANISTERREINFORCED = ITEMS.register("canisterreinforced", () -> new ItemCanister(new Item.Properties().stacksTo(1), () -> ElectrodynamicsCreativeTabs.MAIN));
	
}
