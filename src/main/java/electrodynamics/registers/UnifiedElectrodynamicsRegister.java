package electrodynamics.registers;

import java.util.function.Supplier;

import electrodynamics.api.ISubtype;
import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.blockitem.BlockItemDescriptable;
import electrodynamics.common.tile.gastransformer.TileGasTransformerAddonTank;
import electrodynamics.prefab.utilities.TextUtils;
import net.minecraft.world.level.block.Block;
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
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.electricfurnace), TextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.electricfurnacedouble), TextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.electricfurnacetriple), TextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.wiremill), TextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.wiremilldouble), TextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.wiremilltriple), TextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.mineralcrusher), TextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.mineralcrusherdouble), TextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.mineralcrushertriple), TextUtils.voltageTooltip(960));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.mineralgrinder), TextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.mineralgrinderdouble), TextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.mineralgrindertriple), TextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.oxidationfurnace), TextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.mineralwasher), TextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.chemicalmixer), TextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.chemicalcrystallizer), TextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.energizedalloyer), TextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.reinforcedalloyer), TextUtils.voltageTooltip(960));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.lathe), TextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.chargerlv), TextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.chargermv), TextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.chargerhv), TextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.fermentationplant), TextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.electricpump), TextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.electrolyticseparator), TextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.electricarcfurnace), TextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.electricarcfurnacedouble), TextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.electricarcfurnacetriple), TextUtils.voltageTooltip(480));
		
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.blockCompressor, TextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.blockDecompressor, TextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.blockGasTransformerAddonTank, TextUtils.tooltip("addontankcap", ChatFormatter.formatFluidMilibuckets(TileGasTransformerAddonTank.ADDITIONAL_CAPACITY)));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.blockThermoelectricManipulator, TextUtils.voltageTooltip(120));
		
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.gastanksteel), TextUtils.tooltip("gastank.capacity", ChatFormatter.getChatDisplayShort(8, DisplayUnit.BUCKETS)));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.gastankreinforced), TextUtils.tooltip("gastank.capacity", ChatFormatter.getChatDisplayShort(32, DisplayUnit.BUCKETS)));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.gastankhsla), TextUtils.tooltip("gastank.capacity", ChatFormatter.getChatDisplayShort(128, DisplayUnit.BUCKETS)));
		

		// generators
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.solarpanel), TextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.advancedsolarpanel), TextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.thermoelectricgenerator), TextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.combustionchamber), TextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.hydroelectricgenerator), TextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.windmill), TextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.coalgenerator), TextUtils.voltageTooltip(120));

		// misc
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.downgradetransformer), TextUtils.tooltip("transformer.energyloss"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.upgradetransformer), TextUtils.tooltip("transformer.energyloss"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.batterybox), TextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.lithiumbatterybox), TextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.carbynebatterybox), TextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.creativepowersource), TextUtils.tooltip("creativepowersource.joke"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.creativefluidsource), TextUtils.tooltip("creativefluidsource.joke"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.fluidvoid), TextUtils.tooltip("fluidvoid"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.tanksteel), TextUtils.tooltip("fluidtank.capacity", ChatFormatter.getChatDisplayShort(8, DisplayUnit.BUCKETS)));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.tankreinforced), TextUtils.tooltip("fluidtank.capacity", ChatFormatter.getChatDisplayShort(32, DisplayUnit.BUCKETS)));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.tankhsla), TextUtils.tooltip("fluidtank.capacity", ChatFormatter.getChatDisplayShort(128, DisplayUnit.BUCKETS)));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.blockSeismicMarker, TextUtils.tooltip("seismicmarker.redstone"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.seismicrelay), TextUtils.tooltip("seismicrelay.use"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.coolantresavoir), TextUtils.tooltip("coolantresavoir.place"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.motorcomplex), TextUtils.tooltip("motorcomplex.use"));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.blockFrame, TextUtils.tooltip("blockframe.joke"));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.blockFrameCorner, TextUtils.tooltip("blockframe.joke"));
		BlockItemDescriptable.addDescription(() -> UnifiedElectrodynamicsRegister.getSafeBlock(SubtypeMachine.quarry), TextUtils.tooltip("quarry.power"));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.blockLogisticalManager, TextUtils.tooltip("logisticalmanager.use"));
		
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.getBlock(SubtypeMachine.gasvent), TextUtils.tooltip("gasvent"));
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
