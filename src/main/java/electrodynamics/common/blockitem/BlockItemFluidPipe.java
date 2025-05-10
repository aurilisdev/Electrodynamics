package electrodynamics.common.blockitem;

import java.util.List;
import java.util.function.Supplier;

import electrodynamics.common.block.connect.BlockFluidPipe;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.common.blockitem.BlockItemVoltaic;

public class BlockItemFluidPipe extends BlockItemVoltaic {

    public final BlockFluidPipe pipe;

    public BlockItemFluidPipe(BlockFluidPipe block, Properties properties, Supplier<ItemGroup> creativeTab) {
        super(block, properties, creativeTab);
        pipe = block;
    }

    @Override
    public void appendHoverText(ItemStack stack, World context, List<ITextComponent> tooltips, ITooltipFlag advanced) {
        super.appendHoverText(stack, context, tooltips, advanced);
        tooltips.add(ElectroTextUtils.tooltip("pipethroughput", ChatFormatter.getChatDisplayShort(pipe.pipe.getMaxTransfer() / 1000.0, DisplayUnits.BUCKETS).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY));
    }
}
