package electrodynamics;

import java.util.HashMap;

import com.google.common.base.Supplier;
import com.google.common.collect.Sets;

import electrodynamics.api.References;
import electrodynamics.common.block.BlockMachine;
import electrodynamics.common.block.BlockOre;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.block.wire.BlockWire;
import electrodynamics.common.blockitem.BlockItemWire;
import electrodynamics.common.inventory.container.ContainerBatteryBox;
import electrodynamics.common.inventory.container.ContainerCoalGenerator;
import electrodynamics.common.inventory.container.ContainerElectricFurnace;
import electrodynamics.common.inventory.container.ContainerO2OProcessor;
import electrodynamics.common.item.ItemMultimeter;
import electrodynamics.common.item.ItemProcessorUpgrade;
import electrodynamics.common.item.subtype.SubtypeCircuit;
import electrodynamics.common.item.subtype.SubtypeDust;
import electrodynamics.common.item.subtype.SubtypeGear;
import electrodynamics.common.item.subtype.SubtypeImpureDust;
import electrodynamics.common.item.subtype.SubtypeIngot;
import electrodynamics.common.item.subtype.SubtypePlate;
import electrodynamics.common.item.subtype.SubtypeProcessorUpgrade;
import electrodynamics.common.subtype.Subtype;
import electrodynamics.common.tile.TileBatteryBox;
import electrodynamics.common.tile.TileCoalGenerator;
import electrodynamics.common.tile.TileTransformer;
import electrodynamics.common.tile.TileWire;
import electrodynamics.common.tile.processor.TileElectricFurnace;
import electrodynamics.common.tile.processor.o2o.TileMineralCrusher;
import electrodynamics.common.tile.processor.o2o.TileMineralGrinder;
import electrodynamics.common.tile.processor.o2o.TileWireMill;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class DeferredRegisters {
	public static final HashMap<Subtype, Item> SUBTYPEITEM_MAPPINGS = new HashMap<>();
	public static final HashMap<Subtype, Block> SUBTYPEBLOCK_MAPPINGS = new HashMap<>();
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, References.ID);
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, References.ID);
	public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, References.ID);
	public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, References.ID);

	static {
		for (SubtypeOre subtype : SubtypeOre.values()) {
			BLOCKS.register(subtype.tag(), supplier(new BlockOre(subtype), subtype));
		}
		for (SubtypeMachine subtype : SubtypeMachine.values()) {
			BLOCKS.register(subtype.tag(), supplier(new BlockMachine(subtype), subtype));
		}
		for (SubtypeWire subtype : SubtypeWire.values()) {
			BLOCKS.register(subtype.tag(), supplier(new BlockWire(subtype), subtype));
		}
	}

	private static void registerSubtypeItem(Subtype[] array) {
		for (Subtype subtype : array) {
			ITEMS.register(subtype.tag(), supplier(new Item(new Item.Properties().group(References.CORETAB)), subtype));
		}
	}

	private static void registerSubtypeBlockItem(Subtype[] array) {
		for (Subtype subtype : array) {
			ITEMS.register(subtype.tag(), supplier(new BlockItem(SUBTYPEBLOCK_MAPPINGS.get(subtype), new Item.Properties().group(References.CORETAB)), subtype));
		}
	}

	static {
		registerSubtypeBlockItem(SubtypeOre.values());
		registerSubtypeBlockItem(SubtypeMachine.values());
		for (SubtypeWire subtype : SubtypeWire.values()) {
			ITEMS.register(subtype.tag(), supplier(new BlockItemWire((BlockWire) SUBTYPEBLOCK_MAPPINGS.get(subtype), new Item.Properties().group(References.CORETAB)), subtype));
		}
		registerSubtypeItem(SubtypeIngot.values());
		registerSubtypeItem(SubtypeDust.values());
		registerSubtypeItem(SubtypeImpureDust.values());
		registerSubtypeItem(SubtypeGear.values());
		registerSubtypeItem(SubtypePlate.values());
		registerSubtypeItem(SubtypeCircuit.values());
		for (SubtypeProcessorUpgrade subtype : SubtypeProcessorUpgrade.values()) {
			ITEMS.register(subtype.tag(), supplier(new ItemProcessorUpgrade(new Item.Properties().group(References.CORETAB), subtype), subtype));
		}
	}

	public static final RegistryObject<Item> ITEM_INSULATION = ITEMS.register("insulation", supplier(new Item(new Item.Properties().group(References.CORETAB))));
	public static final RegistryObject<Item> ITEM_MOTOR = ITEMS.register("motor", supplier(new Item(new Item.Properties().group(References.CORETAB))));
	public static final RegistryObject<Item> ITEM_BATTERY = ITEMS.register("battery", supplier(new Item(new Item.Properties().group(References.CORETAB).maxStackSize(1))));
	public static final RegistryObject<Item> ITEM_COIL = ITEMS.register("coil", supplier(new Item(new Item.Properties().group(References.CORETAB))));
	public static final RegistryObject<Item> ITEM_MULTIMETER = ITEMS.register("multimeter", supplier(new ItemMultimeter(new Item.Properties().group(References.CORETAB).maxStackSize(1))));

	public static final RegistryObject<TileEntityType<TileCoalGenerator>> TILE_COALGENERATOR = TILES.register(SubtypeMachine.coalgenerator.tag(),
			() -> new TileEntityType<>(TileCoalGenerator::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.coalgenerator), SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.coalgeneratorrunning)), null));
	public static final RegistryObject<TileEntityType<TileElectricFurnace>> TILE_ELECTRICFURNACE = TILES.register(SubtypeMachine.electricfurnace.tag(),
			() -> new TileEntityType<>(TileElectricFurnace::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnace), SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnacerunning)), null));
	public static final RegistryObject<TileEntityType<TileWireMill>> TILE_WIREMILL = TILES.register(SubtypeMachine.wiremill.tag(),
			() -> new TileEntityType<>(TileWireMill::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.wiremill)), null));
	public static final RegistryObject<TileEntityType<TileMineralGrinder>> TILE_MINERALGRINDER = TILES.register(SubtypeMachine.mineralgrinder.tag(),
			() -> new TileEntityType<>(TileMineralGrinder::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralgrinder)), null));
	public static final RegistryObject<TileEntityType<TileMineralCrusher>> TILE_MINERALCRUSHER = TILES.register(SubtypeMachine.mineralcrusher.tag(),
			() -> new TileEntityType<>(TileMineralCrusher::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralcrusher)), null));
	public static final RegistryObject<TileEntityType<TileBatteryBox>> TILE_BATTERYBOX = TILES.register(SubtypeMachine.batterybox.tag(),
			() -> new TileEntityType<>(TileBatteryBox::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.batterybox)), null));
	public static final RegistryObject<TileEntityType<TileTransformer>> TILE_TRANSFORMER = TILES.register("transformer",
			() -> new TileEntityType<>(TileTransformer::new, Sets.newHashSet(SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.downgradetransformer), SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.upgradetransformer)), null));

	public static final RegistryObject<TileEntityType<TileWire>> TILE_WIRE = TILES.register("wiregenerictile", () -> new TileEntityType<>(TileWire::new, BlockWire.WIRESET, null));

	public static final RegistryObject<ContainerType<ContainerCoalGenerator>> CONTAINER_COALGENERATOR = CONTAINERS.register(SubtypeMachine.coalgenerator.tag(), () -> new ContainerType<>(ContainerCoalGenerator::new));
	public static final RegistryObject<ContainerType<ContainerElectricFurnace>> CONTAINER_ELECTRICFURNACE = CONTAINERS.register(SubtypeMachine.electricfurnace.tag(),
			() -> new ContainerType<>(ContainerElectricFurnace::new));
	public static final RegistryObject<ContainerType<ContainerO2OProcessor>> CONTAINER_O2OPROCESSOR = CONTAINERS.register("o2oprocessor", () -> new ContainerType<>(ContainerO2OProcessor::new));
	public static final RegistryObject<ContainerType<ContainerBatteryBox>> CONTAINER_BATTERYBOX = CONTAINERS.register(SubtypeMachine.batterybox.tag(), () -> new ContainerType<>(ContainerBatteryBox::new));

	private static <T extends IForgeRegistryEntry<T>> Supplier<? extends T> supplier(T entry) {
		return () -> entry;
	}

	private static <T extends IForgeRegistryEntry<T>> Supplier<? extends T> supplier(T entry, Subtype en) {
		if (entry instanceof Block) {
			SUBTYPEBLOCK_MAPPINGS.put(en, (Block) entry);
		} else if (entry instanceof Item) {
			SUBTYPEITEM_MAPPINGS.put(en, (Item) entry);
		}
		return supplier(entry);
	}
}
