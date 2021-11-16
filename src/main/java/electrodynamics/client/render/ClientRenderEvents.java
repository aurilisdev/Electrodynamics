package electrodynamics.client.render;

import electrodynamics.common.item.gear.tools.electric.utils.ItemRailgun;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientRenderEvents {

    @SubscribeEvent
    public static void renderRailgunTooltip(RenderGameOverlayEvent.Post event) {

	if (ElementType.ALL.equals(event.getType())) {
	    ItemStack gunStackMainHand = Minecraft.getInstance().player.getItemBySlot(EquipmentSlot.MAINHAND);
	    ItemStack gunStackOffHand = Minecraft.getInstance().player.getItemBySlot(EquipmentSlot.OFFHAND);

	    if (gunStackMainHand.getItem() instanceof ItemRailgun) {
		renderHeatToolTip(event, gunStackMainHand);
	    } else if (gunStackOffHand.getItem() instanceof ItemRailgun) {
		renderHeatToolTip(event, gunStackOffHand);
	    }
	}
    }

    private static void renderHeatToolTip(RenderGameOverlayEvent.Post event, ItemStack stack) {
	Minecraft minecraft = Minecraft.getInstance();
	ItemRailgun railgun = (ItemRailgun) stack.getItem();
	double temperature = railgun.getTemperatureStored(stack);
	String correction = "";

	event.getMatrixStack().pushPose();

	if (temperature < 10) {
	    correction = "00";
	} else if (temperature < 100) {
	    correction = "0";
	} else {
	    correction = "";
	}

	Component currTempText = new TranslatableComponent("tooltip.electrodynamics.railguntemp", new TextComponent(temperature + correction + " C"))
		.withStyle(ChatFormatting.YELLOW);
	Component maxTempText = new TranslatableComponent("tooltip.electrodynamics.railgunmaxtemp", new TextComponent(railgun.getMaxTemp() + " C"))
		.withStyle(ChatFormatting.YELLOW);

	GuiComponent.drawCenteredString(event.getMatrixStack(), minecraft.font, currTempText, 55, 2, 0);
	GuiComponent.drawCenteredString(event.getMatrixStack(), minecraft.font, maxTempText, 48, 11, 0);

	if (temperature >= railgun.getOverheatTemp()) {
	    Component overheatWarn = new TranslatableComponent("tooltip.electrodynamics.railgunoverheat").withStyle(ChatFormatting.RED,
		    ChatFormatting.BOLD);
	    GuiComponent.drawCenteredString(event.getMatrixStack(), minecraft.font, overheatWarn, 70, 20, 0);
	}

	minecraft.getTextureManager().bindForSetup(GuiComponent.GUI_ICONS_LOCATION);

	event.getMatrixStack().popPose();
    }

}