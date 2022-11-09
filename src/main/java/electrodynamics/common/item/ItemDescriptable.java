package electrodynamics.common.item;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class ItemDescriptable extends Item {

	private String langKey;

	public ItemDescriptable(Properties properties, String langKey) {
		super(properties);
		this.langKey = langKey;
	}

	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltips, TooltipFlag flag) {
		super.appendHoverText(stack, world, tooltips, flag);
		tooltips.add(Component.translatable(langKey).withStyle(ChatFormatting.GRAY));
	}

}
