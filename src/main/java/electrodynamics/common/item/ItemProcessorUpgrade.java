package electrodynamics.common.item;

import java.util.List;

import electrodynamics.common.item.subtype.SubtypeProcessorUpgrade;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class ItemProcessorUpgrade extends Item {
    public final SubtypeProcessorUpgrade subtype;

    public ItemProcessorUpgrade(Properties properties, SubtypeProcessorUpgrade subtype) {
	super(properties.stacksTo(1));
	this.subtype = subtype;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
	super.appendHoverText(stack, worldIn, tooltip, flagIn);
	if (subtype.capacityMultiplier != 1.0) {
	    tooltip.add(new TranslatableComponent("tooltip.info.capacityupgrade", subtype.capacityMultiplier).withStyle(ChatFormatting.GRAY));
	    tooltip.add(new TranslatableComponent("tooltip.info.capacityupgradevoltage", (subtype.capacityMultiplier == 2.25 ? 4 : 2) + "x")
		    .withStyle(ChatFormatting.RED));
	}
	if (subtype.speedMultiplier != 1.0) {
	    tooltip.add(new TranslatableComponent("tooltip.info.speedupgrade", subtype.speedMultiplier).withStyle(ChatFormatting.GRAY));
	}
    }
}
