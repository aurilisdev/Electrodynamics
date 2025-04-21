package electrodynamics.registers;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.settings.ElectroConstants;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.ChatFormatting;
import net.neoforged.bus.api.IEventBus;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.common.blockitem.BlockItemDescriptable;
import voltaic.prefab.utilities.VoltaicTextUtils;

public class UnifiedElectrodynamicsRegister {

	public static void register(IEventBus bus) {
		ElectrodynamicsBlocks.BLOCKS.register(bus);
		ElectrodynamicsTiles.BLOCK_ENTITY_TYPES.register(bus);
		ElectrodynamicsItems.ITEMS.register(bus);
		ElectrodynamicsFluids.FLUIDS.register(bus);
		ElectrodynamicsFluidTypes.FLUID_TYPES.register(bus);
		ElectrodynamicsEntities.ENTITIES.register(bus);
		ElectrodynamicsMenuTypes.MENU_TYPES.register(bus);
		ElectrodynamicsSounds.SOUNDS.register(bus);
		ElectrodynamicsGases.GASES.register(bus);
		ElectrodynamicsCreativeTabs.CREATIVE_TABS.register(bus);
		ElectrodynamicsRuleTestTypes.RULE_TEST_TYPES.register(bus);
		ElectrodynamicsRecipies.RECIPE_TYPES.register(bus);
        ElectrodynamicsRecipies.RECIPE_SERIALIZER.register(bus);
		ElectrodynamicsArmorMaterials.ARMOR_MATERIALS.register(bus);
	}

