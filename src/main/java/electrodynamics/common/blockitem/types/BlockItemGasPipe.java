package electrodynamics.common.blockitem.types;

import java.util.List;
import java.util.function.Supplier;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.common.block.connect.BlockGasPipe;
import electrodynamics.common.blockitem.BlockItemElectrodynamics;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class BlockItemGasPipe extends BlockItemElectrodynamics {

	private final BlockGasPipe pipe;

	public BlockItemGasPipe(BlockGasPipe pipe, Properties properties, Supplier<CreativeModeTab> creativeTab) {
		super(pipe, properties, creativeTab);
		this.pipe = pipe;
	}

	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltips, TooltipFlag advanced) {
		super.appendHoverText(stack, level, tooltips, advanced);
		tooltips.add(ElectroTextUtils.tooltip("pipematerial", pipe.pipe.pipeMaterial.getTranslatedName()).withStyle(ChatFormatting.GRAY));
		// tooltips.add(TextUtils.tooltip("pipeinsulationmaterial", pipe.pipe.insulationMaterial.getTranslatedName()).withStyle(ChatFormatting.GRAY));
		tooltips.add(ElectroTextUtils.tooltip("pipemaximumpressure", ChatFormatter.getChatDisplayShort(pipe.pipe.pipeMaterial.maxPressure, DisplayUnit.PRESSURE_ATM)).withStyle(ChatFormatting.GRAY));
		// tooltips.add(TextUtils.tooltip("pipeheatloss", ChatFormatter.getChatDisplayShort(pipe.pipe.effectivePipeHeatLoss, DisplayUnit.TEMPERATURE_KELVIN)).withStyle(ChatFormatting.GRAY));
	}

}
