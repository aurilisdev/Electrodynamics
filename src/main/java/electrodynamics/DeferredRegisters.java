package electrodynamics;

import java.util.HashMap;

import com.google.common.base.Supplier;
import com.google.common.collect.Sets;

import electrodynamics.api.ISubtype;
import electrodynamics.api.References;
import electrodynamics.common.block.BlockConcrete;
import electrodynamics.common.block.BlockCustomGlass;
import electrodynamics.common.block.BlockDeepslateOre;
import electrodynamics.common.block.BlockMachine;
import electrodynamics.common.block.BlockMultiSubnode;
import electrodynamics.common.block.BlockOre;
import electrodynamics.common.block.BlockResource;
import electrodynamics.common.block.connect.BlockPipe;
import electrodynamics.common.block.connect.BlockWire;
import electrodynamics.common.block.subtype.SubtypeConcrete;
import electrodynamics.common.block.subtype.SubtypeGlass;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypeOreDeepslate;
import electrodynamics.common.block.subtype.SubtypePipe;
import electrodynamics.common.block.subtype.SubtypeResourceBlock;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.blockitem.BlockItemDescriptable;
import electrodynamics.common.blockitem.BlockItemWire;
import electrodynamics.common.entity.projectile.types.EntityEnergyBlast;
import electrodynamics.common.entity.projectile.types.EntityMetalRod;
import electrodynamics.common.fluid.types.FluidConcrete;
import electrodynamics.common.fluid.types.FluidClay;
import electrodynamics.common.fluid.types.FluidEthanol;
import electrodynamics.common.fluid.types.FluidHydrogenFluoride;
import electrodynamics.common.fluid.types.FluidPolyethylene;
import electrodynamics.common.fluid.types.FluidSulfate;
import electrodynamics.common.fluid.types.FluidSulfuricAcid;
import electrodynamics.common.fluid.types.subtype.SubtypeSulfateFluid;
import electrodynamics.common.inventory.container.ContainerBatteryBox;
import electrodynamics.common.inventory.container.ContainerChargerGeneric;
import electrodynamics.common.inventory.container.ContainerChemicalCrystallizer;
import electrodynamics.common.inventory.container.ContainerChemicalMixer;
import electrodynamics.common.inventory.container.ContainerCoalGenerator;
import electrodynamics.common.inventory.container.ContainerCobblestoneGenerator;
import electrodynamics.common.inventory.container.ContainerCombustionChamber;
import electrodynamics.common.inventory.container.ContainerCreativeFluidSource;
import electrodynamics.common.inventory.container.ContainerCreativePowerSource;
import electrodynamics.common.inventory.container.ContainerDO2OProcessor;
import electrodynamics.common.inventory.container.ContainerElectricFurnace;
import electrodynamics.common.inventory.container.ContainerElectricFurnaceDouble;
import electrodynamics.common.inventory.container.ContainerElectricFurnaceTriple;
import electrodynamics.common.inventory.container.ContainerFermentationPlant;
import electrodynamics.common.inventory.container.ContainerItemVoid;
import electrodynamics.common.inventory.container.ContainerHydroelectricGenerator;
import electrodynamics.common.inventory.container.ContainerLithiumBatteryBox;
import electrodynamics.common.inventory.container.ContainerMineralWasher;
import electrodynamics.common.inventory.container.ContainerO2OProcessor;
import electrodynamics.common.inventory.container.ContainerO2OProcessorDouble;
import electrodynamics.common.inventory.container.ContainerO2OProcessorTriple;
import electrodynamics.common.inventory.container.ContainerSolarPanel;
import electrodynamics.common.inventory.container.ContainerTankGeneric;
import electrodynamics.common.inventory.container.ContainerWindmill;
import electrodynamics.common.item.ItemCeramic;
import electrodynamics.common.item.ItemDescriptable;
import electrodynamics.common.item.ItemUpgrade;
import electrodynamics.common.item.gear.armor.types.composite.CompositeArmor;
import electrodynamics.common.item.gear.armor.types.composite.CompositeArmorItem;
import electrodynamics.common.item.gear.armor.types.rubber.ItemRubberArmor;
import electrodynamics.common.item.gear.tools.ItemCanister;
import electrodynamics.common.item.gear.tools.ItemGuidebook;
import electrodynamics.common.item.gear.tools.ItemMultimeter;
import electrodynamics.common.item.gear.tools.ItemWrench;
import electrodynamics.common.item.gear.tools.electric.ItemElectricBaton;
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
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.common.item.subtype.SubtypeNugget;
import electrodynamics.common.item.subtype.SubtypeOxide;
import electrodynamics.common.item.subtype.SubtypePlate;
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
import electrodynamics.common.tile.TileCobblestoneGenerator;
import electrodynamics.common.tile.TileCombustionChamber;
import electrodynamics.common.tile.TileCreativeFluidSource;
import electrodynamics.common.tile.TileCreativePowerSource;
import electrodynamics.common.tile.TileElectricFurnace;
import electrodynamics.common.tile.TileElectricFurnaceDouble;
import electrodynamics.common.tile.TileElectricFurnaceTriple;
import electrodynamics.common.tile.TileElectricPump;
import electrodynamics.common.tile.TileEnergizedAlloyer;
import electrodynamics.common.tile.TileFermentationPlant;
import electrodynamics.common.tile.TileFluidVoid;
import electrodynamics.common.tile.TileHydroelectricGenerator;
import electrodynamics.common.tile.TileItemVoid;
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
import electrodynamics.common.tile.TileTankHSLA;
import electrodynamics.common.tile.TileTankReinforced;
import electrodynamics.common.tile.TileTankSteel;
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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryObject;