	static {
		// machines
		// cleaner and simpler is it not?
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.electricfurnace), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.electricfurnacedouble), VoltaicTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.electricfurnacetriple), VoltaicTextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.wiremill), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.wiremilldouble), VoltaicTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.wiremilltriple), VoltaicTextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.mineralcrusher), VoltaicTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.mineralcrusherdouble), VoltaicTextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.mineralcrushertriple), VoltaicTextUtils.voltageTooltip(960));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.mineralgrinder), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.mineralgrinderdouble), VoltaicTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.mineralgrindertriple), VoltaicTextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.oxidationfurnace), VoltaicTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.mineralwasher), VoltaicTextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.chemicalmixer), VoltaicTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.chemicalcrystallizer), VoltaicTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.energizedalloyer), VoltaicTextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.reinforcedalloyer), VoltaicTextUtils.voltageTooltip(960));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.lathe), VoltaicTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.chargerlv), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.chargermv), VoltaicTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.chargerhv), VoltaicTextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.fermentationplant), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.electricpump), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.electrolyticseparator), VoltaicTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.electricarcfurnace), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.electricarcfurnacedouble), VoltaicTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.electricarcfurnacetriple), VoltaicTextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.electrolosischamber), VoltaicTextUtils.voltageTooltip(1920));

		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCK_COMPRESSOR, VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCK_DECOMPRESSOR, VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCK_ADVANCEDCOMPRESSOR, VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCK_ADVANCEDDECOMPRESSOR, VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCK_COMPRESSOR_ADDONTANK, ElectroTextUtils.tooltip("addontankcap", ChatFormatter.formatFluidMilibuckets(ElectroConstants.GAS_TRANSFORMER_ADDON_TANK_CAPCITY).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCK_THERMOELECTRICMANIPULATOR, VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCK_ADVANCED_THERMOELECTRICMANIPULATOR, VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.gaspipepump), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.fluidpipepump), VoltaicTextUtils.voltageTooltip(120));

		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.gastanksteel), ElectroTextUtils.tooltip("gastank.capacity", ChatFormatter.getChatDisplayShort(8, DisplayUnits.BUCKETS).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.gastankreinforced), ElectroTextUtils.tooltip("gastank.capacity", ChatFormatter.getChatDisplayShort(32, DisplayUnits.BUCKETS).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.gastankhsla), ElectroTextUtils.tooltip("gastank.capacity", ChatFormatter.getChatDisplayShort(128, DisplayUnits.BUCKETS).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCK_ROTARYUNIFIER, ElectroTextUtils.tooltip("rotaryunifier.use1").withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCK_ROTARYUNIFIER, ElectroTextUtils.tooltip("rotaryunifier.use2").withStyle(ChatFormatting.DARK_GRAY));

		// generators
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.solarpanel), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.advancedsolarpanel), VoltaicTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.thermoelectricgenerator), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.combustionchamber), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.hydroelectricgenerator), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.windmill), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.coalgenerator), VoltaicTextUtils.voltageTooltip(120));

		// misc
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.downgradetransformer), ElectroTextUtils.tooltip("transformer.energyloss", ChatFormatter.getChatDisplayShort(ElectroConstants.TRANSFORMER_EFFICIENCY * 100, DisplayUnits.PERCENTAGE).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.upgradetransformer), ElectroTextUtils.tooltip("transformer.energyloss", ChatFormatter.getChatDisplayShort(ElectroConstants.TRANSFORMER_EFFICIENCY * 100, DisplayUnits.PERCENTAGE).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.advanceddowngradetransformer), ElectroTextUtils.tooltip("transformer.energyloss", ChatFormatter.getChatDisplayShort(ElectroConstants.TRANSFORMER_EFFICIENCY * 100, DisplayUnits.PERCENTAGE).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.advancedupgradetransformer), ElectroTextUtils.tooltip("transformer.energyloss", ChatFormatter.getChatDisplayShort(ElectroConstants.TRANSFORMER_EFFICIENCY * 100, DisplayUnits.PERCENTAGE).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.circuitbreaker), ElectroTextUtils.tooltip("transformer.energyloss", ChatFormatter.getChatDisplayShort(ElectroConstants.CIRCUITBREAKER_EFFICIENCY * 100, DisplayUnits.PERCENTAGE).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.relay), ElectroTextUtils.tooltip("transformer.energyloss", ChatFormatter.getChatDisplayShort(ElectroConstants.RELAY_EFFICIENCY * 100, DisplayUnits.PERCENTAGE).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.currentregulator), ElectroTextUtils.tooltip("transformer.energyloss", ChatFormatter.getChatDisplayShort(ElectroConstants.CURRENTREGULATOR_EFFICIENCY * 100, DisplayUnits.PERCENTAGE).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.batterybox), VoltaicTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.lithiumbatterybox), VoltaicTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.carbynebatterybox), VoltaicTextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.creativepowersource), ElectroTextUtils.tooltip("creativepowersource.joke").withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.creativefluidsource), ElectroTextUtils.tooltip("creativefluidsource.joke").withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.creativegassource), ElectroTextUtils.tooltip("creativegassource.joke").withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.fluidvoid), ElectroTextUtils.tooltip("fluidvoid").withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.tanksteel), ElectroTextUtils.tooltip("fluidtank.capacity", ChatFormatter.getChatDisplayShort(8, DisplayUnits.BUCKETS).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.tankreinforced), ElectroTextUtils.tooltip("fluidtank.capacity", ChatFormatter.getChatDisplayShort(32, DisplayUnits.BUCKETS).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.tankhsla), ElectroTextUtils.tooltip("fluidtank.capacity", ChatFormatter.getChatDisplayShort(128, DisplayUnits.BUCKETS).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCK_SEISMICMARKER, ElectroTextUtils.tooltip("seismicmarker.redstone").withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.seismicrelay), ElectroTextUtils.tooltip("seismicrelay.use").withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.coolantresavoir), ElectroTextUtils.tooltip("coolantresavoir.place").withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.motorcomplex), ElectroTextUtils.tooltip("motorcomplex.use").withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCK_FRAME, ElectroTextUtils.tooltip("blockframe.joke").withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCK_FRAME_CORNER, ElectroTextUtils.tooltip("blockframe.joke").withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.quarry), ElectroTextUtils.tooltip("quarry.power").withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCK_LOGISTICALMANAGER, ElectroTextUtils.tooltip("logisticalmanager.use").withStyle(ChatFormatting.DARK_GRAY));

		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.gasvent), ElectroTextUtils.tooltip("gasvent").withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.gascollector), VoltaicTextUtils.voltageTooltip(240));
		//BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.gasvalve), ElectroTextUtils.tooltip("gasvalve").withStyle(ChatFormatting.DARK_GRAY));
		//BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.fluidvalve), ElectroTextUtils.tooltip("fluidvalve"));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCKS_MACHINE.getHolder(SubtypeMachine.potentiometer), ElectroTextUtils.tooltip("potentiometer.use").withStyle(ChatFormatting.DARK_GRAY));
		BlockItemDescriptable.addDescription(ElectrodynamicsBlocks.BLOCK_CHEMICALREACTOR, VoltaicTextUtils.voltageTooltip(480));
	}

}
