package electrodynamics.common.blockitem;

import java.util.List;

import electrodynamics.common.block.connect.BlockGasPipe;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.common.blockitem.BlockItemVoltaic;

public class BlockItemGasPipe extends BlockItemVoltaic {

	private final BlockGasPipe pipe;

	public BlockItemGasPipe(BlockGasPipe pipe, Properties properties, Holder<CreativeModeTab> creativeTab) {
		super(pipe, properties, creativeTab);
		this.pipe = pipe;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltips, TooltipFlag advanced) {
		super.appendHoverText(stack, context, tooltips, advanced);
		//tooltips.add(ElectroTextUtils.tooltip("pipematerial", pipe.pipe.getPipeMaterial().getName().copy().withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
		tooltips.add(ElectroTextUtils.tooltip("pipethroughput", ChatFormatter.getChatDisplayShort(pipe.pipe.getMaxTransfer() / 1000.0, DisplayUnits.BUCKETS).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
		// tooltips.add(TextUtils.tooltip("pipeinsulationmaterial", pipe.pipe.insulationMaterial.getTranslatedName()).withStyle(ChatFormatting.GRAY));
		tooltips.add(ElectroTextUtils.tooltip("pipemaximumpressure", ChatFormatter.getChatDisplayShort(pipe.pipe.getPipeMaterial().getMaxPressuire(), DisplayUnits.PRESSURE_ATM).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
		// tooltips.add(TextUtils.tooltip("pipeheatloss", ChatFormatter.getChatDisplayShort(pipe.pipe.effectivePipeHeatLoss, DisplayUnit.TEMPERATURE_KELVIN)).withStyle(ChatFormatting.GRAY));
	}

}
