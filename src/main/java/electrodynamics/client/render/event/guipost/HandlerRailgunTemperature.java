package electrodynamics.client.render.event.guipost;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.common.item.gear.tools.electric.utils.ItemRailgun;
import electrodynamics.prefab.utilities.TextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.NamedGuiOverlay;

public class HandlerRailgunTemperature extends AbstractPostGuiOverlayHandler {

	@Override
	public void renderToScreen(NamedGuiOverlay overlay, PoseStack stack, Window window, Minecraft minecraft, float partialTicks) {
		Player player = minecraft.player;
		ItemStack gunStackMainHand = player.getItemBySlot(EquipmentSlot.MAINHAND);
		ItemStack gunStackOffHand = player.getItemBySlot(EquipmentSlot.OFFHAND);

		if (gunStackMainHand.getItem() instanceof ItemRailgun) {
			renderHeatToolTip(stack, minecraft, gunStackMainHand);
		} else if (gunStackOffHand.getItem() instanceof ItemRailgun) {
			renderHeatToolTip(stack, minecraft, gunStackOffHand);
		}

	}

	private void renderHeatToolTip(PoseStack stack, Minecraft minecraft, ItemStack item) {

		ItemRailgun railgun = (ItemRailgun) item.getItem();
		double temperature = railgun.getTemperatureStored(item);
		String correction;

		stack.pushPose();

		if (temperature < 10) {
			correction = "00";
		} else if (temperature < 100) {
			correction = "0";
		} else {
			correction = "";
		}

		TextUtils.tooltip("railguntemp", Component.literal(temperature + correction + " C"));

		Component currTempText = TextUtils.tooltip("railguntemp", Component.literal(temperature + correction + " C")).withStyle(ChatFormatting.YELLOW);
		Component maxTempText = TextUtils.tooltip("railgunmaxtemp", Component.literal(railgun.getMaxTemp() + " C")).withStyle(ChatFormatting.YELLOW);

		GuiComponent.drawCenteredString(stack, minecraft.font, currTempText, 55, 2, 0);
		GuiComponent.drawCenteredString(stack, minecraft.font, maxTempText, 48, 11, 0);

		if (temperature >= railgun.getOverheatTemp()) {
			Component overheatWarn = TextUtils.tooltip("railgunoverheat").withStyle(ChatFormatting.RED, ChatFormatting.BOLD);
			GuiComponent.drawCenteredString(stack, minecraft.font, overheatWarn, 70, 20, 0);
		}

		minecraft.getTextureManager().bindForSetup(GuiComponent.GUI_ICONS_LOCATION);

		stack.popPose();
	}

}
