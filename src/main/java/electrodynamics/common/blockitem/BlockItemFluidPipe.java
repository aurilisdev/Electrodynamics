package electrodynamics.common.blockitem;

import java.util.List;

import electrodynamics.common.block.connect.BlockFluidPipe;
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

public class BlockItemFluidPipe extends BlockItemVoltaic {

    public final BlockFluidPipe pipe;

    public BlockItemFluidPipe(BlockFluidPipe block, Properties properties, Holder<CreativeModeTab> creativeTab) {
        super(block, properties, creativeTab);
        pipe = block;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltips, TooltipFlag advanced) {
        super.appendHoverText(stack, context, tooltips, advanced);
        tooltips.add(ElectroTextUtils.tooltip("pipethroughput", ChatFormatter.getChatDisplayShort(pipe.pipe.getMaxTransfer() / 1000.0, DisplayUnits.BUCKETS).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
    }
}
