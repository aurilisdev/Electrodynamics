package electrodynamics.registers;

import java.util.function.Supplier;

import electrodynamics.SoundRegister;
import electrodynamics.api.ISubtype;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.blockitem.BlockItemDescriptable;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;

public class ElectrodynamicsRegisters {

	public static void register(IEventBus bus) {
		SoundRegister.SOUNDS.register(bus);
		ElectrodynamicsBlocks.BLOCKS.register(bus);
		ElectrodynamicsBlockTypes.BLOCK_ENTITY_TYPES.register(bus);
		ElectrodynamicsItems.ITEMS.register(bus);
		ElectrodynamicsFluids.FLUIDS.register(bus);
		ElectrodynamicsFluidTypes.FLUID_TYPES.register(bus);
		ElectrodynamicsEntities.ENTITIES.register(bus);
		ElectrodynamicsFeatures.CONFIGURED_FEATURES.register(bus);
		ElectrodynamicsFeatures.PLACED_FEATURES.register(bus);
		ElectrodynamicsMenuTypes.MENU_TYPES.register(bus);

	}

	static {
		// machines
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.electricfurnace), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.electricfurnacedouble), "|translate|tooltip.machine.voltage.240");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.electricfurnacetriple), "|translate|tooltip.machine.voltage.480");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.wiremill), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.wiremilldouble), "|translate|tooltip.machine.voltage.240");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.wiremilltriple), "|translate|tooltip.machine.voltage.480");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.mineralcrusher), "|translate|tooltip.machine.voltage.240");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.mineralcrusherdouble), "|translate|tooltip.machine.voltage.480");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.mineralcrushertriple), "|translate|tooltip.machine.voltage.960");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.mineralgrinder), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.mineralgrinderdouble), "|translate|tooltip.machine.voltage.240");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.mineralgrindertriple), "|translate|tooltip.machine.voltage.480");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.oxidationfurnace), "|translate|tooltip.machine.voltage.240");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.mineralwasher), "|translate|tooltip.machine.voltage.480");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.chemicalmixer), "|translate|tooltip.machine.voltage.240");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.chemicalcrystallizer), "|translate|tooltip.machine.voltage.240");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.energizedalloyer), "|translate|tooltip.machine.voltage.480");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.reinforcedalloyer), "|translate|tooltip.machine.voltage.960");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.lathe), "|translate|tooltip.machine.voltage.240");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.chargerlv), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.chargermv), "|translate|tooltip.machine.voltage.240");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.chargerhv), "|translate|tooltip.machine.voltage.480");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.fermentationplant), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.electricpump), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.cobblestonegenerator), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.electrolyticseparator), "|translate|tooltip.machine.voltage.240");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.electricarcfurnace), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.electricarcfurnacedouble), "|translate|tooltip.machine.voltage.240");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.electricarcfurnacetriple), "|translate|tooltip.machine.voltage.480");

		// generators
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.solarpanel), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.advancedsolarpanel), "|translate|tooltip.machine.voltage.240");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.thermoelectricgenerator), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.combustionchamber), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.hydroelectricgenerator), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.windmill), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.coalgenerator), "|translate|tooltip.machine.voltage.120");

		// misc
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.downgradetransformer), "|translate|tooltip.transformer.energyloss");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.upgradetransformer), "|translate|tooltip.transformer.energyloss");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.batterybox), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.lithiumbatterybox), "|translate|tooltip.machine.voltage.240");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.carbynebatterybox), "|translate|tooltip.machine.voltage.480");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.creativepowersource), "|translate|tooltip.creativepowersource.joke");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.creativefluidsource), "|translate|tooltip.creativefluidsource.joke");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.fluidvoid), "|translate|tooltip.fluidvoid");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.tanksteel), "|translate|tooltip.tanksteel.capacity");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.tankreinforced), "|translate|tooltip.tankreinforced.capacity");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.tankhsla), "|translate|tooltip.tankhsla.capacity");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.blockSeismicMarker, "|translate|tooltip.seismicmarker.redstone");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.seismicrelay), "|translate|tooltip.seismicrelay.use");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.coolantresavoir), "|translate|tooltip.coolantresavoir.place");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.motorcomplex), "|translate|tooltip.motorcomplex.use");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.blockFrame, "|translate|tooltip.blockframe.joke");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.blockFrameCorner, "|translate|tooltip.blockframe.joke");
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.quarry), "|translate|tooltip.quarry.power");
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
