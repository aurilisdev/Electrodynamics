package electrodynamics.common.item.gear.tools;

import java.util.List;

import electrodynamics.common.inventory.container.item.ContainerGuidebook;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.world.World;

public class ItemGuidebook extends Item {

	private static final String LINK = "https://wiki.aurilis.dev";
	private static final ITextComponent CONTAINER_TITLE = new TranslationTextComponent("container.guidebook");

	public ItemGuidebook(Properties properties) {
		super(properties);
	}

	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltips, ITooltipFlag flag) {
		tooltips.add(ElectroTextUtils.tooltip("info.guidebookuse").withStyle(TextFormatting.LIGHT_PURPLE));
		tooltips.add(ElectroTextUtils.tooltip("guidebookname").withStyle(TextFormatting.GRAY));
		super.appendHoverText(stack, world, tooltips, flag);
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand handIn) {
		if (world.isClientSide) {
			if (player.isShiftKeyDown()) {
				player.sendMessage(ElectroTextUtils.chatMessage("guidebookclick").withStyle(TextFormatting.BOLD, TextFormatting.RED).withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, LINK))), Util.NIL_UUID);
				return ActionResult.pass(player.getItemInHand(handIn));
			}
		} else {
			player.openMenu(getMenuProvider(world, player));
		}
		return super.use(world, player, handIn);
	}

	public INamedContainerProvider getMenuProvider(World world, PlayerEntity player) {
		return new SimpleNamedContainerProvider((id, inv, play) -> new ContainerGuidebook(id, player.inventory), CONTAINER_TITLE);
	}

}
