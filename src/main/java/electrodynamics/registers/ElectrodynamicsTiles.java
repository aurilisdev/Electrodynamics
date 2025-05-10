package electrodynamics.registers;

import com.google.common.collect.Sets;

import electrodynamics.Electrodynamics;
import electrodynamics.common.block.connect.BlockFluidPipe;
import electrodynamics.common.block.connect.BlockLogisticalWire;
import electrodynamics.common.block.connect.BlockWire;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.tile.electricitygrid.TileCircuitBreaker;
import electrodynamics.common.tile.electricitygrid.TileCircuitMonitor;
import electrodynamics.common.tile.electricitygrid.TileCurrentRegulator;
import electrodynamics.common.tile.electricitygrid.TileLogisticalWire;
import electrodynamics.common.tile.electricitygrid.TileMultimeterBlock;
import electrodynamics.common.tile.electricitygrid.TilePotentiometer;
import electrodynamics.common.tile.electricitygrid.TileRelay;
import electrodynamics.common.tile.electricitygrid.TileWire;
import electrodynamics.common.tile.electricitygrid.batteries.TileBatteryBox;
import electrodynamics.common.tile.electricitygrid.batteries.TileCarbyneBatteryBox;
import electrodynamics.common.tile.electricitygrid.batteries.TileLithiumBatteryBox;
import electrodynamics.common.tile.electricitygrid.generators.TileAdvancedSolarPanel;
import electrodynamics.common.tile.electricitygrid.generators.TileCoalGenerator;
import electrodynamics.common.tile.electricitygrid.generators.TileCombustionChamber;
import electrodynamics.common.tile.electricitygrid.generators.TileCreativePowerSource;
import electrodynamics.common.tile.electricitygrid.generators.TileHydroelectricGenerator;
import electrodynamics.common.tile.electricitygrid.generators.TileSolarPanel;
import electrodynamics.common.tile.electricitygrid.generators.TileThermoelectricGenerator;
import electrodynamics.common.tile.electricitygrid.generators.TileWindmill;
import electrodynamics.common.tile.electricitygrid.transformer.TileAdvancedTransformer.TileAdvancedDowngradeTransformer;
import electrodynamics.common.tile.electricitygrid.transformer.TileAdvancedTransformer.TileAdvancedUpgradeTransformer;
import electrodynamics.common.tile.electricitygrid.transformer.TileGenericTransformer.TileDowngradeTransformer;
import electrodynamics.common.tile.electricitygrid.transformer.TileGenericTransformer.TileUpgradeTransformer;
import electrodynamics.common.tile.machines.TileChemicalCrystallizer;
import electrodynamics.common.tile.machines.TileChemicalMixer;
import electrodynamics.common.tile.machines.TileCobblestoneGenerator;
import electrodynamics.common.tile.machines.TileElectrolyticSeparator;
import electrodynamics.common.tile.machines.TileEnergizedAlloyer;
import electrodynamics.common.tile.machines.TileFermentationPlant;
import electrodynamics.common.tile.machines.TileLathe;
import electrodynamics.common.tile.machines.TileMineralWasher;
import electrodynamics.common.tile.machines.TileOxidationFurnace;
import electrodynamics.common.tile.machines.TileReinforcedAlloyer;
import electrodynamics.common.tile.machines.arcfurnace.TileElectricArcFurnace;
import electrodynamics.common.tile.machines.arcfurnace.TileElectricArcFurnaceDouble;
import electrodynamics.common.tile.machines.arcfurnace.TileElectricArcFurnaceTriple;
import electrodynamics.common.tile.machines.charger.TileChargerHV;
import electrodynamics.common.tile.machines.charger.TileChargerLV;
import electrodynamics.common.tile.machines.charger.TileChargerMV;
import electrodynamics.common.tile.machines.furnace.TileElectricFurnace;
import electrodynamics.common.tile.machines.furnace.TileElectricFurnaceDouble;
import electrodynamics.common.tile.machines.furnace.TileElectricFurnaceTriple;
import electrodynamics.common.tile.machines.mineralcrusher.TileMineralCrusher;
import electrodynamics.common.tile.machines.mineralcrusher.TileMineralCrusherDouble;
import electrodynamics.common.tile.machines.mineralcrusher.TileMineralCrusherTriple;
import electrodynamics.common.tile.machines.mineralgrinder.TileMineralGrinder;
import electrodynamics.common.tile.machines.mineralgrinder.TileMineralGrinderDouble;
import electrodynamics.common.tile.machines.mineralgrinder.TileMineralGrinderTriple;
import electrodynamics.common.tile.machines.quarry.TileCoolantResavoir;
import electrodynamics.common.tile.machines.quarry.TileFrame;
import electrodynamics.common.tile.machines.quarry.TileLogisticalManager;
import electrodynamics.common.tile.machines.quarry.TileMotorComplex;
import electrodynamics.common.tile.machines.quarry.TileQuarry;
import electrodynamics.common.tile.machines.quarry.TileSeismicMarker;
import electrodynamics.common.tile.machines.quarry.TileSeismicRelay;
import electrodynamics.common.tile.machines.wiremill.TileWireMill;
import electrodynamics.common.tile.machines.wiremill.TileWireMillDouble;
import electrodynamics.common.tile.machines.wiremill.TileWireMillTriple;
import electrodynamics.common.tile.pipelines.fluid.TileCreativeFluidSource;
import electrodynamics.common.tile.pipelines.fluid.TileElectricPump;
import electrodynamics.common.tile.pipelines.fluid.TileFluidPipe;
import electrodynamics.common.tile.pipelines.fluid.TileFluidPipeFilter;
import electrodynamics.common.tile.pipelines.fluid.TileFluidPipePump;
import electrodynamics.common.tile.pipelines.fluid.TileFluidValve;
import electrodynamics.common.tile.pipelines.fluid.TileFluidVoid;
import electrodynamics.common.tile.pipelines.fluid.tank.TileFluidTankHSLA;
import electrodynamics.common.tile.pipelines.fluid.tank.TileFluidTankReinforced;
import electrodynamics.common.tile.pipelines.fluid.tank.TileFluidTankSteel;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ElectrodynamicsTiles {
	public static final DeferredRegister<TileEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Electrodynamics.ID);

    public static final RegistryObject<TileEntityType<TileCoalGenerator>> TILE_COALGENERATOR = BLOCK_ENTITY_TYPES.register(SubtypeMachine.coalgenerator.tag(), () -> new TileEntityType<>(TileCoalGenerator::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.coalgenerator)), null));
	public static final RegistryObject<TileEntityType<TileSolarPanel>> TILE_SOLARPANEL = BLOCK_ENTITY_TYPES.register(SubtypeMachine.solarpanel.tag(), () -> new TileEntityType<>(TileSolarPanel::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.solarpanel)), null));
	public static final RegistryObject<TileEntityType<TileAdvancedSolarPanel>> TILE_ADVANCEDSOLARPANEL = BLOCK_ENTITY_TYPES.register(SubtypeMachine.advancedsolarpanel.tag(), () -> new TileEntityType<>(TileAdvancedSolarPanel::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.advancedsolarpanel)), null));

	public static final RegistryObject<TileEntityType<TileElectricFurnace>> TILE_ELECTRICFURNACE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.electricfurnace.tag(), () -> new TileEntityType<>(TileElectricFurnace::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electricfurnace)), null));
	public static final RegistryObject<TileEntityType<TileElectricFurnaceDouble>> TILE_ELECTRICFURNACEDOUBLE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.electricfurnacedouble.tag(), () -> new TileEntityType<>(TileElectricFurnaceDouble::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electricfurnacedouble)), null));
	public static final RegistryObject<TileEntityType<TileElectricFurnaceTriple>> TILE_ELECTRICFURNACETRIPLE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.electricfurnacetriple.tag(), () -> new TileEntityType<>(TileElectricFurnaceTriple::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electricfurnacetriple)), null));

	public static final RegistryObject<TileEntityType<TileElectricArcFurnace>> TILE_ELECTRICARCFURNACE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.electricarcfurnace.tag(), () -> new TileEntityType<>(TileElectricArcFurnace::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electricarcfurnace)), null));
	public static final RegistryObject<TileEntityType<TileElectricArcFurnaceDouble>> TILE_ELECTRICARCFURNACEDOUBLE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.electricarcfurnacedouble.tag(), () -> new TileEntityType<>(TileElectricArcFurnaceDouble::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electricarcfurnacedouble)), null));
	public static final RegistryObject<TileEntityType<TileElectricArcFurnaceTriple>> TILE_ELECTRICARCFURNACETRIPLE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.electricarcfurnacetriple.tag(), () -> new TileEntityType<>(TileElectricArcFurnaceTriple::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electricarcfurnacetriple)), null));

	public static final RegistryObject<TileEntityType<TileWireMill>> TILE_WIREMILL = BLOCK_ENTITY_TYPES.register(SubtypeMachine.wiremill.tag(), () -> new TileEntityType<>(TileWireMill::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.wiremill)), null));
	public static final RegistryObject<TileEntityType<TileWireMillDouble>> TILE_WIREMILLDOUBLE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.wiremilldouble.tag(), () -> new TileEntityType<>(TileWireMillDouble::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.wiremilldouble)), null));
	public static final RegistryObject<TileEntityType<TileWireMillTriple>> TILE_WIREMILLTRIPLE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.wiremilltriple.tag(), () -> new TileEntityType<>(TileWireMillTriple::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.wiremilltriple)), null));

	public static final RegistryObject<TileEntityType<TileMineralGrinder>> TILE_MINERALGRINDER = BLOCK_ENTITY_TYPES.register(SubtypeMachine.mineralgrinder.tag(), () -> new TileEntityType<>(TileMineralGrinder::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.mineralgrinder)), null));
	public static final RegistryObject<TileEntityType<TileMineralGrinderDouble>> TILE_MINERALGRINDERDOUBLE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.mineralgrinderdouble.tag(), () -> new TileEntityType<>(TileMineralGrinderDouble::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.mineralgrinderdouble)), null));
	public static final RegistryObject<TileEntityType<TileMineralGrinderTriple>> TILE_MINERALGRINDERTRIPLE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.mineralgrindertriple.tag(), () -> new TileEntityType<>(TileMineralGrinderTriple::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.mineralgrindertriple)), null));

	public static final RegistryObject<TileEntityType<TileMineralCrusher>> TILE_MINERALCRUSHER = BLOCK_ENTITY_TYPES.register(SubtypeMachine.mineralcrusher.tag(), () -> new TileEntityType<>(TileMineralCrusher::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.mineralcrusher)), null));
	public static final RegistryObject<TileEntityType<TileMineralCrusherDouble>> TILE_MINERALCRUSHERDOUBLE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.mineralcrusherdouble.tag(), () -> new TileEntityType<>(TileMineralCrusherDouble::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.mineralcrusherdouble)), null));
	public static final RegistryObject<TileEntityType<TileMineralCrusherTriple>> TILE_MINERALCRUSHERTRIPLE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.mineralcrushertriple.tag(), () -> new TileEntityType<>(TileMineralCrusherTriple::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.mineralcrushertriple)), null));

	public static final RegistryObject<TileEntityType<TileBatteryBox>> TILE_BATTERYBOX = BLOCK_ENTITY_TYPES.register(SubtypeMachine.batterybox.tag(), () -> new TileEntityType<>(TileBatteryBox::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.batterybox)), null));
	public static final RegistryObject<TileEntityType<TileLithiumBatteryBox>> TILE_LITHIUMBATTERYBOX = BLOCK_ENTITY_TYPES.register(SubtypeMachine.lithiumbatterybox.tag(), () -> new TileEntityType<>(TileLithiumBatteryBox::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.lithiumbatterybox)), null));
	public static final RegistryObject<TileEntityType<TileCarbyneBatteryBox>> TILE_CARBYNEBATTERYBOX = BLOCK_ENTITY_TYPES.register(SubtypeMachine.carbynebatterybox.tag(), () -> new TileEntityType<>(TileCarbyneBatteryBox::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.carbynebatterybox)), null));

	public static final RegistryObject<TileEntityType<TileChargerLV>> TILE_CHARGERLV = BLOCK_ENTITY_TYPES.register(SubtypeMachine.chargerlv.tag(), () -> new TileEntityType<>(TileChargerLV::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.chargerlv)), null));
	public static final RegistryObject<TileEntityType<TileChargerMV>> TILE_CHARGERMV = BLOCK_ENTITY_TYPES.register(SubtypeMachine.chargermv.tag(), () -> new TileEntityType<>(TileChargerMV::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.chargermv)), null));
	public static final RegistryObject<TileEntityType<TileChargerHV>> TILE_CHARGERHV = BLOCK_ENTITY_TYPES.register(SubtypeMachine.chargerhv.tag(), () -> new TileEntityType<>(TileChargerHV::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.chargerhv)), null));

	public static final RegistryObject<TileEntityType<TileFluidTankSteel>> TILE_TANKSTEEL = BLOCK_ENTITY_TYPES.register(SubtypeMachine.tanksteel.tag(), () -> new TileEntityType<>(TileFluidTankSteel::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.tanksteel)), null));
	public static final RegistryObject<TileEntityType<TileFluidTankReinforced>> TILE_TANKREINFORCED = BLOCK_ENTITY_TYPES.register(SubtypeMachine.tankreinforced.tag(), () -> new TileEntityType<>(TileFluidTankReinforced::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.tankreinforced)), null));
	public static final RegistryObject<TileEntityType<TileFluidTankHSLA>> TILE_TANKHSLA = BLOCK_ENTITY_TYPES.register(SubtypeMachine.tankhsla.tag(), () -> new TileEntityType<>(TileFluidTankHSLA::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.tankhsla)), null));

	public static final RegistryObject<TileEntityType<TileDowngradeTransformer>> TILE_DOWNGRADETRANSFORMER = BLOCK_ENTITY_TYPES.register("downgradetransformer", () -> new TileEntityType<>(TileDowngradeTransformer::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.downgradetransformer)), null));
	public static final RegistryObject<TileEntityType<TileUpgradeTransformer>> TILE_UPGRADETRANSFORMER = BLOCK_ENTITY_TYPES.register("upgradetransformer", () -> new TileEntityType<>(TileUpgradeTransformer::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.upgradetransformer)), null));

	public static final RegistryObject<TileEntityType<TileAdvancedDowngradeTransformer>> TILE_ADVANCEDDOWNGRADETRANSFORMER = BLOCK_ENTITY_TYPES.register("advanceddowngradetransformer", () -> new TileEntityType<>(TileAdvancedDowngradeTransformer::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.advanceddowngradetransformer)), null));
	public static final RegistryObject<TileEntityType<TileAdvancedUpgradeTransformer>> TILE_ADVANCEDUPGRADETRANSFORMER = BLOCK_ENTITY_TYPES.register("advancedupgradetransformer", () -> new TileEntityType<>(TileAdvancedUpgradeTransformer::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.advancedupgradetransformer)), null));

	public static final RegistryObject<TileEntityType<TileEnergizedAlloyer>> TILE_ENERGIZEDALLOYER = BLOCK_ENTITY_TYPES.register(SubtypeMachine.energizedalloyer.tag(), () -> new TileEntityType<>(TileEnergizedAlloyer::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.energizedalloyer)), null));
	public static final RegistryObject<TileEntityType<TileLathe>> TILE_LATHE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.lathe.tag(), () -> new TileEntityType<>(TileLathe::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.lathe)), null));
	public static final RegistryObject<TileEntityType<TileReinforcedAlloyer>> TILE_REINFORCEDALLOYER = BLOCK_ENTITY_TYPES.register(SubtypeMachine.reinforcedalloyer.tag(), () -> new TileEntityType<>(TileReinforcedAlloyer::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.reinforcedalloyer)), null));
	public static final RegistryObject<TileEntityType<TileOxidationFurnace>> TILE_OXIDATIONFURNACE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.oxidationfurnace.tag(), () -> new TileEntityType<>(TileOxidationFurnace::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.oxidationfurnace)), null));
	public static final RegistryObject<TileEntityType<TileCreativePowerSource>> TILE_CREATIVEPOWERSOURCE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.creativepowersource.tag(), () -> new TileEntityType<>(TileCreativePowerSource::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.creativepowersource)), null));
	public static final RegistryObject<TileEntityType<TileElectricPump>> TILE_ELECTRICPUMP = BLOCK_ENTITY_TYPES.register(SubtypeMachine.electricpump.tag(), () -> new TileEntityType<>(TileElectricPump::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electricpump)), null));
	public static final RegistryObject<TileEntityType<TileThermoelectricGenerator>> TILE_THERMOELECTRICGENERATOR = BLOCK_ENTITY_TYPES.register(SubtypeMachine.thermoelectricgenerator.tag(), () -> new TileEntityType<>(TileThermoelectricGenerator::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.thermoelectricgenerator)), null));
	public static final RegistryObject<TileEntityType<TileHydroelectricGenerator>> TILE_HYDROELECTRICGENERATOR = BLOCK_ENTITY_TYPES.register(SubtypeMachine.hydroelectricgenerator.tag(), () -> new TileEntityType<>(TileHydroelectricGenerator::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.hydroelectricgenerator)), null));
	public static final RegistryObject<TileEntityType<TileWindmill>> TILE_WINDMILL = BLOCK_ENTITY_TYPES.register(SubtypeMachine.windmill.tag(), () -> new TileEntityType<>(TileWindmill::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.windmill)), null));
	public static final RegistryObject<TileEntityType<TileFermentationPlant>> TILE_FERMENTATIONPLANT = BLOCK_ENTITY_TYPES.register(SubtypeMachine.fermentationplant.tag(), () -> new TileEntityType<>(TileFermentationPlant::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.fermentationplant)), null));
	public static final RegistryObject<TileEntityType<TileCombustionChamber>> TILE_COMBUSTIONCHAMBER = BLOCK_ENTITY_TYPES.register(SubtypeMachine.combustionchamber.tag(), () -> new TileEntityType<>(TileCombustionChamber::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.combustionchamber)), null));
	public static final RegistryObject<TileEntityType<TileMineralWasher>> TILE_MINERALWASHER = BLOCK_ENTITY_TYPES.register(SubtypeMachine.mineralwasher.tag(), () -> new TileEntityType<>(TileMineralWasher::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.mineralwasher)), null));
	public static final RegistryObject<TileEntityType<TileChemicalMixer>> TILE_CHEMICALMIXER = BLOCK_ENTITY_TYPES.register(SubtypeMachine.chemicalmixer.tag(), () -> new TileEntityType<>(TileChemicalMixer::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.chemicalmixer)), null));
	public static final RegistryObject<TileEntityType<TileChemicalCrystallizer>> TILE_CHEMICALCRYSTALLIZER = BLOCK_ENTITY_TYPES.register(SubtypeMachine.chemicalcrystallizer.tag(), () -> new TileEntityType<>(TileChemicalCrystallizer::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.chemicalcrystallizer)), null));
	public static final RegistryObject<TileEntityType<TileCircuitBreaker>> TILE_CIRCUITBREAKER = BLOCK_ENTITY_TYPES.register(SubtypeMachine.circuitbreaker.tag(), () -> new TileEntityType<>(TileCircuitBreaker::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.circuitbreaker)), null));
	public static final RegistryObject<TileEntityType<TileMultimeterBlock>> TILE_MULTIMETERBLOCK = BLOCK_ENTITY_TYPES.register(SubtypeMachine.multimeterblock.tag(), () -> new TileEntityType<>(TileMultimeterBlock::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.multimeterblock)), null));
	public static final RegistryObject<TileEntityType<TileWire>> TILE_WIRE = BLOCK_ENTITY_TYPES.register("wiregenerictile", () -> new TileEntityType<>(TileWire::new, BlockWire.WIRES, null));
	public static final RegistryObject<TileEntityType<TileLogisticalWire>> TILE_LOGISTICALWIRE = BLOCK_ENTITY_TYPES.register("wirelogisticaltile", () -> new TileEntityType<>(TileLogisticalWire::new, BlockLogisticalWire.WIRES, null));
	public static final RegistryObject<TileEntityType<TileFluidPipe>> TILE_PIPE = BLOCK_ENTITY_TYPES.register("pipegenerictile", () -> new TileEntityType<>(TileFluidPipe::new, BlockFluidPipe.PIPESET, null));
	public static final RegistryObject<TileEntityType<TileElectrolyticSeparator>> TILE_ELECTROLYTICSEPARATOR = BLOCK_ENTITY_TYPES.register(SubtypeMachine.electrolyticseparator.tag(), () -> new TileEntityType<>(TileElectrolyticSeparator::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electrolyticseparator)), null));
	public static final RegistryObject<TileEntityType<TileCreativeFluidSource>> TILE_CREATIVEFLUIDSOURCE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.creativefluidsource.tag(), () -> new TileEntityType<>(TileCreativeFluidSource::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.creativefluidsource)), null));
	public static final RegistryObject<TileEntityType<TileFluidVoid>> TILE_FLUIDVOID = BLOCK_ENTITY_TYPES.register(SubtypeMachine.fluidvoid.tag(), () -> new TileEntityType<>(TileFluidVoid::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.fluidvoid)), null));
	public static final RegistryObject<TileEntityType<TileSeismicMarker>> TILE_SEISMICMARKER = BLOCK_ENTITY_TYPES.register("seismicmarker", () -> new TileEntityType<>(TileSeismicMarker::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCK_SEISMICMARKER.get()), null));
	public static final RegistryObject<TileEntityType<TileSeismicRelay>> TILE_SEISMICRELAY = BLOCK_ENTITY_TYPES.register(SubtypeMachine.seismicrelay.tag(), () -> new TileEntityType<>(TileSeismicRelay::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.seismicrelay)), null));
	public static final RegistryObject<TileEntityType<TileQuarry>> TILE_QUARRY = BLOCK_ENTITY_TYPES.register(SubtypeMachine.quarry.tag(), () -> new TileEntityType<>(TileQuarry::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.quarry)), null));
	public static final RegistryObject<TileEntityType<TileCoolantResavoir>> TILE_COOLANTRESAVOIR = BLOCK_ENTITY_TYPES.register(SubtypeMachine.coolantresavoir.tag(), () -> new TileEntityType<>(TileCoolantResavoir::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.coolantresavoir)), null));
	public static final RegistryObject<TileEntityType<TileMotorComplex>> TILE_MOTORCOMPLEX = BLOCK_ENTITY_TYPES.register(SubtypeMachine.motorcomplex.tag(), () -> new TileEntityType<>(TileMotorComplex::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.motorcomplex)), null));
	public static final RegistryObject<TileEntityType<TileLogisticalManager>> TILE_LOGISTICALMANAGER = BLOCK_ENTITY_TYPES.register("logisticalmanager", () -> new TileEntityType<>(TileLogisticalManager::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCK_LOGISTICALMANAGER.get()), null));
	public static final RegistryObject<TileEntityType<TileFrame>> TILE_QUARRY_FRAME = BLOCK_ENTITY_TYPES.register("quarryframe", () -> new TileEntityType<>(TileFrame::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCK_FRAME.get(), ElectrodynamicsBlocks.BLOCK_FRAME_CORNER.get()), null));

	public static final RegistryObject<TileEntityType<TileFluidValve>> TILE_FLUIDVALVE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.fluidvalve.tag(), () -> new TileEntityType<>(TileFluidValve::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.fluidvalve)), null));
	public static final RegistryObject<TileEntityType<TileFluidPipePump>> TILE_FLUIDPIPEPUMP = BLOCK_ENTITY_TYPES.register(SubtypeMachine.fluidpipepump.tag(), () -> new TileEntityType<>(TileFluidPipePump::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.fluidpipepump)), null));
	public static final RegistryObject<TileEntityType<TileFluidPipeFilter>> TILE_FLUIDPIPEFILTER = BLOCK_ENTITY_TYPES.register(SubtypeMachine.fluidpipefilter.tag(), () -> new TileEntityType<>(TileFluidPipeFilter::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.fluidpipefilter)), null));

	public static final RegistryObject<TileEntityType<TileRelay>> TILE_RELAY = BLOCK_ENTITY_TYPES.register(SubtypeMachine.relay.tag(), () -> new TileEntityType<>(TileRelay::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.relay)), null));
	public static final RegistryObject<TileEntityType<TilePotentiometer>> TILE_POTENTIOMETER = BLOCK_ENTITY_TYPES.register(SubtypeMachine.potentiometer.tag(), () -> new TileEntityType<>(TilePotentiometer::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.potentiometer)), null));
	public static final RegistryObject<TileEntityType<TileCircuitMonitor>> TILE_CIRCUITMONITOR = BLOCK_ENTITY_TYPES.register(SubtypeMachine.circuitmonitor.tag(), () -> new TileEntityType<>(TileCircuitMonitor::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.circuitmonitor)), null));
	public static final RegistryObject<TileEntityType<TileCurrentRegulator>> TILE_CURRENTREGULATOR = BLOCK_ENTITY_TYPES.register(SubtypeMachine.currentregulator.tag(), () -> new TileEntityType<>(TileCurrentRegulator::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.currentregulator)), null));
	
	public static final RegistryObject<TileEntityType<TileCobblestoneGenerator>> TILE_COBBLESTONEGENERATOR = BLOCK_ENTITY_TYPES.register(SubtypeMachine.cobblestonegenerator.tag(), () -> new TileEntityType<>(TileCobblestoneGenerator::new, Sets.newHashSet(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.cobblestonegenerator)), null));
}
