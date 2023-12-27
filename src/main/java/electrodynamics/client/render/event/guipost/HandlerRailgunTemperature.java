package electrodynamics.client.render.event.guipost;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.api.item.IItemTemperate;
import electrodynamics.common.item.gear.tools.electric.utils.ItemRailgun;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public class HandlerRailgunTemperature extends AbstractPostGuiOverlayHandler {

	@Override
	public void renderToScreen(ElementType type, MatrixStack stack, MainWindow window, Minecraft minecraft, float partialTicks) {
		
		if(type != ElementType.ALL) {
			return;
		}
		
		PlayerEntity player = minecraft.player;
		ItemStack gunStackMainHand = player.getItemBySlot(EquipmentSlotType.MAINHAND);
		ItemStack gunStackOffHand = player.getItemBySlot(EquipmentSlotType.OFFHAND);

		if (gunStackMainHand.getItem() instanceof ItemRailgun) {
			renderHeatToolTip(stack, minecraft, gunStackMainHand);
		} else if (gunStackOffHand.getItem() instanceof ItemRailgun) {
			renderHeatToolTip(stack, minecraft, gunStackOffHand);
		}

	}

	private void renderHeatToolTip(MatrixStack stack, Minecraft minecraft, ItemStack item) {

		ItemRailgun railgun = (ItemRailgun) item.getItem();
		double temperature = IItemTemperate.getTemperature(item);

		stack.pushPose();

		// ElectroTextUtils.tooltip("railguntemp", Component.literal(temperature + correction + " C"));

		ITextComponent currTempText = ElectroTextUtils.tooltip("railguntemp", ChatFormatter.getChatDisplayShort(temperature, DisplayUnit.TEMPERATURE_CELCIUS)).withStyle(TextFormatting.YELLOW);
		ITextComponent maxTempText = ElectroTextUtils.tooltip("railgunmaxtemp", ChatFormatter.getChatDisplayShort(railgun.getMaxTemp(), DisplayUnit.TEMPERATURE_CELCIUS)).withStyle(TextFormatting.YELLOW);

		minecraft.font.draw(stack, currTempText, 2, 2, 0);
		minecraft.font.draw(stack, maxTempText, 2, 12, 0);

		if (temperature >= railgun.getOverheatTemp()) {
			ITextComponent overheatWarn = ElectroTextUtils.tooltip("railgunoverheat").withStyle(TextFormatting.RED, TextFormatting.BOLD);
			minecraft.font.draw(stack, overheatWarn, 2, 22, 0);
		}
		
		minecraft.getTextureManager().bind(AbstractGui.GUI_ICONS_LOCATION);

		stack.popPose();
	}

}