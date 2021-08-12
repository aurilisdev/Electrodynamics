package electrodynamics.client.render;

import electrodynamics.common.item.gear.tools.electric.utils.Railgun;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientRenderEvents {

    @SubscribeEvent
    public static void renderRailgunTooltip(RenderGameOverlayEvent.Post event) {

	if (event.getType().equals(ElementType.ALL)) {
	    ItemStack gunStackMainHand = Minecraft.getInstance().player.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
	    ItemStack gunStackOffHand = Minecraft.getInstance().player.getItemStackFromSlot(EquipmentSlotType.OFFHAND);

	    if (gunStackMainHand.getItem() instanceof Railgun) {
		renderHeatToolTip(event, gunStackMainHand);
	    } else if (gunStackOffHand.getItem() instanceof Railgun) {
		renderHeatToolTip(event, gunStackOffHand);
	    }
	}
    }

    private static void renderHeatToolTip(RenderGameOverlayEvent.Post event, ItemStack stack) {
	Minecraft minecraft = Minecraft.getInstance();
	Railgun railgun = (Railgun) stack.getItem();
	double temperature = railgun.getTemperatureStored(stack);
	String correction = "";

	event.getMatrixStack().push();

	if (temperature < 10) {
	    correction = "00";
	} else if (temperature < 100) {
	    correction = "0";
	} else {
	    correction = "";
	}

	ITextComponent currTempText = new TranslationTextComponent("tooltip.electrodynamics.railguntemp",
		new StringTextComponent(temperature + correction + " C")).mergeStyle(TextFormatting.YELLOW);
	ITextComponent maxTempText = new TranslationTextComponent("tooltip.electrodynamics.railgunmaxtemp",
		new StringTextComponent(railgun.getMaxTemp() + " C")).mergeStyle(TextFormatting.YELLOW);

	AbstractGui.drawCenteredString(event.getMatrixStack(), minecraft.fontRenderer, currTempText, 55, 2, 0);
	AbstractGui.drawCenteredString(event.getMatrixStack(), minecraft.fontRenderer, maxTempText, 48, 11, 0);

	if (temperature >= railgun.getOverheatTemp()) {
	    ITextComponent overheatWarn = new TranslationTextComponent("tooltip.electrodynamics.railgunoverheat").mergeStyle(TextFormatting.RED,
		    TextFormatting.BOLD);
	    AbstractGui.drawCenteredString(event.getMatrixStack(), minecraft.fontRenderer, overheatWarn, 70, 20, 0);
	}

	minecraft.getTextureManager().bindTexture(AbstractGui.GUI_ICONS_LOCATION);

	event.getMatrixStack().pop();
    }

}