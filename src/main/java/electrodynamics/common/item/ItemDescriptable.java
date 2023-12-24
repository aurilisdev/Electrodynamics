package electrodynamics.common.item;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class ItemDescriptable extends Item {

	private ITextComponent[] tooltips;

	public ItemDescriptable(Properties properties, ITextComponent... tooltips) {
		super(properties);
		this.tooltips = tooltips;
	}

	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltips, ITooltipFlag flag) {
		super.appendHoverText(stack, world, tooltips, flag);
		if (tooltips != null) {
			for (ITextComponent tooltip : this.tooltips) {
				tooltips.add(tooltip);
			}
		}
	}

}
