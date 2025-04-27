package electrodynamics.client.event.guipost;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.common.item.gear.tools.electric.utils.ItemRailgun;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.NamedGuiOverlay;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.api.item.IItemTemperate;
import voltaic.client.event.AbstractPostGuiOverlayHandler;

public class HandlerRailgunTemperature extends AbstractPostGuiOverlayHandler {

	@Override
	public void renderToScreen(NamedGuiOverlay overlay, GuiGraphics graphics, Window window, Minecraft minecraft, float partialTicks) {
		Player player = minecraft.player;
		ItemStack gunStackMainHand = player.getItemBySlot(EquipmentSlot.MAINHAND);
		ItemStack gunStackOffHand = player.getItemBySlot(EquipmentSlot.OFFHAND);

		if (gunStackMainHand.getItem() instanceof ItemRailgun) {
			renderHeatToolTip(graphics, minecraft, gunStackMainHand);
		} else if (gunStackOffHand.getItem() instanceof ItemRailgun) {
			renderHeatToolTip(graphics, minecraft, gunStackOffHand);
		}

	}

	private void renderHeatToolTip(GuiGraphics graphics, Minecraft minecraft, ItemStack item) {

		ItemRailgun railgun = (ItemRailgun) item.getItem();
		double temperature = IItemTemperate.getTemperature(item);

		PoseStack stack = graphics.pose();

		stack.pushPose();

		// ElectroTextUtils.tooltip("railguntemp", Component.literal(temperature + correction + " C"));

		Component currTempText = ElectroTextUtils.tooltip("railguntemp", ChatFormatter.getChatDisplayShort(temperature, DisplayUnits.TEMPERATURE_CELCIUS)).withStyle(ChatFormatting.YELLOW);
		Component maxTempText = ElectroTextUtils.tooltip("railgunmaxtemp", ChatFormatter.getChatDisplayShort(railgun.getMaxTemp(), DisplayUnits.TEMPERATURE_CELCIUS)).withStyle(ChatFormatting.YELLOW);

		graphics.drawString(minecraft.font, currTempText, 2, 2, 0);
		graphics.drawString(minecraft.font, maxTempText, 2, 12, 0);

		if (temperature >= railgun.getOverheatTemp()) {
			Component overheatWarn = ElectroTextUtils.tooltip("railgunoverheat").withStyle(ChatFormatting.RED, ChatFormatting.BOLD);
			graphics.drawString(minecraft.font, overheatWarn, 2, 22, 0);
		}

		stack.popPose();
	}

}
