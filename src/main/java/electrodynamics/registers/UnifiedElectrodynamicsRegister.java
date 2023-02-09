package electrodynamics.registers;

import java.util.function.Supplier;

import electrodynamics.api.ISubtype;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.blockitem.BlockItemDescriptable;
import electrodynamics.prefab.utilities.TextUtils;
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
		// cleaner and simpler is it not?
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.electricfurnace), TextUtils.tooltip("machine.voltage.120"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.electricfurnacedouble), TextUtils.tooltip("machine.voltage.240"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.electricfurnacetriple), TextUtils.tooltip("machine.voltage.480"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.wiremill), TextUtils.tooltip("machine.voltage.120"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.wiremilldouble), TextUtils.tooltip("machine.voltage.240"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.wiremilltriple), TextUtils.tooltip("machine.voltage.480"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.mineralcrusher), TextUtils.tooltip("machine.voltage.240"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.mineralcrusherdouble), TextUtils.tooltip("machine.voltage.480"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.mineralcrushertriple), TextUtils.tooltip("machine.voltage.960"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.mineralgrinder), TextUtils.tooltip("machine.voltage.120"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.mineralgrinderdouble), TextUtils.tooltip("machine.voltage.240"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.mineralgrindertriple), TextUtils.tooltip("machine.voltage.480"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.oxidationfurnace), TextUtils.tooltip("machine.voltage.240"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.mineralwasher), TextUtils.tooltip("machine.voltage.480"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.chemicalmixer), TextUtils.tooltip("machine.voltage.240"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.chemicalcrystallizer), TextUtils.tooltip("machine.voltage.240"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.energizedalloyer), TextUtils.tooltip("machine.voltage.480"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.reinforcedalloyer), TextUtils.tooltip("machine.voltage.960"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.lathe), TextUtils.tooltip("machine.voltage.240"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.chargerlv), TextUtils.tooltip("machine.voltage.120"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.chargermv), TextUtils.tooltip("machine.voltage.240"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.chargerhv), TextUtils.tooltip("machine.voltage.480"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.fermentationplant), TextUtils.tooltip("machine.voltage.120"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.electricpump), TextUtils.tooltip("machine.voltage.120"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.electrolyticseparator), TextUtils.tooltip("machine.voltage.240"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.electricarcfurnace), TextUtils.tooltip("machine.voltage.120"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.electricarcfurnacedouble), TextUtils.tooltip("machine.voltage.240"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.electricarcfurnacetriple), TextUtils.tooltip("machine.voltage.480"));

		// generators
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.solarpanel), TextUtils.tooltip("machine.voltage.120"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.advancedsolarpanel), TextUtils.tooltip("machine.voltage.240"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.thermoelectricgenerator), TextUtils.tooltip("machine.voltage.120"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.combustionchamber), TextUtils.tooltip("machine.voltage.120"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.hydroelectricgenerator), TextUtils.tooltip("machine.voltage.120"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.windmill), TextUtils.tooltip("machine.voltage.120"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.coalgenerator), TextUtils.tooltip("machine.voltage.120"));

		// misc
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.downgradetransformer), TextUtils.tooltip("transformer.energyloss"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.upgradetransformer), TextUtils.tooltip("transformer.energyloss"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.batterybox), TextUtils.tooltip("machine.voltage.120"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.lithiumbatterybox), TextUtils.tooltip("machine.voltage.240"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.carbynebatterybox), TextUtils.tooltip("machine.voltage.480"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.creativepowersource), TextUtils.tooltip("creativepowersource.joke"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.creativefluidsource), TextUtils.tooltip("creativefluidsource.joke"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.fluidvoid), TextUtils.tooltip("fluidvoid"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.tanksteel), TextUtils.tooltip("tanksteel.capacity"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.tankreinforced), TextUtils.tooltip("tankreinforced.capacity"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.tankhsla), TextUtils.tooltip("tankhsla.capacity"));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.blockSeismicMarker, TextUtils.tooltip("seismicmarker.redstone"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.seismicrelay), TextUtils.tooltip("seismicrelay.use"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.coolantresavoir), TextUtils.tooltip("coolantresavoir.place"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.motorcomplex), TextUtils.tooltip("motorcomplex.use"));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.blockFrame, TextUtils.tooltip("blockframe.joke"));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.blockFrameCorner, TextUtils.tooltip("blockframe.joke"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.quarry), TextUtils.tooltip("quarry.power"));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.blockLogisticalManager, TextUtils.tooltip("logisticalmanager.use"));
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
