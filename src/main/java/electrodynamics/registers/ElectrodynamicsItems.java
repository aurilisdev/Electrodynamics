package electrodynamics.registers;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.Electrodynamics;
import electrodynamics.common.block.subtype.SubtypeFluidPipe;
import electrodynamics.common.block.subtype.SubtypeGasPipe;
import electrodynamics.common.block.subtype.SubtypeGlass;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypeOreDeepslate;
import electrodynamics.common.block.subtype.SubtypeRawOreBlock;
import electrodynamics.common.block.subtype.SubtypeResourceBlock;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.blockitem.BlockItemFluidPipe;
import electrodynamics.common.blockitem.BlockItemGasPipe;
import electrodynamics.common.blockitem.BlockItemWire;
import electrodynamics.common.item.ItemCeramic;
import electrodynamics.common.item.ItemDrillHead;
import electrodynamics.common.item.gear.armor.types.ItemCombatArmor;
import electrodynamics.common.item.gear.armor.types.ItemCompositeArmor;
import electrodynamics.common.item.gear.armor.types.ItemHydraulicBoots;
import electrodynamics.common.item.gear.armor.types.ItemJetpack;
import electrodynamics.common.item.gear.armor.types.ItemNightVisionGoggles;
import electrodynamics.common.item.gear.armor.types.ItemRubberArmor;
import electrodynamics.common.item.gear.armor.types.ItemServoLeggings;
import electrodynamics.common.item.gear.tools.ItemCanister;
import electrodynamics.common.item.gear.tools.ItemMultimeter;
import electrodynamics.common.item.gear.tools.ItemPortableCylinder;
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
import electrodynamics.common.item.subtype.SubtypeRawOre;
import electrodynamics.common.item.subtype.SubtypeRod;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import voltaic.api.creativetab.CreativeTabSupplier;
import voltaic.api.registration.BulkRegistryObject;
import voltaic.common.blockitem.BlockItemDescriptable;
import voltaic.common.item.ItemBoneMeal;
import voltaic.common.item.ItemVoltaic;
import voltaic.prefab.item.ElectricItemProperties;
import voltaic.prefab.item.ItemElectric;
import voltaic.prefab.utilities.object.TransferPack;

