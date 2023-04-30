package electrodynamics.datagen.server;

import electrodynamics.common.block.subtype.SubtypeGlass;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypeOreDeepslate;
import electrodynamics.common.block.subtype.SubtypeFluidPipe;
import electrodynamics.common.block.subtype.SubtypeGasPipe;
import electrodynamics.common.block.subtype.SubtypeRawOreBlock;
import electrodynamics.common.block.subtype.SubtypeResourceBlock;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.datagen.utils.AbstractLootTableProvider;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import electrodynamics.registers.ElectrodynamicsBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ElectrodynamicsLootTablesProvider extends AbstractLootTableProvider {

	public ElectrodynamicsLootTablesProvider(DataGenerator generator) {
		super(generator);
	}

	@Override
	protected void addTables() {

		for (SubtypeFluidPipe pipe : SubtypeFluidPipe.values()) {
			addSimpleBlock(ElectrodynamicsBlocks.getBlock(pipe));
		}

		for (SubtypeWire wire : SubtypeWire.values()) {
			addSimpleBlock(ElectrodynamicsBlocks.getBlock(wire));
		}
		
		for(SubtypeGasPipe pipe : SubtypeGasPipe.values()) {
			addSimpleBlock(ElectrodynamicsBlocks.getBlock(pipe));
		}

		for (SubtypeGlass glass : SubtypeGlass.values()) {
			addSimpleBlock(ElectrodynamicsBlocks.getBlock(glass));
		}

		for (SubtypeOre ore : SubtypeOre.values()) {
			Block block = ElectrodynamicsBlocks.getBlock(ore);
			addFortuneAndSilkTouchTable(block, ore.nonSilkLootItem == null ? block.asItem() : ore.nonSilkLootItem.get(), ore.minDrop, ore.maxDrop);
		}

		for (SubtypeOreDeepslate ore : SubtypeOreDeepslate.values()) {
			Block block = ElectrodynamicsBlocks.getBlock(ore);
			addFortuneAndSilkTouchTable(block, ore.nonSilkLootItem == null ? block.asItem() : ore.nonSilkLootItem.get(), ore.minDrop, ore.maxDrop);
		}

		for (SubtypeResourceBlock storage : SubtypeResourceBlock.values()) {
			addSimpleBlock(ElectrodynamicsBlocks.getBlock(storage));
		}

		for (SubtypeRawOreBlock raw : SubtypeRawOreBlock.values()) {
			addSimpleBlock(ElectrodynamicsBlocks.getBlock(raw));
		}

		addSimpleBlock(ElectrodynamicsBlocks.blockLogisticalManager);
		addSimpleBlock(ElectrodynamicsBlocks.blockSeismicMarker);

		addIETable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricfurnace), ElectrodynamicsBlockTypes.TILE_ELECTRICFURNACE);
		addIETable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricfurnacedouble), ElectrodynamicsBlockTypes.TILE_ELECTRICFURNACEDOUBLE);
		addIETable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricfurnacetriple), ElectrodynamicsBlockTypes.TILE_ELECTRICFURNACETRIPLE);

		addIETable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricarcfurnace), ElectrodynamicsBlockTypes.TILE_ELECTRICARCFURNACE);
		addIETable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricarcfurnacedouble), ElectrodynamicsBlockTypes.TILE_ELECTRICARCFURNACEDOUBLE);
		addIETable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricarcfurnacetriple), ElectrodynamicsBlockTypes.TILE_ELECTRICARCFURNACETRIPLE);

		addIETable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.wiremill), ElectrodynamicsBlockTypes.TILE_WIREMILL);
		addIETable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.wiremilldouble), ElectrodynamicsBlockTypes.TILE_WIREMILLDOUBLE);
		addIETable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.wiremilltriple), ElectrodynamicsBlockTypes.TILE_WIREMILLTRIPLE);

		addIETable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.mineralcrusher), ElectrodynamicsBlockTypes.TILE_MINERALCRUSHER);
		addIETable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.mineralcrusherdouble), ElectrodynamicsBlockTypes.TILE_MINERALCRUSHERDOUBLE);
		addIETable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.mineralcrushertriple), ElectrodynamicsBlockTypes.TILE_MINERALCRUSHERTRIPLE);

		addIETable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.mineralgrinder), ElectrodynamicsBlockTypes.TILE_MINERALGRINDER);
		addIETable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.mineralgrinderdouble), ElectrodynamicsBlockTypes.TILE_MINERALGRINDERDOUBLE);
		addIETable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.mineralgrindertriple), ElectrodynamicsBlockTypes.TILE_MINERALGRINDERTRIPLE);

		addIETable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.batterybox), ElectrodynamicsBlockTypes.TILE_BATTERYBOX);
		addIETable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.lithiumbatterybox), ElectrodynamicsBlockTypes.TILE_LITHIUMBATTERYBOX);
		addIETable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.carbynebatterybox), ElectrodynamicsBlockTypes.TILE_CARBYNEBATTERYBOX);

		addIETable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.oxidationfurnace), ElectrodynamicsBlockTypes.TILE_OXIDATIONFURNACE);
		addSimpleBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.downgradetransformer));
		addSimpleBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.upgradetransformer));
		addIETable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.coalgenerator), ElectrodynamicsBlockTypes.TILE_COALGENERATOR);
		addIETable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.solarpanel), ElectrodynamicsBlockTypes.TILE_SOLARPANEL);
		addIETable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.advancedsolarpanel), ElectrodynamicsBlockTypes.TILE_ADVANCEDSOLARPANEL);
		addETable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricpump), ElectrodynamicsBlockTypes.TILE_ELECTRICPUMP);
		addETable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.thermoelectricgenerator), ElectrodynamicsBlockTypes.TILE_THERMOELECTRICGENERATOR);
		addIEFTable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.fermentationplant), ElectrodynamicsBlockTypes.TILE_FERMENTATIONPLANT);
		addIEFTable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.combustionchamber), ElectrodynamicsBlockTypes.TILE_COMBUSTIONCHAMBER);
		addIETable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.hydroelectricgenerator), ElectrodynamicsBlockTypes.TILE_HYDROELECTRICGENERATOR);
		addIETable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.windmill), ElectrodynamicsBlockTypes.TILE_WINDMILL);

		addIEFTable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.mineralwasher), ElectrodynamicsBlockTypes.TILE_MINERALWASHER);
		addIEFTable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.chemicalmixer), ElectrodynamicsBlockTypes.TILE_CHEMICALMIXER);
		addIEFTable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.chemicalcrystallizer), ElectrodynamicsBlockTypes.TILE_CHEMICALCRYSTALLIZER);

		addETable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.circuitbreaker), ElectrodynamicsBlockTypes.TILE_CIRCUITBREAKER);
		addIETable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.multimeterblock), ElectrodynamicsBlockTypes.TILE_MULTIMETERBLOCK);
		addIETable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.energizedalloyer), ElectrodynamicsBlockTypes.TILE_ENERGIZEDALLOYER);
		addIETable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.lathe), ElectrodynamicsBlockTypes.TILE_LATHE);
		addIETable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.reinforcedalloyer), ElectrodynamicsBlockTypes.TILE_REINFORCEDALLOYER);

		addIETable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.chargerlv), ElectrodynamicsBlockTypes.TILE_CHARGERLV);
		addIETable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.chargermv), ElectrodynamicsBlockTypes.TILE_CHARGERMV);
		addIETable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.chargerhv), ElectrodynamicsBlockTypes.TILE_CHARGERHV);

		addIFTable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.tanksteel), ElectrodynamicsBlockTypes.TILE_TANKSTEEL);
		addIFTable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.tankreinforced), ElectrodynamicsBlockTypes.TILE_TANKREINFORCED);
		addIFTable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.tankhsla), ElectrodynamicsBlockTypes.TILE_TANKHSLA);

		addSimpleBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.creativepowersource));
		addSimpleBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.creativefluidsource));
		addITable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.fluidvoid), ElectrodynamicsBlockTypes.TILE_FLUIDVOID);
		addIEFTable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electrolyticseparator), ElectrodynamicsBlockTypes.TILE_ELECTROLYTICSEPARATOR);

		addITable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.seismicrelay), ElectrodynamicsBlockTypes.TILE_SEISMICRELAY);
		addIETable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.quarry), ElectrodynamicsBlockTypes.TILE_QUARRY);
		addIFTable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.coolantresavoir), ElectrodynamicsBlockTypes.TILE_COOLANTRESAVOIR);
		addIETable(ElectrodynamicsBlocks.getBlock(SubtypeMachine.motorcomplex), ElectrodynamicsBlockTypes.TILE_MOTORCOMPLEX);

	}

	public <T extends GenericTile> void addITable(Block block, RegistryObject<BlockEntityType<T>> tilereg) {
		lootTables.put(block, itemTable(name(block), block, tilereg.get()));
	}

	public <T extends GenericTile> void addETable(Block block, RegistryObject<BlockEntityType<T>> tilereg) {

		lootTables.put(block, energyTable(name(block), block, tilereg.get()));
	}

	public <T extends GenericTile> void addFTable(Block block, RegistryObject<BlockEntityType<T>> tilereg) {

		lootTables.put(block, fluidTable(name(block), block, tilereg.get()));
	}

	public <T extends GenericTile> void addIETable(Block block, RegistryObject<BlockEntityType<T>> tilereg) {

		lootTables.put(block, itemEnergyTable(name(block), block, tilereg.get()));
	}

	public <T extends GenericTile> void addIFTable(Block block, RegistryObject<BlockEntityType<T>> tilereg) {

		lootTables.put(block, itemFluidTable(name(block), block, tilereg.get()));
	}

	public <T extends GenericTile> void addIEFTable(Block block, RegistryObject<BlockEntityType<T>> tilereg) {

		lootTables.put(block, itemEnergyFluidTable(name(block), block, tilereg.get()));
	}

	/**
	 * Adds the block to the loottables silk touch only
	 *
	 * @author SeaRobber69
	 * @param reg The block that will be added
	 */
	public void addSilkTouchOnlyTable(RegistryObject<Block> reg) {
		Block block = reg.get();
		lootTables.put(block, createSilkTouchOnlyTable(name(block), block));
	}

	public void addFortuneAndSilkTouchTable(RegistryObject<Block> reg, Item nonSilk, int minDrop, int maxDrop) {
		addFortuneAndSilkTouchTable(reg.get(), nonSilk, minDrop, maxDrop);
	}

	public void addFortuneAndSilkTouchTable(Block block, Item nonSilk, int minDrop, int maxDrop) {
		lootTables.put(block, createSilkTouchAndFortuneTable(name(block), block, nonSilk, minDrop, maxDrop));
	}

	public void addSimpleBlock(RegistryObject<Block> reg) {
		addSimpleBlock(reg.get());
	}

	public void addSimpleBlock(Block block) {

		lootTables.put(block, createSimpleBlockTable(name(block), block));
	}

	public String name(Block block) {
		return ForgeRegistries.BLOCKS.getKey(block).getPath();
	}

}
