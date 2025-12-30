package electrodynamics.client.event.guipost;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.common.item.gear.tools.electric.utils.ItemRailgun;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import it.unimi.dsi.fastutil.Pair;
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
import voltaic.prefab.screen.component.CachedComponent;

public class HandlerRailgunTemperature extends AbstractPostGuiOverlayHandler {

    private static final CachedComponent<Pair<Integer, Double>> CURR_TEMP = new CachedComponent<>(
	    state -> ElectroTextUtils
		    .tooltip("railguntemp",
			    ChatFormatter.getChatDisplayShort(state.right(), DisplayUnits.TEMPERATURE_CELCIUS))
		    .withStyle(ChatFormatting.YELLOW));

    private static final CachedComponent<Double> MAX_TEMP = new CachedComponent<>(max -> ElectroTextUtils
	    .tooltip("railgunmaxtemp", ChatFormatter.getChatDisplayShort(max, DisplayUnits.TEMPERATURE_CELCIUS))
	    .withStyle(ChatFormatting.YELLOW));

    private static final CachedComponent<Boolean> OVERHEAT_WARN = new CachedComponent<>(
	    unused -> ElectroTextUtils.tooltip("railgunoverheat").withStyle(ChatFormatting.RED, ChatFormatting.BOLD));

    @Override
    public void renderToScreen(NamedGuiOverlay overlay, GuiGraphics graphics, Window window, Minecraft minecraft,
	    float partialTicks) {
	if (minecraft.player == null || minecraft.level == null) {
	    return;
	}

	Player player = minecraft.player;

	ItemStack main = player.getItemBySlot(EquipmentSlot.MAINHAND);
	if (main.getItem() instanceof ItemRailgun) {
	    renderHeatToolTip(graphics, minecraft, main);
	    return;
	}

	ItemStack off = player.getItemBySlot(EquipmentSlot.OFFHAND);
	if (off.getItem() instanceof ItemRailgun) {
	    renderHeatToolTip(graphics, minecraft, off);
	}
    }

    private static void renderHeatToolTip(GuiGraphics graphics, Minecraft minecraft, ItemStack item) {

	ItemRailgun railgun = (ItemRailgun) item.getItem();
	double temperature = IItemTemperate.getTemperature(item);

	PoseStack stack = graphics.pose();
	stack.pushPose();

	Component currTempText = CURR_TEMP.get(Pair.of(System.identityHashCode(item), temperature));
	Component maxTempText = MAX_TEMP.get(railgun.getMaxTemp());

	graphics.drawString(minecraft.font, currTempText, 2, 2, 0);
	graphics.drawString(minecraft.font, maxTempText, 2, 12, 0);

	if (temperature >= railgun.getOverheatTemp()) {
	    Component warn = OVERHEAT_WARN.get(Boolean.TRUE);
	    graphics.drawString(minecraft.font, warn, 2, 22, 0);
	}

	stack.popPose();
    }

}
