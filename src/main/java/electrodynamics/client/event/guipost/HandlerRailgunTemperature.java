package electrodynamics.client.event.guipost;

import electrodynamics.common.item.gear.tools.electric.utils.ItemRailgun;
import electrodynamics.prefab.utilities.ElectroTextUtils;
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

    private static final CachedComponent<Double> CURR_TEMP = new CachedComponent<>(temperature -> ElectroTextUtils
	    .tooltip("railguntemp", ChatFormatter.getChatDisplayShort(temperature, DisplayUnits.TEMPERATURE_CELCIUS))
	    .withStyle(ChatFormatting.YELLOW));

    private static final CachedComponent<Double> MAX_TEMP = new CachedComponent<>(temperature -> ElectroTextUtils
	    .tooltip("railgunmaxtemp", ChatFormatter.getChatDisplayShort(temperature, DisplayUnits.TEMPERATURE_CELCIUS))
	    .withStyle(ChatFormatting.YELLOW));

    private static final CachedComponent<Integer> FLUID = new CachedComponent<>(
	    amount -> VoltaicTextUtils.ratio(ChatFormatter.formatFluidMilibuckets(amount),
		    ChatFormatter.formatFluidMilibuckets(ItemRailgun.CAPACITY)).withStyle(ChatFormatting.GRAY));

    private static final CachedComponent<Boolean> OVERHEAT = new CachedComponent<>(
	    unused -> ElectroTextUtils.tooltip("railgunoverheat").withStyle(ChatFormatting.RED, ChatFormatting.BOLD));

    @Override
    public void renderToScreen(GuiGraphics graphics, DeltaTracker tracker, Minecraft minecraft) {
	Player player = minecraft.player;
	if (player == null || minecraft.level == null)
	    return;
	ItemStack main = player.getItemBySlot(EquipmentSlot.MAINHAND);
	if (main.getItem() instanceof ItemRailgun railgun) {
	    renderHeatToolTip(graphics, minecraft, main, railgun);
	    return;
	}
	ItemStack off = player.getItemBySlot(EquipmentSlot.OFFHAND);
	if (off.getItem() instanceof ItemRailgun railgun)
	    renderHeatToolTip(graphics, minecraft, off, railgun);
    }

    private static void renderHeatToolTip(GuiGraphics graphics, Minecraft minecraft, ItemStack item,
	    ItemRailgun railgun) {
	double temperature = IItemTemperate.getTemperature(item);
	Component currentTemperature = CURR_TEMP.get(temperature);
	if (currentTemperature != null)
	    graphics.drawString(minecraft.font, currentTemperature, X, Y0, 0);
	Component maximumTemperature = MAX_TEMP.get(railgun.getMaxTemp());
	if (maximumTemperature != null)
	    graphics.drawString(minecraft.font, maximumTemperature, X, Y0 + LINE, 0);
	int lineIndex = 2;
	var fluidHandler = item.getCapability(Capabilities.FluidHandler.ITEM);
	if (fluidHandler != null) {
	    Component fluid = FLUID.get(fluidHandler.getFluidInTank(0).getAmount());
	    if (fluid != null)
		graphics.drawString(minecraft.font, fluid, X, Y0 + LINE * lineIndex, 0);
	    lineIndex++;
	}
	if (temperature >= railgun.getOverheatTemp()) {
	    Component warning = OVERHEAT.get(Boolean.TRUE);
	    if (warning != null)
		graphics.drawString(minecraft.font, warning, X, Y0 + LINE * lineIndex, 0);
	}
    }
}