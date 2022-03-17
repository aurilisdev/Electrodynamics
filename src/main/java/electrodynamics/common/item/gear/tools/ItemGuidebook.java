package electrodynamics.common.item.gear.tools;

import java.util.List;

import electrodynamics.common.inventory.container.item.ContainerGuidebook;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class ItemGuidebook extends Item {

	private static final String LINK = "https://wiki.aurilis.dev";
	private static final Component CONTAINER_TITLE = new TranslatableComponent("container.guidebook");

	public ItemGuidebook(Properties properties) {
		super(properties);
	}

	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltips, TooltipFlag flag) {
		tooltips.add(new TranslatableComponent("tooltip.info.guidebookuse").withStyle(ChatFormatting.LIGHT_PURPLE));
		// tooltips.add(new TranslatableComponent("tooltip.info.guidebooktemp").withStyle(ChatFormatting.BLUE));
		super.appendHoverText(stack, world, tooltips, flag);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand handIn) {
		if (world.isClientSide) {
			if (player.isShiftKeyDown()) {
				player.sendMessage(new TranslatableComponent("message.electrodynamics.guidebookclick").withStyle(ChatFormatting.BOLD, ChatFormatting.RED).withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, LINK))), Util.NIL_UUID);
				return InteractionResultHolder.pass(player.getItemInHand(handIn));
			}
		} else {
			player.openMenu(getMenuProvider(world, player));
		}
		return super.use(world, player, handIn);
	}

	public MenuProvider getMenuProvider(Level world, Player player) {
		return new SimpleMenuProvider((id, inv, play) -> new ContainerGuidebook(id, player.getInventory()), CONTAINER_TITLE);
	}

}
