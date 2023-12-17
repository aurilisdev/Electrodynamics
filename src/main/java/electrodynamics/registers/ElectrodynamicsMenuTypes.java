package electrodynamics.registers;

import electrodynamics.api.References;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.item.ContainerGuidebook;
import electrodynamics.common.inventory.container.tile.ContainerBatteryBox;
import electrodynamics.common.inventory.container.tile.ContainerChargerGeneric;
import electrodynamics.common.inventory.container.tile.ContainerChemicalCrystallizer;
import electrodynamics.common.inventory.container.tile.ContainerChemicalMixer;
import electrodynamics.common.inventory.container.tile.ContainerCoalGenerator;
import electrodynamics.common.inventory.container.tile.ContainerCombustionChamber;
import electrodynamics.common.inventory.container.tile.ContainerCreativeFluidSource;
import electrodynamics.common.inventory.container.tile.ContainerCreativePowerSource;
import electrodynamics.common.inventory.container.tile.ContainerDO2OProcessor;
import electrodynamics.common.inventory.container.tile.ContainerElectricFurnace;
import electrodynamics.common.inventory.container.tile.ContainerElectricFurnaceDouble;
import electrodynamics.common.inventory.container.tile.ContainerElectricFurnaceTriple;
import electrodynamics.common.inventory.container.tile.ContainerFermentationPlant;
import electrodynamics.common.inventory.container.tile.ContainerFluidTankGeneric;
import electrodynamics.common.inventory.container.tile.ContainerHydroelectricGenerator;
import electrodynamics.common.inventory.container.tile.ContainerMineralWasher;
import electrodynamics.common.inventory.container.tile.ContainerO2OProcessor;
import electrodynamics.common.inventory.container.tile.ContainerO2OProcessorDouble;
import electrodynamics.common.inventory.container.tile.ContainerO2OProcessorTriple;
import electrodynamics.common.inventory.container.tile.ContainerSolarPanel;
import electrodynamics.common.inventory.container.tile.ContainerWindmill;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ElectrodynamicsMenuTypes {
	
	public static final DeferredRegister<ContainerType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, References.ID);

	public static final RegistryObject<ContainerType<ContainerCoalGenerator>> CONTAINER_COALGENERATOR = MENU_TYPES.register(SubtypeMachine.coalgenerator.tag(), () -> new ContainerType<>(ContainerCoalGenerator::new));
	public static final RegistryObject<ContainerType<ContainerElectricFurnace>> CONTAINER_ELECTRICFURNACE = MENU_TYPES.register(SubtypeMachine.electricfurnace.tag(), () -> new ContainerType<>(ContainerElectricFurnace::new));
	public static final RegistryObject<ContainerType<ContainerElectricFurnaceDouble>> CONTAINER_ELECTRICFURNACEDOUBLE = MENU_TYPES.register(SubtypeMachine.electricfurnacedouble.tag(), () -> new ContainerType<>(ContainerElectricFurnaceDouble::new));
	public static final RegistryObject<ContainerType<ContainerElectricFurnaceTriple>> CONTAINER_ELECTRICFURNACETRIPLE = MENU_TYPES.register(SubtypeMachine.electricfurnacetriple.tag(), () -> new ContainerType<>(ContainerElectricFurnaceTriple::new));
	public static final RegistryObject<ContainerType<ContainerO2OProcessor>> CONTAINER_O2OPROCESSOR = MENU_TYPES.register("o2oprocessor", () -> new ContainerType<>(ContainerO2OProcessor::new));
	public static final RegistryObject<ContainerType<ContainerO2OProcessorDouble>> CONTAINER_O2OPROCESSORDOUBLE = MENU_TYPES.register("o2oprocessordouble", () -> new ContainerType<>(ContainerO2OProcessorDouble::new));
	public static final RegistryObject<ContainerType<ContainerO2OProcessorTriple>> CONTAINER_O2OPROCESSORTRIPLE = MENU_TYPES.register("o2oprocessortriple", () -> new ContainerType<>(ContainerO2OProcessorTriple::new));
	public static final RegistryObject<ContainerType<ContainerDO2OProcessor>> CONTAINER_DO2OPROCESSOR = MENU_TYPES.register("do2oprocessor", () -> new ContainerType<>(ContainerDO2OProcessor::new));
	public static final RegistryObject<ContainerType<ContainerBatteryBox>> CONTAINER_BATTERYBOX = MENU_TYPES.register(SubtypeMachine.batterybox.tag(), () -> new ContainerType<>(ContainerBatteryBox::new));
	public static final RegistryObject<ContainerType<ContainerFermentationPlant>> CONTAINER_FERMENTATIONPLANT = MENU_TYPES.register(SubtypeMachine.fermentationplant.tag(), () -> new ContainerType<>(ContainerFermentationPlant::new));
	public static final RegistryObject<ContainerType<ContainerMineralWasher>> CONTAINER_MINERALWASHER = MENU_TYPES.register(SubtypeMachine.mineralwasher.tag(), () -> new ContainerType<>(ContainerMineralWasher::new));
	public static final RegistryObject<ContainerType<ContainerChemicalMixer>> CONTAINER_CHEMICALMIXER = MENU_TYPES.register(SubtypeMachine.chemicalmixer.tag(), () -> new ContainerType<>(ContainerChemicalMixer::new));
	public static final RegistryObject<ContainerType<ContainerChemicalCrystallizer>> CONTAINER_CHEMICALCRYSTALLIZER = MENU_TYPES.register(SubtypeMachine.chemicalcrystallizer.tag(), () -> new ContainerType<>(ContainerChemicalCrystallizer::new));
	public static final RegistryObject<ContainerType<ContainerChargerGeneric>> CONTAINER_CHARGER = MENU_TYPES.register("genericcharger", () -> new ContainerType<>(ContainerChargerGeneric::new));
	public static final RegistryObject<ContainerType<ContainerFluidTankGeneric>> CONTAINER_TANK = MENU_TYPES.register("generictank", () -> new ContainerType<>(ContainerFluidTankGeneric::new));
	public static final RegistryObject<ContainerType<ContainerCombustionChamber>> CONTAINER_COMBUSTION_CHAMBER = MENU_TYPES.register("combustionchamber", () -> new ContainerType<>(ContainerCombustionChamber::new));
	public static final RegistryObject<ContainerType<ContainerSolarPanel>> CONTAINER_SOLARPANEL = MENU_TYPES.register("solarpanel", () -> new ContainerType<>(ContainerSolarPanel::new));
	public static final RegistryObject<ContainerType<ContainerWindmill>> CONTAINER_WINDMILL = MENU_TYPES.register("windmill", () -> new ContainerType<>(ContainerWindmill::new));
	public static final RegistryObject<ContainerType<ContainerHydroelectricGenerator>> CONTAINER_HYDROELECTRICGENERATOR = MENU_TYPES.register("hydroelectricgenerator", () -> new ContainerType<>(ContainerHydroelectricGenerator::new));
	public static final RegistryObject<ContainerType<ContainerCreativePowerSource>> CONTAINER_CREATIVEPOWERSOURCE = MENU_TYPES.register("creativepowersource", () -> new ContainerType<>(ContainerCreativePowerSource::new));
	public static final RegistryObject<ContainerType<ContainerCreativeFluidSource>> CONTAINER_CREATIVEFLUIDSOURCE = MENU_TYPES.register("creativefluidsource", () -> new ContainerType<>(ContainerCreativeFluidSource::new));
	public static final RegistryObject<ContainerType<ContainerGuidebook>> CONTAINER_GUIDEBOOK = MENU_TYPES.register("guidebook", () -> new ContainerType<>(ContainerGuidebook::new));

}
