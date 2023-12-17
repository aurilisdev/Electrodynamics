package electrodynamics.registers;

import com.google.common.collect.Sets;
import electrodynamics.api.References;
import electrodynamics.common.block.connect.BlockFluidPipe;
import electrodynamics.common.block.connect.BlockLogisticalWire;
import electrodynamics.common.block.connect.BlockWire;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.tile.*;
import electrodynamics.common.tile.electricitygrid.TileCircuitBreaker;
import electrodynamics.common.tile.electricitygrid.TileLogisticalWire;
import electrodynamics.common.tile.electricitygrid.TileMultimeterBlock;
import electrodynamics.common.tile.electricitygrid.TileWire;
import electrodynamics.common.tile.electricitygrid.batteries.TileBatteryBox;
import electrodynamics.common.tile.electricitygrid.batteries.TileLithiumBatteryBox;
import electrodynamics.common.tile.electricitygrid.generators.TileAdvancedSolarPanel;
import electrodynamics.common.tile.electricitygrid.generators.TileCoalGenerator;
import electrodynamics.common.tile.electricitygrid.generators.TileCombustionChamber;
import electrodynamics.common.tile.electricitygrid.generators.TileCreativePowerSource;
import electrodynamics.common.tile.electricitygrid.generators.TileHydroelectricGenerator;
import electrodynamics.common.tile.electricitygrid.generators.TileSolarPanel;
import electrodynamics.common.tile.electricitygrid.generators.TileThermoelectricGenerator;
import electrodynamics.common.tile.electricitygrid.generators.TileWindmill;
import electrodynamics.common.tile.electricitygrid.transformer.TileGenericTransformer.TileTransformer;
import electrodynamics.common.tile.machines.TileChemicalCrystallizer;
import electrodynamics.common.tile.machines.TileChemicalMixer;
import electrodynamics.common.tile.machines.TileEnergizedAlloyer;
import electrodynamics.common.tile.machines.TileFermentationPlant;
import electrodynamics.common.tile.machines.TileLathe;
import electrodynamics.common.tile.machines.TileMineralWasher;
import electrodynamics.common.tile.machines.TileOxidationFurnace;
import electrodynamics.common.tile.machines.TileReinforcedAlloyer;
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
import electrodynamics.common.tile.machines.wiremill.TileWireMill;
import electrodynamics.common.tile.machines.wiremill.TileWireMillDouble;
import electrodynamics.common.tile.machines.wiremill.TileWireMillTriple;
import electrodynamics.common.tile.pipelines.TileCreativeFluidSource;
import electrodynamics.common.tile.pipelines.TileElectricPump;
import electrodynamics.common.tile.pipelines.fluids.TileFluidPipe;
import electrodynamics.common.tile.pipelines.tank.fluid.TileFluidTankHSLA;
import electrodynamics.common.tile.pipelines.tank.fluid.TileFluidTankReinforced;
import electrodynamics.common.tile.pipelines.tank.fluid.TileFluidTankSteel;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static electrodynamics.registers.UnifiedElectrodynamicsRegister.getSafeBlock;

