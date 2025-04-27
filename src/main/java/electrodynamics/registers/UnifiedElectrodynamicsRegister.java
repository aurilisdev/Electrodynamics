package electrodynamics.registers;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.settings.ElectroConstants;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.ChatFormatting;
import net.minecraftforge.eventbus.api.IEventBus;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.common.blockitem.BlockItemDescriptable;
import voltaic.prefab.utilities.VoltaicTextUtils;

public class UnifiedElectrodynamicsRegister {

	public static void register(IEventBus bus) {
		ElectrodynamicsBlocks.BLOCKS.register(bus);
		ElectrodynamicsTiles.BLOCK_ENTITY_TYPES.register(bus);
		ElectrodynamicsItems.ITEMS.register(bus);
		ElectrodynamicsFluidTypes.FLUID_TYPES.register(bus);
		ElectrodynamicsFluids.FLUIDS.register(bus);
		ElectrodynamicsEntities.ENTITIES.register(bus);
		ElectrodynamicsMenuTypes.MENU_TYPES.register(bus);
		ElectrodynamicsSounds.SOUNDS.register(bus);
		ElectrodynamicsGases.GASES.register(bus);
		ElectrodynamicsCreativeTabs.CREATIVE_TABS.register(bus);
		ElectrodynamicsRuleTestTypes.RULE_TEST_TYPES.register(bus);
		ElectrodynamicsRecipies.RECIPE_TYPES.register(bus);
		ElectrodynamicsRecipies.RECIPE_SERIALIZER.register(bus);
	}

