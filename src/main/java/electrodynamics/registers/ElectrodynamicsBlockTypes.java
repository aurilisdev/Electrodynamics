package electrodynamics.registers;

import com.google.common.collect.Sets;
import electrodynamics.api.References;
import electrodynamics.common.block.connect.BlockFluidPipe;
import electrodynamics.common.block.connect.BlockGasPipe;
import electrodynamics.common.block.connect.BlockLogisticalWire;
import electrodynamics.common.block.connect.BlockWire;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.tile.*;
import electrodynamics.common.tile.arcfurnace.TileElectricArcFurnace;
import electrodynamics.common.tile.arcfurnace.TileElectricArcFurnaceDouble;
import electrodynamics.common.tile.arcfurnace.TileElectricArcFurnaceTriple;
import electrodynamics.common.tile.battery.TileBatteryBox;
import electrodynamics.common.tile.battery.TileCarbyneBatteryBox;
import electrodynamics.common.tile.battery.TileLithiumBatteryBox;
import electrodynamics.common.tile.charger.TileChargerHV;
import electrodynamics.common.tile.charger.TileChargerLV;
import electrodynamics.common.tile.charger.TileChargerMV;
import electrodynamics.common.tile.furnace.TileElectricFurnace;
import electrodynamics.common.tile.furnace.TileElectricFurnaceDouble;
import electrodynamics.common.tile.furnace.TileElectricFurnaceTriple;
import electrodynamics.common.tile.gastransformer.TileGasTransformerAddonTank;
import electrodynamics.common.tile.gastransformer.compressor.TileCompressor;
import electrodynamics.common.tile.gastransformer.compressor.TileDecompressor;
import electrodynamics.common.tile.gastransformer.thermoelectricmanipulator.TileThermoelectricManipulator;
import electrodynamics.common.tile.gastransformer.TileGasTransformerSideBlock;
import electrodynamics.common.tile.generators.TileAdvancedSolarPanel;
import electrodynamics.common.tile.generators.TileCoalGenerator;
import electrodynamics.common.tile.generators.TileCombustionChamber;
import electrodynamics.common.tile.generators.TileCreativePowerSource;
import electrodynamics.common.tile.generators.TileHydroelectricGenerator;
import electrodynamics.common.tile.generators.TileSolarPanel;
import electrodynamics.common.tile.generators.TileThermoelectricGenerator;
import electrodynamics.common.tile.generators.TileWindmill;
import electrodynamics.common.tile.mineralcrusher.TileMineralCrusher;
import electrodynamics.common.tile.mineralcrusher.TileMineralCrusherDouble;
import electrodynamics.common.tile.mineralcrusher.TileMineralCrusherTriple;
import electrodynamics.common.tile.mineralgrinder.TileMineralGrinder;
import electrodynamics.common.tile.mineralgrinder.TileMineralGrinderDouble;
import electrodynamics.common.tile.mineralgrinder.TileMineralGrinderTriple;
import electrodynamics.common.tile.network.electric.TileCircuitBreaker;
import electrodynamics.common.tile.network.electric.TileLogisticalWire;
import electrodynamics.common.tile.network.electric.TilePotentiometer;
import electrodynamics.common.tile.network.electric.TileRelay;
import electrodynamics.common.tile.network.electric.TileWire;
import electrodynamics.common.tile.network.fluid.TileFluidPipe;
import electrodynamics.common.tile.network.fluid.TileFluidPipeFilter;
import electrodynamics.common.tile.network.fluid.TileFluidPipePump;
import electrodynamics.common.tile.network.fluid.TileFluidValve;
import electrodynamics.common.tile.network.fluid.TileFluidVoid;
import electrodynamics.common.tile.network.gas.TileGasPipe;
import electrodynamics.common.tile.network.gas.TileGasPipeFilter;
import electrodynamics.common.tile.network.gas.TileGasPipePump;
import electrodynamics.common.tile.network.gas.TileGasValve;
import electrodynamics.common.tile.network.gas.TileGasVent;
import electrodynamics.common.tile.quarry.TileCoolantResavoir;
import electrodynamics.common.tile.quarry.TileFrame;
import electrodynamics.common.tile.quarry.TileLogisticalManager;
import electrodynamics.common.tile.quarry.TileMotorComplex;
import electrodynamics.common.tile.quarry.TileQuarry;
import electrodynamics.common.tile.quarry.TileSeismicMarker;
import electrodynamics.common.tile.quarry.TileSeismicRelay;
import electrodynamics.common.tile.tanks.fluid.TileFluidTankHSLA;
import electrodynamics.common.tile.tanks.fluid.TileFluidTankReinforced;
import electrodynamics.common.tile.tanks.fluid.TileFluidTankSteel;
import electrodynamics.common.tile.tanks.gas.TileGasTankHSLA;
import electrodynamics.common.tile.tanks.gas.TileGasTankReinforced;
import electrodynamics.common.tile.tanks.gas.TileGasTankSteel;
import electrodynamics.common.tile.wiremill.TileWireMill;
import electrodynamics.common.tile.wiremill.TileWireMillDouble;
import electrodynamics.common.tile.wiremill.TileWireMillTriple;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static electrodynamics.registers.ElectrodynamicsBlocks.getBlock;

public class ElectrodynamicsBlockTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, References.ID);

    public static final RegistryObject<BlockEntityType<TileCoalGenerator>> TILE_COALGENERATOR = BLOCK_ENTITY_TYPES.register(SubtypeMachine.coalgenerator.tag(), () -> new BlockEntityType<>(TileCoalGenerator::new, Sets.newHashSet(getBlock(SubtypeMachine.coalgenerator)), null));
    public static final RegistryObject<BlockEntityType<TileSolarPanel>> TILE_SOLARPANEL = BLOCK_ENTITY_TYPES.register(SubtypeMachine.solarpanel.tag(), () -> new BlockEntityType<>(TileSolarPanel::new, Sets.newHashSet(getBlock(SubtypeMachine.solarpanel)), null));
    public static final RegistryObject<BlockEntityType<TileAdvancedSolarPanel>> TILE_ADVANCEDSOLARPANEL = BLOCK_ENTITY_TYPES.register(SubtypeMachine.advancedsolarpanel.tag(), () -> new BlockEntityType<>(TileAdvancedSolarPanel::new, Sets.newHashSet(getBlock(SubtypeMachine.advancedsolarpanel)), null));

    public static final RegistryObject<BlockEntityType<TileElectricFurnace>> TILE_ELECTRICFURNACE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.electricfurnace.tag(), () -> new BlockEntityType<>(TileElectricFurnace::new, Sets.newHashSet(getBlock(SubtypeMachine.electricfurnace)), null));
    public static final RegistryObject<BlockEntityType<TileElectricFurnaceDouble>> TILE_ELECTRICFURNACEDOUBLE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.electricfurnacedouble.tag(), () -> new BlockEntityType<>(TileElectricFurnaceDouble::new, Sets.newHashSet(getBlock(SubtypeMachine.electricfurnacedouble)), null));
    public static final RegistryObject<BlockEntityType<TileElectricFurnaceTriple>> TILE_ELECTRICFURNACETRIPLE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.electricfurnacetriple.tag(), () -> new BlockEntityType<>(TileElectricFurnaceTriple::new, Sets.newHashSet(getBlock(SubtypeMachine.electricfurnacetriple)), null));

    public static final RegistryObject<BlockEntityType<TileElectricArcFurnace>> TILE_ELECTRICARCFURNACE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.electricarcfurnace.tag(), () -> new BlockEntityType<>(TileElectricArcFurnace::new, Sets.newHashSet(getBlock(SubtypeMachine.electricarcfurnace)), null));
    public static final RegistryObject<BlockEntityType<TileElectricArcFurnaceDouble>> TILE_ELECTRICARCFURNACEDOUBLE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.electricarcfurnacedouble.tag(), () -> new BlockEntityType<>(TileElectricArcFurnaceDouble::new, Sets.newHashSet(getBlock(SubtypeMachine.electricarcfurnacedouble)), null));
    public static final RegistryObject<BlockEntityType<TileElectricArcFurnaceTriple>> TILE_ELECTRICARCFURNACETRIPLE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.electricarcfurnacetriple.tag(), () -> new BlockEntityType<>(TileElectricArcFurnaceTriple::new, Sets.newHashSet(getBlock(SubtypeMachine.electricarcfurnacetriple)), null));

    public static final RegistryObject<BlockEntityType<TileWireMill>> TILE_WIREMILL = BLOCK_ENTITY_TYPES.register(SubtypeMachine.wiremill.tag(), () -> new BlockEntityType<>(TileWireMill::new, Sets.newHashSet(getBlock(SubtypeMachine.wiremill)), null));
    public static final RegistryObject<BlockEntityType<TileWireMillDouble>> TILE_WIREMILLDOUBLE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.wiremilldouble.tag(), () -> new BlockEntityType<>(TileWireMillDouble::new, Sets.newHashSet(getBlock(SubtypeMachine.wiremilldouble)), null));
    public static final RegistryObject<BlockEntityType<TileWireMillTriple>> TILE_WIREMILLTRIPLE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.wiremilltriple.tag(), () -> new BlockEntityType<>(TileWireMillTriple::new, Sets.newHashSet(getBlock(SubtypeMachine.wiremilltriple)), null));

    public static final RegistryObject<BlockEntityType<TileMineralGrinder>> TILE_MINERALGRINDER = BLOCK_ENTITY_TYPES.register(SubtypeMachine.mineralgrinder.tag(), () -> new BlockEntityType<>(TileMineralGrinder::new, Sets.newHashSet(getBlock(SubtypeMachine.mineralgrinder)), null));
    public static final RegistryObject<BlockEntityType<TileMineralGrinderDouble>> TILE_MINERALGRINDERDOUBLE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.mineralgrinderdouble.tag(), () -> new BlockEntityType<>(TileMineralGrinderDouble::new, Sets.newHashSet(getBlock(SubtypeMachine.mineralgrinderdouble)), null));
    public static final RegistryObject<BlockEntityType<TileMineralGrinderTriple>> TILE_MINERALGRINDERTRIPLE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.mineralgrindertriple.tag(), () -> new BlockEntityType<>(TileMineralGrinderTriple::new, Sets.newHashSet(getBlock(SubtypeMachine.mineralgrindertriple)), null));

    public static final RegistryObject<BlockEntityType<TileMineralCrusher>> TILE_MINERALCRUSHER = BLOCK_ENTITY_TYPES.register(SubtypeMachine.mineralcrusher.tag(), () -> new BlockEntityType<>(TileMineralCrusher::new, Sets.newHashSet(getBlock(SubtypeMachine.mineralcrusher)), null));
    public static final RegistryObject<BlockEntityType<TileMineralCrusherDouble>> TILE_MINERALCRUSHERDOUBLE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.mineralcrusherdouble.tag(), () -> new BlockEntityType<>(TileMineralCrusherDouble::new, Sets.newHashSet(getBlock(SubtypeMachine.mineralcrusherdouble)), null));
    public static final RegistryObject<BlockEntityType<TileMineralCrusherTriple>> TILE_MINERALCRUSHERTRIPLE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.mineralcrushertriple.tag(), () -> new BlockEntityType<>(TileMineralCrusherTriple::new, Sets.newHashSet(getBlock(SubtypeMachine.mineralcrushertriple)), null));

    public static final RegistryObject<BlockEntityType<TileBatteryBox>> TILE_BATTERYBOX = BLOCK_ENTITY_TYPES.register(SubtypeMachine.batterybox.tag(), () -> new BlockEntityType<>(TileBatteryBox::new, Sets.newHashSet(getBlock(SubtypeMachine.batterybox)), null));
    public static final RegistryObject<BlockEntityType<TileLithiumBatteryBox>> TILE_LITHIUMBATTERYBOX = BLOCK_ENTITY_TYPES.register(SubtypeMachine.lithiumbatterybox.tag(), () -> new BlockEntityType<>(TileLithiumBatteryBox::new, Sets.newHashSet(getBlock(SubtypeMachine.lithiumbatterybox)), null));
    public static final RegistryObject<BlockEntityType<TileCarbyneBatteryBox>> TILE_CARBYNEBATTERYBOX = BLOCK_ENTITY_TYPES.register(SubtypeMachine.carbynebatterybox.tag(), () -> new BlockEntityType<>(TileCarbyneBatteryBox::new, Sets.newHashSet(getBlock(SubtypeMachine.carbynebatterybox)), null));

    public static final RegistryObject<BlockEntityType<TileChargerLV>> TILE_CHARGERLV = BLOCK_ENTITY_TYPES.register(SubtypeMachine.chargerlv.tag(), () -> new BlockEntityType<>(TileChargerLV::new, Sets.newHashSet(getBlock(SubtypeMachine.chargerlv)), null));
    public static final RegistryObject<BlockEntityType<TileChargerMV>> TILE_CHARGERMV = BLOCK_ENTITY_TYPES.register(SubtypeMachine.chargermv.tag(), () -> new BlockEntityType<>(TileChargerMV::new, Sets.newHashSet(getBlock(SubtypeMachine.chargermv)), null));
    public static final RegistryObject<BlockEntityType<TileChargerHV>> TILE_CHARGERHV = BLOCK_ENTITY_TYPES.register(SubtypeMachine.chargerhv.tag(), () -> new BlockEntityType<>(TileChargerHV::new, Sets.newHashSet(getBlock(SubtypeMachine.chargerhv)), null));

    public static final RegistryObject<BlockEntityType<TileFluidTankSteel>> TILE_TANKSTEEL = BLOCK_ENTITY_TYPES.register(SubtypeMachine.tanksteel.tag(), () -> new BlockEntityType<>(TileFluidTankSteel::new, Sets.newHashSet(getBlock(SubtypeMachine.tanksteel)), null));
    public static final RegistryObject<BlockEntityType<TileFluidTankReinforced>> TILE_TANKREINFORCED = BLOCK_ENTITY_TYPES.register(SubtypeMachine.tankreinforced.tag(), () -> new BlockEntityType<>(TileFluidTankReinforced::new, Sets.newHashSet(getBlock(SubtypeMachine.tankreinforced)), null));
    public static final RegistryObject<BlockEntityType<TileFluidTankHSLA>> TILE_TANKHSLA = BLOCK_ENTITY_TYPES.register(SubtypeMachine.tankhsla.tag(), () -> new BlockEntityType<>(TileFluidTankHSLA::new, Sets.newHashSet(getBlock(SubtypeMachine.tankhsla)), null));

    public static final RegistryObject<BlockEntityType<TileTransformer>> TILE_TRANSFORMER = BLOCK_ENTITY_TYPES.register("transformer", () -> new BlockEntityType<>(TileTransformer::new, Sets.newHashSet(getBlock(SubtypeMachine.downgradetransformer), getBlock(SubtypeMachine.upgradetransformer)), null));
    public static final RegistryObject<BlockEntityType<TileEnergizedAlloyer>> TILE_ENERGIZEDALLOYER = BLOCK_ENTITY_TYPES.register(SubtypeMachine.energizedalloyer.tag(), () -> new BlockEntityType<>(TileEnergizedAlloyer::new, Sets.newHashSet(getBlock(SubtypeMachine.energizedalloyer)), null));
    public static final RegistryObject<BlockEntityType<TileLathe>> TILE_LATHE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.lathe.tag(), () -> new BlockEntityType<>(TileLathe::new, Sets.newHashSet(getBlock(SubtypeMachine.lathe)), null));
    public static final RegistryObject<BlockEntityType<TileReinforcedAlloyer>> TILE_REINFORCEDALLOYER = BLOCK_ENTITY_TYPES.register(SubtypeMachine.reinforcedalloyer.tag(), () -> new BlockEntityType<>(TileReinforcedAlloyer::new, Sets.newHashSet(getBlock(SubtypeMachine.reinforcedalloyer)), null));
    public static final RegistryObject<BlockEntityType<TileOxidationFurnace>> TILE_OXIDATIONFURNACE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.oxidationfurnace.tag(), () -> new BlockEntityType<>(TileOxidationFurnace::new, Sets.newHashSet(getBlock(SubtypeMachine.oxidationfurnace)), null));
    public static final RegistryObject<BlockEntityType<TileCreativePowerSource>> TILE_CREATIVEPOWERSOURCE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.creativepowersource.tag(), () -> new BlockEntityType<>(TileCreativePowerSource::new, Sets.newHashSet(getBlock(SubtypeMachine.creativepowersource)), null));
    public static final RegistryObject<BlockEntityType<TileElectricPump>> TILE_ELECTRICPUMP = BLOCK_ENTITY_TYPES.register(SubtypeMachine.electricpump.tag(), () -> new BlockEntityType<>(TileElectricPump::new, Sets.newHashSet(getBlock(SubtypeMachine.electricpump)), null));
    public static final RegistryObject<BlockEntityType<TileThermoelectricGenerator>> TILE_THERMOELECTRICGENERATOR = BLOCK_ENTITY_TYPES.register(SubtypeMachine.thermoelectricgenerator.tag(), () -> new BlockEntityType<>(TileThermoelectricGenerator::new, Sets.newHashSet(getBlock(SubtypeMachine.thermoelectricgenerator)), null));
    public static final RegistryObject<BlockEntityType<TileHydroelectricGenerator>> TILE_HYDROELECTRICGENERATOR = BLOCK_ENTITY_TYPES.register(SubtypeMachine.hydroelectricgenerator.tag(), () -> new BlockEntityType<>(TileHydroelectricGenerator::new, Sets.newHashSet(getBlock(SubtypeMachine.hydroelectricgenerator)), null));
    public static final RegistryObject<BlockEntityType<TileWindmill>> TILE_WINDMILL = BLOCK_ENTITY_TYPES.register(SubtypeMachine.windmill.tag(), () -> new BlockEntityType<>(TileWindmill::new, Sets.newHashSet(getBlock(SubtypeMachine.windmill)), null));
    public static final RegistryObject<BlockEntityType<TileFermentationPlant>> TILE_FERMENTATIONPLANT = BLOCK_ENTITY_TYPES.register(SubtypeMachine.fermentationplant.tag(), () -> new BlockEntityType<>(TileFermentationPlant::new, Sets.newHashSet(getBlock(SubtypeMachine.fermentationplant)), null));
    public static final RegistryObject<BlockEntityType<TileCombustionChamber>> TILE_COMBUSTIONCHAMBER = BLOCK_ENTITY_TYPES.register(SubtypeMachine.combustionchamber.tag(), () -> new BlockEntityType<>(TileCombustionChamber::new, Sets.newHashSet(getBlock(SubtypeMachine.combustionchamber)), null));
    public static final RegistryObject<BlockEntityType<TileMineralWasher>> TILE_MINERALWASHER = BLOCK_ENTITY_TYPES.register(SubtypeMachine.mineralwasher.tag(), () -> new BlockEntityType<>(TileMineralWasher::new, Sets.newHashSet(getBlock(SubtypeMachine.mineralwasher)), null));
    public static final RegistryObject<BlockEntityType<TileChemicalMixer>> TILE_CHEMICALMIXER = BLOCK_ENTITY_TYPES.register(SubtypeMachine.chemicalmixer.tag(), () -> new BlockEntityType<>(TileChemicalMixer::new, Sets.newHashSet(getBlock(SubtypeMachine.chemicalmixer)), null));
    public static final RegistryObject<BlockEntityType<TileChemicalCrystallizer>> TILE_CHEMICALCRYSTALLIZER = BLOCK_ENTITY_TYPES.register(SubtypeMachine.chemicalcrystallizer.tag(), () -> new BlockEntityType<>(TileChemicalCrystallizer::new, Sets.newHashSet(getBlock(SubtypeMachine.chemicalcrystallizer)), null));
    public static final RegistryObject<BlockEntityType<TileCircuitBreaker>> TILE_CIRCUITBREAKER = BLOCK_ENTITY_TYPES.register(SubtypeMachine.circuitbreaker.tag(), () -> new BlockEntityType<>(TileCircuitBreaker::new, Sets.newHashSet(getBlock(SubtypeMachine.circuitbreaker)), null));
    public static final RegistryObject<BlockEntityType<TileMultimeterBlock>> TILE_MULTIMETERBLOCK = BLOCK_ENTITY_TYPES.register(SubtypeMachine.multimeterblock.tag(), () -> new BlockEntityType<>(TileMultimeterBlock::new, Sets.newHashSet(getBlock(SubtypeMachine.multimeterblock)), null));
    public static final RegistryObject<BlockEntityType<TileMultiSubnode>> TILE_MULTI = BLOCK_ENTITY_TYPES.register("multisubnode", () -> new BlockEntityType<>(TileMultiSubnode::new, Sets.newHashSet(ElectrodynamicsBlocks.multi), null));
    public static final RegistryObject<BlockEntityType<TileWire>> TILE_WIRE = BLOCK_ENTITY_TYPES.register("wiregenerictile", () -> new BlockEntityType<>(TileWire::new, BlockWire.WIRES, null));
    public static final RegistryObject<BlockEntityType<TileLogisticalWire>> TILE_LOGISTICALWIRE = BLOCK_ENTITY_TYPES.register("wirelogisticaltile", () -> new BlockEntityType<>(TileLogisticalWire::new, BlockLogisticalWire.WIRES, null));
    public static final RegistryObject<BlockEntityType<TileFluidPipe>> TILE_PIPE = BLOCK_ENTITY_TYPES.register("pipegenerictile", () -> new BlockEntityType<>(TileFluidPipe::new, BlockFluidPipe.PIPESET, null));
    public static final RegistryObject<BlockEntityType<TileElectrolyticSeparator>> TILE_ELECTROLYTICSEPARATOR = BLOCK_ENTITY_TYPES.register(SubtypeMachine.electrolyticseparator.tag(), () -> new BlockEntityType<>(TileElectrolyticSeparator::new, Sets.newHashSet(getBlock(SubtypeMachine.electrolyticseparator)), null));
    public static final RegistryObject<BlockEntityType<TileCreativeFluidSource>> TILE_CREATIVEFLUIDSOURCE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.creativefluidsource.tag(), () -> new BlockEntityType<>(TileCreativeFluidSource::new, Sets.newHashSet(getBlock(SubtypeMachine.creativefluidsource)), null));
    public static final RegistryObject<BlockEntityType<TileFluidVoid>> TILE_FLUIDVOID = BLOCK_ENTITY_TYPES.register(SubtypeMachine.fluidvoid.tag(), () -> new BlockEntityType<>(TileFluidVoid::new, Sets.newHashSet(getBlock(SubtypeMachine.fluidvoid)), null));
	public static final RegistryObject<BlockEntityType<TileSeismicMarker>> TILE_SEISMICMARKER = BLOCK_ENTITY_TYPES.register("seismicmarker", () -> new BlockEntityType<>(TileSeismicMarker::new, Sets.newHashSet(ElectrodynamicsBlocks.blockSeismicMarker), null));
    public static final RegistryObject<BlockEntityType<TileSeismicRelay>> TILE_SEISMICRELAY = BLOCK_ENTITY_TYPES.register(SubtypeMachine.seismicrelay.tag(), () -> new BlockEntityType<>(TileSeismicRelay::new, Sets.newHashSet(getBlock(SubtypeMachine.seismicrelay)), null));
    public static final RegistryObject<BlockEntityType<TileQuarry>> TILE_QUARRY = BLOCK_ENTITY_TYPES.register(SubtypeMachine.quarry.tag(), () -> new BlockEntityType<>(TileQuarry::new, Sets.newHashSet(getBlock(SubtypeMachine.quarry)), null));
    public static final RegistryObject<BlockEntityType<TileCoolantResavoir>> TILE_COOLANTRESAVOIR = BLOCK_ENTITY_TYPES.register(SubtypeMachine.coolantresavoir.tag(), () -> new BlockEntityType<>(TileCoolantResavoir::new, Sets.newHashSet(getBlock(SubtypeMachine.coolantresavoir)), null));
    public static final RegistryObject<BlockEntityType<TileMotorComplex>> TILE_MOTORCOMPLEX = BLOCK_ENTITY_TYPES.register(SubtypeMachine.motorcomplex.tag(), () -> new BlockEntityType<>(TileMotorComplex::new, Sets.newHashSet(getBlock(SubtypeMachine.motorcomplex)), null));
    public static final RegistryObject<BlockEntityType<TileLogisticalManager>> TILE_LOGISTICALMANAGER = BLOCK_ENTITY_TYPES.register("logisticalmanager", () -> new BlockEntityType<>(TileLogisticalManager::new, Sets.newHashSet(ElectrodynamicsBlocks.blockLogisticalManager), null));
    public static final RegistryObject<BlockEntityType<TileFrame>> TILE_QUARRY_FRAME = BLOCK_ENTITY_TYPES.register("quarryframe", () -> new BlockEntityType<>(TileFrame::new, Sets.newHashSet(ElectrodynamicsBlocks.blockFrame), null));
    public static final RegistryObject<BlockEntityType<TileGasPipe>> TILE_GAS_PIPE = BLOCK_ENTITY_TYPES.register("gaspipe", () -> new BlockEntityType<>(TileGasPipe::new, Sets.newHashSet(BlockGasPipe.PIPESET), null));
    
    public static final RegistryObject<BlockEntityType<TileGasTankSteel>> TILE_GASTANK_STEEL = BLOCK_ENTITY_TYPES.register("gastanksteel", () -> new BlockEntityType<>(TileGasTankSteel::new, Sets.newHashSet(ElectrodynamicsBlocks.getBlock(SubtypeMachine.gastanksteel)), null));
    public static final RegistryObject<BlockEntityType<TileGasTankReinforced>> TILE_GASTANK_REINFORCED = BLOCK_ENTITY_TYPES.register("gastankreinforced", () -> new BlockEntityType<>(TileGasTankReinforced::new, Sets.newHashSet(ElectrodynamicsBlocks.getBlock(SubtypeMachine.gastankreinforced)), null));
    public static final RegistryObject<BlockEntityType<TileGasTankHSLA>> TILE_GASTANK_HSLA = BLOCK_ENTITY_TYPES.register("gastankhsla", () -> new BlockEntityType<>(TileGasTankHSLA::new, Sets.newHashSet(ElectrodynamicsBlocks.getBlock(SubtypeMachine.gastankhsla)), null));
    
    public static final RegistryObject<BlockEntityType<TileCompressor>> TILE_COMPRESSOR = BLOCK_ENTITY_TYPES.register("compressor", () -> new BlockEntityType<>(TileCompressor::new, Sets.newHashSet(ElectrodynamicsBlocks.blockCompressor), null));
    public static final RegistryObject<BlockEntityType<TileDecompressor>> TILE_DECOMPRESSOR = BLOCK_ENTITY_TYPES.register("decompressor", () -> new BlockEntityType<>(TileDecompressor::new, Sets.newHashSet(ElectrodynamicsBlocks.blockDecompressor), null));
    public static final RegistryObject<BlockEntityType<TileGasTransformerSideBlock>> TILE_COMPRESSOR_SIDE = BLOCK_ENTITY_TYPES.register("compressorside", () -> new BlockEntityType<>(TileGasTransformerSideBlock::new, Sets.newHashSet(ElectrodynamicsBlocks.blockGasTransformerSide), null));
    public static final RegistryObject<BlockEntityType<TileGasTransformerAddonTank>> TILE_COMPRESSOR_ADDONTANK = BLOCK_ENTITY_TYPES.register("compressoraddontank", () -> new BlockEntityType<>(TileGasTransformerAddonTank::new, Sets.newHashSet(ElectrodynamicsBlocks.blockGasTransformerAddonTank), null));
    public static final RegistryObject<BlockEntityType<TileGasVent>> TILE_GASVENT = BLOCK_ENTITY_TYPES.register("gasvent", () -> new BlockEntityType<>(TileGasVent::new, Sets.newHashSet(ElectrodynamicsBlocks.getBlock(SubtypeMachine.gasvent)), null));
    public static final RegistryObject<BlockEntityType<TileThermoelectricManipulator>> TILE_THERMOELECTRIC_MANIPULATOR = BLOCK_ENTITY_TYPES.register("thermoelectricmanipulator", () -> new BlockEntityType<>(TileThermoelectricManipulator::new, Sets.newHashSet(ElectrodynamicsBlocks.blockThermoelectricManipulator), null));
    
    public static final RegistryObject<BlockEntityType<TileGasValve>> TILE_GASVALVE = BLOCK_ENTITY_TYPES.register("gasvalve", () -> new BlockEntityType<>(TileGasValve::new, Sets.newHashSet(ElectrodynamicsBlocks.blockGasValve), null));
    public static final RegistryObject<BlockEntityType<TileFluidValve>> TILE_FLUIDVALVE = BLOCK_ENTITY_TYPES.register("fluidvalve", () -> new BlockEntityType<>(TileFluidValve::new, Sets.newHashSet(ElectrodynamicsBlocks.blockFluidValve), null));
    public static final RegistryObject<BlockEntityType<TileGasPipePump>> TILE_GASPIPEPUMP = BLOCK_ENTITY_TYPES.register("gaspipepump", () -> new BlockEntityType<>(TileGasPipePump::new, Sets.newHashSet(ElectrodynamicsBlocks.blockGasPipePump), null));
    public static final RegistryObject<BlockEntityType<TileFluidPipePump>> TILE_FLUIDPIPEPUMP = BLOCK_ENTITY_TYPES.register("fluidpipepump", () -> new BlockEntityType<>(TileFluidPipePump::new, Sets.newHashSet(ElectrodynamicsBlocks.blockFluidPipePump), null));
    public static final RegistryObject<BlockEntityType<TileGasPipeFilter>> TILE_GASPIPEFILTER = BLOCK_ENTITY_TYPES.register("gaspipefilter", () -> new BlockEntityType<>(TileGasPipeFilter::new, Sets.newHashSet(ElectrodynamicsBlocks.blockGasPipeFilter), null));
    public static final RegistryObject<BlockEntityType<TileFluidPipeFilter>> TILE_FLUIDPIPEFILTER = BLOCK_ENTITY_TYPES.register("fluidpipefilter", () -> new BlockEntityType<>(TileFluidPipeFilter::new, Sets.newHashSet(ElectrodynamicsBlocks.blockFluidPipeFilter), null));
    
    public static final RegistryObject<BlockEntityType<TileRelay>> TILE_RELAY = BLOCK_ENTITY_TYPES.register("relay", () -> new BlockEntityType<>(TileRelay::new, Sets.newHashSet(ElectrodynamicsBlocks.getBlock(SubtypeMachine.relay)), null));
    public static final RegistryObject<BlockEntityType<TilePotentiometer>> TILE_POTENTIOMETER = BLOCK_ENTITY_TYPES.register("potentiometer", () -> new BlockEntityType<>(TilePotentiometer::new, Sets.newHashSet(ElectrodynamicsBlocks.getBlock(SubtypeMachine.potentiometer)), null));
}
