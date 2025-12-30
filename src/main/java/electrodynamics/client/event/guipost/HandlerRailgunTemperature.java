package electrodynamics.client.event.guipost;

import electrodynamics.common.item.gear.tools.electric.utils.ItemRailgun;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.api.item.IItemTemperate;
import voltaic.client.event.AbstractPostGuiOverlayHandler;
import voltaic.prefab.screen.component.CachedComponent;
import voltaic.prefab.utilities.VoltaicTextUtils;

public class HandlerRailgunTemperature extends AbstractPostGuiOverlayHandler {

    private static final int X = 2;
    private static final int Y0 = 2;
    private static final int LINE = 10;

    private static final CachedComponent<Pair<ItemStack, Double>> CURR_TEMP = new CachedComponent<>(
	    state -> ElectroTextUtils
		    .tooltip("railguntemp",
			    ChatFormatter.getChatDisplayShort(state.right(), DisplayUnits.TEMPERATURE_CELCIUS))
		    .withStyle(ChatFormatting.YELLOW));

    private static final CachedComponent<Double> MAX_TEMP = new CachedComponent<>(max -> ElectroTextUtils
	    .tooltip("railgunmaxtemp", ChatFormatter.getChatDisplayShort(max, DisplayUnits.TEMPERATURE_CELCIUS))
	    .withStyle(ChatFormatting.YELLOW));

    private static final CachedComponent<Integer> FLUID = new CachedComponent<>(
	    amount -> VoltaicTextUtils.ratio(ChatFormatter.formatFluidMilibuckets(amount),
		    ChatFormatter.formatFluidMilibuckets(ItemRailgun.CAPACITY)).withStyle(ChatFormatting.GRAY));

    private static final CachedComponent<Boolean> OVERHEAT = new CachedComponent<>(
	    unused -> ElectroTextUtils.tooltip("railgunoverheat").withStyle(ChatFormatting.RED, ChatFormatting.BOLD));

    @Override
    public void renderToScreen(GuiGraphics graphics, DeltaTracker tracker, Minecraft minecraft) {
	Player player = minecraft.player;
	if (player == null || minecraft.level == null) {
	    return;
	}

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

	Component currTempText = CURR_TEMP.get(Pair.of(item, temperature));

	Component maxTempText = MAX_TEMP.get(railgun.getMaxTemp());

	graphics.drawString(minecraft.font, currTempText, X, Y0, 0);
	graphics.drawString(minecraft.font, maxTempText, X, Y0 + LINE, 0);

	int lineIndex = 2;

	var fluidCap = item.getCapability(Capabilities.FluidHandler.ITEM);
	if (fluidCap != null) {
	    int amount = fluidCap.getFluidInTank(0).getAmount();
	    Component fluid = FLUID.get(amount);
	    graphics.drawString(minecraft.font, fluid, X, Y0 + (LINE * lineIndex), 0);
	    lineIndex++;
	}

	if (temperature >= railgun.getOverheatTemp()) {
	    Component warn = OVERHEAT.get(Boolean.TRUE);
	    graphics.drawString(minecraft.font, warn, X, Y0 + (LINE * lineIndex), 0);
	}
    }

}
