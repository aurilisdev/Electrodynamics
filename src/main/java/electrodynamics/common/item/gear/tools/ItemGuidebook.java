package electrodynamics.common.item.gear.tools;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class ItemGuidebook extends Item {

	private static final String LINK = "https://electrodynamics.fandom.com/wiki/Electrodynamics_Wiki";
	
	public ItemGuidebook(Properties properties) {
		super(properties);
	}
	
	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltips, TooltipFlag flag) {
		tooltips.add(new TranslatableComponent("tooltip.info.guidebookuse").withStyle(ChatFormatting.LIGHT_PURPLE));
		tooltips.add(new TranslatableComponent("tooltip.info.guidebooktemp").withStyle(ChatFormatting.BLUE));
		super.appendHoverText(stack, world, tooltips, flag);
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand handIn) {
		if(world.isClientSide && player.isShiftKeyDown()) {
			player.sendMessage(new TranslatableComponent("message.electrodynamics.guidebookclick").withStyle(ChatFormatting.BOLD, ChatFormatting.RED)
				.withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, LINK))), Util.NIL_UUID);
			return InteractionResultHolder.pass(player.getItemInHand(handIn));
		}
		return super.use(world, player, handIn);
	}

}