public class DeferredRegisters {

    public static final HashMap<ISubtype, Item> SUBTYPEITEM_MAPPINGS = new HashMap<>();
    public static final HashMap<Item, ISubtype> ITEMSUBTYPE_MAPPINGS = new HashMap<>();
    public static final HashMap<ISubtype, Block> SUBTYPEBLOCK_MAPPINGS = new HashMap<>();
    public static final HashMap<ISubtype, RegistryObject<Item>> SUBTYPEITEMREGISTER_MAPPINGS = new HashMap<>();
    public static final HashMap<ISubtype, RegistryObject<Block>> SUBTYPEBLOCKREGISTER_MAPPINGS = new HashMap<>();
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, References.ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, References.ID);
    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, References.ID);
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, References.ID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, References.ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, References.ID);
    public static BlockMultiSubnode multi = new BlockMultiSubnode();
    public static FluidEthanol fluidEthanol;
    public static FluidSulfuricAcid fluidSulfuricAcid;
    public static FluidHydrogenFluoride fluidHydrogenFluoride;
    public static FluidPolyethylene fluidPolyethylene;
    public static FluidClay fluidClay;
    public static FluidConcrete fluidCement;

	static {

		for (SubtypeOreDeepslate subtype : SubtypeOreDeepslate.values()) {
		    SUBTYPEBLOCKREGISTER_MAPPINGS.put(subtype, BLOCKS.register(subtype.tag(), supplier(new BlockDeepslateOre(subtype), subtype)));
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
		for (SubtypeConcrete subtype : SubtypeConcrete.values()) {
			SUBTYPEBLOCKREGISTER_MAPPINGS.put(subtype, BLOCKS.register(subtype.tag(), supplier(new BlockConcrete(subtype), subtype)));
		}
		
		
		FLUIDS.register("fluidethanol", supplier(fluidEthanol = new FluidEthanol()));
		FLUIDS.register("fluidsulfuricacid", supplier(fluidSulfuricAcid = new FluidSulfuricAcid()));
		FLUIDS.register("fluidhydrogenfluoride", supplier(fluidHydrogenFluoride = new FluidHydrogenFluoride()));
		FLUIDS.register("fluidpolyethylene", supplier(fluidPolyethylene = new FluidPolyethylene()));
		FLUIDS.register("fluidclay", supplier(fluidClay = new FluidClay()));
		FLUIDS.register("fluidconcrete", supplier(fluidCement = new FluidConcrete()));
		for (SubtypeSulfateFluid mineral : SubtypeSulfateFluid.values()) {
		    FluidSulfate fluid = new FluidSulfate(mineral);
		    FLUIDS.register("fluidsulfate" + mineral.name(), supplier(fluid));
		}
    }

	private static void registerSubtypeItem(ISubtype[] array) {
		for (ISubtype subtype : array) {
			ITEMS.register(subtype.tag(), supplier(new Item(new Item.Properties().tab(References.CORETAB)), subtype));
		}
	}

	private static void registerSubtypeBlockItem(ISubtype[] array) {
		for (ISubtype subtype : array) {
			ITEMS.register(subtype.tag(),
					supplier(new BlockItemDescriptable(SUBTYPEBLOCK_MAPPINGS.get(subtype), new Item.Properties().tab(References.CORETAB)), subtype));
		}
	}

    static {
	//registerSubtypeBlockItem(SubtypeOre.values());
	//registerSubtypeBlockItem(SubtypeOreDeepslate.values());
	registerSubtypeBlockItem(SubtypeMachine.values());
	registerSubtypeBlockItem(SubtypeGlass.values());
	registerSubtypeBlockItem(SubtypeResourceBlock.values());
	registerSubtypeBlockItem(SubtypeConcrete.values());
	for (SubtypeWire subtype : SubtypeWire.values()) {
	    ITEMS.register(subtype.tag(), supplier(
		    new BlockItemWire((BlockWire) SUBTYPEBLOCK_MAPPINGS.get(subtype), new Item.Properties().tab(References.CORETAB)), subtype));
	}
	for (SubtypePipe subtype : SubtypePipe.values()) {
	    ITEMS.register(subtype.tag(),
		    supplier(new BlockItemDescriptable(SUBTYPEBLOCK_MAPPINGS.get(subtype), new Item.Properties().tab(References.CORETAB)), subtype));
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
	registerSubtypeItem(SubtypeNugget.values());
	for (SubtypeItemUpgrade subtype : SubtypeItemUpgrade.values()) {
	    SUBTYPEITEMREGISTER_MAPPINGS.put(subtype,
		    ITEMS.register(subtype.tag(), supplier(new ItemUpgrade(new Item.Properties().tab(References.CORETAB), subtype), subtype)));
	}
	for (SubtypeCeramic subtype : SubtypeCeramic.values()) {
	    ITEMS.register(subtype.tag(), supplier(new ItemCeramic(subtype), subtype));
	}
	ITEMS.register("sheetplastic", supplier(new Item(new Item.Properties().stacksTo(64).tab(References.CORETAB))));
	ITEMS.register("compositeplating", supplier(new Item(new Item.Properties().stacksTo(64).tab(References.CORETAB))));
	ITEMS.register("compositeplatingraw", supplier(new Item(new Item.Properties().stacksTo(64).tab(References.CORETAB))));
	ITEMS.register("molybdenumfertilizer", supplier(new BoneMealItem(new Item.Properties().stacksTo(64).tab(References.CORETAB))));
	ITEMS.register("concretemix", supplier(new ItemDescriptable(new Item.Properties().stacksTo(64).tab(References.CORETAB), "tooltip.info.concretejoke")));
	
	//machines
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnace), "|translate|tooltip.machine.voltage.120");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnacedouble), "|translate|tooltip.machine.voltage.240");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnacetriple), "|translate|tooltip.machine.voltage.480");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.wiremill), "|translate|tooltip.machine.voltage.120");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.wiremilldouble), "|translate|tooltip.machine.voltage.240");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.wiremilltriple), "|translate|tooltip.machine.voltage.480");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralcrusher), "|translate|tooltip.machine.voltage.240");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralcrusherdouble), "|translate|tooltip.machine.voltage.480");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralcrushertriple), "|translate|tooltip.machine.voltage.960");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralgrinder), "|translate|tooltip.machine.voltage.120");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralgrinderdouble), "|translate|tooltip.machine.voltage.240");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralgrindertriple), "|translate|tooltip.machine.voltage.480");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.oxidationfurnace),"|translate|tooltip.machine.voltage.240");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralwasher), "|translate|tooltip.machine.voltage.480");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.chemicalmixer), "|translate|tooltip.machine.voltage.240");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.chemicalcrystallizer),"|translate|tooltip.machine.voltage.240");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.energizedalloyer),"|translate|tooltip.machine.voltage.480");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.reinforcedalloyer),"|translate|tooltip.machine.voltage.960");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.lathe),"|translate|tooltip.machine.voltage.240");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.chargerlv),"|translate|tooltip.machine.voltage.120");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.chargermv),"|translate|tooltip.machine.voltage.240");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.chargerhv),"|translate|tooltip.machine.voltage.480");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.fermentationplant),"|translate|tooltip.machine.voltage.120");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricpump),"|translate|tooltip.machine.voltage.120");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.cobblestonegenerator),"|translate|tooltip.machine.voltage.120");
	
	//generators
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.solarpanel),"|translate|tooltip.machine.voltage.120");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.advancedsolarpanel),"|translate|tooltip.machine.voltage.240");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.thermoelectricgenerator),"|translate|tooltip.machine.voltage.120");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.combustionchamber),"|translate|tooltip.machine.voltage.120");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.hydroelectricgenerator),"|translate|tooltip.machine.voltage.120");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.windmill),"|translate|tooltip.machine.voltage.120");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.coalgenerator),"|translate|tooltip.machine.voltage.120");
	
	//misc
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.downgradetransformer),"|translate|tooltip.transformer.energyloss");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.upgradetransformer),"|translate|tooltip.transformer.energyloss");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.batterybox),"|translate|tooltip.machine.voltage.120");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.lithiumbatterybox),"|translate|tooltip.machine.voltage.240");
	
	
	boolean jeiLoaded = ModList.get().isLoaded("jei");
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.tanksteel), "|translate|tooltip.tanksteel.capacity");
	if (jeiLoaded) {
			BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.tanksteel), "|translate|tooltip.tanksteel.usejei");
		}
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.tankreinforced), "|translate|tooltip.tankreinforced.capacity");
	if (jeiLoaded) {
		BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.tankreinforced), "|translate|tooltip.tanksteel.usejei");
	}
	BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.tankhsla), "|translate|tooltip.tankhsla.capacity");
	if (jeiLoaded) {
		BlockItemDescriptable.addDescription(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.tankhsla), "|translate|tooltip.tanksteel.usejei");
	}
	}

    public static final RegistryObject<Item> ITEM_INSULATION = ITEMS.register("insulation",
	    supplier(new Item(new Item.Properties().tab(References.CORETAB))));
    public static final RegistryObject<Item> ITEM_CERAMICINSULATION = ITEMS.register("insulationceramic",
	    supplier(new Item(new Item.Properties().tab(References.CORETAB))));
    public static final RegistryObject<Item> ITEM_RUBBERBOOTS = ITEMS.register("rubberboots",
	    supplier(new ItemRubberArmor(EquipmentSlot.FEET, new Item.Properties().tab(References.CORETAB).stacksTo(1).defaultDurability(100000))));
    public static final RegistryObject<Item> ITEM_MOTOR = ITEMS.register("motor", supplier(new Item(new Item.Properties().tab(References.CORETAB))));
    public static final RegistryObject<Item> ITEM_BATTERY = ITEMS.register("battery",
	    supplier(new ItemElectric((ElectricItemProperties) new ElectricItemProperties().capacity(1666666.66667)
		    .extract(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120))
		    .receive(TransferPack.joulesVoltage(1666666.66667 / (120.0 * 20.0), 120)).tab(References.CORETAB).stacksTo(1))));
    public static final RegistryObject<Item> ITEM_LITHIUMBATTERY = ITEMS.register("lithiumbattery",
	    supplier(new ItemElectric((ElectricItemProperties) new ElectricItemProperties().capacity(4 * 1666666.66667)
		    .extract(TransferPack.joulesVoltage(4 * 1666666.66667 / (120.0 * 20.0), 240))
		    .receive(TransferPack.joulesVoltage(4 * 1666666.66667 / (120.0 * 20.0), 240)).tab(References.CORETAB).stacksTo(1))));
    public static final RegistryObject<Item> ITEM_COIL = ITEMS.register("coil", supplier(new Item(new Item.Properties().tab(References.CORETAB))));
    public static final RegistryObject<Item> ITEM_MULTIMETER = ITEMS.register("multimeter",
	    supplier(new ItemMultimeter(new Item.Properties().tab(References.CORETAB).stacksTo(1))));
    public static final RegistryObject<Item> ITEM_ELECTRICDRILL = ITEMS.register("electricdrill",
	    supplier(new ItemElectricDrill(
		    (ElectricItemProperties) new ElectricItemProperties().capacity(1000000).extract(TransferPack.joulesVoltage(1000, 240))
			    .receive(TransferPack.joulesVoltage(1000, 240)).tab(References.CORETAB).stacksTo(1))));
    public static final RegistryObject<Item> ITEM_ELECTRICCHAINSAW = ITEMS.register("electricchainsaw",
	    supplier(new ItemElectricChainsaw(
		    (ElectricItemProperties) new ElectricItemProperties().capacity(1000000).extract(TransferPack.joulesVoltage(1000, 240))
			    .receive(TransferPack.joulesVoltage(1000, 240)).tab(References.CORETAB).stacksTo(1))));
    public static final RegistryObject<Item> ITEM_ELECTRICBATON = ITEMS.register("electricbaton",
	    supplier(new ItemElectricBaton(
		    (ElectricItemProperties) new ElectricItemProperties().capacity(1000000).extract(TransferPack.joulesVoltage(1000, 240))
			    .receive(TransferPack.joulesVoltage(1000, 240)).tab(References.CORETAB).stacksTo(1))));
    public static final RegistryObject<Item> ITEM_KINETICRAILGUN = ITEMS.register("railgunkinetic",
	    supplier(new ItemRailgunKinetic((ElectricItemProperties) new ElectricItemProperties().capacity(ItemRailgunKinetic.JOULES_PER_SHOT * 5)
		    .extract(TransferPack.joulesVoltage(ItemRailgunKinetic.JOULES_PER_SHOT * 5, 240))
		    .receive(TransferPack.joulesVoltage(ItemRailgunKinetic.JOULES_PER_SHOT * 5, 240)).tab(References.CORETAB).stacksTo(1))));
    public static final RegistryObject<Item> ITEM_PLASMARAILGUN = ITEMS.register("railgunplasma",
	    supplier(new ItemRailgunPlasma((ElectricItemProperties) new ElectricItemProperties().capacity(ItemRailgunPlasma.JOULES_PER_SHOT * 10)
		    .extract(TransferPack.joulesVoltage(ItemRailgunKinetic.JOULES_PER_SHOT * 10, 480))
		    .receive(TransferPack.joulesVoltage(ItemRailgunKinetic.JOULES_PER_SHOT * 10, 480)).tab(References.CORETAB).stacksTo(1))));
    public static final RegistryObject<Item> ITEM_WRENCH = ITEMS.register("wrench",
	    supplier(new ItemWrench(new Item.Properties().tab(References.CORETAB))));
    public static final RegistryObject<Item> ITEM_SOLARPANELPLATE = ITEMS.register("solarpanelplate",
	    supplier(new Item(new Item.Properties().tab(References.CORETAB))));
    public static final RegistryObject<Item> ITEM_CANISTERREINFORCED = ITEMS.register("canisterreinforced",
	    supplier(new ItemCanister(new Item.Properties().stacksTo(1).tab(References.CORETAB))));
    public static final RegistryObject<Item> ITEM_TITANIUM_COIL = ITEMS.register("titaniumheatcoil",
	    supplier(new Item(new Item.Properties().tab(References.CORETAB))));
    public static final RegistryObject<Item> COAL_COKE = ITEMS.register("coalcoke",
	    supplier(new Item(new Item.Properties().tab(References.CORETAB))));
    public static final RegistryObject<Item> SLAG = ITEMS.register("slag",
    	supplier(new Item(new Item.Properties().tab(References.CORETAB))));
    public static final RegistryObject<Item> DRILL_HEAD_TITANIUM = ITEMS.register("drillheadtitanium",
	    supplier(new Item(new Item.Properties().tab(References.CORETAB))));
    public static final RegistryObject<Item> GUIDEBOOK = ITEMS.register("guidebook", 
    	supplier(new ItemGuidebook(new Item.Properties().tab(References.CORETAB))));

	public static final RegistryObject<Item> COMPOSITE_HELMET = ITEMS.register("compositearmorhelmet",
			supplier(new CompositeArmorItem(CompositeArmor.COMPOSITE_ARMOR, EquipmentSlot.HEAD)));
	public static final RegistryObject<Item> COMPOSITE_CHESTPLATE = ITEMS.register("compositearmorchestplate",
			supplier(new CompositeArmorItem(CompositeArmor.COMPOSITE_ARMOR, EquipmentSlot.CHEST)));
	public static final RegistryObject<Item> COMPOSITE_LEGGINGS = ITEMS.register("compositearmorleggings",
			supplier(new CompositeArmorItem(CompositeArmor.COMPOSITE_ARMOR, EquipmentSlot.LEGS)));
	public static final RegistryObject<Item> COMPOSITE_BOOTS = ITEMS.register("compositearmorboots",
			supplier(new CompositeArmorItem(CompositeArmor.COMPOSITE_ARMOR, EquipmentSlot.FEET)));

	// Split from items to tiles

	public static final RegistryObject<BlockEntityType<TileCoalGenerator>> TILE_COALGENERATOR = TILES.register(SubtypeMachine.coalgenerator.tag(),
			() -> new BlockEntityType<>(TileCoalGenerator::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.coalgenerator),
					SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.coalgeneratorrunning)), null));
	public static final RegistryObject<BlockEntityType<TileSolarPanel>> TILE_SOLARPANEL = TILES.register(SubtypeMachine.solarpanel.tag(),
			() -> new BlockEntityType<>(TileSolarPanel::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.solarpanel)), null));
	public static final RegistryObject<BlockEntityType<TileAdvancedSolarPanel>> TILE_ADVANCEDSOLARPANEL = TILES
			.register(SubtypeMachine.advancedsolarpanel.tag(), () -> new BlockEntityType<>(TileAdvancedSolarPanel::new,
					Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.advancedsolarpanel)), null));

	// Split to electric furnaces

	public static final RegistryObject<BlockEntityType<TileElectricFurnace>> TILE_ELECTRICFURNACE = TILES.register(
			SubtypeMachine.electricfurnace.tag(),
			() -> new BlockEntityType<>(TileElectricFurnace::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnace),
					SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnacerunning)), null));
	public static final RegistryObject<BlockEntityType<TileElectricFurnaceDouble>> TILE_ELECTRICFURNACEDOUBLE = TILES.register(
			SubtypeMachine.electricfurnacedouble.tag(),
			() -> new BlockEntityType<>(TileElectricFurnaceDouble::new,
					Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnacedouble),
							SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnacedoublerunning)),
					null));
	public static final RegistryObject<BlockEntityType<TileElectricFurnaceTriple>> TILE_ELECTRICFURNACETRIPLE = TILES.register(
			SubtypeMachine.electricfurnacetriple.tag(),
			() -> new BlockEntityType<>(TileElectricFurnaceTriple::new,
					Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnacetriple),
							SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnacetriplerunning)),
					null));

	// Split to wire mills

	public static final RegistryObject<BlockEntityType<TileWireMill>> TILE_WIREMILL = TILES.register(SubtypeMachine.wiremill.tag(),
			() -> new BlockEntityType<>(TileWireMill::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.wiremill)), null));
	public static final RegistryObject<BlockEntityType<TileWireMillDouble>> TILE_WIREMILLDOUBLE = TILES.register(SubtypeMachine.wiremilldouble.tag(),
			() -> new BlockEntityType<>(TileWireMillDouble::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.wiremilldouble)), null));
	public static final RegistryObject<BlockEntityType<TileWireMillTriple>> TILE_WIREMILLTRIPLE = TILES.register(SubtypeMachine.wiremilltriple.tag(),
			() -> new BlockEntityType<>(TileWireMillTriple::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.wiremilltriple)), null));

	// Split to mineral grinders

	public static final RegistryObject<BlockEntityType<TileMineralGrinder>> TILE_MINERALGRINDER = TILES.register(SubtypeMachine.mineralgrinder.tag(),
			() -> new BlockEntityType<>(TileMineralGrinder::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralgrinder)), null));
	public static final RegistryObject<BlockEntityType<TileMineralGrinderDouble>> TILE_MINERALGRINDERDOUBLE = TILES
			.register(SubtypeMachine.mineralgrinderdouble.tag(), () -> new BlockEntityType<>(TileMineralGrinderDouble::new,
					Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralgrinderdouble)), null));
	public static final RegistryObject<BlockEntityType<TileMineralGrinderTriple>> TILE_MINERALGRINDERTRIPLE = TILES
			.register(SubtypeMachine.mineralgrindertriple.tag(), () -> new BlockEntityType<>(TileMineralGrinderTriple::new,
					Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralgrindertriple)), null));

	// Split to mineral crushers

	public static final RegistryObject<BlockEntityType<TileMineralCrusher>> TILE_MINERALCRUSHER = TILES.register(SubtypeMachine.mineralcrusher.tag(),
			() -> new BlockEntityType<>(TileMineralCrusher::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralcrusher)), null));
	public static final RegistryObject<BlockEntityType<TileMineralCrusherDouble>> TILE_MINERALCRUSHERDOUBLE = TILES
			.register(SubtypeMachine.mineralcrusherdouble.tag(), () -> new BlockEntityType<>(TileMineralCrusherDouble::new,
					Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralcrusherdouble)), null));
	public static final RegistryObject<BlockEntityType<TileMineralCrusherTriple>> TILE_MINERALCRUSHERTRIPLE = TILES
			.register(SubtypeMachine.mineralcrushertriple.tag(), () -> new BlockEntityType<>(TileMineralCrusherTriple::new,
					Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralcrushertriple)), null));

	// Split to rest of tiles

    public static final RegistryObject<BlockEntityType<TileBatteryBox>> TILE_BATTERYBOX = TILES.register(SubtypeMachine.batterybox.tag(),
	    () -> new BlockEntityType<>(TileBatteryBox::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.batterybox)), null));
    public static final RegistryObject<BlockEntityType<TileLithiumBatteryBox>> TILE_LITHIUMBATTERYBOX = TILES
	    .register(SubtypeMachine.lithiumbatterybox.tag(), () -> new BlockEntityType<>(TileLithiumBatteryBox::new,
		    Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.lithiumbatterybox)), null));
    public static final RegistryObject<BlockEntityType<TileTransformer>> TILE_TRANSFORMER = TILES.register("transformer",
	    () -> new BlockEntityType<>(TileTransformer::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.downgradetransformer),
		    SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.upgradetransformer)), null));
    public static final RegistryObject<BlockEntityType<TileEnergizedAlloyer>> TILE_ENERGIZEDALLOYER = TILES.register(
	    SubtypeMachine.energizedalloyer.tag(),
	    () -> new BlockEntityType<>(TileEnergizedAlloyer::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.energizedalloyer),
		    SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.energizedalloyerrunning)), null));
    public static final RegistryObject<BlockEntityType<TileLathe>> TILE_LATHE = TILES.register(SubtypeMachine.lathe.tag(),
	    () -> new BlockEntityType<>(TileLathe::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.lathe)), null));
    public static final RegistryObject<BlockEntityType<TileReinforcedAlloyer>> TILE_REINFORCEDALLOYER = TILES.register(
	    SubtypeMachine.reinforcedalloyer.tag(),
	    () -> new BlockEntityType<>(TileReinforcedAlloyer::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.reinforcedalloyer),
		    SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.reinforcedalloyerrunning)), null));
    public static final RegistryObject<BlockEntityType<TileChargerLV>> TILE_CHARGERLV = TILES.register(SubtypeMachine.chargerlv.tag(),
	    () -> new BlockEntityType<>(TileChargerLV::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.chargerlv)), null));
    public static final RegistryObject<BlockEntityType<TileChargerMV>> TILE_CHARGERMV = TILES.register(SubtypeMachine.chargermv.tag(),
	    () -> new BlockEntityType<>(TileChargerMV::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.chargermv)), null));
    public static final RegistryObject<BlockEntityType<TileChargerHV>> TILE_CHARGERHV = TILES.register(SubtypeMachine.chargerhv.tag(),
	    () -> new BlockEntityType<>(TileChargerHV::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.chargerhv)), null));
    public static final RegistryObject<BlockEntityType<TileTankSteel>> TILE_TANKSTEEL = TILES.register(SubtypeMachine.tanksteel.tag(),
	    () -> new BlockEntityType<>(TileTankSteel::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.tanksteel)), null));
    public static final RegistryObject<BlockEntityType<TileTankReinforced>> TILE_TANKREINFORCED = TILES.register(SubtypeMachine.tankreinforced.tag(),
	    () -> new BlockEntityType<>(TileTankReinforced::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.tankreinforced)), null));
    public static final RegistryObject<BlockEntityType<TileTankHSLA>> TILE_TANKHSLA = TILES.register(SubtypeMachine.tankhsla.tag(),
	    () -> new BlockEntityType<>(TileTankHSLA::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.tankhsla)), null));
    public static final RegistryObject<BlockEntityType<TileOxidationFurnace>> TILE_OXIDATIONFURNACE = TILES.register(
	    SubtypeMachine.oxidationfurnace.tag(),
	    () -> new BlockEntityType<>(TileOxidationFurnace::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.oxidationfurnace),
		    SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.oxidationfurnacerunning)), null));
    public static final RegistryObject<BlockEntityType<TileCreativePowerSource>> TILE_CREATIVEPOWERSOURCE = TILES.register(SubtypeMachine.creativepowersource.tag(),
    	() -> new BlockEntityType<>(TileCreativePowerSource::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.creativepowersource)), null));
    public static final RegistryObject<BlockEntityType<TileCobblestoneGenerator>> TILE_COBBLESTONEGENERATOR = TILES.register(SubtypeMachine.cobblestonegenerator.tag(),
    	() -> new BlockEntityType<>(TileCobblestoneGenerator::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.cobblestonegenerator)), null));
    public static final RegistryObject<BlockEntityType<TileElectricPump>> TILE_ELECTRICPUMP = TILES.register(SubtypeMachine.electricpump.tag(),
	    () -> new BlockEntityType<>(TileElectricPump::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricpump)), null));
    public static final RegistryObject<BlockEntityType<TileThermoelectricGenerator>> TILE_THERMOELECTRICGENERATOR = TILES
	    .register(SubtypeMachine.thermoelectricgenerator.tag(), () -> new BlockEntityType<>(TileThermoelectricGenerator::new,
		    Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.thermoelectricgenerator)), null));
    public static final RegistryObject<BlockEntityType<TileHydroelectricGenerator>> TILE_HYDROELECTRICGENERATOR = TILES
	    .register(SubtypeMachine.hydroelectricgenerator.tag(), () -> new BlockEntityType<>(TileHydroelectricGenerator::new,
		    Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.hydroelectricgenerator)), null));
    public static final RegistryObject<BlockEntityType<TileWindmill>> TILE_WINDMILL = TILES.register(SubtypeMachine.windmill.tag(),
	    () -> new BlockEntityType<>(TileWindmill::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.windmill)), null));
    public static final RegistryObject<BlockEntityType<TileFermentationPlant>> TILE_FERMENTATIONPLANT = TILES
	    .register(SubtypeMachine.fermentationplant.tag(), () -> new BlockEntityType<>(TileFermentationPlant::new,
		    Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.fermentationplant)), null));
    public static final RegistryObject<BlockEntityType<TileCombustionChamber>> TILE_COMBUSTIONCHAMBER = TILES
	    .register(SubtypeMachine.combustionchamber.tag(), () -> new BlockEntityType<>(TileCombustionChamber::new,
		    Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.combustionchamber)), null));
    public static final RegistryObject<BlockEntityType<TileMineralWasher>> TILE_MINERALWASHER = TILES.register(SubtypeMachine.mineralwasher.tag(),
	    () -> new BlockEntityType<>(TileMineralWasher::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralwasher)), null));
    public static final RegistryObject<BlockEntityType<TileChemicalMixer>> TILE_CHEMICALMIXER = TILES.register(SubtypeMachine.chemicalmixer.tag(),
	    () -> new BlockEntityType<>(TileChemicalMixer::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.chemicalmixer)), null));
    public static final RegistryObject<BlockEntityType<TileChemicalCrystallizer>> TILE_CHEMICALCRYSTALLIZER = TILES
	    .register(SubtypeMachine.chemicalcrystallizer.tag(), () -> new BlockEntityType<>(TileChemicalCrystallizer::new,
		    Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.chemicalcrystallizer)), null));
    public static final RegistryObject<BlockEntityType<TileCircuitBreaker>> TILE_CIRCUITBREAKER = TILES.register(SubtypeMachine.circuitbreaker.tag(),
	    () -> new BlockEntityType<>(TileCircuitBreaker::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.circuitbreaker)), null));
    public static final RegistryObject<BlockEntityType<TileMultimeterBlock>> TILE_MULTIMETERBLOCK = TILES.register(
	    SubtypeMachine.multimeterblock.tag(),
	    () -> new BlockEntityType<>(TileMultimeterBlock::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.multimeterblock)), null));
    public static final RegistryObject<BlockEntityType<TileMultiSubnode>> TILE_MULTI = TILES.register("multisubnode",
	    () -> new BlockEntityType<>(TileMultiSubnode::new, Sets.newHashSet(multi), null));
    public static final RegistryObject<BlockEntityType<TileWire>> TILE_WIRE = TILES.register("wiregenerictile",
	    () -> new BlockEntityType<>(TileWire::new, BlockWire.WIRESET, null));
    public static final RegistryObject<BlockEntityType<TileLogisticalWire>> TILE_LOGISTICALWIRE = TILES.register("wirelogisticaltile",
	    () -> new BlockEntityType<>(TileLogisticalWire::new, BlockWire.WIRESET, null));
    public static final RegistryObject<BlockEntityType<TilePipe>> TILE_PIPE = TILES.register("pipegenerictile",
	    () -> new BlockEntityType<>(TilePipe::new, BlockPipe.PIPESET, null));
    /*
    public static final RegistryObject<BlockEntityType<TileCreativeFluidSource>> TILE_CREATIVEFLUIDSOURCE = TILES.register(SubtypeMachine.creativefluidsource.tag(),
    	() -> new BlockEntityType<>(TileCreativeFluidSource::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.creativefluidsource)), null));
    public static final RegistryObject<BlockEntityType<TileItemVoid>> TILE_ITEMVOID = TILES.register(SubtypeMachine.itemvoid.tag(),
    	() -> new BlockEntityType<>(TileItemVoid::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.itemvoid)), null));
    public static final RegistryObject<BlockEntityType<TileFluidVoid>> TILE_FLUIDVOID = TILES.register(SubtypeMachine.fluidvoid.tag(),
    	() -> new BlockEntityType<>(TileFluidVoid::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.fluidvoid)), null));
   */
    //Containers
    
    public static final RegistryObject<MenuType<ContainerCoalGenerator>> CONTAINER_COALGENERATOR = CONTAINERS
	    .register(SubtypeMachine.coalgenerator.tag(), () -> new MenuType<>(ContainerCoalGenerator::new));
    public static final RegistryObject<MenuType<ContainerElectricFurnace>> CONTAINER_ELECTRICFURNACE = CONTAINERS
	    .register(SubtypeMachine.electricfurnace.tag(), () -> new MenuType<>(ContainerElectricFurnace::new));
    public static final RegistryObject<MenuType<ContainerElectricFurnaceDouble>> CONTAINER_ELECTRICFURNACEDOUBLE = CONTAINERS
	    .register(SubtypeMachine.electricfurnacedouble.tag(), () -> new MenuType<>(ContainerElectricFurnaceDouble::new));
    public static final RegistryObject<MenuType<ContainerElectricFurnaceTriple>> CONTAINER_ELECTRICFURNACETRIPLE = CONTAINERS
	    .register(SubtypeMachine.electricfurnacetriple.tag(), () -> new MenuType<>(ContainerElectricFurnaceTriple::new));
    public static final RegistryObject<MenuType<ContainerO2OProcessor>> CONTAINER_O2OPROCESSOR = CONTAINERS.register("o2oprocessor",
	    () -> new MenuType<>(ContainerO2OProcessor::new));
    public static final RegistryObject<MenuType<ContainerO2OProcessorDouble>> CONTAINER_O2OPROCESSORDOUBLE = CONTAINERS.register("o2oprocessordouble",
	    () -> new MenuType<>(ContainerO2OProcessorDouble::new));
    public static final RegistryObject<MenuType<ContainerO2OProcessorTriple>> CONTAINER_O2OPROCESSORTRIPLE = CONTAINERS.register("o2oprocessortriple",
	    () -> new MenuType<>(ContainerO2OProcessorTriple::new));
    public static final RegistryObject<MenuType<ContainerDO2OProcessor>> CONTAINER_DO2OPROCESSOR = CONTAINERS.register("do2oprocessor",
	    () -> new MenuType<>(ContainerDO2OProcessor::new));
    public static final RegistryObject<MenuType<ContainerBatteryBox>> CONTAINER_BATTERYBOX = CONTAINERS.register(SubtypeMachine.batterybox.tag(),
	    () -> new MenuType<>(ContainerBatteryBox::new));
    public static final RegistryObject<MenuType<ContainerLithiumBatteryBox>> CONTAINER_LITHIUMBATTERYBOX = CONTAINERS
	    .register(SubtypeMachine.lithiumbatterybox.tag(), () -> new MenuType<>(ContainerLithiumBatteryBox::new));
    public static final RegistryObject<MenuType<ContainerFermentationPlant>> CONTAINER_FERMENTATIONPLANT = CONTAINERS
	    .register(SubtypeMachine.fermentationplant.tag(), () -> new MenuType<>(ContainerFermentationPlant::new));
    public static final RegistryObject<MenuType<ContainerMineralWasher>> CONTAINER_MINERALWASHER = CONTAINERS
	    .register(SubtypeMachine.mineralwasher.tag(), () -> new MenuType<>(ContainerMineralWasher::new));
    public static final RegistryObject<MenuType<ContainerChemicalMixer>> CONTAINER_CHEMICALMIXER = CONTAINERS
	    .register(SubtypeMachine.chemicalmixer.tag(), () -> new MenuType<>(ContainerChemicalMixer::new));
    public static final RegistryObject<MenuType<ContainerChemicalCrystallizer>> CONTAINER_CHEMICALCRYSTALLIZER = CONTAINERS
	    .register(SubtypeMachine.chemicalcrystallizer.tag(), () -> new MenuType<>(ContainerChemicalCrystallizer::new));
    public static final RegistryObject<MenuType<ContainerChargerGeneric>> CONTAINER_CHARGER = CONTAINERS.register("genericcharger",
	    () -> new MenuType<>(ContainerChargerGeneric::new));
    public static final RegistryObject<MenuType<ContainerTankGeneric>> CONTAINER_TANK = CONTAINERS.register("generictank",
	    () -> new MenuType<>(ContainerTankGeneric::new));
    public static final RegistryObject<MenuType<ContainerCombustionChamber>> CONTAINER_COMBUSTION_CHAMBER = CONTAINERS.register("combustionchamber",
		() -> new MenuType<>(ContainerCombustionChamber::new));
	public static final RegistryObject<MenuType<ContainerSolarPanel>> CONTAINER_SOLARPANEL = CONTAINERS.register("solarpanel",
		() -> new MenuType<>(ContainerSolarPanel::new));
	public static final RegistryObject<MenuType<ContainerWindmill>> CONTAINER_WINDMILL = CONTAINERS.register("windmill",
		() -> new MenuType<>(ContainerWindmill::new));
	public static final RegistryObject<MenuType<ContainerHydroelectricGenerator>> CONTAINER_HYDROELECTRICGENERATOR = CONTAINERS
		.register("hydroelectricgenerator", () -> new MenuType<>(ContainerHydroelectricGenerator::new));
    public static final RegistryObject<MenuType<ContainerCobblestoneGenerator>> CONTAINER_COBBLESTONEGENERATOR = CONTAINERS.register("cobblestonegenerator",
    	() -> new MenuType<>(ContainerCobblestoneGenerator::new));
    public static final RegistryObject<MenuType<ContainerCreativePowerSource>> CONTAINER_CREATIVEPOWERSOURCE = CONTAINERS.register("creativepowersource",
    	() -> new MenuType<>(ContainerCreativePowerSource::new));
    /*
    public static final RegistryObject<MenuType<ContainerCreativeFluidSource>> CONTAINER_CREATIVEFLUIDSOURCE = CONTAINERS.register("creativefluidsource",
    	() -> new MenuType<>(ContainerCreativeFluidSource::new));
    public static final RegistryObject<MenuType<ContainerItemVoid>> CONTAINER_ITEMVOID = CONTAINERS.register("itemvoid",
    	() -> new MenuType<>(ContainerItemVoid::new));
    public static final RegistryObject<MenuType<ContainerFluidVoid>> 
	*/
    //Entities
    
    public static final RegistryObject<EntityType<EntityMetalRod>> ENTITY_METALROD = ENTITIES.register("metalrod", () -> EntityType.Builder
	    .<EntityMetalRod>of(EntityMetalRod::new, MobCategory.MISC).sized(0.25f, 0.25f).fireImmune().build(References.ID + ".metalrod"));
    public static final RegistryObject<EntityType<EntityEnergyBlast>> ENTITY_ENERGYBLAST = ENTITIES.register("energyblast", () -> EntityType.Builder
	    .<EntityEnergyBlast>of(EntityEnergyBlast::new, MobCategory.MISC).sized(0.25f, 0.25f).fireImmune().build(References.ID + ".energyblast"));

	private static <T extends IForgeRegistryEntry<T>> Supplier<? extends T> supplier(T entry) {
		return () -> entry;
	}

	private static <T extends IForgeRegistryEntry<T>> Supplier<? extends T> supplier(T entry, ISubtype en) {
		if (entry instanceof Block bl) {
			SUBTYPEBLOCK_MAPPINGS.put(en, bl);
		} else if (entry instanceof Item it) {
			SUBTYPEITEM_MAPPINGS.put(en, it);
			ITEMSUBTYPE_MAPPINGS.put(it, en);
		}
		return supplier(entry);
	}
}