public class ElectrodynamicsBlockTypes {
    public static final DeferredRegister<TileEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, References.ID);

    public static final RegistryObject<TileEntityType<TileCoalGenerator>> TILE_COALGENERATOR = BLOCK_ENTITY_TYPES.register(SubtypeMachine.coalgenerator.tag(), () -> new TileEntityType<>(TileCoalGenerator::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.coalgenerator), getSafeBlock(SubtypeMachine.coalgeneratorrunning)), null));
    public static final RegistryObject<TileEntityType<TileSolarPanel>> TILE_SOLARPANEL = BLOCK_ENTITY_TYPES.register(SubtypeMachine.solarpanel.tag(), () -> new TileEntityType<>(TileSolarPanel::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.solarpanel)), null));
    public static final RegistryObject<TileEntityType<TileAdvancedSolarPanel>> TILE_ADVANCEDSOLARPANEL = BLOCK_ENTITY_TYPES.register(SubtypeMachine.advancedsolarpanel.tag(), () -> new TileEntityType<>(TileAdvancedSolarPanel::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.advancedsolarpanel)), null));

    public static final RegistryObject<TileEntityType<TileElectricFurnace>> TILE_ELECTRICFURNACE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.electricfurnace.tag(), () -> new TileEntityType<>(TileElectricFurnace::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.electricfurnace), getSafeBlock(SubtypeMachine.electricfurnacerunning)), null));
    public static final RegistryObject<TileEntityType<TileElectricFurnaceDouble>> TILE_ELECTRICFURNACEDOUBLE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.electricfurnacedouble.tag(), () -> new TileEntityType<>(TileElectricFurnaceDouble::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.electricfurnacedouble), getSafeBlock(SubtypeMachine.electricfurnacedoublerunning)), null));
    public static final RegistryObject<TileEntityType<TileElectricFurnaceTriple>> TILE_ELECTRICFURNACETRIPLE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.electricfurnacetriple.tag(), () -> new TileEntityType<>(TileElectricFurnaceTriple::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.electricfurnacetriple), getSafeBlock(SubtypeMachine.electricfurnacetriplerunning)), null));

    public static final RegistryObject<TileEntityType<TileWireMill>> TILE_WIREMILL = BLOCK_ENTITY_TYPES.register(SubtypeMachine.wiremill.tag(), () -> new TileEntityType<>(TileWireMill::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.wiremill)), null));
    public static final RegistryObject<TileEntityType<TileWireMillDouble>> TILE_WIREMILLDOUBLE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.wiremilldouble.tag(), () -> new TileEntityType<>(TileWireMillDouble::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.wiremilldouble)), null));
    public static final RegistryObject<TileEntityType<TileWireMillTriple>> TILE_WIREMILLTRIPLE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.wiremilltriple.tag(), () -> new TileEntityType<>(TileWireMillTriple::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.wiremilltriple)), null));

    public static final RegistryObject<TileEntityType<TileMineralGrinder>> TILE_MINERALGRINDER = BLOCK_ENTITY_TYPES.register(SubtypeMachine.mineralgrinder.tag(), () -> new TileEntityType<>(TileMineralGrinder::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.mineralgrinder)), null));
    public static final RegistryObject<TileEntityType<TileMineralGrinderDouble>> TILE_MINERALGRINDERDOUBLE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.mineralgrinderdouble.tag(), () -> new TileEntityType<>(TileMineralGrinderDouble::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.mineralgrinderdouble)), null));
    public static final RegistryObject<TileEntityType<TileMineralGrinderTriple>> TILE_MINERALGRINDERTRIPLE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.mineralgrindertriple.tag(), () -> new TileEntityType<>(TileMineralGrinderTriple::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.mineralgrindertriple)), null));

    public static final RegistryObject<TileEntityType<TileMineralCrusher>> TILE_MINERALCRUSHER = BLOCK_ENTITY_TYPES.register(SubtypeMachine.mineralcrusher.tag(), () -> new TileEntityType<>(TileMineralCrusher::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.mineralcrusher)), null));
    public static final RegistryObject<TileEntityType<TileMineralCrusherDouble>> TILE_MINERALCRUSHERDOUBLE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.mineralcrusherdouble.tag(), () -> new TileEntityType<>(TileMineralCrusherDouble::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.mineralcrusherdouble)), null));
    public static final RegistryObject<TileEntityType<TileMineralCrusherTriple>> TILE_MINERALCRUSHERTRIPLE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.mineralcrushertriple.tag(), () -> new TileEntityType<>(TileMineralCrusherTriple::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.mineralcrushertriple)), null));

    public static final RegistryObject<TileEntityType<TileBatteryBox>> TILE_BATTERYBOX = BLOCK_ENTITY_TYPES.register(SubtypeMachine.batterybox.tag(), () -> new TileEntityType<>(TileBatteryBox::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.batterybox)), null));
    public static final RegistryObject<TileEntityType<TileLithiumBatteryBox>> TILE_LITHIUMBATTERYBOX = BLOCK_ENTITY_TYPES.register(SubtypeMachine.lithiumbatterybox.tag(), () -> new TileEntityType<>(TileLithiumBatteryBox::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.lithiumbatterybox)), null));

    public static final RegistryObject<TileEntityType<TileChargerLV>> TILE_CHARGERLV = BLOCK_ENTITY_TYPES.register(SubtypeMachine.chargerlv.tag(), () -> new TileEntityType<>(TileChargerLV::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.chargerlv)), null));
    public static final RegistryObject<TileEntityType<TileChargerMV>> TILE_CHARGERMV = BLOCK_ENTITY_TYPES.register(SubtypeMachine.chargermv.tag(), () -> new TileEntityType<>(TileChargerMV::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.chargermv)), null));
    public static final RegistryObject<TileEntityType<TileChargerHV>> TILE_CHARGERHV = BLOCK_ENTITY_TYPES.register(SubtypeMachine.chargerhv.tag(), () -> new TileEntityType<>(TileChargerHV::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.chargerhv)), null));

    public static final RegistryObject<TileEntityType<TileFluidTankSteel>> TILE_TANKSTEEL = BLOCK_ENTITY_TYPES.register(SubtypeMachine.tanksteel.tag(), () -> new TileEntityType<>(TileFluidTankSteel::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.tanksteel)), null));
    public static final RegistryObject<TileEntityType<TileFluidTankReinforced>> TILE_TANKREINFORCED = BLOCK_ENTITY_TYPES.register(SubtypeMachine.tankreinforced.tag(), () -> new TileEntityType<>(TileFluidTankReinforced::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.tankreinforced)), null));
    public static final RegistryObject<TileEntityType<TileFluidTankHSLA>> TILE_TANKHSLA = BLOCK_ENTITY_TYPES.register(SubtypeMachine.tankhsla.tag(), () -> new TileEntityType<>(TileFluidTankHSLA::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.tankhsla)), null));

    public static final RegistryObject<TileEntityType<TileTransformer>> TILE_TRANSFORMER = BLOCK_ENTITY_TYPES.register("transformer", () -> new TileEntityType<>(TileTransformer::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.downgradetransformer), getSafeBlock(SubtypeMachine.upgradetransformer)), null));
    
    public static final RegistryObject<TileEntityType<TileEnergizedAlloyer>> TILE_ENERGIZEDALLOYER = BLOCK_ENTITY_TYPES.register(SubtypeMachine.energizedalloyer.tag(), () -> new TileEntityType<>(TileEnergizedAlloyer::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.energizedalloyer), getSafeBlock(SubtypeMachine.energizedalloyerrunning)), null));
    
    public static final RegistryObject<TileEntityType<TileLathe>> TILE_LATHE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.lathe.tag(), () -> new TileEntityType<>(TileLathe::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.lathe)), null));
    public static final RegistryObject<TileEntityType<TileReinforcedAlloyer>> TILE_REINFORCEDALLOYER = BLOCK_ENTITY_TYPES.register(SubtypeMachine.reinforcedalloyer.tag(), () -> new TileEntityType<>(TileReinforcedAlloyer::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.reinforcedalloyer), getSafeBlock(SubtypeMachine.reinforcedalloyerrunning)), null));
    public static final RegistryObject<TileEntityType<TileOxidationFurnace>> TILE_OXIDATIONFURNACE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.oxidationfurnace.tag(), () -> new TileEntityType<>(TileOxidationFurnace::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.oxidationfurnace), getSafeBlock(SubtypeMachine.oxidationfurnacerunning)), null));
    
    public static final RegistryObject<TileEntityType<TileCreativePowerSource>> TILE_CREATIVEPOWERSOURCE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.creativepowersource.tag(), () -> new TileEntityType<>(TileCreativePowerSource::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.creativepowersource)), null));
    public static final RegistryObject<TileEntityType<TileElectricPump>> TILE_ELECTRICPUMP = BLOCK_ENTITY_TYPES.register(SubtypeMachine.electricpump.tag(), () -> new TileEntityType<>(TileElectricPump::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.electricpump)), null));
    public static final RegistryObject<TileEntityType<TileThermoelectricGenerator>> TILE_THERMOELECTRICGENERATOR = BLOCK_ENTITY_TYPES.register(SubtypeMachine.thermoelectricgenerator.tag(), () -> new TileEntityType<>(TileThermoelectricGenerator::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.thermoelectricgenerator)), null));
    public static final RegistryObject<TileEntityType<TileHydroelectricGenerator>> TILE_HYDROELECTRICGENERATOR = BLOCK_ENTITY_TYPES.register(SubtypeMachine.hydroelectricgenerator.tag(), () -> new TileEntityType<>(TileHydroelectricGenerator::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.hydroelectricgenerator)), null));
    public static final RegistryObject<TileEntityType<TileWindmill>> TILE_WINDMILL = BLOCK_ENTITY_TYPES.register(SubtypeMachine.windmill.tag(), () -> new TileEntityType<>(TileWindmill::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.windmill)), null));
    public static final RegistryObject<TileEntityType<TileFermentationPlant>> TILE_FERMENTATIONPLANT = BLOCK_ENTITY_TYPES.register(SubtypeMachine.fermentationplant.tag(), () -> new TileEntityType<>(TileFermentationPlant::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.fermentationplant)), null));
    public static final RegistryObject<TileEntityType<TileCombustionChamber>> TILE_COMBUSTIONCHAMBER = BLOCK_ENTITY_TYPES.register(SubtypeMachine.combustionchamber.tag(), () -> new TileEntityType<>(TileCombustionChamber::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.combustionchamber)), null));
    public static final RegistryObject<TileEntityType<TileMineralWasher>> TILE_MINERALWASHER = BLOCK_ENTITY_TYPES.register(SubtypeMachine.mineralwasher.tag(), () -> new TileEntityType<>(TileMineralWasher::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.mineralwasher)), null));
    public static final RegistryObject<TileEntityType<TileChemicalMixer>> TILE_CHEMICALMIXER = BLOCK_ENTITY_TYPES.register(SubtypeMachine.chemicalmixer.tag(), () -> new TileEntityType<>(TileChemicalMixer::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.chemicalmixer)), null));
    public static final RegistryObject<TileEntityType<TileChemicalCrystallizer>> TILE_CHEMICALCRYSTALLIZER = BLOCK_ENTITY_TYPES.register(SubtypeMachine.chemicalcrystallizer.tag(), () -> new TileEntityType<>(TileChemicalCrystallizer::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.chemicalcrystallizer)), null));
    public static final RegistryObject<TileEntityType<TileCircuitBreaker>> TILE_CIRCUITBREAKER = BLOCK_ENTITY_TYPES.register(SubtypeMachine.circuitbreaker.tag(), () -> new TileEntityType<>(TileCircuitBreaker::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.circuitbreaker)), null));
    public static final RegistryObject<TileEntityType<TileMultimeterBlock>> TILE_MULTIMETERBLOCK = BLOCK_ENTITY_TYPES.register(SubtypeMachine.multimeterblock.tag(), () -> new TileEntityType<>(TileMultimeterBlock::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.multimeterblock)), null));
    public static final RegistryObject<TileEntityType<TileMultiSubnode>> TILE_MULTI = BLOCK_ENTITY_TYPES.register("multisubnode", () -> new TileEntityType<>(TileMultiSubnode::new, Sets.newHashSet(ElectrodynamicsBlocks.multi), null));
    public static final RegistryObject<TileEntityType<TileWire>> TILE_WIRE = BLOCK_ENTITY_TYPES.register("wiregenerictile", () -> new TileEntityType<>(TileWire::new, BlockWire.WIRES, null));
    public static final RegistryObject<TileEntityType<TileLogisticalWire>> TILE_LOGISTICALWIRE = BLOCK_ENTITY_TYPES.register("wirelogisticaltile", () -> new TileEntityType<>(TileLogisticalWire::new, BlockLogisticalWire.WIRES, null));
    public static final RegistryObject<TileEntityType<TileFluidPipe>> TILE_PIPE = BLOCK_ENTITY_TYPES.register("pipegenerictile", () -> new TileEntityType<>(TileFluidPipe::new, BlockFluidPipe.PIPESET, null));
    public static final RegistryObject<TileEntityType<TileCreativeFluidSource>> TILE_CREATIVEFLUIDSOURCE = BLOCK_ENTITY_TYPES.register(SubtypeMachine.creativefluidsource.tag(), () -> new TileEntityType<>(TileCreativeFluidSource::new, Sets.newHashSet(getSafeBlock(SubtypeMachine.creativefluidsource)), null));
    
}
