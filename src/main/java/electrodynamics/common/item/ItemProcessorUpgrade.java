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
	super(properties.stacksTo(subtype.maxSize));
	this.subtype = subtype;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
	super.appendHoverText(stack, worldIn, tooltip, flagIn);
	if (subtype == SubtypeProcessorUpgrade.advancedcapacity || subtype == SubtypeProcessorUpgrade.basiccapacity) {
	    double capacityMultiplier = subtype == SubtypeProcessorUpgrade.advancedcapacity ? 2.25 : 1.5;
	    tooltip.add(new TranslatableComponent("tooltip.info.capacityupgrade", capacityMultiplier).withStyle(ChatFormatting.GRAY));
	    tooltip.add(new TranslatableComponent("tooltip.info.capacityupgradevoltage", (capacityMultiplier == 2.25 ? 4 : 2) + "x")
		    .withStyle(ChatFormatting.RED));
	}
	if (subtype == SubtypeProcessorUpgrade.advancedspeed || subtype == SubtypeProcessorUpgrade.basicspeed) {
	    double speedMultiplier = subtype == SubtypeProcessorUpgrade.advancedspeed ? 2.25 : 1.5;
	    tooltip.add(new TranslatableComponent("tooltip.info.speedupgrade", speedMultiplier).withStyle(ChatFormatting.GRAY));
	}
    }
}
