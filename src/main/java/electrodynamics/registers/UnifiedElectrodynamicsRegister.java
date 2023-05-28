package electrodynamics.registers;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.blockitem.BlockItemDescriptable;
import electrodynamics.common.tile.gastransformer.TileGasTransformerAddonTank;
import electrodynamics.prefab.utilities.TextUtils;
import net.minecraftforge.eventbus.api.IEventBus;

public class UnifiedElectrodynamicsRegister {

	public static void register(IEventBus bus) {
		ElectrodynamicsRegistries.init();
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
		ElectrodynamicsGases.GASES.register(bus);
		ElectrodynamicsParticles.PARTICLES.register(bus);
	}

	static {
		// machines
		// cleaner and simpler is it not?
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricfurnace), TextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricfurnacedouble), TextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricfurnacetriple), TextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.wiremill), TextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.wiremilldouble), TextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.wiremilltriple), TextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.mineralcrusher), TextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.mineralcrusherdouble), TextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.mineralcrushertriple), TextUtils.voltageTooltip(960));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.mineralgrinder), TextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.mineralgrinderdouble), TextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.mineralgrindertriple), TextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.oxidationfurnace), TextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.mineralwasher), TextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.chemicalmixer), TextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.chemicalcrystallizer), TextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.energizedalloyer), TextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.reinforcedalloyer), TextUtils.voltageTooltip(960));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.lathe), TextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.chargerlv), TextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.chargermv), TextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.chargerhv), TextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.fermentationplant), TextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricpump), TextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.electrolyticseparator), TextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricarcfurnace), TextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricarcfurnacedouble), TextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricarcfurnacetriple), TextUtils.voltageTooltip(480));
		
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.blockCompressor, TextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.blockDecompressor, TextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.blockGasTransformerAddonTank, TextUtils.tooltip("addontankcap", ChatFormatter.formatFluidMilibuckets(TileGasTransformerAddonTank.ADDITIONAL_CAPACITY)));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.blockThermoelectricManipulator, TextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.blockGasPipePump, TextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.blockFluidPipePump, TextUtils.voltageTooltip(120));
		
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.gastanksteel), TextUtils.tooltip("gastank.capacity", ChatFormatter.getChatDisplayShort(8, DisplayUnit.BUCKETS)));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.gastankreinforced), TextUtils.tooltip("gastank.capacity", ChatFormatter.getChatDisplayShort(32, DisplayUnit.BUCKETS)));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.gastankhsla), TextUtils.tooltip("gastank.capacity", ChatFormatter.getChatDisplayShort(128, DisplayUnit.BUCKETS)));
		

		// generators
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.solarpanel), TextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.advancedsolarpanel), TextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.thermoelectricgenerator), TextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.combustionchamber), TextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.hydroelectricgenerator), TextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.windmill), TextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.coalgenerator), TextUtils.voltageTooltip(120));

		// misc
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.downgradetransformer), TextUtils.tooltip("transformer.energyloss"));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.upgradetransformer), TextUtils.tooltip("transformer.energyloss"));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.batterybox), TextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.lithiumbatterybox), TextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.carbynebatterybox), TextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.creativepowersource), TextUtils.tooltip("creativepowersource.joke"));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.creativefluidsource), TextUtils.tooltip("creativefluidsource.joke"));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.fluidvoid), TextUtils.tooltip("fluidvoid"));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.tanksteel), TextUtils.tooltip("fluidtank.capacity", ChatFormatter.getChatDisplayShort(8, DisplayUnit.BUCKETS)));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.tankreinforced), TextUtils.tooltip("fluidtank.capacity", ChatFormatter.getChatDisplayShort(32, DisplayUnit.BUCKETS)));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.tankhsla), TextUtils.tooltip("fluidtank.capacity", ChatFormatter.getChatDisplayShort(128, DisplayUnit.BUCKETS)));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.blockSeismicMarker, TextUtils.tooltip("seismicmarker.redstone"));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.seismicrelay), TextUtils.tooltip("seismicrelay.use"));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.coolantresavoir), TextUtils.tooltip("coolantresavoir.place"));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.motorcomplex), TextUtils.tooltip("motorcomplex.use"));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.blockFrame, TextUtils.tooltip("blockframe.joke"));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.blockFrameCorner, TextUtils.tooltip("blockframe.joke"));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.quarry), TextUtils.tooltip("quarry.power"));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.blockLogisticalManager, TextUtils.tooltip("logisticalmanager.use"));
		
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.gasvent), TextUtils.tooltip("gasvent"));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.blockGasValve, TextUtils.tooltip("gasvalve"));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.blockFluidValve, TextUtils.tooltip("fluidvalve"));
	}

}
