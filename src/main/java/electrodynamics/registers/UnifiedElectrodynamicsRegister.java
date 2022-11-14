package electrodynamics.registers;

import java.util.function.Supplier;

import electrodynamics.api.ISubtype;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.blockitem.BlockItemDescriptable;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;

public class UnifiedElectrodynamicsRegister {

	public static void register(IEventBus bus) {
		ElectrodynamicsBlocks.BLOCKS.register(bus);
		ElectrodynamicsBlockTypes.BLOCK_ENTITY_TYPES.register(bus);
		ElectrodynamicsItems.ITEMS.register(bus);
		ElectrodynamicsFluids.FLUIDS.register(bus);
		ElectrodynamicsFluidTypes.FLUID_TYPES.register(bus);
		ElectrodynamicsEntities.ENTITIES.register(bus);
		ElectrodynamicsFeatures.CONFIGURED_FEATURES.register(bus);
		ElectrodynamicsFeatures.PLACED_FEATURES.register(bus);
		ElectrodynamicsMenuTypes.MENU_TYPES.register(bus);
		ElectrodynamicsSounds.SOUNDS.register(bus);
	}

	static {
		// machines
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.electricfurnace), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.electricfurnacedouble), "|translate|tooltip.machine.voltage.240");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.electricfurnacetriple), "|translate|tooltip.machine.voltage.480");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.wiremill), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.wiremilldouble), "|translate|tooltip.machine.voltage.240");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.wiremilltriple), "|translate|tooltip.machine.voltage.480");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.mineralcrusher), "|translate|tooltip.machine.voltage.240");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.mineralcrusherdouble), "|translate|tooltip.machine.voltage.480");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.mineralcrushertriple), "|translate|tooltip.machine.voltage.960");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.mineralgrinder), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.mineralgrinderdouble), "|translate|tooltip.machine.voltage.240");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.mineralgrindertriple), "|translate|tooltip.machine.voltage.480");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.oxidationfurnace), "|translate|tooltip.machine.voltage.240");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.mineralwasher), "|translate|tooltip.machine.voltage.480");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.chemicalmixer), "|translate|tooltip.machine.voltage.240");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.chemicalcrystallizer), "|translate|tooltip.machine.voltage.240");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.energizedalloyer), "|translate|tooltip.machine.voltage.480");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.reinforcedalloyer), "|translate|tooltip.machine.voltage.960");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.lathe), "|translate|tooltip.machine.voltage.240");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.chargerlv), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.chargermv), "|translate|tooltip.machine.voltage.240");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.chargerhv), "|translate|tooltip.machine.voltage.480");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.fermentationplant), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.electricpump), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.electrolyticseparator), "|translate|tooltip.machine.voltage.240");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.electricarcfurnace), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.electricarcfurnacedouble), "|translate|tooltip.machine.voltage.240");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.electricarcfurnacetriple), "|translate|tooltip.machine.voltage.480");

		// generators
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.solarpanel), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.advancedsolarpanel), "|translate|tooltip.machine.voltage.240");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.thermoelectricgenerator), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.combustionchamber), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.hydroelectricgenerator), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.windmill), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.coalgenerator), "|translate|tooltip.machine.voltage.120");

		// misc
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.downgradetransformer), "|translate|tooltip.transformer.energyloss");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.upgradetransformer), "|translate|tooltip.transformer.energyloss");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.batterybox), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.lithiumbatterybox), "|translate|tooltip.machine.voltage.240");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.carbynebatterybox), "|translate|tooltip.machine.voltage.480");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.creativepowersource), "|translate|tooltip.creativepowersource.joke");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.creativefluidsource), "|translate|tooltip.creativefluidsource.joke");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.fluidvoid), "|translate|tooltip.fluidvoid");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.tanksteel), "|translate|tooltip.tanksteel.capacity");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.tankreinforced), "|translate|tooltip.tankreinforced.capacity");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.tankhsla), "|translate|tooltip.tankhsla.capacity");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.blockSeismicMarker, "|translate|tooltip.seismicmarker.redstone");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.seismicrelay), "|translate|tooltip.seismicrelay.use");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.coolantresavoir), "|translate|tooltip.coolantresavoir.place");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.motorcomplex), "|translate|tooltip.motorcomplex.use");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.blockFrame, "|translate|tooltip.blockframe.joke");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.blockFrameCorner, "|translate|tooltip.blockframe.joke");
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.quarry), "|translate|tooltip.quarry.power");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.blockLogisticalManager, "|translate|tooltip.logisticalmanager.use");
	}

	public static <T> Supplier<? extends T> supplier(Supplier<? extends T> entry) {
		return entry;
	}

	public static <T> Supplier<? extends T> supplier(Supplier<? extends T> entry, ISubtype en) {
		return entry;
	}

	public static Block getSafeBlock(ISubtype type) {
		return ElectrodynamicsBlocks.SUBTYPEBLOCKREGISTER_MAPPINGS.get(type).get();
	}

}
