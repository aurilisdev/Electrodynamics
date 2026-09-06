package electrodynamics.client.event.guipost;

import javax.annotation.Nullable;

import electrodynamics.common.item.gear.armor.types.ItemJetpack;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.api.gas.GasStack;
import voltaic.api.gas.IGasHandlerItem;
import voltaic.client.event.AbstractPostGuiOverlayHandler;
import voltaic.prefab.screen.component.CachedComponent;
import voltaic.prefab.utilities.ItemUtils;
import voltaic.prefab.utilities.VoltaicTextUtils;
import voltaic.registers.VoltaicCapabilities;
import voltaic.registers.VoltaicDataComponentTypes;

public class HandlerJetpackMode extends AbstractPostGuiOverlayHandler {

    private static final int X = 10;

    private static final CachedComponent<Integer> MODE_TEXT = new CachedComponent<>(ItemJetpack::getModeText);

    private static final CachedComponent<Long> GAS_RATIO_TEXT = new CachedComponent<>(packed -> {
	int amount = (int) (packed >>> 32);
	int capacity = (int) (packed & 0xFFFF_FFFFL);
	return VoltaicTextUtils.ratio(ChatFormatter.formatFluidMilibuckets(amount),
		ChatFormatter.formatFluidMilibuckets(capacity));
    });

    private static final CachedComponent<Integer> GAS_TEMP_TEXT = new CachedComponent<>(
	    temperature -> ChatFormatter.getChatDisplayShort(temperature, DisplayUnits.TEMPERATURE_KELVIN));

    private static final CachedComponent<Integer> GAS_PRESSURE_TEXT = new CachedComponent<>(
	    pressure -> ChatFormatter.getChatDisplayShort(pressure, DisplayUnits.PRESSURE_ATM));

    @Override
    public void renderToScreen(GuiGraphics graphics, DeltaTracker tracker, Minecraft minecraft) {
	LocalPlayer player = minecraft.player;
	if (player == null || minecraft.level == null)
	    return;
	ItemStack chestSlot = player.getInventory().armor.get(2);
	if (!ItemUtils.testItems(chestSlot.getItem(), ElectrodynamicsItems.ITEM_JETPACK.get(),
		ElectrodynamicsItems.ITEM_COMBATCHESTPLATE.get()))
	    return;
	IGasHandlerItem handler = chestSlot.getCapability(VoltaicCapabilities.CAPABILITY_GASHANDLER_ITEM);
	if (handler == null)
	    return;
	int height = graphics.guiHeight();
	Component mode = MODE_TEXT.get(chestSlot.getOrDefault(VoltaicDataComponentTypes.MODE, -1));
	GasStack gas = handler.getGasInTank(0);
	if (gas.isEmpty()) {
	    Component ratio = GAS_RATIO_TEXT.get(packInts(0, ItemJetpack.MAX_CAPACITY));
	    drawString(graphics, minecraft, mode, height - 30, 0);
	    drawString(graphics, minecraft, ratio, height - 20, -1);
	    return;
	}
	Component ratio = GAS_RATIO_TEXT.get(packInts(gas.getAmount(), ItemJetpack.MAX_CAPACITY));
	Component temperature = GAS_TEMP_TEXT.get(gas.getTemperature());
	Component pressure = GAS_PRESSURE_TEXT.get(gas.getPressure());
	drawString(graphics, minecraft, mode, height - 50, 0);
	drawString(graphics, minecraft, ratio, height - 40, -1);
	drawString(graphics, minecraft, temperature, height - 30, -1);
	drawString(graphics, minecraft, pressure, height - 20, -1);
    }

    private static void drawString(GuiGraphics graphics, Minecraft minecraft, @Nullable Component text, int y,
	    int colour) {
	if (text != null)
	    graphics.drawString(minecraft.font, text, X, y, colour);
    }

    private static long packInts(int high, int low) {
	return (long) high << 32 | low & 0xFFFF_FFFFL;
    }
}