public class ElectrodynamicsItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Electrodynamics.ID);

	/* BLOCKS */
	public static final BulkRegistryObject<BlockItemDescriptable, SubtypeOre> ITEMS_ORE = new BulkRegistryObject<>(SubtypeOre.values(), subtype -> ITEMS.register(subtype.tag(), () -> new BlockItemDescriptable(ElectrodynamicsBlocks.BLOCKS_ORE.getValue(subtype), new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN.get())));
	public static final BulkRegistryObject<BlockItemDescriptable, SubtypeOreDeepslate> ITEMS_DEEPSLATEORE = new BulkRegistryObject<>(SubtypeOreDeepslate.values(), subtype -> ITEMS.register(subtype.tag(), () -> new BlockItemDescriptable(ElectrodynamicsBlocks.BLOCKS_DEEPSLATEORE.getValue(subtype), new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN.get())));
	public static final BulkRegistryObject<BlockItemDescriptable, SubtypeRawOreBlock> ITEMS_RAWOREBLOCK = new BulkRegistryObject<>(SubtypeRawOreBlock.values(), subtype -> ITEMS.register(subtype.tag(), () -> new BlockItemDescriptable(ElectrodynamicsBlocks.BLOCKS_RAWORE.getValue(subtype), new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN.get())));
	public static final BulkRegistryObject<BlockItemDescriptable, SubtypeResourceBlock> ITEMS_RESOURCEBLOCK = new BulkRegistryObject<>(SubtypeResourceBlock.values(), subtype -> ITEMS.register(subtype.tag(), () -> new BlockItemDescriptable(ElectrodynamicsBlocks.BLOCKS_RESOURCE.getValue(subtype), new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN.get())));
	public static final BulkRegistryObject<BlockItemDescriptable, SubtypeGlass> ITEMS_CUSTOMGLASS = new BulkRegistryObject<>(SubtypeGlass.values(), subtype -> ITEMS.register(subtype.tag(), () -> new BlockItemDescriptable(ElectrodynamicsBlocks.BLOCKS_CUSTOMGLASS.getValue(subtype), new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN.get())));
	public static final BulkRegistryObject<BlockItemDescriptable, SubtypeMachine> ITEMS_MACHINE = new BulkRegistryObject<>(SubtypeMachine.values(), subtype -> {
		if(subtype == SubtypeMachine.downgradetransformer || subtype == SubtypeMachine.upgradetransformer || subtype == SubtypeMachine.multimeterblock || subtype == SubtypeMachine.circuitbreaker || subtype == SubtypeMachine.relay || subtype == SubtypeMachine.potentiometer || subtype == SubtypeMachine.advanceddowngradetransformer || subtype == SubtypeMachine.advancedupgradetransformer || subtype == SubtypeMachine.circuitmonitor || subtype == SubtypeMachine.currentregulator) {
			return ITEMS.register(subtype.tag(), () -> new BlockItemDescriptable(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(subtype), new Item.Properties(), subtype.showInItemGroup() ? ElectrodynamicsCreativeTabs.GRID : null));
		}
		return ITEMS.register(subtype.tag(), () -> new BlockItemDescriptable(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(subtype), new Item.Properties(), subtype.showInItemGroup() ? () -> ElectrodynamicsCreativeTabs.MAIN.get() : null));

	});

	public static final RegistryObject<BlockItemDescriptable> ITEM_STEELSCAFFOLD = ITEMS.register("steelscaffold", () -> new BlockItemDescriptable(ElectrodynamicsBlocks.BLOCK_STEELSCAFFOLDING.get(), new Item.Properties().stacksTo(64), () -> ElectrodynamicsCreativeTabs.MAIN.get()));
	public static final RegistryObject<BlockItemDescriptable> ITEM_FRAME = ITEMS.register("frame", () -> new BlockItemDescriptable(ElectrodynamicsBlocks.BLOCK_FRAME.get(), new Item.Properties().stacksTo(64), null));
	public static final RegistryObject<BlockItemDescriptable> ITEM_FRAMECORNER = ITEMS.register("framecorner", () -> new BlockItemDescriptable(ElectrodynamicsBlocks.BLOCK_FRAME_CORNER.get(), new Item.Properties().stacksTo(64), null));
	public static final RegistryObject<BlockItemDescriptable> ITEM_LOGISTICALMANAGER = ITEMS.register("logisticalmanager", () -> new BlockItemDescriptable(ElectrodynamicsBlocks.BLOCK_LOGISTICALMANAGER.get(), new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN.get()));
	public static final RegistryObject<BlockItemDescriptable> ITEM_SEISMICMARKER = ITEMS.register("seismicmarker", () -> new BlockItemDescriptable(ElectrodynamicsBlocks.BLOCK_SEISMICMARKER.get(), new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN.get()));
	public static final RegistryObject<BlockItemDescriptable> ITEM_COMPRESSOR = ITEMS.register("compressor", () -> new BlockItemDescriptable(ElectrodynamicsBlocks.BLOCK_COMPRESSOR.get(), new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN.get()));
	public static final RegistryObject<BlockItemDescriptable> ITEM_DECOMPRESSOR = ITEMS.register("decompressor", () -> new BlockItemDescriptable(ElectrodynamicsBlocks.BLOCK_DECOMPRESSOR.get(), new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN.get()));
	public static final RegistryObject<BlockItemDescriptable> ITEM_ADVANCEDCOMPRESSOR = ITEMS.register("advancedcompressor", () -> new BlockItemDescriptable(ElectrodynamicsBlocks.BLOCK_ADVANCEDCOMPRESSOR.get(), new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN.get()));
	public static final RegistryObject<BlockItemDescriptable> ITEM_ADVANCEDDECOMPRESSOR = ITEMS.register("advanceddecompressor", () -> new BlockItemDescriptable(ElectrodynamicsBlocks.BLOCK_ADVANCEDDECOMPRESSOR.get(), new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN.get()));
	public static final RegistryObject<BlockItemDescriptable> ITEM_THERMOELECTRIC_MANIPULATOR = ITEMS.register("thermoelectricmanipulator", () -> new BlockItemDescriptable(ElectrodynamicsBlocks.BLOCK_THERMOELECTRICMANIPULATOR.get(), new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN.get()));
	public static final RegistryObject<BlockItemDescriptable> ITEM_ADVANCED_THERMOELECTRIC_MANIPULATOR = ITEMS.register("advancedthermoelectricmanipulator", () -> new BlockItemDescriptable(ElectrodynamicsBlocks.BLOCK_ADVANCED_THERMOELECTRICMANIPULATOR.get(), new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN.get()));
	public static final RegistryObject<BlockItemDescriptable> ITEM_COMPRESSOR_ADDONTANK = ITEMS.register("compressoraddontank", () -> new BlockItemDescriptable(ElectrodynamicsBlocks.BLOCK_COMPRESSOR_ADDONTANK.get(), new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN.get()));
	public static final BulkRegistryObject<BlockItemWire, SubtypeWire> ITEMS_WIRE = new BulkRegistryObject<>(SubtypeWire.values(), subtype -> ITEMS.register(subtype.tag(), () -> new BlockItemWire(ElectrodynamicsBlocks.BLOCKS_WIRE.getValue(subtype), new Item.Properties(), ElectrodynamicsCreativeTabs.GRID)));
	public static final BulkRegistryObject<BlockItemFluidPipe, SubtypeFluidPipe> ITEMS_PIPE = new BulkRegistryObject<>(SubtypeFluidPipe.values(), subtype -> ITEMS.register(subtype.tag(), () -> new BlockItemFluidPipe(ElectrodynamicsBlocks.BLOCKS_FLUIDPIPE.getValue(subtype), new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN.get())));
	public static final BulkRegistryObject<BlockItemGasPipe, SubtypeGasPipe> ITEMS_GASPIPE = new BulkRegistryObject<>(SubtypeGasPipe.values(), subtype -> ITEMS.register(subtype.tag(), () -> new BlockItemGasPipe(ElectrodynamicsBlocks.BLOCKS_GASPIPE.getValue(subtype), new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN.get())));


	/* ITEMS */

	public static final BulkRegistryObject<ItemVoltaic, SubtypeRawOre> ITEMS_RAWORE = new BulkRegistryObject<>(SubtypeRawOre.values(), subtype -> ITEMS.register(subtype.tag(), () -> new ItemVoltaic(new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN.get())));
	public static final BulkRegistryObject<ItemVoltaic, SubtypeIngot> ITEMS_INGOT = new BulkRegistryObject<>(SubtypeIngot.values(), subtype -> ITEMS.register(subtype.tag(), () -> new ItemVoltaic(new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN.get())));
	public static final BulkRegistryObject<ItemVoltaic, SubtypeNugget> ITEMS_NUGGET = new BulkRegistryObject<>(SubtypeNugget.values(), subtype -> ITEMS.register(subtype.tag(), () -> new ItemVoltaic(new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN.get())));
	public static final BulkRegistryObject<ItemVoltaic, SubtypeDust> ITEMS_DUST = new BulkRegistryObject<>(SubtypeDust.values(), subtype -> ITEMS.register(subtype.tag(), () -> new ItemVoltaic(new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN.get())));
	public static final BulkRegistryObject<ItemVoltaic, SubtypeImpureDust> ITEMS_IMPUREDUST = new BulkRegistryObject<>(SubtypeImpureDust.values(), subtype -> ITEMS.register(subtype.tag(), () -> new ItemVoltaic(new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN.get())));
	public static final BulkRegistryObject<ItemVoltaic, SubtypeCrystal> ITEMS_CRYSTAL = new BulkRegistryObject<>(SubtypeCrystal.values(), subtype -> ITEMS.register(subtype.tag(), () -> new ItemVoltaic(new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN.get())));
	public static final BulkRegistryObject<ItemVoltaic, SubtypeOxide> ITEMS_OXIDE = new BulkRegistryObject<>(SubtypeOxide.values(), subtype -> ITEMS.register(subtype.tag(), () -> new ItemVoltaic(new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN.get())));
	public static final BulkRegistryObject<ItemVoltaic, SubtypeGear> ITEMS_GEAR = new BulkRegistryObject<>(SubtypeGear.values(), subtype -> ITEMS.register(subtype.tag(), () -> new ItemVoltaic(new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN.get())));
	public static final BulkRegistryObject<ItemVoltaic, SubtypePlate> ITEMS_PLATE = new BulkRegistryObject<>(SubtypePlate.values(), subtype -> ITEMS.register(subtype.tag(), () -> new ItemVoltaic(new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN.get())));
	public static final BulkRegistryObject<ItemVoltaic, SubtypeCircuit> ITEMS_CIRCUIT = new BulkRegistryObject<>(SubtypeCircuit.values(), subtype -> ITEMS.register(subtype.tag(), () -> new ItemVoltaic(new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN.get())));
	public static final BulkRegistryObject<ItemVoltaic, SubtypeRod> ITEMS_ROD = new BulkRegistryObject<>(SubtypeRod.values(), subtype -> ITEMS.register(subtype.tag(), () -> new ItemVoltaic(new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN.get())));
	//public static final BulkRegistryObject<ItemUpgrade, SubtypeItemUpgrade> ITEMS_UPGRADE = new BulkRegistryObject<>(SubtypeItemUpgrade.values(), subtype -> ITEMS.register(subtype.tag(), () -> new ItemUpgrade(new Item.Properties(), subtype, () -> ElectrodynamicsCreativeTabs.MAIN.get())));
	public static final BulkRegistryObject<ItemCeramic, SubtypeCeramic> ITEMS_CERAMIC = new BulkRegistryObject<>(SubtypeCeramic.values(), subtype -> ITEMS.register(subtype.tag(), () -> new ItemCeramic(subtype)));
	public static final BulkRegistryObject<ItemDrillHead, SubtypeDrillHead> ITEMS_DRILLHEAD = new BulkRegistryObject<>(SubtypeDrillHead.values(), subtype -> ITEMS.register(subtype.tag(), () -> new ItemDrillHead(subtype)));

	public static final RegistryObject<ItemVoltaic> ITEM_COAL_COKE = ITEMS.register("coalcoke", () -> new ItemVoltaic(new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN.get()));
	public static final RegistryObject<ItemVoltaic> ITEM_SLAG = ITEMS.register("slag", () -> new ItemVoltaic(new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN.get()));
	public static final RegistryObject<ItemVoltaic> ITEM_SHEETPLASTIC = ITEMS.register("sheetplastic", () -> new ItemVoltaic(new Item.Properties().stacksTo(64), () -> ElectrodynamicsCreativeTabs.MAIN.get()));
	public static final RegistryObject<ItemVoltaic> ITEM_RAWCOMPOSITEPLATING = ITEMS.register("compositeplatingraw", () -> new ItemVoltaic(new Item.Properties().stacksTo(64), () -> ElectrodynamicsCreativeTabs.MAIN.get()));
	public static final RegistryObject<ItemVoltaic> ITEM_COMPOSITEPLATING = ITEMS.register("compositeplating", () -> new ItemVoltaic(new Item.Properties().stacksTo(64), () -> ElectrodynamicsCreativeTabs.MAIN.get()));
	public static final RegistryObject<ItemBoneMeal> ITEM_MOLYBDENUMFERTILIZER = ITEMS.register("molybdenumfertilizer", () -> new ItemBoneMeal(new Item.Properties().stacksTo(64), () -> ElectrodynamicsCreativeTabs.MAIN.get()));
	public static final RegistryObject<ItemVoltaic> ITEM_INSULATION = ITEMS.register("insulation", () -> new ItemVoltaic(new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN.get()));
	public static final RegistryObject<ItemVoltaic> ITEM_CERAMICINSULATION = ITEMS.register("insulationceramic", () -> new ItemVoltaic(new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN.get()));
	public static final RegistryObject<ItemVoltaic> ITEM_COIL = ITEMS.register("coil", () -> new ItemVoltaic(new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN.get()));
	public static final RegistryObject<ItemVoltaic> ITEM_LAMINATEDCOIL = ITEMS.register("laminatedcoil", () -> new ItemVoltaic(new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN.get()));
	public static final RegistryObject<ItemVoltaic> ITEM_TITANIUM_COIL = ITEMS.register("titaniumheatcoil", () -> new ItemVoltaic(new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN.get()));
	public static final RegistryObject<ItemVoltaic> ITEM_MOTOR = ITEMS.register("motor", () -> new ItemVoltaic(new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN.get()));
	public static final RegistryObject<ItemVoltaic> ITEM_SOLARPANELPLATE = ITEMS.register("solarpanelplate", () -> new ItemVoltaic(new Item.Properties(), () -> ElectrodynamicsCreativeTabs.MAIN.get()));
	public static final RegistryObject<ItemVoltaic> ITEM_PLASTIC_FIBERS = ITEMS.register("plasticfibers", () -> new ItemVoltaic(new Item.Properties().stacksTo(64), () -> ElectrodynamicsCreativeTabs.MAIN.get()));
	public static final RegistryObject<ItemVoltaic> ITEM_MECHANICALVALVE = ITEMS.register("mechanicalvalve", () -> new ItemVoltaic(new Item.Properties().stacksTo(64), () -> ElectrodynamicsCreativeTabs.MAIN.get()));
	public static final RegistryObject<ItemVoltaic> ITEM_PRESSUREGAGE = ITEMS.register("pressuregauge", () -> new ItemVoltaic(new Item.Properties().stacksTo(64), () -> ElectrodynamicsCreativeTabs.MAIN.get()));
	public static final RegistryObject<ItemVoltaic> ITEM_FIBERGLASSSHEET = ITEMS.register("fiberglasssheet", () -> new ItemVoltaic(new Item.Properties().stacksTo(64), () -> ElectrodynamicsCreativeTabs.MAIN.get()));

	public static final RegistryObject<ItemVoltaic> ITEM_BATTERY = ITEMS.register("battery", () -> new ItemElectric((ElectricItemProperties) new ElectricItemProperties().capacity(1666666.66667).extract(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).receive(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).setIsEnergyStorageOnly().stacksTo(1), () -> ElectrodynamicsCreativeTabs.MAIN.get(), item -> Items.AIR));
	public static final RegistryObject<ItemElectric> ITEM_LITHIUMBATTERY = ITEMS.register("lithiumbattery", () -> new ItemElectric((ElectricItemProperties) new ElectricItemProperties().capacity(4 * 1666666.66667).extract(TransferPack.joulesVoltage(4 * 1666666.66667 / (120.0 * 20.0), 240)).receive(TransferPack.joulesVoltage(4 * 1666666.66667 / (120.0 * 20.0), 240)).setIsEnergyStorageOnly().stacksTo(1), () -> ElectrodynamicsCreativeTabs.MAIN.get(), item -> Items.AIR));
	public static final RegistryObject<ItemElectric> ITEM_CARBYNEBATTERY = ITEMS.register("carbynebattery", () -> new ItemElectric((ElectricItemProperties) new ElectricItemProperties().capacity(8 * 1666666.66667).extract(TransferPack.joulesVoltage(8 * 1666666.66667 / (120.0 * 20.0), 480)).receive(TransferPack.joulesVoltage(8 * 1666666.66667 / (120.0 * 20.0), 480)).setIsEnergyStorageOnly().stacksTo(1), () -> ElectrodynamicsCreativeTabs.MAIN.get(), item -> Items.AIR));

	public static final RegistryObject<ItemMultimeter> ITEM_MULTIMETER = ITEMS.register("multimeter", () -> new ItemMultimeter(new Item.Properties().stacksTo(1), () -> ElectrodynamicsCreativeTabs.MAIN.get()));

	public static final RegistryObject<ItemElectricDrill> ITEM_ELECTRICDRILL = ITEMS.register("electricdrill", () -> new ItemElectricDrill((ElectricItemProperties) new ElectricItemProperties().capacity(1666666.66667).extract(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).receive(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).stacksTo(1), () -> ElectrodynamicsCreativeTabs.MAIN.get()));
	public static final RegistryObject<ItemElectricChainsaw> ITEM_ELECTRICCHAINSAW = ITEMS.register("electricchainsaw", () -> new ItemElectricChainsaw((ElectricItemProperties) new ElectricItemProperties().capacity(1666666.66667).extract(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).receive(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).stacksTo(1), () -> ElectrodynamicsCreativeTabs.MAIN.get()));
	public static final RegistryObject<ItemElectricBaton> ITEM_ELECTRICBATON = ITEMS.register("electricbaton", () -> new ItemElectricBaton((ElectricItemProperties) new ElectricItemProperties().capacity(1666666.66667 * 4).extract(TransferPack.joulesVoltage(4 * 1666666.66667 / (120.0 * 20.0), 240)).receive(TransferPack.joulesVoltage(4 * 1666666.66667 / (120.0 * 20.0), 240)).stacksTo(1), () -> ElectrodynamicsCreativeTabs.MAIN.get()));
	public static final RegistryObject<ItemRailgunKinetic> ITEM_KINETICRAILGUN = ITEMS.register("railgunkinetic", () -> new ItemRailgunKinetic((ElectricItemProperties) new ElectricItemProperties().capacity(1666666.66667 * 4).extract(TransferPack.joulesVoltage(4 * 1666666.66667, 240)).receive(TransferPack.joulesVoltage(4 * 1666666.66667 / (120.0 * 20.0), 240)).stacksTo(1), () -> ElectrodynamicsCreativeTabs.MAIN.get()));
	public static final RegistryObject<ItemRailgunPlasma> ITEM_PLASMARAILGUN = ITEMS.register("railgunplasma", () -> new ItemRailgunPlasma((ElectricItemProperties) new ElectricItemProperties().capacity(1666666.66667 * 8).extract(TransferPack.joulesVoltage(8 * 1666666.66667, 480)).receive(TransferPack.joulesVoltage(8 * 1666666.66667 / (120.0 * 20.0), 480)).stacksTo(1), () -> ElectrodynamicsCreativeTabs.MAIN.get()));
	public static final RegistryObject<ItemSeismicScanner> ITEM_SEISMICSCANNER = ITEMS.register("seismicscanner", () -> new ItemSeismicScanner((ElectricItemProperties) new ElectricItemProperties().capacity(1666666.66667).extract(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).receive(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).stacksTo(1), () -> ElectrodynamicsCreativeTabs.MAIN.get()));
	public static final RegistryObject<ItemMechanizedCrossbow> ITEM_MECHANIZEDCROSSBOW = ITEMS.register("mechanizedcrossbow", () -> new ItemMechanizedCrossbow((ElectricItemProperties) new ElectricItemProperties().capacity(1666666.66667).extract(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).receive(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).stacksTo(1), () -> ElectrodynamicsCreativeTabs.MAIN.get()));

	public static final RegistryObject<ItemCompositeArmor> ITEM_COMPOSITEHELMET = ITEMS.register("compositearmorhelmet", () -> new ItemCompositeArmor(Type.HELMET));
	public static final RegistryObject<ItemCompositeArmor> ITEM_COMPOSITECHESTPLATE = ITEMS.register("compositearmorchestplate", () -> new ItemCompositeArmor(Type.CHESTPLATE));
	public static final RegistryObject<ItemCompositeArmor> ITEM_COMPOSITELEGGINGS = ITEMS.register("compositearmorleggings", () -> new ItemCompositeArmor(Type.LEGGINGS));
	public static final RegistryObject<ItemCompositeArmor> ITEM_COMPOSITEBOOTS = ITEMS.register("compositearmorboots", () -> new ItemCompositeArmor(Type.BOOTS));

	public static final RegistryObject<ItemRubberArmor> ITEM_RUBBERBOOTS = ITEMS.register("rubberboots", () -> new ItemRubberArmor(Type.BOOTS, new Item.Properties().stacksTo(1).durability(100000), () -> ElectrodynamicsCreativeTabs.MAIN.get()));

	public static final RegistryObject<ItemNightVisionGoggles> ITEM_NIGHTVISIONGOGGLES = ITEMS.register("nightvisiongoggles", () -> new ItemNightVisionGoggles((ElectricItemProperties) new ElectricItemProperties().capacity(1666666.66667).extract(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).receive(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).stacksTo(1), () -> ElectrodynamicsCreativeTabs.MAIN.get()));
	public static final RegistryObject<ItemHydraulicBoots> ITEM_HYDRAULICBOOTS = ITEMS.register("hydraulicboots", ItemHydraulicBoots::new);
	public static final RegistryObject<ItemJetpack> ITEM_JETPACK = ITEMS.register("jetpack", ItemJetpack::new);
	public static final RegistryObject<ItemServoLeggings> ITEM_SERVOLEGGINGS = ITEMS.register("servoleggings", () -> new ItemServoLeggings((ElectricItemProperties) new ElectricItemProperties().capacity(1666666.66667).extract(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).receive(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).stacksTo(1).fireResistant(), () -> ElectrodynamicsCreativeTabs.MAIN.get()));

	public static final RegistryObject<ItemCombatArmor> ITEM_COMBATHELMET = ITEMS.register("combatarmorhelmet", () -> new ItemCombatArmor(new ElectricItemProperties().capacity(1666666.66667).extract(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).receive(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).stacksTo(1).fireResistant(), Type.HELMET, () -> ElectrodynamicsCreativeTabs.MAIN.get()));
	public static final RegistryObject<ItemCombatArmor> ITEM_COMBATCHESTPLATE = ITEMS.register("combatarmorchestplate", () -> new ItemCombatArmor(new Item.Properties().stacksTo(1).fireResistant(), Type.CHESTPLATE, () -> ElectrodynamicsCreativeTabs.MAIN.get()));
	public static final RegistryObject<ItemCombatArmor> ITEM_COMBATLEGGINGS = ITEMS.register("combatarmorleggings", () -> new ItemCombatArmor(new ElectricItemProperties().capacity(1666666.66667).extract(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).receive(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).stacksTo(1).fireResistant(), Type.LEGGINGS, () -> ElectrodynamicsCreativeTabs.MAIN.get()));
	public static final RegistryObject<ItemCombatArmor> ITEM_COMBATBOOTS = ITEMS.register("combatarmorboots", () -> new ItemCombatArmor(new Item.Properties().stacksTo(1), Type.BOOTS, () -> ElectrodynamicsCreativeTabs.MAIN.get()));

	public static final RegistryObject<ItemCanister> ITEM_CANISTERREINFORCED = ITEMS.register("canisterreinforced", () -> new ItemCanister(new Item.Properties().stacksTo(1), () -> ElectrodynamicsCreativeTabs.MAIN.get()));
	public static final RegistryObject<ItemPortableCylinder> ITEM_PORTABLECYLINDER = ITEMS.register("portablecylinder", () -> new ItemPortableCylinder(new Item.Properties().stacksTo(1), () -> ElectrodynamicsCreativeTabs.MAIN.get()));

	@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Electrodynamics.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
	private static class ElectroCreativeRegistry {

		@SubscribeEvent
		public static void registerItems(BuildCreativeModeTabContentsEvent event) {

			ITEMS.getEntries().forEach(reg -> {

				CreativeTabSupplier supplier = (CreativeTabSupplier) reg.get();

				if (supplier.hasCreativeTab() && supplier.isAllowedInCreativeTab(event.getTab())) {
					List<ItemStack> toAdd = new ArrayList<>();
					supplier.addCreativeModeItems(event.getTab(), toAdd);
					event.acceptAll(toAdd);
				}

			});

		}

	}

}
