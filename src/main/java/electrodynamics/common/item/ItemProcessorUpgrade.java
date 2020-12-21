package electrodynamics.common.item;

import java.util.List;

import electrodynamics.common.item.subtype.SubtypeProcessorUpgrade;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ItemProcessorUpgrade extends Item {
	public final SubtypeProcessorUpgrade subtype;

	public ItemProcessorUpgrade(Properties properties, SubtypeProcessorUpgrade subtype) {
		super(properties.maxStackSize(1));
		this.subtype = subtype;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if (subtype.capacityMultiplier != 1.0) {
			tooltip.add(new TranslationTextComponent("tooltip.info.capacityupgrade", subtype.capacityMultiplier).mergeStyle(TextFormatting.GRAY));
		}
		if (subtype.speedMultiplier != 1.0) {
			tooltip.add(new TranslationTextComponent("tooltip.info.speedupgrade", subtype.speedMultiplier).mergeStyle(TextFormatting.GRAY));
		}
	}
}
