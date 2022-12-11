package electrodynamics.registers;

import static electrodynamics.registers.UnifiedElectrodynamicsRegister.getSafeBlock;

import com.google.common.collect.Sets;

import electrodynamics.api.References;
import electrodynamics.common.block.connect.BlockPipe;
import electrodynamics.common.block.connect.BlockWire;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.tile.TileAdvancedSolarPanel;
import electrodynamics.common.tile.TileBatteryBox;
import electrodynamics.common.tile.TileCarbyneBatteryBox;
import electrodynamics.common.tile.TileChargerHV;
import electrodynamics.common.tile.TileChargerLV;
import electrodynamics.common.tile.TileChargerMV;
import electrodynamics.common.tile.TileChemicalCrystallizer;
import electrodynamics.common.tile.TileChemicalMixer;
import electrodynamics.common.tile.TileCircuitBreaker;
import electrodynamics.common.tile.TileCoalGenerator;
import electrodynamics.common.tile.TileCombustionChamber;
import electrodynamics.common.tile.TileCoolantResavoir;
import electrodynamics.common.tile.TileCreativeFluidSource;
import electrodynamics.common.tile.TileCreativePowerSource;
import electrodynamics.common.tile.TileElectricArcFurnace;
import electrodynamics.common.tile.TileElectricArcFurnaceDouble;
import electrodynamics.common.tile.TileElectricArcFurnaceTriple;
import electrodynamics.common.tile.TileElectricFurnace;
import electrodynamics.common.tile.TileElectricFurnaceDouble;
import electrodynamics.common.tile.TileElectricFurnaceTriple;
import electrodynamics.common.tile.TileElectricPump;
import electrodynamics.common.tile.TileElectrolyticSeparator;
import electrodynamics.common.tile.TileEnergizedAlloyer;
import electrodynamics.common.tile.TileFermentationPlant;
import electrodynamics.common.tile.TileFluidVoid;
import electrodynamics.common.tile.TileHydroelectricGenerator;
import electrodynamics.common.tile.TileLathe;
import electrodynamics.common.tile.TileLithiumBatteryBox;
import electrodynamics.common.tile.TileLogisticalManager;
import electrodynamics.common.tile.TileMineralCrusher;
import electrodynamics.common.tile.TileMineralCrusherDouble;
import electrodynamics.common.tile.TileMineralCrusherTriple;
import electrodynamics.common.tile.TileMineralGrinder;
import electrodynamics.common.tile.TileMineralGrinderDouble;
import electrodynamics.common.tile.TileMineralGrinderTriple;
import electrodynamics.common.tile.TileMineralWasher;
import electrodynamics.common.tile.TileMotorComplex;
import electrodynamics.common.tile.TileMultiSubnode;
import electrodynamics.common.tile.TileMultimeterBlock;
import electrodynamics.common.tile.TileOxidationFurnace;
import electrodynamics.common.tile.TileReinforcedAlloyer;
import electrodynamics.common.tile.TileSeismicMarker;
import electrodynamics.common.tile.TileSeismicRelay;
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
import electrodynamics.common.tile.quarry.TileFrame;
import electrodynamics.common.tile.quarry.TileQuarry;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ElectrodynamicsBlockTypes {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, References.ID);

	public static final RegistryObject<BlockEntityType<TileCoalGenerator>> TILE_COALGENERATOR = BLOCK_ENTITY_TYPES.register(SubtypeMachine.coalgenerator.tag(), () -> new BlockEntityType<>(TileCoalGenerator::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.coalgenerator)), null));
	public static final RegistryObject<BlockEntityType<TileSolarPanel>> TILE_SOLARPANEL = BLOCK_ENTITY_TYPES.register(SubtypeMachine.solarpanel.tag(), () -> new BlockEntityType<>(TileSolarPanel::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.solarpanel)), null));
	public static final RegistryObject<BlockEntityType<TileAdvancedSolarPanel>> TILE_ADVANCEDSOLARPANEL = BLOCK_ENTITY_TYPES.register(SubtypeMachine.advancedsolarpanel.tag(), () -> new BlockEntityType<>(TileAdvancedSolarPanel::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.advancedsolarpanel)), null));

	public static final RegistryObject<BlockEntityType<TileElectricFurnace>> TILE_ELECTRICFURNACE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.electricfurnace.tag(), () -> new BlockEntityType<>(TileElectricFurnace::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.electricfurnace)), null));
	public static final RegistryObject<BlockEntityType<TileElectricFurnaceDouble>> TILE_ELECTRICFURNACEDOUBLE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.electricfurnacedouble.tag(), () -> new BlockEntityType<>(TileElectricFurnaceDouble::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.electricfurnacedouble)), null));
	public static final RegistryObject<BlockEntityType<TileElectricFurnaceTriple>> TILE_ELECTRICFURNACETRIPLE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.electricfurnacetriple.tag(), () -> new BlockEntityType<>(TileElectricFurnaceTriple::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.electricfurnacetriple)), null));

	public static final RegistryObject<BlockEntityType<TileElectricArcFurnace>> TILE_ELECTRICARCFURNACE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.electricarcfurnace.tag(), () -> new BlockEntityType<>(TileElectricArcFurnace::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.electricarcfurnace)), null));
	public static final RegistryObject<BlockEntityType<TileElectricArcFurnaceDouble>> TILE_ELECTRICARCFURNACEDOUBLE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.electricarcfurnacedouble.tag(), () -> new BlockEntityType<>(TileElectricArcFurnaceDouble::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.electricarcfurnacedouble)), null));
	public static final RegistryObject<BlockEntityType<TileElectricArcFurnaceTriple>> TILE_ELECTRICARCFURNACETRIPLE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.electricarcfurnacetriple.tag(), () -> new BlockEntityType<>(TileElectricArcFurnaceTriple::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.electricarcfurnacetriple)), null));

	public static final RegistryObject<BlockEntityType<TileWireMill>> TILE_WIREMILL = BLOCK_ENTITY_TYPES.register(SubtypeMachine.wiremill.tag(), () -> new BlockEntityType<>(TileWireMill::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.wiremill)), null));
	public static final RegistryObject<BlockEntityType<TileWireMillDouble>> TILE_WIREMILLDOUBLE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.wiremilldouble.tag(), () -> new BlockEntityType<>(TileWireMillDouble::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.wiremilldouble)), null));
	public static final RegistryObject<BlockEntityType<TileWireMillTriple>> TILE_WIREMILLTRIPLE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.wiremilltriple.tag(), () -> new BlockEntityType<>(TileWireMillTriple::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.wiremilltriple)), null));

	public static final RegistryObject<BlockEntityType<TileMineralGrinder>> TILE_MINERALGRINDER = BLOCK_ENTITY_TYPES.register(SubtypeMachine.mineralgrinder.tag(), () -> new BlockEntityType<>(TileMineralGrinder::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.mineralgrinder)), null));
	public static final RegistryObject<BlockEntityType<TileMineralGrinderDouble>> TILE_MINERALGRINDERDOUBLE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.mineralgrinderdouble.tag(), () -> new BlockEntityType<>(TileMineralGrinderDouble::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.mineralgrinderdouble)), null));
	public static final RegistryObject<BlockEntityType<TileMineralGrinderTriple>> TILE_MINERALGRINDERTRIPLE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.mineralgrindertriple.tag(), () -> new BlockEntityType<>(TileMineralGrinderTriple::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.mineralgrindertriple)), null));

	public static final RegistryObject<BlockEntityType<TileMineralCrusher>> TILE_MINERALCRUSHER = BLOCK_ENTITY_TYPES.register(SubtypeMachine.mineralcrusher.tag(), () -> new BlockEntityType<>(TileMineralCrusher::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.mineralcrusher)), null));
	public static final RegistryObject<BlockEntityType<TileMineralCrusherDouble>> TILE_MINERALCRUSHERDOUBLE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.mineralcrusherdouble.tag(), () -> new BlockEntityType<>(TileMineralCrusherDouble::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.mineralcrusherdouble)), null));
	public static final RegistryObject<BlockEntityType<TileMineralCrusherTriple>> TILE_MINERALCRUSHERTRIPLE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.mineralcrushertriple.tag(), () -> new BlockEntityType<>(TileMineralCrusherTriple::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.mineralcrushertriple)), null));

	public static final RegistryObject<BlockEntityType<TileBatteryBox>> TILE_BATTERYBOX = BLOCK_ENTITY_TYPES.register(SubtypeMachine.batterybox.tag(), () -> new BlockEntityType<>(TileBatteryBox::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.batterybox)), null));
	public static final RegistryObject<BlockEntityType<TileLithiumBatteryBox>> TILE_LITHIUMBATTERYBOX = BLOCK_ENTITY_TYPES.register(SubtypeMachine.lithiumbatterybox.tag(), () -> new BlockEntityType<>(TileLithiumBatteryBox::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.lithiumbatterybox)), null));
	public static final RegistryObject<BlockEntityType<TileCarbyneBatteryBox>> TILE_CARBYNEBATTERYBOX = BLOCK_ENTITY_TYPES.register(SubtypeMachine.carbynebatterybox.tag(), () -> new BlockEntityType<>(TileCarbyneBatteryBox::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.carbynebatterybox)), null));

	public static final RegistryObject<BlockEntityType<TileChargerLV>> TILE_CHARGERLV = BLOCK_ENTITY_TYPES.register(SubtypeMachine.chargerlv.tag(), () -> new BlockEntityType<>(TileChargerLV::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.chargerlv)), null));
	public static final RegistryObject<BlockEntityType<TileChargerMV>> TILE_CHARGERMV = BLOCK_ENTITY_TYPES.register(SubtypeMachine.chargermv.tag(), () -> new BlockEntityType<>(TileChargerMV::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.chargermv)), null));
	public static final RegistryObject<BlockEntityType<TileChargerHV>> TILE_CHARGERHV = BLOCK_ENTITY_TYPES.register(SubtypeMachine.chargerhv.tag(), () -> new BlockEntityType<>(TileChargerHV::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.chargerhv)), null));

	public static final RegistryObject<BlockEntityType<TileTankSteel>> TILE_TANKSTEEL = BLOCK_ENTITY_TYPES.register(SubtypeMachine.tanksteel.tag(), () -> new BlockEntityType<>(TileTankSteel::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.tanksteel)), null));
	public static final RegistryObject<BlockEntityType<TileTankReinforced>> TILE_TANKREINFORCED = BLOCK_ENTITY_TYPES.register(SubtypeMachine.tankreinforced.tag(), () -> new BlockEntityType<>(TileTankReinforced::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.tankreinforced)), null));
	public static final RegistryObject<BlockEntityType<TileTankHSLA>> TILE_TANKHSLA = BLOCK_ENTITY_TYPES.register(SubtypeMachine.tankhsla.tag(), () -> new BlockEntityType<>(TileTankHSLA::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.tankhsla)), null));

	public static final RegistryObject<BlockEntityType<TileTransformer>> TILE_TRANSFORMER = BLOCK_ENTITY_TYPES.register("transformer", () -> new BlockEntityType<>(TileTransformer::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.downgradetransformer), getSafeBlock(SubtypeMachine.upgradetransformer)), null));
	public static final RegistryObject<BlockEntityType<TileEnergizedAlloyer>> TILE_ENERGIZEDALLOYER = BLOCK_ENTITY_TYPES.register(SubtypeMachine.energizedalloyer.tag(), () -> new BlockEntityType<>(TileEnergizedAlloyer::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.energizedalloyer)), null));
	public static final RegistryObject<BlockEntityType<TileLathe>> TILE_LATHE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.lathe.tag(), () -> new BlockEntityType<>(TileLathe::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.lathe)), null));
	public static final RegistryObject<BlockEntityType<TileReinforcedAlloyer>> TILE_REINFORCEDALLOYER = BLOCK_ENTITY_TYPES.register(SubtypeMachine.reinforcedalloyer.tag(), () -> new BlockEntityType<>(TileReinforcedAlloyer::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.reinforcedalloyer)), null));
	public static final RegistryObject<BlockEntityType<TileOxidationFurnace>> TILE_OXIDATIONFURNACE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.oxidationfurnace.tag(), () -> new BlockEntityType<>(TileOxidationFurnace::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.oxidationfurnace)), null));
	public static final RegistryObject<BlockEntityType<TileCreativePowerSource>> TILE_CREATIVEPOWERSOURCE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.creativepowersource.tag(), () -> new BlockEntityType<>(TileCreativePowerSource::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.creativepowersource)), null));
	public static final RegistryObject<BlockEntityType<TileElectricPump>> TILE_ELECTRICPUMP = BLOCK_ENTITY_TYPES.register(SubtypeMachine.electricpump.tag(), () -> new BlockEntityType<>(TileElectricPump::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.electricpump)), null));
	public static final RegistryObject<BlockEntityType<TileThermoelectricGenerator>> TILE_THERMOELECTRICGENERATOR = BLOCK_ENTITY_TYPES.register(SubtypeMachine.thermoelectricgenerator.tag(), () -> new BlockEntityType<>(TileThermoelectricGenerator::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.thermoelectricgenerator)), null));
	public static final RegistryObject<BlockEntityType<TileHydroelectricGenerator>> TILE_HYDROELECTRICGENERATOR = BLOCK_ENTITY_TYPES.register(SubtypeMachine.hydroelectricgenerator.tag(), () -> new BlockEntityType<>(TileHydroelectricGenerator::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.hydroelectricgenerator)), null));
	public static final RegistryObject<BlockEntityType<TileWindmill>> TILE_WINDMILL = BLOCK_ENTITY_TYPES.register(SubtypeMachine.windmill.tag(), () -> new BlockEntityType<>(TileWindmill::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.windmill)), null));
	public static final RegistryObject<BlockEntityType<TileFermentationPlant>> TILE_FERMENTATIONPLANT = BLOCK_ENTITY_TYPES.register(SubtypeMachine.fermentationplant.tag(), () -> new BlockEntityType<>(TileFermentationPlant::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.fermentationplant)), null));
	public static final RegistryObject<BlockEntityType<TileCombustionChamber>> TILE_COMBUSTIONCHAMBER = BLOCK_ENTITY_TYPES.register(SubtypeMachine.combustionchamber.tag(), () -> new BlockEntityType<>(TileCombustionChamber::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.combustionchamber)), null));
	public static final RegistryObject<BlockEntityType<TileMineralWasher>> TILE_MINERALWASHER = BLOCK_ENTITY_TYPES.register(SubtypeMachine.mineralwasher.tag(), () -> new BlockEntityType<>(TileMineralWasher::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.mineralwasher)), null));
	public static final RegistryObject<BlockEntityType<TileChemicalMixer>> TILE_CHEMICALMIXER = BLOCK_ENTITY_TYPES.register(SubtypeMachine.chemicalmixer.tag(), () -> new BlockEntityType<>(TileChemicalMixer::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.chemicalmixer)), null));
	public static final RegistryObject<BlockEntityType<TileChemicalCrystallizer>> TILE_CHEMICALCRYSTALLIZER = BLOCK_ENTITY_TYPES.register(SubtypeMachine.chemicalcrystallizer.tag(), () -> new BlockEntityType<>(TileChemicalCrystallizer::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.chemicalcrystallizer)), null));
	public static final RegistryObject<BlockEntityType<TileCircuitBreaker>> TILE_CIRCUITBREAKER = BLOCK_ENTITY_TYPES.register(SubtypeMachine.circuitbreaker.tag(), () -> new BlockEntityType<>(TileCircuitBreaker::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.circuitbreaker)), null));
	public static final RegistryObject<BlockEntityType<TileMultimeterBlock>> TILE_MULTIMETERBLOCK = BLOCK_ENTITY_TYPES.register(SubtypeMachine.multimeterblock.tag(), () -> new BlockEntityType<>(TileMultimeterBlock::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.multimeterblock)), null));
	public static final RegistryObject<BlockEntityType<TileMultiSubnode>> TILE_MULTI = BLOCK_ENTITY_TYPES.register("multisubnode", () -> new BlockEntityType<>(TileMultiSubnode::new, Sets.newHashSet(ElectrodynamicsBlocks.multi), null));
	public static final RegistryObject<BlockEntityType<TileWire>> TILE_WIRE = BLOCK_ENTITY_TYPES.register("wiregenerictile", () -> new BlockEntityType<>(TileWire::new, BlockWire.WIRESET, null));
	public static final RegistryObject<BlockEntityType<TileLogisticalWire>> TILE_LOGISTICALWIRE = BLOCK_ENTITY_TYPES.register("wirelogisticaltile", () -> new BlockEntityType<>(TileLogisticalWire::new, BlockWire.WIRESET, null));
	public static final RegistryObject<BlockEntityType<TilePipe>> TILE_PIPE = BLOCK_ENTITY_TYPES.register("pipegenerictile", () -> new BlockEntityType<>(TilePipe::new, BlockPipe.PIPESET, null));
	public static final RegistryObject<BlockEntityType<TileElectrolyticSeparator>> TILE_ELECTROLYTICSEPARATOR = BLOCK_ENTITY_TYPES.register(SubtypeMachine.electrolyticseparator.tag(), () -> new BlockEntityType<>(TileElectrolyticSeparator::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.electrolyticseparator)), null));
	public static final RegistryObject<BlockEntityType<TileCreativeFluidSource>> TILE_CREATIVEFLUIDSOURCE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.creativefluidsource.tag(), () -> new BlockEntityType<>(TileCreativeFluidSource::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.creativefluidsource)), null));
	public static final RegistryObject<BlockEntityType<TileFluidVoid>> TILE_FLUIDVOID = BLOCK_ENTITY_TYPES.register(SubtypeMachine.fluidvoid.tag(), () -> new BlockEntityType<>(TileFluidVoid::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.fluidvoid)), null));
	public static final RegistryObject<BlockEntityType<TileSeismicMarker>> TILE_SEISMICMARKER = BLOCK_ENTITY_TYPES.register("seismicmarker", () -> new BlockEntityType<>(TileSeismicMarker::new, Sets.newHashSet(ElectrodynamicsBlocks.blockSeismicMarker), null));
	public static final RegistryObject<BlockEntityType<TileSeismicRelay>> TILE_SEISMICRELAY = BLOCK_ENTITY_TYPES.register(SubtypeMachine.seismicrelay.tag(), () -> new BlockEntityType<>(TileSeismicRelay::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.seismicrelay)), null));
	public static final RegistryObject<BlockEntityType<TileQuarry>> TILE_QUARRY = BLOCK_ENTITY_TYPES.register(SubtypeMachine.quarry.tag(), () -> new BlockEntityType<>(TileQuarry::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.quarry)), null));
	public static final RegistryObject<BlockEntityType<TileCoolantResavoir>> TILE_COOLANTRESAVOIR = BLOCK_ENTITY_TYPES.register(SubtypeMachine.coolantresavoir.tag(), () -> new BlockEntityType<>(TileCoolantResavoir::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.coolantresavoir)), null));
	public static final RegistryObject<BlockEntityType<TileMotorComplex>> TILE_MOTORCOMPLEX = BLOCK_ENTITY_TYPES.register(SubtypeMachine.motorcomplex.tag(), () -> new BlockEntityType<>(TileMotorComplex::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.motorcomplex)), null));
	public static final RegistryObject<BlockEntityType<TileLogisticalManager>> TILE_LOGISTICALMANAGER = BLOCK_ENTITY_TYPES.register("logisticalmanager", () -> new BlockEntityType<>(TileLogisticalManager::new, Sets.newHashSet(ElectrodynamicsBlocks.blockLogisticalManager), null));
	public static final RegistryObject<BlockEntityType<TileFrame>> TILE_QUARRY_FRAME = BLOCK_ENTITY_TYPES.register("quarryframe", () -> new BlockEntityType<>(TileFrame::new, Sets.newHashSet(ElectrodynamicsBlocks.blockFrame), null));
}
