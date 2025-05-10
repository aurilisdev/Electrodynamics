package electrodynamics.registers;

import electrodynamics.Electrodynamics;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.item.ContainerElectricDrill;
import electrodynamics.common.inventory.container.item.ContainerSeismicScanner;
import electrodynamics.common.inventory.container.tile.ContainerAdvancedDowngradeTransformer;
import electrodynamics.common.inventory.container.tile.ContainerAdvancedUpgradeTransformer;
import electrodynamics.common.inventory.container.tile.ContainerBatteryBox;
import electrodynamics.common.inventory.container.tile.ContainerChargerGeneric;
import electrodynamics.common.inventory.container.tile.ContainerChemicalCrystallizer;
import electrodynamics.common.inventory.container.tile.ContainerChemicalMixer;
import electrodynamics.common.inventory.container.tile.ContainerCircuitMonitor;
import electrodynamics.common.inventory.container.tile.ContainerCoalGenerator;
import electrodynamics.common.inventory.container.tile.ContainerCobblestoneGenerator;
import electrodynamics.common.inventory.container.tile.ContainerCombustionChamber;
import electrodynamics.common.inventory.container.tile.ContainerCoolantResavoir;
import electrodynamics.common.inventory.container.tile.ContainerCreativeFluidSource;
import electrodynamics.common.inventory.container.tile.ContainerCreativePowerSource;
import electrodynamics.common.inventory.container.tile.ContainerElectricArcFurnace;
import electrodynamics.common.inventory.container.tile.ContainerElectricArcFurnaceDouble;
import electrodynamics.common.inventory.container.tile.ContainerElectricArcFurnaceTriple;
import electrodynamics.common.inventory.container.tile.ContainerElectricFurnace;
import electrodynamics.common.inventory.container.tile.ContainerElectricFurnaceDouble;
import electrodynamics.common.inventory.container.tile.ContainerElectricFurnaceTriple;
import electrodynamics.common.inventory.container.tile.ContainerElectrolyticSeparator;
import electrodynamics.common.inventory.container.tile.ContainerFermentationPlant;
import electrodynamics.common.inventory.container.tile.ContainerFluidPipeFilter;
import electrodynamics.common.inventory.container.tile.ContainerFluidPipePump;
import electrodynamics.common.inventory.container.tile.ContainerFluidVoid;
import electrodynamics.common.inventory.container.tile.ContainerHydroelectricGenerator;
import electrodynamics.common.inventory.container.tile.ContainerMineralWasher;
import electrodynamics.common.inventory.container.tile.ContainerMotorComplex;
import electrodynamics.common.inventory.container.tile.ContainerPotentiometer;
import electrodynamics.common.inventory.container.tile.ContainerQuarry;
import electrodynamics.common.inventory.container.tile.ContainerSeismicRelay;
import electrodynamics.common.inventory.container.tile.ContainerSolarPanel;
import electrodynamics.common.inventory.container.tile.ContainerFluidTankGeneric;
import electrodynamics.common.inventory.container.tile.ContainerWindmill;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ElectrodynamicsMenuTypes {
	
	public static final DeferredRegister<ContainerType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, Electrodynamics.ID);

	public static final RegistryObject<ContainerType<ContainerCoalGenerator>> CONTAINER_COALGENERATOR = register(SubtypeMachine.coalgenerator.tag(), ContainerCoalGenerator::new);
	public static final RegistryObject<ContainerType<ContainerElectricFurnace>> CONTAINER_ELECTRICFURNACE = register(SubtypeMachine.electricfurnace.tag(), ContainerElectricFurnace::new);
	public static final RegistryObject<ContainerType<ContainerElectricFurnaceDouble>> CONTAINER_ELECTRICFURNACEDOUBLE = register(SubtypeMachine.electricfurnacedouble.tag(), ContainerElectricFurnaceDouble::new);
	public static final RegistryObject<ContainerType<ContainerElectricFurnaceTriple>> CONTAINER_ELECTRICFURNACETRIPLE = register(SubtypeMachine.electricfurnacetriple.tag(), ContainerElectricFurnaceTriple::new);
	public static final RegistryObject<ContainerType<ContainerElectricArcFurnace>> CONTAINER_ELECTRICARCFURNACE = register(SubtypeMachine.electricarcfurnace.tag(), ContainerElectricArcFurnace::new);
	public static final RegistryObject<ContainerType<ContainerElectricArcFurnaceDouble>> CONTAINER_ELECTRICARCFURNACEDOUBLE = register(SubtypeMachine.electricarcfurnacedouble.tag(), ContainerElectricArcFurnaceDouble::new);
	public static final RegistryObject<ContainerType<ContainerElectricArcFurnaceTriple>> CONTAINER_ELECTRICARCFURNACETRIPLE = register(SubtypeMachine.electricarcfurnacetriple.tag(), ContainerElectricArcFurnaceTriple::new);
	public static final RegistryObject<ContainerType<ContainerBatteryBox>> CONTAINER_BATTERYBOX = register(SubtypeMachine.batterybox.tag(), ContainerBatteryBox::new);
	public static final RegistryObject<ContainerType<ContainerFermentationPlant>> CONTAINER_FERMENTATIONPLANT = register(SubtypeMachine.fermentationplant.tag(), ContainerFermentationPlant::new);
	public static final RegistryObject<ContainerType<ContainerMineralWasher>> CONTAINER_MINERALWASHER = register(SubtypeMachine.mineralwasher.tag(), ContainerMineralWasher::new);
	public static final RegistryObject<ContainerType<ContainerChemicalMixer>> CONTAINER_CHEMICALMIXER = register(SubtypeMachine.chemicalmixer.tag(), ContainerChemicalMixer::new);
	public static final RegistryObject<ContainerType<ContainerChemicalCrystallizer>> CONTAINER_CHEMICALCRYSTALLIZER = register(SubtypeMachine.chemicalcrystallizer.tag(), ContainerChemicalCrystallizer::new);
	public static final RegistryObject<ContainerType<ContainerChargerGeneric>> CONTAINER_CHARGER = register("genericcharger", ContainerChargerGeneric::new);
	public static final RegistryObject<ContainerType<ContainerFluidTankGeneric>> CONTAINER_TANK = register("generictank", ContainerFluidTankGeneric::new);
	public static final RegistryObject<ContainerType<ContainerCombustionChamber>> CONTAINER_COMBUSTION_CHAMBER = register("combustionchamber", ContainerCombustionChamber::new);
	public static final RegistryObject<ContainerType<ContainerSolarPanel>> CONTAINER_SOLARPANEL = register("solarpanel", ContainerSolarPanel::new);
	public static final RegistryObject<ContainerType<ContainerWindmill>> CONTAINER_WINDMILL = register("windmill", ContainerWindmill::new);
	public static final RegistryObject<ContainerType<ContainerHydroelectricGenerator>> CONTAINER_HYDROELECTRICGENERATOR = register("hydroelectricgenerator", ContainerHydroelectricGenerator::new);
	public static final RegistryObject<ContainerType<ContainerCreativePowerSource>> CONTAINER_CREATIVEPOWERSOURCE = register("creativepowersource", ContainerCreativePowerSource::new);
	public static final RegistryObject<ContainerType<ContainerCreativeFluidSource>> CONTAINER_CREATIVEFLUIDSOURCE = register("creativefluidsource", ContainerCreativeFluidSource::new);
	public static final RegistryObject<ContainerType<ContainerFluidVoid>> CONTAINER_FLUIDVOID = register("fluidvoid", ContainerFluidVoid::new);
	public static final RegistryObject<ContainerType<ContainerSeismicScanner>> CONTAINER_SEISMICSCANNER = register("seismicdetector", ContainerSeismicScanner::new);
	public static final RegistryObject<ContainerType<ContainerElectrolyticSeparator>> CONTAINER_ELECTROLYTICSEPARATOR = register("electrolyticseparator", ContainerElectrolyticSeparator::new);
	public static final RegistryObject<ContainerType<ContainerSeismicRelay>> CONTAINER_SEISMICRELAY = register("seismicrelay", ContainerSeismicRelay::new);
	public static final RegistryObject<ContainerType<ContainerCoolantResavoir>> CONTAINER_COOLANTRESAVOIR = register("coolantresavoir", ContainerCoolantResavoir::new);
	public static final RegistryObject<ContainerType<ContainerMotorComplex>> CONTAINER_MOTORCOMPLEX = register("motorcomplex", ContainerMotorComplex::new);
	public static final RegistryObject<ContainerType<ContainerQuarry>> CONTAINER_QUARRY = register("quarry", ContainerQuarry::new);
	public static final RegistryObject<ContainerType<ContainerCobblestoneGenerator>> CONTAINER_COBBLESTONEGENERATOR = MENU_TYPES.register("cobblestonegenerator", () -> new ContainerType<>(ContainerCobblestoneGenerator::new));
	public static final RegistryObject<ContainerType<ContainerFluidPipePump>> CONTAINER_FLUIDPIPEPUMP = register("fluidpipepump", ContainerFluidPipePump::new);
	public static final RegistryObject<ContainerType<ContainerFluidPipeFilter>> CONTAINER_FLUIDPIPEFILTER = register("fluidpipefilter", ContainerFluidPipeFilter::new);
	public static final RegistryObject<ContainerType<ContainerElectricDrill>> CONTAINER_ELECTRICDRILL = register("electricdrill", ContainerElectricDrill::new);
	public static final RegistryObject<ContainerType<ContainerPotentiometer>> CONTAINER_POTENTIOMETER = register("potentiometer", ContainerPotentiometer::new);
	public static final RegistryObject<ContainerType<ContainerAdvancedUpgradeTransformer>> CONTAINER_ADVANCEDUPGRADETRANSFORMER = register("advancedupgradetransformer", ContainerAdvancedUpgradeTransformer::new);
	public static final RegistryObject<ContainerType<ContainerAdvancedDowngradeTransformer>> CONTAINER_ADVANCEDDOWNGRADETRANSFORMER = register("advanceddowngradetransformer", ContainerAdvancedDowngradeTransformer::new);
	public static final RegistryObject<ContainerType<ContainerCircuitMonitor>> CONTAINER_CIRCUITMONITOR = register("circuitmonitor", ContainerCircuitMonitor::new);
	
	private static <T extends Container> RegistryObject<ContainerType<T>> register(String id, ContainerType.IFactory<T> supplier) {
		return MENU_TYPES.register(id, () -> new ContainerType<>(supplier));
	}

}