	static {
		// machines
		// cleaner and simpler is it not?
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electricfurnace), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electricfurnacedouble), VoltaicTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electricfurnacetriple), VoltaicTextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.wiremill), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.wiremilldouble), VoltaicTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.wiremilltriple), VoltaicTextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.mineralcrusher), VoltaicTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.mineralcrusherdouble), VoltaicTextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.mineralcrushertriple), VoltaicTextUtils.voltageTooltip(960));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.mineralgrinder), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.mineralgrinderdouble), VoltaicTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.mineralgrindertriple), VoltaicTextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.oxidationfurnace), VoltaicTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.mineralwasher), VoltaicTextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.chemicalmixer), VoltaicTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.chemicalcrystallizer), VoltaicTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.energizedalloyer), VoltaicTextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.reinforcedalloyer), VoltaicTextUtils.voltageTooltip(960));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.lathe), VoltaicTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.chargerlv), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.chargermv), VoltaicTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.chargerhv), VoltaicTextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.fermentationplant), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electricpump), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electrolyticseparator), VoltaicTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electricarcfurnace), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electricarcfurnacedouble), VoltaicTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electricarcfurnacetriple), VoltaicTextUtils.voltageTooltip(480));

		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCK_COMPRESSOR.get(), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCK_DECOMPRESSOR.get(), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCK_ADVANCEDCOMPRESSOR.get(), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCK_ADVANCEDDECOMPRESSOR.get(), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCK_COMPRESSOR_ADDONTANK.get(), ElectroTextUtils.tooltip("addontankcap", ChatFormatter.formatFluidMilibuckets(ElectroConstants.GAS_TRANSFORMER_ADDON_TANK_CAPCITY).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCK_THERMOELECTRICMANIPULATOR.get(), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCK_ADVANCED_THERMOELECTRICMANIPULATOR.get(), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.gaspipepump), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.fluidpipepump), VoltaicTextUtils.voltageTooltip(120));

		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.gastanksteel), ElectroTextUtils.tooltip("gastank.capacity", ChatFormatter.getChatDisplayShort(8, DisplayUnits.BUCKETS).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.gastankreinforced), ElectroTextUtils.tooltip("gastank.capacity", ChatFormatter.getChatDisplayShort(32, DisplayUnits.BUCKETS).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.gastankhsla), ElectroTextUtils.tooltip("gastank.capacity", ChatFormatter.getChatDisplayShort(128, DisplayUnits.BUCKETS).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));

		// generators
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.solarpanel), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.advancedsolarpanel), VoltaicTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.thermoelectricgenerator), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.combustionchamber), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.hydroelectricgenerator), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.windmill), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.coalgenerator), VoltaicTextUtils.voltageTooltip(120));

		// misc
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.downgradetransformer), ElectroTextUtils.tooltip("transformer.energyloss", ChatFormatter.getChatDisplayShort(ElectroConstants.TRANSFORMER_EFFICIENCY * 100, DisplayUnits.PERCENTAGE).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.upgradetransformer), ElectroTextUtils.tooltip("transformer.energyloss", ChatFormatter.getChatDisplayShort(ElectroConstants.TRANSFORMER_EFFICIENCY * 100, DisplayUnits.PERCENTAGE).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.advanceddowngradetransformer), ElectroTextUtils.tooltip("transformer.energyloss", ChatFormatter.getChatDisplayShort(ElectroConstants.TRANSFORMER_EFFICIENCY * 100, DisplayUnits.PERCENTAGE).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.advancedupgradetransformer), ElectroTextUtils.tooltip("transformer.energyloss", ChatFormatter.getChatDisplayShort(ElectroConstants.TRANSFORMER_EFFICIENCY * 100, DisplayUnits.PERCENTAGE).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.circuitbreaker), ElectroTextUtils.tooltip("transformer.energyloss", ChatFormatter.getChatDisplayShort(ElectroConstants.CIRCUITBREAKER_EFFICIENCY * 100, DisplayUnits.PERCENTAGE).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.relay), ElectroTextUtils.tooltip("transformer.energyloss", ChatFormatter.getChatDisplayShort(ElectroConstants.RELAY_EFFICIENCY * 100, DisplayUnits.PERCENTAGE).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.currentregulator), ElectroTextUtils.tooltip("transformer.energyloss", ChatFormatter.getChatDisplayShort(ElectroConstants.CURRENTREGULATOR_EFFICIENCY * 100, DisplayUnits.PERCENTAGE).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.batterybox), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.lithiumbatterybox), VoltaicTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.carbynebatterybox), VoltaicTextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.creativepowersource), ElectroTextUtils.tooltip("creativepowersource.joke").withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.creativefluidsource), ElectroTextUtils.tooltip("creativefluidsource.joke").withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.creativegassource), ElectroTextUtils.tooltip("creativegassource.joke").withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.fluidvoid), ElectroTextUtils.tooltip("fluidvoid").withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.tanksteel), ElectroTextUtils.tooltip("fluidtank.capacity", ChatFormatter.getChatDisplayShort(8, DisplayUnits.BUCKETS).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.tankreinforced), ElectroTextUtils.tooltip("fluidtank.capacity", ChatFormatter.getChatDisplayShort(32, DisplayUnits.BUCKETS).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.tankhsla), ElectroTextUtils.tooltip("fluidtank.capacity", ChatFormatter.getChatDisplayShort(128, DisplayUnits.BUCKETS).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCK_SEISMICMARKER.get(), ElectroTextUtils.tooltip("seismicmarker.redstone").withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.seismicrelay), ElectroTextUtils.tooltip("seismicrelay.use").withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.coolantresavoir), ElectroTextUtils.tooltip("coolantresavoir.place").withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.motorcomplex), ElectroTextUtils.tooltip("motorcomplex.use").withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCK_FRAME.get(), ElectroTextUtils.tooltip("blockframe.joke").withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCK_FRAME_CORNER.get(), ElectroTextUtils.tooltip("blockframe.joke").withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.quarry), ElectroTextUtils.tooltip("quarry.power").withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCK_LOGISTICALMANAGER.get(), ElectroTextUtils.tooltip("logisticalmanager.use").withStyle(ChatFormatting.DARK_GRAY));

		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.gasvent), ElectroTextUtils.tooltip("gasvent").withStyle(ChatFormatting.DARK_GRAY));
		//BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.gasvalve), ElectroTextUtils.tooltip("gasvalve").withStyle(ChatFormatting.DARK_GRAY));
		//BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.fluidvalve), ElectroTextUtils.tooltip("fluidvalve"));
		BlockItemDescriptable.addDescription(() -> ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.potentiometer), ElectroTextUtils.tooltip("potentiometer.use").withStyle(ChatFormatting.DARK_GRAY));
	}

}
