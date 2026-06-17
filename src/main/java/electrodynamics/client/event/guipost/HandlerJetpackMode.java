package electrodynamics.client.event.guipost;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.common.item.gear.armor.types.ItemJetpack;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.NamedGuiOverlay;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.api.gas.GasStack;
import voltaic.api.gas.IGasHandlerItem;
import voltaic.client.event.AbstractPostGuiOverlayHandler;
import voltaic.prefab.screen.component.CachedComponent;
import voltaic.prefab.utilities.CapabilityUtils;
import voltaic.prefab.utilities.ItemUtils;
import voltaic.prefab.utilities.NBTUtils;
import voltaic.prefab.utilities.VoltaicTextUtils;
import voltaic.registers.VoltaicCapabilities;

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
	    tempK -> ChatFormatter.getChatDisplayShort(tempK, DisplayUnits.TEMPERATURE_KELVIN));

    private static final CachedComponent<Integer> GAS_PRESSURE_TEXT = new CachedComponent<>(
	    atm -> ChatFormatter.getChatDisplayShort(atm, DisplayUnits.PRESSURE_ATM));

    @Override
    public void renderToScreen(NamedGuiOverlay overlay, GuiGraphics graphics, Window window, Minecraft minecraft,
	    float partialTicks) {

	if (minecraft.player == null || minecraft.level == null) {
	    return;
	}

	ItemStack chestSlot = minecraft.player.getInventory().armor.get(2);

	if (!ItemUtils.testItems(chestSlot.getItem(), ElectrodynamicsItems.ITEM_JETPACK.get(),
		ElectrodynamicsItems.ITEM_COMBATCHESTPLATE.get())) {
	    return;
	}
	int height = graphics.guiHeight();

	PoseStack stack = graphics.pose();
	stack.pushPose();

	int modeVal = chestSlot.hasTag() ? chestSlot.getTag().getInt(NBTUtils.MODE) : -1;
	Component mode = MODE_TEXT.get(modeVal);

	IGasHandlerItem handler = chestSlot.getCapability(VoltaicCapabilities.CAPABILITY_GASHANDLER_ITEM)
		.orElse(CapabilityUtils.EMPTY_GAS_ITEM);

	GasStack gas = handler.getGasInTank(0);

	if (gas.isEmpty()) {
	    Component ratio = GAS_RATIO_TEXT.get(packInts(0, ItemJetpack.MAX_CAPACITY));
	    graphics.drawString(minecraft.font, mode, X, height - 30, 0);
	    graphics.drawString(minecraft.font, ratio, X, height - 20, -1);
	    stack.popPose();
	    return;
	}

	Component ratio = GAS_RATIO_TEXT.get(packInts(gas.getAmount(), ItemJetpack.MAX_CAPACITY));
	Component temp = GAS_TEMP_TEXT.get(gas.getTemperature());
	Component pressure = GAS_PRESSURE_TEXT.get(gas.getPressure());

	graphics.drawString(minecraft.font, mode, X, height - 50, 0);
	graphics.drawString(minecraft.font, ratio, X, height - 40, -1);
	graphics.drawString(minecraft.font, temp, X, height - 30, -1);
	graphics.drawString(minecraft.font, pressure, X, height - 20, -1);

	stack.popPose();
    }

    private static long packInts(int high, int low) {
	return (long) high << 32 | low & 0xFFFF_FFFFL;
    }

}
