package electrodynamics.client.event.guipost;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.common.item.gear.tools.electric.utils.ItemRailgun;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
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

    private static final int X = 2;
    private static final int Y0 = 2;
    private static final int LINE = 10;

    private static final CachedComponent<Double> CURR_TEMP = new CachedComponent<>(temperature -> ElectroTextUtils
	    .tooltip("railguntemp", ChatFormatter.getChatDisplayShort(temperature, DisplayUnits.TEMPERATURE_CELCIUS))
	    .withStyle(ChatFormatting.YELLOW));

    private static final CachedComponent<Double> MAX_TEMP = new CachedComponent<>(max -> ElectroTextUtils
	    .tooltip("railgunmaxtemp", ChatFormatter.getChatDisplayShort(max, DisplayUnits.TEMPERATURE_CELCIUS))
	    .withStyle(ChatFormatting.YELLOW));

    private static final CachedComponent<Boolean> OVERHEAT = new CachedComponent<>(
	    unused -> ElectroTextUtils.tooltip("railgunoverheat").withStyle(ChatFormatting.RED, ChatFormatting.BOLD));

    @Override
    public void renderToScreen(NamedGuiOverlay overlay, PoseStack stack, Window window, Minecraft minecraft,
	    float partialTicks) {

	Player player = minecraft.player;

	if (player == null || minecraft.level == null) {
	    return;
	}

	ItemStack main = player.getItemBySlot(EquipmentSlot.MAINHAND);

	if (main.getItem() instanceof ItemRailgun) {
	    renderHeatToolTip(stack, minecraft, main);
	    return;
	}

	ItemStack off = player.getItemBySlot(EquipmentSlot.OFFHAND);

	if (off.getItem() instanceof ItemRailgun) {
	    renderHeatToolTip(stack, minecraft, off);
	}
    }

    private static void renderHeatToolTip(PoseStack stack, Minecraft minecraft, ItemStack item) {

	ItemRailgun railgun = (ItemRailgun) item.getItem();
	double temperature = IItemTemperate.getTemperature(item);

	Component currentTemperature = CURR_TEMP.get(temperature);
	Component maximumTemperature = MAX_TEMP.get(railgun.getMaxTemp());

	minecraft.font.draw(stack, currentTemperature, X, Y0, 0);

	minecraft.font.draw(stack, maximumTemperature, X, Y0 + LINE, 0);

	if (temperature >= railgun.getOverheatTemp()) {
	    minecraft.font.draw(stack, OVERHEAT.get(Boolean.TRUE), X, Y0 + LINE * 2, 0);
	}
    }
}