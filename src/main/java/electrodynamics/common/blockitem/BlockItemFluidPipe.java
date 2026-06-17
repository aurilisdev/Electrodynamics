package electrodynamics.common.blockitem;

import java.util.List;
import java.util.function.Supplier;

import electrodynamics.common.block.connect.BlockFluidPipe;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.common.blockitem.BlockItemVoltaic;

public class BlockItemFluidPipe extends BlockItemVoltaic {

    public final BlockFluidPipe pipe;

    public BlockItemFluidPipe(BlockFluidPipe block, Properties properties, Supplier<CreativeModeTab> creativeTab) {
	super(block, properties, creativeTab);
	pipe = block;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level context, List<Component> tooltips, TooltipFlag advanced) {
	super.appendHoverText(stack, context, tooltips, advanced);
	tooltips.add(ElectroTextUtils.tooltip("pipethroughput",
		ChatFormatter.getChatDisplayShort(pipe.pipe.getMaxTransfer() / 1000.0, DisplayUnits.BUCKETS)
			.withStyle(ChatFormatting.GRAY))
		.withStyle(ChatFormatting.DARK_GRAY));
    }
}
