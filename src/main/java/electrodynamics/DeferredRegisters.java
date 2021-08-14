package electrodynamics;

import java.util.HashMap;

import com.google.common.base.Supplier;
import com.google.common.collect.Sets;

import electrodynamics.api.ISubtype;
import electrodynamics.api.References;
import electrodynamics.common.block.BlockCustomGlass;
import electrodynamics.common.block.BlockMachine;
import electrodynamics.common.block.BlockMultiSubnode;
import electrodynamics.common.block.BlockOre;
import electrodynamics.common.block.BlockResource;
import electrodynamics.common.block.connect.BlockPipe;
import electrodynamics.common.block.connect.BlockWire;
import electrodynamics.common.block.subtype.SubtypeGlass;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypePipe;
import electrodynamics.common.block.subtype.SubtypeResourceBlock;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.blockitem.BlockItemDescriptable;
import electrodynamics.common.blockitem.BlockItemWire;
import electrodynamics.common.entity.projectile.types.EntityEnergyBlast;
import electrodynamics.common.entity.projectile.types.EntityMetalRod;
import electrodynamics.common.fluid.FluidEthanol;
import electrodynamics.common.fluid.FluidMineral;
import electrodynamics.common.fluid.FluidMolybdenum;
import electrodynamics.common.fluid.FluidPolyethylene;
import electrodynamics.common.fluid.FluidSulfuricAcid;
import electrodynamics.common.inventory.container.ContainerBatteryBox;
import electrodynamics.common.inventory.container.ContainerChargerGeneric;
import electrodynamics.common.inventory.container.ContainerChemicalCrystallizer;
import electrodynamics.common.inventory.container.ContainerChemicalMixer;
import electrodynamics.common.inventory.container.ContainerCoalGenerator;
import electrodynamics.common.inventory.container.ContainerDO2OProcessor;
import electrodynamics.common.inventory.container.ContainerElectricFurnace;
import electrodynamics.common.inventory.container.ContainerElectricFurnaceDouble;
import electrodynamics.common.inventory.container.ContainerElectricFurnaceTriple;
import electrodynamics.common.inventory.container.ContainerFermentationPlant;
import electrodynamics.common.inventory.container.ContainerLithiumBatteryBox;
import electrodynamics.common.inventory.container.ContainerMineralWasher;
import electrodynamics.common.inventory.container.ContainerO2OProcessor;
import electrodynamics.common.inventory.container.ContainerO2OProcessorDouble;
import electrodynamics.common.inventory.container.ContainerO2OProcessorTriple;
import electrodynamics.common.item.ItemCeramic;
import electrodynamics.common.item.ItemProcessorUpgrade;
import electrodynamics.common.item.gear.armor.types.composite.CompositeArmor;
import electrodynamics.common.item.gear.armor.types.composite.CompositeArmorItem;
import electrodynamics.common.item.gear.armor.types.rubber.ItemRubberArmor;
import electrodynamics.common.item.gear.tools.ItemCanister;
import electrodynamics.common.item.gear.tools.ItemMultimeter;
import electrodynamics.common.item.gear.tools.ItemWrench;
import electrodynamics.common.item.gear.tools.electric.ItemElectricChainsaw;
import electrodynamics.common.item.gear.tools.electric.ItemElectricDrill;
import electrodynamics.common.item.gear.tools.electric.ItemRailgunKinetic;
import electrodynamics.common.item.gear.tools.electric.ItemRailgunPlasma;
import electrodynamics.common.item.subtype.SubtypeCeramic;
import electrodynamics.common.item.subtype.SubtypeCircuit;
import electrodynamics.common.item.subtype.SubtypeCrystal;
import electrodynamics.common.item.subtype.SubtypeDust;
import electrodynamics.common.item.subtype.SubtypeGear;
import electrodynamics.common.item.subtype.SubtypeImpureDust;
import electrodynamics.common.item.subtype.SubtypeIngot;
import electrodynamics.common.item.subtype.SubtypeMineralFluid;
import electrodynamics.common.item.subtype.SubtypeOxide;
import electrodynamics.common.item.subtype.SubtypePlate;
import electrodynamics.common.item.subtype.SubtypeProcessorUpgrade;
import electrodynamics.common.item.subtype.SubtypeRod;
import electrodynamics.common.tile.TileAdvancedSolarPanel;
import electrodynamics.common.tile.TileBatteryBox;
import electrodynamics.common.tile.TileChargerHV;
import electrodynamics.common.tile.TileChargerLV;
import electrodynamics.common.tile.TileChargerMV;
import electrodynamics.common.tile.TileChemicalCrystallizer;
import electrodynamics.common.tile.TileChemicalMixer;
import electrodynamics.common.tile.TileCircuitBreaker;
import electrodynamics.common.tile.TileCoalGenerator;
import electrodynamics.common.tile.TileCombustionChamber;
import electrodynamics.common.tile.TileElectricFurnace;
import electrodynamics.common.tile.TileElectricFurnaceDouble;
import electrodynamics.common.tile.TileElectricFurnaceTriple;
import electrodynamics.common.tile.TileElectricPump;
import electrodynamics.common.tile.TileEnergizedAlloyer;
import electrodynamics.common.tile.TileFermentationPlant;
import electrodynamics.common.tile.TileHydroelectricGenerator;
import electrodynamics.common.tile.TileLathe;
import electrodynamics.common.tile.TileLithiumBatteryBox;
import electrodynamics.common.tile.TileMineralCrusher;
import electrodynamics.common.tile.TileMineralCrusherDouble;
import electrodynamics.common.tile.TileMineralCrusherTriple;
import electrodynamics.common.tile.TileMineralGrinder;
import electrodynamics.common.tile.TileMineralGrinderDouble;
import electrodynamics.common.tile.TileMineralGrinderTriple;
import electrodynamics.common.tile.TileMineralWasher;
import electrodynamics.common.tile.TileMultiSubnode;
import electrodynamics.common.tile.TileMultimeterBlock;
import electrodynamics.common.tile.TileOxidationFurnace;
import electrodynamics.common.tile.TileReinforcedAlloyer;
import electrodynamics.common.tile.TileSolarPanel;
import electrodynamics.common.tile.TileThermoelectricGenerator;
import electrodynamics.common.tile.TileTransformer;
import electrodynamics.common.tile.TileWindmill;
import electrodynamics.common.tile.TileWireMill;
import electrodynamics.common.tile.TileWireMillDouble;
import electrodynamics.common.tile.TileWireMillTriple;
import electrodynamics.common.tile.network.TileLogisticalWire;
import electrodynamics.common.tile.network.TilePipe;
import electrodynamics.common.tile.network.TileWire;
import electrodynamics.prefab.item.ElectricItemProperties;
import electrodynamics.prefab.item.ItemElectric;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class DeferredRegisters {
    public static final HashMap<ISubtype, Item> SUBTYPEITEM_MAPPINGS = new HashMap<>();
    public static final HashMap<Item, ISubtype> ITEMSUBTYPE_MAPPINGS = new HashMap<>();
    public static final HashMap<ISubtype, Block> SUBTYPEBLOCK_MAPPINGS = new HashMap<>();
    public static final HashMap<ISubtype, RegistryObject<Item>> SUBTYPEITEMREGISTER_MAPPINGS = new HashMap<>();
    public static final HashMap<ISubtype, RegistryObject<Block>> SUBTYPEBLOCKREGISTER_MAPPINGS = new HashMap<>();
    public static HashMap<ISubtype, FluidMineral> SUBTYPEMINERALFLUID_MAPPINGS = new HashMap<>();
    public static HashMap<FluidMineral, ISubtype> MINERALFLUIDSUBTYPE_MAPPINGS = new HashMap<>();
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, References.ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, References.ID);
    public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, References.ID);
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, References.ID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, References.ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, References.ID);
    public static BlockMultiSubnode multi = new BlockMultiSubnode();
    public static FluidEthanol fluidEthanol;
    public static FluidSulfuricAcid fluidSulfuricAcid;
    public static FluidPolyethylene fluidPolyethylene;
    public static FluidMolybdenum fluidMolybdenum;

    static {
	for (SubtypeOre subtype : SubtypeOre.values()) {
	    SUBTYPEBLOCKREGISTER_MAPPINGS.put(subtype, BLOCKS.register(subtype.tag(), supplier(new BlockOre(subtype), subtype)));
	}
	for (SubtypeMachine subtype : SubtypeMachine.values()) {
	    SUBTYPEBLOCKREGISTER_MAPPINGS.put(subtype, BLOCKS.register(subtype.tag(), supplier(new BlockMachine(subtype), subtype)));
	}
	for (SubtypeWire subtype : SubtypeWire.values()) {
	    SUBTYPEBLOCKREGISTER_MAPPINGS.put(subtype, BLOCKS.register(subtype.tag(), supplier(new BlockWire(subtype), subtype)));
	}
	for (SubtypePipe subtype : SubtypePipe.values()) {
	    SUBTYPEBLOCKREGISTER_MAPPINGS.put(subtype, BLOCKS.register(subtype.tag(), supplier(new BlockPipe(subtype), subtype)));
	}
	for (SubtypeGlass subtype : SubtypeGlass.values()) {
	    SUBTYPEBLOCKREGISTER_MAPPINGS.put(subtype, BLOCKS.register(subtype.tag(), supplier(new BlockCustomGlass(subtype), subtype)));
	}
	for (SubtypeResourceBlock subtype : SubtypeResourceBlock.values()) {
	    SUBTYPEBLOCKREGISTER_MAPPINGS.put(subtype, BLOCKS.register(subtype.tag(), supplier(new BlockResource(subtype), subtype)));
	}
	FLUIDS.register("fluidethanol", supplier(fluidEthanol = new FluidEthanol()));
	FLUIDS.register("fluidsulfuricacid", supplier(fluidSulfuricAcid = new FluidSulfuricAcid()));
	FLUIDS.register("fluidpolyethylene", supplier(fluidPolyethylene = new FluidPolyethylene()));
	for (SubtypeMineralFluid mineral : SubtypeMineralFluid.values()) {
	    FluidMineral fluid = new FluidMineral(mineral);
	    SUBTYPEMINERALFLUID_MAPPINGS.put(mineral, fluid);
	    MINERALFLUIDSUBTYPE_MAPPINGS.put(fluid, mineral);
	    FLUIDS.register("fluidmineral" + mineral.name(), supplier(fluid));
	}
	FLUIDS.register("fluidmolybdenum", supplier(fluidMolybdenum = new FluidMolybdenum()));
    }

    private static void registerSubtypeItem(ISubtype[] array) {
	for (ISubtype subtype : array) {
	    ITEMS.register(subtype.tag(), supplier(new Item(new Item.Properties().group(References.CORETAB)), subtype));
	}
    }

    private static void registerSubtypeBlockItem(ISubtype[] array) {
	for (ISubtype subtype : array) {
	    ITEMS.register(subtype.tag(), supplier(
		    new BlockItemDescriptable(SUBTYPEBLOCK_MAPPINGS.get(subtype), new Item.Properties().group(References.CORETAB)), subtype));
	}
    }

    static {
	registerSubtypeBlockItem(SubtypeOre.values());
	registerSubtypeBlockItem(SubtypeMachine.values());
	registerSubtypeBlockItem(SubtypeGlass.values());
	registerSubtypeBlockItem(SubtypeResourceBlock.values());
	for (SubtypeWire subtype : SubtypeWire.values()) {
	    ITEMS.register(subtype.tag(), supplier(
		    new BlockItemWire((BlockWire) SUBTYPEBLOCK_MAPPINGS.get(subtype), new Item.Properties().group(References.CORETAB)), subtype));
	}
	for (SubtypePipe subtype : SubtypePipe.values()) {
	    ITEMS.register(subtype.tag(), supplier(
		    new BlockItemDescriptable(SUBTYPEBLOCK_MAPPINGS.get(subtype), new Item.Properties().group(References.CORETAB)), subtype));
	}
	registerSubtypeItem(SubtypeIngot.values());
	registerSubtypeItem(SubtypeDust.values());
	registerSubtypeItem(SubtypeImpureDust.values());
	registerSubtypeItem(SubtypeCrystal.values());
	registerSubtypeItem(SubtypeOxide.values());
	registerSubtypeItem(SubtypeGear.values());
	registerSubtypeItem(SubtypePlate.values());
	registerSubtypeItem(SubtypeCircuit.values());
	registerSubtypeItem(SubtypeRod.values());
	for (SubtypeProcessorUpgrade subtype : SubtypeProcessorUpgrade.values()) {
	    SUBTYPEITEMREGISTER_MAPPINGS.put(subtype, ITEMS.register(subtype.tag(),
		    supplier(new ItemProcessorUpgrade(new Item.Properties().group(References.CORETAB), subtype), subtype)));
	}
	for (SubtypeCeramic subtype : SubtypeCeramic.values()) {
	    ITEMS.register(subtype.tag(), supplier(new ItemCeramic(subtype), subtype));
	}
	ITEMS.register("sheetplastic", supplier(new Item(new Item.Properties().maxStackSize(64).group(References.CORETAB))));
	ITEMS.register("compositeplating", supplier(new Item(new Item.Properties().maxStackSize(64).group(References.CORETAB))));
	ITEMS.register("compositeplatingraw", supplier(new Item(new Item.Properties().maxStackSize(64).group(References.CORETAB))));
	ITEMS.register("molybdenumfertilizer", supplier(new BoneMealItem(new Item.Properties().maxStackSize(64).group(References.CORETAB))));

	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.downgradetransformer),
		"|translate|tooltip.transformer.energyloss");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.upgradetransformer),
		"|translate|tooltip.transformer.energyloss");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.oxidationfurnace),
		"|translate|tooltip.oxidationfurnace.voltage");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralcrusher), "|translate|tooltip.mineralcrusher.voltage");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.oxidationfurnacerunning),
		"|translate|tooltip.oxidationfurnacerunning.voltage");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.advancedsolarpanel),
		"|translate|tooltip.advancedsolarpanel.voltage");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralwasher), "|translate|tooltip.mineralwasher.voltage");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.chemicalmixer), "|translate|tooltip.chemicalmixer.voltage");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.chemicalcrystallizer),
		"|translate|tooltip.chemicalcrystallizer.voltage");
	BLOCKS.register("multisubnode", supplier(multi));
	ITEMS.register("multisubnode", supplier(new BlockItemDescriptable(multi, new Item.Properties())));
    }

    public static final RegistryObject<Item> ITEM_INSULATION = ITEMS.register("insulation",
	    supplier(new Item(new Item.Properties().group(References.CORETAB))));
    public static final RegistryObject<Item> ITEM_CERAMICINSULATION = ITEMS.register("insulationceramic",
	    supplier(new Item(new Item.Properties().group(References.CORETAB))));
    public static final RegistryObject<Item> ITEM_RUBBERBOOTS = ITEMS.register("rubberboots", supplier(
	    new ItemRubberArmor(EquipmentSlotType.FEET, new Item.Properties().group(References.CORETAB).maxStackSize(1).defaultMaxDamage(100000))));
    public static final RegistryObject<Item> ITEM_MOTOR = ITEMS.register("motor",
	    supplier(new Item(new Item.Properties().group(References.CORETAB))));
    public static final RegistryObject<Item> ITEM_BATTERY = ITEMS.register("battery",
	    supplier(new ItemElectric((ElectricItemProperties) new ElectricItemProperties().capacity(1666666.66667)
		    .extract(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120))
		    .receive(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).group(References.CORETAB).maxStackSize(1))));
    public static final RegistryObject<Item> ITEM_LITHIUMBATTERY = ITEMS.register("lithiumbattery",
	    supplier(new ItemElectric((ElectricItemProperties) new ElectricItemProperties().capacity(4 * 1666666.66667)
		    .extract(TransferPack.joulesVoltage(4 * 1666666.66667 / (120.0 * 20.0), 240))
		    .receive(TransferPack.joulesVoltage(4 * 1666666.66667 / (120.0 * 20.0), 240)).group(References.CORETAB).maxStackSize(1))));
    public static final RegistryObject<Item> ITEM_COIL = ITEMS.register("coil", supplier(new Item(new Item.Properties().group(References.CORETAB))));
    public static final RegistryObject<Item> ITEM_MULTIMETER = ITEMS.register("multimeter",
	    supplier(new ItemMultimeter(new Item.Properties().group(References.CORETAB).maxStackSize(1))));
    public static final RegistryObject<Item> ITEM_ELECTRICDRILL = ITEMS.register("electricdrill",
	    supplier(new ItemElectricDrill(
		    (ElectricItemProperties) new ElectricItemProperties().capacity(1000000).extract(TransferPack.joulesVoltage(1000, 240))
			    .receive(TransferPack.joulesVoltage(1000, 240)).group(References.CORETAB).maxStackSize(1))));
    public static final RegistryObject<Item> ITEM_ELECTRICCHAINSAW = ITEMS.register("electricchainsaw",
	    supplier(new ItemElectricChainsaw(
		    (ElectricItemProperties) new ElectricItemProperties().capacity(1000000).extract(TransferPack.joulesVoltage(1000, 240))
			    .receive(TransferPack.joulesVoltage(1000, 240)).group(References.CORETAB).maxStackSize(1))));
    public static final RegistryObject<Item> ITEM_KINETICRAILGUN = ITEMS.register("railgunkinetic",
	    supplier(new ItemRailgunKinetic((ElectricItemProperties) new ElectricItemProperties().capacity(ItemRailgunKinetic.JOULES_PER_SHOT * 5)
		    .extract(TransferPack.joulesVoltage(ItemRailgunKinetic.JOULES_PER_SHOT * 5, 240))
		    .receive(TransferPack.joulesVoltage(ItemRailgunKinetic.JOULES_PER_SHOT * 5, 240)).group(References.CORETAB).maxStackSize(1))));
    public static final RegistryObject<Item> ITEM_PLASMARAILGUN = ITEMS.register("railgunplasma",
	    supplier(new ItemRailgunPlasma((ElectricItemProperties) new ElectricItemProperties().capacity(ItemRailgunPlasma.JOULES_PER_SHOT * 10)
		    .extract(TransferPack.joulesVoltage(ItemRailgunKinetic.JOULES_PER_SHOT * 10, 480))
		    .receive(TransferPack.joulesVoltage(ItemRailgunKinetic.JOULES_PER_SHOT * 10, 480)).group(References.CORETAB).maxStackSize(1))));
    public static final RegistryObject<Item> ITEM_WRENCH = ITEMS.register("wrench",
	    supplier(new ItemWrench(new Item.Properties().group(References.CORETAB))));
    public static final RegistryObject<Item> ITEM_SOLARPANELPLATE = ITEMS.register("solarpanelplate",
	    supplier(new Item(new Item.Properties().group(References.CORETAB))));
    public static final RegistryObject<Item> ITEM_CANISTERREINFORCED = ITEMS.register("canisterreinforced", supplier(new ItemCanister()));
    public static final RegistryObject<Item> ITEM_TITANIUM_COIL = ITEMS.register("titaniumheatcoil",
	    supplier(new Item(new Item.Properties().group(References.CORETAB))));
    public static final RegistryObject<Item> COAL_COKE = ITEMS.register("coalcoke",
	    supplier(new Item(new Item.Properties().group(References.CORETAB))));

    public static final RegistryObject<Item> DRILL_HEAD_TITANIUM = ITEMS.register("drillheadtitanium",
	    supplier(new Item(new Item.Properties().group(References.CORETAB))));

    public static final RegistryObject<Item> COMPOSITE_HELMET = ITEMS.register("compositearmorhelmet",
	    supplier(new CompositeArmorItem(CompositeArmor.COMPOSITE_ARMOR, EquipmentSlotType.HEAD)));
    public static final RegistryObject<Item> COMPOSITE_CHESTPLATE = ITEMS.register("compositearmorchestplate",
	    supplier(new CompositeArmorItem(CompositeArmor.COMPOSITE_ARMOR, EquipmentSlotType.CHEST)));
    public static final RegistryObject<Item> COMPOSITE_LEGGINGS = ITEMS.register("compositearmorleggings",
	    supplier(new CompositeArmorItem(CompositeArmor.COMPOSITE_ARMOR, EquipmentSlotType.LEGS)));
    public static final RegistryObject<Item> COMPOSITE_BOOTS = ITEMS.register("compositearmorboots",
	    supplier(new CompositeArmorItem(CompositeArmor.COMPOSITE_ARMOR, EquipmentSlotType.FEET)));

    // Split from items to tiles

    public static final RegistryObject<TileEntityType<TileCoalGenerator>> TILE_COALGENERATOR = TILES.register(SubtypeMachine.coalgenerator.tag(),
	    () -> new TileEntityType<>(TileCoalGenerator::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.coalgenerator),
		    SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.coalgeneratorrunning)), null));
    public static final RegistryObject<TileEntityType<TileSolarPanel>> TILE_SOLARPANEL = TILES.register(SubtypeMachine.solarpanel.tag(),
	    () -> new TileEntityType<>(TileSolarPanel::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.solarpanel)), null));
    public static final RegistryObject<TileEntityType<TileAdvancedSolarPanel>> TILE_ADVANCEDSOLARPANEL = TILES
	    .register(SubtypeMachine.advancedsolarpanel.tag(), () -> new TileEntityType<>(TileAdvancedSolarPanel::new,
		    Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.advancedsolarpanel)), null));

    // Split to electric furnaces

    public static final RegistryObject<TileEntityType<TileElectricFurnace>> TILE_ELECTRICFURNACE = TILES.register(
	    SubtypeMachine.electricfurnace.tag(),
	    () -> new TileEntityType<>(TileElectricFurnace::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnace),
		    SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnacerunning)), null));
    public static final RegistryObject<TileEntityType<TileElectricFurnaceDouble>> TILE_ELECTRICFURNACEDOUBLE = TILES.register(
	    SubtypeMachine.electricfurnacedouble.tag(),
	    () -> new TileEntityType<>(TileElectricFurnaceDouble::new,
		    Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnacedouble),
			    SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnacedoublerunning)),
		    null));
    public static final RegistryObject<TileEntityType<TileElectricFurnaceTriple>> TILE_ELECTRICFURNACETRIPLE = TILES.register(
	    SubtypeMachine.electricfurnacetriple.tag(),
	    () -> new TileEntityType<>(TileElectricFurnaceTriple::new,
		    Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnacetriple),
			    SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnacetriplerunning)),
		    null));

    // Split to wire mills

    public static final RegistryObject<TileEntityType<TileWireMill>> TILE_WIREMILL = TILES.register(SubtypeMachine.wiremill.tag(),
	    () -> new TileEntityType<>(TileWireMill::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.wiremill)), null));
    public static final RegistryObject<TileEntityType<TileWireMillDouble>> TILE_WIREMILLDOUBLE = TILES.register(SubtypeMachine.wiremilldouble.tag(),
	    () -> new TileEntityType<>(TileWireMillDouble::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.wiremilldouble)), null));
    public static final RegistryObject<TileEntityType<TileWireMillTriple>> TILE_WIREMILLTRIPLE = TILES.register(SubtypeMachine.wiremilltriple.tag(),
	    () -> new TileEntityType<>(TileWireMillTriple::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.wiremilltriple)), null));

    // Split to mineral grinders

    public static final RegistryObject<TileEntityType<TileMineralGrinder>> TILE_MINERALGRINDER = TILES.register(SubtypeMachine.mineralgrinder.tag(),
	    () -> new TileEntityType<>(TileMineralGrinder::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralgrinder)), null));
    public static final RegistryObject<TileEntityType<TileMineralGrinderDouble>> TILE_MINERALGRINDERDOUBLE = TILES
	    .register(SubtypeMachine.mineralgrinderdouble.tag(), () -> new TileEntityType<>(TileMineralGrinderDouble::new,
		    Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralgrinderdouble)), null));
    public static final RegistryObject<TileEntityType<TileMineralGrinderTriple>> TILE_MINERALGRINDERTRIPLE = TILES
	    .register(SubtypeMachine.mineralgrindertriple.tag(), () -> new TileEntityType<>(TileMineralGrinderTriple::new,
		    Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralgrindertriple)), null));

    // Split to mineral crushers

    public static final RegistryObject<TileEntityType<TileMineralCrusher>> TILE_MINERALCRUSHER = TILES.register(SubtypeMachine.mineralcrusher.tag(),
	    () -> new TileEntityType<>(TileMineralCrusher::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralcrusher)), null));
    public static final RegistryObject<TileEntityType<TileMineralCrusherDouble>> TILE_MINERALCRUSHERDOUBLE = TILES
	    .register(SubtypeMachine.mineralcrusherdouble.tag(), () -> new TileEntityType<>(TileMineralCrusherDouble::new,
		    Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralcrusherdouble)), null));
    public static final RegistryObject<TileEntityType<TileMineralCrusherTriple>> TILE_MINERALCRUSHERTRIPLE = TILES
	    .register(SubtypeMachine.mineralcrushertriple.tag(), () -> new TileEntityType<>(TileMineralCrusherTriple::new,
		    Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralcrushertriple)), null));

    // Split to rest of tiles

    public static final RegistryObject<TileEntityType<TileBatteryBox>> TILE_BATTERYBOX = TILES.register(SubtypeMachine.batterybox.tag(),
	    () -> new TileEntityType<>(TileBatteryBox::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.batterybox)), null));
    public static final RegistryObject<TileEntityType<TileLithiumBatteryBox>> TILE_LITHIUMBATTERYBOX = TILES
	    .register(SubtypeMachine.lithiumbatterybox.tag(), () -> new TileEntityType<>(TileLithiumBatteryBox::new,
		    Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.lithiumbatterybox)), null));
    public static final RegistryObject<TileEntityType<TileTransformer>> TILE_TRANSFORMER = TILES.register("transformer",
	    () -> new TileEntityType<>(TileTransformer::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.downgradetransformer),
		    SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.upgradetransformer)), null));
    public static final RegistryObject<TileEntityType<TileEnergizedAlloyer>> TILE_ENERGIZEDALLOYER = TILES.register(
	    SubtypeMachine.energizedalloyer.tag(),
	    () -> new TileEntityType<>(TileEnergizedAlloyer::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.energizedalloyer),
		    SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.energizedalloyerrunning)), null));
    public static final RegistryObject<TileEntityType<TileLathe>> TILE_LATHE = TILES.register(SubtypeMachine.lathe.tag(),
	    () -> new TileEntityType<>(TileLathe::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.lathe)), null));
    public static final RegistryObject<TileEntityType<TileReinforcedAlloyer>> TILE_REINFORCEDALLOYER = TILES.register(
	    SubtypeMachine.reinforcedalloyer.tag(),
	    () -> new TileEntityType<>(TileReinforcedAlloyer::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.reinforcedalloyer),
		    SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.reinforcedalloyerrunning)), null));
    public static final RegistryObject<TileEntityType<TileChargerLV>> TILE_CHARGERLV = TILES.register(SubtypeMachine.chargerlv.tag(),
	    () -> new TileEntityType<>(TileChargerLV::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.chargerlv)), null));

    public static final RegistryObject<TileEntityType<TileChargerMV>> TILE_CHARGERMV = TILES.register(SubtypeMachine.chargermv.tag(),
	    () -> new TileEntityType<>(TileChargerMV::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.chargermv)), null));
    public static final RegistryObject<TileEntityType<TileChargerHV>> TILE_CHARGERHV = TILES.register(SubtypeMachine.chargerhv.tag(),
	    () -> new TileEntityType<>(TileChargerHV::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.chargerhv)), null));
    public static final RegistryObject<TileEntityType<TileOxidationFurnace>> TILE_OXIDATIONFURNACE = TILES.register(
	    SubtypeMachine.oxidationfurnace.tag(),
	    () -> new TileEntityType<>(TileOxidationFurnace::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.oxidationfurnace),
		    SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.oxidationfurnacerunning)), null));
    public static final RegistryObject<TileEntityType<TileElectricPump>> TILE_ELECTRICPUMP = TILES.register(SubtypeMachine.electricpump.tag(),
	    () -> new TileEntityType<>(TileElectricPump::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricpump)), null));
    public static final RegistryObject<TileEntityType<TileThermoelectricGenerator>> TILE_THERMOELECTRICGENERATOR = TILES
	    .register(SubtypeMachine.thermoelectricgenerator.tag(), () -> new TileEntityType<>(TileThermoelectricGenerator::new,
		    Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.thermoelectricgenerator)), null));
    public static final RegistryObject<TileEntityType<TileHydroelectricGenerator>> TILE_HYDROELECTRICGENERATOR = TILES
	    .register(SubtypeMachine.hydroelectricgenerator.tag(), () -> new TileEntityType<>(TileHydroelectricGenerator::new,
		    Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.hydroelectricgenerator)), null));
    public static final RegistryObject<TileEntityType<TileWindmill>> TILE_WINDMILL = TILES.register(SubtypeMachine.windmill.tag(),
	    () -> new TileEntityType<>(TileWindmill::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.windmill)), null));
    public static final RegistryObject<TileEntityType<TileFermentationPlant>> TILE_FERMENTATIONPLANT = TILES
	    .register(SubtypeMachine.fermentationplant.tag(), () -> new TileEntityType<>(TileFermentationPlant::new,
		    Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.fermentationplant)), null));
    public static final RegistryObject<TileEntityType<TileCombustionChamber>> TILE_COMBUSTIONCHAMBER = TILES
	    .register(SubtypeMachine.combustionchamber.tag(), () -> new TileEntityType<>(TileCombustionChamber::new,
		    Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.combustionchamber)), null));
    public static final RegistryObject<TileEntityType<TileMineralWasher>> TILE_MINERALWASHER = TILES.register(SubtypeMachine.mineralwasher.tag(),
	    () -> new TileEntityType<>(TileMineralWasher::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralwasher)), null));
    public static final RegistryObject<TileEntityType<TileChemicalMixer>> TILE_CHEMICALMIXER = TILES.register(SubtypeMachine.chemicalmixer.tag(),
	    () -> new TileEntityType<>(TileChemicalMixer::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.chemicalmixer)), null));
    public static final RegistryObject<TileEntityType<TileChemicalCrystallizer>> TILE_CHEMICALCRYSTALLIZER = TILES
	    .register(SubtypeMachine.chemicalcrystallizer.tag(), () -> new TileEntityType<>(TileChemicalCrystallizer::new,
		    Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.chemicalcrystallizer)), null));
    public static final RegistryObject<TileEntityType<TileCircuitBreaker>> TILE_CIRCUITBREAKER = TILES.register(SubtypeMachine.circuitbreaker.tag(),
	    () -> new TileEntityType<>(TileCircuitBreaker::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.circuitbreaker)), null));
    public static final RegistryObject<TileEntityType<TileMultimeterBlock>> TILE_MULTIMETERBLOCK = TILES.register(
	    SubtypeMachine.multimeterblock.tag(),
	    () -> new TileEntityType<>(TileMultimeterBlock::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.multimeterblock)), null));
    public static final RegistryObject<TileEntityType<TileMultiSubnode>> TILE_MULTI = TILES.register("multisubnode",
	    () -> new TileEntityType<>(TileMultiSubnode::new, Sets.newHashSet(multi), null));
    public static final RegistryObject<TileEntityType<TileWire>> TILE_WIRE = TILES.register("wiregenerictile",
	    () -> new TileEntityType<>(TileWire::new, BlockWire.WIRESET, null));
    public static final RegistryObject<TileEntityType<TileLogisticalWire>> TILE_LOGISTICALWIRE = TILES.register("wirelogisticaltile",
	    () -> new TileEntityType<>(TileLogisticalWire::new, BlockWire.WIRESET, null));
    public static final RegistryObject<TileEntityType<TilePipe>> TILE_PIPE = TILES.register("pipegenerictile",
	    () -> new TileEntityType<>(TilePipe::new, BlockPipe.PIPESET, null));
    public static final RegistryObject<ContainerType<ContainerCoalGenerator>> CONTAINER_COALGENERATOR = CONTAINERS
	    .register(SubtypeMachine.coalgenerator.tag(), () -> new ContainerType<>(ContainerCoalGenerator::new));
    public static final RegistryObject<ContainerType<ContainerElectricFurnace>> CONTAINER_ELECTRICFURNACE = CONTAINERS
	    .register(SubtypeMachine.electricfurnace.tag(), () -> new ContainerType<>(ContainerElectricFurnace::new));
    public static final RegistryObject<ContainerType<ContainerElectricFurnaceDouble>> CONTAINER_ELECTRICFURNACEDOUBLE = CONTAINERS
	    .register(SubtypeMachine.electricfurnacedouble.tag(), () -> new ContainerType<>(ContainerElectricFurnaceDouble::new));
    public static final RegistryObject<ContainerType<ContainerElectricFurnaceTriple>> CONTAINER_ELECTRICFURNACETRIPLE = CONTAINERS
	    .register(SubtypeMachine.electricfurnacetriple.tag(), () -> new ContainerType<>(ContainerElectricFurnaceTriple::new));
    public static final RegistryObject<ContainerType<ContainerO2OProcessor>> CONTAINER_O2OPROCESSOR = CONTAINERS.register("o2oprocessor",
	    () -> new ContainerType<>(ContainerO2OProcessor::new));
    public static final RegistryObject<ContainerType<ContainerO2OProcessorDouble>> CONTAINER_O2OPROCESSORDOUBLE = CONTAINERS
	    .register("o2oprocessordouble", () -> new ContainerType<>(ContainerO2OProcessorDouble::new));
    public static final RegistryObject<ContainerType<ContainerO2OProcessorTriple>> CONTAINER_O2OPROCESSORTRIPLE = CONTAINERS
	    .register("o2oprocessortriple", () -> new ContainerType<>(ContainerO2OProcessorTriple::new));
    public static final RegistryObject<ContainerType<ContainerDO2OProcessor>> CONTAINER_DO2OPROCESSOR = CONTAINERS.register("do2oprocessor",
	    () -> new ContainerType<>(ContainerDO2OProcessor::new));
    public static final RegistryObject<ContainerType<ContainerBatteryBox>> CONTAINER_BATTERYBOX = CONTAINERS.register(SubtypeMachine.batterybox.tag(),
	    () -> new ContainerType<>(ContainerBatteryBox::new));
    public static final RegistryObject<ContainerType<ContainerLithiumBatteryBox>> CONTAINER_LITHIUMBATTERYBOX = CONTAINERS
	    .register(SubtypeMachine.lithiumbatterybox.tag(), () -> new ContainerType<>(ContainerLithiumBatteryBox::new));
    public static final RegistryObject<ContainerType<ContainerFermentationPlant>> CONTAINER_FERMENTATIONPLANT = CONTAINERS
	    .register(SubtypeMachine.fermentationplant.tag(), () -> new ContainerType<>(ContainerFermentationPlant::new));
    public static final RegistryObject<ContainerType<ContainerMineralWasher>> CONTAINER_MINERALWASHER = CONTAINERS
	    .register(SubtypeMachine.mineralwasher.tag(), () -> new ContainerType<>(ContainerMineralWasher::new));
    public static final RegistryObject<ContainerType<ContainerChemicalMixer>> CONTAINER_CHEMICALMIXER = CONTAINERS
	    .register(SubtypeMachine.chemicalmixer.tag(), () -> new ContainerType<>(ContainerChemicalMixer::new));
    public static final RegistryObject<ContainerType<ContainerChemicalCrystallizer>> CONTAINER_CHEMICALCRYSTALLIZER = CONTAINERS
	    .register(SubtypeMachine.chemicalcrystallizer.tag(), () -> new ContainerType<>(ContainerChemicalCrystallizer::new));
    public static final RegistryObject<ContainerType<ContainerChargerGeneric>> CONTAINER_CHARGER = CONTAINERS.register("genericcharger",
	    () -> new ContainerType<>(ContainerChargerGeneric::new));

    public static final RegistryObject<EntityType<EntityMetalRod>> ENTITY_METALROD = ENTITIES.register("metalrod",
	    () -> EntityType.Builder.<EntityMetalRod>create(EntityMetalRod::new, EntityClassification.MISC).size(0.25f, 0.25f).immuneToFire()
		    .build(References.ID + ".metalrod"));
    public static final RegistryObject<EntityType<EntityEnergyBlast>> ENTITY_ENERGYBLAST = ENTITIES.register("energyblast",
	    () -> EntityType.Builder.<EntityEnergyBlast>create(EntityEnergyBlast::new, EntityClassification.MISC).size(0.25f, 0.25f).immuneToFire()
		    .build(References.ID + ".energyblast"));

    private static <T extends IForgeRegistryEntry<T>> Supplier<? extends T> supplier(T entry) {
	return () -> entry;
    }

    private static <T extends IForgeRegistryEntry<T>> Supplier<? extends T> supplier(T entry, ISubtype en) {
	if (entry instanceof Block) {
	    SUBTYPEBLOCK_MAPPINGS.put(en, (Block) entry);
	} else if (entry instanceof Item) {
	    SUBTYPEITEM_MAPPINGS.put(en, (Item) entry);
	    ITEMSUBTYPE_MAPPINGS.put((Item) entry, en);
	}
	return supplier(entry);
    }
}
